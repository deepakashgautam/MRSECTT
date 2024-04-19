
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%><html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<head>
<script type="text/javascript" src="script/scripts.js"></script>
<script type="text/javascript" src="script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<%
	CommonBean bean1;
	CommonBean bean2;
	CommonBean bean3;
	CommonBean bean4;
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
   	java.util.ArrayList pendWithDt = request.getAttribute("pendWithDt") != null ? (java.util.ArrayList) request.getAttribute("pendWithDt"): null;
   	java.util.ArrayList underProcess = request.getAttribute("underProcess") != null ? (java.util.ArrayList) request.getAttribute("underProcess"): null;
   	java.util.ArrayList additionalInfo = request.getAttribute("additionalInfo") != null ? (java.util.ArrayList) request.getAttribute("additionalInfo"): null;
   	java.util.ArrayList total = request.getAttribute("total") != null ? (java.util.ArrayList) request.getAttribute("total"): null;
   	String FDATE = StringFormat.nullString((String)request.getAttribute("datefrom"));
   	String TDATE = StringFormat.nullString((String)request.getAttribute("dateto"));
   	String CLASS = StringFormat.nullString((String)request.getAttribute("refclass"));
   	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	String serverTime = CommonDAO.getSysDate("HH:mm:ss");
%>
</head>
<body >
<form name="frm" id="frm" action="" method="post">
<table width="100%" border="0" cellspacing="0" align="center">
	<tbody>
		<tr><td align="center"><font size="3"><b><c:out value='<%= sessionBean.getTENUREOFFICENAME()%>' /></b></font>
			<br><font size="1"><c:out value='<%= sessionBean.getTENUREOFFICEADDRESS()%>'/></font></td>
		</tr>
		<tr>
		<td align="right"><font size="2">
		</font></td>
		</tr>
		<tr>
			<td align="center"><img width="20" height="20" src="images/printer.png" onclick="window.print()" title="Print"></img></td>
		</tr>
	</tbody>
</table>
					<TABLE width="80%" border="0" align="center">
						<TR>
							<TD align="center" valign="middle" width="96%">
							
							<div id="d2">
							<table width="100%" id="data">
								<tbody>
									<tr>
									<th align="center" height="18" colspan="4">
									<input type="hidden" name="reportNumber" size="15">Statistical - Incoming Date<FONT size="3"></th>
									</tr>
						<tr>
							<th align="left" height="18" colspan="2" style="font-size: 11px;" width="912">Position of Ref.(s) received during <c:out value='<%=FDATE %>'/>-  <c:out value='<%=TDATE %>'/><br>Ref. Class : <c:out value='<%=CLASS %>'/>
							</th>
							<th align="right" height="18" colspan="2" style="font-size: 11px;" width="104"><c:out value='<%=serverDate %>'/><br>	<c:out value='<%=serverTime %>'/></th>
						</tr>
					</tbody>
							</table>
							</div>
							</TD>
						</TR>
						<tr>
							<td>
							<table width="100%" >
								<TR><TD>
								<table width="100%" cellspacing="0" cellpadding="0" >
							<thead style="display: table-header-group;">
								<TR>
								<TD align="center" valign="top" style="border-top: solid; border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><b>Sr.No</b>.</TD>
								<TD align="center" valign="top" style="border-top: solid; border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><b>Date</b></TD>
								<TD align="center" valign="top" style="border-top: solid; border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11">&nbsp;&nbsp;</td>
								<td align="center" valign="top" style="border-top: solid; border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><b>Day</b></td>
								<TD align="center" valign="top" style="border-top: solid; border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><b>Total<br>Case(s)</b></TD>
								<TD align="center" valign="top" style="border-top: solid; border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><b>Cleared<br></b></TD>
								<TD align="center" valign="top" style="border-top: solid; border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><b>Under Process<br>in Min. Sectt.</b></TD>
								<TD align="center" valign="top" style="border-top: solid; border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><b>Additional Info.<br>Required</b></TD>
								<TD align="center" valign="top" style="border-top: solid; border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><b>Pending with<br>Dte.</b></TD>
							</TR>
							</thead>
								<%
									for(int i=0;i<pendWithDt.size();i++){
									bean1 =(CommonBean) pendWithDt.get(i);
									bean2 =(CommonBean) underProcess.get(i);
									bean3 =(CommonBean) additionalInfo.get(i);
									bean4 =(CommonBean) total.get(i);
									int clearCase = Integer.parseInt(bean4.getField3())-(Integer.parseInt(bean1.getField3()) + Integer.parseInt(bean2.getField3()) + Integer.parseInt(bean3.getField3()));
								%>
								<tr><TD align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><c:out value='<%= (i < pendWithDt.size()-1? (i+1):".") %>'/></TD>
								<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><c:out value='<%= bean1.getField1().length()>0? bean1.getField1() : "-" %>'/></TD>
								<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11">&nbsp;&nbsp;</td>
								<td align="left" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><c:out value='<%= bean3.getField2()%>'/></td>
								<TD align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><c:out value='<%= (bean4.getField3().equalsIgnoreCase("0")? "-":bean4.getField3()) %>'/></TD>
								<TD align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><c:out value='<%= (clearCase != 0? clearCase:"-") %>'/></TD>
								<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><c:out value='<%= (bean2.getField3().equalsIgnoreCase("0")? "-":bean2.getField3()) %>'/></TD>
								<TD align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><c:out value='<%= (bean3.getField3().equalsIgnoreCase("0")? "-":bean3.getField3()) %>'/></TD>
								<TD align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-size: 11"><c:out value='<%= (bean1.getField3().equalsIgnoreCase("0")? "-":bean1.getField3()) %>'/></TD>
								</tr>
							<%}%>
						</TABLE>
								</TD></TR>
				</TABLE>
							</td>
						</tr>
					</TABLE>
					
</form>
</body>
</html>