<html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<head>
<script type="text/javascript" src="script/scripts.js"></script>
<script type="text/javascript" src="script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<% 
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
   	ArrayList<CommonBean> mainArr = (session.getAttribute("mainArr")!=null) ? (ArrayList<CommonBean>) session.getAttribute("mainArr"): new ArrayList<CommonBean>();
   	String dateFrom = StringFormat.nullString((String)session.getAttribute("dateFrom"));
   	String dateTo = StringFormat.nullString((String)session.getAttribute("dateTo"));
   	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
%>
<script type="text/javascript">
function runReport()
 {
 var flag = true;
 var refNo = window.document.frm.refNo;
   if(flag)
    {
   	window.document.frm.method="post";
	window.document.frm.target="_new";
	window.document.frm.action="ReportIncomingDateController";
	window.document.frm.submit();
   }
 }
 
 </script>

</head>
<body >
<form name="frm" id="frm" action="ReportIncomingDateController" method="post">
			<TABLE cellspacing='0' cellpadding='2' height="100%" align="center">
				<TR>
				  <TD valign="top" height="520" width="650">
					<TABLE width="100%">
						<TR>
							<TD align="center" valign="middle" width="96%">
							
							<div id="d2">
							<table width="100%" id="data">
								<tbody>
									<tr>
									<td align="left" height="18" colspan="2">
									<input type="hidden" name="reportNumber" size="15">&nbsp;</td>
									</tr>
									<tr id="tr3" class="treven">
									<td align="right" width="248">Incoming Date<font color="red">*</font> :</td>
									<td align="left" width="388"><input type="text" id="dateFrom" name="dateFrom" maxlength="20" value="<%=sessionBean.getTENURESTARTDATE() %>" size="15">
																 <input type="text" id="dateTo" name="dateTo" maxlength="20" value="<%= serverDate %>" size="15"></td>
									</tr>
							</tbody>
							</table>
							</div>
							</TD>
						</TR>
						<tr>
							<td>
							<table class="formtext" width="100%" border="0"
								cellspacing="0" cellpadding="0" bordercolor="BLACK"
								style="width: 600px;">
								<TR>
									<TD colspan="8" align="center">
									<INPUT class="butts" type="button" name="Submit" value="Submit" onclick="runReport();">
									<INPUT class="butts" type="reset" name="Reset" value="Clear">
								</TD>
								</TR>
								<TR><TD>
								<table width="100%" style="width: 600px;" border="1" cellpadding="0">
								<TR><TD align="center">S.No.</TD><TD align="center">Date</TD><TD align="center">A</TD><TD align="center">B</TD><TD align="center">M</TD><TD align="center">O</TD><TD align="center">S</TD><TD align="center">V</TD></TR>
								<%
									for(int i=0;i<mainArr.size();i++){
									CommonBean beanCommon=(CommonBean) mainArr.get(i);
								%>
								<tr><TD align="center"><%= i+1 %></TD>
								<td align="center"><%= beanCommon.getField1()%></TD><TD align="center"><%= beanCommon.getField2() %></TD><TD align="center"><%= beanCommon.getField3()%></TD>
								<td align="center"><%= beanCommon.getField4()%></TD><TD align="center"><%= beanCommon.getField5() %></TD><TD align="center"><%= beanCommon.getField6()%></TD>
								<TD align="center"><%= beanCommon.getField7()%></TD>
									</tr>
							<%}%>
						</TABLE>
								</TD></TR>
				</TABLE>
							</td>
						</tr>
					</TABLE>
					</TD>
				</TR>
			</TABLE>
</form>
</body>
</html>