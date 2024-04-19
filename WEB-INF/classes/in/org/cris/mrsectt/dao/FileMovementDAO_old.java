package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;

import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class FileMovementDAO_old {
	
	
	static Logger log = LogManager.getLogger(FileMovementDAO_old.class);
	
	
	
	public ArrayList<CommonBean> getFileMovement(String refno,String tenure) {
		//System.out.println("+++++++++++++++++++++"+tenure);
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection con =  new DBConnection();
		try {

			//String strSQL = " SELECT EOFFICEREFNO, SEQNO, FROMID, TOID,FROMNAME,TO_CHAR(MOVEDATE,'DD/MM/YYY') MOVEDATE,TOID,TONAME,FILENO FROM TRNREFMOVEMENT WHERE EOFFICEREFNO =(SELECT EOFFICEREFNO FROM TRNREFERENCE WHERE REFNO = '" + refno + "') ";
		String strSQL =	" SELECT EOFFICERECIEPTNO, SEQNO, FROMID, TOID,FROMNAME,TO_CHAR(MOVEDATE,'DD/MM/YYYY hh24:mi') MOVEDATE,TOID,TONAME,FILENO " + 
			" FROM TRNREFMOVEMENT A WHERE EOFFICERECIEPTNO =(SELECT EOFFICERECEIPTNO FROM TRNREFERENCE WHERE REFNO='"+refno+"' and tenureid="+tenure+") " +
			/*" UNION "+
			" SELECT EOFFICEFILENO, SEQNO, FROMID, TOID,FROMNAME,TO_CHAR(MOVEDATE,'DD/MM/YYYY') MOVEDATE,TOID,TONAME,'' "+
			" FROM TRNFILEMOVEMENT A WHERE EOFFICEFILENO in (SELECT FILENO FROM TRNREFMOVEMENT " + 
			" where EOFFICERECIEPTNO =(SELECT EOFFICERECIEPTNO FROM TRNREFERENCE WHERE REFNO='"+refno+"')) " +*/
			" order by MOVEDATE";			
		
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				
				CommonBean cb = new CommonBean();
				
				cb.setField1(rs.getString("EOFFICERECIEPTNO"));
				cb.setField2(rs.getString("SEQNO"));
				cb.setField3(rs.getString("FROMID"));
				
				cb.setField4(rs.getString("FROMNAME"));
				cb.setField5(rs.getString("MOVEDATE"));
				cb.setField6(rs.getString("TOID"));
				cb.setField7(rs.getString("TONAME"));
				cb.setField8(rs.getString("FILENO"));
				
				arrList.add(cb);
			}
			rs.close();

		} catch (SQLException e) {
			log.error(e);
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}

		return arrList;

	}
}