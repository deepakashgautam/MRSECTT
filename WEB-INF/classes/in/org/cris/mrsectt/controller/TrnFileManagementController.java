package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.Beans.TrnFileIntMarking;
import in.org.cris.mrsectt.Beans.TrnFileMarking;
import in.org.cris.mrsectt.Beans.TrnFileRef;
import in.org.cris.mrsectt.Beans.TrnFileHdr;
import in.org.cris.mrsectt.Beans.TrnFileReply;
import in.org.cris.mrsectt.dao.TrnFileManagementDAO;
import in.org.cris.mrsectt.dao.TrnReferenceDAO;
import in.org.cris.mrsectt.util.StringFormat;
import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ReferenceController
 */
public class TrnFileManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrnFileManagementController() {
        super();
        // TODO Auto-generated constructor stub
    } 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest mrequest, HttpServletResponse response) throws ServletException, IOException {
		mrequest.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//System.out.println("im in controller");
		
		
		try {
			//System.out.println("im in controller try");
			MultipartFormDataRequest request = new MultipartFormDataRequest(mrequest);
			HttpSession session = mrequest.getSession();
			MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
			CommonBean sbean = new CommonBean();
			String UploadFolder = "/opt/MRSECTT/ATTACHMENTFILE";
			
			String msg = "";
			//System.out.println(UploadFolder);

			String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
			//System.out.println("btnClick :"+btnClick);
			String MaximizeOnAttach = StringFormat.nullString(request.getParameter("MaximizeOnAttach"));
			String FMID = StringFormat.nullString(request.getParameter("FMID"));
			String TENUREID = sessionBean.getTENUREID(); 
			String LOGINASROLEID = sessionBean.getLOGINASROLEID();
			String LOGINASROLENAME = sessionBean.getLOGINASROLENAME();
			String LOGINID = sessionBean.getLOGINID();
			String ISCONF = sessionBean.getISCONF();
			String ISREPLY = sessionBean.getISREPLY();
			
			//get parameter for search
			String FILECOUNTERFROM = StringFormat.nullString(request.getParameter("FILECOUNTERFROM"));
			String FILECOUNTERTO = StringFormat.nullString(request.getParameter("FILECOUNTERTO"));
			String REGISTRATIONDATEFROM = StringFormat.nullString(request.getParameter("REGISTRATIONDATEFROM"));
			String REGISTRATIONDATETO = StringFormat.nullString(request.getParameter("REGISTRATIONDATETO"));
			String FILENOSEARCH = StringFormat.nullString(request.getParameter("FILENOSEARCH"));
			String SUBJECTSEARCH = StringFormat.nullString(request.getParameter("SUBJECTSEARCH"));
			String RECEIVEDFRMSEARCH = StringFormat.nullString(request.getParameter("RECEIVEDFRMSEARCH"));
			String COMMONSEARCHVALUE = StringFormat.nullString(request.getParameter("COMMONSEARCHVALUE"));
			
			sbean.setField1(FILECOUNTERFROM);
			sbean.setField2(FILECOUNTERTO);
			sbean.setField3(REGISTRATIONDATEFROM);
			sbean.setField4(REGISTRATIONDATETO);
			sbean.setField5(FILENOSEARCH);
			sbean.setField6(SUBJECTSEARCH);
			sbean.setField7(RECEIVEDFRMSEARCH);
			sbean.setField8(COMMONSEARCHVALUE);
			sbean.setField9(btnClick);
			
			TrnFileManagementDAO trnFileDao = new TrnFileManagementDAO();
			if("CLEAR".equalsIgnoreCase(btnClick)){
				response.sendRedirect("FileManagement.jsp");
				 
			}
			
			if("GO".equalsIgnoreCase(btnClick)){
				String attachFlag="";
				if(MaximizeOnAttach.equalsIgnoreCase("0")) {
					attachFlag="0";
				}else
				{
					attachFlag="1";
				}
				TrnFileHdr fileBean = trnFileDao.getFileData(FMID,LOGINASROLEID,ISREPLY);
				fileBean.setBtnClick(btnClick);
				
				ArrayList<CommonBean> filepathArr = (new TrnReferenceDAO()).getAttachFiles(FMID,"F");
				mrequest.setAttribute("filepathArr", filepathArr);
				session.setAttribute("sbean", sbean); 
				session.setAttribute("attachFlag", attachFlag);
				mrequest.setAttribute("fileBean", fileBean);
				mrequest.getRequestDispatcher("/FileManagement.jsp").forward(mrequest, response);
				 
			}
			
			if ("SAVE".equalsIgnoreCase(btnClick))	{
					String REGISTRATIONDATE = StringFormat.nullString(request.getParameter("REGISTRATIONDATE"));
					String REFERENCETYPE = StringFormat.nullString(request.getParameter("REFERENCETYPE"));
				//	String REFCOUNT = StringFormat.nullString(request.getParameter("REFCOUNT"));
					String FILENO = StringFormat.nullString(request.getParameter("FILENO"));
					String DEPARTMENTCODE = StringFormat.nullString(request.getParameter("DEPARTMENTCODE"));
					String FILESTATUS1 = StringFormat.nullString(request.getParameter("FILESTATUS1"));
					String FILESTATUS2 = StringFormat.nullString(request.getParameter("FILESTATUS2"));
					String SENTTO = StringFormat.nullString(request.getParameter("SENTTO"));
					String NOL = StringFormat.nullString(request.getParameter("NOL"));
					String DESTINATIONMARKING = StringFormat.nullString(request.getParameter("DESTINATIONMARKING"));
					String RECEIVEDFROM = StringFormat.nullString(request.getParameter("RECEIVEDFROM"));
					String REMARKS = StringFormat.nullString(request.getParameter("REMARKS"));
					
					String LINKFILENO1 = StringFormat.nullString(request.getParameter("LINKFILENO1"));
					String LINKFILENO2 = StringFormat.nullString(request.getParameter("LINKFILENO2"));
					String LINKFILENO3 = StringFormat.nullString(request.getParameter("LINKFILENO3"));
					String LINKFILENO4 = StringFormat.nullString(request.getParameter("LINKFILENO4"));
					String FILECOUNTER = StringFormat.nullString(request.getParameter("FILECOUNTER"));
					String EOFFICEFILENO = StringFormat.nullString(request.getParameter("EofficeFILENO"));
					String ROLEIDORG = StringFormat.nullString(request.getParameter("ROLEIDORG"));
					//String LOGINID = StringFormat.nullString(request.getParameter("LOGINID"));
					//String CHANGEDATE = StringFormat.nullString(request.getParameter("CHANGEDATE"));
					String FILESUBJECTCODE = StringFormat.nullString(request.getParameter("FILESUBJECTCODE"));
					String ROLEIDDES = StringFormat.nullString(request.getParameter("ROLEIDDES"));
					String SUBJECT = StringFormat.nullString(request.getParameter("SUBJECT"));
					String REPLYTYPE = StringFormat.nullString(request.getParameter("REPLYTYPE"));
					String REASON = StringFormat.nullString(request.getParameter("REASON"));
					String SUBJECTCODE = StringFormat.nullString(request.getParameter("SUBJECTCODE"));
					String REGISTRATIONNO = StringFormat.nullString(request.getParameter("REGISTRATIONNO"));
					String PROPOSEDPATH = StringFormat.nullString(request.getParameter("PROPOSEDPATH"));
					String REPLY = StringFormat.nullString(request.getParameter("REPLY"));
					//String TENUREID = StringFormat.nullString(request.getParameter("TENUREID"));
					String cntimg = StringFormat.nullString(request.getParameter("cntimg"));
					
					String ISBUDGETPOINT = StringFormat.nullString(request.getParameter("ISBUDGETPOINT"));
					String VIPID = StringFormat.nullString(request.getParameter("vipid"));
				
					TrnFileHdr trnfileBean =  new TrnFileHdr();
					trnfileBean.setFMID(FMID);
//					trnfileBean.setTENUREID(TENUREID);
//					trnfileBean.setROLEID(ROLEID);
					trnfileBean.setREFERENCETYPE(REFERENCETYPE);
					trnfileBean.setFILENO(FILENO);
					trnfileBean.setDEPARTMENTCODE(DEPARTMENTCODE);
					trnfileBean.setFILESTATUS1(FILESTATUS1);
					trnfileBean.setFILESTATUS2(FILESTATUS2);
					trnfileBean.setSENTTO(SENTTO);
					trnfileBean.setNOL(NOL);
					trnfileBean.setDESTINATIONMARKING(DESTINATIONMARKING);
					trnfileBean.setRECEIVEDFROM(RECEIVEDFROM);
					trnfileBean.setREMARKS(REMARKS);
					trnfileBean.setLINKFILENO1(LINKFILENO1);
					trnfileBean.setLINKFILENO2(LINKFILENO2);
					trnfileBean.setLINKFILENO3(LINKFILENO3);
					trnfileBean.setLINKFILENO4(LINKFILENO4);
					trnfileBean.setEOFFICEFILENO(EOFFICEFILENO);
					trnfileBean.setROLEIDORG(ROLEIDORG);
					trnfileBean.setLOGINID(LOGINID);
					trnfileBean.setISCONF(ISCONF);
					trnfileBean.setROLEIDDES(ROLEIDDES);
					trnfileBean.setTENUREID(TENUREID);
					trnfileBean.setSUBJECT(SUBJECT);
					trnfileBean.setREPLYTYPE(REPLYTYPE);
					trnfileBean.setREASON(REASON);
					trnfileBean.setLOGINASROLEID(LOGINASROLEID);
					trnfileBean.setLOGINASROLENAME(LOGINASROLENAME);
					trnfileBean.setREGISTRATIONDATE(REGISTRATIONDATE);
					trnfileBean.setREGISTRATIONNO(REGISTRATIONNO);
					trnfileBean.setFILESUBJECTCODE(FILESUBJECTCODE);
					trnfileBean.setFILECOUNTER(FILECOUNTER);
					trnfileBean.setPROPOSEDPATH(PROPOSEDPATH);
					trnfileBean.setBtnClick(btnClick);
					trnfileBean.setISBUDGETPOINT(ISBUDGETPOINT);
					trnfileBean.setBUDGETPOINTVIPID(VIPID);
					
					TrnFileReply trnfilereply = new TrnFileReply();
					trnfilereply.setFMID(FMID);
					trnfilereply.setREPLY(REPLY);
					
					
					if(trnfileBean.getLOGINASROLEID().equalsIgnoreCase(trnfileBean.getDESTINATIONMARKING())){
						trnfileBean.setREGISTRATIONDATEDES(REGISTRATIONDATE);
						trnfileBean.setREGISTRATIONNODES(REGISTRATIONNO);
						trnfileBean.setFILESUBJECTCODEDES(FILESUBJECTCODE);
						trnfileBean.setFILECOUNTERDES(FILECOUNTER);
					}else{
						trnfileBean.setREGISTRATIONDATEORG(REGISTRATIONDATE);
						trnfileBean.setREGISTRATIONNOORG(REGISTRATIONNO);
						trnfileBean.setFILESUBJECTCODEORG(FILESUBJECTCODE);
						trnfileBean.setFILECOUNTERORG(FILECOUNTER);
					}
					//for reference 
					String[] REFID = request.getParameterValues("REFID");
					String[] REFNO = request.getParameterValues("REFNO");
					String[] COMPLIANCECODE = request.getParameterValues("COMPLIANCECODE");
					String[] COMPREMARKS = request.getParameterValues("COMPREMARKS");
					
					ArrayList<TrnFileRef> trnRefBeanList =  new ArrayList<TrnFileRef>();
					//skip the first row of table from the jsp page...start the loop from 1 instead of 0
					for (int i = 1; i < REFID.length; i++) {
						if(REFID[i].equals(""))
						{
							//System.out.println("inside if=REFID");
						}
						else
						{
						TrnFileRef refBean = new TrnFileRef();
						refBean.setFMID(FMID);
						refBean.setREFID(REFID[i]);
						refBean.setCompliance(COMPLIANCECODE[i]);		
						refBean.setComplianceremarks(COMPREMARKS[i]);
						trnRefBeanList.add(refBean);
						}
					}
					
					//end
					//for internal Marking
					//String[] MARKINGSEQUENCE = request.getParameterValues("MARKINGSEQUENCE");
					String[] IMARKINGTO = request.getParameterValues("IMARKINGTO");
					String[] IMARKINGDATE = request.getParameterValues("IMARKINGDATE");
					String[] IMARKINGSEQUENCE = request.getParameterValues("IMARKINGSEQUENCE");
					String[] IMARKINGREMARK = request.getParameterValues("IMARKINGREMARK");
					String[] IRETURNDATE = request.getParameterValues("IRETURNDATE");
					
					ArrayList<TrnFileIntMarking> trnFileintMarkingBeanList =  new ArrayList<TrnFileIntMarking>();
					//skip the first row of table from the jsp page...start the loop from 1 instead of 0
					for (int i = 1; i < IMARKINGTO.length; i++) {
						if(IMARKINGTO[i].equals(""))
						{
							//System.out.println("inside if=IMARKINGTO");
						}
						else
						{
						TrnFileIntMarking fileintmarkingBean = new TrnFileIntMarking();
						fileintmarkingBean.setFMID(FMID);
						fileintmarkingBean.setMARKINGTO(IMARKINGTO[i]);
						fileintmarkingBean.setMARKINGFROM(LOGINASROLEID);
						fileintmarkingBean.setFILECOUNTER(FILECOUNTER);
						fileintmarkingBean.setMARKINGDATE(IMARKINGDATE[i]);
						fileintmarkingBean.setMARKINGSEQUENCE(IMARKINGSEQUENCE[i]);
						fileintmarkingBean.setMARKINGREMARK(IMARKINGREMARK[i]);
						fileintmarkingBean.setCHANGEDATE(IRETURNDATE[i]);
						fileintmarkingBean.setLOGINID(LOGINID);
						trnFileintMarkingBeanList.add(fileintmarkingBean);
						}
					}
					//end
					
					//for upward Marking
					String[] UMARKINGTO = request.getParameterValues("UMARKINGTO");
					String[] UMARKINGDATE = request.getParameterValues("UMARKINGDATE");
					String[] UMARKINGSEQUENCE = request.getParameterValues("UMARKINGSEQUENCE");
					String[] UMARKINGREMARK = request.getParameterValues("UMARKINGREMARK");
					
					ArrayList<TrnFileMarking> trnFileMarkingUBeanList =  new ArrayList<TrnFileMarking>();
					//skip the first row of table from the jsp page...start the loop from 1 instead of 0
					for (int i = 0; i < UMARKINGTO.length; i++) {
						if(UMARKINGTO[i].equals(""))
						{
							//System.out.println("inside if=UMARKINGTO");
						}
						else
						{
						TrnFileMarking filemarkingUBean = new TrnFileMarking();
						filemarkingUBean.setFMID(FMID);
						filemarkingUBean.setMARKINGSEQUENCE(UMARKINGSEQUENCE[i]);
						filemarkingUBean.setMARKINGFROM(LOGINASROLEID);
						filemarkingUBean.setMARKINGTO(UMARKINGTO[i]);
						filemarkingUBean.setMARKINGDATE(UMARKINGDATE[i]);
						filemarkingUBean.setMARKINGREMARK(UMARKINGREMARK[i]);
						filemarkingUBean.setSUBJECTCODE(SUBJECTCODE);
						filemarkingUBean.setSUBJECT(SUBJECT);
						trnFileMarkingUBeanList.add(filemarkingUBean);
						}
					}
					//end
					
					//for downward Marking
					String[] DMARKINGTO = request.getParameterValues("DMARKINGTO");
					String[] DMARKINGDATE = request.getParameterValues("DMARKINGDATE");
					String[] DMARKINGSEQUENCE = request.getParameterValues("DMARKINGSEQUENCE");
					String[] DMARKINGREMARK = request.getParameterValues("DMARKINGREMARK");
					
					ArrayList<TrnFileMarking> trnFileMarkingDBeanList =  new ArrayList<TrnFileMarking>();
					//skip the first row of table from the jsp page...start the loop from 1 instead of 0
					for (int i = 0; i < DMARKINGTO.length; i++) {
						if(DMARKINGTO[i].equals(""))
						{
							
							trnFileDao.deleteDMarking(FMID);
							//System.out.println("inside if=DMARKINGTO");
						}
						else
						{
						TrnFileMarking filemarkingDBean = new TrnFileMarking();
						filemarkingDBean.setFMID(FMID);
						filemarkingDBean.setMARKINGSEQUENCE(DMARKINGSEQUENCE[i]);
						filemarkingDBean.setMARKINGFROM(LOGINASROLEID);
						filemarkingDBean.setMARKINGTO(DMARKINGTO[i]);
						filemarkingDBean.setMARKINGDATE(DMARKINGDATE[i]);
						filemarkingDBean.setMARKINGREMARK(DMARKINGREMARK[i]);
						filemarkingDBean.setSUBJECTCODE(SUBJECTCODE);
						filemarkingDBean.setSUBJECT(SUBJECT);
						trnFileMarkingDBeanList.add(filemarkingDBean);
						}
					}
					//end
					
//					IMAGE ATTACHMENT CODE STARTS HERE					
					String filePath = "";
					String filetype="";
					String nextimageid = "";
					String txtFile = null;
					if (request != null ) {
						Hashtable files = request.getFiles();
						if (files != null && (!files.isEmpty())) {
							for (int i = 1; i <= Integer.parseInt(cntimg); i++) {
								UploadFile file = (UploadFile) files.get("uploadImage" + (i));
								if (file != null) {
									//System.out.println("Form field : Uploaded file : " + file.getFileName() + " (" + file.getFileSize()+ " bytes)" + "Content Type : " + file.getContentType());
									UploadBean upBean = new UploadBean();
									upBean.setFolderstore(UploadFolder + File.separator);
									upBean.store(request, "uploadImage" + i);
									filePath = UploadFolder + File.separator + file.getFileName();
									if (file.getFileName()!=null) {
										String newfilename = (new TrnReferenceDAO()).saveAttachment(trnfileBean.getFMID(), file.getFileName(),"F");
										File renFile = new File(filePath);
										renFile.renameTo(new File(UploadFolder + File.separator + newfilename));
										filetype = newfilename.substring(newfilename.length() - 3,newfilename.length());
										nextimageid = newfilename.substring(0,newfilename.length() - 4);
									}
								}
							}
						}
					}
//	IMAGE ATTACHMENT CODE ENDS HERE

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
//	CREATING THUMBNAIL OF THE IMAGE ENDS HERE
					
						msg = trnFileDao.saveReference(trnfileBean,trnfilereply,trnRefBeanList,trnFileintMarkingBeanList,trnFileMarkingUBeanList,trnFileMarkingDBeanList);
						trnFileDao.clearForm();
						session.setAttribute("msg", msg);
						session.setAttribute("sbean", sbean); 
			//			request.setAttribute("pcdoBallastHopper", pcdoBallastHopper);
						response.sendRedirect("FileManagement.jsp");
					}
		}
		catch (UploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

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