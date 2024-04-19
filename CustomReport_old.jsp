<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.GenBean"%>
<%@page import="in.org.cris.mrsectt.dao.CustomReportDAO"%>
<%@page import="java.net.URLDecoder"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT</title>
<!-- <link href="${pageContext.request.contextPath}/style/irpsm.css" type="text/css" rel="stylesheet"/>  --> 
<link type="text/css" href="${pageContext.request.contextPath}/jquery-ui-1.8.4.custom/css/blitzer/jquery-ui-1.8.5.custom.css" rel="stylesheet" />
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/CustomReportDAO.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/engine.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/util.js'></SCRIPT>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
 <script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<%
String loginid = session.getAttribute("loginid") != null ? session.getAttribute("loginid").toString(): "";
ArrayList <GenBean> reparr = new CustomReportDAO().getReports(loginid);
%>
<script>
 
 function openReport(repid,param,reptype){
 	reptype = (reptype.length>0?(reptype==1?"":reptype):"1");
 	window.location.href="Report"+reptype+".jsp?reportid="+repid+"&"+param;
 }
 
 function runReport(repid,repType){
 	window.location.href="GenerateReportController?save=Y&generate=Y&reportid="+repid+"&repType="+repType;
 }
 
 
 function deleteReport(obj,repid)
 {
 	
 	if(confirm("Are you sure you want to delete?"))
 	{
 		CustomReportDAO.deleteReport(repid,function isSaved(data)
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
// 	alert(obj);
		var idx = getIndex(obj);
	//	alert(idx);
		document.getElementById("baserow").deleteRow(idx);
	}
	var repno=1;
 function openNewReport(){
// alert(repno);
 if(repno==1)
 	window.location.href='Report.jsp';
 else  if(repno==2)
 	window.location.href='Report2.jsp';
 }
 </script>
</head>

<body>
<form action="" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Report-Custom</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table>
<table width="97%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="8px"></td>
                  </tr>
                  <tr>
                    <td align="center" style="padding-left:7px;">
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
            <!--      		<td align="center">
                  			<input type="button"  class="ui-icon ui-icon-copy" style="width: 20px; height: 20px; cursor: pointer;" value=" " onclick="openReport('','<%=reparr.get(i).getField()[4]%>','','<%= reparr.get(i).getField()[5] %>');" title="Copy as New Report" />
                  		</td>
             -->      		<td align="center">
                  			<input type="button"  class="ui-icon ui-icon-closethick" style="width: 20px; height: 20px; cursor: pointer;" name="delbtn" value=" Delete " onclick="deleteReport(this,'<%=reparr.get(i).getField()[0]%>');" title="Delete Report" />
                  		</td>
                  	</tr>
                  	
                 		 <%} %>
                 		 </tbody>
                  </table> <br />
                  <input type="button" value="Create New" onclick="openNewReport()" style="height: 20px;" class="butts"/>
                   
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
                <td></td>
              </tr>
              <tr>
              	<td style="padding-left: 7px;"><br />
              	  
              	</td>
              </tr>
             
             
            </table>
</form>
</body>
</html>
	