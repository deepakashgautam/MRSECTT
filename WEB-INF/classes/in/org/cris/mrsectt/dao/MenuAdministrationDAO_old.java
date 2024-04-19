package in.org.cris.mrsectt.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import in.org.cris.mrsectt.dbConnection.DBConnection;

public class MenuAdministrationDAO_old {

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

		String query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE "
				+ " PARENTID =(SELECT PARENTID FROM USERMENUTABLE WHERE "
				+ " GROUPID='" + groupid + "' AND MENUID='" + menuid
				+ "') AND UPPER(GROUPID)=UPPER('" + groupid + "')";

		//System.out.println("query is:" + query);
		String parentid = "";
		boolean funresult = false;
		int count = 0;
		String rootmenuid = "";
		try {
			ResultSet rs = con.select(query);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count != 1) {
				query = "DELETE FROM  USERMENUTABLE"
						+ " WHERE UPPER(GROUPID)=UPPER('" + groupid + "') "
						+ " AND MENUID='" + menuid + "' ";
				//System.out.println("DELETE QUERY:" + query);
				con.insert(query);
				return true;
			}

			query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND MENUID='"
					+ menuid + "'";
			//System.out.println("query is:" + query);

			ResultSet rs1 = con.select(query);
			if (rs1.next()) {
				parentid = rs1.getString(1);
				rootmenuid = rs1.getString(2);
			}

			if (parentid.equalsIgnoreCase("0")
					&& rootmenuid.equalsIgnoreCase("0")) {
				return true;
			} else {
				funresult = removeParent(groupid, parentid, flag, con);
				query = "DELETE FROM  USERMENUTABLE"
						+ " WHERE UPPER(GROUPID)=UPPER('" + groupid
						+ "') AND MENUID='" + menuid + "' ";
				//System.out.println("DELETE QUERY:" + query);
				con.insert(query);
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
			query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID='"
					+ menuid + "'";
			//System.out.println("query is:" + query);
			ResultSet rs = con.select(query);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count == 0) {
				return true;
			}

			query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID='"
					+ menuid + "'";
			//System.out.println("query is:" + query);

			ResultSet rs1 = con.select(query);
			ArrayList arr = new ArrayList();
			while (rs1.next()) {
				arr.add(rs1.getString(2));

			}

			for (int i = 0; i < arr.size(); i++) {
				funresult = removeChild(groupid, arr.get(i).toString(), flag,
						con);
				if (funresult == true) {

					query = "delete from USERMENUTABLE WHERE UPPER(GROUPID)=UPPER('"
							+ groupid
							+ "') "
							+ " AND MENUID='"
							+ arr.get(i).toString() + "'";

					//System.out.println("INSETR QUERY:" + query);
					con.insert(query);
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

		String query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE UPPER(GROUPID)=UPPER('"
				+ groupid + "') " + " AND MENUID='" + menuid + "'";

		//System.out.println("INSERT PARENT SELECT query is 1:" + query);
		String parentid = "";
		boolean funresult = false;
		int count = 0;
		String rootmenuid = "";
		try {
			ResultSet rs = con.select(query);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count == 1) {
				return true;
			}
			if (count == 0) {
				query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND MENUID='"
						+ menuid + "'";
				//System.out.println("query is:" + query);

				ResultSet rs1 = con.select(query);
				if (rs1.next()) {
					parentid = rs1.getString(1);
					rootmenuid = rs1.getString(2);
				}
				if (parentid.equalsIgnoreCase("0")
						&& rootmenuid.equalsIgnoreCase("0")) {
					return true;
				} else {
					funresult = insertParent(groupid, parentid, flag, con);
					if (funresult == true) {
						query = "INSERT INTO USERMENUTABLE"
								+ " (GROUPID,menuid,parentid,flag,url,menutext,root)"
								+ " SELECT '"
								+ groupid
								+ "' GROUPID,menuid,parentid,flag,url,menutext,root FROM USERMENUTABLE"
								+ " WHERE GROUPID='rfms' AND MENUID='" + menuid
								+ "' ";
						//System.out.println("INSETR QUERY:" + query);
						con.insert(query);
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
			query = "SELECT COUNT(*) FROM USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID='"
					+ menuid + "'";
			//System.out.println("query is:" + query);
			ResultSet rs = con.select(query);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count == 0) {
				return true;
			}

			query = "SELECT PARENTID,menuid FROM  USERMENUTABLE WHERE GROUPID='rfms' AND PARENTID='"
					+ menuid + "'";
			//System.out.println("query is:" + query);

			ResultSet rs1 = con.select(query);
			ArrayList arr = new ArrayList();
			while (rs1.next()) {
				arr.add(rs1.getString(2));

			}

			for (int i = 0; i < arr.size(); i++) {
				funresult = insertChild(groupid, arr.get(i).toString(), flag,
						con);
				if (funresult == true) {
					query = "INSERT INTO USERMENUTABLE"
							+ " (GROUPID,menuid,parentid,flag,url,menutext,root)"
							+ " SELECT '"
							+ groupid
							+ "' GROUPID,menuid,parentid,flag,url,menutext,root FROM USERMENUTABLE"
							+ " WHERE GROUPID='rfms' AND MENUID='"
							+ arr.get(i).toString() + "' ";
					//System.out.println("INSETR QUERY:" + query);
					con.insert(query);
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

		try {
			con.openConnection();

			if (parentid.equalsIgnoreCase("0")) {
				nodalflag = "H";
				url = "#";

				query = "SELECT NVL(MAX(ROOT),0)+1 FROM USERMENUTABLE WHERE GROUPID='"
						+ groupid + "' ";
			} else {

				query = "SELECT ROOT FROM USERMENUTABLE WHERE GROUPID='"
						+ groupid + "' AND MENUID='" + parentid + "'";
			}

			//System.out.println("query is:" + query);
			ResultSet rs = con.select(query);
			if (rs.next()) {
				root = rs.getString(1);
			}

			if (parentid.equalsIgnoreCase("0")) {
				menuid = root;
			} else {
				query = "SELECT PARENTID||'.'||(MAX(TO_NUMBER(SUBSTR(MENUID,LENGTH(PARENTID)+2))) +1) MENUID"
						+ " FROM USERMENUTABLE A WHERE  GROUPID='rfms' AND PARENTID='"
						+ parentid + "'" + " GROUP BY PARENTID";

				//System.out.println("query is:" + query);
				rs = con.select(query);
				if (rs.next()) {
					menuid = rs.getString(1);
				}
			}

			//System.out.println("MENU ID IS  is:" + menuid);

			query = "INSERT INTO USERMENUTABLE"
					+ " (GROUPID,menuid,parentid,flag,url,menutext,root) VALUES "
					+ " ('" + groupid + "','" + menuid + "','" + parentid
					+ "','" + nodalflag + "','" + url + "','" + menutext
					+ "','" + root + "') ";
			//System.out.println("INSETR QUERY:" + query);
			con.insert(query);

		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}

	}

}
