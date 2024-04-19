<!DOCTYPE html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.*" %>
<%@page import="java.text.SimpleDateFormat"%> 
<%@page import="in.org.cris.mrsectt.util.StringFormat"%><html>
<head><title>Minister for Railways Secretariat</title>
<!-- <LINK href="${pageContext.request.contextPath}/theme/MasterGreen.css" rel="stylesheet" type="text/css">  -->
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<% String loginid = session.getAttribute("loginid")!=null? session.getAttribute("loginid").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<% MstLogin mstLoginBean2 =  (MstLogin)session.getAttribute("MstLogin");
String theamcolor = mstLoginBean2.getTHEMECOLOR();
	%>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>

<SCRIPT	src='${pageContext.request.contextPath}/dwr/interface/MstLoginDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<%
	MstLogin mstLoginBean =  (request.getAttribute("mstLoginBean")!=null) ?(MstLogin)request.getAttribute("mstLoginBean"): new MstLogin();
	
	
	
	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();
	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
	
	String Query="SELECT ROLEID, ROLENAME FROM MSTROLE ORDER BY ROLENAME"; 
	ArrayList roleIdList = (new CommonDAO()).getSQLResult(Query, 2);
	
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	
	
%>

  <script type="text/javascript">
  
  $().ready(function() {
		var $scrollingDiv = $("#scrollingDiv");
 
		$(window).scroll(function(){			
			$scrollingDiv
				.stop()
				.animate({"marginTop": ($(window).scrollTop() + 0) + "px"}, "fast" );			
		});
	});
  
  function showHideTD(tobj){

		var img = tobj.childNodes[0].childNodes[0];
		if((img.src).match("next")=="next"){
			img.src="images/prev.gif";
			document.getElementById("showhidecol").title="Hide search criteria";
			window.location.href="#";
		}else{
			img.src="images/next.gif";
			document.getElementById("showhidecol").title="Show search criteria";
		}
		
		obj=document.getElementById("td2");
		
		if(obj.style.display=='none'){
			document.getElementById("td2").style.display='block';
			//document.getElementById("td3").style.display='none';
		}else{
			//document.getElementById("td3").style.display='block';
			document.getElementById("td2").style.display='none';
		}
	ShowSearch(obj);
}

function ShowSearch(obj){
if(document.getElementById("content")!=null){
		var idno = document.getElementById("content").getElementsByTagName('tr').length;
		//alert(idno);
		
		if(obj.style.display=='none'){
		
			//document.getElementById("sth1").style.display='inline';
			//document.getElementById("sth2").style.display='inline';
			//document.getElementById("sth3").style.display='inline';
			//document.getElementById("sth3").style.width='15px';
			document.getElementById("sth4").style.display='inline';
			document.getElementById("sth5").style.display='inline';
		//	document.getElementById("sth6").style.display='inline';
			document.getElementById("sth7").style.display='inline';
			document.getElementById("sth8").style.display='inline';
			$("#sorttable").tableFilter();
			
		for(var i=0;i<idno;i++){
		
			//document.getElementsByName("std1")[i].style.display='inline';
			//document.getElementsByName("std2")[i].style.display='inline';
			document.getElementsByName("std3")[i].style.display='inline';
			document.getElementsByName("std3")[i].style.width='15px';
			document.getElementsByName("std4")[i].style.display='inline';
			document.getElementsByName("std5")[i].style.display='inline';
		//	document.getElementsByName("std6")[i].style.display='inline';
			document.getElementsByName("std7")[i].style.display='inline';
			document.getElementsByName("std8")[i].style.display='inline';
			}
	}else{
			//document.getElementById("sth1").style.display='inline';
			//document.getElementById("sth2").style.display='none';
			//document.getElementById("sth3").style.display='none';
			document.getElementById("sth3").style.width='15px';
			document.getElementById("sth4").style.display='none';
			document.getElementById("sth5").style.display='none';
		//	document.getElementById("sth6").style.display='none';
			document.getElementById("sth7").style.display='none';
			document.getElementById("sth8").style.display='none';
			// Hide Filter Row
			var y = window.document.getElementById("sorttable");
			//var trtag = y.getElementsByTagName("TR");
			//trtag[1].style.display = "none";
			y.deleteRow(1);
			
			for(i=0;i<idno;i++){
			//document.getElementsByName("std1")[i].style.display='inline';
			//document.getElementsByName("std2")[i].style.display='none';
			//document.getElementsByName("std3")[i].style.display='none';
			//document.getElementsByName("std3")[i].style.width='15px';
			document.getElementsByName("std4")[i].style.display='none';
			document.getElementsByName("std5")[i].style.display='none';
		//	document.getElementsByName("std6")[i].style.display='none';
			document.getElementsByName("std7")[i].style.display='none';
			document.getElementsByName("std8")[i].style.display='none';
			}
	}
}
}  

  $(document).ready(function(){
  
    $("#MainTabDiv").tabs({ fx: { slide: true, fade: true, speed: 'normal' }  });
    
    $( "#tabs" ).tabs();
		$( ".tabs-bottom .ui-tabs-nav, .tabs-bottom .ui-tabs-nav > *" )
			.removeClass( "ui-corner-all ui-corner-top" )
			.addClass( "ui-corner-bottom" );
  });
  
  </script>
  
  <style>
	#tabs { height: 420px } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 420px; overflow: auto; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	</style>
  
  
 <script type="text/javascript">
 
  //	CODE FOR SEARCH STARTS HERE...
 
