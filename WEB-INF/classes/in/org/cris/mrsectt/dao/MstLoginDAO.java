package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstLoginDAO {
	static Logger log = LogManager.getLogger(MstRoleDAO.class);
	
	public ArrayList<MstLogin> getSearchData(String loginId, String loginName, String loginDesignation, String roleID){
		
		ArrayList<MstLogin> arrList = new ArrayList<MstLogin>();
		DBConnection con =  new DBConnection();
		
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		
		String strSQL = " SELECT LOGINID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = LOGINASROLEID) LOGINASROLEID, ISACTIVELOGIN, NVL(TO_CHAR(LASTLOGINDATE,'DD/MM/YYYY'),'') LASTLOGINDATE, TO_CHAR(STARTDATE,'DD/MM/YYYY') STARTDATE, NVL(TO_CHAR(ENDDATE,'DD/MM/YYYY'),'N/A') ENDDATE ," +
							" LOGINDESIGNATION, LOGINNAME FROM MSTLOGIN WHERE 1=1 ";
		String tempSQL = "";
		/*tempSQL += loginId.trim().length() > 0 ? " AND UPPER(LOGINID) LIKE UPPER('%"+loginId+"%')" : "";
		tempSQL += loginName.trim().length() > 0 ? " AND UPPER(LOGINNAME) LIKE UPPER('%"+loginName+"%')" : "";
		tempSQL += loginDesignation.trim().length() > 0 ? " AND UPPER(LOGINDESIGNATION) LIKE UPPER('%"+loginDesignation+"%')" : "";
		tempSQL += roleID.trim().length() > 0 ? " AND UPPER((SELECT ROLENAME FROM MSTROLE WHERE ROLEID = LOGINASROLEID)) LIKE UPPER('%"+roleID+"%') " : "";
		*/
		
		if(loginId.trim().length() > 0 )
		{
			tempSQL +=" AND UPPER(LOGINID) LIKE UPPER(?)" ;
			params.add("%"+loginId+"%");
		}
		
		if(loginName.trim().length() > 0)
		{
			tempSQL +=" AND UPPER(LOGINNAME) LIKE UPPER(?)" ;
			params.add("%"+loginName+"%");
		}
		
		if(loginDesignation.trim().length() > 0 )
		{
			tempSQL +=" AND UPPER(LOGINDESIGNATION) LIKE UPPER(?)" ;
			params.add("%"+loginDesignation+"%");
		}
		
		if(roleID.trim().length() > 0  )
		{
			tempSQL +=" AND UPPER((SELECT ROLENAME FROM MSTROLE WHERE ROLEID = LOGINASROLEID)) LIKE UPPER(?) " ;
			params.add("%"+roleID+"%");
		}
		
		strSQL = strSQL+tempSQL;
		strSQL += " ORDER BY LOGINID";
		
		
		try {
			log.debug(strSQL);
			con.openConnection();
			
			PreparedStatement ps = con.setPrepStatement(strSQL);
			for(int k=1;k<params.size();k++){
	    		ps.setString(k, params.get(k));
	    		//System.out.println("k :"+k+" params "+params.get(k));
	    		
	    	}
			
			ResultSet rs=ps.executeQuery();
			//ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				MstLogin bn =  new MstLogin();
				bn.setLOGINID(rs.getString("LOGINID"));
				//bn.setPASSWORD(rs.getString("PASSWORD"));
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
			//System.out.println("getSearchData executed");
			
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
		
		/*String strSQL = " SELECT LOGINID, PASSWORD, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = LOGINASROLEID) LOGINASROLEID, ISACTIVELOGIN, NVL(TO_CHAR(LASTLOGINDATE,'DD/MM/YYYY'),'') LASTLOGINDATE, TO_CHAR(STARTDATE,'DD/MM/YYYY') STARTDATE, NVL(TO_CHAR(ENDDATE,'DD/MM/YYYY'),'N/A') ENDDATE ," +
		" LOGINDESIGNATION, LOGINNAME FROM MSTLOGIN WHERE UPPER(LOGINID) = UPPER('"+loginId+"') ";*/
		
		String strSQL = " SELECT LOGINID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = LOGINASROLEID) LOGINASROLEID, ISACTIVELOGIN, NVL(TO_CHAR(LASTLOGINDATE,'DD/MM/YYYY'),'') LASTLOGINDATE, TO_CHAR(STARTDATE,'DD/MM/YYYY') STARTDATE, NVL(TO_CHAR(ENDDATE,'DD/MM/YYYY'),'N/A') ENDDATE ," +
				" LOGINDESIGNATION, LOGINNAME FROM MSTLOGIN WHERE UPPER(LOGINID) = UPPER(?) ";
				
		try {
			log.debug(strSQL);
			con.openConnection();
			
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1,loginId );
			
			//ResultSet rs =  con.select(strSQL);
			ResultSet rs=ps.executeQuery();
			
			while (rs.next()) {
				MstLogin bn =  new MstLogin();
				bn.setLOGINID(rs.getString("LOGINID"));
				//bn.setPASSWORDENC(rs.getString("PASSWORD"));
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
			//System.out.println("getLoginData2 executed");
			
			
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
			//System.out.println("getLoginID executed");
		
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
		String settingPasswordenc="";
		
		try
		{
			
			bn.setPASSWORDENC(passenc(bn.getPASSWORD()));
			//System.out.println("sdhfhkhfgkdhg"+bn.getPASSWORDENC());
			
			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement("SELECT LOGINID, PASSWORDENC FROM MSTLOGIN WHERE LOGINID=?");
			ps.setString(1, bn.getLOGINID());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
			    if (bn.getPASSWORD().isEmpty()){
			    	settingPasswordenc=rs.getString("PASSWORDENC");
			    	}
			    else {
			    	settingPasswordenc = bn.getPASSWORDENC();
			    }
				strSQL = " UPDATE MSTLOGIN SET" +
						 " PASSWORDENC = ?," +
						 " LOGINASROLEID = ?," +
						 " ISACTIVELOGIN = ?," +
						 " LASTLOGINDATE = TO_DATE(?,'DD/MM/YYYY')," +
						 " STARTDATE = TO_DATE(?,'DD/MM/YYYY')," +
						 " ENDDATE = TO_DATE(?,'DD/MM/YYYY')," +
						 " LOGINDESIGNATION = ?," +
						 " LOGINNAME = ?," +
						 " ISCONF = ?," +
						 " ISREPLY = ? " +
						 " WHERE LOGINID = ? ";
				
				PreparedStatement ps2 = null;
				ps2=con.setPrepStatement(strSQL);
				//ps2.setString(1, bn.getPASSWORD());
				ps2.setString(1, settingPasswordenc);
				ps2.setString(2, bn.getLOGINASROLEID());
				ps2.setString(3, bn.getISACTIVELOGIN());
				ps2.setString(4, bn.getLASTLOGINDATE());
				ps2.setString(5, bn.getSTARTDATE());
				ps2.setString(6, bn.getENDDATE());
				ps2.setString(7, bn.getLOGINDESIGNATION());
				ps2.setString(8, bn.getLOGINNAME());
				ps2.setString(9, bn.getISCONF());
				ps2.setString(10, bn.getISREPLY());
				ps2.setString(11, bn.getLOGINID());
				
				int row=ps2.executeUpdate();
				//System.out.println("saveLoginID::TOTAL ROWS UPDATED:"+ row);
				isDataSaved = "GRNSave operation successful for Role ID : " + bn.getLOGINID();
			}
			else
			{
			/*strSQL = " INSERT INTO MSTLOGIN(LOGINID,PASSWORD,THEMECOLOR,LOGINASROLEID,ISACTIVELOGIN,LASTLOGINDATE,STARTDATE,ENDDATE,LOGINDESIGNATION,LOGINNAME,ISCONF,ISREPLY)" +
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
		    con.update(strSQL);*/
				
				strSQL = " INSERT INTO MSTLOGIN(LOGINID,THEMECOLOR,LOGINASROLEID,ISACTIVELOGIN,LASTLOGINDATE,STARTDATE,ENDDATE,LOGINDESIGNATION,LOGINNAME,ISCONF,ISREPLY,PASSWORDENC)" +
						 " VALUES(?,?,?,?,"
						 + "TO_DATE(?,'DD/MM/YYYY'),"
						 + "TO_DATE(?,'DD/MM/YYYY'),"
						 + "TO_DATE(?,'DD/MM/YYYY'),"
						 + "?,?,?,?,?)";
				
			   PreparedStatement ps2 = null;
			   ps2=con.setPrepStatement(strSQL);
			   ps2.setString(1, bn.getLOGINID());
			  // ps2.setString(2, bn.getPASSWORD());
			   ps2.setString(2, bn.getTHEMECOLOR());
			   ps2.setString(3, bn.getLOGINASROLEID());
			   ps2.setString(4, bn.getISACTIVELOGIN());
			   ps2.setString(5, bn.getLASTLOGINDATE());//TO_DATE(bn.getLASTLOGINDATE(),'DD/MM/YYYY')
			   ps2.setString(6, bn.getSTARTDATE());
			   ps2.setString(7, bn.getENDDATE());
			   ps2.setString(8, bn.getLOGINDESIGNATION());
			   ps2.setString(9, bn.getLOGINNAME());
			   ps2.setString(10, bn.getISCONF());
			   ps2.setString(11, bn.getISREPLY());
			   ps2.setString(12, bn.getPASSWORDENC());
		    
			   int row=ps2.executeUpdate();
			  // System.out.println("saveLoginID::TOTAL ROWS INSERTED:"+ row);
			   
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

			/*String strSQL = " SELECT LOGINID, PASSWORD, LOGINASROLEID, ISACTIVELOGIN, NVL(TO_CHAR(LASTLOGINDATE,'DD/MM/YYYY'),'') LASTLOGINDATE, TO_CHAR(STARTDATE,'DD/MM/YYYY') STARTDATE, TO_CHAR(ENDDATE,'DD/MM/YYYY') ENDDATE ," +
							" LOGINDESIGNATION, LOGINNAME, ISCONF,ISREPLY FROM MSTLOGIN WHERE LOGINID = '"+loginId+"' ORDER BY LOGINID ";
			log.debug(strSQL);*/
			
			con.openConnection();
			
			String strSQL = " SELECT LOGINID, LOGINASROLEID, ISACTIVELOGIN, NVL(TO_CHAR(LASTLOGINDATE,'DD/MM/YYYY'),'') LASTLOGINDATE, TO_CHAR(STARTDATE,'DD/MM/YYYY') STARTDATE, TO_CHAR(ENDDATE,'DD/MM/YYYY') ENDDATE ," +
					" LOGINDESIGNATION, LOGINNAME, ISCONF,ISREPLY FROM MSTLOGIN WHERE LOGINID = ? ORDER BY LOGINID ";
			 
			PreparedStatement ps2 = null;
			ps2=con.setPrepStatement(strSQL);
			ps2.setString(1, loginId);
			
			ResultSet rs=ps2.executeQuery();
			//ResultSet rs = con.select(strSQL);
			
			while (rs.next()) {
				loginBean = new MstLogin();
				loginBean.setLOGINID(rs.getString("LOGINID"));
				//loginBean.setPASSWORD(rs.getString("PASSWORD"));
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
			//System.out.println("getLoginData executed");
			
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return loginBean;
	}
	public String passenc(String password)
	{
		String encpass = null; 
		try 
		{ 
			MessageDigest m1 = MessageDigest.getInstance("MD5"); 
			/* Use the MD5 update() function to add plain-text password bytes to the digest. */ 
			m1.update(password.getBytes()); 
			/*The hash value should be converted to bytes.*/ 
			byte[] bt = m1.digest(); 
			/*Decimal bytes are contained in the bytes array. Changing the format to hexadecimal. */
			StringBuilder str = new StringBuilder(); 
			for(int i=0; i< bt.length ;i++) 
			{ 
				str.append(Integer.toString((bt[i] & 0xff) + 0x100, 16).substring(1)); 
			} 
			encpass = str.toString(); 
		} 
		catch (NoSuchAlgorithmException e) 
		{ 
			e.printStackTrace(); 
		}
		return encpass;
	}
}