package in.org.cris.mrsectt.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class SummaryReportDAO {

	
	public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256; 
	 public static final int UNIT_OFFSET_LENGTH = 7; 
	 public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 }; 
	
	
	public String generateSummaryReportStateWise(String serverpath,
			String cond) {
		

		
		int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "D://";

		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		try {
			
			//String[] Refclass ={"A","B","E","C"};
			MastersReportBean bean = null;
			
			//ArrayList arrDT = new ArrayList();
			//ArrayList arr = new ArrayList();
			String headername = "Case Wise Reports";

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
			    
				
				headername = "Summary Report State Wise as on " +strDate;
				//String[] year = {"2014","2015","2016","2017"};
				String[] arrh = {"S.No","Reference Name","Vip Type","No of petitions recieved (Since november 2014)","No of petitions complied with *","",""};
				String[] arrh1 = {"","","","","Complied","Partially Complied","Not Feasable"};
				
				
				HSSFSheet sheet = wb.createSheet("StateSummary");
				sheet.setColumnWidth(0, 2000);
		        sheet.setColumnWidth(1, 8000);
		        sheet.setColumnWidth(2, 3500);
		        sheet.setColumnWidth(3, 3500);
		        sheet.setColumnWidth(4, 3500);
		        sheet.setColumnWidth(5, 3500);
		        sheet.setColumnWidth(6, 3500);
		       
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				sheet.getPrintSetup().setLandscape(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				cm = ReportStateWiseData(cond);
				//cm = getReportDataMonthWise(Refclass[index],condMARKTOFinal,condYear);
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;				
				
				HSSFRichTextString string;
				
				HSSFRow row = sheet.createRow(rnum++);
				for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        0, //last row  (0-based)
				        4, //first column (0-based)
				       6  //last column  (0-based)
				));
				row = sheet.createRow(rnum++);
				for (int i = 0; i < arrh1.length; i++) {
					string = new HSSFRichTextString(arrh1[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				
				int sno = 1;
				int sumPetitions = 0;
				int sumComplied = 0;
				int sumPartialComplied = 0;
				int sumNotApp = 0;
				
				//int sumY1Total=0,sumY1Dis=0,sumY2Total=0,sumY2Dis=0,sumY3Total=0,sumY3Dis=0,sumY4Total=0,sumY4Dis=0;
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
						sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
						string = new HSSFRichTextString(cm.get(i).getField3().toString());
						string.applyFont(font_cell);
						//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
						//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
						createCell(wb, row, (short) (3),
								style_cell, string);
						sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
						string = new HSSFRichTextString(cm.get(i).getField4().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (4),
								style_cell, string);
						sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
						string = new HSSFRichTextString(cm.get(i).getField5().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (5),
								style_cell, string);
						sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
						string = new HSSFRichTextString(cm.get(i).getField6().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (6),
								style_cell, string);
						//sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
						//sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
						
					
					sno++;
				}
				row = sheet.createRow(rnum++);


				string = new HSSFRichTextString("Total");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (2),
						style_header, string);
				


				
				string = new HSSFRichTextString(sumPetitions+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (3),
						style_cell, string);
				string = new HSSFRichTextString(sumComplied+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (4),
						style_cell, string);
				string = new HSSFRichTextString(sumPartialComplied+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (5),
						style_cell, string);
				string = new HSSFRichTextString(sumNotApp+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (6),
						style_cell, string);
				
				
				rnum++;
				rnum++;
				row = sheet.createRow(rnum++);
				
				string = new HSSFRichTextString("* Compliance data available since April 2016 ");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (0),
						style_cell, string);
				
				sheet.addMergedRegion(new CellRangeAddress(
				        rnum-1, //first row (0-based)
				        rnum-1, //last row  (0-based)
				        0, //first column (0-based)
				       6  //last column  (0-based)
				));
				
				
				HSSFFooter footer = sheet.getFooter();
				
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				footer.setRight("As on " + df.format(new Date()));
				footer.setCenter("Page " + HSSFFooter.page() + " of "
						+ HSSFFooter.numPages());
				
			

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

	/*private ArrayList<CommonBean> ReportStateWiseData(String cond) {
		// TODO Auto-generated method stub
		
		
		String strSQL = "select referencename,addviptype,sum(refcount),sum(complied),sum(partiallycomp),sum(notfeasible) "
				+ "from "
				+ "(select referencename,addviptype,1 refcount,decode(NVL(compliance,'A'),'C',1,0) complied, "
				+ "decode(NVL(compliance,'A'),'E',1,0) partiallycomp, "
				+ "decode(NVL(compliance,'A'),'D',1,0) notfeasible "
				+ "from trnreference where addviptype in ('LS','RS','MIN','MOS','PM','CM')  "
				+ "and tenureid>=12 and incomingdate>to_date('10/11/2014','dd/mm/yyyy') "
				+ " "+cond +"and refclass in ('A','M')"
				+ "order by 1) "
				+ "group by referencename,addviptype order by 1";
		
		//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
		//log.debug("Remider ** :-- " + strSQL);
		return (new CommonDAO()).getSQLResult(strSQL, 6);
	}*/
	//soumen func
	private ArrayList<CommonBean> ReportStateWiseData(String cond) {
		// TODO Auto-generated method stub
		
		String iparr[]=cond.split("=");
		iparr[1]=iparr[1].replaceAll("\'","");
		//System.out.println("ReportStateWiseData::"+iparr[0]+"...."+iparr[1]);
		
		/*String strSQL = "select referencename,addviptype,sum(refcount),sum(complied),sum(partiallycomp),sum(notfeasible) "
				+ "from "
				+ "(select referencename,addviptype,1 refcount,decode(NVL(compliance,'A'),'C',1,0) complied, "
				+ "decode(NVL(compliance,'A'),'E',1,0) partiallycomp, "
				+ "decode(NVL(compliance,'A'),'D',1,0) notfeasible "
				+ "from trnreference where addviptype in ('LS','RS','MIN','MOS','PM','CM')  "
				+ "and tenureid>=12 and incomingdate>to_date('10/11/2014','dd/mm/yyyy') "
				+ " "+cond +"and refclass in ('A','M')"
				+ "order by 1) "
				+ "group by referencename,addviptype order by 1";
		
		//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
		//log.debug("Remider ** :-- " + strSQL);
		return (new CommonDAO()).getSQLResult(strSQL, 6);*/
		
		
		
		String strSQL = "select referencename,addviptype,sum(refcount),sum(complied),sum(partiallycomp),sum(notfeasible) "
				+ "from "
				+ "(select referencename,addviptype,1 refcount,decode(NVL(compliance,'A'),'C',1,0) complied, "
				+ "decode(NVL(compliance,'A'),'E',1,0) partiallycomp, "
				+ "decode(NVL(compliance,'A'),'D',1,0) notfeasible "
				+ "from trnreference where addviptype in ('LS','RS','MIN','MOS','PM','CM')  "
				+ "and tenureid>=12 and incomingdate>to_date('10/11/2014','dd/mm/yyyy') "
				+ " and statecode= ? and refclass in ('A','M')"
				+ "order by 1) "
				+ "group by referencename,addviptype order by 1";
		
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,iparr[1]);
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 6,con);
				//	System.out.println("ReportStateWiseData executed");
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
			
		
	}

	public ArrayList<CommonBean> ReportStateData() {
		// TODO Auto-generated method stub
		
		String strSQL ="select statecode,(select statename from mststate b where a.statecode=b.statecode) statename, " + 
				"sum(recv2014),sum(recv2015),sum(recv2016),sum(recv2017), sum(recv2018), sum(recv2019), sum(recv2020),sum(totrecv) TOTAL,  " + 
				"sum(disp2014),sum(disp2015),sum(disp2016),sum(disp2017), sum(disp2018), sum(disp2019), sum(disp2020),sum(totdisp) TOTAL, " + 
				"sum(crb),sum(fc),sum(me),sum(ms),sum(mt),sum(mrs),sum(mtraction),sum(totaldisplay) from  " + 
				"( select statecode, " + 
				"decode(to_char(incomingdate,'yyyy'),'2014',1,0) recv2014,  " + 
				"decode(to_char(incomingdate,'yyyy'),'2015',1,0) recv2015,  " + 
				"decode(to_char(incomingdate,'yyyy'),'2016',1,0) recv2016,  " + 
				"decode(to_char(incomingdate,'yyyy'),'2017',1,0) recv2017, " + 
				"decode(to_char(incomingdate,'yyyy'),'2018',1,0) recv2018,  " + 
				"decode(to_char(incomingdate,'yyyy'),'2019',1,0) recv2019,  " + 
				"decode(to_char(incomingdate,'yyyy'),'2020',1,0) recv2020,    " + 
				"decode(to_char(incomingdate,'yyyy'),'2014',1,0) + " + 
				"decode(to_char(incomingdate,'yyyy'),'2015',1,0) + " + 
				"decode(to_char(incomingdate,'yyyy'),'2016',1,0) + " + 
				"decode(to_char(incomingdate,'yyyy'),'2017',1,0) + " + 
				"decode(to_char(incomingdate,'yyyy'),'2018',1,0) + " + 
				"decode(to_char(incomingdate,'yyyy'),'2019',1,0) + " + 
				"decode(to_char(incomingdate,'yyyy'),'2020',1,0) totrecv,  " + 
				"decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2014, " + 
				"decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2015,  " + 
				"decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2016, " + 
				"decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2017, " + 
				"decode(to_char(incomingdate,'yyyy'),'2018',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2018, " + 
				"decode(to_char(incomingdate,'yyyy'),'2019',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2019, " + 
				"decode(to_char(incomingdate,'yyyy'),'2020',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2020, " + 
				"decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ " + 
				"decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+  " + 
				"decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+  " + 
				"decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+  " + 
				"decode(to_char(incomingdate,'yyyy'),'2018',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+  " + 
				"decode(to_char(incomingdate,'yyyy'),'2019',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+  " + 
				"decode(to_char(incomingdate,'yyyy'),'2020',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) totdisp, " + 
				"decode(NVL(s.hod,0),53,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) crb, decode(NVL(s.hod,0),292,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) fc, decode(NVL(s.hod,0),294,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) me,  decode(NVL(s.hod,0),35,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ms,  decode(NVL(s.hod,0),424,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) mt,  decode(NVL(s.hod,0),1132,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) mrs,  decode(NVL(s.hod,0),1133,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) mtraction,  decode(NVL(s.hod,0),53,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+	 decode(NVL(s.hod,0),292,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+ decode(NVL(s.hod,0),294,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+  decode(NVL(s.hod,0),35,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+  decode(NVL(s.hod,0),424,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+  decode(NVL(s.hod,0),1132,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+  decode(NVL(s.hod,0),1133,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) totaldisplay  from  trnreference p,trnmarking  q, mstrole r,mstdept s where p.refclass in ('A','M') and p.addviptype in ('LS','RS','MIN','MOS','PM','CM') and p.tenureid>=12 and p.incomingdate>to_date('10/11/2014','dd/mm/yyyy') and (p.statecode is not null or p.statecode<>'NOM') and p.refid=q.refid(+) and q.markingsequence=1 and q.markingto=r.roleid(+) and r.deptcode=s.deptcode(+) order by 2 ) a group by statecode order by 1";
		
		/*String strSQL = "select statecode,(select statename from mststate b where a.statecode=b.statecode) statename,sum(recv2014),sum(recv2015),sum(recv2016),sum(recv2017),sum(totrecv) TOTAL, "
				+ "sum(disp2014),sum(disp2015),sum(disp2016),sum(disp2017),sum(totdisp) TOTAL,sum(crb),sum(fc),sum(me),sum(ms),sum(mt),sum(mrs),sum(mtraction),sum(totaldisplay) "
				+ "from ( "
				+ "select statecode,decode(to_char(incomingdate,'yyyy'),'2014',1,0) recv2014, "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',1,0) recv2015, "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',1,0) recv2016, "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',1,0) recv2017,  "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',1,0)+decode(to_char(incomingdate,'yyyy'),'2015',1,0) "
				+ "+decode(to_char(incomingdate,'yyyy'),'2016',1,0)+decode(to_char(incomingdate,'yyyy'),'2017',1,0) totrecv, "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2014, "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2015, "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2016, "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2017, "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) totdisp, "
				+ "decode(NVL(s.hod,0),53,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) crb, "
				+ "decode(NVL(s.hod,0),292,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) fc, "
				+ "decode(NVL(s.hod,0),294,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) me, "
				+ " decode(NVL(s.hod,0),35,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ms, "
				+ " decode(NVL(s.hod,0),424,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) mt, "
				+ " decode(NVL(s.hod,0),1132,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) mrs, "
				+ " decode(NVL(s.hod,0),1133,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) mtraction, "
				+ " decode(NVL(s.hod,0),53,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+	"
				+ " decode(NVL(s.hod,0),292,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+"
				+ " decode(NVL(s.hod,0),294,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+ "
				+ " decode(NVL(s.hod,0),35,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+ "
				+ " decode(NVL(s.hod,0),424,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+ "
				+ " decode(NVL(s.hod,0),1132,decode(NVL(compliance,'A'),'A',1,'B',1,0),0)+ "
				+ " decode(NVL(s.hod,0),1133,decode(NVL(compliance,'A'),'A',1,'B',1,0),0) totaldisplay "				
				+ " from "
				+ " trnreference p,trnmarking  q, mstrole r,mstdept s where p.refclass in ('A','M') and p.addviptype in ('LS','RS','MIN','MOS','PM','CM') "
				+ "and p.tenureid>=12 and p.incomingdate>to_date('10/11/2014','dd/mm/yyyy') and "
				+ "(p.statecode is not null or p.statecode<>'NOM') "
				+ "and p.refid=q.refid(+) and q.markingsequence=1 and q.markingto=r.roleid(+) and "
				+ "r.deptcode=s.deptcode(+) "
				+ "order by 2 "
				+ ") a "
				+ "group by statecode order by 1";*/
		
		//System.out.println("qeyry strSQL :"+strSQL);
		
		return (new CommonDAO()).getSQLResult(strSQL, 26);
	}

	public String generateSummaryReportAllState(String serverpath) {
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
			String headername = "All state Report";

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
				ArrayList<CommonBean> cm1 =new ArrayList();	
				
				Date date = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(date);
			    
				
				headername = "Summary Report State Wise as on " +strDate;
				//String[] year = {"2014","2015","2016","2017"};
				String[] arrh = {"S.No","Name of State","Petitions Received","","","","","","","","Disposed","","","","","","","","Pending with","","","","","","","Total Pending"};
				
				String[] arrhRoleWise = {"S.No","Name of State","Petitions Received","","","","","","","","Disposed","","","","","","","","Pending"};
				String[] arrh1RoleWise = {"","","2014","2015","2016","2017","2018","2019","2020","Total","2014","2015","2016","2017","2018","2019","2020","Total",""};
				String[] arrh1 = {"","","2014","2015","2016","2017","2018","2019","2020","Total","2014","2015","2016","2017","2018","2019","2020","Total","CRB","FC","ME","MS","MT","MRS","MTRACTION",""};
				
				
				HSSFSheet sheet = wb.createSheet("StateSummary");
				sheet.setColumnWidth(0, 2000);
		        sheet.setColumnWidth(1, 7000);
		        sheet.setColumnWidth(2, 2500);
		        sheet.setColumnWidth(3, 2500);
		        sheet.setColumnWidth(4, 2500);
		        sheet.setColumnWidth(5, 2500);
		        sheet.setColumnWidth(6, 2500);//
		        sheet.setColumnWidth(7, 2500);//
		        sheet.setColumnWidth(8, 2500);//
		        sheet.setColumnWidth(9, 2500);
		        sheet.setColumnWidth(10, 2500);
		        sheet.setColumnWidth(11, 2500);
		        sheet.setColumnWidth(12, 2500);
		        sheet.setColumnWidth(13, 2500);
		        sheet.setColumnWidth(14, 2500);//
		        sheet.setColumnWidth(15, 2500);//
		        sheet.setColumnWidth(16, 2500);//
		        sheet.setColumnWidth(17, 2500);
		        sheet.setColumnWidth(18, 2500);
		        sheet.setColumnWidth(19, 2500);
		        sheet.setColumnWidth(20, 2500);
		        sheet.setColumnWidth(21, 2500);
		        sheet.setColumnWidth(22, 2500);
		        sheet.setColumnWidth(23, 2500);
		        sheet.setColumnWidth(24, 3500);
		        
		        
		       
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				sheet.getPrintSetup().setLandscape(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				cm = ReportStateData();
				//cm = getReportDataMonthWise(Refclass[index],condMARKTOFinal,condYear);
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;				
				
				HSSFRichTextString string;
				
				HSSFRow row = sheet.createRow(rnum++);
				for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        0, //last row  (0-based)
				        2, //first column (0-based)
				       9  //last column  (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        0, //last row  (0-based)
				        10, //first column (0-based)
				       14  //last column  (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        0, //last row  (0-based)
				       18, //first column (0-based)
				       24  //last column  (0-based)
				));
				row = sheet.createRow(rnum++);
				for (int i = 0; i < arrh1.length; i++) {
					string = new HSSFRichTextString(arrh1[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
			
				}
				
				int sno = 1;
				int sumPetitions2014 = 0;
				int sumPetitions2015 = 0;
				int sumPetitions2016 = 0;
				int sumPetitions2017 = 0;
				int sumPetitions2018 = 0;
				int sumPetitions2019 = 0;
				int sumPetitions2020 = 0;
				int sumPetitionsTotal = 0;
				int disposed2014 = 0;
				int disposed2015 = 0;
				int disposed2016 = 0;
				int disposed2017 = 0;
				int disposed2018 = 0;
				int disposed2019 = 0;
				int disposed2020 = 0;
				int disposedTotal = 0;
				int sumCRB = 0;
				int sumFC = 0;
				int sumME = 0;
				int sumMS = 0;
				int sumMT = 0;
				int sumMRS = 0;
				int sumMTRACTION = 0;
				int sumTOTAL = 0;
				
				//int sumY1Total=0,sumY1Dis=0,sumY2Total=0,sumY2Dis=0,sumY3Total=0,sumY3Dis=0,sumY4Total=0,sumY4Dis=0;
				for (int i = 0; i < cm.size(); i++) {
	
						row = sheet.createRow(rnum++);
						string = new HSSFRichTextString(sno+"");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (0),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField2().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (1),
								style_cell, string);
						
						sumPetitions2014 = sumPetitions2014+Integer.parseInt(cm.get(i).getField3());
						string = new HSSFRichTextString(cm.get(i).getField3().toString());
						string.applyFont(font_cell);
						
						createCell(wb, row, (short) (2),
								style_cell, string);
						sumPetitions2015 = sumPetitions2015+Integer.parseInt(cm.get(i).getField4());
						string = new HSSFRichTextString(cm.get(i).getField4().toString());
						string.applyFont(font_cell);
						//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
						//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
						createCell(wb, row, (short) (3),
								style_cell, string);
						sumPetitions2016 = sumPetitions2016+Integer.parseInt(cm.get(i).getField5());
						string = new HSSFRichTextString(cm.get(i).getField5().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (4),
								style_cell, string);
						sumPetitions2017 = sumPetitions2017+Integer.parseInt(cm.get(i).getField6());
						string = new HSSFRichTextString(cm.get(i).getField6().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (5),
								style_cell, string);
						
						//Added By Vikas  ///////////////////
						sumPetitions2018 = sumPetitions2018+Integer.parseInt(cm.get(i).getField7());
						string = new HSSFRichTextString(cm.get(i).getField7().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (6),
								style_cell, string);
						sumPetitions2019 = sumPetitions2019+Integer.parseInt(cm.get(i).getField8());
						string = new HSSFRichTextString(cm.get(i).getField8().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (7),
								style_cell, string);
						sumPetitions2020 = sumPetitions2020+Integer.parseInt(cm.get(i).getField9());
						string = new HSSFRichTextString(cm.get(i).getField9().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (8),
								style_cell, string);
						/////////////////////////////////////
						
						sumPetitionsTotal =sumPetitionsTotal+Integer.parseInt(cm.get(i).getField10());
						string = new HSSFRichTextString(cm.get(i).getField10().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (9),
								style_header, string);
						disposed2014 =disposed2014+Integer.parseInt(cm.get(i).getField11());
						string = new HSSFRichTextString(cm.get(i).getField11().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (10),
								style_cell, string);
						disposed2015 =disposed2015+Integer.parseInt(cm.get(i).getField12());
						string = new HSSFRichTextString(cm.get(i).getField12().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (11),
								style_cell, string);
						disposed2016 =disposed2016+Integer.parseInt(cm.get(i).getField13());
						string = new HSSFRichTextString(cm.get(i).getField13().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (12),
								style_cell, string);
						disposed2017 =disposed2017+Integer.parseInt(cm.get(i).getField14());
						string = new HSSFRichTextString(cm.get(i).getField14().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (13),
								style_cell, string);
						
						// Added by Vikas //////
						disposed2018 =disposed2018+Integer.parseInt(cm.get(i).getField15());
						string = new HSSFRichTextString(cm.get(i).getField15().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (14),
								style_cell, string);
						disposed2019 =disposed2019+Integer.parseInt(cm.get(i).getField16());
						string = new HSSFRichTextString(cm.get(i).getField16().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (15),
								style_cell, string);
						disposed2020 =disposed2020+Integer.parseInt(cm.get(i).getField17());
						string = new HSSFRichTextString(cm.get(i).getField17().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (16),
								style_cell, string);
						////////////////////////////////
						
						disposedTotal =disposedTotal+Integer.parseInt(cm.get(i).getField18());
						string = new HSSFRichTextString(cm.get(i).getField18().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (17),
								style_header, string);
						sumCRB =sumCRB+Integer.parseInt(cm.get(i).getField19());
						string = new HSSFRichTextString(cm.get(i).getField19().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (18),
								style_cell, string);
						sumFC =sumFC+Integer.parseInt(cm.get(i).getField20());
						string = new HSSFRichTextString(cm.get(i).getField20().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (19),
								style_cell, string);
						sumME =sumME+Integer.parseInt(cm.get(i).getField21());
						string = new HSSFRichTextString(cm.get(i).getField21().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (20),
								style_cell, string);
						sumMS =sumMS+Integer.parseInt(cm.get(i).getField22());
						string = new HSSFRichTextString(cm.get(i).getField22().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (21),
								style_cell, string);
						sumMT =sumMT+Integer.parseInt(cm.get(i).getField23());
						string = new HSSFRichTextString(cm.get(i).getField23().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (22),
								style_cell, string);
						sumMRS =sumMRS+Integer.parseInt(cm.get(i).getField24());
						string = new HSSFRichTextString(cm.get(i).getField24().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (23),
								style_cell, string);
						sumMTRACTION =sumMTRACTION+Integer.parseInt(cm.get(i).getField25());
						string = new HSSFRichTextString(cm.get(i).getField25().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (24),
								style_cell, string);
						sumTOTAL =sumTOTAL+Integer.parseInt(cm.get(i).getField26());
						string = new HSSFRichTextString(cm.get(i).getField26().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (25),
								style_cell, string);
						//sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
						//sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
						
					
					sno++;
				}
				row = sheet.createRow(rnum++);


				string = new HSSFRichTextString("Total");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (1),
						style_header, string);
				


				
				string = new HSSFRichTextString(sumPetitions2014+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (2),
						style_header, string);
				string = new HSSFRichTextString(sumPetitions2015+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (3),
						style_header, string);
				string = new HSSFRichTextString(sumPetitions2016+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (4),
						style_header, string);
				string = new HSSFRichTextString(sumPetitions2017+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (5),
						style_header, string);
				
				string = new HSSFRichTextString(sumPetitions2018+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (6),
						style_header, string);
				string = new HSSFRichTextString(sumPetitions2019+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (7),
						style_header, string);
				string = new HSSFRichTextString(sumPetitions2020+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (8),
						style_header, string);
				
				
				string = new HSSFRichTextString(sumPetitionsTotal+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (9),
						style_header, string);
				
				string = new HSSFRichTextString(disposed2014+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (10),
						style_header, string);
				string = new HSSFRichTextString(disposed2015+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (11),
						style_header, string);
				string = new HSSFRichTextString(disposed2016+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (12),
						style_header, string);
				string = new HSSFRichTextString(disposed2017+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (13),
						style_header, string);
				
				string = new HSSFRichTextString(disposed2018+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (14),
						style_header, string);
				string = new HSSFRichTextString(disposed2019+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (15),
						style_header, string);
				string = new HSSFRichTextString(disposed2020+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (16),
						style_header, string);
				string = new HSSFRichTextString(disposedTotal+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (17),
						style_header, string);
				
				
				string = new HSSFRichTextString(sumCRB+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (18),
						style_header, string);
				string = new HSSFRichTextString(sumFC+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (19),
						style_header, string);
				string = new HSSFRichTextString(sumME+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (20),
						style_header, string);
				string = new HSSFRichTextString(sumMS+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (21),
						style_header, string);
				string = new HSSFRichTextString(sumMT+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (22),
						style_header, string);
				string = new HSSFRichTextString(sumMRS+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (23),
						style_header, string);
				string = new HSSFRichTextString(sumMTRACTION+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (24),
						style_header, string);
				string = new HSSFRichTextString(sumTOTAL+"");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (25),
						style_header, string);
				
				
				
				
				HSSFFooter footer = sheet.getFooter();
				
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				footer.setRight("As on " + df.format(new Date()));
				footer.setCenter("Page " + HSSFFooter.page() + " of "
						+ HSSFFooter.numPages());
			
				
				
				////for sheets of individuals //////////////////////////////////////
				String array[] = {"53","292","294","35","424","1132","1133"};
				String desc="";
				//System.out.println("array.length"+array.length);
				for(int i = 0;i<array.length;i++)
				{
					if(array[i].equalsIgnoreCase("53")){
						desc= "CRB";
					}
					if(array[i].equalsIgnoreCase("292")){
						desc= "FC";
					}
					if(array[i].equalsIgnoreCase("294")){
						desc= "ME";
					}
					if(array[i].equalsIgnoreCase("35")){
						desc= "MS";
					}
					if(array[i].equalsIgnoreCase("424")){
						desc= "MT";
					}
					if(array[i].equalsIgnoreCase("1132")){
						desc= "MRS";
					}
					if(array[i].equalsIgnoreCase("1133")){
						desc= "MTRACTION";
					}
					HSSFSheet sheet1 = wb.createSheet("StateSummary_"+desc);
					sheet1.setColumnWidth(0, 2000);
			        sheet1.setColumnWidth(1, 7000);
			        sheet1.setColumnWidth(2, 2500);
			        sheet1.setColumnWidth(3, 2500);
			        sheet1.setColumnWidth(4, 2500);
			        sheet1.setColumnWidth(5, 2500);
			        sheet1.setColumnWidth(6, 2500);
			        sheet1.setColumnWidth(7, 2500);
			        sheet1.setColumnWidth(8, 2500);
			        sheet1.setColumnWidth(9, 2500);
			        sheet1.setColumnWidth(10, 2500);
			        sheet1.setColumnWidth(11, 2500);
			        sheet1.setColumnWidth(12, 2500);
			        sheet1.setColumnWidth(13, 2500);
			        sheet1.setColumnWidth(14, 2500);
			        sheet1.setColumnWidth(15, 2500);
			        sheet1.setColumnWidth(16, 2500);
			        sheet1.setColumnWidth(17, 2500);
			       
			        
			        
			       
					HSSFPrintSetup ps1 = sheet1.getPrintSetup();
					sheet1.setAutobreaks(true);
					sheet1.getPrintSetup().setLandscape(true);
					ps1.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
					ps1.setFitWidth((short)1);
					ps1.setFitHeight((short)0);
					sheet1.setHorizontallyCenter(true);
					
					cm1 = ReportStateDataROLEWISE(array[i]);
					//cm = getReportDataMonthWise(Refclass[index],condMARKTOFinal,condYear);
					
					HSSFHeader header1 = sheet1.getHeader();
					String headername1 = "Summary Report State Wise for  " +desc;
					header1.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
							+ HSSFHeader.fontSize((short) 16) + headername1);

					header1.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
							+ HSSFHeader.fontSize((short) 16) + headername1);
					
					
					rnum = 0;				
					
					row = sheet1.createRow(rnum++);
					for (int k = 0; k < arrhRoleWise.length; k++) {
						string = new HSSFRichTextString(arrhRoleWise[k].toString()
								.replaceAll("&deg;", "°"));
						string.applyFont(font_Header);
						createCell(wb, row, (short) (k), style_header, string);
					

					}
					
					sheet1.addMergedRegion(new CellRangeAddress(
					        0, //first row (0-based)
					        0, //last row  (0-based)
					        2, //first column (0-based)
					       9  //last column  (0-based)
					));
					sheet1.addMergedRegion(new CellRangeAddress(
					        0, //first row (0-based)
					        0, //last row  (0-based)
					        10, //first column (0-based)
					       17  //last column  (0-based)
					));
					
					row = sheet1.createRow(rnum++);
					for (int j = 0; j < arrh1RoleWise.length; j++) {
						string = new HSSFRichTextString(arrh1RoleWise[j].toString()
								.replaceAll("&deg;", "°"));
						string.applyFont(font_Header);
						createCell(wb, row, (short) (j), style_header, string);
				
					}
					
					int sno1 = 1;
					int sumPetitionsRole2014 = 0;
					int sumPetitionsRole2015 = 0;
					int sumPetitionsRole2016 = 0;
					int sumPetitionsRole2017 = 0;
					int sumPetitionsRole2018 = 0;
					int sumPetitionsRole2019 = 0;
					int sumPetitionsRole2020 = 0;
					int sumPetitionsRoleTotal = 0;
					
					int disposedRole2014 = 0;
					int disposedRole2015 = 0;
					int disposedRole2016 = 0;
					int disposedRole2017 = 0;
					int disposedRole2018 = 0;
					int disposedRole2019 = 0;
					int disposedRole2020 = 0;
					int disposedRoleTotal = 0;
					
					int sumPending = 0;
					
					//int sumY1Total=0,sumY1Dis=0,sumY2Total=0,sumY2Dis=0,sumY3Total=0,sumY3Dis=0,sumY4Total=0,sumY4Dis=0;
					for (int x = 0; x < cm1.size(); x++) {
		
							row = sheet1.createRow(rnum++);
							string = new HSSFRichTextString(sno1+"");
							string.applyFont(font_cell);
							createCell(wb, row, (short) (0),
									style_cell, string);
							string = new HSSFRichTextString(cm1.get(x).getField2().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (1),
									style_cell, string);
							
							sumPetitionsRole2014 = sumPetitionsRole2014+Integer.parseInt(cm1.get(x).getField3());
							string = new HSSFRichTextString(cm1.get(x).getField3().toString());
							string.applyFont(font_cell);
							
							createCell(wb, row, (short) (2),
									style_cell, string);
							sumPetitionsRole2015 = sumPetitionsRole2015+Integer.parseInt(cm1.get(x).getField4());
							string = new HSSFRichTextString(cm1.get(x).getField4().toString());
							string.applyFont(font_cell);
							//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
							//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
							createCell(wb, row, (short) (3),
									style_cell, string);
							sumPetitionsRole2016 = sumPetitionsRole2016+Integer.parseInt(cm1.get(x).getField5());
							string = new HSSFRichTextString(cm1.get(x).getField5().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (4),
									style_cell, string);
							sumPetitionsRole2017 = sumPetitionsRole2017+Integer.parseInt(cm1.get(x).getField6());
							string = new HSSFRichTextString(cm1.get(x).getField6().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (5),
									style_cell, string);
							
							sumPetitionsRole2018 = sumPetitionsRole2018+Integer.parseInt(cm1.get(x).getField7());
							string = new HSSFRichTextString(cm1.get(x).getField7().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (6),
									style_cell, string);
							sumPetitionsRole2019 = sumPetitionsRole2019+Integer.parseInt(cm1.get(x).getField8());
							string = new HSSFRichTextString(cm1.get(x).getField8().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (7),
									style_cell, string);
							sumPetitionsRole2020 = sumPetitionsRole2020+Integer.parseInt(cm1.get(x).getField9());
							string = new HSSFRichTextString(cm1.get(x).getField9().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (8),
									style_cell, string);
							
							sumPetitionsRoleTotal =sumPetitionsRoleTotal+Integer.parseInt(cm1.get(x).getField10());
							string = new HSSFRichTextString(cm1.get(x).getField10().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (9),
									style_header, string);
							disposedRole2014 =disposedRole2014+Integer.parseInt(cm1.get(x).getField11());
							string = new HSSFRichTextString(cm1.get(x).getField11().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (10),
									style_cell, string);
							disposedRole2015 =disposedRole2015+Integer.parseInt(cm1.get(x).getField12());
							string = new HSSFRichTextString(cm1.get(x).getField12().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (11),
									style_cell, string);
							disposedRole2016 =disposedRole2016+Integer.parseInt(cm1.get(x).getField13());
							string = new HSSFRichTextString(cm1.get(x).getField13().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (12),
									style_cell, string);
							disposedRole2017 =disposedRole2017+Integer.parseInt(cm1.get(x).getField14());
							string = new HSSFRichTextString(cm1.get(x).getField14().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (13),
									style_cell, string);
							
							disposedRole2018 =disposedRole2018+Integer.parseInt(cm1.get(x).getField15());
							string = new HSSFRichTextString(cm1.get(x).getField15().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (14),
									style_cell, string);
							disposedRole2019 =disposedRole2019+Integer.parseInt(cm1.get(x).getField16());
							string = new HSSFRichTextString(cm1.get(x).getField16().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (15),
									style_cell, string);
							disposedRole2020 =disposedRole2020+Integer.parseInt(cm1.get(x).getField17());
							string = new HSSFRichTextString(cm1.get(x).getField17().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (16),
									style_cell, string);
							
							disposedRoleTotal =disposedRoleTotal+Integer.parseInt(cm1.get(x).getField18());
							string = new HSSFRichTextString(cm1.get(x).getField18().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (17),
									style_header, string);
							sumPending =sumPending+Integer.parseInt(cm1.get(x).getField19());
							string = new HSSFRichTextString(cm1.get(x).getField19().toString());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (18),
									style_cell, string);
							
						
						sno1++;
					}
					row = sheet1.createRow(rnum++);


					string = new HSSFRichTextString("Total");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_header, string);
		
					string = new HSSFRichTextString(sumPetitionsRole2014+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (2),
							style_header, string);
					string = new HSSFRichTextString(sumPetitionsRole2015+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (3),
							style_header, string);
					string = new HSSFRichTextString(sumPetitionsRole2016+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (4),
							style_header, string);
					string = new HSSFRichTextString(sumPetitionsRole2017+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (5),
							style_header, string);
					
					string = new HSSFRichTextString(sumPetitionsRole2018+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (6),
							style_header, string);
					string = new HSSFRichTextString(sumPetitionsRole2019+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (7),
							style_header, string);
					string = new HSSFRichTextString(sumPetitionsRole2020+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (8),
							style_header, string);
					string = new HSSFRichTextString(sumPetitionsRoleTotal+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (9),
							style_header, string);
					
					string = new HSSFRichTextString(disposedRole2014+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (10),
							style_header, string);
					string = new HSSFRichTextString(disposedRole2015+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (11),
							style_header, string);
					string = new HSSFRichTextString(disposedRole2016+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (12),
							style_header, string);
					string = new HSSFRichTextString(disposedRole2017+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (13),
							style_header, string);
					
					string = new HSSFRichTextString(disposedRole2018+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (14),
							style_header, string);
					string = new HSSFRichTextString(disposedRole2019+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (15),
							style_header, string);
					string = new HSSFRichTextString(disposedRole2020+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (16),
							style_header, string);
					
					string = new HSSFRichTextString(disposedRoleTotal+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (17),
							style_header, string);
					
					
					string = new HSSFRichTextString(sumPending+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (18),
							style_header, string);
					
					HSSFFooter footer1 = sheet1.getFooter();
					
					SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
					footer1.setRight("As on " + df1.format(new Date()));
					footer1.setCenter("Page " + HSSFFooter.page() + " of "
							+ HSSFFooter.numPages());
					
					
					
					
					
				}
				

			FileOutputStream fileOut = new FileOutputStream(FilePath);

			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;

		
		
		
	
	}

	/*private ArrayList<CommonBean> ReportStateDataROLEWISE(String string) {

		String strSQL = "select statecode,(select statename from mststate b where a.statecode=b.statecode) statename,sum(recv2014),sum(recv2015),sum(recv2016),sum(recv2017),sum(totrecv) TOTAL, "
				+ "sum(disp2014),sum(disp2015),sum(disp2016),sum(disp2017),sum(totdisp) TOTAL, sum(pending) PENDING "
				+ "from ( "
				+ "select statecode,decode(to_char(incomingdate,'yyyy'),'2014',1,0) recv2014, "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',1,0) recv2015, "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',1,0) recv2016, "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',1,0) recv2017,  "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',1,0)+decode(to_char(incomingdate,'yyyy'),'2015',1,0) "
				+ "+decode(to_char(incomingdate,'yyyy'),'2016',1,0)+decode(to_char(incomingdate,'yyyy'),'2017',1,0) totrecv, "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2014, "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2015, "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2016, "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2017, "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) totdisp,"
				+ "decode(NVL(compliance,'A'),'A',1,'B',1,0) pending "
				+ "from "
				+ "trnreference p,trnmarking  q, mstrole r,mstdept s where p.refclass in ('A','M') "
				+ "and p.tenureid>=12 and p.incomingdate>to_date('10/11/2014','dd/mm/yyyy') and "
				+ "(p.statecode is not null or p.statecode<>'NOM') and addviptype in ('LS','RS','MIN','MOS','PM','CM') "
				+ "and p.refid=q.refid(+) and q.markingto=r.roleid(+) and "
				+ "r.deptcode=s.deptcode(+) and s.hod="+ string +" "
				+ "order by 2 "
				+ ") a "
				+ "group by statecode order by 1";

		
		//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
		//log.debug("Remider ** :-- " + strSQL);
		return (new CommonDAO()).getSQLResult(strSQL,13);
	}
*/
	
	//soumen fun
	private ArrayList<CommonBean> ReportStateDataROLEWISE(String ipstr) {

		/*String strSQL = "select statecode,(select statename from mststate b where a.statecode=b.statecode) statename,sum(recv2014),sum(recv2015),sum(recv2016),sum(recv2017),sum(totrecv) TOTAL, "
				+ "sum(disp2014),sum(disp2015),sum(disp2016),sum(disp2017),sum(totdisp) TOTAL, sum(pending) PENDING "
				+ "from ( "
				+ "select statecode,decode(to_char(incomingdate,'yyyy'),'2014',1,0) recv2014, "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',1,0) recv2015, "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',1,0) recv2016, "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',1,0) recv2017,  "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',1,0)+decode(to_char(incomingdate,'yyyy'),'2015',1,0) "
				+ "+decode(to_char(incomingdate,'yyyy'),'2016',1,0)+decode(to_char(incomingdate,'yyyy'),'2017',1,0) totrecv, "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2014, "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2015, "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2016, "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2017, "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) totdisp,"
				+ "decode(NVL(compliance,'A'),'A',1,'B',1,0) pending "
				+ "from "
				+ "trnreference p,trnmarking  q, mstrole r,mstdept s where p.refclass in ('A','M') "
				+ "and p.tenureid>=12 and p.incomingdate>to_date('10/11/2014','dd/mm/yyyy') and "
				+ "(p.statecode is not null or p.statecode<>'NOM') and addviptype in ('LS','RS','MIN','MOS','PM','CM') "
				+ "and p.refid=q.refid(+) and q.markingto=r.roleid(+) and "
				+ "r.deptcode=s.deptcode(+) and s.hod="+ ipstr +" "
				+ "order by 2 "
				+ ") a "
				+ "group by statecode order by 1";

		
		//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
		//log.debug("Remider ** :-- " + strSQL);
		return (new CommonDAO()).getSQLResult(strSQL,13);*/
		
		String strSQL = "select statecode,(select statename from mststate b where a.statecode=b.statecode) statename,sum(recv2014),sum(recv2015),sum(recv2016),sum(recv2017),sum(recv2018),sum(recv2019),sum(recv2020),sum(totrecv) TOTAL, "
				+ "sum(disp2014),sum(disp2015),sum(disp2016),sum(disp2017),sum(disp2018),sum(disp2019),sum(disp2020),sum(totdisp) TOTAL, sum(pending) PENDING "
				+ "from ( "
				+ "select statecode,decode(to_char(incomingdate,'yyyy'),'2014',1,0) recv2014, "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',1,0) recv2015, "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',1,0) recv2016, "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',1,0) recv2017,  "
				+ "decode(to_char(incomingdate,'yyyy'),'2018',1,0) recv2018,  "
				+ "decode(to_char(incomingdate,'yyyy'),'2019',1,0) recv2019,  "
				+ "decode(to_char(incomingdate,'yyyy'),'2020',1,0) recv2020,  "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',1,0)+decode(to_char(incomingdate,'yyyy'),'2015',1,0) "
				+ "+decode(to_char(incomingdate,'yyyy'),'2016',1,0)+decode(to_char(incomingdate,'yyyy'),'2017',1,0)+decode(to_char(incomingdate,'yyyy'),'2018',1,0)+decode(to_char(incomingdate,'yyyy'),'2019',1,0)+decode(to_char(incomingdate,'yyyy'),'2020',1,0) totrecv, "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2014, "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2015, "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2016, "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2017, "
				+ "decode(to_char(incomingdate,'yyyy'),'2018',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2018, "
				+ "decode(to_char(incomingdate,'yyyy'),'2019',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2019, "
				+ "decode(to_char(incomingdate,'yyyy'),'2020',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) disp2020, "
				+ "decode(to_char(incomingdate,'yyyy'),'2014',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2015',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2016',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2017',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2018',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2019',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0)+ "
				+ "decode(to_char(incomingdate,'yyyy'),'2020',decode(NVL(compliance,'A'),'C',1,'D',1,'E',1,0),0) totdisp,"
				+ "decode(NVL(compliance,'A'),'A',1,'B',1,0) pending "
				+ "from "
				+ "trnreference p,trnmarking  q, mstrole r,mstdept s where p.refclass in ('A','M') "
				+ "and p.tenureid>=12 and p.incomingdate>to_date('10/11/2014','dd/mm/yyyy') and "
				+ "(p.statecode is not null or p.statecode<>'NOM') and addviptype in ('LS','RS','MIN','MOS','PM','CM') "
				+ "and p.refid=q.refid(+) and q.markingto=r.roleid(+) and "
				+ "r.deptcode=s.deptcode(+) and s.hod=? "
				+ " order by 2 "
				+ ") a "
				+ "group by statecode order by 1";

		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,ipstr);
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 19,con);
					//System.out.println("ReportStateDataROLEWISE executed");
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
			
		
	}
	

	/*private ArrayList<CommonBean> ReportSubjectData(String cond) {
		// TODO Auto-generated method stub
		String strSQL = " select referencename,addviptype,sum(refcount) TOTAL,sum(AS1), "
				+ "sum(CE),sum(DBL),sum(EL),sum(ELC),sum(ET), sum(FOB),sum(GC),sum(IFT),sum(LXM),sum(LXU), sum(MS),sum(NL),sum(NT),sum(ROB),sum(RUB),sum(pending) TOTAL_PENDING, "
				+ "sum(AS1pend), sum(CEpend),sum(DBLpend),sum(ELpend),sum(ELCpend),sum(ETpend), sum(FOBpend),sum(GCpend),sum(IFTpend),sum(LXMpend),sum(LXUpend), "
				+ "sum(MSpend),sum(NLpend),sum(NTpend),sum(ROBpend),sum(RUBpend) from (select referencename,addviptype,1 refcount, decode(subjectcode,'AS',1,0) AS1,  "
				+ "decode(subjectcode,'CE',1,0) CE, decode(subjectcode,'DBL',1,0) DBL,  decode(subjectcode,'EL',1,0) EL,  decode(subjectcode,'ELC',1,0) ELC,  decode(subjectcode,'ET',1,0) ET,  "
				+ "decode(subjectcode,'FOB',1,0) FOB, decode(subjectcode,'GC',1,0) GC, decode(subjectcode,'IFT',1,0) IFT, decode(subjectcode,'LXM',1,0) LXM, "
				+ "decode(subjectcode,'LXU',1,0) LXU, decode(subjectcode,'MS',1,0) MS, decode(subjectcode,'NL',1,0) NL, decode(subjectcode,'NT',1,0) NT, "
				+ "decode(subjectcode,'ROB',1,0) ROB, decode(subjectcode,'RUB',1,0) RUB, decode(NVL(compliance,'A'),'A',1,'B',1,0) pending , "
				+ "decode(subjectcode,'AS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) AS1pend, decode(subjectcode,'CE',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) CEpend,"
				+ " decode(subjectcode,'DBL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) DBLpend, decode(subjectcode,'EL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELpend,  "
				+ "decode(subjectcode,'ELC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELCpend,  "
				+ "decode(subjectcode,'ET',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ETpend,  "
				+ "decode(subjectcode,'FOB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) FOBpend,  "
				+ "decode(subjectcode,'GC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) GCpend,  "
				+ "decode(subjectcode,'IFT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) IFTpend,  "
				+ "decode(subjectcode,'LXM',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXMpend,  "
				+ "decode(subjectcode,'LXU',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXUpend,  "
				+ "decode(subjectcode,'MS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) MSpend,  "
				+ "decode(subjectcode,'NL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NLpend,  "
				+ "decode(subjectcode,'NT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NTpend, "
				+ "decode(subjectcode,'ROB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ROBpend,  "
				+ "decode(subjectcode,'RUB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) RUBpend "
				+ "from trnreference where addviptype in ('LS','RS','MIN','MOS','PM','CM')  and tenureid>=12  "
				+ "and incomingdate>to_date('10/11/2014','dd/mm/yyyy')  "+cond+" and refclass  "
				+ "in ('A','M') and subjectcode in ('AS','CE','DBL','EL','ELC','ET','FOB','GC','IFT','LXM','LXU','MS','NL','NT','ROB','RUB') "
				+ "order by 1) group by referencename,addviptype "
				+ "union "
				+ "select 'ZZZZZ','TOTAL',sum(refcount) TOTAL,sum(AS1), sum(CE),sum(DBL),sum(EL),sum(ELC),sum(ET), sum(FOB),sum(GC),sum(IFT),sum(LXM),sum(LXU), "
				+ "sum(MS),sum(NL),sum(NT),sum(ROB),sum(RUB),sum(pending) TOTAL_PENDING, sum(AS1pend), "
				+ "sum(CEpend),sum(DBLpend),sum(ELpend),sum(ELCpend),sum(ETpend), "
				+ "sum(FOBpend),sum(GCpend),sum(IFTpend),sum(LXMpend),sum(LXUpend), "
				+ "sum(MSpend),sum(NLpend),sum(NTpend),sum(ROBpend),sum(RUBpend) "
				+ "from (select referencename,addviptype,1 refcount, "
				+ "decode(subjectcode,'AS',1,0) AS1,  "
				+ "decode(subjectcode,'CE',1,0) CE, "
				+ "decode(subjectcode,'DBL',1,0) DBL,  "
				+ "decode(subjectcode,'EL',1,0) EL,  "
				+ "decode(subjectcode,'ELC',1,0) ELC,  "
				+ "decode(subjectcode,'ET',1,0) ET,  "
				+ "decode(subjectcode,'FOB',1,0) FOB,  "
				+ "decode(subjectcode,'GC',1,0) GC,  "
				+ "decode(subjectcode,'IFT',1,0) IFT,  "
				+ "decode(subjectcode,'LXM',1,0) LXM,  "
				+ "decode(subjectcode,'LXU',1,0) LXU,  "
				+ "decode(subjectcode,'MS',1,0) MS,  "
				+ "decode(subjectcode,'NL',1,0) NL,  decode(subjectcode,'NT',1,0) NT, decode(subjectcode,'ROB',1,0) ROB, decode(subjectcode,'RUB',1,0) RUB,  "
				+ "decode(NVL(compliance,'A'),'A',1,'B',1,0) pending , decode(subjectcode,'AS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) AS1pend,  "
				+ "decode(subjectcode,'CE',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) CEpend, "
				+ "decode(subjectcode,'DBL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) DBLpend,  "
				+ "decode(subjectcode,'EL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELpend,  "
				+ "decode(subjectcode,'ELC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELCpend,  "
				+ "decode(subjectcode,'ET',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ETpend,  "
				+ "decode(subjectcode,'FOB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) FOBpend,  "
				+ "decode(subjectcode,'GC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) GCpend, decode(subjectcode,'IFT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) IFTpend,  "
				+ "decode(subjectcode,'LXM',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXMpend, decode(subjectcode,'LXU',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXUpend,  "
				+ "decode(subjectcode,'MS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) MSpend,  "
				+ "decode(subjectcode,'NL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NLpend,  "
				+ "decode(subjectcode,'NT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NTpend, "
				+ "decode(subjectcode,'ROB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ROBpend,  "
				+ "decode(subjectcode,'RUB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) RUBpend "
				+ "from trnreference where addviptype in ('LS','RS','MIN','MOS','PM','CM')  and tenureid>=12  "
				+ "and incomingdate>to_date('10/11/2014','dd/mm/yyyy')  "+cond+" and refclass "
				+ " in ('A','M') and subjectcode in ('AS','CE','DBL','EL','ELC','ET','FOB','GC','IFT','LXM','LXU','MS','NL','NT','ROB','RUB') "
				+ "order by 1)  "
				+ "order by 1";

		
		
		return (new CommonDAO()).getSQLResult(strSQL,36);
	}*/
	//soumen func
