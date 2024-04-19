<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%
	String REFERENCENAME = StringFormat.nullString(request.getParameter("REFERENCENAME"));
	String query = request.getParameter("q");
	ArrayList<CommonBean> refNameArrList = new CommonDAO().getRefNameSearchnew(query);
	if(query.length()>5){
		for(int i=0;i<refNameArrList.size();i++){
				CommonBean bean=(CommonBean) refNameArrList.get(i);
				StringBuffer refNameCombined = new StringBuffer(bean.getField1());
				StringBuffer refName = new StringBuffer(bean.getField2());
				StringBuffer vipStatus = new StringBuffer(bean.getField3());
				StringBuffer stateCode = new StringBuffer(bean.getField4());
				StringBuffer vipparty = new StringBuffer(bean.getField5());
				
				//StringBuffer vipAddress = new StringBuffer(bean.getField8());
				//new code
				StringBuffer vipid = new StringBuffer(bean.getField6());
				StringBuffer vipaddress = new StringBuffer(bean.getField7());
				StringBuffer vipAddress = new StringBuffer(bean.getField8());
				StringBuffer vipStatusDesc = new StringBuffer(bean.getField9());
				//System.out.println("1111======"+vipAddress);
				out.println(refName.append(",,").append(vipStatus).append(",,").append(stateCode).append(",,").append(vipparty).append(",,").append(vipid).append(",,").append(vipaddress).append(",,").append(vipStatusDesc).append(",,").append(refNameCombined).append(",,").append(vipAddress));
		}
	}
%>