function getBlankValue(thisObj){
	return (thisObj.className=='active')? thisObj.value:"";	
}
 
function getSearch(Obj){
  	var sLOGINID =  window.document.getElementById("sLOGINID");
  	var sLOGINNAME =  window.document.getElementById("sLOGINNAME");
  	var sLOGINDESIGNATION =  window.document.getElementById("sLOGINDESIGNATION");
  	var sROLEID =  window.document.getElementById("sROLEID");
 // 	alert(sROLEID);
  	//Check for atleast One field for search
  	if(    getBlankValue(sLOGINID)=='' && getBlankValue(sLOGINNAME)=='' && getBlankValue(sLOGINDESIGNATION)=='' && getBlankValue(sROLEID)==''){
  	  	//nothing to search ...simply return 
  		return false;
  	}
	MstLoginDAO.getSearchData(getBlankValue(sLOGINID), getBlankValue(sLOGINNAME), getBlankValue(sLOGINDESIGNATION),getBlankValue(sROLEID), getSearchData);
  }

function getSearchData(data){
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" width="10px" id="sth00"><b>SNo.</b></th> <th align="center" id="sth1" ><b>Login ID</b></th> <th align="center" id="sth2" ><b>User Name</b></th>	<th align="center" id="sth3" ><b>User Desg.</b></th> <th align="center" id="sth4" style="display:none;"><b>Role ID</b></th>	<th align="center" id="sth5" style="display:none;"><b>Active Login</b></th>  <th align="center" id="sth7" style="display:none;"><b>Date(Starting)</b></th> <th align="center" id="sth8" style="display:none;"><b>Date(Ending)</b></th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	for(var loop=0; loop < data.length; loop++){
		var mstLoginBean = data[loop];
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick = "maximizeResult(\''+mstLoginBean.LOGINID+'\');"> <td align="center" id="std00"> '+(loop+1)+' </td>	<td  id="std1"> '+mstLoginBean.LOGINID+' </td> <td  id="std2"> '+mstLoginBean.LOGINNAME+' </td>	<td  id="std3"> '+mstLoginBean.LOGINDESIGNATION+' </td>	<td  id="std4" style="display:none;"> '+mstLoginBean.LOGINASROLEID+' </td> <td  id="std5" style="display:none;"> '+mstLoginBean.ISACTIVELOGIN+' </td>  <td  id="std7" style="display:none;"> '+mstLoginBean.STARTDATE+' </td> <td  id="std8" style="display:none;"> '+mstLoginBean.ENDDATE+' </td> </tr>';
	}
	htmlText += '</tbody>';
	htmlText += '</table>';
	searchResult.innerHTML = htmlText;
	

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
	var obj=document.getElementById("td2");
	if(obj.style.display=='none'){
	ShowSearch(obj);
	}
}
 
function setSearchParamsBlank(){
	var sLOGINID =  window.document.getElementById("sLOGINID");
  	var sLOGINNAME =  window.document.getElementById("sLOGINNAME");
  	var sLOGINDESIGNATION =  window.document.getElementById("sLOGINDESIGNATION");
  	var sROLEID =  window.document.getElementById("sROLEID");
  	sLOGINID.value = getBlankValue(sLOGINID);
  	sLOGINNAME.value = getBlankValue(sLOGINNAME);
  	sLOGINDESIGNATION.value = getBlankValue(sLOGINDESIGNATION);
  	sROLEID.value = getBlankValue(sROLEID);
}

