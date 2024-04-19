<!DOCTYPE html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnFileHdr"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.MstClass"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnFileMarking"%>
<%@page import="in.org.cris.mrsectt.dao.TrnFileManagementDAO"%>

<%@page import="in.org.cris.mrsectt.Beans.TrnFileRef"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnFileIntMarking"%>


<%@page import="in.org.cris.mrsectt.Beans.TrnFileReply"%><html>
<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="/MRSECTT/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->

<script type="text/javascript" src="/MRSECTT/script/validateinput.js"></script>
<script type="text/javascript" src="/MRSECTT/script/dtree.js"></script>
<script type="text/javascript" src="/MRSECTT/script/scripts.js"></script>
<script type="text/javascript" src="/MRSECTT/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="/MRSECTT/script/jquery.textareaCounter.plugin.js"></script>
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/CLEditor1_3_0/jquery.cleditor.css"
	type="text/css">
<script src="${pageContext.request.contextPath}/CLEditor1_3_0/jquery-1.8.0.js"></script> -->
<script type="text/javascript" src="/MRSECTT/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="/MRSECTT/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="/MRSECTT/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="/MRSECTT/theme/jquery/jquery.tablefilter.js"></script>
<SCRIPT type="text/javascript" src="/MRSECTT/theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<!-- <script src="${pageContext.request.contextPath}/CLEditor1_3_0/jquery.cleditor.js"></script> -->



<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/TrnFileManagementDAO.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/CommonDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" /> -->
<!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/jquery/themes<%=theme%>/base/ui.all.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="/MRSECTT/theme/jquery/themes/base/ui.all.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />  -->
<LINK  href="/MRSECTT/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 

<style>
.dtree {
	font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
	font-size: 10px;
	color: #000;
	white-space: nowrap;
}

.dtree img {
	border: 0px;
	vertical-align: middle;
}

.dtree a {
	color: #000;
	text-decoration: none;
}

.dtree a.node,.dtree a.nodeSel {
	white-space: nowrap;
	padding: 1px 2px 1px 2px;
}

.dtree a.node:hover,.dtree a.nodeSel:hover {
	color: #666;
	text-decoration: underline;
}

.dtree a.nodeSel {
	background-color: #c0d2ec;
}

.dtree .clip {
	overflow: hidden;
}


#frmOriginated .inactive{
		color:#999;
		}	 
	#frmOriginated .active{
		color:#000;
		}	 		
	#frmOriginated .focused{
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
  	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();
  	
  	TrnFileHdr fileBean =  (request.getAttribute("fileBean")!=null) ?(TrnFileHdr)request.getAttribute("fileBean"): new TrnFileHdr();
  //	TrnFileReply fileReply =  (request.getAttribute("fileReply")!=null) ?(TrnFileReply)request.getAttribute("fileReply"): new TrnFileReply();
  	ArrayList<TrnFileMarking> inboxList = (new TrnFileManagementDAO()).getInboxData(sessionBean.getLOGINASROLEID());
  	
  	if(fileBean.getREGISTRATIONDATEORG().length()==0){
  		fileBean.setREGISTRATIONDATEORG(CommonDAO.getSysDate("dd/MM/yyyy"));
  	}
  	
  	if(fileBean.getFILESTATUS1().length()==0){
  		fileBean.setFILESTATUS1("1");
  	}
  	
  	
  	
  	if(fileBean.getFILESTATUS2().length()==0){
  		fileBean.setFILESTATUS2("3");
  	}
  	
  	if(fileBean.getREFERENCETYPE().length()==0){
  		if(sessionBean.getISCONF().equalsIgnoreCase("1")){
  			fileBean.setREFERENCETYPE("C");
  		}else{
  			fileBean.setREFERENCETYPE("D");
  		}
  	}
  	
  	if(fileBean.getREGISTRATIONDATEDES().length()==0){
  		fileBean.setREGISTRATIONDATEDES(CommonDAO.getSysDate("dd/MM/yyyy"));
  	}
  
  	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
 /* 	
  	//code for showing alteast one row in the PUC detail table
  	if(fileBean.getRefBeanList().size()==0){
          TrnFileRef iMark =  new TrnFileRef();
		//iMark.setSUBJECT(serverDate);
          ArrayList<TrnFileRef> arrIMark =  new ArrayList<TrnFileRef>();
          arrIMark.add(iMark);
          fileBean.setRefBeanList(arrIMark);
    }
    */ 
  	//code for showing alteast one row in the imarking detail table
  	if(fileBean.getIntmarkingBeanList().size()==0){
          TrnFileIntMarking iMark =  new TrnFileIntMarking();
          iMark.setMARKINGDATE(serverDate);
          ArrayList<TrnFileIntMarking> arrIMark =  new ArrayList<TrnFileIntMarking>();
          arrIMark.add(iMark);
          fileBean.setIntmarkingBeanList(arrIMark);
      }
      
  	//code for showing alteast one row in the marking detail table
      if(fileBean.getMarkingBeanUList().size()==0){
          TrnFileMarking uMark =  new TrnFileMarking();
          uMark.setMARKINGDATE(serverDate);
          ArrayList<TrnFileMarking> arrUMark =  new ArrayList<TrnFileMarking>();
          arrUMark.add(uMark);
          fileBean.setMarkingBeanUList(arrUMark);
      }//////
      
      //code for showing alteast one row in the budget detail table
      if(fileBean.getMarkingBeanDList().size()==0){
          TrnFileMarking dMark =  new TrnFileMarking();
          //dMark.setMARKINGDATE(serverDate);
          ArrayList<TrnFileMarking> arrDMark =  new ArrayList<TrnFileMarking>();
          arrDMark.add(dMark);
          fileBean.setMarkingBeanDList(arrDMark);
      }//////
      
    
    
  	
  	
	

 	String querySubjectr = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT WHERE SUBJECTTYPE='R'"; 
	ArrayList<CommonBean> subjectListr = (new CommonDAO()).getSQLResult(querySubjectr, 3);
	
	String querySubjectf = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT WHERE SUBJECTTYPE='F'"; 
	ArrayList<CommonBean> subjectListf = (new CommonDAO()).getSQLResult(querySubjectf, 3);

 	/*String queryMarkingTo = " SELECT A.ROLEID, A.ROLENAME "+ 
 							" FROM MSTROLE A,MSTTENURE B WHERE B.ISACTIVE = 'Y' AND A.ROLEID <> '"+sessionBean.getLOGINASROLEID()+"' "+
 							" AND A.ROLEID=B.ROLEID ORDER BY A.ROLENAME";*/ 
 	String queryFMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' "+
 							" UNION SELECT '"+sessionBean.getLOGINASROLEID()+"','"+sessionBean.getLOGINASROLENAME()+"' FROM DUAL ORDER BY 2"; 
	ArrayList<CommonBean> fmarkingToList = (new CommonDAO()).getSQLResult(queryFMarkingTo, 2);						
 	
 	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	String queryIMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='5' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> imarkingToList = (new CommonDAO()).getSQLResult(queryIMarkingTo, 2);
	
	String queryBranch = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='6' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> queryBranchList = (new CommonDAO()).getSQLResult(queryBranch, 2);			

 	//String querySignedBy = " SELECT B.ROLEID, (SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.ROLEID ) ROLENAME "+ 
 	//						" FROM MSTTENURE B WHERE B.ISACTIVE = 'Y' ORDER BY ROLENAME"; 
 	String querySignedBy = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);
	
	String queryfileStatus1 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'0' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus1 = (new CommonDAO()).getSQLResult(queryfileStatus1, 3);
	
	String queryfileStatus2 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'0' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus2 = (new CommonDAO()).getSQLResult(queryfileStatus2, 3);
	
	String queryreplyCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00'"; 
	ArrayList<CommonBean> replyCode = (new CommonDAO()).getSQLResult(queryreplyCode, 3);
	
  	ArrayList<CommonBean> arrTreeD = new ArrayList<CommonBean>();
  	arrTreeD = (new TrnFileManagementDAO()).getTreeviewD(fileBean.getFMID());
  	
  	ArrayList<CommonBean> arrTreeU = new ArrayList<CommonBean>();
  	arrTreeU = (new TrnFileManagementDAO()).getTreeviewU(fileBean.getFMID());
  	
  	ArrayList<CommonBean> arrTreeInt = new ArrayList<CommonBean>();
  	arrTreeInt = (new TrnFileManagementDAO()).getTreeviewInt(fileBean.getFMID());
  	
  	
  	
  	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
  	String regNo = msg.length()>0? msg.substring(29): "";
  
  %>
  
  
  

      
$().ready(function() { 
	    $("#sorttable0").tablesorter(); 
	    
	});
	
