<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.org.cris.mrsectt.dao.ModifyHindi_GDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnFileHdr"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnFileIntMarking"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Text Search - Reference</title>
  <link href="${pageContext.request.contextPath}/theme/sub/style.css" rel="stylesheet" type="text/css" />
<!--   <link type="text/css" href="${pageContext.request.contextPath}/theme/MasterModifyAck.css" rel="stylesheet" /> -->
  <% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
  <link type="text/css" href="${pageContext.request.contextPath}/theme/autoSuggest.css" rel="stylesheet" />
  <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
	
    <SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ModifyHindi_GDAO.js'></SCRIPT>
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
	
	String queryfileStatus1 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'0' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus1 = (new CommonDAO()).getSQLResult(queryfileStatus1, 3);
	
	String queryfileStatus2 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'0' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus2 = (new CommonDAO()).getSQLResult(queryfileStatus2, 3);
	
	String queryIMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> imarkingToList = (new CommonDAO()).getSQLResult(queryIMarkingTo, 2);
	
	String queryreplyCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00'"; 
	ArrayList<CommonBean> replyCode = (new CommonDAO()).getSQLResult(queryreplyCode, 3);
	
	TrnFileHdr fileBean =  (request.getAttribute("fileBean")!=null) ?(TrnFileHdr)request.getAttribute("fileBean"): new TrnFileHdr();
	
%>
 <style type="text/css">  
 
 #frm .inactive{
		color:#999;
		}	 
	#frm .active{
		color:#000;
		}	 		
	#frm .focused{
		color:#000;
		background:#fffee7;
		}	 
		
   
    div.quicksearch 
    {              
      padding-bottom: 10px;      
    }
    		

table.tablesorter thead tr .header {
	background-image: url(images/bg.gif);
	background-repeat: no-repeat;
	background-position: center right;
	cursor: pointer;
	/*background-color : #F6CED8;*/
}

table.tablesorter thead tr .headerSortUp {
	background-image: url(images/asc.gif);
}

table.tablesorter thead tr .headerSortDown {
	background-image: url(images/desc.gif);
}

/* div container containing the form  */
#searchContainer {
	margin:20px;
}
 
/* Style the search input field. */
#field {
	float:left;
	width:300px;
	height:27px;
	line-height:27px;
	text-indent:10px;
	font-family:tahoma, arial, sans-serif;
	font-size:1em;
	color:#333;
	background: #fff;
	border:solid 1px #d9d9d9;
	border-top:solid 1px #c0c0c0;
	border-right:none;
}
 
/* Style the "X" text button next to the search input field */
#delete {
	float:left;
	width:16px;
	height:29px;
	line-height:27px;
	margin-right:15px;
	padding:0 10px 0 10px;
	font-family: "tahoma", "Lucida Sans Unicode",sans-serif;
	font-size:22px;
	background: #fff;
	border:solid 1px #d9d9d9;
	border-top:solid 1px #c0c0c0;
	border-left:none;
}
/* Set default state of "X" and hide it */
#delete #x {
	color:#A1B9ED;
	cursor:pointer;
	display:none;
}
/* Set the hover state of "X" */
#delete #x:hover {
	color:#36c;
}
/* Syle the search button. Settings of line-height, font-size, text-indent used to hide submit value in IE */
#submit {
	cursor:pointer;
	width:50px;
	height: 21px;
	line-height:0;
	font-size:0;
	text-indent:-999px;
	color: transparent;
	background: url(images/icon_search12.png) no-repeat #4d90fe center;
	border: 1px solid #3079ED;
	-moz-border-radius: 2px;
	-webkit-border-radius: 2px;
}
/* Style the search button hover state */
#submit:hover {
	background: url(images/icon_search12.png) no-repeat center #357AE8;
	border: 1px solid #2F5BB7;
}
/* Clear floats */
.fclear {clear:both}

