package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class UpdateRoleIDDAO {
	static Logger log = LogManager.getLogger(MstSubjectDAO.class);
	
	
	public int UPDATE(String sqlstr, String arg1,String arg2,DBConnection dbcon)
	{
		PreparedStatement ps =dbcon.setPrepStatement(sqlstr) ;
		int row = 0;
		try {
			ps.setString(1,arg1);
			ps.setString(2,arg2);
			row=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return row;
	}
	
	public String saveSubject(String[] arrOROLEID, String[] arrOROLENAME, String[] arrNROLEID, String[] arrNROLENAME)
	{
		DBConnection con = new DBConnection();
		String strSQL = "";
		String isDataSaved = "";
		
		int count=0,row;
		try
		{
			con.openConnection();
			for (int i = 0; i < arrOROLEID.length; i++) {
				if(arrNROLENAME[i].length() > 0 && arrOROLENAME[i].length() > 0)
				{
					//System.out.println("arrNROLENAME : "+ arrNROLENAME[i] + " ^ arrNROLEID : "+ arrNROLEID[i]);
//		TRNREFERENCE -------------------------------------------------------------------------------------------------------
					/*strSQL = " UPDATE TRNREFERENCE SET ROLEID = '"+arrNROLEID[i]+"' WHERE ROLEID = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNREFERENCE SET ROLEID = ? WHERE ROLEID = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNREFERENCE SET ACKBY = '"+arrNROLEID[i]+"' WHERE ACKBY = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNREFERENCE SET ACKBY = ? WHERE ACKBY = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNREFERENCE SET SIGNEDBY = '"+arrNROLEID[i]+"' WHERE SIGNEDBY = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNREFERENCE SET SIGNEDBY = ? WHERE SIGNEDBY = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNREFERENCE SET IMARKINGTO = '"+arrNROLEID[i]+"' WHERE IMARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNREFERENCE SET IMARKINGTO = ? WHERE IMARKINGTO = ? ";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNREFERENCE SET DMARKINGTO = '"+arrNROLEID[i]+"' WHERE DMARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNREFERENCE SET DMARKINGTO = ? WHERE DMARKINGTO = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					con.commit();
					con.closeConnection();
					//System.out.println("saveSubject::TRNREFERENCE::TOTAL ROWS UPDATED:"+ count);
					count=0;
					
//		TRNMARKING -------------------------------------------------------------------------------------------------------
					con.openConnection();
					/*strSQL = " UPDATE TRNMARKING SET MARKINGFROM = '"+arrNROLEID[i]+"' WHERE MARKINGFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					strSQL = " UPDATE TRNMARKING SET MARKINGFROM = ? WHERE MARKINGFROM = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNMARKING SET MARKINGTO = '"+arrNROLEID[i]+"' WHERE MARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					strSQL = " UPDATE TRNMARKING SET MARKINGTO = ? WHERE MARKINGTO = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					con.commit();
					con.closeConnection();

					//System.out.println("saveSubject::TRNMARKING::TOTAL ROWS UPDATED:"+ count);
					count=0;
					

//		TRNREMINDER -------------------------------------------------------------------------------------------------------
					con.openConnection();
					/*strSQL = " UPDATE TRNREMINDER SET REMINDERFROM = '"+arrNROLEID[i]+"' WHERE REMINDERFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNREMINDER SET REMINDERFROM = ? WHERE REMINDERFROM = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNREMINDER SET REMINDERTO = '"+arrNROLEID[i]+"' WHERE REMINDERTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					strSQL = " UPDATE TRNREMINDER SET REMINDERTO = ? WHERE REMINDERTO = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNREMINDER SET SIGNEDBY = '"+arrNROLEID[i]+"' WHERE SIGNEDBY = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					strSQL = " UPDATE TRNREMINDER SET SIGNEDBY = ? WHERE SIGNEDBY = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					con.commit();
					con.closeConnection();

					//System.out.println("saveSubject::TRNREMINDER::TOTAL ROWS UPDATED:"+ count);
					count=0;
					

//		TRNFILEHDR -------------------------------------------------------------------------------------------------------
					con.openConnection();
					/*strSQL = " UPDATE TRNFILEHDR SET DESTINATIONMARKING = '"+arrNROLEID[i]+"' WHERE DESTINATIONMARKING = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNFILEHDR SET DESTINATIONMARKING = ? WHERE DESTINATIONMARKING = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNFILEHDR SET RECEIVEDFROM = '"+arrNROLEID[i]+"' WHERE RECEIVEDFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNFILEHDR SET RECEIVEDFROM = ? WHERE RECEIVEDFROM = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNFILEHDR SET ROLEIDDES = '"+arrNROLEID[i]+"' WHERE ROLEIDDES = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNFILEHDR SET ROLEIDDES = ? WHERE ROLEIDDES = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					con.commit();
					con.closeConnection();
					//System.out.println("saveSubject::TRNFILEHDR::TOTAL ROWS UPDATED:"+ count);
					count=0;

//		TRNFILEINTMARKING ------------------------------------------------------------------------------------------------------
					con.openConnection();
					/*strSQL = " UPDATE TRNFILEINTMARKING SET MARKINGTO = '"+arrNROLEID[i]+"' WHERE MARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNFILEINTMARKING SET MARKINGTO = ? WHERE MARKINGTO = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNFILEINTMARKING SET MARKINGFROM = '"+arrNROLEID[i]+"' WHERE MARKINGFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					strSQL = " UPDATE TRNFILEINTMARKING SET MARKINGFROM = ? WHERE MARKINGFROM = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					con.commit();
					con.closeConnection();

					//System.out.println("saveSubject::TRNFILEINTMARKING::TOTAL ROWS UPDATED:"+ count);
					count=0;
					

//		TRNFILEMARKING ------------------------------------------------------------------------------------------------------
					con.openConnection();
					/*strSQL = " UPDATE TRNFILEMARKING SET MARKINGFROM = '"+arrNROLEID[i]+"' WHERE MARKINGFROM = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNFILEMARKING SET MARKINGFROM = ? WHERE MARKINGFROM = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					/*strSQL = " UPDATE TRNFILEMARKING SET MARKINGTO = '"+arrNROLEID[i]+"' WHERE MARKINGTO = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNFILEMARKING SET MARKINGTO = ? WHERE MARKINGTO = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					con.commit();
					con.closeConnection();
					//System.out.println("saveSubject::TRNFILEMARKING::TOTAL ROWS UPDATED:"+ count);
					count=0;

//		MSTLOGIN ------------------------------------------------------------------------------------------------------
					con.openConnection();
					/*strSQL = " UPDATE MSTLOGIN SET LOGINASROLEID = '"+arrNROLEID[i]+"' WHERE LOGINASROLEID = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					strSQL = " UPDATE MSTLOGIN SET LOGINASROLEID = ? WHERE LOGINASROLEID = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					con.commit();
					con.closeConnection();
					//System.out.println("saveSubject::MSTLOGIN::TOTAL ROWS UPDATED:"+ count);
					count=0;

//		MSTTENURE ------------------------------------------------------------------------------------------------------
					con.openConnection();
					/*strSQL = " UPDATE MSTTENURE SET ROLEID = '"+arrNROLEID[i]+"' WHERE ROLEID = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE MSTTENURE SET ROLEID = ? WHERE ROLEID = ?";
					row=UPDATE(strSQL,arrNROLEID[i],arrOROLEID[i],con);
					count+=row;
					
					con.commit();
					con.closeConnection();
					//System.out.println("saveSubject::MSTTENURE::TOTAL ROWS UPDATED:"+ count);
					count=0;

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
					/*strSQL = " DELETE FROM MSTROLE WHERE ROLEID = '"+arrOROLEID[i]+"'";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " DELETE FROM MSTROLE WHERE ROLEID = ?";
					PreparedStatement ps =con.setPrepStatement(strSQL) ;
					ps.setString(1, arrOROLEID[i]);
					row=ps.executeUpdate();
					
					con.commit();
					con.closeConnection();
					//System.out.println("saveSubject::MSTROLE::TOTAL ROWS DELETED:"+ row);
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