function maximizeResult(sLOGINID){
	window.document.frmMstLogin.LOGINID.value = sLOGINID;
	setSearchParamsBlank();
	submitForm('GO');
}

function setSessionSeachParams(OBJNAME, objValue){
		if(objValue!=''){
			window.document.getElementById(OBJNAME).value = objValue;
    		window.document.getElementById(OBJNAME).className = 'active';
    	}	
	}
 //	CODE FOR SEARCH ENDS HERE...
 
function chkLoginID(Obj){
  	var LOGINID =  window.document.getElementById("LOGINID").value;
  	if(LOGINID.length>0)
  	{
  		MstLoginDAO.getLoginID(LOGINID, getLoginID1);
  	}
  	else{
  		var loginDetail = window.document.getElementById("loginDetail");
		loginDetail.innerHTML = "";
  	}
  }

function getLoginID1(data){
	if(window.document.getElementById("LOGINID").value == data)
	{
		alert('Login ID already exist.');		
		window.document.frmMstLogin.LOGINID.value = "";
		window.document.frmMstLogin.LOGINID.focus();

		MstLoginDAO.getLoginData2(data, getSearchData2);
	}
	else{
		var loginDetail = window.document.getElementById("loginDetail");
		loginDetail.innerHTML = "";
	}
}
function getSearchData2(data){
	var loginDetail = window.document.getElementById("loginDetail");
	var htmlText = '';
	//htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" width="10px" ><b>SNo.</b></th> <th align="center" ><b>Login ID</b></th> <th align="center" ><b>Login Name</b></th>	<th align="center"><b>Designation</b></th> <th align="center" ><b>Role ID</b></th>	<th align="center"  ><b>Avtive Login</b></th>  <th align="center" ><b>Start Date</b></th> <th align="center" ><b>End Date</b></th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	for(var loop=0; loop < data.length; loop++){
		var mstLoginBean = data[loop];
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick = "maximizeResult(\''+mstLoginBean.LOGINID+'\');"> <td align="center"> '+(loop+1)+' </td>	<td > '+mstLoginBean.LOGINID+' </td> <td > '+mstLoginBean.LOGINNAME+' </td>	<td  > '+mstLoginBean.LOGINDESIGNATION+' </td>	<td  > '+mstLoginBean.LOGINASROLEID+' </td> <td > '+mstLoginBean.ISACTIVELOGIN+' </td>  <td  > '+mstLoginBean.STARTDATE+' </td> <td   > '+mstLoginBean.ENDDATE+' </td> </tr>';
	}
	htmlText += '</tbody>';
	htmlText += '</table>';
	loginDetail.innerHTML = htmlText;
}
  
function submitForm(btnclick) {
 
	window.document.frmMstLogin.btnclick.value = btnclick;
	setSearchParamsBlank();
	if("GO"==btnclick){
	    window.document.frmMstLogin.method = "post";
		window.document.frmMstLogin.submit();
	}
	if("N"==btnclick){
	    window.document.frmMstLogin.method = "post";
		window.document.frmMstLogin.submit();
	}
	if("S"==btnclick){
		if( chkblank(window.document.frmMstLogin.LOGINID) && chkblank(window.document.frmMstLogin.LOGINNAME)
		&& chkblank(window.document.frmMstLogin.PASSWORD) && chkblank(window.document.frmMstLogin.ISACTIVELOGIN)
		&& chkblank(window.document.frmMstLogin.LOGINDESIGNATION) && chkblank(window.document.frmMstLogin.ROLEID)
		&& chkblank(window.document.frmMstLogin.STARTDATE) && chkblank(window.document.frmMstLogin.ISCONF)
		&& chkblank(window.document.frmMstLogin.ISREPLY)){
			window.document.frmMstLogin.submit();
		}	 
	}
	if("C"==btnclick){
	    window.document.frmMstLogin.method = "post";
		window.document.frmMstLogin.submit();
	}
} 

