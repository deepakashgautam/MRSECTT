<html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.dao.ReportRefNoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<head>
  <link href="${pageContext.request.contextPath}/theme/sub/style.css" rel="stylesheet" type="text/css" />
  <% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
  <link type="text/css" href="${pageContext.request.contextPath}/theme/autoSuggest.css" rel="stylesheet" />
  <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
	
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">

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
	String querySubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME " +    
  						  "	FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
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
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	String queryreplyCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00'"; 
	ArrayList<CommonBean> replyCode = (new CommonDAO()).getSQLResult(queryreplyCode, 3);
	
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	
	String FREMDATE=request.getParameter("FREMDATE")!=null ? request.getParameter("FREMDATE") : "";
    String TREMDATE=request.getParameter("TREMDATE")!=null ? request.getParameter("TREMDATE") : "";
	String CLASS=request.getParameter("CLASS")!=null ? request.getParameter("CLASS") : "";
	String DEPT=request.getParameter("DEPT")!=null ? request.getParameter("DEPT") : "";
	String REQOF=request.getParameter("REQOF")!=null ? request.getParameter("REQOF") : "";
	String STATE=request.getParameter("STATE")!=null ? request.getParameter("STATE") : "";
	String REMARKS=request.getParameter("REMARKS")!=null ? request.getParameter("REMARKS") : "";
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
    $("#receivedfrom").autocomplete("getReferenceNamesData.jsp", {scroll:false});
    $('#receivedfrom').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('receivedfrom').value = dataarr[0];
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
var isreply = '<c:out value="<%=sessionBean.getISREPLY()%>"/>';
for (var i=0;i<window.document.frm.REP.length;i++)
{
if(window.document.frm.REP[i].checked==true)
  {
	if(window.document.frm.REP[i].value==1)
	{
	unhideTR(document.getElementById("tr1"));
	document.getElementById("normal").style.display = 'block';
	document.getElementById("Ackonly").style.display = 'none';
	hideTR(document.getElementById("tr2"));
	hideTR(document.getElementById("tr8"));
	unhideTR(document.getElementById("tr3"));
    unhideTR(document.getElementById("tr4"));
//    unhideTR(document.getElementById("tr5"));
	hideTR(document.getElementById("tr5"));
    unhideTR(document.getElementById("tr6"));
    unhideTR(document.getElementById("tr7"));
    hideTR(document.getElementById("tr10"));
	hideTR(document.getElementById("tr11"));
	unhideTR(document.getElementById("tr12"));
	if(isreply == "1"){
	unhideTR(document.getElementById("trreply"));
	}
    document.frm.reportNumber.value=document.frm.REP[i].value;
	}
	else if(window.document.frm.REP[i].value==2)
	{
	hideTR(document.getElementById("tr1"));
	hideTR(document.getElementById("tr2"));
	hideTR(document.getElementById("tr8"));
	unhideTR(document.getElementById("tr3"));
    unhideTR(document.getElementById("tr4"));
    unhideTR(document.getElementById("tr5"));
    unhideTR(document.getElementById("tr6"));
    unhideTR(document.getElementById("tr7"));
    hideTR(document.getElementById("tr10"));
	hideTR(document.getElementById("tr11"));
	unhideTR(document.getElementById("tr12"));
	if(isreply == "1"){
	unhideTR(document.getElementById("trreply"));
	}
    document.frm.reportNumber.value=document.frm.REP[i].value;
	}
	else if(window.document.frm.REP[i].value==3)
	{
	hideTR(document.getElementById("tr1"));
	hideTR(document.getElementById("tr8"));
	unhideTR(document.getElementById("tr2"));
	unhideTR(document.getElementById("tr3"));
    unhideTR(document.getElementById("tr4"));
    unhideTR(document.getElementById("tr5"));
    unhideTR(document.getElementById("tr6"));
    unhideTR(document.getElementById("tr7"));
    hideTR(document.getElementById("tr10"));
	hideTR(document.getElementById("tr11"));
	unhideTR(document.getElementById("tr12"));
	if(isreply == "1"){
	unhideTR(document.getElementById("trreply"));
	}
    document.frm.reportNumber.value=document.frm.REP[i].value;
    }
    else if(window.document.frm.REP[i].value==4)
	{
	hideTR(document.getElementById("tr1"));
	hideTR(document.getElementById("tr2"));
	hideTR(document.getElementById("tr3"));
    hideTR(document.getElementById("tr4"));
    hideTR(document.getElementById("tr5"));
    hideTR(document.getElementById("tr6"));
    hideTR(document.getElementById("tr7"));
	hideTR(document.getElementById("tr8"));
	hideTR(document.getElementById("tr9"));
    unhideTR(document.getElementById("tr10"));
	unhideTR(document.getElementById("tr11"));
	hideTR(document.getElementById("tr50"));
	unhideTR(document.getElementById("tr12"));
	
	hideTR(document.getElementById("trreply"));
	
    document.frm.reportNumber.value=document.frm.REP[i].value;
    document.getElementById("refNo").focus();
    }
    else if(window.document.frm.REP[i].value==5)
	{
	hideTR(document.getElementById("tr1"));
	hideTR(document.getElementById("tr2"));
	hideTR(document.getElementById("tr3"));
	hideTR(document.getElementById("tr4"));
	hideTR(document.getElementById("tr5"));
	hideTR(document.getElementById("tr6"));
	hideTR(document.getElementById("tr7"));
    hideTR(document.getElementById("tr8"));
	hideTR(document.getElementById("tr9"));
	unhideTR(document.getElementById("tr10"));
	unhideTR(document.getElementById("tr11"));
	hideTR(document.getElementById("tr12"));
	hideTR(document.getElementById("trreply"));
	document.frm.reportNumber.value=document.frm.REP[i].value;
	document.getElementById("refNo").focus();
    }
    else if(window.document.frm.REP[i].value==6)
	{ 
    unhideTR(document.getElementById("tr1"));
    document.getElementById("normal").style.display = 'none';
	document.getElementById("Ackonly").style.display = 'block';
	hideTR(document.getElementById("tr2"));
	hideTR(document.getElementById("tr8"));
	hideTR(document.getElementById("tr3"));
    hideTR(document.getElementById("tr4"));
//    unhideTR(document.getElementById("tr5"));
	hideTR(document.getElementById("tr5"));
    hideTR(document.getElementById("tr6"));
    hideTR(document.getElementById("tr7"));
     hideTR(document.getElementById("tr9"));
    hideTR(document.getElementById("tr10"));
	hideTR(document.getElementById("tr11"));
	hideTR(document.getElementById("tr12"));
	
	hideTR(document.getElementById("trreply"));
	//alert();
    document.frm.reportNumber.value=document.frm.REP[i].value;
	}
   }
  }
}

