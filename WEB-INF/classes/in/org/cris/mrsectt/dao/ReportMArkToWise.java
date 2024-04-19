package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MastersReportBean;

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

public class ReportMArkToWise {

	
	public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256; 
	 public static final int UNIT_OFFSET_LENGTH = 7; 
	 public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 }; 
	
	public String generateConsolidatedReportWithFormat(String serverpath,String criteria) {
		
		
		
		
		int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "D://";

		String FilePath = Directory + File.separator + "RFMSExcel"+ ran + ".xls";
		try {
			
			MastersReportBean bean = null;
			
			//ArrayList arrDT = new ArrayList();
			//ArrayList arr = new ArrayList();
			String headername = "Official Wise Consolidated Reports";

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
		
			
			//LocalDate localDate = LocalDate.now();
			Date date = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    String strDate = sdf.format(date);
		    
			String heading ="Status";
	
			String[] arrh1 = {"Class","Year","Total No of Cases","Disposed Cases","% of Disposal"};
			
			String[] arrh2 = {"S.No","Incoming Date","Name","VIP Status","Subject Type","Subject"};
			
			HSSFSheet sheet = wb.createSheet((1) + ". "+heading);
			sheet.setColumnWidth(0, 2000);
	        sheet.setColumnWidth(1, 6000);
	        sheet.setColumnWidth(2, 3000);
	        sheet.setColumnWidth(3, 3000);
	        sheet.setColumnWidth(4, 3000);
	        sheet.setColumnWidth(5, 3000);
	        sheet.setColumnWidth(6, 3000);
			HSSFPrintSetup ps = sheet.getPrintSetup();
			sheet.setAutobreaks(true);
			ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			//ps.setLandscape(layout.equalsIgnoreCase("L"));
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			sheet.setHorizontallyCenter(true);
			
			
			
			
			// Create Sheet Header for Print
			HSSFHeader header = sheet.getHeader();
			/*header.setLeft("IRPSM Reports");
			header.setRight(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);*/
			
			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);

			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);
			
			
			int rnum = 0;
			
			// Create a row of the present sheet
			/*
						string = new HSSFRichTextString(heading);
			string.applyFont(font_Header);
			createCell(wb, row, (short) (0), style_header, string);
			*/
			
			//sheet.addMergedRegion(new CellRangeAddress(rnum,rnum,1,7));
			int sheetno=1;
			HSSFRow row = sheet.createRow(rnum++);
			HSSFRichTextString string;

			
			//sheet.createRow(rnum++);
			// Print the Report header fields
			
			
			int sno = 1;
			
			ArrayList<CommonBean> officials = getMarkTo();
				
				
				for(int i=0;i<officials.size();i++)
				{
					
					if(rnum>6000)
					{
						sheet = wb.createSheet(++sheetno + ". "+heading);
						rnum=1;
					}
					
					row = sheet.createRow(rnum++);
					/*string = new HSSFRichTextString(officials.get(i).getField1());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_cell, string);*/
					string = new HSSFRichTextString(officials.get(i).getField2());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_header, string);
					
					//sheet.addMergedRegion(new Region(rnum-1, (short) (1), rnum-1, (short) (12)));
					sheet.addMergedRegion(new CellRangeAddress(rnum-1, rnum-1, 1, 12));
					
					//System.out.println(rnum+":"+officials.get(i).getField1()+":"+officials.get(i).getField2());
					
					rnum++;
					row = sheet.createRow(rnum++);
					
					
					
					for(int j=0;j<4;j++)
					{
						string = new HSSFRichTextString((2014+j)+"");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (1+3*j),style_header, string);	
						sheet.addMergedRegion(new CellRangeAddress(rnum-1,rnum-1, 1+3*j, 3+3*j));
						
					}
					
					row = sheet.createRow(rnum++);
					for(int j=0;j<4;j++)
					{
						string = new HSSFRichTextString("Total");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (j*3+1),style_header, string);					
						
						string = new HSSFRichTextString("Despensed");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (j*3+2),style_header, string);
						
						string = new HSSFRichTextString("%age");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (j*3+3),style_header, string);						
						
						//getMarkToDetails
					}
					
					
					
					
						row = sheet.createRow(rnum++);
						int summaryRowNum=rnum;
						string = new HSSFRichTextString("A");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (0),style_header, string);					
						
						/*ArrayList<CommonBean> refclassyeardtl = getRefYearDtl(officials.get(1).getField1(),"A");
						
						if(refclassyeardtl.size()>0){
						string = new HSSFRichTextString(refclassyeardtl.get(0).getField1());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (1),style_header, string);	
						
						string = new HSSFRichTextString(refclassyeardtl.get(0).getField2());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (2),style_header, string);
						
						string = new HSSFRichTextString(refclassyeardtl.get(0).getField3());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (3),style_header, string);
 						
						string = new HSSFRichTextString(refclassyeardtl.get(0).getField4());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (4),style_header, string);
						
						
						string = new HSSFRichTextString(refclassyeardtl.get(0).getField5());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (5),style_header, string);
						
						
						string = new HSSFRichTextString(refclassyeardtl.get(0).getField6());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (6),style_header, string);
						
						string = new HSSFRichTextString(refclassyeardtl.get(0).getField7());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (7),style_header, string);
						
						
						string = new HSSFRichTextString(refclassyeardtl.get(0).getField8());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (8),style_header, string);
						
						
						string = new HSSFRichTextString(refclassyeardtl.get(0).getField9());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (9),style_header, string);
						
						
						}*/
						
						
						
						row = sheet.createRow(rnum++);
						string = new HSSFRichTextString("B");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (0),style_header, string);
						
						row = sheet.createRow(rnum++);
						string = new HSSFRichTextString("C");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (0),style_header, string);						
						
						row = sheet.createRow(rnum++);
						string = new HSSFRichTextString("E-mail");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (0),style_header, string);
						
						
						ArrayList<CommonBean> dtl =  getMarkToDetails(officials.get(i).getField1(),criteria);
						
						
						
						
						String oldrefclass="";
						for(CommonBean bn : dtl)
						{
							
							if(!oldrefclass.equals(bn.getField1()))
							{
								
								rnum++;
								row = sheet.createRow(rnum++);
								string = new HSSFRichTextString("List of "+bn.getField1()+" Cases");
								string.applyFont(font_cell);
								createCell(wb, row, (short) (0),style_header, string);
								
								oldrefclass=bn.getField1();
								sheet.addMergedRegion(new CellRangeAddress(rnum-1, rnum-1, 0, 12));
								sno=0;
								
								rnum++;
								row = sheet.createRow(rnum++);
								string = new HSSFRichTextString("S.No.");
								string.applyFont(font_cell);
								createCell(wb, row, (short) (0),style_header, string);
								
								string = new HSSFRichTextString("Date");
								string.applyFont(font_cell);
								createCell(wb, row, (short) (1),style_header, string);
								
								string = new HSSFRichTextString("Name");
								string.applyFont(font_cell);
								createCell(wb, row, (short) (2),style_header, string);
								
								string = new HSSFRichTextString("VIP Status");
								string.applyFont(font_cell);
								createCell(wb, row, (short) (3),style_header, string);
								
								string = new HSSFRichTextString("Category");
								string.applyFont(font_cell);
								createCell(wb, row, (short) (4),style_header, string);
								
								string = new HSSFRichTextString("Subject");
								string.applyFont(font_cell);
								createCell(wb, row, (short) (5),style_header, string);
								
								
								sheet.addMergedRegion(new CellRangeAddress(rnum-1, rnum-1, 5, 12));
							}
							
							if(bn.getField9().equals("0")){
								
							
							row = sheet.createRow(rnum++);
							string = new HSSFRichTextString((++sno)+"");
							string.applyFont(font_cell);
							createCell(wb, row, (short) (0),style_cell, string);
							
							
							
							string = new HSSFRichTextString(bn.getField3());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (1),style_cell, string);
							
							string = new HSSFRichTextString(bn.getField5());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (2),style_cell, string);
							
							string = new HSSFRichTextString(bn.getField6());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (3),style_cell, string);
							
							string = new HSSFRichTextString(bn.getField7());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (4),style_cell, string);
							
							string = new HSSFRichTextString(bn.getField8());
							string.applyFont(font_cell);
							createCell(wb, row, (short) (5),style_cell, string);
							sheet.addMergedRegion(new CellRangeAddress(rnum-1, rnum-1, 5,  12));
							}
							
							oldrefclass=bn.getField1();
								
						}
						
						
					
					
								
					
					
					rnum=rnum+2;
				}
				
				
				
				/*//	System.out.println("i is not zerooooooo");
					if(cm.get(i).getField2().toString().equalsIgnoreCase(cm.get(i-1).getField2().toString()))
					{
						//System.out.println("if is true");
					string = new HSSFRichTextString("");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_cell, string);
					string = new HSSFRichTextString("");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_cell, string);
					}
					else{
						sno=sno+1;
						string = new HSSFRichTextString(sno+"");
						string.applyFont(font_cell);
						createCell(wb, row, (short) (0),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField2().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (1),
								style_cell, string);	
					}
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
					string = new HSSFRichTextString(cm.get(i).getField7().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (6),
							style_cell, string);*/
				

			
			
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

	
	
	
public String generateConsolidatedReportCaseWise(String serverpath,
		String condMARKTOFinal) {
	

	
	int ran = (int) (1000000 * Math.random());
//	String Directory = serverpath;
	String Directory = "D://";

	String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
	try {
		
		String[] Refclass ={"A","B","C","E"};
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
		
		for (int index = 0; index < Refclass.length; index++) {
			ArrayList<CommonBean> cm =new ArrayList();	
			
			Date date = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    String strDate = sdf.format(date);
		    
			String heading="";
			if (Refclass[index].equalsIgnoreCase("A")){
				heading="A Class";
			}
			else if (Refclass[index].equalsIgnoreCase("B")){
				heading="B Class";
			}
			else if (Refclass[index].equalsIgnoreCase("C")){
				heading="C Class";
			}
			else if (Refclass[index].equalsIgnoreCase("E")){
				heading="Email";
			}
			headername = "Reference Class wise Status Report as on " +strDate;
			String[] year = {"2014","2015","2016","2017"};
			String[] arrh = {"S.No","Marked To","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal"};
			
			
			HSSFSheet sheet = wb.createSheet((index+1) + ". "+heading);
			sheet.setColumnWidth(0, 2000);
	        sheet.setColumnWidth(1, 6000);
	        sheet.setColumnWidth(2, 3500);
	        sheet.setColumnWidth(3, 3500);
	        sheet.setColumnWidth(4, 3500);
	        sheet.setColumnWidth(5, 3500);
	        sheet.setColumnWidth(6, 3500);
	        sheet.setColumnWidth(7, 3500);
	        sheet.setColumnWidth(8, 3500);
	        sheet.setColumnWidth(9, 3500);
	        sheet.setColumnWidth(10, 3500);
	        sheet.setColumnWidth(11, 3500);
	        sheet.setColumnWidth(12, 3500);
	        sheet.setColumnWidth(13, 3500);
			HSSFPrintSetup ps = sheet.getPrintSetup();
			sheet.setAutobreaks(true);
			ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			sheet.setHorizontallyCenter(true);
			
			cm = ConsolidatedReportCaseWiseData(Refclass[index],condMARKTOFinal);
			//cm = getReportDataMonthWise(Refclass[index],condMARKTOFinal,condYear);
			
			HSSFHeader header = sheet.getHeader();
			
			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);

			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);
			
			
			int rnum = 0;
			
			HSSFRow row = sheet.createRow(rnum++);
			HSSFRichTextString string;

			
			//sheet.createRow(rnum++);
			// Print the Report header fields
			string = new HSSFRichTextString("");
			createCell(wb, row, (short) (0), style_header, string);
			createCell(wb, row, (short) (1), style_header,string);
			int inc =2;
			int inc1 =4;
			for (int i = 0; i < year.length; i++) {
				string = new HSSFRichTextString(year[i].toString()
						.replaceAll("&deg;", "°"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (inc), style_header, string);
				
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        0, //last row  (0-based)
				        inc, //first column (0-based)
				       inc1  //last column  (0-based)
				));
			inc=inc+3;
			inc1=inc1+3;

			}
			row = sheet.createRow(rnum++);
			for (int i = 0; i < arrh.length; i++) {
				string = new HSSFRichTextString(arrh[i].toString()
						.replaceAll("&deg;", "°"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (i), style_header, string);
			

			}
			
			int sno = 1;
			int sumY1Total=0,sumY1Dis=0,sumY2Total=0,sumY2Dis=0,sumY3Total=0,sumY3Dis=0,sumY4Total=0,sumY4Dis=0;
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
				string = new HSSFRichTextString(cm.get(i).getField3().toString());
				string.applyFont(font_cell);
				
				createCell(wb, row, (short) (2),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField4().toString());
				string.applyFont(font_cell);
				sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
				sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
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
				string = new HSSFRichTextString(cm.get(i).getField7().toString());
				string.applyFont(font_cell);
				sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
				sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
				createCell(wb, row, (short) (6),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField8().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (7),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField9().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (8),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField10().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (9),
						style_cell, string);
				sumY3Total =sumY3Total+ (Integer.parseInt(cm.get(i).getField9()));
				sumY3Dis =sumY3Dis+ (Integer.parseInt(cm.get(i).getField10()));
				string = new HSSFRichTextString(cm.get(i).getField11().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (10),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField12().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (11),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField13().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (12),
						style_cell, string);
				sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
				sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
				string = new HSSFRichTextString(cm.get(i).getField14().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (13),
						style_cell, string);
				sno++;
			}

			
			
			row = sheet.createRow(rnum++);
			row = sheet.createRow(rnum++);
			string = new HSSFRichTextString("Total");
			string.applyFont(font_Header);
			sheet.addMergedRegion(new CellRangeAddress(
			        rnum, //first row (0-based)
			       rnum, //last row  (0-based)
			        0, //first column (0-based)
			        1  //last column  (0-based)
			        ));
			createCell(wb, row, (short) (1), style_header, string);
			
			
			string = new HSSFRichTextString(sumY1Total+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (2),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY1Dis+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (3),
					style_cell, string);
			
			string = new HSSFRichTextString("");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (4),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY2Total+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (5),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY2Dis+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (6),
					style_cell, string);
			
			string = new HSSFRichTextString("");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (7),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY3Total+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (8),
					style_cell, string);
			string = new HSSFRichTextString(sumY3Dis+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (9),
					style_cell, string);
			
			string = new HSSFRichTextString("");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (10),
					style_cell, string);
			string = new HSSFRichTextString(sumY4Total+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (11),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY4Dis+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (12),
					style_cell, string);
			
			string = new HSSFRichTextString("");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (13),
					style_cell, string);
			
			
			HSSFFooter footer = sheet.getFooter();
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			footer.setRight("As on " + df.format(new Date()));
			footer.setCenter("Page " + HSSFFooter.page() + " of "
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



public String generateConsolidatedReportYearWise(String serverpath,
		String condMARKTOFinal) {
	

	
	int ran = (int) (1000000 * Math.random());
//	String Directory = serverpath;
	String Directory = "D://";

	String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
	try {
		
		String[] Refclass ={"A","B","C","E"};
		String[] year = {"2014","2015","2016","2017"};
		MastersReportBean bean = null;
		
		//ArrayList arrDT = new ArrayList();
		//ArrayList arr = new ArrayList();
		String headername = "Case Wise Reports ";

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
		
		for (int index = 0; index < year.length; index++) {
			ArrayList<CommonBean> cm =new ArrayList();	
			
			Date date = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    String strDate = sdf.format(date);
		    
			String heading="";
			
			headername = "Year Wise Status Report as on " +strDate;
			
			String[] arrh = {"S.No","Marked To","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal"};
			
			
			HSSFSheet sheet = wb.createSheet((index+1) + ". "+year[index]);
			sheet.setColumnWidth(0, 2000);
	        sheet.setColumnWidth(1, 6000);
	        sheet.setColumnWidth(2, 3500);
	        sheet.setColumnWidth(3, 3500);
	        sheet.setColumnWidth(4, 3500);
	        sheet.setColumnWidth(5, 3500);
	        sheet.setColumnWidth(6, 3500);
	        sheet.setColumnWidth(7, 3500);
	        sheet.setColumnWidth(8, 3500);
	        sheet.setColumnWidth(9, 3500);
	        sheet.setColumnWidth(10, 3500);
	        sheet.setColumnWidth(11, 3500);
	        sheet.setColumnWidth(12, 3500);
	        sheet.setColumnWidth(13, 3500);
			HSSFPrintSetup ps = sheet.getPrintSetup();
			sheet.setAutobreaks(true);
			ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			sheet.setHorizontallyCenter(true);
			
			cm = ConsolidatedReportYearWiseData(year[index],condMARKTOFinal);
			//cm = getReportDataMonthWise(Refclass[index],condMARKTOFinal,condYear);
			
			HSSFHeader header = sheet.getHeader();
			
			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);

			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);
			
			
			int rnum = 0;
			
			HSSFRow row = sheet.createRow(rnum++);
			HSSFRichTextString string;

			
			//sheet.createRow(rnum++);
			// Print the Report header fields
			string = new HSSFRichTextString("");
			createCell(wb, row, (short) (0), style_header, string);
			createCell(wb, row, (short) (1), style_header,string);
			int inc =2;
			int inc1 =4;
			for (int i = 0; i < year.length; i++) {
				String head="";
				if (Refclass[i].equalsIgnoreCase("A")){
					head="A Class";
				}
				else if (Refclass[i].equalsIgnoreCase("B")){
					head="B Class";
				}
				else if (Refclass[i].equalsIgnoreCase("C")){
					head="C Class";
				}
				else if (Refclass[i].equalsIgnoreCase("E")){
					head="Email Category";
				}
				string = new HSSFRichTextString(head);
				string.applyFont(font_Header);
				createCell(wb, row, (short) (inc), style_header, string);
				
				sheet.addMergedRegion(new CellRangeAddress(
				        0, //first row (0-based)
				        0, //last row  (0-based)
				        inc, //first column (0-based)
				       inc1  //last column  (0-based)
				));
			inc=inc+3;
			inc1=inc1+3;

			}
			row = sheet.createRow(rnum++);
			for (int i = 0; i < arrh.length; i++) {
				string = new HSSFRichTextString(arrh[i].toString()
						.replaceAll("&deg;", "°"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (i), style_header, string);
			

			}
			
			int sno = 1;
			int sumY1Total=0,sumY1Dis=0,sumY2Total=0,sumY2Dis=0,sumY3Total=0,sumY3Dis=0,sumY4Total=0,sumY4Dis=0;
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
				string = new HSSFRichTextString(cm.get(i).getField3().toString());
				string.applyFont(font_cell);
				
				createCell(wb, row, (short) (2),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField4().toString());
				string.applyFont(font_cell);
				sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
				sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
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
				string = new HSSFRichTextString(cm.get(i).getField7().toString());
				string.applyFont(font_cell);
				sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
				sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
				createCell(wb, row, (short) (6),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField8().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (7),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField9().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (8),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField10().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (9),
						style_cell, string);
				sumY3Total =sumY3Total+ (Integer.parseInt(cm.get(i).getField9()));
				sumY3Dis =sumY3Dis+ (Integer.parseInt(cm.get(i).getField10()));
				string = new HSSFRichTextString(cm.get(i).getField11().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (10),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField12().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (11),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField13().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (12),
						style_cell, string);
				sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
				sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
				string = new HSSFRichTextString(cm.get(i).getField14().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (13),
						style_cell, string);
				sno++;
			}

			
			
			row = sheet.createRow(rnum++);
			row = sheet.createRow(rnum++);
			string = new HSSFRichTextString("Total");
			string.applyFont(font_Header);
			sheet.addMergedRegion(new CellRangeAddress(
			        rnum, //first row (0-based)
			       rnum, //last row  (0-based)
			        0, //first column (0-based)
			        1  //last column  (0-based)
			        ));
			createCell(wb, row, (short) (1), style_header, string);
			
			
			string = new HSSFRichTextString(sumY1Total+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (2),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY1Dis+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (3),
					style_cell, string);
			
			int per1 =sumY1Dis/sumY1Total*100;
			string = new HSSFRichTextString((per1+"").substring(1, 5));
			string.applyFont(font_cell);
			createCell(wb, row, (short) (4),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY2Total+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (5),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY2Dis+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (6),
					style_cell, string);
			
			string = new HSSFRichTextString("");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (7),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY3Total+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (8),
					style_cell, string);
			string = new HSSFRichTextString(sumY3Dis+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (9),
					style_cell, string);
			
			string = new HSSFRichTextString("");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (10),
					style_cell, string);
			string = new HSSFRichTextString(sumY4Total+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (11),
					style_cell, string);
			
			string = new HSSFRichTextString(sumY4Dis+"");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (12),
					style_cell, string);
			
			string = new HSSFRichTextString("");
			string.applyFont(font_cell);
			createCell(wb, row, (short) (13),
					style_cell, string);
			

			
			HSSFFooter footer = sheet.getFooter();
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			footer.setRight("As on " + df.format(new Date()));
			footer.setCenter("Page " + HSSFFooter.page() + " of "
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
public ArrayList<CommonBean> getMarkTo() {

	String strSQL = "select distinct  b.markingto,c.ROLENAME "
					+ " from trnreference a, trnmarking b,mstrole c "
					+ " where "
					+ " a.tenureid>=12 "
					//+ "(select max(tenureid) from msttenure where roleid=430) "
					+ " and "
					+ " ( to_char(incomingdate,'yyyy') in (2014,2015,2016,2017) ) "
					+ " and "
					+ " a.refid =b.refid "
					+ " and a.REFCLASS in ('A','B','C','E') and b.markingto=c.ROLEID order by c.rolename";
	
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 2);
}

