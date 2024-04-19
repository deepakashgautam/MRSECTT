<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%
	String DESTINATIONMARKING = StringFormat.nullString(request.getParameter("DESTINATIONMARKING"));
	String query = request.getParameter("q");
	if(query.length()>6){
		ArrayList<CommonBean> refNameArrList = new CommonDAO().getRefNoSearch(query,DESTINATIONMARKING);
		for(int i=0;i<refNameArrList.size();i++){
				CommonBean bean=(CommonBean) refNameArrList.get(i);
				StringBuffer refNo = new StringBuffer(bean.getField1());
				StringBuffer refId = new StringBuffer(bean.getField2());
				StringBuffer refName = new StringBuffer(bean.getField3());
				StringBuffer subjectCode = new StringBuffer(bean.getField9());
				StringBuffer subjectName = new StringBuffer(bean.getField4());
				StringBuffer subject = new StringBuffer(bean.getField5());
				StringBuffer status = new StringBuffer(bean.getField6());
				StringBuffer vipstatus = new StringBuffer(bean.getField7());
				StringBuffer state = new StringBuffer(bean.getField8());
				StringBuffer incomingdate = new StringBuffer(bean.getField10());
				StringBuffer combinedrefno = refNo.append(" ( ").append(incomingdate).append(" ) ");
				StringBuffer filestatus1 = new StringBuffer(bean.getField11());
				
			out.println(refNo.append(",,").append(refId).append(",,").append(refName).append(",,").append(subjectCode+"~"+subjectName).append(",,").append(subject).append(",,").append(status).append(",,").append(vipstatus+"/"+state).append(",,").append(combinedrefno).append(",,").append(filestatus1));
		}
	}
%>