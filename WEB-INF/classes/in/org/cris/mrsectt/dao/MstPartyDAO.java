package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.Beans.MstParty;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class MstPartyDAO {
	static Logger log = LogManager.getLogger(MstPartyDAO.class);
	
	public ArrayList<MstParty> getSearchData(String partycode, String name){
		ArrayList<MstParty> arr = new ArrayList<MstParty>();
		DBConnection dbcon = new DBConnection();
		ArrayList<String> params = new ArrayList<String>();
		
		params.add("");
		String strSQL= "SELECT PARTYCODE, NAME FROM MSTPARTY WHERE 1=1";
		String tempSQL = "";
		if(partycode.trim().length() > 0)
		{
			tempSQL +=" AND UPPER(PARTYCODE) LIKE UPPER(?)" ;
			params.add("%"+partycode+"%");
		}
		if(name.trim().length() > 0)
		{
			tempSQL +=" AND UPPER(NAME) LIKE UPPER(?)" ;
			params.add("%"+name+"%");
		}
		
		strSQL = strSQL+tempSQL;
		strSQL += " ORDER BY PARTYCODE";
		
		try{
		log.debug(strSQL);
		dbcon.openConnection();
		PreparedStatement pst = dbcon.setPrepStatement(strSQL);
		for(int k=1;k<params.size();k++){
    		pst.setString(k, params.get(k));
    		//System.out.println("k :"+k+" params "+params.get(k));	
    		}
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			MstParty bn = new MstParty();
			bn.setPartycode(rs.getString("PARTYCODE"));
			bn.setName(rs.getString("NAME"));
			arr.add(bn);
			
		}
		}catch(SQLException e){
			log.fatal(e,e);
		}finally{
			
			dbcon.closeConnection();
		}
		
		
		return arr;
	}
	
	public String saveVIPParty(MstParty bn){
		String result = "";
		DBConnection con = new DBConnection();
		String strSQL = "";
		String strSQL1 = "";
		String strSQL2 = "";
		String strSQL3 = "";
		String strSQL4 = "";
		String strSQL5 = "";
		String strSQL6 = "";
		
		try{
			int row;
			con.openConnection();
			PreparedStatement ps0 = null;
			ps0 = con.setPrepStatement("SELECT PARTYCODE FROM mrsectt.MSTPARTY WHERE PARTYCODE=?");
			ps0.setString(1, bn.getPartycode());
			
			ResultSet rs = ps0.executeQuery();
			if(rs.next()){
				strSQL = " UPDATE mrsectt.MSTPARTY SET NAME = ? WHERE PARTYCODE = ?";
				
				PreparedStatement ps=con.setPrepStatement(strSQL);
				ps.setString(1, bn.getName());
				ps.setString(2, bn.getPartycode());
			    row=ps.executeUpdate();
			    
			    strSQL2= " UPDATE mosrsectt.MSTPARTY SET NAME = ? WHERE PARTYCODE = ?";
				
				PreparedStatement ps2=con.setPrepStatement(strSQL2);
				ps2.setString(1, bn.getName());
				ps2.setString(2, bn.getPartycode());
			    row=ps2.executeUpdate();
			    
			    strSQL3= " UPDATE mosr2sectt.MSTPARTY SET NAME = ? WHERE PARTYCODE = ?";
				
				PreparedStatement ps3=con.setPrepStatement(strSQL3);
				ps3.setString(1, bn.getName());
				ps3.setString(2, bn.getPartycode());
			    row=ps3.executeUpdate();
			    
			    strSQL4= " UPDATE eisectt.MSTPARTY SET NAME = ? WHERE PARTYCODE = ?";
				
				PreparedStatement ps4=con.setPrepStatement(strSQL4);
				ps4.setString(1, bn.getName());
				ps4.setString(2, bn.getPartycode());
			    row=ps4.executeUpdate();
			    
			    
			    strSQL5= " UPDATE dopsectt.MSTPARTY SET NAME = ? WHERE PARTYCODE = ?";
				
				PreparedStatement ps5=con.setPrepStatement(strSQL5);
				ps5.setString(1, bn.getName());
				ps5.setString(2, bn.getPartycode());
			    row=ps5.executeUpdate();
			    
			    strSQL6= " UPDATE dotsectt.MSTPARTY SET NAME = ? WHERE PARTYCODE = ?";
				
				PreparedStatement ps6=con.setPrepStatement(strSQL6);
				ps6.setString(1, bn.getName());
				ps6.setString(2, bn.getPartycode());
			    row=ps6.executeUpdate();
			    
			    
			    result = "GRNSave operation successful for VIP Party : " + bn.getPartycode();
			}
			else{
				PreparedStatement ps3 = null;
				strSQL = " INSERT INTO mrsectt.MSTPARTY(PARTYCODE,NAME) VALUES(?,?)";
			    
				ps3=con.setPrepStatement(strSQL);
				ps3.setString(1,bn.getPartycode());
				ps3.setString(2,bn.getName());
				row=ps3.executeUpdate();
				log.debug("MRSECTT::TOTAL ROWS INSERTED:"+ row);
				
				strSQL2 = " INSERT INTO mosrsectt.MSTPARTY(PARTYCODE,NAME) VALUES(?,?)";
			    
				ps3=con.setPrepStatement(strSQL2);
				ps3.setString(1,bn.getPartycode());
				ps3.setString(2,bn.getName());
				row=ps3.executeUpdate();
				log.debug("MOSRSECTT::TOTAL ROWS INSERTED:"+ row);
	
				strSQL2 = " INSERT INTO mosr2sectt.MSTPARTY(PARTYCODE,NAME) VALUES(?,?)";
			    
				ps3=con.setPrepStatement(strSQL2);
				ps3.setString(1,bn.getPartycode());
				ps3.setString(2,bn.getName());
				row=ps3.executeUpdate();
				log.debug("MOSR2SECTT::TOTAL ROWS INSERTED:"+ row);
				
				strSQL2 = " INSERT INTO eisectt.MSTPARTY(PARTYCODE,NAME) VALUES(?,?)";
			    
				ps3=con.setPrepStatement(strSQL2);
				ps3.setString(1,bn.getPartycode());
				ps3.setString(2,bn.getName());
				row=ps3.executeUpdate();
				log.debug("EISECTT::TOTAL ROWS INSERTED:"+ row);
				
				strSQL2 = " INSERT INTO dopsectt.MSTPARTY(PARTYCODE,NAME) VALUES(?,?)";
			    
				ps3=con.setPrepStatement(strSQL2);
				ps3.setString(1,bn.getPartycode());
				ps3.setString(2,bn.getName());
				row=ps3.executeUpdate();
				log.debug("DOPSECTT::TOTAL ROWS INSERTED:"+ row);
				
				strSQL2 = " INSERT INTO dotsectt.MSTPARTY(PARTYCODE,NAME) VALUES(?,?)";
			    
				ps3=con.setPrepStatement(strSQL2);
				ps3.setString(1,bn.getPartycode());
				ps3.setString(2,bn.getName());
				row=ps3.executeUpdate();
				log.debug("DOTSECTT::TOTAL ROWS INSERTED:"+ row);
	
				result = "GRNSave operation successful for VIP Party : " + bn.getPartycode();
			}
			
		}catch(SQLException e)
		{
			result = "REDSave operation failure for VIP ID : " + bn.getPartycode() + ". ERROR--> " + e.getMessage();
			e.printStackTrace();
		}
		finally
		{
			con.commit();
			con.closeConnection();
		}
		return result;
	}
	
	public MstParty getVIPParty(String partycode) {
		MstParty partybean = new MstParty();
		//System.out.println("Party code :"+partycode);
		DBConnection con =  new DBConnection();
		try {

			String strSQL = " SELECT PARTYCODE, NAME FROM MSTPARTY WHERE PARTYCODE = ?";

			log.debug(strSQL);
			con.openConnection();
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1,partycode);
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				partybean = new MstParty();
				partybean.setName(rs.getString("NAME"));
				partybean.setPartycode(rs.getString("PARTYCODE"));
				log.debug("Party Name :"+rs.getString("NAME"));
			}
			rs.close();
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}

		return partybean;

	}
}