public ArrayList<CommonBean> getMarkToDetails(String markto,String criteria) {

	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	 strSQL = "select CASE WHEN a.REFCATEGORY='E' THEN 'E' ELSE a.refclass END REFNEW , "
					+ " a.refclass,to_char(a.incomingdate,'dd/mm/yyyy'),to_char(a.incomingdate,'yyyy') refyear,a.REFERENCENAME,a.VIPSTATUS "
					+ " ,c.SUBJECTNAME "
					+ " ,a.subject,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed from trnreference a, trnmarking b "
					+ " ,MSTSUBJECT c "
					+ " where "
					+ " a.SUBJECTCODE=c.SUBJECTCODE(+) and "
					+ " a.tenureid>=12 "
					//+ "(select max(tenureid) from msttenure where roleid=430) "
					+ " and "
					+ " ( to_char(incomingdate,'yyyy') in (2014,2015,2016,2017) ) "
					+ " and "
					+ " a.refid =b.refid "
					+ " and b.markingto ='"+markto+"' "
					+ " and a.REFCLASS in ('A','B','C','E') "
					+ " order by a.refclass,a.incomingdate,a.REFERENCENAME,a.VIPSTATUS,c.SUBJECTNAME";
	}
	else {
		strSQL = "select CASE WHEN a.REFCATEGORY='E' THEN 'E' ELSE a.refclass END REFNEW , "
				+ " a.refclass,to_char(a.incomingdate,'dd/mm/yyyy'),to_char(a.incomingdate,'yyyy') refyear,a.REFERENCENAME,a.VIPSTATUS "
				+ " ,c.SUBJECTNAME "
				+ " ,a.subject,DECODE(NVL(a.fmid,0),0,0,1) disposed from trnreference a, trnmarking b "
				+ " ,MSTSUBJECT c "
				+ " where "
				+ " a.SUBJECTCODE=c.SUBJECTCODE(+) and "
				+ " a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ " and "
				+ " ( to_char(incomingdate,'yyyy') in (2014,2015,2016,2017) ) "
				+ " and "
				+ " a.refid =b.refid "
				+ " and b.markingto ='"+markto+"' "
				+ " and a.REFCLASS in ('A','B','C','E') "
				+ " order by a.refclass,a.incomingdate,a.REFERENCENAME,a.VIPSTATUS,c.SUBJECTNAME";
	
		
	}
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 9);
}

