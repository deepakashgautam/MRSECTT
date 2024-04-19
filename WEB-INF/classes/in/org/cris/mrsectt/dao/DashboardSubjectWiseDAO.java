package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MastersReportBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;


public class DashboardSubjectWiseDAO {
	static Logger log = LogManager.getLogger(DashboardSubjectWiseDAO.class);
	public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256; 
	 public static final int UNIT_OFFSET_LENGTH = 7; 
	 public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 }; 
	 
public String generateDashboardSubjectWiseDetailState(String serverpath,String subjectCode,String loginID,String status ) {
		 int ran = (int) (1000000 * Math.random());
			String Directory = serverpath;
			//String Directory = "D:\\";

			String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
			try {
				
				//String[] Refclass ={"A","B","E","C"};
				MastersReportBean bean = null;
				
				//ArrayList arrDT = new ArrayList();
				//ArrayList arr = new ArrayList();
				String headername = "Detail List SubjectWise";

				// Create New Workbook
				HSSFWorkbook wb = new HSSFWorkbook();
				// Define Styles to be used for PageHeader and CellHeader and
				// DataCells

				HSSFCellStyle style_cell = wb.createCellStyle();
				//style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				style_cell.setBorderBottom(BorderStyle.THIN);
				style_cell.setBottomBorderColor(HSSFColor.BLACK.index);
				style_cell.setBorderLeft(BorderStyle.THIN);
				style_cell.setLeftBorderColor(HSSFColor.BLACK.index);
				style_cell.setBorderRight(BorderStyle.THIN);
				style_cell.setRightBorderColor(HSSFColor.BLACK.index);
				style_cell.setBorderTop(BorderStyle.THIN);
				style_cell.setTopBorderColor(HSSFColor.BLACK.index);
				style_cell.setAlignment(HorizontalAlignment.CENTER);
				style_cell.setWrapText(true);
				HSSFCellStyle style_cell_num = wb.createCellStyle();
				style_cell_num.setAlignment(HorizontalAlignment.RIGHT);
				style_cell_num.setBorderBottom(BorderStyle.THIN);
				style_cell_num.setBottomBorderColor(HSSFColor.BLACK.index);
				style_cell_num.setBorderLeft(BorderStyle.THIN);
				style_cell_num.setLeftBorderColor(HSSFColor.BLACK.index);
				style_cell_num.setBorderRight(BorderStyle.THIN);
				style_cell_num.setRightBorderColor(HSSFColor.BLACK.index);
				style_cell_num.setBorderTop(BorderStyle.THIN);
				style_cell_num.setTopBorderColor(HSSFColor.BLACK.index);
				style_cell_num.setWrapText(true);
				HSSFCellStyle style_pageHeader = wb.createCellStyle();
				style_pageHeader.setAlignment(HorizontalAlignment.CENTER);

				HSSFCellStyle style_header = wb.createCellStyle();
				style_header
						.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				style_header.setAlignment(HorizontalAlignment.CENTER);
				style_header.setBorderBottom(BorderStyle.THIN);
				style_header.setBottomBorderColor(HSSFColor.BLACK.index);
				style_header.setBorderLeft(BorderStyle.THIN);
				style_header.setLeftBorderColor(HSSFColor.BLACK.index);
				style_header.setBorderRight(BorderStyle.THIN);
				style_header.setRightBorderColor(HSSFColor.BLACK.index);
				style_header.setBorderTop(BorderStyle.THIN);
				style_header.setTopBorderColor(HSSFColor.BLACK.index);
				style_header.setWrapText(true);

				// Fonts for PageHeader and CellHeaders
				HSSFFont font_pageHeader = wb.createFont();
				font_pageHeader.setFontName("Arial");
				font_pageHeader.setFontHeightInPoints((short) 10);
				font_pageHeader.setBold(true);
				font_pageHeader.setColor(HSSFColor.BLACK.index);

				HSSFFont font_Header = wb.createFont();
				font_Header.setFontHeightInPoints((short) 10);
				font_Header.setBold(true);
				
				HSSFFont font_cell = wb.createFont();
				font_cell.setFontHeightInPoints((short) 10);
				//font_cell.setAlignment(HSSFCellStyle.ALIGN_CENTER);

				
				
				///--------loopp-----------------------------------------------------///
				
				//for (int index = 0; index < Refclass.length; index++) {
					ArrayList<CommonBean> cm =new ArrayList();	
					
					
					Date date = new Date();
				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				    String strDate = sdf.format(date);
				    
					
					headername = "Detail List of Dashboard" +strDate;
					String[] arrh = {"S.No","Reference Number \n Incoming Date","Reference Date"," Name","State","Subject Code","Subject","Marking \n To Marking \n Date","Remarks \n Signed By \n Signed On","File No.","Compliance","Compliance Remarks"};
					HSSFSheet sheet = wb.createSheet("Detail List of Dashboard");
					sheet.setColumnWidth(0, 2000);
			        sheet.setColumnWidth(1, 5000);
			        sheet.setColumnWidth(2, 3000);
			        sheet.setColumnWidth(3, 5000);
			        
			        sheet.setColumnWidth(4, 3000);
			        sheet.setColumnWidth(5, 6000);
			        sheet.setColumnWidth(6, 5000);
			        sheet.setColumnWidth(7, 5000);
			        sheet.setColumnWidth(8, 5000);
			        sheet.setColumnWidth(9, 5000);
			        sheet.setColumnWidth(10, 5000);
			        sheet.setColumnWidth(11, 5000);
			        sheet.setColumnWidth(12, 5000);
			        //sheet.setColumnWidth(12, 5000);
			        //sheet.setColumnWidth(13, 5000);
			        
			        
					HSSFPrintSetup ps = sheet.getPrintSetup();
					sheet.setAutobreaks(true);
					sheet.getPrintSetup().setLandscape(true);
					ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
					ps.setFitWidth((short)1);
					ps.setFitHeight((short)0);
					sheet.setHorizontallyCenter(true);
					
					cm =getRefDashBoardDetailList(subjectCode,loginID,status);
					
					
					HSSFHeader header = sheet.getHeader();
					
					header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
							+ HSSFHeader.fontSize((short) 16) + headername);

					header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
							+ HSSFHeader.fontSize((short) 16) + headername);
					
					
					int rnum = 0;				
					
					HSSFRichTextString string;
					HSSFRichTextString s1;
					HSSFRichTextString s2;
					HSSFRichTextString s3;
					
					HSSFRow row = sheet.createRow(rnum++);
					row.setHeightInPoints(40);
					for (int i = 0; i < arrh.length; i++) {
						string = new HSSFRichTextString(arrh[i].toString()
								.replaceAll("&deg;", "°"));
						string.applyFont(font_Header);
						createCell(wb, row, (short) (i), style_header, string);
					

					}
					
					int sno =1;
					
					
					for (int i = 0; i < cm.size(); i++) {
						
						row = sheet.createRow(rnum++);
						string = new HSSFRichTextString(sno+"");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (0),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField1().toString()+"\n"+cm.get(i).getField2().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (1),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField3().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (2),
								style_cell, string);
						
					
						string = new HSSFRichTextString(cm.get(i).getField4().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (3),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField19().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (4),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField18().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (5),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField5().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (6),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField6().toString()+"\n"+cm.get(i).getField7().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (7),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField8().toString()+"\n"+cm.get(i).getField9().toString()+"\n"+cm.get(i).getField10().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (8),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField13().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (9),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField14().toString().equalsIgnoreCase("")?"":cm.get(i).getField14().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (10),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField15().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (11),
								style_cell, string);
//						
//						string = new HSSFRichTextString("");
//						string.applyFont(font_cell);
//						createCell(wb, row, (short) (11),
//								style_cell, string);
//					
//						string = new HSSFRichTextString("");
//						string.applyFont(font_cell);
//						createCell(wb, row, (short) (12),
//								style_cell, string);
						
						
						row.setHeightInPoints(60);
					sno++;
				}
				
				//row = sheet.createRow(rnum++);
					FileOutputStream fileOut = new FileOutputStream(FilePath);

					wb.write(fileOut);
					fileOut.close();		

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return FilePath;


}
			
	
private static void createCell(HSSFWorkbook wb, HSSFRow row, short column,
		HSSFCellStyle style, HSSFRichTextString value) {
	HSSFCell cell = row.createCell(column);
	cell.setCellValue(value);
	cell.setCellStyle(style);
}

public ArrayList<CommonBean> getRefDashBoardDetailListHOD(String subjectCode,String loginID,String status){
	String condSubject="";
	String condStatus="";
	if(subjectCode.equalsIgnoreCase("All")){
		condSubject=" and 1=1";	
	}else{
		condSubject=" and NVL(subjectcode,'OTR')='"+subjectCode+"'";
	}
	if(status.equalsIgnoreCase("Total")){
		condStatus=" and 1=1";		
	}else {
		condStatus = " and NVL(compliance,'A') = "+status;
	}
	
		
		String strSQL = "select a.REFNO, TO_CHAR(a.incomingdate,'DD/MM/YYYY'),TO_CHAR(a.letterdate,'DD/MM/YYYY'),a.referencename, subject, "
				+ "(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=a.REFID AND M.MARKINGSEQUENCE=1)) markingto, (select TO_CHAR(b.markingdate,'DD/MM/YYYY')  "
				+ "from trnmarking b where refid=a.refid) markingdate,Remarks, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.SIGNEDBY) SIGNEDBY,TO_CHAR(SIGNEDON,'DD/MM/YYYY'), (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.ACKBY)ACKBY,  "
				+ "TO_CHAR(ACKDATE,'DD/MM/YYYY'),fileno,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = a.COMPLIANCE) compliancedesc,a.COMPLIANCEREMARKS,a.COMPLIANCE,a.refid,a.subjectcode,a.forwardtosubhead,a.railway from trnreference a where refid in "
				+ "( select refid from trnmarking where MARKINGTO in ( select roleid from mstrole where deptcode in  "
				+ "( (select deptcode from mstdept where hod=(select roleid from mstrole where roleid = '"+loginID+"'))) and  "
				+ "refid in (select refid from trnreference where  tenureid>=12 and "
				+ "refclass in ('A','M') ))) " +condSubject +condStatus;
		
		
		return (new CommonDAO()).getSQLResult(strSQL, 20);

		
	}

