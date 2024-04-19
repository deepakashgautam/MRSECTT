<!DOCTYPE html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.dao.TrnFileManagementDAO"%>
<%@page import="in.org.cris.mrsectt.dao.TrnFileManagementDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnFileIntMarking"%>
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
<!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/jquery/themes<%=theme%>/base/ui.all.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />  -->
<LINK href="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">

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
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingFromList = (new CommonDAO()).getSQLResult(queryMarkingFrom, 2);
	
   	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2";
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	
	String querySubject = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 3);
  		
	ArrayList<TrnFileIntMarking> inboxList = (new TrnFileManagementDAO()).getIntMarkingData(sessionBean.getLOGINASROLEID(),"P");
	ArrayList<TrnFileIntMarking> inboxList1 = (new TrnFileManagementDAO()).getIntMarkingData(sessionBean.getLOGINASROLEID(),"R");
	ArrayList<TrnFileIntMarking> inboxList2 = (new TrnFileManagementDAO()).getIntMarkingData(sessionBean.getLOGINASROLEID(),"A");
	
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
	
$().ready(function() { 
	    $("#sorttable2").tablesorter(); 
	    
	});
	
$().ready(function() {
    	$("#sorttable2").tableFilter();
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
 // alert(SUBJECTCODE);
  	 	
 //Check for atleast One field for search
  	if(    getBlankValue(FILENOID)=='' && SUBJECTCODE=='' && getBlankValue(SUBJECTID)=='' && MARKINGFROM==''
  		   && MARKINGTO=='' && getBlankValue(REMARKSID)=='' && getBlankValue(MARKINGDATEFROMID)=='' && getBlankValue(MARKINGDATETOID)==''
  	  ){
  	  	//nothing to search ...simply return 
  	  	window.document.getElementById("searchResult").style.display='none';
  		return false;
  	}
  	
	TrnFileManagementDAO.getSearchFileIntInboxOutBox(FILENO,SUBJECTCODE, SUBJECT, MARKINGFROM, MARKINGTO,REMARKS,MARKINGDATEFROM,MARKINGDATETO,'<%=sessionBean.getLOGINASROLEID()%>', getData);  	
  }


function getData(data){
	window.document.getElementById("searchResult").style.display='block';
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" width="10px" id="sth00"><b>SNo.</b></th><th align="center" ><b>File No.</b></th><th align="center" style="display:none" id="sth1"><b>Marked on</b></th><th align="center" style="display:none" id="sth2"><b>Received from</b></th><th align="center" style="display:none" id="sth3"><b>Sub.</b></th> <th align="center"  nowrap="nowrap"><b>Subject</b></th><th align="center" style="display:none" id="sth4"><b>Acceptance Date</b></th><th align="center" style="display:none" id="sth5"><b>Mark To</b></th><th align="center" style="display:none" id="sth6"><b>Marking Remarks</b></th><th align="center" style="display:none" id="sth7"><b>Return Date</b></th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	for(var i=0; i < data.length; i++){
	var trclass = (i%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick=""><td align="center" id="std00"> '+(i+1)+' </td><td>'+data[i].field2+'</td><td style="display:none" id="std1"> '+data[i].field7+' </td><td style="display:none" id="std2"> '+data[i].field5+' </td><td style="display:none" id="std3"> '+data[i].field4+' </td><td>'+data[i].field3+'</td><td style="display:none" id="std4"> '+data[i].field10+' </td><td style="display:none" id="std5"> '+data[i].field6+' </td><td style="display:none" id="std6"> '+data[i].field8+' </td><td style="display:none" id="std7"> '+data[i].field11+' </td></tr>';
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
		for(var i=0;i<idno-2;i++){
		
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
			var y = window.document.getElementById("sorttable");
			//var trtag = y.getElementsByTagName("TR");
			//trtag[1].style.display = "none";
			y.deleteRow(1);
			for(i=0;i<idno-2;i++){
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
	TrnFileManagementDAO.setFileInboxAction(fmId, INACTION,'<%=sessionBean.getTENUREID()%>','<%=sessionBean.getLOGINASROLEID()%>','<%=sessionBean.getLOGINASROLENAME()%>', getReturnData);
}  


function getReturnData(data){
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

  $(document).ready(function(){
    $("#MainTabDiv").tabs({ fx: { slide: true, fade: true, speed: 'normal' }  });
    $( "#tabs" ).tabs();
      });
 </script>

</head>
<body>
<form name="frm" id="frm" action="${pageContext.request.contextPath}/TrnReferenceController" method="post">
<table width="100%" align="left" style="vertical-align: top;" height="100%">
<tr>  
      	<td valign="top"> 
      		<font size="3" > 
      			<b><i>File-Forward</i>  - Internal Marking</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    

          
        </td>
 	</tr>
	<tr>
		<td><div>
		<table width="100%" align="Center"  style="vertical-align: middle;" height="100%">
 		<tr>
		<td width="*" valign="top" align="left" valign="top" height="100%">
		<fieldset  id="Search" style="height: 100%">
		<table border="0">
		<tr>
 			<td>
 			<table  border="0" >
			<tr>
				<td colspan="2"><img border="0" src="images/Search1.png"
								width="91" height="35"></td></tr>
				
			
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
				<td align="left" valign="bottom"><nobr><label for="MARKINGDATEFROM">Marking Date From</label>
				<input type="text" name="MARKINGDATEFROM" id="MARKINGDATEFROM"
					style="width: 80px"  onblur="chkWorkDate(this);"><input type="button" class="calbutt" name="mrkdtfrm"
								onclick="Calender1(document.getElementById('MARKINGDATEFROM'))"></nobr></td>
				<td align="left" valign="bottom"><nobr><label for="MARKINGDATETO">Marking Date To</label>
				<input type="text" name="MARKINGDATETO" id="MARKINGDATETO"
					style="width: 80px"  onblur="chkWorkDate(this);"><input type="button" class="calbutt" name="mrkdtfrm"
								onclick="Calender2(document.getElementById('MARKINGDATETO'))"></nobr></td>
			</tr>
			<tr>
				<td align="left" valign="bottom"><select name="SUBJECTCODE"
					id="SUBJECTCODE" style="width: 100px">
					<option value="">Subject Code</option>
					<%
							for(int i=0;i<subjectList.size();i++){
							CommonBean beanCommon = (CommonBean) subjectList.get(i);
							%>
					<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>

					<%
						}%>
				</select></td>
				<td align="left" valign="bottom"><label for="SUBJECT">Subject</label> <input
					type="text" name="SUBJECT" id="SUBJECT" style="width: 100px"></td>
			</tr>
			
			<tr>
				<td align="left"><select name="MARKINGFROM" id="MARKINGFROM"
					style="width: 100px">
					<option value="">Received From</option>
					<%
							for(int i=0;i<markingFromList.size();i++){
							CommonBean beanCommon = (CommonBean) markingFromList.get(i);
							%>
					<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
					<%
						}%>
				</select></td>
				<td align="left">
					<select name="MARKINGTO" id="MARKINGTO" style="width: 100px"> 
						<option value="">Mark To</option>
						<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
							<%
						}%>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2"><label for="REMARKS">Remarks</label>
				<input type="text" name="REMARKS" id="REMARKS" style="width: 200px"></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td align="right"><input type="button" name="" value="&gt;&gt;"	onclick="getSearch(this);"></td>
			</tr>

		</table>
		</td>
					<td align="center">
						
					</td>
					</tr>
					</table>
		</fieldset>
		<div id="searchResult" style="height:200px; overflow:auto; overflow-x: hidden;"></div>
		</td>

		<td width="5px;" bgcolor="#F2F3F1" onclick="showHideTD(this)"
			title="Show search criteria" style="cursor: pointer;" valign="top"
			id="showhidecol">
		<div id="scrollingDiv"><img src="images/next.gif"></div>
		</td>

		<td id="td2" width="80%" valign="top">
		<div id="tabs" >
			    <ul>
			        <li><a href="#Pending"><span>Pending Files</span></a></li>
			        <li ><a href="#Returned"><span>Returned Files</span></a></li>
			        <li ><a href="#All"><span>All Files</span></a></li>
			        
			       
			        
			   </ul>
			   
		<div id="Pending">
		<fieldset>
		<table id="data" width="100%">
			<tbody id="Forwarded">
		

				<tr height="100%">
					<td width="50%" style="border: 1px dotted;">
					<table width="100%" class="tablesorter" id="sorttable0">
						<thead>
							<tr class="treven">
								<th>File No.</th>
								<th>Marked On</th>
								<th>Received from</th>
								<th>Subject Code</th>
								<th>Subject</th>
								<th>Remarks</th>
								<th>Acceptance Date</th>
								
							</tr>
						</thead>
						<tbody id="content">
							<%
						for(int i=0; i<inboxList.size();i++){
							TrnFileIntMarking inboxListB = inboxList.get(i);
							%>
							<tr class="odd">
								<td><%=inboxListB.getFILENO() %></td>
								<td><%=inboxListB.getMARKINGDATE() %></td>
								<td><%=inboxListB.getMARKINGFROM() %></td>
								<td><%=inboxListB.getSUBJECTCODE() %></td>
								<td><%=inboxListB.getSUBJECT() %></td>
								<td><%=inboxListB.getMARKINGREMARK() %></td>
								<td><%= inboxListB.getACTIONDATE()%></td>
								
							</tr>
							<%
							
						}
						%>
						</tbody>
					</table>
					</td>

				</tr>
			</tbody>
		</table>
		</fieldset>
		</div>
					
					
		<div id="Returned">
		<fieldset>
		<table  width="100%">
			<tbody id="Forwarded">
		

				<tr height="100%">
					<td width="50%" style="border: 1px dotted;">
					<table width="100%" class="tablesorter" id="sorttable1">
						<thead>
							<tr class="treven">
								<th>File No.</th>
								<th>Marked On</th>
								<th>Received from</th>
								<th>Subject Code</th>
								<th>Subject</th>
								<th>Remarks</th>
								<th>Return Date</th>
								
							</tr>
						</thead>
						<tbody id="content">
							<%
						for(int i=0; i<inboxList1.size();i++){
							TrnFileIntMarking inboxListB1 = inboxList1.get(i);
							%>
							<tr class="odd">
								<td><%=inboxListB1.getFILENO() %></td>
								<td><%=inboxListB1.getMARKINGDATE() %></td>
								<td><%=inboxListB1.getMARKINGFROM() %></td>
								<td><%=inboxListB1.getSUBJECTCODE() %></td>
								<td><%=inboxListB1.getSUBJECT() %></td>
								<td><%=inboxListB1.getMARKINGREMARK() %></td>
								<td><%= inboxListB1.getCHANGEDATE()%></td>
								
							</tr>
							<%
							
						}
						%>
						</tbody>
					</table>
					</td>

				</tr>
			</tbody>
		</table>
		</fieldset>
		</div>
		
		
		<div id="All">
		<fieldset>
		<table id="data" width="100%">
			<tbody id="Forwarded">
		

				<tr height="100%">
					<td width="50%" style="border: 1px dotted;">
					<table width="100%" class="tablesorter" id="sorttable2">
						<thead>
							<tr class="treven">
								<th>File No.</th>
								<th>Marked On</th>
								<th>Received from</th>
								<th>Subject Code</th>
								<th>Subject</th>
								<th>Remarks</th>
								<th>Acceptance Date</th>
								<th>Return Date</th>
								
							</tr>
						</thead>
						<tbody id="content">
							<%
						for(int i=0; i<inboxList2.size();i++){
							TrnFileIntMarking inboxListB2 = inboxList2.get(i);
							%>
							<tr class="odd">
								<td><%=inboxListB2.getFILENO() %></td>
								<td><%=inboxListB2.getMARKINGDATE() %></td>
								<td><%=inboxListB2.getMARKINGFROM() %></td>
								<td><%=inboxListB2.getSUBJECTCODE() %></td>
								<td><%=inboxListB2.getSUBJECT() %></td>
								<td><%=inboxListB2.getMARKINGREMARK() %></td>
								<td><%= inboxListB2.getACTIONDATE()%></td>
								<td><%= inboxListB2.getCHANGEDATE()%></td>
								
							</tr>
							<%
							
						}
						%>
						</tbody>
					</table>
					</td>

				</tr>
			</tbody>
		</table>
		</fieldset>
		</div>
		</div>
		
		</td>
		</tr>
		</tbody>
		</table>
		</div>
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
</form>
</body>
</html>
