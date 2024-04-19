package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstTenure;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class MstTenureDAO {
	static Logger log = LogManager.getLogger(MstTenureDAO.class);
	
	public ArrayList<MstTenure> getSearchData(String tenureId, String name, String year) {
		
		ArrayList<MstTenure> arrList = new ArrayList<MstTenure>();
		DBConnection con = new DBConnection();
		
		String strSQL = " SELECT TENUREID, (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = MSTTENURE.ROLEID) ROLEID, YEAR, NAME, ISACTIVE, TO_CHAR(TENURESTARTDATE,'DD/MM/YYYY') TENURESTARTDATE, NVL(TO_CHAR(TENUREENDDATE,'DD/MM/YYYY'),'N/A') TENUREENDDATE, TENUREOFFICENAME,"
					+ " NVL(TENUREOFFICEADDRESS,'N/A') TENUREOFFICEADDRESS FROM MSTTENURE WHERE 1=1 ";
		String tempSQL = "";
		tempSQL += tenureId.trim().length() > 0 ? " AND TENUREID = '" + tenureId + "'" : "";
		// tempSQL += refClass.trim().length() > 0 ? " AND UPPER(B.REFCLASS) LIKE UPPER('%"+refClass+"%')" : "";
		tempSQL += name.trim().length() > 0 ? " AND UPPER(NAME) LIKE UPPER('%" + name + "%')" : "";
		tempSQL += year.trim().length() > 0 ? " AND YEAR = '" + year + "'" : "";
		strSQL = strSQL + tempSQL;
		strSQL = strSQL + " ORDER BY TENUREID";
		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				MstTenure bn = new MstTenure();
				bn.setTENUREENDDATE(rs.getString("TENUREENDDATE"));
				bn.setTENURESTARTDATE(rs.getString("TENURESTARTDATE"));
				bn.setISACTIVE(rs.getString("ISACTIVE"));
				bn.setTENUREOFFICEADDRESS(rs.getString("TENUREOFFICEADDRESS"));
				bn.setTENUREOFFICENAME(rs.getString("TENUREOFFICENAME"));
				bn.setNAME(rs.getString("NAME"));
				bn.setYEAR(rs.getString("YEAR"));
				bn.setROLEID(rs.getString("ROLEID"));
				bn.setTENUREID(rs.getString("TENUREID"));
				arrList.add(bn);
			}
			rs.close();
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return arrList;
	}
	
	public String saveTenureID(MstTenure bn, String[] arrREFCLASS, String[] arrINOUT, String[] arrCLASSDESCRIPTION, String[] arrISNEW) {
		// /MstRole bn = new MstRole();
		DBConnection con = new DBConnection();
		String strSQL = "";
		String strSQL1 = "";
		String strSQL2 = "";
		String strSQL3 = "";
		String isDataSaved = "";
		
		try {
			con.openConnection();
			if (bn.getISACTIVE().equalsIgnoreCase("Y")) {
				con.openConnection();
				strSQL = " UPDATE MSTTENURE SET ISACTIVE='N' WHERE ROLEID = " + bn.getROLEID() + " ";
				con.update(strSQL);

			}
			if (bn.getTENUREID().length() <= 0) {
				bn.setTENUREID(CommonDAO.getNextId(con, "TENUREID", "MSTTENURE"));
				
				strSQL = " INSERT INTO MSTTENURE(TENUREID,ROLEID,YEAR,NAME,ISACTIVE,TENURESTARTDATE,TENUREENDDATE,TENUREOFFICENAME,TENUREOFFICEADDRESS)"
						+ " VALUES(" + "'"
						+ bn.getTENUREID()
						+ "',"
						+ "'"
						+ bn.getROLEID()
						+ "',"
						+ "'"
						+ bn.getYEAR()
						+ "',"
						+ "'"
						+ bn.getNAME()
						+ "',"
						+ "'"
						+ bn.getISACTIVE()
						+ "',"
						+ "TO_DATE('"
						+ bn.getTENURESTARTDATE()
						+ "','DD/MM/YYYY'),"
						+ "TO_DATE('"
						+ bn.getTENUREENDDATE()
						+ "','DD/MM/YYYY'),"
						+ "'"
						+ bn.getTENUREOFFICENAME()
						+ "',"
						+ "'"
						+ bn.getTENUREOFFICEADDRESS() + "')";
				log.debug(strSQL);
				con.update(strSQL);
				
				for (int i = 1; i < arrREFCLASS.length; i++) {
					strSQL = " INSERT INTO MSTCLASS(TENUREID,ROLEID,YEAR,REFCLASS,INOUT,CLASSDESCRIPTION)" + " VALUES(" + "'" + bn.getTENUREID() + "',"
							+ "'" + bn.getROLEID() + "'," + "'" + bn.getYEAR() + "'," + "UPPER('" + arrREFCLASS[i] + "')," + "'"
							+ arrINOUT[i] + "','"+arrCLASSDESCRIPTION[i]+"')";
					log.debug(strSQL);
					con.update(strSQL);
				}
				
				strSQL = " INSERT INTO MSTFILECOUNTER(TENUREID,ROLEID,YEAR,FILECOUNTER)" + " VALUES(" + "'" + bn.getTENUREID() + "'," + "'"
						+ bn.getROLEID() + "'," + "'" + bn.getYEAR() + "'," + "'0')";
				log.debug(strSQL);
				con.update(strSQL);
				
				isDataSaved = "T";
			} else {
				//System.out.println("in updation mode");
				strSQL1 = " UPDATE MSTTENURE SET " +
						" ROLEID = '" + bn.getROLEID() + "'," + 
						" YEAR = '" + bn.getYEAR() + "'," + 
						" NAME = '" + bn.getNAME() + "'," + 
						" ISACTIVE = '" + bn.getISACTIVE() + "'," + 
						" TENURESTARTDATE = TO_DATE('"+ bn.getTENURESTARTDATE() + "','DD/MM/YYYY')," + 
						" TENUREENDDATE = TO_DATE('" + bn.getTENUREENDDATE() + "','DD/MM/YYYY')," + 
						" TENUREOFFICENAME = '" + bn.getTENUREOFFICENAME() + "'," + 
						" TENUREOFFICEADDRESS = '" + bn.getTENUREOFFICEADDRESS() + "'" + 
						" WHERE TENUREID = '" + bn.getTENUREID() + "'";
				log.debug(strSQL1);
				con.update(strSQL1);
				
				/*
				strSQL2 = " DELETE FROM MSTCLASS WHERE TENUREID = '" + bn.getTENUREID() + "'";
				log.debug(strSQL2);
				con.update(strSQL2);
				*/
				
				for (int i = 1; i < arrREFCLASS.length; i++) {
					if(arrISNEW[i].equalsIgnoreCase("Y"))
					{
						strSQL3 = " INSERT INTO MSTCLASS(TENUREID,ROLEID,YEAR,REFCLASS,INOUT,CLASSDESCRIPTION)" +
								 " VALUES("+bn.getTENUREID()+","+bn.getROLEID()+","+bn.getYEAR()+",UPPER('"+arrREFCLASS[i]+"')," +
								 " '"+arrINOUT[i]+"','"+arrCLASSDESCRIPTION[i]+"')";
						log.debug(strSQL3);
						con.update(strSQL3);
					}
					else if(arrISNEW[i].equalsIgnoreCase("N"))
					{
						strSQL = " UPDATE MSTCLASS SET CLASSDESCRIPTION = '"+arrCLASSDESCRIPTION[i]+"'" +
						" WHERE ROLEID = "+bn.getROLEID()+"" +
						" AND YEAR = "+bn.getYEAR()+"" +
						" AND REFCLASS = UPPER('"+arrREFCLASS[i]+"')" +
						" AND TENUREID = "+bn.getTENUREID()+"";
						log.debug(strSQL);
						con.update(strSQL);
					}
				}
			}
			isDataSaved = "GRNSave operation successfull for Tenure Id : " + bn.getTENUREID();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			isDataSaved = "REDSave Operation failuare...";
			e.printStackTrace();
		} finally {
		//	con.commit();
			con.closeConnection();
		}
		return isDataSaved;
	}
	
	public MstTenure getTenureData(String tenureid) {
		
		MstTenure tenureBean = new MstTenure();
		ArrayList<CommonBean> commonarr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
			String strSQL = " SELECT TENUREID, ROLEID, YEAR, NAME, ISACTIVE, TO_CHAR(TENURESTARTDATE,'DD/MM/YYYY') TENURESTARTDATE, TO_CHAR(TENUREENDDATE,'DD/MM/YYYY') TENUREENDDATE, TENUREOFFICENAME,"
					+ " TENUREOFFICEADDRESS FROM MSTTENURE WHERE"
					+ " TENUREID = '"+ tenureid+"' ";
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				tenureBean = new MstTenure();
				tenureBean.setTENUREENDDATE(rs.getString("TENUREENDDATE"));
				tenureBean.setTENURESTARTDATE(rs.getString("TENURESTARTDATE"));
				tenureBean.setISACTIVE(rs.getString("ISACTIVE"));
				tenureBean.setTENUREOFFICEADDRESS(rs.getString("TENUREOFFICEADDRESS"));
				tenureBean.setTENUREOFFICENAME(rs.getString("TENUREOFFICENAME"));
				tenureBean.setNAME(rs.getString("NAME"));
				tenureBean.setYEAR(rs.getString("YEAR"));
				tenureBean.setROLEID(rs.getString("ROLEID"));
				tenureBean.setTENUREID(rs.getString("TENUREID"));
			}
			rs.close();
			con.closeConnection();
			
			String strSQL1 = " SELECT REFCLASS, INOUT, REFCOUNTER, CLASSDESCRIPTION FROM MSTCLASS WHERE TENUREID = '" + tenureid + "' ";
			log.debug(strSQL1);
			con.openConnection();
			ResultSet rs1 = con.select(strSQL1);
			// String[] arrRefClass = new String[100];
			// String[] arrInOut = new String[100];
			while (rs1.next()) {
				CommonBean bn = new CommonBean();
				bn.setField1(StringFormat.nullString(rs1.getString("REFCLASS")));
				bn.setField2(StringFormat.nullString(rs1.getString("INOUT")));
				bn.setField3(StringFormat.nullString(rs1.getString("REFCOUNTER")));
				bn.setField4(StringFormat.nullString(rs1.getString("CLASSDESCRIPTION")));
				commonarr.add(bn);
			}
			rs1.close();
			tenureBean.setArr(commonarr);
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return tenureBean;
	}
	/*
	 * public ArrayList<MstTenure> getRefClass(String tenureId) { ArrayList<MstTenure> tenureBeanList = new ArrayList<MstTenure>();
	 * DBConnection con = new DBConnection(); try { String strSQL = "SELECT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID='" + tenureId +
	 * "' ORDER BY REFCLASS"; log.debug(strSQL); con.openConnection(); ResultSet rs = con.select(strSQL); while (rs.next()) { MstTenure
	 * tenureBean = new MstTenure(); tenureBean.setREFCLASS(rs.getString("REFCLASS")); tenureBean.setINOUT(rs.getString("INOUT"));
	 * tenureBeanList.add(tenureBean); } rs.close(); } catch (SQLException e) { log.fatal(e, e); } finally {
	 * 
	 * con.closeConnection(); } return tenureBeanList; }
	 */
}