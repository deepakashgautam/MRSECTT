<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.dbConnection.DBConnection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%><html>
<head>
<!--   <% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<style type="text/css" media="print">
	@page port {size: portrait;}
	@page land {size: landscape;}
	.portrait {page: port;}
	.landscape {page: land;}
</style>
<%
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
	CommonBean bean;
	CommonBean bean2;
	ArrayList beanSerialNoFromTo = request.getAttribute("beanSerialNoFromTo") != null ? (ArrayList) request.getAttribute("beanSerialNoFromTo"): null;
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	String serverTime = CommonDAO.getSysDate("HH:mm:ss");
	
	String FREGNO = (String)request.getAttribute("regnofrom");
	String TREGNO = (String)request.getAttribute("regnoto");
	String FDATE = (String)request.getAttribute("datefrom");
	String TDATE = (String)request.getAttribute("dateto");
//	String fileType = (String)request.getAttribute("fileType");
//	String iMarkingTo = (String)request.getAttribute("iMarkingTo");
//	String fStatus1 = (String)request.getAttribute("fStatus1");
//	String fStatus2 = (String)request.getAttribute("fStatus2");
	
//	String markTo = (String)CommonDAO.getRoleName(iMarkingTo);
//	fStatus1 = (String)CommonDAO.getFileStatus1(fStatus1);
//	fStatus2 = (String)CommonDAO.getFileStatus2(fStatus2);
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Diary Report</title>
<script type="text/javascript">
function printMsg() {
	alert('Print in LANDSCAPE orientation.');
}
</script>
</head>
<body onload="printMsg();" >
<%
	String msg = request.getParameter("var");
	if(msg != null){	
%>
<table align="center">
<tr><td><font face="Tahoma" color="red" size="5"><c:out value='<%= msg %>'/></font></td></tr>
</table>
<%}else {%>
<table width="100%" border="0" cellspacing="0" align="center" style="font-family: Tahoma; font-size: 10">
		<tr><td align="center" colspan="2"><font size="3"><b><c:out value='<%= sessionBean.getTENUREOFFICENAME() %>'/></b></font>
			<br><font size="1"><b><c:out value='<%= sessionBean.getTENUREOFFICEADDRESS() %>'/></b></font></td>
		</tr>
		<tr>
			<td align="center" colspan="2"><img width="20" height="20" src="images/printer.png" onclick="window.print()" title="Print"></img></td>
		</tr>
		<tr>
		<%-- <td align="left">
			<b>File(s) received in Sectt., related to <%= markTo%>, <%= fStatus1%>, <%= fStatus2%><%= fileType.length()>0? fileType.equalsIgnoreCase("A")? ", Approval": fileType.equalsIgnoreCase("D")? ", Draft": fileType.equalsIgnoreCase("P")? ", Position": fileType.equalsIgnoreCase("C")? ", Conf.":"" : "" %></b><br>
			<b>during</b> <%=FDATE+" - "+TDATE %>
		</td> --%>
		<td align="left">
			<b>File(s) received in Sectt., related to Reg. No. </b> <c:out value='<%= FREGNO+" TO "+TREGNO%>'/><br>
			<b>during</b> <c:out value='<%=FDATE+" - "+TDATE %>'/>
		</td>
		<td align="right">
			<c:out value='<%=serverDate %>'/><br><c:out value='<%=serverTime %>'/>
		</td>
		</tr>
</table>
<table width="100%" cellspacing="0" cellpadding="0" style="font-family: Tahoma; font-size: 10px;">
	<thead style="display: table-header-group;">
		<tr valign="top">
		    <td align="center" rowspan="2" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">S.No.</td>
			<td align="center" rowspan="2" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">Reg. No<br>Reg. Dt.<br>eOffice No.</td>
			<td align="center" rowspan="2" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">File Type</td>
			<td align="center" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;" rowspan="2">File No.<br>PUC</td>
			<td align="center" width="80" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;" rowspan="2">Branch<br>Final Marking</td>
			<td align="center" width="80" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;" rowspan="2">Received From</td>
			<td align="center" width="243" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;" rowspan="2">Subject</td>
			<td align="center" colspan="3" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">Internal</td>
			<td align="center" rowspan="2" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">File Status 1<br>File Status 2</td>
			<td align="center" rowspan="2" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">Full Status</td>
			<td align="center" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">Downward</td>
			<td align="center" rowspan="2" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">Remarks</td>
			<td align="center" rowspan="2" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">Reply<br>Type</td>
			<td align="center" rowspan="2" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">Reason</td>
			<!-- <td align="center" rowspan="2" style="border-bottom: dotted; border-top: dotted; border-width: 1px; border-color: gray;">e-Office No</td> -->
		</tr>
		<tr valign="top">
			<td align="center" style="border-bottom: dotted; border-right: dotted; border-width: 1px; border-color: gray;">Marking To</td>
			<td align="center" style="border-bottom: dotted; border-right: dotted; border-width: 1px; border-color: gray;">Marking On</td>
			<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;">Return On</td>
			<td align="center" valign="top" style="border-bottom: dotted; border-width: 1px; border-color: gray;">Mark To<br>
			Mark On</td>
		</tr>
	</thead>
		<%
			for(int i=0;i<beanSerialNoFromTo.size();i++){
				bean =(CommonBean) beanSerialNoFromTo.get(i);
		%>
		<tr valign="top">
		   <td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=(i+1)%>'/></td>
		<% if(bean.getField20().equalsIgnoreCase("C")){ %>
		   <td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><font color="red"><c:out value='<%=bean.getField2()%>'/>*</font><br><c:out value='<%=bean.getField3()%>'/>&nbsp;<br><c:out value='<%=bean.getField20()%>'/></td>
		<%}else{ %>
		   <td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField2()%>'/><br><c:out value='<%=bean.getField3()%>'/>&nbsp;<br><c:out value='<%=bean.getField20()%>'/></td>
		<%} %>
		   <td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField4()%>'/>&nbsp;</td>
		   <td align="left" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField5()%>'/>
		   <%
		   	String qryPUC = "SELECT (SELECT REFNO||' ~ '||TO_CHAR(INCOMINGDATE,'DD/MM/YY') FROM TRNREFERENCE WHERE REFID = A.REFID) REFNO FROM TRNFILEREF A WHERE FMID = "+bean.getField1()+"";
		   	ArrayList<CommonBean> arrPUC = (new CommonDAO()).getSQLResult(qryPUC, 1);

		   	for(int j=0;j<arrPUC.size();j++){
				bean2 =(CommonBean) arrPUC.get(j);
		   %>
		   <br>	<nobr><c:out value='<%=bean2.getField1()%>'/>&nbsp;</nobr><%}%>&nbsp;</td>
		   <td align="left" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField6()%>'/><br><c:out value='<%=bean.getField7()%>'/>&nbsp;</td>
		<td align="left" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField9()%>'/>&nbsp;</td>
		<td align="justify" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField8()%>'/>&nbsp;</td>
		<%
			String queryIntMark = " SELECT DISTINCT (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=B.MARKINGTO) MARKINGTO,TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') MARKINGDATE,"+
								   " TO_CHAR(B.CHANGEDATE,'DD/MM/YYYY') CHANGEDATE, MARKINGSEQUENCE"+
								   " FROM TRNFILEHDR A, trnfileintmarking B"+
								   " WHERE A.FMID = B.FMID(+) AND A.FMID = "+bean.getField1()+""+
								   " AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+sessionBean.getLOGINASROLEID()+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FDATE+"','DD/MM/YYYY') AND TO_DATE('"+TDATE+"','DD/MM/YYYY')"+
								   " ORDER BY MARKINGSEQUENCE";
	//		System.out.println("sunnnnnnnnnnnnnnn ---------- : "+ queryIntMark);
			ArrayList<CommonBean> arrIntMark = (new CommonDAO()).getSQLResult(queryIntMark, 4);
		%>
		<td align="left" valign="top"  style="border-bottom: dotted; border-width: 1px; border-color: gray;">
			<%
				for(int j=0;j<arrIntMark.size();j++){
				bean2 =(CommonBean) arrIntMark.get(j);
			%>
				&nbsp;&nbsp;<nobr><c:out value='<%=bean2.getField1()%>'/></nobr><BR>
			<%}%>&nbsp;
		</td>
		<td valign="top" style="border-bottom: dotted; border-width: 1px; border-color: gray;">
			<%
				for(int j=0;j<arrIntMark.size();j++){
				bean2 =(CommonBean) arrIntMark.get(j);
			%>
				<c:out value='<%=bean2.getField2()%>'/>&nbsp;<BR>
			<%}%>&nbsp;
		</td>
		<td valign="top" style="border-bottom: dotted; border-width: 1px; border-color: gray;">
			<%
				for(int j=0;j<arrIntMark.size();j++){
				bean2 =(CommonBean) arrIntMark.get(j);
			%>
				&nbsp;<c:out value='<%= bean2.getField3()%>'/><BR>
			<%}%>&nbsp;
		</td>
		<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><nobr><c:out value='<%=bean.getField10()%>'/></nobr><br><nobr><c:out value='<%=bean.getField11()%>'/></nobr>&nbsp;</td>
		<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField12()%>'/>&nbsp;</td>
		<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField13()%>'/><br><c:out value='<%=bean.getField14()%>'/>&nbsp;</td>
		<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField15()%>'/>&nbsp;</td>
		<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField16()%>'/>&nbsp;</td>
		<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField17()%>'/>&nbsp;</td>
		<td align="center" style="border-bottom: dotted; border-width: 1px; border-color: gray;" valign="top"><c:out value='<%=bean.getField22()%>'/>&nbsp;</td>
		
	</tr>
		<tr><td style="border-bottom: dotted; border-width: 1px; border-color: gray;">&nbsp;</td>
	<td colspan="15" style="border-bottom: dotted; border-width: 1px; border-color: gray;"><c:out value='<%=bean.getField21()%>'/>&nbsp;</td></tr>
		<%} %>
</table>
<%} %>
</body>
</html>