public ArrayList<CommonBean> getRefDashBoardDetailListHODSubHead(String subjectCode,String loginname,String status){
	String condSubject="";
	String condStatus="";
	if(subjectCode.equalsIgnoreCase("All")){
		condSubject=" and 1=1";	
	}else{
		condSubject=" and NVL(subjectcode,'OTR')='"+subjectCode+"'";
	}
	if(status.equalsIgnoreCase("Total")){
		condStatus=" and 1=1";		
	}else {
		condStatus = " and NVL(compliance,'A') = "+status;
	}
	
		
		String strSQL = "select a.REFNO, TO_CHAR(a.incomingdate,'DD/MM/YYYY'),TO_CHAR(a.letterdate,'DD/MM/YYYY'),a.referencename, subject, "
				+ "(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=a.REFID AND M.MARKINGSEQUENCE=1)) markingto, (select TO_CHAR(b.markingdate,'DD/MM/YYYY')  "
				+ "from trnmarking b where refid=a.refid) markingdate,Remarks, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.SIGNEDBY) SIGNEDBY,TO_CHAR(SIGNEDON,'DD/MM/YYYY'), (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.ACKBY)ACKBY,  "
				+ "TO_CHAR(ACKDATE,'DD/MM/YYYY'),fileno,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = a.COMPLIANCE) compliancedesc,a.COMPLIANCEREMARKS,a.COMPLIANCE,a.refid,a.subjectcode from trnreference a where "
				+ " FORWARDTOSUBHEAD ='"+loginname+"' and tenureid>=12 and  "
				+ "refclass in ('A','M')" +condSubject +condStatus;
		
		
		return (new CommonDAO()).getSQLResult(strSQL, 18);

		
	}

public ArrayList<CommonBean> getRefDashBoardDetailListRailway(String subjectCode,String loginname,String status){
	String condSubject="";
	String condStatus="";
	if(subjectCode.equalsIgnoreCase("All")){
		condSubject=" and 1=1";	
	}else{
		condSubject=" and NVL(subjectcode,'OTR')='"+subjectCode+"'";
	}
	if(status.equalsIgnoreCase("Total")){
		condStatus=" and 1=1";		
	}else {
		condStatus = " and NVL(compliance,'A') = "+status;
	}
	
		
		String strSQL = "select a.REFNO, TO_CHAR(a.incomingdate,'DD/MM/YYYY'),TO_CHAR(a.letterdate,'DD/MM/YYYY'),a.referencename, subject, "
				+ "(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=a.REFID AND M.MARKINGSEQUENCE=1)) markingto, (select TO_CHAR(b.markingdate,'DD/MM/YYYY')  "
				+ "from trnmarking b where refid=a.refid) markingdate,Remarks, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.SIGNEDBY) SIGNEDBY,TO_CHAR(SIGNEDON,'DD/MM/YYYY'), (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.ACKBY)ACKBY,  "
				+ "TO_CHAR(ACKDATE,'DD/MM/YYYY'),fileno,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = a.COMPLIANCE) compliancedesc,a.COMPLIANCEREMARKS,a.COMPLIANCE,a.refid,a.subjectcode from trnreference a where "
				+ " RAILWAY ='"+loginname+"' and tenureid>=12 and  "
				+ "refclass in ('A','M')" +condSubject +condStatus;
		
		
		return (new CommonDAO()).getSQLResult(strSQL, 18);

		
	}
public ArrayList<CommonBean> getRefDashBoardDetailList(String subjectCode,String loginID,String status){
	String condSubject="";
	String condStatus="";
	if(subjectCode.equalsIgnoreCase("All")){
		condSubject=" and 1=1";	
	}else{
		condSubject=" and NVL(subjectcode,'OTR')='"+subjectCode+"'";
	}
	if(status.equalsIgnoreCase("Total")){
		condStatus=" and 1=1";		
	}else {
		condStatus = " and NVL(compliance,'A') = "+status;
	}
	
	
	String strSQL1 ="select statecode from mstrolestate where roleid ="+loginID+"";
	ArrayList<CommonBean> cm =(new CommonDAO()).getSQLResult(strSQL1, 1);
	String stForState = "";
	for(int i=0;i<cm.size();i++){
		CommonBean bn = (CommonBean) cm.get(i);
		 stForState =stForState + "'"+bn.getField1() +"',";
	}
	String stateCond = " and statecode in ("+stForState.substring(0, stForState.length()-1)+" ) ";	
	//System.out.println("stateCond"+stateCond);
	
		String strSQL = "select a.REFNO, TO_CHAR(a.incomingdate,'DD/MM/YYYY'),TO_CHAR(a.letterdate,'DD/MM/YYYY'),a.referencename, subject, "
				+ "(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=a.REFID AND M.MARKINGSEQUENCE=1)) markingto, (select TO_CHAR(b.markingdate,'DD/MM/YYYY')  "
				+ "from trnmarking b where refid=a.refid) markingdate,Remarks, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.SIGNEDBY) SIGNEDBY,TO_CHAR(SIGNEDON,'DD/MM/YYYY'), (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.ACKBY)ACKBY,  "
				+ "TO_CHAR(ACKDATE,'DD/MM/YYYY'),fileno,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = a.COMPLIANCE) compliancedesc,a.COMPLIANCEREMARKS,a.COMPLIANCE,a.refid,a.subjectcode, (SELECT statename FROM mststate WHERE statecode = a.statecode) statename from trnreference a"
				+ " where  tenureid>=12 and "
				+ "refclass in ('A','M') "+stateCond +condSubject +condStatus;
		
		//System.out.println(strSQL);
		return (new CommonDAO()).getSQLResult(strSQL, 19);

		
	}


public String getStatesOfRole(String loginID){
	
	
	
	String strSQL1 ="select b.statecode,(select statename from mststate a where b.statecode = a.statecode) statename from mstrolestate b where roleid ="+loginID+"";
	ArrayList<CommonBean> cm =(new CommonDAO()).getSQLResult(strSQL1, 2);
	String stForState = "";
	for(int i=0;i<cm.size();i++){
		CommonBean bn = (CommonBean) cm.get(i);
		 stForState =stForState + ""+bn.getField2() +", ";
	}
	String stateOfRole = " States : "+stForState.substring(0, stForState.length()-2)+"  ";	
	//System.out.println("stateCond"+stateCond);		
		return stateOfRole;

		
	}

public String saveComplianceData(String refid, String compliance, String compliianceRem,String LoginID,String forwardTo, String railwayTo) {
	String result = "";
	DBConnection con = new DBConnection();
	try {
		con.openConnection();

		String Query = " 	update trnreference set  compliance = '"+compliance+"', complianceremarks = '"+compliianceRem+"',forwardtoSubHead = '"+forwardTo+"',railway = '"+railwayTo+"' ,compliancedate= sysdate,changedate= sysdate,loginid = '"+LoginID+"' WHERE refid = '"
				+ refid + "'";

		//System.out.println(Query);
		con.update(Query);

		result = "Data Saved Successfully";

	} catch (SQLException e) {
		e.printStackTrace();
		result = "0~" + e.getMessage();
		con.rollback();
	} finally {
		con.closeConnection();
	}
	return result;

}

public String saveComplianceDataSub(String refid, String compliance, String compliianceRem,String LoginID) {
	String result = "";
	DBConnection con = new DBConnection();
	try {
		con.openConnection();

		String Query = " 	update trnreference set  compliance = '"+compliance+"', complianceremarks = '"+compliianceRem+"',compliancedate= sysdate,changedate= sysdate,loginid = '"+LoginID+"' WHERE refid = '"
				+ refid + "'";

		//System.out.println(Query);
		con.update(Query);

		result = "Data Saved Successfully";

	} catch (SQLException e) {
		e.printStackTrace();
		result = "0~" + e.getMessage();
		con.rollback();
	} finally {
		con.closeConnection();
	}
	return result;

}

public String saveComplianceDataSubRailway(String refid,String compliianceRem,String LoginID) {
	String result = "";
	DBConnection con = new DBConnection();
	try {
		con.openConnection();

		String Query = " 	update trnreference set  complianceremarks = '"+compliianceRem+"',compliancedate= sysdate,changedate= sysdate,loginid = '"+LoginID+"' WHERE refid = '"
				+ refid + "'";

		//System.out.println(Query);
		con.update(Query);

		result = "Data Saved Successfully";

	} catch (SQLException e) {
		e.printStackTrace();
		result = "0~" + e.getMessage();
		con.rollback();
	} finally {
		con.closeConnection();
	}
	return result;

}
private static void createNumberCell(HSSFWorkbook wb, HSSFRow row,
		short column, HSSFCellStyle style, String value) {
	HSSFCell cell = row.createCell(column);
	
	if (value != null && value.length() > 0) {
		cell.setCellValue(Double.parseDouble(value));
	} else {
		cell.setCellValue(new HSSFRichTextString(value));
	}
	cell.setCellStyle(style);
}

