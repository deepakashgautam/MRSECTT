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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MastersReportBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class MonthlyReportDAO {

	public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256; 
	 public static final int UNIT_OFFSET_LENGTH = 7; 
	 public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 }; 
	
	public String generateConsolidatedReportWithFormat(String serverpath, String[] Refclass,String condMARKTOFinal, String condYear,String year,String criteria) {
		
		
		
		
		int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "D://";

		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		try {
			
			MastersReportBean bean = null;
			
			//ArrayList arrDT = new ArrayList();
			//ArrayList arr = new ArrayList();
			String headername = "Consolidated Reports";

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
			ArrayList<CommonBean> cm1 =new ArrayList();	
			
			//LocalDate localDate = LocalDate.now();
			Date date = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    String strDate = sdf.format(date);
		    
			String heading ="";
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
			else if (Refclass[index].equalsIgnoreCase("M")){
				heading="M Class";
			}
			headername= "Status of "+heading+"  receipts as on "+strDate;
			String[] arrheadTable = {"Year","Total","Disposed ","Pending","% of Disposal"};
			String[] arrh = {"S.No","Marked To","Year","Total No of Cases","Disposed Cases","% of Disposal","Pending Cases"};
			
			HSSFSheet sheet = wb.createSheet((index+1) + ". "+heading);
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
			
			int rnum = 0;
			
			// Create a row of the present sheet
			/*
						string = new HSSFRichTextString(heading);
			string.applyFont(font_Header);
			createCell(wb, row, (short) (0), style_header, string);
			*/
			
			//sheet.addMergedRegion(new CellRangeAddress(rnum,rnum,1,7));
			
			HSSFRow row = sheet.createRow(rnum++);
			HSSFRichTextString string;
			
			HSSFHeader header = sheet.getHeader();
			/*header.setLeft("IRPSM Reports");
			header.setRight(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);*/
			
			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);

			header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
					+ HSSFHeader.fontSize((short) 16) + headername);
			
			
			
			cm1 = getReportHeader(Refclass[index],criteria);
			
			
			//header table code
			
			for (int i = 0; i < arrheadTable.length; i++) {
				string = new HSSFRichTextString(arrheadTable[i].toString()
						.replaceAll("&deg;", "°"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (i+2), style_header, string);
			}
			
			for (int i = 0; i < cm1.size(); i++) {
				row = sheet.createRow(rnum++);
					//System.out.println("i is zerooooooo");
				string = new HSSFRichTextString(cm1.get(i).getField1().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (2),
						style_cell, string);
				string = new HSSFRichTextString(cm1.get(i).getField2().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (3),
						style_cell, string);
				string = new HSSFRichTextString(cm1.get(i).getField3().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (4),
						style_cell, string);
				string = new HSSFRichTextString(cm1.get(i).getField5().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (5),
						style_cell, string);
				string = new HSSFRichTextString(cm1.get(i).getField4().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (6),
						style_cell, string);
			
				
			}
			
			
		
			//header table code ends
			cm = getReportData(Refclass[index],condMARKTOFinal,condYear,criteria);
			
			
			// Create Sheet Header for Print
		
			//sheet.createRow(rnum++);
			// Print the Report header fields
			
			//rnum = rnum++;
			for (int i = 0; i < arrh.length; i++) {
				string = new HSSFRichTextString(arrh[i].toString()
						.replaceAll("&deg;", "°"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (i), style_header, string);
			

			}
			
			int sno = 1;
			for (int i = 0; i < cm.size(); i++) {
				row = sheet.createRow(rnum++);
				if(i==0){
					//System.out.println("i is zerooooooo");
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
						style_cell, string);
				}
				else
				{
				//	System.out.println("i is not zerooooooo");
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
							style_cell, string);
				}

			}
			
			HSSFFooter footer = sheet.getFooter();
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			footer.setRight("As on " + df.format(new Date()));
			footer.setCenter("Page " + HSSFFooter.page() + " of "
					+ HSSFFooter.numPages());
			}
			
			for (int index = 0; index < Refclass.length; index++) {
				ArrayList<CommonBean> cm =new ArrayList();	
				ArrayList<CommonBean> cm1 =new ArrayList();	
				
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
				else if (Refclass[index].equalsIgnoreCase("M")){
					heading="M Class";
				}
				headername = "Status of "+heading+"  receipts as on " +strDate;
				String[] arrheadTable = {"Year","Total","Disposed ","Pending","% of Disposal"};
				String[] arrh = {"S.No","Marked To","Year","Total No of Cases","Disposed Cases","% of Disposal","Pending Cases"};
				
				
				HSSFSheet sheet = wb.createSheet((index+1) + ". 2017(Monthly)-"+heading);
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
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;
				
				HSSFRow row = sheet.createRow(rnum++);
				HSSFRichTextString string;

				cm1 = getReportHeader2(Refclass[index],criteria);
				
				//header table code
				
				for (int i = 0; i < arrheadTable.length; i++) {
					string = new HSSFRichTextString(arrheadTable[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i+2), style_header, string);
				}
				
				for (int i = 0; i < cm1.size(); i++) {
					row = sheet.createRow(rnum++);
					
						//System.out.println("i is zerooooooo");
					string = new HSSFRichTextString(cm1.get(i).getField1().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (2),
							style_cell, string);
					string = new HSSFRichTextString(cm1.get(i).getField2().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (3),
							style_cell, string);
					string = new HSSFRichTextString(cm1.get(i).getField3().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (4),
							style_cell, string);
					string = new HSSFRichTextString(cm1.get(i).getField5().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (5),
							style_cell, string);
					string = new HSSFRichTextString(cm1.get(i).getField4().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (6),
							style_cell, string);
				
					
				}
				
				
				//rnum = rnum+2;
				//header table code ends
				
				cm = getReportDataMonthWise(Refclass[index],condMARKTOFinal,condYear,criteria);
				

				// Print the Report header fields
				for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "°"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				
				int sno = 1;
				for (int i = 0; i < cm.size(); i++) {
					row = sheet.createRow(rnum++);
					if(i==0){
					//	System.out.println("i is zerooooooo");
					string = new HSSFRichTextString(sno+"");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField2().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField4().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (2),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField5().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (3),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField6().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (4),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField7().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (5),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField8().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (6),
							style_cell, string);
					}
					else
					{
						if(cm.get(i).getField2().toString().equalsIgnoreCase(cm.get(i-1).getField2().toString()))
						{
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
						string = new HSSFRichTextString(cm.get(i).getField4().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (2),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField5().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (3),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField6().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (4),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField7().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (5),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField8().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (6),
								style_cell, string);
					}	

				}

				
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
		String condMARKTOFinal,String criteria) {
	

	
	int ran = (int) (1000000 * Math.random());
	String Directory = serverpath;
	//String Directory = "D://";

	String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
	try {
		
		String[] Refclass ={"A","B","E","C","M"};
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
			else if (Refclass[index].equalsIgnoreCase("M")){
				heading="M Class";
			}
			headername = "Reference Class wise Status Report as on " +strDate;
			String[] year = {"2014","2015","2016","2017","2018","2019","2020"};
			String[] arrh = {"S.No","Marked To","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Total Disposed Cases","% of Total Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal"};
			
			
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
	        sheet.setColumnWidth(14, 3500);
	        sheet.setColumnWidth(15, 3500);
	        sheet.setColumnWidth(16, 3500);
	        sheet.setColumnWidth(17, 3500);
	        sheet.setColumnWidth(18, 3500);
	        sheet.setColumnWidth(19, 3500);
	        sheet.setColumnWidth(20, 3500);
	        sheet.setColumnWidth(21, 3500);
	        sheet.setColumnWidth(22, 3500);
	        sheet.setColumnWidth(23, 3500);
	        sheet.setColumnWidth(24, 3500);
	        sheet.setColumnWidth(25, 3500);
			HSSFPrintSetup ps = sheet.getPrintSetup();
			sheet.setAutobreaks(true);
			sheet.getPrintSetup().setLandscape(true);
			ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			sheet.setHorizontallyCenter(true);
			
			cm = ConsolidatedReportCaseWiseData(Refclass[index],condMARKTOFinal,criteria);
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
			for (int i = 0; i <= year.length; i++) {
				if(i==year.length)
				{
					string = new HSSFRichTextString("Total");
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
				}else{
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
			}
			row = sheet.createRow(rnum++);
			for (int i = 0; i < arrh.length; i++) {
				string = new HSSFRichTextString(arrh[i].toString()
						.replaceAll("&deg;", "°"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (i), style_header, string);
			

			}
			
			int sno = 1;
			//int sumY1Total=0,sumY1Dis=0,sumY2Total=0,sumY2Dis=0,sumY3Total=0,sumY3Dis=0,sumY4Total=0,sumY4Dis=0;
			for (int i = 0; i < cm.size(); i++) {
				if(i==0){
					
					row = sheet.createRow(rnum++);
					string = new HSSFRichTextString("");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField2().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_header, string);
					string = new HSSFRichTextString(cm.get(i).getField3().toString());
					string.applyFont(font_cell);
					
					createCell(wb, row, (short) (2),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField4().toString());
					string.applyFont(font_cell);
					//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
					//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
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
					//sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
					//sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
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
					//sumY3Total =sumY3Total+ (Integer.parseInt(cm.get(i).getField9()));
					//sumY3Dis =sumY3Dis+ (Integer.parseInt(cm.get(i).getField10()));
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
					//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
					//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
					string = new HSSFRichTextString(cm.get(i).getField14().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (13),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField15().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (14),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField16().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (15),
							style_cell, string);
					//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
					//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
					string = new HSSFRichTextString(cm.get(i).getField17().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (16),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField18().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (17),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField19().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (18),
							style_cell, string);
					//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
					//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (19),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (20),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (21),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (22),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (23),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (24),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (25),
							style_cell, string);
					
				}
				else{
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
				//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
				//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
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
				//sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
				//sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
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
				//sumY3Total =sumY3Total+ (Integer.parseInt(cm.get(i).getField9()));
				//sumY3Dis =sumY3Dis+ (Integer.parseInt(cm.get(i).getField10()));
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
				//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
				//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
				string = new HSSFRichTextString(cm.get(i).getField14().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (13),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField15().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (14),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField16().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (15),
						style_cell, string);
				//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
				//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
				string = new HSSFRichTextString(cm.get(i).getField17().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (16),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField18().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (17),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField19().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (18),
						style_cell, string);
				//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
				//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
				string = new HSSFRichTextString(cm.get(i).getField20().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (19),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField21().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (20),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField22().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (21),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField23().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (22),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField24().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (23),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField25().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (24),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField26().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (25),
						style_cell, string);
				
				sno++;
				}
			}

			
			
			
			
			
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
		String condMARKTOFinal,String criteria) {
	

	
	int ran = (int) (1000000 * Math.random());
	String Directory = serverpath;
	//String Directory = "D://";

	String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
	try {
		
		String[] Refclass ={"A","B","C","E","M"};
		String[] year = {"2014","2015","2016","2017","2018","2019","2020"};
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
			
			String[] arrh = {"S.No","Marked To","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Total Disposed Cases","% of Total Disposal"};
			
			
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
	        sheet.setColumnWidth(14, 3500);
	        sheet.setColumnWidth(15, 3500);
	        sheet.setColumnWidth(16, 3500);
	        sheet.setColumnWidth(17, 3500);
	        sheet.setColumnWidth(18, 3500);
	        sheet.setColumnWidth(19, 3500);
	        sheet.setColumnWidth(20, 3500);
	        sheet.setColumnWidth(21, 3500);
	        sheet.setColumnWidth(22, 3500);
			HSSFPrintSetup ps = sheet.getPrintSetup();
			sheet.setAutobreaks(true);
			sheet.getPrintSetup().setLandscape(true);
			ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			sheet.setHorizontallyCenter(true);
			
			cm = ConsolidatedReportYearWiseData(year[index],condMARKTOFinal,criteria);
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
			for (int i = 0; i <=Refclass.length; i++) {
				String head="";
				if(i==Refclass.length)
				{
					string = new HSSFRichTextString("Total");
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
				}else{
					//System.out.println("i = "+i);
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
				else if (Refclass[i].equalsIgnoreCase("M")){
					head="M Class";
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
			}
			
			row = sheet.createRow(rnum++);
			for (int i = 0; i < arrh.length; i++) {
				string = new HSSFRichTextString(arrh[i].toString()
						.replaceAll("&deg;", "°"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (i), style_header, string);
			

			}
			
			int sno = 1;
			
			//double sumY1Total=0,sumY1Dis=0,sumY2Total=0,sumY2Dis=0,sumY3Total=0,sumY3Dis=0,sumY4Total=0,sumY4Dis=0;
			
			for (int i = 0; i < cm.size(); i++) {
					if(i==0){
					
					row = sheet.createRow(rnum++);
					string = new HSSFRichTextString("");
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField2().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (1),
							style_header, string);
					string = new HSSFRichTextString(cm.get(i).getField3().toString());
					string.applyFont(font_cell);
					
					createCell(wb, row, (short) (2),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField4().toString());
					string.applyFont(font_cell);
					//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
					//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
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
					//sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
					//sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
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
					//sumY3Total =sumY3Total+ (Integer.parseInt(cm.get(i).getField9()));
					//sumY3Dis =sumY3Dis+ (Integer.parseInt(cm.get(i).getField10()));
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
					//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
					//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
					string = new HSSFRichTextString(cm.get(i).getField14().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (13),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField15().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (14),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField16().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (15),
							style_cell, string);
					//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
					//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
					string = new HSSFRichTextString(cm.get(i).getField17().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (16),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField18().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (17),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField19().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (18),
							style_cell, string);
					//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
					//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (19),
							style_cell, string);
					
				}
				else{
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
				
				//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
				//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
				
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
				
				//sumY2Total =sumY2Total+ (Integer.parseInt(cm.get(i).getField6()));
				//sumY2Dis =sumY2Dis+ (Integer.parseInt(cm.get(i).getField7()));
				
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
				
				//sumY3Total =sumY3Total+ (Integer.parseInt(cm.get(i).getField9()));
				//sumY3Dis =sumY3Dis+ (Integer.parseInt(cm.get(i).getField10()));
				
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
				
				//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
				//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
				
				string = new HSSFRichTextString(cm.get(i).getField14().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (13),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField15().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (14),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField16().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (15),
						style_cell, string);
				//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
				//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
				string = new HSSFRichTextString(cm.get(i).getField17().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (16),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField18().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (17),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField19().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (18),
						style_cell, string);
				//sumY4Total =sumY4Total+ (Integer.parseInt(cm.get(i).getField12()));
				//sumY4Dis =sumY4Dis+ (Integer.parseInt(cm.get(i).getField13()));
				string = new HSSFRichTextString(cm.get(i).getField20().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (19),
						style_cell, string);
				sno++;
				}
			}

			
			
			
			
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
/*public ArrayList<CommonBean> getReportData(String refclass,String condMARKTOFinal, String condYear,String criteria) {
	String condOfE ="";
	if(refclass.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+refclass+"' "+condOfE
			+ ") "
			+ "and "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ condYear 
			+ "and "
			+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
			+ "group by markingto,to_char(incomingdate,'yyyy') "
			+ "order by rolename,to_char(incomingdate,'yyyy')";
	}
	else {
		strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1),0) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass ='"+refclass+"' "+condOfE
				+ ") "
				+ "and "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ condYear 
				+ "and "
				+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
				+ "group by markingto,to_char(incomingdate,'yyyy') "
				+ "order by rolename,to_char(incomingdate,'yyyy')";	
		
	}
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 7);
}*/

//soumen func
public ArrayList<CommonBean> getReportData(String refclass,String condMARKTOFinal, String condYear,String criteria) {
	
	ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
	DBConnection con = new DBConnection();
	
	String condOfE ="";
	if(refclass.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	if(criteria.equalsIgnoreCase("1"))
	{
		
	/*strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+refclass+"' "+condOfE
			+ ") "
			+ "and "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ condYear 
			+ "and "
			+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
			+ "group by markingto,to_char(incomingdate,'yyyy') "
			+ "order by rolename,to_char(incomingdate,'yyyy')";*/
	
	
	strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass =? "+condOfE
			+ ") "
			+ "and "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ condYear 
			+ "and "
			+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
			+ "group by markingto,to_char(incomingdate,'yyyy') "
			+ "order by rolename,to_char(incomingdate,'yyyy')";
	
	
	}
	else 
	{
		/*strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1),0) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass ='"+refclass+"' "+condOfE
				+ ") "
				+ "and "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ condYear 
				+ "and "
				+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
				+ "group by markingto,to_char(incomingdate,'yyyy') "
				+ "order by rolename,to_char(incomingdate,'yyyy')";	*/
		
		
		strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1),0) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass =? "+condOfE
				+ ") "
				+ "and "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ condYear 
				+ "and "
				+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
				+ "group by markingto,to_char(incomingdate,'yyyy') "
				+ "order by rolename,to_char(incomingdate,'yyyy')";
		
	}
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	//return (new CommonDAO()).getSQLResult(strSQL, 7);
	
	try {
		 con.openConnection();
		 PreparedStatement ps = con.setPrepStatement(strSQL);
		
		ps.setString(1, refclass);
    		
		arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 7,con);
		//System.out.println("getReportData executed");
		
       } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }
      finally{
		con.closeConnection();
     }
	
   return arr;

	
}

/*public ArrayList<CommonBean> getReportDataMonthWise(String refclass,String condMARKTOFinal, String condYear,String criteria) {
	String condOfE ="";
	if(refclass.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	 strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy'), sum(subjectcount),sum(disposed), "
				+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
				+ " from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
				+ " where  "
				+ "(a.refclass ='"+refclass+"'"+condOfE
				+ ") " 
				+ " and "
				+ " a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ " and "
				+ "(a.incomingdate between to_date('01/01/2017','dd/mm/yyyy') and to_date('31/12/2017','dd/mm/yyyy')) "
				+ " and"
				+ " a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
				+ " group by markingto,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') "
				+ " order by rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') ";
	}
	else {
		strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy'), sum(subjectcount),sum(disposed), "
				+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
				+ " from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
				+ " where  "
				+ "(a.refclass ='"+refclass+"'"+condOfE
				+ ") " 
				+ " and "
				+ " a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ " and "
				+ "(a.incomingdate between to_date('01/01/2017','dd/mm/yyyy') and to_date('31/12/2017','dd/mm/yyyy')) "
				+ " and"
				+ " a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
				+ " group by markingto,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') "
				+ " order by rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') ";
		
		
	}
	//System.out.println(" getReportDataMonthWise getReportDataMonthWise++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 8);
}*/
//soumen func
public ArrayList<CommonBean> getReportDataMonthWise(String refclass,String condMARKTOFinal, String condYear,String criteria) {
	String condOfE ="";
	if(refclass.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	 strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy'), sum(subjectcount),sum(disposed), "
				+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
				+ " from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
				+ " where  "
				//"(a.refclass ='"+refclass+"'"+condOfE
				+ "(a.refclass =? "+condOfE
				+ ") " 
				+ " and "
				+ " a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ " and "
				+ "(a.incomingdate between to_date('01/01/2020','dd/mm/yyyy') and to_date('31/12/2020','dd/mm/yyyy')) "
				+ " and"
				+ " a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
				+ " group by markingto,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') "
				+ " order by rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') ";
	}
	else {
		strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy'), sum(subjectcount),sum(disposed), "
				+ " round(sum(disposed)/sum(subjectcount)*100,2), sum(subjectcount)-sum(disposed) pending "
				+ " from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
				+ " where  "
				//"(a.refclass ='"+refclass+"'"+condOfE
				+ "(a.refclass =? "+condOfE
				+ ") " 
				+ " and "
				+ " a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ " and "
				+ "(a.incomingdate between to_date('01/01/2020','dd/mm/yyyy') and to_date('31/12/2020','dd/mm/yyyy')) "
				+ " and"
				+ " a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
				+ " group by markingto,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') "
				+ " order by rolename,to_char(incomingdate,'yyyy-mm'),to_char(incomingdate,'MON-yyyy') ";
		
		
	}
	//System.out.println(" getReportDataMonthWise getReportDataMonthWise++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	//return (new CommonDAO()).getSQLResult(strSQL, 8);


	 DBConnection con = new DBConnection();
	 ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,refclass);
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 8,con);
					//System.out.println("getReportDataMonthWise executed");
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
}


