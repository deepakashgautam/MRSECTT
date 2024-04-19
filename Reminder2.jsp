<html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.dao.ReportRefNoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<head>
<script type="text/javascript" src="script/scripts.js"></script>
<script type="text/javascript" src="script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ReportRefNoDAO.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ReminderAutoDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<% 
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
    
    String queryRefClass = "SELECT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
/*    
	String querySubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME " +    
  						  "	FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND DEPTCODE='"+sessionBean.getDEPTCODE()+"' ORDER BY 2"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 2);
*/
	String querySubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID AND A.SUBJECTTYPE='R' ) SUBJECTNAME " +    
  						  "	FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' ORDER BY 2"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 2);
	/*
 	String queryMarkingTo = " SELECT B.TENUREID, (SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.ROLEID ) ROLENAME "+ 
 							" FROM MSTTENURE B WHERE B.ISACTIVE = 'Y' AND TENUREID <> '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	*/
/*	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND DEPTCODE='"+sessionBean.getDEPTCODE()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
*/
	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	
	String FREMDATE=request.getParameter("FREMDATE")!=null ? request.getParameter("FREMDATE") : "";
    String TREMDATE=request.getParameter("TREMDATE")!=null ? request.getParameter("TREMDATE") : "";
	String CLASS=request.getParameter("CLASS")!=null ? request.getParameter("CLASS") : "";
	String DEPT=request.getParameter("DEPT")!=null ? request.getParameter("DEPT") : "";
	String REQOF=request.getParameter("REQOF")!=null ? request.getParameter("REQOF") : "";
	String STATE=request.getParameter("STATE")!=null ? request.getParameter("STATE") : "";
	String REMARKS=request.getParameter("REMARKS")!=null ? request.getParameter("REMARKS") : "";
	String DREMARKS=request.getParameter("DREMARKS")!=null ? request.getParameter("DREMARKS") : "";
	String SUBJECT=request.getParameter("SUBJECT")!=null ? request.getParameter("SUBJECT") : "";
	String SIGNEDBY=request.getParameter("SIGNEDBY")!=null ? request.getParameter("SIGNEDBY") : "";

	String REMARKSINGLE=request.getParameter("REMARKSINGLE")!=null ? request.getParameter("REMARKSINGLE") : "";
	String SIGNEDBYSINGLE=request.getParameter("SIGNEDBYSINGLE")!=null ? request.getParameter("SIGNEDBYSINGLE") : "";
	
	String querydept = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2";  
 	ArrayList<CommonBean> deptList = (new CommonDAO()).getSQLResult(querydept, 2);
 	
 	String queryState = "SELECT STATECODE, STATENAME FROM MSTSTATE"; 
	ArrayList<CommonBean> stateList = (new CommonDAO()).getSQLResult(queryState, 2);
 	
 	String querySignedBy = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);
 
%>

<script type="text/javascript">

  $(document).ready(function(){
    $("#REQOF").autocomplete("getReferenceNamesData.jsp", {scroll:false});
    $('#REQOF').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('REQOF').value = dataarr[0];
		}
	});
  });
  

function unhideTR(row)
{
   row.style.display = '';
}

function hideTR(row)
{
   row.style.display = 'none';
}