public	HSSFWorkbook getExcelDataState(String st){
	String loginId=st;
	HSSFWorkbook hwb=new HSSFWorkbook();
 	  HSSFSheet sheet =  hwb.createSheet("Reference Subject Wise");
	 sheet.setColumnWidth(0, 8000);
     sheet.setColumnWidth(1, 2000);
     sheet.setColumnWidth(2, 3500);
     sheet.setColumnWidth(3, 3000);
     sheet.setColumnWidth(4, 7000);
     sheet.setColumnWidth(5, 7000);
     sheet.setColumnWidth(6, 3000);
     /*
     sheet.addMergedRegion(new CellRangeAddress(
    	      0, //first row (0-based)
    	      0, //last row (0-based)
    	      4, //first column (0-based)
    	      6 //last column (0-based)
    	      ));
     */
     HSSFCellStyle style2 = hwb.createCellStyle();
     style2.setAlignment(HorizontalAlignment.CENTER);
     style2.setVerticalAlignment( 
     VerticalAlignment.CENTER);
    
     HSSFFont font = hwb.createFont();
     font.setFontHeightInPoints((short) 10);
 	 font.setBold(true);
     style2.setFont(font);
     
     style2.setBorderLeft(BorderStyle.THIN);
     style2.setBorderRight(BorderStyle.THIN);
     style2.setBorderTop(BorderStyle.THIN);
     style2.setBorderBottom(BorderStyle.THIN);
     
     HSSFCellStyle style1 = hwb.createCellStyle();
     style1.setAlignment(HorizontalAlignment.CENTER);
     style1.setVerticalAlignment(VerticalAlignment.CENTER);
    
     HSSFFont font1 = hwb.createFont();
     font1.setFontHeightInPoints((short) 10);
 	  style1.setFont(font1);
     
     style1.setBorderLeft(BorderStyle.THIN);
     style1.setBorderRight(BorderStyle.THIN);
     style1.setBorderTop(BorderStyle.THIN);
     style1.setBorderBottom(BorderStyle.THIN);
    
     
     HSSFPrintSetup ps = sheet.getPrintSetup();
		sheet.setAutobreaks(true);
		ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		//ps.setLandscape(layout.equalsIgnoreCase("L"));
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
		sheet.setHorizontallyCenter(true);
	
	
	  HSSFRow rowhead=   sheet.createRow((short) 0);
	     HSSFCell cell0 = rowhead.createCell(0);
	     HSSFCell cell1 = rowhead.createCell(1);
	     HSSFCell cell2 = rowhead.createCell(2);
	     HSSFCell cell3 = rowhead.createCell(3);
	     HSSFCell cell4 = rowhead.createCell(4);
	     HSSFCell cell5 = rowhead.createCell(5);
	     HSSFCell cell6 = rowhead.createCell(6);


	  cell0.setCellValue("Subject Code");
	  cell1.setCellValue("Pending");
	  cell2.setCellValue("Under Process");
	  cell3.setCellValue("Complied");
	  cell4.setCellValue("Not Feasable");
	  cell5.setCellValue("Partially Complied");
	  cell6.setCellValue("Total");

	  cell0.setCellStyle(style2);
	 cell1.setCellStyle(style2);
	 cell2.setCellStyle(style2);
	 cell3.setCellStyle(style2);
	 cell4.setCellStyle(style2);
	 cell5.setCellStyle(style2);
	 cell6.setCellStyle(style2);
	

	  try {	  
	  DBConnection con = new DBConnection();
	  con.openConnection();
	  
		String strSQL1 ="select statecode from mstrolestate where roleid ="+loginId+"";
		ArrayList<CommonBean> cm =(new CommonDAO()).getSQLResult(strSQL1, 1);
		String stForState = "";
		for(int i=0;i<cm.size();i++){
			CommonBean bn = (CommonBean) cm.get(i);
			 stForState =stForState + "'"+bn.getField1() +"',";
		}
		String stateCond = " and statecode in ("+stForState.substring(0, stForState.length()-1)+")";	
		//System.out.println("stateCond"+stateCond);
		
	  
	  
		// String strSQL = "select referencename, addviptype, sum(refcount) as refcount, sum(complied) as complied, sum(partiallycomp) as partiallycomp,sum(notfeasible) as notfeasible from(select referencename,addviptype,1 refcount,decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'E',1,0) partiallycomp,decode(NVL(compliance,'A'),'D',1,0) notfeasible from trnreference where addviptype in ('LS','RS','MIN','MOS','PM') and tenureid=12 and incomingdate>to_date('10/11/2014','dd/mm/yyyy')and addvipid<>'820' and statecode= '"+state+"' order by 1)group by referencename,addviptype order by 1";
		/*String strSQL = "select NVL(subjectcodeform,'TOTAL') as sub,sum(pending) as pending ,sum(UE) as up, sum(complied) as cmp,sum(NF) as nf,sum(PC) as pc,sum(pending)+sum(UE)+sum(complied)+sum(NF)+sum(PC) total, (Select subjectname from mstsubject where subjectcode=subjectcodeform) subjectdesc from "
				+ "(select refid,NVL(subjectcode,'OTR') subjectcodeform,decode(NVL(compliance,'A'),'A',1,0) pending,decode(NVL(compliance,'A'),'B',1,0) UE, "
				+ "decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'D',1,0) NF, "
				+ "decode(NVL(compliance,'A'),'E',1,0) PC "
				+ "from trnreference where refid in ( "
				+ "select refid from trnmarking where MARKINGTO in ( "
				+ "select roleid from mstrole where deptcode in ( "
				+ "(select deptcode from mstdept where hod=(select roleid from mstrole where roleid = '"+loginId+"'))) "
				+ "and refid in (select refid from trnreference where  tenureid>=12 and refclass in ('A','M') "+stateCond+") "
				+ ")) ) "
				+ "group by cube(subjectcodeform) order by NVL(subjectcodeform,'ZTOTAL') ";
*/
		String strSQL = "select NVL(subjectcodeform,'TOTAL') as sub,sum(pending) as pending ,sum(UE) as up, sum(complied) as cmp,sum(NF) as nf,sum(PC) as pc,sum(pending)+sum(UE)+sum(complied)+sum(NF)+sum(PC) total, (Select subjectname from mstsubject where subjectcode=subjectcodeform) subjectdesc from "
				+ "(select refid,NVL(subjectcode,'OTR') subjectcodeform,decode(NVL(compliance,'A'),'A',1,0) pending,decode(NVL(compliance,'A'),'B',1,0) UE, "
				+ "decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'D',1,0) NF, "
				+ "decode(NVL(compliance,'A'),'E',1,0) PC "
				+ "from trnreference  where  tenureid>=12 and refclass in ('A','M') "+stateCond+" "
				+ " ) "
				+ "group by cube(subjectcodeform) order by NVL(subjectcodeform,'ZTOTAL') ";

		ResultSet rs2=con.select(strSQL);

		int i=2,j=1;
	int count =0;
	while(rs2.next())
	{
		count=count+1;
	}
	rs2.close();

	//System.out.println("Number of records in ResultSet: " + count);
	
	ResultSet rs=con.select(strSQL);	  
	  while(rs.next()){
		  
	  HSSFRow row=   sheet.createRow((short)i);
	  
	if(j<count)
	{
	  createCell(hwb, row, (short) (0),style1, rs.getString("subjectdesc"));
		createCell(hwb, row, (short) (1),style1, String.valueOf(rs.getInt("pending")));
	      createCell(hwb, row, (short) (2),style1, String.valueOf(rs.getInt("up")));
			createCell(hwb, row, (short) (3),style1, String.valueOf(rs.getInt("cmp")));
		      createCell(hwb, row, (short) (4),style1, String.valueOf(rs.getInt("nf")));
				createCell(hwb, row, (short) (5),style1, String.valueOf(rs.getInt("pc")));
				createCell(hwb, row, (short) (6),style2, String.valueOf(rs.getInt("total")));
	}
	else
	{
		createCell(hwb, row, (short) (0),style2, "Total");
		createCell(hwb, row, (short) (1),style2, String.valueOf(rs.getInt("pending")));
	      createCell(hwb, row, (short) (2),style2, String.valueOf(rs.getInt("up")));
			createCell(hwb, row, (short) (3),style2, String.valueOf(rs.getInt("cmp")));
		      createCell(hwb, row, (short) (4),style2, String.valueOf(rs.getInt("nf")));
				createCell(hwb, row, (short) (5),style2, String.valueOf(rs.getInt("pc")));
				createCell(hwb, row, (short) (6),style2, String.valueOf(rs.getInt("total")));
	
	}
		i++;
		j++;
	  }
	  
	  }
	  catch(Exception e)
	  {
		  log.error(e);
	  }
return hwb;
	}
private static void createCell(HSSFWorkbook wb, HSSFRow row, short column,
		HSSFCellStyle style, String string) {
	HSSFCell cell = row.createCell(column);
	cell.setCellValue(string);
	cell.setCellStyle(style);
}


public	HSSFWorkbook getExcelDataSubRailway(String st){
	String loginId=st;
	HSSFWorkbook hwb=new HSSFWorkbook();
 	  HSSFSheet sheet =  hwb.createSheet("Reference Subject Wise");
		sheet.setColumnWidth(0, 8000);
     sheet.setColumnWidth(1, 2000);
     sheet.setColumnWidth(2, 3500);
     sheet.setColumnWidth(3, 3000);
     sheet.setColumnWidth(4, 7000);
     sheet.setColumnWidth(5, 7000);
     sheet.setColumnWidth(6, 3000);
     /*
     sheet.addMergedRegion(new CellRangeAddress(
    	      0, //first row (0-based)
    	      0, //last row (0-based)
    	      4, //first column (0-based)
    	      6 //last column (0-based)
    	      ));
     */
     HSSFCellStyle style2 = hwb.createCellStyle();
     style2.setAlignment(HorizontalAlignment.CENTER);
     style2.setVerticalAlignment( 
     VerticalAlignment.CENTER);
    
     HSSFFont font = hwb.createFont();
     font.setFontHeightInPoints((short) 10);
 	 font.setBold(true);
     style2.setFont(font);
     
     style2.setBorderLeft(BorderStyle.THIN);
     style2.setBorderRight(BorderStyle.THIN);
     style2.setBorderTop(BorderStyle.THIN);
     style2.setBorderBottom(BorderStyle.THIN);
     
     HSSFCellStyle style1 = hwb.createCellStyle();
     style1.setAlignment(HorizontalAlignment.CENTER);
     style1.setVerticalAlignment( 
    		 VerticalAlignment.CENTER);
    
     HSSFFont font1 = hwb.createFont();
     font1.setFontHeightInPoints((short) 10);
 	  style1.setFont(font1);
     
     style1.setBorderLeft(BorderStyle.THIN);
     style1.setBorderRight(BorderStyle.THIN);
     style1.setBorderTop(BorderStyle.THIN);
     style1.setBorderBottom(BorderStyle.THIN);
    
     
     HSSFPrintSetup ps = sheet.getPrintSetup();
		sheet.setAutobreaks(true);
		ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		//ps.setLandscape(layout.equalsIgnoreCase("L"));
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
		sheet.setHorizontallyCenter(true);
	
	
	  HSSFRow rowhead=   sheet.createRow((short) 0);
	     HSSFCell cell0 = rowhead.createCell(0);
	     HSSFCell cell1 = rowhead.createCell(1);
	     HSSFCell cell2 = rowhead.createCell(2);
	     HSSFCell cell3 = rowhead.createCell(3);
	     HSSFCell cell4 = rowhead.createCell(4);
	     HSSFCell cell5 = rowhead.createCell(5);
	     HSSFCell cell6 = rowhead.createCell(6);


	  cell0.setCellValue("Subject Code");
	  cell1.setCellValue("Pending");
	  cell2.setCellValue("Under Process");
	  cell3.setCellValue("Complied");
	  cell4.setCellValue("Not Feasable");
	  cell5.setCellValue("Partially Complied");
	  cell6.setCellValue("Total");

	  cell0.setCellStyle(style2);
	 cell1.setCellStyle(style2);
	 cell2.setCellStyle(style2);
	 cell3.setCellStyle(style2);
	 cell4.setCellStyle(style2);
	 cell5.setCellStyle(style2);
	 cell6.setCellStyle(style2);
	

	  try {	  
	  DBConnection con = new DBConnection();
	  con.openConnection();
		// String strSQL = "select referencename, addviptype, sum(refcount) as refcount, sum(complied) as complied, sum(partiallycomp) as partiallycomp,sum(notfeasible) as notfeasible from(select referencename,addviptype,1 refcount,decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'E',1,0) partiallycomp,decode(NVL(compliance,'A'),'D',1,0) notfeasible from trnreference where addviptype in ('LS','RS','MIN','MOS','PM') and tenureid=12 and incomingdate>to_date('10/11/2014','dd/mm/yyyy')and addvipid<>'820' and statecode= '"+state+"' order by 1)group by referencename,addviptype order by 1";
		String strSQL = "select NVL(subjectcodeform,'TOTAL'),sum(pending) as pending,sum(UE) as up,sum(complied) as cmp,sum(NF) as nf,sum(PC) as pc ,sum(pending)+sum(UE)+sum(complied)+sum(NF)+sum(PC) total, (Select subjectname from mstsubject where subjectcode=subjectcodeform) subjectdesc from "
			+ "(select refid,NVL(subjectcode,'OTR') subjectcodeform,decode(NVL(compliance,'A'),'A',1,0) pending,decode(NVL(compliance,'A'),'B',1,0) UE, "
			+ "decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'D',1,0) NF, "
			+ "decode(NVL(compliance,'A'),'E',1,0) PC "
			+ "from trnreference  where railway ='"+loginId+"' ) "
			+ "group by cube(subjectcodeform) order by NVL(subjectcodeform,'ZTOTAL') ";

		//System.out.println(strSQL);
		ResultSet rs2=con.select(strSQL);

		int i=2,j=1;
	int count =0;
	while(rs2.next())
	{
		count=count+1;
	}
	rs2.close();

	//System.out.println("Number of records in ResultSet: " + count);
	
	ResultSet rs=con.select(strSQL);	  
	  while(rs.next()){
		  
	  HSSFRow row=   sheet.createRow((short)i);
	  
	if(j<count)
	{
	  createCell(hwb, row, (short) (0),style1, rs.getString("subjectdesc"));
		createCell(hwb, row, (short) (1),style1, String.valueOf(rs.getInt("pending")));
	      createCell(hwb, row, (short) (2),style1, String.valueOf(rs.getInt("up")));
			createCell(hwb, row, (short) (3),style1, String.valueOf(rs.getInt("cmp")));
		      createCell(hwb, row, (short) (4),style1, String.valueOf(rs.getInt("nf")));
				createCell(hwb, row, (short) (5),style1, String.valueOf(rs.getInt("pc")));
				createCell(hwb, row, (short) (6),style2, String.valueOf(rs.getInt("total")));
	}
	else
	{
		createCell(hwb, row, (short) (0),style2, "Total");
		createCell(hwb, row, (short) (1),style2, String.valueOf(rs.getInt("pending")));
	      createCell(hwb, row, (short) (2),style2, String.valueOf(rs.getInt("up")));
			createCell(hwb, row, (short) (3),style2, String.valueOf(rs.getInt("cmp")));
		      createCell(hwb, row, (short) (4),style2, String.valueOf(rs.getInt("nf")));
				createCell(hwb, row, (short) (5),style2, String.valueOf(rs.getInt("pc")));
				createCell(hwb, row, (short) (6),style2, String.valueOf(rs.getInt("total")));
	
	}
		i++;
		j++;
	  }
	  
	  }
	  catch(Exception e)
	  {
		  log.error(e);
	  }
return hwb;
	}


