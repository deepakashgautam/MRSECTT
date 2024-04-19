<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="in.org.cris.mrsectt.util.Encrypt"%>
<%@ taglib prefix="enc" uri="/WEB-INF/encrypt.tld"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="java.util.Date"%>
<%@page import="in.org.cris.mrsectt.dao.BudgetReportDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.ReportRefNoDAO"%>
<html> 
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="theme/jquery/jquery.tablefilter.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<!--  <LINK href="${pageContext.request.contextPath}/theme/MasterGreen.css" rel="stylesheet" type="text/css"> -->
<!-- <LINK href="${pageContext.request.contextPath}/theme/MasterModifyAck.css" rel="stylesheet" type="text/css">  -->
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/MasterModifyAck<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/MasterModifyAck.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ReportRefNoDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<%  
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
    String btnClick = request.getParameter("btnClick")!=null ? request.getParameter("btnClick") : "";
    String REFNOFROM = request.getParameter("REFNOFROM")!=null ? request.getParameter("REFNOFROM") : "";
    String REFNOTO = request.getParameter("REFNOTO")!=null ? request.getParameter("REFNOTO") : "";
    String VIPSTATUS = request.getParameter("VIPSTATUS")!=null ? request.getParameter("VIPSTATUS") : "";
    String LETDATECONDITION = request.getParameter("LETDATECONDITION")!=null ? request.getParameter("LETDATECONDITION") : "";
    String LETDATEFROM = request.getParameter("LETDATEFROM")!=null ? request.getParameter("LETDATEFROM") : "";
    String LETDATETO = request.getParameter("LETDATETO")!=null ? request.getParameter("LETDATETO") : "";
    String INCDATECONDITION = request.getParameter("INCDATECONDITION")!=null ? request.getParameter("INCDATECONDITION") : "";
    String INCDATEFROM = request.getParameter("INCDATEFROM")!=null ? request.getParameter("INCDATEFROM") : "";
    String INCDATETO = request.getParameter("INCDATETO")!=null ? request.getParameter("INCDATETO") : "";
    String REFNAME = request.getParameter("REFNAME")!=null ? request.getParameter("REFNAME") : "";
    String STATECODE = request.getParameter("STATECODE")!=null ? request.getParameter("STATECODE") : "";
    String SUBJECTCODE = request.getParameter("SUBJECTCODE")!=null ? request.getParameter("SUBJECTCODE") : "";
    String SUBJECT = request.getParameter("SUBJECT")!=null ? request.getParameter("SUBJECT") : "";
    String INDATE_DRPDWN = request.getParameter("INDATE_DRPDWN")!=null ? request.getParameter("INDATE_DRPDWN") : "";
    String INDATE_DRPDWN2 = request.getParameter("INDATE_DRPDWN2")!=null ? request.getParameter("INDATE_DRPDWN2") : "";
    String MRREMARK = request.getParameter("MRREMARK")!=null ? request.getParameter("MRREMARK") : "";
    String REMARK = request.getParameter("REMARK")!=null ? request.getParameter("REMARK") : "";
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
    String cond = "";
   	String msg = "";
    String querySubject = "SELECT DISTINCT SUBJECTCODE, (SELECT SUBJECTNAME FROM MSTSUBJECT X WHERE X.SUBJECTCODE= A.SUBJECTCODE AND X.SUBJECTTYPE = 'R') SUBJECTNAME FROM TRNREFERENCE A WHERE A.SUBJECTCODE IS NOT NULL"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 2);
	
	String queryState = "SELECT DISTINCT A.STATECODE, (SELECT STATENAME FROM MSTSTATE X WHERE X.STATECODE=A.STATECODE) FROM TRNREFERENCE A WHERE A.STATECODE IS NOT NULL ORDER BY A.STATECODE"; 
	ArrayList<CommonBean> stateList = (new CommonDAO()).getSQLResult(queryState, 2);
	
