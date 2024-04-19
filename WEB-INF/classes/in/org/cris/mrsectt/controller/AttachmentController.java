package in.org.cris.mrsectt.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AttachmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String UploadFolderfiles = "/opt/MRSECTT/ATTACHMENTFILE/";
	public static String UploadFolderref = "/opt/MRSECTT/ATTACHMENT/";
       
    public AttachmentController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String GENFILENAME = request.getParameter("GENFILENAME");
		String EOFFICEFLAG = request.getParameter("EOFFICEFLAG");
		String type = request.getParameter("TYPE");
		String pathfile = UploadFolderfiles;
		String pathref = UploadFolderref;
		doDownload(request,response,pathfile,pathref,GENFILENAME,EOFFICEFLAG,type);
	}
	
	private void doDownload(HttpServletRequest req, HttpServletResponse resp,String pathfile,String pathref, String genFName,String eOfficeflag,String type) throws IOException {
		String filename ="";
		
		
		//ATTACHMENT CODE FOR EOFFICE
	/*	if(eOfficeflag.equalsIgnoreCase("1")){
			//System.out.println("genFName is :"+genFName);;
			 filename = genFName;
			 try {
				// resp.sendRedirect(filename);
				req.getRequestDispatcher(filename).forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{*/
		if("R".equals(type))
			filename = pathref+genFName;
			else
		    filename = pathfile+genFName;
		
//		System.out.println("filename::"+filename);
		File f = new File(filename);
		int length = 0;
		ServletOutputStream op = resp.getOutputStream();
		ServletContext context = getServletConfig().getServletContext();
		String mimetype = context.getMimeType(filename);
	
		if(!(("".equals(mimetype)) || (mimetype==null)))
			resp.setContentType(mimetype);
		else
			resp.setContentType("");
			resp.setContentLength((int) f.length());
			resp.setHeader("Content-Disposition", "attachment; filename=\""	+ genFName + "\"");
	
		byte[] bbuf = new byte[1024];
		DataInputStream in = new DataInputStream(new FileInputStream(f));

		while ((in != null) && ((length = in.read(bbuf)) != -1)) {
			op.write(bbuf, 0, length);
		}
		in.close();
		op.flush();
		op.close();
	//}
	}
}