public	HSSFWorkbook getExcelDataHODSub(String st){
	String loginId=st;
	HSSFWorkbook hwb=new HSSFWorkbook();
 	  HSSFSheet sheet =  hwb.createSheet("Reference Subject Wise");
		sheet.setColumnWidth(0, 8000);
     sheet.setColumnWidth(1, 2000);
     sheet.setColumnWidth(2, 3500);
     sheet.setColumnWidth(3, 3000);
     sheet.setColumnWidth(4, 7000);
     sheet.setColumnWidth(5, 7000);
     sheet.setColumnWidth(6, 3000);
     /*
     sheet.addMergedRegion(new CellRangeAddress(
    	      0, //first row (0-based)
    	      0, //last row (0-based)
    	      4, //first column (0-based)
    	      6 //last column (0-based)
    	      ));
     */
     HSSFCellStyle style2 = hwb.createCellStyle();
     style2.setAlignment(HorizontalAlignment.CENTER);
     style2.setVerticalAlignment( 
    		 VerticalAlignment.TOP);
    
     HSSFFont font = hwb.createFont();
     font.setFontHeightInPoints((short) 10);
 	 font.setBold(true);
     style2.setFont(font);
     
     style2.setBorderLeft(BorderStyle.THIN);
     style2.setBorderRight(BorderStyle.THIN);
     style2.setBorderTop(BorderStyle.THIN);
     style2.setBorderBottom(BorderStyle.THIN);
     
     HSSFCellStyle style1 = hwb.createCellStyle();
     style1.setAlignment(HorizontalAlignment.CENTER);
     style1.setVerticalAlignment( 
    		 VerticalAlignment.TOP);
    
     HSSFFont font1 = hwb.createFont();
     font1.setFontHeightInPoints((short) 10);
 	  style1.setFont(font1);
     
     style1.setBorderLeft(BorderStyle.THIN);
     style1.setBorderRight(BorderStyle.THIN);
     style1.setBorderTop(BorderStyle.THIN);
     style1.setBorderBottom(BorderStyle.THIN);
    
     
     HSSFPrintSetup ps = sheet.getPrintSetup();
		sheet.setAutobreaks(true);
		ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		//ps.setLandscape(layout.equalsIgnoreCase("L"));
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
		sheet.setHorizontallyCenter(true);
	
	
	  HSSFRow rowhead=   sheet.createRow((short) 0);
	     HSSFCell cell0 = rowhead.createCell(0);
	     HSSFCell cell1 = rowhead.createCell(1);
	     HSSFCell cell2 = rowhead.createCell(2);
	     HSSFCell cell3 = rowhead.createCell(3);
	     HSSFCell cell4 = rowhead.createCell(4);
	     HSSFCell cell5 = rowhead.createCell(5);
	     HSSFCell cell6 = rowhead.createCell(6);


	  cell0.setCellValue("Subject Code");
	  cell1.setCellValue("Pending");
	  cell2.setCellValue("Under Process");
	  cell3.setCellValue("Complied");
	  cell4.setCellValue("Not Feasable");
	  cell5.setCellValue("Partially Complied");
	  cell6.setCellValue("Total");

	  cell0.setCellStyle(style2);
	 cell1.setCellStyle(style2);
	 cell2.setCellStyle(style2);
	 cell3.setCellStyle(style2);
	 cell4.setCellStyle(style2);
	 cell5.setCellStyle(style2);
	 cell6.setCellStyle(style2);
	

	  try {	  
	  DBConnection con = new DBConnection();
	  con.openConnection();
		// String strSQL = "select referencename, addviptype, sum(refcount) as refcount, sum(complied) as complied, sum(partiallycomp) as partiallycomp,sum(notfeasible) as notfeasible from(select referencename,addviptype,1 refcount,decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'E',1,0) partiallycomp,decode(NVL(compliance,'A'),'D',1,0) notfeasible from trnreference where addviptype in ('LS','RS','MIN','MOS','PM') and tenureid=12 and incomingdate>to_date('10/11/2014','dd/mm/yyyy')and addvipid<>'820' and statecode= '"+state+"' order by 1)group by referencename,addviptype order by 1";
		String strSQL = "select NVL(subjectcodeform,'TOTAL'),sum(pending) as pending,sum(UE) as up,sum(complied) as cmp,sum(NF) as nf,sum(PC) as pc ,sum(pending)+sum(UE)+sum(complied)+sum(NF)+sum(PC) total, (Select subjectname from mstsubject where subjectcode=subjectcodeform) subjectdesc from "
			+ "(select refid,NVL(subjectcode,'OTR') subjectcodeform,decode(NVL(compliance,'A'),'A',1,0) pending,decode(NVL(compliance,'A'),'B',1,0) UE, "
			+ "decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'D',1,0) NF, "
			+ "decode(NVL(compliance,'A'),'E',1,0) PC "
			+ "from trnreference  where FORWARDTOSUBHEAD ='"+loginId+"' ) "
			+ "group by cube(subjectcodeform) order by NVL(subjectcodeform,'ZTOTAL') ";

		log.debug(strSQL);
		ResultSet rs2=con.select(strSQL);

		int i=2,j=1;
	int count =0;
	while(rs2.next())
	{
		count=count+1;
	}
	rs2.close();

	//System.out.println("Number of records in ResultSet: " + count);
	
	ResultSet rs=con.select(strSQL);	  
	  while(rs.next()){
		  
	  HSSFRow row=   sheet.createRow((short)i);
	  
	if(j<count)
	{
	  createCell(hwb, row, (short) (0),style1, rs.getString("subjectdesc"));
		createCell(hwb, row, (short) (1),style1, String.valueOf(rs.getInt("pending")));
	      createCell(hwb, row, (short) (2),style1, String.valueOf(rs.getInt("up")));
			createCell(hwb, row, (short) (3),style1, String.valueOf(rs.getInt("cmp")));
		      createCell(hwb, row, (short) (4),style1, String.valueOf(rs.getInt("nf")));
				createCell(hwb, row, (short) (5),style1, String.valueOf(rs.getInt("pc")));
				createCell(hwb, row, (short) (6),style2, String.valueOf(rs.getInt("total")));
	}
	else
	{
		createCell(hwb, row, (short) (0),style2, "Total");
		createCell(hwb, row, (short) (1),style2, String.valueOf(rs.getInt("pending")));
	      createCell(hwb, row, (short) (2),style2, String.valueOf(rs.getInt("up")));
			createCell(hwb, row, (short) (3),style2, String.valueOf(rs.getInt("cmp")));
		      createCell(hwb, row, (short) (4),style2, String.valueOf(rs.getInt("nf")));
				createCell(hwb, row, (short) (5),style2, String.valueOf(rs.getInt("pc")));
				createCell(hwb, row, (short) (6),style2, String.valueOf(rs.getInt("total")));
	
	}
		i++;
		j++;
	  }
	  
	  }
	  catch(Exception e)
	  {
		  log.error(e);
	  }
return hwb;
	}