/*private ArrayList<CommonBean> ConsolidatedReportCaseWiseData(String refclass,String condMARKTOFinal,String criteria) {
	// TODO Auto-generated method stub
	String condOfE="";
	if(refclass.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	 strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
			+ "sum(subjectcount14),sum(disposed14), "
			+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ "sum(subjectcount15),sum(disposed15), "
			+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ "sum(subjectcount16),sum(disposed16), "
			+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
			+ "sum(subjectcount17),sum(disposed17), "
			+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
			
			+ "sum(subjectcount18),sum(disposed18), "
			+ "round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), "
			
			+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) totalSub,sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) totaldis,round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) percent,1000 percentorder "
			+ " from "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, "
			
			+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18 "
			
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where  "
			+ "(a.refclass ='"+refclass+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  "
			+ "and  "
			+ "a.refid =b.refid "+condMARKTOFinal+"  order by b.markingto,a.incomingdate) z "
			+ "group by markingto "
			+ "union "
			+ "select 000,'  TOTAL' rolename,  "
			+ "sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ "sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ "sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
			+ "sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),  "
			
			+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),  "
			
			+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) totalSub,sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) totaldis,round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) percent, 0 percentorder "
			+ " from (select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate, "
			+ "'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
			+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17,"
			
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18"

			+ " from trnreference a,   "
			+ "trnmarking b  where  (a.refclass ='"+refclass+"'"+condOfE +") and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and  a.refid =b.refid  "+condMARKTOFinal+""
			+ "order by b.markingto,a.incomingdate) z "
			+ "order by percentorder,percent ";
	}
	else {
		 strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
				+ "sum(subjectcount14),sum(disposed14), "
				+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
				+ "sum(subjectcount15),sum(disposed15), "
				+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
				+ "sum(subjectcount16),sum(disposed16), "
				+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
				+ "sum(subjectcount17),sum(disposed17), "
				+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
				

			+ "sum(subjectcount18),sum(disposed18), "
			+ "round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), "
				
				+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) totalSub,sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) totaldis,round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) percent,1000 percentorder "
				+ " from "
				+ "(select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, "
				
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 "
				
				+ "from trnreference a,  "
				+ "trnmarking b  "
				+ "where  "
				+ "(a.refclass ='"+refclass+"'"+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  "
				+ "and  "
				+ "a.refid =b.refid "+condMARKTOFinal+"  order by b.markingto,a.incomingdate) z "
				+ "group by markingto "
				+ "union "
				+ "select 000,'  TOTAL' rolename,  "
				+ "sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
				+ "sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
				+ "sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
				+ "sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),  "
				
	+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),  "
				
				+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) totalSub,sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) totaldis,round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) percent, 0 percentorder "
				+ " from (select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,  "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate, "
				+ "'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
				+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, "
				
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 "

				
				+ "from trnreference a,   "
				+ "trnmarking b  where  (a.refclass ='"+refclass+"'"+condOfE +") and "
				+ " a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and  a.refid =b.refid  "+condMARKTOFinal+""
				+ "order by b.markingto,a.incomingdate) z "
				+ "order by percentorder,percent ";
		}
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 20);
}*/

