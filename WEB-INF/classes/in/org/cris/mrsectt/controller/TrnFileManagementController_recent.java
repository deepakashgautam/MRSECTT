package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.Beans.TrnFileIntMarking;
import in.org.cris.mrsectt.Beans.TrnFileMarking;
import in.org.cris.mrsectt.Beans.TrnFileRef;
import in.org.cris.mrsectt.Beans.TrnFileHdr;
import in.org.cris.mrsectt.Beans.TrnFileReply;
import in.org.cris.mrsectt.dao.TrnFileManagementDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ReferenceController
 */
public class TrnFileManagementController_recent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrnFileManagementController_recent() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//System.out.println("im in controller");
		HttpSession session = request.getSession();
		MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
		CommonBean sbean = new CommonBean();
		
		String msg = "";
		String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
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
			
			TrnFileHdr fileBean = trnFileDao.getFileData(FMID,LOGINASROLEID,ISREPLY);
			fileBean.setBtnClick(btnClick);
			
			
			session.setAttribute("sbean", sbean); 
			request.setAttribute("fileBean", fileBean);
			request.getRequestDispatcher("/FileManagement.jsp").forward(request, response);
			 
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
			
				TrnFileHdr trnfileBean =  new TrnFileHdr();
				trnfileBean.setFMID(FMID);
//				trnfileBean.setTENUREID(TENUREID);
//				trnfileBean.setROLEID(ROLEID);
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
					msg = trnFileDao.saveReference(trnfileBean,trnfilereply,trnRefBeanList,trnFileintMarkingBeanList,trnFileMarkingUBeanList,trnFileMarkingDBeanList);
					trnFileDao.clearForm();
					session.setAttribute("msg", msg);
					session.setAttribute("sbean", sbean); 
		//			request.setAttribute("pcdoBallastHopper", pcdoBallastHopper);
					response.sendRedirect("FileManagement.jsp");
				}
	}
}