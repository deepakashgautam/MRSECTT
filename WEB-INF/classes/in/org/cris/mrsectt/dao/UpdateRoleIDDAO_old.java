package in.org.cris.mrsectt.dao;

import java.sql.SQLException;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class UpdateRoleIDDAO_old {
	static Logger log = LogManager.getLogger(MstSubjectDAO.class);
	
	public String saveSubject(String[] arrOROLEID, String[] arrOROLENAME, String[] arrNROLEID, String[] arrNROLENAME)
	{
		DBConnection con = new DBConnection();
		String strSQL = "";
		String isDataSaved = "";
		try
		{
			con.openConnection();
			for (int i = 0; i < arrOROLEID.length; i++) {
				if(arrNROLENAME[i].length() > 0 && arrOROLENAME[i].length() > 0)
				{
					//System.out.println("arrNROLENAME : "+ arrNROLENAME[i] + " ^ arrNROLEID : "+ arrNROLEID[i]);
//		TRNREFERENCE -------------------------------------------------------------------------------------------------------
					strSQL = " UPDATE TRNREFERENCE SET ROLEID = '"+arrNROLEID[i]+"' WHERE ROLEID = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNREFERENCE SET ACKBY = '"+arrNROLEID[i]+"' WHERE ACKBY = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNREFERENCE SET SIGNEDBY = '"+arrNROLEID[i]+"' WHERE SIGNEDBY = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNREFERENCE SET IMARKINGTO = '"+arrNROLEID[i]+"' WHERE IMARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNREFERENCE SET DMARKINGTO = '"+arrNROLEID[i]+"' WHERE DMARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();
					
//		TRNMARKING -------------------------------------------------------------------------------------------------------
					con.openConnection();
					strSQL = " UPDATE TRNMARKING SET MARKINGFROM = '"+arrNROLEID[i]+"' WHERE MARKINGFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNMARKING SET MARKINGTO = '"+arrNROLEID[i]+"' WHERE MARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();

//		TRNREMINDER -------------------------------------------------------------------------------------------------------
					con.openConnection();
					strSQL = " UPDATE TRNREMINDER SET REMINDERFROM = '"+arrNROLEID[i]+"' WHERE REMINDERFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNREMINDER SET REMINDERTO = '"+arrNROLEID[i]+"' WHERE REMINDERTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNREMINDER SET SIGNEDBY = '"+arrNROLEID[i]+"' WHERE SIGNEDBY = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();

//		TRNFILEHDR -------------------------------------------------------------------------------------------------------
					con.openConnection();
					strSQL = " UPDATE TRNFILEHDR SET DESTINATIONMARKING = '"+arrNROLEID[i]+"' WHERE DESTINATIONMARKING = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNFILEHDR SET RECEIVEDFROM = '"+arrNROLEID[i]+"' WHERE RECEIVEDFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNFILEHDR SET ROLEIDDES = '"+arrNROLEID[i]+"' WHERE ROLEIDDES = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();

//		TRNFILEINTMARKING ------------------------------------------------------------------------------------------------------
					con.openConnection();
					strSQL = " UPDATE TRNFILEINTMARKING SET MARKINGTO = '"+arrNROLEID[i]+"' WHERE MARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNFILEINTMARKING SET MARKINGFROM = '"+arrNROLEID[i]+"' WHERE MARKINGFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();

//		TRNFILEMARKING ------------------------------------------------------------------------------------------------------
					con.openConnection();
					strSQL = " UPDATE TRNFILEMARKING SET MARKINGFROM = '"+arrNROLEID[i]+"' WHERE MARKINGFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					
					strSQL = " UPDATE TRNFILEMARKING SET MARKINGTO = '"+arrNROLEID[i]+"' WHERE MARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();

//		MSTLOGIN ------------------------------------------------------------------------------------------------------
					con.openConnection();
					strSQL = " UPDATE MSTLOGIN SET LOGINASROLEID = '"+arrNROLEID[i]+"' WHERE LOGINASROLEID = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();

//		MSTTENURE ------------------------------------------------------------------------------------------------------
					con.openConnection();
					strSQL = " UPDATE MSTTENURE SET ROLEID = '"+arrNROLEID[i]+"' WHERE ROLEID = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();

//		MSTROLEDEPT ------------------------------------------------------------------------------------------------------
		/*			con.openConnection();
					strSQL = " UPDATE MSTROLEDEPT SET ROLEID = '"+arrNROLEID[i]+"' WHERE ROLEID = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();
		*/
//		FINALLY DELETE OLD ROLE ID FROM ROLE MASTER ---------------------------------------------------------------------------
					con.openConnection();					
					strSQL = " DELETE FROM MSTROLE WHERE ROLEID = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);
					con.commit();
					con.closeConnection();
				}
				else if(arrNROLENAME[i].length() == 0 && arrOROLENAME[i].length() > 0)
				{
					log.debug("arrOROLENAME : "+ arrOROLENAME[i] + " ^ arrOROLEID : "+ arrOROLEID[i]);
				}
			}
		isDataSaved = "GRNSave operation successfull...";
		} catch (SQLException e) {
			con.rollback();
			isDataSaved = "REDSave Operation failuare...";
			e.printStackTrace();
		} finally {
			//System.out.println("Mission Accomplished");
//			con.commit();
//			con.closeConnection();
		}
		return isDataSaved;
	}
}