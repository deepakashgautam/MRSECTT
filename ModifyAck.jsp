<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
  
  
<!--   <link type="text/css" href="/MRSECTT/theme/MasterModifyAck.css" rel="stylesheet" />  -->
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/MasterModifyAck<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/MasterModifyAck.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
  <link type="text/css" href="${pageContext.request.contextPath}/theme/autoSuggest.css" rel="stylesheet" />
  <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>

	
	<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ReportRefNoDAO.js'></SCRIPT>
    <SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ModifyAckDAO.js'></SCRIPT>
    <SCRIPT src='${pageContext.request.contextPath}/dwr/interface/TrnFileManagementDAO.js'></SCRIPT>
	<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
	<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
  
<% 
	String searchString = (request.getParameter("searchString")!=null) ?request.getParameter("searchString"): "";
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
	String type = (request.getParameter("type")!=null) ?request.getParameter("type"): "";
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
//	String msg = StringFormat.nullString((String)session.getAttribute("var"));
	String msg = StringFormat.nullString((String)request.getAttribute("result"));
	//ArrayList<TrnReference> sSub = (new TrnReferenceDAO()).getSearchSub(searchString);
	
	String querySignedBy = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);
	
	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
%>
 
<script type="text/javascript">

function clearForm(){
	window.document.getElementById("RECEIVEDFROM").value = "";
	window.document.getElementById("SUBJECT").value = "";
	window.document.getElementById("FILESTATUS1").value = "";
	window.document.getElementById("FILESTATUS2").value = "";
	window.document.getElementById("REMARK").value = "";
	window.document.getElementById("ACKNBY").value = "";
	window.document.getElementById("ACKNDATE").value = "";
	window.document.getElementById("FORWARDTO").value = "";
	window.document.getElementById("FORWARDDATE").value = "";
	document.getElementById("INDATE_DRPDWN").options.length=0;
	document.getElementById("searchString").focus();
}

function getsearchDetail(){
	var inDate = window.document.getElementById("INDATE_DRPDWN").value;
	if(inDate.length == 0) {
		alert('Select Ref.No first...');
	}else {
		window.document.getElementById("msgDiv").style.display="none";
		var searchString = window.document.getElementById("searchString").value;
		var suggestData = window.document.getElementById("suggest");
		var lengthDiv=suggestData.childNodes.length;
		if(lengthDiv==0){
			suggestData.innerHTML = '<span>'+inDate+'<br><span>' + suggestData.innerHTML;
		}else{
			for(var i=0;i<lengthDiv;i++){
				if(suggestData.childNodes[i].firstChild.innerHTML==inDate){
					suggestData.removeChild(suggestData.childNodes[i]);
					break;
				}
			}
		suggestData.innerHTML = '<span>'+inDate+'<br><span>' + suggestData.innerHTML;
		}
		ModifyAckDAO.getSearchSub(inDate,dataSearchDetail);
	}
}
	
function dataSearchDetail(data){
  if(data.length>0){
  }
  else {
  	alert('No Data Found!!!');
  }
  var htmlText = '';
	var refBean = new Array(data.length);
	var counter=0;
	for(var loop=0; loop < data.length; loop++){
	counter++;
		var trnRefBean = data[loop];
		refBean[loop] = data[loop];
		window.document.getElementById("RECEIVEDFROM").value = trnRefBean.REFERENCENAME+', '+trnRefBean.VIPSTATUS+', '+trnRefBean.STATECODE;
		window.document.getElementById("SUBJECT").value = trnRefBean.SUBJECT;
		window.document.getElementById("FILESTATUS1").value = trnRefBean.FILESTATUS1;
		window.document.getElementById("FILESTATUS2").value = trnRefBean.FILESTATUS2;
		window.document.getElementById("REMARK").value = trnRefBean.STATUSREMARK;
		window.document.getElementById("ACKNBY").value = trnRefBean.ACKBY;
		window.document.getElementById("ACKNDATE").value = trnRefBean.ACKDATE;
		window.document.getElementById("FORWARDTO").value = trnRefBean.MARKINGTO;
		window.document.getElementById("FORWARDDATE").value = trnRefBean.MARKINGDATE;
 }
}

function saveData(obj)
{
	var ackBy = window.document.getElementById("ACKNBY").value;
	var ackDate = window.document.getElementById("ACKNDATE").value;
	var markingTo = window.document.getElementById("FORWARDTO").value;
	var markinOn = window.document.getElementById("FORWARDDATE").value;
	var inDate = window.document.getElementById("INDATE_DRPDWN").value;
	ModifyAckDAO.updateData(ackBy,ackDate,markingTo,markinOn,inDate,dataUpdateDetail);	
}

function blankAcknDate(obj){
	var ackBy = window.document.getElementById("ACKNBY").value;
//	alert(ackBy);
	if(ackBy <= 0){
		window.document.getElementById("ACKNDATE").value = "";
	}
	
}
function dataUpdateDetail(data){
  if(data.length>0){
  	alert(data);
  	clearForm();
  	document.getElementById("searchString").focus();
  }
 }
 
