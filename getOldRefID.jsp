<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%
	String REFERENCENO = StringFormat.nullString(request.getParameter("REFERENCENAME"));
	String query = request.getParameter("q");
	ArrayList<CommonBean> refNoArrList = new CommonDAO().getRoleIdSearch(query);
	for(int i=0;i<refNoArrList.size();i++){
	CommonBean bean=(CommonBean) refNoArrList.get(i);
			StringBuffer ROLEID = new StringBuffer(bean.getField1());
			StringBuffer ROLENAME = new StringBuffer(bean.getField2());
	out.println(ROLENAME.append(",,").append(ROLEID).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(ROLENAME));
	}
%>