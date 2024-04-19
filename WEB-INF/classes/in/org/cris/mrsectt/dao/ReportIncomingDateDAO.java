package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class ReportIncomingDateDAO {
	static Logger log = LogManager.getLogger(ReportIncomingDateDAO.class);

	public ArrayList<CommonBean> getData( String dateFrom, String dateTo) {
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection con =  new DBConnection();
		try {
			con.openConnection();

			String strSQL1 = "SELECT LISTAGG(REFCLASS,',') WITHIN GROUP (ORDER BY NULL) REFCLASS FROM UNIQUECLASS";
			log.debug(strSQL1);
			ResultSet rs =  con.select(strSQL1);
			String refClass1 = "";
			if(rs.next()) {		refClass1 = rs.getString("REFCLASS");	}
			String[] str1 = refClass1.split(",");
//System.out.println("str1 : "+ str1.length);
for(int i=0;i<str1.length;i++)
{
	//System.out.println("str "+i+" : "+ str1[i]);
}
			String strSQL = "SELECT DISTINCT LISTAGG('NVL(TO_CHAR(\"'||''''||REFCLASS||''''||'\"),''-'') '||REFCLASS||' ',',') WITHIN GROUP (ORDER BY NULL) REFCLASS FROM UNIQUECLASS";
			log.debug(strSQL);
			rs =  con.select(strSQL);
			if(rs.next())
			{
				String refClass = rs.getString("REFCLASS");
/*				String strSQL2 = " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'A'\"),'N/A') A,NVL(TO_CHAR(\"'B'\"),'N/A') B,NVL(TO_CHAR(\"'M'\"),'N/A') M,NVL(TO_CHAR(\"'O'\"),'N/A') O,NVL(TO_CHAR(\"'S'\") ,'N/A') S,NVL(TO_CHAR(\"'V'\"),'N/A') V" +
						  " FROM (SELECT TRUNC(TO_DATE('"+dateFrom+"','DD/MM/YYYY'),'MM')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))" +
						  " WHERE ROWNUM <= TO_DATE('"+dateTo+"','DD/MM/YYYY')+1 - TO_DATE('"+dateFrom+"','DD/MM/YYYY')) A," +
						  " (select * from (select INCOMINGDATE, refclass, REFNO from trnreference)" +
						  " pivot (count(REFNO) for refclass in ('A', 'B', 'M', 'O','S','V'))" +
						  " WHERE INCOMINGDATE BETWEEN TO_DATE('"+dateFrom+"','DD/MM/YYYY') AND TO_DATE('"+dateTo+"','DD/MM/YYYY')) B" +
						  " WHERE A.DATES = B.INCOMINGDATE(+) ORDER BY A.DATES " ;
*/				String strSQL2 = " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, "+refClass+" " +
						  " FROM (SELECT TO_DATE('"+dateFrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))" +
						  " WHERE ROWNUM <= TO_DATE('"+dateTo+"','DD/MM/YYYY')+1 - TO_DATE('"+dateFrom+"','DD/MM/YYYY')) A," +
						  " (select * from (select INCOMINGDATE, refclass, REFNO from trnreference)" +
						  " pivot (count(REFNO) for refclass in ('A', 'B','I', 'M', 'O','S','V'))" +
						  " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+dateFrom+"','DD/MM/YYYY') AND TO_DATE('"+dateTo+"','DD/MM/YYYY')) B" +
						  " WHERE A.DATES = B.INCOMINGDATE(+) ORDER BY A.DATES " ;
					log.debug(strSQL2);
					ResultSet rs2 =  con.select(strSQL2);
					CommonBean bn ;
					while(rs2.next())
					{
						bn = new CommonBean();
						bn.setField1(rs2.getString("DATES"));
						switch (str1.length) {
						case 6:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							break;
						case 7:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							bn.setField8(rs2.getString(str1[6]));
							break;
						case 8:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							bn.setField8(rs2.getString(str1[6]));
							bn.setField9(rs2.getString(str1[7]));
							break;
						case 9:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							bn.setField8(rs2.getString(str1[6]));
							bn.setField9(rs2.getString(str1[7]));
							bn.setField10(rs2.getString(str1[8]));
							break;
						case 10:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							bn.setField8(rs2.getString(str1[6]));
							bn.setField9(rs2.getString(str1[7]));
							bn.setField10(rs2.getString(str1[8]));
							bn.setField11(rs2.getString(str1[9]));
							break;
						case 11:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							bn.setField8(rs2.getString(str1[6]));
							bn.setField9(rs2.getString(str1[7]));
							bn.setField10(rs2.getString(str1[8]));
							bn.setField11(rs2.getString(str1[9]));
							bn.setField12(rs2.getString(str1[10]));
							break;
						case 12:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							bn.setField8(rs2.getString(str1[6]));
							bn.setField9(rs2.getString(str1[7]));
							bn.setField10(rs2.getString(str1[8]));
							bn.setField11(rs2.getString(str1[9]));
							bn.setField12(rs2.getString(str1[10]));
							bn.setField13(rs2.getString(str1[11]));
							break;
						case 13:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							bn.setField8(rs2.getString(str1[6]));
							bn.setField9(rs2.getString(str1[7]));
							bn.setField10(rs2.getString(str1[8]));
							bn.setField11(rs2.getString(str1[9]));
							bn.setField12(rs2.getString(str1[10]));
							bn.setField13(rs2.getString(str1[11]));
							bn.setField14(rs2.getString(str1[12]));
							break;
						case 14:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							bn.setField8(rs2.getString(str1[6]));
							bn.setField9(rs2.getString(str1[7]));
							bn.setField10(rs2.getString(str1[8]));
							bn.setField11(rs2.getString(str1[9]));
							bn.setField12(rs2.getString(str1[10]));
							bn.setField13(rs2.getString(str1[11]));
							bn.setField14(rs2.getString(str1[12]));
							bn.setField15(rs2.getString(str1[13]));
							break;
						case 15:
							bn.setField2(rs2.getString(str1[0]));
							bn.setField3(rs2.getString(str1[1]));
							bn.setField4(rs2.getString(str1[2]));
							bn.setField5(rs2.getString(str1[3]));
							bn.setField6(rs2.getString(str1[4]));
							bn.setField7(rs2.getString(str1[5]));
							bn.setField8(rs2.getString(str1[6]));
							bn.setField9(rs2.getString(str1[7]));
							bn.setField10(rs2.getString(str1[8]));
							bn.setField11(rs2.getString(str1[9]));
							bn.setField12(rs2.getString(str1[10]));
							bn.setField13(rs2.getString(str1[11]));
							bn.setField14(rs2.getString(str1[12]));
							bn.setField15(rs2.getString(str1[13]));
							bn.setField16(rs2.getString(str1[14]));
							break;
						}
//						System.out.println(bn.getField1() + " : "+ bn.getField2() + " : "+ bn.getField3());						
						arrList.add(bn);
					}
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return arrList;
	}
}