//soumen code
private ArrayList<CommonBean> ConsolidatedReportCaseWiseData(String refclass,String condMARKTOFinal,String criteria) {
	// TODO Auto-generated method stub
	String condOfE="";
	if(refclass.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
		
		strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename, " + 
				"  sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), " + 
				"  sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), " + 
				"  sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), " + 
				"  sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), " + 
				"  sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), " + 
				"   sum(subjectcount19),sum(disposed19), round(sum(disposed19)/decode(sum(subjectcount19),0,1,sum(subjectcount19))*100,2), " + 
				"    sum(subjectcount20),sum(disposed20), round(sum(disposed20)/decode(sum(subjectcount20),0,1,sum(subjectcount20))*100,2), " + 
				"  sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20) totalSub, " + 
				"  sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20) totaldis, " + 
				"  round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)),0,1, " + 
				"  (sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)))*100,2) percent,1000 percentorder " + 
				"  from (select b.markingto,a.incomingdate,a.subject, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2019',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed19, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2020',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed20   " + 
				"  from trnreference a,  trnmarking b  where  (a.refclass =? ) and  a.tenureid>=12 and to_char(incomingdate,'yyyy') " + 
				"  in (2014,2015,2016,2017,2018,2019,2020)  and  a.refid =b.refid  and b.MARKINGTO in  ('691','436','208','1202','1244','52','255','860','315','617','639','386','387','8','324','60','262','326','61','325','215','6','7','504','5','1191','1169','695','328','1151','1252','922','1130','958','979','1079','389','698','700','9','59','593','297','1087','1102','39','974','1069','1254','299','1094','1210','384','437','605','975','969','1068','977','1089','1080','1088','1090','667','53','704','1059','983','103','502','907','266','216','10','330','74','55','870','592','591','1199','589','488','213','587','891','222','167','495','373','174','203','1160','322','382','586','383','634','585','666','1175','582','580','579','577','307','56','966','121','662','934','867','868','1170','494','1228','575','261','483','57','491','636','573','320','571','568','567','566','565','564','1205','1140','562','1070','1076','559','707','1103','665','558','1297','1154','331','169','265','678','1158','1157','63','391','67','1065','335','950','657','1197','1181','220','267','393','124','66','919','392','1212','1286','1259','552','170','13','933','1211','172','1200','11','865','1290','1209','205','219','399','217','21','395','336','396','1064','14','1128','69','551','70','1177','506','550','20','340','937','1051','1224','273','1201','159','129','376','130','400','73','547','689','1099','1002','1003','1027','1015','1038','980','1043','1028','946','76','993','1000','1006','1023','23','1007','1044','1016','1004','1030','1010','1045','1001','1011','984','994','991','987','1033','1025','962','988','1031','1041','1042','952','1097','274','943','989','967','1020','920','1039','999','546','1136','226','900','544','895','543','125','126','538','16','1172','709','175','252','713','714','716','397','718','720','339','176','398','357','479','19','619','615','630','520','517','223','75','625','25','344','1250','133','276','229','478','270','345','401','28','1178','651','529','284','518','953','652','352','80','650','185','233','260','285','526','140','481','1235','530','1085','1218','306','287','31','47','236','480','142','237','403','404','184','106','916','1236','1207','1171','145','235','351','186','147','238','355','965','86','356','232','277','182','1263','1117','290','183','81','509','135','281','440','508','441','620','136','230','26','308','180','926','231','516','1180','956','402','513','505','187','475','408','291','409','1046','346','279','347','30','348','687','519','349','282','234','82','138','1262','534','350','240','413','477','649','242','476','275','227','528','286','407','1113','732','903','887','1121','1091','879','736','627','741','292','745','1106','746','88','1101','358','89','34','414','191','359','192','1220','148','149','37','91','196','248','95','959','96','423','33','197','45','198','945','748','246','206','362','944','471','754','755','1268','674','193','461','194','417','92','310','459','90','1092','40','1139','1174','469','1074','420','309','48','1284','421','1161','416','604','463','42','195','1159','418','873','361','941','415','1179','43','419','36','756','363','880','311','955','760','458','761','762','764','524','1253','312','152','1086','1075','44','936','298','153','294','766','1271','767','1132','885','35','1270','960','918','424','768','1133','433','99','1168','1193','770','978','1107','100','303','948','304','456','771','774','981','1062','1055','1047','1281','379','380','1292','1100','209','314','313','375','105','1077','608','114','1223','1','256','599','117','118','1222','318','597','319','113','316','1291','924','162','4','163','317','2','115','1134','596','1225','112','778','369','370','46','1124','157','610','869','783','250','789','302','821','818','1082','976','829','1260','158','365','453','832','200','451','366','837','1208','847','1238','1111','849','816','834','449','448','855','1119','427','447','935','925','1063','104','446','444','445','202','644','429','1149','1104','826','1243','374','503','750','803','' )  order by b.markingto,a.incomingdate) z group by markingto union select 000,'  TOTAL' rolename, " + 
				"  sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), " + 
				"  sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2),  " + 
				"  sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), " + 
				"  sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), " + 
				"  sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), " + 
				"   sum(subjectcount19),sum(disposed19), round(sum(disposed19)/decode(sum(subjectcount19),0,1,sum(subjectcount19))*100,2), " + 
				"  sum(subjectcount20),sum(disposed20), round(sum(disposed20)/decode(sum(subjectcount20),0,1,sum(subjectcount20))*100,2), " + 
				"  sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20) totalSub, " + 
				"  sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20) totaldis, " + 
				"  round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)),0,1, " + 
				"  (sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)))*100,2) percent, 0 percentorder  from (select b.markingto,a.incomingdate,a.subject, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,  decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, " + 
				"  decode(to_char(a.incomingdate, 'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, " + 
				"decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18,  " + 
				"decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19, decode(to_char(a.incomingdate,'yyyy'),'2019',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed19, " + 
				"decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20, decode(to_char(a.incomingdate,'yyyy'),'2020',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed20 " + 
				"from " + 
				" trnreference a,   trnmarking b  where  (a.refclass =? ) and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)  and  a.refid =b.refid   and b.MARKINGTO in  ('691','436','208','1202','1244','52','255','860','315','617','639','386','387','8','324','60','262','326','61','325','215','6','7','504','5','1191','1169','695','328','1151','1252','922','1130','958','979','1079','389','698','700','9','59','593','297','1087','1102','39','974','1069','1254','299','1094','1210','384','437','605','975','969','1068','977','1089','1080','1088','1090','667','53','704','1059','983','103','502','907','266','216','10','330','74','55','870','592','591','1199','589','488','213','587','891','222','167','495','373','174','203','1160','322','382','586','383','634','585','666','1175','582','580','579','577','307','56','966','121','662','934','867','868','1170','494','1228','575','261','483','57','491','636','573','320','571','568','567','566','565','564','1205','1140','562','1070','1076','559','707','1103','665','558','1297','1154','331','169','265','678','1158','1157','63','391','67','1065','335','950','657','1197','1181','220','267','393','124','66','919','392','1212','1286','1259','552','170','13','933','1211','172','1200','11','865','1290','1209','205','219','399','217','21','395','336','396','1064','14','1128','69','551','70','1177','506','550','20','340','937','1051','1224','273','1201','159','129','376','130','400','73','547','689','1099','1002','1003','1027','1015','1038','980','1043','1028','946','76','993','1000','1006','1023','23','1007','1044','1016','1004','1030','1010','1045','1001','1011','984','994','991','987','1033','1025','962','988','1031','1041','1042','952','1097','274','943','989','967','1020','920','1039','999','546','1136','226','900','544','895','543','125','126','538','16','1172','709','175','252','713','714','716','397','718','720','339','176','398','357','479','19','619','615','630','520','517','223','75','625','25','344','1250','133','276','229','478','270','345','401','28','1178','651','529','284','518','953','652','352','80','650','185','233','260','285','526','140','481','1235','530','1085','1218','306','287','31','47','236','480','142','237','403','404','184','106','916','1236','1207','1171','145','235','351','186','147','238','355','965','86','356','232','277','182','1263','1117','290','183','81','509','135','281','440','508','441','620','136','230','26','308','180','926','231','516','1180','956','402','513','505','187','475','408','291','409','1046','346','279','347','30','348','687','519','349','282','234','82','138','1262','534','350','240','413','477','649','242','476','275','227','528','286','407','1113','732','903','887','1121','1091','879','736','627','741','292','745','1106','746','88','1101','358','89','34','414','191','359','192','1220','148','149','37','91','196','248','95','959','96','423','33','197','45','198','945','748','246','206','362','944','471','754','755','1268','674','193','461','194','417','92','310','459','90','1092','40','1139','1174','469','1074','420','309','48','1284','421','1161','416','604','463','42','195','1159','418','873','361','941','415','1179','43','419','36','756','363','880','311','955','760','458','761','762','764','524','1253','312','152','1086','1075','44','936','298','153','294','766','1271','767','1132','885','35','1270','960','918','424','768','1133','433','99','1168','1193','770','978','1107','100','303','948','304','456','771','774','981','1062','1055','1047','1281','379','380','1292','1100','209','314','313','375','105','1077','608','114','1223','1','256','599','117','118','1222','318','597','319','113','316','1291','924','162','4','163','317','2','115','1134','596','1225','112','778','369','370','46','1124','157','610','869','783','250','789','302','821','818','1082','976','829','1260','158','365','453','832','200','451','366','837','1208','847','1238','1111','849','816','834','449','448','855','1119','427','447','935','925','1063','104','446','444','445','202','644','429','1149','1104','826','1243','374','503','750','803','' )order by b.markingto,a.incomingdate) z order by percentorder,percent";
/*	 strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
			+ "sum(subjectcount14),sum(disposed14), "
			+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ "sum(subjectcount15),sum(disposed15), "
			+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ "sum(subjectcount16),sum(disposed16), "
			+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
			+ "sum(subjectcount17),sum(disposed17), "
			+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
			
			+ "sum(subjectcount18),sum(disposed18), "
			+ "round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), "
			
			+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) totalSub,sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) totaldis,round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) percent,1000 percentorder "
			+ " from "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, "
			
			+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18 "
			
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where  "
			//"(a.refclass ='"+refclass+"'"+condOfE
			+ "(a.refclass =? "+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  "
			+ "and  "
			+ "a.refid =b.refid "+condMARKTOFinal+"  order by b.markingto,a.incomingdate) z "
			+ "group by markingto "
			+ "union "
			+ "select 000,'  TOTAL' rolename,  "
			+ "sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ "sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ "sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
			+ "sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),  "
			
			+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),  "
			
			+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) totalSub,sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) totaldis,round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) percent, 0 percentorder "
			+ " from (select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate, "
			+ "'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
			+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17,"
			
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18"

			+ " from trnreference a,   "
			//"trnmarking b  where  (a.refclass ='"+refclass+"'"+condOfE +") and  "
			+ "trnmarking b  where  (a.refclass =? "+condOfE +") and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and  a.refid =b.refid  "+condMARKTOFinal+""
			+ "order by b.markingto,a.incomingdate) z "
			+ "order by percentorder,percent ";*/
	
	}
	else {
		strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,   " + 
				"sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), " + 
				" sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), " + 
				" sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), " + 
				" sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), " + 
				" sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), " + 
				"  sum(subjectcount19),sum(disposed19), round(sum(disposed19)/decode(sum(subjectcount19),0,1,sum(subjectcount19))*100,2), " + 
				"   sum(subjectcount20),sum(disposed20), round(sum(disposed20)/decode(sum(subjectcount20),0,1,sum(subjectcount20))*100,2), " + 
				" sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20) totalSub, " + 
				" sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20) totaldis, " + 
				" round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20))/ " + 
				" decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)),0,1, " + 
				" (sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)))*100,2) percent,1000 percentorder   " + 
				" from (select b.markingto,a.incomingdate,a.subject, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014', " + 
				" DECODE(NVL(a.fmid,0),0,0,1),0) disposed14,  " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', " + 
				" DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', " + 
				" DECODE(NVL(a.fmid,0),0,0,1),0) disposed16,  " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', " + 
				" DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', " + 
				" DECODE(NVL(a.fmid,0),0,0,1),0) disposed18, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19, decode(to_char(a.incomingdate,'yyyy'),'2019', " + 
				" DECODE(NVL(a.fmid,0),0,0,1),0) disposed19, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20, decode(to_char(a.incomingdate,'yyyy'),'2020', " + 
				" DECODE(NVL(a.fmid,0),0,0,1),0) disposed20  " + 
				" from trnreference a,  trnmarking b  where  (a.refclass =? ) and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)  and  a.refid =b.refid  and b.MARKINGTO in  ('691','436','208','1202','1244','52','255','860','315','617','639','386','387','8','324','60','262','326','61','325','215','6','7','504','5','1191','1169','695','328','1151','1252','922','1130','958','979','1079','389','698','700','9','59','593','297','1087','1102','39','974','1069','1254','299','1094','1210','384','437','605','975','969','1068','977','1089','1080','1088','1090','667','53','704','1059','983','103','502','907','266','216','10','330','74','55','870','592','591','1199','589','488','213','587','891','222','167','495','373','174','203','1160','322','382','586','383','634','585','666','1175','582','580','579','577','307','56','966','121','662','934','867','868','1170','494','1228','575','261','483','57','491','636','573','320','571','568','567','566','565','564','1205','1140','562','1070','1076','559','707','1103','665','558','1297','1154','331','169','265','678','1158','1157','63','391','67','1065','335','950','657','1197','1181','220','267','393','124','66','919','392','1212','1286','1259','552','170','13','933','1211','172','1200','11','865','1290','1209','205','219','399','217','21','395','336','396','1064','14','1128','69','551','70','1177','506','550','20','340','937','1051','1224','273','1201','159','129','376','130','400','73','547','689','1099','1002','1003','1027','1015','1038','980','1043','1028','946','76','993','1000','1006','1023','23','1007','1044','1016','1004','1030','1010','1045','1001','1011','984','994','991','987','1033','1025','962','988','1031','1041','1042','952','1097','274','943','989','967','1020','920','1039','999','546','1136','226','900','544','895','543','125','126','538','16','1172','709','175','252','713','714','716','397','718','720','339','176','398','357','479','19','619','615','630','520','517','223','75','625','25','344','1250','133','276','229','478','270','345','401','28','1178','651','529','284','518','953','652','352','80','650','185','233','260','285','526','140','481','1235','530','1085','1218','306','287','31','47','236','480','142','237','403','404','184','106','916','1236','1207','1171','145','235','351','186','147','238','355','965','86','356','232','277','182','1263','1117','290','183','81','509','135','281','440','508','441','620','136','230','26','308','180','926','231','516','1180','956','402','513','505','187','475','408','291','409','1046','346','279','347','30','348','687','519','349','282','234','82','138','1262','534','350','240','413','477','649','242','476','275','227','528','286','407','1113','732','903','887','1121','1091','879','736','627','741','292','745','1106','746','88','1101','358','89','34','414','191','359','192','1220','148','149','37','91','196','248','95','959','96','423','33','197','45','198','945','748','246','206','362','944','471','754','755','1268','674','193','461','194','417','92','310','459','90','1092','40','1139','1174','469','1074','420','309','48','1284','421','1161','416','604','463','42','195','1159','418','873','361','941','415','1179','43','419','36','756','363','880','311','955','760','458','761','762','764','524','1253','312','152','1086','1075','44','936','298','153','294','766','1271','767','1132','885','35','1270','960','918','424','768','1133','433','99','1168','1193','770','978','1107','100','303','948','304','456','771','774','981','1062','1055','1047','1281','379','380','1292','1100','209','314','313','375','105','1077','608','114','1223','1','256','599','117','118','1222','318','597','319','113','316','1291','924','162','4','163','317','2','115','1134','596','1225','112','778','369','370','46','1124','157','610','869','783','250','789','302','821','818','1082','976','829','1260','158','365','453','832','200','451','366','837','1208','847','1238','1111','849','816','834','449','448','855','1119','427','447','935','925','1063','104','446','444','445','202','644','429','1149','1104','826','1243','374','503','750','803','' )  order by b.markingto,a.incomingdate) z group by markingto union select 000,'  TOTAL' rolename, " + 
				" sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), " + 
				" sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), " + 
				" sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), " + 
				" sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), " + 
				" sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), " + 
				"  sum(subjectcount19),sum(disposed19), round(sum(disposed19)/decode(sum(subjectcount19),0,1,sum(subjectcount19))*100,2), " + 
				"   sum(subjectcount20),sum(disposed20), round(sum(disposed20)/decode(sum(subjectcount20),0,1,sum(subjectcount20))*100,2), " + 
				" sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20) totalSub, " + 
				" sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20) totaldis, " + 
				" round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20))/ " + 
				" decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)),0,1, " + 
				" (sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)))*100,2) percent, 0 percentorder " + 
				" from (select b.markingto,a.incomingdate,a.subject, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,   " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14,  " + 
				" decode(to_char(a.incomingdate, 'yyyy'),'2015',1,0) subjectcount15, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2016', DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18,  " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2019',DECODE(NVL(a.fmid,0),0,0,1),0) disposed19,  " + 
				"  decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20, " + 
				" decode(to_char(a.incomingdate,'yyyy'),'2020',DECODE(NVL(a.fmid,0),0,0,1),0) disposed20 " + 
				"from trnreference a,   trnmarking b  where  (a.refclass =? ) and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)  and  a.refid =b.refid   and b.MARKINGTO in  ('691','436','208','1202','1244','52','255','860','315','617','639','386','387','8','324','60','262','326','61','325','215','6','7','504','5','1191','1169','695','328','1151','1252','922','1130','958','979','1079','389','698','700','9','59','593','297','1087','1102','39','974','1069','1254','299','1094','1210','384','437','605','975','969','1068','977','1089','1080','1088','1090','667','53','704','1059','983','103','502','907','266','216','10','330','74','55','870','592','591','1199','589','488','213','587','891','222','167','495','373','174','203','1160','322','382','586','383','634','585','666','1175','582','580','579','577','307','56','966','121','662','934','867','868','1170','494','1228','575','261','483','57','491','636','573','320','571','568','567','566','565','564','1205','1140','562','1070','1076','559','707','1103','665','558','1297','1154','331','169','265','678','1158','1157','63','391','67','1065','335','950','657','1197','1181','220','267','393','124','66','919','392','1212','1286','1259','552','170','13','933','1211','172','1200','11','865','1290','1209','205','219','399','217','21','395','336','396','1064','14','1128','69','551','70','1177','506','550','20','340','937','1051','1224','273','1201','159','129','376','130','400','73','547','689','1099','1002','1003','1027','1015','1038','980','1043','1028','946','76','993','1000','1006','1023','23','1007','1044','1016','1004','1030','1010','1045','1001','1011','984','994','991','987','1033','1025','962','988','1031','1041','1042','952','1097','274','943','989','967','1020','920','1039','999','546','1136','226','900','544','895','543','125','126','538','16','1172','709','175','252','713','714','716','397','718','720','339','176','398','357','479','19','619','615','630','520','517','223','75','625','25','344','1250','133','276','229','478','270','345','401','28','1178','651','529','284','518','953','652','352','80','650','185','233','260','285','526','140','481','1235','530','1085','1218','306','287','31','47','236','480','142','237','403','404','184','106','916','1236','1207','1171','145','235','351','186','147','238','355','965','86','356','232','277','182','1263','1117','290','183','81','509','135','281','440','508','441','620','136','230','26','308','180','926','231','516','1180','956','402','513','505','187','475','408','291','409','1046','346','279','347','30','348','687','519','349','282','234','82','138','1262','534','350','240','413','477','649','242','476','275','227','528','286','407','1113','732','903','887','1121','1091','879','736','627','741','292','745','1106','746','88','1101','358','89','34','414','191','359','192','1220','148','149','37','91','196','248','95','959','96','423','33','197','45','198','945','748','246','206','362','944','471','754','755','1268','674','193','461','194','417','92','310','459','90','1092','40','1139','1174','469','1074','420','309','48','1284','421','1161','416','604','463','42','195','1159','418','873','361','941','415','1179','43','419','36','756','363','880','311','955','760','458','761','762','764','524','1253','312','152','1086','1075','44','936','298','153','294','766','1271','767','1132','885','35','1270','960','918','424','768','1133','433','99','1168','1193','770','978','1107','100','303','948','304','456','771','774','981','1062','1055','1047','1281','379','380','1292','1100','209','314','313','375','105','1077','608','114','1223','1','256','599','117','118','1222','318','597','319','113','316','1291','924','162','4','163','317','2','115','1134','596','1225','112','778','369','370','46','1124','157','610','869','783','250','789','302','821','818','1082','976','829','1260','158','365','453','832','200','451','366','837','1208','847','1238','1111','849','816','834','449','448','855','1119','427','447','935','925','1063','104','446','444','445','202','644','429','1149','1104','826','1243','374','503','750','803','' )order by b.markingto,a.incomingdate) z order by percentorder,percent  " + 
				"";
		 /*strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
				+ "sum(subjectcount14),sum(disposed14), "
				+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
				+ "sum(subjectcount15),sum(disposed15), "
				+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
				+ "sum(subjectcount16),sum(disposed16), "
				+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
				+ "sum(subjectcount17),sum(disposed17), "
				+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
				

			+ "sum(subjectcount18),sum(disposed18), "
			+ "round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), "
				
				+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) totalSub,sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) totaldis,round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) percent,1000 percentorder "
				+ " from "
				+ "(select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, "
				
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 "
				
				+ "from trnreference a,  "
				+ "trnmarking b  "
				+ "where  "
				//"(a.refclass ='"+refclass+"'"+condOfE
				+ "(a.refclass =? "+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  "
				+ "and  "
				+ "a.refid =b.refid "+condMARKTOFinal+"  order by b.markingto,a.incomingdate) z "
				+ "group by markingto "
				+ "union "
				+ "select 000,'  TOTAL' rolename,  "
				+ "sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
				+ "sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
				+ "sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
				+ "sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),  "
				
	+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),  "
				
				+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) totalSub,sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) totaldis,round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) percent, 0 percentorder "
				+ " from (select b.markingto,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,  "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate, "
				+ "'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
				+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
				+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, "
				
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 "

				
				+ "from trnreference a,   "
				//"trnmarking b  where  (a.refclass ='"+refclass+"'"+condOfE +") and "
				+ "trnmarking b  where  (a.refclass =? "+condOfE +") and "
				+ " a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and  a.refid =b.refid  "+condMARKTOFinal+""
				+ "order by b.markingto,a.incomingdate) z "
				+ "order by percentorder,percent ";*/
		}
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//System.out.println("refclass :"+refclass);
	//log.debug("Remider ** :-- " + strSQL);
   // return (new CommonDAO()).getSQLResult(strSQL, 20);
	
	
	DBConnection con = new DBConnection();
	  ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,refclass);
					ps.setString(2,refclass);
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 26,con);
					//System.out.println("ConsolidatedReportCaseWiseData executed");
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
}

