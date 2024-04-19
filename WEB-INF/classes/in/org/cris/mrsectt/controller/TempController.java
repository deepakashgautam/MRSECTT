package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.Beans.TrnBudget;
import in.org.cris.mrsectt.Beans.TrnMarking;
import in.org.cris.mrsectt.Beans.TrnReference;
import in.org.cris.mrsectt.Beans.TrnReminder;
import in.org.cris.mrsectt.dao.TrnReferenceDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TempController
 */
public class TempController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TempController() {
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
		
		String msg = "";
		String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
		String btnClickBDG = StringFormat.nullString(request.getParameter("btnClickBDG"));
		String btnClickREM = StringFormat.nullString(request.getParameter("btnClickREM"));
		//System.out.println("btnClick ---- " + btnClick);
		//System.out.println("btnClickBDG ---- " + btnClickBDG);
		
		String REFID = StringFormat.nullString(request.getParameter("REFID"));
		
		String TENUREID = sessionBean.getTENUREID(); 
		String LOGINASROLEID = sessionBean.getLOGINASROLEID();
		String LOGINASROLENAME = sessionBean.getLOGINASROLENAME();
		String LOGINID = sessionBean.getLOGINID();
		
		
		TrnReferenceDAO trnRefDao = new TrnReferenceDAO();
		
		if("GO".equalsIgnoreCase(btnClick)){
			
			TrnReference refBean = trnRefDao.getRefData(REFID,sessionBean.getISREPLY());
			refBean.setBtnClick(btnClick);
			 
			request.setAttribute("refBean", refBean);
			request.getRequestDispatcher("/Reference3.jsp").forward(request, response);
			 
		}
		
		if ("SAVE".equalsIgnoreCase(btnClick))	{
				String REFNO = StringFormat.nullString(request.getParameter("REFNO"));
				String REFCLASS = StringFormat.nullString(request.getParameter("REFCLASS"));
			//	String REFCOUNT = StringFormat.nullString(request.getParameter("REFCOUNT"));
				String INCOMINGDATE = StringFormat.nullString(request.getParameter("INCOMINGDATE"));
				String REFERENCENAME = StringFormat.nullString(request.getParameter("REFERENCENAME"));
				String LETTERDATE = StringFormat.nullString(request.getParameter("LETTERDATE"));
				String ISBUDGET = StringFormat.nullString(request.getParameter("ISBUDGET"));
				
				String VIPSTATUS = StringFormat.nullString(request.getParameter("VIPSTATUS"));
				String STATECODE = StringFormat.nullString(request.getParameter("STATECODE"));
				String ACKDATE = StringFormat.nullString(request.getParameter("ACKDATE"));
				String ACKBY = StringFormat.nullString(request.getParameter("ACKBY"));
				String REFCATEGORY = StringFormat.nullString(request.getParameter("REFCATEGORY"));
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
				
				TrnReference trnRefBean =  new TrnReference();
				
				trnRefBean.setLOGINASROLENAME(LOGINASROLENAME);
		
				trnRefBean.setREFID(REFID);
				trnRefBean.setTENUREID(TENUREID);
				trnRefBean.setREFNO(REFNO);
				trnRefBean.setROLEID(LOGINASROLEID);
				trnRefBean.setREFCLASS(REFCLASS);
				//trnRefBean.setREFCOUNT(REFCOUNT);
				trnRefBean.setINCOMINGDATE(INCOMINGDATE);
				trnRefBean.setREFERENCENAME(REFERENCENAME);
				trnRefBean.setLETTERDATE(LETTERDATE);
				trnRefBean.setISBUDGET(ISBUDGET);
				trnRefBean.setVIPSTATUS(VIPSTATUS);
				trnRefBean.setSTATECODE(STATECODE);
				trnRefBean.setACKDATE(ACKDATE);
				trnRefBean.setACKBY(ACKBY);
				trnRefBean.setREFCATEGORY(REFCATEGORY);
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
				trnRefBean.setLOGINID(LOGINID);
				
		
				String[] MARKINGTO = request.getParameterValues("MARKINGTO");
				String[] MARKINGDATE = request.getParameterValues("MARKINGDATE");
				String[] MARKINGREMARK = request.getParameterValues("MARKINGREMARK");
				String[] TARGETDAYS = request.getParameterValues("TARGETDAYS");
				String[] TARGETDATE = request.getParameterValues("TARGETDATE");
				String[] SUBJECTCODE = request.getParameterValues("SUBJECTCODE");
				String[] SUBJECT = request.getParameterValues("SUBJECT");
		
				
				ArrayList<TrnMarking> trnMarkingBeanList =  new ArrayList<TrnMarking>();
				//skip the first row of table from the jsp page...start the loop from 1 instead of 0
				for (int i = 1; i < MARKINGTO.length; i++) {
					
					TrnMarking markingBean = new TrnMarking();
					markingBean.setREFID(REFID);
					markingBean.setMARKINGFROM(TENUREID);
					markingBean.setMARKINGTO(MARKINGTO[i]);
					markingBean.setMARKINGDATE(MARKINGDATE[i]);
					//markingBean.setMARKINGREMARK(MARKINGREMARK[i]);
					markingBean.setTARGETDAYS(TARGETDAYS[i]);
					markingBean.setTARGETDATE(TARGETDATE[i]);
					markingBean.setSUBJECTCODE(SUBJECTCODE[i]);
					markingBean.setSUBJECT(SUBJECT[i]);
								
					trnMarkingBeanList.add(markingBean);
				}
				
		//			msg = trnRefDao.saveReference(trnRefBean, trnMarkingBeanList);
					trnRefDao.clearForm();
					
					session.setAttribute("msg", msg);
		//			request.setAttribute("pcdoBallastHopper", pcdoBallastHopper);
					response.sendRedirect("Reference3.jsp");
				}
		
