<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%><HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<%
MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");


String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
String serverTime = CommonDAO.getSysDate("HH:mm:ss");

String paramSTR1 = request.getParameter("param1") != null ? (String)request.getParameter("param1")+"" :"";
String paramSTR2 = request.getParameter("param2") != null ? (String)request.getParameter("param2")+"" :"";
String hideftr = request.getParameter("hideftr") != null ? (String)request.getParameter("hideftr")+"" :"";
	
//System.out.println(paramSTR1+":::"+paramSTR2);
	
String flag=request.getParameter("flag")!=null?request.getParameter("flag"):"";
if("Y".equalsIgnoreCase(flag)){ %>
 <% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
 <%} else if("td".equalsIgnoreCase(flag)){ %>
 <style media="print">
 	@media print {
@page {
size: A3 landscape;
}
}
 
 </style>

 <LINK href="theme/MasterGreen.css" rel="stylesheet" type="text/css">

 	<style type="text/css">
	#scale table tr  td {
	height: 20px;
	vertical-align: top;
	background-repeat: no-repeat;
	background-position: bottom;
	font-size: 8px;
	font-family:tahoma;
	font-wigth:bold;
	width: 4px;
}

#zoomdiv table td {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 9px;
	white-space: normal;
	font-weight: bold;
	empty-cells: show;
	
}	
	</style>
 

 <%} %>
</HEAD>
<BODY onload="window.print();" style="background-image: none; background-color: white;">

<script>
var x = window.opener.document.getElementById("reportData").cloneNode(true);
document.body.innerHTML+=(x.innerHTML);
var thisDocument = document.getElementsByTagName("INPUT");
for(var i=0;i<thisDocument.length;i++)
{
	if(thisDocument[i].type=='button')
		thisDocument[i].style.display= 'none'; 
}
if(document.getElementById("reportHeader"))
{
	document.getElementById("reportHeader").style.display = 'block';
}

// Hide Filter Row
</script>
<%if(hideftr.equalsIgnoreCase("Y")){%>
<script>
//alert("if");
var y = window.document.getElementById("sorttable");
var trtag = y.getElementsByTagName("TR");
trtag[1].style.display = "none";
</script>
<%}%>


</BODY>
</HTML>
