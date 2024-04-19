
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%
	
	String STATE = StringFormat.nullString(request.getParameter("TRAIN"));
	String query = request.getParameter("q");
	//System.out.println(" TRAIN : "+ query);
	ArrayList<CommonBean> stateArrList = new CommonDAO().getTrain(query); 

	for(int i=0;i<stateArrList.size();i++){
	
			CommonBean bean=(CommonBean) stateArrList.get(i);
			StringBuffer stateCode = new StringBuffer(bean.getField1());
			StringBuffer stateName = new StringBuffer(bean.getField1()+"-"+bean.getField2());
			
	//	out.println(refName.append(",,").append(vipStatus).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(refName));
		out.println(stateCode.append(",,").append(stateName).append(",,").append(stateName).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(stateName));
//		out.println(refNameCombined.append(",,").append(vipStatus).append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append("").append(",,").append(refName));
		
	}
%>