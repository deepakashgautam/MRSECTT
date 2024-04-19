<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="in.org.cris.mrsectt.dao.BudgetReportDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%><html> 
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
<script type="text/javascript" src="theme/jquery/jquery.tablesorter.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
<%
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
    String btnClick = request.getParameter("btnClick")!=null ? request.getParameter("btnClick") : "";

    String LANG = StringFormat.nullString(request.getParameter("LANG"));
    String REFNO = StringFormat.nullString(request.getParameter("REFNO"));
    String REFNAME = StringFormat.nullString(request.getParameter("REFNAME"));
    String LETTERDATE = StringFormat.nullString(request.getParameter("LETTERDATE"));
    String SUBJECT = StringFormat.nullString(request.getParameter("SUBJECT"));
    String VIPSTATUS = StringFormat.nullString(request.getParameter("VIPSTATUS"));
    String STATE = StringFormat.nullString(request.getParameter("STATE"));

 	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
 	String serverTime = CommonDAO.getSysDate("HH:mm:ss"); 
%>
<script>
function submitPage() {
alert('hii');
		window.opener.document.frmOriginated.submit();

		alert(window.opener.document.getElementById("REFERENCENAME").value); 
	//	alert(parent.document.getElementById("REFERENCENAME").value); 
	//	parent.document.frmOriginated.submit(); 
}
</script>
<style>
/* Syle the search button. Settings of line-height, font-size, text-indent used to hide submit value in IE */
#submit {
	cursor:pointer;
	width:50px;
	height: 21px;
	line-height:0;
	font-size:0;
	text-indent:-999px;
	color: transparent;
	background: url(images/icon_search12.png) no-repeat #4d90fe center;
	border: 1px solid #3079ED;
	-moz-border-radius: 2px;
	-webkit-border-radius: 2px;
}
/* Style the search button hover state */
#submit:hover {
	background: url(images/icon_search12.png) no-repeat center #357AE8;
	border: 1px solid #2F5BB7;
}
</style>
</head>

<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
<form name="frm" action="" method="post">
	<TABLE WIDTH="21%" BORDER=1 CELLPADDING=0 CELLSPACING=0 align="center">
	<tr><td align="center"><br /><input type="button" name="withMail" id="withMail" value="Save with Mail" style="height: 23px;" onclick="submitPage();"/>
						   <input type="button" name="withOutMail" id="withOutMail" value="Save without Mail" style="height: 23px;"/><br /></td></tr>
	<TR><TD VALIGN="TOP" ALIGN="CENTER">
<% if(LANG.equalsIgnoreCase("1")) { %>
							 <TABLE WIDTH="20%" BORDER=0 CELLPADDING=0 CELLSPACING=0 style="font-size: 14; font-family: TAHOMA">
							 <TR><TD ALIGN="CENTER" COLSPAN=4><IMG SRC="images/letterHeader.jpg" WIDTH=726 HEIGHT=165></TD></TR>
							 <TR><TD ALIGN="CENTER" COLSPAN=4><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD ALIGN="CENTER" COLSPAN=4><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD align="LEFT" width="623"><%= REFNO %></TD><TD ALIGN="RIGHT" > <%= serverDate %></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD align="LEFT" width="623"></TD><TD ALIGN="RIGHT" ></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD align="LEFT" width="623"></TD><TD ALIGN="RIGHT" ></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD align="LEFT" width="623"></TD><TD ALIGN="RIGHT" ></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT">Dear <%= REFNAME %></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I am in receipt of your letter dated <%= LETTERDATE %> regarding <%= SUBJECT %></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I am having the matter looked into.</TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;With regards,</TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right">Yours sincerely,</TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right">( Mukul Roy )</TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"><%= REFNAME %></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"><%= VIPSTATUS %></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"><%= STATE %></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD COLSPAN=4><IMG SRC="images/letterHead_05.gif"></TD></TR>
							 <TR><TD COLSPAN=4><IMG SRC="images/letterHead_05.gif"></TD></TR>
							 </TD></TR></TABLE>
<%} %>
	</TD></TR></TABLE>
  </form> 
</body>
</html>