<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>

<%@page import="in.org.cris.mrsectt.dao.DashboardSubjectWiseDAO"%><html>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%><html>



<link href="${pageContext.request.contextPath}/datatable/media/css/jquery.dataTables.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/datatable/media/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/datatable/media/js/jquery.dataTables.min.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/DashboardSubjectWiseDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
  
%>
<% 
CommonBean bn;
MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");

String RoleId = request.getParameter("RoleId")!=null?request.getParameter("RoleId"):"";
String loginID = request.getParameter("loginID")!=null?request.getParameter("loginID"):"";
String status = request.getParameter("status")!=null?request.getParameter("status"):"";
//System.out.println(" getReportData getReportData+++++++++++++++++++++"+RoleId);

ArrayList<CommonBean> RefDashBoardDetails = new DashboardSubjectWiseDAO().getRefDashBoardDetailListRoleWise(RoleId,loginID,status);


String loginIDName = sessionBean.getLOGINID()!=null?sessionBean.getLOGINID():"";
String querycomplianceCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' order by code"; 
ArrayList<CommonBean> complianceCode = (new CommonDAO()).getSQLResult(querycomplianceCode, 3);

String queryloginnames = "SELECT LOGINDESIGNATION FROM MSTLOGIN WHERE LOGINASROLEID = '"+sessionBean.getLOGINASROLEID()+"' AND ISACTIVELOGIN ='Y' order by LOGINDESIGNATION"; 
ArrayList<CommonBean> loginnamesList = (new CommonDAO()).getSQLResult(queryloginnames,1);
 %>
<script type="text/javascript">
$(document).ready( function () {
	 $('#example').DataTable( {
	        "paging":   false,
	        "searching": false,
	        "info":     false
	    } );
} );

function SaveCompliance(refno,sno,loginIDName){
	//alert()
	//alert(refno +"----"+sno);
	
	var compliianceRem = window.document.getElementById("complianceRemarks_"+sno).value;
	var compliance = window.document.getElementById("ROLECOMPLIANCE_"+sno).value;
	var forwardTo = window.document.getElementById("ROLEFORWARD_TO_"+sno).value;
	
	//alert (compliianceRem +"=========" +compliance);
	DashboardSubjectWiseDAO.saveComplianceData(refno,compliance,compliianceRem,loginIDName,forwardTo,dateSaveCompliance);
	
}

function dateSaveCompliance(data){
	
	alert(data);
}



function submitPage(btnval){
	document.forms[0].submit();


}

function popupGallery(refId)
{
	//alert(refId);
	var url="ImageGallery.jsp?refId="+refId+"&type=C";
	window.open(url,"","toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,left=0,top=0,scrollbars=1,resizable=1");
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Detailed List - Dashboard</title>
</head>

<body>
<form name="report" action="DetailedListOfDashboardRoleWiseController" method="post">
<br>
 <input type="hidden" name="loginID" value="<%=loginID%>">
  <input type="hidden" name="RoleId" value="<%=RoleId%>">
   <input type="hidden" name="status" value="<%=status%>">
   <input type ="hidden" value ="hod" name ="typeOfUser">		
<input type="button" class="btn btn-large btn-primary" name="btnsave" id="btnsave" class="butts" value="Print in Excel" onclick="submitPage('Go');" />
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0"  style="margin-top: 0px;margin: 0px; height: 331px" >
			
			<tr>
				<td valign="top" id="SubHdCenLft">&nbsp;</td>
				<td width="100%" valign="top">
<!-- CHANGED ON 24_07_2012 -->
			<table width="100%" style="margin-top: 1px;margin: 1px;" border="1" id="example">
				<thead>
				<tr><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>S. No</b></td>
				<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Reference Number<br>Incoming Date</b></td>
				<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Reference Date</b></td>
				<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Name</b></td>
				<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Subject Code</b></td>
				<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Subject</b></td>
				<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Marking To<br>Marking Date</b></td>
				<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Remarks <br>Signed By<br>Signed On</b></td>
				
					<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>File No</b></td>
						<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Forward To</b></td>
						<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Compliance</b></td>
							<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Compliance Remarks</b></td>
							<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Attach</b></td>
							<td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Save</b></td>
					</tr>
					
					</thead>
					<tbody>
				<%
					for(int i=0;i<RefDashBoardDetails.size();i++){
						bn = (CommonBean) RefDashBoardDetails.get(i);
						
						%>
						
						<tr>
						<td> <%=i+1%></td>
						<td><%=bn.getField1() %>&nbsp;<br> <%=bn.getField2() %></td>
						<td ><%=bn.getField3() %> &nbsp;</td>
						<td><%=bn.getField4() %>&nbsp;</td>
						
						<td><%=bn.getField5() %>&nbsp;</td>
						
						
						<td><%=bn.getField6() %>&nbsp;</td>
						<td><%=bn.getField7() %>&nbsp;<br> <%=bn.getField8() %></td>
						<td><%=bn.getField9() %>&nbsp;<br> <%=bn.getField10() %><br> <%=bn.getField11() %></td>
					
						<td><%=bn.getField14() %>&nbsp;</td>
						<td>
						<select id="ROLEFORWARD_TO_<%=i+1%>" style="width: 120px" tabindex="21"> 	 
							<option value="" selected>Select one- </option>
								<%
									for(int k=0;k<loginnamesList.size();k++){
									CommonBean beanCommon = (CommonBean) loginnamesList.get(k);
									%>
								<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(bn.getField20())?"selected":""%>><%=beanCommon.getField1()%></option>
								<%
								}%>
							</select>

</td>
						
						<td>
						<select id="ROLECOMPLIANCE_<%=i+1%>" style="width: 120px" tabindex="21"> 	 
							<option value="" selected>Select one- </option>
								<%
									for(int k=0;k<complianceCode.size();k++){
									CommonBean beanCommon = (CommonBean) complianceCode.get(k);
									%>
								<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(bn.getField17())?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
								}%>
							</select>
						</td>
						
						
						
						<td><textarea id ="complianceRemarks_<%=i+1%>" onkeypress="allowAlphaNum('()/*');" rows="3" cols="20"><%=bn.getField16()%></textarea>
						</td>
						<td align="center" ><img border="0" alt="Image Gallery" src="${pageContext.request.contextPath}/images/stn.gif" onclick="popupGallery('<%= bn.getField18()%>');"></td>
						<td><input type="button" name="SaveCompliance111" value="save" onclick="SaveCompliance('<%=bn.getField1() %>',<%=i+1 %>,'<%=loginIDName%>');"
											title="Save Compliance" >
											</td>
						</tr>
				
					<%} %>
					
					</tbody>
				</table>
				</td>
				<td valign="top" id="SubHdCenRight">&nbsp;</td>
			</tr>

			<tr>
				<td>
				<div id="SubHdBtmLft">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmCen">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmRt">&nbsp;</div>
				</td>
			</tr>
		</table>



</form>
</body>
</html>