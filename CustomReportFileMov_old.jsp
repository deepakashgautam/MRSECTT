<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.GenBean"%>
<%@page import="in.org.cris.mrsectt.dao.CustomReportFileMovDAO"%>
<%@page import="java.net.URLDecoder"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT</title>

<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  --> 
<link type="text/css" href="${pageContext.request.contextPath}/jquery-ui-1.8.4.custom/css/blitzer/jquery-ui-1.8.5.custom.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/CustomReportFileMovDAO.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/engine.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/util.js'></SCRIPT>

<%
String loginid = session.getAttribute("loginid") != null ? session.getAttribute("loginid").toString(): "";
ArrayList <GenBean> reparr = new CustomReportFileMovDAO().getReports(loginid);
%>
<script>
 
 function openReport(repid,param,reptype){
 //	reptype = (reptype.length>0?(reptype==2?"":reptype):"2");
 //	window.location.href="Report"+reptype+".jsp?reportid="+repid+"&"+param;
	window.location.href="ReportFileMov.jsp?reportid="+repid+"&"+param;
 }
 
 function runReport(repid,repType){
 	window.location.href="GenerateReportFileMovController?save=Y&generate=Y&reportid="+repid+"&repType="+repType;
 }
 
 
 function deleteReport(obj,repid)
 {
 	
 	if(confirm("Are you sure you want to delete?"))
 	{
 		CustomReportFileMovDAO.deleteReport(repid,function isSaved(data)
 		
	{
			
		if(data.split("~")[0]=="0")
		{
			alert(data.split("~")[1]);
		} else
		{
			//alert("Data Saved!!!");;
			removeRow(obj);		
		}
	} );
 	}
 } 
 
 	function removeRow(obj){
		var idx = getIndex(obj);
		document.getElementById("baserow").deleteRow(idx);
	}
	var repno=1;
 function openNewReport(){
// alert(repno);
 if(repno==1)
 	window.location.href='ReportFileMov.jsp';
 else  if(repno==2)
 	window.location.href='ReportFileMov.jsp';
 }
 </script>
</head>

<body>
<form action="" method="post">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr><td><font size="3" face="Tahoma"> <b><i>File-Create</i> - Report-Custom</b></font>
<img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11"></td></tr>
      <tr>
        <td align="center" style="padding-left:7px;"><br/><br/>
			<table  width="40%" border="1" bordercolor="#7e7d7d" cellspacing="0" cellpadding="0" style="border-collapse:collapse;">
                  	<tr class="treven"> 
                  	
                  		<td>S.No.</td><td>Report Name</td><td>Last Updated on</td>
														<td colspan="4"></td>
													</tr>
                  	<tbody id="baserow">
                    	<% for(int i=0;i<reparr.size();i++){ %>
                  	<tr>
                  		<td><%=(i+1) %></td>
                  		<td><%=  URLDecoder.decode(reparr.get(i).getField()[1], "utf-8")   %></td>
                  		<td><%= reparr.get(i).getField()[2] %></td>
						<td align="center" style="white-space: nowrap;">     
							<input type="button" class="ui-icon ui-icon-play" style="width: 20px; height: 20px; cursor: pointer;" value=" "  onclick="runReport('<%= reparr.get(i).getField()[0] %>','<%= reparr.get(i).getField()[5] %>');" title="Run Report">
						</td>
						<td align="center">
    						<input type="button" class="ui-icon ui-icon-wrench" style="width: 20px; height: 20px; cursor: pointer;"  value=" " onclick="openReport('<%= reparr.get(i).getField()[0] %>','<%=reparr.get(i).getField()[4]%>','<%= reparr.get(i).getField()[5] %>');" title="Edit Report" />
                  		</td>
<!--              		<td align="center">
                  			<input type="button"  class="ui-icon ui-icon-copy" style="width: 20px; height: 20px; cursor: pointer;" value=" " onclick="openReport('','<%=reparr.get(i).getField()[4]%>','','<%= reparr.get(i).getField()[5] %>');" title="Copy as New Report" />
                  		</td> -->
                  		<td align="center">
                  			<input type="button"  class="ui-icon ui-icon-closethick" style="width: 20px; height: 20px; cursor: pointer;" name="delbtn" value=" Delete " onclick="deleteReport(this,'<%=reparr.get(i).getField()[0]%>');" title="Delete Report" />
                  		</td>
                  	</tr>
                 		 <%} %>
                 		 </tbody>
                  </table> <br />
                  <input type="button" value=" Create New " class="butts" onclick="openNewReport()" />
                   
                    <table id="tRepType" border="1" bordercolor="#7e7d7d" cellspacing="0" cellpadding="0" style="border-collapse:collapse;display: none;">
                    	<tr class="firstbg">
                    		<th>Report Type</th>
                    	</tr>
                    	<tr class="treven">
                    		<td> <input type="radio" name="RepType" value="1" onclick="javascript: repno=1;"> For   Works-In-Progress </td>
                    	</tr> 
                    	<tr class="trodd">
                    		<td> <input type="radio" name="RepType" value="2" onclick="javascript: repno=2;"> For New Proposals </td>
                    	</tr> 
                    	<tr class="firstbg">
                    		<th> <input type="button" value=" OK " class="butts" onclick="openNewReport();"> <input type="button" value=" Cancel " class="butts" onclick="document.getElementById('tRepType').style.display='none';"> </th>
                    	</tr> 
                    </table>
</td>
      </tr>
      <tr>
        <td height="4"></td>
      </tr>
    </table>
</form>
</body>
</html>
	