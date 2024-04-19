package in.org.cris.mrsectt.controller;
import in.org.cris.mrsectt.dao.DashboardSubjectWiseDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * Servlet implementation class RefrenceDashboardSubjectWise
 */
public class RefrenceDashboardSubjectWise extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefrenceDashboardSubjectWise() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	doGet(request, response);
	
      try{
	    	  
	    	  // FileOutputStream fileOut =  new FileOutputStream(filename);
	    	 // hwb.write(fileOut);
	    	// fileOut.close();
	       //  File target = new File(filename);
	    		 String loginid = request.getParameter("loginid");
	    		 String typeOfUser = request.getParameter("typeOfUser");
	    		 String loginname = request.getParameter("loginname");
	    		 
	    		 if(typeOfUser.equalsIgnoreCase("stateCoordinator")){

	  		OutputStream output = response.getOutputStream();
	  		response.setContentType("application/vnd.ms-excel");
	  		response.setHeader("Content-Disposition:","attachment;filename= StateWiseSummary" + ".xls;");
	  		response.setHeader("Cache-Control", "must-revalidate");
	  ///		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(filename));
	  //		int ch = 0;
	  //		while ((ch = fin.read()) != -1) {
	  //			output.write(ch);
	  //		}
	 // 		output.flush();
	  //		output.close();
	  	//	fin.close();
	  		//target.delete();
	  		HSSFWorkbook hwb = (new DashboardSubjectWiseDAO()).getExcelDataState(loginid);
	  		FileOutputStream out = new FileOutputStream(new File("exceldatabase.xlsx"));
	  		      hwb.write(output);
	  		      out.close();
	  		      
	  		      
	    		 }
	    		 
	    		 
	    		 if(typeOfUser.equalsIgnoreCase("H")){
	    			 
	    			 OutputStream output = response.getOutputStream();
	    		  		response.setContentType("application/vnd.ms-excel");
	    		  		response.setHeader("Content-Disposition:","attachment;filename= StateWiseSummary" + ".xls;");
	    		  		response.setHeader("Cache-Control", "must-revalidate");
	    		  ///		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(filename));
	    		  //		int ch = 0;
	    		  //		while ((ch = fin.read()) != -1) {
	    		  //			output.write(ch);
	    		  //		}
	    		 // 		output.flush();
	    		  //		output.close();
	    		  	//	fin.close();
	    		  		//target.delete();
	    		  		HSSFWorkbook hwb = (new DashboardSubjectWiseDAO()).getExcelDataHOD(loginid);
	    		  		FileOutputStream out = new FileOutputStream(new File("exceldatabase.xlsx"));
	    		  		      hwb.write(output);
	    		  		      out.close(); 
	    			 
	    		 }
	    		 
	    		 
	    		 if(typeOfUser.equalsIgnoreCase("S")){
	    			 
	    			 OutputStream output = response.getOutputStream();
	    		  		response.setContentType("application/vnd.ms-excel");
	    		  		response.setHeader("Content-Disposition:","attachment;filename= StateWiseSummary" + ".xls;");
	    		  		response.setHeader("Cache-Control", "must-revalidate");
	    		  ///		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(filename));
	    		  //		int ch = 0;
	    		  //		while ((ch = fin.read()) != -1) {
	    		  //			output.write(ch);
	    		  //		}
	    		 // 		output.flush();
	    		  //		output.close();
	    		  	//	fin.close();
	    		  		//target.delete();
	    		  		HSSFWorkbook hwb = (new DashboardSubjectWiseDAO()).getExcelDataHODSub(loginname);
	    		  		FileOutputStream out = new FileOutputStream(new File("exceldatabase.xlsx"));
	    		  		      hwb.write(output);
	    		  		      out.close(); 
	    			 
	    		 }
	    		 
	    		 
	    		 if(typeOfUser.equalsIgnoreCase("R")){
	    			 
	    			 OutputStream output = response.getOutputStream();
	    		  		response.setContentType("application/vnd.ms-excel");
	    		  		response.setHeader("Content-Disposition:","attachment;filename= StateWiseSummary" + ".xls;");
	    		  		response.setHeader("Cache-Control", "must-revalidate");
	    		  ///		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(filename));
	    		  //		int ch = 0;
	    		  //		while ((ch = fin.read()) != -1) {
	    		  //			output.write(ch);
	    		  //		}
	    		 // 		output.flush();
	    		  //		output.close();
	    		  	//	fin.close();
	    		  		//target.delete();
	    		  		HSSFWorkbook hwb = (new DashboardSubjectWiseDAO()).getExcelDataSubRailway(loginname);
	    		  		FileOutputStream out = new FileOutputStream(new File("exceldatabase.xlsx"));
	    		  		      hwb.write(output);
	    		  		      out.close(); 
	    			 
	    		 }
	    		 
	    		 
	    		 
	    	  } catch ( Exception ex ) {
	    	     // System.out.println(ex);

	    	  }

	
	}

}