function chkRole(obj){
	var roleName = obj.value;
	if(roleName.length>0){
		ModifyAckDAO.chkRoleName(roleName,dataRole);
	}
}
function dataRole(data){
  if(data.length>0){
  	return false;
  }
  else {
  	alert('Role Not Found. Enter Valid Role Name.');
  	window.document.getElementById("ACKBY").focus();
  }
}
function chkRole2(obj){
	var roleName = obj.value;
	if(roleName.length>0){
		ModifyAckDAO.chkRoleName(roleName,dataRole2);
	}
}
function dataRole2(data){
  if(data.length>0){
  	return false;
  }
  else {
  	alert('Role Not Found. Enter Valid Role Name.');
  	window.document.getElementById("MARKINGTO").focus();
  }
}

 function funcKeyPress(obj,objvalue){
 //if(objvalue.length==0){
    if(window.event.keyCode==113){
        obj.value=obj.value+'<c:out value="<%=serverDate%>" />'; 
 	}
 // } 
 }
 
   function functionKey(obj,objvalue){ 
	
	if(!obj.readOnly&&((obj.type=='text')||(obj.type=='textarea'))){
	
	if(window.event.keyCode==113){
	//alert(parseInt(obj.maxLength));
	var maxlength=parseInt(obj.maxLength);
	
	if(maxlength >0 ){
	if(maxlength >= (parseInt(objvalue.length)+parseInt('<c:out value="<%=serverDate.length()%>" />'))){
	 obj.value=obj.value+"<c:out value='<%=serverDate%>' />";
	 }}
	 else {
	 	obj.value=obj.value+"<c:out value='<%=serverDate%>' />";
	 }
	 
	 }	}
	// if((window.event.shiftKey==true) && (window.event.keyCode==80))   
	//{   
	//	alert("alt+p")
	//}  
	//obj.value=objvalue+" hello ";
	}
//	 $(function(){
// $("INPUT").keyup(function () {functionKey(this,this.value); }); 
// })
// $(function(){
// $("TEXTAREA").keyup(function () {functionKey(this,this.value); }); 
// })
 
 $(function(){
$("INPUT").live("keyup", function(){
functionKey(this,this.value);
});
});
  $(function(){
$("TEXTAREA").live("keyup", function(){
functionKey(this,this.value);
});
});

 
 
function chkWorkDate(obj) {
	if(obj.value.length>0 && chkDate(obj)) {
		if(compareDateLTET(obj.value,'<c:out value="<%=serverDate%>" />'))
			return true;
		else {
			alert("Date should be less than or equal to Current Date!!!");
			obj.style.backgroundColor = "yellow";
			//obj.value="";
			obj.focus();
			return false;
		}
	}
	return false;
}
function chkDate(obj) {
	if(obj.value=="") {
		obj.style.backgroundColor = "white";
		return true;
	}else {
		if(isValidDate(obj,'DMY')) {
			if(obj.value.split('/')[2].length==2) {
				alert("Enter valid date in format DD/MM/YYYY");
				obj.style.backgroundColor = "yellow";
				obj.focus();
				return false;
			}
			return true;
		}else {
			alert("Enter valid date in format DD/MM/YYYY");
			obj.focus();
			return false;
		}
	}
}

 




$().ready(function() {
	// if text input field value is not empty show the "X" button
	$("#field").keyup(function() {
		$("#x").fadeIn();
		if ($.trim($("#field").val()) == "") {
			$("#x").fadeOut();
		}
	});
	// on click of "X", delete input field value and hide "X"
	$("#x").click(function() {
		$("#field").val("");
		$(this).hide();
	});
});

function getIndate(obj)
{
	var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
	var ISCONF ='<c:out value="<%= sessionBean.getISCONF() %>"/>';
	var REFNO = window.document.getElementById("searchString").value;
//	var REFNO = obj.value;
	if(REFNO.length >0 ){
		if(REFNO.substring(1,2) == '/'){
			if(REFNO.length > 0){
				ReportRefNoDAO.getIncomingDate(ROLEID, REFNO, ISCONF, getInDateData);
			}
		} else{
			alert('Not a valid Ref. No.');
			document.getElementById("searchString").value = "";
			document.getElementById("searchString").focus();
		}
	}else { return 0;}}

function getInDateData(data)
{
	if(document.getElementById("INDATE_DRPDWN").options.length > 0)
	{
		document.getElementById("INDATE_DRPDWN").options.length=0;
	}
  if(data.length > 0)
  {
  	document.getElementById("INDATE_DRPDWN").style.display = "block" ;
  	var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop];
		 
  		var indate = data;
  		var opt = document.createElement("option");
  		document.getElementById("INDATE_DRPDWN").options.add(opt);
  		opt.text = trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE;
    	opt.value = trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE;
  	}
  	document.getElementById("INDATE_DRPDWN").focus();
  }
  else
  {
  	document.getElementById("INDATE_DRPDWN").style.display = "none";
  	document.getElementById("INDATE_DRPDWN").options.length=0;
  	document.getElementById("search").focus();
  }
}
function load()
{
	document.getElementById("searchString").focus();
}
</script>      
</head>
<body onload="load();">
<form name="frm" action="" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Edit-Acknowledgment</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table>
<div id="searchContainer" align="center">

