package in.org.cris.mrsectt.dao;
import in.org.cris.mrsectt.Beans.CommonBean;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MastersReportBean;

public class PMOfficeReportDAO {

	public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256; 
	 public static final int UNIT_OFFSET_LENGTH = 7; 
	 public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 }; 
	 public String generatePMofficeReport(String serverpath ,String cond) {
			// TODO Auto-generated method stub
			
			int ran = (int) (1000000 * Math.random());
			String Directory = serverpath;
			//String Directory = "D:\\";

			String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
			try {
				
				//String[] Refclass ={"A","B","E","C"};
				MastersReportBean bean = null;
				
				//ArrayList arrDT = new ArrayList();
				//ArrayList arr = new ArrayList();
				String headername = "PM Office Report";
				//System.out.println(" getReportData getReportData+++++++++++++++++++++"+cond);
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
				
				HSSFFont font_ENDcell = wb.createFont();
				font_ENDcell.setFontHeightInPoints((short) 10);
				font_ENDcell.setBold(true);
				font_ENDcell.setColor(HSSFColor.RED.index);


				HSSFFont font_Header = wb.createFont();
				font_Header.setFontHeightInPoints((short) 10);
				font_Header.setBold(true);
				
				HSSFFont font_cell = wb.createFont();
				font_cell.setFontHeightInPoints((short) 10);
				//font_cell.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				ArrayList<CommonBean> cm =new ArrayList();	
				ArrayList<CommonBean> cm1 =new ArrayList();
				ArrayList<CommonBean> cm2 =new ArrayList();	
				Date date = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(date);
			    
				
				headername = "Files received in Office of Hon' Minister" +strDate;
				String[] arrh = {"1","2","3","4","5","6","7","8","9"};
				String[] arrh1 = {"Period","Opening Balance","Files received during period","Total Files","Disposed","Pending at end of period","Breakup of pending files"," "," "};
				String[] arrh2 = {" "," "," "," "," "," ","< 15 days","> 15 days to 1 month","1 month to 3 months"};
				HSSFSheet sheet = wb.createSheet("PMOfficeReport");
				sheet.setColumnWidth(0, 6000);
		        sheet.setColumnWidth(1, 4500);
		        sheet.setColumnWidth(2, 4500);
		        sheet.setColumnWidth(3, 4500);
		        sheet.setColumnWidth(4, 4500);
		        sheet.setColumnWidth(5, 4500);
		        sheet.setColumnWidth(6, 4500);
		        sheet.setColumnWidth(7, 4500);
		        sheet.setColumnWidth(8, 4500);
		       
		        
		        

				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				sheet.getPrintSetup().setLandscape(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				
				//cm = getReportDataMonthWise(Refclass[index],condMARKTOFinal,condYear);
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;				
				
				HSSFRichTextString string;
				
				HSSFRow row = sheet.createRow(rnum++);
				 row.setHeightInPoints(35);
				/*for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				row = sheet.createRow(rnum++);
				*/
				for (int i = 0; i < arrh1.length; i++) {
					string = new HSSFRichTextString(arrh1[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
			
				}
				
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        0, //last row  (0-based)
				       6, //first column (0-based)
				       8  //last column  (0-based)
				));
				row = sheet.createRow(rnum++);
				row.setHeightInPoints(35);
				
				for (int i = 0; i < arrh2.length; i++) {
					string = new HSSFRichTextString(arrh2[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
			
				}
				
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        1, //last row  (0-based)
				        0, //first column (0-based)
				       0 //last column  (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        1, //last row  (0-based)
				        1, //first column (0-based)
				       1//last column  (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        1, //last row  (0-based)
				        2, //first column (0-based)
				       2//last column  (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        1, //last row  (0-based)
				        3, //first column (0-based)
				       3//last column  (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        1, //last row  (0-based)
				        4, //first column (0-based)
				       4 //last column  (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        1, //last row  (0-based)
				        5, //first column (0-based)
				       5 //last column  (0-based)
				));
				
				
				row = sheet.createRow(rnum++);
				row.setHeightInPoints(35);
				
				int Openingbalance =0;
				int TotalFile=0;
				int Disposed =0;
				int panding=0;
				int fileRecDuringP=0;
				int b1=0;
				int b2=0;
				int b3=0;
				
				
				
				string = new HSSFRichTextString("01/06/2014 to 31/03/2015");
				string.applyFont(font_Header);
				createCell(wb, row, (short) (0),
						style_header, string);
				string = new HSSFRichTextString(Openingbalance+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (1),
						style_cell, string);
				cm = FileReciveDuringPeriod();
				fileRecDuringP = Integer.parseInt(cm.get(0).getField1());
				string = new HSSFRichTextString(cm.get(0).getField1().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (2),
						style_cell, string);
				TotalFile= Openingbalance + fileRecDuringP;
				string = new HSSFRichTextString(TotalFile+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (3),
						style_cell, string);
				cm = Disposed();
				Disposed = Integer.parseInt(cm.get(0).getField1());
				string = new HSSFRichTextString(cm.get(0).getField1().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (4),
						style_cell, string);
				panding = TotalFile -	Disposed;
				string = new HSSFRichTextString(panding+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (5),
						style_cell, string);
				cm = Breakupaofpandingfile();
				string = new HSSFRichTextString(cm.get(0).getField1().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (6),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(0).getField2().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (7),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(0).getField3().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (8),
						style_cell, string);
					
				
				row = sheet.createRow(rnum++);
				row.setHeightInPoints(35);
				
				string = new HSSFRichTextString("01/04/2015 to 31/03/2016");
				string.applyFont(font_Header);
				createCell(wb, row, (short) (0),
						style_header, string);
				Openingbalance=panding;
				string = new HSSFRichTextString(Openingbalance+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (1),
						style_cell, string);
				cm1 = FileReciveDuringPeriod1();
				fileRecDuringP = Integer.parseInt(cm1.get(0).getField1());
				
				string = new HSSFRichTextString(cm1.get(0).getField1().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (2),
						style_cell, string);
				TotalFile= Openingbalance + fileRecDuringP;
				string = new HSSFRichTextString(TotalFile+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (3),
						style_cell, string);
				cm1 = Disposed1();
				Disposed = Integer.parseInt(cm1.get(0).getField1());
				//System.out.println(" getReportData getReportData+++++++++++++++++++++"+Disposed);
				string = new HSSFRichTextString(cm1.get(0).getField1().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (4),
						style_cell, string);
				panding = TotalFile -	Disposed;
				string = new HSSFRichTextString(panding+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (5),
						style_cell, string);
				cm1 = Breakupaofpandingfile1();
				string = new HSSFRichTextString(cm1.get(0).getField1().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (6),
						style_cell, string);
				string = new HSSFRichTextString(cm1.get(0).getField2().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (7),
						style_cell, string);
				string = new HSSFRichTextString(cm1.get(0).getField3().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (8),
						style_cell, string);
					
				row = sheet.createRow(rnum++);
				row.setHeightInPoints(35);
				string = new HSSFRichTextString("01/04/2016 to"+cond);
				string.applyFont(font_Header);
				createCell(wb, row, (short) (0),
						style_header, string);
				Openingbalance=0;
				Openingbalance=panding;
				string = new HSSFRichTextString(Openingbalance+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (1),
						style_cell, string);
				cm2 = FileReciveDuringPeriod2(cond);
				fileRecDuringP = Integer.parseInt(cm2.get(0).getField1());
				string = new HSSFRichTextString(cm2.get(0).getField1().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (2),
						style_cell, string);
				TotalFile= Openingbalance + fileRecDuringP;
				string = new HSSFRichTextString(TotalFile+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (3),
						style_cell, string);
				
				cm2 = Breakupaofpandingfile2(cond);
				b1 = Integer.parseInt(cm2.get(0).getField1());
				b2 = Integer.parseInt(cm2.get(0).getField2());
				b3 = Integer.parseInt(cm2.get(0).getField3());
				panding = b1 +b2+b3;
				
				Disposed =TotalFile-panding ;
				//cm2 = Disposed2(cond);
				//Disposed = Integer.parseInt(cm2.get(0).getField1());
				//string = new HSSFRichTextString(cm2.get(0).getField1().toString());
				string = new HSSFRichTextString(Disposed+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (4),
						style_cell, string);
				
				//panding = TotalFile -	Disposed;
				string = new HSSFRichTextString(panding+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (5),
						style_cell, string);
				
				cm2 = Breakupaofpandingfile2(cond);
				string = new HSSFRichTextString(cm2.get(0).getField1().toString());
				string.applyFont(font_ENDcell);
				createCell(wb, row, (short) (6),
						style_cell, string);
				string = new HSSFRichTextString(cm2.get(0).getField2().toString());
				string.applyFont(font_ENDcell);
				createCell(wb, row, (short) (7),
						style_cell, string);
				string = new HSSFRichTextString(cm2.get(0).getField3().toString());
				string.applyFont(font_ENDcell);
				createCell(wb, row, (short) (8),
						style_cell, string);
					
					
					
				
				
				

				///--------loopp-----------------------------------------------------///
				
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

		public ArrayList<CommonBean> FileReciveDuringPeriod() {
			// TODO Auto-generated method stub
			
			String strSQL = "select count(*) from(select fmid,registrationdatedes from trnfilehdr where "
					+ "registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and "
					+ "registrationdatedes < to_date('01/04/2015','dd/mm/yyyy') and referencetype in ('A','C') "
					+ "order by registrationdatedes)";
			
			return (new CommonDAO()).getSQLResult(strSQL, 1);
		}
		public ArrayList<CommonBean> Disposed() {
			// TODO Auto-generated method stub
			
			String strSQL = "select count(*) from( select fmid,markingdate from trnfilemarking where "
					+ "markingdate > to_date('31/05/2014','dd/mm/yyyy') and  "
					+ "markingdate < to_date('01/04/2015','dd/mm/yyyy') "
					+ "and fmid in ( "
					+ "select fmid from trnfilehdr where referencetype in ('A','C') and  "
					+ "registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and  "
					+ "registrationdatedes < to_date('01/04/2015','dd/mm/yyyy'))) ";
			
			return (new CommonDAO()).getSQLResult(strSQL, 1);
		}
		public ArrayList<CommonBean> Breakupaofpandingfile() {
			// TODO Auto-generated method stub
			
			String strSQL = "select sum(a),sum(b),sum(c) from  "
					+ "(select fmid,registrationdatedes,registrationnodes,subject, "
					+ "REFERENCETYPE FILETYPE,FILENO,(select sname from mstgec where codetype=6 and code=b.FILESTATUS1) STATUS1, "
					+ "(select sname from mstgec where codetype=7 and code=b.FILESTATUS2) STATUS2,"
					+ "(select rolename from mstrole where roleid=b.RECEIVEDFROM) RECEIVEDFROM, "
					+ "(select rolename from mstrole where roleid=b.marking) DOWNWARDMARKINGTO,MARKINGDATE, "
					+ "DECODE(pendingduration,1,1,0) a,DECODE(pendingduration,2,1,0) b,DECODE(pendingduration,3,1,0) c "
					+ "from "
					+ "(select fmid,registrationdatedes,registrationnodes,subject, "
					+ "REFERENCETYPE,FILENO,FILESTATUS1,FILESTATUS2,RECEIVEDFROM, "
					+ "(select markingto from trnfilemarking where fmid=a.fmid) marking, "
					+ "(select markingdate from trnfilemarking where fmid=a.fmid) maRKINGDATE, "
					+ "CASE WHEN to_date('31/03/2015','dd/mm/yyyy')-registrationdatedes <=15 then 1 "
					+ "WHEN  to_date('31/03/2015','dd/mm/yyyy')-registrationdatedes >15 and to_date('31/03/2015','dd/mm/yyyy')-registrationdatedes <30 then 2 "
					+ "WHEN  to_date('31/03/2015','dd/mm/yyyy')-registrationdatedes >30  then 3 "
					+ "END pendingduration  "
					+ "from trnfilehdr a where registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and "
					+ "registrationdatedes < to_date('01/04/2015','dd/mm/yyyy') and referencetype in ('A','C') "
					+ "and fmid not in ( "
					+ "select fmid from trnfilemarking where markingdate > to_date('31/05/2014','dd/mm/yyyy') and  "
					+ "markingdate < to_date('01/04/2015','dd/mm/yyyy') "
					+ "and fmid in ( "
					+ "select fmid from trnfilehdr where referencetype in ('A','C') and "
					+ "registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and  "
					+ "registrationdatedes < to_date('01/04/2015','dd/mm/yyyy')))) b)"
					+ "order by 1";
			
			return (new CommonDAO()).getSQLResult(strSQL, 3);
		}
		public ArrayList<CommonBean> FileReciveDuringPeriod1() {
			// TODO Auto-generated method stub
			
			String strSQL = "select COUNT(*) from (select fmid,registrationdatedes from trnfilehdr where "
					+ "registrationdatedes > to_date('31/03/2015','dd/mm/yyyy') and  "
					+ "registrationdatedes < to_date('01/04/2016','dd/mm/yyyy') and referencetype in ('A','C') "
					+ "order by registrationdatedes)";
		
			
			return (new CommonDAO()).getSQLResult(strSQL, 1);
		}
		public ArrayList<CommonBean> Disposed1() {
			// TODO Auto-generated method stub
			
			String strSQL = "select count(*) from(select fmid,markingdate from trnfilemarking where "
					+ "markingdate > to_date('31/03/2015','dd/mm/yyyy') and "
					+ "markingdate < to_date('01/04/2016','dd/mm/yyyy') "
					+ "and fmid in ( "
					+ "select fmid from trnfilehdr where referencetype in ('A','C') and "
					+ "registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and registrationdatedes < "
					+ "to_date('01/04/2016','dd/mm/yyyy'))) ";
			//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
			return (new CommonDAO()).getSQLResult(strSQL, 1);
		}
		public ArrayList<CommonBean> Breakupaofpandingfile1() {
			// TODO Auto-generated method stub
			
			String strSQL = "select sum(a),sum(b),sum(c) from "
					+ "(select  "
					+ "fmid,registrationdatedes,registrationnodes,subject, "
					+ "REFERENCETYPE FILETYPE,FILENO,(select sname from mstgec where codetype=6 and code=b.FILESTATUS1) STATUS1, "
					+ "(select sname from mstgec where codetype=7 and code=b.FILESTATUS2) STATUS2 "
					+ ",(select rolename from mstrole where roleid=b.RECEIVEDFROM) RECEIVEDFROM, "
					+ "(select rolename from mstrole where roleid=b.marking) DOWNWARDMARKINGTO,MARKINGDATE, "
					+ "DECODE(pendingduration,1,1,0) a,DECODE(pendingduration,2,1,0) b,DECODE(pendingduration,3,1,0) c "
					+ "from "
					+ "(select fmid,registrationdatedes,registrationnodes,subject, "
					+ "REFERENCETYPE,FILENO,FILESTATUS1,FILESTATUS2,RECEIVEDFROM, "
					+ "(select max(markingto) from trnfilemarking where fmid=a.fmid) marking, "
					+ "(select max(markingdate) from trnfilemarking where fmid=a.fmid) maRKINGDATE, "
					+ "CASE WHEN to_date('31/03/2016','dd/mm/yyyy')-registrationdatedes <=15 then 1 "
					+ "WHEN  to_date('31/03/2016','dd/mm/yyyy')-registrationdatedes >15 and to_date('31/03/2016','dd/mm/yyyy')-registrationdatedes <30 then 2 "
					+ "WHEN  to_date('31/03/2016','dd/mm/yyyy')-registrationdatedes >30  then 3 "
					+ "END pendingduration "
					+ "from trnfilehdr a where registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and  "
					+ "registrationdatedes < to_date('01/04/2016','dd/mm/yyyy') and referencetype in ('A','C') "
					+ "and fmid not in ( "
					+ "select fmid from trnfilemarking where markingdate > to_date('31/03/2015','dd/mm/yyyy') and  "
					+ "markingdate < to_date('01/04/2016','dd/mm/yyyy') "
					+ "and fmid in ( "
					+ "select fmid from trnfilehdr where referencetype in ('A','C') and "
					+ "registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and  "
					+ "registrationdatedes < to_date('01/04/2016','dd/mm/yyyy'))) "
					+ "and fmid not in "
					+ "( "
					+ "select fmid from trnfilemarking where markingdate > to_date('31/05/2014','dd/mm/yyyy') and  "
					+ "markingdate < to_date('01/04/2015','dd/mm/yyyy') "
					+ "and fmid in ( "
					+ "select fmid from trnfilehdr where referencetype in ('A','C') and "
					+ "registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and  "
					+ "registrationdatedes < to_date('01/04/2016','dd/mm/yyyy'))) "
					+ ") b) order by fmid";
		
			return (new CommonDAO()).getSQLResult(strSQL, 3);
		}
		public ArrayList<CommonBean> FileReciveDuringPeriod2(String cond) {
			// TODO Auto-generated method stub
			
			String strSQL = "select count(*) from (select fmid,registrationdatedes from trnfilehdr where "
					+ "registrationdatedes > to_date('31/03/2016','dd/mm/yyyy') and  "
					+ "registrationdatedes < to_date('"+ cond +"','dd/mm/yyyy')+1 and referencetype in ('A','C') "
					+ "order by registrationdatedes)";
			
			return (new CommonDAO()).getSQLResult(strSQL, 1);
		}
		public ArrayList<CommonBean> Disposed2(String cond) {
			// TODO Auto-generated method stub
			
			String strSQL = "select count(*) from(select distinct fmid,markingdate from trnfilemarking where "
					+ "markingdate > to_date('31/03/2016','dd/mm/yyyy') and  "
					+ "markingdate < to_date('"+ cond +"','dd/mm/yyyy')+1 "
					+ "and fmid in ( "
					+ "select fmid from trnfilehdr where referencetype in ('A','C') and "
					+ "registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and registrationdatedes <  "
					+ "to_date('"+ cond +"','dd/mm/yyyy')+1)) ";
			
			return (new CommonDAO()).getSQLResult(strSQL, 1);
		}
		public ArrayList<CommonBean> Breakupaofpandingfile2(String cond) {
			// TODO Auto-generated method stub
			//System.out.println(" getReportData getReportData+++++++++++++++++++++"+cond+1);
			String strSQL = " select sum(a),sum(b),sum(c) from  "
					+ "(select fmid,registrationdatedes,registrationnodes,subject, "
					+ "REFERENCETYPE FILETYPE,FILENO,(select sname from mstgec where codetype=6 and code=b.FILESTATUS1) STATUS1, "
					+ "(select sname from mstgec where codetype=7 and code=b.FILESTATUS2) STATUS2 "
					+ ",(select rolename from mstrole where roleid=b.RECEIVEDFROM) RECEIVEDFROM, "
					+ "(select rolename from mstrole where roleid=b.marking) DOWNWARDMARKINGTO,MARKINGDATE, "
					+ "DECODE(pendingduration,1,1,0) a,DECODE(pendingduration,2,1,0) b,DECODE(pendingduration,3,1,0) c "
					+ "from "
					+ "(select fmid,registrationdatedes,registrationnodes,subject, "
					+ "REFERENCETYPE,FILENO,FILESTATUS1,FILESTATUS2,RECEIVEDFROM, "
					+ "(select max(markingto) from trnfilemarking where fmid=a.fmid) marking, "
					+ "(select max(markingdate) from trnfilemarking where fmid=a.fmid) maRKINGDATE, "
					+ "CASE WHEN to_date('31/05/2017','dd/mm/yyyy')-registrationdatedes <=15 then 1 "
					+ "WHEN  to_date('31/05/2017','dd/mm/yyyy')-registrationdatedes >15 and to_date('31/05/2017','dd/mm/yyyy')-registrationdatedes <30 then 2 "
					+ "WHEN  to_date('31/05/2017','dd/mm/yyyy')-registrationdatedes >30  then 3 "
					+ "END pendingduration "
					+ "from trnfilehdr a where registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and registrationdatedes < "
					+ "to_date('"+ cond +"','dd/mm/yyyy')+1 and referencetype in ('A','C') "
					+ "and fmid not in ( "
					+ "select fmid from trnfilemarking where markingdate > to_date('31/03/2016','dd/mm/yyyy') and markingdate < "
					+ "to_date('"+ cond +"','dd/mm/yyyy')+1 "
					+ "and fmid in ( "
					+ "select fmid from trnfilehdr where referencetype in ('A','C') and "
					+ "registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and registrationdatedes < "
					+ "to_date('"+ cond +"','dd/mm/yyyy')+1)) "
					+ "and fmid not in "
					+ "( "
					+ "select fmid from trnfilemarking where markingdate > to_date('31/05/2014','dd/mm/yyyy') and markingdate < "
					+ "to_date('01/04/2016','dd/mm/yyyy') "
					+ "and fmid in ( "
					+ "select fmid from trnfilehdr where referencetype in ('A','C') and "
					+ "registrationdatedes > to_date('31/05/2014','dd/mm/yyyy') and registrationdatedes < "
					+ "to_date('"+ cond +"','dd/mm/yyyy')+1)) "
					+ ") b) "
					+ "order by fmid ";
			
			return (new CommonDAO()).getSQLResult(strSQL,3);
		}
}