//	String queryStatus = "SELECT DISTINCT A.VIPSTATUS FROM TRNREFERENCE A WHERE A.VIPSTATUS IS NOT NULL ORDER BY A.VIPSTATUS"; 
//	ArrayList<CommonBean> statusList = (new CommonDAO()).getSQLResult(queryStatus, 1);
	
    ArrayList<CommonBean> dataArr = new ArrayList<CommonBean>();
    if(btnClick.equals("GO"))
    {
		dataArr = (new BudgetReportDAO()).getData(REFNOFROM,REFNOTO,INDATE_DRPDWN,INDATE_DRPDWN2,LETDATEFROM,LETDATETO,REFNAME,STATECODE,SUBJECTCODE,SUBJECT,INCDATEFROM,INCDATETO,VIPSTATUS,MRREMARK,REMARK,sessionBean.getLOGINASROLEID());
		if(dataArr.size()>0){	msg = ""; } else { msg = "No records found... !";}
	}
%>
<script>
function popupGallery(refId, flg)
{
	var url="ImageGallery.jsp?refId="+refId+"&flg="+flg;
	window.open(url,"","toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,left=0,top=0,scrollbars=1,resizable=1");
}

function getIndate(obj)
{
	var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
	var ISCONF ='<c:out value="<%= sessionBean.getISCONF() %>"/>';
	var REFNO = obj.value;
		if(REFNO.length >0 )
		{	
			if(REFNO.substring(1,2) == '/')
			{
				if(REFNO.length > 0)
				{
					ReportRefNoDAO.getIncomingDate(ROLEID, REFNO, ISCONF, getInDateData);
				}
			}else
			{
				alert('Not a valid Ref. No.');
				document.getElementById("REFNOFROM").value = "";
				document.getElementById("REFNOFROM").focus();
			}
		}
		else
		{
			return 0;
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

function getIndate2(obj)
{
	var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
	var ISCONF ='<c:out value="<%= sessionBean.getISCONF() %>"/>';
	var REFNO = obj.value;
	if(REFNO.length >0 ){
		if(REFNO.substring(1,2) == '/'){
			if(REFNO.length > 0){
				ReportRefNoDAO.getIncomingDate(ROLEID, REFNO, ISCONF, getInDateData2);
			}
		} else{
			alert('Not a valid Ref. No.');
			document.getElementById("REFNOTO").value = "";
			document.getElementById("REFNOTO").focus();
		}
	}else { return 0;}}

function getInDateData2(data)
{
	if(document.getElementById("INDATE_DRPDWN2").options.length > 0)
	{
		document.getElementById("INDATE_DRPDWN2").options.length=0;
	}
  if(data.length > 0)
  {
  	document.getElementById("INDATE_DRPDWN2").style.display = "block" ;
  	var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop];
		 
  		var indate = data;
  		var opt = document.createElement("option");
  		document.getElementById("INDATE_DRPDWN2").options.add(opt);
  		opt.text = trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE;
    	opt.value = trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE;
  	}
  	document.getElementById("INDATE_DRPDWN2").focus();
  }
  else
  {
  	document.getElementById("INDATE_DRPDWN2").style.display = "none";
  	document.getElementById("INDATE_DRPDWN2").options.length=0;
  	document.getElementById("search").focus();
  }
}

function submitPage()
{
	var refNo = window.document.frm.REFNOFROM.value;
	var inDate = window.document.frm.INCDATEFROM.value;
	var inDateF = window.document.frm.INDATE_DRPDWN.value;
	var inDateT = window.document.frm.INDATE_DRPDWN2.value;
	if(refNo.length > 0)
	{
		if( chkblank(window.document.frm.REFNOFROM) && chkblank(window.document.frm.REFNOTO)){
			if(inDateF.length > 0 && inDateT.length > 0) {
				document.frm.btnClick.value="GO";
				document.frm.submit();
			}else {	alert('Select Reference No. From/To first. e.g.(MR/A/1/1900    01/01/1900)!');	}
 		}
 	}
	if(inDate.length > 0)
	{
		if( chkblank(window.document.frm.INCDATEFROM) && chkblank(window.document.frm.INCDATETO)){
	  		document.frm.btnClick.value="GO";
  			document.frm.submit();
 		}
 	}
}

