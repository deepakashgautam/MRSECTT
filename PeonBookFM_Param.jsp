<html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.dao.ReportFileNoDAO"%>
<%@page import="in.org.cris.mrsectt.dao.ModifyHindi_GDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<head>
<!--  <link type="text/css" href="/MRSECTT/theme/MasterModifyAck.css" rel="stylesheet" />  -->
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/MasterModifyAck<c:out value="<%=theme%>"/>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/MasterModifyAck.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
  <link type="text/css" href="${pageContext.request.contextPath}/theme/autoSuggest.css" rel="stylesheet" />
  <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ModifyHindi_GDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<% 
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
    
	String queryDate = " SELECT TO_CHAR(SYSDATE-1,'DD/MM/YYYY') FROM DUAL "; 
	ArrayList<CommonBean> date = (new CommonDAO()).getSQLResult(queryDate, 1);

	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	
	String MARKINGUSER=request.getParameter("MARKINGUSER")!=null ? request.getParameter("MARKINGUSER") : "";
	
	String queryMarkingUser = "SELECT DISTINCT STATUSCHANGEUSER FROM TRNFILEMARKING WHERE STATUSCHANGEUSER IS NOT NULL "; 
	ArrayList<CommonBean> markingUserList = (new CommonDAO()).getSQLResult(queryMarkingUser, 1);
%>

<script type="text/javascript">

$(document).ready(function ()
        {
            $.ui.dialog.defaults.bgiframe = true;
            $("#datefrom").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true'});
            $("#dateto").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true' });
            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
        });
        
function unhideTR(row)
{
   row.style.display = '';
}

function hideTR(row)
{
   row.style.display = 'none';
}

function setChkBlank()
{
for (var i=0;i<window.document.frm.REP.length;i++)
{
if(window.document.frm.REP[i].checked==true)
  {
	if(window.document.frm.REP[i].value==1)
	{
		if(chkblank(window.document.getElementById("regnofrom")) && chkblank(window.document.getElementById("regnoto")) && chkblank(window.document.getElementById("datefrom")) && chkblank(window.document.getElementById("dateto")))
		{return 'true'; } else {return 'false';}
	}
	else if(window.document.frm.REP[i].value==2)
	{
		if(chkblank(window.document.getElementById("datefrom")) && chkblank(window.document.getElementById("dateto")) && chkblank(window.document.getElementById("regnofrom")) && chkblank(window.document.getElementById("regnoto")))
		{return 'true'; } else {return 'false';}
	}
	else if(window.document.frm.REP[i].value==3)
	{
		if(chkblank(window.document.getElementById("datefrom")))
		{return 'true'; } else {return 'false';}
    }
    else if(window.document.frm.REP[i].value==4)
	{
    }
   }
  }
}
function runReport()
 {
	//var aa = setChkBlank();
	//if(aa == 'false'){
	//	return false;
//	}
//	else {
		var flag = true;
		var datefrom = window.document.frm.datefrom;
		var dateto = window.document.frm.dateto;
//		if(flag)
	//	{
			window.document.frm.method="post";
			window.document.frm.target="_new";
			window.document.frm.action="GeneralReports_FileMovController";
			window.document.frm.submit();
//		}
	//}
 }

function getIndate(obj)
{
	var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
	var REFNO = obj.value;
	if(REFNO.length>0){
		ModifyHindi_GDAO.getIncomingDateFM(ROLEID, REFNO, getInDateData);
	}
}

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
</script>
<script>  
 function funcKeyPress(obj,objvalue){
 //if(objvalue.length==0){
    if(window.event.keyCode==113){
        obj.value=obj.value+'<c:out value="<%=serverDate%>"/>'; 
 	}
 // } 
 }
 
  function functionKey(obj,objvalue){ 
	
	if(!obj.readOnly&&((obj.type=='text')||(obj.type=='textarea'))){
	
	if(window.event.keyCode==113){
	//alert(parseInt(obj.maxLength));
	var maxlength=parseInt(obj.maxLength);
	
	if(maxlength >0 ){
	if(maxlength >= (parseInt(objvalue.length)+parseInt('<c:out value="<%=serverDate.length()%>"/>'))){
	 obj.value=obj.value+"<c:out value='<%=serverDate%>'/>";
	 }}
	 else {
	 	obj.value=obj.value+"<c:out value='<%=serverDate%>'/>";
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
</script>
</head>
<body onload="window.document.getElementById('datefrom').focus();">
<form name="frm" id="frm" action="" method="post">
			<TABLE cellspacing='0'  height="100%" align="center" width="100%">
			<tr><td><font size="3" face="Tahoma"> <b><i>File-Create</i> - Peon Book</b></font>
<img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11"></td></tr>
				<TR>
				  <TD valign="top" height="520">
					<TABLE width="100%">
						<TR>
							<TD align="center" valign="middle">
				
				<div id="d2">
						<table width="30%" align="center" style="border: solid; border-width: 1.5"><tr><td>
							<table id="data">
							<tr>
				<td align="center"><table width="984px" border="0"  cellspacing="0"
					cellpadding="0">
					<tr>
						<td colspan="9"><br><br><center><fieldset style="width: 60%;">
								<table  border="0" align="center">
								<tbody>
									<tr>
									<td align="left" height="18" colspan="2">
									<input type="hidden" name="reportNumber" size="15" value="4"></td>
									</tr>
									<tr id="tr4">
									<td align="center" width="263" valign="top">Marked On (Downward)<font color="red"></font> :</td>
									<td align="left" width="497" valign="top"><input type="text" name="datefrom" id="datefrom" value="<c:out value='<%= date.get(0).getField1() %>'/>" size="11"> to
									<input type="text" name="dateto" id="dateto" value="<c:out value='<%= date.get(0).getField1() %>'/>" size="11"></td>
									<td   align="right">&nbsp;&nbsp;&nbsp;<b>User:</b> </td>
									<td nowrap="nowrap"><select style="width: 120px"  name="MARKINGUSER" id="MARKINGUSER"  tabindex="1" >
								<option value="" selected>ALL</option>
								<%
										for(int i=0;i<markingUserList.size();i++){
										CommonBean beanCommon=(CommonBean) markingUserList.get(i);
										%>		
								<option value='<c:out value="<%=beanCommon.getField1()%>"/>' <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(MARKINGUSER)?"selected":""%>'/>><c:out value="<%=beanCommon.getField1()%>"/></option>
								<%
									}%>
							</select></td>
									</tr>
								</tbody>
								</table>
								</td></tr>
								</td></tr>
							</table>
							</td></tr>
						</table>
							</div>
							</TD>
						</TR>
						<tr>
							<td>
							<table class="formtext" width="100%" border=".5"
								cellspacing="0" cellpadding="2" bordercolor="white"
								>
								<TR>
									<TD colspan="4" align="center">
									<INPUT type="button" name="Submit" id="Submit" value="Generate" onclick="runReport();" style="height: 20px;" class="butts">
								</TD>
								</TR>
							</TABLE>
							</td>
						</tr>
					</TABLE>
					</TD>
				</TR>
			</TABLE>
</form>
</body>
</html>