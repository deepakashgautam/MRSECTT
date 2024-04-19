<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.Encrypt"%>
<%@ taglib prefix="enc" uri="/WEB-INF/encrypt.tld"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.Beans.MstRole"%>
<%@page import="in.org.cris.mrsectt.dao.MstRoleDAO"%>
<html>
<head><title>Minister for Railways Secretariat</title>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/MstRoleDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />

<%
	MstRole	mstRoleBean =  (request.getAttribute("mstRoleBean")!=null) ?(MstRole)request.getAttribute("mstRoleBean"): new MstRole();
	CommonBean sBean = (session.getAttribute("sbeanROLE")!=null) ?(CommonBean)session.getAttribute("sbeanROLE"): new CommonBean();

	String btnclick =  request.getAttribute("btnclick")!=null ? request.getAttribute("btnclick").toString():"";
	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
	session.removeAttribute("msg");
	session.removeAttribute("sbeanROLE");
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	//System.out.println(serverDate);
%>

  <script type="text/javascript">
  /*
  $().ready(function() {
		var $scrollingDiv = $("#scrollingDiv");
 
		$(window).scroll(function(){			
			$scrollingDiv
				.stop()
				.animate({"marginTop": ($(window).scrollTop() + 0) + "px"}, "fast" );			
		});
	});
  */
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
			document.getElementById("sth3").style.display='inline';
			//document.getElementById("sth3").style.width='15px';
			document.getElementById("sth4").style.display='inline';
			$("#sorttable").tableFilter();
			
		for(var i=0;i<idno;i++){
		
			//document.getElementsByName("std1")[i].style.display='inline';
			//document.getElementsByName("std2")[i].style.display='inline';
			document.getElementsByName("std3")[i].style.display='inline';
			document.getElementsByName("std4")[i].style.display='inline';
			}
	}else{
			//document.getElementById("sth1").style.display='inline';
			//document.getElementById("sth2").style.display='none';
			document.getElementById("sth3").style.display='none';
			document.getElementById("sth4").style.display='none';
			// Hide Filter Row
			var y = window.document.getElementById("sorttable");
			//var trtag = y.getElementsByTagName("TR");
			//trtag[1].style.display = "none";
			y.deleteRow(1);
			
			for(i=0;i<idno;i++){
			//document.getElementsByName("std1")[i].style.display='inline';
			//document.getElementsByName("std2")[i].style.display='none';
			document.getElementsByName("std3")[i].style.display='none';
			document.getElementsByName("std4")[i].style.display='none';
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
  	var SEARCHROLEIDFROM =  window.document.getElementById("SEARCHROLEIDFROM");
  	var SEARCHROLEIDTO =  window.document.getElementById("SEARCHROLEIDTO");
  	var SEARCHROLENAME =  window.document.getElementById("SEARCHROLENAME");
  	var SEARCHDEPT =  window.document.getElementById("SEARCHDEPT");
  	var SEARCHAUTH =  window.document.getElementById("SEARCHAUTH");
  	//Check for atleast One field for search
  	if(    getBlankValue(SEARCHROLEIDFROM)=='' && getBlankValue(SEARCHROLEIDTO)=='' && getBlankValue(SEARCHROLENAME)=='' && getBlankValue(SEARCHDEPT)=='' && getBlankValue(SEARCHAUTH)==''){
  	  	//nothing to search ...simply return 
  		return false;
  	}
	MstRoleDAO.getSearchData(getBlankValue(SEARCHROLEIDFROM), getBlankValue(SEARCHROLEIDTO), getBlankValue(SEARCHROLENAME),getBlankValue(SEARCHDEPT), getBlankValue(SEARCHAUTH), getSearchData);  
  }

function getSearchData(data){
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" width="10px" id="sth00"><b>SNo.</b></th><th align="center"  id="sth1" ><b>Role ID</b></th><th align="center"  id="sth1" ><b>Role Name</b></th><th align="center"  style="display:none;" id="sth3" width="10px"><b>Department</b></th><th align="center"  style="display:none;" id="sth4" nowrap="nowrap"><b>Auth Level</b></th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	for(var loop=0; loop < data.length; loop++){
		var mstRoleBean = data[loop];
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick = "maximizeResult('+mstRoleBean.ROLEID+');"><td align="center" id="std00"> '+(loop+1)+' </td> <td  id="std1"> '+mstRoleBean.ROLEID+' </td><td  id="std1"> '+mstRoleBean.ROLENAME+' </td> <td  id="std3" style="display:none;"> '+mstRoleBean.DEPTCODE+' </td> <td  id="std4" style="display:none;"> '+mstRoleBean.AUTHLEVEL+' </td></tr>';
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
	var SEARCHROLEIDFROM =  window.document.getElementById("SEARCHROLEIDFROM");
  	var SEARCHROLEIDTO =  window.document.getElementById("SEARCHROLEIDTO");
  	var SEARCHROLENAME =  window.document.getElementById("SEARCHROLENAME");
  	var SEARCHDEPT =  window.document.getElementById("SEARCHDEPT");
  	var SEARCHAUTH =  window.document.getElementById("SEARCHAUTH");
  	SEARCHROLEIDFROM.value = getBlankValue(SEARCHROLEIDFROM);
  	SEARCHROLEIDTO.value = getBlankValue(SEARCHROLEIDTO);
  	SEARCHROLENAME.value = getBlankValue(SEARCHROLENAME);
  	SEARCHDEPT.value = getBlankValue(SEARCHDEPT);
  	SEARCHAUTH.value = getBlankValue(SEARCHAUTH);
}

function maximizeResult(ROLEID){
	window.document.frmMstRole.ROLEID.value = ROLEID;
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
	window.document.frmMstRole.btnclick.value=btnclick;
	//clear the search fields of inactive values
	setSearchParamsBlank();
	if("GO"==btnclick){
	    window.document.frmMstRole.method = "post";
		window.document.frmMstRole.submit();
	}
	if("N"==btnclick){
	    window.document.frmMstRole.method = "post";
		window.document.frmMstRole.submit();
	}
	if("S"==btnclick){
		window.document.frmMstRole.submit();
	}
	if("C"==btnclick){
	    window.document.frmMstRole.method = "post";
		window.document.frmMstRole.submit();
	}
} 


function bodyOnload(){
	showDiv();
	document.frmMstRole.ROLENAME1.focus();
    ////code for handling search paramas
	label2value();	
	setSessionSeachParams('SEARCHROLEIDFROM', '<c:out value="<%=sBean.getField1()%>"/>');
	setSessionSeachParams('SEARCHROLEIDTO', '<c:out value="<%=sBean.getField2()%>"/>');
	setSessionSeachParams('SEARCHROLENAME', '<c:out value="<%=sBean.getField3()%>"/>');
	setSessionSeachParams('SEARCHDEPT', '<c:out value="<%=sBean.getField4()%>"/>');
	setSessionSeachParams('SEARCHDEPT_DRP', '<c:out value="<%=sBean.getField4()%>"/>');
	setSessionSeachParams('SEARCHAUTH', '<c:out value="<%=sBean.getField5()%>"/>');
	setSessionSeachParams('SEARCHAUTH_DRP', '<c:out value="<%=sBean.getField5()%>"/>');
	//call getSearch to show prvious search
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

function getReturnData(data){
	alert(data);
	window.location.reload();	
}

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
	
	function setCode(obj) {
		setSessionSeachParams('SEARCHDEPT', obj.value);
	}  

	function setCodeAuth(obj) {
		setSessionSeachParams('SEARCHAUTH', obj.value);
	}  
	</script>
</head>
<body onload="bodyOnload();">
<FORM action="${pageContext.request.contextPath}/MstRoleController" method="post"  name="frmMstRole">
<%
	String maxRoleSQL="SELECT MAX(ROLEID) FROM MSTROLE";
	ArrayList<CommonBean> maxRoleId = (new CommonDAO()).getSQLResult(maxRoleSQL, 1);
	
	String deptCodeSQL="SELECT DEPTCODE, DEPTNAME FROM MSTDEPT WHERE DEPTNAME IS NOT NULL ORDER BY DEPTNAME"; 
	ArrayList deptCodeList = (new CommonDAO()).getSQLResult(deptCodeSQL, 2);

	String authLevelSQL="SELECT AUTHLEVEL, AUTHNAME FROM MSTAUTHLEVEL ORDER BY AUTHNAME"; 
	ArrayList authLevelList = (new CommonDAO()).getSQLResult(authLevelSQL, 2);
	
%>
<table width="100%" align="left">
	<tr>  
      	<td colspan="3"> 
      		<font size="3" > 
      			<b><i>Masters</i> - Role Master</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">                
        </td>
 	</tr>

	<tr>
		<td valign="top">
		<td width="*" valign="top" align="left" valign="top">
		<fieldset id="Search">
		<table border="0"> 
 				<tr>
 					<td colspan="2"><img border="0" src="images/Search1.png"
								width="91" height="35"></td></tr>
 				<tr>
 					<td align="left" >
 						<label for="SEARCHROLEIDFROM">Role ID From...</label>
 						<input type="text" size="20" name="SEARCHROLEIDFROM" id="SEARCHROLEIDFROM"  ></td>
 					<td align="left" >
 						<label for="SEARCHROLEIDTO">... Role ID To</label>
 						<input type="text" size="20" name="SEARCHROLEIDTO" id="SEARCHROLEIDTO"  ></td></tr>
				<tr>
					<td colspan="2" ><label for="SEARCHROLENAME">Role Name</label><input type="text" size="20" name="SEARCHROLENAME" id="SEARCHROLENAME"  ></td>
 				</tr>
 				<tr>
					<td align="left" colspan="2">Department
					<select name="SEARCHDEPT_DRP" id="SEARCHDEPT_DRP" style="width: 180px" onchange="setCode(this);" > 
					<option value="" selected="selected"></option>
					<%
						for(int i=0;i<deptCodeList.size();i++){
						CommonBean beanDeptCodE=(CommonBean) deptCodeList.get(i);
					%>
						<option value="<c:out value='<%=beanDeptCodE.getField1()%>'/>"><c:out value='<%=beanDeptCodE.getField2()%>'/></option>
					<%}%>
				</select>
				<label for="SEARCHDEPT"></label><input type="hidden" size="20" name="SEARCHDEPT" id="SEARCHDEPT"  >
					</td>
				</tr>
			<tr>
				<td align="left"  colspan="2">Auth Level
					<select name="SEARCHAUTH_DRP" id="SEARCHAUTH_DRP" style="width: 180px" onchange="setCodeAuth(this);">
					<option value="" selected="selected"></option>
					<%
						for(int i=0;i<authLevelList.size();i++){
						CommonBean beanAuthLevel=(CommonBean) authLevelList.get(i);
					%>
						<option value="<c:out value='<%=beanAuthLevel.getField1()%>'/>"><c:out value='<%=beanAuthLevel.getField2()%>'/></option>
					<%}%>
				</select>
				<label for="SEARCHAUTH"></label><input type="hidden" size="20" name="SEARCHAUTH" id="SEARCHAUTH"  ></td>
			</tr>
			<tr>
				<td align="right" colspan="2"><input type="button" name="" value="&gt;&gt;" onclick="getSearch(this);"></td>
			</tr>
			<tr>
						<td align="right" ><img width="20" height="20" src="images/printer_new.png" onclick="window.open('PopUpPrintReport.jsp?flagecr=<c:out value="${enc:encrypt('y')}" />');" title="Print"></img></td>
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

		<table id="data" width="50%">
					
					
					<tr class="trodd">
						<td nowrap  valign="middle" width="159"><b>Role ID</b><font
							color="red">*</font> :</td>
						<td valign="middle" width="302">
						<input type="text" name="ROLEID" id="ROLEID" class="readonly" title="Auto Generated Field" value="<c:out value='<%=mstRoleBean.getROLEID()%>'/>" onkeypress="chknumeric();" maxlength="4" readonly  >
						
						<%
						CommonBean beanCommon=(CommonBean) maxRoleId.get(0);
						%>
						<font color="green">Last Role ID : <c:out value='<%= beanCommon.getField1()%>'/></font>
						</td>


					</tr>
					<tr class="trodd">
						<td nowrap valign="middle" width="159">Role Name<font
							color="red">*</font> :</td>
						<td nowrap="nowrap" valign="middle" width="302"><input
							type="text" name="ROLENAME1" id="ROLENAME1" maxlength="20"  
							value="<c:out value='<%=mstRoleBean.getROLENAME()%>'/>" size="20"></td>
					</tr>
					<tr class="trodd">
						<td nowrap valign="middle" width="159">Full Name<font
							color="red">*</font> :</td>
						<td nowrap="nowrap" valign="middle" width="302"><input
							type="text" name="FNAME1" id="FNAME1" maxlength="200"  
							value="<c:out value='<%=mstRoleBean.getFNAME()%>'/>" size="20"></td>
							
					</tr>

			<tr class="trodd">
				<td nowrap valign="middle" width="159">Department :</td>
				<td nowrap="nowrap" valign="middle" width="302">
				<select name="DEPTCODE" id="DEPTCODE" style="width: 180px"  > 
					<option value="" selected="selected"></option>
					<%
						for(int i=0;i<deptCodeList.size();i++){
						CommonBean beanDeptCodE=(CommonBean) deptCodeList.get(i);
					%>
						<option value="<c:out value='<%=beanDeptCodE.getField1()%>'/>" <%=beanDeptCodE.getField1().equalsIgnoreCase(mstRoleBean.getDEPTCODE())?"selected":""%>><c:out value='<%=beanDeptCodE.getField2()%>'/></option>
					<%}%>
				</select>
				</td>
			</tr>
			<tr class="trodd">
				<td nowrap valign="middle" width="159">Authentication Level :</td>
				<td nowrap="nowrap" valign="middle" width="302">
				<select name="AUTHLEVEL" id="AUTHLEVEL" style="width: 180px"  >
					<option value="" selected="selected"></option>
					<%
						for(int i=0;i<authLevelList.size();i++){
						CommonBean beanAuthLevel=(CommonBean) authLevelList.get(i);
					%>
						<option value="<c:out value='<%=beanAuthLevel.getField1()%>'/>" <%=beanAuthLevel.getField1().equalsIgnoreCase(mstRoleBean.getAUTHLEVEL())?"selected":""%>><c:out value='<%=beanAuthLevel.getField2()%>'/></option>
					<%}%>
				</select>
				</td>
			</tr>
			<tr >
						<td nowrap  valign="middle" colspan="2">
						<table border="0" width="167">
							<tbody>
								<tr align="center">
									<td width="101"><input type="button" name="btnSAVE"	class="butts" value=" Save " onclick="submitForm('S')"></td>
									<td width="101"><input type="button" name="btnCLEAR" class="butts" value=" Clear " onclick="submitForm('C')">
										<input type="hidden" name="btnclick" id="btnclick">
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

</form>
</body>
</html>
