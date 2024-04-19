<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.org.cris.mrsectt.dao.ModifyAckDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnReference"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%><html>
<%@page import="in.org.cris.mrsectt.dao.ReportRefNoDAO"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Text Search - Reference</title>

  <link type="text/css" href="/MRSECTT/theme/MasterModifyAck.css" rel="stylesheet" />
  <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
	
    <SCRIPT src='${pageContext.request.contextPath}/dwr/interface/CommonDAO.js'></SCRIPT>
	<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
	<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<% 
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
%>
<script>
function saveData(obj) {
	var roleName = window.document.getElementById("roleName").value;
	var roleId = window.document.getElementById("roleId").value;
	var loginId = window.document.getElementById("loginId").value;
	var oldPass = window.document.getElementById("oldPass").value;
	var newPass = window.document.getElementById("newPass").value;
	if(chkblank(window.document.frm.oldPass)) {
		if(chkblank(window.document.frm.newPass)) {
		CommonDAO.changePassword(loginId,roleId,oldPass,newPass,dataUpdateDetail);
		}
	}
}

function dataUpdateDetail(data){
  	if(data == '0'){
  		alert('Operation Failed. Try again.');
  		window.document.getElementById("oldPass").value = "";
  		window.document.getElementById("newPass").value = "";
  		document.getElementById("oldPass").focus();
  	} else {
  		alert('Password Changed Successfully.\n\n Login with new password.');
  		window.open("LogOffController","_parent");
  	}
 }

function load()
{
	document.getElementById("oldPass").focus();
}
</script>      
</head>
<body onload="load();">
<form name="frm" action="" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Home</i> - Change Password</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table>
<br>
<br><div id="searchContainer" align="center">

<fieldset style="width: 300px; height: 150px;">
<table align="center" style="border: 0;border-color: transparent;"  cellpadding="0" cellspacing="0">  
	<tr>
		<td align="left" valign="top">Role ID</td>
		<td align="left" height="30" valign="top">
			&nbsp;&nbsp;<input type="text" name="roleName" id="roleName" style="text-align: center; border: none;" readonly="readonly" value="<c:out value='<%= sessionBean.getLOGINASROLENAME() %>'/>" onkeypress="allowAlphaNumWithoutSpace('/');" size="19" tabindex="1">
			<input type="hidden" name="roleId" id="roleId" value="<c:out value='<%= sessionBean.getLOGINASROLEID() %>'/>">
		</td>
	</tr>
	<tr>
		<td align="left" valign="top">Login ID</td>
		<td align="left" height="30" valign="top">
			&nbsp;&nbsp;<input type="text" name="loginId" id="loginId" style="text-align: center; border: none;" readonly="readonly" value="<c:out value='<%= sessionBean.getLOGINID() %>'/>" onkeypress="allowAlphaNumWithoutSpace('/');" size="19" tabindex="1">
		</td>
	</tr>
	<tr>
		<td align="left" valign="top">Old Password</td>
		<td align="left" height="30" valign="top">
			&nbsp;&nbsp;<input type="text" name="oldPass" id="oldPass" style="text-align: center" value="" onkeypress="allowAlphaNumWithoutSpace('/');" size="19" tabindex="2">
		</td>
	</tr>
	<tr>
		<td align="left" valign="top">New Password</td>
		<td align="left" height="30" valign="top">
			&nbsp;&nbsp;<input type="text" name="newPass" id="newPass" style="text-align: center" value="" onkeypress="allowAlphaNumWithoutSpace('/');" size="19" tabindex="3">
		</td>
	</tr>
</table></fieldset>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="4" align="center"><input style="height: 20px;" type="button" name="save" value=" Save " title="Search" onclick="saveData();" tabindex="4"></td>
		</tr>
</table>
</div>
</form>
</body>
</html>