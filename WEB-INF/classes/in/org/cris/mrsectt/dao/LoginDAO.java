/*
 * Created on Sep 2, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.MstLogin;
import java.sql.PreparedStatement;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import java.sql.ResultSet;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class LoginDAO {
	static Logger log = LogManager.getLogger(LoginDAO.class);

	public MstLogin validateLogin(String loginid, String password,
			String sessionid) {
		MstLogin bn = new MstLogin();
		String strSQL = "";
		DBConnection con = new DBConnection();
		try {

			con.openConnection();

			/*PreparedStatement ps = null;
			ps = con.setPrepStatement(" SELECT LOGINID, PASSWORD, LOGINASROLEID, (SELECT TENUREID FROM MSTTENURE WHERE ROLEID = LOGINASROLEID AND ISACTIVE ='Y') TENUREID, ISACTIVELOGIN, LASTLOGINDATE,  STARTDATE," 
								+ " ENDDATE, LOGINDESIGNATION, LOGINNAME "
								+ " FROM MSTLOGIN "
								+ " WHERE LOGINID=? AND PASSWORD=?");
			ps.setString(1, loginid);
			ps.setString(2, password);*/
			PreparedStatement ps = null;
			ps = con.setPrepStatement(" SELECT A.LOGINID, A.PASSWORDENC, A.LOGINASROLEID, " +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = A.LOGINASROLEID) LOGINASROLENAME," +
					" B.TENUREID TENUREID, " +
					" B.TENUREOFFICENAME TENUREOFFICENAME,B.TENUREOFFICEADDRESS TENUREOFFICEADDRESS, " +
					" A.ISACTIVELOGIN, A.LASTLOGINDATE," +
					" A.STARTDATE, A.ENDDATE, A.LOGINDESIGNATION, A.LOGINNAME, " +
					" TO_CHAR(B.TENURESTARTDATE, 'DD/MM/YYYY') TENURESTARTDATE," +
					"(SELECT M.DEPTCODE FROM MSTROLE M WHERE M.ROLEID=A.LOGINASROLEID) DEPTCODE, A.ISCONF, THEME, THEMECOLOR,ISREPLY " +
					" FROM MSTLOGIN A, MSTTENURE B " +
					" WHERE A.LOGINASROLEID = B.ROLEID " +
					" AND B.ISACTIVE = 'Y' " + 
					" AND A.ISACTIVELOGIN = 'Y' " + 
					" AND UPPER(A.LOGINID) = UPPER(?) AND A.PASSWORDENC=?");
			ps.setString(1, loginid);
			ps.setString(2, password);
			//System.out.println("rfmsuser");
			
			/*String sqlStr = " SELECT A.LOGINID, A.PASSWORD, A.LOGINASROLEID, " +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = A.LOGINASROLEID) LOGINASROLENAME," +
					" B.TENUREID TENUREID, " +
					" B.TENUREOFFICENAME TENUREOFFICENAME,B.TENUREOFFICEADDRESS TENUREOFFICEADDRESS, " +
					" A.ISACTIVELOGIN, A.LASTLOGINDATE," +
					" A.STARTDATE, A.ENDDATE, A.LOGINDESIGNATION, A.LOGINNAME, " +
					" TO_CHAR(B.TENURESTARTDATE, 'DD/MM/YYYY') TENURESTARTDATE," +
					"(SELECT M.DEPTCODE FROM MSTROLE M WHERE M.ROLEID=A.LOGINASROLEID) DEPTCODE, A.ISCONF, THEME, THEMECOLOR,ISREPLY " +
					" FROM MSTLOGIN A, MSTTENURE B " +
					" WHERE A.LOGINASROLEID = B.ROLEID " +
					" AND B.ISACTIVE = 'Y' " + 
					" AND A.ISACTIVELOGIN = 'Y' " + 
					" AND UPPER(A.LOGINID) = UPPER('"+loginid+"') AND A.PASSWORD='"+password+"'";*/
			//log.debug(sqlStr);
			//ResultSet rs = con.select(sqlStr);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bn.setLOGINID(handleNull(rs.getString("LOGINID")));
				bn.setPASSWORDENC(handleNull(rs.getString("PASSWORDENC")));
				bn.setLOGINASROLEID(handleNull(rs.getString("LOGINASROLEID")));
				bn.setLOGINASROLENAME(handleNull(rs.getString("LOGINASROLENAME")));
				bn.setTENUREID(handleNull(rs.getString("TENUREID")));
				bn.setISACTIVELOGIN(handleNull(rs.getString("ISACTIVELOGIN")));
				bn.setLASTLOGINDATE(handleNull(rs.getString("LASTLOGINDATE")));
				bn.setSTARTDATE(handleNull(rs.getString("STARTDATE")));
				bn.setENDDATE(handleNull(rs.getString("ENDDATE")));
				bn.setLOGINDESIGNATION(handleNull(rs.getString("LOGINDESIGNATION")));
				bn.setLOGINNAME(handleNull(rs.getString("LOGINNAME")));
				bn.setTENUREOFFICENAME(handleNull(rs.getString("TENUREOFFICENAME")));
				bn.setTENUREOFFICEADDRESS(handleNull(rs.getString("TENUREOFFICEADDRESS")));
				bn.setTENURESTARTDATE(handleNull(rs.getString("TENURESTARTDATE")));
				bn.setDEPTCODE(handleNull(rs.getString("DEPTCODE")));
				bn.setISCONF(handleNull(rs.getString("ISCONF")));
				bn.setISREPLY(handleNull(rs.getString("ISREPLY")));
				bn.setISVALIDUSER("Y");
				bn.setTHEME(handleNull(rs.getString("THEME")));
				bn.setTHEMECOLOR(handleNull(rs.getString("THEMECOLOR")));
			} else {
				bn.setISVALIDUSER("N");
			}
			rs.close();
			if ("Y".equalsIgnoreCase(bn.getISVALIDUSER())) {
					ps = con.setPrepStatement("UPDATE MSTLOGIN SET LASTLOGINDATE = SYSDATE WHERE UPPER(LOGINID)=UPPER(?)");
					ps.setString(1, loginid);
					ps.executeUpdate();
					/*strSQL = " UPDATE MSTLOGIN SET LASTLOGINDATE = SYSDATE WHERE UPPER(LOGINID)=UPPER('" + loginid + "')";
					log.debug(strSQL);
					con.update(strSQL);*/
			}
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			// log.fatal(e,e);
		} finally {
			con.closeConnection();
		}
		return bn;
	}
	
	public String checkUserType(String loginid) {
		String userType="";
		String strSQL = "";
		DBConnection con = new DBConnection();
		try {

			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement(" SELECT THEMECOLOR  FROM  "
					+ "MSTLOGIN A WHERE  UPPER(A.LOGINID) = UPPER(?)");
			ps.setString(1, loginid);
			ResultSet rs = ps.executeQuery();
			/*String sqlStr = " SELECT THEMECOLOR  FROM  "
					+ "MSTLOGIN A WHERE  UPPER(A.LOGINID) = UPPER('"+loginid+"')";
			log.debug(sqlStr);
			ResultSet rs = con.select(sqlStr);*/
			
			if (rs.next()) {
				userType= handleNull(rs.getString("THEMECOLOR"));
			}else {
				userType= "";
			}
			rs.close();
			
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			// log.fatal(e,e);
		} finally {
			con.closeConnection();
		}
		return userType;
	}
	
	
	
	public MstLogin validateLoginOther(String loginid, String password,
			String sessionid) {
		MstLogin bn = new MstLogin();
		String strSQL = "";
		DBConnection con = new DBConnection();
		try {

			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement(" SELECT A.LOGINID, A.PASSWORDENC, A.LOGINASROLEID,  (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = A.LOGINASROLEID) LOGINASROLENAME,  A.ISACTIVELOGIN, A.LASTLOGINDATE, A.STARTDATE, A.ENDDATE, A.LOGINDESIGNATION, A.LOGINNAME,(SELECT M.DEPTCODE FROM MSTROLE M WHERE M.ROLEID=A.LOGINASROLEID) DEPTCODE, A.ISCONF, THEME, THEMECOLOR,ISREPLY  FROM  "
					+ "MSTLOGIN A WHERE A.ISACTIVELOGIN = 'Y'  AND A.ISACTIVELOGIN = 'Y'  AND UPPER(A.LOGINID) = UPPER('?') AND A.PASSWORD=?");
			ps.setString(1, loginid);
			ps.setString(2, password);
			/*String sqlStr = " SELECT A.LOGINID, A.PASSWORD, A.LOGINASROLEID,  (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = A.LOGINASROLEID) LOGINASROLENAME,  A.ISACTIVELOGIN, A.LASTLOGINDATE, A.STARTDATE, A.ENDDATE, A.LOGINDESIGNATION, A.LOGINNAME,(SELECT M.DEPTCODE FROM MSTROLE M WHERE M.ROLEID=A.LOGINASROLEID) DEPTCODE, A.ISCONF, THEME, THEMECOLOR,ISREPLY  FROM  "
					+ "MSTLOGIN A WHERE A.ISACTIVELOGIN = 'Y'  AND A.ISACTIVELOGIN = 'Y'  AND UPPER(A.LOGINID) = UPPER('"+loginid+"') AND A.PASSWORD='"+password+"'";
			log.debug(sqlStr);
			ResultSet rs = con.select(sqlStr);*/
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bn.setLOGINID(handleNull(rs.getString("LOGINID")));
				bn.setPASSWORDENC(handleNull(rs.getString("PASSWORDENC")));
				bn.setLOGINASROLEID(handleNull(rs.getString("LOGINASROLEID")));
				bn.setLOGINASROLENAME(handleNull(rs.getString("LOGINASROLENAME")));	
				bn.setISACTIVELOGIN(handleNull(rs.getString("ISACTIVELOGIN")));
				bn.setLASTLOGINDATE(handleNull(rs.getString("LASTLOGINDATE")));
				bn.setSTARTDATE(handleNull(rs.getString("STARTDATE")));
				bn.setENDDATE(handleNull(rs.getString("ENDDATE")));
				bn.setLOGINDESIGNATION(handleNull(rs.getString("LOGINDESIGNATION")));
				bn.setLOGINNAME(handleNull(rs.getString("LOGINNAME")));
				bn.setDEPTCODE(handleNull(rs.getString("DEPTCODE")));
				bn.setISCONF(handleNull(rs.getString("ISCONF")));
				bn.setISREPLY(handleNull(rs.getString("ISREPLY")));
				bn.setISVALIDUSER("Y");
				bn.setTHEME(handleNull(rs.getString("THEME")));
				bn.setTHEMECOLOR(handleNull(rs.getString("THEMECOLOR")));
			} else {
				bn.setISVALIDUSER("N");
			}
			rs.close();
			if ("Y".equalsIgnoreCase(bn.getISVALIDUSER())) {
					ps = con.setPrepStatement("UPDATE MSTLOGIN SET LASTLOGINDATE = SYSDATE WHERE UPPER(LOGINID)=UPPER(?)");
					ps.setString(1, loginid);
					ps.executeUpdate();
					/*strSQL = " UPDATE MSTLOGIN SET LASTLOGINDATE = SYSDATE WHERE UPPER(LOGINID)=UPPER('" + loginid + "')";
					log.debug(strSQL);
					con.update(strSQL);*/
			}
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			// log.fatal(e,e);
		} finally {
			con.closeConnection();
		}
		return bn;
	}
	

	public String getFilePath() {
		String result = "";
		String strSQL = "";
		DBConnection con = new DBConnection();
		try {

			con.openConnection();
			strSQL = " SELECT FILEPATH FROM CONFIG_MAST ";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			
			//System.out.println(strSQL);
			//
			
			//ResultSet rs = con.select(strSQL);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				result = rs.getString(1);
			}
			rs.close();

		} catch (Exception e) {
			con.rollback();
			// log.fatal(e,e);
		} finally {
			con.closeConnection();
		}
		return result;
	}
	public String handleNull(String str) {
		return str != null ? CommonDAO.numberFormat(str) : "";
	}
}