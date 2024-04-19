<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MenuBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%><html>
<head>

<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
   String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";
   
%>

<title>menu</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/ddsmoothmenu<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/ddsmoothmenu.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/ddsmoothmenu-v.css" />

<script type="text/javascript"  src="${pageContext.request.contextPath}/jquery-ui-1.8.4.custom/js/jquery-1.3.2.min.js"></script>

<% 
MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
ArrayList arr=new ArrayList();

  arr=(new CommonDAO()).getUserMenuData(sessionBean.getLOGINID());
 // arr=(new CommonDAO()).getMenuData("WPMS1");
 
  MenuBean bean;

  String lastflag="";
  String parentid="";
  String rootid="0"; 
  String arraymenuid="";
  boolean arrayflag=true;
 %> 
<body bgcolor="#ffd6bf">

<div id="smoothmenu-ajax" class="ddsmoothmenu" >
<ul>
<% 

ArrayList  myarray=new ArrayList();
if(arr.size()>0){ 
for(int i=0;i<arr.size();i++){
bean=new MenuBean();
bean=(MenuBean)arr.get(i);

if(!bean.getRoot().equalsIgnoreCase(rootid)){

while( myarray.size()>0&& rootid.length()>0) {
myarray.remove(myarray.size()-1);%>
</ul>
<%
} %>
<li><a href="<%=bean.getUrl() %>"  ><%=bean.getMenutext() %></a><ul>
<% myarray.add(bean.getMenuid());
}else if(bean.getFlag().equalsIgnoreCase("H")&& lastflag.equalsIgnoreCase("U")){
arrayflag=true;
while(arrayflag&& myarray.size()>0) {
arraymenuid=myarray.get(myarray.size()-1).toString();
if(!arraymenuid.equalsIgnoreCase(bean.getParentid())){
myarray.remove(myarray.size()-1);%>
</ul></li>
<%
}else {
arrayflag=false;

}

}

myarray.add(bean.getMenuid());%>

<li><a href="<%=bean.getUrl() %>"  ><%=bean.getMenutext() %></a><ul>
<%
}
else if(bean.getFlag().equalsIgnoreCase("H")) {
myarray.add(bean.getMenuid()); %>
<li><a href="<%=bean.getUrl() %>"  ><%=bean.getMenutext() %></a><ul>
<%
} else if(bean.getFlag().equalsIgnoreCase("U")&& lastflag.equalsIgnoreCase("U") && !parentid.equalsIgnoreCase(bean.getParentid()) ){
arrayflag=true;
while(arrayflag&& myarray.size()>0) {
arraymenuid=myarray.get(myarray.size()-1).toString();
if(!arraymenuid.equalsIgnoreCase(bean.getParentid())){
myarray.remove(myarray.size()-1);%>
</ul></li>
<%
}else {
arrayflag=false;

}

}
%>
<li><a href="<%=bean.getUrl() %>"  target="tmscontent"><%=bean.getMenutext() %></a></li>
<%} else if(bean.getFlag().equalsIgnoreCase("U")){%>
<li><a href="<%=bean.getUrl() %>"  target="tmscontent"><%=bean.getMenutext() %></a></li>
<%} %>

<% parentid=bean.getParentid();lastflag=bean.getFlag();rootid=bean.getRoot();} } %>

</ul>
<br style="clear: left" />
</div>
</body>
</html>