</style>
<script type="text/javascript">

 $(document).ready(function() {
 
    //Setup the sorting for the table with the first column initially sorted ascending
    //and the rows striped using the zebra widget
       // $("#tableOne").tablesorter({ sortList: [[0, 0]], widgets: ['zebra'] });
	label2value();
	
	
});  

 function getsearchDetail(){
	  var inDate = window.document.getElementById("INDATE_DRPDWN").value;
	 if(inDate == "")
	 {
	 	alert('Not a valid File Counter');
	 }else {
	  window.document.getElementById("msgDiv").style.display="none";
	  var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>" />';
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
		ModifyHindi_GDAO.getSearchSub(inDate,ROLEID,dataSearchDetail);
		ModifyHindi_GDAO.getFileIntMarking(inDate,ROLEID,dataSearchIntMark);
	}
}
	
function dataSearchDetail(data){
  if(data.length<=0){
  	alert('No Data Found!!!');
  }
	var refBean = new Array(data.length);
	var counter=0;
	for(var loop=0; loop < data.length; loop++){
		counter++;
		var trnRefBean = data[loop];
		refBean[loop] = data[loop];
		window.document.getElementById("REGISTRATIONNO").value = trnRefBean.REGISTRATIONNO;
		window.document.getElementById("REGISTRATIONDATE").value = trnRefBean.REGISTRATIONDATE;
		window.document.getElementById("FILENO").value = trnRefBean.FILENO;
		window.document.getElementById("DEPARTMENTCODE").value = trnRefBean.DEPARTMENTCODE;
		window.document.getElementById("SUBJECT").value = trnRefBean.SUBJECT;
		window.document.getElementById("RECEIVEDFROM").value = trnRefBean.RECEIVEDFROM;
		
		window.document.getElementById("FILESTATUS1").value = trnRefBean.FILESTATUS1;
		window.document.getElementById("FILESTATUS2").value = trnRefBean.FILESTATUS2;
		window.document.getElementById("REMARKS").value = trnRefBean.REMARKS;
		window.document.getElementById("REPLYTYPE").value = trnRefBean.REPLYTYPE;
		window.document.getElementById("REASON").value = trnRefBean.REASON;

		window.document.getElementById("DMARKINGTO").value = trnRefBean.DMARKINGTO;
		window.document.getElementById("DMARKINGDATE").value = trnRefBean.DMARKINGDATE;
		window.document.getElementById("DREMARKS").value = trnRefBean.DMARKINGREMARKS;
		window.document.getElementById("DMARKINGSEQUENCE").value = trnRefBean.DMARKINGSEQUENCE;
 	}
}

function dataSearchIntMark(data1){
  if(data1.length<=0){
  	alert('No Data Found for Internal Marking!!!');
  }
	var refBean1 = new Array(data1.length);
	var counter=0;
	var targetobj = window.document.getElementById('baserow1');

	var firstTR = targetobj.childNodes[0];
	dwr.util.removeAllRows('baserow1');
	targetobj.appendChild(firstTR);
	
	for(var loop=0; loop < data1.length; loop++){
		counter++;
		var trnRefBean1 = data1[loop];
		refBean1[loop] = data1[loop];
		addrowMarkingWithData(targetobj,trnRefBean1.MARKINGTO,trnRefBean1.MARKINGDATE,trnRefBean1.CHANGEDATE,trnRefBean1.MARKINGSEQUENCE);
 	}
}

function addrowMarkingWithData(targetobj,markingto,markingon,returnon,markingSequence)	{
	

	var idno = targetobj.getElementsByTagName('tr').length;
	newtr=targetobj.childNodes[0].cloneNode(true);
	newtr.style.display='block';
//	newtr.className=targetobj.childNodes.length%2==0?'treven':'trodd';
	newtr.childNodes[0].innerText=idno;
	newtr.childNodes[1].childNodes[0].value=markingto;
	newtr.childNodes[2].childNodes[1].value=markingon;
	newtr.childNodes[3].childNodes[0].value=returnon;
	newtr.childNodes[4].childNodes[0].value=markingSequence;
	targetobj.appendChild(newtr);
}

