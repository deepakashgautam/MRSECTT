<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->

<html>
<head>
<style type="text/css">
.sample {
	font-family: sans-serif;
	font-style: normal;
	font-variant: normal;
	font-weight: normal;
	font-size: 10px;
	line-height: 100%;
	word-spacing: normal;
	letter-spacing: normal;
	text-decoration: none;
	text-align: left;
	text-indent: 0ex;
}
</style>

<%
CommonBean bean;
java.util.ArrayList detailarr = request.getAttribute("detailarr") != null ? (java.util.ArrayList) request.getAttribute("detailarr"): null;
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Diary Report</title>
</head>
<body>
<table width="650" border="0" cellspacing="0">
	<tbody>
		<tr><td colspan="12" align="center" ><b>OFFICE OF THE MINISTER FOR RAILWAYS
			<br>244 RAIL BHAWAN, RLY. BOARD, NEW DELHI TEL 44753 </b></td>
		</tr>
	</tbody>
</table>	
<br>

<table width="650"  cellspacing="0" >
	<tbody>
		<thead style="display: table-header-group;">
		<tr  class="sample">
			<td colspan="4" align="left">REFERENCE(s) RECEIVED IN SECTT., ALL Ref(s)</td>
			
		</tr>
		<tr  class="sample">
			
			<td colspan="4" align="left" height="25" class="bototmDashed">&nbsp;</td>
			
		</tr>
		<tr class="sample" >
		    <td align="left" height="25"  class="bototmDashed" width="229">Reference from &amp; Contents</td>
			<td align="left" class="bototmDashed" width="83">Marked to<br>Date</td>
			<td align="left" class="bototmDashed" width="207">Tar. Dt File No.<br>Sub. Dt Returned to</td>
			<td align="left" class="bototmDashed" width="130">Status</td>
		</tr>
		
	</thead>
		<%
			for(int i=0;i<detailarr.size();i++){
				bean =(CommonBean) detailarr.get(i);
		%>
		<tr class="sample" >
		   <td align="left"  style="text-transform: uppercase;" class="bototmDashed"><%=(i+1)%>.&nbsp;<br><%=bean.getField1()%>, <%=bean.getField2()%>, <%=bean.getField3()%><br>
		    <%=bean.getField4()%><br> <%=bean.getField5()%></td>
		   <td align="left" class="bototmDashed" nowrap="nowrap"><%=bean.getField6()%><br><%=bean.getField11()%><br><%=bean.getField12()%></td>
		   <td align="left" class="bototmDashed"><%=bean.getField7()%> (<%=bean.getField8()%>) (<%=bean.getField9()%> <%=bean.getField10()%>)<br><%=bean.getField18()%> <%=bean.getField14()%><br><%=bean.getField17()%></td>
		   <td align="left" class="bototmDashed"><%=bean.getField16()%>&nbsp;</td>
		</tr>
		<%} %>
</table>
</body>
</html>