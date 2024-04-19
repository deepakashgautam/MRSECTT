package in.org.cris.mrsectt.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import in.org.cris.mrsectt.Beans.GenBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class CustomReportFileMovDAO {

	public ArrayList<GenBean> getReports(String loginid) {
		String strSQL = "";
		strSQL = " SELECT REPID,REPHEADER,TO_CHAR(CHANGEDATE,'DD/MM/YYYY    HH24:MI'), '' SQL, '' PARAMS,NVL(REPTYPE,1) FROM MSTREPORTS WHERE LOGINID='"+loginid+"' AND REPTYPE = '2' ORDER BY CHANGEDATE DESC";
		//System.out.println("CustomReportDAO.getReports() :--" + strSQL);
		ArrayList<GenBean> arr = new CommonDAO().getCodeDesc(strSQL, 6);
		DBConnection con = new DBConnection();
		try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement("SELECT SQL,PARAMS FROM  MSTREPORTS  WHERE REPID=?");
				for(int i=0;i<arr.size();i++){
					
					ps.setString(1, arr.get(i).getField()[0]);
					ResultSet rs =  ps.executeQuery();
					
					if(rs.next())
					{
						
						InputStream is = null;
						Blob blobObj = null;
						
						
						
						if (rs.getBlob(1) != null) {
							blobObj = rs.getBlob(1);
							byte[] byteStr = blobObj.getBytes(1, (int) blobObj.length());
							arr.get(i).getField()[3] = (new String(byteStr));
						}
						if (rs.getBlob(2) != null) {
							blobObj = rs.getBlob(2);
							byte[] byteStr = blobObj.getBytes(1, (int) blobObj.length());
							arr.get(i).getField()[4] = (new String(byteStr));
						}
						
					}
				}
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}
	
	public String saveReport(String loginid, String params) {
		String result = "0";
		String strSQL = "";
		String repid = "";
		String repheader = ""; 
		DBConnection con = new DBConnection();
		try {

			repid = repid.trim().length()>0?repid:"";
			con.openConnection();
			params = params.split("dummyfield=&")[1];
			
			String [] param = params.split("&reportid=");
			//System.out.println(params);
			repid = param.length>1? param[1]: "";
			//System.out.println(param[0]);
			repheader = param[0].split("&repheader=").length>1? param[0].split("&repheader=")[1]:"";
			repheader = URLDecoder.decode(repheader, "utf-8");
				
			if(repid.length()==0)
			{
				ResultSet rs = con.select("SELECT NVL('"+repid+"',((NVL(MAX(REPID),0)+1) )) FROM MSTREPORTS WHERE LOGINID='"+loginid+"'");
				if(rs.next())
					repid = handleNull(rs.getString(1));
			}
			
			
			strSQL = " DELETE FROM  MSTREPORTS WHERE REPID='"+repid+"'";
			//System.out.println("CustomReportDAO.saveReport() 1:--" + strSQL);
			con.delete(strSQL);
			strSQL = " INSERT INTO  MSTREPORTS (REPID,REPHEADER,LOGINID,CHANGEDATE,PARAMS)" +
					" VALUES ('"+repid+"','"+repheader+"','"+loginid+"',SYSDATE,?)"; //param[0]
			//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
			//con.insert(strSQL);
			
			PreparedStatement ps = con.setPrepStatement(strSQL);
			InputStream is = new ByteArrayInputStream(param[0].getBytes("UTF-8"));
			ps.setBinaryStream(1, is);
			ps.executeUpdate();
			ps.close();
			result="1";

		} catch (Exception e) {
			result="0";
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}

		return repid;
	}
	
	
	
	public String updateReportSQL(String repid, String strSQL,String repType) {
		String result = "0";
		DBConnection con = new DBConnection();
		try {

			
			con.openConnection();	
			PreparedStatement ps = con.setPrepStatement("UPDATE MSTREPORTS SET SQL=?,REPTYPE='"+repType+"' WHERE REPID='"+repid+"'");
			//System.out.println("CustomReportDAO.updateReportSQL() for repid "+repid+" :--" + strSQL);
			
			
			InputStream is = new ByteArrayInputStream(strSQL.getBytes("UTF-8"));
			ps.setBinaryStream(1, is);
			ps.executeUpdate();
			ps.close();
			result="1";

		} catch (Exception e) {
			result="0";
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}

		return repid;
	}
	
	public String updateReportLayout(String repid, String Layout,String colswidth) {
		String result = "0";
		DBConnection con = new DBConnection();
		try {

			
			con.openConnection();	
			PreparedStatement ps = con.setPrepStatement("UPDATE MSTREPORTS SET LAYOUT =?  , COLSIZES =? WHERE REPID='"+repid+"'");
			ps.setString(1, Layout);
			ps.setString(2, colswidth);
			ps.executeUpdate();
			ps.close();
			result="1";

		} catch (Exception e) {
			result="0";
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}

		return repid;
	}
	
	
	
	/*public String deleteReport(String repid) {
		String result = "0";
		DBConnection con = new DBConnection();
		try {

			
			con.openConnection();	
			con.delete("DELETE FROM  MSTREPORTS WHERE REPID='"+repid+"'");			
			result="1~Deleted!!!!";

		} catch (Exception e) {
			result="0~"+e.getMessage();
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}

		return result;
	}*/
	//ps rishabh
	public String deleteReport(String repid) {
		String result = "0";
		DBConnection con = new DBConnection();
		PreparedStatement ps = null;
		String strSQL="DELETE FROM  MSTREPORTS WHERE REPID=?";
/*		try {

			
			con.openConnection();	
			con.delete("DELETE FROM  MSTREPORTS WHERE REPID='"+repid+"'");			
			result="1~Deleted!!!!";

		} catch (Exception e) {
			result="0~"+e.getMessage();
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}*/
		
		try{
			con.openConnection();
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, repid);
			ps.executeUpdate();
			result="1~Deleted!!!!";
			
		}catch(Exception e){
			result="0~"+e.getMessage();
			con.rollback();
			e.printStackTrace();}
		finally{
			con.closeConnection();
		}
		

		return result;
	}
	
	public String [] getReportSQL(String repid) {
		String result [] = {"0","","","",""};
		DBConnection con = new DBConnection();
		try {

			
			con.openConnection();	
			ResultSet rs = con.select("SELECT SQL,NVL(REPHEADER,''),NVL(LAYOUT,''),NVL(COLSIZES,'')  FROM  MSTREPORTS  WHERE REPID='"+repid+"'");
			if(rs.next())
			{
				InputStream is = null;
				Blob blobObj = null;
				
				
				result[0] = "1";
				result[1] = "";
				if (rs.getBlob(1) != null) {
					blobObj = rs.getBlob(1);
					byte[] byteStr = blobObj.getBytes(1, (int) blobObj.length());
					result[1] = (new String(byteStr));
				}
				result[2] = handleNull(rs.getString(2));
				result[3] = handleNull(rs.getString(3));
				result[4] = handleNull(rs.getString(4));
			}
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}

		return result;
	}
	
	public String generateSQL(String FinalSelection,String conditions,String repType){
		String strSQL="";		
		if("1".equals(repType)){
			strSQL = FinalSelection + " FROM TRNFILEHDR, TRNFILEMARKING WHERE 1=1 AND TRNFILEHDR.FMID = TRNFILEMARKING.FMID(+)  " + conditions;
/*			strSQL = FinalSelection + " FROM ( SELECT WORKID,(SELECT SNAME FROM MSTRLY P WHERE P.RLYCODE=A.RLYCODE) RLYNAME,RLYCODE,PLANHD,SUBHD," +
			" (SELECT P.SNAME FROM MSTWORKCLASS P WHERE P.CLASSCODE=A.CLASSCODE AND P.PLANHD=A.PLANHD AND P.SUBHD=A.SUBHD ) CLASSCODEDESC, CLASSCODE,WORKTYPE,SNAME, FNAME,DECODE(WORKTYPE,'I1','Board','I2','Board','I3','GM','I4','DRM') SANCTYPEDESC,SANCTYPE,SANCYEAR,SANCBOOK," +
			" SANCNO,OTFLAG,DECODE(OTFLAG,'1','OT','') OTFLAGDESC, STATION, SECTION,FORMATPROJID(PROJECTID) PROJECTID,(SELECT M.SNAME FROM MSTAGENCY M WHERE M.AGENCYCODE=A.AGENCYCODE AND M.RLYCODE=A.RLYCODE) AGENCYDESC,AGENCYCODE,DIVISION,STATE," +
			" DISTRICT,PROGMONTH,LOGINID,ALLOCATION,CURRCOST,DPO,EXPPREV,OUTLAY,OUTBUDGET,EXPCURR,EXPTOTAL,BALANCE,THROWFORWARD,TO_CHAR(TGTDATE1,'DD/MM/YYYY') TGTDATE1,TO_CHAR(TGTDATE2,'DD/MM/YYYY') TGTDATE2," +
			" TO_CHAR(TGTDATE3,'DD/MM/YYYY') TGTDATE3,PER1,PER2,PER3,PERCURR,FIN1,FIN2,FIN3,FINCURR,ENVCLR,LANDACQ,PLAN,ESTIMATE,TENDER,AWARD,COMPLETION,DESIGNSTATUS," +
			" REMARKS,TO_CHAR(ATGTDATE1,'DD/MM/YYYY') ATGTDATE1,TO_CHAR(ATGTDATE2,'DD/MM/YYYY') ATGTDATE2,TO_CHAR(ATGTDATE3,'DD/MM/YYYY') ATGTDATE3,APER1,APER2,APER3,APERCURR,AFIN1,AFIN2,AFIN3,AFINCURR,TO_CHAR(COMPLETIONDATE,'DD/MM/YYYY') COMPLETIONDATE,REPOFF1,REPOFF2" +
			" FROM VIEWWORKS A" +
			") WHERE 1=1 " + conditions;
*/
		} else if("2".equals(repType)){
			strSQL = FinalSelection + " FROM TRNFILEHDR,  TRNFILEMARKING WHERE 1=1 AND TRNFILEHDR.FMID = TRNFILEMARKING.FMID(+)  " + conditions;
/*			strSQL = FinalSelection + " FROM ( " +
					" SELECT WORKID,(SELECT SNAME FROM MSTRLY P WHERE P.RLYCODE=A.RLYCODE) RLYNAME,RLYCODE,PLANHD,SUBHD," +
					" (SELECT P.SNAME FROM MSTWORKCLASS P WHERE P.CLASSCODE=A.CLASSCODE AND P.PLANHD=A.PLANHD AND P.SUBHD=A.SUBHD ) CLASSCODEDESC," +
					" CLASSCODE,WORKTYPE,SNAME, FNAME,DECODE(WORKTYPE,'I1','Board','I2','Board','I3','GM','I4','DRM') SANCTYPEDESC," +
					" SANCTYPE,BUDGETYEAR, OTFLAG,DECODE(OTFLAG,'1','OT','') OTFLAGDESC,STATION,SECTION,DIVISION,STATE," +
					" DISTRICT,ALLOCATION,CURRCOST,DPO,OUTLAY,TO_CHAR(PENDINGSINCE,'DD/MM/YYYY') PENDINGSINCE," +
					" VETFLAGRLY, DECODE(VETFLAGRLY,'Y','Vetted','N','Not Vetted','-') VETFLAGRLYDESC," +
					" VETFLAGDIV, DECODE(VETFLAGDIV,'Y','Vetted','N','Not Vetted','-') VETFLAGDIVDESC, " +
					" VETFLAG, BANKDIVSTATUS," +
					" DECODE(BANKDIVSTATUS,'A','Shortlisted','M','Shortlisted','N','Not Shortlisted','-') BANKDIVSTATUSDESC," +
					" BANKRLYSTATUS," +
					" DECODE(BANKRLYSTATUS,'A','Shortlisted','M','Shortlisted','N','Not Shortlisted','-') BANKRLYSTATUSDESC," +
					" BANKSTATUS, CREATEDBY,DECODE(CREATEDBY,'D','Division','R','Railway','B','Board','-') CREATEDBYDESC, CURRENTLYWITHDESIG, CURRENTLYWITH  FROM VIEWPROPOSALS A" +
			") WHERE 1=1 " + conditions;
*/
		}
		//System.out.println("strSQLstrSQLstrSQLstrSQL"+strSQL);		
		return strSQL;
		
	}
	
	
	public String handleNull(String str)
	{
		return str != null ? str : "";
	}
	
	
}
