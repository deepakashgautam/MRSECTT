package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.dao.TrnReferenceDAO;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.ScaleDescriptor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class UploadServ_old extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	static Logger log = LogManager.getLogger(UploadServ_old.class);
	
	static
	{
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}
	public UploadServ_old()
	{
		super();
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		DBConnection con = new DBConnection();
		try
		{
			con.openConnection();
			String strSQL = " UPDATE TRNATTACHMENT SET STATUSFLAG='0'" +
							" WHERE SUBSTR(NEWFILENAME,0,LENGTH(NEWFILENAME)-4) = '"+req.getParameter("del")+"' AND TYPE='"+req.getParameter("type")+"'";
			log.debug(strSQL);
			con.update(strSQL);
		} catch (Exception e)
		{
			log.fatal(e, e);
		} finally
		{
			con.closeConnection();
		}
//		req.setAttribute("eform", req.getParameter("eform"));
//		req.setAttribute("type", req.getParameter("type"));
		req.setAttribute("refId", req.getParameter("refId"));
		req.setAttribute("type", req.getParameter("type"));
		req.getRequestDispatcher("ImageGallery.jsp").forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		MultipartFormDataRequest request = null ;
		try {
			request = new MultipartFormDataRequest(req);
		} catch (UploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String refId = request.getParameter("refId")!=null ? request.getParameter("refId") : "";
		String type = request.getParameter("type")!=null ? request.getParameter("type") : "";
		String cntimg = StringFormat.nullString(request.getParameter("cntimg"));
		
		
		String UploadFolder="";
		if(type.equalsIgnoreCase("F")){
			UploadFolder = "/opt/MRSECTT/ATTACHMENTFILE";
		}else if(type.equalsIgnoreCase("R")){
			UploadFolder = "/opt/MRSECTT/ATTACHMENT";	
		}else if(type.equalsIgnoreCase("C")){
			//UploadFolder = "/opt/MRSECTT/ATTACHMENT";	
			UploadFolder = "/opt/MRSECTT/COMPLIANCEATTACHMENT";	
		}
		
		String txtFile = null;
		String nextimageid = "";
		DBConnection con = new DBConnection();
		String filetype="";
		try
		{
//			IMAGE ATTACHMENT CODE STARTS HERE					
			String filePath = "";
			if (request != null ) {
				Hashtable files = request.getFiles();
				if (files != null && (!files.isEmpty())) {
						UploadFile file = (UploadFile) files.get("uploadfile");
						if (file != null) {
//							System.out.println("Form field : Uploaded file : " + file.getFileName() + " (" + file.getFileSize()+ " bytes)" + "Content Type : " + file.getContentType());
							UploadBean upBean = new UploadBean();
							upBean.setFolderstore(UploadFolder + File.separator);
							upBean.store(request, "uploadfile");
							filePath = UploadFolder + File.separator + file.getFileName();
							if (file.getFileName()!=null) {
								String newfilename = (new TrnReferenceDAO()).saveAttachment(refId, file.getFileName(),type);
								File renFile = new File(filePath);
								renFile.renameTo(new File(UploadFolder + File.separator + newfilename));
								filetype = newfilename.substring(newfilename.length() - 3,newfilename.length());
								nextimageid = newfilename.substring(0,newfilename.length() - 4);
							}
						}
				}
			}
//IMAGE ATTACHMENT CODE ENDS HERE
		}catch (Exception e)
		{
			log.fatal(e, e);
		}finally
		{
			con.closeConnection();
		}
		
//	CREATING THUMBNAIL OF THE IMAGE
		
		if(filetype.equalsIgnoreCase("jpg")){
		txtFile = UploadFolder  + File.separator + nextimageid + ".jpg";
		PlanarImage image = JAI.create("fileload", txtFile);
		int destWidth = Integer.parseInt("105");
		float xScale = (float) destWidth / image.getWidth();
		float yScale = 0f;
		int destHeight = Integer.parseInt("105");
		yScale = (float) destHeight / (float) image.getHeight();
		yScale = xScale;
		RenderedOp renderedOp = ScaleDescriptor.create(image, new Float(xScale), new Float(yScale), new Float(0.0f), new Float(0.0f), Interpolation.getInstance(Interpolation.INTERP_BICUBIC), null);
		writeJPEG(renderedOp.getAsBufferedImage(), txtFile.substring(0, txtFile.length() - 4) + "_s.jpg");
		}
		req.setAttribute("refId", refId);
		req.getRequestDispatcher("ImageGallery.jsp").forward(req, res);
	
	}

	public void writeJPEG(BufferedImage input, String name) throws IOException
	{
		Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
		if (iter.hasNext())
		{
			ImageWriter writer = (ImageWriter) iter.next();
			ImageWriteParam iwp = writer.getDefaultWriteParam();
			iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwp.setCompressionQuality(0.95f);
			File outFile = new File(name);
			FileImageOutputStream output = new FileImageOutputStream(outFile);
			writer.setOutput(output);
			IIOImage image = new IIOImage(input, null, null);
			writer.write(null, image, iwp);
			output.close();
		}
	}
}