/*private ArrayList<CommonBean> ConsolidatedReportYearWiseData(String year,String condMARKTOFinal,String criteria) {
	// TODO Auto-generated method stub
	String condYear="";
	
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	 strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
			+ "sum(subjectcountA),sum(disposedA), "
			+ "round(sum(disposedA)/decode(sum(subjectcountA),0,1,sum(subjectcountA))*100,2), "
			+ "sum(subjectcountB),sum(disposedB), "
			+ "round(sum(disposedB)/decode(sum(subjectcountB),0,1,sum(subjectcountB))*100,2), "
			+ "sum(subjectcountC),sum(disposedC), "
			+ "round(sum(disposedC)/decode(sum(subjectcountC),0,1,sum(subjectcountC))*100,2), "
			+ "sum(subjectcountE),sum(disposedE), "
			+ "round(sum(disposedE)/decode(sum(subjectcountE),0,1,sum(subjectcountE))*100,2), "
			+ " sum(subjectcountM),sum(disposedM), "
			+ "round(sum(disposedM)/decode(sum(subjectcountM),0,1,sum(subjectcountM))*100,2), "
			+ "sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM) totalSub,sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM) totaldis,round((sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM))/decode((sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)),0,1,(sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)))*100,2) percent,1000 percentorder "
			+ " from  "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(a.refclass,'A',1,0) subjectcountA, "
			+ "decode(a.refclass,'A',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedA, "
			+ "decode(a.refclass,'B',1,0) subjectcountB, "
			+ "decode(a.refclass,'B',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedB, "
			+ "decode(a.refclass,'C',1,0) subjectcountC, "
			+ "decode(a.refclass,'C',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedC, "
			+ "decode(a.refcategory,'E',1,0) subjectcountE, "
			+ "decode(a.refcategory,'E',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedE,"
			+ "decode(a.refclass,'M',1,0) subjectcountM, "
			+ "decode(a.refclass,'M',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedM "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where   "
			+ "(a.refclass  in ('A','B','C','E','M') "
			+ ")  "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy')='"+year+"' "
			+ "and  "
			+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z  "
			+ "group by markingto "
			+ "union "
			+ "select 000,'  TOTAL' rolename,sum(subjectcountA),sum(disposedA), round(sum(disposedA)/decode(sum(subjectcountA),0,1,sum(subjectcountA))*100,2), "
			+ "sum(subjectcountB),sum(disposedB), round(sum(disposedB)/decode(sum(subjectcountB),0,1,sum(subjectcountB))*100,2),  "
			+ "sum(subjectcountC),sum(disposedC), round(sum(disposedC)/decode(sum(subjectcountC),0,1,sum(subjectcountC))*100,2), "
			+ "sum(subjectcountE),sum(disposedE), round(sum(disposedE)/decode(sum(subjectcountE),0,1,sum(subjectcountE))*100,2), "
			+ "sum(subjectcountM),sum(disposedM), round(sum(disposedM)/decode(sum(subjectcountM),0,1,sum(subjectcountM))*100,2), "
			+ "sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM) totalSub,sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM) totaldis,round((sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM))/decode((sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)),0,1,(sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)))*100,2) percent,0 percentorder "
			+ " from   "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(a.refclass,'A',1,0) subjectcountA, "
			+ "decode(a.refclass,'A',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedA, "
			+ "decode(a.refclass,'B',1,0) subjectcountB, "
			+ "decode(a.refclass,'B',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedB, "
			+ "decode(a.refclass,'C',1,0) subjectcountC, "
			+ "decode(a.refclass,'C',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedC, "
			+ "decode(a.refcategory,'E',1,0) subjectcountE, "
			+ "decode(a.refcategory,'E',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedE,"
			+ "decode(a.refclass,'M',1,0) subjectcountM, "
			+ "decode(a.refclass,'M',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedM "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where   "
			+ "(a.refclass  in ('A','B','C','E','M') "
			+ ")  "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy')='"+year+"' "
			+ "and  "
			+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
			+ "order by percentorder,percent ";
	}
	else {
	 strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
			+ "sum(subjectcountA),sum(disposedA), "
			+ "round(sum(disposedA)/decode(sum(subjectcountA),0,1,sum(subjectcountA))*100,2), "
			+ "sum(subjectcountB),sum(disposedB), "
			+ "round(sum(disposedB)/decode(sum(subjectcountB),0,1,sum(subjectcountB))*100,2), "
			+ "sum(subjectcountC),sum(disposedC), "
			+ "round(sum(disposedC)/decode(sum(subjectcountC),0,1,sum(subjectcountC))*100,2), "
			+ "sum(subjectcountE),sum(disposedE), "
			+ "round(sum(disposedE)/decode(sum(subjectcountE),0,1,sum(subjectcountE))*100,2), "
			+ "sum(subjectcountM),sum(disposedM), "
			+ "round(sum(disposedM)/decode(sum(subjectcountM),0,1,sum(subjectcountM))*100,2), "
			+ "sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM) totalSub,sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM) totaldis,round((sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM))/decode((sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)),0,1,(sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)))*100,2) percent,1000 percentorder "
			+ " from  "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(a.refclass,'A',1,0) subjectcountA, "
			+ "decode(a.refclass,'A',DECODE(NVL(a.fmid,0),0,0,1),0) disposedA, "
			+ "decode(a.refclass,'B',1,0) subjectcountB, "
			+ "decode(a.refclass,'B',DECODE(NVL(a.fmid,0),0,0,1),0) disposedB, "
			+ "decode(a.refclass,'C',1,0) subjectcountC, "
			+ "decode(a.refclass,'C',DECODE(NVL(a.fmid,0),0,0,1),0) disposedC, "
			+ "decode(a.refcategory,'E',1,0) subjectcountE, "
			+ "decode(a.refcategory,'E',DECODE(NVL(a.fmid,0),0,0,1),0) disposedE, "
			+ "decode(a.refclass,'M',1,0) subjectcountM, "
			+ "decode(a.refclass,'M',DECODE(NVL(a.fmid,0),0,0,1),0) disposedM "
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
			+ "union "
			+ "select 000,'  TOTAL' rolename,sum(subjectcountA),sum(disposedA), round(sum(disposedA)/decode(sum(subjectcountA),0,1,sum(subjectcountA))*100,2), "
			+ "sum(subjectcountB),sum(disposedB), round(sum(disposedB)/decode(sum(subjectcountB),0,1,sum(subjectcountB))*100,2),  "
			+ "sum(subjectcountC),sum(disposedC), round(sum(disposedC)/decode(sum(subjectcountC),0,1,sum(subjectcountC))*100,2), "
			+ "sum(subjectcountE),sum(disposedE), round(sum(disposedE)/decode(sum(subjectcountE),0,1,sum(subjectcountE))*100,2), "
			+ "sum(subjectcountM),sum(disposedM), round(sum(disposedM)/decode(sum(subjectcountM),0,1,sum(subjectcountM))*100,2), "
			+ "sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM) totalSub,sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM) totaldis,round((sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM))/decode((sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)),0,1,(sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)))*100,2) percent,0 percentorder "
			+ " from   "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(a.refclass,'A',1,0) subjectcountA, decode(a.refclass,'A', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedA, decode(a.refclass,'B',1,0) subjectcountB, decode(a.refclass,'B', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedB,"
			+ " decode(a.refclass,'C',1,0) subjectcountC, decode(a.refclass,'C', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedC, "
			+ "decode(a.refcategory,'E',1,0) subjectcountE, decode(a.refcategory,'E', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedE,"
			+ " decode(a.refclass,'M',1,0) subjectcountM, decode(a.refclass,'M', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedM "
			+ " from trnreference a,  trnmarking b  where   (a.refclass  in ('A','B','C','M','E') )  "
			+ "and  a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy')='"+year+"'  "
			+ "and  a.refid =b.refid  "+condMARKTOFinal+""
			+ "order by b.markingto,a.incomingdate) z "
			+ "order by percentorder,percent ";
	}
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 21);
}*/
//soumen code
private ArrayList<CommonBean> ConsolidatedReportYearWiseData(String year,String condMARKTOFinal,String criteria) {
	// TODO Auto-generated method stub
	String condYear="";
	
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	 strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
			+ "sum(subjectcountA),sum(disposedA), "
			+ "round(sum(disposedA)/decode(sum(subjectcountA),0,1,sum(subjectcountA))*100,2), "
			+ "sum(subjectcountB),sum(disposedB), "
			+ "round(sum(disposedB)/decode(sum(subjectcountB),0,1,sum(subjectcountB))*100,2), "
			+ "sum(subjectcountC),sum(disposedC), "
			+ "round(sum(disposedC)/decode(sum(subjectcountC),0,1,sum(subjectcountC))*100,2), "
			+ "sum(subjectcountE),sum(disposedE), "
			+ "round(sum(disposedE)/decode(sum(subjectcountE),0,1,sum(subjectcountE))*100,2), "
			+ " sum(subjectcountM),sum(disposedM), "
			+ "round(sum(disposedM)/decode(sum(subjectcountM),0,1,sum(subjectcountM))*100,2), "
			+ "sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM) totalSub,sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM) totaldis,round((sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM))/decode((sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)),0,1,(sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)))*100,2) percent,1000 percentorder "
			+ " from  "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(a.refclass,'A',1,0) subjectcountA, "
			+ "decode(a.refclass,'A',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedA, "
			+ "decode(a.refclass,'B',1,0) subjectcountB, "
			+ "decode(a.refclass,'B',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedB, "
			+ "decode(a.refclass,'C',1,0) subjectcountC, "
			+ "decode(a.refclass,'C',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedC, "
			+ "decode(a.refcategory,'E',1,0) subjectcountE, "
			+ "decode(a.refcategory,'E',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedE,"
			+ "decode(a.refclass,'M',1,0) subjectcountM, "
			+ "decode(a.refclass,'M',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedM "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where   "
			+ "(a.refclass  in ('A','B','C','E','M') "
			+ ")  "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			//"and to_char(incomingdate,'yyyy')='"+year+"' "
			+ "and to_char(incomingdate,'yyyy')=? "
			+ "and  "
			+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z  "
			+ "group by markingto "
			+ "union "
			+ "select 000,'  TOTAL' rolename,sum(subjectcountA),sum(disposedA), round(sum(disposedA)/decode(sum(subjectcountA),0,1,sum(subjectcountA))*100,2), "
			+ "sum(subjectcountB),sum(disposedB), round(sum(disposedB)/decode(sum(subjectcountB),0,1,sum(subjectcountB))*100,2),  "
			+ "sum(subjectcountC),sum(disposedC), round(sum(disposedC)/decode(sum(subjectcountC),0,1,sum(subjectcountC))*100,2), "
			+ "sum(subjectcountE),sum(disposedE), round(sum(disposedE)/decode(sum(subjectcountE),0,1,sum(subjectcountE))*100,2), "
			+ "sum(subjectcountM),sum(disposedM), round(sum(disposedM)/decode(sum(subjectcountM),0,1,sum(subjectcountM))*100,2), "
			+ "sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM) totalSub,sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM) totaldis,round((sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM))/decode((sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)),0,1,(sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)))*100,2) percent,0 percentorder "
			+ " from   "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(a.refclass,'A',1,0) subjectcountA, "
			+ "decode(a.refclass,'A',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedA, "
			+ "decode(a.refclass,'B',1,0) subjectcountB, "
			+ "decode(a.refclass,'B',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedB, "
			+ "decode(a.refclass,'C',1,0) subjectcountC, "
			+ "decode(a.refclass,'C',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedC, "
			+ "decode(a.refcategory,'E',1,0) subjectcountE, "
			+ "decode(a.refcategory,'E',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedE,"
			+ "decode(a.refclass,'M',1,0) subjectcountM, "
			+ "decode(a.refclass,'M',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposedM "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where   "
			+ "(a.refclass  in ('A','B','C','E','M') "
			+ ")  "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			//"and to_char(incomingdate,'yyyy')='"+year+"' "
			+ "and to_char(incomingdate,'yyyy')=? "
			+ "and  "
			+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z "
			+ "order by percentorder,percent ";
	}
	else {
	 strSQL = "select markingto,(select rolename from mstrole x where x.roleid=z.markingto) rolename,  "
			+ "sum(subjectcountA),sum(disposedA), "
			+ "round(sum(disposedA)/decode(sum(subjectcountA),0,1,sum(subjectcountA))*100,2), "
			+ "sum(subjectcountB),sum(disposedB), "
			+ "round(sum(disposedB)/decode(sum(subjectcountB),0,1,sum(subjectcountB))*100,2), "
			+ "sum(subjectcountC),sum(disposedC), "
			+ "round(sum(disposedC)/decode(sum(subjectcountC),0,1,sum(subjectcountC))*100,2), "
			+ "sum(subjectcountE),sum(disposedE), "
			+ "round(sum(disposedE)/decode(sum(subjectcountE),0,1,sum(subjectcountE))*100,2), "
			+ "sum(subjectcountM),sum(disposedM), "
			+ "round(sum(disposedM)/decode(sum(subjectcountM),0,1,sum(subjectcountM))*100,2), "
			+ "sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM) totalSub,sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM) totaldis,round((sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM))/decode((sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)),0,1,(sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)))*100,2) percent,1000 percentorder "
			+ " from  "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(a.refclass,'A',1,0) subjectcountA, "
			+ "decode(a.refclass,'A',DECODE(NVL(a.fmid,0),0,0,1),0) disposedA, "
			+ "decode(a.refclass,'B',1,0) subjectcountB, "
			+ "decode(a.refclass,'B',DECODE(NVL(a.fmid,0),0,0,1),0) disposedB, "
			+ "decode(a.refclass,'C',1,0) subjectcountC, "
			+ "decode(a.refclass,'C',DECODE(NVL(a.fmid,0),0,0,1),0) disposedC, "
			+ "decode(a.refcategory,'E',1,0) subjectcountE, "
			+ "decode(a.refcategory,'E',DECODE(NVL(a.fmid,0),0,0,1),0) disposedE, "
			+ "decode(a.refclass,'M',1,0) subjectcountM, "
			+ "decode(a.refclass,'M',DECODE(NVL(a.fmid,0),0,0,1),0) disposedM "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where   "
			+ "(a.refclass  in ('A','B','C','M','E') "
			+ ")  "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			// "and to_char(incomingdate,'yyyy')='"+year+"' "
			+ "and to_char(incomingdate,'yyyy')=? "
			+ "and  "
			+ "a.refid =b.refid "+condMARKTOFinal+" order by b.markingto,a.incomingdate) z  "
			+ "group by markingto "
			+ "union "
			+ "select 000,'  TOTAL' rolename,sum(subjectcountA),sum(disposedA), round(sum(disposedA)/decode(sum(subjectcountA),0,1,sum(subjectcountA))*100,2), "
			+ "sum(subjectcountB),sum(disposedB), round(sum(disposedB)/decode(sum(subjectcountB),0,1,sum(subjectcountB))*100,2),  "
			+ "sum(subjectcountC),sum(disposedC), round(sum(disposedC)/decode(sum(subjectcountC),0,1,sum(subjectcountC))*100,2), "
			+ "sum(subjectcountE),sum(disposedE), round(sum(disposedE)/decode(sum(subjectcountE),0,1,sum(subjectcountE))*100,2), "
			+ "sum(subjectcountM),sum(disposedM), round(sum(disposedM)/decode(sum(subjectcountM),0,1,sum(subjectcountM))*100,2), "
			+ "sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM) totalSub,sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM) totaldis,round((sum(disposedA)+sum(disposedB)+sum(disposedC)+sum(disposedE)+sum(disposedM))/decode((sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)),0,1,(sum(subjectcountA)+sum(subjectcountB)+sum(subjectcountC)+sum(subjectcountE)+sum(subjectcountM)))*100,2) percent,0 percentorder "
			+ " from   "
			+ "(select b.markingto,a.incomingdate,a.subject,decode(a.refclass,'A',1,0) subjectcountA, decode(a.refclass,'A', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedA, decode(a.refclass,'B',1,0) subjectcountB, decode(a.refclass,'B', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedB,"
			+ " decode(a.refclass,'C',1,0) subjectcountC, decode(a.refclass,'C', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedC, "
			+ "decode(a.refcategory,'E',1,0) subjectcountE, decode(a.refcategory,'E', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedE,"
			+ " decode(a.refclass,'M',1,0) subjectcountM, decode(a.refclass,'M', "
			+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposedM "
			+ " from trnreference a,  trnmarking b  where   (a.refclass  in ('A','B','C','M','E') )  "
			+ "and  a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			//"and to_char(incomingdate,'yyyy')='"+year+"'  "
			+ "and to_char(incomingdate,'yyyy')=? "
			+ "and  a.refid =b.refid  "+condMARKTOFinal+""
			+ "order by b.markingto,a.incomingdate) z "
			+ "order by percentorder,percent ";
	}
	//System.out.println(" getReportData getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//System.out.println("year :"+year);
	//log.debug("Remider ** :-- " + strSQL);
	//return (new CommonDAO()).getSQLResult(strSQL, 21);
	
	DBConnection con = new DBConnection();
	  ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,year);
					ps.setString(2,year);
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 21,con);
					//System.out.println("ConsolidatedReportYearWiseData executed");
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
	
	
}

