<!DOCTYPE html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.dao.TrnFileManagementDAO"%>
<%@page import="in.org.cris.mrsectt.dao.TrnFileManagementDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnFileMarking"%>
<html>
<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>

<SCRIPT	src='${pageContext.request.contextPath}/dwr/interface/TrnFileManagementDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<LINK href="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />

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
</style>

<script type="text/javascript">
  
  <% 
  
  	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
   	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
   	String queryMarkingFrom = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND DEPTCODE='"+sessionBean.getDEPTCODE()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingFromList = (new CommonDAO()).getSQLResult(queryMarkingFrom, 2);
	
   	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND DEPTCODE='"+sessionBean.getDEPTCODE()+"' ORDER BY 2";
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	String querySubject = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 3);
  		
	ArrayList<TrnFileMarking> inboxList = (new TrnFileManagementDAO()).getInboxData(sessionBean.getLOGINASROLEID());
	ArrayList<TrnFileMarking> outBoxList = (new TrnFileManagementDAO()).getOutBoxData(sessionBean.getLOGINASROLEID());
  	
  	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
  %>
  
  
$().ready(function() { 
	    $("#sorttable0").tablesorter(); 
	    
	});
	
$().ready(function() {
    	$("#sorttable0").tableFilter();
	});
	
$().ready(function() { 
	    $("#sorttable1").tablesorter(); 
	    
	});
	
