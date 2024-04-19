
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%
	
	String REFERENCENO = StringFormat.nullString(request.getParameter("REFERENCENAME"));
	String query = request.getParameter("q");
	//System.out.println(" REFERENCENAME : "+ query);
	ArrayList<CommonBean> refNoArrList = new CommonDAO().getRefNoSearch(query);

	for(int i=0;i<refNoArrList.size();i++){
	
	CommonBean bean=(CommonBean) refNoArrList.get(i);
			StringBuffer refNo = new StringBuffer(bean.getField1());
			StringBuffer refId = new StringBuffer(bean.getField2());
			StringBuffer refName = new StringBuffer(bean.getField3());
			StringBuffer subjectCode = new StringBuffer(bean.getField9());
			StringBuffer subjectName = new StringBuffer(bean.getField4());
			StringBuffer subject = new StringBuffer(bean.getField5());
			StringBuffer status = new StringBuffer(bean.getField6());
			StringBuffer vipstatus = new StringBuffer(bean.getField7());
			StringBuffer state = new StringBuffer(bean.getField8());
			
	//	out.println(refName.append(",,").append(vipStatus).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(refName));
		out.println(refNo.append(",,").append(refId).append(",,").append(refName).append(",,").append(subjectCode+"~"+subjectName).append(",,").append(subject).append(",,").append(status).append(",,").append(vipstatus+","+state).append(",,").append(refNo));
//		out.println(refNameCombined.append(",,").append(vipStatus).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(refName));
		
	}
%>