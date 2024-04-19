package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstLoginDAO_old {
	static Logger log = LogManager.getLogger(MstRoleDAO.class);
	
	public ArrayList<MstLogin> getSearchData(String loginId, String loginName, String loginDesignation, String roleID){
		
		ArrayList<MstLogin> arrList = new ArrayList<MstLogin>();
		DBConnection con =  new DBConnection();
		
		String strSQL = " SELECT LOGINID, PASSWORD, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = LOGINASROLEID) LOGINASROLEID, ISACTIVELOGIN, NVL(TO_CHAR(LASTLOGINDATE,'DD/MM/YYYY'),'') LASTLOGINDATE, TO_CHAR(STARTDATE,'DD/MM/YYYY') STARTDATE, NVL(TO_CHAR(ENDDATE,'DD/MM/YYYY'),'N/A') ENDDATE ," +
							" LOGINDESIGNATION, LOGINNAME FROM MSTLOGIN WHERE 1=1 ";
		String tempSQL = "";
		tempSQL += loginId.trim().length() > 0 ? " AND UPPER(LOGINID) LIKE UPPER('%"+loginId+"%')" : "";
		tempSQL += loginName.trim().length() > 0 ? " AND UPPER(LOGINNAME) LIKE UPPER('%"+loginName+"%')" : "";
		tempSQL += loginDesignation.trim().length() > 0 ? " AND UPPER(LOGINDESIGNATION) LIKE UPPER('%"+loginDesignation+"%')" : "";
		tempSQL += roleID.trim().length() > 0 ? " AND UPPER((SELECT ROLENAME FROM MSTROLE WHERE ROLEID = LOGINASROLEID)) LIKE UPPER('%"+roleID+"%') " : "";
		strSQL = strSQL+tempSQL;
		strSQL += " ORDER BY LOGINID";
		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				MstLogin bn =  new MstLogin();
				bn.setLOGINID(rs.getString("LOGINID"));
				bn.setPASSWORD(rs.getString("PASSWORD"));
				bn.setLOGINASROLEID(rs.getString("LOGINASROLEID"));
				bn.setISACTIVELOGIN(rs.getString("ISACTIVELOGIN"));
				bn.setLASTLOGINDATE(rs.getString("LASTLOGINDATE"));
				bn.setSTARTDATE(rs.getString("STARTDATE"));
				bn.setENDDATE(rs.getString("ENDDATE"));
				bn.setLOGINDESIGNATION(rs.getString("LOGINDESIGNATION"));
				bn.setLOGINNAME(rs.getString("LOGINNAME"));
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
	public ArrayList<MstLogin> getLoginData2(String loginId){
		
		ArrayList<MstLogin> arrList = new ArrayList<MstLogin>();
		DBConnection con =  new DBConnection();
		
		String strSQL = " SELECT LOGINID, PASSWORD, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = LOGINASROLEID) LOGINASROLEID, ISACTIVELOGIN, NVL(TO_CHAR(LASTLOGINDATE,'DD/MM/YYYY'),'') LASTLOGINDATE, TO_CHAR(STARTDATE,'DD/MM/YYYY') STARTDATE, NVL(TO_CHAR(ENDDATE,'DD/MM/YYYY'),'N/A') ENDDATE ," +
		" LOGINDESIGNATION, LOGINNAME FROM MSTLOGIN WHERE UPPER(LOGINID) = UPPER('"+loginId+"') ";
		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				MstLogin bn =  new MstLogin();
				bn.setLOGINID(rs.getString("LOGINID"));
				bn.setPASSWORD(rs.getString("PASSWORD"));
				bn.setLOGINASROLEID(rs.getString("LOGINASROLEID"));
				bn.setISACTIVELOGIN(rs.getString("ISACTIVELOGIN"));
				bn.setLASTLOGINDATE(rs.getString("LASTLOGINDATE"));
				bn.setSTARTDATE(rs.getString("STARTDATE"));
				bn.setENDDATE(rs.getString("ENDDATE"));
				bn.setLOGINDESIGNATION(rs.getString("LOGINDESIGNATION"));
				bn.setLOGINNAME(rs.getString("LOGINNAME"));
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
	
	public String getLoginID(String loginId){
		//System.out.println("loginId : " + loginId);
		DBConnection con =  new DBConnection();
		String result = "";
		try{
			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement("SELECT LOGINID FROM MSTLOGIN WHERE UPPER(LOGINID) = UPPER(?)");
			ps.setString(1, loginId);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				//result = rs.getString("LOGINID");
				result = loginId;
			}
			rs.close();
		} catch(SQLException e)		{
			log.fatal(e,e);
		}finally{
			con.closeConnection();
		}
		//System.out.println("result : " + result);
		return result;
	}
	
	public String saveLoginID(MstLogin bn)
	{
		///MstRole bn = new MstRole();
		DBConnection con = new DBConnection();
		String strSQL = "";
		String isDataSaved = "";
		
		try
		{
			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement("SELECT LOGINID FROM MSTLOGIN WHERE LOGINID=?");
			ps.setString(1, bn.getLOGINID());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				strSQL = " UPDATE MSTLOGIN SET" +
						 " PASSWORD = '"+bn.getPASSWORD()+"'," +
						 " LOGINASROLEID = "+bn.getLOGINASROLEID()+"," +
						 " ISACTIVELOGIN = '"+bn.getISACTIVELOGIN()+"'," +
						 " LASTLOGINDATE = TO_DATE('"+bn.getLASTLOGINDATE()+"','DD/MM/YYYY')," +
						 " STARTDATE = TO_DATE('"+bn.getSTARTDATE()+"','DD/MM/YYYY')," +
						 " ENDDATE = TO_DATE('"+bn.getENDDATE()+"','DD/MM/YYYY')," +
						 " LOGINDESIGNATION = '"+bn.getLOGINDESIGNATION()+"'," +
						 " LOGINNAME = '"+bn.getLOGINNAME()+"'," +
						 " ISCONF = '"+bn.getISCONF()+"'," +
						 " ISREPLY = '"+bn.getISREPLY()+"'" +
						 " WHERE LOGINID = '"+bn.getLOGINID()+"'";
				//System.out.println("strSQL -- " + strSQL);
				con.update(strSQL);
				isDataSaved = "GRNSave operation successful for Role ID : " + bn.getLOGINID();
			}
			else
			{
			strSQL = " INSERT INTO MSTLOGIN(LOGINID,PASSWORD,THEMECOLOR,LOGINASROLEID,ISACTIVELOGIN,LASTLOGINDATE,STARTDATE,ENDDATE,LOGINDESIGNATION,LOGINNAME,ISCONF,ISREPLY)" +
					 " VALUES("
			+ "'"+bn.getLOGINID()+"',"                       
			+ "'"+bn.getPASSWORD()+"',"   
			+ "'"+bn.getTHEMECOLOR()+"',"   
			+ ""+bn.getLOGINASROLEID()+","                           
			+ "'"+bn.getISACTIVELOGIN()+"',"                           
			+ "TO_DATE('"+bn.getLASTLOGINDATE()+"','DD/MM/YYYY'),"                       
			+ "TO_DATE('"+bn.getSTARTDATE()+"','DD/MM/YYYY'),"                
			+ "TO_DATE('"+bn.getENDDATE()+"','DD/MM/YYYY'),"
			+ "'"+bn.getLOGINDESIGNATION()+"',"
			+ "'"+bn.getLOGINNAME()+"','"+bn.getISCONF()+"','"+bn.getISREPLY()+"')";
			//System.out.println("strSQL -- " + strSQL);
		    con.update(strSQL);
		    isDataSaved = "GRNSave operation successful for Login ID : " + bn.getLOGINID();
			}
		}
		catch(SQLException e)
		{
			isDataSaved = "REDSave operation failure for Login ID : " + bn.getLOGINID() + ". ERROR--> " + e.getMessage();
			e.printStackTrace();
		}
		finally
		{
			con.commit();
			con.closeConnection();
		}
		return isDataSaved;
	}

/*
	public MstLogin getTenureData(String loginid) {
		
		MstLogin loginBean = new MstLogin();
		DBConnection con =  new DBConnection();
		try {
			String strSQL = " SELECT LOGINID, LOGINNAME FROM MSTLOGIN WHERE LOGINID = '"+loginid+ "'" + " ORDER BY ROLEID ";
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				loginBean = new MstLogin();
				loginBean.setLOGINID(rs.getString("LOGINID"));
				loginBean.setLOGINNAME(rs.getString("LOGINNAME"));
			}
			rs.close();
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}
		return loginBean;
	}
*/	
	public MstLogin getLoginData(String loginId) {
		
		MstLogin loginBean = new MstLogin();
		DBConnection con =  new DBConnection();
		try {

			String strSQL = " SELECT LOGINID, PASSWORD, LOGINASROLEID, ISACTIVELOGIN, NVL(TO_CHAR(LASTLOGINDATE,'DD/MM/YYYY'),'') LASTLOGINDATE, TO_CHAR(STARTDATE,'DD/MM/YYYY') STARTDATE, TO_CHAR(ENDDATE,'DD/MM/YYYY') ENDDATE ," +
							" LOGINDESIGNATION, LOGINNAME, ISCONF,ISREPLY FROM MSTLOGIN WHERE LOGINID = '"+loginId+"' ORDER BY LOGINID ";
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				loginBean = new MstLogin();
				loginBean.setLOGINID(rs.getString("LOGINID"));
				loginBean.setPASSWORD(rs.getString("PASSWORD"));
				loginBean.setLOGINASROLEID(rs.getString("LOGINASROLEID"));
				loginBean.setISACTIVELOGIN(rs.getString("ISACTIVELOGIN"));
				loginBean.setLASTLOGINDATE(rs.getString("LASTLOGINDATE"));
				loginBean.setSTARTDATE(rs.getString("STARTDATE"));
				loginBean.setENDDATE(rs.getString("ENDDATE"));
				loginBean.setLOGINDESIGNATION(rs.getString("LOGINDESIGNATION"));
				loginBean.setLOGINNAME(rs.getString("LOGINNAME"));
				loginBean.setISCONF(rs.getString("ISCONF"));
				loginBean.setISREPLY(rs.getString("ISREPLY"));
			}
			rs.close();
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return loginBean;
	}
}