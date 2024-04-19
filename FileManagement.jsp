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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.util.Encrypt"%>
<%@ taglib prefix="enc" uri="/WEB-INF/encrypt.tld"%>

<%@page import="in.org.cris.mrsectt.Beans.TrnFileRef"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnFileIntMarking"%>


<%@page import="in.org.cris.mrsectt.Beans.TrnFileReply"%><html>
<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<c:out value="<%=theme%>"/>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
  <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.textareaCounter.plugin.js"></script>
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/CLEditor1_3_0/jquery.cleditor.css"
	type="text/css">
<script src="${pageContext.request.contextPath}/CLEditor1_3_0/jquery-1.8.0.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<!-- <script src="${pageContext.request.contextPath}/CLEditor1_3_0/jquery.cleditor.js"></script> -->


<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/TrnReferenceDAO.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/TrnFileManagementDAO.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/CommonDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" /> -->
<!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/jquery/themes<c:out value="<%=theme%>"/>/base/ui.all.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />  -->
<LINK  href="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 

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
	
	//String querySuggestion = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '12' AND CODE<>'00' order by code ";
	//ArrayList<CommonBean> suggestion = (new CommonDAO()).getSQLResult(querySuggestion,3);

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
	
	String queryfileStatus1 = "SELECT CODE, FNAME, CODETYPE, PRIORITYNO FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'0' ORDER BY PRIORITYNO"; 
	ArrayList<CommonBean> fileStatus1 = (new CommonDAO()).getSQLResult(queryfileStatus1, 4);
	
	String queryfileStatus2 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'0' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus2 = (new CommonDAO()).getSQLResult(queryfileStatus2, 3);
	
	String queryreplyCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00'"; 
	ArrayList<CommonBean> replyCode = (new CommonDAO()).getSQLResult(queryreplyCode, 3);
	
	//new code for COmpliance
	
	String querycomplianceCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' order by code"; 
	ArrayList<CommonBean> complianceCode = (new CommonDAO()).getSQLResult(querycomplianceCode, 3);
			
	//End of new code for Compliance
	
  	ArrayList<CommonBean> arrTreeD = new ArrayList<CommonBean>();
  	arrTreeD = (new TrnFileManagementDAO()).getTreeviewD(fileBean.getFMID());
  	
  	ArrayList<CommonBean> arrTreeU = new ArrayList<CommonBean>();
  	arrTreeU = (new TrnFileManagementDAO()).getTreeviewU(fileBean.getFMID());
  	
  	ArrayList<CommonBean> arrTreeInt = new ArrayList<CommonBean>();
  	arrTreeInt = (new TrnFileManagementDAO()).getTreeviewInt(fileBean.getFMID());
  	
  	
  	ArrayList<CommonBean> filepathArr=new ArrayList();
	if(request.getAttribute("filepathArr")!=null) {
		filepathArr = (ArrayList) request.getAttribute("filepathArr");
	}
	String attachFlag = StringFormat.nullString((String)session.getAttribute("attachFlag"));
  	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
  	//System.out.println("msg "+msg);
  	String[] varfile = new String[0];
  	String regNo = ""; 
  	if(msg.length()>0){
  		String varmsg = msg.substring(29);
  		varfile  = varmsg.split("/-",0);
  		regNo = varfile[0];
  	}
  	else {
  		regNo = ""; 
  	}
  	/* String regNo = msg.length()>0? msg.substring(29): ""; */
  	
  			
  
  %>
  
  
  $(document).ready(function ()
	        {
	            $.ui.dialog.defaults.bgiframe = true;
	            $("#REGISTRATIONDATE").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true', endDate: "today"});
	            $("#REGISTRATIONDATEFROM").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true', endDate: "today" });
	            $("#REGISTRATIONDATETO").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true', endDate: "today" });
	            $("#DMARKINGDATE").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true', endDate: "today" });
	           // $("input[name='IMARKINGDATE']").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true', endDate: "today" });
	          //  $("input[name='IRETURNDATE']").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true', endDate: "today" });
	   
	            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
	            $( "#datepicker" ).datepicker({  maxDate: new Date() });
	            
	            $("#vipname").autocomplete("getVIPNameFile.jsp", {scroll:false});
	            $('#vipname').result(function(event, data, formatted) {
	        		    if (data) {
	        		   // alert(data);
	         			var dataarr = new Array();
	         			dataarr = String(data).split(",,");
	         			//alert("--------------"+dataarr.length);
	         			//alert(' dataarr 5 '+ dataarr[4]);
	        			  window.document.getElementById('vipname').value = dataarr[0];
	        			//window.document.getElementById('VIPSTATUS').value = dataarr[1];
	        			//window.document.getElementById('STATECODE').value = dataarr[2];
	        			//window.document.getElementById('VIPPARTY').value = dataarr[3]; 
	        			//new code
	        			window.document.getElementById('vipid').value = dataarr[4];
	        			//window.document.getElementById('VIPADDRESS').value = dataarr[5];
	        			//window.document.getElementById('ADDRESSENGLISH').value = dataarr[8];
	        			//window.document.getElementById('VIPSTATUSDESC').value = dataarr[6]; 
	        		}
	        	});
	        }); 

 
  
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
        
	  var isie = checkVersion();
		if(isie){
			var img = tobj.childNodes[0].childNodes[0];
		} else {
			var img =tobj.firstElementChild.firstElementChild;
			
		}
	  
		//var img = tobj.childNodes[0].childNodes[0];
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

  	var LOGINASROLENAME = '<c:out value="<%= sessionBean.getLOGINASROLENAME()%>"/>';
  	//	alert(' LOGINASROLENAME : '+ LOGINASROLENAME);
  	
  	
  	
  	REFNO.value = LOGINASROLENAME + '/' +thisObj.value + '/';
  }
  
  
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