function hideField()
{
for (var i=0;i<window.document.frm.REP.length;i++)
{
if(window.document.frm.REP[i].checked==true)
  {
    if(window.document.frm.REP[i].value==1)
	{
 //   unhideTR(document.getElementById("tr10"));
//	unhideTR(document.getElementById("tr11"));
	document.getElementById("reminderDiv").style.display="none";
	document.getElementById("reminderDivSingle").style.display="block";
    document.frm.reportNumber.value=document.frm.REP[i].value;
    document.getElementById("refNo").focus();
    }
    else if(window.document.frm.REP[i].value==2)
	{
  //  hideTR(document.getElementById("tr10"));
//	hideTR(document.getElementById("tr11"));
	document.getElementById("reminderDiv").style.display="block";
	document.getElementById("reminderDivSingle").style.display="none";
    document.frm.reportNumber.value=document.frm.REP[i].value;
    document.getElementById("CLASS").focus();
	}
	else if(window.document.frm.REP[i].value==3)
	{
  //  hideTR(document.getElementById("tr10"));
//	hideTR(document.getElementById("tr11"));
	document.getElementById("reminderDiv").style.display="block";
	document.getElementById("reminderDivSingle").style.display="none";
    document.frm.reportNumber.value=document.frm.REP[i].value;
    document.getElementById("CLASS").focus();
	}
	else if(window.document.frm.REP[i].value==4)
	{
  //  hideTR(document.getElementById("tr10"));
//	hideTR(document.getElementById("tr11"));
	document.getElementById("reminderDiv").style.display="block";
	document.getElementById("reminderDivSingle").style.display="none";
    document.frm.reportNumber.value=document.frm.REP[i].value;
    document.getElementById("CLASS").focus();
	}
   }
  }
}

function popupReminderSingle(obj)
{
	var refNo = window.document.getElementById("refNo").value;
	if(chkblank(document.frm.refNo) && chkblank(document.frm.SIGNEDBYSINGLE)) {
		var INDATE_DRPDWN = document.frm.INDATE_DRPDWN.value;
		var REMARKSINGLE = document.frm.REMARKSINGLE.value;
		var SIGNEDBYSINGLE = document.frm.SIGNEDBYSINGLE.value;
		if(INDATE_DRPDWN.length > 0) {
			var url="SingleReminderReport.jsp?INDATE_DRPDWN="+INDATE_DRPDWN+"&REMARKS="+REMARKSINGLE+"&SIGNEDBY="+SIGNEDBYSINGLE;
		 	var width  = 920;
			var height = 600;
			var left   = (screen.width  - width)/2;
			var top    = (screen.height - height)/2;
			var params = 'width='+width+', height='+height;
			params += ', top=20, left='+left;
			params += ', directories=no';
			params += ', location=no';
			params += ', menubar=no';
			params += ', resizable=no';
			params += ', scrollbars=yes';
			params += ', status=no';
			params += ', toolbar=no';
			var win = window.open(url ,"popupReminderSingle",params);
			win.focus();
		}else {		alert('Please select atleast one Ref.No./Incoming Date.');	}
	}
}

function patchSingleReminder(obj)
{
	var refNo = window.document.getElementById("refNo").value;
	if(chkblank(document.frm.refNo) && chkblank(document.frm.SIGNEDBYSINGLE)) {
		var INDATE_DRPDWN = document.frm.INDATE_DRPDWN.value;
		var REMARKSINGLE = document.frm.REMARKSINGLE.value;
		var SIGNEDBYSINGLE = document.frm.SIGNEDBYSINGLE.value;
		var LOGINID = '<c:out value="<%= sessionBean.getLOGINID()%>"/>';
		if(INDATE_DRPDWN.length > 0) {
			ReminderAutoDAO.patchSingleReminder(INDATE_DRPDWN,REMARKSINGLE,SIGNEDBYSINGLE,LOGINID,'3',getPatchedData);
		}else {		alert('Please select atleast one Ref.No./Incoming Date.');	}
	}
}

function patchSinglePaperReminder(obj)
{
	if(chkblank(document.frm.refNo) && chkblank(document.frm.SIGNEDBYSINGLE)) {
		var INDATE_DRPDWN = document.frm.INDATE_DRPDWN.value;
		var REMARKSINGLE = document.frm.REMARKSINGLE.value;
		var SIGNEDBYSINGLE = document.frm.SIGNEDBYSINGLE.value;
		var LOGINID = '<c:out value="<%= sessionBean.getLOGINID()%>"/>';
		if(INDATE_DRPDWN.length > 0) {
			ReminderAutoDAO.patchSingleReminder(INDATE_DRPDWN,REMARKSINGLE,SIGNEDBYSINGLE,LOGINID,'1',getPaperPatchedData);
		}else {		alert('Please select atleast one Ref.No./Incoming Date.');	}
	}
}

