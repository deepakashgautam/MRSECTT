<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%><html>
<head>
<%
	CommonBean bean;
	CommonBean beanFileMarking;
	CommonBean beanFileIntMarking;
	CommonBean beanFileRef;
	java.util.ArrayList TrnFileHdr = request.getAttribute("TrnFileHdr") != null ? (java.util.ArrayList) request.getAttribute("TrnFileHdr"): null;
	java.util.ArrayList TrnFileRef = request.getAttribute("TrnFileRef") != null ? (java.util.ArrayList) request.getAttribute("TrnFileRef"): null;
	java.util.ArrayList TrnFileMarking = request.getAttribute("TrnFileMarking") != null ? (java.util.ArrayList) request.getAttribute("TrnFileMarking"): null;
	java.util.ArrayList TrnFileIntMarking = request.getAttribute("TrnFileIntMarking") != null ? (java.util.ArrayList) request.getAttribute("TrnFileIntMarking"): null;
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	String serverTime = CommonDAO.getSysDate("HH:mm:ss");
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Diary Report</title>
<script type="text/javascript">
function printMsg() {
	alert('Print in LANDSCAPE orientation.');
}
</script>
</head>
<body onload="printMsg();">
<table width="100%" border="0" cellspacing="0" align="center">
		<tr>
			<td colspan="12" align="center"><font size="3"><b><c:out value='<%= sessionBean.getTENUREOFFICENAME() %>'/></b></font>
			<br><font size="1"><b><c:out value='<%= sessionBean.getTENUREOFFICEADDRESS() %>'/></b></font></td>
		</tr>
		<tr>
			<td align="right" colspan="12" style="font-family: Tahoma; font-size: 11"><c:out value='<%=serverDate %>'/><br><c:out value='<%=serverTime %>'/></td>
		</tr>
		<tr>
			<td align="center" colspan="12"><font face="Verdana" size="-1"><img
				width="20" height="20" src="images/printer.png"
				onclick="window.print()" title="Print"></font></td>
		</tr>
		<tr><td colspan="12"><hr></td></tr>
