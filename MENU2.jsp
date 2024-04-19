<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MenuBean"%><html>
<head>
<title>menu</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/ddsmoothmenu.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/ddsmoothmenu-v.css" />

<script type="text/javascript"  src="${pageContext.request.contextPath}/jquery-ui-1.8.4.custom/js/jquery-1.3.2.min.js"></script>

<% ArrayList arr=new ArrayList();

  arr=(new CommonDAO()).getMenuData("A");
  String tempparentid="ABC";
  MenuBean bean;
  int count=0;
  String lastflag="";
 %> 
<body>

<div id="smoothmenu-ajax" class="ddsmoothmenu">
<ul>
<% 
int count1=0;
if(arr.size()>0){ 
for(int i=0;i<arr.size();i++){
bean=new MenuBean();
bean=(MenuBean)arr.get(i);

%>

<% if(bean.getFlag().equalsIgnoreCase("H")&& count++ ==0){ %>

<li><a href="#" ><%=bean.getMenutext() %> </a><ul>

<%}else if(bean.getFlag().equalsIgnoreCase("H")&& !lastflag.equalsIgnoreCase("H")) { %>
<% for (int j=0;j<count1+1;j++){ %>
</ul></li>
<%} count1=0; %>
<li><a href="#"  ><%=bean.getMenutext() %></a><ul>
<%}else if(bean.getFlag().equalsIgnoreCase("H")&& lastflag.equalsIgnoreCase("H")) { count1=count1+1; %>
<li><a href="#"  ><%=bean.getMenutext() %></a><ul>
<%} else if(bean.getFlag().equalsIgnoreCase("U")) {%>
<li><a href="<%=bean.getUrl() %>"  target="mainPage"><%=bean.getMenutext() %></a></li>
<%} %>

<%lastflag= bean.getFlag();} } %>
<% for (int j=0;j<count1+1;j++){ %>
</ul></li>
<%} count1=0; %>

</ul>
<br style="clear: left" />
</div>

</body>
</html>