function enable(obj){
	var objVal = obj.value;
	if(objVal==1){
		window.document.getElementById("REFNOFROM").readOnly = false;
		window.document.getElementById("REFNOTO").readOnly = false;
		
		window.document.getElementById("INCDATEFROM").readOnly = true;
		window.document.getElementById("INCDATETO").readOnly = true;
		window.document.getElementById("INCDATEFROM").value = "";
		window.document.getElementById("INCDATETO").value = "";
	}
	if(objVal==2){
		window.document.getElementById("REFNOFROM").readOnly = true;
		window.document.getElementById("REFNOTO").readOnly = true;	
		window.document.getElementById("REFNOFROM").value = "";
		window.document.getElementById("REFNOTO").value = "";
		
		window.document.getElementById("INCDATEFROM").readOnly = false;
		window.document.getElementById("INCDATETO").readOnly = false;
		
		if(document.getElementById("INDATE_DRPDWN").options.length > 0) {
			document.getElementById("INDATE_DRPDWN").options.length=0;
			document.getElementById("INDATE_DRPDWN").style.display = "none";
		}
		
		if(document.getElementById("INDATE_DRPDWN2").options.length > 0) {
			document.getElementById("INDATE_DRPDWN2").options.length=0;
			document.getElementById("INDATE_DRPDWN2").style.display = "none";
		}
	}
}

function setToField(thisObj, TOFIELD){	
	var fieldTo = window.document.getElementById(TOFIELD); 
	fieldTo.value = thisObj.value;
	fieldTo.className = 'active';
}