<table align="center" style="border:0;border-color: transparent;" cellpadding="0" cellspacing="0" >
<tr>
<td valign="middle">
<fieldset style="width: 250px; height: 100px;">
<table align="center" style="border: 0;border-color: transparent;"  cellpadding="0" cellspacing="0">  
	<tr>
				<td colspan='5' align="left" height="50" valign="top">
				<input type="text" name="searchString" id="searchString" value="<c:out value='<%=searchString %>'/>" onblur=""
				style="text-transform: uppercase;" onkeypress="allowAlphaNumWithoutSpace('/');" size="19" align="left" tabindex="1">
	<input type="button" name="search" id="search" value=" Go " align="left" style="height: 20px; display: none;" onclick="getIndate(this);" title="Search" tabindex="2">
	<input type="button" name="search" id="btnsave" class="butts" value=" Go " align="left" style="height: 20px;" onclick="getIndate(this);" title="Search" tabindex="2">
	<input type="button" name="search" id="search" value="Search" align="left" style="height: 20px; display: none;" onclick="getsearchDetail();" title="Search" tabindex="4">
	<input type="button" name="search" id="btnsave" class="butts" value="Search" align="left" style="height: 20px;" onclick="getsearchDetail();" title="Search" tabindex="4"><BR>
				<select name="INDATE_DRPDWN" id="INDATE_DRPDWN" style="display: none; width: 200px; font-size: 13px;" tabindex="2" size="4"></select>
				</td>
	</tr>
</table></fieldset></td>
<td valign="top" style="margin-top:0px">

<table id="data" align="center" style="margin-top:0px">  
	<tr>
				<td colspan='5' align="left">
				<fieldset style="height: 100px;margin-top:0px"><legend align="top" style="margin-top:0px">Search History</legend>
				<div id="suggest" style="height: 80px; width: 210px; overflow: auto;"></div>
				</fieldset></td>
			</tr>
</table>
</td>
</tr>
</table>
<table width="60%" border="1" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td align="left"><b>Received From</b></td>
			<td colspan="3" align="left"><input type="text" name="RECEIVEDFROM"
				id="RECEIVEDFROM" size="80" style="border: 0px;" readonly="readonly"></td>
		</tr>
		<tr>
			<td align="left"><b>Subject</b></td>
			<td colspan="3" align="left"><input type="text" name="SUBJECT" id="SUBJECT"
				size="80" style="border: 0px;" readonly="readonly"></td>
		</tr>
		<tr>
			<td align="left"><b>File Status1</b></td>
			<td colspan="3" align="left"><input type="text" name="FILESTATUS1"
				id="FILESTATUS1" size="80" style="border: 0px;" readonly="readonly"></td>
		</tr>
		<tr>
			<td align="left"><b>File Status2</b></td>
			<td colspan="3" align="left"><input type="text" name="FILESTATUS2"
				id="FILESTATUS2" size="80" style="border: 0px;" readonly="readonly"></td>
		</tr>
		<tr>
			<td align="left"><b>Status Remark</b></td>
			<td colspan="3" align="left"><input type="text" name="REMARK" id="REMARK"
				size="80" style="border: 0px;" readonly="readonly"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td width="154"></td>
			<td width="121"></td>
			<td></td>
		</tr>
		<tr>
			<td align="center"><b>Ack. By</b></td>
			<td width="154" align="center"><b>Ack. Date</b></td>
			<td width="121" align="center"><b>Forward To</b></td>
			<td align="center"><b>Forward On</b></td>
		</tr>
		<tr>
			<td align="center"><select name="ACKNBY" id="ACKNBY" style="height: 20px" tabindex="5" onblur="blankAcknDate(this);">
			<option value=""> </option>
				<%
									for(int k=0;k<querySignedByList.size();k++){
									CommonBean beanCommon = (CommonBean) querySignedByList.get(k);
									%>
				<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
				<%
								}%>
			</select></td>
			<td width="154" align="center"><input type="text" name="ACKNDATE" id="ACKNDATE" size="15" maxlength="10" tabindex="6"></td>
			<td width="121" align="center"><select name="FORWARDTO" id="FORWARDTO" style="height: 20px" tabindex="7">
				<%
									for(int k=0;k<markingToList.size();k++){
									CommonBean beanCommon = (CommonBean) markingToList.get(k);
									%>
				<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
				<%
								}%>
			</select></td>
			<td align="center"><input type="text" name="FORWARDDATE" id="FORWARDDATE" size="15" maxlength="10" tabindex="8"></td>
		</tr>
		<tr>
			<td colspan="4" align="center"><input style="height: 20px;" type="button" class="butts"
				name="save" value=" Save " title="Search" onclick="saveData();" tabindex="9">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input style="height: 20px;" class="butts" type="button" onclick="clearForm(this);" name="clear" value=" Clear " title="Clear" tabindex="10"></td>
		</tr>
	</tbody>
</table>
<div id="msgDiv"><c:out value='<%= msg.length()>0?msg:"" %>'/></div>
</div>
</form>
</body>
</html>