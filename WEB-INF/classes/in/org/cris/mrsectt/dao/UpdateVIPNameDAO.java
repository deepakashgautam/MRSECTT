package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstClass;
import in.org.cris.mrsectt.Beans.UpdateVIPName;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class UpdateVIPNameDAO {
	static Logger log = LogManager.getLogger(MstSubjectDAO.class);
	
	public String saveSubject(String[] arrOLDNAME, String[] arrOLDSTATUS, String[] arrOLDSTATE, String[] arrNEWNAME, 
			String[] arrNEWSTATUS, String[] arrNEWSTATE,String[] arrOLDPARTY,String[] arrNEWPARTY
			,String[] arrVIPID,String[] arrVIPADDRESS)
	{
		DBConnection con = new DBConnection();
		String strSQL = "";
		String isDataSaved = "";
		try
		{
			con.openConnection();
			//System.out.println("Rohit");

			for (int i = 0; i < arrOLDNAME.length; i++) {
				String tmpCon = "";
				String tmpCon2 = "";
				ArrayList<String> params = new ArrayList<String>();
				params.add("");
				
				if(arrOLDNAME[i].length() > 0 && arrNEWNAME[i].length() > 0)
				{
					//System.out.println("arrOLDNAME : "+ arrOLDNAME[i] + " ^ arrNEWNAME : "+ arrNEWNAME[i]);
					//System.out.println("arrOLDSTATUS : "+ arrOLDSTATUS[i] + " ^ arrNEWSTATUS : "+ arrNEWSTATUS[i]);
					//System.out.println("arrNEWPARTY[i] : "+ arrNEWPARTY[i] + " ^ arrNEWSTATE : "+ arrNEWSTATE[i]);
					
					/*tmpCon += arrOLDNAME[i].length()>0? " AND UPPER(REFERENCENAME) = UPPER('"+arrOLDNAME[i]+"')": "";
					tmpCon += arrOLDSTATUS[i].length()>0? " AND UPPER(VIPSTATUS) = UPPER('"+arrOLDSTATUS[i]+"')": "";
					tmpCon += arrOLDPARTY[i].length()>0? " AND UPPER(VIPPARTY) = UPPER('"+arrOLDPARTY[i]+"')": "";
					tmpCon += arrOLDSTATE[i].length()>0? " AND UPPER(STATECODE) = UPPER('"+arrOLDSTATE[i]+"')": "";*/
				//	tmpCon += " AND incomingdate<=to_date('14/12/2014','dd/mm/yyyy') ";
					
					params.add(arrNEWNAME[i]);
					params.add(arrNEWSTATUS[i]);
					params.add(arrNEWSTATE[i]);
					params.add(arrNEWPARTY[i]);
					params.add(arrVIPID[i]);
					params.add(arrVIPADDRESS[i]);
					
					if(arrOLDNAME[i].length()>0)
					{
						tmpCon +=" AND UPPER(REFERENCENAME) = UPPER(?)";
						params.add(arrOLDNAME[i]);
					}
					if(arrOLDSTATUS[i].length()>0)
					{
						tmpCon +=" AND UPPER(VIPSTATUS) = UPPER(?)";
						params.add(arrOLDSTATUS[i]);
					}
					if(arrOLDPARTY[i].length()>0)
					{
						tmpCon +=" AND UPPER(VIPPARTY) = UPPER(?)";
						params.add(arrOLDPARTY[i]);
					}
					if(arrOLDSTATE[i].length()>0)
					{
						tmpCon +=" AND UPPER(STATECODE) = UPPER(?)";
						params.add(arrOLDSTATE[i]);
					}
			  		
//		TRNREFERENCE -------------------------------------------------------------------------------------------------------
					/*strSQL = " UPDATE TRNREFERENCE SET REFERENCENAME = '"+arrNEWNAME[i]+"'," +
							 " VIPSTATUS = '"+arrNEWSTATUS[i]+"'," +
							 " STATECODE = '"+arrNEWSTATE[i]+"'" +
							 " ,VIPPARTY = '"+arrNEWPARTY[i]+"'" +
							 " ,ADDVIPID = '"+arrVIPID[i]+"'" +
							 " ,ADDVIPTYPE = '"+arrVIPADDRESS[i]+"'" +
							 " WHERE 1=1 "+tmpCon+"";
					log.debug(strSQL);
					con.update(strSQL);*/
					
					strSQL = " UPDATE TRNREFERENCE SET REFERENCENAME = ?," +
							 " VIPSTATUS = ?," +
							 " STATECODE = ? " +
							 " ,VIPPARTY = ? " +
							 " ,ADDVIPID = ? " +
							 " ,ADDVIPTYPE = ?" +
							 " WHERE 1=1 "+tmpCon+"";
					PreparedStatement ps=con.setPrepStatement(strSQL);
					for(int k=1;k<params.size();k++){
			    		ps.setString(k, params.get(k));
			    		//System.out.println("k :"+k+" params "+params.get(k));	
			    	}
					int row=ps.executeUpdate();
					//System.out.println("saveSubject::TOTAL ROWS UPDATED:"+ row);	
					
					
					/*tmpCon2 += arrOLDNAME[i].length()>0? " AND UPPER(VIPNAME) = UPPER('"+arrOLDNAME[i]+"')": "";
					tmpCon2 += arrOLDSTATUS[i].length()>0? " AND UPPER(VIPSTATUS) = UPPER('"+arrOLDSTATUS[i]+"')": "";
					tmpCon2 += arrOLDPARTY[i].length()>0? " AND UPPER(VIPPARTY) = UPPER('"+arrOLDPARTY[i]+"')": "";
					tmpCon2 += arrOLDSTATE[i].length()>0? " AND UPPER(STATECODE) = UPPER('"+arrOLDSTATE[i]+"')": "";*/
//		FINALLY DELETE OLD NAME FROM VIP MASTER ---------------------------------------------------------------------------
					/* strSQL = " DELETE FROM MSTVIP" +
							 " WHERE 1=1 "+tmpCon2+"";
					log.debug(strSQL);
					con.update(strSQL); */
					con.commit();
				//	tmpCon2 += arrOLDNAME[i].length()>0? "SELECT VIPNAME FROM MSTVIP WHERE VIPID = '"+arrNEWNAME[i]+"'": "SELECT VIPNAME FROM MSTVIP";
				//	tmpCon2 += arrOLDSTATUS[i].length()>0? "": "";
				}
				else if(arrNEWNAME[i].length() == 0 && arrOLDNAME[i].length() > 0)
				{
					log.debug("arrOROLENAME : "+ arrOLDNAME[i] + " ^ arrOROLEID : "+ arrNEWNAME[i]);
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
			con.closeConnection();
		}
		return isDataSaved;
	}

	public ArrayList<UpdateVIPName> getData(String REFERENCENAME) {
		DBConnection dbCon = new DBConnection();
		ArrayList<UpdateVIPName> arr = new ArrayList<UpdateVIPName>();
		try {
			/*String strSQL = "SELECT REFERENCENAME, VIPSTATUS, STATECODE FROM TRNREFERENCE WHERE REFERENCENAME LIKE '%"+REFERENCENAME+"%'";
			log.debug(strSQL);*/
			
			dbCon.openConnection();
			String strSQL = "SELECT REFERENCENAME, VIPSTATUS, STATECODE FROM TRNREFERENCE WHERE REFERENCENAME LIKE ?";
			PreparedStatement ps=dbCon.setPrepStatement(strSQL);
			ps.setString(1, "%"+REFERENCENAME+"%");
			ResultSet rs=ps.executeQuery();
			//ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				UpdateVIPName bean = new UpdateVIPName();

				bean.setVIPNAME(rs.getString("REFERENCENAME"));
				bean.setSTATUS(rs.getString("VIPSTATUS"));
				bean.setSTATE(rs.getString("STATECODE"));
				arr.add(bean);
			}
			rs.close();
			//System.out.println("getData executed");
			
		} catch (Exception e) {
			e.printStackTrace();
			//result = "0~" + e.getMessage();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}
}