function popupReminderSingle(obj)
{
	var INDATE_DRPDWN = document.frm.INDATE_DRPDWN.value;
	var REMARKSINGLE = document.frm.REMARKSINGLE.value;
	var SIGNEDBYSINGLE = document.frm.SIGNEDBYSINGLE.value;
	if(chkblank(document.frm.INDATE_DRPDWN))	{
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
	}
	patchSinglePaperReminder();
}

function patchSingleReminder(obj)
{
	var INDATE_DRPDWN = document.frm.INDATE_DRPDWN.value;
	var REMARKSINGLE = document.frm.REMARKSINGLE.value;
	var SIGNEDBYSINGLE = document.frm.SIGNEDBYSINGLE.value;
	var LOGINID = '<c:out value="<%= sessionBean.getLOGINID()%>"/>';
	if(chkblank(document.frm.INDATE_DRPDWN)) {
		ReminderAutoDAO.patchSingleReminder(INDATE_DRPDWN,REMARKSINGLE,SIGNEDBYSINGLE,LOGINID,'3',getPatchedData);
	}
}

function patchSinglePaperReminder(obj)
{
	var INDATE_DRPDWN = document.frm.INDATE_DRPDWN.value;
	var REMARKSINGLE = document.frm.REMARKSINGLE.value;
	var SIGNEDBYSINGLE = document.frm.SIGNEDBYSINGLE.value;
	var LOGINID = '<c:out value="<%= sessionBean.getLOGINID()%>"/>';
	if(chkblank(document.frm.INDATE_DRPDWN)) {
		ReminderAutoDAO.patchSingleReminder(INDATE_DRPDWN,REMARKSINGLE,SIGNEDBYSINGLE,LOGINID,'1',getPaperPatchedData);
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
	var SIGNEDBY = document.frm.SIGNEDBY.value;
	if(chkblank(document.frm.FREMDATE) && chkblank(document.frm.TREMDATE))
		{
	var url="ReminderReport.jsp?FREMDATE="+FREMDATE+"&TREMDATE="+TREMDATE+"&CLASS="+CLASS+"&DEPT="+DEPT+"&STATE="+STATE+"&REMARKS="+REMARKS+"&SIGNEDBY="+SIGNEDBY;
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
		var win = window.open(url ,"popupReminder",params);
		win.focus();
		}
	patchPaperReminder();
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
	
	if(chkblank(document.frm.FREMDATE) && chkblank(document.frm.TREMDATE)) {
		ReminderAutoDAO.dataPatched(ROLEID,TENUREID,LOGINID,FREMDATE,TREMDATE,CLASS,DEPT,STATE,REMARKS,SIGNEDBY,REQOF,'4',getPatchedData);
	}
}

function getPatchedData(data) {
	if(data.length > 0) {
		alert(data);
	}
}

function patchPaperReminder(obj)
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
	
	if(chkblank(document.frm.FREMDATE) && chkblank(document.frm.TREMDATE)) {
		ReminderAutoDAO.dataPatched(ROLEID,TENUREID,LOGINID,FREMDATE,TREMDATE,CLASS,DEPT,STATE,REMARKS,SIGNEDBY,REQOF,'2',getPaperPatchedData);
	}
}

