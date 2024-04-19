<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.ReportRefNoDAO"%><html>
<head>
<%
	CommonBean bean;
	
	String refNo="";
	String inDate=""; 
	String val = request.getParameter("VAL");
	String rval[] = val.split(",");
	/*for(int i=0;i<rval.length;i++){
	 refNo = rval[i].split("~")[0];
	 inDate = rval[i].split("~")[1];
	//System.out.println(refNo+"-----"+inDate);
	}*/
	
	
				
	
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Diary Report</title>
<script type="text/javascript">
function printMsg() {
	alert('Print in LANDSCAPE orientation.');
}
</script>
</head>
<body class="landscape" onload="printMsg();" topmargin="0">
<table width="100%" border="0" cellspacing="0" align="center">
	<tbody>
		<tr><td colspan="12" align="center"><b><font size="3"><b><%= sessionBean.getTENUREOFFICENAME()%></b></font>
			<br><font size="1"><%= sessionBean.getTENUREOFFICEADDRESS()%></font></td>
		</tr>
		<tr>
			<td align="center" colspan="12"><img width="20" height="20" src="images/printer.png" onclick="window.print()" title="Print"></img></td>
		</tr>
	</tbody>
</table>	
<br>
<% for(int r=0;r<rval.length;r++){ 
	java.util.ArrayList mainArr = (new ReportRefNoDAO()).getMainBlock(refNo,rval[r]);
	java.util.ArrayList reminderArr = (new ReportRefNoDAO()).getReminderBlock(refNo,rval[r]);
	java.util.ArrayList finalStatusArr = (new ReportRefNoDAO()).getFinalStatusBlock(refNo,rval[r],sessionBean.getISREPLY());
%>
<fieldset><legend style="font-weight: bold;"><%=rval[r]%></legend><table><tr><td>
<label style="border: solid; border-width: 1px; font-size: 14; font-family: Tahoma"><b>Main</b>
<table width="100%" border="0" style="font-size: 11; font-family: Tahoma" align="center">
	<%
			for(int i=0;i<mainArr.size();i++){
				bean =(CommonBean) mainArr.get(i);
		%>
	<tr>
		<td align="left" width="104">Ref. Class</td>
		<td align="left" width="264">: <%=bean.getField1()%></td>
		<td align="left" width="10"></td>
		<td align="left" width="72">Ref. No</td>
		<td align="left" width="251">: <b><%=bean.getField2()%></b></td>

		<td align="left" width="9"></td>
		<td align="left" width="115">Link Ref. No</td>
		<td align="left" width="89">: <%=bean.getField3()%></td>
		<td align="left" width="5"></td>
		<td align="left" width="78"></td>
		<td align="left" width="80"></td>
		<td align="left" width="3"></td>
		<td align="left" width="92"></td>
		<td align="left" width="107"></td>
	</tr>
	<tr>
		<td align="left" width="104">Ref. Category</td>
		<td align="left" width="264">: <%=bean.getField4()%></td>
		<td align="left" width="10"></td>
		<td align="left" width="72">Language</td>
		<td align="left" width="251">: <%=bean.getField5()%></td>

		<td align="left" width="9"></td>
		<td align="left" width="115">Urgency &amp; Security</td>
		<td align="left" width="89">: <%=bean.getField6()%></td>
		<td align="left" width="5"></td>
		<td align="left" width="78">Received By</td>
		<td align="left" width="80">: <%=bean.getField23()%></td>
		<td align="left" width="3"></td>
		<td align="left" width="92"></td>
		<td align="left" width="107"></td>
	</tr>
	<tr>
		<td align="left" colspan="14">
		<hr>
		</td>
	</tr>
	<tr>
		<td align="left" width="104">Incoming Dt.<span style="COLOR: red"><b></b></span></td>
		<td align="left" width="264">: <%=bean.getField7()%></td>
		<td align="left" width="10"></td>
		<td align="left" width="72">Letter Dt.<span style="COLOR: red"><b></b></span></td>
		<td align="left" width="251">: <%=bean.getField8()%></td>
		<td align="left" width="9"></td>
		<td align="left" width="115">Budget</td>
		<td align="left" width="89">: <%=bean.getField9()%></td>
		<td align="left" width="5"></td>
		<td align="left" width="78"></td>
		<td align="left" width="80"></td>
		<td align="left" width="3"></td>
		<td align="left" width="92"></td>
		<td align="left" width="107"></td>
	</tr>
	<tr>
		<td align="left" width="104">Received From<span style="COLOR: red"><b></b></span></td>
		<td align="left" width="264">: <%=bean.getField10()%></td>
		<td align="left" width="10"></td>
		<td align="left" width="72">Status</td>
		<td align="left" width="251">: <%=bean.getField11()%></td>
		<td align="left" width="9"></td>
		<td align="left" width="115">State</td>
		<td align="left" width="89">: <%=bean.getField12()%></td>
		<td align="left" width="5"></td>
		<td align="left" width="78"></td>
		<td align="left" width="80"></td>
		<td align="left" width="3"></td>
		<td align="left" width="92">Target Days</td>
		<td align="left" valign="top" width="107">: <%=bean.getField24()%></td>
	</tr>
	<tr>
		<td align="left" valign="top" width="104">Subject</td>
		<td align="left" width="264">: <%=bean.getField15()%></td>
		<td align="left" width="10"></td>
		<td align="left" width="72" valign="top">Sub.</td>
		<td align="left" width="251" valign="top">: <%=bean.getField16()%></td>
		<td align="left" width="9"></td>
		<td align="left" width="115" valign="top">Forward To</td>
		<td align="left" width="89" valign="top">: <%=bean.getField17()%></td>
		<td align="left" width="5"></td>
		<td align="left" width="78" valign="top">Forward Dt.</td>
		<td align="left" width="80" valign="top">: <%=bean.getField13()%></td>
		<td align="left" width="3"></td>
		<td align="left" valign="top" width="92">Target Dt.</td>
		<td align="left" valign="top" width="107">: <%=bean.getField14()%></td>
	</tr>
	<tr>
		<td align="left" colspan="14">
		<hr>
		</td>
	</tr>
	<tr>
		<td align="left" rowspan="2" width="104" valign="top">Remarks</td>
		<td align="left" rowspan="2" width="264" valign="top">: <%=bean.getField18()%></td>
		<td align="left" width="10"></td>
		<td align="left" valign="top" width="72">Signed By</td>
		<td align="left" valign="top" width="251">: <%=bean.getField19()%></td>
		<td align="left" width="9"></td>
		<td align="left" valign="top" width="115">Ack. By</td>
		<td align="left" valign="top" width="89">: <%=bean.getField20()%></td>
		<td align="left" width="5"></td>
		<td align="left" width="78"></td>
		<td align="left" width="80"></td>
		<td align="left" width="3"></td>
		<td align="left" width="92"></td>
		<td align="left" width="107"></td>
	</tr>
	<tr>
		<td align="left" width="10"></td>
		<td align="left" valign="top" width="72">Signed On</td>
		<td align="left" valign="top" width="251">: <%=bean.getField21()%></td>
		<td align="left" width="9"></td>
		<td align="left" valign="top" width="115">Ack. On</td>
		<td align="left" valign="top" width="89">: <%=bean.getField22()%></td>
		<td align="left" width="5"></td>
		<td align="left" width="78"></td>
		<td align="left" width="80"></td>
		<td align="left" width="3"></td>
		<td align="left" width="92"></td>
		<td align="left" width="107"></td>
	</tr>
	<%} %>
</table>
</label>
<br>
<label style="border: solid; border-width: 1px; font-size: 14; font-family: Tahoma"><b>Reminder</b>
<table width="100%" border="0" style="font-size: 11; font-family: Tahoma" align="center">
	<tr>
		<td align="left" width="50"> S.No.</td>
		<td align="left" width="157"> Reminder Type</td>
		<td align="left" width="23"></td>
		<td align="left" width="83">Reminder Dt.</td>
		<td align="left" width="70"></td>
		<td align="left" width="180">Remarks</td>
		<td align="left" width="21"></td>
		<td align="left" width="61">Signed By</td>
		<td align="left" width="60"></td>
		<td align="left" width="76">Signed On</td>
		<td align="left" width="91"></td>
		<td align="left" width="119">Sent To</td>
		<td align="center" width="14" colspan="3"></td>
	</tr>
	<tr><td colspan="14"><hr></td></tr>
		<%
			for(int i=0;i<reminderArr.size();i++){
				bean =(CommonBean) reminderArr.get(i);
		%>
	<tr>
		<td align="center" width="50"><%= i+1%></td>
		<td align="left" width="157"><%=bean.getField1()%></td>
		<td align="center" width="23"></td>
		<td align="left" width="83"><%=bean.getField2()%></td>
		<td align="center" width="70"></td>
		<td align="left" width="180"><%=bean.getField3()%></td>
		<td align="center" width="21"></td>
		<td align="left" width="61"><%=bean.getField4()%></td>
		<td align="center" width="60"></td>
		<td align="left" width="76"><%=bean.getField5()%></td>
		<td align="center" width="91"></td>
		<td align="left" width="119"><%=bean.getField6()%></td>
		<td align="center" width="14"></td>
		<td align="center" width="66"></td>
		<td align="center" width="80"></td>
	</tr>
	<%} %>
</table>
</label>
<br>
<label style="border: solid; border-width: 1px; font-size: 14; font-family: Tahoma"><b>Final Status - File</b>
<table width="100%" border="0" style="font-size: 11; font-family: Tahoma" align="center">
		<%
			for(int i=0;i<finalStatusArr.size();i++){
				bean =(CommonBean) finalStatusArr.get(i);
		%>
	<tr>
		<td align="left" colspan="2"> Reg. No</td>
		<td align="left" width="248">: <%=bean.getField1()%></td>
		<td align="left" width="48">Reg. Dt.</td>
		<td align="left" width="138">: <%=bean.getField2()%></td>
		<td align="left" colspan="10"></td>
	</tr>
	<tr>
		<td align="left" colspan="2">File No</td>
		<td align="left" colspan="13">: <%=bean.getField3()%></td>
	</tr>
	<tr>
		<td align="left" width="78"><b>Position</b></td>
		<td align="left" colspan="14"><hr></td>
	</tr>
	<tr>
		<td align="left" colspan="14"><b></b> </td>
		<td align="left">
		</td>
	</tr>
	<tr>
		<td align="left" colspan="2">Internal marking<span style="COLOR: red"><b></b></span></td>
		<td align="left" colspan="13">: <%=bean.getField4()%></td>
	</tr>
	<tr>
		<td align="left" colspan="2">Status 1<br>Status 2
		</td>
		<td align="left" colspan="2">: <%=bean.getField5()%> <br>
		: <%=bean.getField6()%></td><td align="left" width="138"></td>
		<td align="left" colspan="5" width="79">Status Remark</td>
		<td align="left" colspan="5" width="546">: <%=bean.getField7()%></td>
	</tr>
	<tbody></tbody>
	<tr>
		<td align="left" colspan="15"><hr></td>
	</tr>
	<tr>
		<td align="left" colspan="2">Reply Type</td>
		<td align="left" colspan="2">: <%=bean.getField8()%></td>
		<td align="left" width="138"></td>
		<td align="left" colspan="2" width="79">Reason</td>
		<td align="left" colspan="8">: <%=bean.getField9()%></td>
	</tr>
	<tr>
		<td align="left" colspan="2">Marking To</td>
		<td align="left" colspan="2">: <%=bean.getField10()%></td>
		<td align="left" width="138"></td>
		<td align="left" colspan="2" width="79">Marking On</td>
		<td align="left" colspan="8">: <%=bean.getField11()%></td>
	</tr>
	<%} %>
</table>
</label>
</td></tr></table></fieldset>
<%} %>
</body>
</html>