public String generateStatisticalSummary(String serverpath,String criteria) {
	// TODO Auto-generated method stub

	

	
	int ran = (int) (1000000 * Math.random());
	String Directory = serverpath;
	//String Directory = "D://";

	String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
	try {
		
		String[] Refclass ={"A","B","E","C","M"};
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
		
		
			ArrayList<CommonBean> cm =new ArrayList();	
			
			Date date = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    String strDate = sdf.format(date);
		    
			
			headername = "Statistical Summary Report as on " +strDate;
			String[] year = {"2014","2015","2016","2017","2018","2019","2020"};
			String[] arrh = {"Category","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases","Disposed Cases","% of Disposal","Total No of Cases"," Total Disposed Cases","% of Disposal","Total No of Cases"," Total Disposed Cases","% of Disposal","Total No of Cases"," Total Disposed Cases","% of Disposal","Total No of Cases"," Total Disposed Cases","% of Disposal"};
			
			
			HSSFSheet sheet = wb.createSheet("Statistical Report");
			sheet.setColumnWidth(0, 3500);
	        sheet.setColumnWidth(1, 3500);
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
	        sheet.setColumnWidth(14, 3500);
	        sheet.setColumnWidth(15, 3500);
	        sheet.setColumnWidth(16, 3500);
	        sheet.setColumnWidth(17, 3500);
	        sheet.setColumnWidth(18, 3500);
	        sheet.setColumnWidth(19, 3500);
	        sheet.setColumnWidth(20, 3500);
	        sheet.setColumnWidth(21, 3500);
	        sheet.setColumnWidth(22, 3500);
	        sheet.setColumnWidth(23, 3500);
	        sheet.setColumnWidth(24, 3500);
	       
			HSSFPrintSetup ps = sheet.getPrintSetup();
			sheet.setAutobreaks(true);
			sheet.getPrintSetup().setLandscape(true);
			ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			ps.setFitWidth((short)1);
			ps.setFitHeight((short)0);
			sheet.setHorizontallyCenter(true);
			
			cm = generateStatisticalSummaryData(criteria);
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
			
			
			//row = sheet.createRow(rnum++);
			string = new HSSFRichTextString("Statistical data of all Cases");
			createCell(wb, row, (short) (0), style_header, string);
			sheet.addMergedRegion(new CellRangeAddress(
			        0, //first row (0-based)
			        0, //last row  (0-based)
			        0, //first column (0-based)
			       21  //last column  (0-based)
			));
			
			
			row = sheet.createRow(rnum++);
			string = new HSSFRichTextString("Year");
			createCell(wb, row, (short) (0), style_header, string);
			
			int inc =1;
			int inc1 =3;
			for (int i = 0; i <= year.length; i++) {
				if(i==year.length)
				{
					string = new HSSFRichTextString("Total");
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
				}else{
				string = new HSSFRichTextString(year[i].toString()
						.replaceAll("&deg;", "°"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (inc), style_header, string);
				
				sheet.addMergedRegion(new CellRangeAddress(
				        1, //first row (0-based)
				        1, //last row  (0-based)
				        inc, //first column (0-based)
				       inc1  //last column  (0-based)
				));
			inc=inc+3;
			inc1=inc1+3;
				}
			}
			row = sheet.createRow(rnum++);
			for (int i = 0; i < arrh.length; i++) {
				string = new HSSFRichTextString(arrh[i].toString()
						.replaceAll("&deg;", "°"));
				string.applyFont(font_Header);
				createCell(wb, row, (short) (i), style_header, string);
			

			}
			
			//int sno = 1;
			//System.out.println("size :"+cm.size());
			for (int i = 0; i < cm.size(); i++) {
				if(i==0){
					//System.out.println("in one");
					row = sheet.createRow(rnum++);
					string = new HSSFRichTextString(cm.get(i).getField1().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (0),
							style_header, string);
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
					string = new HSSFRichTextString(cm.get(i).getField14().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (13),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField15().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (14),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField16().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (15),
							style_cell, string);
					
					
					
					string = new HSSFRichTextString(cm.get(i).getField17().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (16),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField18().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (17),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField19().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (18),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField20().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (19),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField21().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (20),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField22().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (21),
							style_cell, string);
					
					string = new HSSFRichTextString(cm.get(i).getField23().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (22),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField24().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (23),
							style_cell, string);
					string = new HSSFRichTextString(cm.get(i).getField25().toString());
					string.applyFont(font_cell);
					createCell(wb, row, (short) (24),
							style_cell, string);
					
				}
				else{
					//System.out.println("in one");
				row = sheet.createRow(rnum++);
				string = new HSSFRichTextString(cm.get(i).getField1().toString());
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
				string = new HSSFRichTextString(cm.get(i).getField14().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (13),
						style_cell, string);
				//System.out.println(string);
				string = new HSSFRichTextString(cm.get(i).getField15().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (14),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField16().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (15),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField17().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (16),
						style_cell, string);
				//System.out.println(string);
				string = new HSSFRichTextString(cm.get(i).getField18().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (17),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField19().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (18),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField20().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (19),
						style_cell, string);
				//System.out.println(string);
				string = new HSSFRichTextString(cm.get(i).getField21().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (20),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField22().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (21),
						style_cell, string);
				
				string = new HSSFRichTextString(cm.get(i).getField23().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (22),
						style_cell, string);
				//System.out.println(string);
				string = new HSSFRichTextString(cm.get(i).getField24().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (23),
						style_cell, string);
				string = new HSSFRichTextString(cm.get(i).getField25().toString());
				string.applyFont(font_cell);
				createCell(wb, row, (short) (24),
						style_cell, string);
			
				}
			}

			
			
			
			
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

/*private ArrayList<CommonBean> generateStatisticalSummaryData(String criteria) {
	// TODO Auto-generated method stub
String condYear="";
String strSQL="";
//System.out.println(criteria);
if(criteria.equalsIgnoreCase("1")){
	
	 strSQL = "select refclass, sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), "
	 		+ " round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
	 		+ "sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
	 		+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),"
	 		+ "sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) +sum(subjectcount18) sumtotal,"
	 		+ "sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) sumdisp, round((sum(disposed14)+ "
	 		+ "sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+ "
	 		+ "sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17  "
	 		+ ", decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18 "
	 		+ "from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  "
	 		+ "a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  union  "
	 		+ "select refcategory, sum(subjectcount14), "
	 		+ "sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
	 		+ "sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
	 		+ "sum(subjectcount17),sum(disposed17),round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),  sum(subjectcount18),sum(disposed18),round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),   "
	 		+ "sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) sumtotal,  "
	 		+ "sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) sumdisp,  round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/ "
	 		+ "decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) "
	 		+ "from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 , decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18 "
	 		+ " from trnreference a,  trnmarking b  where   (a.refcategory='E') and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory  "
	 		+ "union "
	 		+ "select ' TOTAL', sum(P),sum(Q),ROund(SUM(Q)/(decode(SUM(P),0,1,SUM(P))) * 100,2), sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2),sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2), "
	 		+ "sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2),sum(X),sum(Y),ROund(SUM(Y)/(decode(SUM(X),0,1,SUM(X))) * 100,2),sum(P)+sum(R)+sum(T)+sum(V)+sum(X) sumtotal, sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y) sumdisp , "
	 		+ "ROund((sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y))/decode((sum(P)+sum(R)+sum(T)+sum(V)+sum(X)),0,1,(sum(P)+sum(R)+sum(T)+sum(V)+sum(X))) * 100,2)  "
	 		+ "from ( select refclass, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), "
	 		+ "sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), sum(subjectcount16) T, "
	 		+ "sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, "
	 		+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1, sum(subjectcount17))*100,2),sum(subjectcount18) X,sum(disposed18) Y, "
	 		+ "round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2) from  (select a.refclass,a.incomingdate,a.subject, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14,   "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18  "
	 		+ "from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  a.tenureid>=12   and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid  "
	 		+ "order by a.refclass,a.incomingdate) z  group by refclass   "
	 		+ "union "
	 		+ "select refcategory, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S,  "
	 		+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,  "
	 		+ "sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1, sum(subjectcount17))*100,2), sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2) from   "
	 		+ "(select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',  "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'), '2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, decode(to_char(a.incomingdate,'yyyy'), '2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18 from  trnreference a,  trnmarking b  where   (a.refcategory='E') and  a.tenureid>=12 and to_char(incomingdate,'yyyy')  "
	 		+ "in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory ) order by 1";
			 
			 
	 
			 "select refclass, "
			+ "sum(subjectcount14),sum(disposed14), "
			+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ "sum(subjectcount15),sum(disposed15), "
			+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ "sum(subjectcount16),sum(disposed16), "
			+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(s"
			+ ""
			+ "ubjectcount16))*100,2), "
			+ "sum(subjectcount17),sum(disposed17), "
			+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),"
			
			+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) sumdisp, "
			+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)))*100,2) "

			
			+ "from  "
			+ "(select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where  "
			+ "a.refclass  in ('A','B','C','M') "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  "
			+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
			+ "group by refclass "
			+ "union "
			+ "select refcategory, "
			+ "sum(subjectcount14),sum(disposed14), "
			+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ "sum(subjectcount15),sum(disposed15), "
			+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ "sum(subjectcount16),sum(disposed16), "
			+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
			+ "sum(subjectcount17),sum(disposed17), "
			+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
			
			+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) sumdisp, "
			+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)))*100,2) "

			
			+ "from  "
			+ "(select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where   "
			+ "(a.refcategory='E') "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  "
			+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
			+ "group by refcategory "
			+ "union "
			+ "select ' TOTAL', sum(P),sum(Q),ROund(SUM(Q)/(decode(SUM(P),0,1,SUM(P))) * 100,2), "
			+ "sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2),sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2), "
			+ "sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2),"
			
			+ "sum(P)+sum(R)+sum(T)+sum(V) sumtotal, sum(Q)+sum(S)+sum(U)+sum(W) sumdisp ,"
			+ "ROund((sum(Q)+sum(S)+sum(U)+sum(W))/decode((sum(P)+sum(R)+sum(T)+sum(V)),0,1,(sum(P)+sum(R)+sum(T)+sum(V))) * 100,2) "
			
			+ " from ( "
			+ "select refclass, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, "
			+ "sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
			+ "sum(subjectcount15))*100,2), sum(subjectcount16) T,sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, "
			+ "sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, round(sum(disposed17)/decode(sum(subjectcount17),0,1,"
			+ " sum(subjectcount17))*100,2) from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  "
			+ "subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', "
			+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', "
			+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') "
			+ "and  a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)"
			+ "  and to_char(incomingdate,'yyyy') "
			+ "in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  "
			+ "union  "
			+ "select refcategory, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, "
			+ "sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
			+ "sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1, "
			+ "sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1, "
			+ "sum(subjectcount17))*100,2) from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  "
			+ "subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', "
			+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'), "
			+ "'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 from  "
			+ "trnreference a,  trnmarking b  where   (a.refcategory='E') and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
			+ "group by refcategory "
			+ ") "
			+ "order by 1";
			}
			else {
				 strSQL = "select refclass, sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), "
				 		+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), "
				 		+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
				 		+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) +sum(subjectcount18) sumtotal,  "
				 		+ "sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) +sum(disposed18) sumdisp, round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) "
				 		+ "from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18  "
				 		+ "from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  "
				 		+ "union select refcategory, sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2),  "
				 		+ "sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),  "
				 		+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),  sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) +sum(disposed18)  sumdisp,  "
				 		+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2)  "
				 		+ "from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,decode(to_char(a.incomingdate,'yyyy'),'2014', "
				 		+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
				 		+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17,decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 from trnreference a,  trnmarking b  where   (a.refcategory='E') and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory  "
				 		+ "union select ' TOTAL', sum(P),sum(Q),ROund(SUM(Q)/decode(SUM(P),0,1,SUM(P)) * 100,2), sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2),sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2), sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2),sum(X),sum(Y),ROund(SUM(Y)/decode(SUM(X),0,1,SUM(X)) * 100,2),sum(P)+sum(R)+sum(T)+sum(V)+sum(X) sumtotal,  "
				 		+ "sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y) sumdisp ,ROund((sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y))/decode((sum(P)+sum(R)+sum(T)+sum(V)+sum(X)),0,1,(sum(P)+sum(R)+sum(T)+sum(V)+sum(X))) * 100,2)  "
				 		+ "from ( select refclass, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2),  "
				 		+ "sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), sum(subjectcount16) T,sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, round(sum(disposed17)/decode(sum(subjectcount17),0,1,  "
				 		+ "sum(subjectcount17))*100,2), sum(subjectcount18) X,sum(disposed18) Y, round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2) from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14,  decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 , "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  a.tenureid>=12  and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)   "
				 		+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  union select refcategory, sum(subjectcount14) P,sum(disposed14) Q, "
				 		+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), "
				 		+ "sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1, sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17),  "
				 		+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1, sum(subjectcount17))*100,2) ,sum(subjectcount18),sum(disposed18), "
				 		+ "round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2) from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',  "
				 		+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
				 		+ "decode(to_char(a.incomingdate,'yyyy'), '2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17,decode(to_char(a.incomingdate,'yyyy'), '2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 from  trnreference a,  trnmarking b  where  "
				 		+ "(a.refcategory='E') and  a.tenureid>=12  and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory ) order by 1";						 
						 
						 
						 
						 
						 
						 
						 
						 
						 
						 "select refclass, "
							+ "sum(subjectcount14),sum(disposed14), "
							+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
							+ "sum(subjectcount15),sum(disposed15), "
							+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
							+ "sum(subjectcount16),sum(disposed16), "
							+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
							+ "sum(subjectcount17),sum(disposed17), "
							+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),"
							
							+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) sumdisp, "
							+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)))*100,2) "

							
							+ "from  "
							+ "(select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
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
							+ "a.refclass  in ('A','B','C','M') "
							+ "and  "
							+ "a.tenureid>=12 "
							//+ "(select max(tenureid) from msttenure where roleid=430)  "
							+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  "
							+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
							+ "group by refclass "
							+ "union "
							+ "select refcategory, "
							+ "sum(subjectcount14),sum(disposed14), "
							+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
							+ "sum(subjectcount15),sum(disposed15), "
							+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
							+ "sum(subjectcount16),sum(disposed16), "
							+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
							+ "sum(subjectcount17),sum(disposed17), "
							+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
							
							+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) sumdisp, "
							+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)))*100,2) "

							
							+ "from  "
							+ "(select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 "
							+ "from trnreference a,  "
							+ "trnmarking b  "
							+ "where   "
							+ "(a.refcategory='E') "
							+ "and  "
							+ "a.tenureid>=12 "
							//+ "(select max(tenureid) from msttenure where roleid=430)  "
							+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  "
							+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
							+ "group by refcategory "
							+ "union "
							+ "select ' TOTAL', sum(P),sum(Q),ROund(SUM(Q)/decode(SUM(P),0,1,SUM(P)) * 100,2), "
							+ "sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2),sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2), "
							+ "sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2),"
							
							+ "sum(P)+sum(R)+sum(T)+sum(V) sumtotal, sum(Q)+sum(S)+sum(U)+sum(W) sumdisp ,"
							+ "ROund((sum(Q)+sum(S)+sum(U)+sum(W))/decode((sum(P)+sum(R)+sum(T)+sum(V)),0,1,(sum(P)+sum(R)+sum(T)+sum(V))) * 100,2) "
							
							+ " from ( "
							+ "select refclass, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, "
							+ "sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
							+ "sum(subjectcount15))*100,2), sum(subjectcount16) T,sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, "
							+ "sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, round(sum(disposed17)/decode(sum(subjectcount17),0,1,"
							+ " sum(subjectcount17))*100,2) from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  "
							+ "subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14,  "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', "
							+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', "
							+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') "
							+ "and  a.tenureid>=12 "
							//+ "(select max(tenureid) from msttenure where roleid=430)  "
							+ " and to_char(incomingdate,'yyyy') "
							+ "in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  "
							+ "union  "
							+ "select refcategory, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, "
							+ "sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
							+ "sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1, "
							+ "sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1, "
							+ "sum(subjectcount17))*100,2) from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  "
							+ "subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', "
							+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'), "
							+ "'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 from  "
							+ "trnreference a,  trnmarking b  where   (a.refcategory='E') and  "
							+ "a.tenureid>=12 "
							//+ "(select max(tenureid) from msttenure where roleid=430)  "
							+ " and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
							+ "group by refcategory "
							+ ") "
							+ "order by 1";
			}
	//System.out.println(" getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 19);
	
}
*/
//soumen func
private ArrayList<CommonBean> generateStatisticalSummaryData(String criteria) {
	// TODO Auto-generated method stub
String condYear="";
String strSQL="";
//System.out.println(criteria);
if(criteria.equalsIgnoreCase("1")){
	
	strSQL = "select refclass, sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), "
	 		+ " round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
	 		+ "sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
	 		+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),"
	 		+ "sum(subjectcount19),sum(disposed19), round(sum(disposed19)/decode(sum(subjectcount19),0,1,sum(subjectcount19))*100,2),"
	 		+ "sum(subjectcount20),sum(disposed20), round(sum(disposed20)/decode(sum(subjectcount20),0,1,sum(subjectcount20))*100,2),"
	 		
	 		+ "sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) +sum(subjectcount18) +sum(subjectcount19) +sum(subjectcount20) sumtotal,"
	 		+ "sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20) sumdisp, round((sum(disposed14)+ "
	 		+ "sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))+sum(disposed19)+sum(disposed20)/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)),0,1,(sum(subjectcount14)+sum(subjectcount15)+ "
	 		+ "sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)))*100,2) from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17  "
	 		+ ", decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18 "
	 		+ ", decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2019',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed19 "
	 		+ ", decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2020',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed20 "
	 		+ "from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  "
	 		+ "a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  union  "
	 		+ "select refcategory, sum(subjectcount14), "
	 		+ "sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
	 		+ "sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
	 		+ "sum(subjectcount17),sum(disposed17),round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),  sum(subjectcount18),sum(disposed18),round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),   "
			+ "sum(subjectcount19),sum(disposed19),round(sum(disposed19)/decode(sum(subjectcount19),0,1,sum(subjectcount19))*100,2), "
			+ "sum(subjectcount20),sum(disposed20),round(sum(disposed20)/decode(sum(subjectcount20),0,1,sum(subjectcount20))*100,2), "
	 		+ "sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19) +sum(subjectcount20) sumtotal,  "
	 		+ "sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20) sumdisp,  round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19)+sum(disposed20))/ "
	 		+ "decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)))*100,2) "
	 		+ "from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 , decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18, decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2019',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed19, decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2020',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed20 "
	 		+ " from trnreference a,  trnmarking b  where   (a.refcategory='E') and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory  "
	 		+ "union "
	 		+ "select ' TOTAL', sum(P),sum(Q),ROund(SUM(Q)/(decode(SUM(P),0,1,SUM(P))) * 100,2), sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2),sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2), "
	 		+ "sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2),sum(X),sum(Y),ROund(SUM(Y)/(decode(SUM(X),0,1,SUM(X))) * 100,2),sum(N),sum(O),ROund(SUM(O)/(decode(SUM(N),0,1,SUM(N))) * 100,2),sum(L),sum(M),ROund(SUM(M)/(decode(SUM(L),0,1,SUM(L))) * 100,2),sum(P)+sum(R)+sum(T)+sum(V)+sum(X)+sum(N)+sum(L) sumtotal, sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y)+sum(O)+sum(M) sumdisp , "
	 		+ "ROund((sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y)+sum(O)+sum(M))/decode((sum(P)+sum(R)+sum(T)+sum(V)+sum(X)+sum(N)+sum(L)),0,1,(sum(P)+sum(R)+sum(T)+sum(V)+sum(X)+sum(N)+sum(L))) * 100,2)  "
	 		+ "from ( select refclass, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), "
	 		+ "sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), sum(subjectcount16) T, "
	 		+ "sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, "
	 		+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1, sum(subjectcount17))*100,2),sum(subjectcount18) X,sum(disposed18) Y, "
	 		+ "round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2),sum(subjectcount19) N,sum(disposed19) O, "
	 		+ "round(sum(disposed19)/decode(sum(subjectcount19),0,1, sum(subjectcount19))*100,2),sum(subjectcount20) L,sum(disposed20) M, "
	 		+ "round(sum(disposed20)/decode(sum(subjectcount20),0,1, sum(subjectcount20))*100,2) from  (select a.refclass,a.incomingdate,a.subject, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14,   "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16,  "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, "
	 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19, decode(to_char(a.incomingdate,'yyyy'),'2019', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed19,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20, decode(to_char(a.incomingdate,'yyyy'),'2020', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed20  "
	 		+ "from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  a.tenureid>=12   and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)  and a.refid =b.refid  "
	 		+ "order by a.refclass,a.incomingdate) z  group by refclass   "
	 		+ "union "
	 		+ "select refcategory, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S,  "
	 		+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), sum(subjectcount16) T,sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1,  "
	 		+ "sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, round(sum(disposed17)/decode(sum(subjectcount17),0,1, sum(subjectcount17))*100,2), sum(subjectcount18) X,sum(disposed18) Y, round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2),sum(subjectcount19) N,sum(disposed19) O, round(sum(disposed19)/decode(sum(subjectcount19),0,1, sum(subjectcount19))*100,2),sum(subjectcount20) L,sum(disposed20) M, round(sum(disposed20)/decode(sum(subjectcount20),0,1, sum(subjectcount20))*100,2) from   "
	 		+ "(select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',  "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'), '2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, decode(to_char(a.incomingdate,'yyyy'), '2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18, decode(to_char(a.incomingdate,'yyyy'), '2019',1,0) subjectcount19, decode(to_char(a.incomingdate,'yyyy'),'2019', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed19, decode(to_char(a.incomingdate,'yyyy'), '2020',1,0) subjectcount20, decode(to_char(a.incomingdate,'yyyy'),'2020', "
	 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed20 from  trnreference a,  trnmarking b  where   (a.refcategory='E') and  a.tenureid>=12 and to_char(incomingdate,'yyyy')  "
	 		+ "in (2014,2015,2016,2017,2018,2019,2020)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory ) order by 1";

	 
	 
/*	 strSQL = "select refclass, sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), "
		 		+ " round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
		 		+ "sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
		 		+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),"
		 		+ "sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) +sum(subjectcount18) sumtotal,"
		 		+ "sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) sumdisp, round((sum(disposed14)+ "
		 		+ "sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+ "
		 		+ "sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,  "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15,  "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17  "
		 		+ ", decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18 "
		 		+ "from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  "
		 		+ "a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  union  "
		 		+ "select refcategory, sum(subjectcount14), "
		 		+ "sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
		 		+ "sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
		 		+ "sum(subjectcount17),sum(disposed17),round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),  sum(subjectcount18),sum(disposed18),round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),   "
		 		+ "sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) sumtotal,  "
		 		+ "sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18) sumdisp,  round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/ "
		 		+ "decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) "
		 		+ "from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 , decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18 "
		 		+ " from trnreference a,  trnmarking b  where   (a.refcategory='E') and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory  "
		 		+ "union "
		 		+ "select ' TOTAL', sum(P),sum(Q),ROund(SUM(Q)/(decode(SUM(P),0,1,SUM(P))) * 100,2), sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2),sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2), "
		 		+ "sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2),sum(X),sum(Y),ROund(SUM(Y)/(decode(SUM(X),0,1,SUM(X))) * 100,2),sum(P)+sum(R)+sum(T)+sum(V)+sum(X) sumtotal, sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y) sumdisp , "
		 		+ "ROund((sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y))/decode((sum(P)+sum(R)+sum(T)+sum(V)+sum(X)),0,1,(sum(P)+sum(R)+sum(T)+sum(V)+sum(X))) * 100,2)  "
		 		+ "from ( select refclass, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), "
		 		+ "sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), sum(subjectcount16) T, "
		 		+ "sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, "
		 		+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1, sum(subjectcount17))*100,2),sum(subjectcount18) X,sum(disposed18) Y, "
		 		+ "round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2) from  (select a.refclass,a.incomingdate,a.subject, "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14,   "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15,  "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16,  "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, "
		 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18  "
		 		+ "from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  a.tenureid>=12   and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid  "
		 		+ "order by a.refclass,a.incomingdate) z  group by refclass   "
		 		+ "union "
		 		+ "select refcategory, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S,  "
		 		+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,  "
		 		+ "sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1, sum(subjectcount17))*100,2), sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2) from   "
		 		+ "(select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014', "
		 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',  "
		 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
		 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'), '2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', "
		 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17, decode(to_char(a.incomingdate,'yyyy'), '2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', "
		 		+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed18 from  trnreference a,  trnmarking b  where   (a.refcategory='E') and  a.tenureid>=12 and to_char(incomingdate,'yyyy')  "
		 		+ "in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory ) order by 1";*/
			 
			 
	 
			 /*"select refclass, "
			+ "sum(subjectcount14),sum(disposed14), "
			+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ "sum(subjectcount15),sum(disposed15), "
			+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ "sum(subjectcount16),sum(disposed16), "
			+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(s"
			+ ""
			+ "ubjectcount16))*100,2), "
			+ "sum(subjectcount17),sum(disposed17), "
			+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),"
			
			+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) sumdisp, "
			+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)))*100,2) "

			
			+ "from  "
			+ "(select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where  "
			+ "a.refclass  in ('A','B','C','M') "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  "
			+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
			+ "group by refclass "
			+ "union "
			+ "select refcategory, "
			+ "sum(subjectcount14),sum(disposed14), "
			+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
			+ "sum(subjectcount15),sum(disposed15), "
			+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
			+ "sum(subjectcount16),sum(disposed16), "
			+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
			+ "sum(subjectcount17),sum(disposed17), "
			+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
			
			+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) sumdisp, "
			+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)))*100,2) "

			
			+ "from  "
			+ "(select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 "
			+ "from trnreference a,  "
			+ "trnmarking b  "
			+ "where   "
			+ "(a.refcategory='E') "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  "
			+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
			+ "group by refcategory "
			+ "union "
			+ "select ' TOTAL', sum(P),sum(Q),ROund(SUM(Q)/(decode(SUM(P),0,1,SUM(P))) * 100,2), "
			+ "sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2),sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2), "
			+ "sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2),"
			
			+ "sum(P)+sum(R)+sum(T)+sum(V) sumtotal, sum(Q)+sum(S)+sum(U)+sum(W) sumdisp ,"
			+ "ROund((sum(Q)+sum(S)+sum(U)+sum(W))/decode((sum(P)+sum(R)+sum(T)+sum(V)),0,1,(sum(P)+sum(R)+sum(T)+sum(V))) * 100,2) "
			
			+ " from ( "
			+ "select refclass, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, "
			+ "sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
			+ "sum(subjectcount15))*100,2), sum(subjectcount16) T,sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, "
			+ "sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, round(sum(disposed17)/decode(sum(subjectcount17),0,1,"
			+ " sum(subjectcount17))*100,2) from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  "
			+ "subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', "
			+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', "
			+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') "
			+ "and  a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)"
			+ "  and to_char(incomingdate,'yyyy') "
			+ "in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  "
			+ "union  "
			+ "select refcategory, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, "
			+ "sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
			+ "sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1, "
			+ "sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1, "
			+ "sum(subjectcount17))*100,2) from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  "
			+ "subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed14, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', "
			+ "DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
			+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed16, decode(to_char(a.incomingdate,'yyyy'), "
			+ "'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0),0) disposed17 from  "
			+ "trnreference a,  trnmarking b  where   (a.refcategory='E') and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430)  "
			+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
			+ "group by refcategory "
			+ ") "
			+ "order by 1";*/
			}
			else {
				
				strSQL = "select refclass, " + 
						"sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2),  " + 
						"sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), " + 
						"sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), " + 
						"sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), " + 
						"sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), " + 
						"sum(subjectcount19),sum(disposed19), round(sum(disposed19)/decode(sum(subjectcount19),0,1,sum(subjectcount19))*100,2), " + 
						"sum(subjectcount20),sum(disposed20), round(sum(disposed20)/decode(sum(subjectcount20),0,1,sum(subjectcount20))*100,2), " + 
						"sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) +sum(subjectcount18)+sum(subjectcount19) +sum(subjectcount20) sumtotal, " + 
						"sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) +sum(disposed18)+sum(disposed19) +sum(disposed20) sumdisp, " + 
						"round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19) +sum(disposed20))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19) +sum(subjectcount20)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19) +sum(subjectcount20)))*100,2)  " + 
						"from  (select a.refclass,a.incomingdate,a.subject, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14,  " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17,  " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19, decode(to_char(a.incomingdate,'yyyy'),'2019',DECODE(NVL(a.fmid,0),0,0,1),0) disposed19, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20, decode(to_char(a.incomingdate,'yyyy'),'2020',DECODE(NVL(a.fmid,0),0,0,1),0) disposed20  " + 
						"from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)  " + 
						"and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  union  " + 
						" " + 
						"select refcategory, " + 
						"sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), " + 
						"sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2),  " + 
						"sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), " + 
						"sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),   " + 
						"sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), " + 
						"sum(subjectcount19),sum(disposed19), round(sum(disposed19)/decode(sum(subjectcount19),0,1,sum(subjectcount19))*100,2),  " + 
						"sum(subjectcount20),sum(disposed20), round(sum(disposed20)/decode(sum(subjectcount20),0,1,sum(subjectcount20))*100,2),  " + 
						"sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) +sum(disposed18)+sum(disposed19) +sum(disposed20)  sumdisp,  round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18)+sum(disposed19) +sum(disposed20))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)+sum(subjectcount19)+sum(subjectcount20)))*100,2)  from  (select a.refcategory,a.incomingdate,a.subject, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,decode(to_char(a.incomingdate,'yyyy'),'2014', DECODE(NVL(a.fmid,0),0,0,1),0) disposed14,  " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18,  decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19,  decode(to_char(a.incomingdate,'yyyy'),'2019',DECODE(NVL(a.fmid,0),0,0,1),0) disposed19, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20,  decode(to_char(a.incomingdate,'yyyy'),'2020',DECODE(NVL(a.fmid,0),0,0,1),0) disposed20 " + 
						"from trnreference a,  trnmarking b  where   (a.refcategory='E') and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory  union  " + 
						" " + 
						"select ' TOTAL',  " + 
						"sum(P),sum(Q),ROund(SUM(Q)/decode(SUM(P),0,1,SUM(P)) * 100,2), " + 
						"sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2), " + 
						"sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2),  " + 
						"sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2), " + 
						"sum(X),sum(Y),ROund(SUM(Y)/decode(SUM(X),0,1,SUM(X)) * 100,2), " + 
						"sum(N),sum(O),ROund(SUM(O)/decode(SUM(N),0,1,SUM(N)) * 100,2), " + 
						"sum(L),sum(M),ROund(SUM(M)/decode(SUM(L),0,1,SUM(L)) * 100,2), " + 
						"sum(P)+sum(R)+sum(T)+sum(V)+sum(X)+sum(N)+sum(L) sumtotal,   " + 
						"sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y)+sum(O)+sum(M) sumdisp , " + 
						"ROund((sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y)+sum(O)+sum(M))/decode((sum(P)+sum(R)+sum(T)+sum(V)+sum(X)+sum(N)+sum(L)),0,1,(sum(P)+sum(R)+sum(T)+sum(V)+sum(X)+sum(N)+sum(L))) * 100,2)  " + 
						"from ( select refclass,  " + 
						"sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), " + 
						"sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), " + 
						"sum(subjectcount16) T,sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, sum(subjectcount16))*100,2), " + 
						"sum(subjectcount17) V,sum(disposed17) W, round(sum(disposed17)/decode(sum(subjectcount17),0,1,  sum(subjectcount17))*100,2), " + 
						"sum(subjectcount18) X,sum(disposed18) Y, round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2), " + 
						"sum(subjectcount19) N,sum(disposed19) O, round(sum(disposed19)/decode(sum(subjectcount19),0,1, sum(subjectcount19))*100,2), " + 
						"sum(subjectcount20) L,sum(disposed20) M, round(sum(disposed20)/decode(sum(subjectcount20),0,1, sum(subjectcount20))*100,2)  " + 
						"from  (select a.refclass,a.incomingdate,a.subject, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14,  " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 , " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', DECODE(NVL(a.fmid,0),0,0,1),0) disposed18, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2019',1,0) subjectcount19, decode(to_char(a.incomingdate,'yyyy'),'2019', DECODE(NVL(a.fmid,0),0,0,1),0) disposed19, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2020',1,0) subjectcount20, decode(to_char(a.incomingdate,'yyyy'),'2020', DECODE(NVL(a.fmid,0),0,0,1),0) disposed20  " + 
						"from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  a.tenureid>=12  and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)   and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass " + 
						"union " + 
						" " + 
						"select refcategory,  " + 
						"sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), " + 
						"sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2),  " + 
						"sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1, sum(subjectcount16))*100,2), " + 
						"sum(subjectcount17),sum(disposed17),  round(sum(disposed17)/decode(sum(subjectcount17),0,1, sum(subjectcount17))*100,2) , " + 
						"sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2), " + 
						"sum(subjectcount19),sum(disposed19), round(sum(disposed19)/decode(sum(subjectcount19),0,1, sum(subjectcount19))*100,2), " + 
						"sum(subjectcount20),sum(disposed20), round(sum(disposed20)/decode(sum(subjectcount20),0,1, sum(subjectcount20))*100,2) " + 
						"from  (select a.refcategory,a.incomingdate,a.subject, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14,  decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',  DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, " + 
						"decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, " + 
						"decode(to_char(a.incomingdate,'yyyy'), '2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, " + 
						"decode(to_char(a.incomingdate,'yyyy'), '2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18, " + 
						"decode(to_char(a.incomingdate,'yyyy'), '2019',1,0) subjectcount19, decode(to_char(a.incomingdate,'yyyy'),'2019',DECODE(NVL(a.fmid,0),0,0,1),0) disposed19, " + 
						"decode(to_char(a.incomingdate,'yyyy'), '2020',1,0) subjectcount20, decode(to_char(a.incomingdate,'yyyy'),'2020',DECODE(NVL(a.fmid,0),0,0,1),0) disposed20 " + 
						"from  trnreference a,   " + 
						"trnmarking b  where  (a.refcategory='E') and  a.tenureid>=12  and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory)  order by 1 " + 
						"";
			/*	 strSQL = "select refclass, sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), "
				 		+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), "
				 		+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
				 		+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2), sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) +sum(subjectcount18) sumtotal,  "
				 		+ "sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) +sum(disposed18) sumdisp, round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2) "
				 		+ "from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17, "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18  "
				 		+ "from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  "
				 		+ "union select refcategory, sum(subjectcount14),sum(disposed14), round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), sum(subjectcount15),sum(disposed15), round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2),  "
				 		+ "sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),  "
				 		+ "sum(subjectcount18),sum(disposed18), round(sum(disposed18)/decode(sum(subjectcount18),0,1,sum(subjectcount18))*100,2),  sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) +sum(disposed18)  sumdisp,  "
				 		+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17)+sum(disposed18))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)+sum(subjectcount18)))*100,2)  "
				 		+ "from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14,decode(to_char(a.incomingdate,'yyyy'),'2014', "
				 		+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016', "
				 		+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17,decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 from trnreference a,  trnmarking b  where   (a.refcategory='E') and  a.tenureid>=12 and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory  "
				 		+ "union select ' TOTAL', sum(P),sum(Q),ROund(SUM(Q)/decode(SUM(P),0,1,SUM(P)) * 100,2), sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2),sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2), sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2),sum(X),sum(Y),ROund(SUM(Y)/decode(SUM(X),0,1,SUM(X)) * 100,2),sum(P)+sum(R)+sum(T)+sum(V)+sum(X) sumtotal,  "
				 		+ "sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y) sumdisp ,ROund((sum(Q)+sum(S)+sum(U)+sum(W)+sum(Y))/decode((sum(P)+sum(R)+sum(T)+sum(V)+sum(X)),0,1,(sum(P)+sum(R)+sum(T)+sum(V)+sum(X))) * 100,2)  "
				 		+ "from ( select refclass, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2),  "
				 		+ "sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), sum(subjectcount16) T,sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, round(sum(disposed17)/decode(sum(subjectcount17),0,1,  "
				 		+ "sum(subjectcount17))*100,2), sum(subjectcount18) X,sum(disposed18) Y, round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2) from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14,  decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', DECODE(NVL(a.fmid,0),0,0,1),0) disposed15,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 , "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018', DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') and  a.tenureid>=12  and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018)   "
				 		+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  union select refcategory, sum(subjectcount14) P,sum(disposed14) Q, "
				 		+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1, sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, sum(subjectcount15))*100,2), "
				 		+ "sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1, sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17),  "
				 		+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1, sum(subjectcount17))*100,2) ,sum(subjectcount18),sum(disposed18), "
				 		+ "round(sum(disposed18)/decode(sum(subjectcount18),0,1, sum(subjectcount18))*100,2) from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  subjectcount14,  "
				 		+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015',  "
				 		+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
				 		+ "decode(to_char(a.incomingdate,'yyyy'), '2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17,decode(to_char(a.incomingdate,'yyyy'), '2018',1,0) subjectcount18, decode(to_char(a.incomingdate,'yyyy'),'2018',DECODE(NVL(a.fmid,0),0,0,1),0) disposed18 from  trnreference a,  trnmarking b  where  "
				 		+ "(a.refcategory='E') and  a.tenureid>=12  and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refcategory ) order by 1";						 
						 
						 */
						 
						 
						 
						 
						 
						 
						 
						 /*"select refclass, "
							+ "sum(subjectcount14),sum(disposed14), "
							+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
							+ "sum(subjectcount15),sum(disposed15), "
							+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
							+ "sum(subjectcount16),sum(disposed16), "
							+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
							+ "sum(subjectcount17),sum(disposed17), "
							+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2),"
							
							+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) sumdisp, "
							+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)))*100,2) "

							
							+ "from  "
							+ "(select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
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
							+ "a.refclass  in ('A','B','C','M') "
							+ "and  "
							+ "a.tenureid>=12 "
							//+ "(select max(tenureid) from msttenure where roleid=430)  "
							+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  "
							+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
							+ "group by refclass "
							+ "union "
							+ "select refcategory, "
							+ "sum(subjectcount14),sum(disposed14), "
							+ "round(sum(disposed14)/decode(sum(subjectcount14),0,1,sum(subjectcount14))*100,2), "
							+ "sum(subjectcount15),sum(disposed15), "
							+ "round(sum(disposed15)/decode(sum(subjectcount15),0,1,sum(subjectcount15))*100,2), "
							+ "sum(subjectcount16),sum(disposed16), "
							+ "round(sum(disposed16)/decode(sum(subjectcount16),0,1,sum(subjectcount16))*100,2), "
							+ "sum(subjectcount17),sum(disposed17), "
							+ "round(sum(disposed17)/decode(sum(subjectcount17),0,1,sum(subjectcount17))*100,2), "
							
							+ " sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17) sumtotal, sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17) sumdisp, "
							+ "round((sum(disposed14)+sum(disposed15)+sum(disposed16)+sum(disposed17))/decode((sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)),0,1,(sum(subjectcount14)+sum(subjectcount15)+sum(subjectcount16)+sum(subjectcount17)))*100,2) "

							
							+ "from  "
							+ "(select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0) subjectcount14, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2015',DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 "
							+ "from trnreference a,  "
							+ "trnmarking b  "
							+ "where   "
							+ "(a.refcategory='E') "
							+ "and  "
							+ "a.tenureid>=12 "
							//+ "(select max(tenureid) from msttenure where roleid=430)  "
							+ "and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  "
							+ "and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
							+ "group by refcategory "
							+ "union "
							+ "select ' TOTAL', sum(P),sum(Q),ROund(SUM(Q)/decode(SUM(P),0,1,SUM(P)) * 100,2), "
							+ "sum(R),sum(S),ROund(SUM(S)/decode(SUM(R),0,1,SUM(R)) * 100,2),sum(T),sum(U),ROund(SUM(U)/decode(SUM(T),0,1,SUM(T)) * 100,2), "
							+ "sum(V),sum(W),ROund(SUM(W)/decode(SUM(V),0,1,SUM(V)) * 100,2),"
							
							+ "sum(P)+sum(R)+sum(T)+sum(V) sumtotal, sum(Q)+sum(S)+sum(U)+sum(W) sumdisp ,"
							+ "ROund((sum(Q)+sum(S)+sum(U)+sum(W))/decode((sum(P)+sum(R)+sum(T)+sum(V)),0,1,(sum(P)+sum(R)+sum(T)+sum(V))) * 100,2) "
							
							+ " from ( "
							+ "select refclass, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, "
							+ "sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
							+ "sum(subjectcount15))*100,2), sum(subjectcount16) T,sum(disposed16) U, round(sum(disposed16)/decode(sum(subjectcount16),0,1, "
							+ "sum(subjectcount16))*100,2), sum(subjectcount17) V,sum(disposed17) W, round(sum(disposed17)/decode(sum(subjectcount17),0,1,"
							+ " sum(subjectcount17))*100,2) from  (select a.refclass,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  "
							+ "subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14,  "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', "
							+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16,  "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017', "
							+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 from trnreference a,  trnmarking b  where  a.refclass  in ('A','B','C','M') "
							+ "and  a.tenureid>=12 "
							//+ "(select max(tenureid) from msttenure where roleid=430)  "
							+ " and to_char(incomingdate,'yyyy') "
							+ "in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  group by refclass  "
							+ "union  "
							+ "select refcategory, sum(subjectcount14) P,sum(disposed14) Q, round(sum(disposed14)/decode(sum(subjectcount14),0,1, "
							+ "sum(subjectcount14))*100,2), sum(subjectcount15) R,sum(disposed15) S, round(sum(disposed15)/decode(sum(subjectcount15),0,1, "
							+ "sum(subjectcount15))*100,2), sum(subjectcount16),sum(disposed16), round(sum(disposed16)/decode(sum(subjectcount16),0,1, "
							+ "sum(subjectcount16))*100,2), sum(subjectcount17),sum(disposed17), round(sum(disposed17)/decode(sum(subjectcount17),0,1, "
							+ "sum(subjectcount17))*100,2) from  (select a.refcategory,a.incomingdate,a.subject,decode(to_char(a.incomingdate,'yyyy'),'2014',1,0)  "
							+ "subjectcount14, decode(to_char(a.incomingdate,'yyyy'),'2014',DECODE(NVL(a.fmid,0),0,0,1),0) disposed14, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2015',1,0) subjectcount15, decode(to_char(a.incomingdate,'yyyy'),'2015', "
							+ "DECODE(NVL(a.fmid,0),0,0,1),0) disposed15, decode(to_char(a.incomingdate,'yyyy'),'2016',1,0) subjectcount16, "
							+ "decode(to_char(a.incomingdate,'yyyy'),'2016',DECODE(NVL(a.fmid,0),0,0,1),0) disposed16, decode(to_char(a.incomingdate,'yyyy'), "
							+ "'2017',1,0) subjectcount17, decode(to_char(a.incomingdate,'yyyy'),'2017',DECODE(NVL(a.fmid,0),0,0,1),0) disposed17 from  "
							+ "trnreference a,  trnmarking b  where   (a.refcategory='E') and  "
							+ "a.tenureid>=12 "
							//+ "(select max(tenureid) from msttenure where roleid=430)  "
							+ " and to_char(incomingdate,'yyyy') in (2014,2015,2016,2017)  and a.refid =b.refid order by a.refclass,a.incomingdate) z  "
							+ "group by refcategory "
							+ ") "
							+ "order by 1";*/
			}
	//System.out.println(" getReportData++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	//return (new CommonDAO()).getSQLResult(strSQL, 19);
	
	DBConnection con = new DBConnection();
	  ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					//ps.setString(1,string);
					//ps.setString(2,string);
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 25,con);
					//System.out.println("generateStatisticalSummaryData executed");
					
			} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
	
}



