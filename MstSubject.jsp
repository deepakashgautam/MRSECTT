<!DOCTYPE html>
<%@page import="in.org.cris.mrsectt.Beans.MstSubject"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="javax.ejb.SessionBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%><html>
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

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/MstSubjectDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<%
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
	MstSubject	mstSubjectBean =  (request.getAttribute("mstSubjectBean")!=null) ?(MstSubject)request.getAttribute("mstSubjectBean"): new MstSubject();
	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();
	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
	String departmentCode = StringFormat.nullString((String)request.getAttribute("deptCode"));
	CommonBean bean = new CommonBean();
	departmentCode = (departmentCode != "") ? departmentCode : sessionBean.getDEPTCODE();
	
	String QueryR="SELECT SUBJECTCODE, SUBJECTNAME, (SELECT DEPTNAME FROM MSTDEPT WHERE DEPTCODE=SUBJECTDEPT) SUBJECTDEPT, SUBJECTTYPE, ISBUDGET FROM MSTSUBJECT WHERE SUBJECTDEPT = '"+departmentCode+"' AND SUBJECTTYPE='R' ORDER BY SUBJECTCODE";
	ArrayList RsubjectList = (new CommonDAO()).getSQLResult(QueryR, 5);

	String QueryF="SELECT SUBJECTCODE, SUBJECTNAME, (SELECT DEPTNAME FROM MSTDEPT WHERE DEPTCODE=SUBJECTDEPT) SUBJECTDEPT, SUBJECTTYPE, ISBUDGET FROM MSTSUBJECT WHERE SUBJECTDEPT = '"+departmentCode+"' AND SUBJECTTYPE='F' ORDER BY SUBJECTCODE"; 
	ArrayList FsubjectList = (new CommonDAO()).getSQLResult(QueryF, 5);

	String deptList = "SELECT DEPTCODE, DEPTNAME FROM MSTDEPT"; 
	ArrayList arrDeptList = (new CommonDAO()).getSQLResult(deptList, 2);

	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
//	style="height:235px; overflow:auto; overflow-x: hidden;"
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
		//	document.getElementById("td3").style.display='block';
			//document.getElementById("td3").style.display='none';
		}else{
			//document.getElementById("td3").style.display='block';
			document.getElementById("td2").style.display='none';
		//	document.getElementById("td3").style.display='none';
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
  	var sSUBJECTCODE =  window.document.getElementById("sSUBJECTCODE");
  	var sSUBJECTNAME =  window.document.getElementById("sSUBJECTNAME");
  	var sSUBJECTDEPT =  window.document.getElementById("sSUBJECTDEPT");
  	//Check for atleast One field for search
  	if(    getBlankValue(sSUBJECTCODE)=='' && getBlankValue(sSUBJECTNAME)=='' && getBlankValue(sSUBJECTDEPT)==''){
  	  	//nothing to search ...simply return 
  		return false;
  	}
	MstSubjectDAO.getSearchData(getBlankValue(sSUBJECTCODE), getBlankValue(sSUBJECTNAME), getBlankValue(sSUBJECTDEPT), getSearchData);
  }

function getSearchData(data){
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" width="10px" id="sth00"><b>SNo.</b></th><th align="center"  id="sth1" ><b>Sub. Code</b></th><th align="center"  id="sth1" ><b>Sub. Name</b></th><th align="center" id="sth3" width="10px"><b>Sub. Dept.</b></th><th align="center" id="sth3" width="10px"><b>Sub. Type</b></th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	for(var loop=0; loop < data.length; loop++){
		var mstSubjectBean = data[loop];
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'"><td align="center" id="std00"> '+(loop+1)+' </td> <td  id="std1"> '+mstSubjectBean.SUBJECTCODE+' </td><td  id="std1"> '+mstSubjectBean.SUBJECTNAME+' </td> <td  id="std3" width="10px"> '+mstSubjectBean.SUBJECTDEPT+' </td><td  id="std3" width="10px"> '+mstSubjectBean.SUBJECTTYPE+' </td></tr>';
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
	var obj=document.getElementById("td3");
//	if(obj.style.display=='none'){
//	ShowSearch(obj);
//	}
}
 