public ArrayList<CommonBean> getRefYearDtl(String markto,String refclass) {

	String strSQL = 
			
			refclass.equalsIgnoreCase("e")? 
					
					"select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
					+ " sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
					+ " sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
					+ " sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
					+ " sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2) "
					+ " from (select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
					+ " decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate, "
					+ " 'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, "
					+ " decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
					+ " DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
					+ " decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 from trnreference a, "
					+ " trnmarking b  where  (a.refclass ='C') and  a.tenureid>=12 "
					//+ "(select max(tenureid) from msttenure where roleid=430) "
					+ " and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  and  a.refid =b.refid  and b.MARKINGTO='"+markto+"' and a.refclass='E' or a.REFCATEGORY='E' " 
					+ " order by b.markingto,a.incomingdate) z "
					+ " group by markingto order by rolename"
					:
			"select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
			+ " sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ " sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ " sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
			+ " sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2) "
			+ " from (select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
			+ " decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate, "
			+ " 'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, "
			+ " decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
			+ " DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ " decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 from trnreference a, "
			+ " trnmarking b  where  (a.refclass ='C') and  a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ " and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  and  a.refid =b.refid  and b.MARKINGTO='"+markto+"' and a.refclass='"+refclass+"' and a.REFCATEGORY<>'E'" 
			+ " order by b.markingto,a.incomingdate) z "
			+ " group by markingto order by rolename";
	
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 14);
}
public ArrayList<CommonBean> getReportDataMonthWise(String refclass,String condMARKTOFinal, String condYear) {
	String condOfE ="";
	if(refclass.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy'), sum(subjectcount),sum(disposed), "
				+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
				+ " from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
				+ " where  "
				+ "(a.refclass ='"+refclass+"'"+condOfE
				+ ") " 
				+ " and "
				+ " a.tenureid>=12 "
			//	+ "(select max(tenureid) from msttenure where roleid=430) "
				+ " and "
				+ "(a.incomingdate between to_date('01/01/2017','dd/mm/yyyy') and to_date('31/12/2017','dd/mm/yyyy')) "
				+ " and"
				+ " a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
				+ " group by markingto,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') "
				+ " order by rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') ";
	
	//System.out.println(" getReportDataMonthWise getReportDataMonthWise++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 8);
}

private ArrayList<CommonBean> ConsolidatedReportCaseWiseData(String refclass,String condMARKTOFinal) {
	// TODO Auto-generated method stub
	String condOfE="";
	if(refclass.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
			+ "sum(subjectcount14),sum(disposed14), "
			+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ "sum(subjectcount15),sum(disposed15), "
			+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ "sum(subjectcount16),sum(disposed16), "
			+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
			+ "sum(subjectcount17),sum(disposed17), "
			+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2) "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where  "
			+ "(a.refclass ='"+refclass+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  "
			+ "and  "
			+ "a.refid =b.refid "+condMARKTOFinal+"  order by b.markingto,a.incomingdate) z "
			+ "group by markingto "
			+ "order by rolename ";
	
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 14);
}