</table>
	<%
			for(int i=0;i<TrnFileHdr.size();i++){
				bean =(CommonBean) TrnFileHdr.get(i);
	%>
		<table border="0" width="80%" style="font-family: Tahoma; font-size: 10">
			<tbody>
				<tr>
					<td ><b>Reg. No.</b>&nbsp;&nbsp;:&nbsp;&nbsp;<c:out value='<%=bean.getField2()%>'/></td>
					<td ><b>Reg.
		Date&nbsp;&nbsp;</b>:&nbsp;&nbsp;<c:out value='<%=bean.getField3()%>'/></td>
					<td ><b>File
		Type&nbsp;&nbsp;</b>:&nbsp;&nbsp;<c:out value='<%=bean.getField4()%>'/></td>
				</tr>
				<tr>
					<td ><b>File
		No.&nbsp;&nbsp;</b>:&nbsp;&nbsp;<c:out value='<%=bean.getField6()%>'/></td>
					<td ><b>Originated Branch&nbsp;&nbsp;</b><span style="COLOR: red"><span
			style="COLOR: red"><b></b></span></span>:&nbsp;&nbsp;<c:out value='<%=bean.getField5()%>'/></td>
					<td ><b>eOffice No.&nbsp;&nbsp;</b><span style="COLOR: red"><span
			style="COLOR: red"><b></b></span></span>: &nbsp;<c:out value='<%=bean.getField23()%>'/></td>
				</tr>
		<tr>
			<td colspan="2" ><b>Link File No</B>(s). : <c:out value='<%=bean.getField8()%>'/>&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='<%=bean.getField9()%>'/>&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='<%=bean.getField10()%>'/>&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='<%=bean.getField11()%>'/></td>
		
		</tr>
	</tbody>
		</table>
	<hr>
	<b style="font-family: Tahoma; font-size: 12">PUC</b>
	<table width="100%" border="0" style="font-family: Tahoma; font-size: 10">
			<tr>
				<td align="left" width="38" ><b>S. No</b></td>
				<td align="left" width="69" ><b>Ref No</b></td>
				<td align="left" width="214" ><b>Ref From</b></td>
				<td align="left" width="77" ><b>Sub.</b></td>
				<td align="left" width="503" ><b>Subject</b></td>
				<td align="left" width="369" ><b>Status</b></td>
			</tr>

	<%
			for(int k=0;k<TrnFileRef.size();k++){
				beanFileRef =(CommonBean) TrnFileRef.get(k);
	%>
			<tr>
				<td align="left" width="38" ><c:out value='<%= k+1 %>'/></td>
				<td align="left" width="69" ><c:out value='<%=beanFileRef.getField3()%>'/></td>
				<td align="left" width="214" ><c:out value='<%=beanFileRef.getField4()%>'/>,
				<c:out value='<%=beanFileRef.getField8()%>'/>, <c:out value='<%=beanFileRef.getField9()%>'/></td>
				<td align="left" width="77" ><c:out value='<%=beanFileRef.getField5()%>'/></td>
				<td align="left" width="503" ><c:out value='<%=beanFileRef.getField6()%>'/></td>
				<td align="left" width="369" ><c:out value='<%=beanFileRef.getField7()%>'/></td>
			</tr>
	<%} %>
		</table>
	<hr>
		<table border="0" width="100%" style="font-family: Tahoma; font-size: 10">
			<tbody>
				<tr>
					<td ><b>Subject<span
			style="COLOR: red"></span></b>&nbsp;&nbsp;:&nbsp;&nbsp;<c:out value='<%=bean.getField12()%>'/></td>
					<td ><b>Received From</b>&nbsp;&nbsp;:&nbsp;&nbsp;<c:out value='<%=bean.getField13()%>'/></td>
				</tr>
			</tbody>
		</table>
	<hr>
	<b style="font-family: Tahoma; font-size: 12">Internal Marking</b> <br>
	<table border="0" width="80%" style="font-family: Tahoma; font-size: 10">
		<tbody>
	<%
			for(int k=0;k<TrnFileIntMarking.size();k++){
				beanFileIntMarking =(CommonBean) TrnFileIntMarking.get(k);
	%>
			<tr>
				<td ><b>Mark To</b>&nbsp;&nbsp;:&nbsp;&nbsp;<c:out value='<%=beanFileIntMarking.getField2()%>'/></td>
				<td ><b>Mark On&nbsp;&nbsp;</b>:&nbsp;&nbsp;<c:out value='<%=beanFileIntMarking.getField3()%>'/></td>
				<td ><b>Return On</b>&nbsp;&nbsp;:&nbsp;&nbsp;<c:out value='<%=beanFileIntMarking.getField4()%>'/></td>
			</tr>
	<%}%>
		</tbody>
	</table>
	<hr>
		<table border="0" width="80%" style="font-family: Tahoma; font-size: 10">
			<tbody>
				<tr>
					<td ><b>File Status 1</b>&nbsp;&nbsp;:&nbsp;&nbsp;<c:out value='<%=bean.getField16()%>'/></td>
					<td ><b>File Status 2</b>&nbsp;&nbsp;:&nbsp;&nbsp;<c:out value='<%=bean.getField17()%>'/></td>
					<td ><b>No of letters :</b> <c:out value='<%=bean.getField21()%>'/></td>
					<td ><b>Full Status&nbsp;&nbsp;</b>:&nbsp;&nbsp;<c:out value='<%=bean.getField18()%>'/></td>
				</tr>
			</tbody>
		</table>
	<hr>
<table border="0" width="60%" style="font-family: Tahoma; font-size: 10">
			<tbody>
				<tr>
		<%
			for(int j=0;j<TrnFileMarking.size();j++){
				beanFileMarking =(CommonBean) TrnFileMarking.get(j);
		%>
			<td width="204" ><b>Mark To</b><br>(Downward)</td>
			<td width="174" ><b>Mark On<br></b>(Downward)</td>
			<td width="368" ><b>Remarks<br></b>(Downward)</td>
		</tr>
		<tr>
			<td width="204" ><c:out value='<%= beanFileMarking.getField2()%>'/></td>
			<td width="174" ><c:out value='<%=beanFileMarking.getField3()%>'/></td>
			<td width="368" ><c:out value='<%=beanFileMarking.getField4()%>'/></td>
		</tr>
		<%} %>
	</tbody>
		</table><hr>
<table border="0" width="50%" style="font-family: Tahoma; font-size: 10">
			<tbody>
				<tr>
		<%
			for(int j=0;j<TrnFileMarking.size();j++){
				beanFileMarking =(CommonBean) TrnFileMarking.get(j);
		%><td width="59" ><b>Reply</b></td>
			<td width="296" ><b>Reason</b></td>
		</tr>
		<tr><td width="59" ><c:out value='<%=bean.getField19()%>'/></td>
			<td width="296" ><c:out value='<%=bean.getField20()%>'/></td>
		</tr>
		<%} %>
	</tbody>
		</table>
	<table style="font-family: Tahoma; font-size: 10">
	<tr>
	<td>
	<c:out value='<%=bean.getField22() %>'/>
	</td>
	</tr>
	</table>
	<%} %>
</body>
</html>