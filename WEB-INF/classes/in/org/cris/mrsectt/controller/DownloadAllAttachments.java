package in.org.cris.mrsectt.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import in.org.cris.mrsectt.util.Encrypt;

/**
 * Servlet implementation class DownloadAllAttachments
 */
@WebServlet("/DownloadAllAttachments")
public class DownloadAllAttachments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadAllAttachments() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    private ServletConfig config;
	public void init(ServletConfig config) throws ServletException 
	{
		this.config = config;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
			
		String imageFileName = req.getParameter("filenames");
		String type = req.getParameter("types");
		String imageFilePath="";
		if(type.equalsIgnoreCase("F")){
		 imageFilePath = "/opt/MRSECTT/ATTACHMENTFILE";
		}
		else if(type.equalsIgnoreCase("C")){
			 imageFilePath = "/opt/MRSECTT/COMPLIANCEATTACHMENT";
			}else{
		 imageFilePath = "/opt/MRSECTT/ATTACHMENT";	
		}
		
		
		
		File imageFile = new File(imageFilePath, imageFileName);
		String contentType = URLConnection.guessContentTypeFromName(imageFileName);
		/*if (contentType == null || !contentType.startsWith("image"))
		{
			//System.out.println("Not an image");
			return;
		}*/
		if(!imageFile.exists())
		{
			ServletContext sc = config.getServletContext();
			String webAppPath = sc.getRealPath("/");
			String filePath = webAppPath + File.separator + "iwebalbumfiles" + File.separator + "ImageNotFound.jpg";
			imageFile = new File(filePath);
		}
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try
		{
			input = new BufferedInputStream(new FileInputStream(imageFile));
			int contentLength = input.available();
			res.reset();
			res.setContentLength(contentLength);
			res.setContentType(contentType);
			res.setHeader("Content-disposition", "attachment; filename=\"" + imageFileName + "\"");
			output = new BufferedOutputStream(res.getOutputStream());

			while (contentLength-- > 0)
			{
				output.write(input.read());
			}
			output.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			if (output != null)
			{
				try
				{
					output.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