private ArrayList<CommonBean> ConsolidatedReportYearWiseData(String year,String condMARKTOFinal) {
	// TODO Auto-generated method stub
	String condYear="";
	
	String strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
			+ "sum(subjectcountA),sum(disposedA), "
			+ "round(sum(disposedA)/decode(sum(subjectcountA),0,1,sum(subjectcountA))*100,2), "
			+ "sum(subjectcountB),sum(disposedB), "
			+ "round(sum(disposedB)/decode(sum(subjectcountB),0,1,sum(subjectcountB))*100,2), "
			+ "sum(subjectcountC),sum(disposedC), "
			+ "round(sum(disposedC)/decode(sum(subjectcountC),0,1,sum(subjectcountC))*100,2), "
			+ "sum(subjectcountE),sum(disposedE), "
			+ "round(sum(disposedE)/decode(sum(subjectcountE),0,1,sum(subjectcountE))*100,2) "
			+ "from  "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(a.refclass,'A',1,0) subjectcountA, "
			+ "decode(a.refclass,'A',DECODE(NVL(a.fmid,0),0,0,1),0) disposedA, "
			+ "decode(a.refclass,'B',1,0) subjectcountB, "
			+ "decode(a.refclass,'B',DECODE(NVL(a.fmid,0),0,0,1),0) disposedB, "
			+ "decode(a.refclass,'C',1,0) subjectcountC, "
			+ "decode(a.refclass,'C',DECODE(NVL(a.fmid,0),0,0,1),0) disposedC, "
			+ "decode(a.refcategory,'E',1,0) subjectcountE, "
			+ "decode(a.refcategory,'E',DECODE(NVL(a.fmid,0),0,0,1),0) disposedE "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where   "
			+ "(a.refclass  in ('A','B','C','M','E') "
			+ ")  "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy')='"+year+"' "
			+ "and  "
			+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z  "
			+ "group by markingto "
			+ "order by rolename ";
	
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 14);
}
}