function popupReminder(obj)
{
	
	var FREMDATE = document.frm.FREMDATE.value;
	var TREMDATE = document.frm.TREMDATE.value;
	var CLASS = document.frm.CLASS.value;
	var DEPT = document.frm.DEPT.value;
	var STATE = document.frm.STATE.value;
	var REMARKS = document.frm.REMARKS.value;
	var DREMARKS = document.frm.DREMARKS.value;
	var SUBJECT = document.frm.SUBJECT.value;
	var SIGNEDBY = document.frm.SIGNEDBY.value;
	var REQOF = document.frm.REQOF.value;
	var repno = document.frm.reportNumber.value
	var vvip = 1;
	if(chkblank(document.frm.FREMDATE) && chkblank(document.frm.TREMDATE) && chkblank(document.frm.SIGNEDBY)){
		var url=""; 
		if(repno==2){
		url="ReminderReport.jsp?FREMDATE="+FREMDATE+"&TREMDATE="+TREMDATE+"&CLASS="+CLASS+"&DEPT="+DEPT+"&STATE="+STATE+"&REMARKS="+REMARKS+"&DREMARKS="+DREMARKS+"&SUBJECT="+SUBJECT+"&SIGNEDBY="+SIGNEDBY+"&REQOF="+REQOF;
		}else if(repno==3){
		url="ReminderReportWOContent.jsp?FREMDATE="+FREMDATE+"&TREMDATE="+TREMDATE+"&CLASS="+CLASS+"&DEPT="+DEPT+"&STATE="+STATE+"&REMARKS="+REMARKS+"&DREMARKS="+DREMARKS+"&SUBJECT="+SUBJECT+"&SIGNEDBY="+SIGNEDBY+"&REQOF="+REQOF;
		}
		if(repno==4){
			url="ReminderReport.jsp?FREMDATE="+FREMDATE+"&TREMDATE="+TREMDATE+"&vvip="+vvip+"&CLASS="+CLASS+"&DEPT="+DEPT+"&STATE="+STATE+"&REMARKS="+REMARKS+"&DREMARKS="+DREMARKS+"&SUBJECT="+SUBJECT+"&SIGNEDBY="+SIGNEDBY+"&REQOF="+REQOF;
			}
	 	 var width  = 1000;
		 var height = 600;
		 var left   = (screen.width  - width)/2;
		 var top    = (screen.height - height)/2;
		 var params = 'width='+width+', height='+height;
		 params += ', top=20, left='+left;
		 params += ', directories=no';
		 params += ', location=no';
		 params += ', menubar=no';
		 params += ', resizable=no';
		 params += ', scrollbars=yes';
		 params += ', status=no';
		 params += ', toolbar=no';		
		var win = window.open(url ,"popupReminder",params);
		win.focus();
		}
//	patchPaperReminder();
}

function patchReminder(obj)
{
	var FREMDATE = document.frm.FREMDATE.value;
	var TREMDATE = document.frm.TREMDATE.value;
	var CLASS = document.frm.CLASS.value;
	var DEPT = document.frm.DEPT.value;
	var STATE = document.frm.STATE.value;
	var REMARKS = document.frm.REMARKS.value;
	var SIGNEDBY = document.frm.SIGNEDBY.value;
	var REQOF = document.frm.REQOF.value;
	var ROLEID = '<c:out value="<%= sessionBean.getLOGINASROLEID()%>"/>';
	var TENUREID = '<c:out value="<%= sessionBean.getTENUREID()%>"/>';
	var LOGINID = '<c:out value="<%= sessionBean.getLOGINID()%>"/>';
	var ISCONF = '<c:out value="<%= sessionBean.getISCONF()%>"/>';
	
	if(chkblank(document.frm.FREMDATE) && chkblank(document.frm.TREMDATE) && chkblank(document.frm.SIGNEDBY)){
		ReminderAutoDAO.dataPatched(ROLEID,TENUREID,LOGINID,FREMDATE,TREMDATE,CLASS,DEPT,STATE,REMARKS,SIGNEDBY,REQOF,ISCONF,'4',getPatchedData);
	}
}