$().ready(function() {
    	$("#sorttable1").tableFilter();
	});
	
 $().ready(function() {     // call the tablesorter plugin     
		$("table").tablesorter({         // change the multi sort key from the default shift to alt button         
			sortMultiSortKey: 'altKey'     
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
  
function chkWorkDate(obj) {
	if(obj.value.length>0 && chkDate(obj)) {
		if(compareDateLTET(obj.value,'<%=serverDate%>'))
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

function makeFieldBlank(thisObj){
	if(isFieldBlank(thisObj)){
		thisObj.value='';
	}
}



function isFieldBlank(thisObj){
	//alert('thisObj : '+thisObj );
	//alert('thisObj : '+thisObj.value );
	//alert('thisObj name: '+thisObj.name );
	
	if(thisObj.name == 'REFNOFROM' && thisObj.value =='Ref. No from'){
			//alert('blank');
			return true;
	}
	if(thisObj.name == 'REFNOTO' && thisObj.value =='Ref. No to'){
			//alert('blank');
			return true;
	}
	if(thisObj.name == 'INCOMINGDATEFROM' && thisObj.value =='In. Date from'){
			//alert('blank');
			return true;
	}
	if(thisObj.name == 'INCOMINGDATETO' && thisObj.value =='In. Date to'){
			//alert('blank');
			return true;
	}
	if(thisObj.name == 'REFERENCENAMESEARCH' && thisObj.value =='Received from'){
			//alert('blank');
			return true;
	}
	if(thisObj.name == 'SUBJECTSEARCH' && thisObj.value =='Subject'){
			//alert('blank');
			return true;
	}
}

var OriginalMarkToMenu = window.document.getElementById("OUTBOXMARKTO");

function setReturnMarkToMenu(thisObj){
	alert(thisObj.name);
	alert(thisObj.value);
	
	var OUTBOXMARKTO = window.document.getElementById("OUTBOXMARKTO");
	
	if(thisObj.value = 'RET'){
		alert('Returning');	
			
		OUTBOXMARKTO.length = 0;
		
	}else{
		thisObj = OriginalMarkToMenu;
	}
	

}

function getBlankValue(thisObj){
	return (thisObj.className=='active')? thisObj.value:"";
	
}

function getSearch(thisObj){
  	
  	var FILENOID =  window.document.getElementById("FILENO");
  	var SUBJECTCODEID =  window.document.getElementById("SUBJECTCODE");
  	var SUBJECTID =  window.document.getElementById("SUBJECT");
  	var MARKINGFROMID =  window.document.getElementById("MARKINGFROM");
  	var MARKINGTOID =  window.document.getElementById("MARKINGTO");
  	var REMARKSID =  window.document.getElementById("REMARKS");
  	var MARKINGDATEFROMID =  window.document.getElementById("MARKINGDATEFROM");
  	var MARKINGDATETOID =  window.document.getElementById("MARKINGDATETO");
  	
  	
  	
  	var FILENO =  FILENOID.className=='active'?FILENOID.value:"";
  	var SUBJECTCODE = SUBJECTCODEID.value;
  	var SUBJECT =  SUBJECTID.className=='active'?SUBJECTID.value:"";
  	var MARKINGFROM =  MARKINGFROMID.value;
  	var MARKINGTO =  MARKINGTOID.value;
  	var REMARKS =  REMARKSID.className=='active'?REMARKSID.value:"";
  	var MARKINGDATEFROM =  MARKINGDATEFROMID.className=='active'?MARKINGDATEFROMID.value:"";
  	var MARKINGDATETO =  MARKINGDATETOID.className=='active'?MARKINGDATETOID.value:"";
  
  	 	
 //Check for atleast One field for search
  	if(    getBlankValue(FILENOID)=='' && SUBJECTCODE=='' && getBlankValue(SUBJECTID)=='' && MARKINGFROM==''
  		   && MARKINGTO=='' && getBlankValue(REMARKSID)=='' && getBlankValue(MARKINGDATEFROMID)=='' && getBlankValue(MARKINGDATETOID)==''
  	  ){
  	  	//nothing to search ...simply return 
  	  	window.document.getElementById("searchResult").style.display='none';
  		return false;
  	}
  	
	TrnFileManagementDAO.getSearchFileInboxOutBox(FILENO,SUBJECTCODE, SUBJECT, MARKINGFROM, MARKINGTO,REMARKS,MARKINGDATEFROM,MARKINGDATETO,'<%=sessionBean.getLOGINASROLEID()%>', getData);  	
  }
  
  function getData(data){
	window.document.getElementById("searchResult").style.display='block';
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" width="10px" id="sth00"><b>SNo.</b></th><th align="center" ><b>File No.</b></th><th align="center" style="display:none" id="sth1"><b>Marked on</b></th><th align="center" style="display:none" id="sth2"><b>Received from</b></th><th align="center" style="display:none" id="sth3"><b>Sub.</b></th> <th align="center"  nowrap="nowrap"><b>Subject</b></th><th align="center" style="display:none" id="sth4"><b>Acceptance Date</b></th><th align="center" style="display:none" id="sth5"><b>Mark To</b></th><th align="center" style="display:none" id="sth6"><b>Marking Remarks</b></th><th align="center" style="display:none" id="sth7"><b>Action</b></th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	for(var i=0; i < data.length; i++){
	var trclass = (i%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick=""><td align="center" id="std00"> '+(i+1)+' </td>  <td>'+data[i].field2+'</td><td style="display:none" id="std1"> '+data[i].field7+' </td><td style="display:none" id="std2"> '+data[i].field5+' </td><td style="display:none" id="std3"> '+data[i].field4+' </td><td>'+data[i].field3+'</td><td style="display:none" id="std4"> '+data[i].field10+' </td><td style="display:none" id="std5"> '+data[i].field6+' </td><td style="display:none" id="std6"> '+data[i].field8+' </td><td style="display:none" id="std7"> '+data[i].field9+' </td></tr>';
	}
	
	htmlText += '</tbody></table>';
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
	//alert(obj);
	if(obj.style.display=='none'){
	ShowSearch(obj);
	}
}

function ShowSearch(obj){
if(document.getElementById("sorttable")!=null){
if(document.getElementById("content")!=null){
		var idno = document.getElementById("content").getElementsByTagName('tr').length;
		//alert(idno);
		
		if(obj.style.display=='none'){
		
			document.getElementById("sth1").style.display='inline';
			document.getElementById("sth2").style.display='inline';
			document.getElementById("sth3").style.display='inline';
			document.getElementById("sth4").style.display='inline';
			document.getElementById("sth5").style.display='inline';
			document.getElementById("sth6").style.display='inline';
			document.getElementById("sth7").style.display='inline';
			//document.getElementById("sth8").style.display='inline';
			
			$("#sorttable").tableFilter();
		for(var i=0;i<idno;i++){
		
			document.getElementsByName("std1")[i].style.display='inline';
			document.getElementsByName("std2")[i].style.display='inline';
			document.getElementsByName("std3")[i].style.display='inline';
			document.getElementsByName("std4")[i].style.display='inline';
			document.getElementsByName("std5")[i].style.display='inline';
			document.getElementsByName("std6")[i].style.display='inline';
			document.getElementsByName("std7")[i].style.display='inline';
			//document.getElementsByName("std8")[i].style.display='inline';
			}
			
		
			
		
	}else{
			document.getElementById("sth1").style.display='none';
			document.getElementById("sth2").style.display='none';
			document.getElementById("sth3").style.display='none';
			document.getElementById("sth4").style.display='none';
			document.getElementById("sth5").style.display='none';
			document.getElementById("sth6").style.display='none';
			document.getElementById("sth7").style.display='none';
			//document.getElementById("sth8").style.display='none';
			// Hide Filter Row
			var y = window.document.getElementById("sorttable");
			//var trtag = y.getElementsByTagName("TR");
			//trtag[1].style.display = "none";
			y.deleteRow(1);
			for(i=0;i<idno;i++){
			document.getElementsByName("std1")[i].style.display='none';
			document.getElementsByName("std2")[i].style.display='none';
			document.getElementsByName("std3")[i].style.display='none';
			document.getElementsByName("std4")[i].style.display='none';
			document.getElementsByName("std5")[i].style.display='none';
			document.getElementsByName("std6")[i].style.display='none';
			document.getElementsByName("std7")[i].style.display='none';
			//document.getElementsByName("std8")[i].style.display='none';
			}
	
	}
}
}
}  


function showMarkingTree(FMID){
	//alert("refid i s "+ REFID);

	var col = new Array("#d69203","#927344","#ac5e09","#805e38","#451800","#d69203","#927344","#ac5e09","#805e38","#451800");
									
	var treeDiv  = window.document.getElementById("treeDiv");
	//alert(' treeDiv '+ treeDiv);
	
d = new dTree('d');
d.add(2,1,'<span class="treespan" name="treespan" style="color:'+col[2]+';">MT   - Returned on 28/12/2011 14:59:48</span>','1');
d.add(3,2,'<span class="treespan" name="treespan" style="color:'+col[2]+';">MR   - Received on 28/12/2011 15:05:23</span>','2');
d.add(4,3,'<span class="treespan" name="treespan" style="color:'+col[2]+';">MR   - Forwarded on 28/12/2011 15:05:23</span>','3');
d.add(5,4,'<span class="treespan" name="treespan" style="color:'+col[2]+';">AM/T&C   - Received on 28/12/2011 15:13:38</span>','4');
d.add(6,5,'<span class="treespan" name="treespan" style="color:'+col[2]+';">AM/T&C   - Forwarded on 28/12/2011 15:25:11</span>','5');
d.add(9,8,'<span class="treespan" name="treespan" style="color:'+col[2]+';">SO/TG/III   - Received on 28/12/2011 15:35:45</span>','6');
d.add(10,9,'<span class="treespan" name="treespan" style="color:'+col[2]+';">SO/TG/III   - Returned on 28/12/2011 15:36:18</span>','7');
d.add(11,10,'<span class="treespan" name="treespan" style="color:'+col[2]+';">DIR/T&C   - Received on 28/12/2011 15:38:22</span>','8');
d.add(12,11,'<span class="treespan" name="treespan" style="color:'+col[2]+';">DIR/T&C   - Forwarded on 28/12/2011 15:38:42</span>','9');
d.add(1,0,'<span class="treespan" name="treespan" style="color:'+col[2]+';">MT   - Received on 28/12/2011 14:58:54</span>','10');
d.add(0,-1,'<span class="treespan" name="treespan" style="color:'+col[2]+';">MR   - Forwarded on 28/12/2011 14:57:21</span>','11');
d.add(7,6,'<span class="treespan" name="treespan" style="color:'+col[2]+';">DIR/T&C   - Received on 28/12/2011 15:29:48</span>','12');
d.add(13,12,'<span class="treespan" name="treespan" style="color:'+col[2]+';">TG/III   - Received on 28/12/2011 15:42:10</span>','14');
d.add(14,13,'<span class="treespan" name="treespan" style="color:'+col[2]+';">TG/III   - Pending on 28/12/2011 15:42:10</span>','15');
	
	//alert(d);
	treeDiv.innerHTML=d;
//									d.openAll();
	
	
		  
}  
  
function confirmAction(fmId, index){

	var INACTION   = window.document.getElementsByName("INACTION")[index].value;
	var MARKINGTYPE   = window.document.getElementsByName("MARKINGTYPE")[index].value;
	TrnFileManagementDAO.setFileInboxAction(fmId, INACTION,'<%=sessionBean.getTENUREID()%>','<%=sessionBean.getLOGINASROLEID()%>','<%=sessionBean.getLOGINASROLENAME()%>',MARKINGTYPE, getReturnData);
}  


function getReturnData(data){
	alert(data);
	window.location.reload();
	
}

function setOutBoxMarkAction(index, fmId, outBoxMarkingFrom, subjectCode, subject ){

	var OUTBOXMARKTO  = window.document.getElementsByName("OUTBOXMARKTO")[index].value;
	var OUTBOXMARKINGREMARK  = window.document.getElementsByName("OUTBOXMARKINGREMARK")[index].value;
	var OUTACTION   = window.document.getElementsByName("OUTACTION")[index].value;
	var MARKINGTYPE   = window.document.getElementsByName("MARKINGTYPE")[index].value;
	
	TrnFileManagementDAO.setFileOutBoxAction(fmId, outBoxMarkingFrom, OUTBOXMARKTO, OUTBOXMARKINGREMARK, subjectCode, subject, OUTACTION,MARKINGTYPE, getOutReturnData);

}  

function getOutReturnData(data){
	//alert(data);
	window.location.reload();
}
  
  function setMaxRowsOfMarking(thisObj){
  	
  	var btnADD = window.document.getElementById("btnADD");
  	if(thisObj.value =='Y'){
  		btnADD.disabled = true;
  		window.document.getElementById("listMainBudget").style.display = 'block';
  	}else {
  		btnADD.disabled = false;
  		window.document.getElementById("listMainBudget").style.display = 'none';
  	}
  }
  
  function setTargetDate(oldDate, thisObj){
  
 var index =  getIndex(thisObj); 
  var newDate  = addDaysToDate(oldDate, thisObj.value);
  window.document.frm.TARGETDATE[index].value = newDate;
  
  }
  
  
  function showDiv(){
	<%
		if (msg.length() > 0 )
		{%>
	//	alert("Hiii");
		window.document.getElementById('updateDiv').style.display="block";
		window.document.getElementById('updateDivInner').style.display="block";
	<%	}%>
	
}

function hideDiv(){
	window.document.getElementById('updateDiv').style.display="none";
	window.document.getElementById('updateDivInner').style.display="none";
}

function callMe(){
	hideDiv();
}
 
 $(document).ready(function(){
  $("#MainTabDiv").tabs({ fx: { slide: true, fade: true, speed: 'normal' }  });
  label2value();
  });
 
function Calender1(obj){
obj.className='active';
newCalendar('MARKINGDATEFROM-L');
}
function Calender2(obj){
obj.className='active';
newCalendar('MARKINGDATETO-L');
} 

  
function chkWorkDate(obj) {
	if(obj.value.length>0 && chkDate(obj)) {
		if(compareDateLTET(obj.value,'<%=serverDate%>'))
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

 </script>

</head>
<body>
<form name="frm" id="frm" action="${pageContext.request.contextPath}/TrnReferenceController" method="post">
<table width="100%" align="left" style="vertical-align: top;" height="100%">
<tr>  
      	<td valign="top"> 
      		<font size="3" > 
      			<b><i>File-Forward</i> - Main Mail Box</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
        </td>
 	</tr>
	<tr>
		<td height="100%"><div style="height: 100%">
		<table width="100%" align="Center" style="vertical-align: middle;" height="100%">
				<tr>
					<td width="*" valign="top" align="left" height="100%">
					<fieldset id="Search">
					<table border="0" height="100%">
						<tr>
							<td valign="top">
							<table border="0" height="100%">
								<tr>
									<td colspan="2"><img border="0" src="images/Search1.png"
										width="91" height="35"></td>
								</tr>


								<tr>
									<td></td>
									<td align="right"></td>
								</tr>
								<tr>
									<td colspan="2"><label for="FILENO">File No.</label> <input
										type="text" name="FILENO" id="FILENO" style="width: 200px">
									</td>

								</tr>
								<tr>
									<td align="left" valign="bottom"><nobr><label
										for="MARKINGDATEFROM">Marking Date From</label> <input
										type="text" name="MARKINGDATEFROM" id="MARKINGDATEFROM"
										style="width: 80px" onblur="chkWorkDate(this);"><input
										type="button" class="calbutt" name="mrkdtfrm"
										onclick="Calender1(document.getElementById('MARKINGDATEFROM'))"></nobr></td>
									<td align="left" valign="bottom"><nobr><label
										for="MARKINGDATETO">Marking Date To</label> <input type="text"
										name="MARKINGDATETO" id="MARKINGDATETO" style="width: 80px"
										onblur="chkWorkDate(this);"><input type="button"
										class="calbutt" name="mrkdtfrm"
										onclick="Calender2(document.getElementById('MARKINGDATETO'))"></nobr></td>
								</tr>
								<tr>
									<td align="left" valign="bottom"><select
										name="SUBJECTCODE" id="SUBJECTCODE" style="width: 100px">
										<option value="">Subject Code</option>
										<%
							for(int i=0;i<subjectList.size();i++){
							CommonBean beanCommon = (CommonBean) subjectList.get(i);
							%>
										<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>

										<%
						}%>
									</select></td>
									<td align="left" valign="bottom"><label for="SUBJECT">Subject</label>
									<input type="text" name="SUBJECT" id="SUBJECT"
										style="width: 100px"></td>
								</tr>

								<tr>
									<td align="left"><select name="MARKINGFROM"
										id="MARKINGFROM" style="width: 100px">
										<option value="">Received From</option>
										<%
							for(int i=0;i<markingFromList.size();i++){
							CommonBean beanCommon = (CommonBean) markingFromList.get(i);
							%>
										<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
										<%
						}%>
									</select></td>
									<td align="left"><select name="MARKINGTO" id="MARKINGTO"
										style="width: 100px">
										<option value="">Mark To</option>
										<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
										<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
										<%
						}%>
									</select></td>
								</tr>
								<tr>
									<td align="left" colspan="2"><label for="REMARKS">Remarks</label>
									<input type="text" name="REMARKS" id="REMARKS"
										style="width: 200px"></td>
								</tr>
								<tr>
									<td align="right"></td>
									<td align="right"><input type="button" name=""
										value="&gt;&gt;" onclick="getSearch(this);"></td>
								</tr>

							</table>
							</td>
							<td align="center"></td>
						</tr>
					</table>
					</fieldset>
					<div id="searchResult" style="height:200px; overflow:auto; overflow-x: hidden;"></div>
					</td>

					<td width="5px;" onclick="showHideTD(this)" bgcolor="#F2F3F1"
						title="Show search criteria" style="cursor: pointer;" valign="top"
						id="showhidecol">
					<div id="scrollingDiv"><img src="images/next.gif"></div>
					</td>

					<td id="td2" width="80%" valign="top">

					<fieldset>

					<table id="data" width="100%">
						<tbody id="Forwarded">
							<tr height="50%">
								<td width="50%" style="border: 1px dotted;">
								<fieldset><legend><b><font size="+1">Inbox</font></b></legend>
								<table width="100%" class="tablesorter" id="sorttable0">
									<thead>
										<tr class="treven">
											<th>File No.</th>
											<th>Marked On</th>
											<th>Received from</th>
											<th>Subject Code</th>
											<th>Subject</th>
											<th>Remarks</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody id="content">
										<%
						for(int i=0; i<inboxList.size();i++){
							TrnFileMarking inBoxBean = inboxList.get(i);
							%>
										<tr class="odd">
											<td><%=inBoxBean.getFILENO() %></td>
											<td><%=inBoxBean.getMARKINGDATE() %></td>
											<td><%=inBoxBean.getMARKINGFROM() %></td>
											<td><%=inBoxBean.getSUBJECTCODE() %></td>
											<td><%=inBoxBean.getSUBJECT() %></td>
											<td><%=inBoxBean.getMARKINGREMARK() %></td>
											<td><input type="hidden" name="MARKINGTYPE"
												id="MARKINGTYPE" value="<%= inBoxBean.getMARKINGTYPE()%>"><select
												name="INACTION" id="INACTION" style="width: 100px">
												<option value="" selected="selected">-select-</option>
												<option value="RCD"
													<%="RCD".equalsIgnoreCase(inBoxBean.getACTION())?"selected":""%>>Received</option>
												<option value="NOR"
													<%="NOR".equalsIgnoreCase(inBoxBean.getACTION())?"selected":""%>>Not
												Received</option>

											</select><input type="button" value="confirm" class="butts"
												onclick="confirmAction('<%=inBoxBean.getFMID()%>', '<%=i%>');"></td>
										</tr>
										<%
						}
						%>
									</tbody>
								</table>
								</fieldset>
								</td>

							</tr>

							<tr height="50%">
								<td width="50%" style="border: 1px dotted;">
								<fieldset><legend><b><font size="+1">Outbox</font></b></legend>

								<table width="100%" class="tablesorter" id="sorttable1">
									<thead>
										<tr class="treven">
											<th>File No.</th>
											<th>Marked On</th>
											<th>Received from</th>
											<th>Subject Code</th>
											<th>Subject</th>
											<th>Remarks</th>
											<th>Acceptance Date</th>
											<th>Marking Remarks</th>
											<th>Mark To</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody id="content">
										<%
						for(int i=0; i<outBoxList.size();i++){
							TrnFileMarking outBoxBean = outBoxList.get(i);
							%>
										<tr class="odd">
											<td><%=outBoxBean.getFILENO() %></td>
											<td><%=outBoxBean.getMARKINGDATE() %></td>
											<td><%=outBoxBean.getMARKINGFROM() %></td>
											<td><%=outBoxBean.getSUBJECTCODE() %></td>
											<td><%=outBoxBean.getSUBJECT() %></td>
											<td><%=outBoxBean.getMARKINGREMARK() %></td>
											<td><%= outBoxBean.getACTIONDATE()%></td>
											<td><input type="text" name="OUTBOXMARKINGREMARK"
												value="<%= outBoxBean.getMARKINGREMARK()%>"><input
												type="hidden" name="MARKINGTYPE" id="MARKINGTYPE"
												value="<%= outBoxBean.getMARKINGTYPE()%>"></td>
											<td><select name="OUTBOXMARKTO" style="width: 120px">
												<option value="">-select-</option>
												<%
											for(int ii=0;ii<markingToList.size();ii++){
											CommonBean beanCommon = (CommonBean) markingToList.get(ii);
											%>
												<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
												<%
										}%>
											</select></td>
											<td><select name="OUTACTION" id="OUTACTION"
												style="width: 100px">
												<option value="" selected="selected">-select-</option>
												<option value="FOW"
													<%="FOW".equalsIgnoreCase(outBoxBean.getACTION())?"selected":""%>>Forward</option>
												<option value="RET"
													<%="RET".equalsIgnoreCase(outBoxBean.getACTION())?"selected":""%>>Return</option>
											</select><input type="button" name="btnMARK" value="confirm"
												class="butts"
												onclick="setOutBoxMarkAction('<%=i%>','<%=outBoxBean.getFMID()%>', '<%=sessionBean.getLOGINASROLEID() %>', '<%=outBoxBean.getSUBJECTCODE()%>', '<%=outBoxBean.getSUBJECT()%>'  );"></td>
										</tr>
										<%
							
						}
						%>
									</tbody>
								</table>
								</fieldset>
								</td>

							</tr>
						</tbody>
					</table>
					</fieldset>


					</td>
				</tr>
		</table>
		</div>
		</td>
	</tr>
</table></form></body>
</html>
