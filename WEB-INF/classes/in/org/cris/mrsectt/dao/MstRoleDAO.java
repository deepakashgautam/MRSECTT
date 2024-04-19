package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.MstRole;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstRoleDAO {
	static Logger log = LogManager.getLogger(MstRoleDAO.class);
	
	public ArrayList<MstRole> getSearchData(String roleIdfrom, String roleIdTo, String roleName, String dept, String auth){
		
		ArrayList<MstRole> arrList = new ArrayList<MstRole>();
		DBConnection con =  new DBConnection();
		ArrayList<String> params = new ArrayList<String>();
		
		String strSQL = " SELECT ROLEID, ROLENAME, NVL((SELECT DISTINCT A.DEPTNAME FROM MSTDEPT A WHERE A.DEPTCODE=MSTROLE.DEPTCODE),'N/A') DEPTCODE, NVL((SELECT DISTINCT A.AUTHNAME FROM MSTAUTHLEVEL A WHERE A.AUTHLEVEL=MSTROLE.AUTHLEVEL),'N/A') AUTHLEVEL";
		strSQL += " FROM MSTROLE WHERE 1=1";
		String tempSQL = "";
		
		/*tempSQL += (roleIdfrom.trim().length() > 0 && roleIdTo.trim().length() > 0) ? " AND ROLEID BETWEEN "+roleIdfrom+" AND "+roleIdTo+" " : "";
		tempSQL += (roleIdfrom.trim().length() > 0 && roleIdTo.trim().length() == 0) ? " AND ROLEID = "+roleIdfrom+" " : "";
		tempSQL += (roleIdfrom.trim().length() == 0 && roleIdTo.trim().length() > 0) ? " AND ROLEID = "+roleIdTo+" " : "";
		tempSQL += roleName.trim().length() > 0 ? " AND UPPER(ROLENAME) LIKE UPPER('%"+roleName+"%')" : "";
		tempSQL += dept.trim().length() > 0 ? " AND DEPTCODE = '"+dept+"'" : "";
		tempSQL += auth.trim().length() > 0 ? " AND AUTHLEVEL = '"+auth+"'" : "";*/
		
		params.add("");
		if(roleIdfrom.trim().length() > 0 && roleIdTo.trim().length() > 0)
		{
			tempSQL +=" AND ROLEID BETWEEN ? AND ? ";	
			params.add(roleIdfrom);
			params.add(roleIdTo);
			
		}
		
		if(roleIdfrom.trim().length() > 0 && roleIdTo.trim().length() == 0)
		{
			tempSQL +=" AND ROLEID = ? ";
			params.add(roleIdfrom);
		}
		
		if(roleIdfrom.trim().length() == 0 && roleIdTo.trim().length() > 0)
		{
			tempSQL +=" AND ROLEID = ? ";
			params.add(roleIdTo);
		}
		
		if(roleName.trim().length() > 0)
		{
			tempSQL +=" AND UPPER(ROLENAME) LIKE UPPER(?)";
		    params.add("%"+roleName+"%");
		}
		
		if(dept.trim().length() > 0)
		{
			tempSQL +=" AND DEPTCODE = ?";
		    params.add(dept);
		}
		
		if(auth.trim().length() > 0)
		{
			tempSQL +=" AND AUTHLEVEL = ?";
		    params.add(auth);
		}
		
		strSQL = strSQL+tempSQL;
		strSQL += " ORDER BY ROLEID";
		try {
			  log.debug(strSQL);
			  con.openConnection();
			  //ResultSet rs =  con.select(strSQL);

              PreparedStatement ps = con.setPrepStatement(strSQL);
			  for(int k=1;k<params.size();k++){
	    		ps.setString(k, params.get(k));
	    		//System.out.println("k :"+k+" params "+params.get(k));
	    	   }
			  ResultSet rs =ps.executeQuery();
			
			  while (rs.next()) {
				MstRole bn =  new MstRole();
				bn.setROLEID(rs.getString("ROLEID"));
				bn.setROLENAME(rs.getString("ROLENAME"));
				bn.setDEPTCODE(rs.getString("DEPTCODE"));
				bn.setAUTHLEVEL(rs.getString("AUTHLEVEL"));
				arrList.add(bn);
			}
			rs.close();
			//System.out.println("getSearchData executed");
		} catch(SQLException e)		{
			log.fatal(e,e);
		}finally{
			
			con.closeConnection();
		}
		return arrList;
	}
	
	
	public String saveRoleID(MstRole bn)
	{
		///MstRole bn = new MstRole();
		DBConnection con = new DBConnection();
		String strSQL = "";
		String isDataSaved = "";
		int row;
		try
		{
			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement("SELECT ROLEID FROM MSTROLE WHERE ROLEID=?");
			ps.setString(1, bn.getROLEID());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				/*strSQL = " UPDATE MSTROLE SET ROLENAME = '"+bn.getROLENAME()+"'," +
						 " DEPTCODE = '"+bn.getDEPTCODE()+"', " +
				 		 " AUTHLEVEL = '"+bn.getAUTHLEVEL()+"'," +
				 		 " FNAME = '"+bn.getFNAME()+"'" +
						 " WHERE ROLEID = '"+bn.getROLEID()+"' ";
				log.fatal(strSQL);
				con.update(strSQL);*/
				strSQL = " UPDATE MSTROLE SET ROLENAME = ?," +
						 " DEPTCODE = ?, " +
				 		 " AUTHLEVEL = ?," +
				 		 " FNAME = ? " +
						 " WHERE ROLEID = ? ";
				
				PreparedStatement ps1 = con.setPrepStatement(strSQL);
				ps1.setString(1, bn.getROLENAME());
				ps1.setString(2, bn.getDEPTCODE());
				ps1.setString(3, bn.getAUTHLEVEL());
				ps1.setString(4, bn.getFNAME());
				ps1.setString(5, bn.getROLEID());
			    row=ps1.executeUpdate();
			    
			   // System.out.println("saveRoleID::TOTAL ROWS UPDATED:"+ row);
				isDataSaved = "GRNSave operation successful for Role ID : " + bn.getROLEID();
			}
			else
			{
				if (bn.getROLEID().length() <= 0) {
					bn.setROLEID(CommonDAO.getNextIdRole(con));
				}
				/*strSQL = " INSERT INTO MSTROLE(ROLEID, ROLENAME, DEPTCODE, AUTHLEVEL,FNAME)" +
						 " VALUES("+bn.getROLEID()+",'"+bn.getROLENAME()+"','"+bn.getDEPTCODE()+"','"+bn.getAUTHLEVEL()+"','"+bn.getFNAME()+"')";
				log.fatal(strSQL);
			    con.update(strSQL);*/
			    
			    strSQL = " INSERT INTO MSTROLE(ROLEID, ROLENAME, DEPTCODE, AUTHLEVEL,FNAME)" +
						 " VALUES(?,?,?,?,?)";
			
			    PreparedStatement ps2 = con.setPrepStatement(strSQL);
			    ps2.setString(1, bn.getROLEID());
			    ps2.setString(2, bn.getROLENAME());
			    ps2.setString(3, bn.getDEPTCODE());
			    ps2.setString(4, bn.getAUTHLEVEL());
			    ps2.setString(5, bn.getFNAME());
			    row=ps2.executeUpdate();
			    //System.out.println("saveRoleID::TOTAL ROWS INSERTED:"+ row);
			    
				isDataSaved = "GRNSave operation successful for Role ID : " + bn.getROLEID();
			}
		} catch (SQLException sql) {
				con.rollback();
				log.fatal(sql, sql);
				isDataSaved = "REDSave operation failure for Role ID : " + bn.getROLEID() + ". ERROR--> " + sql.getMessage();
				(new CommonDAO()).generateMessage("TESTLOGIN",isDataSaved);
			} finally {
				con.commit();
				con.closeConnection();
			}
		return isDataSaved;
	}


	public MstRole getRoleData(String roleid) {
		MstRole roleBean = new MstRole();

		DBConnection con =  new DBConnection();
		try {

			/*String strSQL = " SELECT ROLEID, ROLENAME, DEPTCODE, AUTHLEVEL,FNAME FROM MSTROLE WHERE ROLEID = "
					+ roleid + "" + " ORDER BY ROLEID ";*/
			
			String strSQL = " SELECT ROLEID, ROLENAME, DEPTCODE, AUTHLEVEL,FNAME FROM MSTROLE WHERE ROLEID = ? "
					      + " ORDER BY ROLEID ";
			
			log.debug(strSQL);
			con.openConnection();
			//ResultSet rs = con.select(strSQL);
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1,roleid);
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				roleBean = new MstRole();
				roleBean.setROLEID(rs.getString("ROLEID"));
				roleBean.setROLENAME(rs.getString("ROLENAME"));
				roleBean.setDEPTCODE(rs.getString("DEPTCODE"));
				roleBean.setAUTHLEVEL(rs.getString("AUTHLEVEL"));
				roleBean.setFNAME(rs.getString("FNAME"));
			}
			rs.close();
			//System.out.println("getRoleData executed");
			
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}

		return roleBean;

	}
}