function getPatchedData(data) {
	if(data.length > 0) {
		alert(data);
	}
}

function patchPaperReminder(obj)
{
	if(chkblank(document.frm.FREMDATE) && chkblank(document.frm.TREMDATE) && chkblank(document.frm.SIGNEDBY)){
	var FREMDATE = document.frm.FREMDATE.value;
	var TREMDATE = document.frm.TREMDATE.value;
	var CLASS = document.frm.CLASS.value;
	var DEPT = document.frm.DEPT.value;
	var STATE = document.frm.STATE.value;
	var REMARKS = document.frm.REMARKS.value;
	var DREMARKS = document.frm.DREMARKS.value;
	var SUBJECT = document.frm.SUBJECT.value;
	var SIGNEDBY = document.frm.SIGNEDBY.value;
	var REQOF = document.frm.REQOF.value;
	var ROLEID = '<c:out value="<%= sessionBean.getLOGINASROLEID()%>"/>';
	var TENUREID = '<c:out value="<%= sessionBean.getTENUREID()%>"/>';
	var LOGINID = '<c:out value="<%= sessionBean.getLOGINID()%>"/>';
	var ISCONF = '<c:out value="<%= sessionBean.getISCONF()%>"/>';
		ReminderAutoDAO.dataPatched(ROLEID,TENUREID,LOGINID,FREMDATE,TREMDATE,CLASS,DEPT,STATE,REMARKS,DREMARKS,SUBJECT,SIGNEDBY,REQOF,ISCONF,'2',getPaperPatchedData);
	}
}

function getPaperPatchedData(data) {
	if(data.length > 0) {
		alert('Data Patched Successfully...!');
	} else { alert('Data Patching Failed...!');	}
}