function bodyOnLoad(){
	 <% if (request.getAttribute("mstLoginBean")!=null){
	 	%>
	 		window.document.getElementById("LOGINID").readOnly = true;
	 		document.frmMstLogin.LOGINNAME.focus();
	 	<%} else { %>
	 		document.frmMstLogin.LOGINID.focus();
	 	<%} %>		

	 	 <% if (theamcolor.equals("H")){
	 	 	%>
	 	 		window.document.getElementById("THEMECOLOR").value = "S";
	 	 		
	 	 	<%} else { %>
	 	 	window.document.getElementById("THEMECOLOR").value = "";
	 	 	<%} %>	
	showDiv();
	label2value();	
	setSessionSeachParams('sLOGINID', '<%=sBean.getField1()%>');
	setSessionSeachParams('sLOGINNAME', '<%=sBean.getField2()%>');
	setSessionSeachParams('sLOGINDESIGNATION', '<%=sBean.getField3()%>');
	setSessionSeachParams('sROLEID', '<%=sBean.getField4()%>');
 	getSearch();
}
function showDiv()
{
<%
	if (msg.length() > 3 )
	{%>
	document.getElementById('updateDiv').style.display="block";
	document.getElementById('updateDivInner').style.display="block";
<%	}%>
	
}

function hideDiv()
{
	document.getElementById('updateDiv').style.display="none";
	document.getElementById('updateDivInner').style.display="none";
	
}
function callMe()
{
	hideDiv();
}
</script>
<script>  
 function funcKeyPress(obj,objvalue){
 //if(objvalue.length==0){
    if(window.event.keyCode==113){
        obj.value=obj.value+'<%=serverDate%>'; 
 	}
 // } 
 }
 
  function functionKey(obj,objvalue){ 
	
	if(!obj.readOnly&&((obj.type=='text')||(obj.type=='textarea'))){
	
	if(window.event.keyCode==113){
	//alert(parseInt(obj.maxLength));
	var maxlength=parseInt(obj.maxLength);
	
	if(maxlength >0 ){
	if(maxlength >= (parseInt(objvalue.length)+parseInt('<%=serverDate.length()%>'))){
	 obj.value=obj.value+"<%=serverDate%>";
	 }}
	 else {
	 	obj.value=obj.value+"<%=serverDate%>";
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
<script>
   var cpval;
   function functionKey(obj,objvalue){ 
	if(!obj.readOnly&&((obj.type=='text')||(obj.type=='textarea'))){
	if(window.event.keyCode==113){
	//alert(parseInt(obj.maxLength));
	var maxlength=parseInt(obj.maxLength);
	if(maxlength >0 ){
	if(maxlength >= (parseInt(objvalue.length)+parseInt('<%=serverDate.length()%>'))){
	 obj.value=obj.value+"<%=serverDate%>";
	 }}
	 else {
	 	obj.value=obj.value+"<%=serverDate%>";
	 }
	 }
	 }
	 if(!obj.readOnly&&((obj.type=='textarea'))){
	 if(window.event.keyCode==118){
	//alert(parseInt(objvalue.length));
	var maxlength=parseInt(objvalue.length);
	if(maxlength >0 ){
	 cpval=obj.value;
	 }}
	 if(window.event.keyCode==119){
	//alert(parseInt(obj.length));
	var maxlength=parseInt(objvalue.length);
		 obj.value=obj.value+cpval;
	 }
	 }
	}

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

$(document).ready(function ()
        {
            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
             $("input[type=text]").blur(function () {$(this).css('background', '#FFFFFF') });
             $("select").live('focus', function() {$(this).css('background', '#FFE4E1')});
             $("select").live('blur', function() {$(this).css('background', '#FFFFFF')});
            $("TEXTAREA").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
            $("TEXTAREA").blur(function () {  $(this).css('background', '#FFFFFF') });
        });	
        
 document.onkeyup = KeyCheckup;       
	function KeyCheckup()
	{
	  var KeyID = event.keyCode;
	  //alert(KeyID);
	  if(KeyID=="120")
	  {
	 	 submitForm('SAVE');
	  }
	}      
</script>
</head>
<body  onload="showDiv(); bodyOnLoad();">
<form action="${pageContext.request.contextPath}/MstLoginController" method="post"  name="frmMstLogin" >
<table width="100%" align="left">
	<tr>  
      	<td colspan="4"> 
      		<font size="3" > 
      			<b><i>Masters</i> - Login Master</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">                
        </td>
 	</tr>

	<tr>
		<td valign="top">
		<td valign="top" align="left">
		<fieldset id="Search">
		<table width="170" style="border: 0">
 				<tr><td colspan="2">
 					 <img border="0" src="images/Search1.png"
								width="91" height="35"></td></tr>
 				<tr>
						<td align="right"></td>
					</tr>
 				<tr>
 					<td width="130"><label for="sLOGINID">Login ID...</label>
 						<input type="text" size="20" name="sLOGINID" id="sLOGINID"></td>
					</tr>
					<tr>
						<td width="130"><label for="sLOGINNAME">User's Name...</label>
 						<input type="text" size="20" name="sLOGINNAME" id="sLOGINNAME"></td>
					</tr>
					<tr>
 					<td width="130"><label for="sLOGINDESIGNATION">User's Designation...</label>
 						<input type="text" size="20" name="sLOGINDESIGNATION" id="sLOGINDESIGNATION"></td>
					</tr>
					<tr>
 					<td width="130"><label for="sROLEID">Login as Role ID...</label>
 						<input type="text" size="20" name="sROLEID" id="sROLEID"></td>
					</tr>
					<tr>
						<td align="right" width="130"><input type="button" name="" value="&gt;&gt;" onclick="getSearch(this)" ></td>
					</tr>
					<tr>
						<td align="right" ><img width="20" height="20" src="images/printer_new.png" onclick="window.open('PopUpPrintReport.jsp?flag=y');" title="Print"></img></td>
					</tr>
					</table>
					<div id="searchResult"   style="height:200px; overflow:auto; overflow-x: hidden;"></div>
		</fieldset>
		</td>
		<td bgcolor="#F2F3F1" onclick="showHideTD(this)"
			title="Show search criteria" style="cursor: pointer;" valign="top"
			id="showhidecol">
		<div id="scrollingDiv"><img src="images/next.gif"></div>
		</td>
		<td id="td2" width="100%" valign="top">
		<fieldset >
		<table id="data" width="61%">
			    		
					<tr class="trodd">
						<td nowrap  valign="middle" width="147"><b></b>Login ID<b><font
							color="red">*</font></b> :</td>
						<td  valign="middle" width="182">
						<input type="text" name="LOGINID" id="LOGINID" value="<%=mstLoginBean.getLOGINID() %>" maxlength="20" onblur="chkLoginID(this);" ></td>

<td nowrap valign="middle" width="121">Login Password<b><font
							color="red">*</font></b> :</td>
						<td valign="middle" width="124"><input type="password" name="PASSWORD" id="PASSWORD" maxlength="20" value="<%=mstLoginBean.getPASSWORD() %>" size="20"></td>
					</tr>
					<tr class="trodd">
					<td width="147">User's Name<b><font color="red">*</font></b> :</td>
						<td width="182"><input type="text" name="LOGINNAME" id="LOGINNAME"
							value="<%=mstLoginBean.getLOGINNAME() %>" maxlength="20"></td>
						<td nowrap valign="middle" width="121">User's Designation<b><font
							color="red">*</font> :</b></td><td valign="middle" width="124"><input
							type="text" name="LOGINDESIGNATION" id="LOGINDESIGNATION" maxlength="20"
							value="<%=mstLoginBean.getLOGINDESIGNATION() %>" size="20"></td>
					</tr>
					<tr class="trodd">
					<td width="147">Is Active<b><font
							color="red">*</font></b> :</td>
						<td width="182">
						<select name="ISACTIVELOGIN" id="ISACTIVELOGIN" style="width: 50px;">
						
							<option value="" selected="selected"></option>
							<option value="Y"
									<%= mstLoginBean.getISACTIVELOGIN().equalsIgnoreCase("Y")?"selected":""%>>Yes</option>
							<option value="N"
									<%= mstLoginBean.getISACTIVELOGIN().equalsIgnoreCase("N")?"selected":""%>>No</option>
						</select>
						<input type="hidden" name="THEMECOLOR" id="THEMECOLOR"></td>
						</td>
						<td nowrap valign="middle" width="121"> Confidential User<font
							color="red">*</font> :</td><td valign="middle" width="124">
							<select name="ISCONF" id="ISCONF" style="width: 50px;">
								<option value="" selected="selected"></option>
								<option value="1" <%= mstLoginBean.getISCONF().equalsIgnoreCase("1")?"selected":""%>>Yes</option>
								<option value="0" <%= mstLoginBean.getISCONF().equalsIgnoreCase("0")?"selected":""%>>No</option>
							</select></td>
					</tr>
					<tr class="trodd">
						<td nowrap valign="middle" width="147">Date(Starting)<b><font
							color="red">*</font></b> :</td><td valign="middle" width="182">
							<input type="text" name="STARTDATE" id="STARTDATE" maxlength="10" value="<%= mstLoginBean.getSTARTDATE() != null? mstLoginBean.getSTARTDATE():"" %>" size="10"></td>
						<td nowrap valign="middle" width="121">Date(Ending) :</td><td valign="middle" width="124"><input type="text" name="ENDDATE" id="ENDDATE" size="10" value="<%= mstLoginBean.getENDDATE() != null? mstLoginBean.getENDDATE():"" %>" maxlength="10"></td>
					</tr>
					<tr class="trodd">
						<td nowrap valign="middle" width="147">Login as Role ID<b><font
					color="red">*</font></b> :</td>
						<td valign="middle" width="182"><select name="ROLEID" id="ROLEID" style="width: 200px;">
						<% if(theamcolor.equals("H"))
						{
							%>
					
					<option value="<%=mstLoginBean2.getLOGINASROLEID() %>" selected="selected"><%=mstLoginBean2.getLOGINASROLENAME() %></option>

					
					<% } else { %>
					
					
					<option value="" selected="selected"></option>
					
					<%
									for(int i=0;i<roleIdList.size();i++){
									CommonBean beanCommon=(CommonBean) roleIdList.get(i);
								%>
					<option value="<%=beanCommon.getField1()%>"
						<%=beanCommon.getField1().equalsIgnoreCase(mstLoginBean.getLOGINASROLEID())?"selected":""%>><%=beanCommon.getField2()%></option>
					<%} 
					}
					%>
				</select></td>
						<td nowrap valign="middle" width="121"> Show Reply<font
							color="red">*</font> :</td><td valign="middle" width="124">
							<select name="ISREPLY" id="ISREPLY" style="width: 50px;">
								<option value="" selected="selected"></option>
								<option value="1" <%= mstLoginBean.getISREPLY().equalsIgnoreCase("1")?"selected":""%>>Yes</option>
								<option value="0" <%= mstLoginBean.getISREPLY().equalsIgnoreCase("0")?"selected":""%>>No</option>
							</select></td>
					</tr>
					<tr >
				<td nowrap valign="top" colspan="6">
				<table border="0" width="150">
					<tbody>
						<tr>
							<td width="88"><input type="button" name="btnSAVE" class="butts" value=" Save " onclick="submitForm('S')"></td>
							<td width="102"><input type="button" name="btnCLEAR" class="butts" value=" Clear " onclick="submitForm('C')">
								<input type="hidden" name="btnclick" id="btnclick"></td>
						</tr>
					</tbody>
				</table>
				</td>
			</tr>
			<tr><TD colspan="5"><table width="100%">
					<tr>
						<td><div id="loginDetail" style="height:200px; overflow:auto; overflow-x: hidden;"></div></td>
					</tr>
				</table>
			</TD></tr>
		</table>
		</fieldset>
		</td>
	</tr>
</table>

<!-- DO NOT DELETE BELOW THIS!!! Following will create model. It will get uncommented once jsp is processed. -->

<!--main Content Area Ends-->
<!--  <%=" Model Starts -->"%>
<DIV class="transparent_class" style="z-index:2000; background-color:#000; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 150%;" id="updateDiv">
	
</DIV>
<DIV style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:20px; top: 160px; display: none; width: 100%" id="updateDivInner">
<DIV class="pageheader" style="color: blue; font-family:tahoma ; z-index:5000; background-color: #FBD6B5; width: 400px; border: groove; text-align: center; background-image:url(images/top-hd-bar-bg.gif); background-repeat: repeat; " id="divLoading">
<%	if(msg.length()>3) { %>
			<BR><img src="images/<%= msg.substring(0,3)%>.gif"/><%= msg.substring(3) %> <BR><input type="button" value=" Ok " onclick="callMe()">
			<BR><BR>
<% } %>



</DIV>
</DIV>
<%="<!-- Model Ends "%>    -->
<!-- DO NOT ABOVE BELOW THIS!!! -->
<%session.removeAttribute("msg"); %>
<%session.removeAttribute("sbean"); %>
</form>
</body>
</html>