public	HSSFWorkbook getExcelDataHOD(String st){
	String loginId=st;
	HSSFWorkbook hwb=new HSSFWorkbook();
 	  HSSFSheet sheet =  hwb.createSheet("Reference Subject Wise");
		sheet.setColumnWidth(0, 8000);
     sheet.setColumnWidth(1, 2000);
     sheet.setColumnWidth(2, 3500);
     sheet.setColumnWidth(3, 3000);
     sheet.setColumnWidth(4, 7000);
     sheet.setColumnWidth(5, 7000);
     sheet.setColumnWidth(6, 3000);
     /*
     sheet.addMergedRegion(new CellRangeAddress(
    	      0, //first row (0-based)
    	      0, //last row (0-based)
    	      4, //first column (0-based)
    	      6 //last column (0-based)
    	      ));
     */
     HSSFCellStyle style2 = hwb.createCellStyle();
     style2.setAlignment(HorizontalAlignment.CENTER);
     style2.setVerticalAlignment( 
    		 VerticalAlignment.CENTER);
    
     HSSFFont font = hwb.createFont();
     font.setFontHeightInPoints((short) 10);
 	 font.setBold(true);
     style2.setFont(font);
     
     style2.setBorderLeft(BorderStyle.THIN);
     style2.setBorderRight(BorderStyle.THIN);
     style2.setBorderTop(BorderStyle.THIN);
     style2.setBorderBottom(BorderStyle.THIN);
     
     HSSFCellStyle style1 = hwb.createCellStyle();
     style1.setAlignment(HorizontalAlignment.CENTER);
     style1.setVerticalAlignment( 
    		 VerticalAlignment.CENTER);
    
     HSSFFont font1 = hwb.createFont();
     font1.setFontHeightInPoints((short) 10);
 	  style1.setFont(font1);
     
     style1.setBorderLeft(BorderStyle.THIN);
     style1.setBorderRight(BorderStyle.THIN);
     style1.setBorderTop(BorderStyle.THIN);
     style1.setBorderBottom(BorderStyle.THIN);
    
     
     HSSFPrintSetup ps = sheet.getPrintSetup();
		sheet.setAutobreaks(true);
		ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		//ps.setLandscape(layout.equalsIgnoreCase("L"));
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
		sheet.setHorizontallyCenter(true);
	
	
	  HSSFRow rowhead=   sheet.createRow((short) 0);
	     HSSFCell cell0 = rowhead.createCell(0);
	     HSSFCell cell1 = rowhead.createCell(1);
	     HSSFCell cell2 = rowhead.createCell(2);
	     HSSFCell cell3 = rowhead.createCell(3);
	     HSSFCell cell4 = rowhead.createCell(4);
	     HSSFCell cell5 = rowhead.createCell(5);
	     HSSFCell cell6 = rowhead.createCell(6);


	  cell0.setCellValue("Subject Code");
	  cell1.setCellValue("Pending");
	  cell2.setCellValue("Under Process");
	  cell3.setCellValue("Complied");
	  cell4.setCellValue("Not Feasable");
	  cell5.setCellValue("Partially Complied");
	  cell6.setCellValue("Total");

	  cell0.setCellStyle(style2);
	 cell1.setCellStyle(style2);
	 cell2.setCellStyle(style2);
	 cell3.setCellStyle(style2);
	 cell4.setCellStyle(style2);
	 cell5.setCellStyle(style2);
	 cell6.setCellStyle(style2);
	

	  try {	  
	  DBConnection con = new DBConnection();
	  con.openConnection();
		// String strSQL = "select referencename, addviptype, sum(refcount) as refcount, sum(complied) as complied, sum(partiallycomp) as partiallycomp,sum(notfeasible) as notfeasible from(select referencename,addviptype,1 refcount,decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'E',1,0) partiallycomp,decode(NVL(compliance,'A'),'D',1,0) notfeasible from trnreference where addviptype in ('LS','RS','MIN','MOS','PM') and tenureid=12 and incomingdate>to_date('10/11/2014','dd/mm/yyyy')and addvipid<>'820' and statecode= '"+state+"' order by 1)group by referencename,addviptype order by 1";
		String strSQL = "select NVL(subjectcodeform,'TOTAL') as sub,sum(pending) as pending ,sum(UE) as up, sum(complied) as cmp,sum(NF) as nf,sum(PC) as pc,sum(pending)+sum(UE)+sum(complied)+sum(NF)+sum(PC) total, (Select subjectname from mstsubject where subjectcode=subjectcodeform) subjectdesc from "
				+ "(select refid,NVL(subjectcode,'OTR') subjectcodeform,decode(NVL(compliance,'A'),'A',1,0) pending,decode(NVL(compliance,'A'),'B',1,0) UE, "
				+ "decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'D',1,0) NF, "
				+ "decode(NVL(compliance,'A'),'E',1,0) PC "
				+ "from trnreference where refid in ( "
				+ "select refid from trnmarking where MARKINGTO in ( "
				+ "select roleid from mstrole where deptcode in ( "
				+ "(select deptcode from mstdept where hod=(select roleid from mstrole where roleid = '"+loginId+"'))) "
				+ "and refid in (select refid from trnreference where  tenureid>=12 and refclass in ('A','M')) "
				+ ")) ) "
				+ "group by cube(subjectcodeform) order by NVL(subjectcodeform,'ZTOTAL') ";

		
		ResultSet rs2=con.select(strSQL);

		int i=2,j=1;
	int count =0;
	while(rs2.next())
	{
		count=count+1;
	}
	rs2.close();

	//System.out.println("Number of records in ResultSet: " + count);
	
	ResultSet rs=con.select(strSQL);	  
	  while(rs.next()){
		  
	  HSSFRow row=   sheet.createRow((short)i);
	  
	if(j<count)
	{
	  createCell(hwb, row, (short) (0),style1, rs.getString("subjectdesc"));
		createCell(hwb, row, (short) (1),style1, String.valueOf(rs.getInt("pending")));
	      createCell(hwb, row, (short) (2),style1, String.valueOf(rs.getInt("up")));
			createCell(hwb, row, (short) (3),style1, String.valueOf(rs.getInt("cmp")));
		      createCell(hwb, row, (short) (4),style1, String.valueOf(rs.getInt("nf")));
				createCell(hwb, row, (short) (5),style1, String.valueOf(rs.getInt("pc")));
				createCell(hwb, row, (short) (6),style2, String.valueOf(rs.getInt("total")));
	}
	else
	{
		createCell(hwb, row, (short) (0),style2, "Total");
		createCell(hwb, row, (short) (1),style2, String.valueOf(rs.getInt("pending")));
	      createCell(hwb, row, (short) (2),style2, String.valueOf(rs.getInt("up")));
			createCell(hwb, row, (short) (3),style2, String.valueOf(rs.getInt("cmp")));
		      createCell(hwb, row, (short) (4),style2, String.valueOf(rs.getInt("nf")));
				createCell(hwb, row, (short) (5),style2, String.valueOf(rs.getInt("pc")));
				createCell(hwb, row, (short) (6),style2, String.valueOf(rs.getInt("total")));
	
	}
		i++;
		j++;
	  }
	  
	  }
	  catch(Exception e)
	  {
		  log.error(e);
	  }
return hwb;
	}

