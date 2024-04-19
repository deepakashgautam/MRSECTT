package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import java.util.ArrayList;

public class UploadAttachmentFileDAO_old {
	String strSQL = "";
	String condSQL = "";
	DBConnection con = new DBConnection();

public ArrayList<CommonBean> getData(String filenofrom, String filenoto, String datefrom, String dateto, String roleId, String isConf) {
	
		
			
			
			String fileCountFrom = filenofrom;
			String fileCountTo = filenoto;
			//condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			
			strSQL = " SELECT FMID, FILENO, TO_CHAR(REGISTRATIONDATEDES,'DD/MM/YYYY') REGISTRATIONDATEDES, " +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=TRNFILEHDR.RECEIVEDFROM) RECEIVEDFROM, SUBJECT" +
					 " FROM TRNFILEHDR WHERE ROLEIDDES = '"+roleId+"' " +
					 " AND FILECOUNTERDES BETWEEN '"+fileCountFrom+"' AND '"+fileCountTo+"' "+condSQL+"" +
					 " AND TO_DATE(TO_CHAR(REGISTRATIONDATEDES,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					 " ORDER BY FMID DESC";
		
			return (new CommonDAO()).getSQLResult(strSQL, 5);
		}
	}