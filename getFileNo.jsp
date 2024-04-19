
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%
	
	String query = request.getParameter("q");
	String fdate = request.getParameter("fdate");
	String tdate = request.getParameter("tdate");
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
	String roleid = sessionBean.getLOGINASROLEID();
	//System.out.println(" STATUS : "+ query);
	if(query.length()>3){
	ArrayList<CommonBean> statusArrList = new CommonDAO().getFileNo(query,fdate,tdate,roleid);

	for(int i=0;i<statusArrList.size();i++){
	
			CommonBean bean=(CommonBean) statusArrList.get(i);
			StringBuffer status = new StringBuffer(bean.getField1());
			
	//	out.println(refName.append(",,").append(vipStatus).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(refName));
		out.println(status.append(",,").append(status).append(status).append(status).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(status));
//		out.println(refNameCombined.append(",,").append(vipStatus).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(refName));
		
	}
	}
%>