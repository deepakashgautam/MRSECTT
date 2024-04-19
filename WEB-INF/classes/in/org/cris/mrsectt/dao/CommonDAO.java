package in.org.cris.mrsectt.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.GenBean;
import in.org.cris.mrsectt.Beans.MenuBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.Encrypt;


public class CommonDAO {
	
	static Logger log = LogManager.getLogger(CommonDAO.class);
	
	public ArrayList<GenBean> getCodeDescPrepStmnt(PreparedStatement ps, int cols) {
		ArrayList<GenBean> arr = new ArrayList<GenBean>();
		GenBean bn;
		String[] field;
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			ResultSet rs =ps.executeQuery(); //con.select(strSQL);
			while (rs.next()) {
				field = new String[cols];
				bn = new GenBean();
				for (int cnt = 0; cnt < cols; cnt++) {
					field[cnt] = handleNull(rs.getString(cnt + 1));
				}
				bn.setField(field);
				arr.add(bn);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			con.closeConnection();
		}
		return arr;
	}
	
	
	
	public String getallowstatus() {
		DBConnection con = new DBConnection();
		String strSQL = "";
		String result = "Y";
		try {
			con.openConnection();
			strSQL = "select case when sysdate<to_date('22/11/2011','dd/mm/yyyy') then 'Y' else 'N' end from dual";
//			System.out.println(strSQL);
			ResultSet rsdelay = con.select(strSQL);
			if (rsdelay.next()) {
				result = rsdelay.getString(1) != null ? rsdelay.getString(1)
						: "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return result;
	}

	public static String getDesignation(String loginid) {
		DBConnection con = new DBConnection();
		String strSQL = "";
		try {
			con.openConnection();
			strSQL = " SELECT DESIGNATION FROM MSTLOGIN WHERE lower(LOGINID)='"
					+ loginid.toLowerCase() + "'  ";
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				return handleNull(rs.getString("DESIGNATION")).toUpperCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return "";
	}

	public static String getRoleName(String roleId) {
		DBConnection con = new DBConnection();
		String strSQL = "";
		try {
			con.openConnection();
			strSQL = "SELECT ROLENAME FROM MSTROLE WHERE ROLEID='"+roleId+"'";
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				return handleNull(rs.getString("ROLENAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return "";
	}

	public static String getFileStatus1(String fStatus1) {
		DBConnection con = new DBConnection();
		String strSQL = "";
		try {
			con.openConnection();
			strSQL = "SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'0' AND CODE='"+fStatus1+"'";
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				return handleNull(rs.getString("FNAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return "";
	}

	public static String getFileStatus2(String fStatus2) {
		DBConnection con = new DBConnection();
		String strSQL = "";
		try {
			con.openConnection();
			strSQL = "SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'0' AND CODE='"+fStatus2+"'";
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				return handleNull(rs.getString("FNAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return "";
	}

	public static String getStateName(String stateCode) {
		DBConnection con = new DBConnection();
		String strSQL = "";
		PreparedStatement ps = null;
		try {
			con.openConnection();
			strSQL = "SELECT STATENAME FROM MSTSTATE WHERE STATECODE LIKE ?";
			
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, stateCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return handleNull(rs.getString("STATENAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return "";
	}

	public static String getIsConf(String refClass) {
		DBConnection con = new DBConnection();
		String strSQL = "";
		try {
			con.openConnection();
			strSQL = "SELECT ISCONF FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE = '"+refClass+"'";
			log.debug(strSQL);
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				return handleNull(rs.getString("ISCONF"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return "";
	}

	public static String handleNull(String str) {
		return str != null ? numberFormat(str) : "";
	}

	public static String numberFormat(String var) {
		try {
			// System.out.println(" decimal = "+var.indexOf("."));
			if (var.indexOf(".") != -1) {
				double ret = (new Double(var)).doubleValue();
				String x = "" + ret;
				int j = x.indexOf(".");
				String y = x.substring(j + 1);
				if (y.equals("0"))
					y = "";
				String z = x.substring(0, j);
				String returnvalue;
				if (y.length() == 0) {
					returnvalue = "" + z;
				} else {
					returnvalue = z + "." + y;
				}
				return returnvalue;
			}
			return var;
		} catch (NumberFormatException nfe) {
			return var;
		}
	}

	

	

	static String shuffle(String text) {
		if (text.length() <= 1)
			return text;

		int split = text.length() / 2;

		String temp1 = shuffle(text.substring(0, split));
		String temp2 = shuffle(text.substring(split));

		if (Math.random() > 0.5)
			return temp1 + temp2;
		else
			return temp2 + temp1;
	}

	public String changePassword(String loginId, String roleId, String oldPass, String newPass) {
		String result = "0";
		DBConnection con = new DBConnection();
		try {
			String encpass = passenc(oldPass);
			String encpass1 = passenc(newPass);
			
			
			String strSQL = " UPDATE MSTLOGIN SET  PASSWORDENC='"+encpass1+"' WHERE PASSWORDENC='"+encpass+"' AND UPPER(LOGINID) = UPPER('"+loginId+"') AND LOGINASROLEID = '"+roleId+"'";
			con.openConnection();
			con.update(strSQL);
			con.commit();

			strSQL = " SELECT UPPER(LOGINID) RES FROM MSTLOGIN WHERE PASSWORDENC='"+encpass1+"' AND UPPER(LOGINID) = UPPER('"+loginId+"') AND LOGINASROLEID = '"+roleId+"'";
			ResultSet rs = con.select(strSQL);
			if(rs.next()){
				result = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return result;
	}


	public String editprofile(String loginid, String dob, String mobile,
			String email) {
		String result = "0";
		DBConnection con = new DBConnection();
		try {
			String strSQL = " update mstlogin set changedate=sysdate,dob=to_date('"
					+ dob
					+ "','dd/mm/yyyy'),mobileno='"
					+ mobile
					+ "',email='"
					+ email + "' where upper(loginid)=upper('" + loginid + "')";
			con.openConnection();
			con.update(strSQL);
			result = "1";
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			con.closeConnection();
		}
		return result;
	}
	

	public String getChangePwdFlag(String loginid) {
		DBConnection con = new DBConnection();
		String strSQL = "";
		try {
			con.openConnection();
			strSQL = " select nvl(max(1),0) changepwdflag from mstlogin "
					+ " where loginid='" + loginid
					+ "' and nvl(changedate,sysdate-20)+15<sysdate ";
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				return rs.getString("changepwdflag").toLowerCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return "0";
	}

	public ArrayList<CommonBean> getCodeDesc(String strSQL) {
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		CommonBean bn;
		DBConnection con = new DBConnection();
		try {

			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				bn = new CommonBean();
				bn.setField1(handleNull(rs.getString(1)));
				bn.setField2(handleNull(rs.getString(2)));
				arr.add(bn);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}

	public ArrayList<CommonBean> getCodeDesclogin(String strSQL) {
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		CommonBean bn;
		DBConnection con = new DBConnection();
		try {

			con.openConnection();
			ResultSet rs = con.select(strSQL);
			//System.out.println(strSQL);
			while (rs.next()) {
				bn = new CommonBean();
				bn.setField1(handleNull(rs.getString(1)));
				bn.setField2(handleNull(rs.getString(2)));
				bn.setField3(handleNull(rs.getString(3)));
				arr.add(bn);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}

	public ArrayList<GenBean> getCodeDesc(String strSQL, int cols) {
		ArrayList<GenBean> arr = new ArrayList<GenBean>();
		GenBean bn;
		String[] field;
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				field = new String[cols];
				bn = new GenBean();
				for (int cnt = 0; cnt < cols; cnt++) {
					field[cnt] = handleNull(rs.getString(cnt + 1));
				}
				bn.setField(field);
				arr.add(bn);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			con.closeConnection();
		}
		return arr;
	}


	public ArrayList<CommonBean> getMSTGEC(String codetype) {// TRTDDL-->Track
		// Renewal Type
		// Drop Down
		// List
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		String strSQL = "";
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			strSQL = " SELECT CODE, SNAME, FNAME  FROM MSTGEC WHERE CODETYPE='"
					+ codetype
					+ "' AND NVL(STATUSFLAG,1)=1 ORDER BY NVL(PRIORITYNO,99),CODE ";
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				CommonBean bn = new CommonBean();
				bn.setField1(rs.getString(1) != null ? rs.getString(1) : "");
				bn.setField2(rs.getString(2) != null ? rs.getString(2) : "");
				bn.setField3(rs.getString(3) != null ? rs.getString(3) : "");
				arr.add(bn);
			}
			rs.close();

		} catch (Exception e) {
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return arr;
	}

	public ArrayList<CommonBean> getLoginID(String loginid) {
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		String strSQL = "";
		DBConnection con = new DBConnection();
		try {

			con.openConnection();
			strSQL = " SELECT LOGINID, DESIGNATION, RLYCODE, DIVCODE "
					+ " FROM MSTLOGIN WHERE ((RLYCODE,NVL(DIVCODE,'-')) IN "
					+ " (SELECT RLYCODE,NVL(DIVCODE,'-') FROM MSTLOGIN WHERE LOGINID='"
					+ loginid + "') OR (RLYCODE,NVL(DIVCODE,'-')) IN "
					+ " (SELECT RLYCODE, NVL(DIVCODE,'-')  FROM MSTDIV "
					+ " WHERE "
					+ " ''||(SELECT LINKEDDIV FROM MSTDIV WHERE DIVCODE IN "
					+ " (SELECT DIVCODE FROM MSTLOGIN WHERE LOGINID='"
					+ loginid + "'))||'' LIKE "
					+ " '%'||DIVCODE||'%')) AND RLYCODE IN "
					+ " (SELECT RLYCODE FROM MSTLOGIN WHERE LOGINID='"
					+ loginid + "') ORDER BY RLYCODE,DIVCODE ";
			ResultSet rs = con.select(strSQL);

			while (rs.next()) {
				CommonBean bn = new CommonBean();
				bn.setField1(rs.getString(1) != null ? rs.getString(1) : "");
				bn.setField2(rs.getString(2) != null ? rs.getString(2) : "");
				arr.add(bn);
			}
			rs.close();

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}

	public String getCurrLoginID(String workid) {
		DBConnection con = new DBConnection();
		String strSQL = "";
		try {
			con.openConnection();
			strSQL = " SELECT NVL(CURRLOGINID,CREATELOGINID) FROM WORKS WHERE WORKID='"
					+ workid + "' ";
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return "";
	}


	public String getPassword(int len) {

		String validCharacters = "ab2cd3ef4gh5jk6mn7pq8rs9tuvwxyz";
		try {
			return shuffle(validCharacters).substring(0, len).toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return "";

	}

		public ArrayList<CommonBean> getRlyEditUser() {
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		String strSQL = "";
		DBConnection con = new DBConnection();
		try {
			con.openConnection();

			strSQL = " SELECT RLYCODE, FNAME, "
					+ " RLYCODE||' - '||HINFNAME FROM MSTRLY "
					+ " WHERE RLYCODE NOT IN (22,99) AND (STATUSFLAG='1' or rlycode='51')  "
					+ " ORDER BY PRIORITYNO ";

			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				CommonBean bn = new CommonBean();
				bn.setField1(rs.getString(1) != null ? rs.getString(1) : "");
				bn.setField2(rs.getString(2) != null ? rs.getString(2) : "");
				bn.setField3(rs.getString(3) != null ? rs.getString(3) : "");
				arr.add(bn);
			}
			rs.close();

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}

	public ArrayList<CommonBean> getState() {
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		String strSQL = "";
		DBConnection con = new DBConnection();
		try {

			con.openConnection();
			strSQL = "SELECT STATECODE,FNAME FROM MSTSTATE ORDER BY STATECODE";
			
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				CommonBean bn = new CommonBean();
				bn.setField1(rs.getString(1) != null ? rs.getString(1) : "");
				bn.setField2(rs.getString(2) != null ? rs.getString(2) : "");
				arr.add(bn);
			}
			rs.close();

		} catch (Exception e) {
			con.rollback();
			// log.fatal(e,e);
		} finally {
			con.closeConnection();
		}
		return arr;
	}

	public ArrayList<CommonBean> getStation(String station){	
		String strSQL = " SELECT DISTINCT STNCODE,NAME FROM MSTSTATION WHERE UPPER(NAME) LIKE UPPER('"+station+"%') OR UPPER(STNCODE)=UPPER('"+station+"') ORDER BY NAME";
		return getSQLResult(strSQL, 2);
	}
	
	public ArrayList<CommonBean> getAllStation(){	
		String strSQL = " SELECT DISTINCT STNCODE,NAME FROM MSTSTATION ORDER BY STNCODE,NAME";
		return getSQLResult(strSQL, 2);
	}
	
	public ArrayList<CommonBean> getTrain(String train){	
		String strSQL = " SELECT DISTINCT TRAIN_NUMBER, TRAIN_NAME FROM TRAINMASTER WHERE UPPER(TRAIN_NAME) LIKE UPPER('%"+train+"%') OR UPPER(TRAIN_NUMBER)=UPPER('"+train+"') ORDER BY TRAIN_NAME";
		return getSQLResult(strSQL, 2);
	}
	
	public ArrayList<CommonBean> getAllTrain(){	
		String strSQL = " SELECT DISTINCT TRAIN_NUMBER, TRAIN_NAME FROM TRAINMASTER  ORDER BY TRAIN_NUMBER,TRAIN_NAME";
		return getSQLResult(strSQL, 2);
	}
	
	public ArrayList<CommonBean> getSubjectKeywords(String cat){	
		String strSQL = " SELECT CAT, KEYWORDS FROM MSTSUBJECTKEYWORDS WHERE CAT='"+cat+"'  ORDER BY CAT";
		return getSQLResult(strSQL, 2);
	}
	
	/*public String getStringParam(String strSQL) {

		String param = "";
		DBConnection con = new DBConnection();
		try {
			con.openConnection();

			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			con.closeConnection();
		}
		return param;
	}*/
	
	//soumen function
	public String getStringParam(String strSQL) {

		String param = "";
		DBConnection con = new DBConnection();
		try {
			con.openConnection();

			//ResultSet rs = con.select(strSQL);
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ResultSet rs =ps.executeQuery();		
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
			rs.close();
			//System.out.println("getStringParam executed");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			con.closeConnection();
		}
		return param;
	}
	
	public static String getNextIdRole(DBConnection con) {
		String strSQL = "";
			strSQL = "(SELECT NVL(MAX(A.ROLEID), 0)+ 1 FROM MSTROLE A)";
		 log.debug(strSQL);
		return getStringParam(strSQL, con);
	}

	public void setTheme(String loginid, String theme) {
		String strSQL = "";
		DBConnection con = new DBConnection();
		try {

			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement(" UPDATE MSTLOGIN SET theme=? WHERE LOGINID=?");
			ps.setString(1, theme);
			ps.setString(2, loginid );
			
			ps.executeUpdate();

			/*strSQL = " UPDATE MSTLOGIN SET theme='" + (theme)
					+ "' WHERE LOGINID='" + loginid + "' ";
			con.update(strSQL);*/

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();

		} finally {
			con.closeConnection();
		}

	}
	
	public static String numberFormet(String var) {
		String x = "";
		try {
			if (var.indexOf(".") != -1) {

//				double retD = (new Double(var)).doubleValue();
				BigDecimal ret = new BigDecimal(var);
				x = "" + ret;
				int j = x.indexOf(".");
				String y = x.substring(j + 1);

				double decret = (new Double("1." + y)).doubleValue();
				String xx = "" + decret;
				int jj = xx.indexOf(".");
				y = xx.substring(jj + 1);
				if (y.equals("0"))
					y = "";

				String z = x.substring(0, j);
				String returnvalue;
				if (y.length() == 0) {
					returnvalue = "" + z;
				} else {
					returnvalue = z + "." + y;
				}
				return returnvalue;
			}
			return var;
		} catch (NumberFormatException nfe) {
			return var;
		} catch (StringIndexOutOfBoundsException e) {
			return x;
		}
	}

	/*public void generateMessage(String loginId, String message) {
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			String strSQL = "";
			String messageId = "";

			strSQL = "SELECT LPAD(MESSAGEID_SEQ.NEXTVAL, 12, 0) FROM DUAL ";
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				messageId = rs.getString(1);
			}
			rs.close();

			message = message + ". Please contact Mr. Neeraj Gupta/CRIS.";
			strSQL = "INSERT INTO MSTMESSAGE (MESSAGEID, MESSAGETYPE, MESSAGE, MESSAGEDATE, LOGINID, STATUSFLAG, STATUSDATE, CLOSESTATUS, CLOSEDATE, AUTOALERT)"
					+ " VALUES('"
					+ messageId
					+ "', 'E', '"
					+ message
					+ "', SYSDATE, '"
					+ loginId
					+ "', NULL, NULL, NULL, NULL,'Y')";
			con.insert(strSQL);

			strSQL = "INSERT INTO TRNMESSAGE (MESSAGEID, LOGINID, STATUSFLAG, STATUSDATE, REPLYID)"
					+ " VALUES ('"
					+ messageId
					+ "', 'crisadmin', NULL, NULL, NULL)";
			con.insert(strSQL);
		} catch (Exception e) {
			con.rollback();
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
	}*/
	//soumen func
	public void generateMessage(String loginId, String message) {
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			String strSQL = "";
			String messageId = "";

			strSQL = "SELECT LPAD(MESSAGEID_SEQ.NEXTVAL, 12, 0) FROM DUAL ";
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				messageId = rs.getString(1);
			}
			rs.close();

			message = message + ". Please contact Mr. Neeraj Gupta/CRIS.";
			/*strSQL = "INSERT INTO MSTMESSAGE (MESSAGEID, MESSAGETYPE, MESSAGE, MESSAGEDATE, LOGINID, STATUSFLAG, STATUSDATE, CLOSESTATUS, CLOSEDATE, AUTOALERT)"
					+ " VALUES('"
					+ messageId
					+ "', 'E', '"
					+ message
					+ "', SYSDATE, '"
					+ loginId
					+ "', NULL, NULL, NULL, NULL,'Y')";
					
					con.insert(strSQL);
					*/
			
			strSQL = "INSERT INTO MSTMESSAGE (MESSAGEID, MESSAGETYPE, MESSAGE, MESSAGEDATE, LOGINID, STATUSFLAG, STATUSDATE, CLOSESTATUS, CLOSEDATE, AUTOALERT)"
					+ " VALUES(?, 'E', ?, SYSDATE, ?, NULL, NULL, NULL, NULL,'Y')";
			 
			PreparedStatement ps2 = con.setPrepStatement(strSQL);
			ps2.setString(1, messageId);
			ps2.setString(2, message);
			ps2.setString(3, loginId);
			int row =ps2.executeUpdate();
			//System.out.println("generateMessage::TOTAL ROWS INSERTED:"+ row);	

			/*strSQL = "INSERT INTO TRNMESSAGE (MESSAGEID, LOGINID, STATUSFLAG, STATUSDATE, REPLYID)"
					+ " VALUES ('"
					+ messageId
					+ "', 'crisadmin', NULL, NULL, NULL)";
			con.insert(strSQL);*/
			
			strSQL = "INSERT INTO TRNMESSAGE (MESSAGEID, LOGINID, STATUSFLAG, STATUSDATE, REPLYID)"
					+ " VALUES (?, 'crisadmin', NULL, NULL, NULL)";
			ps2 = con.setPrepStatement(strSQL);
			ps2.setString(1, messageId);
			row =ps2.executeUpdate();
			//System.out.println("generateMessage::2::TOTAL ROWS INSERTED:"+ row);	
			
		} catch (Exception e) {
			con.rollback();
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
	}
	
	
	public String SaveQuotes(String messageText,String loginId) {
		String isDataSaved = "0~Can not Saved.";
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			String strSQL = "";
			strSQL = "INSERT INTO MSTMESSAGE (MESSAGETEXT,MESSAGETYPE,LOGINID,ISACTIVE)"
					+ " VALUES('"
					+ messageText
					+ "', 'IQ', '"
					+ loginId
					+ "','Y')";
			con.insert(strSQL);
			isDataSaved = "1~Saved Successfully.";
			
		} catch (Exception e) {
			con.rollback();
			isDataSaved = "0~Can not Saved";
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return isDataSaved;
	}
	
	public static String getNextId(DBConnection con, String colName, String tableName) {
		String strSQL = "";
			strSQL = "(SELECT NVL(MAX(A."+colName+"), 0)+ 1 FROM "+tableName+" A)";
		 log.debug(strSQL);
		return getStringParam(strSQL, con);
	}
	
	public static String getNextIdDept(DBConnection con) {
		String strSQL = "";
			strSQL = "(SELECT NVL(MAX(A.DEPTCODE), 0)+ 1 FROM MSTDEPT A)";
		 log.debug(strSQL);
		return getStringParam(strSQL, con);
	}
	
	public static String getNextIdRef(DBConnection con){
		String strSQL = "";
		String param = "";
	
		try{
			strSQL = "(SELECT NVL(MAX(A.REFID), 0)+ 1 FROM TRNREFERENCE A)";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
		}
		catch(Exception e){
			log.fatal(e, e);
		}
		return param;
		
	}
	
	public static String getNextIdFile(DBConnection con){
		String strSQL = "";
		String param = "";
	
		try{
			strSQL = "(SELECT NVL(MAX(A.FMID), 0)+ 1 FROM TRNFILEHDR A)";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
		}
		catch(Exception e){
			log.fatal(e, e);
		}
		return param;
		
	}
	
	
	/*public static String getStringParam(String strSQL, DBConnection con) {
		String param = "";
		try {
			//con.openConnection();
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
			rs.close();
		} catch (Exception e) {
			log.fatal(e, e);
		}
		return param;
	}*/
//soumen func
	public static String getStringParam(String strSQL, DBConnection con) {
		String param = "";
		try {
			//con.openConnection();
			//ResultSet rs = con.select(strSQL);
			PreparedStatement ps=con.setPrepStatement(strSQL);
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
			rs.close();
		} catch (Exception e) {
			log.fatal(e, e);
		}
		return param;
	}
	public static String getSysDate(String dateFormat) {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return sdf.format(cal.getTime());
	  }
	
	public static void main(String[] args) {
		//System.out.println(getSysDate("dd/MM/yyyy"));
		
		//System.out.println(Calendar.getInstance().get(Calendar.YEAR));
		
	}
	
	/*public ArrayList<CommonBean> getRefNameSearch(String referenceName){
		
		String strSQL = " SELECT DISTINCT '<font color=#001f00>'||VIPNAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, VIPNAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY FROM MSTVIP WHERE UPPER(VIPNAME) LIKE UPPER('%"+referenceName+"%')"+	
						" UNION " +
						" SELECT DISTINCT '<font color=#cc0000>'||REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER('%"+referenceName+"%')";
		return getSQLResult(strSQL, 5);
		
	}*/
	
	/*public ArrayList<CommonBean> getRefNameSearch(String referenceName){
			String strSQL="";
		
		 strSQL = " SELECT DISTINCT '<font color=#cc0000>'||REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER('%"+referenceName+"%')";
		
		
		
		return getSQLResult(strSQL, 5);
		
	}*/
	
	//soumen function
	public ArrayList<CommonBean> getRefNameSearch(String referenceName){
		String strSQL="";
	
	 /*strSQL = " SELECT DISTINCT '<font color=#cc0000>'||REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER('%"+referenceName+"%')";
	
	
	
	return getSQLResult(strSQL, 5);*/
		
	strSQL = " SELECT DISTINCT '<font color=#cc0000>'||REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER(?)";
		
	DBConnection con = new DBConnection();
	ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
	try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement(strSQL);
				ps.setString(1,"%"+referenceName+"%");
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 5,con);
				//System.out.println("getRefNameSearch executed");
				
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		finally{
				con.closeConnection();
		}
	return arr;
		
}
	
	/*public ArrayList<CommonBean> getRefNameSearchnew(String referenceName){
		String strSQL="";
		 strSQL = " SELECT DISTINCT '<font color=#001f00>'||VIPNAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, VIPNAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY,vipid,vipaddress,CORRADDRESS,VIPSTATUSDESC FROM MSTVIP WHERE UPPER(VIPNAME) LIKE UPPER('%"+referenceName+"%')"+	
					" UNION " +
					" SELECT DISTINCT '<font color=#cc0000>'||REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY,addvipid,addviptype,ADDRESSENGLISH,VIPSTATUSDESC FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER('%"+referenceName+"%')";
		
		
		return getSQLResult(strSQL,9);
		
	}*/
	//soumemn func 
	public ArrayList<CommonBean> getRefNameSearchnew(String referenceName){
		String strSQL="";
		 /*strSQL = " SELECT DISTINCT '<font color=#001f00>'||VIPNAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, VIPNAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY,vipid,vipaddress,CORRADDRESS,VIPSTATUSDESC FROM MSTVIP WHERE UPPER(VIPNAME) LIKE UPPER('%"+referenceName+"%')"+	
					" UNION " +
					" SELECT DISTINCT '<font color=#cc0000>'||REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY,addvipid,addviptype,ADDRESSENGLISH,VIPSTATUSDESC FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER('%"+referenceName+"%')";
		*/
		
		//return getSQLResult(strSQL,9);
		 
		strSQL = " SELECT DISTINCT '<font color=#001f00>'||VIPNAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, VIPNAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY,vipid,vipaddress,CORRADDRESS,VIPSTATUSDESC FROM MSTVIP WHERE UPPER(VIPNAME) LIKE UPPER(?)"+	
				" UNION " +
				" SELECT DISTINCT '<font color=#cc0000>'||REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY,addvipid,addviptype,ADDRESSENGLISH,VIPSTATUSDESC FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER(?)";

		 ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		 DBConnection con = new DBConnection();
		 try {
		 			con.openConnection();
		 			PreparedStatement ps = con.setPrepStatement(strSQL);
		 			ps.setString(1,"%"+referenceName+"%");
		 			ps.setString(2,"%"+referenceName+"%");
		 			arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 9,con);
		 			//System.out.println("getCaseListCMSRLYDiv executed");
		 			
		 	} catch (SQLException e) {
		 			// TODO Auto-generated catch block
		 			e.printStackTrace();
		 	}
		 	finally{
		 			con.closeConnection();
		 	}
		 return arr;
		 		
		
	}
	
	public ArrayList<CommonBean> getRefNameSearchFile(String referenceName){
		String strSQL="";
		
		 
		strSQL = " SELECT DISTINCT '<font color=#001f00>'||VIPNAME ||' - '|| VIPSTATUS ||' - '|| STATECODE||'- '|| VIPPARTY||'</font>' REFNAMECOMBINED, VIPNAME REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY,vipid,vipaddress,CORRADDRESS,VIPSTATUSDESC FROM MSTVIP WHERE UPPER(VIPNAME) LIKE UPPER(?)"	;
			

		 ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		 DBConnection con = new DBConnection();
		 try {
		 			con.openConnection();
		 			PreparedStatement ps = con.setPrepStatement(strSQL);
		 			ps.setString(1,"%"+referenceName+"%");
		 			
		 			arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 9,con);
		 			//System.out.println("getCaseListCMSRLYDiv executed");
		 			
		 	} catch (SQLException e) {
		 			// TODO Auto-generated catch block
		 			e.printStackTrace();
		 	}
		 	finally{
		 			con.closeConnection();
		 	}
		 return arr;
		 		
		
	}
	
	public ArrayList<CommonBean> getRefDashBoardDetails_RoleWise(String loginId){

		
		
		String strSQL = "select NVL(markingto,9999999),NVL((select rolename from mstrole b where "
				+ "b.roleid=a.markingto),'TOTAL') sname ,count(refid) from trnmarking a where MARKINGTO in "
				+ "( select roleid from mstrole where deptcode in( "
				+ "(select deptcode from mstdept where hod=(select roleid from mstrole where roleid ='"+loginId+"' ))) "
				+ "and refid in (select refid from trnreference  where  "
				+ "tenureid>=12 and refclass in ('A','M'))) group by cube(markingto) order by sname";
		//System.out.println(" getReportData getReportData+++++++++++++++++++++"+strSQL);	
			return getSQLResult(strSQL, 3);
			
		}



	
	/*public ArrayList<CommonBean> getRefNameSearchA(String referenceName){
		
		String strSQL = " SELECT DISTINCT REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER('%"+referenceName+"%')";
		return getSQLResult(strSQL, 4);
		
	}*/
	//soumen func
public ArrayList<CommonBean> getRefNameSearchA(String referenceName){
		
		/*String strSQL = " SELECT DISTINCT REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER('%"+referenceName+"%')";
		return getSQLResult(strSQL, 4);*/
		
		String strSQL = " SELECT DISTINCT REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER(?)";
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,"%"+referenceName+"%");
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 4,con);
					//System.out.println("getRefNameSearchA executed");
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
				
		
	}
	
//	FOR STATIC REPORT CHANGED BY SUNEEL
	
	public ArrayList<CommonBean> getRefNameSearch1(String referenceName){		
		String strSQL = " SELECT DISTINCT REFERENCENAME ||' - '|| VIPSTATUS ||' - '|| STATECODE REFNAMECOMBINED, REFERENCENAME REFERENCENAME, VIPSTATUS, STATECODE FROM TRNREFERENCE WHERE UPPER(REFERENCENAME) LIKE UPPER('%"+referenceName+"%')";
		//System.out.println("suneel - > "+ strSQL);
		return getSQLResult(strSQL, 4);	
	}
//	FOR CUSTOM REPORT CHANGED BY SUNEEL
	public ArrayList<CommonBean> getStatus(String status){	
		String strSQL = " SELECT DISTINCT VIPSTATUS FROM TRNREFERENCE WHERE UPPER(VIPSTATUS) LIKE UPPER('%"+status+"%')";
		return getSQLResult(strSQL, 1);
	}
	
	public ArrayList<CommonBean> getFileNo(String fileno,String fdate,String tdate,String roleid){	
		String strSQL = " SELECT DISTINCT FILENO FROM TRNFILEHDR WHERE UPPER(FILENO) LIKE UPPER('%"+fileno+"%') " +
				"AND TO_DATE(TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleid+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
		return getSQLResult(strSQL, 1);
	}
	
	public ArrayList<CommonBean> getState(String state){	
		String strSQL = " SELECT DISTINCT STATECODE,STATENAME FROM MSTSTATE WHERE UPPER(STATENAME) LIKE UPPER('%"+state+"%') ORDER BY STATENAME";
		return getSQLResult(strSQL, 2);
	}
	
	public ArrayList<CommonBean> getRefNoSearch(String refnumber,String destinationmarking){
		
		/*String strSQL = " SELECT A.REFNO,A.REFID,A.REFERENCENAME," +
				"(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R') SUBJECTNAME," +
				"REPLACE(REPLACE(A.SUBJECT, CHR(10), ' '), CHR(13), ' ') SUBJECT," +
				"(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO)||','||(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = O.ACTION)||','||O.ACTIONDATE FROM TRNMARKING O WHERE O.REFID=A.REFID " +
				" AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNMARKING P WHERE P.REFID=A.REFID)) STATUS,A.VIPSTATUS," +
				"(SELECT R.STATENAME FROM MSTSTATE R WHERE R.STATECODE=A.STATECODE) STATENAME,A.SUBJECTCODE,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE" +
				" FROM TRNREFERENCE A WHERE UPPER(A.REFNO) LIKE UPPER('%"+refnumber+"%') " +
				"AND A.ROLEID = '"+destinationmarking+"'" +
				//" AND A.REFID NOT IN (SELECT REFID FROM TRNFILEREF)" +
				"";*/
		
		String strSQL ="SELECT A.REFNO,A.REFID,A.REFERENCENAME,"
				+ "B.SUBJECTNAME SUBJECTNAME,"
				+ "REPLACE(REPLACE(A.SUBJECT, CHR(10), ' '), CHR(13), ' ') SUBJECT,"
				+ "'' STATUS,A.VIPSTATUS,"
				+ "C.STATENAME STATENAME,A.SUBJECTCODE,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE, A.FILESTATUS1 FILESTATUS1 "
				+ "FROM TRNREFERENCE A,MSTSUBJECT B,MSTSTATE C WHERE UPPER(A.REFNO) LIKE UPPER('%"+refnumber+"%') "
						+ "AND A.ROLEID = '"+destinationmarking+"'"
						+ "and A.SUBJECTCODE=B.SUBJECTCODE(+) and A.STATECODE=C.STATECODE(+)";
		return getSQLResult(strSQL, 11);
		
	}
	
	/*public ArrayList<CommonBean> getRoleIdSearch(String roleId){
		String strSQL = " SELECT ROLEID, ROLENAME FROM MSTROLE WHERE UPPER(ROLENAME) LIKE UPPER('"+roleId+"%') ";
		return getSQLResult(strSQL, 2);
	}*/
	//soumen func
	public ArrayList<CommonBean> getRoleIdSearch(String roleId){
		/*String strSQL = " SELECT ROLEID, ROLENAME FROM MSTROLE WHERE UPPER(ROLENAME) LIKE UPPER('"+roleId+"%') ";
		return getSQLResult(strSQL, 2);*/
		String strSQL = " SELECT ROLEID, ROLENAME FROM MSTROLE WHERE UPPER(ROLENAME) LIKE UPPER(?) ";
		
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,roleId+"%");
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 2,con);
					//System.out.println("getRoleIdSearch executed");
					
			} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
		
	}


	public ArrayList<CommonBean> getRefNoSearch(String refnumber){
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection dbcon = new DBConnection();
		PreparedStatement ps = null;
		String strSQL = " SELECT A.REFNO,A.REFID,A.REFERENCENAME," +
		"(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R') SUBJECTNAME," +
		"REPLACE(REPLACE(A.SUBJECT, CHR(10), ' '), CHR(13), ' ') SUBJECT," +
		"(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO)||','||(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = O.ACTION)||','||O.ACTIONDATE FROM TRNMARKING O WHERE O.REFID=A.REFID " +
		" AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNMARKING P WHERE P.REFID=A.REFID)) STATUS,A.VIPSTATUS," +
		"(SELECT R.STATENAME FROM MSTSTATE R WHERE R.STATECODE=A.STATECODE) STATENAME,A.SUBJECTCODE" +
		" FROM TRNREFERENCE A WHERE UPPER(A.REFNO) LIKE UPPER(?) " +
		//"AND A.ROLEID = '"+destinationmarking+"'" +
		//" AND A.REFID NOT IN (SELECT REFID FROM TRNFILEREF)" +
		"";
		try{
			dbcon.openConnection();
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1,"%"+refnumber+"%");
			arrList = (new CommonDAO()).getSQLResultPreparedStmt(ps,9,dbcon);
			
		}
		catch (Exception e){
			
		}
		finally{
			dbcon.closeConnection();
		}
		
		
		return arrList;
	}
	
	
	

	public ArrayList<CommonBean> getFileNoSearch(String fileno,String roleId){
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection dbcon = new DBConnection();
		PreparedStatement ps = null;
		
		String strSQL = " SELECT DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
				"TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
				"DECODE(A.DESTINATIONMARKING,?,A.FILECOUNTERDES,A.FILECOUNTERORG) FILECOUNTER,A.FMID," +
				"(SELECT DISTINCT X.ROLENAME FROM MSTROLE X WHERE X.ROLEID = A.RECEIVEDFROM ) RECEIVEDFROM," +
				"DECODE(A.DESTINATIONMARKING,?,A.FILESUBJECTCODEDES,A.FILESUBJECTCODEORG) FILESUBJECTCODE," +
				"REPLACE(REPLACE(A.SUBJECT, CHR(10), ' '), CHR(13), ' ') SUBJECT,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
				"(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
				"(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO) FROM TRNFILEINTMARKING O WHERE O.FMID=A.FMID " +
				" AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID)) MARKINGTO," +
				"(SELECT TO_CHAR(O.MARKINGDATE,'DD/MM/YYYY') FROM TRNFILEINTMARKING O WHERE O.FMID=A.FMID " +
				" AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID)) MARKINGDATE" +
				" FROM TRNFILEHDR A WHERE UPPER(A.FILENO) = UPPER(?) AND A.FILESTATUS1 IN ('1','6','10','12')" +
				
				//" AND A.REFID NOT IN (SELECT REFID FROM TRNFILEREF)" +
				"";
		try{
			dbcon.openConnection();
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, roleId);
			ps.setString(2, roleId);
			ps.setString(3, roleId);
			ps.setString(4, roleId);
			ps.setString(5, fileno);
			arrList = (new CommonDAO()).getSQLResultPreparedStmt(ps,11,dbcon);
			
		}
		catch (Exception e){
			
		}
		finally{
			dbcon.closeConnection();
		}
		
		
		return arrList;
	}
	
	
	public ArrayList<CommonBean> getSQLResult(String strSQL, int cols) {
	ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
	CommonBean bn;
	DBConnection con = new DBConnection();
	try {
		con.openConnection();
		if (strSQL.length() > 0) {

			log.debug(strSQL);
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				bn = new CommonBean();
				switch (cols) {
				case 39:
					bn.setField39(handleNull(rs.getString(39)));
				case 38:
					bn.setField38(handleNull(rs.getString(38)));
				case 37:
					bn.setField37(handleNull(rs.getString(37)));
				case 36:
					bn.setField36(handleNull(rs.getString(36)));
				case 35:
					bn.setField35(handleNull(rs.getString(35)));
				case 34:
					bn.setField34(handleNull(rs.getString(34)));
				case 33:
					bn.setField33(handleNull(rs.getString(33)));					
				case 32:
					bn.setField32(handleNull(rs.getString(32)));
				case 31:
					bn.setField31(handleNull(rs.getString(31)));
				case 30:
					bn.setField30(handleNull(rs.getString(30)));
				case 29:
					bn.setField29(handleNull(rs.getString(29)));
				case 28:
					bn.setField28(handleNull(rs.getString(28)));
				case 27:
					bn.setField27(handleNull(rs.getString(27)));
				case 26:
					bn.setField26(handleNull(rs.getString(26)));
				case 25:
					bn.setField25(handleNull(rs.getString(25)));
				case 24:
					bn.setField24(handleNull(rs.getString(24)));
				case 23:
					bn.setField23(handleNull(rs.getString(23)));
				case 22:
					bn.setField22(handleNull(rs.getString(22)));
				case 21:
					bn.setField21(handleNull(rs.getString(21)));
				case 20:
					bn.setField20(handleNull(rs.getString(20)));
				case 19:
					bn.setField19(handleNull(rs.getString(19)));
				case 18:
					bn.setField18(handleNull(rs.getString(18)));
				case 17:
					bn.setField17(handleNull(rs.getString(17)));
				case 16:
					bn.setField16(handleNull(rs.getString(16)));
				case 15:
					bn.setField15(handleNull(rs.getString(15)));
				case 14:
					bn.setField14(handleNull(rs.getString(14)));
				case 13:
					bn.setField13(handleNull(rs.getString(13)));
				case 12:
					bn.setField12(handleNull(rs.getString(12)));
				case 11:
					bn.setField11(handleNull(rs.getString(11)));
				case 10:
					bn.setField10(handleNull(rs.getString(10)));
				case 9:
					bn.setField9(handleNull(rs.getString(9)));
				case 8:
					bn.setField8(handleNull(rs.getString(8)));
				case 7:
					bn.setField7(handleNull(rs.getString(7)));
				case 6:
					bn.setField6(handleNull(rs.getString(6)));
				case 5:
					bn.setField5(handleNull(rs.getString(5)));
				case 4:
					bn.setField4(handleNull(rs.getString(4)));
				case 3:
					bn.setField3(handleNull(rs.getString(3)));
				case 2:
					bn.setField2(handleNull(rs.getString(2)));
				case 1:
					bn.setField1(handleNull(rs.getString(1)));
					break;
				}
				arr.add(bn);
			}
			rs.close();
		}
	} catch (Exception e) {
		log.fatal(e, e);
	} finally {

		con.closeConnection();
	}
	return arr;
	}
	
	public ArrayList<CommonBean> getSQLResultPreparedStmt(PreparedStatement pst, int cols, DBConnection con) {
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		CommonBean bn;
		
		try {

				log.debug(pst);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					bn = new CommonBean();
					switch (cols) {
					case 39:
						bn.setField39(handleNull(rs.getString(39)));
					case 38:
						bn.setField38(handleNull(rs.getString(38)));
					case 37:
						bn.setField37(handleNull(rs.getString(37)));
					case 36:
						bn.setField36(handleNull(rs.getString(36)));
					case 35:
						bn.setField35(handleNull(rs.getString(35)));
					case 34:
						bn.setField34(handleNull(rs.getString(34)));
					case 33:
						bn.setField33(handleNull(rs.getString(33)));					
					case 32:
						bn.setField32(handleNull(rs.getString(32)));
					case 31:
						bn.setField31(handleNull(rs.getString(31)));
					case 30:
						bn.setField30(handleNull(rs.getString(30)));
					case 29:
						bn.setField29(handleNull(rs.getString(29)));
					case 28:
						bn.setField28(handleNull(rs.getString(28)));
					case 27:
						bn.setField27(handleNull(rs.getString(27)));
					case 26:
						bn.setField26(handleNull(rs.getString(26)));
					case 25:
						bn.setField25(handleNull(rs.getString(25)));
					case 24:
						bn.setField24(handleNull(rs.getString(24)));
					case 23:
						bn.setField23(handleNull(rs.getString(23)));
					case 22:
						bn.setField22(handleNull(rs.getString(22)));
					case 21:
						bn.setField21(handleNull(rs.getString(21)));
					case 20:
						bn.setField20(handleNull(rs.getString(20)));
					case 19:
						bn.setField19(handleNull(rs.getString(19)));
					case 18:
						bn.setField18(handleNull(rs.getString(18)));
					case 17:
						bn.setField17(handleNull(rs.getString(17)));
					case 16:
						bn.setField16(handleNull(rs.getString(16)));
					case 15:
						bn.setField15(handleNull(rs.getString(15)));
					case 14:
						bn.setField14(handleNull(rs.getString(14)));
					case 13:
						bn.setField13(handleNull(rs.getString(13)));
					case 12:
						bn.setField12(handleNull(rs.getString(12)));
					case 11:
						bn.setField11(handleNull(rs.getString(11)));
					case 10:
						bn.setField10(handleNull(rs.getString(10)));
					case 9:
						bn.setField9(handleNull(rs.getString(9)));
					case 8:
						bn.setField8(handleNull(rs.getString(8)));
					case 7:
						bn.setField7(handleNull(rs.getString(7)));
					case 6:
						bn.setField6(handleNull(rs.getString(6)));
					case 5:
						bn.setField5(handleNull(rs.getString(5)));
					case 4:
						bn.setField4(handleNull(rs.getString(4)));
					case 3:
						bn.setField3(handleNull(rs.getString(3)));
					case 2:
						bn.setField2(handleNull(rs.getString(2)));
					case 1:
						bn.setField1(handleNull(rs.getString(1)));
						break;
					}
					arr.add(bn);
				}
				rs.close();
			
		} catch (Exception e) {
			log.fatal(e, e);
		}
		return arr;
		}
	
	public ArrayList<CommonBean> getSQLResultPreparedStmtRep2(PreparedStatement pst, int cols, DBConnection con) {
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		CommonBean bn;
		//final String secret = "iojd345iuK";
		
		try {

				log.debug(pst);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					bn = new CommonBean();
					switch (cols) {
					case 39:
						bn.setField39(handleNull(rs.getString(39)));
					case 38:
						bn.setField38(handleNull(rs.getString(38)));
					case 37:
						bn.setField37(handleNull(rs.getString(37)));
					case 36:
						bn.setField36(handleNull(rs.getString(36)));
					case 35:
						bn.setField35(handleNull(rs.getString(35)));
					case 34:
						bn.setField34(handleNull(rs.getString(34)));
					case 33:
						bn.setField33(handleNull(rs.getString(33)));					
					case 32:
						bn.setField32(handleNull(rs.getString(32)));
					case 31:
						bn.setField31(handleNull(rs.getString(31)));
					case 30:
						bn.setField30(handleNull(rs.getString(30)));
					case 29:
						bn.setField29(handleNull(rs.getString(29)));
					case 28:
						bn.setField28(handleNull(rs.getString(28)));
					case 27:
						bn.setField27(handleNull(rs.getString(27)));
					case 26:
						bn.setField26(handleNull(rs.getString(26)));
					case 25:
						bn.setField25(handleNull(rs.getString(25)));
					case 24:
						bn.setField24(handleNull(rs.getString(24)));
					case 23:
						bn.setField23(handleNull(rs.getString(23)));
					case 22:
						bn.setField22(handleNull(rs.getString(22)));
					case 21:
						bn.setField21(handleNull(rs.getString(21)));
					case 20:
						bn.setField20(handleNull(rs.getString(20)));
					case 19:
						bn.setField19(handleNull(rs.getString(19)));
					case 18:
						bn.setField18(handleNull(rs.getString(18)));
					case 17:
						bn.setField17(handleNull(rs.getString(17)));
					case 16:
						bn.setField16(handleNull(rs.getString(16)));
					case 15:
						//System.out.println(rs.getString(4));
							if(rs.getString(4).equalsIgnoreCase("Conf.")){
								//bn.setField15(handleNull(AES.decrypt(handleNull(rs.getString(15)), secret)));
								bn.setField15(handleNull(Encrypt.decrypt(handleNull(rs.getString(15)))));
						}else{
							bn.setField15(handleNull(rs.getString(15)));
						}
							//System.out.println(rs.getString(15));
					case 14:
						
						bn.setField14(handleNull(rs.getString(14)));
					case 13:
						bn.setField13(handleNull(rs.getString(13)));
					case 12:
						bn.setField12(handleNull(rs.getString(12)));
					case 11:
						bn.setField11(handleNull(rs.getString(11)));
					case 10:
						bn.setField10(handleNull(rs.getString(10)));
					case 9:
						bn.setField9(handleNull(rs.getString(9)));
					case 8:
						bn.setField8(handleNull(rs.getString(8)));
					case 7:
						bn.setField7(handleNull(rs.getString(7)));
					case 6:
						bn.setField6(handleNull(rs.getString(6)));
					case 5:
						bn.setField5(handleNull(rs.getString(5)));
					case 4:
						bn.setField4(handleNull(rs.getString(4)));
					case 3:
						bn.setField3(handleNull(rs.getString(3)));
					case 2:
						bn.setField2(handleNull(rs.getString(2)));
					case 1:
						bn.setField1(handleNull(rs.getString(1)));
						break;
					}
					arr.add(bn);
				}
				rs.close();
			
		} catch (Exception e) {
			log.fatal(e, e);
		}
		return arr;
		}
	
	public ArrayList<CommonBean> getSQLResultPreparedStmtRep1(PreparedStatement pst, int cols, DBConnection con) {
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		CommonBean bn;
		//final String secret = "iojd345iuK";
		
		try {

				log.debug(pst);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					bn = new CommonBean();
					switch (cols) {
					case 5:
						bn.setField5(handleNull(rs.getString(5)));
					case 4:
						if(rs.getString(5).equalsIgnoreCase("C")){
							//bn.setField4(handleNull(AES.decrypt(handleNull(rs.getString(4)), secret)));
							bn.setField4(handleNull(Encrypt.decrypt(handleNull(rs.getString(4)))));
						}
						else{
							bn.setField4(handleNull(rs.getString(4)));
						}
						
					case 3:
						bn.setField3(handleNull(rs.getString(3)));
					case 2:
						bn.setField2(handleNull(rs.getString(2)));
					case 1:
						bn.setField1(handleNull(rs.getString(1)));
						break;
					}
					arr.add(bn);
				}
				rs.close();
			
		} catch (Exception e) {
			log.fatal(e, e);
		}
		return arr;
		}
	
	public ArrayList getMenuData(String group) {

		MenuBean bean;
		ArrayList arr = new ArrayList();

		String query = "";

		bean = new MenuBean();
		bean.setMenuid("1");
		bean.setParentid("");
		bean.setFlag("H");
		bean.setScflag("0");
		bean.setUrl("#");
		bean.setMenutext("Forms");
		bean.setRoot("1");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("1.1");
		bean.setParentid("1");
		bean.setFlag("U");
		bean.setScflag("1");
		bean.setUrl("dummy.jsp");
		bean.setMenutext("Revenue");
		bean.setRoot("1");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("1.2");
		bean.setParentid("1");
		bean.setFlag("U");
		bean.setScflag("1");
		bean.setUrl("dummy1.jsp");
		bean.setMenutext("Works");
		bean.setRoot("1");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("1.3");
		bean.setParentid("1");
		bean.setFlag("U");
		bean.setScflag("0");
		bean.setScflag("1");
		bean.setUrl("dummy2.jsp");
		bean.setMenutext("Earning");
		bean.setRoot("1");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2");
		bean.setParentid("");
		bean.setFlag("H");
		bean.setScflag("0");
		bean.setUrl("#");
		bean.setMenutext("Reports");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.1");
		bean.setParentid("2");
		bean.setFlag("H");
		bean.setScflag("0");
		bean.setUrl("#");
		bean.setMenutext("rep 2.1");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.1.1");
		bean.setParentid("2.1");
		bean.setFlag("H");
		bean.setScflag("0");
		bean.setUrl("#");
		bean.setMenutext("rep 2.1.1");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.1.1.1");
		bean.setParentid("2.1.1");
		bean.setFlag("U");
		bean.setScflag("1");
		bean.setUrl("dummy.jsp");
		bean.setMenutext("report");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.1.1.2");
		bean.setParentid("2.1.1");
		bean.setFlag("U");
		bean.setScflag("1");
		bean.setUrl("dummy1.jsp");
		bean.setMenutext("report2");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.2");
		bean.setParentid("2");
		bean.setFlag("H");
		bean.setScflag("0");
		bean.setUrl("#");
		bean.setMenutext("rep 2.2");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.2.1");
		bean.setParentid("2.2");
		bean.setFlag("U");
		bean.setScflag("0");
		bean.setUrl("dummy.jsp");
		bean.setMenutext("rep 2.2.1");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.3");
		bean.setParentid("2");
		bean.setFlag("H");
		bean.setScflag("0");
		bean.setUrl("#");
		bean.setMenutext("rep 2.3");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.3.1");
		bean.setParentid("2.3");
		bean.setFlag("H");
		bean.setScflag("0");
		bean.setUrl("#");
		bean.setMenutext("rep 2.3.1");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.3.1.1");
		bean.setParentid("2.3.1");
		bean.setFlag("U");
		bean.setScflag("1");
		bean.setUrl("dummy.jsp");
		bean.setMenutext("report");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("2.3.1.2");
		bean.setParentid("2.3.1");
		bean.setFlag("U");
		bean.setScflag("1");
		bean.setUrl("dummy1.jsp");
		bean.setMenutext("report2");
		bean.setRoot("2");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("3");
		bean.setParentid("");
		bean.setFlag("H");
		bean.setScflag("0");
		bean.setUrl("#");
		bean.setMenutext("Administration");
		bean.setRoot("3");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("3.1");
		bean.setParentid("3");
		bean.setFlag("U");
		bean.setScflag("1");
		bean.setUrl("dummy2.jsp");
		bean.setMenutext("Edit Profile");
		bean.setRoot("3");
		arr.add(bean);

		bean = new MenuBean();
		bean.setMenuid("3.2");
		bean.setParentid("3");
		bean.setFlag("U");
		bean.setScflag("1");
		bean.setUrl("dummy.jsp");
		bean.setMenutext("Change Password");
		bean.setRoot("3");
		arr.add(bean);

		/*
		 * bean=new MenuBean(); bean.setMenuid("4"); bean.setParentid("");
		 * bean.setFlag("H"); bean.setScflag("0"); bean.setUrl("#");
		 * bean.setMenutext("head 4"); bean.setRoot("4"); arr.add(bean);
		 * 
		 * bean=new MenuBean(); bean.setMenuid("5"); bean.setParentid("");
		 * bean.setFlag("H"); bean.setScflag("0"); bean.setUrl("#");
		 * bean.setMenutext("head 5"); bean.setRoot("5"); arr.add(bean);
		 * 
		 * bean=new MenuBean(); bean.setMenuid("6"); bean.setParentid("");
		 * bean.setFlag("H"); bean.setScflag("0"); bean.setUrl("#");
		 * bean.setMenutext("head 6"); bean.setRoot("6"); arr.add(bean);
		 * 
		 * bean=new MenuBean(); bean.setMenuid("7"); bean.setParentid("");
		 * bean.setFlag("H"); bean.setScflag("0"); bean.setUrl("#");
		 * bean.setMenutext("head 7"); bean.setRoot("7"); arr.add(bean);
		 * 
		 * bean=new MenuBean(); bean.setMenuid("8"); bean.setParentid("");
		 * bean.setFlag("H"); bean.setScflag("0"); bean.setUrl("#");
		 * bean.setMenutext("head 8"); bean.setRoot("8"); arr.add(bean);
		 * 
		 * 
		 * 
		 * 
		 * bean=new MenuBean(); bean.setMenuid("9"); bean.setParentid("");
		 * bean.setFlag("H"); bean.setScflag("0"); bean.setUrl("#");
		 * bean.setMenutext("head 9"); bean.setRoot("9"); arr.add(bean);
		 * 
		 * bean=new MenuBean(); bean.setMenuid("10"); bean.setParentid("");
		 * bean.setFlag("H"); bean.setScflag("0"); bean.setUrl("#");
		 * bean.setMenutext("head 10"); bean.setRoot("10"); arr.add(bean);
		 * 
		 * bean=new MenuBean(); bean.setMenuid("11"); bean.setParentid("");
		 * bean.setFlag("H"); bean.setScflag("0"); bean.setUrl("#");
		 * bean.setMenutext("head 11"); bean.setRoot("11"); arr.add(bean);
		 * 
		 * bean=new MenuBean(); bean.setMenuid("12"); bean.setParentid("");
		 * bean.setFlag("H"); bean.setScflag("0"); bean.setUrl("#");
		 * bean.setMenutext("head 12"); bean.setRoot("12"); arr.add(bean);
		 */

		return arr;
	}
	
	/*public ArrayList getUserMenuData(String group) {

		MenuBean bean;
		ArrayList arr = new ArrayList();

		String query = "";

		query = " select  menuid,parentid,flag,url,nvl(menutext,''),root"
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,1),0),'.','')) \"firstset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,2),0),'.','')) \"secondset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,3),0),'.','')) \"thirdset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,4),0),'.','')) \"fourthset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,5),0),'.','')) \"fifthset\" "
				+ " from usermenuTABLE where upper(groupID)=upper('"+group+"')"
				+ " order by  "
				+ " \"firstset\",\"secondset\",\"thirdset\",\"fourthset\",\"fifthset\""
				+ " ,1";

		//System.out.println("query is USER:" + query);

		DBConnection con = new DBConnection();

		try {
			con.openConnection();
			ResultSet rs = con.select(query);

			while (rs.next()) {
				bean = new MenuBean();

				bean.setMenuid(rs.getString(1));
				bean
						.setParentid(rs.getString(2) != null ? rs.getString(2)
								: "");
				bean.setFlag(rs.getString(3));
				bean.setScflag("0");
				bean.setUrl(rs.getString(4) != null ? rs.getString(4) : "");
				bean.setMenutext(handleNull(rs.getString(5)));
				bean.setRoot(rs.getString(6));
				arr.add(bean);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}

		return arr;
	}*/
	//soumen functions
	public ArrayList getUserMenuData(String group) {

		MenuBean bean;
		ArrayList arr = new ArrayList();

		String query = "";

		/*query = " select  menuid,parentid,flag,url,nvl(menutext,''),root"
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,1),0),'.','')) \"firstset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,2),0),'.','')) \"secondset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,3),0),'.','')) \"thirdset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,4),0),'.','')) \"fourthset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,5),0),'.','')) \"fifthset\" "
				+ " from usermenuTABLE where upper(groupID)=upper('"+group+"')"
				+ " order by  "
				+ " \"firstset\",\"secondset\",\"thirdset\",\"fourthset\",\"fifthset\""
				+ " ,1";*/

		
		query = " select  menuid,parentid,flag,url,nvl(menutext,''),root"
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,1),0),'.','')) \"firstset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,2),0),'.','')) \"secondset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,3),0),'.','')) \"thirdset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,4),0),'.','')) \"fourthset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,5),0),'.','')) \"fifthset\" "
				+ " from usermenuTABLE where upper(groupID)=upper(?)"
				+ " order by  "
				+ " \"firstset\",\"secondset\",\"thirdset\",\"fourthset\",\"fifthset\""
				+ " ,1";

		//System.out.println("query is USER:" + query);

		DBConnection con = new DBConnection();

		try {
			con.openConnection();
			PreparedStatement ps = con.setPrepStatement(query);
			ps.setString(1,group);
			ResultSet rs=ps.executeQuery();
			//ResultSet rs = con.select(query);

			while (rs.next()) {
				bean = new MenuBean();

				bean.setMenuid(rs.getString(1));
				bean
						.setParentid(rs.getString(2) != null ? rs.getString(2)
								: "");
				bean.setFlag(rs.getString(3));
				bean.setScflag("0");
				bean.setUrl(rs.getString(4) != null ? rs.getString(4) : "");
				bean.setMenutext(handleNull(rs.getString(5)));
				bean.setRoot(rs.getString(6));
				arr.add(bean);
			}
			
			rs.close();
			//System.out.println("getUserMenuData executed");

		}

		catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}

		return arr;
	}
	
	
	/*public ArrayList getMasterMenuData(String group) {

		MenuBean bean;
		ArrayList arr = new ArrayList();

		String query = "";

		query = " select  menuid,parentid,flag,url,nvl(menutext,''),root"
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,1),0),'.','')) \"firstset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,2),0),'.','')) \"secondset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,3),0),'.','')) \"thirdset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,4),0),'.','')) \"fourthset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,5),0),'.','')) \"fifthset\" "
				+ " from usermenuTABLE where groupID='"+group+"' and menuid <> '0'"
				+ " order by  "
				+ " \"firstset\",\"secondset\",\"thirdset\",\"fourthset\",\"fifthset\""
				+ " ,1";

		//System.out.println("query is:" + query);

		DBConnection con = new DBConnection();

		try {
			con.openConnection();
			ResultSet rs = con.select(query);

			while (rs.next()) {
				bean = new MenuBean();

				bean.setMenuid(rs.getString(1));
				bean
						.setParentid(rs.getString(2) != null ? rs.getString(2)
								: "");
				bean.setFlag(rs.getString(3));
				bean.setScflag("0");
				bean.setUrl(rs.getString(4) != null ? rs.getString(4) : "");
				bean.setMenutext(handleNull(rs.getString(5)));
				bean.setRoot(rs.getString(6));
				bean.setLevel(rs.getString(6));
				arr.add(bean);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}

		return arr;
	}*/
	//soumen func
	public ArrayList getMasterMenuData(String group) {

		MenuBean bean;
		ArrayList arr = new ArrayList();

		String query = "";

		/*query = " select  menuid,parentid,flag,url,nvl(menutext,''),root"
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,1),0),'.','')) \"firstset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,2),0),'.','')) \"secondset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,3),0),'.','')) \"thirdset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,4),0),'.','')) \"fourthset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,5),0),'.','')) \"fifthset\" "
				+ " from usermenuTABLE where groupID='"+group+"' and menuid <> '0'"
				+ " order by  "
				+ " \"firstset\",\"secondset\",\"thirdset\",\"fourthset\",\"fifthset\""
				+ " ,1";*/

		
		query = " select  menuid,parentid,flag,url,nvl(menutext,''),root"
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,1),0),'.','')) \"firstset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,2),0),'.','')) \"secondset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,3),0),'.','')) \"thirdset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,4),0),'.','')) \"fourthset\" "
				+ " ,to_number(replace(nvl(regexp_substr(menuid,'\\.*[0-9]*',1,5),0),'.','')) \"fifthset\" "
				+ " from usermenuTABLE where groupID=? and menuid <> '0'"
				+ " order by  "
				+ " \"firstset\",\"secondset\",\"thirdset\",\"fourthset\",\"fifthset\""
				+ " ,1";
		//System.out.println("query is:" + query);

		DBConnection con = new DBConnection();

		try {
			con.openConnection();
			PreparedStatement ps = con.setPrepStatement(query);
			ps.setString(1,group);
			ResultSet rs=ps.executeQuery();
			
			//ResultSet rs = con.select(query);

			while (rs.next()) {
				bean = new MenuBean();

				bean.setMenuid(rs.getString(1));
				bean
						.setParentid(rs.getString(2) != null ? rs.getString(2)
								: "");
				bean.setFlag(rs.getString(3));
				bean.setScflag("0");
				bean.setUrl(rs.getString(4) != null ? rs.getString(4) : "");
				bean.setMenutext(handleNull(rs.getString(5)));
				bean.setRoot(rs.getString(6));
				bean.setLevel(rs.getString(6));
				arr.add(bean);
			}
			rs.close();
			//System.out.println("getMasterMenuData executed");

		}

		catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.closeConnection();
		}

