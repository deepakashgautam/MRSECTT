<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.dbConnection.DBConnection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%><html>
<head>
<%
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
	CommonBean bean;
	ArrayList detailarr = request.getAttribute("detailarr") != null ? (ArrayList) request.getAttribute("detailarr"): null;
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	String serverTime = CommonDAO.getSysDate("HH:mm:ss");
	
	String[] subjectcode = request.getAttribute("subjectcode") != null ? (String[])request.getAttribute("subjectcode"):null;
	String subCode="Subject : ";
	if(subjectcode.length>0){
		for(int i=0;i<subjectcode.length;i++){
		 subCode = subCode+(subjectcode[i].equalsIgnoreCase("")?"ALL":subjectcode[i])+","; 
		
		}
	}
	
	
			
	
			
	String FDATE = request.getAttribute("datefrom") != null ? (String)request.getAttribute("datefrom")+" - " :"";
	String TDATE = request.getAttribute("dateto") != null ? (String)request.getAttribute("dateto") :"";
	
	String REFCLASS = request.getAttribute("refclass") != null ? (String)request.getAttribute("refclass"):"";
		   REFCLASS = REFCLASS.length() > 0 ? "Class : "+REFCLASS+", " :"";
	
	 
		   
	
	

	
	
	String paramSTR1 = REFCLASS+subCode;
	String paramSTR2 = FDATE+TDATE;
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Diary Report</title>
<script type="text/javascript">
function printMsg() {
	alert('Print in LANDSCAPE orientation.');
}
</script>
</head>
<body class="landscape" onload="printMsg();">
<%
	String msg = request.getParameter("var");
	if(msg != null){	
%>
<table align="center">
<tr><td><font face="Tahoma" color="red" size="5"><c:out value='<%= msg %>'/></font></td></tr>
</table>
<%}else {%>
<table width="100%" border="0" cellspacing="0" align="center">
	<tbody>
		<tr><td align="center"><b><font size="3"><b><c:out value='<%= sessionBean.getTENUREOFFICENAME()%>'/></b></font>
			<br><font size="1"><c:out value='<%= sessionBean.getTENUREOFFICEADDRESS()%>'/></font></td>
		</tr>
		<tr>
		<td align="right"></td>
		</tr>
		<tr>
			<td align="center"><img width="20" height="20" src="images/printer.png" onclick="window.print()" title="Print"></img></td>
		</tr>
	</tbody>
</table>	
<table width="100%">
	<TR>
		<TD>
			<font size="2" face="Tahoma"><b>Reference(s) received in Sectt., related to  <c:out value='<%= paramSTR1 %>' /></b></font><br>
			<font size="2" face="Tahoma"><b>during <c:out value='<%=paramSTR2 %>'/></b></font>
		</TD>
		<TD align="right">
			<font size="2" face="Tahoma"><c:out value='<%=serverDate %>'/></font><br>
			<font size="2" face="Tahoma"><c:out value='<%=serverTime %>'/></font>
		</TD>
	</TR>
</table>
<table width="100%" border="0" cellspacing="0" bordercolor="black">
		<thead style="display: table-header-group;">
		<tr valign="top">
		    <td align="center" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>S.No.</b></td>
			<td align="center" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>Ref. No<br>Incoming Dt.</b></td>
			<td align="center" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>Ref. Dated</b>
			</td>
			<td align="left" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10" width="150"><b>Received From</b></td>
			<td align="center" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>Sub.</b></td>
			<td align="justify" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10" width="180"><b>Subject</b></td>
			<td align="left" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>Forward To<br>Forward On</b>
			</td>
			<td align="left" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>Remarks<br>Signed By<br>Signed On</b>
			</td>
			<td align="left" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>Ack. By<br>Ack. On</b>
			</td>
			<td align="left" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>File Mark To<br>Regn. No.<br>Regn. Dt.</b></td>
			<td align="left" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>File No.<br>Status<br></b></td>
			<td align="left" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><b>Return To<br>Return On</b><br>
			<b>Reply Type</b>
			</td>
		</tr>
		</thead>
		<%
			for(int i=0;i<detailarr.size();i++){
				bean =(CommonBean) detailarr.get(i);
		%>
		<tr>
		   	<td align="center" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=(i+1)%>'/>.&nbsp;</td>
		   	<td align="center" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField1()%>'/><br><c:out value='<%=bean.getField3()%>'/></td>
		   	<td align="center" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField5()%>'/>&nbsp;</td>
		   	<td align="left" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField4()%>'/><br><c:out value='<%=bean.getField6()%>'/><br><c:out value='<%=bean.getField7()%>'/></td>
		   	<td align="center" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField16() + (bean.getField30().equalsIgnoreCase("Y")? " - Budget":"")%>'/>&nbsp;</td>
		   	<td align="justify" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField17()%>'/>&nbsp;</td>
		   	<td align="left" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField18()%>'/><br><c:out value='<%=bean.getField19()%>'/><br>Target Dt :<br><c:out value='<%=bean.getField20()%>'/></td>
		   	<td align="left" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField14()%>'/><br><c:out value='<%=bean.getField12()%>'/><br><c:out value='<%=bean.getField13()%>'/></td>
		   	<td align="left" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField9()%>'/><br><c:out value='<%=bean.getField8()%>'/></td>
			<td align="left" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField29()%>'/><br><c:out value='<%=bean.getField25()%>'/><br><c:out value='<%=bean.getField26()%>'/></td>
			<td align="left" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField21()%>'/><br><c:out value='<%=bean.getField22()%>'/><br><c:out value='<%=bean.getField23()%>'/></td>
			<td align="left" style=" font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField27()%>'/><br><c:out value='<%=bean.getField28()%>'/><br><c:out value='<%=bean.getField24()%>'/>
		</td>
	</tr>
	<tr><td style="border-bottom: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10">&nbsp;</td>
	<td colspan="15" style="border-bottom: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10"><c:out value='<%=bean.getField31()%>'/>&nbsp;</td></tr>
		<%} %>
</table>
<%} %>
</body>
</html>