function getValues(objname) {
		var vals = new Array();
		for(var i=0;i<document.getElementsByName(objname).length;i++)
		{
			vals[i]=document.getElementsByName(objname)[i].value;
		}
		return vals;
	}
	
function saveData(obj)
{
	var fileStatus1 = window.document.getElementById("FILESTATUS1").value;
	var fileStatus2 = window.document.getElementById("FILESTATUS2").value;
	var remarks = window.document.getElementById("REMARKS").value;
	var imarkingToArr = window.document.getElementById("IMARKINGTO");
	var imarkingDateArr = window.document.getElementById("IMARKINGDATE");
	var ireturnDateArr = window.document.getElementById("IRETURNDATE");
	var markingSeqArr = window.document.getElementById("MARKINGSEQUENCE");
	var inDate = window.document.getElementById("INDATE_DRPDWN").value;
	var subject = window.document.getElementById("SUBJECT").value;

	var DMARKINGTO = window.document.getElementById("DMARKINGTO").value;
	var DMARKINGDATE = window.document.getElementById("DMARKINGDATE").value;
	var DREMARKS = window.document.getElementById("DREMARKS").value;
	var DMARKINGSEQUENCE = window.document.getElementById("DMARKINGSEQUENCE").value;
	var REPLYTYPE = window.document.getElementById("REPLYTYPE").value;
	var REASON = window.document.getElementById("REASON").value;

	var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>" />';
	var loginID ='<c:out value="<%= sessionBean.getLOGINID() %>" />';
	ModifyHindi_GDAO.updateDataFM2(fileStatus1,fileStatus2,remarks,getValues(imarkingToArr.name),getValues(imarkingDateArr.name),getValues(ireturnDateArr.name),getValues(markingSeqArr.name),inDate,ROLEID,loginID,DMARKINGTO,DMARKINGDATE,DREMARKS,DMARKINGSEQUENCE,REPLYTYPE,REASON,subject,dataUpdateDetail);	
}

function dataUpdateDetail(data){
  if(data.length>0){
  	alert(data);
  	clearForm();
  	document.getElementById("searchString").focus();
  }
 }

function clearForm(){
	window.document.getElementById("REGISTRATIONNO").value = "";
	window.document.getElementById("REGISTRATIONDATE").value = "";
	window.document.getElementById("FILENO").value = "";
	window.document.getElementById("DEPARTMENTCODE").value = "";
	window.document.getElementById("SUBJECT").value = "";
	window.document.getElementById("RECEIVEDFROM").value = "";
	window.document.getElementById("FILESTATUS1").value = "";
	window.document.getElementById("FILESTATUS2").value = "";
	window.document.getElementById("REMARKS").value = "";
	window.document.getElementById("DMARKINGTO").value = "";
	window.document.getElementById("DMARKINGDATE").value = "";
	window.document.getElementById("DREMARKS").value = "";
	window.document.getElementById("REPLYTYPE").value = "";
	window.document.getElementById("REASON").value = "";
	document.getElementById("INDATE_DRPDWN").options.length=0;
	var targetobj = window.document.getElementById('baserow1');
	var firstTR = targetobj.childNodes[0];
	dwr.util.removeAllRows('baserow1');
	targetobj.appendChild(firstTR);
	document.getElementById('searchString').focus();
}