		return arr;
	}
	
	
public ArrayList<CommonBean> getRefDashBoardDetails(String loginId){
	
	
	String strSQL1 ="select statecode from mstrolestate where roleid ="+loginId+"";
	ArrayList<CommonBean> cm =(new CommonDAO()).getSQLResult(strSQL1, 1);
	String stForState = "";
	for(int i=0;i<cm.size();i++){
		CommonBean bn = (CommonBean) cm.get(i);
		 stForState =stForState + "'"+bn.getField1() +"',";
	}
	String stateCond = " and statecode in ("+stForState.substring(0, stForState.length()-1)+")";	
	//System.out.println("stateCond"+stateCond);
	
		
		String strSQL = "select NVL(subjectcodeform,'TOTAL'),sum(pending),sum(UE),sum(complied),sum(NF),sum(PC),sum(pending)+sum(UE)+sum(complied)+sum(NF)+sum(PC) total, (Select subjectname from mstsubject where subjectcode=subjectcodeform) subjectdesc from "
				+ "(select refid,NVL(subjectcode,'OTR') subjectcodeform,decode(NVL(compliance,'A'),'A',1,0) pending,decode(NVL(compliance,'A'),'B',1,0) UE, "
				+ "decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'D',1,0) NF, "
				+ "decode(NVL(compliance,'A'),'E',1,0) PC "
				+ "from trnreference "
				+ " where  tenureid>=12 and refclass in ('A','M') "+stateCond+""
				+ ") "
				+ "group by cube(subjectcodeform) order by NVL(subjectcodeform,'ZTOTAL') ";
		return getSQLResult(strSQL, 8);
		
	}


