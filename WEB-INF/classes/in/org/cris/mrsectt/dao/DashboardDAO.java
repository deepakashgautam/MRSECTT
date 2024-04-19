package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstClass;
import in.org.cris.mrsectt.Beans.TrnBudget;
import in.org.cris.mrsectt.Beans.TrnMarking;
import in.org.cris.mrsectt.Beans.TrnReference;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import in.org.cris.mrsectt.util.MailSend;

public class DashboardDAO {
	static Logger log = LogManager.getLogger(DashboardDAO.class);

	public ArrayList<CommonBean> getTotalReference(String tid,String cond) {

		String strSQL = "";
		
		/*strSQL = " SELECT (SELECT COUNT(A.REFCLASS) FROM TRNREFERENCE A WHERE A.REFCLASS=B.REFCLASS "+cond+")," +
		"B.REFCLASS FROM MSTCLASS B WHERE B.TENUREID='"+tid+"'";*/
		
			strSQL = " SELECT COUNT(A.REFCLASS),A.REFCLASS FROM TRNREFERENCE A " +
					" WHERE 1=1 "+cond+" GROUP BY A.REFCLASS ORDER BY A.REFCLASS";
		
				//System.out.println(strSQL);
		log.debug(strSQL);
		return (new CommonDAO()).getSQLResult(strSQL, 2 );

	}
	
	public ArrayList<CommonBean> getReferenceSubjectWise(String cond) {

		String strSQL = "";
		
			strSQL = " SELECT COUNT(A.SUBJECTCODE),A.SUBJECTCODE FROM TRNREFERENCE A " +
					" WHERE A.SUBJECTCODE IS NOT NULL  "+cond+" GROUP BY A.SUBJECTCODE ORDER BY A.SUBJECTCODE";
		
			//System.out.println(strSQL);	
		log.debug(strSQL);
		return (new CommonDAO()).getSQLResult(strSQL, 2 );

	}
	
	public ArrayList<CommonBean> getReferenceStatus(String cond) {
		//ArrayList status = new ArrayList() ;
		String strSQL = "";
		/*strSQL = " SELECT DISTINCT A.REPLYTYPE FROM TRNREFERENCE A " +
		" WHERE 1=1 "+cond+" ";
		status =  (new CommonDAO()).getSQLResult(strSQL, 1 );
		for (int i = 0; i < status.size(); i++) {*/
		strSQL = " SELECT (SELECT COUNT(NVL(A.REPLYTYPE,'O')) FROM TRNREFERENCE A WHERE NVL(A.REPLYTYPE,'O')=DECODE(B.CODE,'00','O',B.CODE) "+cond+")," +
				"DECODE(B.CODE,'00','Pending with Dte.',B.SNAME) FROM MSTGEC B WHERE B.CODETYPE='8'";			
		//}
		
		//System.out.println(strSQL);	
		log.debug(strSQL);
		return (new CommonDAO()).getSQLResult(strSQL, 2 );

	}
	
	
}