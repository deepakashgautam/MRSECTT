package in.org.cris.mrsectt.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import in.org.cris.mrsectt.Beans.MastersReportBean;
import in.org.cris.mrsectt.util.CommonUtil;
import in.org.cris.mrsectt.util.MSExcelUtil;

public class MastersReportsExcelDAO {
	 public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256; 
	 public static final int UNIT_OFFSET_LENGTH = 7; 
	 public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 }; 
	 
	public MastersReportsExcelDAO() {
		super();
	}

	public String generateReport(String serverpath, ArrayList arrMasterBeans) {
		int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;

		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		try {
			
			MastersReportBean bean = null;
			ArrayList arrh = new ArrayList();
			ArrayList arrDT = new ArrayList();
			ArrayList arr = new ArrayList();
			String headername = "";

			// Create New Workbook
			HSSFWorkbook wb = new HSSFWorkbook();
			// Define Styles to be used for PageHeader and CellHeader and
			// DataCells

			HSSFCellStyle style_cell = wb.createCellStyle();
			style_cell.setAlignment(HorizontalAlignment.LEFT);
			style_cell.setBorderBottom(BorderStyle.THIN);
			style_cell.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderLeft(BorderStyle.THIN);
			style_cell.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderRight(BorderStyle.THIN);
			style_cell.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderTop(BorderStyle.THIN);
			style_cell.setTopBorderColor(HSSFColor.BLACK.index);
			//style_cell.setWrapText(true);

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

			// Fonts for PageHeader and CellHeaders
			HSSFFont font_pageHeader = wb.createFont();
			font_pageHeader.setFontName("Arial");
			font_pageHeader.setFontHeightInPoints((short) 14);
			font_pageHeader.setBold(true);
			font_pageHeader.setColor(HSSFColor.BLACK.index);

			HSSFFont font_Header = wb.createFont();
			font_Header.setFontHeightInPoints((short) 10);
			font_Header.setBold(true);

			// Loop for all MasterBeans
			for (int index = 0; index < arrMasterBeans.size(); index++) {
				bean = (MastersReportBean) (arrMasterBeans.get(index));
				arrh = bean.getArrh();
				arr = bean.getArr();
				arrDT = bean.getArrDT();
				headername = bean.getHeader();
				headername = (new CommonUtil().StripHTML(headername.replaceAll(
						"/", "-")));
				headername = headername.substring(0,
						30 > headername.length() ? headername.length() : 30);
				// Create New Sheet winth the headername

				HSSFSheet sheet = wb
						.createSheet((index + 1) + "." + headername);

				// Print set up to fit in one page (Overrided in last for best
				// fit)
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);

				ps.setFitHeight((short) 1);
				ps.setFitWidth((short) 1);

				// Create Sheet Header for Print
				HSSFHeader header = sheet.getHeader();
				header.setLeft("IRPSM");
				header.setRight(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				short rnum = 0;
				HSSFRichTextString string;
				// Create a row of the present sheet
				HSSFRow row = sheet.createRow(rnum++);
				// Print the Report header fields
				for (int i = 0; i < arrh.size(); i++) {
					string = new HSSFRichTextString(arrh.get(i).toString()
							.replaceAll("&deg;", "�"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);

				}
				// Prints Data
				for (int i = 0; i < arr.size(); i++) {
					if (i % arrh.size() == 0) {
						row = sheet.createRow(rnum++);
					}
					//System.out.println("DT  ["+i+"] :"+arrDT.get(i % arrh.size()));
					if (arrDT.get(i % arrh.size()).toString().equalsIgnoreCase(
							"NUMBER")
							|| arrDT.get(i % arrh.size()).toString()
									.equalsIgnoreCase("DECIMAL")) {
						//System.out.println("INSIDE IF DT  ["+i+"] :"+arrDT.get(i % arrh.size()));
						createNumberCell(wb, row, (short) (i % arrh.size()),
								style_cell_num, arr.get(i).toString());
					} else {
						createCell(wb, row, (short) (i % arrh.size()),
								style_cell, new HSSFRichTextString(arr.get(i)
										.toString()));
					}

				}
				// Adjust width of the columns
				for (int i = 0; i < arrh.size(); i++) {
					sheet.autoSizeColumn((short) i);
				}

				// Create Footer of sheet for printing
				HSSFFooter footer = sheet.getFooter();
				footer.setRight("Page " + HSSFFooter.page() + " of "
						+ HSSFFooter.numPages());
			}
			// Loop ends

			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(FilePath);

		/*	// Password Protect All Sheets
			for (int ii = 0; ii < wb.getNumberOfSheets(); ii++) {
				wb.getSheetAt(ii).protectSheet("password");
			}*/
			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;
	}
	public String generateReportWithFormat(String serverpath, ArrayList arrMasterBeans,String layout,String  colwidth) {
		int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "C:\\opt\\";
		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		//System.out.println("FilePath: " +FilePath);
		try {
			
			MastersReportBean bean = null;
			ArrayList arrh = new ArrayList();
			ArrayList arrDT = new ArrayList();
			ArrayList arr = new ArrayList();
			String headername = "";

			// Create New Workbook
			HSSFWorkbook wb = new HSSFWorkbook();
			// Define Styles to be used for PageHeader and CellHeader and
			// DataCells

			HSSFCellStyle style_cell = wb.createCellStyle();
			style_cell.setAlignment(HorizontalAlignment.LEFT);
			style_cell.setBorderBottom(BorderStyle.THIN);
			style_cell.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderLeft(BorderStyle.THIN);
			style_cell.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderRight(BorderStyle.THIN);
			style_cell.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderTop(BorderStyle.THIN);
			style_cell.setTopBorderColor(HSSFColor.BLACK.index);
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
			font_pageHeader.setFontHeightInPoints((short) 8);
			font_pageHeader.setBold(true);
			font_pageHeader.setColor(HSSFColor.BLACK.index);

			HSSFFont font_Header = wb.createFont();
			font_Header.setFontHeightInPoints((short) 8);
			font_Header.setBold(true);
			
			HSSFFont font_cell = wb.createFont();
			font_cell.setFontHeightInPoints((short) 8);

			// Loop for all MasterBeans
			for (int index = 0; index < arrMasterBeans.size(); index++) {
				bean = (MastersReportBean) (arrMasterBeans.get(index));
				arrh = bean.getArrh();
				arr = bean.getArr();
				arrDT = bean.getArrDT();
				headername = bean.getHeader();
				headername = (new CommonUtil().StripHTML(headername.replaceAll(
						"/", "-")));
				headername = headername.substring(0,
						30 > headername.length() ? headername.length() : 30);
				// Create New Sheet winth the headername

				HSSFSheet sheet = wb
						.createSheet((index + 1) + "." + headername);
				//wb.setRepeatingRowsAndColumns(index,0,arrh.size(),-1,-1);
				// Print set up to fit in one page (Overrided in last for best
				// fit)
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setLandscape(layout.equalsIgnoreCase("L"));
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

				short rnum = 0;
				HSSFRichTextString string;
				// Create a row of the present sheet
				HSSFRow row = sheet.createRow(rnum++);
				// Print the Report header fields
			/*	for (int i = 0; i < arrh.size(); i++) {
					string = new HSSFRichTextString(arrh.get(i).toString()
							.replaceAll("&deg;", "�"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}*/
	for (int i = 0; i < (arrh.size()+1); i++) {					
					
		
					if(i==0) {
						HSSFRichTextString sn=new HSSFRichTextString(("S.No").toString());
					 sn.applyFont(font_Header);
						createCell(wb, row, (short) (i), style_header, sn);
					}
					else {
					string = new HSSFRichTextString(arrh.get(i-1).toString().replaceAll("&deg;", "�"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
					}

				}
				// Prints Data
	             int j=0;
				for (int i = 0; i < arr.size() ; i++) {
					if (i % (arrh.size()) == 0) {
						row = sheet.createRow(rnum++);
						j++;
					}
					string = new HSSFRichTextString(arr.get(i).toString());						 
					string.applyFont(font_cell);
					//System.out.println("DT  ["+i+"] :"+arrDT.get(i % arrh.size()));
					if (arrDT.get(i % (arrh.size()) ).toString().equalsIgnoreCase(
							"NUMBER")
							|| arrDT.get(i % arrh.size()).toString()
									.equalsIgnoreCase("DECIMAL")) {
						//System.out.println("INSIDE IF DT  ["+i+"] :"+arrDT.get(i % arrh.size()));
						String sno=Integer.toString(j);  						
 						HSSFRichTextString sn=new HSSFRichTextString((sno).toString());						
 						 sn.applyFont(font_Header);
 						createCell(wb, row,(short) 0,style_cell, sn);
						createNumberCell(wb, row, (short) (i % arrh.size()+1),style_cell_num, arr.get(i).toString());
					} else {	
						
  						String sno=Integer.toString(j);  						
 						HSSFRichTextString sn=new HSSFRichTextString((sno).toString());						
 						 sn.applyFont(font_Header);
 						//createCell(wb, row, (short) (i), style_header, sn); 
 				
						
						
						createCell(wb, row,(short) 0,style_cell, sn);	
						
						
						createCell(wb, row, (short) ((i % arrh.size())+1),style_cell, string); 
						
					 }

				}
				// Adjust width of the columns
				String [] colwidths=colwidth.lastIndexOf("~")>-1?colwidth.split("~"):new String []{};
				//System.out.println("colwidths:: " + colwidth );
				for (int i = 0; i < arrh.size(); i++) {
					if(colwidths.length>(i))
						{
							//System.out.println("colwidths[i+1]::"+colwidths[i+1]);
							//sheet.setColumnWidth(i, new MSExcelUtil().pixel2WidthUnits(Integer.parseInt(colwidths[i+1])));
						
						sheet.setColumnWidth(i, new MSExcelUtil().pixel2WidthUnits((int) Double.parseDouble(colwidths[i+1])));
						}
				}
				/*wb.setPrintArea(
			            index, //sheet index
			            0, //start column
			            arrh.size(), //end column
			            0, //start row
			            row.getRowNum()  //end row
			    );*/
				// Create Footer of sheet for printing
				
				HSSFFooter footer = sheet.getFooter();
				
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				footer.setRight("As on " + df.format(new Date()));
				footer.setCenter("Page " + HSSFFooter.page() + " of "
						+ HSSFFooter.numPages());
			}
			// Loop ends

			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(FilePath);

		/*	// Password Protect All Sheets
			for (int ii = 0; ii < wb.getNumberOfSheets(); ii++) {
				wb.getSheetAt(ii).protectSheet("password");
			}*/
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

}