public ArrayList<CommonBean> getRefDashBoardDetails_Other(String loginId){
	
	
	
		
	String strSQL = "select NVL(subjectcodeform,'TOTAL'),sum(pending),sum(UE),sum(complied),sum(NF),sum(PC),sum(NA),sum(pending)+sum(UE)+sum(complied)+sum(NF)+sum(PC)+sum(NA) total, (Select subjectname from mstsubject where subjectcode=subjectcodeform) subjectdesc from "
			+ "(select refid,NVL(subjectcode,'OTR') subjectcodeform,decode(NVL(compliance,'A'),'A',1,0) pending,decode(NVL(compliance,'A'),'B',1,0) UE, "
			+ "decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'D',1,0) NF, "
			+ "decode(NVL(compliance,'A'),'E',1,0) PC,decode(NVL(compliance,'A'),'N',1,0) NA "
			+ "from trnreference where refid in ( "
			+ "select refid from trnmarking where MARKINGTO in ( "
			+ "select roleid from mstrole where deptcode in ( "
			+ "(select deptcode from mstdept where hod=(select roleid from mstrole where roleid = '"+loginId+"'))) "
			+ "and refid in (select refid from trnreference where  tenureid>=12 and refclass in ('A','M')) "
			+ ")) ) "
			+ "group by cube(subjectcodeform) order by NVL(subjectcodeform,'ZTOTAL') ";
		return getSQLResult(strSQL, 9);
		
	}