function chkRole(obj){
	var roleName = obj.value;
	if(roleName.length>0){
		ModifyHindi_GDAO.chkRoleName(roleName,dataRole);
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
		ModifyHindi_GDAO.chkRoleName(roleName,dataRole2);
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

function functionKey(obj,objvalue){
	//alert('Hi'); 
	if(!obj.readOnly&&((obj.type=='text')||(obj.type=='textarea'))){
	
	if(window.event.keyCode==113){
	//alert(parseInt(obj.maxLength));
	var maxlength=parseInt(obj.maxLength);
	
	if(maxlength >0 ){
	//alert("1   "+obj.value);
	if(maxlength >= (parseInt(obj.value.length)+parseInt('<c:out value="<%=serverDate.length()%>" />'))){
	 obj.value=obj.value+"<c:out value='<%=serverDate%>'/>";
	 //alert("2  "+obj.value);
	 }}
	 else {
	 	obj.value=obj.value+"<c:out value='<%=serverDate%>'/>";
	 	//alert("3  "+obj.value);
	 }
	 
	 }	}
	// if((window.event.shiftKey==true) && (window.event.keyCode==80))   
	//{   
	//	alert("alt+p")
	//}  
	//obj.value=objvalue+" hello ";
	}
	 $(function(){
 $("INPUT").keyup(function () {functionKey(this,this.value); }); 
 })
 $(function(){
 $("TEXTAREA").keyup(function () {functionKey(this,this.value); }); 
 })


function chkWorkDate(obj) {
	if(obj.value.length>0 && chkDate(obj)) {
		if(compareDateLTET(obj.value,'<c:out value="<%=serverDate%>"/>'))
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


$(document).ready(function ()
        {
            $.ui.dialog.defaults.bgiframe = true;
            $("#FDATE").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true'});
            $("#TDATE").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true' });
            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
        });
        
function getIndate(obj)
{
	var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
	var REFNO = window.document.getElementById("searchString").value;
	if(REFNO.length>0){
		ModifyHindi_GDAO.getIncomingDateFM(ROLEID, REFNO, '<c:out value="<%= sessionBean.getISCONF()%>" />', getInDateData);
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
  		var refType = trnRefBean.REFERENCETYPE == 'C'? trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE+' *': trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE;
  		document.getElementById("INDATE_DRPDWN").options.add(opt);
  		opt.text = refType;
    	opt.value = trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE;
    	trnRefBean.REFERENCETYPE == 'C'? opt.style.color = "red":"" ;
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

function addrowMarking(targetobj)	{
	var idno = targetobj.getElementsByTagName('tr').length;
	newtr=targetobj.childNodes[0].cloneNode(true);
	newtr.style.display='block';
//	newtr.className=targetobj.childNodes.length%2==0?'treven':'trodd';
	newtr.childNodes[0].innerText=idno;
	targetobj.appendChild(newtr);

}

function changeColor() {
	window.document.getElementById("go").style.backgroundscolor = "red";
}
</script>
</head>
<body onload="document.getElementById('searchString').focus();">
<form name="frm" action="" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>File-Create</i> - Edit Hindi/G</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table>
<div id="searchContainer" align="left">
<table><tr><td align="center">
<fieldset style="width: 40%;">
<table align="center" style="border:0;border-color: transparent;" cellpadding="0" cellspacing="0" border="0">
<tr>
<td valign="middle">
<table align="center" style="border: 0;border-color: transparent;"  cellpadding="0" cellspacing="0">  
	<tr>
				<td colspan='5' align="left" height="50" valign="top">
				<input type="text" name="searchString" id="searchString" value="<c:out value='<%=searchString %>'/>"
						style="text-transform: uppercase;" onkeypress="allowAlphaNumWithoutSpace('/');" size="19" align="left" tabindex="1">
				<input type="button" name="go" id="go" value=" Go " align="left" style="height: 20px;" onfocus="changeColor();" class="butts"
						onclick="getIndate(this);" title="Search" tabindex="2">
				&nbsp;&nbsp;&nbsp;<input type="button" name="search" id="btnsave" value="Search" align="left" style="height: 20px;" class="butts"
						onclick="getsearchDetail();" title="Search" tabindex="4"><BR>
				<select name="INDATE_DRPDWN" id="INDATE_DRPDWN"  size="3" tabindex="3" style="display: none; size: 3; font-size: 11px; height: 60px; width: 180px;">
				</select>
				</td>
	</tr>
</table></td>
<td valign="top" style="margin-top:0px">
<fieldset style="height: 80px;margin-top:0px"><legend align="top" style="margin-top:0px">Search History</legend>
<table id="data" align="center" style="margin-top:0px">  
	<tr>
				<td colspan='5' align="left">
				<div id="suggest" style="height:80px;width:200px; overflow: auto;"></div>
				</td>
			</tr>
</table>
</fieldset></td>
</tr>
</table>
</fieldset>

</td></tr>
<tr><td align="center">
<fieldset style="width: 90%;">
<table border="0" width="80%" align="center" cellpadding="0" cellspacing="0">
	<tbody align="left">
		<tr>
			<td style="border-bottom: dotted; border-width: 1px;"><b>Regn. No. :</b></td>
			<td width="579" style="border-bottom: dotted; border-width: 1px;"><input type="text" name="REGISTRATIONNO" id="REGISTRATIONNO" size="20" style="border: 0px;" disabled></td>
			<td width="149" style="border-bottom: dotted; border-width: 1px;"><b>Regn. Date</b> </td>
			<td width="446" style="border-bottom: dotted; border-width: 1px;"><input type="text" name="REGISTRATIONDATE" id="REGISTRATIONDATE" size="20" style="border: 0px;" disabled></td>
		</tr>
		<tr>
			<td style="border-bottom: dashed; border-width: 1px;"><b>File No. :</b></td>
			<td width="579" style="border-bottom: dotted; border-width: 1px;"><input type="text" name="FILENO" id="FILENO" size="30" style="border: 0px;" disabled></td>
			<td width="149" style="border-bottom: dotted; border-width: 1px;"><b>Originated Branch</b> </td>
			<td width="446" style="border-bottom: dotted; border-width: 1px;"><input type="text" name="DEPARTMENTCODE" id="DEPARTMENTCODE" size="20" style="border: 0px;" disabled></td>
		</tr>
		<tr>
			<td style="border-bottom: dashed; border-width: 1px;"><b>Subject :</b></td>
			<td width="579" style="border-bottom: dotted; border-width: 1px;"><input type="text" name="SUBJECT" id="SUBJECT" size="80" style="border: 0px;" disabled></td>
			<td width="149" style="border-bottom: dotted; border-width: 1px;"><b>Received From</b> </td>
			<td width="446" style="border-bottom: dotted; border-width: 1px;"><input type="text" name="RECEIVEDFROM" id="RECEIVEDFROM" size="20" style="border: 0px;" disabled></td>
		</tr>
		<tr>
			<td colspan="4" style="border-bottom: dashed; border-width: 1px;"><b>Internal Marking :</b></td>
		</tr>
		<tr>
			<td colspan="4" >
<DIV align="center" style="color:black;font-family:tahoma ; background-color: white; width: 500px; text-align: center;  " id="FileResult">
			<table id="data" width="100%">
				    		<tr class="treven">
								<td nowrap="nowrap" width="30">S.No.</td>
					    		<td nowrap="nowrap" >Marking To</td>
					    		<td nowrap="nowrap" >Marking On</td>
					    		<td nowrap="nowrap" >Return On</td>
					    		<td nowrap="nowrap" ></td>
							</tr>
										<TBODY id="baserow1" >
					<tr style="display: none">
						<td nowrap="nowrap" width="30"></td>
						<td nowrap="nowrap" width="129">
						<select name="IMARKINGTO" id="IMARKINGTO" style="width: 200px; height: 20px; font-size: 11px;"> 
						<option value="">-select-</option>
						<%
									for(int k=0;k<imarkingToList.size();k++){
									CommonBean beanCommon = (CommonBean) imarkingToList.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
					</select>
						</td>
						<td nowrap="nowrap" ><input type="hidden"
											name="IMARKINGSEQUENCE" id="IMARKINGSEQUENCE" size="12" maxlength="10"
											><input type="text" style="font-size: 11px;"
											name="IMARKINGDATE" id="IMARKINGDATE" size="12" maxlength="10" 
											value="<c:out value='<%=serverDate%>'/>"  onblur="chkWorkDate(this);"><input
											type="button" class="calbutt" name="imrkdt" 
											onclick="newCalendar('IMARKINGDATE-L',getIndex(this));" ></td>
						<td nowrap="nowrap" ><input type="text" style="font-size: 11px;"
											name="IRETURNDATE" id="IRETURNDATE" size="12" maxlength="10"
											value=""  onblur="chkWorkDate(this);" ><input type="button" class="calbutt" name="iretdt"
											onclick="newCalendar('IRETURNDATE-L',getIndex(this));" ></td>
						<td nowrap="nowrap" ><input type="hidden"	name="MARKINGSEQUENCE" id="MARKINGSEQUENCE" size="5" maxlength="10" ></td>
						</tr>
					<% for(int i=0; i < fileBean.getIntmarkingBeanList().size();i++){
					TrnFileIntMarking intmarkingBean = (TrnFileIntMarking)fileBean.getIntmarkingBeanList().get(i);
						%>
						<tr >
							<td nowrap="nowrap" width="30"><c:out value='<%=i+1%>'/></td>
							<td nowrap="nowrap" width="129">
						<select name="IMARKINGTO" id="IMARKINGTO" style="width: 120px;height: 20px; font-size: 11px;"   tabindex="5"> 
						<option value="">-select-</option>
						<%
									for(int k=0;k<imarkingToList.size();k++){
									CommonBean beanCommon = (CommonBean) imarkingToList.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(intmarkingBean.getMARKINGTO())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
					</select>
						</td>
							<td nowrap="nowrap" ><input type="hidden"
											name="IMARKINGSEQUENCE" id="IMARKINGSEQUENCE" size="12" maxlength="10"
											value="<c:out value='<%=intmarkingBean.getMARKINGSEQUENCE()%>'/>"><input type="text" tabindex="6" 
											name="IMARKINGDATE" id="IMARKINGDATE" size="12" maxlength="10" style="font-size: 11px;"
											value="<c:out value='<%= intmarkingBean.getMARKINGDATE().equalsIgnoreCase("")?serverDate:intmarkingBean.getMARKINGDATE()%>'/>"   onblur="chkWorkDate(this);">
											<input type="button" class="calbutt" name="imrkdt"
											onclick="newCalendar('IMARKINGDATE-L',getIndex(this));" ></td>
							<td nowrap="nowrap" ><input type="text" style="font-size: 11px;"
											name="IRETURNDATE" id="IRETURNDATE" size="12" maxlength="10"   tabindex="7"
											value="<c:out value='<%= intmarkingBean.getCHANGEDATE()%>'/>"  onblur="chkWorkDate(this);">
											<input type="button" class="calbutt" name="iretdt"
											onclick="newCalendar('IRETURNDATE-L',getIndex(this));"></td>
							<td nowrap="nowrap" ><input type="hidden"
											name="MARKINGSEQUENCE" id="MARKINGSEQUENCE" size="5" maxlength="10"
											value="<c:out value='<%= intmarkingBean.getMARKINGSEQUENCE()%>'/>"></td>
						</tr>
					<%
						}
					%>
					</TBODY>
					<tr >
						<td width="30"> <input type="button" name="btnADDI" id="btnADDI" value=" + " onclick="addrowMarking(window.document.getElementById('baserow1'));"
							class="butts1" title="Add Multiple Markings here."   tabindex="13"></td>
						<td ></td>
						<td ></td>
						<td ></td>
					</tr>
				</table>
</DIV>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
			<table border="0" width="92%" >
					<tr class="treven">
						<td align="center" valign="top" style="border-bottom: dotted; border-width: 1px;"><b>File Status 1</b></td>
						<td align="center" valign="top" style="border-bottom: dotted; border-width: 1px;"><b>File Status 2</b></td>
						<td align="center" valign="top" style="border-bottom: dotted; border-width: 1px;"><b>Action Taken</b></td>
						<td align="center" valign="top" style="border-bottom: dotted; border-width: 1px;">
						</td>
						<td align="center" valign="top" style="border-bottom: dotted; border-width: 1px;"></td>
					</tr>
					<tr>
						<td valign="top" style="border-bottom: dotted; border-width: 1px;"><select name="FILESTATUS1" id="FILESTATUS1" style="height:20px; width: 180px; font-size: 11px;"  tabindex="8">
			<option> </option>
								<%
									for(int k=0;k<fileStatus1.size();k++){
									CommonBean beanCommon = (CommonBean) fileStatus1.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
				</select></td>
						<td valign="top" style="border-bottom: dashed; border-width: 1px;"><select name="FILESTATUS2" id="FILESTATUS2" style="height:20px; width: 180px; font-size: 11px;"  tabindex="9">
			<option> </option>
								<%
									for(int k=0;k<fileStatus2.size();k++){
									CommonBean beanCommon = (CommonBean) fileStatus2.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
				</select></td>
						<td valign="top" style="border-bottom: dashed; border-width: 1px;"><textarea name="REMARKS" id="REMARKS" onkeypress="allowAlphaNum('()/');" cols="60" style="font-size: 11px;" tabindex="10"></textarea></td>
						<td style="border-bottom: dashed; border-width: 1px;">
						<input	type="hidden" name=DMARKINGSEQUENCE id="DMARKINGSEQUENCE"></td>
						<td style="border-bottom: dashed; border-width: 1px;"></td>
					</tr>
					<tr class="treven">
						<td align="center" style="border-bottom: dashed; border-width: 1px;"><b>Marked To<br>
						(Downward)</b></td>
						<td align="center" style="border-bottom: dashed; border-width: 1px;"><b>Marked On<br>
						(Downward)</b></td>
						<td align="center" style="border-bottom: dashed; border-width: 1px;"><b>Remarks To<br>
						(Downward)</b></td>
						<td align="center" style="border-bottom: dashed; border-width: 1px;"><b>Reply</b></td>
						<td align="center" style="border-bottom: dashed; border-width: 1px;"><b>Reason</b></td>
					</tr>
					<tr>
						<td valign="top" style="border-bottom: dashed; border-width: 1px;"><select name="DMARKINGTO" id="DMARKINGTO" style="width: 200px; height: 20px; font-size: 11px;"  tabindex="11"> 
						<option value="">-select-</option>
						<%
									for(int k=0;k<imarkingToList.size();k++){
									CommonBean beanCommon = (CommonBean) imarkingToList.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
					</select></td>
						<td valign="top" style="border-bottom: dashed; border-width: 1px;"><input type="text" name="DMARKINGDATE" id="DMARKINGDATE" size="12" maxlength="10" style="font-size: 11px;"  tabindex="12" onblur="chkWorkDate(this);">
							<input type="button" class="calbutt" name="dmrkdt" onclick="newCalendar('DMARKINGDATE',getIndex(this));" ></td>
						<td valign="top" style="border-bottom: dashed; border-width: 1px;"><textarea name="DREMARKS" id="DREMARKS" tabindex="13" cols="60" style="font-size: 11px;"> </textarea></td>
						<td valign="top" style="border-bottom: dashed; border-width: 1px;"><select name="REPLYTYPE" id="REPLYTYPE" style="height: 20px; width: 120px; font-size: 11px;" tabindex="14">
							<option value=""></option>
							<%
									for(int k=0;k<replyCode.size();k++){
									CommonBean beanCommon = (CommonBean) replyCode.get(k);
									%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
								<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getREPLYTYPE())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%
								}%>
						</select></td>
						<td valign="top" style="border-bottom: dashed; border-width: 1px;"><textarea name="REASON" id="REASON" onkeypress="allowAlphaNum('()/');" cols="25" style="font-size: 11px;" tabindex="15"></textarea></td>
					</tr>
					<tr>
		<td colspan="8" align="center"><input style="height: 20px;" type="button" onclick="saveData(this);" name="save" value=" Save " title="Search" class="butts">
		&nbsp;&nbsp;&nbsp;&nbsp;<input style="height: 20px;" type="button" onclick="clearForm(this);" name="clear" value=" Clear " title="Clear" class="butts"></td>
		</tr>
			</table>
			</td>
		</tr>
	</tbody>
</table></fieldset></td></tr></table>
<div id="msgDiv"><c:out value='<%= msg.length()>0?msg:"" %>'/></div>
</div>
</form>
</body>
</html>