/*private ArrayList<CommonBean> getReportHeader(String string,String criteria) {
	// TODO Auto-generated method stub
	
	String condOfE="";
	if(string.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	 strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+string+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2014,2015,2016,2017) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') union all "
			+"select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+string+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2017) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') "
			+ "order by 1";
	
	}
	else 
	{
		 strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass ='"+string+"'"+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and "
				+ "to_char(incomingdate,'yyyy') in (2014,2015,2016,2017) "
				+ "and "
				+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
				+ "group by to_char(incomingdate,'yyyy') union all "
				+"select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass ='"+string+"'"+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and "
				+ "to_char(incomingdate,'yyyy') in (2017) "
				+ "and "
				+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
				+ "group by to_char(incomingdate,'yyyy') "
				+ "order by 1";
}
	//System.out.println("getReportHeader++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 5);
}*/
//soumen func
private ArrayList<CommonBean> getReportHeader(String string,String criteria) {
	// TODO Auto-generated method stub
	
	String condOfE="";
	if(string.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	
	ArrayList<CommonBean> arr = new ArrayList<CommonBean>();

	if(criteria.equalsIgnoreCase("1")){
	/* strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+string+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2014,2015,2016,2017) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') union all "
			+"select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+string+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2017) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') "
			+ "order by 1";*/
		
		strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass =? "+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and "
				+ "to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020) "
				+ "and "
				+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
				+ "group by to_char(incomingdate,'yyyy') union all "
				+"select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass =? "+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and "
				+ "to_char(incomingdate,'yyyy') in (2020) "
				+ "and "
				+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
				+ "group by to_char(incomingdate,'yyyy') "
				+ "order by 1";
				
	}
	else 
	{
		 /*strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass ='"+string+"'"+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and "
				+ "to_char(incomingdate,'yyyy') in (2014,2015,2016,2017) "
				+ "and "
				+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
				+ "group by to_char(incomingdate,'yyyy') union all "
				+"select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass ='"+string+"'"+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and "
				+ "to_char(incomingdate,'yyyy') in (2017) "
				+ "and "
				+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
				+ "group by to_char(incomingdate,'yyyy') "
				+ "order by 1";*/
		
		strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass =? "+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and "
				+ "to_char(incomingdate,'yyyy') in (2014,2015,2016,2017,2018,2019,2020) "
				+ "and "
				+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
				+ "group by to_char(incomingdate,'yyyy') union all "
				+"select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
				+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
				+ "from "
				+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
				+ "where  "
				+ "(a.refclass =? "+condOfE
				+ ") "
				+ "and  "
				+ "a.tenureid>=12 "
				//+ "(select max(tenureid) from msttenure where roleid=430) "
				+ "and "
				+ "to_char(incomingdate,'yyyy') in (2020) "
				+ "and "
				+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
				+ "group by to_char(incomingdate,'yyyy') "
				+ "order by 1";
		
     }
	//System.out.println("getReportHeader++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	//return (new CommonDAO()).getSQLResult(strSQL, 5);
	
	DBConnection con = new DBConnection();
	try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement(strSQL);
				ps.setString(1,string);
				ps.setString(2,string);
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 5,con);
				//System.out.println("getReportHeader executed");
				
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		finally{
				con.closeConnection();
		}
	return arr;
			
}


