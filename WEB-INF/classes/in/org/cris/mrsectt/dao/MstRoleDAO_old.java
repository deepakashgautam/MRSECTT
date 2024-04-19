package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.MstRole;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstRoleDAO_old {
	static Logger log = LogManager.getLogger(MstRoleDAO_old.class);
	
	public ArrayList<MstRole> getSearchData(String roleIdfrom, String roleIdTo, String roleName, String dept, String auth){
		
		ArrayList<MstRole> arrList = new ArrayList<MstRole>();
		DBConnection con =  new DBConnection();
		
		String strSQL = " SELECT ROLEID, ROLENAME, NVL((SELECT DISTINCT A.DEPTNAME FROM MSTDEPT A WHERE A.DEPTCODE=MSTROLE.DEPTCODE),'N/A') DEPTCODE, NVL((SELECT DISTINCT A.AUTHNAME FROM MSTAUTHLEVEL A WHERE A.AUTHLEVEL=MSTROLE.AUTHLEVEL),'N/A') AUTHLEVEL";
		strSQL += " FROM MSTROLE WHERE 1=1";
		String tempSQL = "";
		
		tempSQL += (roleIdfrom.trim().length() > 0 && roleIdTo.trim().length() > 0) ? " AND ROLEID BETWEEN "+roleIdfrom+" AND "+roleIdTo+" " : "";
		tempSQL += (roleIdfrom.trim().length() > 0 && roleIdTo.trim().length() == 0) ? " AND ROLEID = "+roleIdfrom+" " : "";
		tempSQL += (roleIdfrom.trim().length() == 0 && roleIdTo.trim().length() > 0) ? " AND ROLEID = "+roleIdTo+" " : "";
		tempSQL += roleName.trim().length() > 0 ? " AND UPPER(ROLENAME) LIKE UPPER('%"+roleName+"%')" : "";
		tempSQL += dept.trim().length() > 0 ? " AND DEPTCODE = '"+dept+"'" : "";
		tempSQL += auth.trim().length() > 0 ? " AND AUTHLEVEL = '"+auth+"'" : "";
		strSQL = strSQL+tempSQL;
		strSQL += " ORDER BY ROLEID";
		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				MstRole bn =  new MstRole();
				bn.setROLEID(rs.getString("ROLEID"));
				bn.setROLENAME(rs.getString("ROLENAME"));
				bn.setDEPTCODE(rs.getString("DEPTCODE"));
				bn.setAUTHLEVEL(rs.getString("AUTHLEVEL"));
				arrList.add(bn);
			}
			rs.close();
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
		try
		{
			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement("SELECT ROLEID FROM MSTROLE WHERE ROLEID=?");
			ps.setString(1, bn.getROLEID());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				strSQL = " UPDATE MSTROLE SET ROLENAME = '"+bn.getROLENAME()+"'," +
						 " DEPTCODE = '"+bn.getDEPTCODE()+"', " +
				 		 " AUTHLEVEL = '"+bn.getAUTHLEVEL()+"'," +
				 		 " FNAME = '"+bn.getFNAME()+"'" +
						 " WHERE ROLEID = '"+bn.getROLEID()+"' ";
				log.fatal(strSQL);
				con.update(strSQL);
				isDataSaved = "GRNSave operation successful for Role ID : " + bn.getROLEID();
			}
			else
			{
				if (bn.getROLEID().length() <= 0) {
					bn.setROLEID(CommonDAO.getNextId(con, "ROLEID", "MSTROLE"));
				}
				strSQL = " INSERT INTO MSTROLE(ROLEID, ROLENAME, DEPTCODE, AUTHLEVEL,FNAME)" +
						 " VALUES("+bn.getROLEID()+",'"+bn.getROLENAME()+"','"+bn.getDEPTCODE()+"','"+bn.getAUTHLEVEL()+"','"+bn.getFNAME()+"')";
				log.fatal(strSQL);
			    con.update(strSQL);
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

			String strSQL = " SELECT ROLEID, ROLENAME, DEPTCODE, AUTHLEVEL,FNAME FROM MSTROLE WHERE ROLEID = "
					+ roleid + "" + " ORDER BY ROLEID ";

			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				roleBean = new MstRole();
				roleBean.setROLEID(rs.getString("ROLEID"));
				roleBean.setROLENAME(rs.getString("ROLENAME"));
				roleBean.setDEPTCODE(rs.getString("DEPTCODE"));
				roleBean.setAUTHLEVEL(rs.getString("AUTHLEVEL"));
				roleBean.setFNAME(rs.getString("FNAME"));
			}
			rs.close();

		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}

		return roleBean;

	}
}