public ArrayList<CommonBean> getRefDashBoardDetails_OtherSubHeadWise(String loginName){
	
	
	
	
	String strSQL = "select NVL(subjectcodeform,'TOTAL'),sum(pending),sum(UE),sum(complied),sum(NF),sum(PC),sum(NA),sum(pending)+sum(UE)+sum(complied)+sum(NF)+sum(PC)+sum(NA) total, (Select subjectname from mstsubject where subjectcode=subjectcodeform) subjectdesc from "
			+ "(select refid,NVL(subjectcode,'OTR') subjectcodeform,decode(NVL(compliance,'A'),'A',1,0) pending,decode(NVL(compliance,'A'),'B',1,0) UE, "
			+ "decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'D',1,0) NF, "
			+ "decode(NVL(compliance,'A'),'E',1,0) PC ,decode(NVL(compliance,'A'),'N',1,0) NA "
			+ "from trnreference  where FORWARDTOSUBHEAD ='"+loginName+"' ) "
			+ "group by cube(subjectcodeform) order by NVL(subjectcodeform,'ZTOTAL') ";
		return getSQLResult(strSQL, 9);
		
	}

public ArrayList<CommonBean> getRefDashBoardDetails_OtherRailway(String loginName){
	
	
	
	
	String strSQL = "select NVL(subjectcodeform,'TOTAL'),sum(pending),sum(UE),sum(complied),sum(NF),sum(PC),sum(NA),sum(pending)+sum(UE)+sum(complied)+sum(NF)+sum(PC)+sum(NA) total, (Select subjectname from mstsubject where subjectcode=subjectcodeform) subjectdesc from "
			+ "(select refid,NVL(subjectcode,'OTR') subjectcodeform,decode(NVL(compliance,'A'),'A',1,0) pending,decode(NVL(compliance,'A'),'B',1,0) UE, "
			+ "decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'D',1,0) NF, "
			+ "decode(NVL(compliance,'A'),'E',1,0) PC ,decode(NVL(compliance,'A'),'N',1,0) NA "
			+ "from trnreference  where railway ='"+loginName+"' ) "
			+ "group by cube(subjectcodeform) order by NVL(subjectcodeform,'ZTOTAL') ";
		return getSQLResult(strSQL, 9);
		
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