private ArrayList<CommonBean> ReportSubjectData(String cond) {
		
		String iparr[]=cond.split("=");
		iparr[1]=iparr[1].replaceAll("\'","");
		//System.out.println("ReportSubjectData::"+iparr[0]+"...."+iparr[1]);
		
		// TODO Auto-generated method stub
		/*String strSQL = " select referencename,addviptype,sum(refcount) TOTAL,sum(AS1), "
				+ "sum(CE),sum(DBL),sum(EL),sum(ELC),sum(ET), sum(FOB),sum(GC),sum(IFT),sum(LXM),sum(LXU), sum(MS),sum(NL),sum(NT),sum(ROB),sum(RUB),sum(pending) TOTAL_PENDING, "
				+ "sum(AS1pend), sum(CEpend),sum(DBLpend),sum(ELpend),sum(ELCpend),sum(ETpend), sum(FOBpend),sum(GCpend),sum(IFTpend),sum(LXMpend),sum(LXUpend), "
				+ "sum(MSpend),sum(NLpend),sum(NTpend),sum(ROBpend),sum(RUBpend) from (select referencename,addviptype,1 refcount, decode(subjectcode,'AS',1,0) AS1,  "
				+ "decode(subjectcode,'CE',1,0) CE, decode(subjectcode,'DBL',1,0) DBL,  decode(subjectcode,'EL',1,0) EL,  decode(subjectcode,'ELC',1,0) ELC,  decode(subjectcode,'ET',1,0) ET,  "
				+ "decode(subjectcode,'FOB',1,0) FOB, decode(subjectcode,'GC',1,0) GC, decode(subjectcode,'IFT',1,0) IFT, decode(subjectcode,'LXM',1,0) LXM, "
				+ "decode(subjectcode,'LXU',1,0) LXU, decode(subjectcode,'MS',1,0) MS, decode(subjectcode,'NL',1,0) NL, decode(subjectcode,'NT',1,0) NT, "
				+ "decode(subjectcode,'ROB',1,0) ROB, decode(subjectcode,'RUB',1,0) RUB, decode(NVL(compliance,'A'),'A',1,'B',1,0) pending , "
				+ "decode(subjectcode,'AS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) AS1pend, decode(subjectcode,'CE',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) CEpend,"
				+ " decode(subjectcode,'DBL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) DBLpend, decode(subjectcode,'EL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELpend,  "
				+ "decode(subjectcode,'ELC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELCpend,  "
				+ "decode(subjectcode,'ET',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ETpend,  "
				+ "decode(subjectcode,'FOB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) FOBpend,  "
				+ "decode(subjectcode,'GC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) GCpend,  "
				+ "decode(subjectcode,'IFT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) IFTpend,  "
				+ "decode(subjectcode,'LXM',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXMpend,  "
				+ "decode(subjectcode,'LXU',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXUpend,  "
				+ "decode(subjectcode,'MS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) MSpend,  "
				+ "decode(subjectcode,'NL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NLpend,  "
				+ "decode(subjectcode,'NT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NTpend, "
				+ "decode(subjectcode,'ROB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ROBpend,  "
				+ "decode(subjectcode,'RUB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) RUBpend "
				+ "from trnreference where addviptype in ('LS','RS','MIN','MOS','PM','CM')  and tenureid>=12  "
				+ "and incomingdate>to_date('10/11/2014','dd/mm/yyyy')  "+cond+" and refclass  "
				+ "in ('A','M') and subjectcode in ('AS','CE','DBL','EL','ELC','ET','FOB','GC','IFT','LXM','LXU','MS','NL','NT','ROB','RUB') "
				+ "order by 1) group by referencename,addviptype "
				+ "union "
				+ "select 'ZZZZZ','TOTAL',sum(refcount) TOTAL,sum(AS1), sum(CE),sum(DBL),sum(EL),sum(ELC),sum(ET), sum(FOB),sum(GC),sum(IFT),sum(LXM),sum(LXU), "
				+ "sum(MS),sum(NL),sum(NT),sum(ROB),sum(RUB),sum(pending) TOTAL_PENDING, sum(AS1pend), "
				+ "sum(CEpend),sum(DBLpend),sum(ELpend),sum(ELCpend),sum(ETpend), "
				+ "sum(FOBpend),sum(GCpend),sum(IFTpend),sum(LXMpend),sum(LXUpend), "
				+ "sum(MSpend),sum(NLpend),sum(NTpend),sum(ROBpend),sum(RUBpend) "
				+ "from (select referencename,addviptype,1 refcount, "
				+ "decode(subjectcode,'AS',1,0) AS1,  "
				+ "decode(subjectcode,'CE',1,0) CE, "
				+ "decode(subjectcode,'DBL',1,0) DBL,  "
				+ "decode(subjectcode,'EL',1,0) EL,  "
				+ "decode(subjectcode,'ELC',1,0) ELC,  "
				+ "decode(subjectcode,'ET',1,0) ET,  "
				+ "decode(subjectcode,'FOB',1,0) FOB,  "
				+ "decode(subjectcode,'GC',1,0) GC,  "
				+ "decode(subjectcode,'IFT',1,0) IFT,  "
				+ "decode(subjectcode,'LXM',1,0) LXM,  "
				+ "decode(subjectcode,'LXU',1,0) LXU,  "
				+ "decode(subjectcode,'MS',1,0) MS,  "
				+ "decode(subjectcode,'NL',1,0) NL,  decode(subjectcode,'NT',1,0) NT, decode(subjectcode,'ROB',1,0) ROB, decode(subjectcode,'RUB',1,0) RUB,  "
				+ "decode(NVL(compliance,'A'),'A',1,'B',1,0) pending , decode(subjectcode,'AS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) AS1pend,  "
				+ "decode(subjectcode,'CE',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) CEpend, "
				+ "decode(subjectcode,'DBL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) DBLpend,  "
				+ "decode(subjectcode,'EL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELpend,  "
				+ "decode(subjectcode,'ELC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELCpend,  "
				+ "decode(subjectcode,'ET',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ETpend,  "
				+ "decode(subjectcode,'FOB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) FOBpend,  "
				+ "decode(subjectcode,'GC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) GCpend, decode(subjectcode,'IFT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) IFTpend,  "
				+ "decode(subjectcode,'LXM',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXMpend, decode(subjectcode,'LXU',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXUpend,  "
				+ "decode(subjectcode,'MS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) MSpend,  "
				+ "decode(subjectcode,'NL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NLpend,  "
				+ "decode(subjectcode,'NT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NTpend, "
				+ "decode(subjectcode,'ROB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ROBpend,  "
				+ "decode(subjectcode,'RUB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) RUBpend "
				+ "from trnreference where addviptype in ('LS','RS','MIN','MOS','PM','CM')  and tenureid>=12  "
				+ "and incomingdate>to_date('10/11/2014','dd/mm/yyyy')  "+cond+" and refclass "
				+ " in ('A','M') and subjectcode in ('AS','CE','DBL','EL','ELC','ET','FOB','GC','IFT','LXM','LXU','MS','NL','NT','ROB','RUB') "
				+ "order by 1)  "
				+ "order by 1";

		return (new CommonDAO()).getSQLResult(strSQL,36);*/
		
		String strSQL = " select referencename,addviptype,sum(refcount) TOTAL,sum(AS1), "
				+ "sum(CE),sum(DBL),sum(EL),sum(ELC),sum(ET), sum(FOB),sum(GC),sum(IFT),sum(LXM),sum(LXU), sum(MS),sum(NL),sum(NT),sum(ROB),sum(RUB),sum(pending) TOTAL_PENDING, "
				+ "sum(AS1pend), sum(CEpend),sum(DBLpend),sum(ELpend),sum(ELCpend),sum(ETpend), sum(FOBpend),sum(GCpend),sum(IFTpend),sum(LXMpend),sum(LXUpend), "
				+ "sum(MSpend),sum(NLpend),sum(NTpend),sum(ROBpend),sum(RUBpend) from (select referencename,addviptype,1 refcount, decode(subjectcode,'AS',1,0) AS1,  "
				+ "decode(subjectcode,'CE',1,0) CE, decode(subjectcode,'DBL',1,0) DBL,  decode(subjectcode,'EL',1,0) EL,  decode(subjectcode,'ELC',1,0) ELC,  decode(subjectcode,'ET',1,0) ET,  "
				+ "decode(subjectcode,'FOB',1,0) FOB, decode(subjectcode,'GC',1,0) GC, decode(subjectcode,'IFT',1,0) IFT, decode(subjectcode,'LXM',1,0) LXM, "
				+ "decode(subjectcode,'LXU',1,0) LXU, decode(subjectcode,'MS',1,0) MS, decode(subjectcode,'NL',1,0) NL, decode(subjectcode,'NT',1,0) NT, "
				+ "decode(subjectcode,'ROB',1,0) ROB, decode(subjectcode,'RUB',1,0) RUB, decode(NVL(compliance,'A'),'A',1,'B',1,0) pending , "
				+ "decode(subjectcode,'AS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) AS1pend, decode(subjectcode,'CE',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) CEpend,"
				+ " decode(subjectcode,'DBL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) DBLpend, decode(subjectcode,'EL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELpend,  "
				+ "decode(subjectcode,'ELC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELCpend,  "
				+ "decode(subjectcode,'ET',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ETpend,  "
				+ "decode(subjectcode,'FOB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) FOBpend,  "
				+ "decode(subjectcode,'GC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) GCpend,  "
				+ "decode(subjectcode,'IFT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) IFTpend,  "
				+ "decode(subjectcode,'LXM',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXMpend,  "
				+ "decode(subjectcode,'LXU',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXUpend,  "
				+ "decode(subjectcode,'MS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) MSpend,  "
				+ "decode(subjectcode,'NL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NLpend,  "
				+ "decode(subjectcode,'NT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NTpend, "
				+ "decode(subjectcode,'ROB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ROBpend,  "
				+ "decode(subjectcode,'RUB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) RUBpend "
				+ "from trnreference where addviptype in ('LS','RS','MIN','MOS','PM','CM')  and tenureid>=12  "
				+ "and incomingdate>to_date('10/11/2014','dd/mm/yyyy') and statecode=?  and refclass  "
				+ "in ('A','M') and subjectcode in ('AS','CE','DBL','EL','ELC','ET','FOB','GC','IFT','LXM','LXU','MS','NL','NT','ROB','RUB') "
				+ "order by 1) group by referencename,addviptype "
				+ "union "
				+ "select 'ZZZZZ','TOTAL',sum(refcount) TOTAL,sum(AS1), sum(CE),sum(DBL),sum(EL),sum(ELC),sum(ET), sum(FOB),sum(GC),sum(IFT),sum(LXM),sum(LXU), "
				+ "sum(MS),sum(NL),sum(NT),sum(ROB),sum(RUB),sum(pending) TOTAL_PENDING, sum(AS1pend), "
				+ "sum(CEpend),sum(DBLpend),sum(ELpend),sum(ELCpend),sum(ETpend), "
				+ "sum(FOBpend),sum(GCpend),sum(IFTpend),sum(LXMpend),sum(LXUpend), "
				+ "sum(MSpend),sum(NLpend),sum(NTpend),sum(ROBpend),sum(RUBpend) "
				+ "from (select referencename,addviptype,1 refcount, "
				+ "decode(subjectcode,'AS',1,0) AS1,  "
				+ "decode(subjectcode,'CE',1,0) CE, "
				+ "decode(subjectcode,'DBL',1,0) DBL,  "
				+ "decode(subjectcode,'EL',1,0) EL,  "
				+ "decode(subjectcode,'ELC',1,0) ELC,  "
				+ "decode(subjectcode,'ET',1,0) ET,  "
				+ "decode(subjectcode,'FOB',1,0) FOB,  "
				+ "decode(subjectcode,'GC',1,0) GC,  "
				+ "decode(subjectcode,'IFT',1,0) IFT,  "
				+ "decode(subjectcode,'LXM',1,0) LXM,  "
				+ "decode(subjectcode,'LXU',1,0) LXU,  "
				+ "decode(subjectcode,'MS',1,0) MS,  "
				+ "decode(subjectcode,'NL',1,0) NL,  decode(subjectcode,'NT',1,0) NT, decode(subjectcode,'ROB',1,0) ROB, decode(subjectcode,'RUB',1,0) RUB,  "
				+ "decode(NVL(compliance,'A'),'A',1,'B',1,0) pending , decode(subjectcode,'AS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) AS1pend,  "
				+ "decode(subjectcode,'CE',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) CEpend, "
				+ "decode(subjectcode,'DBL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) DBLpend,  "
				+ "decode(subjectcode,'EL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELpend,  "
				+ "decode(subjectcode,'ELC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ELCpend,  "
				+ "decode(subjectcode,'ET',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ETpend,  "
				+ "decode(subjectcode,'FOB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) FOBpend,  "
				+ "decode(subjectcode,'GC',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) GCpend, decode(subjectcode,'IFT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) IFTpend,  "
				+ "decode(subjectcode,'LXM',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXMpend, decode(subjectcode,'LXU',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) LXUpend,  "
				+ "decode(subjectcode,'MS',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) MSpend,  "
				+ "decode(subjectcode,'NL',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NLpend,  "
				+ "decode(subjectcode,'NT',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) NTpend, "
				+ "decode(subjectcode,'ROB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) ROBpend,  "
				+ "decode(subjectcode,'RUB',decode(NVL(compliance,'A'),'A',1,'B',1,0),0) RUBpend "
				+ "from trnreference where addviptype in ('LS','RS','MIN','MOS','PM','CM')  and tenureid>=12  "
				+ "and incomingdate>to_date('10/11/2014','dd/mm/yyyy')  and statecode=? and refclass "
				+ " in ('A','M') and subjectcode in ('AS','CE','DBL','EL','ELC','ET','FOB','GC','IFT','LXM','LXU','MS','NL','NT','ROB','RUB') "
				+ "order by 1)  "
				+ "order by 1";
		
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,iparr[1]);
					ps.setString(2,iparr[1]);
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 36,con);
					//System.out.println("ReportSubjectData executed");
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
			
		
	}

	public String generateSubjectWiseSummaryReport1(String serverpath,
			String cond) {

		

		
		int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "D://";

		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		try {
			
			//String[] Refclass ={"A","B","E","C"};
			MastersReportBean bean = null;
			
			//ArrayList arrDT = new ArrayList();
			//ArrayList arr = new ArrayList();
			String headername = "Case Wise Reports";

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
			    
				
				headername = "Summary Report State Wise as on " +strDate;
				
				
				String[] arrh = {"S.No","Reference Name","VIP Type","Total Petitions Recieved (Since November 2014)","","","","","","","","","","","","","","","","","Pending","","","","","","","","","","","","","","","",""};
				
				
				String[] arrh1RoleWise = {"","","","Total","AS","CE","DBL","EL","ELC","ET","FOB","GC","IFT","LXM","LXU","MS","NL","NT","ROB","RUB","Total","AS","CE","DBL","EL","ELC","ET","FOB","GC","IFT","LXM","LXU","MS","NL","NT","ROB","RUB"};
			
				
				HSSFSheet sheet = wb.createSheet("Subject Summary");
				sheet.setColumnWidth(0, 2000);
		        sheet.setColumnWidth(1, 7000);
		        sheet.setColumnWidth(2, 2500);
		        sheet.setColumnWidth(3, 2500);
		        sheet.setColumnWidth(4, 2500);
		        sheet.setColumnWidth(5, 2500);
		        sheet.setColumnWidth(6, 2500);
		        sheet.setColumnWidth(7, 2500);
		        sheet.setColumnWidth(8, 2500);
		        sheet.setColumnWidth(9, 2500);
		        sheet.setColumnWidth(10, 2500);
		        sheet.setColumnWidth(11, 2500);
		        sheet.setColumnWidth(12, 2500);
		        sheet.setColumnWidth(13, 2500);
		        sheet.setColumnWidth(14, 2500);
		        sheet.setColumnWidth(15, 2500);
		        sheet.setColumnWidth(16, 2500);
		        sheet.setColumnWidth(17, 2500);
		        sheet.setColumnWidth(18, 3500);
		        sheet.setColumnWidth(19, 2500);
		        sheet.setColumnWidth(20, 2500);
		        sheet.setColumnWidth(21, 2500);
		        sheet.setColumnWidth(22, 2500);
		        sheet.setColumnWidth(23, 2500);
		        sheet.setColumnWidth(24, 2500);
		        sheet.setColumnWidth(25, 2500);
		        sheet.setColumnWidth(26, 2500);
		        sheet.setColumnWidth(27, 2500);
		        sheet.setColumnWidth(28, 2500);
		        sheet.setColumnWidth(29, 2500);
		        sheet.setColumnWidth(30, 2500);
		        sheet.setColumnWidth(31, 2500);
		        sheet.setColumnWidth(32, 2500);
		        sheet.setColumnWidth(33, 2500);
		        sheet.setColumnWidth(34, 2500);
		        sheet.setColumnWidth(35, 2500);
		        sheet.setColumnWidth(36, 2500);
		        sheet.setColumnWidth(37, 2500);
		        sheet.setColumnWidth(38, 2500);
		       
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				sheet.getPrintSetup().setLandscape(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				cm = ReportSubjectData(cond);
				//cm = getReportDataMonthWise(Refclass[index],condMARKTOFinal,condYear);
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;				
				
				HSSFRichTextString string;
				
				HSSFRow row = sheet.createRow(rnum++);
				for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        0, //last row  (0-based)
				        3, //first column (0-based)
				       19  //last column  (0-based)
				));
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        0, //last row  (0-based)
				        20, //first column (0-based)
				       36  //last column  (0-based)
				));
				
				row = sheet.createRow(rnum++);
				for (int i = 0; i < arrh1RoleWise.length; i++) {
					string = new HSSFRichTextString(arrh1RoleWise[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
			
				}
				int sno=1;
				//System.out.println("cm.size()"+cm.size());
				//int sumY1Total=0,sumY1Dis=0,sumY2Total=0,sumY2Dis=0,sumY3Total=0,sumY3Dis=0,sumY4Total=0,sumY4Dis=0;
				for (int i = 0; i < cm.size(); i++) {
							//System.out.println("cm.get(i).getField1().toString()==="+cm.get(i).getField1());
							if(!(i==(cm.size()-1))){
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
					//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
						string = new HSSFRichTextString(cm.get(i).getField3().toString());
						string.applyFont(font_cell);
						//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
						//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
						createCell(wb, row, (short) (3),
								style_header, string);
						//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
						string = new HSSFRichTextString(cm.get(i).getField4().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (4),
								style_cell, string);
						//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
						string = new HSSFRichTextString(cm.get(i).getField5().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (5),
								style_cell, string);
						//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
						string = new HSSFRichTextString(cm.get(i).getField6().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (6),
								style_cell, string);
						
						
						string = new HSSFRichTextString(cm.get(i).getField7().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (7),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField8().toString());
						string.applyFont(font_cell);
						
						createCell(wb, row, (short) (8),
								style_cell, string);
					//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
						string = new HSSFRichTextString(cm.get(i).getField9().toString());
						string.applyFont(font_cell);
						//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
						//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
						createCell(wb, row, (short) (9),
								style_cell, string);
						//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
						string = new HSSFRichTextString(cm.get(i).getField10().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (10),
								style_cell, string);
						//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
						string = new HSSFRichTextString(cm.get(i).getField11().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (11),
								style_cell, string);
						//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
						string = new HSSFRichTextString(cm.get(i).getField12().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (12),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField13().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (13),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField14().toString());
						string.applyFont(font_cell);
						
						createCell(wb, row, (short) (14),
								style_cell, string);
					//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
						string = new HSSFRichTextString(cm.get(i).getField15().toString());
						string.applyFont(font_cell);
						//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
						//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
						createCell(wb, row, (short) (15),
								style_cell, string);
						//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
						string = new HSSFRichTextString(cm.get(i).getField16().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (16),
								style_cell, string);
						//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
						string = new HSSFRichTextString(cm.get(i).getField17().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (17),
								style_cell, string);
						//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
						string = new HSSFRichTextString(cm.get(i).getField18().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (18),
								style_cell, string);
						//sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
						//sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
						string = new HSSFRichTextString(cm.get(i).getField19().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (19),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField20().toString());
						string.applyFont(font_cell);
						
						createCell(wb, row, (short) (20),
								style_header, string);
					//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
						string = new HSSFRichTextString(cm.get(i).getField21().toString());
						string.applyFont(font_cell);
						//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
						//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
						createCell(wb, row, (short) (21),
								style_cell, string);
						//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
						string = new HSSFRichTextString(cm.get(i).getField22().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (22),
								style_cell, string);
						//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
						string = new HSSFRichTextString(cm.get(i).getField23().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (23),
								style_cell, string);
						//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
						string = new HSSFRichTextString(cm.get(i).getField24().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (24),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField25().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (25),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField26().toString());
						string.applyFont(font_cell);
						
						createCell(wb, row, (short) (26),
								style_cell, string);
					//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
						string = new HSSFRichTextString(cm.get(i).getField27().toString());
						string.applyFont(font_cell);
						//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
						//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
						createCell(wb, row, (short) (27),
								style_cell, string);
						//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
						string = new HSSFRichTextString(cm.get(i).getField28().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (28),
								style_cell, string);
						//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
						string = new HSSFRichTextString(cm.get(i).getField29().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (29),
								style_cell, string);
						//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
						string = new HSSFRichTextString(cm.get(i).getField30().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (30),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField31().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (31),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField32().toString());
						string.applyFont(font_cell);
						
						createCell(wb, row, (short) (32),
								style_cell, string);
					//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
						string = new HSSFRichTextString(cm.get(i).getField33().toString());
						string.applyFont(font_cell);
						//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
						//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
						createCell(wb, row, (short) (33),
								style_cell, string);
						//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
						string = new HSSFRichTextString(cm.get(i).getField34().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (34),
								style_cell, string);
						//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
						string = new HSSFRichTextString(cm.get(i).getField35().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (35),
								style_cell, string);
						//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
						string = new HSSFRichTextString(cm.get(i).getField36().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (36),
								style_cell, string);
					
					sno++;
							}
							else{
								row = sheet.createRow(rnum++);
								string = new HSSFRichTextString("");
								string.applyFont(font_cell);
								createCell(wb, row, (short) (0),
										style_cell, string);
								string = new HSSFRichTextString("");
								string.applyFont(font_cell);
								createCell(wb, row, (short) (1),
										style_cell, string);
								string = new HSSFRichTextString(cm.get(i).getField2().toString());
								string.applyFont(font_cell);
								
								createCell(wb, row, (short) (2),
										style_header, string);
							//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
								string = new HSSFRichTextString(cm.get(i).getField3().toString());
								string.applyFont(font_cell);
								//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
								//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
								createCell(wb, row, (short) (3),
										style_header, string);
								//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
								string = new HSSFRichTextString(cm.get(i).getField4().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (4),
										style_header, string);
								//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
								string = new HSSFRichTextString(cm.get(i).getField5().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (5),
										style_header, string);
								//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
								string = new HSSFRichTextString(cm.get(i).getField6().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (6),
										style_header, string);
								
								
								string = new HSSFRichTextString(cm.get(i).getField7().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (7),
										style_header, string);
								string = new HSSFRichTextString(cm.get(i).getField8().toString());
								string.applyFont(font_cell);
								
								createCell(wb, row, (short) (8),
										style_header, string);
							//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
								string = new HSSFRichTextString(cm.get(i).getField9().toString());
								string.applyFont(font_cell);
								//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
								//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
								createCell(wb, row, (short) (9),
										style_header, string);
								//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
								string = new HSSFRichTextString(cm.get(i).getField10().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (10),
										style_header, string);
								//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
								string = new HSSFRichTextString(cm.get(i).getField11().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (11),
										style_header, string);
								//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
								string = new HSSFRichTextString(cm.get(i).getField12().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (12),
										style_header, string);
								
								string = new HSSFRichTextString(cm.get(i).getField13().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (13),
										style_header, string);
								string = new HSSFRichTextString(cm.get(i).getField14().toString());
								string.applyFont(font_cell);
								
								createCell(wb, row, (short) (14),
										style_header, string);
							//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
								string = new HSSFRichTextString(cm.get(i).getField15().toString());
								string.applyFont(font_cell);
								//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
								//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
								createCell(wb, row, (short) (15),
										style_header, string);
								//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
								string = new HSSFRichTextString(cm.get(i).getField16().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (16),
										style_header, string);
								//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
								string = new HSSFRichTextString(cm.get(i).getField17().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (17),
										style_header, string);
								//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
								string = new HSSFRichTextString(cm.get(i).getField18().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (18),
										style_header, string);
								//sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
								//sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
								string = new HSSFRichTextString(cm.get(i).getField19().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (19),
										style_header, string);
								string = new HSSFRichTextString(cm.get(i).getField20().toString());
								string.applyFont(font_cell);
								
								createCell(wb, row, (short) (20),
										style_header, string);
							//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
								string = new HSSFRichTextString(cm.get(i).getField21().toString());
								string.applyFont(font_cell);
								//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
								//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
								createCell(wb, row, (short) (21),
										style_header, string);
								//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
								string = new HSSFRichTextString(cm.get(i).getField22().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (22),
										style_header, string);
								//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
								string = new HSSFRichTextString(cm.get(i).getField23().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (23),
										style_header, string);
								//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
								string = new HSSFRichTextString(cm.get(i).getField24().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (24),
										style_header, string);
								
								string = new HSSFRichTextString(cm.get(i).getField25().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (25),
										style_header, string);
								string = new HSSFRichTextString(cm.get(i).getField26().toString());
								string.applyFont(font_cell);
								
								createCell(wb, row, (short) (26),
										style_header, string);
							//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
								string = new HSSFRichTextString(cm.get(i).getField27().toString());
								string.applyFont(font_cell);
								//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
								//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
								createCell(wb, row, (short) (27),
										style_header, string);
								//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
								string = new HSSFRichTextString(cm.get(i).getField28().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (28),
										style_header, string);
								//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
								string = new HSSFRichTextString(cm.get(i).getField29().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (29),
										style_header, string);
								//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
								string = new HSSFRichTextString(cm.get(i).getField30().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (30),
										style_header, string);
								
								string = new HSSFRichTextString(cm.get(i).getField31().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (31),
										style_header, string);
								string = new HSSFRichTextString(cm.get(i).getField32().toString());
								string.applyFont(font_cell);
								
								createCell(wb, row, (short) (32),
										style_header, string);
							//	sumPetitions = sumPetitions+ Integer.parseInt(cm.get(i).getField3());
								string = new HSSFRichTextString(cm.get(i).getField33().toString());
								string.applyFont(font_cell);
								//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
								//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
								createCell(wb, row, (short) (33),
										style_header, string);
								//sumComplied = sumComplied+ Integer.parseInt(cm.get(i).getField4());
								string = new HSSFRichTextString(cm.get(i).getField34().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (34),
										style_header, string);
								//sumPartialComplied = sumPartialComplied+ Integer.parseInt(cm.get(i).getField5());
								string = new HSSFRichTextString(cm.get(i).getField35().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (35),
										style_header, string);
								//sumNotApp = sumNotApp+ Integer.parseInt(cm.get(i).getField6());
								string = new HSSFRichTextString(cm.get(i).getField36().toString());
								string.applyFont(font_cell);
								createCell(wb, row, (short) (36),
										style_header, string);
							}
				}
				
				
				
				rnum++;
				rnum++;
				row = sheet.createRow(rnum++);
				
				string = new HSSFRichTextString("* Compliance data available since April 2016 ");
				string.applyFont(font_cell);
				createCell(wb, row, (short) (0),
						style_cell, string);
				
				sheet.addMergedRegion(new CellRangeAddress(
				        rnum-1, //first row (0-based)
				        rnum-1, //last row  (0-based)
				        0, //first column (0-based)
				       6  //last column  (0-based)
				));
				
				
				HSSFFooter footer = sheet.getFooter();
				
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				footer.setRight("As on " + df.format(new Date()));
				footer.setCenter("Page " + HSSFFooter.page() + " of "
						+ HSSFFooter.numPages());
				
			

			FileOutputStream fileOut = new FileOutputStream(FilePath);

			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;
	
	}

}
