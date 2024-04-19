<!DOCTYPE html>
<%@page import="in.org.cris.mrsectt.Beans.MstTenure"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.*" %>
<%@page import="in.org.cris.mrsectt.util.Encrypt"%>
<%@ taglib prefix="enc" uri="/WEB-INF/encrypt.tld"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="com.ibm.ObjectQuery.crud.util.Array"%><html>
<head><title>Minister for Railways Secretariat</title>
<!-- <LINK href="${pageContext.request.contextPath}/theme/MasterGreen.css" rel="stylesheet" type="text/css">  -->
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/MstTenureDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>


<%
	MstTenure mstTenureBean =  (request.getAttribute("mstTenureBean")!=null) ?(MstTenure)request.getAttribute("mstTenureBean"): new MstTenure();
	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();
	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
	
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	if(mstTenureBean.getTENURESTARTDATE().length() <=0){
		
		mstTenureBean.setTENURESTARTDATE(serverDate);
	}
	
	if(mstTenureBean.getYEAR().length() <=0){
		Date date = new Date();
  		SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
  		String year = simpleDateformat.format(date);
		mstTenureBean.setYEAR(year);
	}

	if(mstTenureBean.getArr().size()==0){
          CommonBean iMark =  new CommonBean();
          ArrayList<CommonBean> commonarr = new ArrayList<CommonBean>();
          commonarr.add(iMark);
          mstTenureBean.setArr(commonarr);
      }
	CommonBean bean = new CommonBean();
	String Query="SELECT ROLEID, ROLENAME FROM MSTROLE ORDER BY ROLENAME"; 
	ArrayList roleIdList = (new CommonDAO()).getSQLResult(Query, 2);
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
			document.getElementById("sth6").style.display='inline';
			document.getElementById("sth7").style.display='inline';
			document.getElementById("sth8").style.display='inline';
			document.getElementById("sth9").style.display='inline';
			$("#sorttable").tableFilter();
			
		for(var i=0;i<idno;i++){
		
			//document.getElementsByName("std1")[i].style.display='inline';
			//document.getElementsByName("std2")[i].style.display='inline';
			//document.getElementsByName("std3")[i].style.display='inline';
			document.getElementsByName("std4")[i].style.display='inline';
			document.getElementsByName("std5")[i].style.display='inline';
			document.getElementsByName("std6")[i].style.display='inline';
			document.getElementsByName("std7")[i].style.display='inline';
			document.getElementsByName("std8")[i].style.display='inline';
			document.getElementsByName("std9")[i].style.display='inline';
			}
	}else{
			//document.getElementById("sth1").style.display='inline';
			//document.getElementById("sth2").style.display='none';
			//document.getElementById("sth3").style.display='none';
			document.getElementById("sth4").style.display='none';
			document.getElementById("sth5").style.display='none';
			document.getElementById("sth6").style.display='none';
			document.getElementById("sth7").style.display='none';
			document.getElementById("sth8").style.display='none';
			document.getElementById("sth9").style.display='none';
			// Hide Filter Row
			var y = window.document.getElementById("sorttable");
			//var trtag = y.getElementsByTagName("TR");
			//trtag[1].style.display = "none";
			y.deleteRow(1);
			
			for(i=0;i<idno;i++){
			//document.getElementsByName("std1")[i].style.display='inline';
			//document.getElementsByName("std2")[i].style.display='none';
			//document.getElementsByName("std3")[i].style.display='none';
			document.getElementsByName("std4")[i].style.display='none';
			document.getElementsByName("std5")[i].style.display='none';
			document.getElementsByName("std6")[i].style.display='none';
			document.getElementsByName("std7")[i].style.display='none';
			document.getElementsByName("std8")[i].style.display='none';
			document.getElementsByName("std9")[i].style.display='none';
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
  	var sTENUREID =  window.document.getElementById("sTENUREID");
  	var sNAME =  window.document.getElementById("sNAME");
  	var sYEAR =  window.document.getElementById("sYEAR");
  	//Check for atleast One field for search
  	if(    getBlankValue(sTENUREID)=='' && getBlankValue(sNAME)=='' && getBlankValue(sYEAR)==''){
  	  	//nothing to search ...simply return 
  		return false;
  	}
	MstTenureDAO.getSearchData(getBlankValue(sTENUREID), getBlankValue(sNAME), getBlankValue(sYEAR), getSearchData);
  }

function getSearchData(data){
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" width="10px" id="sth00"><b>SNo.</b></th>	<th align="center"  id="sth1" ><b>Tenure ID</b></th><th align="center"  id="sth2" ><b>Name</b></th>	<th align="center" id="sth3" width="10px"><b>Year</b></th>	<th align="center" style="display:none;" id="sth4" nowrap="nowrap"><b>Role ID</b></th><th align="center"  style="display:none;" id="sth5" nowrap="nowrap"><b>Tenure Start Date</b></th>	<th align="center"  style="display:none;" id="sth6" nowrap="nowrap"><b>Tenure End Date</b></th>	<th align="center"  style="display:none;" id="sth7" nowrap="nowrap"><b>Tenure Office Name</b></th><th align="center"  style="display:none;" id="sth8" nowrap="nowrap"><b>Office Address</b></th><th align="center"  style="display:none;" id="sth9" nowrap="nowrap"><b>Is Active</b></th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	for(var loop=0; loop < data.length; loop++){
		var mstTenureBean = data[loop];
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick = "maximizeResult(\''+mstTenureBean.TENUREID+'\');"><td align="center" id="std00"> '+(loop+1)+' </td> <td  id="std1"> '+mstTenureBean.TENUREID+' </td><td  id="std2"> '+mstTenureBean.NAME+' </td> <td  id="std3" width="10px"> '+mstTenureBean.YEAR+' </td><td  id="std4" style="display:none;"> '+mstTenureBean.ROLEID+' </td><td  id="std5" style="display:none;"> '+mstTenureBean.TENURESTARTDATE+' </td><td  id="std6" style="display:none;"> '+mstTenureBean.TENUREENDDATE+' </td><td  id="std7" style="display:none;"> '+mstTenureBean.TENUREOFFICENAME+' </td><td  id="std8" style="display:none;"> '+mstTenureBean.TENUREOFFICEADDRESS+' </td><td  id="std9" style="display:none;"> '+mstTenureBean.ISACTIVE+' </td></tr>';
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
	var sTENUREID =  window.document.getElementById("sTENUREID");
  	var sNAME =  window.document.getElementById("sNAME");
  	var sYEAR =  window.document.getElementById("sYEAR");
  	sTENUREID.value = getBlankValue(sTENUREID);
  	sNAME.value = getBlankValue(sNAME);
  	sYEAR.value = getBlankValue(sYEAR);
}

function maximizeResult(sTENUREID){
	window.document.frmMstTenure.TENUREID.value = sTENUREID;
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
 function submitForm(btnclick) {
 	//alert(btnclick);
	window.document.frmMstTenure.btnclick.value=btnclick;
	setSearchParamsBlank();
	if("GO"==btnclick){
	    window.document.frmMstTenure.method = "post";
		window.document.frmMstTenure.submit();
	}
	if("N"==btnclick){
	    window.document.frmMstTenure.method = "post";
		window.document.frmMstTenure.submit();
	}
	
	if("S"==btnclick){
		if( chkblank(window.document.frmMstTenure.ROLEID) && chkblank(window.document.frmMstTenure.TENURESTARTDATE)  
			&& chkblank(window.document.frmMstTenure.NAME) && chkblank(window.document.frmMstTenure.TENUREOFFICENAME)
			&& chkblank(window.document.frmMstTenure.ISACTIVE)){
			
			//	for(var i=1;i<window.document.frmMstTenure.REFCLASS.length;i++){
			//		if(chkblank(window.document.frmMstTenure.REFCLASS[i]) && chkblank(window.document.frmMstTenure.INOUT[i])) {			
			 			window.document.frmMstTenure.submit();
			// 		}
		//	 }
		}	 
	}
	if("C"==btnclick){
	    window.document.frmMstTenure.method = "post";
		window.document.frmMstTenure.submit();
	}
} 

function bodyOnLoad(){
	showDiv();
	label2value();	
	setSessionSeachParams('sTENUREID', '<c:out value="<%=sBean.getField1()%>"/>');
	setSessionSeachParams('sNAME', '<c:out value="<%=sBean.getField2()%>"/>');
	setSessionSeachParams('sYEAR', '<c:out value="<%=sBean.getField3()%>"/>');
 	getSearch();
 	document.frmMstTenure.ROLEID.focus();
}
function showDiv()
{
<%
	if (msg.length() > 3 )
	{%>
//	alert("Hiii");
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

function setFlag(obj){
	window.document.getElementById("ISNEW").value = 'Y';
}

// function for F2 for date...
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
<script>
   var cpval;
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
<body onload="showDiv(); bodyOnLoad();" >
<FORM action="${pageContext.request.contextPath}/MstTenureController" method="post"  name="frmMstTenure">
<%
	String maxTenureSQL="SELECT MAX(TENUREID) FROM MSTTENURE";
	ArrayList<CommonBean> maxTenureId = (new CommonDAO()).getSQLResult(maxTenureSQL, 1);
%>
<table width="100%" align="left">
	<tr>  
      	<td colspan="4"> 
      		<font size="3" > 
      			<b><i>Masters</i> - Tenure Master</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">                
        </td>
 	</tr>

	<tr>
		<td valign="top">
		<td width="*" valign="top" align="left" valign="top">
		<fieldset id="Search">
		<table border="0" width="170">
			<tr>
				<td colspan="2"> <img border="0" src="images/Search1.png"
								width="91" height="35"></td><td rowspan="7" id="TDTREE" style="display: none;"> <div id="treeDiv" class="dtree"></div></td>
				<td></td>
			</tr>
			<tr>
 					<td><label for="sTENUREID">Tenure ID...</label>
 						<input type="text" size="20" name="sTENUREID" id="sTENUREID"  ></td>
					</tr>
					<tr>
 					<td><label for="sNAME">Name...</label>
 						<input type="text" size="20" name="sNAME" id="sNAME"  >
					</tr>
					<tr>
						<td><label for="sYEAR">Year...</label>
 						<input type="text" size="20" name="sYEAR" id="sYEAR"  ></td>
					</tr>
					<tr>
						<td align="right"><input type="button" name="" value="&gt;&gt;" onclick="getSearch(this)"></td>
					</tr>
					 <tr>
						<td align="right" width="130"><img width="20" height="20" src="images/printer_new.png" onclick="window.open('PopUpPrintReport.jsp?flagecr=<c:out value="${enc:encrypt('y')}" />');" title="Print"></img></td>
					</tr>
					
		</table>
		<div id="searchResult"   style="height:200px; overflow:auto; overflow-x: hidden;"></div>
		</fieldset>
		</td>
		<td width="5px;" bgcolor="#F2F3F1" onclick="showHideTD(this)"
			title="Show search criteria" style="cursor: pointer;" valign="top"
			id="showhidecol">
		<div id="scrollingDiv"><img src="images/next.gif"></div>
		</td>

		<td id="td2" width="100%" valign="top">
		<fieldset>
			<table width="100%" id="data">
					<tr class="trodd">
						
						<td nowrap  valign="middle" width="117"><b></b>Tenure ID :</td>
						<%
						CommonBean beanCmn=(CommonBean) maxTenureId.get(0);
						%>
						<td  valign="middle" width="186"><input type="text" class="readonly" title="Auto Generated Field" name="TENUREID" id="TENUREID" value="<c:out value='<%=mstTenureBean.getTENUREID() %>'/>" readonly  ><font color="green">Last ID : <c:out value='<%=beanCmn.getField1()%>'/></font></td>

						<td width="159">Role ID<font color="red">*</font> :</td>
						<td width="478"><select name="ROLEID" id="ROLEID" style="height: 25px;"  >
							<option value="" selected="selected">--select--</option>
							<%
								for(int i=0;i<roleIdList.size();i++){
								CommonBean beanCommon=(CommonBean) roleIdList.get(i);
							%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <%=beanCommon.getField1().equalsIgnoreCase(mstTenureBean.getROLEID())?"selected":""%>><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%
							}%>
						</select></td>


					</tr>
					<tr class="trodd">
						<td nowrap height="31" valign="middle" width="117">Tenure Start Date<font
							color="red">*</font> :</td>
						<td height="31" valign="middle" width="186"><input type="text" name="TENURESTARTDATE" id="TENURESTARTDATE" maxlength="10" value="<c:out value='<%= mstTenureBean.getTENURESTARTDATE() != null? mstTenureBean.getTENURESTARTDATE():"" %>'/>" size="20"  ></td>
						<td height="31" width="159">Tenure End Date :</td>
						<td height="31" width="478"><input type="text" name="TENUREENDDATE" id="TENUREENDDATE" maxlength="10" value="<c:out value='<%= mstTenureBean.getTENUREENDDATE() != null? mstTenureBean.getTENUREENDDATE():"" %>'/>" size="20"  ></td>
					</tr>
					<tr class="trodd">
						<td nowrap height="31" valign="middle" width="117">Name<font
							color="red">*</font> :</td><td height="31" valign="middle" width="186"><input type="text" name="NAME" id="NAME" maxlength="50" value="<c:out value='<%=mstTenureBean.getNAME() %>'/>" size="30"  ></td>
						
						<td nowrap height="31" valign="middle" width="159">Year<font
							color="red">*</font> :</td><td height="31" valign="middle" width="478">
						<input type="text" name="YEAR" id="YEAR" class="readonly" maxlength="20" value="<c:out value='<%=mstTenureBean.getYEAR() %>'/>" size="20" readonly  ></td>
						
					</tr>
					<tr class="trodd">
						<td nowrap height="31" valign="middle" width="117">Tenure Office Name<font
							color="red">*</font> :</td><td height="31" valign="middle" width="186"><input type="text" name="TENUREOFFICENAME" id="TENUREOFFICENAME" maxlength="40" value="<c:out value='<%=mstTenureBean.getTENUREOFFICENAME() %>'/>" size="40"  ></td>
						
						<td nowrap height="31" valign="top" width="159">Tenure Office Address :</td><td height="31" valign="middle" width="478"><input type="text" name="TENUREOFFICEADDRESS" id="TENUREOFFICEADDRESS" size="80" value="<c:out value='<%= mstTenureBean.getTENUREOFFICEADDRESS() != null? mstTenureBean.getTENUREOFFICEADDRESS():"" %>'/>" maxlength="100"  ></td>
						
					</tr>
					<tr class="trodd">
						<td nowrap height="31" valign="middle" width="117">Is Active<font
							color="red">*</font> :</td>
						<td height="31" valign="middle" width="186"><select name="ISACTIVE" id="ISACTIVE"  >
							<option value="">--select--</option>
							<option value="Y" <%= mstTenureBean.getISACTIVE().equalsIgnoreCase("Y")?"selected":""%>>Y</option>
							<option value="N" <%= mstTenureBean.getISACTIVE().equalsIgnoreCase("N")?"selected":""%>>N</option>
						</select></td>
						<td height="31" width="159"></td>
						<td height="31" width="478"></td>
					</tr>
			<tr class="trodd">
						<td nowrap height="31" valign="top" colspan="4">
<TABLE width="60%">
		<TBODY>
			<TR class = "treven">
				<TD valign="top" align="center"><nobr>Referance Class</nobr><font color="red">*</font></TD>
				<TD valign="top" align="center"><nobr>Inward/Outward</nobr><font color="red">*</font></TD>
				<td valign="top" align="center" width="124">Reference Counter</td>
				<TD valign="top" align="center" width="274"><nobr>Class Description</nobr><font color="red"></font></TD>
			</TR>
		</TBODY>
		<TBODY id="baserow">
			<TR class="trodd" align="center" style="display: none;">
				<TD ><input type="hidden" name="dtlid" value="">
				<INPUT type="text" name="REFCLASS" id="REFCLASS" value="" size="8" style="text-align: left; text-transform: uppercase" maxlength="3"  ></TD>
				<td><select name="INOUT" id="INOUT"  ><option>--SELECT--</option>
							<option value="I">Inward</option>
							<option value="O">Outward</option>
					</select>
				</td>
							<td><input type="hidden" name="ISNEW" id="ISNEW" value="Y" size="5"></td>
							<TD ><input type="hidden" name="dtlid" value="">
				<INPUT type="text" name="CLASSDESCRIPTION" id="CLASSDESCRIPTION" value="" size="40" style="text-align: left; text-transform: uppercase" maxlength="50"  ></TD>
			</TR>
<%
	for(int i=0; i<mstTenureBean.getArr().size(); i++)
	{
		bean = (CommonBean)(mstTenureBean.getArr().get(i));
%>
			<TR class="<%=i%2==0?"trodd":"trodd" %>" align="center" >
				<TD><INPUT type="hidden" name="dtlid" value="<c:out value='<%=bean.getField1()%>'/>">
					<INPUT type="text" name="REFCLASS" id="REFCLASS" value="<c:out value='<%=bean.getField1()%>'/>" size="8" style="text-align: left; text-transform: uppercase"" maxlength="3"  ></TD>
				<td><select name="INOUT" id="INOUT"  ><option value="">--SELECT--</option>
<option value="I" <%="I".equalsIgnoreCase(bean.getField2())?"selected":""%>>I</option>
<option value="O" <%="O".equalsIgnoreCase(bean.getField2())?"selected":""%>>O</option>
					</select></td>
							<td><c:out value='<%=bean.getField3()%>'/><input type="hidden" name="ISNEW" id="ISNEW" value="N" size="5"></td>
							<TD><INPUT type="hidden" name="dtlid" value="<c:out value='<%=bean.getField1()%>'/>">
					<INPUT type="text" name="CLASSDESCRIPTION" id="CLASSDESCRIPTION" value="<c:out value='<%=bean.getField4()%>'/>" size="40" style="text-align: left; text-transform: uppercase"" maxlength="50"  ></TD>
			</TR>
<%
	}
%>
		</TBODY>
			<TR>
				<TD align="left">
				<INPUT type="button" name="btnaddrow" style="width: 20px;"   value="+" class="butts1" onclick="addrow(window.document.getElementById('baserow'));setFlag(this);"></TD>
			</TR>
		</TABLE>
						</td>
					</tr>
					<tr >
						<td nowrap valign="top" colspan="3">
						<table border="1" width="129">
							<tbody>
								<tr>
									<td width="101"><input type="button" name="btnSAVE" class="butts" value=" Save "
										onclick="submitForm('S')"></td>
									<td width="101" nowrap="nowrap"><input type="button" name="btnCLEAR" 
										class="butts" value=" Clear "  onclick="submitForm('C')">
										<input type="hidden" name="btnclick">
									</td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
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
			<BR><img src="images/<%= msg.substring(0,3)%>.gif"/><c:out value='<%= msg.substring(3) %>'/> <BR><input type="button" value=" Ok " onclick="callMe()">
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
