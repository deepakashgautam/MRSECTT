<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%><html>
<%@page import="in.org.cris.mrsectt.Beans.MstDepartment"%>
<%@page import="in.org.cris.mrsectt.dao.MstDepartmentDAO"%>
<html> 
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

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/MstDepartmentDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<%
	MstDepartment mstDepartmentBean =  (request.getAttribute("mstDepartmentBean")!=null) ?(MstDepartment)request.getAttribute("mstDepartmentBean"): new MstDepartment();
	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();

	String btnclick =  request.getAttribute("btnclick")!=null ? request.getAttribute("btnclick").toString():"";
	String msg = StringFormat.nullString((String)session.getAttribute("msg"));	
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
  	var sDEPTCODE =  window.document.getElementById("sDEPTCODE");
  	var sDEPTNAME =  window.document.getElementById("sDEPTNAME");
  	//Check for atleast One field for search
  	if(    getBlankValue(sDEPTCODE)=='' && getBlankValue(sDEPTNAME)==''){
  	  	//nothing to search ...simply return 
  		return false;
  	}
	MstDepartmentDAO.getSearchData(getBlankValue(sDEPTCODE), getBlankValue(sDEPTNAME), getSearchData);  
  }

function getSearchData(data){
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" width="10px" id="sth00"><b>SNo.</b></th><th align="center"  id="sth1" ><b>Dept. Code</b></th><th align="center"  id="sth1" ><b>Dept. Name</b></th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	for(var loop=0; loop < data.length; loop++){
		var mstDepartmentBean = data[loop];
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick = "maximizeResult('+mstDepartmentBean.DEPTCODE+');"><td align="center" id="std00"> '+(loop+1)+' </td> <td  id="std1"> '+mstDepartmentBean.DEPTCODE+' </td><td  id="std1"> '+mstDepartmentBean.DEPTNAME+' </td></tr>';
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
	var sDEPTCODE =  window.document.getElementById("sDEPTCODE");
  	var sDEPTNAME =  window.document.getElementById("sDEPTNAME");
  	sDEPTCODE.value = getBlankValue(sDEPTCODE);
  	sDEPTNAME.value = getBlankValue(sDEPTNAME);
}

function maximizeResult(DEPTCODE){
	window.document.frmMstDepartment.DEPTCODE.value = DEPTCODE;
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
	window.document.frmMstDepartment.btnclick.value=btnclick;
	//clear the search fields of inactive values
	setSearchParamsBlank();
	if("GO"==btnclick){
	    window.document.frmMstDepartment.method = "post";
		window.document.frmMstDepartment.submit();
	}
	if("N"==btnclick){
	    window.document.frmMstDepartment.method = "post";
		window.document.frmMstDepartment.submit();
	}
	if("S"==btnclick){
		window.document.frmMstDepartment.submit();
	}
	if("C"==btnclick){
	    window.document.frmMstDepartment.method = "post";
		window.document.frmMstDepartment.submit();
	}
} 

function bodyOnload(){
	showDiv();
	document.frmMstDepartment.DEPTNAME.focus();
    ////code for handling search paramas
	label2value();	
	setSessionSeachParams('sDEPTCODE', '<%=sBean.getField1()%>');
	setSessionSeachParams('sDEPTNAME', '<%=sBean.getField2()%>');
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
</head>
<body onload="bodyOnload();">
<FORM action="${pageContext.request.contextPath}/MstDepartmentController" method="post"  name="frmMstDepartment">
<%
	String maxDepartmentSQL="SELECT MAX(DEPTCODE) FROM MSTDEPT";
	ArrayList<CommonBean> maxDepartmentId = (new CommonDAO()).getSQLResult(maxDepartmentSQL, 1);
%>
<table width="100%" align="left">
	<tr>  
      	<td colspan="4"> 
      		<font size="3" > 
      			<b><i>Masters</i> - Department Master</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">                
        </td>
 	</tr>

	<tr>
		<td valign="top">
		<td width="*" valign="top" align="left" valign="top">
		<fieldset id="Search">
		<table width="170" border="0"> 
 				<tr>
 					<td colspan="2"><img border="0" src="images/Search1.png"
								width="91" height="35"></td></tr>
 				<TR>
 					<td align="left" width="50">
 						<label for="sDEPTCODE">Dept.</label>
 						<input type="text" size="20" name="sDEPTCODE" id="sDEPTCODE"></td></TR>
				<TR>
					<td align="left" width="50">
						<label for="sDEPTNAME">Department</label>
						<input type="text" size="20" name="sDEPTNAME" id="sDEPTNAME"></td>
 				</tr>
 				<tr>
					<td align="right"><input type="button" name="" value="&gt;&gt;" onclick="getSearch(this);"></td>
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
						<td nowrap  valign="middle" width="139"><b>Dept.</b><font
							color="red"></font></td>
						<td valign="middle" width="386">
						<input type="text" name="DEPTCODE" id="DEPTCODE" class="readonly" title="Auto Generated Field" value="<%=mstDepartmentBean.getDEPTCODE()%>" maxlength="4" readonly >
						
						<%
						CommonBean beanCommon=(CommonBean) maxDepartmentId.get(0);
						%>
						<font color="green">Last Department Code : <%= beanCommon.getField1()%></font>
						</td>


					</tr>
					<tr class="trodd">
						<td nowrap valign="middle" width="139">Department<font
							color="red"></font></td>
						<td nowrap="nowrap" valign="middle" width="386"><input type="text" name="DEPTNAME" id="DEPTNAME" maxlength="400"
							value="<%=mstDepartmentBean.getDEPTNAME()%>" size="50">
							<input type="hidden" name="btnclick" id="btnclick">
							</td>
					</tr>
			<tr >
						<td nowrap  valign="middle" colspan="2">
						<table >
							<tbody>
								<tr align="center">
									<td><input type="button" name="btnSAVE"	class="butts" value=" Save " onclick="submitForm('S')"></td>
									<td><input type="button" name="btnCLEAR" class="butts" value=" Clear " onclick="submitForm('C')"></td>
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
