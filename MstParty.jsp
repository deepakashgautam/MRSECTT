<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%><html>
<%@page import="in.org.cris.mrsectt.Beans.MstParty"%><html>
<head><title>Minister for Railways Secretariat</title>
<!-- <LINK href="${pageContext.request.contextPath}/theme/MasterGreen.css" rel="stylesheet" type="text/css">  -->
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/MstPartyDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<%
	MstParty mstPartyBean =  (request.getAttribute("mstPartyBean")!=null) ?(MstParty)request.getAttribute("mstPartyBean"): new MstParty();
	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();
	
	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
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
  	var sPARTYCODE =  window.document.getElementById("sPARTYCODE");
  	var sPARTYNAME =  window.document.getElementById("sPARTYNAME");
  	//Check for atleast One field for search
  	if(getBlankValue(sPARTYCODE)=='' && getBlankValue(sPARTYNAME)==''){
  	  	//nothing to search ...simply return 
  		return false;
  	}
  	MstPartyDAO.getSearchData(getBlankValue(sPARTYCODE), getBlankValue(sPARTYNAME),getSearchData);
  }

function getSearchData(data){
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" width="10px" id="sth00"><b>SNo.</b></th><th align="center"  id="sth1" ><b>Party Code</b></th><th align="center"  id="sth2" ><b>Party Name</b></th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	for(var loop=0; loop < data.length; loop++){
		var mstPartyBean = data[loop];
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick = "maximizeResult(\''+mstPartyBean.partycode+'\');"><td align="center" id="std00"> '+(loop+1)+' </td> <td  id="std1"> '+mstPartyBean.partycode+' </td><td  id="std2"> '+mstPartyBean.name+' </td> </tr>';
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
	var sPARTYCODE =  window.document.getElementById("sPARTYCODE");                                             
	var sPARTYNAME =  window.document.getElementById("sPARTYNAME");      
  	
	sPARTYCODE.value = getBlankValue(sPARTYCODE);
	sPARTYNAME.value = getBlankValue(sPARTYNAME);
}