function setSearchParamsBlank(){
	var sSUBJECTCODE =  window.document.getElementById("sSUBJECTCODE");
  	var sSUBJECTNAME =  window.document.getElementById("sSUBJECTNAME");
  	var sSUBJECTDEPT =  window.document.getElementById("sSUBJECTDEPT");
  	sSUBJECTCODE.value = getBlankValue(sSUBJECTCODE);
  	sSUBJECTNAME.value = getBlankValue(sSUBJECTNAME);
  	sSUBJECTDEPT.value = getBlankValue(sSUBJECTDEPT);
}

function maximizeResult(sSUBJECTCODE){
	window.document.frmMstLogin.sSUBJECTCODE.value = sSUBJECTCODE;
	setSearchParamsBlank();
	submitForm('GO');
}

function setSessionSeachParams(OBJNAME, objValue){
		if(objValue!=''){
			window.document.getElementById(OBJNAME).value = objValue;
    		window.document.getElementById(OBJNAME).className = 'active';
    	}	
	}
	
function bodyOnLoad(){
	showDiv();
	label2value();	
	setSessionSeachParams('sSUBJECTCODE', '<c:out value="<%=sBean.getField1()%>"/>');
	setSessionSeachParams('sSUBJECTNAME', '<c:out value="<%=sBean.getField2()%>"/>');
	setSessionSeachParams('sSUBJECTDEPT', '<c:out value="<%=sBean.getField3()%>"/>');
 	getSearch();
 	document.frmMstSubject.department.focus();
}
 //	CODE FOR SEARCH ENDS HERE...  

 function submitForm(btnclick) {
	window.document.frmMstSubject.btnclick.value=btnclick;
	setSearchParamsBlank();
	if("GO"==btnclick){
	    window.document.frmMstSubject.method = "post";
		window.document.frmMstSubject.submit();
	}
	if("D"==btnclick){
	    window.document.frmMstSubject.method = "post";
		window.document.frmMstSubject.submit();
	}
	
	if("S"==btnclick){
		if( containsDuplicate('RSUBCODE')) {
			disabled('RSUBDEPT',false);
			disabled('RISBUDGET',false);
			disabled('FSUBDEPT',false);
			disabled('FISBUDGET',false);
			window.document.frmMstSubject.submit();
		}
	}
	
	if("C"==btnclick){
	    window.document.frmMstSubject.method = "post";
		window.document.frmMstSubject.submit();
	}
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
function ReditRow(indValue){
	window.document.getElementsByName("RSUBNAME")[indValue].readOnly = false;
	window.document.getElementsByName("RISBUDGET")[indValue].disabled = false;
	window.document.getElementsByName("RNE")[indValue].value = 'E';
}
function FeditRow(indValue){
	window.document.getElementsByName("FSUBNAME")[indValue].readOnly = false;
	window.document.getElementsByName("FISBUDGET")[indValue].disabled = false;
	window.document.getElementsByName("FNE")[indValue].value = 'E';
}

function setIsBudgetF(obj,indValue) {
if(indValue == '0') {
	alert(window.document.getElementsByName("FSUBNAME").length-1);
	indValue = window.document.getElementsByName("FSUBNAME").length-1;
}
	if(obj.value=="Y"){
		window.document.getElementsByName("FISBUDGET")[indValue].value = 'Y';
	}
	else if(obj.value=="N"){
		window.document.getElementsByName("FISBUDGET")[indValue].value = 'N';
	}
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
<body onload="showDiv(); bodyOnLoad();">
<FORM action="${pageContext.request.contextPath}/MstSubjectController" method="post"  name="frmMstSubject">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Admin</i> - Subject Master</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table>
<table width="100%" align="left">
<tr>
	<td>
	<table width="50%" align="left"  style="vertical-align: top;" id="data">
 	<tr>
 		<td width="*" valign="top" align="left" >
 		<fieldset id="Search">
 			<table id="data" width="170">
 				<tr>
 					<td><font size="+1">Search</font></td>
						<td></td>
					</tr>
 				<tr>
 					<td></td>
						<td align="right"></td>
					</tr>
 				<tr>
 					<td><label for="sSUBJECTCODE">Subject Code...</label>
 						<input type="text" size="20" name="sSUBJECTCODE" id="sSUBJECTCODE"  ></td>
					</tr>
					<tr>
						<td><label for="sSUBJECTNAME">Subject Name...</label>
 						<input type="text" size="20" name="sSUBJECTNAME" id="sSUBJECTNAME"  ></td>
					</tr>
					<tr>
 					<td><label for="sSUBJECTDEPT">Subject Department</label>
 						<input type="text" size="20" name="sSUBJECTDEPT" id="sSUBJECTDEPT"  ></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="right"><input type="button" name="" value="&gt;&gt;" onclick="getSearch(this)"></td>
					</tr>
					</table>
					<div id="searchResult"   style="height:200px; overflow:auto; overflow-x: hidden;"></div>
				</fieldset>
		</td>
		
		<td width="5px;" bgcolor="#F2F3F1" onclick="showHideTD(this)" title="Show search criteria" style="cursor: pointer;" valign="top" id="showhidecol">
			<div id="scrollingDiv"> 
       			<img src="images/next.gif" ></div></td>
 		<td id="td2" width="50%" align="center" valign="top">
 <table><tr>
 		<td colspan="2">Department : <select name="department" id="department" style="width: 200px; height: 20px;">
			<%
				for(int j=0;j< arrDeptList.size();j++) {
					CommonBean cbn = (CommonBean) arrDeptList.get(j);	%>
					<option value="<c:out value='<%=cbn.getField1()%>'/>" <%=cbn.getField1().equalsIgnoreCase(departmentCode)?"selected":""%>><c:out value='<%= cbn.getField2() %>'/></option>
			 <%}%>
			</select>	<input type="button" name="search" id="search" value="Go" class="butts" onclick="submitForm('D')" style="height: 20px; width: 35px; text-align: center; font-weight: bold">
		</td></tr>
 <tr valign="top"><td><fieldset><b><font size="+1">Reference</font></b> 
<TABLE >
	<TBODY>
		<tr class="treven">
		<TD>S.No.</TD>
			<td nowrap valign="top" align="center" width="141"><b><font
				color="red"></font> Sub.<font color="red">*</font></b></td>
			<td valign="top" align="center" width="159">Subject</td>
							<td valign="top" align="center" width="118">IsBudget</td>
							<td valign="top" ></td>
		</tr>
	</TBODY>
	<TBODY id="Rbaserow">
		<TR class="treven" align="left" style="display: none;">
		<td></td>
			<TD><input type="hidden" name="dtlid" value="">
			<input	type="text" name="RSUBCODE" id="RSUBCODE" maxlength="3" size="10" style="text-transform: uppercase;"  ></td>
			<TD><input type="text" name="RSUBNAME" maxlength="30" size="30"></td>
			<td><select name="RISBUDGET" id="RISBUDGET">
					<option></option>
					<option value="Y">Budget</option>
					<option value="N">Non Budget</option>
				</select>
			</td>
			<td>
			<TD><input type="hidden" name="RISNEW" id="RISNEW" value="Y" size="5">
				<input type="hidden" name="RNE" id="RNE" value="E" size="5">
				<input type="hidden" name="RSUBTYPE" id="RSUBTYPE" size="5">
				<input type="hidden" name="dtlid" value=""></td>
		</TR>
<%
	for(int i=0;i<RsubjectList.size();i++){
	CommonBean beanCommon=(CommonBean) RsubjectList.get(i);
%>
		<TR class="<%=i%2==0?"trodd":"trodd" %>" align="left" >
		<TD><%= i+1 %></TD>
			<TD><INPUT type="hidden" name="dtlid" value="<c:out value='<%=bean.getField1()%>'/>">
				<input class="readonly"	type="text" name="RSUBCODE" id="RSUBCODE" value="<c:out value='<%=beanCommon.getField1() %>'/>" maxlength="3" size="10" readonly  ></TD>
			<td><input type="text" name="RSUBNAME" id="RSUBNAME" id="RSUBNAME" maxlength="30" value="<c:out value='<%=beanCommon.getField2() %>'/>" size="30" readonly  ></td>
			<td><select name="RISBUDGET" id="RISBUDGET" disabled="disabled">
					<option></option>
					<option value="N" <%="N".equalsIgnoreCase(beanCommon.getField5())?"selected":""%> >Non-Budget</option>
					<option value="Y" <%="Y".equalsIgnoreCase(beanCommon.getField5())?"selected":""%> >Budget</option>
				</select>
			</td>
			<td><input type="hidden" name="RISNEW" id="RISNEW" value="N" size="5">
				<input type="hidden" name="RNE" id="RNE" value="NE" size="5">
				<input type="hidden" name="RSUBTYPE" id="RSUBTYPE" size="5" value="<c:out value='<%=beanCommon.getField4() %>'/>">
				<INPUT type="hidden" name="dtlid" value="<c:out value='<%=bean.getField1()%>'/>">
				<input type="button" name="REDIT" value="Edit" class="butts1" style="width: 40px;" onclick="ReditRow('<%= i+1%>');"></td>
		</TR>
<%}%>
	</TBODY>
	<TR>
		<TD align="left">
		<INPUT type="button" name="Rbtnaddrow" style="width: 30px;" value=" + " class="butts1" onclick="addrow(window.document.getElementById('Rbaserow'));"></TD>
	</TR>
	</TABLE>
</fieldset></td>
<td><fieldset><font size="+1">File</font>
<TABLE >
	<TBODY>
		<tr class="treven">
			<td nowrap valign="top" align="center"><input type="hidden" name="btnclick" ><b><font
				color="red"></font> Sub.<font color="red">*</font></b></td>
			<td valign="top" align="center" width="119">Subject</td>
							<td valign="top" align="center" width="118">IsBudget</td>
							<td valign="top"></td>
		</tr>
	</TBODY>
	<TBODY id="Fbaserow">
		<TR class="treven" align="left" style="display: none;">
			<TD><input type="hidden" name="dtlid" value="">
				<input type="text" name="FSUBCODE" maxlength="3" size="10"></td>
			<TD><input type="text" name="FSUBNAME" id="FSUBNAME" maxlength="30" size="30"></td>
			<td><select name="FISBUDGET" id="FISBUDGET">
					<option></option>
					<option value="Y">Budget</option>
					<option value="N">Non Budget</option>
				</select>
			</td>
			<TD><input type="hidden" name="FISNEW" id="FISNEW" value="Y" size="5">
				<input type="hidden" name="FNE" id="FNE" value="E" size="5">
				<input type="hidden" name="FSUBTYPE" id="FSUBTYPE" size="5">
				<input type="hidden" name="dtlid" value=""></td>
		</TR>
<%
	for(int i=0;i<FsubjectList.size();i++){
	CommonBean beanCommon=(CommonBean) FsubjectList.get(i);
%>
		<TR class="<%=i%2==0?"treven":"trodd" %>" align="left" >
			<TD><INPUT type="hidden" name="dtlid" value="<c:out value='<%=bean.getField1()%>'/>">
				<input class="readonly"	type="text" name="FSUBCODE" value="<c:out value='<%=beanCommon.getField1() %>'/>" maxlength="3" size="10" readonly></TD>
			<td><input type="text" name="FSUBNAME" id="FSUBNAME" maxlength="30" value="<c:out value='<%=beanCommon.getField2() %>'/>" size="30" readonly></td>
			<td><select name="FISBUDGET" id="FISBUDGET" disabled="disabled">
					<option></option>
					<option value="N" <%="N".equalsIgnoreCase(beanCommon.getField5())?"selected":""%> >Non-Budget</option>
					<option value="Y" <%="Y".equalsIgnoreCase(beanCommon.getField5())?"selected":""%> >Budget</option>
				</select>
			</td>
			<td><input type="hidden" name="FISNEW" id="FISNEW" value="N" size="5">
				<input type="hidden" name="FNE" id="FNE" value="NE" size="5">
				<input type="hidden" name="FSUBTYPE" id="FSUBTYPE" size="5" value="<c:out value='<%=beanCommon.getField4() %>'/>">
				<INPUT type="hidden" name="dtlid" value="<c:out value='<%=bean.getField1()%>'/>">
				<input type="button" name="FEDIT" value="Edit" class="butts1" style="width: 40px;" onclick="FeditRow('<%= i+1%>');"></td>
		</TR>
<%}%>
	</TBODY>
	<TR>
		<TD align="left">
		<INPUT type="button" name="Fbtnaddrow" style="width: 30px;" value=" + " class="butts1" onclick="addrow(window.document.getElementById('Fbaserow'));"></TD>
	</TR>
	</TABLE>
</fieldset></td></tr>
 		</table>
</TD>
<TR align="left">
<TD align="center" colspan="4">
						<table border="0">
							<tbody>
								<tr>
									<td>
<input type="button" name="btnSAVE" class="butts" value=" Save " onclick="submitForm('S')"></td>
									<td ><input type="button" name="btnCLEAR"
										class="butts" value=" Clear " onclick="submitForm('C')">
									</td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
				</table>	
</td>
</tr>
</table>
<!-- DO NOT DELETE BELOW TS!!! Following will create model. It will get uncommented once jsp is processed. -->
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
</FORM>
</body>
</html>