function setChkBlank()
{
for (var i=0;i<window.document.frm.REP.length;i++)
{
if(window.document.frm.REP[i].checked==true)
  {
	if(window.document.frm.REP[i].value==1)
	{
		if(chkblank(window.document.getElementById("diarynofrom")) && chkblank(window.document.getElementById("diarynoto")) && chkblank(window.document.getElementById("datefrom")) && chkblank(window.document.getElementById("dateto")))
		{return 'true'; } else {return 'false';}
	}
	else if(window.document.frm.REP[i].value==2)
	{
		if(chkblank(window.document.getElementById("datefrom")) && chkblank(window.document.getElementById("dateto")) && chkblank(window.document.getElementById("receivedfrom")))
		{return 'true'; } else {return 'false';}
	}
	else if(window.document.frm.REP[i].value==3)
	{
		if(chkblank(window.document.getElementById("searchfor")) && chkblank(window.document.getElementById("datefrom")) && chkblank(window.document.getElementById("dateto")))
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
	var aa = setChkBlank();
	if(aa == 'false'){
		return false;
	}
	else {
		var flag = true;
		var diarynofrom = window.document.frm.diarynofrom;
		var diarynoto = window.document.frm.diarynoto;
		var searchfor = window.document.frm.searchfor;
		var receivedfrom = window.document.frm.receivedfrom;
		var datefrom = window.document.frm.datefrom;
		var dateto = window.document.frm.dateto;
		var refclass = window.document.frm.refclass;
		var subjectcode = window.document.frm.subjectcode;
		var markto = window.document.frm.markto;        
		if(flag)
		{
			window.document.frm.method="post";
			window.document.frm.target="_new";
			window.document.frm.action="GeneralReportsController";
			window.document.frm.submit();
		}
	}
 }

function getIndate(obj)
{
	var refNo = obj.value;
	if(refNo.length > 0){
		if((refNo.substr(1,1)) == '/'){
			var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
			var ISCONF ='<c:out value="<%= sessionBean.getISCONF() %>"/>';
			ReportRefNoDAO.getIncomingDate(ROLEID,refNo,ISCONF,getInDateData);
		}else { alert('Not a valid Ref.No.'); window.document.getElementById("refNo").focus();	}
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
  else{
  	document.getElementById("INDATE_DRPDWN").style.display = "none";
  	document.getElementById("INDATE_DRPDWN").options.length=0;
  	document.getElementById("Submit").focus();
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
	 
	 if(window.event.keyCode==119){
				//alert((cpval.length));
				var maxlength=parseInt(objvalue.length);
				
				obj.value=obj.value+'(Personally Handed Over)';
				
			}
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
<body onload="hideField();">
<form name="frm" id="frm" action="" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Reminder </b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table><br>
				<center><fieldset style="width: 60%; overflow: auto"><table id="data" width="93%" align="center">
					<tbody>
						<tr>
			<td align="left" height="18" colspan="3">&nbsp;&nbsp;</td>
		</tr>
						<tr id="a1" class="treven">
							<td align="left" width="189"><input type="radio" name="REP" id="REP" value="1" onclick="hideField();" checked="checked">&nbsp;Reminder (Single)</td>
							<td align="left" width="165"><input type="radio" name="REP" id="REP" value="2" onclick="hideField();">&nbsp;Reminder (All)</td>
							<td align="left" width="237"><input type="radio" name="REP" id="REP" value="3" onclick="hideField();">&nbsp;Reminder (W/O Covering Letter)</td>
							<td align="left" width="165"><input type="radio" name="REP" id="REP" value="4" onclick="hideField();">&nbsp;Reminder (CM,Gov,Min)</td>
		</tr>
			<tr>
			<td align="left" height="18" colspan="3">&nbsp;&nbsp;</td>
		</tr>
					</tbody>
				</table></fieldset></center> <br>
				<center><fieldset style="width: 60%; text-align: center">
<div id="reminderDiv"><table border="0">
									
								<tbody><tr align="center"><td align="left">Ref.Class :&nbsp; &nbsp;</td><td nowrap="nowrap"><select style="width: 120px;height: 50px" multiple="multiple" name="CLASS" id="CLASS" tabindex="1">
								<option value="" selected="selected">ALL</option>
								<%
										for(int i=0;i<refClassList.size();i++){
										CommonBean beanCommon=(CommonBean) refClassList.get(i);
										%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <%=beanCommon.getField1().equalsIgnoreCase(CLASS)?"selected":""%>><c:out value='<%=beanCommon.getField1()%>'/></option>
								<%
									}%>
							</select><input type="hidden" name="btnClick" id="btnClick" value="<c:out value='<%=request.getParameter("btnClick")!=null ? request.getParameter("btnClick"):""%>'/>" /></td>
											<td nowrap="nowrap">&nbsp; &nbsp;</td>
											<td align="left">Forward To :&nbsp; &nbsp;</td>
							<td nowrap="nowrap" align="left"><select style="width: 120px;height: 50px" multiple="multiple"" name="DEPT" id="DEPT" tabindex="2">
								<option value="" selected="selected">ALL</option>
								<%
										for(int i=0;i<deptList.size();i++){
										CommonBean beanCommon=(CommonBean) deptList.get(i);
										%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <%=beanCommon.getField1().equalsIgnoreCase(DEPT)?"selected":""%>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
									}%>
							</select></td></tr>
										<tr align="center">
											<td align="left">Received From</td>
											<td nowrap="nowrap" align="left"><input type="text" name="REQOF"
												id="REQOF"  tabindex="3" style=" background-image: url('images/search.png'); background-position: right;background-repeat: no-repeat;"
												value="<c:out value='<%=request.getParameter("REQOF")!=null ? request.getParameter("REQOF"):""%>'/>" size="25"/></td>
											<td nowrap="nowrap" align="left">&nbsp; &nbsp;</td>
											<td align="left">State :&nbsp; &nbsp;</td>
											<td nowrap="nowrap" align="left"><select style="width: 120px"
												name="STATE" id="STATE" tabindex="4">
												<option value="" selected="selected">ALL</option>
												<%
										for(int i=0;i<stateList.size();i++){
										CommonBean beanCommon=(CommonBean) stateList.get(i);
										%>
												<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
													<%=beanCommon.getField1().equalsIgnoreCase(STATE)?"selected":""%>><c:out value='<%=beanCommon.getField2()%>'/></option>
												<%
									}%>
											</select></td>
										</tr>
										<tr align="center">
											<td align="left">Target From&nbsp;: &nbsp;</td><td nowrap="nowrap" align="left"><input type="text" name="FREMDATE" id="FREMDATE" tabindex="5" value="<c:out value='<%=request.getParameter("FREMDATE")!=null ? request.getParameter("FREMDATE"):sessionBean.getTENURESTARTDATE()%>'/>" size="25"/> </td>
											<td nowrap="nowrap" align="left">&nbsp; &nbsp;</td>

											<td align="left">Target To  :&nbsp; &nbsp;</td><td nowrap="nowrap" align="left"><input type="text" name="TREMDATE" id="TREMDATE" value="<c:out value='<%=request.getParameter("TREMDATE")!=null ? request.getParameter("TREMDATE"):serverDate%>'/>" size="25" tabindex="6" /> </td>
											
										</tr>
										
										<tr align="center">
											<td align="left">Adjective :&nbsp; &nbsp;</td>
											<td nowrap="nowrap" colspan="4" align="left"><input
												type="text" name="REMARKS" id="REMARKS"
												value="<c:out value='<%=request.getParameter("REMARKS")!=null ? request.getParameter("REMARKS"):""%>'/>"
												size="75" tabindex="7" /></td>
										</tr>

		<tr align="center">
			<td align="left">Subject :</td>
			<td nowrap="nowrap" align="left" colspan="4">
			<input type="text" name="SUBJECT" id="SUBJECT"
				onkeypress="allowAlphaNum('*');"
				value="<c:out value='<%=request.getParameter("SUBJECT")!=null ? request.getParameter("SUBJECT"):""%>'/>"
				size="75" tabindex="7"></td>
		</tr>
				<tr align="center">
											<td align="left">Remarks :&nbsp; &nbsp;</td>
											<td nowrap="nowrap" colspan="4" align="left"><input
												type="text" name="DREMARKS" id="DREMARKS"
												value="<c:out value='<%=request.getParameter("DREMARKS")!=null ? request.getParameter("DREMARKS"):""%>'/>"
												size="75" tabindex="7" /></td>
										</tr>
		<tr align="center">
											<td align="left">Signed By&nbsp;: &nbsp;</td>
											<td nowrap="nowrap" colspan="3" align="left"><select
												style="width: 120px" name="SIGNEDBY" id="SIGNEDBY"
												tabindex="8">
												<option value="" selected="selected">-Select-</option>
												<%
										for(int i=0;i<querySignedByList.size();i++){
										CommonBean beanCommon=(CommonBean) querySignedByList.get(i);
										%>
												<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
													<%=beanCommon.getField1().equalsIgnoreCase(STATE)?"selected":""%>><c:out value='<%=beanCommon.getField2()%>'/></option>
												<%
									}%>
											</select></td>
											
											<!--<td align="left">Only Cabinet Minister, CM and Governors &nbsp;: &nbsp;</td>
											<td> <input type="radio" name="vvip" id="vvip" value="<c:out value='<%=request.getParameter("vvip")!=null ? request.getParameter("vvip"):"1"%>'/>" > yes </td>
											<td> <input type="radio" name="vvip" id="vvip" value="0" > no</td>
											
											--><td nowrap="nowrap">
												<input type="button" value="Paper(All)" tabindex="9" class="butts" onclick="popupReminder(this);" style="height: 20px; width: 70px;"/>
												<input type="button" value="Patch(Paper All)" tabindex="9" class="butts" onclick="patchPaperReminder(this);" style="height: 20px; width: 100px;"/>
												<input type="button" tabindex="10" class="butts" value="E-Reminder(All)" style="height: 20px; width: 100px;"	onclick="patchReminder(this);" /></td>
										</tr>
									</tbody></table></div>
<div id="reminderDivSingle"><table border="0">
									
								<tbody>
										<tr align="center">
											<td align="left" valign="top">Ref.No. :</td>
											<td nowrap="nowrap" colspan="3" align="left" valign="top"><input
				type="text" id="refNo" name="refNo" maxlength="30" size="20"
				onblur="getIndate(this)" tabindex="1"></td>
											<td nowrap="nowrap" align="left" valign="top" rowspan="2"><select
				name="INDATE_DRPDWN" id="INDATE_DRPDWN"
				style="display: none; height: 50px; width: 200px" size="3"
				tabindex="2"></select></td>
										</tr>
		<tr align="center">
			<td align="left" valign="top">Signed By :</td>
			<td nowrap="nowrap" align="left" valign="top" colspan="3"><select tabindex="3"
												style="width: 120px" name="SIGNEDBYSINGLE" id="SIGNEDBYSINGLE">
												<option value="" selected="selected">-Select-</option>
												<%
										for(int i=0;i<querySignedByList.size();i++){
										CommonBean beanCommon=(CommonBean) querySignedByList.get(i);
										%>
												<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
													<%=beanCommon.getField1().equalsIgnoreCase(STATE)?"selected":""%>><c:out value='<%=beanCommon.getField2()%>'/></option>
												<%
									}%>
											</select></td></tr>
		<tr align="center">
											<td align="left">Adjective : &nbsp; &nbsp;</td>
											<td nowrap="nowrap" colspan="4" align="left"><input tabindex="4"
												type="text" name="REMARKSINGLE" id="REMARKSINGLE"
												value="<c:out value='<%=request.getParameter("REMARKSINGLE")!=null ? request.getParameter("REMARKSINGLE"):""%>'/>"
												size="55" />
											<input type="hidden" name="buttonClick" id="buttonClick" value="<c:out value='<%=request.getParameter("buttonClick")!=null ? request.getParameter("buttonClick"):""%>'/>" />
										</td>
										</tr>
		<tr align="center">
			<td align="left"><input type="button" tabindex="5" class="butts" value=" Paper(Single) " style="height: 20px; width: 90px;" onclick="popupReminderSingle(this);"></td>
			<td nowrap="nowrap" align="left" colspan="3">
			<input type="button" tabindex="5" value=" Patch(Paper Single) " class="butts" style="height: 20px; width: 140px;" onclick="patchSinglePaperReminder(this);"></td>
			
		</tr>
		<tr align="center">
		<td nowrap="nowrap" align="right"><input type="button" class="butts"
				tabindex="6" value=" Patch(E-Reminder Single) "
				style="height: 20px; width: 205px;"
				onclick="patchSingleReminder(this);"></td>
		</tr>
		<tr align="center">
			<td align="left"><input type="hidden" name="reportNumber"></td>
			<td nowrap="nowrap" align="left" colspan="3"></td>
			<td nowrap="nowrap" align="right"></td>
		</tr>
	</tbody></table></div>
							</fieldset>
</form>
</body>
</html>