function maximizeResult(sPARTYCODE){
	window.document.frmMstParty.PARTYCODE.value = sPARTYCODE;
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
	window.document.frmMstParty.btnclick.value=btnclick;
	setSearchParamsBlank();
	if("GO"==btnclick){
	    window.document.frmMstParty.method = "post";
		window.document.frmMstParty.submit();
	}
	if("N"==btnclick){
	    window.document.frmMstParty.method = "post";
		window.document.frmMstParty.submit();
	}
	
	if("S"==btnclick){
		if( chkblank(window.document.frmMstParty.PARTYCODE) && chkblank(window.document.frmMstParty.PARTYNAME)){
		window.document.frmMstParty.submit();
		}	 
	}
	
	if("C"==btnclick){
	    window.document.frmMstParty.method = "post";
		window.document.frmMstParty.submit();
	}
} 
function bodyOnload(){
	showDiv();
	window.document.getElementById("PARTYNAME").focus();
	label2value();	
	
	setSessionSeachParams('sPARTYCODE', '<c:out value="<%=sBean.getField1()%>"/>');
	setSessionSeachParams('sPARTYNAME', '<c:out value="<%=sBean.getField2()%>"/>');
	//call getSearch to show prvious search
 	getSearch();
 	////search param code ends here
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
<body onload="bodyOnload();">
<FORM action="${pageContext.request.contextPath}/MstPartyController" method="post"  name="frmMstParty">
<%
	
%>
<table width="100%" align="left" >
	<tr>  
      	<td colspan="3"> 
      		<font size="3" > 
      			<b><i>Masters</i> - Party Master</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">                
        </td>
 	</tr>

	<tr>
		<td valign="top">
		<td width="*" valign="top" align="left" valign="top">
 		<fieldset id="Search">
 			<table border="0" width="170">
 				<tr>
 					<td ><font size="+1">Search</font></td>
					</tr>
 				<tr>
 					<td ></td>
					</tr>
 				<tr>
 					<td align="left" ><label for="sPARTYCODE">PARTY Code</label><input type="text" size="20" name="sPARTYCODE" id="sPARTYCODE"  ></td>
				</tr>
 				<tr>
 					<td align="left" ><label for="sPARTYNAME">PARTY Name</label><input type="text" size="20" name="sPARTYNAME" id="sPARTYNAME"  ></td>
 				</tr>
 				
 				<tr>
					<td align="right" ><input type="button" name="" value="&gt;&gt;"
					onclick="getSearch(this);"></td>
				</tr>		
			</table>
		<div id="searchResult"   style="height:200px; overflow:auto; overflow-x: hidden;"></div>			
			</fieldset>		
		</td>
		
		<td width="5px;" bgcolor="#F2F3F1" onclick="showHideTD(this)" title="Show search criteria" style="cursor: pointer;" valign="top" id="showhidecol">
			<div id="scrollingDiv"> 
       			<img src="images/next.gif" ></div></td>
       		
 		<td id="td2" width="80%" valign="top">	 
			<div >
			    <div >
			    	<table id="data" width="50%">
					<tr class="trodd">
						<td nowrap height="11" valign="middle" width="170"><input type="hidden" name="btnclick" size="20"><b>Party Code</b></td>
							
						<td height="11" valign="middle" width="271">
						<input type="text" name="PARTYCODE" id="PARTYCODE" maxlength="10" value="<c:out value='<%=mstPartyBean.getPartycode()%>'/>" ></td>


					</tr>
					<tr class="trodd">
						<td nowrap height="31" valign="middle" width="170">Party Name<font
							color="red"></font></td>
						<td height="31" valign="middle" width="271"><input
							type="text" name="PARTYNAME" id="PARTYNAME" maxlength="50"  
							value="<c:out value='<%=mstPartyBean.getName()%>'/>" size="50"></td>
					</tr>
					<tr class="trevenRight">
						<td nowrap height="31" valign="middle" colspan="3"></td>
					</tr>
					<tr class="trevenRight">
						<td nowrap height="31" valign="middle" colspan="3">
						<table border="0" width="127">
							<tbody>
								<tr align="center">
									<td width="101"><input type="button" name="btnSAVE"	class="butts" value=" Save " onclick="submitForm('S')"></td>
									<td width="101"><input type="button" name="btnCLEAR" class="butts" value=" Clear " onclick="submitForm('C')"></td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
				</table>	
			    </div>
			    </div>
		</td>
	</tr>
</table>
<!-- DO NOT DELETE BELOW THIS!!! Following will create model. It will get uncommented once jsp is processed. -->
<!--main Content Area Ends--> <!--  <%=" Model Starts -->"%>
<DIV class="transparent_class" style="z-index:2000; background-color:#000; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 150%;" id="updateDiv">
</DIV>
<DIV style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:20px; top: 160px; display: none; width: 100%" id="updateDivInner">
<DIV class="pageheader" style="color: blue; font-family:tahoma ; z-index:5000; background-color: #FBD6B5; width: 400px; border: groove; text-align: center; background-image:url(images/top-hd-bar-bg.gif); background-repeat: repeat; " id="divLoading">
<%	if(msg.length()>3) { %>
			<BR><img src="images/<%= msg.substring(0,3).equals("GRN")?"GRN":"RED"%>.gif"/><c:out value='<%= msg.substring(3) %>'/> <BR><input type="button" value=" Ok " onclick="callMe()">
			<BR><BR>
<% } %>
</DIV>
</DIV>
<%="<!-- Model Ends "%>    --> <!-- DO NOT ABOVE BELOW THIS!!! -->
<% session.removeAttribute("msg"); %>
<%session.removeAttribute("sbean"); %>
</FORM>
</body>
</html>
