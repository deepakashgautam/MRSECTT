package in.org.cris.mrsectt.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLConnection;

public class ImageServlet_unencrypted extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public ImageServlet_unencrypted()
	{
		super();
	}
	
	private ServletConfig config;
	public void init(ServletConfig config) throws ServletException 
	{
		this.config = config;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
//		String type = req.getParameter("type")!=null ? req.getParameter("type") : "";
//		String sanpath=req.getSession().getAttribute("sanpath")!=null?req.getSession().getAttribute("sanpath").toString():"";

		String imageFileName = req.getParameter("file");
		String type = req.getParameter("type");
		String imageFilePath="";
		if(type.equalsIgnoreCase("F")){
		 imageFilePath = "/opt/MRSECTT/ATTACHMENTFILE";
		}
		else if(type.equalsIgnoreCase("C")){
			 imageFilePath = "/opt/MRSECTT/COMPLIANCEATTACHMENT";
			}else{
		 imageFilePath = "/opt/MRSECTT/ATTACHMENT";	
		}
		
		//System.out.println(" imageFileName : "+imageFileName);
		
//		imageFilePath = imageFilePath + File.separator + "YARD" + File.separator;

/*		if (imageFileName != null)
		{
			imageFileName = imageFileName.replaceAll("\\.+(\\\\|/)", "");
		} else
		{
			return;
		}*/
		//System.out.println(imageFilePath);
		
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
			res.setHeader("Content-disposition", "inline; filename=\"" + imageFileName + "\"");
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

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		doGet(req, res);
	}

}
