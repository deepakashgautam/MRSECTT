package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstSubject;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstSubjectDAO {
	static Logger log = LogManager.getLogger(MstSubjectDAO.class);
	
	public ArrayList<MstSubject> getSearchData(String subjectCode, String subjectName, String subjectDept){
		
		ArrayList<MstSubject> arrList = new ArrayList<MstSubject>();
		DBConnection con =  new DBConnection();
		
		String strSQL = " SELECT SUBJECTCODE, SUBJECTNAME, (SELECT DEPTNAME FROM MSTDEPT WHERE DEPTCODE=SUBJECTDEPT) SUBJECTDEPT, decode(SUBJECTTYPE,'R','Reference','F','File') SUBJECTTYPE";
		strSQL += " FROM MSTSUBJECT  WHERE 1=1";

		String tempSQL = "";
		tempSQL += subjectCode.trim().length() > 0 ? " AND UPPER(SUBJECTCODE) = UPPER('"+subjectCode+"')" : "";
		tempSQL += subjectName.trim().length() > 0 ? " AND UPPER(SUBJECTNAME) LIKE UPPER('%"+subjectName+"%')" : "";
		tempSQL += subjectDept.trim().length() > 0 ? " AND UPPER(SUBJECTDEPT) = (SELECT DEPTCODE FROM MSTDEPT WHERE UPPER(DEPTNAME) = UPPER('"+subjectDept+"'))" : "";
		strSQL = strSQL+tempSQL;
		strSQL += " ORDER BY SUBJECTCODE";
		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				MstSubject bn =  new MstSubject();
				bn.setSUBJECTCODE(rs.getString("SUBJECTCODE"));
				bn.setSUBJECTNAME(rs.getString("SUBJECTNAME"));
				bn.setSUBJECTDEPT(rs.getString("SUBJECTDEPT"));
				bn.setSUBJECTTYPE(rs.getString("SUBJECTTYPE"));
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
	
	public String saveSubject(String[] arrRSUBCODE, String[] arrRSUBNAME, String DEPTCODE, String[] arrRISBUDGET, String[] arrRISNEW, String[] arrRISEDIT,
							  String[] arrFSUBCODE, String[] arrFSUBNAME, String[] arrFISBUDGET, String[] arrFISNEW, String[] arrFISEDIT)
	{
		DBConnection con = new DBConnection();
		String strSQL = "";
		String isDataSaved = "";
		try
		{
			con.openConnection();
			for (int i = 1; i < arrRSUBCODE.length; i++) {
				if(arrRISNEW[i].equalsIgnoreCase("Y"))
				{
					strSQL = " INSERT INTO MSTSUBJECT(SUBJECTCODE,SUBJECTNAME,SUBJECTDEPT,SUBJECTTYPE,ISBUDGET)" +
							 " VALUES(UPPER('"+arrRSUBCODE[i]+"'),UPPER('"+arrRSUBNAME[i]+"'),'"+DEPTCODE+"','R',UPPER('"+arrRISBUDGET[i]+"'))";
					log.debug(strSQL);
					con.update(strSQL);
				}
				else if(arrRISNEW[i].equalsIgnoreCase("N"))
				{
					if(arrRISEDIT[i].equalsIgnoreCase("E"))
					{
						strSQL = " UPDATE MSTSUBJECT SET SUBJECTNAME = UPPER('"+arrRSUBNAME[i]+"'), SUBJECTDEPT = '"+DEPTCODE+"'," +
								 " ISBUDGET = UPPER('"+arrRISBUDGET[i]+"')" +
								 " WHERE SUBJECTCODE = UPPER('"+arrRSUBCODE[i]+"')";
						log.debug(strSQL);
						con.update(strSQL);						
					}
				}
			}
			for (int i = 1; i < arrFSUBCODE.length; i++) {
				if(arrFISNEW[i].equalsIgnoreCase("Y"))
				{
					strSQL = " INSERT INTO MSTSUBJECT(SUBJECTCODE,SUBJECTNAME,SUBJECTDEPT,SUBJECTTYPE,ISBUDGET)" +
					" VALUES(UPPER('"+arrFSUBCODE[i]+"'),UPPER('"+arrFSUBNAME[i]+"'),'"+DEPTCODE+"','F',UPPER('"+arrFISBUDGET[i]+"'))";
					log.debug(strSQL);
					con.update(strSQL);
				}
				else if(arrFISNEW[i].equalsIgnoreCase("N"))
				{
					if(arrFISEDIT[i].equalsIgnoreCase("E"))
					{
						strSQL = " UPDATE MSTSUBJECT SET SUBJECTNAME = UPPER('"+arrFSUBNAME[i]+"'), SUBJECTDEPT = '"+DEPTCODE+"'," +
								 " ISBUDGET = UPPER('"+arrFISBUDGET[i]+"')" +
								 " WHERE SUBJECTCODE = UPPER('"+arrFSUBCODE[i]+"')";
						log.debug(strSQL);
						con.update(strSQL);						
					}
				}
			}
			isDataSaved = "GRNSave operation successfull...";
		} catch (SQLException e) {
			con.rollback();
			isDataSaved = "REDSave Operation failuare...";
			e.printStackTrace();
		} finally {
			con.commit();
			con.closeConnection();
		}
		return isDataSaved;
	}


	public MstSubject getSubjectData(String subjectcode) {
		MstSubject subjectBean = new MstSubject();

		DBConnection con =  new DBConnection();
		try {

			String strSQL = " SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT WHERE SUBJECTCODE = '"
					+ subjectcode + "'" + " ORDER BY SUBJECTCODE ";

			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				subjectBean = new MstSubject();
				subjectBean.setSUBJECTCODE(rs.getString("SUBJECTCODE"));
				subjectBean.setSUBJECTNAME(rs.getString("SUBJECTNAME"));
				subjectBean.setSUBJECTDEPT(rs.getString("SUBJECTDEPT"));
			}
			rs.close();

		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}

		return subjectBean;

	}
/*
	public MstSubject getSubjectDataOnload() {
		MstSubject subjectBean = new MstSubject();
		ArrayList<CommonBean> commonarr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
			String strSQL1 = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT";
			log.debug(strSQL1);
			con.openConnection();
			ResultSet rs1 = con.select(strSQL1);
			while (rs1.next()) {
				CommonBean bn = new CommonBean();
				bn.setField1(StringFormat.nullString(rs1.getString("REFCLASS")));
				bn.setField2(StringFormat.nullString(rs1.getString("INOUT")));
				bn.setField3(StringFormat.nullString(rs1.getString("REFCOUNTER")));
				bn.setField4(StringFormat.nullString(rs1.getString("CLASSDESCRIPTION")));
				commonarr.add(bn);
			}
			rs1.close();
			subjectBean.setArr(commonarr);
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return subjectBean;
	}*/
}