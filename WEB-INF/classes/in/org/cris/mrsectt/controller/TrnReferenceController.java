package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.Beans.TrnBudget;
import in.org.cris.mrsectt.Beans.TrnMarking;
import in.org.cris.mrsectt.Beans.TrnReference;
import in.org.cris.mrsectt.dao.CommonDAO;
import in.org.cris.mrsectt.dao.TrnReferenceDAO;
import in.org.cris.mrsectt.util.StringFormat;

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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class TrnReferenceController extends HttpServlet {
	static Logger log = LogManager.getLogger(TrnReferenceController.class);
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrnReferenceController() {
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
		//System.out.println("in controller--------------------------");
	
		mrequest.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			if (MultipartFormDataRequest.isMultipartFormData(mrequest)) {
				MultipartFormDataRequest request = new MultipartFormDataRequest(mrequest);
				HttpSession session = mrequest.getSession();
				MstLogin sessionBean = (MstLogin) session.getAttribute("MstLogin");
				CommonBean sbean = new CommonBean();
				ServletContext context = getServletContext();
				String UploadFolder = "/opt/MRSECTT/ATTACHMENT";
				
				String msg = "";
				String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
				String MaximizeOnAttach = StringFormat.nullString(request.getParameter("MaximizeOnAttach"));
				
				String btnClickBDG = StringFormat.nullString(request.getParameter("btnClickBDG"));
				String REFID = StringFormat.nullString(request.getParameter("REFID"));
				String MAILID = StringFormat.nullString(request.getParameter("MAILID"));
				String TENUREID = sessionBean.getTENUREID();
				String LOGINASROLEID = sessionBean.getLOGINASROLEID();
				String LOGINASROLENAME = sessionBean.getLOGINASROLENAME();
				String LOGINID = sessionBean.getLOGINID();
				
				//////get and set search parameters 

				String EOFFICEREFNOForSave = StringFormat.nullString(request.getParameter("EOFFICEREFNOForSave"));
				//String URL = StringFormat.nullString(request.getParameter("URL"));
				//String EOFFICERECEIPTNOForSave = StringFormat.nullString(request.getParameter("EOFFICERECEIPTNOForSave"));
				
				
				//System.out.println("EOFFICEREFNOForSav=========e"+EOFFICEREFNOForSave);
				String EOFFICEREFNO = StringFormat.nullString(request.getParameter("EOFFICEREFNO"));
				
				String REFNOFROM = StringFormat.nullString(request.getParameter("REFNOFROM"));
				String REFNOTO = StringFormat.nullString(request.getParameter("REFNOTO"));
				String INCOMINGDATEFROM = StringFormat.nullString(request.getParameter("INCOMINGDATEFROM"));
				String INCOMINGDATETO = StringFormat.nullString(request.getParameter("INCOMINGDATETO"));
				String REFERENCENAMESEARCH = StringFormat.nullString(request.getParameter("REFERENCENAMESEARCH"));
				String SUBJECTSEARCH = StringFormat.nullString(request.getParameter("SUBJECTSEARCH"));
				String budgetVal = StringFormat.nullString(request.getParameter("budgetVal"));
				String COMMONSEARCHVALUE = StringFormat.nullString(request.getParameter("COMMONSEARCHVALUE"));
				String REMARKSEARCH = StringFormat.nullString(request.getParameter("REMARKSEARCH"));
				
				
				String NO_FILE_FLAG = StringFormat.nullString(request.getParameter("no_file_flag"));
				//System.out.println("NO_FILE_FLAG========="+NO_FILE_FLAG);
			
				sbean.setField1(REFNOFROM);
				sbean.setField2(REFNOTO);
				sbean.setField3(INCOMINGDATEFROM);
				sbean.setField4(INCOMINGDATETO);
				sbean.setField5(REFERENCENAMESEARCH);
				sbean.setField6(SUBJECTSEARCH);
				sbean.setField7(REMARKSEARCH);
				sbean.setField8(COMMONSEARCHVALUE);
				//////
				
				TrnReferenceDAO trnRefDao = new TrnReferenceDAO();
				
				if ("CLEAR".equalsIgnoreCase(btnClick)) {
					
					//add sbean to session for search parameters
					session.setAttribute("sbean", sbean);
					//
					response.sendRedirect("Reference.jsp");
					
				}
				
				if ("GO".equalsIgnoreCase(btnClick)) {
					String attachFlag="";
					if(MaximizeOnAttach.equalsIgnoreCase("0")) {
						attachFlag="0";
					}else
					{
						attachFlag="1";
					}
					//System.out.println("attachFlag++++++++ "+attachFlag);
					TrnReference refBean = trnRefDao.getRefData(REFID,sessionBean.getISREPLY());
					refBean.setBtnClick(btnClick);
					
					ArrayList<CommonBean> filepathArr = (new TrnReferenceDAO()).getAttachFiles(REFID,"R");
					mrequest.setAttribute("filepathArr", filepathArr);
					
					ArrayList<CommonBean> reminderArr = (new TrnReferenceDAO()).getReminderData(REFID);
					mrequest.setAttribute("reminderArr", reminderArr);
					
					//add sbean to session for search parameters
					session.setAttribute("sbean", sbean);
					session.setAttribute("attachFlag", attachFlag);
					//
					mrequest.setAttribute("refBean", refBean);
					mrequest.getRequestDispatcher("/Reference.jsp").forward(mrequest, response);
					
				}
				
				if ("GOEOFFFICE".equalsIgnoreCase(btnClick)) {
					
					TrnReference refBean = trnRefDao.getEofficeData(EOFFICEREFNO);
					refBean.setBtnClick(btnClick);
					
					//add sbean to session for search parameters
					//session.setAttribute("sbean", sbean);
					//
					mrequest.setAttribute("refBean", refBean);
					mrequest.getRequestDispatcher("/Reference.jsp").forward(mrequest, response);
					
					
					//System.out.println("inside case for eoffice++++" +btnClick + "====eofficeRefno==== "+EOFFICEREFNO);
				}
				
				
				if ("SAVE".equalsIgnoreCase(btnClick)) {
					//System.out.println("in SAVE block of controller--------------------------");
					
//	SAVE BLOCK STARTS HERE -------------------------------------------------------------------------------------------------------------------------------
					String REFNO = StringFormat.nullString(request.getParameter("REFNO"));
					String REFCLASS = StringFormat.nullString(request.getParameter("REFCLASS"));
					session.setAttribute("refClass", REFCLASS);
					String INOUT = StringFormat.nullString(request.getParameter("INOUT"));
					String INCOMINGDATE = StringFormat.nullString(request.getParameter("INCOMINGDATE"));
					String REFERENCENAME = StringFormat.nullString(request.getParameter("REFERENCENAME"));
					String LETTERDATE = StringFormat.nullString(request.getParameter("LETTERDATE"));
					String LETTERNO = StringFormat.nullString(request.getParameter("LETTERNO"));
					//System.out.println("LETTERNO  :"+LETTERNO);
					String ISBUDGET = StringFormat.nullString(request.getParameter("ISBUDGET"));
					String VIPSTATUS = StringFormat.nullString(request.getParameter("VIPSTATUS"));
					String STATECODE = StringFormat.nullString(request.getParameter("STATECODE"));
					String VIPPARTY = StringFormat.nullString(request.getParameter("VIPPARTY"));
					String ADDVIPTYPE = StringFormat.nullString(request.getParameter("VIPADDRESS"));
					String ADDVIPID = StringFormat.nullString(request.getParameter("VIPID"));
					String ACKDATE = StringFormat.nullString(request.getParameter("ACKDATE"));
					String ACKBY = StringFormat.nullString(request.getParameter("ACKBY"));
					String REFCATEGORY = StringFormat.nullString(request.getParameter("REFCATEGORY"));
					String LANGUAGE = StringFormat.nullString(request.getParameter("LANGUAGE"));
					String URGENCY = StringFormat.nullString(request.getParameter("URGENCY"));
					String LINKREFID = StringFormat.nullString(request.getParameter("LINKREFID"));
					String NOTECREATOR = StringFormat.nullString(request.getParameter("NOTECREATOR"));
					String SECURITYGRADING = StringFormat.nullString(request.getParameter("SECURITYGRADING"));
					String SIGNEDBY = StringFormat.nullString(request.getParameter("SIGNEDBY"));
					String SIGNEDON = StringFormat.nullString(request.getParameter("SIGNEDON"));
					String REMARKS = StringFormat.nullString(request.getParameter("REMARKS"));
					String TOURREMARKS = StringFormat.nullString(request.getParameter("TOURREMARKS"));
					String ODSPLACE = StringFormat.nullString(request.getParameter("ODSPLACE"));
					String ODSRAILWAY = StringFormat.nullString(request.getParameter("ODSRAILWAY"));
					String ODSDATE = StringFormat.nullString(request.getParameter("ODSDATE"));
//					String FMID = StringFormat.nullString(request.getParameter("FMID"));
//					String FILECOUNTER = StringFormat.nullString(request.getParameter("FILECOUNTER"));
					String REGISTRATIONNO = StringFormat.nullString(request.getParameter("REGISTRATIONNO"));
					String FILENO = StringFormat.nullString(request.getParameter("FILENO"));
					String EOFFICEFILENO = StringFormat.nullString(request.getParameter("EofficeFILENO"));
					String IMARKINGTO = StringFormat.nullString(request.getParameter("IMARKINGTO"));
					String IMARKINGDATE = StringFormat.nullString(request.getParameter("IMARKINGDATE"));
					String ICHANGEDATE = StringFormat.nullString(request.getParameter("ICHANGEDATE"));
					String FILESTATUS1 = StringFormat.nullString(request.getParameter("FILESTATUS1"));
					
					if(NO_FILE_FLAG.equalsIgnoreCase("18")||NO_FILE_FLAG.equalsIgnoreCase("99")||NO_FILE_FLAG.equalsIgnoreCase("98"))
					{
					 FILESTATUS1 = NO_FILE_FLAG; 
					}
					
					
					
					String FILESTATUS2 = StringFormat.nullString(request.getParameter("FILESTATUS2"));
					String REPLYTYPE = StringFormat.nullString(request.getParameter("REPLYTYPE"));
					String REASON = StringFormat.nullString(request.getParameter("REASON"));
					String DMARKINGTO = StringFormat.nullString(request.getParameter("DMARKINGTO"));
					String DMARKINGDATE = StringFormat.nullString(request.getParameter("DMARKINGDATE"));
					String REGISTRATIONDATE = StringFormat.nullString(request.getParameter("REGISTRATIONDATE"));
					String STATUSREMARK = StringFormat.nullString(request.getParameter("STATUSREMARK"));
					String RECIEVEDBY = StringFormat.nullString(request.getParameter("RECIEVEDBY"));
					String TAG1 = StringFormat.nullString(request.getParameter("TAG1"));
					String TAG2 = StringFormat.nullString(request.getParameter("TAG2"));
					String TAG3 = StringFormat.nullString(request.getParameter("TAG3"));
					String TAG4 = StringFormat.nullString(request.getParameter("TAG4"));
					String isConf = (String) CommonDAO.getIsConf(URGENCY);
					String cntimg = StringFormat.nullString(request.getParameter("cntimg"));
					
					String TXT_NOTE = StringFormat.nullString(request.getParameter("TXT_NOTE"));
					String SIGNEDBY_YS = StringFormat.nullString(request.getParameter("SIGNEDBY_YS"));
					
					String ADDRESSENGLISH = StringFormat.nullString(request.getParameter("ADDRESSENGLISH"));
					String SALUTATION = StringFormat.nullString(request.getParameter("SALUTATION"));
					
					 String VIPSTATUSDESC = StringFormat.nullString(request.getParameter("VIPSTATUSDESC"));
					 
					 
					 String COMPLIANCE = StringFormat.nullString(request.getParameter("COMPLIANCE"));
					 String COMPREMARKS = StringFormat.nullString(request.getParameter("COMPREMARKS"));
					 String COMPDATE = StringFormat.nullString(request.getParameter("COMPDATE"));
					
					
					TrnReference trnRefBean = new TrnReference();
					
					trnRefBean.setCOMPLIANCE(COMPLIANCE);
					trnRefBean.setCOMPLIANCEDATE(COMPDATE);
					trnRefBean.setCOMPLIANCEREMARKS(COMPREMARKS);
					trnRefBean.setEOFFICEREFNO(EOFFICEREFNOForSave);
					//trnRefBean.setURL(URL);
					
					//trnRefBean.setEOFFICERECEIPTNO(EOFFICERECEIPTNOForSave);
					
					trnRefBean.setLOGINASROLENAME(LOGINASROLENAME);
					trnRefBean.setREFID(REFID);
					trnRefBean.setTENUREID(TENUREID);
					trnRefBean.setREFNO(REFNO);
					trnRefBean.setROLEID(LOGINASROLEID);
					trnRefBean.setREFCLASS(REFCLASS);
					// trnRefBean.setREFCOUNT(REFCOUNT);
					trnRefBean.setINCOMINGDATE(INCOMINGDATE);
					trnRefBean.setREFERENCENAME(REFERENCENAME);
					trnRefBean.setLETTERDATE(LETTERDATE);
					trnRefBean.setLETTERNO(LETTERNO);
					trnRefBean.setISBUDGET(ISBUDGET);
					trnRefBean.setVIPSTATUS(VIPSTATUS);
					trnRefBean.setSTATECODE(STATECODE);
					trnRefBean.setVIPPARTY(VIPPARTY);
					
					trnRefBean.setADDVIPID(ADDVIPID);
					trnRefBean.setADDVIPTYPE(ADDVIPTYPE);
					
					trnRefBean.setACKDATE(ACKDATE);
					trnRefBean.setACKBY(ACKBY);
					trnRefBean.setREFCATEGORY(REFCATEGORY);
					trnRefBean.setLANGUAGE(LANGUAGE);
					trnRefBean.setURGENCY(URGENCY);
					trnRefBean.setLINKREFID(LINKREFID);
					trnRefBean.setNOTECREATOR(NOTECREATOR);
					trnRefBean.setSECURITYGRADING(SECURITYGRADING);
					trnRefBean.setSIGNEDBY(SIGNEDBY);
					trnRefBean.setSIGNEDON(SIGNEDON);
					trnRefBean.setREMARKS(REMARKS);
					trnRefBean.setTOURREMARKS(TOURREMARKS);
					trnRefBean.setODSRAILWAY(ODSRAILWAY);
					trnRefBean.setODSDATE(ODSDATE);
					//trnRefBean.setFMID(FMID);
					//trnRefBean.setFILECOUNTER(FILECOUNTER);
					trnRefBean.setREGISTRATIONNO(REGISTRATIONNO);
					trnRefBean.setFILENO(FILENO);
					trnRefBean.setEOFFICEFILENO(EOFFICEFILENO);
					trnRefBean.setIMARKINGTO(IMARKINGTO);
					trnRefBean.setIMARKINGON(IMARKINGDATE);
					trnRefBean.setCHANGEDATE(ICHANGEDATE);
					trnRefBean.setFILESTATUS1(FILESTATUS1);
					trnRefBean.setFILESTATUS2(FILESTATUS2);
					trnRefBean.setREPLYTYPE(REPLYTYPE);
					trnRefBean.setREASON(REASON);
					trnRefBean.setDMARKINGTO(DMARKINGTO);
					trnRefBean.setDMARKINGDATE(DMARKINGDATE);
					trnRefBean.setREGISTRATIONDATE(REGISTRATIONDATE);
					trnRefBean.setSTATUSREMARK(STATUSREMARK);
					trnRefBean.setRECIEVEDBY(RECIEVEDBY);
					trnRefBean.setTAG1(TAG1);
					trnRefBean.setTAG2(TAG2);
					trnRefBean.setTAG3(TAG3);
					trnRefBean.setTAG4(TAG4);
					trnRefBean.setISCONF(isConf);
					trnRefBean.setLOGINID(LOGINID);
					trnRefBean.setTXT_NOTE(TXT_NOTE);
					trnRefBean.setSIGNEDBY_YS(SIGNEDBY_YS);
					
					
					trnRefBean.setADDRESSENGLISH(ADDRESSENGLISH);
					trnRefBean.setVIPSTATUSDESC(VIPSTATUSDESC);
					
					trnRefBean.setSALUTATION(new String(SALUTATION.getBytes("ISO-8859-1"), "UTF-8"));
					
					String[] BDGREFID = request.getParameterValues("BDGREFID");
					String[] BUDGETSEQUENCE = request.getParameterValues("BUDGETSEQUENCE");
					String[] BDGSUBJECTCODE = request.getParameterValues("BDGSUBJECTCODE");
					
					
					String[] BDGSUBJECT = request.getParameterValues("BDGSUBJECT");
					String[] BDGMRREMARK = request.getParameterValues("BDGMRREMARK");
					String[] BDGREMARK = request.getParameterValues("BDGREMARK");
					
					ArrayList<TrnBudget> trnBudgetBeanList = new ArrayList<TrnBudget>();
					// skip the first row of table from the jsp page...start the loop from 1 instead of 0
					for (int i = 1; i < BDGSUBJECTCODE.length; i++) {
						
						TrnBudget budgetBean = new TrnBudget();
						
						budgetBean.setREFID(BDGREFID[i]);
						budgetBean.setSUBJECTCODE(BDGSUBJECTCODE[i]);
						
						//code added by rounak for hindi saving
						//System.out.println("insideeeee");
						//System.out.println("11111111111"+BDGSUBJECT[i]);
						String subject = new String(BDGSUBJECT[i].getBytes("ISO-8859-1"), "UTF-8").replaceAll("'", "");
						subject = subject.replaceAll("\"", "");
						
						budgetBean.setSUBJECT(subject);
						//ends
						
						//budgetBean.setSUBJECT(BDGSUBJECT[i]);
						budgetBean.setMRREMARK(BDGMRREMARK[i]);
						budgetBean.setREMARK(BDGREMARK[i]);
						budgetBean.setLOGINID(LOGINID);
						budgetBean.setBUDGETSEQUENCE(BUDGETSEQUENCE[i]);
						
						trnBudgetBeanList.add(budgetBean);
					}
					
					String[] MARKINGREFID = request.getParameterValues("MARKINGREFID");
					String[] MARKINGSEQUENCE = request.getParameterValues("MARKINGSEQUENCE");
					String[] MARKINGRECIEPTNUMBER = request.getParameterValues("MARKINGRECEIPTNUMBER");
					String[] MARKINGTO = request.getParameterValues("MARKINGTO");
					
					//System.out.println("-------------------"+MARKINGTO.length);
					//System.out.println("------------------"+MARKINGRECIEPTNUMBER.length);
					
					String[] MARKINGDATE = request.getParameterValues("MARKINGDATE");
					// String[] MARKINGREMARK = request.getParameterValues("MARKINGREMARK");
					String[] TARGETDAYS = request.getParameterValues("TARGETDAYS");
					String[] TARGETDATE = request.getParameterValues("TARGETDATE");
					String[] SUBJECTCODE = request.getParameterValues("SUBJECTCODE");
					String[] SUBJECT = request.getParameterValues("SUBJECT");
					//keywords
					String[] keyword1 = request.getParameterValues("keyword1");
					String[] keyword2 = request.getParameterValues("keyword2");
					String[] keyword3 = request.getParameterValues("keyword3");
					//keywords
					//String[] k1 = request.getParameterValues("k1");
					//String[] k2 = request.getParameterValues("k2");
					//String[] k3 = request.getParameterValues("k3");
					
					
					ArrayList<TrnMarking> trnMarkingBeanList = new ArrayList<TrnMarking>();
					// skip the first row of table from the jsp page...start the loop from 1 instead of 0
					for (int i = 1; i < MARKINGTO.length; i++) {
						
						TrnMarking markingBean = new TrnMarking();
						markingBean.setREFID(MARKINGREFID[i]);
						markingBean.setMARKINGSEQUENCE(MARKINGSEQUENCE[i]);
						markingBean.setEOFFICERECEIPTNO(MARKINGRECIEPTNUMBER[i]);
//						markingBean.setMARKINGFROM(TENUREID);
						markingBean.setMARKINGFROM(LOGINASROLEID);
						markingBean.setMARKINGTO(MARKINGTO[i]);
						markingBean.setMARKINGDATE(MARKINGDATE[i]);
						markingBean.setEOFFICEREFNO(EOFFICEREFNOForSave);
						//markingBean.setEOFFICERECEIPTNO(EOFFICERECEIPTNOForSave);
						// markingBean.setMARKINGREMARK(MARKINGREMARK[i]);
						markingBean.setTARGETDAYS(TARGETDAYS[i]);
						markingBean.setTARGETDATE(TARGETDATE[i]);
						markingBean.setSUBJECTCODE(SUBJECTCODE[i]);
						
						
						String st = new String(SUBJECT[i].getBytes("ISO-8859-1"), "UTF-8").replaceAll("'", "");
						st = st.replaceAll("\"", "");
						//System.out.println("st==========================="+st);
						//System.out.println("subject==========================="+SUBJECT[i]);
						markingBean.setSUBJECT(st);
						//markingBean.setSUBJECT(SUBJECT[i]);
						trnRefBean.setSUBJECTCODE(SUBJECTCODE[i]);
						//trnRefBean.setSUBJECT(new String(BDGSUBJECT[i].getBytes("ISO-8859-1"), "UTF-8"));
						//trnRefBean.setSUBJECT(SUBJECT[i]);
						trnRefBean.setSUBJECT(st);
						
						markingBean.setKeywords1(keyword1[i]);
						markingBean.setKeywords2(keyword2[i]);
						markingBean.setKeywords3(keyword3[i]);
						
						/*trnRefBean.setK1(k1[i]);
						trnRefBean.setK2(k2[i]);
						trnRefBean.setK3(k3[i]);*/
						
						trnMarkingBeanList.add(markingBean);
					}
					
//	IMAGE ATTACHMENT CODE STARTS HERE					
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
										String newfilename = (new TrnReferenceDAO()).saveAttachment(trnRefBean.getREFID(), file.getFileName(),"R");
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
					
					if(INOUT.trim().equalsIgnoreCase("I")){
						msg = trnRefDao.saveReference(trnRefBean, trnMarkingBeanList, trnBudgetBeanList);
					} else if (INOUT.trim().equalsIgnoreCase("O")){
						msg = trnRefDao.saveReferenceOutWard(trnRefBean);	
					}else{
						
						log.debug("No Inward/OutWard Parameter : Saving Nothin");
						
					}

					//add sbean to session for search parameters
					session.setAttribute("sbean", sbean);
					//
					session.setAttribute("msg", msg);
					response.sendRedirect("Reference.jsp");
//	SAVE BLOCK ENDS HERE -------------------------------------------------------------------------------------------------------------------------------
				}
				
				if ("EMAIL".equalsIgnoreCase(btnClick)) {
					/*
//	SAVE BLOCK STARTS HERE -------------------------------------------------------------------------------------------------------------------------------
					String REFNO = StringFormat.nullString(request.getParameter("REFNO"));
					String REFCLASS = StringFormat.nullString(request.getParameter("REFCLASS"));
					session.setAttribute("refClass", REFCLASS);
					String INOUT = StringFormat.nullString(request.getParameter("INOUT"));
					String INCOMINGDATE = StringFormat.nullString(request.getParameter("INCOMINGDATE"));
					String REFERENCENAME = StringFormat.nullString(request.getParameter("REFERENCENAME"));
					String LETTERDATE = StringFormat.nullString(request.getParameter("LETTERDATE"));
					String ISBUDGET = StringFormat.nullString(request.getParameter("ISBUDGET"));
					String VIPSTATUS = StringFormat.nullString(request.getParameter("VIPSTATUS"));
					String STATECODE = StringFormat.nullString(request.getParameter("STATECODE"));
					String ACKDATE = StringFormat.nullString(request.getParameter("ACKDATE"));
					String ACKBY = StringFormat.nullString(request.getParameter("ACKBY"));
					String REFCATEGORY = StringFormat.nullString(request.getParameter("REFCATEGORY"));
					String LANGUAGE = StringFormat.nullString(request.getParameter("LANGUAGE"));
					String URGENCY = StringFormat.nullString(request.getParameter("URGENCY"));
					String LINKREFID = StringFormat.nullString(request.getParameter("LINKREFID"));
					String NOTECREATOR = StringFormat.nullString(request.getParameter("NOTECREATOR"));
					String SECURITYGRADING = StringFormat.nullString(request.getParameter("SECURITYGRADING"));
					String SIGNEDBY = StringFormat.nullString(request.getParameter("SIGNEDBY"));
					String SIGNEDON = StringFormat.nullString(request.getParameter("SIGNEDON"));
					String REMARKS = StringFormat.nullString(request.getParameter("REMARKS"));
					String ODSPLACE = StringFormat.nullString(request.getParameter("ODSPLACE"));
					String ODSRAILWAY = StringFormat.nullString(request.getParameter("ODSRAILWAY"));
					String ODSDATE = StringFormat.nullString(request.getParameter("ODSDATE"));
//					String FMID = StringFormat.nullString(request.getParameter("FMID"));
//					String FILECOUNTER = StringFormat.nullString(request.getParameter("FILECOUNTER"));
					String REGISTRATIONNO = StringFormat.nullString(request.getParameter("REGISTRATIONNO"));
					String FILENO = StringFormat.nullString(request.getParameter("FILENO"));
					String IMARKINGTO = StringFormat.nullString(request.getParameter("IMARKINGTO"));
					String IMARKINGDATE = StringFormat.nullString(request.getParameter("IMARKINGDATE"));
					String ICHANGEDATE = StringFormat.nullString(request.getParameter("ICHANGEDATE"));
					String FILESTATUS1 = StringFormat.nullString(request.getParameter("FILESTATUS1"));
					String FILESTATUS2 = StringFormat.nullString(request.getParameter("FILESTATUS2"));
					String REPLYTYPE = StringFormat.nullString(request.getParameter("REPLYTYPE"));
					String REASON = StringFormat.nullString(request.getParameter("REASON"));
					String DMARKINGTO = StringFormat.nullString(request.getParameter("DMARKINGTO"));
					String DMARKINGDATE = StringFormat.nullString(request.getParameter("DMARKINGDATE"));
					String REGISTRATIONDATE = StringFormat.nullString(request.getParameter("REGISTRATIONDATE"));
					String STATUSREMARK = StringFormat.nullString(request.getParameter("STATUSREMARK"));
					String RECIEVEDBY = StringFormat.nullString(request.getParameter("RECIEVEDBY"));
					String TAG1 = StringFormat.nullString(request.getParameter("TAG1"));
					String TAG2 = StringFormat.nullString(request.getParameter("TAG2"));
					String TAG3 = StringFormat.nullString(request.getParameter("TAG3"));
					String TAG4 = StringFormat.nullString(request.getParameter("TAG4"));
					String isConf = (String) CommonDAO.getIsConf(URGENCY);
					String cntimg = StringFormat.nullString(request.getParameter("cntimg"));
					
					String TXT_NOTE = StringFormat.nullString(request.getParameter("TXT_NOTE"));
					String SIGNEDBY_YS = StringFormat.nullString(request.getParameter("SIGNEDBY_YS"));
					
					
					TrnReference trnRefBean = new TrnReference();
					
					trnRefBean.setLOGINASROLENAME(LOGINASROLENAME);
					trnRefBean.setREFID(REFID);
					trnRefBean.setTENUREID(TENUREID);
					trnRefBean.setREFNO(REFNO);
					trnRefBean.setROLEID(LOGINASROLEID);
					trnRefBean.setREFCLASS(REFCLASS);
					// trnRefBean.setREFCOUNT(REFCOUNT);
					trnRefBean.setINCOMINGDATE(INCOMINGDATE);
					trnRefBean.setREFERENCENAME(REFERENCENAME);
					trnRefBean.setLETTERDATE(LETTERDATE);
					trnRefBean.setISBUDGET(ISBUDGET);
					trnRefBean.setVIPSTATUS(VIPSTATUS);
					trnRefBean.setSTATECODE(STATECODE);
					trnRefBean.setACKDATE(ACKDATE);
					trnRefBean.setACKBY(ACKBY);
					trnRefBean.setREFCATEGORY(REFCATEGORY);
					trnRefBean.setLANGUAGE(LANGUAGE);
					trnRefBean.setURGENCY(URGENCY);
					trnRefBean.setLINKREFID(LINKREFID);
					trnRefBean.setNOTECREATOR(NOTECREATOR);
					trnRefBean.setSECURITYGRADING(SECURITYGRADING);
					trnRefBean.setSIGNEDBY(SIGNEDBY);
					trnRefBean.setSIGNEDON(SIGNEDON);
					trnRefBean.setREMARKS(REMARKS);
					trnRefBean.setODSPLACE(ODSPLACE);
					trnRefBean.setODSRAILWAY(ODSRAILWAY);
					trnRefBean.setODSDATE(ODSDATE);
					//trnRefBean.setFMID(FMID);
					//trnRefBean.setFILECOUNTER(FILECOUNTER);
					trnRefBean.setREGISTRATIONNO(REGISTRATIONNO);
					trnRefBean.setFILENO(FILENO);
					trnRefBean.setIMARKINGTO(IMARKINGTO);
					trnRefBean.setIMARKINGON(IMARKINGDATE);
					trnRefBean.setCHANGEDATE(ICHANGEDATE);
					trnRefBean.setFILESTATUS1(FILESTATUS1);
					trnRefBean.setFILESTATUS2(FILESTATUS2);
					trnRefBean.setREPLYTYPE(REPLYTYPE);
					trnRefBean.setREASON(REASON);
					trnRefBean.setDMARKINGTO(DMARKINGTO);
					trnRefBean.setDMARKINGDATE(DMARKINGDATE);
					trnRefBean.setREGISTRATIONDATE(REGISTRATIONDATE);
					trnRefBean.setSTATUSREMARK(STATUSREMARK);
					trnRefBean.setRECIEVEDBY(RECIEVEDBY);
					trnRefBean.setTAG1(TAG1);
					trnRefBean.setTAG2(TAG2);
					trnRefBean.setTAG3(TAG3);
					trnRefBean.setTAG4(TAG4);
					trnRefBean.setISCONF(isConf);
					trnRefBean.setLOGINID(LOGINID);
					trnRefBean.setTXT_NOTE(TXT_NOTE);
					trnRefBean.setSIGNEDBY_YS(SIGNEDBY_YS);
					
					String[] BDGREFID = request.getParameterValues("BDGREFID");
					String[] BUDGETSEQUENCE = request.getParameterValues("BUDGETSEQUENCE");
					String[] BDGSUBJECTCODE = request.getParameterValues("BDGSUBJECTCODE");
					String[] BDGSUBJECT = request.getParameterValues("BDGSUBJECT");
					String[] BDGMRREMARK = request.getParameterValues("BDGMRREMARK");
					String[] BDGREMARK = request.getParameterValues("BDGREMARK");
					
					ArrayList<TrnBudget> trnBudgetBeanList = new ArrayList<TrnBudget>();
					// skip the first row of table from the jsp page...start the loop from 1 instead of 0
					for (int i = 1; i < BDGSUBJECTCODE.length; i++) {
						
						TrnBudget budgetBean = new TrnBudget();
						
						budgetBean.setREFID(BDGREFID[i]);
						budgetBean.setSUBJECTCODE(BDGSUBJECTCODE[i]);
						budgetBean.setSUBJECT(BDGSUBJECT[i]);
						budgetBean.setMRREMARK(BDGMRREMARK[i]);
						budgetBean.setREMARK(BDGREMARK[i]);
						budgetBean.setLOGINID(LOGINID);
						budgetBean.setBUDGETSEQUENCE(BUDGETSEQUENCE[i]);
						
						trnBudgetBeanList.add(budgetBean);
					}
					
					String[] MARKINGREFID = request.getParameterValues("MARKINGREFID");
					String[] MARKINGSEQUENCE = request.getParameterValues("MARKINGSEQUENCE");
					String[] MARKINGTO = request.getParameterValues("MARKINGTO");
					String[] MARKINGDATE = request.getParameterValues("MARKINGDATE");
					// String[] MARKINGREMARK = request.getParameterValues("MARKINGREMARK");
					String[] TARGETDAYS = request.getParameterValues("TARGETDAYS");
					String[] TARGETDATE = request.getParameterValues("TARGETDATE");
					String[] SUBJECTCODE = request.getParameterValues("SUBJECTCODE");
					String[] SUBJECT = request.getParameterValues("SUBJECT");
					
					ArrayList<TrnMarking> trnMarkingBeanList = new ArrayList<TrnMarking>();
					// skip the first row of table from the jsp page...start the loop from 1 instead of 0
					for (int i = 1; i < MARKINGTO.length; i++) {
						
						TrnMarking markingBean = new TrnMarking();
						markingBean.setREFID(MARKINGREFID[i]);
						markingBean.setMARKINGSEQUENCE(MARKINGSEQUENCE[i]);
//						markingBean.setMARKINGFROM(TENUREID);
						markingBean.setMARKINGFROM(LOGINASROLEID);
						markingBean.setMARKINGTO(MARKINGTO[i]);
						markingBean.setMARKINGDATE(MARKINGDATE[i]);
						// markingBean.setMARKINGREMARK(MARKINGREMARK[i]);
						markingBean.setTARGETDAYS(TARGETDAYS[i]);
						markingBean.setTARGETDATE(TARGETDATE[i]);

						markingBean.setSUBJECTCODE(SUBJECTCODE[i]);
						markingBean.setSUBJECT(SUBJECT[i]);
						//also set the trnrefbean subject
						trnRefBean.setSUBJECTCODE(SUBJECTCODE[i]);
						trnRefBean.setSUBJECT(SUBJECT[i]);
						
						
						trnMarkingBeanList.add(markingBean);
					}
					
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
										String newfilename = (new TrnReferenceDAO()).saveAttachment(trnRefBean.getREFID(), file.getFileName(),"R");
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
					
					if(INOUT.trim().equalsIgnoreCase("I")){
						msg = trnRefDao.saveReference(trnRefBean, trnMarkingBeanList, trnBudgetBeanList);
					} else if (INOUT.trim().equalsIgnoreCase("O")){
						msg = trnRefDao.saveReferenceOutWard(trnRefBean);	
					}else{
						
						log.debug("No Inward/OutWard Parameter : Saving Nothin");
						
					}

					//add sbean to session for search parameters
				//	session.setAttribute("sbean", sbean);
					//
				//	session.setAttribute("msg", msg);
				//	response.sendRedirect("Reference.jsp");
//	SAVE BLOCK ENDS HERE -------------------------------------------------------------------------------------------------------------------------------
					
//					MAIL BLOCK STARTS HERE -----------------------------------------------------------------------------------------------------------------------------
			//		String REFNO = StringFormat.nullString(request.getParameter("REFNO"));
			//		String REFERENCENAME = StringFormat.nullString(request.getParameter("REFERENCENAME"));
			//		String INCOMINGDATE = StringFormat.nullString(request.getParameter("INCOMINGDATE"));
			//		String LETTERDATE = StringFormat.nullString(request.getParameter("LETTERDATE"));
					String[] SUBJECTMAIL = request.getParameterValues("SUBJECT");
			//		String VIPSTATUS = StringFormat.nullString(request.getParameter("VIPSTATUS"));
			//		String STATECODE = StringFormat.nullString(request.getParameter("STATECODE"));
					
					TrnReference trnRefBeanMail = new TrnReference();
					
					trnRefBeanMail.setREFID(REFID);
					trnRefBeanMail.setREFNO(REFNO);
					trnRefBeanMail.setREFERENCENAME(REFERENCENAME);
					trnRefBeanMail.setINCOMINGDATE(INCOMINGDATE);
					trnRefBeanMail.setLETTERDATE(LETTERDATE);
					trnRefBeanMail.setSUBJECT(SUBJECTMAIL[SUBJECTMAIL.length-1]);
					trnRefBeanMail.setVIPSTATUS(VIPSTATUS);
					trnRefBeanMail.setSTATECODE(STATECODE);
								
					msg = trnRefDao.sendMail(trnRefBeanMail, context, MAILID);
					
					//add sbean to session for search parameters
					session.setAttribute("sbean", sbean);
					session.setAttribute("msg", msg);
					response.sendRedirect("Reference.jsp");
//	MAIL BLOCK STARTS HERE -----------------------------------------------------------------------------------------------------------------------------
				*/
					//new ReminderEmailDAO().sendReminderEmail(context);
				}
			}
		} catch (UploadException e) {
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