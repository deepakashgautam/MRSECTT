
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%
	
	String STATUS = StringFormat.nullString(request.getParameter("STATUS"));
	String query = request.getParameter("q");
	//System.out.println(" STATUS : "+ query);
	ArrayList<CommonBean> statusArrList = new CommonDAO().getStatus(query);

	for(int i=0;i<statusArrList.size();i++){
	
			CommonBean bean=(CommonBean) statusArrList.get(i);
			StringBuffer status = new StringBuffer(bean.getField1());
			
	//	out.println(refName.append(",,").append(vipStatus).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(refName));
		out.println(status.append(",,").append(status).append(status).append(status).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(status));
//		out.println(refNameCombined.append(",,").append(vipStatus).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(refName));
		
	}
%>