package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstVIP;
import in.org.cris.mrsectt.rfmsupdate.db.DBConnection;
import in.org.cris.mrsectt.dao.CommonDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstVIPDAO {
	static Logger log = LogManager.getLogger(MstVIPDAO.class);
	
	public ArrayList<MstVIP> getSearchData(String vipID, String vipName, String stateCode, String vipStatus, String vipConstituency, String vipParty){
		
		ArrayList<MstVIP> arrList = new ArrayList<MstVIP>();
		DBConnection con =  new DBConnection();
		
		ArrayList<String> params = new ArrayList<String>();
		
		params.add("");
		
		String strSQL = " SELECT VIPID,VIPNAME,STATECODE,NVL(VIPSTATUS,'N/A') VIPSTATUS,NVL(VIPCONSTITUENCY,'N/A') VIPCONSTITUENCY,NVL(VIPPARTY,'N/A') VIPPARTY, NVL(VIPADDRESS,'N/A') VIPADDRESS, CONSTID";
		strSQL += " FROM mrsectt.MSTVIP WHERE 1=1";
		String tempSQL = "";
		
		/*tempSQL += (vipID.trim().length() > 0) ? " AND VIPID = "+vipID+" " : "";
		tempSQL += (vipName.trim().length() > 0) ? " AND UPPER(VIPNAME) LIKE UPPER('%"+vipName+"%')" : "";
		tempSQL += (stateCode.trim().length() > 0) ? " AND UPPER(STATECODE) LIKE UPPER('%"+stateCode+"%')" : "";
		tempSQL += (vipStatus.trim().length() > 0) ? " AND UPPER(VIPSTATUS) LIKE UPPER('%"+vipStatus+"%')" : "";
		tempSQL += (vipConstituency.trim().length() > 0) ? " AND UPPER(VIPCONSTITUENCY) LIKE UPPER('%"+vipConstituency+"%')" : "";
		tempSQL += (vipParty.trim().length() > 0) ? " AND UPPER(VIPPARTY) LIKE UPPER('%"+vipParty+"%')" : "";
		strSQL = strSQL+tempSQL;
		strSQL += " ORDER BY VIPNAME";*/
		
		if(vipID.trim().length() > 0)
		{
			tempSQL +=" AND VIPID = ? " ;
			params.add(vipID);
		}
		
		if(vipName.trim().length() > 0)
		{
			tempSQL +=" AND UPPER(VIPNAME) LIKE UPPER(?)" ;
			params.add("%"+vipName+"%");
		}
		
		if(stateCode.trim().length() > 0)
		{
			tempSQL +=" AND UPPER(STATECODE) LIKE UPPER(?)" ;
			params.add("%"+stateCode+"%");
		}
		if(vipStatus.trim().length() > 0)
		{
			tempSQL +=" AND UPPER(VIPSTATUS) LIKE UPPER(?)" ;
			params.add("%"+vipStatus+"%");
		}
		
		if(vipConstituency.trim().length() > 0)
		{
			tempSQL +=" AND UPPER(VIPCONSTITUENCY) LIKE UPPER(?)" ;
			params.add("%"+vipConstituency+"%");
		}
		
		if(vipParty.trim().length() > 0)
		{
			tempSQL +=" AND UPPER(VIPPARTY) LIKE UPPER(?)" ;
			params.add("%"+vipParty+"%");
		}
		strSQL = strSQL+tempSQL;
		strSQL += " ORDER BY VIPNAME";
		
		try {
			/*
			strSQL = " SELECT ROLEID, ROLENAME FROM MSTROLE " +
					" WHERE ROLEID = DECODE(NVL('"+search1+"', '-'),'-', ROLEID, '"+search1+"' )  AND UPPER(ROLENAME) LIKE UPPER('%"+search2+"%')" + 
					" ORDER BY ROLEID " ;
			*/
			log.debug(strSQL);
			con.openConnection();
			//ResultSet rs =  con.select(strSQL);
			
			PreparedStatement ps = con.setPrepStatement(strSQL);
			for(int k=1;k<params.size();k++){
	    		ps.setString(k, params.get(k));
	    		//System.out.println("k :"+k+" params "+params.get(k));	
	    	}
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				MstVIP bn =  new MstVIP();
				bn.setVIPID(rs.getString("VIPID"));
				bn.setVIPNAME(rs.getString("VIPNAME"));
				bn.setSTATECODE(rs.getString("STATECODE"));
				bn.setVIPSTATUS(rs.getString("VIPSTATUS"));
				bn.setVIPCONSTITUENCY(rs.getString("VIPCONSTITUENCY"));
				bn.setVIPPARTY(rs.getString("VIPPARTY"));
				bn.setVIPADDRESS(rs.getString("VIPADDRESS"));
				bn.setCONSTID(rs.getString("CONSTID"));
				
				arrList.add(bn);
			}
			rs.close();
			//System.out.println("getSearchData executed");
		} catch(SQLException e)		{
			log.fatal(e,e);
		}finally{
			
			con.closeConnection();
		}
		return arrList;
	}
	
	public String saveVIPID(MstVIP bn)
	{
		///MstRole bn = new MstRole();
		DBConnection con = new DBConnection();
		String strSQL = "";
		String strSQL1 = "";
		String strSQL2 = "";
		String isDataSaved = "";
		
		try
		{   
			int row;
			con.openConnection();
			PreparedStatement ps0 = null;
			ps0 = con.setPrepStatement("SELECT VIPID FROM mrsectt.MSTVIP WHERE VIPID=?");
			ps0.setString(1, bn.getVIPID());
			
			ResultSet rs = ps0.executeQuery();
			if(rs.next())
			{
				/*strSQL = " UPDATE mrsectt.MSTVIP SET VIPNAME = '"+bn.getVIPNAME()+"'," +
						 " STATECODE = '"+bn.getSTATECODE()+"'," +
						 " VIPSTATUS = '"+bn.getVIPSTATUS()+"'," +
						 " VIPCONSTITUENCY = '"+bn.getVIPCONSTITUENCY()+"'," +
						 " VIPPARTY = '"+bn.getVIPPARTY()+"'," +
						 " VIPADDRESS = '"+bn.getVIPADDRESS()+"'," +
						 " MOBILENO = '"+bn.getMOBILENO()+"' " +
						 " WHERE VIPID = '"+bn.getVIPID()+"'";*/
				
				strSQL = " UPDATE mrsectt.MSTVIP SET VIPNAME = ?," +
						 " STATECODE = ?," +
						 " VIPSTATUS = ?," +
						 " VIPCONSTITUENCY = ?," +
						 " VIPPARTY = ?," +
						 " VIPADDRESS = ?," +
						 " CONSTID = ?," +
						 " MOBILENO = ? " +
						 " WHERE VIPID = ?";
				
				PreparedStatement ps=con.setPrepStatement(strSQL);
				ps.setString(1, bn.getVIPNAME());
				ps.setString(2, bn.getSTATECODE());
				ps.setString(3, bn.getVIPSTATUS());
				ps.setString(4, bn.getVIPCONSTITUENCY());
				ps.setString(5, bn.getVIPPARTY());
				ps.setString(6, bn.getVIPADDRESS());
				ps.setString(7, bn.getCONSTID());
				ps.setString(8, bn.getMOBILENO());
				ps.setString(9, bn.getVIPID());
			    row=ps.executeUpdate();
				//System.out.println("MRSECTT::TOTAL ROWS UPDATED:"+ row);
				
				//after adding other jndi
				/*strSQL1 = " UPDATE mosgsectt.MSTVIP SET VIPNAME = '"+bn.getVIPNAME()+"'," +
						 " STATECODE = '"+bn.getSTATECODE()+"'," +
						 " VIPSTATUS = '"+bn.getVIPSTATUS()+"'," +
						 " VIPCONSTITUENCY = '"+bn.getVIPCONSTITUENCY()+"'," +
						 " VIPPARTY = '"+bn.getVIPPARTY()+"'," +
						 " VIPADDRESS = '"+bn.getVIPADDRESS()+"'," +
						 " MOBILENO = '"+bn.getMOBILENO()+"' " +
						 " WHERE VIPID = '"+bn.getVIPID()+"'";*/
				
				/*strSQL1 = " UPDATE mosgsectt.MSTVIP SET VIPNAME = ?," +
						 " STATECODE = ?," +
						 " VIPSTATUS = ?," +
						 " VIPCONSTITUENCY = ?," +
						 " VIPPARTY = ?," +
						 " VIPADDRESS = ?," +
						 " MOBILENO = ? " +
						 " WHERE VIPID = ?";
				
				PreparedStatement ps1=con.setPrepStatement(strSQL1);
				ps1.setString(1, bn.getVIPNAME());
				ps1.setString(2, bn.getSTATECODE());
				ps1.setString(3, bn.getVIPSTATUS());
				ps1.setString(4, bn.getVIPCONSTITUENCY());
				ps1.setString(5, bn.getVIPPARTY());
				ps1.setString(6, bn.getVIPADDRESS());
				ps1.setString(7, bn.getMOBILENO());
				ps1.setString(8, bn.getVIPID());
				
				row=ps1.executeUpdate();
				//System.out.println("MOSGSECTT::TOTAL ROWS UPDATED:"+ row);*/
				
				/*strSQL2 = " UPDATE mosrsectt.MSTVIP SET VIPNAME = '"+bn.getVIPNAME()+"'," +
						 " STATECODE = '"+bn.getSTATECODE()+"'," +
						 " VIPSTATUS = '"+bn.getVIPSTATUS()+"'," +
						 " VIPCONSTITUENCY = '"+bn.getVIPCONSTITUENCY()+"'," +
						 " VIPPARTY = '"+bn.getVIPPARTY()+"'," +
						 " VIPADDRESS = '"+bn.getVIPADDRESS()+"'," +
						 " MOBILENO = '"+bn.getMOBILENO()+"' " +
						 " WHERE VIPID = '"+bn.getVIPID()+"'";*/
				
				strSQL2 = " UPDATE mosrsectt.MSTVIP SET VIPNAME = ?," +
						 " STATECODE = ?," +
						 " VIPSTATUS = ?," +
						 " VIPCONSTITUENCY = ?," +
						 " VIPPARTY = ?," +
						 " VIPADDRESS = ?," +
						 " CONSTID = ?," +
						 " MOBILENO = ? " +
						 " WHERE VIPID = ?";
				
				PreparedStatement ps2=con.setPrepStatement(strSQL2);
				ps2.setString(1, bn.getVIPNAME());
				ps2.setString(2, bn.getSTATECODE());
				ps2.setString(3, bn.getVIPSTATUS());
				ps2.setString(4, bn.getVIPCONSTITUENCY());
				ps2.setString(5, bn.getVIPPARTY());
				ps2.setString(6, bn.getVIPADDRESS());
				ps2.setString(7, bn.getCONSTID());
				ps2.setString(8, bn.getMOBILENO());
				ps2.setString(9, bn.getVIPID());
				//System.out.println("query :"+strSQL2);
				row=ps2.executeUpdate();
				
				
				
				String strSQL3 = " UPDATE mosr2sectt.MSTVIP SET VIPNAME = ?," +
						 " STATECODE = ?," +
						 " VIPSTATUS = ?," +
						 " VIPCONSTITUENCY = ?," +
						 " VIPPARTY = ?," +
						 " VIPADDRESS = ?," +
						 " CONSTID = ?," +
						 " MOBILENO = ? " +
						 " WHERE VIPID = ?";
				
				PreparedStatement ps3=con.setPrepStatement(strSQL3);
				ps3.setString(1, bn.getVIPNAME());
				ps3.setString(2, bn.getSTATECODE());
				ps3.setString(3, bn.getVIPSTATUS());
				ps3.setString(4, bn.getVIPCONSTITUENCY());
				ps3.setString(5, bn.getVIPPARTY());
				ps3.setString(6, bn.getVIPADDRESS());
				ps3.setString(7, bn.getCONSTID());
				ps3.setString(8, bn.getMOBILENO());
				ps3.setString(9, bn.getVIPID());
				//3System.out.println("query :"+strSQL2);
				row=ps3.executeUpdate();
				
				
				
				String strSQL4 = " UPDATE eisectt.MSTVIP SET VIPNAME = ?," +
						 " STATECODE = ?," +
						 " VIPSTATUS = ?," +
						 " VIPCONSTITUENCY = ?," +
						 " VIPPARTY = ?," +
						 " VIPADDRESS = ?," +
						 " CONSTID = ?," +
						 " MOBILENO = ? " +
						 " WHERE VIPID = ?";
				
				
				PreparedStatement ps4=con.setPrepStatement(strSQL4);
				ps4.setString(1, bn.getVIPNAME());
				ps4.setString(2, bn.getSTATECODE());
				ps4.setString(3, bn.getVIPSTATUS());
				ps4.setString(4, bn.getVIPCONSTITUENCY());
				ps4.setString(5, bn.getVIPPARTY());
				ps4.setString(6, bn.getVIPADDRESS());
				ps4.setString(7, bn.getCONSTID());
				ps4.setString(8, bn.getMOBILENO());
				ps4.setString(9, bn.getVIPID());
				//System.out.println("query :"+strSQL2);
				row=ps4.executeUpdate();
				//System.out.println("MOSRSECTT::TOTAL ROWS UPDATED:"+ row);
				
				
				String strSQL5 = " UPDATE dotsectt.MSTVIP SET VIPNAME = ?," +
						 " STATECODE = ?," +
						 " VIPSTATUS = ?," +
						 " VIPCONSTITUENCY = ?," +
						 " VIPPARTY = ?," +
						 " VIPADDRESS = ?," +
						 " CONSTID = ?," +
						 " MOBILENO = ? " +
						 " WHERE VIPID = ?";
				
				PreparedStatement ps5=con.setPrepStatement(strSQL5);
				ps5.setString(1, bn.getVIPNAME());
				ps5.setString(2, bn.getSTATECODE());
				ps5.setString(3, bn.getVIPSTATUS());
				ps5.setString(4, bn.getVIPCONSTITUENCY());
				ps5.setString(5, bn.getVIPPARTY());
				ps5.setString(6, bn.getVIPADDRESS());
				ps5.setString(7, bn.getCONSTID());
				ps5.setString(8, bn.getMOBILENO());
				ps5.setString(9, bn.getVIPID());
				//System.out.println("query :"+strSQL2);
				row=ps5.executeUpdate();
				
				
				
				String strSQL6 = " UPDATE dopsectt.MSTVIP SET VIPNAME = ?," +
						 " STATECODE = ?," +
						 " VIPSTATUS = ?," +
						 " VIPCONSTITUENCY = ?," +
						 " VIPPARTY = ?," +
						 " VIPADDRESS = ?," +
						 " CONSTID = ?," +
						 " MOBILENO = ? " +
						 " WHERE VIPID = ?";
				
				PreparedStatement ps6=con.setPrepStatement(strSQL6);
				ps6.setString(1, bn.getVIPNAME());
				ps6.setString(2, bn.getSTATECODE());
				ps6.setString(3, bn.getVIPSTATUS());
				ps6.setString(4, bn.getVIPCONSTITUENCY());
				ps6.setString(5, bn.getVIPPARTY());
				ps6.setString(6, bn.getVIPADDRESS());
				ps6.setString(7, bn.getCONSTID());
				ps6.setString(8, bn.getMOBILENO());
				ps6.setString(9, bn.getVIPID());
				//System.out.println("query :"+strSQL2);
				row=ps6.executeUpdate();
				//con.update(strSQL);
				//con.update(strSQL1);
				//con.update(strSQL2);
				isDataSaved = "GRNSave operation successful for VIP ID : " + bn.getVIPID();
			}
			else
			{
				
				
				//PreparedStatement ps1 = null;
				//ps1 = con.setPrepStatement("SELECT NVL(MAX(A.VIPID), 0)+ 1 VIPID FROM mrsectt.MSTVIP A");
				//ps1.setString(1, bn.getVIPID());
				
				PreparedStatement ps3 = null;
				strSQL = "(SELECT NVL(MAX(A.VIPID), 0)+ 1 VIPID FROM mrsectt.MSTVIP A)";
				//ResultSet rs1 =  con.select(strSQL);
				ps3=con.setPrepStatement(strSQL);
				ResultSet rs1 = ps3.executeQuery();
				
				if(rs1.next()) {
				bn.setVIPID(rs1.getString("VIPID")); 
				}
				//System.out.println("VIPID===" + bn.getVIPID());
				
				//System.out.println("VIPID  -- " + bn.getVIPID());
				//bn.setVIPID(CommonDAO.getNextId(con, "VIPID", "MSTVIP" ));
				/*strSQL = " INSERT INTO mrsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO)" +
						 " VALUES("+bn.getVIPID()+",'"+bn.getVIPNAME()+"','"+bn.getSTATECODE()+"','"+bn.getVIPSTATUS()+"','"+bn.getVIPCONSTITUENCY()+"','"+bn.getVIPPARTY()+"','"+bn.getVIPADDRESS()+"','"+bn.getMOBILENO()+"')";
			    con.update(strSQL);*/
				strSQL = " INSERT INTO mrsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO,CONSTID)" +
						 " VALUES(?,?,?,?,?,?,?,?,?)";
			    
				ps3=con.setPrepStatement(strSQL);
				ps3.setString(1,bn.getVIPID());
				ps3.setString(2,bn.getVIPNAME());
				ps3.setString(3,bn.getSTATECODE());
				ps3.setString(4,bn.getVIPSTATUS());
				ps3.setString(5,bn.getVIPCONSTITUENCY());
				ps3.setString(6,bn.getVIPPARTY());
				ps3.setString(7,bn.getVIPADDRESS());
				ps3.setString(8,bn.getMOBILENO());
				ps3.setString(9,bn.getCONSTID());
				row=ps3.executeUpdate();
				//System.out.println("MRSECTT::TOTAL ROWS INSERTED:"+ row);
			    
				/*strSQL1 = " INSERT INTO mosrsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO)" +
						 " VALUES("+bn.getVIPID()+",'"+bn.getVIPNAME()+"','"+bn.getSTATECODE()+"','"+bn.getVIPSTATUS()+"','"+bn.getVIPCONSTITUENCY()+"','"+bn.getVIPPARTY()+"','"+bn.getVIPADDRESS()+"','"+bn.getMOBILENO()+"')";
			    con.update(strSQL1);*/
			    
				strSQL1 = " INSERT INTO mosrsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO,CONSTID)" +
						 " VALUES(?,?,?,?,?,?,?,?,?)";
			    
				ps3=con.setPrepStatement(strSQL1);
				ps3.setString(1,bn.getVIPID());
				ps3.setString(2,bn.getVIPNAME());
				ps3.setString(3,bn.getSTATECODE());
				ps3.setString(4,bn.getVIPSTATUS());
				ps3.setString(5,bn.getVIPCONSTITUENCY());
				ps3.setString(6,bn.getVIPPARTY());
				ps3.setString(7,bn.getVIPADDRESS());
				ps3.setString(8,bn.getMOBILENO());
				ps3.setString(9,bn.getCONSTID());
				row=ps3.executeUpdate();
				log.debug("MOSRSECTT::TOTAL ROWS INSERTED:"+ row);
			
			    
			    /*strSQL2 = " INSERT INTO mosgsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO)" +
						 " VALUES("+bn.getVIPID()+",'"+bn.getVIPNAME()+"','"+bn.getSTATECODE()+"','"+bn.getVIPSTATUS()+"','"+bn.getVIPCONSTITUENCY()+"','"+bn.getVIPPARTY()+"','"+bn.getVIPADDRESS()+"','"+bn.getMOBILENO()+"')";
			    con.update(strSQL2);*/
				
				strSQL2 = " INSERT INTO mosr2sectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO,CONSTID)" +
						 " VALUES(?,?,?,?,?,?,?,?,?)";
			    
				ps3=con.setPrepStatement(strSQL2);
				ps3.setString(1,bn.getVIPID());
				ps3.setString(2,bn.getVIPNAME());
				ps3.setString(3,bn.getSTATECODE());
				ps3.setString(4,bn.getVIPSTATUS());
				ps3.setString(5,bn.getVIPCONSTITUENCY());
				ps3.setString(6,bn.getVIPPARTY());
				ps3.setString(7,bn.getVIPADDRESS());
				ps3.setString(8,bn.getMOBILENO());
				ps3.setString(9,bn.getCONSTID());
				row=ps3.executeUpdate();
				log.debug("MOSR2SECTT::TOTAL ROWS INSERTED:"+ row);
			
				
				strSQL2 = " INSERT INTO eisectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO,CONSTID)" +
						 " VALUES(?,?,?,?,?,?,?,?,?)";
			    
				ps3=con.setPrepStatement(strSQL2);
				ps3.setString(1,bn.getVIPID());
				ps3.setString(2,bn.getVIPNAME());
				ps3.setString(3,bn.getSTATECODE());
				ps3.setString(4,bn.getVIPSTATUS());
				ps3.setString(5,bn.getVIPCONSTITUENCY());
				ps3.setString(6,bn.getVIPPARTY());
				ps3.setString(7,bn.getVIPADDRESS());
				ps3.setString(8,bn.getMOBILENO());
				ps3.setString(9,bn.getCONSTID());
				row=ps3.executeUpdate();
				log.debug("EISECTT::TOTAL ROWS INSERTED:"+ row);
			
				
				strSQL2 = " INSERT INTO dotsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO,CONSTID)" +
						 " VALUES(?,?,?,?,?,?,?,?,?)";
			    
				ps3=con.setPrepStatement(strSQL2);
				ps3.setString(1,bn.getVIPID());
				ps3.setString(2,bn.getVIPNAME());
				ps3.setString(3,bn.getSTATECODE());
				ps3.setString(4,bn.getVIPSTATUS());
				ps3.setString(5,bn.getVIPCONSTITUENCY());
				ps3.setString(6,bn.getVIPPARTY());
				ps3.setString(7,bn.getVIPADDRESS());
				ps3.setString(8,bn.getMOBILENO());
				ps3.setString(9,bn.getCONSTID());
				row=ps3.executeUpdate();
				log.debug("DOTSECTT::TOTAL ROWS INSERTED:"+ row);
			
				
				
				strSQL2 = " INSERT INTO dopsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO,CONSTID)" +
						 " VALUES(?,?,?,?,?,?,?,?,?)";
			    
				ps3=con.setPrepStatement(strSQL2);
				ps3.setString(1,bn.getVIPID());
				ps3.setString(2,bn.getVIPNAME());
				ps3.setString(3,bn.getSTATECODE());
				ps3.setString(4,bn.getVIPSTATUS());
				ps3.setString(5,bn.getVIPCONSTITUENCY());
				ps3.setString(6,bn.getVIPPARTY());
				ps3.setString(7,bn.getVIPADDRESS());
				ps3.setString(8,bn.getMOBILENO());
				ps3.setString(9,bn.getCONSTID());
				row=ps3.executeUpdate();
				log.debug("DOPSECTT::TOTAL ROWS INSERTED:"+ row);
			
				
				
				
			    isDataSaved = "GRNSave operation successful for VIP ID : " + bn.getVIPID();
			}
		}
		catch(SQLException e)
		{
			isDataSaved = "REDSave operation failure for VIP ID : " + bn.getVIPID() + ". ERROR--> " + e.getMessage();
			e.printStackTrace();
		}
		finally
		{
			con.commit();
			con.closeConnection();
		}
		return isDataSaved;
	}


	public MstVIP getVIPData(String vipid) {
		MstVIP vipBean = new MstVIP();

		DBConnection con =  new DBConnection();
		try {

			/*String strSQL = " SELECT VIPID,VIPNAME,STATECODE,NVL(VIPSTATUS,'N/A') VIPSTATUS,NVL(VIPCONSTITUENCY,'N/A') VIPCONSTITUENCY,NVL(VIPPARTY,'N/A') VIPPARTY, NVL(VIPADDRESS,'N/A') VIPADDRESS,VIPPARTY,MOBILENO FROM mrsectt.MSTVIP" +
					        " WHERE VIPID = "+vipid+" ORDER BY VIPID";*/
			
			String strSQL = " SELECT VIPID,VIPNAME,STATECODE,NVL(VIPSTATUS,'N/A') VIPSTATUS,NVL(VIPCONSTITUENCY,'N/A') VIPCONSTITUENCY,NVL(VIPPARTY,'N/A') VIPPARTY, NVL(VIPADDRESS,'N/A') VIPADDRESS,VIPPARTY,MOBILENO,CONSTID FROM mrsectt.MSTVIP" +
			        " WHERE VIPID = ? ORDER BY VIPID";

			log.debug(strSQL);
			con.openConnection();
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1,vipid);
			//ResultSet rs = con.select(strSQL);
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				vipBean = new MstVIP();
				vipBean.setVIPID(rs.getString("VIPID"));
				vipBean.setVIPNAME(rs.getString("VIPNAME"));
				vipBean.setSTATECODE(rs.getString("STATECODE"));
				vipBean.setVIPSTATUS(rs.getString("VIPSTATUS"));
				vipBean.setVIPCONSTITUENCY(rs.getString("VIPCONSTITUENCY"));
				vipBean.setVIPPARTY(rs.getString("VIPPARTY"));
				vipBean.setVIPADDRESS(rs.getString("VIPADDRESS"));
				vipBean.setPARTYCODE(rs.getString("VIPPARTY"));
				vipBean.setMOBILENO(rs.getString("MOBILENO"));
				vipBean.setCONSTID(rs.getString("CONSTID"));
			}
			rs.close();
			//System.out.println("getVIPData executed");

		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}

		return vipBean;

	}
	
	public int getAckPrepared(String FBOOKDATE,String TBOOKDATE,String LANGUAGE) {
		String tmpCond = "";
		DBConnection con =  new DBConnection();
		String strSQL = "";
		 Integer retval = null;
		 int ackCount = 0;
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		params.add(FBOOKDATE);
		params.add(TBOOKDATE);
		
		if(LANGUAGE.length() > 0)
		{
			tmpCond +=" AND LANGUAGE = ?";
			params.add(LANGUAGE);	
		}
		
		strSQL = " SELECT COUNT(*) FROM eisectt.CB_ACKLETTERS" +
				 " WHERE TO_DATE(TO_CHAR(CHANGEDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
				 " AND MINISTRYCODE=1 "+tmpCond+" ";
		
		try {
			  con.openConnection();
			  PreparedStatement ps = con.setPrepStatement(strSQL);
			  for(int k=1;k<params.size();k++){
	    		   ps.setString(k, params.get(k));
	    		   //System.out.println("k :"+k+" params "+params.get(k));
	    	   }
			 
			  ResultSet rs = ps.executeQuery();
		        if (rs.next())
		            retval = rs.getInt(1);
		        else
		            retval = null;
		        rs.close();
		       
			 if (retval != null)
				 {ackCount=retval.intValue();}
	       } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	      finally{
			con.closeConnection();
	     }
		 return ackCount;
	}
	
}