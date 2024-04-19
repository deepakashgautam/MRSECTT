<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>

<SCRIPT src='${pageContext.request.contextPath}/script/stm31.js'></SCRIPT>
<link type="text/css" href="${pageContext.request.contextPath}/theme/gmailStyleMenu/gmailStyleMenu.css" rel="stylesheet" />
<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
   
	String loginid = session.getAttribute("loginid")!=null? session.getAttribute("loginid").toString(): "";
%>
	<title>MRSectt</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<script>
	document.onclick=stcls;
	function resizeIframe(){
		window.document.getElementById("tmscontent").style.height= (screen.availHeight-250)+"px";
	}
	</script>

</head>
	
<body onload="resizeIframe();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<script language="JavaScript1.2">
<!--
top.window.moveTo(0,0);
if (document.all) {
top.window.resizeTo(screen.availWidth,screen.availHeight);
}
else if (document.layers||document.getElementById) {
if (top.window.outerHeight<screen.availHeight||top.window.outerWidth<screen.availWidth){
top.window.outerHeight = screen.availHeight;
top.window.outerWidth = screen.availWidth;
}
}
//-->
</script>
		<%-- <TABLE WIDTH="100%" cellspacing="0" style="margin: 0px;">
			<TR > <% if (loginid.equalsIgnoreCase("neeraj")) {%>
				<TD ><jsp:include page="headerAdmin.jsp"></jsp:include></TD>
				<%} else { %>
				<TD ><jsp:include page="header.jsp"></jsp:include></TD>
				<%} %>
			</TR>
		</TABLE> --%>
		<TABLE WIDTH="100%" cellspacing="0" style="margin: 0px;">
			<TR >
				<TD ><jsp:include page="header.jsp"></jsp:include></TD>
			</TR>
		</TABLE>
<!--main Content Area Starts-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
				<td width="100%">
					<iframe name="tmscontent" id="tmscontent" src="Home.jsp" style="width: 100%; height: 100%" frameborder="0">
					</iframe>
				</td>
			</tr>
		</table>
		
 
<!--main Content Area Ends-->
</body>
</html>