function getPaperPatchedData(data) {
	if(data.length > 0) {
		return 0;
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
		var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
		var ISCONF ='<c:out value="<%= sessionBean.getISCONF() %>"/>';
		ReportRefNoDAO.getIncomingDate(ROLEID,refNo,ISCONF,getInDateData);
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

function setToField(thisObj, TOFIELD){
	
	var fieldTo = window.document.getElementById(TOFIELD); 
	
	fieldTo.value = thisObj.value;
	fieldTo.className = 'active';

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
	
	$(document).ready(function ()
        {
            $.ui.dialog.defaults.bgiframe = true;
            $("#datefrom").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true'});
            $("#dateto").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true' });
            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
        });
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




function setReply(obj){
	if(obj.checked){
		document.getElementById('reply').value="1";
	}else{
		document.getElementById('reply').value="0";
	}
}
</script>
</head>
<body onload="hideField();">
<form name="frm" id="frm" action="" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Report General</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table><br>
				<center><fieldset style="width: 60%; overflow: auto"><table id="data" width="93%" align="center">
					<tbody>
						<tr>
			<td align="left" height="18" colspan="11">&nbsp;</td>
		</tr>
						<tr id="a1" class="treven">
							<td align="right" ><input type="radio" name="REP" value="3" onclick="hideField();" checked="checked">
							<input type="hidden" name="ISCONF" id="ISCONF" value="<c:out value='<%= sessionBean.getISCONF() %>'/>">
							<input type="hidden" name="ROLEID" id="ROLEID" value="<c:out value='<%= sessionBean.getLOGINASROLEID() %>'/>"></td>
							<td align="left" colspan="2" >Search</td>
							<td align="right" ><input type="radio" name="REP" value="2" onclick="hideField();"></td>
							<td align="left" colspan="2" >Received From</td>
							<td align="right" ><input type="radio" name="REP" value="1" onclick="hideField();"></td>
							<td align="left" colspan="2" >Ref. No.</td>
							<td align="right" ><input type="radio" name="REP" value="5" onclick="hideField();"></td>
							<td align="left" >Ref. No. (Single)</td>
							<td align="right" ><input type="radio" name="REP" value="6" onclick="hideField();"></td>
							<td align="left" >Acknowledgement</td>
		</tr>
		<tr>
			<td align="left" height="18" colspan="11">&nbsp;</td>
		</tr>
					</tbody>
				</table></fieldset></center> <br>
				<center><fieldset style="width: 60%; text-align: center"><table width="70%" id="data" align="center">
								<tbody>
									<tr>
									<td align="left" height="18" colspan="2">
									<input type="hidden" name="reportNumber" size="15">&nbsp;</td>
									</tr>
									<tr id="tr1" >
									<td align="right" width="406">Ref. No. From<font color="red"></font> :</td>
									<td align="left" width="784"><input type="text" name="diarynofrom" id="diarynofrom" size="15" onkeyup="setToField(this, 'diarynoto');"> to&nbsp;
									<input type="text" name="diarynoto" id="diarynoto" size="15" > <span id="normal" style="display:none" >(e.g. A/1)</span><span id="Ackonly" style="display:none">(e.g. A/1/2016)</span></td>
									</tr>
						<tr id="tr2"  style="display: none;">
									<td align="right" height="15" width="406">Search For<font color="red"></font> :</td>
									<td align="left" height="15" width="784"><input type="text" name="searchfor" id="searchfor" value="" size="34"></td>
									</tr>
									<tr id="tr4"  style="display: none;">
									<td align="right" width="406">Incoming Dt.<font color="red"></font> :</td>
									<td align="left" width="784"><input type="text" name="datefrom" id="datefrom" value="<c:out value='<%=sessionBean.getTENURESTARTDATE() %>'/>" size="11"> to
									<input type="text" name="dateto" id="dateto" value="<c:out value='<%= serverDate %>'/>" size="11"></td>
									</tr>
									<tr id="tr3"  style="display: none;">
									<td align="right" width="406">Received From<font color="red"></font> :</td>
									<td align="left" width="784"><input type="text" id="receivedfrom" name="receivedfrom" maxlength="30" value="" size="38" style=" background-image: url('images/search.png'); background-position: right;background-repeat: no-repeat;"></td>
									</tr>
									<tr id="tr5" style="display: none;">
									<td align="right" width="406">Ref. Class :</td>
									<td align="left" width="784"><select name="refclass" id="refclass" class="drop" style="width: 80px">
									<option value="" selected> </option>
			    					<%
										for(int i=0;i<refClassList.size();i++){
										CommonBean beanCommon=(CommonBean) refClassList.get(i);
										%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField1()%>'/></option>
										<%
									}%>
									</select></td>
									</tr>
						<tr id="tr6"  style="display: none;">
							<td align="right" width="406">Sub. :</td>
							<td align="left" width="784"><select name="subjectcode" id="subjectcode" class="drop" style="width: 180px">
								<option value=""> </option>
								<%
										for(int i=0;i<subjectList.size();i++){
										CommonBean beanCommon = (CommonBean) subjectList.get(i);
										%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
									}%>
							</select></td>
						</tr>
						<tr id="tr7" style="display: none;">
									<td align="right" width="406">Forward To :</td>
									<td align="left" width="784"><select name="markto" id="markto" class="drop" style="width: 180px">
									<option value=""></option>
									<%
										for(int i=0;i<markingToList.size();i++){
										CommonBean beanCommon = (CommonBean) markingToList.get(i);
										%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
										<%
									}%>
									</select></td>
									
									</tr>
						<tr id="tr8" style="display: none;" valign="top">
									<td align="right" width="406" valign="top"></td>
									<td align="left" width="784" nowrap="nowrap" valign="top"></td>
									</tr>
						<tr id="tr9">
							<td align="right" width="406">Tag (1,2,3,4) :</td>
							<td align="left" width="784"><input type="text" name="tag" id="tag" value="" size="34"></td>
						</tr>
						<tr id="tr10">
							<td align="right" width="406">Ref. No. :</td>
							<td align="left" width="784"><input type="text" id="refNo" name="refNo" maxlength="30" size="20" onblur="getIndate(this)" tabindex="1"> e.g.(A/1)</td>
						</tr>
						<tr id="tr11">
							<td align="right" width="406"></td>
							<td align="left" width="784">
									<select	name="INDATE_DRPDWN" id="INDATE_DRPDWN" style="display: none; height: 50px; width: 200px" size="3" tabindex="2"></select>
							</td>
						</tr>
						<tr id="tr12">
						<td align="right" width="406">Reply Type:</td>
						<td>
						<select name="REPLYTYPE" id="REPLYTYPE" class="drop">
											<option value="">--All--</option>
											<%
												for(int k=0;k<replyCode.size();k++){
												CommonBean beanCommon = (CommonBean) replyCode.get(k);
											%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
											<%}%>
										</select>
						</td></tr>
						<tr id="trreply" style="display:none;">
					<td align="right" width="222">Reply? : </td>
					<td align="left" width="410"><input type="checkbox" name="chkreply" id="chkreply" onclick="setReply(this);"/><input type="hidden" name="reply" id="reply" value="0"/></td>
				</tr>
					</tbody>
							</table>
							</fieldset>
							<table class="formtext" width="100%" border=".5"
								cellspacing="0" cellpadding="2" bordercolor="white"
								>
								<TR id="tr50">
									<TD colspan="4" align="center">
									<INPUT type="button" name="Submit" id="Submit" value="Submit" onclick="runReport();" style="height: 20px;" class="butts">
									<INPUT type="reset" name="Reset" value="Clear" style="height: 20px;" class="butts">
								</TD>
								</TR>
							</TABLE>
</form>
</body>
</html>