function fileNoValidation(obj)
{
  fileformat = /^[\w &/*-]+$/i;
  if(obj.value.length >0){
  if(fileformat.test(obj.value)){
  return true;
  } else{
  alert("Remove special character other than backclash");
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
	//alert("btn clicked");
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

	//var FILECOUNTERFROM = FILECOUNTERFROMID.className=='active'?FILECOUNTERFROMID.value:"";
	var FILECOUNTERFROM = FILECOUNTERFROMID.value;
	//var FILECOUNTERTO = FILECOUNTERTOID.className=='active'?FILECOUNTERTOID.value:"";
	var FILECOUNTERTO = FILECOUNTERTOID.value;
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
  	//alert(FILECOUNTERFROM);
  	//alert(FILECOUNTERTO);
  	
	TrnFileManagementDAO.getSearchData(FILECOUNTERFROM, FILECOUNTERTO, REGISTRATIONDATEFROM, REGISTRATIONDATETO,FILENOSEARCH, SUBJECTSEARCH, COMMONSEARCH,'<c:out value="<%=sessionBean.getLOGINASROLEID()%>"/>','<c:out value="<%=sessionBean.getISCONF()%>"/>',  getData);  	
	
  }
  
  
  function getData(data){
	
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	//alert(data.length);
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)</div>';
	htmlText += "<TABLE class='tablesorter' name='sorttable' id='sorttable' width='100%'>";	
	htmlText += "<thead><tr><th align='center' id='sth00'><b>SNo.</b></th><th >&nbsp;</th><th align='center' id='sth0'><b>Reg No.</b></th><th align='center'  nowrap='nowrap' id='sth1'><b>Reg. Date</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth2'><b>File No.</b></th><th align='center'  nowrap='nowrap' style='display:none' id='sth3'><b>Sub.</b></th> <th align='center'  nowrap='nowrap' id='sth31' style='display:none'><b>Subject</b><th align='center' nowrap='nowrap' style='display:none' id='sth4'><b>Marked To</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth5'><b>Status</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth6'><b>Final Status</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth7'><b>Directed To</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth8'><b>Date</b></th><th align='center' nowrap='nowrap' style='display:none' id='sth9'>&nbsp;</th></tr></thead>";
	htmlText+="<TBODY id='content'>";
	//alert(searchResult.innerHTML);
	for(var loop=0; loop < data.length; loop++){
	//alert('data ['+loop+'] is : '+ data[loop]);
	//alert(' ['+loop+'] is : '+ data[loop].field1 + '------- '+data[loop].field2);
	var attachment = (data[loop].ISATTACHMENT == "1"? '<img border="0" alt="Image Gallery" src="${pageContext.request.contextPath}/images/stn.gif" onclick="popupGallery(\''+data[loop].FMIDENC+'\',\''+data[loop].FMIDENC+'\');">': "");
	var trclass = (loop%2==0)? "trodd":"trodd";
	var refClass = data[loop].REFERENCETYPE == 'C'? '<font color="#FF0000">'+data[loop].REGISTRATIONNO+'*</font>': data[loop].REGISTRATIONNO;
		htmlText += '<tr style="cursor: pointer;" onclick="maximizeResult('+data[loop].FMID+')"><td align="center" id="std00" name="std00" style="border-bottom: dotted; border-width: 1px; border-color: gray;"> '+(loop+1)+' </td>  <td name="std20"  id="std20" style=" border-bottom: dotted; border-width: 1px; border-color: gray;"> '+attachment+'&nbsp; </td> <td align="left" name="std0" id="std0" style="border-bottom: dotted; border-width: 1px; border-color: gray;">'+refClass+' </td><td name="std1"  id="std1" style="border-bottom: dotted; border-width: 1px; border-color: gray;"> '+data[loop].REGISTRATIONDATE+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" name="std2"  id="std2"> '+data[loop].FILENO+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" name="std3" id="std3"> '+data[loop].FILESUBJECTCODE+' </td><td name="std31" id="std31" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" > '+data[loop].SUBJECT+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" name="std4" id="std4"> '+data[loop].IMARKINGTO+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" name="std5" id="std5"> '+data[loop].FILESTATUS1+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" name="std6" id="std6"> '+data[loop].FILESTATUS2+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" name="std7" id="std7"> '+data[loop].DMARKINGTO+' </td><td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" name="std8" id="std8"> '+data[loop].DMARKINGDATE+' </td> <td style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;" name="std9"  id="std9"> &nbsp; </td></tr>';
	}
	
	
	htmlText += '</tbody></table>';
	
	//alert(htmlText);
	//console.log(htmlText);
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


function popupGallery(refId, flg)
{
//alert()
	var url="ImageGallery.jsp?refId="+refId+"&flg="+flg+"&type=F";
	window.open(url,"","toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,left=0,top=0,scrollbars=1,resizable=1");
}

function maximizeResult(FMID){
	//alert('REFID 11'+REFID);
	//thisObj.style.bgcolor = 'red';
	window.document.frmOriginated.MaximizeOnAttach.value = 0;
	    window.document.frmOriginated.MaximizeOnAttach.value = 0;
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
  //alert(refno);
  if(refid.length>0){
  TrnFileManagementDAO.RefNoValidate(refno,refid, function (data){
	  //alert("data"+data)
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
  	if('<c:out value="<%=sBean.getField1()%>"/>'!=''){
    window.document.getElementById("FILECOUNTERFROM").value = '<c:out value="<%=sBean.getField1()%>"/>';
    window.document.getElementById("FILECOUNTERFROM").className = 'active';
    }
    if('<c:out value="<%=sBean.getField2()%>"/>'!=''){
	window.document.getElementById("FILECOUNTERTO").value = '<c:out value="<%=sBean.getField2()%>"/>';
	window.document.getElementById("FILECOUNTERTO").className = 'active';
	}
	if('<c:out value="<%=sBean.getField3()%>"/>'!=''){
	window.document.getElementById("REGISTRATIONDATEFROM").value = '<c:out value="<%=sBean.getField3()%>"/>';
	window.document.getElementById("REGISTRATIONDATEFROM").className = 'active';
	}
	if('<c:out value="<%=sBean.getField4()%>"/>'!=''){
	window.document.getElementById("REGISTRATIONDATETO").value = '<c:out value="<%=sBean.getField4()%>"/>';
	window.document.getElementById("REGISTRATIONDATETO").className = 'active';
	}
	if('<c:out value="<%=sBean.getField5()%>"/>'!=''){
	window.document.getElementById("FILENOSEARCH").value = '<c:out value="<%=sBean.getField5()%>"/>';
	window.document.getElementById("FILENOSEARCH").className = 'active';
	}
	if('<c:out value="<%=sBean.getField6()%>"/>'!=''){
	window.document.getElementById("SUBJECTSEARCH").value = '<c:out value="<%=sBean.getField6()%>"/>';
	window.document.getElementById("SUBJECTSEARCH").className = 'active';
	}
	if('<c:out value="<%=sBean.getField7()%>"/>'!=''){
	window.document.getElementById("COMMONSEARCH").value =  '<c:out value="<%=sBean.getField7()%>"/>';
	window.document.getElementById("COMMONSEARCH").className = 'active';
	}
	
	
  });
  

function addrowref(targetobj)	{
//alert(window.document.frmOriginated.DESTINATIONMARKING.disabled);
//window.document.frmOriginated.DESTINATIONMARKING.disabled=false;
if(chkblank(window.document.frmOriginated.DESTINATIONMARKING)){
var idno = targetobj.getElementsByTagName('tr').length;
	//newtr=targetobj.childNodes[0].cloneNode(true);
	var isie = checkVersion();
	if(isie){
		var newtr=targetobj.childNodes[0].cloneNode(true);
		newtr.style.display='';
		newtr.className=targetobj.childNodes.length%2==0?'trodd':'trodd';
		newtr.childNodes[0].innerText=idno;
		newtr.childNodes[1].firstChild.id+=idno;
	} else {
		var newtr =targetobj.firstElementChild.cloneNode(true);
		newtr.style.display='';
		newtr.className=targetobj.childNodes.length%2==0?'trodd':'trodd';
		newtr.children[1].firstElementChild.id+=idno;
		newtr.firstElementChild.innerText=idno;
	}
	
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
 			/* alert(' dataarr 4 '+ dataarr[4]);
 			alert(' dataarr 5 '+ dataarr[5]);
 			alert(' dataarr 6 '+ dataarr[6]);
 			alert(' dataarr 7 '+ dataarr[7]);
 			alert(' dataarr 8 '+ dataarr[8]);
 			alert(' dataarr 9 '+ dataarr[9]);
 			alert(' dataarr 10 '+ dataarr[10]);
 			alert(' dataarr 11 '+ dataarr[11]);
 			alert(' dataarr 12 '+ dataarr[12]);
 			alert(' dataarr 13 '+ dataarr[13]);
 			alert(' dataarr 14 '+ dataarr[14]);
 			alert(' dataarr 15 '+ dataarr[15]); */
 			
 			//alert(' dataarr 15 '+ dataarr[15]);
 			if(dataarr[15]==='18' || dataarr[15]==='8' || dataarr[15]==='13'  || dataarr[15]==='16' || dataarr[15]==='5') alert('PUC Disposed Already');
 			if (dataarr[15]==='99') alert('Draft Reply Ready');
			window.document.getElementsByName('REFID')[idno].value = dataarr[1];
			window.document.getElementsByName('REFERENCENAMEV')[idno].innerText = dataarr[2]+'\n'+dataarr[6];
			window.document.getElementsByName('SUBJECTCODEV')[idno].innerText = dataarr[3].split("~")[1];
			window.document.getElementsByName('SUBJECTV')[idno].innerText = dataarr[4];
			window.document.getElementsByName('STATUSV')[idno].innerText = dataarr[5];
			
			if(idno==1){
	window.document.getElementById('SUBJECTCODE').value = dataarr[3].split("~")[0];
	//window.document.getElementById('FILESUBJECTCODE').value = dataarr[3].split("~")[0];
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
	//window.document.getElementById('FILESUBJECTCODE').value = dataarr[3];
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
		var cnt1 = 1;
		var flag=1;
		setSearch();
		
		if (window.document.frmOriginated.FILENO.value.length>0 && window.document.frmOriginated.FMID.value.length=="")
		{
		 fileno = window.document.frmOriginated.FILENO;
		 filevalidation = fileNoValidation(fileno);
		 if(filevalidation==false){

		    return false;
		    }
		}
		
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
			
			window.document.frmOriginated.cntimg.value = eval(document.getElementsByName("uploadImage").length);
			//alert(document.getElementsByName("uploadImage").length);
			while(cnt1<document.getElementsByName("uploadImage").length)
			{
//				document.getElementsByName("uploadImage")[cnt1].setAttribute("name",("uploadImage"+cnt1));
//				cnt1++;
				if(!checkFile(document.getElementsByName("uploadImage")[cnt1])){
					document.getElementsByName("uploadImage")[cnt1].focus();
					return;
				}else{
					document.getElementsByName("uploadImage")[cnt1].setAttribute("name",("uploadImage"+cnt1));
//					document.getElementsByName("PATH")[cnt1].setAttribute("name",("PATH"+cnt1));				
				}
				cnt1++;
			}
			
			//for New check of Compliance Status
			var objArray = document.getElementsByName("COMPLIANCECODE");
			var objArraycomp = document.getElementsByName("COMPREMARKS");
			var objArrayrefno = document.getElementsByName("REFNO");
			for(var i=objArray.length-1;i>=0;i--){
				if(objArray[i].value.length=="" && objArrayrefno[i].value.length!="")
					{
					alert("For each PUC demand, filling compliance Status is mandatory");
					return;
					}
				if(objArraycomp[i].value.length=="" && objArrayrefno[i].value.length!="")
				{
				alert("For each PUC demand, filling compliance Remarks is mandatory");
				return;
				}
				}
			
			if(document.getElementById("FILESTATUS1").value == 5 || document.getElementById("FILESTATUS1").value == 8){
				if(document.getElementById("NOL").value > 0) {
				}else {
					alert('No of letter can not be zero');
					window.document.frmOriginated.NOL.focus();
					return 0;
				}
			}
			
			/* if(document.getElementById("ISBUDGETPOINT").value == 1)
				{
				  if(document.getElementById("vipid").value==0)
				  {
					  alert('Please Select name of MP for from dropdown');
					  window.document.frmOriginated.vipname.focus();
					  return 0;
				  }
				 
				} */
			
			for (var loop=1; loop<IMARKINGTO.length; loop++){
				if(chkblank(IMARKINGTO[loop]) && chkblank(IMARKINGDATE[loop])){
					flag=1;
				}else{
					flag=0;
				}
			}
			if(flag==1){
					window.document.getElementById("frmOriginated").submit();
					window.document.frmOriginated.btnSAVE.disabled = true;
					//alert();
			}
			window.document.frmOriginated.btnSAVE.disabled = false;
		}
	}
}

  function delRefFile(obj, refID, attachmentID)	{
		var LOGINDESIGNATION = '<c:out value="<%= sessionBean.getLOGINDESIGNATION()%>"/>'; 
		var LOGINID = '<c:out value="<%= sessionBean.getLOGINID()%>"/>'; 
		var delpermission= 0;
		switch (LOGINDESIGNATION){
		case 'DD/MRCC' :
			delpermission = 1;
			break;
		case 'SO/MRCC' :
			delpermission = 1;
			break;
		case 'admin' :
			delpermission = 1;
			break;
		default:
			delpermission = 0;
		}
		
		if ((delpermission==1)||(( LOGINID== 'alok'))){
			if(confirm('Are you sure you want to delete?')){
			
			var idx = getIndex(obj);
		  TrnReferenceDAO.deleteRefImage(refID, attachmentID,'F', function (data){
		  		if(data.split("~")[0]==1) {
		  			document.getElementById("refimage").deleteRow(idx); 
		  			alert(data.split("~")[1]);
		  		} else {
		  			alert(data.split("~")[1]);
		  		}
		  		} 
	 		);
		} else {return false;}
		}
		else {
			alert("Only DD/MRCC or SO/MRCC can delete attachment");
			return false;
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
		window.document.getElementById('btnOK').focus();
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
 
function callMeAttach(){
	hideDiv();
	var x= "<c:out value='<%= msg%>'/>";
	var leng = x.length;
	var arrayString = x.split("/-")
	var FMID = arrayString[1];
	
	//maximizeResultAttach(refid)
	//MaximizeOnAttach
	
	window.document.frmOriginated.MaximizeOnAttach.value = 1;
	window.document.frmOriginated.FMID.value = FMID;

	setSearch();
	
	submitForm('GO');
	<%-- setRefClass('<c:out value="<%= rClass %>"/>'); --%>
	
	
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

function openAttachment(REFID, ATTACHMENTID, ORIGFILENAME, NEWFILENAME,EOFFICEFLAG,TYPE){
	var  prev_action = document.forms[0].action;
//	var newimgid = assetid+"_"+imgid+ext;
	//alert(ORIGFILENAME+"-"+EOFFICEFLAG);
	window.document.forms[0].action="${pageContext.request.contextPath}/AttachmentFileController?ATTACHMENTID="+ATTACHMENTID+"&REFID="+REFID+"&ORIGFILENAME="+ORIGFILENAME+"&GENFILENAME="+NEWFILENAME+"&EOFFICEFLAG="+EOFFICEFLAG+"&TYPE="+TYPE+"";
	window.document.forms[0].submit();
	window.document.forms[0].action=prev_action;		
	return true;
}

function addlinkfile(){
//alert(window.document.getElementById("lfile1").style.display);
		if(window.document.getElementById("lfile1").style.display=='none'){
		window.document.getElementById("lfile1").style.display='';
		}else if(window.document.getElementById("lfile2").style.display=='none'&&window.document.getElementById("LINKFILENO1").value!=''){
		window.document.getElementById("lfile2").style.display='';
		}else if(window.document.getElementById("lfile3").style.display=='none'&&window.document.getElementById("LINKFILENO2").value!=''){
		window.document.getElementById("lfile3").style.display='';
		}else if(window.document.getElementById("lfile4").style.display=='none'&&window.document.getElementById("LINKFILENO3").value!=''){
		window.document.getElementById("lfile4").style.display='';
		}	
		
}


function addrowMarking(targetobj)	{
	var idno = targetobj.getElementsByTagName('tr').length;
	newtr=targetobj.childNodes[0].cloneNode(true);

	newtr.style.display='block';

	newtr.className=targetobj.childNodes.length%2==0?'treven':'trodd';
	newtr.childNodes[0].innerText=idno;
	targetobj.appendChild(newtr);

}
function addrowMarkingFile()	{
	
	var tarobject =  document.getElementById('baserow1');
	var idno = tarobject.getElementsByTagName('tr').length;
	
	var isie = checkVersion();
	if(isie){
		var newtr=tarobject.childNodes[0].cloneNode(true);
		newtr.style.display='';
		newtr.className='trodd';
		newtr.childNodes[0].innerText=idno;
		
	} else {
		var newtr =tarobject.firstElementChild.cloneNode(true);
		newtr.style.display='';
		newtr.className='trodd';
		newtr.firstElementChild.innerText=idno;
	}
	
	tarobject.appendChild(newtr);

}

function addrowAttach(){
	var targetobj = document.getElementById("attachimg");
	var isie = checkVersion();
	if(isie){
		var newtr = targetobj.firstChild.cloneNode(true);
	}
	else {
		var newtr = targetobj.firstElementChild.cloneNode(true);
	}
	newtr.style.display='block';
	newtr.className=targetobj.childNodes.length%2==0?'treven':'trodd';
	targetobj.appendChild(newtr);
	
}

function getInternetExplorerVersion()
{
  var rv = -1; // Return value assumes failure.
  if (navigator.appName == 'Microsoft Internet Explorer')
  {
    var ua = navigator.userAgent;
    var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
    if (re.exec(ua) != null)
      rv = parseFloat( RegExp.$1 );
  }
  return rv;
}

function checkVersion()
{
  var ver = getInternetExplorerVersion();
  if ( ver > -1 )
  {
    return true;
  }	else{
  	return false;
  }
}
function checkFile(obj)
{
	var filename = obj.value;
	if(obj.value)
	{
		var ext = filename.substring(filename.lastIndexOf('.') + 1);
		if( ext == "jpg" || ext == "JPG" || ext == "pdf" || ext == "PDF")
		{
		   return true;
		}else
		{
			alert("File type not supported.");
			obj.value = "";
			return false;
		}
	}
}

function FuncOnload(){
		window.document.getElementById("FILENO").focus();
		if("<c:out value='<%= attachFlag%>'/>"=="1"){
			document.getElementById("MainAttachmentLink").click();
			  } 
		if('<c:out value="<%=sBean.getField9()%>"/>'=='GO'){
		getSearch('abc');
		if('<c:out value="<%=sBean.getField9()%>"/>'=='GO'){
		window.document.getElementById("btnADDI").focus();
		}
		}
		window.document.getElementById("REFERENCETYPE").value='<c:out value="<%=fileBean.getREFERENCETYPE()%>"/>';
		('<c:out value="<%=fileBean.getREFERENCETYPE()%>"/>'=='')?"":window.document.getElementById("DESTINATIONMARKING").disabled=true;

	if('<c:out value="<%= fileBean.getDESTINATIONMARKING()%>"/>' == '<c:out value="<%= sessionBean.getLOGINASROLEID()%>"/>'){
 		//window.document.getElementById("tabMarking").style.display ='block';
 		//window.document.getElementById("Marking").style.display ='block';
 		window.document.getElementById("tabIntMarking").style.display ='block';
 	}else{
 		//window.document.getElementById("tabMarking").style.display ='none';
 		//window.document.getElementById("Marking").style.display ='none';
 		window.document.getElementById("tabIntMarking").style.display ='none';
	}
	//alert('<c:out value="<%= sessionBean.getISREPLY()%>"/>');
	if('<c:out value="<%= sessionBean.getISREPLY()%>"/>' == '1'){
 		window.document.getElementById("listfileReply").style.display ='block';
 	}else{
 		window.document.getElementById("listfileReply").style.display ='none';
 	}
	
		var linkNo1 = '<c:out value="<%= fileBean.getLINKFILENO1()%>"/>';
		var linkNo2 = '<c:out value="<%= fileBean.getLINKFILENO2()%>"/>';
		var linkNo3 = '<c:out value="<%= fileBean.getLINKFILENO3()%>"/>';
		var linkNo4 = '<c:out value="<%= fileBean.getLINKFILENO4()%>"/>';
		var NOL     = '<c:out value="<%= fileBean.getNOL()%>"/>';
		var SENTTO     = '<c:out value="<%= fileBean.getSENTTO()%>"/>';
		var ISBUDGETPOINT = '<c:out value="<%= fileBean.getISBUDGETPOINT()%>"/>';
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
		if(ISBUDGETPOINT == 1)
			{
			document.getElementById("vipname").style.display='block';
			document.getElementById("persontd").style.display='block';
			}
		
		if(SENTTO !="" )
		{
			document.getElementById("thn1").style.display='block';
			document.getElementById("tdn1").style.display='block';
			document.getElementById("thSentTo").style.display='block';
			document.getElementById("tdSentTo").style.display='block';
		}
			
		window.document.getElementById("DESTINATIONMARKING").value="<c:out value='<%=fileBean.getDESTINATIONMARKING().equals("")?sessionBean.getLOGINASROLEID():fileBean.getDESTINATIONMARKING()%>'/>";
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
if(obj == '<c:out value="<%=sessionBean.getLOGINASROLEID()%>"/>'){
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
if(obj=='<c:out value="<%=sessionBean.getLOGINASROLEID()%>"/>'){
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
	CommonDAO.getFileNoSearch(FILENO,'<c:out value="<%=sessionBean.getLOGINASROLEID()%>"/>',getFileData);
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
	var TENUREID='<c:out value="<%=sessionBean.getTENUREID()%>"/>';
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


function confirmAction(fmId, index){

	var INACTION   = window.document.getElementsByName("INACTION")[index].value;
	var MARKINGTYPE   = window.document.getElementsByName("MARKINGTYPE")[index].value;
	TrnFileManagementDAO.setFileInboxAction(fmId, INACTION,'<c:out value="<%=sessionBean.getTENUREID()%>"/>','<c:out value="<%=sessionBean.getLOGINASROLEID()%>"/>','<c:out value="<%=sessionBean.getLOGINASROLENAME()%>"/>',MARKINGTYPE, getReturnData);
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
	  else if(KeyID=="13")
	  { 
		  var buttontype = window.document.getElementById("sbtn");
	 	 getSearch(buttontype);
	  }
	}

	function showComSugg(obj){
		var val = obj.value;
		alert('hii ' + val);
		if(val == 'D'){
			document.getElementById("sugr").style.display='block';
			document.getElementById("tdsugr").style.display='block';
			document.getElementById("sugcomremarks").style.display='block';
			document.getElementById("tdsugcomremarks").style.display='block';
		}
		else{
			document.getElementById("sugr").style.display='none';
			document.getElementById("tdsugr").style.display='none';
			document.getElementById("sugcomremarks").style.display='none';
			document.getElementById("tdsugcomremarks").style.display='none';
		}
	}
		
function showNOL(obj){
	var val = obj.value;
//	alert('hii ' + val);
	if(val == 5 || val == 8 || val == 16){
		document.getElementById("thn").style.display='block';
		document.getElementById("tdn").style.display='block';
		document.getElementById("thNOL").style.display='block';
		document.getElementById("tdNOL").style.display='block';
		
		document.getElementById("thn1").style.display='block';
		document.getElementById("tdn1").style.display='block';
		document.getElementById("thSentTo").style.display='block';
		document.getElementById("tdSentTo").style.display='block';
		document.getElementById("FILESTATUS2").value=7;
	} else if(val==98){
		document.getElementById("FILESTATUS2").value=7;
		document.getElementById("REMARKS").value="Position noted in MRCC";
	}
	else{
		document.getElementById("thn").style.display='none';
		document.getElementById("tdn").style.display='none';
		document.getElementById("thNOL").style.display='none';
		document.getElementById("tdNOL").style.display='none';
		
		document.getElementById("thn1").style.display='none';
		document.getElementById("tdn1").style.display='none';
		document.getElementById("thSentTo").style.display='none';
		document.getElementById("tdSentTo").style.display='none';
	}
}
function putInRemarks(){
	//var val = obj.value;
	//var cmp = document.getElementById("COMPLIANCECODE");
	//alert("suggestion"+val);
	//alert("compliance code"+cmp.value);
	//alert("compremarks"+document.getElementById("COMPREMARKS").value);
	//document.getElementById("COMPREMARKS").value = val;
	var objArray = document.getElementsByName("COMPREMARKS");
	for(var i=1;i<=objArray.length-1;i++){
		//var x = document.getElementsByName("COMPREMARKS")[i].value;
		//(x == null){
				document.getElementsByName("COMPREMARKS")[i].value = document.getElementsByName("tdsugcomremarksc")[i].value
			//}
	}
}

function showSuggestion(){
	var objArray = document.getElementsByName("COMPLIANCECODE");
	for(var i=1;i<=objArray.length-1;i++){
			var x = document.getElementsByName("COMPLIANCECODE")[i].value;
			if(x=="D"){
					document.getElementsByName("tdsugcomremarksc")[i].style.display="block";
					//document.getElementsByName("tdsugra").style.display="block";
					document.getElementsByName("tdsugra")[i].style.display="block";
					
				}
			else{
					document.getElementsByName("tdsugcomremarksc")[i].style.display="none";
					//document.getElementsByName("sugcomremarksc").style.display="none";
					document.getElementsByName("tdsugra")[i].style.display="none";
				}
		}
}

function showVIPPerson(){
	var budgetpoint= window.document.getElementById("ISBUDGETPOINT").value;
	//alert(budgetpoint);
	if (budgetpoint==1){
		window.document.getElementById("vipname").style.display="block";
		window.document.getElementById("persontd").style.display="block";
	}
	else {
		window.document.getElementById("vipname").style.display="none";
		window.document.getElementById("persontd").style.display="none";
	}
}

function changeStatus(showhideflag){
		if(showhideflag==1){
			document.all('displaystatus').style.display='block';
			var TENUREID='<c:out value="<%=sessionBean.getTENUREID()%>"/>';
			var ROLEID='<c:out value="<%=sessionBean.getLOGINASROLEID()%>"/>';
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
<form name="frmOriginated" id="frmOriginated" action="${pageContext.request.contextPath}/TrnFileManagementController" method="post" enctype="multipart/form-data">

<table width="95%" align="left" style="vertical-align: top;border: 0px;" border="0" >
<tr>  
      	<td > 
      		<font size="3" > 
      			<b><i>File-Create</i> - Add</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
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
 					</nobr></td>
							<td align="left"><nobr> <label
								for="REGISTRATIONDATETO">Reg. Date to</label> <input type="text"
								name="REGISTRATIONDATETO" id="REGISTRATIONDATETO" size="15"  onblur="chkWorkDate(this);" maxlength="10">
							</nobr></td>
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
			        <!-- <li ><a href="#Received"><span>Received</span></a></li> -->
			        <li id="tabIntMarking"><a href="#IntMarkingChain"><span>Internal Chainage</span></a></li>
			        <li ><a href="#DMarkingChain"><span>Downward Chainage</span></a></li>
			        <li><a id="MainAttachmentLink" href="#MainAttachment"><span> Attachment </span></a></li>
			        
			       
			        
			    </ul>
			    <div align="right"><c:out value="<%= fileBean.getLOGINID().toUpperCase() %>"/></div>
			    <div id="File" >
			    	
			    	<fieldset>
			    	<table id="data" width="1000"><tr class="trodd"><td>Regn. No.</td>
			    			<td width="193"><input type="text" name="REGISTRATIONNO" id="REGISTRATIONNO" readonly="readonly" class="readonly" value="<c:out value='<%= fileBean.getREGISTRATIONNO()%>'/>">
			    				<input type="hidden" name="FILECOUNTER" id="FILECOUNTER" readonly="readonly" class="readonly" value="<c:out value='<%= fileBean.getFILECOUNTER()%>'/>">
			    			</td>
							<td valign="top"><font color="green">Last Regn.No.:<c:out value='<%= regNo %>'/></font></td>

							<td>Regn. Date<span style="color: red"><span
								style="color: red"><b>*</b></span></span></td>
			    			<td ><input type="text" name="REGISTRATIONDATE" id="REGISTRATIONDATE" value="<c:out value='<%=fileBean.getREGISTRATIONDATE().equalsIgnoreCase("")?serverDate:fileBean.getREGISTRATIONDATE()%>'/>" onblur="chkWorkDate(this);">
							<input type="hidden" name="FMID" id="FMID" value="<c:out value='<%= fileBean.getFMID()%>'/>" readonly="readonly" class="readonly">
							
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
							<td colspan="2">
							<% if(fileBean.getFILENO().isEmpty()){ %>
							<input type="text" id="FILENO" onkeydown="changecase(this,'U')" size="40" name="FILENO" tabindex="1" value="<c:out value='<%= fileBean.getFILENO()%>'/>" onblur="getFileSearch(this);setOraignatedAt(this);fileNoValidation(this);" onkeypress="allowAlphaNum('&/*-');changecase(this,'U')">
							<%}else if((sessionBean.getLOGINID().equalsIgnoreCase("alok")) || (sessionBean.getLOGINID().equalsIgnoreCase("neeraj") )){ %>
							<input type="text" id="FILENO" onkeydown="changecase(this,'U')" size="40" name="FILENO" tabindex="1" value="<c:out value='<%= fileBean.getFILENO()%>'/>" onblur="getFileSearch(this);setOraignatedAt(this);" onkeypress="allowAlphaNum('()&/*-');changecase(this,'U');">
							<%}else{ %>
							<input type="text" id="FILENO" onkeydown="changecase(this,'U')" size="40" name="FILENO" tabindex="1" value="<c:out value='<%= fileBean.getFILENO()%>'/>" onblur="getFileSearch(this);setOraignatedAt(this);" onkeypress="allowAlphaNum('()&/*-');changecase(this,'U');" readonly>
							<%} %>
							<input type="button" tabindex="0" name="btnADD" id="btnADDf" value=" + " onclick="addlinkfile();" class="butts1" title="Add More Link File here."></td>
							
								<td>Originated Branch<span style="color: red"><span
								style="color: red"><b>*</b></span></span></td>
								<td><select style="width: 200px;height: 20px" name="DEPARTMENTCODE" id="DEPARTMENTCODE" tabindex="3">
								<option value="" selected> </option>
								<%
									for(int k=0;k<queryBranchList.size();k++){
									CommonBean beanCommon = (CommonBean) queryBranchList.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getDEPARTMENTCODE())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
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
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getDESTINATIONMARKING())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select></td>
							
			    			
			    		</tr>
						<tr class="trodd" id="lfile1" style="display: none">
							<td>Link File No.<span style="color: red"><b></b></span></td>
							<td colspan="2"><input type="text" name="LINKFILENO1" id="LINKFILENO1"
							onkeypress="allowAlphaNumWithoutSpace('/&');changecase(this,'U')"
								size="40" value="<c:out value='<%= fileBean.getLINKFILENO1()%>'/>"></td>
							<td></td>
							<td></td>
							<td></td>
								<td></td>
							
						</tr>
						<tr class="trodd" id="lfile2" style="display: none">
							<td>Link File No.<span style="color: red"><b></b></span></td>
							<td colspan="2"><input type="text" name="LINKFILENO2" id="LINKFILENO2"
								onkeypress="allowAlphaNumWithoutSpace('/&');changecase(this,'U')"
								size="40" value="<c:out value='<%= fileBean.getLINKFILENO2()%>'/>"></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
							
						</tr>
						<tr class="trodd" id="lfile3" style="display: none">
							<td>Link File No.<span style="color: red"><b></b></span></td>
							<td colspan="2"><input type="text" id="LINKFILENO3"
								onkeypress="allowAlphaNumWithoutSpace('/&');changecase(this,'U')"
								size="40" name="LINKFILENO3" value="<c:out value='<%= fileBean.getLINKFILENO3()%>'/>"></td>
						<td></td>
						<td></td>
						<td></td>
								<td></td>
							
						</tr>
						<tr class="trodd" id="lfile4" style="display: none">
							<td>Link File No.<span style="color: red"><b></b></span></td>
							<td colspan="2"><input type="text" id="LINKFILENO4"
								onkeypress="allowAlphaNumWithoutSpace('/&');changecase(this,'U')"
								size="40" name="LINKFILENO4" value="<c:out value='<%= fileBean.getLINKFILENO4()%>'/>"></td>
						<td></td>
								<td></td>
						<td></td>
								<td></td>
							
						</tr>
						<tr>
						<td nowrap >E-Office File No.</td>							 
								<td nowrap colspan="2"><input type="text" name="EofficeFILENO" id="EofficeFILENO" size="40" tabindex="4" maxlength="40" style="text-transform: uppercase;"
								 onkeypress="allowAlphaNumWithoutSpace('/');" value="<c:out value='<%=fileBean.getEOFFICEFILENO()%>'/>"></td>
								 <input type="hidden" name="MaximizeOnAttach" id="MaximizeOnAttach" />
						<td>Budget</td>
						<td><select  name="ISBUDGETPOINT" id="ISBUDGETPOINT" tabindex="4"  >
								<option value=0 <c:out value='<%="0".equalsIgnoreCase( fileBean.getISBUDGETPOINT())?"selected":""%>'/> >Not Budget</option>
								<option value=1 <c:out value='<%="1".equalsIgnoreCase( fileBean.getISBUDGETPOINT())?"selected":""%>'/>>Budget</option>
							</select>
							</td>
							<td style="display: none;" id="persontd">VIP Person</td>
							<td>
							 <input style="width: 150px;height: 20px; display: none;" type="text" id="vipname" name="vipname" autocomplete="off" value="<c:out value='<%= fileBean.getBUDGETPOINTVIPPERSON().length()>0? fileBean.getBUDGETPOINTVIPPERSON():"" %>'/>">
      						<input type="hidden"  id="vipid" name="vipid" type="text" value="<c:out value='<%= fileBean.getBUDGETPOINTVIPID().length()>0? fileBean.getBUDGETPOINTVIPID():0 %>'/>"  >
							</td>
							
							
						</tr>
					</table>	
			    		</fieldset>
			    	<fieldset>	
			    	<table id="data" width="100%">	
			    	
					
					<tr class="trodd">
						<td colspan="7" ><fieldset><legend>Paper Under Consideration(PUC)</legend>
							<table id="data" width="1000px;">
								<tr class="treven">
									<td width="30px;" ><nobr>S.No.</nobr></td>
									<td nowrap="nowrap" >Ref. No.</td>
									<td nowrap="nowrap" >Recd. From</td>
									<td nowrap="nowrap" >Sub.</td>
									<td nowrap="nowrap" >Subject</td>
									<td nowrap="nowrap" >Ref. Status</td>
									<td nowrap="nowrap">Compliance<span
								style="color: red"><b>*</b></span></td>
								<td nowrap="nowrap" >Compliance Remarks<span
								style="color: red"><b>*</b></span></td>
								<td nowrap="nowrap" name="sugra" id="sugra" ><div id="sugcomremarksc" >Suggestion</div></td>
								


								</tr>
								
								<TBODY id="baserow">


								<tr class="trodd" style="display: none">
								<td align="center">&nbsp;</td>
										<td nowrap="nowrap" width="35px;"><input type="text"
											size="40" maxlength="40" tabindex="5" onblur="ValidateReference(this,document.getElementById('REFID'));"
											name="REFNO" id="REFNO" style=" background-image: url('${pageContext.request.contextPath}/images/search.png'); background-position: right;background-repeat: no-repeat; ">
										<input type="hidden" name="REFID" id="REFID"></td>
										<td nowrap="nowrap"  name="REFERENCENAMEV" id="REFERENCENAMEV" width="100px;">&nbsp;</td>
										<td nowrap="nowrap" name="SUBJECTCODEV" id="SUBJECTCODEV" width="50px;">&nbsp;</td>
										<td name="SUBJECTV" id="SUBJECTV">&nbsp;</td>
										<td nowrap="nowrap"  name="STATUSV" id="STATUSV">&nbsp;</td>
										<td nowrap="nowrap">
										<select style="width: 200px;height: 20px" name="COMPLIANCECODE" id="COMPLIANCECODE" tabindex="6" onchange="showSuggestion();" >
								<option value="" selected>Select one- </option>
								<%
									for(int k=0;k<complianceCode.size();k++){
									CommonBean beanCommon = (CommonBean) complianceCode.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" ><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select></td>
							<td><textarea rows="2"
											cols="40" name="COMPREMARKS" id="COMPREMARKS" onkeypress="allowAlphaNum('()&/*');" tabindex="7"></textarea></td>
											
							
								<td nowrap="nowrap" id="tdsugra"  name="tdsugra" style="display:none">
										<select style="width: 200px;height: 20px" name="tdsugcomremarksc" id="tdsugcomremarksc" style="display: none;" tabindex="7" onchange="putInRemarks(this);" >
								<option value="" selected>Select one- </option>
								
								<option value="Not Completed Prescribed service of 5/10years">Not Completed Prescribed service of 5/10years</option>
								<option value="Not applied in prescribed format">Not applied in prescribed format</option>
								<option value="Change of category not permissible">Change of category not permissible</option>
								<option value="does not fulfil prescribed criteria">does not fulfil prescribed criteria</option>
								<option value="Rehabilitation is a state subject">Rehabilitation is a state subject</option>
								<option value="Change of name of station is done by Home minister and concerned state">Change of name of station is done by Home minister and concerned state</option>
								<option value ="Lot of vacancies in the cadre, cannot be agreed to">Lot of vacancies in the cadre, cannot be agreed to</option>
								<option value="Registered in the priority register">Registered in the priority register</option>
								<option value="Subject of Minsitry of Home Affairs">Subject of Ministry of Home Affairs</option>
								<option value="Should apply when notified by RRB/RSC">Should apply when notified by RBB/RSC</option>
								<option value="Transfer essentially falls under the category of service condition which stand governed by Rules Regulations and Subsidiary instructions etc and concerned employee may accordingly be advised to place his request through proper channel to the prescribed Competent Authority">Transfer essentially falls under the category of service condition which stand governed by Rules Regulations and Subsidiary instructions etc and concerned employee may accordingly be advised to place his request through proper channel to the prescribed Competent Authority</option>
								
								
						
					
								
							</select></td>
								
					
							
									</tr>
								


									<% for(int i=0; i < fileBean.getRefBeanList().size();i++){
					TrnFileRef refBean = (TrnFileRef)fileBean.getRefBeanList().get(i);
					
						%>
									<tr class="trodd">
									<td align="center"><c:out value='<%=i+1%>' /></td>
										<td nowrap="nowrap" width="35px;">
						<input type="text" class="readonly" size="40" maxlength="40" onblur="ValidateReference(this,'<c:out value="<%=refBean.getREFID() %>"/>');" 
						name="REFNO" id="REFNO" value="<c:out value='<%=refBean.getREFNO()+" ("+refBean.getINCOMINGDATE()+")" %>' />" style=" background-image: url('${pageContext.request.contextPath}/images/search.png'); background-position: right;background-repeat: no-repeat; ">
										<input type="hidden" name="REFID" id="REFID" value="<c:out value='<%=refBean.getREFID() %>'/>"></td>
										<td nowrap="nowrap" name="REFERENCENAMEV" id="REFERENCENAMEV" width="100px;"><nobr><c:out value='<%=refBean.getREFERENCENAME() %>'/><br><c:out value='<%=refBean.getVIPSTATUS() %>'/>,<c:out value='<%=refBean.getSTATENAME() %>'/>&nbsp;</nobr></td>
										<td nowrap="nowrap" name="SUBJECTCODEV" id="SUBJECTCODEV" width="50px;"><nobr><c:out value='<%=refBean.getSUBJECTCODE() %>'/>&nbsp;</nobr></td>
										<td id="SUBJECTV" name="SUBJECTV" ><c:out value='<%=refBean.getSUBJECT() %>'/>&nbsp;</td>
										<td nowrap="nowrap" name="STATUSV" id="STATUSV"><nobr><c:out value='<%=refBean.getSTATUS() %>'/>&nbsp;</nobr></td>
										<td nowrap="nowrap">
										<select style="width: 200px;height: 20px" name="COMPLIANCECODE" id="COMPLIANCECODE" onchange="showSuggestion(this);">
								<option value="" selected>Select one- </option>
								<%
									for(int k=0;k<complianceCode.size();k++){
									CommonBean beanCommon = (CommonBean) complianceCode.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" 	<c:out value='<%=beanCommon.getField1().equals(refBean.getCompliance())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select>
										<input type="button" name="btnDEL"
											id="btnDEL" onclick="deleteReference(this,'<c:out value="<%=refBean.getREFID() %>"/>');"
											class="delbutt" title="Delete Reference." ></td>
									<td><textarea rows="2"
											cols="40" name="COMPREMARKS" id="COMPREMARKS" onkeypress="allowAlphaNum('()&/*');" tabindex="7"><c:out value="<%= refBean.getComplianceremarks()%>"/></textarea></td>


							<td nowrap="nowrap" id="tdsugra"  name="tdsugra" style="display:none">
										<select style="width: 200px;height: 20px" name="tdsugcomremarksc" id="tdsugcomremarksc" tabindex="7" onchange="putInRemarks(this);" style="display: none;" >
								<option value="" selected>Select one- </option>
								
								<option value="Not Completed Prescribed service of 5/10years">Not Completed Prescribed service of 5/10years</option>
								<option value="Not applied in prescribed format">Not applied in prescribed format</option>
								<option value="Change of category not permissible">Change of category not permissible</option>
								<option value="does not fulfil prescribed criteria">does not fulfil prescribed criteria</option>
								<option value="Rehabilitation is a state subject">Rehabilitation is a state subject</option>
								<option value="Change of name of station is done by Home minister and concerned state">Change of name of station is done by Home minister and concerned state</option>
								<option value ="Lot of vacancies in the cadre, cannot be agreed to">Lot of vacancies in the cadre, cannot be agreed to</option>
								<option value="Registered in the priority register">Registered in the priority register</option>
								<option value="Subject of Minsitry of Home Affairs">Subject of Ministry of Home Affairs</option>
								<option value="Should apply when notified by RRB/RSC">Should apply when notified by RBB/RSC</option>
								<option value="Transfer essentially falls under the category of service condition which stand governed by Rules Regulations and Subsidiary instructions etc and concerned employee may accordingly be advised to place his request through proper channel to the prescribed Competent Authority">Transfer essentially falls under the category of service condition which stand governed by Rules Regulations and Subsidiary instructions etc and concerned employee may accordingly be advised to place his request through proper channel to the prescribed Competent Authority</option>
								
								
								
					
								
							</select></td>
							
									</tr>

<%
						}
					%>

								</TBODY>
								<tr class="trodd">
									<td ><input type="button" name="btnADD" tabindex="7"
										id="btnADD" value=" + "
										onclick="addrowref(window.document.getElementById('baserow'));"
										class="butts1" title="Add Multiple Reference here."></td>
									<td ></td>
									<td ></td>
									<td ></td>
									<td ></td>
									<td ></td>
									<td></td>
									<td></td>

								</tr>
							</table>
							</fieldset>
							</td>
					</tr>
					
					
					<tr class="trodd">
						<td colspan="7" >
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
											cols="80" name="SUBJECT" id="SUBJECT" onkeypress="allowAlphaNum('()&/*');" tabindex="8"><c:out value="<%= fileBean.getSUBJECT()%>"/></textarea>
										<select  name="SUBJECTCODE" id="SUBJECTCODE" style="width: 100px; display: none">
											<option value=""> </option>
											<%
											for(int j=0;j<subjectListr.size();j++){
											CommonBean beanCommon = (CommonBean) subjectListr.get(j);
											%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
												<c:out value='<%=beanCommon.getField1().equalsIgnoreCase("")?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
											<%
										}%>
										</select></td>
										<td nowrap="nowrap" ><select name="RECEIVEDFROM" id="RECEIVEDFROM"
											style="width: 200px;height: 20px"  tabindex="9"> 
											<option value=""> </option>
											<%
											for(int k=0;k<markingToList.size();k++){
											CommonBean beanCommon = (CommonBean) markingToList.get(k);
											%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
												<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getRECEIVEDFROM())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
											<%
										}%>
										</select></td>
										<td nowrap="nowrap" width="71"  id="umsc"><select
											name="FILESUBJECTCODE" id="FILESUBJECTCODE" style="width: 120px;height: 20px" tabindex="10" >
											<option value=""> </option>
											<%
											//System.out.println("fileBean.getFILESUBJECTCODE():::"+fileBean.getFILESUBJECTCODE());
											for(int j=0;j<subjectListf.size();j++){
											CommonBean beanCommon = (CommonBean) subjectListf.get(j);
											%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
												<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getFILESUBJECTCODE())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
											<%
										}%>
										</select></td>
										<td nowrap="nowrap" width="22"  id="umpp" ><input type="text"  tabindex="11"
											size="25" maxlength="30" name="PROPOSEDPATH" id="PROPOSEDPATH" value="<c:out value='<%= fileBean.getPROPOSEDPATH()%>'/>"></td>
										<td nowrap="nowrap" id="umtt" style="display: none" width="111"><select name="UMARKINGTO" id="UMARKINGTO"  tabindex="11"
											style="width: 200px;height: 20px">
											<option value=""> </option>
											<%
							for(int j=0;j<markingToList.size();j++){
							CommonBean beanCommon = (CommonBean) markingToList.get(j);
							%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(markingBeanU.getMARKINGTO())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								
											<%
						}%>
										</select></td>
										<td nowrap="nowrap" id="umdt" style="display: none" ><input type="text"  tabindex="12"
											name="UMARKINGDATE" id="UMARKINGDATE" size="12" maxlength="10"
											value="<c:out value='<%=markingBeanU.getMARKINGDATE().equalsIgnoreCase("")?serverDate:markingBeanU.getMARKINGDATE()%>'/>"  onblur="chkWorkDate(this);"><input type="hidden"
											name="UMARKINGSEQUENCE" id="UMARKINGSEQUENCE" size="12" maxlength="10"
											value="<c:out value='<%=markingBeanU.getMARKINGSEQUENCE()%>'/>"><input
										type="button" class="calbutt" name="umrkdt"
										onclick="newCalendar('UMARKINGDATE-L');"></td>
										<td width="145" id="umrt" style="display: none" >
										<textarea rows="2" cols="20" name="UMARKINGREMARK" id="UMARKINGREMARK"  tabindex="13"><c:out value='<%= markingBeanU.getMARKINGREMARK()%>'/></textarea></td>
							

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
					    		<td nowrap="nowrap"  width="135">Marked To</td>
					    		<td nowrap="nowrap" size="12">Marked On</td>
					    		<td nowrap="nowrap" size="12">Returned On</td>
					    		
								
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
						<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
						<%		}%>
					</select>
						</td>
						<td nowrap="nowrap" ><input type="hidden"
											name="IMARKINGSEQUENCE" id="IMARKINGSEQUENCE" size="12" maxlength="10"
											><input type="text"
											name="IMARKINGDATE" id="IMARKINGDATE" size="12" maxlength="10"   
											value="<c:out value='<%=serverDate%>'/>"  onblur="chkWorkDate(this);"></td>
						<td nowrap="nowrap" ><input type="text"
											name="IRETURNDATE" id="IRETURNDATE" size="12" maxlength="10" 
											value=""  onblur="chkWorkDate(this);"   tabindex="16"></td>
						<td width="145" style="display: none" >
										<textarea rows="2" cols="20" name="IMARKINGREMARK" id="IMARKINGREMARK"  tabindex="11"></textarea></td>
							
						</tr>
						
					
					<% for(int i=0; i < fileBean.getIntmarkingBeanList().size();i++){
					TrnFileIntMarking intmarkingBean = (TrnFileIntMarking)fileBean.getIntmarkingBeanList().get(i);
					
						%>
						<tr class="trodd">
							<td nowrap="nowrap" width="30"><c:out value='<%=i+1%>' /></td>
							
							<td nowrap="nowrap" width="129">
						<select name="IMARKINGTO" id="IMARKINGTO" style="width: 200px;height: 20px"   tabindex="14"> 
						<option value=""> </option>
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
											value="<c:out value='<%=intmarkingBean.getMARKINGSEQUENCE()%>'/>"><input type="text"  
											name="IMARKINGDATE" id="IMARKINGDATE" size="12" maxlength="10"  tabindex="14"
											value="<c:out value='<%= intmarkingBean.getMARKINGDATE().equalsIgnoreCase("")?serverDate:intmarkingBean.getMARKINGDATE()%>'/>"   onblur="chkWorkDate(this);"></td>
							<td nowrap="nowrap" ><input type="text"
											name="IRETURNDATE" id="IRETURNDATE" size="12" maxlength="10"   tabindex="14"
											value="<c:out value='<%= intmarkingBean.getCHANGEDATE()%>'/>"  onblur="chkWorkDate(this);"></td>
							<td width="145" style="display: none" >
										<textarea rows="2" cols="20" name="IMARKINGREMARK" id="IMARKINGREMARK"  tabindex="11"></textarea></td>
							
						</tr>
					<%
						}
					%>
					
					</TBODY>
					<tr class="trodd">
						<td width="30"> <input type="button" name="btnADDI" id="btnADDI" value=" + " onclick="addrowMarkingFile();"
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
								<td id="thn1"  style="display: none"><div id="thSentTo" style="display: none;">Sent To</div></td>
							</tr>
			    	<tr class="trodd">
							<td nowrap="nowrap" ><select name="FILESTATUS1" onchange="showNOL(this);"
								id="FILESTATUS1" style="height:20px"  tabindex="17">
								
								<%
									for(int k=0;k<fileStatus1.size();k++){
									CommonBean beanCommon = (CommonBean) fileStatus1.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getFILESTATUS1())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select></td><td nowrap="nowrap"><select name="FILESTATUS2"
								id="FILESTATUS2" style="height:20px"  tabindex="18">
								
								<%
									for(int k=0;k<fileStatus2.size();k++){
									CommonBean beanCommon = (CommonBean) fileStatus2.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getFILESTATUS2())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select></td>
							<td id="tdn" style="display: none"><div id="tdNOL" style="display: none;"><input type="text" name="NOL" id="NOL" size="2" maxlength="3" value="<c:out value='<%= fileBean.getNOL().length()>0? fileBean.getNOL():0 %>'/>" tabindex="19"></div></td>
								<td><textarea rows="2" tabindex="20" cols="50" name="REMARKS"
									id="REMARKS"><c:out value='<%= fileBean.getREMARKS()%>'/></textarea></td>
									
							<td id="tdn1" style="display: none"><div id="tdSentTo" style="display: none;"><input type="text"  name="SENTTO" id="SENTTO" value="<c:out value='<%= fileBean.getSENTTO().length()>0? fileBean.getSENTTO():"" %>'/>" tabindex="19"></div></td>
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
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(markingBeanD.getMARKINGTO())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select></td>
							<td nowrap="nowrap" width="121"><nobr><input type="text"
											name="DMARKINGDATE" id="DMARKINGDATE" size="12" maxlength="10" tabindex="21" 
											value="<c:out value='<%=markingBeanD.getMARKINGDATE()%>'/>"  onblur="chkWorkDate(this);">
											<input type="hidden"
											name="DMARKINGSEQUENCE" id="DMARKINGSEQUENCE" size="12" maxlength="10"
											value="<c:out value='<%=markingBeanD.getMARKINGSEQUENCE()%>'/>"></nobr></td>
							<td width="328"><textarea rows="2" cols="35"
								name="DMARKINGREMARK" id="DMARKINGREMARK" tabindex="22"><c:out value='<%= markingBeanD.getMARKINGREMARK()%>'/></textarea></td>
							<td nowrap="nowrap" width="98"><select name="REPLYTYPE" id="REPLYTYPE" style="height: 20px"  tabindex="23">
								<option value=""> </option>
								<%
									for(int k=0;k<replyCode.size();k++){
									CommonBean beanCommon = (CommonBean) replyCode.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(fileBean.getREPLYTYPE())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select></td>
							<td width="443"><textarea rows="2" cols="50" name="REASON"
								id="REASON" tabindex="24"><c:out value='<%= fileBean.getREASON()%>'/></textarea></td>
						</tr>
					<%} %>
						
					</table>
				</fieldset>
				
							    				
				
			    </div>
			    <div align="right"><c:out value="<%= ((TrnFileMarking)fileBean.getMarkingBeanDList().get(0)).getSTATUSCHANGEUSER().toUpperCase() %>"/></div>
			   <div id="fileReply" >
			    <table width="1000">
 					<tr>
  						<td valign="top" >
							
			<textarea id="REPLY" name="REPLY" cols="160" rows="15"><c:out value='<%=fileBean.getREPLY() %>'/></textarea>
		
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
											<c:out value='<%=bn.getField1()%>'/>
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
											<c:out value='<%=bn.getField1()%>' escapeXml="False"/>
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
											<c:out value='<%=bn.getField1()%>' escapeXml="False"/>
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
			    
			    <%-- <div id="Received">
		
				
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
								<td><c:out value='<%=inBoxBean.getFILENO() %>'/></td>
								<td><c:out value='<%=inBoxBean.getMARKINGDATE() %>'/></td>
								<td><c:out value='<%=inBoxBean.getMARKINGFROM() %>'/></td>
								<td><c:out value='<%=inBoxBean.getSUBJECTCODE() %>'/></td>
								<td><c:out value='<%=inBoxBean.getSUBJECT() %>'/></td>
								<td><c:out value='<%=inBoxBean.getMARKINGREMARK() %>'/></td>
								<td><input type="hidden" name="MARKINGTYPE" id="MARKINGTYPE" 
									value="<c:out value='<%= inBoxBean.getMARKINGTYPE()%>'/>"><select name="INACTION" id="INACTION"
									style="width: 100px;height: 20px">
									<option value="" selected="selected"> </option>
									<option value="RCD"
										<c:out value='<%="RCD".equalsIgnoreCase(inBoxBean.getACTION())?"selected":""%>'/>>Received</option>
									<option value="NOR"
										<c:out value='<%="NOR".equalsIgnoreCase(inBoxBean.getACTION())?"selected":""%>'/>>Not
									Received</option>

								</select><input type="button" value="confirm" class="butts"
									onclick="confirmAction('<c:out value='<%=inBoxBean.getFMID()%>'/>', '<%=i%>');"></td>
							</tr>
							<%
						}
						%>
						</tbody>
					</table>
					</fieldset>
				</div>	 --%>
				
				<div id="MainAttachment">
			      <table width="100%">
			    	<tr>
			    		<th width="100%" align="left" valign="middle" height="30"">
			    			<b>&nbsp;<c:out value='<%= fileBean.getREGISTRATIONNO() %>'/></b>
			    		</th>
			    	</tr>
			    </table>
			      
			      <fieldset style="border: 0px;">
			    <table id="data">
					<tr >
						<td align="left" width="100%" colspan="4">&nbsp;
							<table id="data" width="100%">
							<% if(filepathArr.size()>0) {%>
								<tr>
									<th align="center" width="80%">Name</th>
							<!-- 		<th  style="visibility:hidden; align="center" width="20%">Delete</th> -->
									<!-- <th  align="center" width="20%">Delete</th> -->
									<th  align="center" width="20%">Delete</th>
								</tr>
							<%} %>	
								<tbody id="refimage">
								<%System.out.println(filepathArr.size());
								for (int i = 0; i < filepathArr.size(); i++) {
									CommonBean cmnbn = (CommonBean) filepathArr.get(i);
						    	%>
								<tr>
									<td width="507" align="left"><a href="#" onclick="openAttachment('<c:out value="<%=cmnbn.getField1()%>"/>','<c:out value="<%=cmnbn.getField2()%>"/>','<c:out value="<%=cmnbn.getField3()%>"/>','<c:out value="<%=cmnbn.getField4()%>"/>','<c:out value="<%=cmnbn.getField5()%>"/>','<c:out value="<%=cmnbn.getField6()%>"/>');"><font color="blue"><c:out value="<%=cmnbn.getField3()%>"/></font></a></td>
									<td align="center">
									<% if("F".equalsIgnoreCase(cmnbn.getField6())) { %>   
									<input type="button" name="delfile" id="delfile" onclick="delRefFile(this,'<c:out value="<%=cmnbn.getField1()%>"/>','<c:out value="<%=cmnbn.getField2()%>"/>');" class="delbutt" title="Delete Attachment" >
									<%} %>
									</td>
								</tr>
								<%} %>
							</table>
						</td>
					</tr>
					
				<tbody id="attachimg">
					<tr  style="display:none">
						<th><nobr>File Location</nobr></th>
						<td colspan="3">
							<input type="hidden" name="ATTACHMENTFMID" id="ATTACHMENTFMID" value="<c:out value='<%=fileBean.getFMID()%>'/>">
							<input size="30" type="file" class="drop" name="uploadImage" accept="jpg">
						</td>
					</tr>
				</tbody>
				<tr class="trodd">
					<td colspan="6">
						<%if(request.getAttribute("fileBean")!=null){ %>
						<input class="butts" type="button" name="attachbutt" value="Add pdf/jpg" style= "width: 100px" onclick="addrowAttach();">(Upload .JPG or .PDF files only.)
						<%} %>
						<input name = "cntimg" type = "hidden" value="0">
					</td></tr>
			</table>
			</fieldset>
			 </div>
				
				
</div></td></tr>
			  
<tr><td>			   
			    
			    <!-- <table width="100%" style="display: none;">
			    		<tr class="trodd">
						<td colspan="6" align="center"><input type="button" name="btnNEW" id="btnNEW" class="buttNew" style="display: none;" >  
										<input type="button" name="btnSAVE" id="btnSAVE" tabindex="25" onclick="submitForm('SAVE');" class="buttSave">
										
										<input type="button" name="btnCLEAR" onclick="submitForm('CLEAR');" class="buttClear">
										
										<input type="hidden" name="btnClick" id="btnClick">
						</td>
					</tr>
			    		</table> -->	
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
<%	if(msg.length()>3) { String s[] = msg.split("/-");%>

			<%-- <BR><img src="images/<c:out value='<%= msg.substring(0,3)%>'/>.gif"/><c:out value='<%= msg.substring(3) %>' escapeXml="false"/>
			 <BR><input type="button" value=" Ok " onclick="callMe()" id="msgok"> --%>
			
			<BR><img src="images/<c:out value='<%= msg.substring(0,3)%>'/>.gif"/><c:out value='<%= s[0] %>' escapeXml="false"/>
			 <BR><input name="btnOK" id="btnOK" type="button"  class="butts" value=" Close " onclick="callMe()">
			<%--  <%if(s[2].equalsIgnoreCase("8")) { %> --%>
			 <input name="btnOKAttach" id="btnOKAttach" type="button" class="butts" value=" Continue to attach file " onclick="callMeAttach()">
			<%-- <% } %> --%>
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
<% session.setAttribute("attachFlag", "0"); %>
</html>