$().ready(function() {
    	$("#sorttable0").tableFilter();
	});
	
 $().ready(function() {     // call the tablesorter plugin     
		$("table").tablesorter({         // change the multi sort key from the default shift to alt button         
			sortMultiSortKey: 'altKey'     
		});
	 }); 	 
  
  $().ready(function() {
		var $scrollingDiv = $("#scrollingDiv");
 
		$(window).scroll(function(){			
			$scrollingDiv
				.stop()
				.animate({"marginTop": ($(window).scrollTop() + 0) + "px"}, "fast" );		
				
				
					
		});
	});
	$().ready(function() {
		var $scrollingDivFooter = $("#scrollingDivFooter");
 
		$(window).scroll(function(){			
			$scrollingDivFooter
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
  
  
  function containsDuplicateRow(objname)	{
	var objArray = document.getElementsByName(objname);
	var msgobjname="";
	if(objname.toUpperCase()=="DUPLICATECHECK")
		msgobjname="Record";
	else
	msgobjname=objname;	
	
	//alert(objArray.length);
	for(var i=objArray.length-1;i>=0;i--){
		objArray[i].parentNode.style.backgroundColor=normalColor;
	}
	for(var i=objArray.length-1;i>=0;i--){
		if(objArray[i].type=='text'){	
			for(var j=objArray.length-1;j>=i;j--){
				if(  (i!=j) && (objArray[i].value.length!=0) && (objArray[i].value==objArray[j].value)){
					objArray[i].parentNode.style.backgroundColor="FFE4E1";
					objArray[j].parentNode.style.backgroundColor="FFE4E1";
					alert("Duplicate value found at " + i + " and at "+j);
					//objArray[i].focus();
					return false;
				}
			}
		}

	}
	return true;
}
  
  function setRefNoPrefix(thisObj){
  	//alert(' thisObj : '+ thisObj.value);
  	var REFNO = window.document.getElementById("REFNO");
  	REFNO.value = '';
  	if(thisObj.value.length <=0){
  		return;
  	}

  	var LOGINASROLENAME = '<%= sessionBean.getLOGINASROLENAME()%>';
  	//	alert(' LOGINASROLENAME : '+ LOGINASROLENAME);
  	
  	
  	
  	REFNO.value = LOGINASROLENAME + '/' +thisObj.value + '/';
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

function getBlankValue(thisObj){
	return (thisObj.className=='active')? thisObj.value:"";
	
}


var btn;
function getSearch(thisObj){
  	btn = thisObj;
  	//alert(btn.name);
  	var FILECOUNTERFROMID =  window.document.getElementById("FILECOUNTERFROM");
  	var FILECOUNTERTOID =  window.document.getElementById("FILECOUNTERTO");
  	var REGISTRATIONDATEFROMID =  window.document.getElementById("REGISTRATIONDATEFROM");
  	var REGISTRATIONDATETOID =  window.document.getElementById("REGISTRATIONDATETO");
  	var FILENOSEARCHID =  window.document.getElementById("FILENOSEARCH");
  	var SUBJECTSEARCHID =  window.document.getElementById("SUBJECTSEARCH");
  	var COMMONSEARCHID =  window.document.getElementById("COMMONSEARCH");
  	var COMMONSEARCHVALUEID =  window.document.getElementById("COMMONSEARCHVALUE");

	var FILECOUNTERFROM = FILECOUNTERFROMID.className=='active'?FILECOUNTERFROMID.value:"";
	var FILECOUNTERTO = FILECOUNTERTOID.className=='active'?FILECOUNTERTOID.value:"";
	var REGISTRATIONDATEFROM = REGISTRATIONDATEFROMID.className=='active'?REGISTRATIONDATEFROMID.value:"";
	var REGISTRATIONDATETO = REGISTRATIONDATETOID.className=='active'?REGISTRATIONDATETOID.value:"";
	var FILENOSEARCH = FILENOSEARCHID.className=='active'?FILENOSEARCHID.value:"";
	var SUBJECTSEARCH = SUBJECTSEARCHID.className=='active'?SUBJECTSEARCHID.value:"";
	var COMMONSEARCH =  COMMONSEARCHID.value;
	var COMMONSEARCHVALUE = COMMONSEARCHVALUEID.className=='active'?COMMONSEARCHVALUEID.value:"";
	
	
  	
 //Check for atleast One field for search
  	if(    getBlankValue(FILECOUNTERFROMID)=='' && getBlankValue(FILECOUNTERTOID)=='' && getBlankValue(REGISTRATIONDATEFROMID)=='' && getBlankValue(REGISTRATIONDATETOID)==''
  		   && getBlankValue(FILENOSEARCHID)=='' && getBlankValue(SUBJECTSEARCHID)=='' && getBlankValue(COMMONSEARCHID)=='' && getBlankValue(COMMONSEARCHVALUE)==''
  	  ){
  	  	//nothing to search ...simply return 
  		return false;
  	}
  	
	TrnFileManagementDAO.getSearchData(FILECOUNTERFROM, FILECOUNTERTO, REGISTRATIONDATEFROM, REGISTRATIONDATETO,FILENOSEARCH, SUBJECTSEARCH, COMMONSEARCH,'<%=sessionBean.getLOGINASROLEID()%>','<%=sessionBean.getISCONF()%>',  getData);  	
	
  }
  
  function getData(data){
	
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	//alert(data.length);
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += "<thead><tr><th align='center' id='sth00'><b>SNo.</b></th><th align='center' id='sth0'><b>Reg No.</b></th><th align='center'  nowrap='nowrap' id='sth1'><b>Reg. Date</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth2'><b>File No.</b></th><th align='center'  nowrap='nowrap' style='display:none' id='sth3'><b>Sub.</b></th> <th align='center'  nowrap='nowrap' id='sth31' style='display:none'><b>Subject</b><th align='center' nowrap='nowrap' style='display:none' id='sth4'><b>Marked To</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth5'><b>Status</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth6'><b>Final Status</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth7'><b>Directed To</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth8'><b>Date</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth9'>&nbsp;</th></tr></thead>";
	htmlText+="<TBODY id='content'>";
	//alert(searchResult.innerHTML);
	for(var loop=0; loop < data.length; loop++){
	//alert('data ['+loop+'] is : '+ data[loop]);
	//alert(' ['+loop+'] is : '+ data[loop].field1 + '------- '+data[loop].field2);
	
	var trclass = (loop%2==0)? "trodd":"trodd";
	var refClass = data[loop].REFERENCETYPE == 'C'? '<font color="#FF0000">'+data[loop].REGISTRATIONNO+'*</font>': data[loop].REGISTRATIONNO;
		htmlText += '<tr style="cursor: pointer;" onclick="maximizeResult('+data[loop].FMID+')"><td align="center" id="std00" style="border-bottom: dotted; border-width: 1px; border-color: gray;"> '+(loop+1)+' </td> <td align="left" id="std0" style="border-bottom: dotted; border-width: 1px; border-color: gray;">'+refClass+' </td><td id="std1" style="border-bottom: dotted; border-width: 1px; border-color: gray;"> '+data[loop].REGISTRATIONDATE+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" id="std2"> '+data[loop].FILENO+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" id="std3"> '+data[loop].FILESUBJECTCODE+' </td><td id="std31" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" > '+data[loop].SUBJECT+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" id="std4"> '+data[loop].IMARKINGTO+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" id="std5"> '+data[loop].FILESTATUS1+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" id="std6"> '+data[loop].FILESTATUS2+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" id="std7"> '+data[loop].DMARKINGTO+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" id="std8"> '+data[loop].DMARKINGDATE+' </td> <td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" id="std9"> &nbsp; </td></tr>';
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
	//alert(btn);
	if(data.length==1&&btn.name=='sbtn'){
	maximizeResult(data[0].FMID);
	}
}


function maximizeResult(FMID){
	//alert('REFID 11'+REFID);
	//thisObj.style.bgcolor = 'red';
	
		window.document.getElementById("FMID").value = FMID;
	
		setSearch();
		
		submitForm('GO');
	
}  
  
function ShowSearch(obj){
//alert(document.getElementById("sorttable"));
if(document.getElementById("sorttable")!=null){
if(document.getElementById("content")!=null){
		var idno = document.getElementById("content").getElementsByTagName('tr').length;
		//alert(idno);
		if(obj.style.display=='none'){
			document.getElementById("sth1").style.display='inline';
			document.getElementById("sth2").style.display='inline';
			document.getElementById("sth3").style.display='inline';
			document.getElementById("sth31").style.display='inline';
			document.getElementById("sth31").style.width='200px';
			document.getElementById("sth4").style.display='inline';
			document.getElementById("sth5").style.display='inline';
			document.getElementById("sth6").style.display='inline';
			document.getElementById("sth7").style.display='inline';
			document.getElementById("sth8").style.display='inline';
			document.getElementById("sth9").style.display='inline';
			$("#sorttable").tableFilter();
		for(var i=0;i<idno;i++){
			document.getElementsByName("std1")[i].style.display='inline';
			document.getElementsByName("std2")[i].style.display='inline';
			document.getElementsByName("std3")[i].style.display='inline';
			document.getElementsByName("std31")[i].style.display='inline';
			document.getElementsByName("std31")[i].style.width='200px';
			document.getElementsByName("std4")[i].style.display='inline';
			document.getElementsByName("std5")[i].style.display='inline';
			document.getElementsByName("std6")[i].style.display='inline';
			document.getElementsByName("std7")[i].style.display='inline';
			document.getElementsByName("std8")[i].style.display='inline';
			document.getElementsByName("std9")[i].style.display='inline';
		}
	}else{
		//	document.getElementById("sth1").style.display='none';
			document.getElementById("sth2").style.display='none';
			document.getElementById("sth3").style.display='none';
			document.getElementById("sth31").style.display='none';
			document.getElementById("sth31").style.width='200px';
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
	//		document.getElementsByName("std1")[i].style.display='none';
			document.getElementsByName("std2")[i].style.display='none';
			document.getElementsByName("std3")[i].style.display='none';
			document.getElementsByName("std31")[i].style.display='none';
			document.getElementsByName("std31")[i].style.width='200px';
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
}  
  
function setSearch(){

		var FILECOUNTERFROMID =  window.document.getElementById("FILECOUNTERFROM");
	  	var FILECOUNTERTOID =  window.document.getElementById("FILECOUNTERTO");
	  	var REGISTRATIONDATEFROMID =  window.document.getElementById("REGISTRATIONDATEFROM");
	  	var REGISTRATIONDATETOID =  window.document.getElementById("REGISTRATIONDATETO");
	  	var FILENOSEARCHID =  window.document.getElementById("FILENOSEARCH");
	  	var SUBJECTSEARCHID =  window.document.getElementById("SUBJECTSEARCH");
	  	var COMMONSEARCHID =  window.document.getElementById("COMMONSEARCH");
	
		window.document.getElementById("FILECOUNTERFROM").value = FILECOUNTERFROMID.className=='active'?FILECOUNTERFROMID.value:"";
		window.document.getElementById("FILECOUNTERTO").value = FILECOUNTERTOID.className=='active'?FILECOUNTERTOID.value:"";
		window.document.getElementById("REGISTRATIONDATEFROM").value = REGISTRATIONDATEFROMID.className=='active'?REGISTRATIONDATEFROMID.value:"";
		window.document.getElementById("REGISTRATIONDATETO").value = REGISTRATIONDATETOID.className=='active'?REGISTRATIONDATETOID.value:"";
		window.document.getElementById("FILENOSEARCH").value = FILENOSEARCHID.className=='active'?FILENOSEARCHID.value:"";
		window.document.getElementById("SUBJECTSEARCH").value = SUBJECTSEARCHID.className=='active'?SUBJECTSEARCHID.value:"";
		window.document.getElementById("COMMONSEARCH").value =  COMMONSEARCHID.value;

}  


function getReturnData(data){
	//alert(data);
	window.location.reload();


}



function deleteReference(obj,refIdObj)
{
  var idx = getIndex(obj);
  //alert(idx);
  if(confirm('Are you sure to Delete?')){
  var fmid = window.document.getElementById("FMID").value;
  var refid = refIdObj;
  //alert(refid);
  TrnFileManagementDAO.delReference(fmid,refid, function (data){
  		if(data.split("~")[0]==1)
  		{
  			document.getElementById("baserow").deleteRow(idx+1); 
  			alert(data.split("~")[1]);
  		} else {
  			alert(data.split("~")[1]);
  		}
  } 
  );
  }
}	


function ValidateReference(obj,refIdObj)
{
  var idx = getIndex(obj);
  //alert(idx);
 
  var refno = obj.value;
  var refid = window.document.getElementsByName("REFID")[idx].value;
  //alert(refid);
  if(refid.length>0){
  TrnFileManagementDAO.RefNoValidate(refno,refid, function (data){
  		if(data==1)
  		{
  			return;
  		} else {
  			alert('Please select valid Reference from the list');
  			obj.value='';
  			obj.focus();
  			window.document.getElementsByName("REFID")[idx].value='';
  		}
  } 
  );
  }
}	

function getOutReturnData(data){
	//alert(data);
	window.location.reload();


}
  
  $(document).ready(function(){
    $("#MainTabDiv").tabs({ fx: { slide: true, fade: true, speed: 'normal' }  });
    $( "#tabs" ).tabs();
    var desmarking = window.document.frmOriginated.DESTINATIONMARKING.value;
    //alert(desmarking);
    
  	 label2value();
  	if('<%=sBean.getField1()%>'!=''){
    window.document.getElementById("FILECOUNTERFROM").value = '<%=sBean.getField1()%>';
    window.document.getElementById("FILECOUNTERFROM").className = 'active';
    }
    if('<%=sBean.getField2()%>'!=''){
	window.document.getElementById("FILECOUNTERTO").value = '<%=sBean.getField2()%>';
	window.document.getElementById("FILECOUNTERTO").className = 'active';
	}
	if('<%=sBean.getField3()%>'!=''){
	window.document.getElementById("REGISTRATIONDATEFROM").value = '<%=sBean.getField3()%>';
	window.document.getElementById("REGISTRATIONDATEFROM").className = 'active';
	}
	if('<%=sBean.getField4()%>'!=''){
	window.document.getElementById("REGISTRATIONDATETO").value = '<%=sBean.getField4()%>';
	window.document.getElementById("REGISTRATIONDATETO").className = 'active';
	}
	if('<%=sBean.getField5()%>'!=''){
	window.document.getElementById("FILENOSEARCH").value = '<%=sBean.getField5()%>';
	window.document.getElementById("FILENOSEARCH").className = 'active';
	}
	if('<%=sBean.getField6()%>'!=''){
	window.document.getElementById("SUBJECTSEARCH").value = '<%=sBean.getField6()%>';
	window.document.getElementById("SUBJECTSEARCH").className = 'active';
	}
	if('<%=sBean.getField7()%>'!=''){
	window.document.getElementById("COMMONSEARCH").value =  '<%=sBean.getField7()%>';
	window.document.getElementById("COMMONSEARCH").className = 'active';
	}
	
  });
  

function addrowref(targetobj)	{
//alert(window.document.frmOriginated.DESTINATIONMARKING.disabled);
//window.document.frmOriginated.DESTINATIONMARKING.disabled=false;
if(chkblank(window.document.frmOriginated.DESTINATIONMARKING)){
var idno = targetobj.getElementsByTagName('tr').length;
//alert('hi');
	newtr=targetobj.childNodes[0].cloneNode(true);
	newtr.style.display='block';
	newtr.className=targetobj.childNodes.length%2==0?'trodd':'trodd';
	newtr.childNodes[0].innerText=idno;
	newtr.childNodes[1].firstChild.id+=idno;
	targetobj.appendChild(newtr);
	
	//disabled(window.document.frmOriginated.DESTINATIONMARKING,true);
	
	$('#REFNO'+idno).autocomplete("getReferenceData.jsp?DESTINATIONMARKING="+window.document.frmOriginated.DESTINATIONMARKING.value,{scroll:false});
	
		$('#REFNO'+idno).result(function(event, data, formatted) {
		    if (data) {
		    //alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			//alert(' dataarr length '+ dataarr.length);
 			//alert(' dataarr 0 '+ dataarr[0]);
 			//alert(' dataarr 1 '+ dataarr[1]);
 			//alert(' dataarr 2 '+ dataarr[2]);
 			//alert(' dataarr 3 '+ dataarr[3].split("~")[0]);
			window.document.getElementsByName('REFID')[idno].value = dataarr[1];
			window.document.getElementsByName('REFERENCENAMEV')[idno].innerText = dataarr[2]+'\n'+dataarr[6];
			window.document.getElementsByName('SUBJECTCODEV')[idno].innerText = dataarr[3].split("~")[1];
			window.document.getElementsByName('SUBJECTV')[idno].innerText = dataarr[4];
			window.document.getElementsByName('STATUSV')[idno].innerText = dataarr[5];
			
			if(idno==1){
	window.document.getElementById('SUBJECTCODE').value = dataarr[3].split("~")[0];
	window.document.getElementById('FILESUBJECTCODE').value = dataarr[3].split("~")[0];
	window.document.getElementById('SUBJECT').value = dataarr[4];
	}
		}
	});
	
}
}

function setAutocomplete()
{

var idno = document.getElementById('baserow').getElementsByTagName('tr').length;
//alert(idno);
for(var k=1;parseInt(k)<parseInt(idno);k++){
$('#REFNO').autocomplete("getReferenceData.jsp?DESTINATIONMARKING="+window.document.frmOriginated.DESTINATIONMARKING.value,{scroll:false});
		$('#REFNO').result(function(event, data, formatted) {
		    if (data) {
		    //alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			//alert(' dataarr length '+ dataarr.length);
 			//alert(' dataarr 0 '+ dataarr[0]);
 			//alert(' dataarr 1 '+ dataarr[1]);
 			//alert(' dataarr 2 '+ dataarr[2]);
 			//alert(' dataarr 3 '+ dataarr[3]);
 			var i=getIndex(this);
			window.document.getElementsByName('REFID')[i].value = dataarr[1];
			window.document.getElementsByName('REFERENCENAMEV')[i].innerText = dataarr[2]+'\n'+dataarr[6];
			window.document.getElementsByName('SUBJECTCODEV')[i].innerText = dataarr[3];
			window.document.getElementsByName('SUBJECTV')[i].innerText = dataarr[4];
			window.document.getElementsByName('STATUSV')[i].innerText = dataarr[5];
			
			if(i==1){
	window.document.getElementById('SUBJECTCODE').value = dataarr[3];
	window.document.getElementById('FILESUBJECTCODE').value = dataarr[3];
	window.document.getElementById('SUBJECT').value = dataarr[4];
	}
		}
	});
}
}
 
  
  function submitForm(btnval){
	window.document.getElementById("btnClick").value = btnval;
	if(btnval=='GO'){
		window.document.frmOriginated.submit();
	}	
	
	if(btnval=='CLEAR'){
		window.document.frmOriginated.submit();
	}	
	
	if(btnval=='SAVE'){
		var flag=1;
		setSearch();
		window.document.frmOriginated.btnSAVE.disabled = true;
		window.document.frmOriginated.DESTINATIONMARKING.disabled = false;
		var IMARKINGTO = window.document.getElementsByName("IMARKINGTO");
		var IMARKINGDATE = window.document.getElementsByName("IMARKINGDATE");
		
		if( chkblank(window.document.frmOriginated.REGISTRATIONDATE) && chkblank(window.document.frmOriginated.REFERENCETYPE) &&
			chkblank(window.document.frmOriginated.FILENO) && chkblank(window.document.frmOriginated.DEPARTMENTCODE) && chkblank(window.document.frmOriginated.DESTINATIONMARKING) &&
			chkblank(window.document.frmOriginated.SUBJECT) && chkblank(window.document.frmOriginated.RECEIVEDFROM))
		{
			if(!( containsDuplicateRow("REFNO"))){
				flag=0;
				alert("Duplicate Reference No.");
				return;
			}
			
			if(document.getElementById("FILESTATUS1").value == 5 || document.getElementById("FILESTATUS1").value == 8){
				if(document.getElementById("NOL").value > 0) {
				}else {
					alert('No of letter can not be zero');
					window.document.frmOriginated.NOL.focus();
					return 0;
				}
			}
			
			for (var loop=1; loop<IMARKINGTO.length; loop++){
				if(chkblank(IMARKINGTO[loop]) && chkblank(IMARKINGDATE[loop])){
					flag=1;
				}else{
					flag=0;
				}
			}
			if(flag==1){
					window.document.getElementById("frmOriginated").submit();
			}
			window.document.frmOriginated.btnSAVE.disabled = false;
		}
	}
}

function setTargetDate(oldDate, thisObj){
  
 // alert('oldDate '+oldDate);
 var index =  getIndex(thisObj); 
 //alert('index '+index);
 // alert('noOfDaysToAdd '+thisObj.value);
  
  var newDate  = addDaysToDate(oldDate, thisObj.value);
  window.document.frmOriginated.TARGETDATE[index].value = newDate;
  
  }
  
  
  function showDiv(){
	<%
		if (msg.length() > 0 )
		{%>
	//	alert("Hiii");
		window.document.getElementById('updateDiv').style.display="block";
		window.document.getElementById('updateDivInner').style.display="block";
		window.document.getElementById('msgok').focus();
	<%	}%>
	
}

function hideDiv(){
	window.document.getElementById('updateDiv').style.display="none";
	window.document.getElementById('updateDivInner').style.display="none";
	window.document.getElementById("FILENO").focus();
}

function callMe(){
	hideDiv();
}

function showDivResult(){
	
	//	alert("Hiii");
		window.document.getElementById('resultDiv').style.display="block";
		window.document.getElementById('resultDivInner').style.display="block";
	
}

function hideDivResult(){
	window.document.getElementById('resultDiv').style.display="none";
	window.document.getElementById('resultDivInner').style.display="none";
}

function callMe(){
	hideDiv();
}
  
 
 
 function hideUnHideDiv(thisObj){
 	
 	var directedto = window.document.getElementById("directedto");
 	//alert('directedto : '+ directedto.style.display);
 	
 	if(directedto.style.display == 'block')	{
 		directedto.style.display = 'none';
 	}else if(directedto.style.display == 'none')	{
 		directedto.style.display = 'block';
 	}
 }
 
 
function setTreeView(){

		window.document.getElementById("treediv").style.display="none";
		document.getElementById('txtData1').innerHTML="";
		document.getElementById('txtData2').innerHTML="";
		GangAnalysisDAO.getTextualData(x,fromdate,todate,textdata);
		window.document.getElementById("chartdiv").style.display="block";
		changeChart();		
		
}


function addlinkfile(){
//alert(window.document.getElementById("lfile1").style.display);
		if(window.document.getElementById("lfile1").style.display=='none'){
		window.document.getElementById("lfile1").style.display='block';
		}else if(window.document.getElementById("lfile2").style.display=='none'&&window.document.getElementById("LINKFILENO1").value!=''){
		window.document.getElementById("lfile2").style.display='block';
		}else if(window.document.getElementById("lfile3").style.display=='none'&&window.document.getElementById("LINKFILENO2").value!=''){
		window.document.getElementById("lfile3").style.display='block';
		}else if(window.document.getElementById("lfile4").style.display=='none'&&window.document.getElementById("LINKFILENO3").value!=''){
		window.document.getElementById("lfile4").style.display='block';
		}	
		
}


function addrowMarking(targetobj)	{
	var idno = targetobj.getElementsByTagName('tr').length;
	newtr=targetobj.childNodes[0].cloneNode(true);

	newtr.style.display='block';

	newtr.className=targetobj.childNodes.length%2==0?'trodd':'trodd';
	newtr.childNodes[0].innerText=idno;
	targetobj.appendChild(newtr);

}

function FuncOnload(){
		window.document.getElementById("FILENO").focus();
		if('<%=sBean.getField9()%>'=='GO'){
		getSearch('abc');
		if('<%=sBean.getField9()%>'=='GO'){
		window.document.getElementById("btnADDI").focus();
		}
		}
		window.document.getElementById("REFERENCETYPE").value='<%=fileBean.getREFERENCETYPE()%>';
		('<%=fileBean.getREFERENCETYPE()%>'=='')?"":window.document.getElementById("DESTINATIONMARKING").disabled=true;

	if('<%= fileBean.getDESTINATIONMARKING()%>' == '<%=sessionBean.getLOGINASROLEID()%>'){
 		//window.document.getElementById("tabMarking").style.display ='block';
 		//window.document.getElementById("Marking").style.display ='block';
 		window.document.getElementById("tabIntMarking").style.display ='block';
 	}else{
 		//window.document.getElementById("tabMarking").style.display ='none';
 		//window.document.getElementById("Marking").style.display ='none';
 		window.document.getElementById("tabIntMarking").style.display ='none';
	}
	//alert('<%= sessionBean.getISREPLY()%>');
	if('<%= sessionBean.getISREPLY()%>' == '1'){
 		window.document.getElementById("listfileReply").style.display ='block';
 	}else{
 		window.document.getElementById("listfileReply").style.display ='none';
 	}
	
		var linkNo1 = '<%= fileBean.getLINKFILENO1()%>';
		var linkNo2 = '<%= fileBean.getLINKFILENO2()%>';
		var linkNo3 = '<%= fileBean.getLINKFILENO3()%>';
		var linkNo4 = '<%= fileBean.getLINKFILENO4()%>';
		var NOL     = '<%= fileBean.getNOL()%>';
		if(linkNo1.length>0)
		{
			window.document.getElementById("lfile1").style.display='block';
		}
		if(linkNo2.length>0)
		{
			window.document.getElementById("lfile2").style.display='block';
		}
		if(linkNo3.length>0)
		{
			window.document.getElementById("lfile3").style.display='block';
		}
		if(linkNo4.length>0)
		{
			window.document.getElementById("lfile4").style.display='block';
		}
		if(NOL > 0 )
		{
			document.getElementById("thn").style.display='block';
			document.getElementById("tdn").style.display='block';
			document.getElementById("thNOL").style.display='block';
			document.getElementById("tdNOL").style.display='block';
		}
			
		window.document.getElementById("DESTINATIONMARKING").value='<%=fileBean.getDESTINATIONMARKING().equals("")?sessionBean.getLOGINASROLEID():fileBean.getDESTINATIONMARKING()%>';
		showTab(window.document.getElementById("DESTINATIONMARKING").value);
		setUmarking(window.document.getElementById("DESTINATIONMARKING").value);
		showDiv();
//		alert(document.getElementById('baserow').getElementsByTagName('tr').length);
		var trLength = document.getElementById('baserow').getElementsByTagName('tr').length;
		if(trLength == 1) {
			addrowref(window.document.getElementById('baserow'));
		}
}

function showTab(obj){
if(obj == '<%=sessionBean.getLOGINASROLEID()%>'){
 		//window.document.getElementById("tabMarking").style.display ='block';
 		//window.document.getElementById("Marking").style.display ='block';
 		window.document.getElementById("tabIntMarking").style.display ='block';
 	}else{
 		//window.document.getElementById("tabMarking").style.display ='none';
 		//window.document.getElementById("Marking").style.display ='none';
 		window.document.getElementById("tabIntMarking").style.display ='none';
	}

}

function Calender1(obj){
obj.className='active';
newCalendar('REGISTRATIONDATEFROM-L');
}
function Calender2(obj){
obj.className='active';
newCalendar('REGISTRATIONDATETO-L');
}

function setUmarking(obj){
if(obj=='<%=sessionBean.getLOGINASROLEID()%>'){
window.document.getElementById("ums").style.display='none';
window.document.getElementById("ump").style.display='none';
window.document.getElementById("umt").style.display='none';
window.document.getElementById("umd").style.display='none';
window.document.getElementById("umr").style.display='none';
window.document.getElementById("umsc").style.display='none';
window.document.getElementById("umpp").style.display='none';
window.document.getElementById("umtt").style.display='none';
window.document.getElementById("umdt").style.display='none';
window.document.getElementById("umrt").style.display='none';
}else{
window.document.getElementById("ums").style.display='block';
window.document.getElementById("ump").style.display='block';
window.document.getElementById("umt").style.display='block';
window.document.getElementById("umd").style.display='block';
window.document.getElementById("umr").style.display='block';
window.document.getElementById("umsc").style.display='block';
window.document.getElementById("umpp").style.display='block';
window.document.getElementById("umtt").style.display='block';
window.document.getElementById("umdt").style.display='block';
window.document.getElementById("umrt").style.display='block';
}
}



function getFileSearch(thisObj){
  	var FMID = window.document.getElementById("FMID");
  	var FILENO =  thisObj.value;
  	//alert(thisObj.value.length);
  	if(thisObj.value.length>0 && FMID.value.length==0){	
	CommonDAO.getFileNoSearch(FILENO,'<%=sessionBean.getLOGINASROLEID()%>',getFileData);
	}  	
	
  }
  
  function getFileData(data){
	
	var FileResult = window.document.getElementById("FileResult");
	var htmlText = '';
	//alert(data.length);
	
	//alert(searchResult.innerHTML);
	for(var loop=0; loop < data.length; loop++){
	//alert('data ['+loop+'] is : '+ data[loop]);
	//alert(' ['+loop+'] is : '+ data[loop].field1 + '------- '+data[loop].field2);
	
	var trclass = (loop%2==0)? "trodd":"trodd";
	htmlText = ' <table id="data" width="100%">';	
	htmlText += ' <tr class= "'+trclass+'"><tH align="center" width="100%" colspan="4"><b><font color="white">History</font></b></tH></tr>';
	htmlText += ' <tr class= "'+trclass+'"><td align="left" ><b>'+data[loop].field1+'</b></td> <td align="left"  colspan="2" rowspan="3"><b>'+data[loop].field7+'</b></td><td align="right" ><b>'+data[loop].field10+'</b></td></tr>';
	htmlText += ' <tr class= "'+trclass+'"><td align="left" ><b>'+data[loop].field5+'</b></td> <td align="right" ><b>'+data[loop].field11+'</b></td></tr>';
	htmlText += ' <tr class= "'+trclass+'"><td align="left" ><b>'+data[loop].field2+'</b></td> <td align="right" ></td></tr>';
	htmlText += ' <tr ><th align="center" colspan="4"><font color="white">'+data[loop].field8+','+data[loop].field9+'</font></th></tr>';
	htmlText += ' <tr ><td align="center" colspan="4"><INPUT name="btnSAVE1" type="button" class="butts" value="  OK  "	onclick=submitForm("CLEAR")></td></tr>';
	
	htmlText += '</table>';
	//alert(htmlText);
	
	FileResult.innerHTML = htmlText;
	showDivResult();	
	}
	
	
	
	
}

function setOraignatedAt(obj){
var y=document.getElementById("DEPARTMENTCODE").options;
var x=document.getElementById("DEPARTMENTCODE").selectedIndex;
if(obj.value!=''){
for(var i=0;i<y.length;i++){
if(y[i].text==BranchFromFileNo(obj).toUpperCase())
{
if(y.value==''){
y.value=y[i].value
}
}
}
}else{
window.document.getElementById("DEPARTMENTCODE").value='';

}
}

function BranchFromFileNo(obj){
var x= new Array();
var y= new Array();
var z="";
x=obj.value.split('/');

var m=0;
for(var i=0;i<x.length;i++){

if(isAlpha(x[i].charAt(0))){
y[m]=x[i];
m=m+1;

}
}
for(var j=0;j<y.length;j++){
z=z+y[j]+(j==y.length-1?'':'/');
}
//alert(z);
return(z);
}

function isAlpha(val)
{
// True if val is a single alphabetic character.
var re = /^([a-zA-Z])$/;
return (re.test(val));
}

var cntrow;


function setToField(thisObj, TOFIELD){
	
	var fieldTo = window.document.getElementById(TOFIELD); 
	
	fieldTo.value = thisObj.value;
	fieldTo.className = 'active';
	//window.document.getElementById("sbtn").focus();
} 
	function statusChangeNew(){
	//alert('haii');
	var TENUREID='<%=sessionBean.getTENUREID()%>';
	 TrnFileManagementDAO.getCounterStatusNew(TENUREID, getCounterStatusNewData);	
	}
	
	function getCounterStatusNewData(data1){
	var statusResult = window.document.getElementById("scrollingDivFooter");
		var htmlText = '';	
		
	htmlText  = ' <table width="80%"><tbody ><tr>';	
	htmlText += ' <td width="20%"><nobr><A href="#" style="color: green;text-decoration: underline;font-size: 11px;font-weight: bold;"	onclick="javascript:statusChangeNew()" >Refresh Counter {Last Reg. No.}</A>&nbsp;</nobr></td>';
	
	for(var loop1=0; loop1 < data1.length; loop1++){
		var width=(80/(data1.length));
		var trclass = (loop1%2==0)? '<u><font color="green">':'<font color="blue">';
		var trclass1 = (loop1%2==0)? '</u>':'';
		htmlText += ' <td    width='+width+'%><b>'+trclass+' '+data1[loop1]+' '+trclass1+' </font></b></td>';					
	}	
	htmlText += '</tr></tbody></table>';
	//alert(htmlText);
	statusResult.innerHTML = htmlText;
	}
 </script>
     <style> 
                #scrollingDivFooter{ 
                                background-color:#CFDFF5; 
                                height:30px;                                 
                                width:800px;                                
                              
                                
                } 
   </style> 
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


function confirmAction(fmId, index){

	var INACTION   = window.document.getElementsByName("INACTION")[index].value;
	var MARKINGTYPE   = window.document.getElementsByName("MARKINGTYPE")[index].value;
	TrnFileManagementDAO.setFileInboxAction(fmId, INACTION,'<%=sessionBean.getTENUREID()%>','<%=sessionBean.getLOGINASROLEID()%>','<%=sessionBean.getLOGINASROLENAME()%>',MARKINGTYPE, getReturnData);
}  


function getReturnData(data){
	window.location.reload();
	
}

$(document).ready(function ()

        {
			//var name = $("input[type=text]").attr("name"); 
			//alert(name);
            //if(name!="REFNO"){
            //$("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
            // $("input[type=text]").blur(function () {$(this).css('background', '#FFFFFF') });
            // }
             $("select").live('focus', function() {$(this).css('background', '#FFE4E1')});
             $("select").live('blur', function() {$(this).css('background', '#FFFFFF')});
            $("TEXTAREA").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
            $("TEXTAREA").blur(function () {  $(this).css('background', '#FFFFFF') });

 /*$("#REPLY").cleditor(
        
        {
        	width:        "90%", // width not including margins, borders or padding
          	height:       "300", // height not including margins, borders or padding
        	 controls:     // controls to add to the toolbar
                        "bold italic underline  | size " +
                        "style | color highlight removeformat | alignleft center alignright justify | undo redo |  cut copy paste pastetext |  source"
        }
        
        );*/


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
	
function showNOL(obj){
	var val = obj.value;
//	alert('hii ' + val);
	if(val == 5 || val == 8){
		document.getElementById("thn").style.display='block';
		document.getElementById("tdn").style.display='block';
		document.getElementById("thNOL").style.display='block';
		document.getElementById("tdNOL").style.display='block';
	}
	else{
		document.getElementById("thn").style.display='none';
		document.getElementById("tdn").style.display='none';
		document.getElementById("thNOL").style.display='none';
		document.getElementById("tdNOL").style.display='none';
	}
}

function changeStatus(showhideflag){
		if(showhideflag==1){
			document.all('displaystatus').style.display='block';
			var TENUREID='<%=sessionBean.getTENUREID()%>';
			var ROLEID='<%=sessionBean.getLOGINASROLEID()%>';
			TrnFileManagementDAO.getFileCounterStatus(TENUREID, ROLEID, getCounterStatusData);
		}else if(showhideflag==2){
			document.all('displaystatus').style.display='none';
		}
	}
function getCounterStatusData(data1){
	var statusResult = window.document.getElementById("displaystatus");
	var htmlText = '';
	htmlText  = ' <table id="data" width="200px" height="80px"><tbody >';	
//	htmlText += ' <tr><th align="center" ><b>File Counter</b></th></tr>';
    var statusBean = new Array(data1.length);
	for(var loop1=0; loop1 < data1.length; loop1++){
		statusBean[loop1]= data1[loop1]; 
		var trclass = (loop1%2==0)? "trodd":"trodd";
		htmlText += '<tr><th align="center" nowrap="nowrap"><font size="3">File Counter : '+statusBean[loop1].COUNTER+'</font></th></tr>';
	}
	htmlText += '</table>';
	statusResult.innerHTML = htmlText;
}


function ismaxlength(obj){
var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : "";
if (obj.getAttribute && obj.value.length>mlength)
obj.value=obj.value.substring(0,mlength)
}


var info;
$(document).ready(function(){


	var options2 = {
			'maxCharacterSize': 4000,
			'originalStyle': 'originalTextareaInfo',
			'warningStyle' : 'warningTextareaInfo',
			'warningNumber': 40,
			'displayFormat' : '#input/#max | #words words'
	};
	$('#REPLY').textareaCount(options2);


});
</script>
</head>
<body onload="FuncOnload();statusChangeNew();">
<form name="frmOriginated" id="frmOriginated" action="${pageContext.request.contextPath}/TrnFileManagementController" method="post">

<table width="95%" align="left" style="vertical-align: top;border: 0px;" border="0" >
<tr>  
      	<td > 
      		<font size="3" > 
      			<b><i>File-Create</i> - Add</b>
      		</font><img src="/MRSECTT/images/arrow_big.gif" width="6" height="11">    
<div id="scrollingDivFooter" style="z-index:5000; position: absolute; left:300px; top:0px;display: none" ></div> 
        </td>
 	</tr>
<tr>
	<td valign="top"><div id="MainTabDiv" style="border: 0px;"><table align="Center"  style="vertical-align: middle;border: 0px;" width="100%" border="0" cellspacing="0" cellpadding="0">
			        <tbody id="Originated">
 	<tr>
 		<td width="*" valign="top" align="left" valign="top" height="100%">		<fieldset id="Search" style="border: 0px;height: 100%" >
 			<table  border="0" >
 				<tr>
 					<td><img border="0" src="images/Search1.png"
								width="91" height="35"></td>
							<td><A href="#" style="color: red;text-decoration: underline;font-size: 11px;font-weight: bold;"
							onmouseover="javascript:changeStatus(1)"
							onmouseout="javascript:changeStatus(2)">File Counter</A><DIV id="displaystatus" style="display:none;float:left;position:absolute;background-color:transparent;Z-index:10;
							margin-left:auto;margin-top:auto;border-width: inherit ;border-style: none; ">
					</div></td>
						</tr>
 				<tr>
 					<td></td>
						<td align="right"></td>
					</tr>
 				<tr>
 					<td align="left">
 						<label for="FILECOUNTERFROM">Reg. No from</label>
 						<input type="text" name="FILECOUNTERFROM" id="FILECOUNTERFROM" size="15" maxlength="6" onkeypress="chknumeric();" onkeyup="setToField(this, 'FILECOUNTERTO');" >
 					</td>
							<td align="left"><label for="FILECOUNTERTO">Reg. No
							to</label> <input type="text" name="FILECOUNTERTO" id="FILECOUNTERTO"
								size="15" maxlength="6"></td>
						</tr>
					<tr>
					<td align="left"><nobr>
 						<label for="REGISTRATIONDATEFROM">Reg. Date from</label>
 						<input type="text" name="REGISTRATIONDATEFROM" id="REGISTRATIONDATEFROM" size="15"  onblur="chkWorkDate(this);" maxlength="10">
 					<input type="button" class="calbutt" name="regdtfrm"
								onclick="Calender1(document.getElementById('REGISTRATIONDATEFROM'))"></nobr></td>
							<td align="left"><nobr> <label
								for="REGISTRATIONDATETO">Reg. Date to</label> <input type="text"
								name="REGISTRATIONDATETO" id="REGISTRATIONDATETO" size="15"  onblur="chkWorkDate(this);" maxlength="10">
							<input type="button" class="calbutt" name="regdtto"
								onclick="Calender2(document.getElementById('REGISTRATIONDATETO'))"></nobr></td>
						</tr>
					<tr>
 					<td align="left">
 						<label for="FILENOSEARCH">File No</label>
 						<input type="text" name="FILENOSEARCH" id="FILENOSEARCH" size="15" onkeydown="changecase(this,'U')" onkeyup="changecase(this,'U')">
 					</td>
							<td align="left"><label for="SUBJECTSEARCH">Subject</label>
							<input type="text" name="SUBJECTSEARCH" id="SUBJECTSEARCH"
								size="15"></td>

						</tr>
				
					<tr>
							<td align="left"><select name="COMMONSEARCH"
								id="COMMONSEARCH" style="height: 20px;border-color:#efd3bc">
								<option value="" selected> </option>
								<option>Ref. No</option>
								<option>Received from</option>
								<option>Marked to</option>
								<option>Date From</option>
								<option>Incoming Date</option>
								<option>Letter Date</option>
							</select></td>
							<td align="left"><input type="text" name="COMMONSEARCHVALUE"
								id="COMMONSEARCHVALUE" size="15"></td>
						</tr>
					<tr>
						<td align="right"></td>
						<td align="right"><input type="button" name="sbtn"  id="sbtn" value="&gt;&gt;" onclick="getSearch(this);" ></td>
					</tr>
					
					</table>
					
					<div id="searchResult" style="height:235px; overflow:auto; overflow-x: hidden;">
					
					</div>
					
		</fieldset>			
		</td>
		
		<td width="5px;" bgcolor="#F2F3F1" onclick="showHideTD(this)" title="Show search criteria" style="cursor: pointer;" valign="top" id="showhidecol">
			<div id="scrollingDiv"> 
       			<img src="images/next.gif" ></div></td>
       		
 		<td id="td2" valign="top" width="100%">	 
 		<table><tr><td>
			<div id="tabs" style="border: 0px">
			    <ul>
			        <li><a href="#File"><span>File Entry</span></a></li>
			        
			        <li id="listfileReply"><a href="#fileReply" ><span>Reply</span></a></li>
			        <li ><a href="#UMarkingChain"><span>Upward Chainage</span></a></li>
			        <li ><a href="#Received"><span>Received</span></a></li>
			        <li id="tabIntMarking"><a href="#IntMarkingChain"><span>Internal Chainage</span></a></li>
			        <li ><a href="#DMarkingChain"><span>Downward Chainage</span></a></li>
			        
			       
			        
			    </ul>
			    <div align="right"><%= fileBean.getLOGINID().toUpperCase() %></div>
			    <div id="File" >
			    	
			    	<fieldset>
			    	<table id="data" width="1000"><tr class="trodd"><td>Regn. No.</td>
			    			<td width="193"><input type="text" name="REGISTRATIONNO" id="REGISTRATIONNO" readonly="readonly" class="readonly" value="<%= fileBean.getREGISTRATIONNO()%>">
			    				<input type="hidden" name="FILECOUNTER" id="FILECOUNTER" readonly="readonly" class="readonly" value="<%= fileBean.getFILECOUNTER()%>">
			    			</td>
							<td valign="top"><font color="green">Last Regn.No.:<%= regNo %></font></td>

							<td>Regn. Date<span style="color: red"><span
								style="color: red"><b>*</b></span></span></td>
			    			<td ><input type="text" name="REGISTRATIONDATE" id="REGISTRATIONDATE" value="<%=fileBean.getREGISTRATIONDATE().equalsIgnoreCase("")?serverDate:fileBean.getREGISTRATIONDATE()%>" onblur="chkWorkDate(this);">
							<INPUT 	type="button" class="calbutt" name="regdt" onclick="newCalendar('REGISTRATIONDATE-L');"><input type="hidden" name="FMID" id="FMID" value="<%= fileBean.getFMID()%>" readonly="readonly" class="readonly">
							
						 </td>
			    			<td>File Type<span style="color: red"><span style="color: red"><b>*</b></span></span></td>
			    			<td>
			    <% if(sessionBean.getISCONF().equalsIgnoreCase("0")){ %>
			    			<select style="width: 120px;height: 20px" name="REFERENCETYPE" id="REFERENCETYPE" tabindex="2">
								<option value="A" >Approval</option>
								<option value="D" >Draft</option>
								<option value="P" >Position</option>
							</select>
				<%}else{ %>
							<select style="width: 120px;height: 20px" name="REFERENCETYPE" id="REFERENCETYPE" tabindex="2">
								<option value="A" >Approval</option>
								<option value="D" >Draft</option>
								<option value="P" >Position</option>
								<option value="C" >Confidential</option>
							</select>
				<%} %>
							</td>
			    		</tr>
			    		<tr class="trodd">
			    			<td>File No.<span style="color: red"><b>*</b></span></td>
							<td colspan="2"><input type="text" id="FILENO" onkeydown="changecase(this,'U')" size="40" name="FILENO" tabindex="1" value="<%= fileBean.getFILENO()%>" onblur="getFileSearch(this);setOraignatedAt(this);" onkeypress="allowAlphaNumWithoutSpace('/&');changecase(this,'U')">
							<input type="button" tabindex="0" name="btnADD" id="btnADD" value=" + " onclick="addlinkfile();" class="butts1" title="Add More Link File here."></td>
							
								<td>Originated Branch<span style="color: red"><span
								style="color: red"><b>*</b></span></span></td>
								<td><select style="width: 200px;height: 20px" name="DEPARTMENTCODE" id="DEPARTMENTCODE" tabindex="3">
								<option value="" selected> </option>
								<%
									for(int k=0;k<queryBranchList.size();k++){
									CommonBean beanCommon = (CommonBean) queryBranchList.get(k);
									%>
								<option value="<%=beanCommon.getField1()%>"
									<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getDEPARTMENTCODE())?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
								}%>
							</select></td>
							<td>Final Marking<span style="color: red"><span
								style="color: red"><b>*</b></span></span></td>
								<td><select name="DESTINATIONMARKING" id="DESTINATIONMARKING"
								style="width: 120px;height: 20px;" onchange="setUmarking(this.value);showTab(this.value)" tabindex="4" disabled="disabled">
								<option value="" selected> </option>
								<%
									for(int k=0;k<fmarkingToList.size();k++){
									CommonBean beanCommon = (CommonBean) fmarkingToList.get(k);
									%>
								<option value="<%=beanCommon.getField1()%>"
									<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getDESTINATIONMARKING())?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
								}%>
							</select></td>
							
			    			
			    		</tr>
						<tr class="trodd" id="lfile1" style="display: none">
							<td>Link File No.<span style="color: red"><b></b></span></td>
							<td colspan="2"><input type="text" name="LINKFILENO1" id="LINKFILENO1"
							onkeypress="allowAlphaNumWithoutSpace('/&');changecase(this,'U')"
								size="40" value="<%= fileBean.getLINKFILENO1()%>"></td>
							<td></td>
							<td></td>
							<td></td>
								<td></td>
							
						</tr>
						<tr class="trodd" id="lfile2" style="display: none">
							<td>Link File No.<span style="color: red"><b></b></span></td>
							<td colspan="2"><input type="text" name="LINKFILENO2" id="LINKFILENO2"
								onkeypress="allowAlphaNumWithoutSpace('/&');changecase(this,'U')"
								size="40" value="<%= fileBean.getLINKFILENO2()%>"></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
							
						</tr>
						<tr class="trodd" id="lfile3" style="display: none">
							<td>Link File No.<span style="color: red"><b></b></span></td>
							<td colspan="2"><input type="text" id="LINKFILENO3"
								onkeypress="allowAlphaNumWithoutSpace('/&');changecase(this,'U')"
								size="40" name="LINKFILENO3" value="<%= fileBean.getLINKFILENO3()%>"></td>
						<td></td>
						<td></td>
						<td></td>
								<td></td>
							
						</tr>
						<tr class="trodd" id="lfile4" style="display: none">
							<td>Link File No.<span style="color: red"><b></b></span></td>
							<td colspan="2"><input type="text" id="LINKFILENO4"
								onkeypress="allowAlphaNumWithoutSpace('/&');changecase(this,'U')"
								size="40" name="LINKFILENO4" value="<%= fileBean.getLINKFILENO4()%>"></td>
						<td></td>
								<td></td>
						<td></td>
								<td></td>
							
						</tr>
						
					</table>	
			    		</fieldset>
			    	<fieldset>	
			    	<table id="data" width="100%">	
			    	
					
					<tr class="trodd">
						<td colspan="6" ><fieldset><legend>Paper Under Consideration(PUC)</legend>
							<table id="data" width="1000px;">
								<tr class="treven">
									<td width="30px;" ><nobr>S.No.</nobr></td>
									<td nowrap="nowrap" >Ref. No.</td>
									<td nowrap="nowrap" >Recd. From</td>
									<td nowrap="nowrap" >Sub.</td>
									<td nowrap="nowrap" >Subject</td>
									<td nowrap="nowrap" >Ref. Status</td>
									<td nowrap="nowrap"></td>


								</tr>
								
								<TBODY id="baserow">


								<tr class="trodd" style="display: none">
								<td align="center">&nbsp;</td>
										<td nowrap="nowrap" width="35px;"><input type="text"
											size="40" maxlength="40" tabindex="5" onblur="ValidateReference(this,document.getElementById('REFID'));"
											name="REFNO" id="REFNO" style=" background-image: url('${pageContext.request.contextPath}/images/search.png'); background-position: right;background-repeat: no-repeat; ">
										<input type="hidden" name="REFID" id="REFID"></td>
										<td nowrap="nowrap" id="REFERENCENAMEV" width="100px;">&nbsp;</td>
										<td nowrap="nowrap" id="SUBJECTCODEV" width="50px;">&nbsp;</td>
										<td id="SUBJECTV">&nbsp;</td>
										<td nowrap="nowrap" id="STATUSV">&nbsp;</td>
										<td nowrap="nowrap"></td>

									</tr>
								


									<% for(int i=0; i < fileBean.getRefBeanList().size();i++){
					TrnFileRef refBean = (TrnFileRef)fileBean.getRefBeanList().get(i);
					
						%>
									<tr class="trodd">
									<td align="center"><%=i+1%></td>
										<td nowrap="nowrap" width="35px;">
						<input type="text" class="readonly" size="40" maxlength="40" onblur="ValidateReference(this,'<%=refBean.getREFID() %>');" 
						name="REFNO" id="REFNO" value="<%=refBean.getREFNO()+" ("+refBean.getINCOMINGDATE()+")" %>" style=" background-image: url('${pageContext.request.contextPath}/images/search.png'); background-position: right;background-repeat: no-repeat; ">
										<input type="hidden" name="REFID" id="REFID" value="<%=refBean.getREFID() %>"></td>
										<td nowrap="nowrap" id="REFERENCENAMEV" width="100px;"><nobr><%=refBean.getREFERENCENAME() %><br><%=refBean.getVIPSTATUS() %>,<%=refBean.getSTATENAME() %>&nbsp;</nobr></td>
										<td nowrap="nowrap" id="SUBJECTCODEV" width="50px;"><nobr><%=refBean.getSUBJECTCODE() %>&nbsp;</nobr></td>
										<td id="SUBJECTV" ><%=refBean.getSUBJECT() %>&nbsp;</td>
										<td nowrap="nowrap" id="STATUSV"><nobr><%=refBean.getSTATUS() %>&nbsp;</nobr></td>
										<td nowrap="nowrap"><input type="button" name="btnDEL"
											id="btnDEL" onclick="deleteReference(this,'<%=refBean.getREFID() %>');"
											class="delbutt" title="Delete Reference." ></td>

									</tr>

<%
						}
					%>

								</TBODY>
								<tr class="trodd">
									<td ><input type="button" name="btnADD" tabindex="6"
										id="btnADD" value=" + "
										onclick="addrowref(window.document.getElementById('baserow'));"
										class="butts1" title="Add Multiple Reference here."></td>
									<td ></td>
									<td ></td>
									<td ></td>
									<td ></td>
									<td ></td>
									<td></td>


								</tr>
							</table>
							</fieldset>
							</td>
					</tr>
					
					
					<tr class="trodd">
						<td colspan="6" >
							<table id="data" width="50%">
								<tr class="treven">
									<td nowrap="nowrap" width="80">Subject<span style="color: red"><b>*</b></span></td>
									<td nowrap="nowrap" width="80"><nobr><b>Recd. From</b></nobr><span style="color: red"><b>*</b></span></td>
									<td nowrap="nowrap" id="ums"  width="71">Sub.</td>
									<td nowrap="nowrap" id="ump"  width="22">Proposed Path</td>
									<td nowrap="nowrap" id="umt" style="display: none" width="111"> Marked To<br>
									(Upward)</td>
									<td nowrap="nowrap" id="umd" style="display: none" > Marked On</td>
									<td nowrap="nowrap" id="umr" style="display: none" width="145">Remarks(Marking) </td>

								</tr>


								<% for(int i=0; i < fileBean.getMarkingBeanUList().size();i++){
					TrnFileMarking markingBeanU = (TrnFileMarking)fileBean.getMarkingBeanUList().get(i);
					
						%>

									<tr class="trodd">
										<td nowrap="nowrap" width="80"> <textarea rows="2"
											cols="80" name="SUBJECT" id="SUBJECT" onkeypress="allowAlphaNum('()&/*');" tabindex="6"><%= fileBean.getSUBJECT()%></textarea>
										<select  name="SUBJECTCODE" id="SUBJECTCODE" style="width: 100px; display: none">
											<option value=""> </option>
											<%
											for(int j=0;j<subjectListr.size();j++){
											CommonBean beanCommon = (CommonBean) subjectListr.get(j);
											%>
											<option value="<%=beanCommon.getField1()%>"
												<%=beanCommon.getField1().equalsIgnoreCase("")?"selected":""%>><%=beanCommon.getField2()%></option>
											<%
										}%>
										</select></td>
										<td nowrap="nowrap" ><select name="RECEIVEDFROM" id="RECEIVEDFROM"
											style="width: 200px;height: 20px"  tabindex="7"> 
											<option value=""> </option>
											<%
											for(int k=0;k<markingToList.size();k++){
											CommonBean beanCommon = (CommonBean) markingToList.get(k);
											%>
											<option value="<%=beanCommon.getField1()%>"
												<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getRECEIVEDFROM())?"selected":""%>><%=beanCommon.getField2()%></option>
											<%
										}%>
										</select></td>
										<td nowrap="nowrap" width="71"  id="umsc"><select
											name="FILESUBJECTCODE" id="FILESUBJECTCODE" style="width: 120px;height: 20px" tabindex="8" >
											<option value=""> </option>
											<%
											//System.out.println("fileBean.getFILESUBJECTCODE():::"+fileBean.getFILESUBJECTCODE());
											for(int j=0;j<subjectListf.size();j++){
											CommonBean beanCommon = (CommonBean) subjectListf.get(j);
											%>
											<option value="<%=beanCommon.getField1()%>"
												<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getFILESUBJECTCODE())?"selected":""%>><%=beanCommon.getField2()%></option>
											<%
										}%>
										</select></td>
										<td nowrap="nowrap" width="22"  id="umpp" ><input type="text"  tabindex="9"
											size="25" maxlength="30" name="PROPOSEDPATH" id="PROPOSEDPATH" value="<%= fileBean.getPROPOSEDPATH()%>"></td>
										<td nowrap="nowrap" id="umtt" style="display: none" width="111"><select name="UMARKINGTO" id="UMARKINGTO"  tabindex="10"
											style="width: 200px;height: 20px">
											<option value=""> </option>
											<%
							for(int j=0;j<markingToList.size();j++){
							CommonBean beanCommon = (CommonBean) markingToList.get(j);
							%>
											<option value="<%=beanCommon.getField1()%>"
									<%=beanCommon.getField1().equalsIgnoreCase(markingBeanU.getMARKINGTO())?"selected":""%>><%=beanCommon.getField2()%></option>
								
											<%
						}%>
										</select></td>
										<td nowrap="nowrap" id="umdt" style="display: none" ><input type="text"  tabindex="11"
											name="UMARKINGDATE" id="UMARKINGDATE" size="12" maxlength="10"
											value="<%=markingBeanU.getMARKINGDATE().equalsIgnoreCase("")?serverDate:markingBeanU.getMARKINGDATE()%>"  onblur="chkWorkDate(this);"><input type="hidden"
											name="UMARKINGSEQUENCE" id="UMARKINGSEQUENCE" size="12" maxlength="10"
											value="<%=markingBeanU.getMARKINGSEQUENCE()%>"><input
										type="button" class="calbutt" name="umrkdt"
										onclick="newCalendar('UMARKINGDATE-L');"></td>
										<td width="145" id="umrt" style="display: none" >
										<textarea rows="2" cols="20" name="UMARKINGREMARK" id="UMARKINGREMARK"  tabindex="12"><%= markingBeanU.getMARKINGREMARK()%></textarea></td>
							

									</tr>


							<%} %>
								

							</table>
							</td>
					</tr>
					
			    		
			    		</table>	
				
				</fieldset>
				

				

			    <fieldset>
				<table width="50%">
					<tr class="trodd" id="fmarking">
						<td colspan="6"><fieldset><legend><b>Internal Marking</b></legend>
						<table id="data" width="100%">
				    		<tr class="treven">
								<td nowrap="nowrap" width="30">S.No.</td>
					    		<td nowrap="nowrap" >Marked To</td>
					    		<td nowrap="nowrap" >Marked On</td>
					    		<td nowrap="nowrap" >Returned On</td>
					    		
								
							</tr>
				<TBODY id="baserow1" >
					<tr class="trodd" style="display: none">
						<td nowrap="nowrap" width="30"></td>
						<td nowrap="nowrap" width="129">
						<select name="IMARKINGTO" id="IMARKINGTO" style="width: 200px;height: 20px"  tabindex="16"> 
						<option value=""> </option>
						<%
									for(int k=0;k<imarkingToList.size();k++){
									CommonBean beanCommon = (CommonBean) imarkingToList.get(k);
						%>
						<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
						<%		}%>
					</select>
						</td>
						<td nowrap="nowrap" ><input type="hidden"
											name="IMARKINGSEQUENCE" id="IMARKINGSEQUENCE" size="12" maxlength="10"
											><input type="text"
											name="IMARKINGDATE" id="IMARKINGDATE" size="12" maxlength="10"   
											value="<%=serverDate%>"  onblur="chkWorkDate(this);"><input
											type="button" class="calbutt" name="imrkdt" 
											onclick="newCalendar('IMARKINGDATE-L',getIndex(this));" ></td>
						<td nowrap="nowrap" ><input type="text"
											name="IRETURNDATE" id="IRETURNDATE" size="12" maxlength="10"
											value=""  onblur="chkWorkDate(this);"   tabindex="16"><input type="button" class="calbutt" name="iretdt"
											onclick="newCalendar('IRETURNDATE-L',getIndex(this));" ></td>
						<td width="145" style="display: none" >
										<textarea rows="2" cols="20" name="IMARKINGREMARK" id="IMARKINGREMARK"  tabindex="11"></textarea></td>
							
						</tr>
						
					
					<% for(int i=0; i < fileBean.getIntmarkingBeanList().size();i++){
					TrnFileIntMarking intmarkingBean = (TrnFileIntMarking)fileBean.getIntmarkingBeanList().get(i);
					
						%>
						<tr class="trodd">
							<td nowrap="nowrap" width="30"><%=i+1%></td>
							
							<td nowrap="nowrap" width="129">
						<select name="IMARKINGTO" id="IMARKINGTO" style="width: 200px;height: 20px"   tabindex="14"> 
						<option value=""> </option>
						<%
									for(int k=0;k<imarkingToList.size();k++){
									CommonBean beanCommon = (CommonBean) imarkingToList.get(k);
									%>
								<option value="<%=beanCommon.getField1()%>"
									<%=beanCommon.getField1().equalsIgnoreCase(intmarkingBean.getMARKINGTO())?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
								}%>
					</select>
						</td>
							<td nowrap="nowrap" ><input type="hidden"
											name="IMARKINGSEQUENCE" id="IMARKINGSEQUENCE" size="12" maxlength="10"
											value="<%=intmarkingBean.getMARKINGSEQUENCE()%>"><input type="text"  
											name="IMARKINGDATE" id="IMARKINGDATE" size="12" maxlength="10"
											value="<%= intmarkingBean.getMARKINGDATE().equalsIgnoreCase("")?serverDate:intmarkingBean.getMARKINGDATE()%>"   onblur="chkWorkDate(this);"><input type="button" class="calbutt" name="imrkdt"
											onclick="newCalendar('IMARKINGDATE-L',getIndex(this));" ></td>
							<td nowrap="nowrap" ><input type="text"
											name="IRETURNDATE" id="IRETURNDATE" size="12" maxlength="10"  
											value="<%= intmarkingBean.getCHANGEDATE()%>"  onblur="chkWorkDate(this);"><input type="button" class="calbutt" name="iretdt"
											onclick="newCalendar('IRETURNDATE-L',getIndex(this));"></td>
							<td width="145" style="display: none" >
										<textarea rows="2" cols="20" name="IMARKINGREMARK" id="IMARKINGREMARK"  tabindex="11"></textarea></td>
							
						</tr>
						
						
					<%
						}
					%>
					
											
				
							
					
					
						
					
					
					</TBODY>
					<tr class="trodd">
						<td width="30"> <input type="button" name="btnADDI" id="btnADDI" value=" + " onclick="addrowMarking(window.document.getElementById('baserow1'));"
							class="butts1" title="Add Multiple Markings here."    tabindex="15" ></td>
						<td ></td>
						<td ></td>
						<td ></td>
						
					</tr>
				</table>
				</fieldset>
				</td>
					</tr>
						<tr class="trodd">
							<td colspan="6"></td>
						</tr>
					</table>
				</fieldset>
			    <fieldset>
				<table width="1000px;" id="data">
				<tbody>
						<tr class="treven">
							<td nowrap="nowrap" align="center">File Status 1</td><td nowrap="nowrap" align="center">File Status 2</td>
							<td id="thn"  style="display: none"><div id="thNOL" style="display: none;">No. of Letters</div></td>
								<td>Action Taken</td>
							</tr>
			    	<tr class="trodd">
							<td nowrap="nowrap" ><select name="FILESTATUS1" onchange="showNOL(this);"
								id="FILESTATUS1" style="height:20px"  tabindex="17">
								
								<%
									for(int k=0;k<fileStatus1.size();k++){
									CommonBean beanCommon = (CommonBean) fileStatus1.get(k);
									%>
								<option value="<%=beanCommon.getField1()%>"
									<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getFILESTATUS1())?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
								}%>
							</select></td><td nowrap="nowrap"><select name="FILESTATUS2"
								id="FILESTATUS2" style="height:20px"  tabindex="18">
								
								<%
									for(int k=0;k<fileStatus2.size();k++){
									CommonBean beanCommon = (CommonBean) fileStatus2.get(k);
									%>
								<option value="<%=beanCommon.getField1()%>"
									<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getFILESTATUS2())?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
								}%>
							</select></td>
							<td id="tdn" style="display: none"><div id="tdNOL" style="display: none;"><input type="text" name="NOL" id="NOL" size="2" maxlength="3" value="<%= fileBean.getNOL().length()>0? fileBean.getNOL():0 %>" tabindex="19"></div></td>
								<td><textarea rows="2" tabindex="20" cols="60" name="REMARKS"
									id="REMARKS"><%= fileBean.getREMARKS()%></textarea></td>
							</tr>
				</tbody>	
				</table>
				</fieldset>
				<fieldset>
				<table width="1000px;" id="data">
				
				<tr class="treven">
							<td nowrap="nowrap" width="115"><nobr> Marked To<br>
							(Downward)</nobr></td>
							<td nowrap="nowrap" width="121"><nobr> Marked On</nobr><br>(Downward)
							</td>
							<td nowrap="nowrap" width="328"><nobr> Remarks</nobr><br>(Downward)
							</td>
							<td nowrap="nowrap" align="center" width="98">Reply<br>
							</td>
							<td width="443">Reason 
						</td>
						</tr>
						
					<% for(int i=0; i < fileBean.getMarkingBeanDList().size();i++){
					TrnFileMarking markingBeanD = (TrnFileMarking)fileBean.getMarkingBeanDList().get(i);
					
					
						%>
			    	<tr class="trodd">
						<td nowrap="nowrap" width="115"><select name="DMARKINGTO" id="DMARKINGTO"
								style="width: 200px;height: 20px"  tabindex="20">
								<option value=""> </option>
								<%
									for(int k=0;k<markingToList.size();k++){
									CommonBean beanCommon = (CommonBean) markingToList.get(k);
									%>
								<option value="<%=beanCommon.getField1()%>"
									<%=beanCommon.getField1().equalsIgnoreCase(markingBeanD.getMARKINGTO())?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
								}%>
							</select></td>
							<td nowrap="nowrap" width="121"><nobr><input type="text"
											name="DMARKINGDATE" id="DMARKINGDATE" size="12" maxlength="10" tabindex="21" 
											value="<%=markingBeanD.getMARKINGDATE()%>"  onblur="chkWorkDate(this);">
											<input type="hidden"
											name="DMARKINGSEQUENCE" id="DMARKINGSEQUENCE" size="12" maxlength="10"
											value="<%=markingBeanD.getMARKINGSEQUENCE()%>"><input
								type="button" class="calbutt" name="dmrkdt"
								onclick="newCalendar('DMARKINGDATE-L');"></nobr></td>
							<td width="328"><textarea rows="2" cols="35"
								name="DMARKINGREMARK" id="DMARKINGREMARK" tabindex="22"><%= markingBeanD.getMARKINGREMARK()%></textarea></td>
							<td nowrap="nowrap" width="98"><select name="REPLYTYPE" id="REPLYTYPE" style="height: 20px"  tabindex="23">
								<option value=""> </option>
								<%
									for(int k=0;k<replyCode.size();k++){
									CommonBean beanCommon = (CommonBean) replyCode.get(k);
									%>
								<option value="<%=beanCommon.getField1()%>"
									<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getREPLYTYPE())?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
								}%>
							</select></td>
							<td width="443"><textarea rows="2" cols="50" name="REASON"
								id="REASON" tabindex="24"><%= fileBean.getREASON()%></textarea></td>
						</tr>
					<%} %>
						
					</table>
				</fieldset>
				
							    				
				
			    </div>
			   <div id="fileReply" >
			    <table width="1000">
 					<tr>
  						<td valign="top" >
							
			<textarea id="REPLY" name="REPLY" cols="160" rows="15"><%=fileBean.getREPLY() %></textarea>
		
				</td>
				</tr>
				</table>		
			    
			    
			    </div>
			   <div id="UMarkingChain" >
			    <table width="1000">
 					<tr>
  						<td valign="top">
							<div id="treediv" class="dtree">
								<script type="text/javascript">
									var col = new Array("#d69203","#927344","#ac5e09","#805e38","#451800","#d69203","#927344","#ac5e09","#805e38","#451800");
									
									d = new dTree('d');
									
									<%for (int kk = 0; kk < arrTreeU.size(); kk++) {
										CommonBean bn = (CommonBean) arrTreeU.get(kk);
										%>
											<%=bn.getField1()%>
										<%}
									%>
									
									document.write(d);
									d.openAll();
									
								</script>
							</div>
				</td>
				</tr>
				</table>		
			    
			    
			    </div>
			   
			   <div id="DMarkingChain">
			    <table width="1000px;">
 					<tr>
  						<td valign="top">
							<div id="treediv" class="dtree">
								<script type="text/javascript">
									var col = new Array("#d69203","#927344","#ac5e09","#805e38","#451800","#d69203","#927344","#ac5e09","#805e38","#451800");
									
									d1 = new dTree('d1');
									
									<%for (int kk = 0; kk < arrTreeD.size(); kk++) {
										CommonBean bn = (CommonBean) arrTreeD.get(kk);
										%>
											<%=bn.getField1()%>
										<%}
									%>
									
									document.write(d1);
									d1.openAll();
								</script>
							</div>
				</td>
				</tr>
				</table>		
			    
			    
			    </div>
			    
			    
			    
			    <div id="IntMarkingChain">
			    <table width="1000px;">
 					<tr>
  						<td valign="top">
							<div id="treediv" class="dtree">
								<script type="text/javascript">
									var col = new Array("#d69203","#927344","#ac5e09","#805e38","#451800","#d69203","#927344","#ac5e09","#805e38","#451800");
									
									d2 = new dTree('d2');
									<% for (int kk = 0; kk < arrTreeInt.size(); kk++) {
										CommonBean bn = (CommonBean) arrTreeInt.get(kk);
										%>
											<%=bn.getField1()%>
										<%}
									%>
									document.write(d2);
									d2.openAll();
								</script>
							</div>
				</td>
				</tr>
				</table>		
			    
			    
			    </div>
			    
			    <div id="Received">
		
				
					<fieldset><legend><b><font size="+1">Inbox</font></b></legend>
					<table width="1000px;" class="tablesorter" id="sorttable0">
						<thead>
							<tr class="treven">
								<td>File No.</td>
								<td>Marked On</td>
								<td>Recd. from</td>
								<td>Sub.</td>
								<td>Subject</td>
								<td>Remarks</td>
								<td>Action</td>
							</tr>
						</thead>
						<tbody id='content'>
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
								<td><input type="hidden" name="MARKINGTYPE" id="MARKINGTYPE" 
									value="<%= inBoxBean.getMARKINGTYPE()%>"><select name="INACTION" id="INACTION"
									style="width: 100px;height: 20px">
									<option value="" selected="selected"> </option>
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
				</div>	
				
				
</div></td></tr>
			  
<tr><td>			   
			    
			    <table width="100%" style="display: none;">
			    		<tr class="trodd">
						<td colspan="6" align="center"><input type="button" name="btnNEW" id="btnNEW" class="buttNew" style="display: none;" >  
										<input type="button" name="btnSAVE" id="btnSAVE" tabindex="25" onclick="submitForm('SAVE');" class="buttSave">
										
										<input type="button" name="btnCLEAR" onclick="submitForm('CLEAR');" class="buttClear">
										
										<input type="hidden" name="btnClick" id="btnClick">
						</td>
					</tr>
			    		</table>	
			    <table width="100%">
			    		<tr class="trodd">
						<td colspan="6" align="center">
						<input type="button" name="btnSAVE" id="btnSAVE" tabindex="25" onclick="submitForm('SAVE');" class="butts" value=" Save ">
						<input type="button" name="btnCLEAR" onclick="submitForm('CLEAR');" class="butts" value=" Clear ">
						<input type="hidden" name="btnClick" id="btnClick">
						</td>
					</tr>
			    		</table>	
		
			    
			   
			   					   
</td></tr></table></tbody></table></div></td>			    

</tr>
</table>						    
			    
			    


<!-- DO NOT DELETE BELOW THIS!!! Following will create model. It will get uncommented once jsp is processed. -->

<!--main Content Area Ends--> <!--  <%=" Model Starts -->"%>
<DIV class="transparent_class" style="z-index:2000; background-color:#000; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 150%;" id="updateDiv">
	
</DIV>
<DIV style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:20px; top: 160px; display: none; width: 100%" id="updateDivInner">
<DIV class="pageheader" style="color: blue; font-family:tahoma ; z-index:5000; background-color: #FBD6B5; width: 400px; border: groove; text-align: center; background-image:url(images/top-hd-bar-bg.gif); background-repeat: repeat; " id="divLoading">
<%	if(msg.length()>3) { %>

			<BR><img src="images/<%= msg.substring(0,3)%>.gif"/><%= msg.substring(3) %> <BR><input type="button" value=" Ok " onclick="callMe()" id="msgok">
			<BR><BR>
			
<% } %>


</DIV>
</DIV>

<DIV class="transparent_class" align="center" style="z-index:2000; background-color:#e8e8e8; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 100%;" id="resultDiv">
	
</DIV>
<DIV align="center"  style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; top: 260px; display: none; width: 100%;height: 100%;" id="resultDivInner">
<DIV align="center" style="color:black;font-family:tahoma ; background-color: white; width: 500px; text-align: center;  " id="FileResult">
			
			
</DIV>
</DIV>
<!-- Model Ends     -->


<%session.removeAttribute("msg"); %>
<%session.removeAttribute("sbean"); %>
</form>
</body>
</html>