		if ("SAVEBDG".equalsIgnoreCase(btnClickBDG))	
		
		{
			//System.out.println("SAVEBDG in controller---------------------------");
			String[] BDGREFID = request.getParameterValues("BDGREFID");
			String[] BUDGETSEQUENCE = request.getParameterValues("BUDGETSEQUENCE");
			String[] BDGSUBJECTCODE = request.getParameterValues("BDGSUBJECTCODE");
			String[] BDGSUBJECT = request.getParameterValues("BDGSUBJECT");
			String[] BDGMRREMARK = request.getParameterValues("BDGMRREMARK");
			String[] BDGREMARK = request.getParameterValues("BDGREMARK");

			ArrayList<TrnBudget> trnBudgetBeanList =  new ArrayList<TrnBudget>();
			//skip the first row of table from the jsp page...start the loop from 1 instead of 0
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
			
			msg = trnRefDao.saveBudget(trnBudgetBeanList);
			trnRefDao.clearForm();
			
			session.setAttribute("msg", msg);
			response.sendRedirect("Reference2.jsp");
		}
		
		/*if ("SAVEREM".equalsIgnoreCase(btnClickREM))	
			
		{
			//System.out.println("SAVEBDG in controller---------------------------");
			String INCOMINGDATE = StringFormat.nullString(request.getParameter("REMINDERREMARKS"));
			String INCOMINGDATE = StringFormat.nullString(request.getParameter("REMINDERSIGNEDBY"));
			String INCOMINGDATE = StringFormat.nullString(request.getParameter("REMINDERDATE"));
			String INCOMINGDATE = StringFormat.nullString(request.getParameter("REMINDERTO"));
			String INCOMINGDATE = StringFormat.nullString(request.getParameter("INCOMINGDATE"));
			
			TrnReminder trnRefBean =  new TrnReminder();
			
			msg = trnRefDao.saveReminder(trnRefBean);
			trnRefDao.clearForm();
			
			session.setAttribute("msg", msg);
			response.sendRedirect("Reference2.jsp");
		}*/
	}

}
