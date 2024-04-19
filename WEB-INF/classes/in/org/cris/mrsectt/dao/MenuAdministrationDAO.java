package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import in.org.cris.mrsectt.dbConnection.DBConnection;

public class MenuAdministrationDAO {

	public void addMenu(String groupid, String menuid, String flag) {

		DBConnection con = new DBConnection();
		String query = "";
		boolean parentflag = false;
		boolean childflag = true;

		try {
			con.openConnection();

			if (!menuid.equalsIgnoreCase("0")) {
				parentflag = insertParent(groupid, menuid, flag, con);
				if (flag.equalsIgnoreCase("H")) {
					childflag = insertChild(groupid, menuid, flag, con);
				}

				if (parentflag == true && childflag == true) {
					con.commit();
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}

	}

	public void RemoveMenu(String groupid, String menuid, String flag) {
		DBConnection con = new DBConnection();
		String query = "";
		boolean parentflag = false;
		boolean childflag = true;

		try {
			con.openConnection();

			if (!menuid.equalsIgnoreCase("0")) {
				parentflag = removeParent(groupid, menuid, flag, con);
				if (flag.equalsIgnoreCase("H")) {
					childflag = removeChild(groupid, menuid, flag, con);
				}

				if (parentflag == true && childflag == true) {
					con.commit();
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
	}

	public boolean removeParent(String groupid, String menuid, String flag,
			DBConnection con) {

		/*String query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE "
				+ " PARENTID =(SELECT PARENTID FROM USERMENUTABLE WHERE "
				+ " GROUPID='" + groupid + "' AND MENUID='" + menuid
				+ "') AND UPPER(GROUPID)=UPPER('" + groupid + "')";*/
		

		//System.out.println("query is:" + query);
		
		String query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE "
				+ " PARENTID =(SELECT PARENTID FROM USERMENUTABLE WHERE "
				+ " GROUPID= ? AND MENUID= ? ) AND UPPER(GROUPID)=UPPER(?)";
		
		String parentid = "";
		boolean funresult = false;
		int count = 0;
		String rootmenuid = "";
		try {
			
			PreparedStatement ps = con.setPrepStatement(query);
			ps.setString(1,groupid);
			ps.setString(2,menuid);
			ps.setString(3,groupid);
			ResultSet rs=ps.executeQuery();
			
			//ResultSet rs = con.select(query);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
			//System.out.println("removeParent::1::executed:");
			if (count != 1) {
				/*query = "DELETE FROM  USERMENUTABLE"
						+ " WHERE UPPER(GROUPID)=UPPER('" + groupid + "') "
						+ " AND MENUID='" + menuid + "' ";
				//System.out.println("DELETE QUERY:" + query);
				con.insert(query);*/
				
				query = "DELETE FROM  USERMENUTABLE"
						+ " WHERE UPPER(GROUPID)=UPPER(?) "
						+ " AND MENUID=? ";
				
				ps = con.setPrepStatement(query);
				ps.setString(1,groupid);
				ps.setString(2,menuid);
				int row=ps.executeUpdate();
				//System.out.println("removeParent::TOTAL ROWS DELETED:"+ row);
				
				return true;
			}

			/*query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND MENUID='"
					+ menuid + "'";
			//System.out.println("query is:" + query);
			ResultSet rs1 = con.select(query);*/
			
			query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND MENUID= ?";
				
			PreparedStatement ps2 = con.setPrepStatement(query);
			ps2.setString(1,menuid);
			ResultSet rs1 = ps2.executeQuery();
			
			if (rs1.next()) {
				parentid = rs1.getString(1);
				rootmenuid = rs1.getString(2);
			}

			//System.out.println("removeParent::2::executed:");
			
			if (parentid.equalsIgnoreCase("0")
					&& rootmenuid.equalsIgnoreCase("0")) {
				return true;
			} else {
				funresult = removeParent(groupid, parentid, flag, con);
				/*query = "DELETE FROM  USERMENUTABLE"
						+ " WHERE UPPER(GROUPID)=UPPER('" + groupid
						+ "') AND MENUID='" + menuid + "' ";
				//System.out.println("DELETE QUERY:" + query);
				con.insert(query);*/
				query = "DELETE FROM  USERMENUTABLE"
						+ " WHERE UPPER(GROUPID)=UPPER(?) AND MENUID=? ";
				
				ps2 = con.setPrepStatement(query);
				ps2.setString(1,groupid);
				ps2.setString(2,menuid);
				
				int row=ps2.executeUpdate();
				//System.out.println("removeParent::2::TOTAL ROWS DELETED:"+ row);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;

	}

	public boolean removeChild(String groupid, String menuid, String flag,
			DBConnection con) {
		String query = "";
		int count = 0;
		boolean funresult = false;
		String parentid = "";
		String childmenuid = "";

		
		try {
			/*query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID='"
					+ menuid + "'";
			//System.out.println("query is:" + query);
			ResultSet rs = con.select(query);*/
			
			query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID= ?";		
			PreparedStatement ps = con.setPrepStatement(query);
			ps.setString(1,menuid);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
			//System.out.println("removeChild::1::executed:");
			if (count == 0) {
				return true;
			}

			/*query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID='"
					+ menuid + "'";
			//System.out.println("query is:" + query);

			ResultSet rs1 = con.select(query);*/
			
			query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID=?";
			ps = con.setPrepStatement(query);
			ps.setString(1,menuid);
			ResultSet rs1 = ps.executeQuery();
			
			ArrayList arr = new ArrayList();
			while (rs1.next()) {
				arr.add(rs1.getString(2));

			}

			//System.out.println("removeChild::2::executed:");
			
			for (int i = 0; i < arr.size(); i++) {
				funresult = removeChild(groupid, arr.get(i).toString(), flag,
						con);
				if (funresult == true) {

					/*query = "delete from USERMENUTABLE WHERE UPPER(GROUPID)=UPPER('"
							+ groupid
							+ "') "
							+ " AND MENUID='"
							+ arr.get(i).toString() + "'";

					//System.out.println("INSETR QUERY:" + query);
					con.insert(query);*/
					
					query = "delete from USERMENUTABLE WHERE UPPER(GROUPID)=UPPER(?) "
							+ " AND MENUID=?";
					ps = con.setPrepStatement(query);
					ps.setString(1,groupid);
					ps.setString(2,arr.get(i).toString());
					int row=ps.executeUpdate();
					//System.out.println("removeChild::TOTAL ROWS DELETED:"+ row);
					
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;

	}

	public boolean insertParent(String groupid, String menuid, String flag,
			DBConnection con) {

		/*String query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE UPPER(GROUPID)=UPPER('"
				+ groupid + "') " + " AND MENUID='" + menuid + "'";*/

		//System.out.println("INSERT PARENT SELECT query is 1:" + query);
		
		String query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE UPPER(GROUPID)=UPPER(?)  AND MENUID=?";
		
		String parentid = "";
		boolean funresult = false;
		int count = 0;
		String rootmenuid = "";
		try {
			
			PreparedStatement ps = con.setPrepStatement(query);
			ps.setString(1,groupid);
			ps.setString(2,menuid);
			ResultSet rs=ps.executeQuery();
			
			//ResultSet rs = con.select(query);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			//System.out.println("insertParent::1::executed:");
			if (count == 1) {
				return true;
			}
			if (count == 0) {
				/*query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND MENUID='"
						+ menuid + "'";*/
				//System.out.println("query is:" + query);

				query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND MENUID=?";
				
				 ps = con.setPrepStatement(query);
				 ps.setString(1,menuid);
				 ResultSet rs1=ps.executeQuery();
				//ResultSet rs1 = con.select(query);
				 
				if (rs1.next()) {
					parentid = rs1.getString(1);
					rootmenuid = rs1.getString(2);
				}
				
				//System.out.println("insertParent::2::executed:");
				
				if (parentid.equalsIgnoreCase("0")
						&& rootmenuid.equalsIgnoreCase("0")) {
					return true;
				} else {
					funresult = insertParent(groupid, parentid, flag, con);
					if (funresult == true) {
						/*query = "INSERT INTO USERMENUTABLE"
								+ " (GROUPID,menuid,parentid,flag,url,menutext,root)"
								+ " SELECT '"
								+ groupid
								+ "' GROUPID,menuid,parentid,flag,url,menutext,root FROM USERMENUTABLE"
								+ " WHERE GROUPID='rfms' AND MENUID='" + menuid
								+ "' ";
						//System.out.println("INSETR QUERY:" + query);
						con.insert(query);*/
						
						query = "INSERT INTO USERMENUTABLE"
								+ " (GROUPID,menuid,parentid,flag,url,menutext,root)"
								+ " SELECT ? "
								+ " GROUPID,menuid,parentid,flag,url,menutext,root FROM USERMENUTABLE"
								+ " WHERE GROUPID='rfms' AND MENUID= ?"; 
								
						ps = con.setPrepStatement(query);
						ps.setString(1,groupid);
						ps.setString(2,menuid);
						int row =ps.executeUpdate();
						//System.out.println("insertParent::TOTAL ROWS INSERTED:"+ row);
						
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;

	}

	public boolean insertChild(String groupid, String menuid, String flag,
			DBConnection con) {
		String query = "";
		int count = 0;
		boolean funresult = false;
		String parentid = "";
		String childmenuid = "";

		try {
			/*query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID='"
					+ menuid + "'";
			//System.out.println("query is:" + query);
			ResultSet rs = con.select(query);*/
			
			query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID= ?";
			
			PreparedStatement ps = con.setPrepStatement(query);
			ps.setString(1,menuid);
			ResultSet rs=ps.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
			//System.out.println("insertChild::1::executed:");
			if (count == 0) {
				return true;
			}

			/*query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID='"
					+ menuid + "'";*/
			//System.out.println("query is:" + query);

			query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID= ?";
			ps = con.setPrepStatement(query);
			ps.setString(1,menuid);
			ResultSet rs1=ps.executeQuery();
			
			//ResultSet rs1 = con.select(query);
			ArrayList arr = new ArrayList();
			while (rs1.next()) {
				arr.add(rs1.getString(2));

			}
			//System.out.println("insertChild::2::executed:");
			for (int i = 0; i < arr.size(); i++) {
				funresult = insertChild(groupid, arr.get(i).toString(), flag,
						con);
				if (funresult == true) {
					/*query = "INSERT INTO USERMENUTABLE"
							+ " (GROUPID,menuid,parentid,flag,url,menutext,root)"
							+ " SELECT '"
							+ groupid
							+ "' GROUPID,menuid,parentid,flag,url,menutext,root FROM USERMENUTABLE"
							+ " WHERE GROUPID='rfms' AND MENUID='"
							+ arr.get(i).toString() + "' ";
					//System.out.println("INSETR QUERY:" + query);
					con.insert(query);*/
					
					query = "INSERT INTO USERMENUTABLE"
							+ " (GROUPID,menuid,parentid,flag,url,menutext,root)"
							+ " SELECT ? "
							+ " GROUPID,menuid,parentid,flag,url,menutext,root FROM USERMENUTABLE"
							+ " WHERE GROUPID='rfms' AND MENUID=? ";
							
					ps = con.setPrepStatement(query);
					ps.setString(1,groupid);
					ps.setString(2,arr.get(i).toString());
					int row=ps.executeUpdate();
					//System.out.println("insertChild::TOTAL ROWS INSERTED:"+ row);
					
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;

	}

	public void insertMenu(String parentid, String nodalflag, String menutext,
			String url, String groupid) {

		DBConnection con = new DBConnection();
		String query = "";
		String root = "";
		String menuid = "";
		
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		
		try {
			con.openConnection();

			if (parentid.equalsIgnoreCase("0")) {
				nodalflag = "H";
				url = "#";

				/*query = "SELECT NVL(MAX(ROOT),0)+1 FROM USERMENUTABLE WHERE GROUPID='"
						+ groupid + "' ";*/
				
				query = "SELECT NVL(MAX(ROOT),0)+1 FROM USERMENUTABLE WHERE GROUPID=?";
				params.add(groupid);
				
			} else {

				/*query = "SELECT ROOT FROM USERMENUTABLE WHERE GROUPID='"
						+ groupid + "' AND MENUID='" + parentid + "'";*/
				
				query = "SELECT ROOT FROM USERMENUTABLE WHERE GROUPID=? AND MENUID=?";
				params.add(groupid);
				params.add(parentid);
				
				
			}

			//System.out.println("query is:" + query);
			PreparedStatement ps = con.setPrepStatement(query);
			for(int k=1;k<params.size();k++){
	    		ps.setString(k, params.get(k));
	    		//System.out.println("k :"+k+" params "+params.get(k));	
	    	}
			ResultSet rs=ps.executeQuery();
			//ResultSet rs = con.select(query);
			if (rs.next()) {
				root = rs.getString(1);
			}

			//System.out.println("insertMenu::1::executed:");
			
			if (parentid.equalsIgnoreCase("0")) {
				menuid = root;
			} else {
				/*query = "SELECT PARENTID||'.'||(MAX(TO_NUMBER(SUBSTR(MENUID,LENGTH(PARENTID)+2))) +1) MENUID"
						+ " FROM USERMENUTABLE A WHERE  GROUPID='rfms' AND PARENTID='"
						+ parentid + "'" + " GROUP BY PARENTID";

				//System.out.println("query is:" + query);
				rs = con.select(query);*/
				
				query = "SELECT PARENTID||'.'||(MAX(TO_NUMBER(SUBSTR(MENUID,LENGTH(PARENTID)+2))) +1) MENUID"
						+ " FROM USERMENUTABLE A WHERE  GROUPID='rfms' AND PARENTID=?  GROUP BY PARENTID";
				ps = con.setPrepStatement(query);
				ps.setString(1, parentid);
				rs=ps.executeQuery();
				
				if (rs.next()) {
					menuid = rs.getString(1);
				}
				
				//System.out.println("insertMenu::2::executed:");
			}

			//System.out.println("MENU ID IS  is:" + menuid);

			/*query = "INSERT INTO USERMENUTABLE"
					+ " (GROUPID,menuid,parentid,flag,url,menutext,root) VALUES "
					+ " ('" + groupid + "','" + menuid + "','" + parentid
					+ "','" + nodalflag + "','" + url + "','" + menutext
					+ "','" + root + "') ";
			//System.out.println("INSETR QUERY:" + query);
			con.insert(query);*/
			
			query = "INSERT INTO USERMENUTABLE"
					+ " (GROUPID,menuid,parentid,flag,url,menutext,root) "
					+ "VALUES(?,?,?,?,?,?,?) ";
					
			ps = con.setPrepStatement(query);
			ps.setString(1, groupid);
			ps.setString(2, menuid);
			ps.setString(3, parentid);
			ps.setString(4, nodalflag);
			ps.setString(5, url);
			ps.setString(6, menutext);
			ps.setString(7, root);
			
			int row=ps.executeUpdate();
			//System.out.println("insertMenu::TOTAL ROWS INSERTED:"+ row);

		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}

	}

}