/*private ArrayList<CommonBean> getReportHeader2(String string, String criteria) {
	// TODO Auto-generated method stub
	String condOfE="";
	if(string.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+string+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2017) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') union all "
			+" select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+string+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2017) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') "
			+ "order by 1";
	}
	else {
	strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+string+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2017) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') union all "
			+" select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			+ "(a.refclass ='"+string+"'"+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2017) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') "
			+ "order by 1";
	}
	//System.out.println("getReportHeader2++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	return (new CommonDAO()).getSQLResult(strSQL, 5);


}*/
//soumen func
private ArrayList<CommonBean> getReportHeader2(String string, String criteria) {
	// TODO Auto-generated method stub
	String condOfE="";
	if(string.equalsIgnoreCase("E")){
		condOfE="or a.refcategory='E'";	
	}
	String strSQL="";
	if(criteria.equalsIgnoreCase("1")){
	strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			//a.refclass ='"+string+"'"+condOfE
			+ "(a.refclass =? "+condOfE 
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2020) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') union all "
			+" select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.compliance,'A'),'C',1,'D',1,'E',1,0) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			// "(a.refclass ='"+string+"'"+condOfE
			+ "(a.refclass =? "+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2020) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') "
			+ "order by 1";
	}
	else {
	strSQL = "select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			//+ "(a.refclass ='"+string+"'"+condOfE
			+ "(a.refclass =? "+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2020) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') union all "
			+" select to_char(incomingdate,'yyyy'), sum(subjectcount),sum(disposed), "
			+ "to_char(sum(disposed)/sum(subjectcount)*100,'00.99'), sum(subjectcount)-sum(disposed) pending "
			+ "from "
			+ "(select b.markingto,a.incomingdate,a.subject,1 subjectcount,DECODE(NVL(a.fmid,0),0,0,1) disposed,a.fmid from trnreference a, trnmarking b "
			+ "where  "
			//"(a.refclass ='"+string+"'"+condOfE
			+ "(a.refclass =? "+condOfE
			+ ") "
			+ "and  "
			+ "a.tenureid>=12 "
			//+ "(select max(tenureid) from msttenure where roleid=430) "
			+ "and "
			+ "to_char(incomingdate,'yyyy') in (2020) "
			+ "and "
			+ "a.refid =b.refid  order by b.markingto,a.incomingdate) z "
			+ "group by to_char(incomingdate,'yyyy') "
			+ "order by 1";
	}
	//System.out.println("getReportHeader2++++++++++++++++++++++++++++++++++++++++"+strSQL);
	//log.debug("Remider ** :-- " + strSQL);
	//return (new CommonDAO()).getSQLResult(strSQL, 5);
	
	ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
	DBConnection con = new DBConnection();
	try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement(strSQL);
				ps.setString(1,string);
				ps.setString(2,string);
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 5,con);
				//System.out.println("getReportHeader2 executed");
				
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		finally{
				con.closeConnection();
		}
	return arr;


}




public String[] getMarkToListForeDrishti() {
	
	String queryMarkingTo = "select distinct z.markingto from trnmarking z where z.markingdate>to_date('01/01/2014','dd/mm/yyyy') AND markingfrom='430' order by 1";	
						   
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo,1);
	
	String[] array=  new String[markingToList.size()];
	for(int i=0;i<markingToList.size();i++)
	{
		CommonBean beanCommon = (CommonBean) markingToList.get(i);
		array[i]=beanCommon.getField1();
	}
	return array;
}


}