function load() {
	var inDate = window.document.frm.INCDATEFROM.value;
	var inDateF = '<c:out value="<%= REFNOFROM %>"/>';
	var inDateT = '<c:out value="<%= REFNOTO %>"/>';
	if(inDate.length > 0)
	{
		window.document.getElementById("REF_INCOMING").checked = true;
		window.document.getElementById("REFNOFROM").readOnly = true;
		window.document.getElementById("REFNOTO").readOnly = true;	
		window.document.getElementById("REFNOFROM").value = "";
		window.document.getElementById("REFNOTO").value = "";
		
		window.document.getElementById("INCDATEFROM").readOnly = false;
		window.document.getElementById("INCDATETO").readOnly = false;
 	}
 	if(inDateF.length > 0){
 		document.getElementById("INDATE_DRPDWN").style.display = "block";
 		getIndate(document.getElementById("REFNOFROM"));
 	}
 	if(inDateT.length > 0){
 		document.getElementById("INDATE_DRPDWN2").style.display = "block";
 		getIndate2(document.getElementById("REFNOTO"));
 	}
}

  $(document).ready(function(){
    $("#REFNAME").autocomplete("getReferenceNamesData.jsp", {scroll:false});
    $('#REFNAME').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('REFNAME').value = dataarr[0];
		}
	});
  });

	$(document).ready(function() { 
	    $("#sorttable").tablesorter({ 
	        // pass the headers argument and assing a object 
	        headers: { 
	            // assign the tenth column (we start counting zero) 
	          
	            0: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }
	             
	        }
	    }); 
	    
	});
	
	
		
	$(function(){
		$("#sorttable").live("sortEnd", function(){
	  		resetSrNo();
		});
	});
	
	
	$(document).ready(function() {
    	$("#sorttable").tableFilter();
	});
	

	$(document).ready(function() {     // call the tablesorter plugin     
		$("table").tablesorter({         // change the multi sort key from the default shift to alt button         
			sortMultiSortKey: 'altKey'     
		});
	 }); 	
	
	function resetSrNo(){
	
		var content = window.document.getElementById("content");
		//alert(' content length ' + content.rows.length );
		
		var counter = 0;
		for(var i=0;i<content.rows.length ;i++){
			
			
			//alert(content.childNodes[i].style.display);
			
			if(content.childNodes[i].style.display!='none'){
				counter++;
			}
			
			content.childNodes[i].firstChild.innerText = (counter)+'.';
		}
		
		//alert(content.childNodes[1].childNodes[0]);
		//alert(content.childNodes[1].firstChild.innerText);
		//alert(content.childNodes[6].firstChild.innerText);
		
	
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

function PrintReport(){
	var param1= document.getElementById('REFNOFROM').value;
	var param2 = document.getElementById('INDATE_DRPDWN').value +' - '+document.getElementById('INDATE_DRPDWN2').value;
	var url = 'PopUpPrintReport_SearchGeneral.jsp?flag=y&hideftr=Y&param1='+param1+'&param2='+param2;
	window.open(url);
}	
	
</script>

<style>
table.tablesorter thead tr .header {
	background-image: url(images/bg.gif);
	background-repeat: no-repeat;
	background-position: center right;
	cursor: pointer;
}

table.tablesorter thead tr .headerSortUp {
	background-image: url(images/asc.gif);
}

table.tablesorter thead tr .headerSortDown {
	background-image: url(images/desc.gif);
}
</style>
</head>
<body onload="load();">
<form name="frm" action="BudgetReport.jsp" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Search Budget</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table><br /><br />
<center>
<table align="center" style="width: 848px; border: solid 1px black;border-style: dotted">
	<tr>
		<td valign="top" colspan="2" width="406">
		<table border="0" width="100%">
			<tbody>
				<tr valign="top" align="left">
					<td align="right" width="75">Ref. No. :</td>
					<td width="120"><input type="radio" style="height: 10px; width: 10px;"
						name="REF_INCOMING" id="REF_INCOMING0" value="1"
						onclick="enable(this);" checked="checked" /><input type="text" tabindex="1"
						id="REFNOFROM" name="REFNOFROM" size="12" value="<c:out value='<%=REFNOFROM %>'/>"
						style="text-transform: uppercase;"
						onkeypress="allowAlphaNumWithoutSpace('/');"
						onblur="getIndate(this)" /></td>
					<td width="277"><input type="text" id="REFNOTO" name="REFNOTO" size="12" tabindex="3"
						value="<c:out value='<%=REFNOTO %>'/>" style="text-transform: uppercase;"
						onblur="getIndate2(this)" /></td>
				</tr>
				<tr valign="top" align="left">
					<td colspan="2"><select name="INDATE_DRPDWN" id="INDATE_DRPDWN" tabindex="2"
						style="display: none; width: 195px; font-size: 13px; height: 70; position: "
						size="3"></select></td><td width="277"><select name="INDATE_DRPDWN2"
						id="INDATE_DRPDWN2" tabindex="4"
						style="display: none; width: 195px; font-size: 13px; height: 70"
						size="3"></select></td>
				</tr>
			</tbody>
		</table>
		</td><td valign="top" colspan="2" width="436">
		<table border="0" width="100%">
			<tbody>
				<tr>
					<td align="right" width="110">Incoming Dt. :</td>
					<td nowrap="nowrap" width="324"><input type="radio" name="REF_INCOMING" id="REF_INCOMING"
						style="height: 10px; width: 10px;" value="2"
						onclick="enable(this);" /><input type="text" id="INCDATEFROM" tabindex="5"
						name="INCDATEFROM" size="12" value="<c:out value='<%=INCDATEFROM %>'/>"
						readonly="readonly"  onkeyup="setToField(this, 'INCDATETO');"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="text" id="INCDATETO" name="INCDATETO" size="12" tabindex="6"
						value="<c:out value='<%=INCDATETO %>'/>" readonly="readonly" /></td>
				</tr>
			</tbody>
		</table>
		</td></tr>
</table></center>
<center>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="width: 848px; border: solid 1px black;border-style: dotted"">
	<tr align="left">
		<td width="93">Ref. Dated :</td><td nowrap="nowrap" valign="top" width="270"><input
			type="text" id="LETDATEFROM" name="LETDATEFROM" size="12" tabindex="7"
			value="<c:out value='<%=LETDATEFROM %>'/>" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="LETDATETO" tabindex="8"
			name="LETDATETO" size="12" value="<c:out value='<%=LETDATETO %>'/>" /></td>
			<td nowrap="nowrap" valign="top" width="107">Received From :</td>
			<td colspan="3" width="376"><input type="text" name="REFNAME"
			id="REFNAME" size="34" tabindex="9"
			style="background-image: url('images/search.png'); background-position: right; background-repeat: no-repeat;"
			value="<c:out value='<%=REFNAME%>'/>" /></td>
	</tr>
	<tr align="left">
																	<td width="93">Status :</td>
																	<td width="270"><input type="text" name="VIPSTATUS" tabindex="10"
			id="VIPSTATUS" value="<c:out value='<%= VIPSTATUS %>'/>" size="35" /></td>
																	<td width="107">State :</td><td colspan="3" width="376">
		<select name="STATECODE" style="width: 120px" tabindex="11">
			<option value=""></option>
			<%
														for(int i=0;i<stateList.size();i++){
														CommonBean beanCommon = (CommonBean) stateList.get(i);
														%>
			<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
				<%=STATECODE.equals(beanCommon.getField1())? "selected":"" %>><c:out value='<%=beanCommon.getField2()%>'/></option>
			<%
														}%>
		</select></td>
												</tr>
													<tr align="left">
														<td width="93">Sub. :</td><td width="270"><select
			name="SUBJECTCODE" style="width: 120px" tabindex="12">
			<option value=""></option>
			<%
																		for(int i=0;i<subjectList.size();i++){
																		CommonBean beanCommon = (CommonBean) subjectList.get(i);
																		%>
			<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
				<%=SUBJECTCODE.equals(beanCommon.getField1())? "selected":"" %>><c:out value='<%=beanCommon.getField2()%>'/></option>

			<%
																	}%>
		</select></td>
														<td width="107">Subject :</td><td colspan="3" width="376"><input type="text" name="SUBJECT" size="35" value="<c:out value='<%=SUBJECT%>'/>" tabindex="13"/></td></tr>
												<tr align="left">
													<td width="93">MR's Remarks :</td><td width="270"><input tabindex="14"
			type="text" name="MRREMARK" size="35" value="<c:out value='<%=MRREMARK%>'/>" /></td><td width="107">Remarks :</td><td colspan="3" width="376"><input tabindex="15"
			type="text" name="REMARK" size="35" value="<c:out value='<%=REMARK%>'/>" /></td></tr>
	<tr align="left">
		<td width="93"><br />
		</td>
		<td width="270"></td>
		<td width="107"></td>
		<td colspan="3" width="376"></td>
	</tr>
	<tr align="left">
											<td colspan="6" align="center">
											<input style="height: 20px;" class="butts" type="button" name="btngo" id="btngo" value=" Submit " onclick="submitPage('GO');" tabindex="16"/>
												<input type="hidden" name="btnClick" id="btnClick" value="" /></td>
										</tr>
									</table>
										<%
											if ((dataArr != null) && (dataArr.size() != 0)) {
										%>
										<table align="center">
										  <tr><th colspan="11"><img width="20" height="20" src="images/printer_new.png" onclick="PrintReport();"></img></th></tr>
										</table>
										<div id="reportData">
										<table class="tablesorter" id="sorttable" align="center">
										  <thead>
										<tr onclick="resetSrNo();">
											<th >#</th>
											<th >&nbsp;</th>
											<th>Ref. No.</th>
											<th>Seq. No.</th>
											<th>Incoming Date</th>
											<th>Ref. Dated</th>
											<th>State</th>
											<th>Received From</th>
											<th>Status</th>
											<th>Sub.</th>
											<th>Subject</th>
											<th>MR's Remarks</th>
											<th>Remarks</th>
										</tr>
										</thead>
										  <tbody id="content">
										  <%
										  String attachment = "";
										  for (int i = 0; i < dataArr.size(); i++) {
												CommonBean cmnbn = (CommonBean) dataArr.get(i);
												String field13Enc=Encrypt.encrypt(cmnbn.getField13());
												attachment = (cmnbn.getField12().equalsIgnoreCase("1")? "<img border='0' alt='Image Gallery' src='images/stn.gif' onclick='popupGallery(\""+field13Enc+"\",\""+field13Enc+"\");'>": "");
										   %>
										   <tr >  
					    						<td><%=i+1%>.</td>
					    						<td><c:out value='<%=attachment%>' escapeXml="false"/></td>
					    						<td><c:out value='<%=cmnbn.getField1()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField2()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField3()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField4()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField5()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField6()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField7()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField8()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField9()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField10()%>'/></td>
					    						<td><c:out value='<%=cmnbn.getField11()%>'/></td>
										  </tr>
										  <%
										   }
										   %>
										   </tbody>
										</table>
										</div>
										 <%
										   }else {
										   %><br /><table><tr><td><font color="red"><c:out value='<%= msg %>'/></font></td></tr></table><%} %>
  </form> 
</body>
</html>