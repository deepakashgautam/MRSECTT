package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstRole;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstMessageDAO_old {
	static Logger log = LogManager.getLogger(MstMessageDAO_old.class);
	
	public ArrayList<CommonBean> getMRData(){
		
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection con =  new DBConnection();
		
		String strSQL = " SELECT MESSAGETEXT,MESSAGETYPE,LOGINID,ISACTIVE ";
		strSQL += " FROM MSTMESSAGE  WHERE MESSAGETYPE='MR' AND ISACTIVE='Y'";
		
		try {
		
		//	log.debug(strSQL);
			con.openConnection();
			ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				CommonBean bn =  new CommonBean();
				bn.setField1(rs.getString("MESSAGETEXT"));
				bn.setField2(rs.getString("LOGINID"));
				
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

	public ArrayList<CommonBean> getNoteData(String roleID){

		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection con =  new DBConnection();
//		String strSQL = " SELECT MESSAGETEXT,MESSAGETYPE,LOGINID,ISACTIVE FROM MSTMESSAGE  WHERE MESSAGETYPE='NT' AND ISACTIVE='Y'";

		String strSQL = " SELECT NAME, TO_CHAR(TENURESTARTDATE,'DD-MON-YYYY') STARTDATE, NVL(TO_CHAR(TENUREENDDATE,'DD-MON-YYYY'),'Till Date') ENDDATE" +
						" FROM MSTTENURE A WHERE ROLEID = '"+roleID+"' ORDER BY TENUREID DESC ";
		try {
		//	log.debug(strSQL);
			con.openConnection();
			ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				CommonBean bn =  new CommonBean();
//				bn.setField1(rs.getString("MESSAGETEXT"));
//				bn.setField2(rs.getString("LOGINID"));
				bn.setField1(rs.getString("NAME"));
				bn.setField2(rs.getString("STARTDATE"));
				bn.setField3(StringFormat.nullString(rs.getString("ENDDATE")));
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

	public ArrayList<CommonBean> getRefClassData(String roleID){
		
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection con =  new DBConnection();
		String strSQL = " SELECT TENUREID, LISTAGG(REFCLASS,', ') WITHIN GROUP (ORDER BY REFCLASS) REFCLASS FROM MSTCLASS WHERE ROLEID = '"+roleID+"' GROUP BY TENUREID ORDER BY TENUREID DESC";
		try {
			con.openConnection();
			ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				CommonBean bn =  new CommonBean();
				bn.setField1(rs.getString("TENUREID"));
				bn.setField2(rs.getString("REFCLASS"));
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
	
public ArrayList<CommonBean> getMARQUEEData(){
	
	ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
	DBConnection con =  new DBConnection();
	
	String strSQL = " SELECT MESSAGETEXT,MESSAGETYPE,LOGINID,ISACTIVE ";
	strSQL += " FROM MSTMESSAGE  WHERE MESSAGETYPE='MQ' AND ISACTIVE='Y'";
	
	try {
	
		//log.debug(strSQL);
		con.openConnection();
		ResultSet rs =  con.select(strSQL);
		while (rs.next()) {
			CommonBean bn =  new CommonBean();
			bn.setField1(rs.getString("MESSAGETEXT"));
			bn.setField2(rs.getString("LOGINID"));
			
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

public ArrayList<CommonBean> getIQData(String loginid){
	
	ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
	DBConnection con =  new DBConnection();
	
	String strSQL = " SELECT MESSAGETEXT,MESSAGETYPE,LOGINID,ISACTIVE ";
	strSQL += " FROM MSTMESSAGE  WHERE MESSAGETYPE='IQ' AND ISACTIVE='Y'";
	
	try {
	
		//log.debug(strSQL);
		con.openConnection();
		ResultSet rs =  con.select(strSQL);
		while (rs.next()) {
			CommonBean bn =  new CommonBean();
			bn.setField1(rs.getString("MESSAGETEXT"));
			bn.setField2(rs.getString("LOGINID"));
			
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

public ArrayList<CommonBean> getMenuLevel(String loginid){
	ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
	DBConnection con =  new DBConnection();
	String strSQL = "SELECT MENUID FROM USERMENUTABLE WHERE GROUPID = '"+loginid+"'";
	try {
		con.openConnection();
		ResultSet rs =  con.select(strSQL);
		while (rs.next()) {
			CommonBean bn =  new CommonBean();
			bn.setField1(rs.getString("MENUID"));
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
	
	public String saveMessage(MstRole bn)
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
				isDataSaved = "F";
			}
			else
			{
				//System.out.println("role id -- " + bn.getROLEID());
				//System.out.println("role name -- " + bn.getROLENAME());
				strSQL = "INSERT INTO MSTROLE(ROLEID, ROLENAME) VALUES("+bn.getROLEID()+",'"+bn.getROLENAME()+"')";
			    con.update(strSQL);
				isDataSaved = "T";
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.commit();
			con.closeConnection();
		}
		return isDataSaved;
	}


	public MstRole getRoleData(String roleid) {
		MstRole roleBean = new MstRole();

		DBConnection con =  new DBConnection();
		try {

			String strSQL = " SELECT ROLEID, ROLENAME FROM MSTROLE WHERE ROLEID = '"
					+ roleid + "'" + " ORDER BY ROLEID ";

			//log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				roleBean = new MstRole();
				roleBean.setROLEID(rs.getString("ROLEID"));
				roleBean.setROLENAME(rs.getString("ROLENAME"));

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