public String generateDashboardSubjectWiseDetailSubRailway(String serverpath,String subjectCode,String loginID,String status ) {
	 int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "D:\\";

		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		try {
			
			//String[] Refclass ={"A","B","E","C"};
			MastersReportBean bean = null;
			
			//ArrayList arrDT = new ArrayList();
			//ArrayList arr = new ArrayList();
			String headername = "Detail List SubjectWise";

			// Create New Workbook
			HSSFWorkbook wb = new HSSFWorkbook();
			// Define Styles to be used for PageHeader and CellHeader and
			// DataCells

			HSSFCellStyle style_cell = wb.createCellStyle();
			//style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style_cell.setBorderBottom(BorderStyle.THIN);
			style_cell.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderLeft(BorderStyle.THIN);
			style_cell.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderRight(BorderStyle.THIN);
			style_cell.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderTop(BorderStyle.THIN);
			style_cell.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell.setAlignment(HorizontalAlignment.CENTER);
			style_cell.setWrapText(true);
			HSSFCellStyle style_cell_num = wb.createCellStyle();
			style_cell_num.setAlignment(HorizontalAlignment.RIGHT);
			style_cell_num.setBorderBottom(BorderStyle.THIN);
			style_cell_num.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderLeft(BorderStyle.THIN);
			style_cell_num.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderRight(BorderStyle.THIN);
			style_cell_num.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderTop(BorderStyle.THIN);
			style_cell_num.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setWrapText(true);
			HSSFCellStyle style_pageHeader = wb.createCellStyle();
			style_pageHeader.setAlignment(HorizontalAlignment.CENTER);

			HSSFCellStyle style_header = wb.createCellStyle();
			style_header
					.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style_header.setAlignment(HorizontalAlignment.CENTER);
			style_header.setBorderBottom(BorderStyle.THIN);
			style_header.setBottomBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderLeft(BorderStyle.THIN);
			style_header.setLeftBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderRight(BorderStyle.THIN);
			style_header.setRightBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderTop(BorderStyle.THIN);
			style_header.setTopBorderColor(HSSFColor.BLACK.index);
			style_header.setWrapText(true);

			// Fonts for PageHeader and CellHeaders
			HSSFFont font_pageHeader = wb.createFont();
			font_pageHeader.setFontName("Arial");
			font_pageHeader.setFontHeightInPoints((short) 10);
			font_pageHeader.setBold(true);
			font_pageHeader.setColor(HSSFColor.BLACK.index);

			HSSFFont font_Header = wb.createFont();
			font_Header.setFontHeightInPoints((short) 10);
			font_Header.setBold(true);
			
			HSSFFont font_cell = wb.createFont();
			font_cell.setFontHeightInPoints((short) 10);
			//font_cell.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			
			
			///--------loopp-----------------------------------------------------///
			
			//for (int index = 0; index < Refclass.length; index++) {
				ArrayList<CommonBean> cm =new ArrayList();	
				
				
				Date date = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(date);
			    
				
				headername = "Detail List of Dashboard" +strDate;
				String[] arrh = {"S.No","Reference Number \n Incoming Date","Reference Date"," Name","Subject Code","Subject","Marking \n To Marking \n Date","Remarks \n Signed By \n Signed On","File No.","Compliance","Compliance Remarks"};
				HSSFSheet sheet = wb.createSheet("Detail List of Dashboard");
				sheet.setColumnWidth(0, 2000);
		        sheet.setColumnWidth(1, 5000);
		        sheet.setColumnWidth(2, 3000);
		        sheet.setColumnWidth(3, 5000);
		        sheet.setColumnWidth(4, 3000);
		        sheet.setColumnWidth(5, 6000);
		        sheet.setColumnWidth(6, 5000);
		        sheet.setColumnWidth(7, 5000);
		        sheet.setColumnWidth(8, 5000);
		        sheet.setColumnWidth(9, 5000);
		        sheet.setColumnWidth(10, 5000);
		        sheet.setColumnWidth(11, 5000);
		        //sheet.setColumnWidth(12, 5000);
		        //sheet.setColumnWidth(13, 5000);
		        
		        
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				sheet.getPrintSetup().setLandscape(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				cm =getRefDashBoardDetailListRailway(subjectCode,loginID,status);
				
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;				
				
				HSSFRichTextString string;
				HSSFRichTextString s1;
				HSSFRichTextString s2;
				HSSFRichTextString s3;
				
				HSSFRow row = sheet.createRow(rnum++);
				row.setHeightInPoints(40);
				for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				
				int sno =1;
				
				
				for (int i = 0; i < cm.size(); i++) {
					
					row = sheet.createRow(rnum++);
					string = new HSSFRichTextString(sno+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField1().toString()+"\n"+cm.get(i).getField2().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField3().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (2),
							style_cell, string);
					
				
					string = new HSSFRichTextString(cm.get(i).getField4().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (3),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField18().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (4),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField5().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (5),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField6().toString()+"\n"+cm.get(i).getField7().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (6),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField8().toString()+"\n"+cm.get(i).getField9().toString()+"\n"+cm.get(i).getField10().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (7),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField13().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (8),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField14().toString().equalsIgnoreCase("")?"":cm.get(i).getField14().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (9),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField15().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (10),
							style_cell, string);
//					
//					string = new HSSFRichTextString("");
//					string.applyFont(font_cell);
//					createCell(wb, row, (short) (11),
//							style_cell, string);
//				
//					string = new HSSFRichTextString("");
//					string.applyFont(font_cell);
//					createCell(wb, row, (short) (12),
//							style_cell, string);
					
					
					row.setHeightInPoints(60);
				sno++;
			}
			
			//row = sheet.createRow(rnum++);
				FileOutputStream fileOut = new FileOutputStream(FilePath);

				wb.write(fileOut);
				fileOut.close();		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;



}


public String generateDashboardSubjectWiseDetailHODSub(String serverpath,String subjectCode,String loginID,String status ) {
	 int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "D:\\";

		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		try {
			
			//String[] Refclass ={"A","B","E","C"};
			MastersReportBean bean = null;
			
			//ArrayList arrDT = new ArrayList();
			//ArrayList arr = new ArrayList();
			String headername = "Detail List SubjectWise";

			// Create New Workbook
			HSSFWorkbook wb = new HSSFWorkbook();
			// Define Styles to be used for PageHeader and CellHeader and
			// DataCells

			HSSFCellStyle style_cell = wb.createCellStyle();
			//style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style_cell.setBorderBottom(BorderStyle.THIN);
			style_cell.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderLeft(BorderStyle.THIN);
			style_cell.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderRight(BorderStyle.THIN);
			style_cell.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderTop(BorderStyle.THIN);
			style_cell.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell.setAlignment(HorizontalAlignment.CENTER);
			style_cell.setWrapText(true);
			HSSFCellStyle style_cell_num = wb.createCellStyle();
			style_cell_num.setAlignment(HorizontalAlignment.RIGHT);
			style_cell_num.setBorderBottom(BorderStyle.THIN);
			style_cell_num.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderLeft(BorderStyle.THIN);
			style_cell_num.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderRight(BorderStyle.THIN);
			style_cell_num.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderTop(BorderStyle.THIN);
			style_cell_num.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setWrapText(true);
			HSSFCellStyle style_pageHeader = wb.createCellStyle();
			style_pageHeader.setAlignment(HorizontalAlignment.CENTER);

			HSSFCellStyle style_header = wb.createCellStyle();
			style_header
					.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style_header.setAlignment(HorizontalAlignment.CENTER);
			style_header.setBorderBottom(BorderStyle.THIN);
			style_header.setBottomBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderLeft(BorderStyle.THIN);
			style_header.setLeftBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderRight(BorderStyle.THIN);
			style_header.setRightBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderTop(BorderStyle.THIN);
			style_header.setTopBorderColor(HSSFColor.BLACK.index);
			style_header.setWrapText(true);

			// Fonts for PageHeader and CellHeaders
			HSSFFont font_pageHeader = wb.createFont();
			font_pageHeader.setFontName("Arial");
			font_pageHeader.setFontHeightInPoints((short) 10);
			font_pageHeader.setBold(true);
			font_pageHeader.setColor(HSSFColor.BLACK.index);

			HSSFFont font_Header = wb.createFont();
			font_Header.setFontHeightInPoints((short) 10);
			font_Header.setBold(true);
			
			HSSFFont font_cell = wb.createFont();
			font_cell.setFontHeightInPoints((short) 10);
			//font_cell.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			
			
			///--------loopp-----------------------------------------------------///
			
			//for (int index = 0; index < Refclass.length; index++) {
				ArrayList<CommonBean> cm =new ArrayList();	
				
				
				Date date = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(date);
			    
				
				headername = "Detail List of Dashboard" +strDate;
				String[] arrh = {"S.No","Reference Number \n Incoming Date","Reference Date"," Name","Subject Code","Subject","Marking \n To Marking \n Date","Remarks \n Signed By \n Signed On","File No.","Compliance","Compliance Remarks"};
				HSSFSheet sheet = wb.createSheet("Detail List of Dashboard");
				sheet.setColumnWidth(0, 2000);
		        sheet.setColumnWidth(1, 5000);
		        sheet.setColumnWidth(2, 3000);
		        sheet.setColumnWidth(3, 5000);
		        sheet.setColumnWidth(4, 3000);
		        sheet.setColumnWidth(5, 6000);
		        sheet.setColumnWidth(6, 5000);
		        sheet.setColumnWidth(7, 5000);
		        sheet.setColumnWidth(8, 5000);
		        sheet.setColumnWidth(9, 5000);
		        sheet.setColumnWidth(10, 5000);
		        sheet.setColumnWidth(11, 5000);
		        //sheet.setColumnWidth(12, 5000);
		        //sheet.setColumnWidth(13, 5000);
		        
		        
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				sheet.getPrintSetup().setLandscape(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				cm =getRefDashBoardDetailListHODSubHead(subjectCode,loginID,status);
				
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;				
				
				HSSFRichTextString string;
				HSSFRichTextString s1;
				HSSFRichTextString s2;
				HSSFRichTextString s3;
				
				HSSFRow row = sheet.createRow(rnum++);
				row.setHeightInPoints(40);
				for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				
				int sno =1;
				
				
				for (int i = 0; i < cm.size(); i++) {
					
					row = sheet.createRow(rnum++);
					string = new HSSFRichTextString(sno+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField1().toString()+"\n"+cm.get(i).getField2().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField3().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (2),
							style_cell, string);
					
				
					string = new HSSFRichTextString(cm.get(i).getField4().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (3),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField18().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (4),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField5().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (5),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField6().toString()+"\n"+cm.get(i).getField7().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (6),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField8().toString()+"\n"+cm.get(i).getField9().toString()+"\n"+cm.get(i).getField10().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (7),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField13().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (8),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField14().toString().equalsIgnoreCase("")?"":cm.get(i).getField14().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (9),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField15().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (10),
							style_cell, string);
//					
//					string = new HSSFRichTextString("");
//					string.applyFont(font_cell);
//					createCell(wb, row, (short) (11),
//							style_cell, string);
//				
//					string = new HSSFRichTextString("");
//					string.applyFont(font_cell);
//					createCell(wb, row, (short) (12),
//							style_cell, string);
					
					
					row.setHeightInPoints(60);
				sno++;
			}
			
			//row = sheet.createRow(rnum++);
				FileOutputStream fileOut = new FileOutputStream(FilePath);

				wb.write(fileOut);
				fileOut.close();		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;



}


public String generateDashboardSubjectWiseDetailHOD(String serverpath,String subjectCode,String loginID,String status ) {
	 int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "D:\\";

		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		try {
			
			//String[] Refclass ={"A","B","E","C"};
			MastersReportBean bean = null;
			
			//ArrayList arrDT = new ArrayList();
			//ArrayList arr = new ArrayList();
			String headername = "Detail List SubjectWise";

			// Create New Workbook
			HSSFWorkbook wb = new HSSFWorkbook();
			// Define Styles to be used for PageHeader and CellHeader and
			// DataCells

			HSSFCellStyle style_cell = wb.createCellStyle();
			//style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style_cell.setBorderBottom(BorderStyle.THIN);
			style_cell.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderLeft(BorderStyle.THIN);
			style_cell.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderRight(BorderStyle.THIN);
			style_cell.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderTop(BorderStyle.THIN);
			style_cell.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell.setAlignment(HorizontalAlignment.CENTER);
			style_cell.setWrapText(true);
			HSSFCellStyle style_cell_num = wb.createCellStyle();
			style_cell_num.setAlignment(HorizontalAlignment.RIGHT);
			style_cell_num.setBorderBottom(BorderStyle.THIN);
			style_cell_num.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderLeft(BorderStyle.THIN);
			style_cell_num.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderRight(BorderStyle.THIN);
			style_cell_num.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderTop(BorderStyle.THIN);
			style_cell_num.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setWrapText(true);
			HSSFCellStyle style_pageHeader = wb.createCellStyle();
			style_pageHeader.setAlignment(HorizontalAlignment.CENTER);

			HSSFCellStyle style_header = wb.createCellStyle();
			style_header
					.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style_header.setAlignment(HorizontalAlignment.CENTER);
			style_header.setBorderBottom(BorderStyle.THIN);
			style_header.setBottomBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderLeft(BorderStyle.THIN);
			style_header.setLeftBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderRight(BorderStyle.THIN);
			style_header.setRightBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderTop(BorderStyle.THIN);
			style_header.setTopBorderColor(HSSFColor.BLACK.index);
			style_header.setWrapText(true);

			// Fonts for PageHeader and CellHeaders
			HSSFFont font_pageHeader = wb.createFont();
			font_pageHeader.setFontName("Arial");
			font_pageHeader.setFontHeightInPoints((short) 10);
			font_pageHeader.setBold(true);
			font_pageHeader.setColor(HSSFColor.BLACK.index);

			HSSFFont font_Header = wb.createFont();
			font_Header.setFontHeightInPoints((short) 10);
			font_Header.setBold(true);
			
			HSSFFont font_cell = wb.createFont();
			font_cell.setFontHeightInPoints((short) 10);
			//font_cell.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			
			
			///--------loopp-----------------------------------------------------///
			
			//for (int index = 0; index < Refclass.length; index++) {
				ArrayList<CommonBean> cm =new ArrayList();	
				
				
				Date date = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(date);
			    
				
				headername = "Detail List of Dashboard" +strDate;
				String[] arrh = {"S.No","Reference Number \n Incoming Date","Reference Date"," Name","Subject Code","Subject","Marking \n To Marking \n Date","Remarks \n Signed By \n Signed On","File No.","Compliance","Compliance Remarks"};
				HSSFSheet sheet = wb.createSheet("Detail List of Dashboard");
				sheet.setColumnWidth(0, 2000);
		        sheet.setColumnWidth(1, 5000);
		        sheet.setColumnWidth(2, 3000);
		        sheet.setColumnWidth(3, 5000);
		        sheet.setColumnWidth(4, 3000);
		        sheet.setColumnWidth(5, 6000);
		        sheet.setColumnWidth(6, 5000);
		        sheet.setColumnWidth(7, 5000);
		        sheet.setColumnWidth(8, 5000);
		        sheet.setColumnWidth(9, 5000);
		        sheet.setColumnWidth(10, 5000);
		        sheet.setColumnWidth(11, 5000);
		        //sheet.setColumnWidth(12, 5000);
		        //sheet.setColumnWidth(13, 5000);
		        
		        
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				sheet.getPrintSetup().setLandscape(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				cm =getRefDashBoardDetailListHOD(subjectCode,loginID,status);
				
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;				
				
				HSSFRichTextString string;
				HSSFRichTextString s1;
				HSSFRichTextString s2;
				HSSFRichTextString s3;
				
				HSSFRow row = sheet.createRow(rnum++);
				row.setHeightInPoints(40);
				for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				
				int sno =1;
				
				
				for (int i = 0; i < cm.size(); i++) {
					
					row = sheet.createRow(rnum++);
					string = new HSSFRichTextString(sno+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField1().toString()+"\n"+cm.get(i).getField2().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField3().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (2),
							style_cell, string);
					
				
					string = new HSSFRichTextString(cm.get(i).getField4().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (3),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField18().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (4),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField5().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (5),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField6().toString()+"\n"+cm.get(i).getField7().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (6),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField8().toString()+"\n"+cm.get(i).getField9().toString()+"\n"+cm.get(i).getField10().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (7),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField13().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (8),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField14().toString().equalsIgnoreCase("")?"":cm.get(i).getField14().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (9),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField15().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (10),
							style_cell, string);
//					
//					string = new HSSFRichTextString("");
//					string.applyFont(font_cell);
//					createCell(wb, row, (short) (11),
//							style_cell, string);
//				
//					string = new HSSFRichTextString("");
//					string.applyFont(font_cell);
//					createCell(wb, row, (short) (12),
//							style_cell, string);
					
					
					row.setHeightInPoints(60);
				sno++;
			}
			
			//row = sheet.createRow(rnum++);
				FileOutputStream fileOut = new FileOutputStream(FilePath);

				wb.write(fileOut);
				fileOut.close();		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;



}


//Swati code

public ArrayList<CommonBean> getRefDashBoardDetailListRoleWise(String RoleId,String loginID,String status){
	
	
	
	
	
	String strSQL = "select a.REFNO, TO_CHAR(a.incomingdate,'DD/MM/YYYY'),TO_CHAR(a.letterdate,'DD/MM/YYYY'),a.referencename, SUBJECTCODE,subject, "
			+ "(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=a.REFID AND M.MARKINGSEQUENCE=1)) markingto, "
			+ "(select TO_CHAR(b.markingdate,'DD/MM/YYYY')  from trnmarking b where refid=a.refid) markingdate,Remarks, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.SIGNEDBY) SIGNEDBY, "
			+ "TO_CHAR(SIGNEDON,'DD/MM/YYYY'), (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.ACKBY)ACKBY,  TO_CHAR(ACKDATE,'DD/MM/YYYY'),fileno,(SELECT FNAME FROM MSTGEC  "
			+ "WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = a.COMPLIANCE) compliancedesc,a.COMPLIANCEREMARKS, "
			+ "a.COMPLIANCE,a.refid,a.subjectcode,a.forwardtosubhead "
			+ "from trnreference a where refid in ( select refid from trnmarking where MARKINGTO ='"+RoleId+"') "
			+ "and refid in (select refid from trnreference  where  tenureid>=12 and refclass in ('A','M'))";
	
	
	
	//System.out.println("Queary-----------"+strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 20);

	
}


public String generateDashboardRoleWiseDetail(String serverpath,String RoleId,String loginID,String status ) {
 int ran = (int) (1000000 * Math.random());
	String Directory = serverpath;
	//String Directory = "D:\\";

	String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
	try {
		
		//String[] Refclass ={"A","B","E","C"};
		MastersReportBean bean = null;
		
		//ArrayList arrDT = new ArrayList();
		//ArrayList arr = new ArrayList();
		String headername = "Detail List RoleWise";

		// Create New Workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		// Define Styles to be used for PageHeader and CellHeader and
		// DataCells

		HSSFCellStyle style_cell = wb.createCellStyle();
		//style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style_cell.setBorderBottom(BorderStyle.THIN);
		style_cell.setBottomBorderColor(HSSFColor.BLACK.index);
		style_cell.setBorderLeft(BorderStyle.THIN);
		style_cell.setLeftBorderColor(HSSFColor.BLACK.index);
		style_cell.setBorderRight(BorderStyle.THIN);
		style_cell.setRightBorderColor(HSSFColor.BLACK.index);
		style_cell.setBorderTop(BorderStyle.THIN);
		style_cell.setTopBorderColor(HSSFColor.BLACK.index);
		style_cell.setAlignment(HorizontalAlignment.CENTER);
		style_cell.setWrapText(true);
		HSSFCellStyle style_cell_num = wb.createCellStyle();
		style_cell_num.setAlignment(HorizontalAlignment.RIGHT);
		style_cell_num.setBorderBottom(BorderStyle.THIN);
		style_cell_num.setBottomBorderColor(HSSFColor.BLACK.index);
		style_cell_num.setBorderLeft(BorderStyle.THIN);
		style_cell_num.setLeftBorderColor(HSSFColor.BLACK.index);
		style_cell_num.setBorderRight(BorderStyle.THIN);
		style_cell_num.setRightBorderColor(HSSFColor.BLACK.index);
		style_cell_num.setBorderTop(BorderStyle.THIN);
		style_cell_num.setTopBorderColor(HSSFColor.BLACK.index);
		style_cell_num.setWrapText(true);
		HSSFCellStyle style_pageHeader = wb.createCellStyle();
		style_pageHeader.setAlignment(HorizontalAlignment.CENTER);

		HSSFCellStyle style_header = wb.createCellStyle();
		style_header
				.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style_header.setAlignment(HorizontalAlignment.CENTER);
		style_header.setBorderBottom(BorderStyle.THIN);
		style_header.setBottomBorderColor(HSSFColor.BLACK.index);
		style_header.setBorderLeft(BorderStyle.THIN);
		style_header.setLeftBorderColor(HSSFColor.BLACK.index);
		style_header.setBorderRight(BorderStyle.THIN);
		style_header.setRightBorderColor(HSSFColor.BLACK.index);
		style_header.setBorderTop(BorderStyle.THIN);
		style_header.setTopBorderColor(HSSFColor.BLACK.index);
		style_header.setWrapText(true);

		// Fonts for PageHeader and CellHeaders
		HSSFFont font_pageHeader = wb.createFont();
		font_pageHeader.setFontName("Arial");
		font_pageHeader.setFontHeightInPoints((short) 10);
		font_pageHeader.setBold(true);
		font_pageHeader.setColor(HSSFColor.BLACK.index);

		HSSFFont font_Header = wb.createFont();
		font_Header.setFontHeightInPoints((short) 10);
		font_Header.setBold(true);
		
		HSSFFont font_cell = wb.createFont();
		font_cell.setFontHeightInPoints((short) 10);
		//font_cell.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		
		
		///--------loopp-----------------------------------------------------///
		
		//for (int index = 0; index < Refclass.length; index++) {
			ArrayList<CommonBean> cm =new ArrayList();	
			
			
			Date date = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    String strDate = sdf.format(date);
		    
			
			headername = "Detail List of Dashboard" +strDate;
			String[] arrh = {"S.No","Reference Number \n Incoming Date","Reference Date"," Name","Subject Code","Subject","Marking \n To Marking \n Date","Remarks \n Signed By \n Signed On","File No.","Compliance","Compliance Remarks"};
			HSSFSheet sheet = wb.createSheet("Detail List of Role Wise Dashboard");
			sheet.setColumnWidth(0, 2000);
	        sheet.setColumnWidth(1, 5000);
	        sheet.setColumnWidth(2, 3000);
	        sheet.setColumnWidth(3, 5000);
	        
	        sheet.setColumnWidth(4, 3000);
	        sheet.setColumnWidth(5, 6000);
	        sheet.setColumnWidth(6, 5000);
	        sheet.setColumnWidth(7, 5000);
	        sheet.setColumnWidth(8, 5000);
	        sheet.setColumnWidth(9, 5000);
	        sheet.setColumnWidth(10, 5000);
	        sheet.setColumnWidth(11, 5000);
	        		        //sheet.setColumnWidth(12, 5000);
	        //sheet.setColumnWidth(13, 5000);
	        
	        
			HSSFPrintSetup ps = sheet.getPrintSetup();
			sheet.setAutobreaks(true);
			sheet.getPrintSetup().setLandscape(true);
			ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			sheet.setHorizontallyCenter(true);
			
			cm =getRefDashBoardDetailListRoleWise(RoleId,loginID,status);
			
			
			HSSFHeader header = sheet.getHeader();
			
			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);

			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);
			
			
			int rnum = 0;				
			
			HSSFRichTextString string;
			HSSFRichTextString s1;
			HSSFRichTextString s2;
			HSSFRichTextString s3;
			
			HSSFRow row = sheet.createRow(rnum++);
			row.setHeightInPoints(40);
			for (int i = 0; i < arrh.length; i++) {
				string = new HSSFRichTextString(arrh[i].toString()
						.replaceAll("&deg;", "xB0"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (i), style_header, string);
			

			}
			
			int sno =1;
			
			
			for (int i = 0; i < cm.size(); i++) {
				
				row = sheet.createRow(rnum++);
				string = new HSSFRichTextString(sno+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (0),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField1().toString()+"\n"+cm.get(i).getField2().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (1),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField3().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (2),
						style_cell, string);
				
			
				string = new HSSFRichTextString(cm.get(i).getField4().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (3),
						style_cell, string);
				
				
				
				string = new HSSFRichTextString(cm.get(i).getField5().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (4),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField6().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (5),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField7().toString()+"\n"+cm.get(i).getField8().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (6),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField9().toString()+"\n"+cm.get(i).getField10().toString()+"\n"+cm.get(i).getField11().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (7),
						style_cell, string);
				
				
				
				string = new HSSFRichTextString(cm.get(i).getField14().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (8),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField15().toString().equalsIgnoreCase("")?"":cm.get(i).getField15().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (9),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField16().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (10),
						style_cell, string);
//				
//				string = new HSSFRichTextString("");
//				string.applyFont(font_cell);
//				createCell(wb, row, (short) (11),
//						style_cell, string);
//			
//				string = new HSSFRichTextString("");
//				string.applyFont(font_cell);
//				createCell(wb, row, (short) (12),
//						style_cell, string);
				
				
				row.setHeightInPoints(60);
			sno++;
		}
		
		//row = sheet.createRow(rnum++);
			FileOutputStream fileOut = new FileOutputStream(FilePath);

			wb.write(fileOut);
			fileOut.close();		

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return FilePath;


}




public	HSSFWorkbook getExcelDataRoleWise(String st){
String loginId=st;

Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
String strDate = sdf.format(date);


String headername = "Role wise Detail "+strDate;



HSSFWorkbook hwb=new HSSFWorkbook();
	  HSSFSheet sheet =  hwb.createSheet("Reference List Role Wise");
	sheet.setColumnWidth(0, 8000);
 sheet.setColumnWidth(1, 5000);
 sheet.setColumnWidth(2, 3500);
 sheet.setColumnWidth(3, 3000);
// sheet.setColumnWidth(4, 7000);
 //sheet.setColumnWidth(5, 7000);
 //sheet.setColumnWidth(6, 3000);
 /*
 sheet.addMergedRegion(new CellRangeAddress(
	      0, //first row (0-based)
	      0, //last row (0-based)
	      4, //first column (0-based)
	      6 //last column (0-based)
	      ));
 */
 HSSFCellStyle style2 = hwb.createCellStyle();
 style2.setAlignment(HorizontalAlignment.CENTER);
 style2.setVerticalAlignment( 
 VerticalAlignment.CENTER);

 HSSFFont font = hwb.createFont();
 font.setFontHeightInPoints((short) 10);
	 font.setBold(true);
 style2.setFont(font);
 
 style2.setBorderLeft(BorderStyle.THIN);
 style2.setBorderRight(BorderStyle.THIN);
 style2.setBorderTop(BorderStyle.THIN);
 style2.setBorderBottom(BorderStyle.THIN);
 
 HSSFCellStyle style1 = hwb.createCellStyle();
 style1.setAlignment(HorizontalAlignment.CENTER);
 style1.setVerticalAlignment( 
 VerticalAlignment.CENTER);

 HSSFFont font1 = hwb.createFont();
 font1.setFontHeightInPoints((short) 10);
	  style1.setFont(font1);
 
 style1.setBorderLeft(BorderStyle.THIN);
 style1.setBorderRight(BorderStyle.THIN);
 style1.setBorderTop(BorderStyle.THIN);
 style1.setBorderBottom(BorderStyle.THIN);

 
 HSSFPrintSetup ps = sheet.getPrintSetup();
	sheet.setAutobreaks(true);
	ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
	//ps.setLandscape(layout.equalsIgnoreCase("L"));
	ps.setFitWidth((short)1);
	ps.setFitHeight((short)0);
	sheet.setHorizontallyCenter(true);


  HSSFRow rowhead=   sheet.createRow((short) 0);
     HSSFCell cell0 = rowhead.createCell(0);
     HSSFCell cell1 = rowhead.createCell(1);
    

 
  cell0.setCellValue("Role");
  cell1.setCellValue("Number of cases");
  
  cell0.setCellStyle(style2);
 cell1.setCellStyle(style2);
 

  try {	  
  DBConnection con = new DBConnection();
  con.openConnection();
	// String strSQL = "select referencename, addviptype, sum(refcount) as refcount, sum(complied) as complied, sum(partiallycomp) as partiallycomp,sum(notfeasible) as notfeasible from(select referencename,addviptype,1 refcount,decode(NVL(compliance,'A'),'C',1,0) complied,decode(NVL(compliance,'A'),'E',1,0) partiallycomp,decode(NVL(compliance,'A'),'D',1,0) notfeasible from trnreference where addviptype in ('LS','RS','MIN','MOS','PM') and tenureid=12 and incomingdate>to_date('10/11/2014','dd/mm/yyyy')and addvipid<>'820' and statecode= '"+state+"' order by 1)group by referencename,addviptype order by 1";
  String strSQL = "select NVL(markingto,9999999) markingto,NVL((select rolename from mstrole b where "
			+ "b.roleid=a.markingto),'TOTAL') sname ,count(refid) from trnmarking a where MARKINGTO in "
			+ "( select roleid from mstrole where deptcode in( "
			+ "(select deptcode from mstdept where hod=(select roleid from mstrole where roleid ='"+loginId+"' ))) "
			+ "and refid in (select refid from trnreference  where  "
			+ "tenureid>=12 and refclass in ('A','M'))) group by cube(markingto) order by sname";

	//System.out.println(strSQL);
	ResultSet rs2=con.select(strSQL);

	int i=1,j=1;
int count =0;
while(rs2.next())
{
	count=count+1;
}
rs2.close();

//System.out.println("Number of records in ResultSet: " + count);

ResultSet rs=con.select(strSQL);	  
  while(rs.next()){
	  
  HSSFRow row=   sheet.createRow((short)i);
  
if(j<count)
{
	
	createCell(hwb, row, (short) (0),style1, rs.getString("sname"));
      createCell(hwb, row, (short) (1),style1, String.valueOf(rs.getInt("count(refid)")));
		
}
else
{
	
	createCell(hwb, row, (short) (0),style2, "Total");
	createCell(hwb, row, (short) (1),style2, String.valueOf(rs.getInt("count(refid)")));
      
}
	i++;
	j++;
  }
  
  }
  catch(Exception e)
  {
	  log.error(e);
  }
return hwb;
}


public String generateStateCoordinatorDetailReport(String serverpath,
		String condSTATEFinal, String condVIPFinal ,String loginID) {
	 int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "D:\\";

		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		try {
			
			//String[] Refclass ={"A","B","E","C"};
			MastersReportBean bean = null;
			
			//ArrayList arrDT = new ArrayList();
			//ArrayList arr = new ArrayList();
			String headername = "Detail List SubjectWise";

			// Create New Workbook
			HSSFWorkbook wb = new HSSFWorkbook();
			// Define Styles to be used for PageHeader and CellHeader and
			// DataCells

			HSSFCellStyle style_cell = wb.createCellStyle();
			//style_cell.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style_cell.setBorderBottom(BorderStyle.THIN);
			style_cell.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderLeft(BorderStyle.THIN);
			style_cell.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderRight(BorderStyle.THIN);
			style_cell.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderTop(BorderStyle.THIN);
			style_cell.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell.setAlignment(HorizontalAlignment.CENTER);
			style_cell.setWrapText(true);
			HSSFCellStyle style_cell_num = wb.createCellStyle();
			style_cell_num.setAlignment(HorizontalAlignment.RIGHT);
			style_cell_num.setBorderBottom(BorderStyle.THIN);
			style_cell_num.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderLeft(BorderStyle.THIN);
			style_cell_num.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderRight(BorderStyle.THIN);
			style_cell_num.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderTop(BorderStyle.THIN);
			style_cell_num.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setWrapText(true);
			HSSFCellStyle style_pageHeader = wb.createCellStyle();
			style_pageHeader.setAlignment(HorizontalAlignment.CENTER);

			HSSFCellStyle style_header = wb.createCellStyle();
			style_header
					.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style_header.setAlignment(HorizontalAlignment.CENTER);
			style_header.setBorderBottom(BorderStyle.THIN);
			style_header.setBottomBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderLeft(BorderStyle.THIN);
			style_header.setLeftBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderRight(BorderStyle.THIN);
			style_header.setRightBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderTop(BorderStyle.THIN);
			style_header.setTopBorderColor(HSSFColor.BLACK.index);
			style_header.setWrapText(true);

			// Fonts for PageHeader and CellHeaders
			HSSFFont font_pageHeader = wb.createFont();
			font_pageHeader.setFontName("Arial");
			font_pageHeader.setFontHeightInPoints((short) 10);
			font_pageHeader.setBold(true);
			font_pageHeader.setColor(HSSFColor.BLACK.index);

			HSSFFont font_Header = wb.createFont();
			font_Header.setFontHeightInPoints((short) 10);
			font_Header.setBold(true);
			
			HSSFFont font_cell = wb.createFont();
			font_cell.setFontHeightInPoints((short) 10);
			//font_cell.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			
			
			///--------loopp-----------------------------------------------------///
			
			//for (int index = 0; index < Refclass.length; index++) {
				ArrayList<CommonBean> cm =new ArrayList();	
				
				
				Date date = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(date);
			    
				
				headername = "Detail List of Dashboard" +strDate;
				String[] arrh = {"S.No","Reference \n Number "," Incoming \n Date","Reference \n Date"," Name","Vip  \n Status","State","Subject  \n Code","Subject","Marking \n To"," Marking \n Date","Remarks \n Signed By \n Signed On","File No.","Compliance","Compliance \n Remarks"};
				HSSFSheet sheet = wb.createSheet("Detail List of Dashboard");
				sheet.setColumnWidth(0, 2000);
		        sheet.setColumnWidth(1, 4000);
		        sheet.setColumnWidth(2, 3000);
		        sheet.setColumnWidth(3, 3000);
		        
		        sheet.setColumnWidth(4, 5000);
		        sheet.setColumnWidth(5, 3000);
		        sheet.setColumnWidth(6, 3000);
		        sheet.setColumnWidth(7, 3000);
		        sheet.setColumnWidth(8, 7000);
		        sheet.setColumnWidth(9, 3000);
		        sheet.setColumnWidth(10, 3000);
		        sheet.setColumnWidth(11, 3000);
		        sheet.setColumnWidth(12, 3000);
		        sheet.setColumnWidth(12, 3000);
		        sheet.setColumnWidth(13, 3000);
		        sheet.setColumnWidth(14, 5000);
		        
		        
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				sheet.getPrintSetup().setLandscape(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				cm =getStateCoordinatorReport(condSTATEFinal,condVIPFinal,loginID);
				
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;				
				
				HSSFRichTextString string;
				HSSFRichTextString s1;
				HSSFRichTextString s2;
				HSSFRichTextString s3;
				
				HSSFRow row = sheet.createRow(rnum++);
				row.setHeightInPoints(40);
				for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				
				int sno =1;
				
				
				for (int i = 0; i < cm.size(); i++) {
					
					row = sheet.createRow(rnum++);
					string = new HSSFRichTextString(sno+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField1().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField2().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (2),
							style_cell, string);
					
				
					string = new HSSFRichTextString(cm.get(i).getField3().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (3),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField4().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (4),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (5),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField19().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (6),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField18().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (7),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField5().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (8),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField6().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (9),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField7().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (10),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField8().toString()+"\n"+cm.get(i).getField9().toString()+"\n"+cm.get(i).getField10().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (11),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField13().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (12),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField14().toString().equalsIgnoreCase("")?"":cm.get(i).getField14().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (13),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField15().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (14),
							style_cell, string);
//					
//					string = new HSSFRichTextString("");
//					string.applyFont(font_cell);
//					createCell(wb, row, (short) (11),
//							style_cell, string);
//				
//					string = new HSSFRichTextString("");
//					string.applyFont(font_cell);
//					createCell(wb, row, (short) (12),
//							style_cell, string);
					
					
					row.setHeightInPoints(60);
				sno++;
			}
			
			//row = sheet.createRow(rnum++);
				FileOutputStream fileOut = new FileOutputStream(FilePath);

				wb.write(fileOut);
				fileOut.close();		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;


	
}


private ArrayList<CommonBean> getStateCoordinatorReport(String condSTATEFinal,
		String condVIPFinal, String loginID) {
	String strSQL = "select a.REFNO, TO_CHAR(a.incomingdate,'DD/MM/YYYY'),TO_CHAR(a.letterdate,'DD/MM/YYYY'),a.referencename, subject, "
			+ "(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=a.REFID AND M.MARKINGSEQUENCE=1)) markingto, (select TO_CHAR(b.markingdate,'DD/MM/YYYY')  "
			+ "from trnmarking b where refid=a.refid) markingdate,Remarks, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.SIGNEDBY) SIGNEDBY,TO_CHAR(SIGNEDON,'DD/MM/YYYY'), (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = a.ACKBY)ACKBY,  "
			+ "TO_CHAR(ACKDATE,'DD/MM/YYYY'),fileno,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = a.COMPLIANCE) compliancedesc,a.COMPLIANCEREMARKS,a.COMPLIANCE,a.refid,a.subjectcode, (SELECT statename FROM mststate WHERE statecode = a.statecode) statename,a.vipstatus from trnreference a"
			+ " where  tenureid>=12 and "
			+ "refclass in ('A','M') "+condSTATEFinal +condVIPFinal;
	//System.out.println(strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 20);
	
}










}
