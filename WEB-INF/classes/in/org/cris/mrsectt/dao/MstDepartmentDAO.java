package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.MstDepartment;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstDepartmentDAO {
	static Logger log = LogManager.getLogger(MstDepartmentDAO.class);
	
	public ArrayList<MstDepartment> getSearchData(String deptCode, String deptName){
		
		ArrayList<MstDepartment> arrList = new ArrayList<MstDepartment>();
		DBConnection con =  new DBConnection();
		
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		
		String strSQL = " SELECT DEPTCODE, DEPTNAME FROM MSTDEPT WHERE 1=1";
		String tempSQL = "";
		/*tempSQL += deptCode.trim().length() > 0 ? " AND DEPTCODE = "+deptCode+" " : "";
		tempSQL += deptName.trim().length() > 0 ? " AND UPPER(DEPTNAME) LIKE UPPER('%"+deptName+"%')" : "";*/
			
		if(deptCode.trim().length() > 0 )
		{
			tempSQL +=" AND DEPTCODE = ?";
			params.add(deptCode);
		}
		
		if(deptName.trim().length() > 0 )
		{
			tempSQL +=" AND UPPER(DEPTNAME) LIKE UPPER(?)";
			params.add("%"+deptName+"%");
		}
		
		strSQL = strSQL+tempSQL;
		strSQL += " ORDER BY DEPTCODE";
		
		try {
			log.debug(strSQL);
			con.openConnection();
			PreparedStatement ps = con.setPrepStatement(strSQL);
			for(int k=1;k<params.size();k++){
	    		ps.setString(k, params.get(k));
	    		//System.out.println("k :"+k+" params "+params.get(k));	
	    	}
			
			ResultSet rs =ps.executeQuery();
			//ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				MstDepartment bn =  new MstDepartment();
				bn.setDEPTCODE(rs.getString("DEPTCODE"));
				bn.setDEPTNAME(rs.getString("DEPTNAME"));
				
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
	
	public String saveDepartmentID(MstDepartment bn)
	{
		///MstDepartment bn = new MstDepartment();
		DBConnection con = new DBConnection();
		String strSQL = "";
		String isDataSaved = "";
		try
		{
			con.openConnection();
			PreparedStatement ps = null;
			ps = con.setPrepStatement("SELECT DEPTCODE FROM MSTDEPT WHERE DEPTCODE=?");
			ps.setString(1, bn.getDEPTCODE());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				/*strSQL = " UPDATE MSTDEPT SET DEPTNAME = '"+bn.getDEPTNAME()+"' WHERE DEPTCODE = "+bn.getDEPTCODE()+" ";
				log.fatal(strSQL);
				con.update(strSQL);*/
				
				strSQL = " UPDATE MSTDEPT SET DEPTNAME = ? WHERE DEPTCODE = ? ";
				PreparedStatement ps1 =con.setPrepStatement(strSQL);
				ps1.setString(1, bn.getDEPTNAME());
				ps1.setString(2, bn.getDEPTCODE());
				int row=ps1.executeUpdate();
				//System.out.println("saveDepartmentID::TOTAL ROWS UPDATED:"+ row);	
				isDataSaved = "GRNSave operation successful for Department Code : " + bn.getDEPTCODE();
			}
			else
			{
				if (bn.getDEPTCODE().length() <= 0) {
					//bn.setDEPTCODE(CommonDAO.getNextId(con, "DEPTCODE", "MSTDEPT"));
					bn.setDEPTCODE(CommonDAO.getNextIdDept(con));
				}
				/*strSQL = " INSERT INTO MSTDEPT(DEPTCODE, DEPTNAME)" +
						 " VALUES("+bn.getDEPTCODE()+",'"+bn.getDEPTNAME()+"')";
			    con.update(strSQL);*/
				
				strSQL = " INSERT INTO MSTDEPT(DEPTCODE, DEPTNAME)" +
						 " VALUES(?,?)";
			    PreparedStatement ps2=con.setPrepStatement(strSQL);
			    ps2.setString(1, bn.getDEPTCODE());
			    ps2.setString(2, bn.getDEPTNAME());
			    int row=ps2.executeUpdate();
			    //System.out.println("saveDepartmentID::TOTAL ROWS INSERTED:"+ row);
				isDataSaved = "GRNSave operation successful for Department Code : " + bn.getDEPTCODE();
			}
		} catch (SQLException sql) {
				con.rollback();
				log.fatal(sql, sql);
				isDataSaved = "REDSave operation failure for Department Code : " + bn.getDEPTCODE() + ". ERROR--> " + sql.getMessage();
				(new CommonDAO()).generateMessage("TESTLOGIN",isDataSaved);
			} finally {
				con.commit();
				con.closeConnection();
			}
		return isDataSaved;
	}

	public MstDepartment getDepartmentData(String deptCode) {
		MstDepartment deptBean = new MstDepartment();
		DBConnection con =  new DBConnection();
		try {
			/*String strSQL = " SELECT DEPTCODE, DEPTNAME FROM MSTDEPT WHERE DEPTCODE = "+deptCode+" ORDER BY DEPTCODE ";
			log.debug(strSQL);*/
			
			String strSQL = " SELECT DEPTCODE, DEPTNAME FROM MSTDEPT WHERE DEPTCODE = ? ORDER BY DEPTCODE ";
			con.openConnection();
			PreparedStatement ps3=con.setPrepStatement(strSQL);
			ps3.setString(1,deptCode);
			//ResultSet rs = con.select(strSQL);
			ResultSet rs =ps3.executeQuery();
			while (rs.next()) {
				deptBean = new MstDepartment();
				deptBean.setDEPTCODE(rs.getString("DEPTCODE"));
				deptBean.setDEPTNAME(rs.getString("DEPTNAME"));
			}
			rs.close();
			//System.out.println("getDepartmentData executed");
			
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return deptBean;
	}
}