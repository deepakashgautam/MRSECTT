<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.dbConnection.DBConnection"%>
<%@page import="java.sql.SQLException"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="java.sql.ResultSet"%><html>
<head>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<%
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
	CommonBean bean;
	CommonBean bean2;
	ArrayList<CommonBean> bnPeonBook = request.getAttribute("bnPeonBook") != null ? (ArrayList) request.getAttribute("bnPeonBook"): null;
	ArrayList<CommonBean> bnPeonBookTotal = request.getAttribute("bnPeonBookTotal") != null ? (ArrayList) request.getAttribute("bnPeonBookTotal"): null;
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	String serverTime = CommonDAO.getSysDate("HH:mm:ss");
	String FDATE = (String)request.getAttribute("datefrom");
	String TDATE = (String)request.getAttribute("dateto");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Diary Report</title>
</head>
<body class="landscape" >
<%
	String msg = request.getParameter("var");
	if(msg != null){	
%>
<table align="center">
<tr><td><font face="Tahoma" color="red" size="5"><c:out value='<%= msg %>' /></font></td></tr>
</table>
<%}else {%>
<table width="80%" border="0" cellspacing="0" align="center">
	<tbody>
		<tr><td align="center" colspan="2"><font size="3"><b><c:out value='<%= sessionBean.getTENUREOFFICENAME() %>'/></b></font>
			<br><b><font size="1"><c:out value='<%= sessionBean.getTENUREOFFICEADDRESS() %>'/></font></b>
		</td></tr>
		<tr>
			<td align="center" colspan="2"><img width="30" height="30" src="images/printer_new.png" onclick="window.print()" title="Print"></img>
		</td></tr>
		<tr>
		<td align="left" style="font-family: Tahoma; font-size: 11" >
			<b>Date: </b> <c:out value='<%=FDATE+" - "+TDATE %>'/>
		</td>
		<td align="right" style="font-family: Tahoma; font-size: 11" ><c:out value='<%=serverDate %>'/><br><c:out value='<%=serverTime %>'/></td>
		</tr>
		<tr>
			<td align="center" style="font-family: Tahoma; font-size: 11" colspan="2">Kindly arrange to issue the following Letter(s).</td></tr>
		<tr>
			<td align="center" ><br>
			</td><td></td>
		</tr>
	</tbody>
</table>
<table width="80%" border="0" cellspacing="0" cellpadding="0" style="font-family: Tahoma; font-size: 10px;" align="center">
	<tbody>
		<%
		if(bnPeonBook.size()>0){
			for(int i=0;i<bnPeonBook.size();){
//				bean =(CommonBean) bnPeonBook.get(i);
		%>
		<tr>
		   	<td style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><c:out value='<%= i<bnPeonBook.size()?((1+i)+".  "+bnPeonBook.get(i++).getField2()):"" %>' /></td>
			<td style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><c:out value='<%= i<bnPeonBook.size()?((1+i)+".  "+bnPeonBook.get(i++).getField2()):"" %>' /></td>
			<td style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><c:out value='<%= i<bnPeonBook.size()?((1+i)+".  "+bnPeonBook.get(i++).getField2()):"" %>' /></td>
		</tr>
		<%} }%>
		<tr>
			<td colspan="3" align="center"><br></td>
		</tr>
		<tr>
			<td colspan="3" align="center" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10">Total&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='<%= bnPeonBookTotal.get(0).getField1() %>'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Letter(s)</td>
			
		</tr>
	</tbody>
</table>
<p><br></p>
<p><br></p>
<p><br></p>
<p><br></p>
<p><br></p>
<table width="60%" border="0" cellspacing="0" cellpadding="0" style="font-family: Tahoma; font-size: 10px;" align="center">
	<tbody>
		<tr>
		   	<td align="right">For S.O./M.R. Sectt.</td>
		</tr>
		<tr>
		   	<td align="left"><BR><BR><BR>Issue(D)</td>
		</tr>
	</tbody>
</table>
<%} %>
</body>
</html>