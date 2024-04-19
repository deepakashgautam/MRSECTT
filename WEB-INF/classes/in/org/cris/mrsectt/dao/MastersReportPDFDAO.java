package in.org.cris.mrsectt.dao;


import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import in.org.cris.mrsectt.Beans.MastersReportBean;
import in.org.cris.mrsectt.util.MSExcelUtil;



import com.lowagie.text.Cell;
import com.lowagie.text.DocWriter;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class MastersReportPDFDAO {

	
/*	public String  generateReport(String FilePath,ArrayList arrMasterBeans){
		int ran =(int) ( 1000000 * Math.random());
		FilePath=FilePath + "IRPSMReportsMasters" + ran + ".pdf";
		try {
			
			
			MastersReportBean bean = null;
			ArrayList arrh = new ArrayList();
			ArrayList arrDT = new ArrayList();			
			ArrayList arr = new ArrayList();
			String headername = ""; 
			for(int index=0;index<arrMasterBeans.size();index++)
			{
				bean = (MastersReportBean) (arrMasterBeans.get(index));
				arrh = bean.getArrh();
				arr= bean.getArr();
				arrDT = bean.getArrDT();
				headername=bean.getHeader();
			}
			
			  
			// PDF using iText			
			 Document document=new Document(PageSize.A0.rotate());
			 
			
				  
				  
			 // PdfWriter,DocWriter
		      PdfWriter.getInstance(document,new FileOutputStream(FilePath));
		      document.open();  
		      Table table=new Table(arrh.size());
		      
		     
		      
		      for(int i=0;i<arrh.size();i++)
			    {
		    	  Cell cell = new Cell (new Paragraph (arrh.get(i).toString()));
		    	  cell.setBackgroundColor (new Color (128, 200, 128));
		    	  cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		    	  table.addCell(cell);			    	
			    }	
		      
		      for(int i=0;i<arr.size();i++)
			    {
		    	  Cell cell = new Cell (new Paragraph (arr.get(i).toString()));
		    	  table.addCell(cell);				    	  
			    }
		      document.add(table);
		      document.close(); 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;
		

	}*/
	
	
	public String  generateReportWithFormat(String serverpath, ArrayList arrMasterBeans,String layout,String  colwidth) {
		int ran =(int) ( 1000000 * Math.random());
		String FilePath=serverpath + "IRPSMReportsMasters" + ran + ".pdf";
		
		//String Directory = serverpath;
				//String Directory = "D:\\";
				//String FilePath=Directory + "IRPSMReportsMasters" + ran + ".pdf";
		try {
			
			
			MastersReportBean bean = null;
			ArrayList arrh = new ArrayList();
			ArrayList arrDT = new ArrayList();			
			ArrayList arr = new ArrayList();
			String headername = ""; 
			for(int index=0;index<arrMasterBeans.size();index++)
			{
				bean = (MastersReportBean) (arrMasterBeans.get(index));
				arrh = bean.getArrh();
				arr= bean.getArr();
				arrDT = bean.getArrDT();
				headername=bean.getHeader();
			}
			// PDF using iText	
			
			//Create Fonts for header and cells
			Font fonthdr = new Font(Font.TIMES_ROMAN  , 8, Font.BOLD);
			Font fontcell = new Font(Font.TIMES_ROMAN  , 8, Font.NORMAL);
			
			// Get Column width from layout saved
			String [] colwidths=colwidth.lastIndexOf("~")>-1?colwidth.split("~"):new String []{};
			float [] fwidth = new float [arrh.size()+1];
			float sum=0;
			for (int i = 0;i <= colwidths.length; i++) {
				if(colwidths.length>(i))
					{
						//System.out.println("colwidths[i+1]::"+colwidths[i+1]);
					sum+= Float.parseFloat(colwidths[i]);
					}
			}
			for (int i = 0;i <= colwidths.length; i++) {
				if(colwidths.length>(i))
					{
						//System.out.println("colwidths[i+1]::"+colwidths[i+1]);
					fwidth[i]= Float.parseFloat(colwidths[i])/sum;
					//System.out.println("fwidth[i]::"+fwidth[i]);
					}
			}
			
			// Create PDF document
			 Document document=new Document( "P".equalsIgnoreCase(layout)? PageSize.A4 : PageSize.A4.rotate());
			
			// DocWriter.getInstance(document,new FileOutputStream(FilePath));
			 
			// com.lowagie.text.DocWriter dw = new DocWriter();
			 
			 
		      PdfWriter.getInstance(document,new FileOutputStream(FilePath));
		    //Set metadata
				 document.addTitle("IRPSM : "+headername);
				 document.addSubject(headername);
				 document.addKeywords("IRPSM,"+headername);
				 document.addCreator("http://ircep.gov.in/IRPSM");
				 document.addAuthor("Center For Raiwlay Information Systems");
				 document.addCreationDate();
			
		     if(headername.length()>0){ document.setMargins(10, 10, 20, 10);}else{document.setMargins(10, 10, 10, 10);}
		      //Set Header and Footer before opening the document
		      HeaderFooter header = new HeaderFooter(new Phrase(headername,fonthdr), false);
		      header.setAlignment(Element.ALIGN_CENTER);
		      header.setBorder(Rectangle.NO_BORDER);
		      document.setHeader(header);
		      
		      header = new HeaderFooter(new Phrase("",fonthdr), true);
		      header.setAlignment(Element.ALIGN_RIGHT);
		      header.setBorder(Rectangle.NO_BORDER);
		      document.setFooter(header);
		      
		      
		      document.open();  
		      
		      //Create Pdf table
		      PdfPTable table=new PdfPTable(arrh.size()+1);
		      table.setHeaderRows(1);
		      //System.out.println(fwidth.length+"->fwidth.length:::::::::arrh.size()<--"+arrh.size());
		      
		      //Set column width to PDF table using ratio obtained
		      if(colwidths.length==(arrh.size()+1))
				{
		    	  table.setWidths(fwidth);
		    	  float [] farr = table.getAbsoluteWidths();
		    	 
		    	  
		    	  
		    	  //table.horizontalAlignment = 0;
		    	  //leave a gap before and after the table
		    	  table.setSpacingBefore(0f);
		    	  table.setSpacingAfter(0f);
		    	   		    	  
				}
		      
		      //Set table header
		      for(int i=0;i<arrh.size();i++)
			    {
		    	  if(i==0)
		    	  {
		    		  PdfPCell cell = new PdfPCell (new Paragraph ("S.No.",fonthdr));
			    	  cell.setBackgroundColor (new Color (204, 204, 204));
			    	  //cell.setGrayFill(10);
			    	  cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			    	  table.addCell(cell);			    	
		    	  }
		    	  PdfPCell cell = new PdfPCell (new Paragraph (arrh.get(i).toString(),fonthdr));
		    	  cell.setBackgroundColor (new Color (204, 204, 204));
		    	  //cell.setGrayFill(10);
		    	  cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		    	  table.addCell(cell);			    	
			    }	
		      
		      //Set table data
		      for(int i=0;i<arr.size();i++)
			    {
		    	  if(i%arrh.size()==0)
		    	  {
		    		  PdfPCell cell = new PdfPCell (new Paragraph (((i/arrh.size())+1)+"",fontcell));
		    		  cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
			    	  table.addCell(cell);			    	
		    	  }
		    	  PdfPCell cell = new PdfPCell (new Paragraph (arr.get(i).toString(),fontcell));
		    	  cell.setHorizontalAlignment ( (arrDT.get(i % arrh.size()).toString().equalsIgnoreCase(
					"NUMBER")
					|| arrDT.get(i % arrh.size()).toString()
							.equalsIgnoreCase("DECIMAL"))? Element.ALIGN_RIGHT:Element.ALIGN_LEFT);
		    	  cell.setNoWrap(false);
		    	  table.addCell(cell);				    	  
			    }
		      document.add(table);
		      
		     
		      
		      document.close(); 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;
		

	}
	
	
	private static void download(URL input, File output) 
		    throws IOException { 
		  InputStream in = input.openStream(); 
		  try { 
		    OutputStream out = new FileOutputStream(output); 
		    try { 
		      copy(in, out); 
		    } finally { 
		      out.close(); 
		    } 
		  } finally { 
		    in.close(); 
		  } 
		} 
		
		private static void copy(InputStream in, OutputStream out) 
		    throws IOException { 
		  byte[] buffer = new byte[1024]; 
		  while (true) { 
		    int readCount = in.read(buffer); 
		    if (readCount == -1) { 
		      break; 
		    } 
		    out.write(buffer, 0, readCount); 
		  } 
		} 

}
