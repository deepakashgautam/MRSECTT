package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.MstVIP;
import in.org.cris.mrsectt.rfmsupdate.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstVIPDAO_old {
	static Logger log = LogManager.getLogger(MstVIPDAO_old.class);
	
	public ArrayList<MstVIP> getSearchData(String vipID, String vipName, String stateCode, String vipStatus, String vipConstituency, String vipParty){
		
		ArrayList<MstVIP> arrList = new ArrayList<MstVIP>();
		DBConnection con =  new DBConnection();
		
		String strSQL = " SELECT VIPID,VIPNAME,STATECODE,NVL(VIPSTATUS,'N/A') VIPSTATUS,NVL(VIPCONSTITUENCY,'N/A') VIPCONSTITUENCY,NVL(VIPPARTY,'N/A') VIPPARTY, NVL(VIPADDRESS,'N/A') VIPADDRESS";
		strSQL += " FROM mrsectt.MSTVIP WHERE 1=1";
		String tempSQL = "";
		
		tempSQL += (vipID.trim().length() > 0) ? " AND VIPID = "+vipID+" " : "";
		tempSQL += (vipName.trim().length() > 0) ? " AND UPPER(VIPNAME) LIKE UPPER('%"+vipName+"%')" : "";
		tempSQL += (stateCode.trim().length() > 0) ? " AND UPPER(STATECODE) LIKE UPPER('%"+stateCode+"%')" : "";
		tempSQL += (vipStatus.trim().length() > 0) ? " AND UPPER(VIPSTATUS) LIKE UPPER('%"+vipStatus+"%')" : "";
		tempSQL += (vipConstituency.trim().length() > 0) ? " AND UPPER(VIPCONSTITUENCY) LIKE UPPER('%"+vipConstituency+"%')" : "";
		tempSQL += (vipParty.trim().length() > 0) ? " AND UPPER(VIPPARTY) LIKE UPPER('%"+vipParty+"%')" : "";
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
			ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				MstVIP bn =  new MstVIP();
				bn.setVIPID(rs.getString("VIPID"));
				bn.setVIPNAME(rs.getString("VIPNAME"));
				bn.setSTATECODE(rs.getString("STATECODE"));
				bn.setVIPSTATUS(rs.getString("VIPSTATUS"));
				bn.setVIPCONSTITUENCY(rs.getString("VIPCONSTITUENCY"));
				bn.setVIPPARTY(rs.getString("VIPPARTY"));
				bn.setVIPADDRESS(rs.getString("VIPADDRESS"));
				
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
			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement("SELECT VIPID FROM mrsectt.MSTVIP WHERE VIPID=?");
			ps.setString(1, bn.getVIPID());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				strSQL = " UPDATE mrsectt.MSTVIP SET VIPNAME = '"+bn.getVIPNAME()+"'," +
						 " STATECODE = '"+bn.getSTATECODE()+"'," +
						 " VIPSTATUS = '"+bn.getVIPSTATUS()+"'," +
						 " VIPCONSTITUENCY = '"+bn.getVIPCONSTITUENCY()+"'," +
						 " VIPPARTY = '"+bn.getVIPPARTY()+"'," +
						 " VIPADDRESS = '"+bn.getVIPADDRESS()+"'," +
						 " MOBILENO = '"+bn.getMOBILENO()+"' " +
						 " WHERE VIPID = '"+bn.getVIPID()+"'";
				
				//after adding other jndi
				strSQL1 = " UPDATE mosgsectt.MSTVIP SET VIPNAME = '"+bn.getVIPNAME()+"'," +
						 " STATECODE = '"+bn.getSTATECODE()+"'," +
						 " VIPSTATUS = '"+bn.getVIPSTATUS()+"'," +
						 " VIPCONSTITUENCY = '"+bn.getVIPCONSTITUENCY()+"'," +
						 " VIPPARTY = '"+bn.getVIPPARTY()+"'," +
						 " VIPADDRESS = '"+bn.getVIPADDRESS()+"'," +
						 " MOBILENO = '"+bn.getMOBILENO()+"' " +
						 " WHERE VIPID = '"+bn.getVIPID()+"'";
				
				strSQL2 = " UPDATE mosrsectt.MSTVIP SET VIPNAME = '"+bn.getVIPNAME()+"'," +
						 " STATECODE = '"+bn.getSTATECODE()+"'," +
						 " VIPSTATUS = '"+bn.getVIPSTATUS()+"'," +
						 " VIPCONSTITUENCY = '"+bn.getVIPCONSTITUENCY()+"'," +
						 " VIPPARTY = '"+bn.getVIPPARTY()+"'," +
						 " VIPADDRESS = '"+bn.getVIPADDRESS()+"'," +
						 " MOBILENO = '"+bn.getMOBILENO()+"' " +
						 " WHERE VIPID = '"+bn.getVIPID()+"'";
				con.update(strSQL);
				con.update(strSQL1);
				con.update(strSQL2);
				isDataSaved = "GRNSave operation successful for VIP ID : " + bn.getVIPID();
			}
			else
			{
				
				
				//PreparedStatement ps1 = null;
				//ps1 = con.setPrepStatement("SELECT NVL(MAX(A.VIPID), 0)+ 1 VIPID FROM mrsectt.MSTVIP A");
				//ps1.setString(1, bn.getVIPID());
				
				strSQL = "(SELECT NVL(MAX(A.VIPID), 0)+ 1 VIPID FROM mrsectt.MSTVIP A)";
				ResultSet rs1 =  con.select(strSQL);
				
				if(rs1.next()) {
				bn.setVIPID(rs1.getString("VIPID")); 
				}
				//System.out.println("VIPID===" + bn.getVIPID());
				
				//System.out.println("VIPID  -- " + bn.getVIPID());
				//bn.setVIPID(CommonDAO.getNextId(con, "VIPID", "MSTVIP" ));
				strSQL = " INSERT INTO mrsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO)" +
						 " VALUES("+bn.getVIPID()+",'"+bn.getVIPNAME()+"','"+bn.getSTATECODE()+"','"+bn.getVIPSTATUS()+"','"+bn.getVIPCONSTITUENCY()+"','"+bn.getVIPPARTY()+"','"+bn.getVIPADDRESS()+"','"+bn.getMOBILENO()+"')";
			    con.update(strSQL);
			    strSQL1 = " INSERT INTO mosrsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO)" +
						 " VALUES("+bn.getVIPID()+",'"+bn.getVIPNAME()+"','"+bn.getSTATECODE()+"','"+bn.getVIPSTATUS()+"','"+bn.getVIPCONSTITUENCY()+"','"+bn.getVIPPARTY()+"','"+bn.getVIPADDRESS()+"','"+bn.getMOBILENO()+"')";
			    con.update(strSQL1);
			    strSQL2 = " INSERT INTO mosgsectt.MSTVIP(VIPID,VIPNAME,STATECODE,VIPSTATUS,VIPCONSTITUENCY,VIPPARTY,VIPADDRESS,MOBILENO)" +
						 " VALUES("+bn.getVIPID()+",'"+bn.getVIPNAME()+"','"+bn.getSTATECODE()+"','"+bn.getVIPSTATUS()+"','"+bn.getVIPCONSTITUENCY()+"','"+bn.getVIPPARTY()+"','"+bn.getVIPADDRESS()+"','"+bn.getMOBILENO()+"')";
			    con.update(strSQL2);
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

			String strSQL = " SELECT VIPID,VIPNAME,STATECODE,NVL(VIPSTATUS,'N/A') VIPSTATUS,NVL(VIPCONSTITUENCY,'N/A') VIPCONSTITUENCY,NVL(VIPPARTY,'N/A') VIPPARTY, NVL(VIPADDRESS,'N/A') VIPADDRESS,VIPPARTY,MOBILENO FROM mrsectt.MSTVIP" +
					        " WHERE VIPID = "+vipid+" ORDER BY VIPID";
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
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
			}
			rs.close();

		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}

		return vipBean;

	}
}