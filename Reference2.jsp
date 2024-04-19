<!DOCTYPE html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnReference"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.MstClass"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnMarking"%>
<%@page import="in.org.cris.mrsectt.dao.TrnReferenceDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnBudget"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.Beans.TrnYellowSlip"%><html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );  
   response.setDateHeader( "Expires", 0 );
%>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<c:out value="<%=theme%>"/>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<LINK  href="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 
<!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/jquery/themes<c:out value="<%=theme%>"/>/base/ui.all.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />  -->
<link type="text/css" href="${pageContext.request.contextPath}/theme/dtree.css" rel="stylesheet" />
  <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dateFormatter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/TrnReferenceDAO.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/CommonDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<link href="${pageContext.request.contextPath}/theme/sub/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="${pageContext.request.contextPath}/theme/autoSuggest.css" rel="stylesheet" />
<!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" /> -->

  <%
  	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
  	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();
  	
  	TrnReference refBean =  (request.getAttribute("refBean")!=null) ?(TrnReference)request.getAttribute("refBean"): new TrnReference();
  	TrnReference remBean =  (request.getAttribute("remBean")!=null) ?(TrnReference)request.getAttribute("remBean"): new TrnReference();
  	String rClass = (String) session.getAttribute("refClass");// .getAttribute("refClass")!=null) ? (String) request.getAttribute("refClass"): "";
  	if(refBean.getINCOMINGDATE().length()==0){
  		refBean.setINCOMINGDATE(CommonDAO.getSysDate("dd/MM/yyyy"));
  	}
  	if(refBean.getCOMPLIANCEDATE().length()==0){
  		refBean.setCOMPLIANCEDATE(CommonDAO.getSysDate("dd/MM/yyyy"));
  	}
  	
//   	if(refBean.getLETTERDATE().length()==0){
//   		refBean.setLETTERDATE(CommonDAO.getSysDate("dd/MM/yyyy"));
//   	}
  	if(refBean.getSIGNEDON().length()==0 && refBean.getREFID().length()==0 ){
  		refBean.setSIGNEDON(CommonDAO.getSysDate("dd/MM/yyyy"));
  	}
  	
  
  	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
  	String refclass="";
  	String eofficeFlag="";
  	if(request.getAttribute("refBean")!=null){
  		refclass=refBean.getREFCLASS();
  		eofficeFlag = refBean.getEOFFIEFLAG();
  	}
  	//String 
  			//code for showing alteast one row in the marking detail table test 
  	String defaultTargetDays = "7";
      if(refBean.getMarkingBeanList().size()==0){
          TrnMarking tMark =  new TrnMarking();
          tMark.setMARKINGDATE(serverDate);
          tMark.setTARGETDAYS(defaultTargetDays);
          //tMark.setTARGETDATE("15");
          
          ArrayList<TrnMarking> arrMark =  new ArrayList<TrnMarking>();
          arrMark.add(tMark);
          refBean.setMarkingBeanList(arrMark);
      }//////
      
      //code for showing alteast one row in the budget detail table
      if(refBean.getBudgetBeanList().size()==0){
          TrnBudget tBudget =  new TrnBudget();
          ArrayList<TrnBudget> arrtBudget =  new ArrayList<TrnBudget>();
          arrtBudget.add(tBudget);
          refBean.setBudgetBeanList(arrtBudget);
      }//////
      
      
  	
  	String queryRefClass = "SELECT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"' and year=to_char(sysdate,'yyyy') order by refclass"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
	
	String queryRefCCategory = "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' ORDER BY PRIORITYNO"; 
	ArrayList<CommonBean> refCategoryList = (new CommonDAO()).getSQLResult(queryRefCCategory, 3);
	
	String queryUrgency = sessionBean.getISCONF().equalsIgnoreCase("1")? "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' ORDER BY PRIORITYNO": "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND ISCONF = 0 ORDER BY PRIORITYNO"; 
	ArrayList<CommonBean> urgencyList = (new CommonDAO()).getSQLResult(queryUrgency, 3);

	String queryReceivedBy = "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '9' AND CODE<>'00' ORDER BY PRIORITYNO"; 
	ArrayList<CommonBean> receivedByList = (new CommonDAO()).getSQLResult(queryReceivedBy, 3);
	
	String queryLanguage = "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '3' AND CODE <> '00'"; 
	ArrayList<CommonBean> languageList = (new CommonDAO()).getSQLResult(queryLanguage, 3);

 	String querySecurityGrading = "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '2' AND CODE<>'00'"; 
	ArrayList<CommonBean> SecurityGradingList = (new CommonDAO()).getSQLResult(querySecurityGrading, 3);

 	String queryState = "SELECT STATECODE, STATENAME FROM MSTSTATE order by STATECODE"; 
	ArrayList<CommonBean> stateList = (new CommonDAO()).getSQLResult(queryState, 2);
	String queryparty = "SELECT partycode, NAME FROM MSTPARTY ORDER by PARTYCODE";
	ArrayList<CommonBean> partyList = (new CommonDAO()).getSQLResult(queryparty, 2);
	/*
 	String querySubject = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT ORDER BY SUBJECTCODE"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 3);
	*/
// 	String querySubject = "SELECT SUBJECTCODE, SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTTYPE = 'R' AND SUBJECTDEPT = '"+sessionBean.getDEPTCODE()+"'";
 	
 	 String querySubject = " SELECT B.PREFERREDID SUBJECTCODE,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME " +    
  	 "	FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 

	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 2);

// 	String queryBdgSubject = "SELECT SUBJECTCODE, SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTTYPE = 'R' AND SUBJECTDEPT = '"+sessionBean.getDEPTCODE()+"'";

 	String queryBdgSubject =  " SELECT B.PREFERREDID SUBJECTCODE,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME " +    
  	 " FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 

	ArrayList<CommonBean> bdgSubjectList = (new CommonDAO()).getSQLResult(queryBdgSubject, 2);

	/*
	String queryMarkingTo = " SELECT DISTINCT A.ROLEID, A.ROLENAME FROM MSTROLE A WHERE ROLEID <> '"+sessionBean.getLOGINASROLEID()+"'"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	*/
	
	/*
 	String queryMarkingTo = " SELECT A.ROLEID, A.ROLENAME "+ 
 							" FROM MSTROLE A, MSTTENURE B WHERE B.ISACTIVE = 'Y' AND A.ROLEID <> '"+sessionBean.getLOGINASROLEID()+"' "+
 							" AND A.ROLEID=B.ROLEID ORDER BY A.ROLENAME"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	*/
	
	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME, (Select deptname from mstdept where deptcode  = (SELECT DEPTcode FROM mstrole A WHERE A.ROLEID = B.PREFERREDID )) DEPTNAME"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 3);

	String querySignedBy = " SELECT TO_NUMBER(B.PREFERREDID) ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						   " FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'" +
 						   " UNION" +
						   " SELECT ROLEID, ROLENAME FROM MSTROLE WHERE ROLEID = '"+refBean.getSIGNEDBY()+"' ORDER BY ROLENAME";  
	ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);

	String queryAckBy = " SELECT TO_NUMBER(B.PREFERREDID) ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						   " FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='7' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'" +
 						   " UNION" +
						   " SELECT ROLEID, ROLENAME FROM MSTROLE WHERE ROLEID = '"+refBean.getACKBY()+"' ORDER BY ROLENAME";  
	ArrayList<CommonBean> queryAckByList = (new CommonDAO()).getSQLResult(queryAckBy, 2);
	
	
	String queryreplyCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00'"; 
	ArrayList<CommonBean> replyCode = (new CommonDAO()).getSQLResult(queryreplyCode, 3);
	
	
	String querycomplianceCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' order by code"; 
	ArrayList<CommonBean> complianceCode = (new CommonDAO()).getSQLResult(querycomplianceCode, 3);
	
	String queryfileStatus1 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'0' AND CODE <> '00' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus1 = (new CommonDAO()).getSQLResult(queryfileStatus1, 3);
	
	String queryfileStatus2 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'0' AND CODE <> '00' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus2 = (new CommonDAO()).getSQLResult(queryfileStatus2, 3);
	
	
	ArrayList<TrnMarking> returnBoxList = (new TrnReferenceDAO()).getReturnBoxData(sessionBean.getLOGINASROLEID());
  	
  	ArrayList<CommonBean> arrTree = new ArrayList<CommonBean>();
  	arrTree = (new TrnReferenceDAO()).getTreeview(refBean.getREFID());

  	ArrayList<CommonBean> arrTreeReminder = new ArrayList<CommonBean>();
  	arrTreeReminder = (new TrnReferenceDAO()).getTreeviewReminder(refBean.getREFID());
  	
  	String querySalutation = "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '11' AND CODE<>'00' ORDER BY PRIORITYNO"; 
	ArrayList<CommonBean> salutationList = (new CommonDAO()).getSQLResult(querySalutation, 3);
  	
  	ArrayList<CommonBean> filepathArr=new ArrayList();
	if(request.getAttribute("filepathArr")!=null) {
		filepathArr = (ArrayList) request.getAttribute("filepathArr");
	}

    ArrayList<CommonBean> reminderArr=new ArrayList();
		if(request.getAttribute("reminderArr")!=null) {
		reminderArr = (ArrayList) request.getAttribute("reminderArr");
	}
  	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
  	String attachFlag = StringFormat.nullString((String)session.getAttribute("attachFlag"));
 %>
 <script type="text/javascript"><!--
 
 var refClassListArray = new Array(2);
 refClassListArray[0] = new Array(<c:out value='<%=refClassList.size()%>'/>);
 refClassListArray[1] = new Array(<c:out value='<%=refClassList.size()%>'/>);
 
 <% 
       		for(int i=0; i< refClassList.size();i++){
       			%> 
       				refClassListArray[0][<%=i %>] = '<c:out value="<%=refClassList.get(i).getField1()%>"/>'; 
       				refClassListArray[1][<%=i %>] = '<c:out value="<%=refClassList.get(i).getField2()%>"/>';
       			
       			<%
       		}
       
       %>
 
 
  $().ready(function() {
		var $scrollingDiv = $("#scrollingDiv");
 
		$(window).scroll(function(){			
			$scrollingDiv
				.stop()
				.animate({"marginTop": ($(window).scrollTop() + 0) + "px"}, "fast" );		
		});
		
		$('#EOFFICEREFNO').keypress(function(event){
			  if(event.keyCode == 13){
			    $('#fetchEofficedet').click();
			  }
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

function getValue(objname){
	
	//alert('obj is : '+objname );
	//alert('window.document.getElementById(objname) : '+window.document.getElementById(objname) );
	
	var value = window.document.getElementById(objname).value;
	return value;
}

	function getValues(objname)
	{
		var vals = new Array();
		
		for(var i=0;i<document.getElementsByName(objname).length;i++)
		{
			vals[i]=document.getElementsByName(objname)[i].value;
		}
		return vals;
	}
	
	//set the value of the objname object to the values of value array
	function setValues(objname, value){
		for(var i=0; i < objname.length; i++){
			
			alert(' value ['+i+']' + value[i]);
			alert(' name = ' + objname[i].name +' ##  value = '+objname[i].value);
			objname[i].value = '3';
		}
	}
	
  	function remoteSaveBudget() {
	
		var BDGREFID = getValues("BDGREFID");
		var BUDGETSEQUENCE = getValues("BUDGETSEQUENCE");
		var BDGSUBJECTCODE = getValues("BDGSUBJECTCODE");
		var BDGSUBJECT = getValues("BDGSUBJECT");
		var BDGMRREMARK = getValues("BDGMRREMARK");
		var BDGREMARK = getValues("BDGREMARK");
		var LOGINID = '<c:out value="<%=sessionBean.getLOGINID() %>"/>';
		
	//	TrnReferenceDAO.saveBudgetData(BDGREFID,BUDGETSEQUENCE,BDGSUBJECTCODE,BDGSUBJECT,BDGMRREMARK,BDGREMARK, LOGINID, isSaved);
	} 
	
    function isSaved(data){
    		var msg;
		if(data.split("~")[0]=="0"){
			msg=data.split("~")[1];
		} else{
			msg="Data Saved!!!";
		}
		


       
      
		
		document.getElementById('CommonMsg').innerText=msg; 
       
        window.document.getElementById('updateDiv').style.display="block";
        
		window.document.getElementById('CommonDivInner').style.display="block";
		
		window.document.getElementById('CommonbtnOK').focus();
		
		
	} 


function remoteSaveFileStatus() {
	
		var REFID = getValue("REFID");
		var FILENO = getValue("FILENO");
		var REGISTRATIONDATE = getValue("REGISTRATIONDATE");
		var IMARKINGTO = getValue("IMARKINGTO");
		var FILESTATUS1 = getValue("FILESTATUS1");
		var FILESTATUS2 = getValue("FILESTATUS2");
		var STATUSREMARK = getValue("STATUSREMARK");
		var REPLYTYPE = getValue("REPLYTYPE");
		var REASON = getValue("REASON");
		var DMARKINGTO = getValue("DMARKINGTO");
		var DMARKINGDATE = getValue("DMARKINGDATE");
		var LOGINID = '<c:out value="<%=sessionBean.getLOGINID() %>"/>';
		
		if(REFID.Trim()==''){
			alert('No Reference no. Selected for saving File Status!!!');
			return; 
		}
		
		if( chkblank(window.document.getElementById("FILENO")) && chkblank(window.document.getElementById("REGISTRATIONDATE"))
		
		){
		
			TrnReferenceDAO.saveFileStatus(REFID, FILENO, REGISTRATIONDATE, IMARKINGTO, FILESTATUS1, FILESTATUS2, STATUSREMARK,  REPLYTYPE, REASON, DMARKINGTO, DMARKINGDATE, LOGINID, isStatusSaved);
		}
		
		
	} 

function isStatusSaved(data){
		//if(data.split("~")[0]=="0"){
		//	alert(data.split("~")[1]);
		//} else{
		//	alert("Status Saved Successfully!!!");
		//}
		
		var msg;
		if(data.split("~")[0]=="0"){
			msg=data.split("~")[1];
		} else{
			msg="Status Saved Successfully!!!";
		}
		
		document.getElementById('CommonMsg').innerText=msg; 
       
        window.document.getElementById('updateDiv').style.display="block";
        
		window.document.getElementById('CommonDivInner').style.display="block";
		
		window.document.getElementById('CommonbtnOK').focus();

       
     
		
	} 

  
  var bugdetBaseRowObj;
  var budgetThisObj ;
  function delBudgetRow(thisObj, refId, budgetId, baseRowObj) {
	
	var baseRowLength =  baseRowObj.childNodes.length;
	var index = getIndex(thisObj);
	
	bugdetBaseRowObj = baseRowObj;
	budgetThisObj= thisObj;
	
	//alert(' baseRowLength : '+baseRowLength);
	//alert(' Index is : '+ index);
	if(baseRowLength > 1 && budgetId !=''){
	
		if(confirm('Are you sure you want to delete?')){
			TrnReferenceDAO.deleteBudgetRow(refId, budgetId, isBudgetDeleted);
			//baseRowObj.removeChild(baseRowObj.childNodes[index]);
		}
	}else{
		alert('Nothing to Delete');
	}
}
	
function isBudgetDeleted(data){
	if(data.split("~")[0]=="0"){
		alert(data.split("~")[1]);
	} else{
		alert("Delete Successfull!!!");;
		deleteRow(budgetThisObj, bugdetBaseRowObj);
	}
}  
	
function deleteRow(thisObj, bugdetBaseRowObj){
	var baseRowLength =  bugdetBaseRowObj.childNodes.length;
	var index = getIndex(thisObj);
	//alert(' baseRowLength : '+baseRowLength);
	//alert(' Index is : '+ index);
	if(baseRowLength > 1){
		//if(confirm('Are you sure you want to delete?')){
			bugdetBaseRowObj.removeChild(bugdetBaseRowObj.childNodes[index]);
	//	}
	}	

}

function setRefCategoryE(thisObj){
    var refclass=thisObj;
    //alert(refclass.value);
    if(refclass.value=='A' || refclass.value=='M'){
    	 window.document.getElementById("REFERENCENAME").value="";
    	 window.document.getElementById("ADDRESSENGLISH").value="";
    }
    if(refclass.value=='E') {
    window.document.getElementById("REFCATEGORY").value='E';
    window.document.getElementById("RECIEVEDBY").value='EM';
        }
    else
    {
    	window.document.getElementById("REFCATEGORY").value='L';
        window.document.getElementById("RECIEVEDBY").value='PO';
	  }  
  }

function hideShowTourRemarks(thisObj){
    var tourSel=thisObj;
    //alert(tourSel.value);
    if(tourSel.value=='T' || tourSel.value=='P') {
   	document.getElementById("tourRem").style.display="block";
        }
    else{
    	document.getElementById("tourRem").style.display="none";	
    }
  }
  
  function setRefClass(thisObj){
  
  	<%if(request.getAttribute("refBean")!=null){%>
  		window.document.getElementById("REFCLASS").value = '<c:out value="<%=refclass%>"/>';
  		return;
  	<%}%>
  	window.document.getElementById("REFCLASS").value = thisObj;
  	var REFNO = thisObj;
  	REFNO.value = '';
  	document.getElementById("REFCOUNTER").value="";
  	document.getElementById("REFCOUNTER").style.display="none";
  	if(thisObj.value <=0){
  		return;
  	}
  	var LOGINASROLENAME = '<c:out value="<%= sessionBean.getLOGINASROLENAME()%>"/>';
  	REFNO.value = LOGINASROLENAME + '/' +thisObj.value + '/';
  	var refclass=thisObj;
  	var tenureid='<c:out value="<%= sessionBean.getTENUREID()%>"/>';
  	if(refclass.length>0){
  		TrnReferenceDAO.getClassCounter(tenureid,refclass,getRefClassCounter);
  	}
  	window.document.getElementById("REFCLASS").focus();
  	setRefCategoryE(window.document.getElementById("REFCLASS"));
  }

   
  function setRefNoPrefix(thisObj){
  	//alert(' thisObj : '+ thisObj.value);
 <%-- var eofficeflagdesc = <%= eofficeFlag%> --%>

  	<%if((request.getAttribute("refBean")!=null)  && (eofficeFlag=="eofficedata")){%>
  			//alert();
  			//document.getElementById("fetchEofficedet").disabled = "True";
			//document.getElementById("EOFFICEREFNO").disabled = "True";
			
  	<%}else if((request.getAttribute("refBean")!=null) && (eofficeFlag=="")){%>
		thisObj.value='<c:out value="<%=refclass%>"/>';
		return;
  	<%}%>
  	
  	var REFNO = window.document.getElementById("REFNO");
  	REFNO.value = '';
  	document.getElementById("REFCOUNTER").value="";
  	document.getElementById("REFCOUNTER").style.display="none";
  	if(thisObj.value.length <=0){
  		return;
  	}

  	var LOGINASROLENAME = '<c:out value="<%= sessionBean.getLOGINASROLENAME()%>"/>';
  	//	alert(' LOGINASROLENAME : '+ LOGINASROLENAME);    	
  	REFNO.value = LOGINASROLENAME + '/' +thisObj.value + '/';
  	
  	var refclass=thisObj.value;
  	var tenureid='<c:out value="<%= sessionBean.getTENUREID()%>"/>';
  	
  	if(refclass.length>0){
  		//alert("tenureid:"+tenureid+" and LOGINASROLENAME:"+LOGINASROLENAME+" and refclass:"+refclass);
  		TrnReferenceDAO.getClassCounter(tenureid,refclass,getRefClassCounter);
  	}
  	
  }
  
  function fillRemarks(){
	  var SUBJECTCODEA = window.document.getElementsByName("SUBJECTCODE")[1].value;
	  var complianceremarks = window.document.getElementById("COMPREMARKS").value;
	  if(SUBJECTCODEA=='TFR' && complianceremarks =='' ){
	  window.document.getElementById("COMPREMARKS").value = "Interim reply given stating that the letter "+
	   "has been forwarded to the concerned Dte for further necessary action as per Rules";
	  window.document.getElementById("COMPLIANCE").value= 'E';
	  }
	  
  }
  
  function getRefClassCounter(data){
  if(data!="0"){
  var REFNO = window.document.getElementById("REFNO").value;
  document.getElementById("REFCOUNTER").style.display="block";
  document.getElementById("REFCOUNTER").innerHTML="<font color='green'>Last # :"+data+"</font>";
  
  }
  
  }
  
function complianceDateChange(){
	window.document.getElementById("COMPDATE").value='<c:out value="<%=serverDate%>"/>';
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

function getBlankValue(thisObj){
	return (thisObj.className=='active')? thisObj.value:"";
	
}

function setSearchParamsBlank(){

	var REFNOFROM =  window.document.getElementById("REFNOFROM");
  	var REFNOTO =  window.document.getElementById("REFNOTO");
  	var INCOMINGDATEFROM =  window.document.getElementById("INCOMINGDATEFROM");
  	var INCOMINGDATETO =  window.document.getElementById("INCOMINGDATETO");
  	var REFERENCENAMESEARCH =  window.document.getElementById("REFERENCENAMESEARCH");
  	var SUBJECTSEARCH =  window.document.getElementById("SUBJECTSEARCH");
  	var REMARKSEARCH =  window.document.getElementById("REMARKSEARCH");
  	var COMMONSEARCHVALUE =  window.document.getElementById("COMMONSEARCHVALUE");
  	
  	REFNOFROM.value = getBlankValue(REFNOFROM);
  	REFNOTO.value = getBlankValue(REFNOTO);
  	INCOMINGDATEFROM.value = getBlankValue(INCOMINGDATEFROM);
  	INCOMINGDATETO.value = getBlankValue(INCOMINGDATETO);
  	REFERENCENAMESEARCH.value = getBlankValue(REFERENCENAMESEARCH);
  	SUBJECTSEARCH.value = getBlankValue(SUBJECTSEARCH);
  	REMARKSEARCH.value = getBlankValue(REMARKSEARCH);
  	COMMONSEARCHVALUE.value = getBlankValue(COMMONSEARCHVALUE);
}

function saveRemind()
{
    //alert("inside save1");
    var REMINDERREMARKS =   window.document.getElementById("REMINDERREMARKS").value;
    //alert("inside save2");
    var REMINDERFROM =   window.document.getElementById("REMINDERFROM").value;
    //alert("inside save3");
    var REMINDERDATE =   window.document.getElementById("REMINDERDATE").value;
    //alert("inside save4");
    var REMINDERSIGNEDBY =   window.document.getElementById("REMINDERSIGNEDBY").value;
    //alert("inside save5");
    var REMINDERSIGNEDON =   window.document.getElementById("REMINDERSIGNEDON").value;
    //alert("inside save6");
    var REMINDERTYPE =   window.document.getElementById("REMINDERTYPE").value;
    //alert("inside save7");
    
    var REFID = '<c:out value="<%=refBean.getREFID()%>"/>';
    var ROLEID = '<c:out value="<%=sessionBean.getLOGINASROLEID()%>"/>';
    var LOGINID = '<c:out value="<%=sessionBean.getLOGINID()%>"/>';
    //alert("inside save8");
    TrnReferenceDAO.saveReminder(REFID,REMINDERFROM,REMINDERTYPE,REMINDERSIGNEDBY,REMINDERSIGNEDON,REMINDERREMARKS,ROLEID,LOGINID,saveRem);
}


function saveRem(data)
{
 var msg;
  if(data=="0")
  {
    msg="Some Problem Occured in Saving Record.";
  }
  else
  {
    msg="Data Saved Successfully.";
  }   

 
  
 
  
        document.getElementById('CommonMsg').innerText=msg; 
       
        window.document.getElementById('updateDiv').style.display="block";
        
		window.document.getElementById('CommonDivInner').style.display="block";
		
		
		window.document.getElementById('CommonbtnOK').focus();
		
}

var searchString;
 function getRefNameDetail(){
  
  var refname = window.document.getElementById("REFERENCENAME").value;  
   var vipstatus = window.document.getElementById("VIPSTATUS").value;  
	if(refname.length==0){
		return;
	}
	
	var focusfield=document.activeElement.name;
	if(focusfield!="VIPSTATUS"){
		return;
	}
	
	TrnReferenceDAO.getRefDetail(refname,vipstatus, dataRefNameDetail);
	  }
 
   function dataRefNameDetail(data){
   //alert(data.length);
  if(data.length>0){
  	window.document.getElementById("divRefNameResult").style.display="block";
  }
  else {
 	window.document.getElementById("divRefNameResult").style.display="none";
  }
  var divRefNameResult = window.document.getElementById("divRefNameResult");
  
  var htmlText = '';
  
    htmlText  = ' <fieldset style="height:250px ;overflow:scroll;overflow-x: hidden;" ><legend>History</legend><table id="data" width="100%"><tbody id="refNameTd">';	
	htmlText += ' <tr><td align="left" colspan="1"><img border="0" src="images/error.gif" width="20" height="20" onclick="hideDivRef();"></td><td align="right" colspan="1"><b>Total Records</b></td> <td align="left" COLSPAN="8">&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)&nbsp;</td><td align="left" ><img border="0" src="images/error.gif" width="20" height="20" onclick="hideDivRef();"></img></td></tr>';
	htmlText += ' <tr><th align="center" ><nobr><b>Reference No.</b></nobr></th><th align="center" ><nobr><b>Incoming Date</b></nobr></th><th align="center" ><nobr><b>Letter Date</b><nobr></th><th align="center"><nobr><b>Received From</b><nobr></th><th align="center" ><nobr><b>Status</b><nobr></th><th align="center" ><nobr><b>State</b><nobr></th><th nowrap="nowrap"><b>Sub.</b></th><th nowrap="nowrap"><b>Subject</b></th><th align="center" ><nobr><b>Forwarded To</b><nobr></th><th align="center" ><nobr><b>Forwarded On</b><nobr></th><th align="center" ><nobr><b>Status</b><nobr></th></tr>';
	
	//alert(searchResult.innerHTML);
	//alert(data);
	
	var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop]; 
		//alert('refBean.REFNO '+ refBean[loop].REFNO);
		
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr class= "'+trclass+'" > <td > '+trnRefBean.REFNO+' </td> <td WIDTH="60"  align="left"> '+trnRefBean.INCOMINGDATE+' </td> <td WIDTH="60"  align="left"> '+trnRefBean.LETTERDATE+' </td> <td  align="left"> '+trnRefBean.REFERENCENAME+' </td> <td > '+trnRefBean.VIPSTATUS+' </td> <td  align="left"> '+trnRefBean.STATECODE+' </td><td  align="left"> '+trnRefBean.SUBJECTCODE+' </td><td align="left"> '+trnRefBean.SUBJECT+' </td><td align="left"> '+trnRefBean.MARKINGTO+' </td><td align="left"> '+trnRefBean.MARKINGDATE+' </td><td align="left"> '+trnRefBean.ACTION+' </td> </tr>';
		//htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick="maximizeRefData(refBean[loop]);"> <td> '+trnRefBean.REFNO+' </td><td> kkkkk </td> </tr>';
			
			//alert(' htmlText ' + htmlText);	
					
	}
	
	htmlText += '</table></fieldset>';
	//alert(htmlText);
	divRefNameResult.innerHTML = htmlText;
	//document.getElementById("focusTEXT").focus();
	
	
  
  }
  
  
 
function getSearchOnRefNo(thisObj){  
  	var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
  	var REFNOFROM =  window.document.getElementById("REFNOFROM");
  	var REFNOTO =  window.document.getElementById("REFNOTO");
  	var ISCONF =  window.document.getElementById("ISCONF").value;
  	//Check for atleast One field for search
  	if(    getBlankValue(REFNOFROM)=='' && getBlankValue(REFNOTO)=='' && getBlankValue(ISCONF)=='' ){
  		return false;
  	}
	TrnReferenceDAO.getSearchDataOnRefNo(ROLEID, getBlankValue(REFNOFROM), getBlankValue(REFNOTO), ISCONF , getSearchDataOnRefNo);  	
}
function getSearchOnRefNo1(){  
  	var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
  	var REFNOFROM =  window.document.getElementById("REFNOFROM");
  	var REFNOTO =  window.document.getElementById("REFNOTO");
  	var ISCONF =  window.document.getElementById("ISCONF").value;
  	//Check for atleast One field for search
  	if(    getBlankValue(REFNOFROM)=='' && getBlankValue(REFNOTO)=='' && getBlankValue(ISCONF)=='' ){
  		return false;
  	}
	TrnReferenceDAO.getSearchDataOnRefNo(ROLEID, getBlankValue(REFNOFROM), getBlankValue(REFNOTO), ISCONF , getSearchDataOnRefNo);  	
}
function getSearchDataOnRefNo(data){
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	//alert('data.length : '+data.length);
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;)  <img width="25" height="25" src="images/printer_new.png" onclick="PrintReport();" title="Print"></img></div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" id="sth00" style="border: solid 1px black;border-style: dotted">S.No.</th><th style="" id="sth20" nowrap="nowrap">&nbsp;</th><th align="center"  id="sth1" style="border: solid 1px black;border-style: dotted">Reference No.</th><th align="center" id="sth3" style="border: solid 1px black;border-style: dotted">Incoming Date</th><th align="center"  style="display: none; border: solid 1px black; border-style: dotted;" id="sth4" >Letter Date</th><th align="center"  style="display: none; border: solid 1px black; border-style: dotted; " id="sth5" nowrap="nowrap" >Received From</th><th align="center" style="display: none; border: solid 1px black; border-style: dotted; " id="sth6">Status</th><th align="center" id="sth7" style="display: none; border: solid 1px black; border-style: dotted; ">State</th><th align="center" id="sth8" style="display: none; border: solid 1px black; border-style: dotted; ">Sub.</th><th align="center" style="display: none; border: solid 1px black; border-style: dotted; " id="sth9">Subject</th><th align="center"  style="display: none; border: solid 1px black; border-style: dotted; " id="sth10" nowrap="nowrap">Forwarded To</th><th align="center"  style="display: none; border: solid 1px black; border-style: dotted; " id="sth11" nowrap="nowrap">Forwarded On</th><th align="center" style="display: none; border: solid 1px black; border-style: dotted; " id="sth12" nowrap="nowrap">Remarks</th><th style="display: none; border: solid 1px black; border-style: dotted; " id="sth15" nowrap="nowrap">File No.</th><th style="display: none; border: solid 1px black; border-style: dotted; " id="sth13" nowrap="nowrap">FM Mark To</th><th style="display: none; border: solid 1px black; border-style: dotted; " id="sth14" nowrap="nowrap">FM Mark On</th><th style="display: none; border: solid 1px black; border-style: dotted; " id="sth16" nowrap="nowrap">File Status1</th><th style="display: none; border: solid 1px black; border-style: dotted; " id="sth17" nowrap="nowrap">File Status2</th><th style="display: none; border: solid 1px black; border-style: dotted; " id="sth18" nowrap="nowrap">Return To</th><th style="display: none; border: solid 1px black; border-style: dotted; " id="sth19" nowrap="nowrap">Return On</th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	
	//alert(searchResult.innerHTML);
	//alert(data);
	
	var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop]; 
		//alert('refBean.REFNO '+ refBean[loop].REFNO);
		var attachment = (trnRefBean.ISATTACHMENT == "1"? '<img border="0" alt="Image Gallery" src="${pageContext.request.contextPath}/images/stn.gif" onclick="popupGallery(\''+trnRefBean.refidencr+'\',\''+trnRefBean.refidencr+'\');">': "");
		//alert(attachment);
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;"><td align="center" name="std00" id="std00" style="border-bottom: dotted; border-width: 1px; border-color: gray;"> '+(loop+1)+' </td><td  id="std20" name="std20" style=" border-bottom: dotted; border-width: 1px; border-color: gray;"> '+attachment+'&nbsp; </td> <td name="std1" id="std1" style="border-bottom: dotted; border-width: 1px; border-color: gray;" onclick = "maximizeResult('+trnRefBean.REFID+');"> '+trnRefBean.REFNO+' </td> <td name="std3" id="std3" style="border-bottom: dotted; border-width: 1px; border-color: gray; " onclick = "maximizeResult('+trnRefBean.REFID+');"> '+trnRefBean.INCOMINGDATE+' </td> <td name="std4" id="std4" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.LETTERDATE+'&nbsp; </td> <td  id="std5" name="std5" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.REFERENCENAME+' &nbsp;</td> <td  id="std6" name="std6" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.VIPSTATUS+'&nbsp; </td> <td  id="std7" name="std7" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.STATECODE+' &nbsp;</td> <td  id="std8" name="std8" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.SUBJECTCODE+trnRefBean.ISBUDGET+'&nbsp; </td> <td  id="std9" name="std9" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.SUBJECT+'&nbsp; </td> <td  id="std10" name="std10" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.MARKINGTO+'&nbsp; </td> <td  id="std11" name="std11" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.MARKINGDATE+'&nbsp; </td> <td  id="std12" name="std12" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.STATUSREMARK+'&nbsp; </td> <td  id="std15" name="std15" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.FILENO+'&nbsp; </td> <td  id="std13" name="std13" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.IMARKINGTO+'&nbsp; </td>	<td  id="std14" name="std14" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.IMARKINGON+' &nbsp;</td> <td  id="std16" name="std16" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.FILESTATUS1+'&nbsp; </td>	<td  id="std17" name="std17" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.FILESTATUS2+'&nbsp; </td>	<td  id="std18" name="std18" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.DMARKINGTO+'&nbsp; </td> <td  id="std19" name="std19" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.DMARKINGDATE+'&nbsp; </td>  </tr>';
		//htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick="maximizeRefData(refBean[loop]);"> <td> '+trnRefBean.REFNO+' </td><td> kkkkk </td> </tr>';
			
			//alert(' htmlText ' + htmlText);	
					
	}
	
	htmlText += '</tbody>';
	htmlText += '</table>';
	//alert(htmlText);
	searchResult.innerHTML = htmlText;
	
	
//	highlightSearchTerms(searchString,false);
	
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

//function getSearch(thisObj,flag){

function getSearch(thisObj){
  	
  	var ROLEID ='<c:out value="<%= sessionBean.getLOGINASROLEID() %>"/>';
  	//alert( ' ROLEID : '+ ROLEID);
  	var REFNOFROM =  window.document.getElementById("REFNOFROM");
  	var REFNOTO =  window.document.getElementById("REFNOTO");
  	var INCOMINGDATEFROM =  window.document.getElementById("INCOMINGDATEFROM");
  	var INCOMINGDATETO =  window.document.getElementById("INCOMINGDATETO");
  	var REFERENCENAMESEARCH =  window.document.getElementById("REFERENCENAMESEARCH");
  	var SUBJECTSEARCH =  window.document.getElementById("SUBJECTSEARCH");
  	var REMARKSEARCH =  window.document.getElementById("REMARKSEARCH");
  	//alert(REMARKSEARCH);
  	var COMMONSEARCHVALUE =  window.document.getElementById("COMMONSEARCHVALUE");
  	var ISCONF =  window.document.getElementById("ISCONF").value;
  	
  	//Check for atleast One field for search
  	if(    getBlankValue(REFNOFROM)=='' && getBlankValue(REFNOTO)=='' && getBlankValue(INCOMINGDATEFROM)=='' && getBlankValue(INCOMINGDATETO)==''
  		   && getBlankValue(REFERENCENAMESEARCH)=='' && getBlankValue(SUBJECTSEARCH)=='' && getBlankValue(REMARKSEARCH)=='' && getBlankValue(COMMONSEARCHVALUE)==''
  	  ){
  	  	//nothing to search ...simply return 
  	  	//alert('haiIIIIIIIIIIIIIIIIIIIIIIIIi');
  		return false;
  	}
  	
 // 	alert(getBlankValue(REFERENCENAMESEARCH)+getBlankValue(SUBJECTSEARCH)+getBlankValue(COMMONSEARCHVALUE));
//	searchString=(getBlankValue(REFERENCENAMESEARCH)+' '+getBlankValue(SUBJECTSEARCH)+' '+getBlankValue(COMMONSEARCHVALUE)).Trim();
  	//alert('haii');
	//TrnReferenceDAO.getSearchData(ROLEID, getBlankValue(REFNOFROM), getBlankValue(REFNOTO), getBlankValue(INCOMINGDATEFROM), getBlankValue(INCOMINGDATETO),getBlankValue(REFERENCENAMESEARCH), getBlankValue(SUBJECTSEARCH), getBlankValue(REMARKSEARCH), getBlankValue(COMMONSEARCHVALUE),flag, getData);  	
	TrnReferenceDAO.getSearchData(ROLEID, getBlankValue(REFNOFROM), getBlankValue(REFNOTO), getBlankValue(INCOMINGDATEFROM), getBlankValue(INCOMINGDATETO),getBlankValue(REFERENCENAMESEARCH), getBlankValue(SUBJECTSEARCH), getBlankValue(REMARKSEARCH), getBlankValue(COMMONSEARCHVALUE), '0', ISCONF, getData);  	
	//TrnReferenceDAO.getSearchDataNew(getBlankValue(REFNOFROM), getBlankValue(REFNOTO), getBlankValue(INCOMINGDATEFROM), getBlankValue(INCOMINGDATETO),getBlankValue(REFERENCENAMESEARCH), getBlankValue(SUBJECTSEARCH), getBlankValue(REMARKSEARCH), getBlankValue(COMMONSEARCHVALUE), getData);  	
	//TrnReferenceDAO.getSearchDataNew(REFNOFROM.value, REFNOTO.value, INCOMINGDATEFROM.value, INCOMINGDATETO.value,REFERENCENAMESEARCH.value, SUBJECTSEARCH.value, REMARKSEARCH.value,  getData);  	
	
  }
  
 
  
  function getData(data){
	
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	//alert('data.length : '+data.length);
	htmlText = '<div><b>Total Records</b>&nbsp;(&nbsp;<b>'+data.length+'</b>&nbsp;) <img width="25" height="25" src="images/printer_new.png" onclick="PrintReport();" title="Print"></img></div>';
	htmlText += "<TABLE class='tablesorter' id='sorttable' width='100%'>";	
	htmlText += '<thead><tr><th align="center" id="sth00" style="border: solid 1px black;border-style: dotted">SNo.</th><th style="" id="sth20" nowrap="nowrap">&nbsp;</th><th align="center"  id="sth1" style="border: solid 1px black;border-style: dotted">Reference No.</th><th align="center" id="sth3" style="border: solid 1px black;border-style: dotted">Incoming Date</th><th align="center"  style="display:none;" id="sth4" nowrap="nowrap" style="border: solid 1px black;border-style: dotted">Letter Date</th><th align="center"  style="display:none" id="sth5" nowrap="nowrap" style="border: solid 1px black;border-style: dotted">Received From</th><th align="center"  style="display:none" id="sth6" nowrap="nowrap" style="border: solid 1px black;border-style: dotted">Status</th><th align="center" style="display:none" id="sth7" nowrap="nowrap" style="border: solid 1px black;border-style: dotted">State</th><th align="center"  style="display:none" id="sth8" nowrap="nowrap" style="border: solid 1px black;border-style: dotted">Sub.</th><th align="center" style="display:none" id="sth9" nowrap="nowrap">Subject</th><th align="center"  style="display:none" id="sth10" nowrap="nowrap">Forwarded To</th><th align="center"  style="display:none" id="sth11" nowrap="nowrap">Forwarded On</th><th align="center"  style="display:none" id="sth12" nowrap="nowrap">Remarks</th><th style="display:none" id="sth15" nowrap="nowrap">File No.</th><th style="display:none" id="sth13" nowrap="nowrap">FM Mark To</th><th style="display:none" id="sth14" nowrap="nowrap">FM Mark On</th><th style="display:none" id="sth16" nowrap="nowrap">File Status1</th><th style="display:none" id="sth17" nowrap="nowrap">File Status2</th><th style="display:none" id="sth18" nowrap="nowrap">Return To</th><th style="display:none" id="sth19" nowrap="nowrap">Return On</th></tr></thead>';
	htmlText+="<TBODY id='content'>";
	
	//alert(searchResult.innerHTML);
	//alert(data);
	
	var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop]; 
		//alert('refBean.REFNO '+ refBean[loop].REFNO);
		var attachment = (trnRefBean.ISATTACHMENT == "1"? '<img border="0" alt="Image Gallery" src="${pageContext.request.contextPath}/images/stn.gif" onclick="popupGallery(\''+trnRefBean.refidencr+'\',\''+trnRefBean.refidencr+'\');">': "");
		var REFNO = (data[loop].URGENCY == 'C' || data[loop].URGENCY == 'S' || data[loop].URGENCY == 'T')? '<font color="#FF0000">'+trnRefBean.REFNO+'*</font>': trnRefBean.REFNO;
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;"><td align="center" id="std00"  name="std00" style="border-bottom: dotted; border-width: 1px; border-color: gray;"> '+(loop+1)+' </td><td  id="std20" name="std20" style=" border-bottom: dotted; border-width: 1px; border-color: gray;"> '+attachment+'&nbsp; </td> <td  id="std1" name="std1" style="border-bottom: dotted; border-width: 1px; border-color: gray;" onclick = "maximizeResult('+trnRefBean.REFID+');"> '+REFNO+' </td> <td  id="std3" name="std3"  style="border-bottom: dotted; border-width: 1px; border-color: gray; " onclick = "maximizeResult('+trnRefBean.REFID+');"> '+trnRefBean.INCOMINGDATE+' </td> <td  id="std4" name="std4"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.LETTERDATE+'&nbsp; </td> <td  id="std5" name="std5" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.REFERENCENAME+'&nbsp; </td> <td name="std6"  id="std6" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.VIPSTATUS+'&nbsp; </td> <td  id="std7" name="std7"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.STATECODE+'&nbsp; </td> <td  id="std8" name="std8"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.SUBJECTCODE+trnRefBean.ISBUDGET+'&nbsp; </td> <td  id="std9" name="std9"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.SUBJECT+'&nbsp; </td> <td  id="std10" name="std10"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.MARKINGTO+'&nbsp; </td> <td  id="std11" name="std11"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.MARKINGDATE+'&nbsp; </td> <td name="std12"  id="std12" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.STATUSREMARK+'&nbsp; </td> <td  id="std15" name="std15" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.FILENO+'&nbsp; </td> <td  id="std13" name="std13"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.IMARKINGTO+'&nbsp; </td>	<td  id="std14" name="std14" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.IMARKINGON+'&nbsp; </td> <td  id="std16" name="std16"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.FILESTATUS1+'&nbsp; </td>	<td  id="std17" name="std17"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.FILESTATUS2+'&nbsp; </td>	<td  id="std18" name="std18" style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.DMARKINGTO+'&nbsp; </td> <td  id="std19" name="std19"  style="display:none; border-bottom: dotted; border-width: 1px; border-color: gray;"> '+trnRefBean.DMARKINGDATE+'&nbsp; </td>  </tr>';
		//htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick="maximizeRefData(refBean[loop]);"> <td> '+trnRefBean.REFNO+' </td><td> kkkkk </td> </tr>';
			
			//alert(' htmlText ' + htmlText);	
					
	}
	
	htmlText += '</tbody>';
	htmlText += '</table>';
	//alert(htmlText);
	searchResult.innerHTML = htmlText;
	
//	highlightSearchTerms(searchString,false);
	
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

function PrintReport(){
var inDateFrom = document.getElementById('INCOMINGDATEFROM').value == 'In. Date from'? '': document.getElementById('INCOMINGDATEFROM').value;
var inDateTo = document.getElementById('INCOMINGDATETO').value == 'In. Date to'? '': document.getElementById('INCOMINGDATETO').value;

var refNoFrom = document.getElementById('REFNOFROM').value == 'Ref. No from'? '': document.getElementById('REFNOFROM').value;
var refNoTo = document.getElementById('REFNOTO').value == 'Ref. No to'? '': document.getElementById('REFNOTO').value;

var refName = document.getElementById('REFERENCENAMESEARCH').value == 'Received from'? '': document.getElementById('REFERENCENAMESEARCH').value;
var subject = document.getElementById('SUBJECTSEARCH').value == 'Subject'? '': document.getElementById('SUBJECTSEARCH').value;
var forTo = document.getElementById('COMMONSEARCHVALUE').value == 'FORWARD TO'? '': document.getElementById('COMMONSEARCHVALUE').value;
var remarks = document.getElementById('REMARKSEARCH').value == 'REMARKS'? '': document.getElementById('REMARKSEARCH').value;

	var param1= document.getElementById('searchResult').value;
	var param2 = inDateFrom +' - '+ inDateTo;
	var param3 = refNoFrom +' - '+ refNoTo;
	var param4 = refName +' - '+ subject +' - '+ forTo +' - '+ remarks;
	var url = 'PopUpPrintReport_RefAdd.jsp?flag=y&hideftr=Y&param1='+param1+'&param2='+param2+'&param3='+param3+'&param4='+param4;
	window.open(url);
}

function popupGallery(refId, flg)
{
	var url="ImageGallery.jsp?refId="+refId+"&flg="+flg+"&type=R";
	window.open(url,"","toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,left=0,top=0,scrollbars=1,resizable=1");
}

function ShowSearch(obj){

if(document.getElementById("content")!=null){
		var idno = document.getElementById("content").getElementsByTagName('tr').length;
		//alert(idno);
		
		if(obj.style.display=='none'){
		
			document.getElementById("sth1").style.display='inline';
			//document.getElementById("sth2").style.display='inline';
			document.getElementById("sth3").style.display='inline';
			document.getElementById("sth3").style.width='15px';
			document.getElementById("sth4").style.display='inline';
			document.getElementById("sth5").style.display='inline';
			document.getElementById("sth5").style.width='200px';
			document.getElementById("sth6").style.display='inline';
			//document.getElementById("sth6").style.width='200px';
			document.getElementById("sth7").style.display='inline';
			document.getElementById("sth8").style.display='inline';
			document.getElementById("sth9").style.display='inline';
			//document.getElementById("sth9").style.width='200px';
			document.getElementById("sth10").style.display='inline';
			//document.getElementById("sth10").style.width='100px';
			document.getElementById("sth11").style.display='inline';
			document.getElementById("sth12").style.display='inline';
			document.getElementById("sth13").style.display='inline';
			document.getElementById("sth14").style.display='inline';
			document.getElementById("sth15").style.display='inline';
			document.getElementById("sth16").style.display='inline';
			document.getElementById("sth17").style.display='inline';
			document.getElementById("sth18").style.display='inline';
			document.getElementById("sth19").style.display='inline';
//			document.getElementById("sth20").style.display='inline';
			//document.getElementById("sth8").style.display='inline';
			$("#sorttable").tableFilter();
			
		for(var i=0;i<idno;i++){
		
			document.getElementsByName("std1")[i].style.display='inline';
			//document.getElementsByName("std2")[i].style.display='inline';
			document.getElementsByName("std3")[i].style.display='inline';
			document.getElementsByName("std3")[i].style.width='800px';
			document.getElementsByName("std4")[i].style.display='inline';
			document.getElementsByName("std4")[i].style.width='800px';
			document.getElementsByName("std5")[i].style.display='inline';
			document.getElementsByName("std5")[i].style.width='800px';
			document.getElementsByName("std6")[i].style.display='inline';
			document.getElementsByName("std6")[i].style.width='800px';
			document.getElementsByName("std7")[i].style.display='inline';
			document.getElementsByName("std7")[i].style.width='800px';
			document.getElementsByName("std8")[i].style.display='inline';
			document.getElementsByName("std8")[i].style.width='800px';
			document.getElementsByName("std9")[i].style.display='inline';
			document.getElementsByName("std9")[i].style.width='800px';
			document.getElementsByName("std10")[i].style.display='inline';
			document.getElementsByName("std10")[i].style.width='800px';
			document.getElementsByName("std11")[i].style.display='inline';
			document.getElementsByName("std11")[i].style.width='800px';
			document.getElementsByName("std12")[i].style.display='inline';
			document.getElementsByName("std12")[i].style.width='800px';
			document.getElementsByName("std13")[i].style.display='inline';
			document.getElementsByName("std13")[i].style.width='800px';
			document.getElementsByName("std14")[i].style.display='inline';
			document.getElementsByName("std14")[i].style.width='800px';
			document.getElementsByName("std15")[i].style.display='inline';
			document.getElementsByName("std16")[i].style.display='inline';
			document.getElementsByName("std17")[i].style.display='inline';
			document.getElementsByName("std18")[i].style.display='inline';
			document.getElementsByName("std19")[i].style.display='inline';
//			document.getElementsByName("std20")[i].style.display='inline';
			//document.getElementsByName("std8")[i].style.display='inline';
			}
			
		
			
		
	}else{
			//document.getElementById("sth1").style.display='inline';
			//document.getElementById("sth2").style.display='none';
			document.getElementById("sth3").style.display='inline';
			document.getElementById("sth3").style.width='15px';
			document.getElementById("sth4").style.display='none';
			document.getElementById("sth5").style.display='none';
			document.getElementById("sth5").style.width='200px';
			document.getElementById("sth6").style.display='none';
		//	document.getElementById("sth6").style.width='200px';
			document.getElementById("sth7").style.display='none';
			document.getElementById("sth8").style.display='none';
			document.getElementById("sth9").style.display='none';
//			document.getElementById("sth9").style.width='200px';
			document.getElementById("sth10").style.display='none';
		//	document.getElementById("sth10").style.width='100px';
			document.getElementById("sth11").style.display='none';
			document.getElementById("sth12").style.display='none';
			document.getElementById("sth13").style.display='none';
			document.getElementById("sth14").style.display='none';
			document.getElementById("sth15").style.display='none';
			document.getElementById("sth16").style.display='none';
			document.getElementById("sth17").style.display='none';
			document.getElementById("sth18").style.display='none';
			document.getElementById("sth19").style.display='none';
//			document.getElementById("sth20").style.display='none';
			//document.getElementById("sth8").style.display='none';
			// Hide Filter Row
			var y = window.document.getElementById("sorttable");
			//var trtag = y.getElementsByTagName("TR");
			//trtag[1].style.display = "none";
			y.deleteRow(1);
			
			for(i=0;i<idno;i++){
			//document.getElementsByName("std1")[i].style.display='inline';
			//document.getElementsByName("std2")[i].style.display='none';
			document.getElementsByName("std3")[i].style.display='inline';
			document.getElementsByName("std3")[i].style.width='15px';
			document.getElementsByName("std4")[i].style.display='none';
			document.getElementsByName("std5")[i].style.display='none';
			document.getElementsByName("std5")[i].style.width='200px';
			document.getElementsByName("std6")[i].style.display='none';
			document.getElementsByName("std6")[i].style.width='200px';
			document.getElementsByName("std7")[i].style.display='none';
			document.getElementsByName("std8")[i].style.display='none';
			document.getElementsByName("std9")[i].style.display='none';
			document.getElementsByName("std9")[i].style.width='200px';
			document.getElementsByName("std10")[i].style.display='none';
			document.getElementsByName("std11")[i].style.display='none';
			document.getElementsByName("std12")[i].style.display='none';
			document.getElementsByName("std13")[i].style.display='none';
			document.getElementsByName("std14")[i].style.display='none';
			document.getElementsByName("std15")[i].style.display='none';
			document.getElementsByName("std16")[i].style.display='none';
			document.getElementsByName("std17")[i].style.display='none';
			document.getElementsByName("std18")[i].style.display='none';
			document.getElementsByName("std19")[i].style.display='none';
//			document.getElementsByName("std20")[i].style.display='none';
			//document.getElementsByName("std8")[i].style.display='none';
			}
	
	}
}

}  

function maximizeResult(REFID){
	//alert('REFID 11'+REFID);
	
	window.document.frmOriginated.MaximizeOnAttach.value = 0;
	window.document.frmOriginated.REFID.value = REFID;

	setSearchParamsBlank();
	
	submitForm('GO');
	setRefClass('<c:out value="<%= rClass %>"/>');
}
function maximizeResultForEoffice(){
	
	
	var valEofficeNo = window.document.frmOriginated.EOFFICEREFNO.value;
	
	submitForm('GOEOFFFICE');
	
	//window.document.getElementsByName("SUBJECT").disabled
	
	<%-- //setRefClass('<%= rClass %>'); --%>
}


function maximizeRefData(refBean){
	window.document.frmOriginated.REFID.value = refBean.REFID;
	//window.document.frmOriginated.TENUREID.value = refBean.TENUREID;
	window.document.frmOriginated.REFNO.value = refBean.REFNO;
	//window.document.frmOriginated.ROLEID.value = refBean.ROLEID;
	window.document.frmOriginated.REFCLASS.value = refBean.REFCLASS;
	//window.document.frmOriginated.REFCOUNT.value = refBean.REFCOUNT;
	window.document.frmOriginated.INCOMINGDATE.value = refBean.INCOMINGDATE;
	window.document.frmOriginated.REFERENCENAME.value = refBean.REFERENCENAME;
	window.document.frmOriginated.LETTERDATE.value = refBean.LETTERDATE;
	window.document.frmOriginated.VIPSTATUS.value = refBean.VIPSTATUS;
	window.document.frmOriginated.STATECODE.value = refBean.STATECODE;
	window.document.frmOriginated.VIPPARTY.value = refBean.VIPPARTY;
	window.document.frmOriginated.ACKDATE.value = refBean.ACKDATE;
	window.document.frmOriginated.ACKBY.value = refBean.ACKBY;
	window.document.frmOriginated.REFCATEGORY.value = refBean.REFCATEGORY;
	window.document.frmOriginated.URGENCY.value = refBean.URGENCY;
	window.document.frmOriginated.LINKREFID.value = refBean.LINKREFID;
	//window.document.frmOriginated.NOTECREATOR.value = refBean.NOTECREATOR;
	//window.document.frmOriginated.SECURITYGRADING.value = refBean.SECURITYGRADING;
	window.document.frmOriginated.SIGNEDBY.value = refBean.SIGNEDBY;
	window.document.frmOriginated.SIGNEDON.value = refBean.SIGNEDON;
	window.document.frmOriginated.REMARKS.value = refBean.REMARKS;
	window.document.frmOriginated.ODSPLACE.value = refBean.ODSPLACE;
	window.document.frmOriginated.ODSRAILWAY.value = refBean.ODSRAILWAY;
	window.document.frmOriginated.ODSDATE.value = refBean.ODSDATE;
	//window.document.frmOriginated.LOGINID.value = refBean.LOGINID;
	//window.document.frmOriginated.CHANGEDATE.value = refBean.CHANGEDATE;
	window.document.frmOriginated.ISBUDGET.value = refBean.ISBUDGET;
	window.document.frmOriginated.LANGUAGE.value = refBean.LANGUAGE;
	
	setMarkingValues(refBean.markingBeanList);
	//thisObj.style.bgcolor = 'red';
	
	//window.document.frmOriginated.REFID.value = REFID;
	//submitForm('GO');
}  

function setMarkingValues(markingBeanList){
	var SUBJECT = window.document.getElementsByName("SUBJECT");
	var diffOfRow = eval(markingBeanList.length)-eval(SUBJECT.length -1);
	
	if(diffOfRow > 0){
	
	}

	for(var i =0; i < diffOfRow ; i++){
		addrow(window.document.getElementById('baserow'));
	}
	
	for(i =0; i < markingBeanList.length ; i++){
		SUBJECT[i+1].value = markingBeanList[i].SUBJECT;
	}
}
  

function setReturnBoxAction(refId, index, returnMarkFrom, targetDate, subjectCode, subject){

	//alert(' refId : '+ refId);
	//alert(' index: '+ index);
	var MARKFROM   = '<c:out value="<%= sessionBean.getLOGINASROLEID()%>"/>';
	var MARKTO   = window.document.getElementsByName("RETURNMARKTO")[index];
	var RETURNACTION   = window.document.getElementsByName("RETURNACTION")[index].value;
	var RETURNMARKINGREMARK   = window.document.getElementsByName("RETURNMARKINGREMARK")[index].value;
	alert(' returnMarkFrom: '+ returnMarkFrom);
	//alert(' MARKFROM: '+ MARKFROM);
	
	//alert(' MARKTO: '+ MARKTO.value);
	if(!chkblank(MARKTO)){
		return;
	}

	//alert(' RETURNACTION: '+ RETURNACTION);
	//alert(' RETURNMARKINGREMARK: '+ RETURNMARKINGREMARK);
	//alert(' targetDate: '+ targetDate);
	//alert(' subjectCode: '+ subjectCode);
	//alert(' subject: '+ subject);
	
	TrnReferenceDAO.setReturnBoxAction(refId, returnMarkFrom, MARKFROM, MARKTO.value,RETURNMARKINGREMARK,  targetDate, subjectCode, subject, RETURNACTION, getReturnBoxData);
}  

function getReturnBoxData(data){
	//alert(data);
	window.location.reload();


}

  
  $(document).ready(function(){
	  
	
	  
	  
	  if(document.getElementById("REFCATEGORY").value=='T'||document.getElementById("REFCATEGORY").value=='P') {
		   	document.getElementById("tourRem").style.display="block";
	  }
    $("#MainTabDiv").tabs({ fx: { slide: true, fade: true, speed: 'normal' }  });
    $( "#tabs" ).tabs();
    
    $("#REFERENCENAME").autocomplete("getOldVIPName.jsp", {scroll:false});
    $('#REFERENCENAME').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			//alert("--------------"+dataarr.length);
 			//alert(' dataarr 5 '+ dataarr[4]);
			  window.document.getElementById('REFERENCENAME').value = dataarr[0];
			window.document.getElementById('VIPSTATUS').value = dataarr[1];
			window.document.getElementById('STATECODE').value = dataarr[2];
			window.document.getElementById('VIPPARTY').value = dataarr[3]; 
			//new code
			window.document.getElementById('VIPID').value = dataarr[4];
			window.document.getElementById('VIPADDRESS').value = dataarr[5];
			window.document.getElementById('ADDRESSENGLISH').value = dataarr[8];
			window.document.getElementById('VIPSTATUSDESC').value = dataarr[6]; 
		}
	});
    
   
  });
  

  
  function submitForm(btnval){
		window.document.getElementById("btnClick").value = btnval;
		var cnt1 = 1;
		//clear the search fields of inactive values
		setSearchParamsBlank();
		
		if(btnval=='GO'){
			window.document.frmOriginated.submit();
			
		}	
		if(btnval=='CLEAR'){
			window.document.frmOriginated.submit();
		}	
		if(btnval=='EMAIL'){
			window.document.frmOriginated.submit();
		}	
		if(btnval=='GOEOFFFICE'){
			window.document.frmOriginated.submit();
		}	
		if(btnval=='SAVE'){
			var LOGINID = '<c:out value="<%= sessionBean.getLOGINID()%>"/>'; 
			var refidvalue=window.document.getElementById("REFID"); 
			if(window.document.getElementById("no_file_flag").value==18)
			{
			//alert();
			if(window.document.getElementById("ACKBY").value=="")
				{
					window.document.getElementById("ACKBY").value='1083';
					window.document.getElementById("ACKDATE").value=window.document.getElementById("COMPDATE").value;
				}
			}
			
			 var subjectCodeValue = window.document.getElementsByName("SUBJECTCODE")[0].value;
			 if (subjectCodeValue=='NOM' || subjectCodeValue=='COM'){
				 window.document.getElementById("ACKBY").value='1330';
			 } 
			
			 if (window.document.frmOriginated.LETTERDATE.value.length<=0)
				 {
				 	window.document.frmOriginated.LETTERDATE.focus();
				 	return false;
				 }
			if (window.document.getElementById("REFCLASS").value=='A' || window.document.getElementById("REFCLASS").value=='M')
				{
				if (LOGINID!='nishant') {
				if (window.document.frmOriginated.STATECODE.value.length<=0)
			 	{
			 		window.document.frmOriginated.STATECODE.focus();
			 		return false;
			 	} }
				}
			//New Code aboce
			if( chkblank(window.document.frmOriginated.REFCATEGORY)  
				&& chkblank(window.document.frmOriginated.LANGUAGE) && chkblank(window.document.frmOriginated.URGENCY)
				&& chkblank(window.document.frmOriginated.RECIEVEDBY) //&& chkblank(window.document.frmOriginated.SECURITYGRADING) 
				&& chkblank(window.document.frmOriginated.INCOMINGDATE) && chkblank(window.document.frmOriginated.LETTERDATE)
				&& chkblank(window.document.frmOriginated.ISBUDGET) && chkblank(window.document.frmOriginated.REFERENCENAME)
				&& (window.document.getElementById("REFCLASS").value=='A' || window.document.getElementById("REFCLASS").value=='M')?(chkblank(window.document.frmOriginated.SALUTATION)&&chkblank(window.document.frmOriginated.VIPSTATUS)) :true
				&& (window.document.getElementById("REFCLASS").value=='A' || window.document.getElementById("REFCLASS").value=='M')?chkblank(window.document.frmOriginated.STATECODE) :true		
				) {
				
				
			var refClass = window.document.getElementById("REFCLASS").value;
			
			window.document.frmOriginated.cntimg.value = eval(document.getElementsByName("uploadImage").length);
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
			
			if(refClass.length > 0) {
				var MARKINGTO = window.document.getElementsByName("MARKINGTO");
				var SUBJECTCODE = window.document.getElementsByName("SUBJECTCODE");
				
				var keyword1 = window.document.getElementsByName("keyword1");
				var keyword2 = window.document.getElementsByName("keyword2");
				var keyword3 = window.document.getElementsByName("keyword3");
				
				var k1 = window.document.getElementsByName("k1");
				var k2 = window.document.getElementsByName("k2");
				var k3 = window.document.getElementsByName("k3");
				
				var INOUT= window.document.getElementById("INOUT");
				window.document.frmOriginated.btnSAVE.disabled = true;
				var flag = true;
				//marking is madatory Only for Inward Reference
				if(INOUT.value == 'I'){
					for (var loop=1; loop<MARKINGTO.length; loop++){
						if(chkblank(SUBJECTCODE[loop])&&chkblank(MARKINGTO[loop])){
						}else{
							flag = false;	
							window.document.frmOriginated.btnSAVE.disabled = false;
						}
					}
				}else if(INOUT.value == 'O'){
					for (var loop=1; loop<SUBJECTCODE.length; loop++){
						if(chkblank(SUBJECTCODE[loop])){
						}else{
							flag = false;	
							window.document.frmOriginated.btnSAVE.disabled = false;
						}
					}
				}
				
				for (var loop=1; loop<keyword1.length; loop++){
						if(keyword1[loop].value==k1[loop].value && keyword2[loop].value==k2[loop].value && keyword3[loop].value==k3[loop].value ){
						}else{
						alert("keywords should be selected from list only");
							flag = false;	
							window.document.frmOriginated.btnSAVE.disabled = false;
						}
					}
				
				if(flag){
					//if(window.document.frmOriginated.ACKBY.value != "" && window.document.frmOriginated.ACKDATE.value != "") {
					//	showMailDiv();				
					//}else {
						window.document.getElementById("frmOriginated").submit();
						window.document.frmOriginated.btnSAVE.disabled = true;
						//alert();
					//}
				}
				
			}else { alert('Please select refclass first...'); window.document.frmOriginated.REFCLASS.focus();}
		}
		}
	}


function submitPage(val) {
	window.document.getElementById("btnClick").value = val;
	if(val=='SAVE'){
		window.document.frmOriginated.submit();
	}	
	if(val=='EMAIL'){
		window.document.frmOriginated.submit();
	}	
}

function setMaxRowsOfMarking(thisObj){
  	var btnADD = window.document.getElementById("btnADD");
  	var indValueSub = window.document.getElementsByName("SUBJECTCODE").length-1;
  	if(thisObj.value =='Y'){
  		window.document.getElementById("listMainBudget").style.display = 'block';
  	//	btnADD.disabled = true;
  	}else if(thisObj.value =='N'){
  		window.document.getElementById("listMainBudget").style.display = 'none';
  	//	btnADD.disabled = false;
  	}
  }
  
  function setTargetDate(oldDate, thisObj){

//	  alert('oldDate '+oldDate);
//	  alert('thisObj '+thisObj);
	 var index =  getIndex(thisObj); 
	 //alert('index '+index);
	 // alert('noOfDaysToAdd '+thisObj.value);
	 var TARGETDATE = window.document.frmOriginated.TARGETDATE;
	  
	   if(thisObj.value == '' || thisObj.value ==' '){
	   	 TARGETDATE[index].value = '';
	  	 return;
	  }
	  
	  var newDate  = addDaysToDate(oldDate, thisObj.value);
	  TARGETDATE[index].value = newDate;
  
  }
  
  
  function showDiv(){
	<%
		if (msg.length() > 0 )
		{%>
	//	alert("Hiii");
		window.document.getElementById('updateDiv').style.display="block";
		window.document.getElementById('updateDivInner').style.display="block";
		window.document.getElementById('btnOK').focus();
		//setRefCategoryE(window.document.getElementById('REFCLASS'));
	<%	}%>
	
}

function hideDiv(){
	window.document.getElementById('updateDiv').style.display="none";
	window.document.getElementById('updateDivInner').style.display="none";
}
function CommonhideDiv(){
	window.document.getElementById('updateDiv').style.display="none";
	window.document.getElementById('CommonDivInner').style.display="none";
}

function showMailDiv()
{
	window.document.getElementById('updateDiv').style.display="block";
	window.document.getElementById('MailDivInner').style.display="block";
	window.document.getElementById('updateDiv').focus();
} 
 
function hideMailDiv()
{
	window.document.getElementById('updateDiv').style.display="none";
	window.document.getElementById('MailDivInner').style.display="none";
	//document.getElementById('passwordDiv').focus();
}



function callMe(){
	hideDiv();
	document.getElementById("MainReferencesLink").click()
}




<%-- function callMeAttach() {
	hideDiv();
    longfunctionfirst(shortfunctionsecond);
}

function longfunctionfirst(callback) {
    setTimeout(function() {
    	
    	var x= "<%= msg%>";
    	var leng = x.length;
    	var refid = x.substr(leng-6,leng);
    	window.document.frmOriginated.REFID.value = refid;

    	setSearchParamsBlank();
    	
    	submitForm('GO');
    	setRefClass('<%= rClass %>');
        if(typeof callback == 'function')
            callback();
    }, 3000);
}

function shortfunctionsecond() {
    setTimeout('document.getElementById("MainAttachmentLink").click();', 200);
}

 --%>


function callMeAttach(){
	hideDiv();
	var x= "<c:out value='<%= msg%>'/>";
	var leng = x.length;
	var arrayString = x.split("/-")
	var refid = arrayString[1];
	
	//maximizeResultAttach(refid)
	//MaximizeOnAttach
	//alert(refid);
	var attachpage = window.document.getElementById("MaximizeOnAttach");
	attachpage.value = 1;
	//window.document.frmOriginated.MaximizeOnAttach.value = 1;
	var refidvalue =  window.document.getElementById("REFID");
	refidvalue.value = refid;
	//window.document.frmOriginated.REFID.value = refid;

	setSearchParamsBlank();
	
	submitForm('GO');
	setRefClass('<c:out value="<%= rClass %>"/>');
	
	
} 

function CommoncallMe(){
	CommonhideDiv();
}


 function bodyOnload(){
 	window.document.getElementById("REFCLASS").focus();
 	//setting the inward/outward type
 //	setInOutLabels(window.document.getElementById('REFCLASS'));
 
 	if("<c:out value='<%= eofficeFlag%>'/>"=="eofficedata"){
		  document.getElementById("fetchEofficedet").disabled = "True";
			document.getElementById("EOFFICEREFNO").disabled = "True";
		  } 
 	
	if("<c:out value='<%= attachFlag%>'/>"=="1"){
		document.getElementById("MainAttachmentLink").click();
		  } 
 	
 	<%if(request.getAttribute("refBean")!=null && eofficeFlag.equalsIgnoreCase("")){%>
 		document.getElementById("fetchEofficedet").disabled = "True";
		document.getElementById("EOFFICEREFNO").disabled = "True";
  		<%}%>
	/////setting the default target date
 	var newTargetDate = addDaysToDate('<c:out value="<%= serverDate%>"/>', '<c:out value="<%=defaultTargetDays%>"/>');
 	window.document.getElementsByName("TARGETDATE")[0].value = newTargetDate;
 	
 	if(window.document.getElementsByName("TARGETDATE")[1].value.length==0){
 		window.document.getElementsByName("TARGETDATE")[1].value = newTargetDate;
 	} 	
	/////

 	////code for handling search paramas
	label2value();	
	
	setSessionSeachParams('REFNOFROM', '<c:out value="<%=sBean.getField1()%>"/>');
	setSessionSeachParams('REFNOTO', '<c:out value="<%=sBean.getField2()%>"/>');
//	setSessionSeachParams("INCOMINGDATEFROM", "<c:out value='<%=sBean.getField3().equalsIgnoreCase("")?sessionBean.getTENURESTARTDATE():sBean.getField3()%>'/>");
	setSessionSeachParams('INCOMINGDATEFROM', '<c:out value="<%=sBean.getField3()%>"/>');
//	setSessionSeachParams("INCOMINGDATETO", "<c:out value='<%=sBean.getField4().equalsIgnoreCase("")?serverDate:sBean.getField4()%>'/>");
	setSessionSeachParams('INCOMINGDATETO', '<c:out value="<%=sBean.getField4()%>"/>');
	setSessionSeachParams('REFERENCENAMESEARCH', '<c:out value="<%=sBean.getField5()%>"/>');
	setSessionSeachParams('SUBJECTSEARCH', '<c:out value="<%=sBean.getField6()%>"/>');
	setSessionSeachParams('REMARKSEARCH', '<c:out value="<%=sBean.getField7()%>"/>');
	setSessionSeachParams('COMMONSEARCHVALUE', '<c:out value="<%=sBean.getField8()%>"/>');

	//call getSearch to show prvious search
 	getSearch();
 	////search param code ends here
 	
 
 	if('<c:out value="<%= refBean.getISBUDGET()%>"/>' == 'Y'){
 		window.document.getElementById("listMainBudget").style.display ='block';
 	}else{
 		window.document.getElementById("listMainBudget").style.display ='none';
 	}
 	if('<c:out value="<%= sessionBean.getISREPLY()%>"/>' == '1'){
 		window.document.getElementById("listfileReply").style.display ='block';
 	}else{
 		window.document.getElementById("listfileReply").style.display ='none';
 	}
 	
 	var indValueBdg = 1;
 	var indValueSub = 1;
 
 	showDiv();
 	
 	//Showing Additional tags if Any
 	<% if(refBean.getODSPLACE().length() > 0 || refBean.getODSDATE().length() > 0 
 	|| refBean.getODSRAILWAY().length() > 0 || refBean.getTAG1().length() > 0
 	|| refBean.getTAG2().length() > 0  || refBean.getTAG3().length() > 0
 	|| refBean.getTAG4().length() > 0   ){
 		%>
 			hideUnHideDiv();
 		<%
 	
 	}  
 	%>
 	
 	// show previous save class
 	setRefClass('<c:out value="<%= rClass %>"/>');
 	setInOutLabels(window.document.getElementById('REFCLASS'));
 	window.document.getElementById("REFCLASS").focus();
 	
 	if('<c:out value="<%= refBean.getACKBY()%>"/>'.length > 0){
 		window.document.getElementById("mail").style.display ='none';
 		//window.document.getElementById("MAILID").style.display ='block';
 	}
 } 
 
  function setSessionSeachParams(OBJNAME, objValue){
		if(objValue!=''){
			//alert( '****  OBJNAME '+ OBJNAME);
			//alert( '****  objValue '+ objValue);
			window.document.getElementById(OBJNAME).value = objValue;
    		window.document.getElementById(OBJNAME).className = 'active';
    	}	
	}	
 
 function hideUnHideDiv(){
 	
 	var ODS = window.document.getElementById("ODS");
 	var TAGS = window.document.getElementById("TAGS");
 	//alert('ODS : '+ ODS.style.display);
 	
 	if(ODS.style.display == 'block')	{
 		ODS.style.display = 'none';
 		TAGS.style.display = 'none';
 	}else if(ODS.style.display == 'none')	{
 		ODS.style.display = 'block';
 		TAGS.style.display = 'block';
 	}
 }
 
 
function setTreeView(){

		window.document.getElementById("treediv").style.display="none";
		window.document.getElementById('txtData1').innerHTML="";
		window.document.getElementById('txtData2').innerHTML="";
		GangAnalysisDAO.getTextualData(x,fromdate,todate,textdata);
		window.document.getElementById("chartdiv").style.display="block";
		changeChart();		
		
}

function openAttachment(REFID, ATTACHMENTID, ORIGFILENAME, NEWFILENAME,EOFFICEFLAG,TYPE){
		var  prev_action = document.forms[0].action;
	//	var newimgid = assetid+"_"+imgid+ext;
		//alert(ORIGFILENAME+"-"+EOFFICEFLAG);
		window.document.forms[0].action="${pageContext.request.contextPath}/AttachmentController?ATTACHMENTID="+ATTACHMENTID+"&REFID="+REFID+"&ORIGFILENAME="+ORIGFILENAME+"&GENFILENAME="+NEWFILENAME+"&EOFFICEFLAG="+EOFFICEFLAG+"&TYPE="+TYPE+"";
		window.document.forms[0].submit();
		window.document.forms[0].action=prev_action;		
		return true;
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
	  TrnReferenceDAO.deleteRefImage(refID, attachmentID,'R', function (data){
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

function setToField(thisObj, TOFIELD){
	
	var fieldTo = window.document.getElementById(TOFIELD); 
	
	fieldTo.value = thisObj.value;
	fieldTo.className = 'active';

} 

function setOnDate(thisObj, targetObjName){
	var targetObj = window.document.getElementById(targetObjName);
	//alert(' targetObj '+ targetObj);
	if(thisObj.value.Trim() !=''){
		targetObj.value = '<c:out value="<%=serverDate%>"/>';
	}else {
		targetObj.value = '';
	}
}

function setAckWithSubcode(thisObj){ 
	var subjectObjValue = thisObj.value;
	var ackBy = window.document.getElementById("ACKBY");
	var ackOn = window.document.getElementById("ACKDATE");
	if ( subjectObjValue=='NOM' || subjectObjValue=='COM' ){
		ackBy.value = 1330;
		 if(!ackOn.value) {ackOn.value = '<c:out value="<%=serverDate%>"/>'; } 
	}
	else {
		ackBy.value = "";
		ackOn.value = ""; 
	}
}

function setStatusRemark(){
	var FILESTATUS1 = window.document.getElementById("FILESTATUS1");
	var FILESTATUS2 = window.document.getElementById("FILESTATUS2");
	
	var STATUSREMARK = window.document.getElementById("STATUSREMARK");
	
//	alert(FILESTATUS1.options[FILESTATUS1.selectedIndex].text);
	var text1 =  (FILESTATUS1.selectedIndex==0)?"": FILESTATUS1.options[FILESTATUS1.selectedIndex].text;
	var text2 =  (FILESTATUS2.selectedIndex==0)?"": FILESTATUS2.options[FILESTATUS2.selectedIndex].text;
	
	STATUSREMARK.value = text1 + ', ' + text2;
	
	

}
 function funcKeyPress(obj,objvalue){
 //if(objvalue.length==0){
    if(window.event.keyCode==113){
        obj.value=obj.value+'<c:out value="<%=serverDate%>"/>'; 
 	}
 // } 
 }
function changeStatus(showhideflag){
		if(showhideflag==1){
			document.all('displaystatus').style.display='block';
			var TENUREID='<c:out value="<%=sessionBean.getTENUREID()%>"/>';
			TrnReferenceDAO.getCounterStatus(TENUREID, getCounterStatusData);
			
			
		}else if(showhideflag==2){
			document.all('displaystatus').style.display='none';
		}
	}
function getCounterStatusData(data1){
	var statusResult = window.document.getElementById("displaystatus");
	var htmlText = '';
	//alert('data.length : '+statusResult.innerHTML);
	htmlText  = ' <table id="data" width="100%"><tbody >';	
	//htmlText += ' <tr><td align="left" ><b>Total Records</b></td> <td align="center" ><b>'+data.length+'</b></td></tr>';
	htmlText += ' <tr><th align="center" ><b><nobr>Ref Class.</nobr></b></th> <th align="center"  nowrap="nowrap"><nobr>Counter</nobr></th> <th align="center"  nowrap="nowrap"><nobr>Inward/Outward</nobr></th><th align="center" width="300" nowrap="nowrap"><nobr>Description</nobr></th></tr>';
	
	//alert(searchResult.innerHTML);
	
    var statusBean = new Array(data1.length);
	for(var loop1=0; loop1 < data1.length; loop1++){
		statusBean[loop1]= data1[loop1]; 
		
		//alert('refBean.REFNO '+ statusBean[loop1].REFCLASS);
		
		var trclass = 'treven';
	//	var trclass = (loop1%2==0)? "trodd":"treven";
		htmlText += '<tr " class= "'+trclass+'" > <td align="center"  nowrap="nowrap" > '+statusBean[loop1].REFCLASS+' </td> <td align="right"  nowrap="nowrap">'+statusBean[loop1].COUNTER+'</td>  </td> <td align="center"  nowrap="nowrap">'+statusBean[loop1].INOUT+'</td> </td> <td align="left"  nowrap="nowrap" width="300"><nobr>'+statusBean[loop1].CLASSDESCRIPTION+'</nobr></td> </tr>';
		//htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick="maximizeRefData(refBean[loop]);"> <td> '+trnRefBean.REFNO+' </td><td> kkkkk </td> </tr>';
			
			//alert(' htmlText ' + htmlText);	
					
	}
	
	htmlText += '</table>';
	//alert(htmlText);
	statusResult.innerHTML = htmlText;
	
	}
	function statusChangeNew(){
	//alert('haii');
	var TENUREID='<c:out value="<%=sessionBean.getTENUREID()%>"/>';
	 TrnReferenceDAO.getCounterStatusNew(TENUREID, getCounterStatusNewData);	
	}
	
	function getCounterStatusNewData(data1){
	var statusResult = window.document.getElementById("scrollingDivFooter");
		var htmlText = '';	
		
	htmlText  = ' <table width="100%"><tbody ><tr valign=top>';	
	htmlText += ' <td width="20%" ><A href="#" style="color: green;text-decoration: underline;font-size: 11px;font-weight: bold;"	onclick="javascript:statusChangeNew()" > <img src="images/refresh.png" width="10%" height="10%" style="border-style: none"> Class Status</A></td>';
	
	for(var loop1=0; loop1 < data1.length; loop1++){
		var width=(80/(data1.length));
		var trclass = (loop1%2==0)? '<u><font color="green">':'<font color="blue">';
		var trclass1 = (loop1%2==0)? '</u>':'';
		
//		htmlText += '<td nowrap="nowrap" width='+width+'%><b>'+trclass+' '+data1[loop1]+' '+trclass1+' </font></b></td>';					
		htmlText += '<td nowrap="nowrap" width='+width+'%><b>'+data1[loop1]+' </font></b></td>';					
	}	
	htmlText += '</tr></tbody></table>';
	//alert(htmlText);
	statusResult.innerHTML = htmlText;
	}

	function checkDateVer2(obj) {
	     dateformat = /^(0[1-9]|[12][0-9]|3[01])[\/\-](0[1-9]|1[012])[\/\-]\d{4}$/;
	     date = obj.value;
	    // Matching the date through regular expression      
	    if (dateformat.test(date)) {
	         operator = date.split('/');

	        // Extract the string into month, date and year      
	         datepart = [];
	        if (operator.length > 1) {
	            datepart = date.split('/');
	        }
	         day = parseInt(datepart[0]);
	         month = parseInt(datepart[1]);
	         year = parseInt(datepart[2]);
	        // Create a list of days of a month      
	         ListofDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	        if (month == 1 || (month > 2 && month < 13)) {
	            if (day > ListofDays[month - 1]) {
	                //to check if the date is out of range
	                alert("Invalid date")    
	                return false;
	            }
	        } else if (month == 2) {
	             leapYear = false;
	            if ((!(year % 4) && year % 100) || !(year % 400)) leapYear = true;
	            if ((leapYear == false) && (day >= 29)) {
	            alert("Invalid date")
	                return false;
	            }
	            else
	                if ((leapYear == true) && (day > 29)) {
	                alert("Invalid date")
	                    return false;
	                }
	        } else if (month>13){
	        alert("Invalid Month");
	        return false;
	        }
	    } else {
	    alert("Enter valid date in format DD/MM/YYYY")
	        return false;
	    }
	    return true;
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
 --></script>
 
   <style> 
                #scrollingDivFooter{ 
                                background-color:#F1ECE4; 
                                height:30px; 
                                
                                width:800px; 
                                
                              
                                
                } 
   </style> 
<script>  
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


//variable for holding inward or outward type of reference
var inOut='';
function setInOutLabels(thisObj){
  		
	//document.getElementById("EOFFICEREFNO").disabled = true;
	
	if(window.document.getElementById("REFCLASS").value=='A' || window.document.getElementById("REFCLASS").value=='M'){
  		//alert(window.document.getElementById("REFCLASS").value);
  		window.document.getElementById("compCondSalute").style.display="block";
  		}
  		else{
  		window.document.getElementById("compCondSalute").style.display="none";
  		}
  		var label_REFERENCENAME = window.document.getElementById("label_REFERENCENAME");
  		var label_INCOMINGDATE = window.document.getElementById("label_INCOMINGDATE");
  		for(var i =0; i < refClassListArray[1].length;i++){
  			if(thisObj.value == refClassListArray[0][i]){
  				inOut = refClassListArray[1][i];
  			}
  		}
  		if(inOut == 'I')
  		{	
  			window.document.getElementById("INOUT").value = 'I';
  		}
  		else if(inOut == 'O')
  		{
  			window.document.getElementById("INOUT").value = 'O';
  		}
<%	if(request.getAttribute("refBean") != null)	{	%>
//alert('hiiiii');
  		if(inOut =='O' || inOut =='o'){
  			label_REFERENCENAME.innerHTML = "Sent To ";	
  			label_INCOMINGDATE.innerHTML = "Sent Date";
  			label_FORWARDTO.innerHTML = "";	
  			label_FORWARDON.innerHTML = "";
  			label_TARGET.innerHTML = "";	
  			for (var loop=1; loop < window.document.getElementsByName("MARKINGTO").length; loop++){
	  			window.document.getElementsByName("MARKINGTO")[loop].style.display="none";
	  			window.document.getElementsByName("MARKINGDATE")[loop].value=' ';
	  			window.document.getElementsByName("MARKINGDATE")[loop].style.display="none";
	  			window.document.getElementsByName("TARGETDAYS")[loop].value='0';
	  			window.document.getElementsByName("TARGETDAYS")[loop].style.display="none";
	  			window.document.getElementsByName("TARGETDATE")[loop].value=' ';
	  			window.document.getElementsByName("TARGETDATE")[loop].style.display="none";
			}
  		}else if(inOut =='I' || inOut =='i'){
  			label_REFERENCENAME.innerHTML = "Received From ";
  			label_INCOMINGDATE.innerHTML = "Incoming Date ";
  			label_FORWARDTO.innerHTML = "Forward To";	
  			label_FORWARDON.innerHTML = "Forward On";
  			label_TARGET.innerHTML = "";
//  			for (loop=1; loop < window.document.getElementsByName("MARKINGTO").length; loop++){
//	  			window.document.getElementsByName("MARKINGTO")[loop].style.display="block";
//	  			window.document.getElementsByName("MARKINGDATE")[loop].style.display="block";
//	  			window.document.getElementsByName("MARKINGDATE")[loop].value='<c:out value="<%=serverDate %>"/>';
//	  			window.document.getElementsByName("TARGETDAYS")[loop].style.display="block";
//	  			window.document.getElementsByName("TARGETDAYS")[loop].value='15';
//	  			window.document.getElementsByName("TARGETDATE")[loop].style.display="block";
//				setTargetDate('<c:out value="<%=serverDate %>"/>', window.document.getElementsByName("TARGETDAYS")[loop]);
	  			//window.document.getElementsByName("TARGETDATE")[loop].value='';
//			}
  		}
<%} else {%>
  		if(inOut =='O' || inOut =='o'){
  			label_REFERENCENAME.innerHTML = "Sent To ";	
  			label_INCOMINGDATE.innerHTML = "Sent Date";
  			label_FORWARDTO.innerHTML = "";	
  			label_FORWARDON.innerHTML = "";
  			label_TARGET.innerHTML = "";	
  			for (var loop=1; loop < window.document.getElementsByName("MARKINGTO").length; loop++){
	  			window.document.getElementsByName("MARKINGTO")[loop].style.display="none";
	  			window.document.getElementsByName("MARKINGDATE")[loop].value=' ';
	  			window.document.getElementsByName("MARKINGDATE")[loop].style.display="none";
	  			window.document.getElementsByName("TARGETDAYS")[loop].value='0';
	  			window.document.getElementsByName("TARGETDAYS")[loop].style.display="none";
	  			window.document.getElementsByName("TARGETDATE")[loop].value=' ';
	  			window.document.getElementsByName("TARGETDATE")[loop].style.display="none";
			}
  		}else if(inOut =='I' || inOut =='i'){
  			label_REFERENCENAME.innerHTML = "Received From ";
  			label_INCOMINGDATE.innerHTML = "Incoming Date ";
  			label_FORWARDTO.innerHTML = "Forward To";	
  			label_FORWARDON.innerHTML = "Forward On";
  			label_TARGET.innerHTML = "";
  			for (loop=1; loop < window.document.getElementsByName("MARKINGTO").length; loop++){
	  			window.document.getElementsByName("MARKINGTO")[loop].style.display="block";
	  			window.document.getElementsByName("MARKINGDATE")[loop].style.display="block";
	  			window.document.getElementsByName("MARKINGDATE")[loop].value='<c:out value="<%=serverDate %>"/>';
	  			window.document.getElementsByName("TARGETDAYS")[loop].style.display="block";
	  			window.document.getElementsByName("TARGETDAYS")[loop].value='7';
	  			window.document.getElementsByName("TARGETDATE")[loop].style.display="block";
				setTargetDate('<c:out value="<%=serverDate %>"/>', window.document.getElementsByName("TARGETDAYS")[loop]);
	  			//window.document.getElementsByName("TARGETDATE")[loop].value='';
			}
  		}

<%}%>
  }

function Calender1(obj){
obj.className='active';
newCalendar('INCOMINGDATEFROM-L');
}
function Calender2(obj){
obj.className='active';
newCalendar('INCOMINGDATETO-L');
}


function setBudgetVal(obj)
{
	window.document.getElementById("REMARKSEARCH").value=obj.value;
}
</script>

<script>
   var cpval='';
   function functionKey(obj,objvalue){
   		if(!obj.readOnly&&((obj.type=='text')||(obj.type=='textarea'))){
   			if(window.event.keyCode==113){
   				//alert(parseInt(obj.maxLength));
				var maxlength=parseInt(obj.maxLength);
				if(maxlength >0 ){
					if(maxlength >= (parseInt(objvalue.length)+parseInt('<c:out value="<%=serverDate.length()%>"/>'))){
	 					obj.value=obj.value+"<c:out value='<%=serverDate%>'/>";
	 				}
	 			}
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
				}
			}
			if(window.event.keyCode==119){
				//alert((cpval.length));
				var maxlength=parseInt(objvalue.length);
				if(cpval.length >0 ){
				obj.value=obj.value+cpval;
				cpval='';
				}else{
				obj.value=obj.value+'(Personally Handed Over)';
				}
			}
		}
}
$(document).ready(function () {
	$("select").live('focus', function() {$(this).css('background', '#FFE4E1')});
	$("select").live('blur', function() {$(this).css('background', '#FFFFFF')});
	$("TEXTAREA").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
	$("TEXTAREA").blur(function () {  $(this).css('background', '#FFFFFF') });
	
	 $.ui.dialog.defaults.bgiframe = true;
     $("#INCOMINGDATEFROM").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true', endDate: "today" });
     $("#INCOMINGDATETO").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true', endDate: "today" });
     $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
     $( "#datepicker" ).datepicker({  maxDate: new Date() });
	

});	


/* $("#REFNOTO").keypress(function(event) {
    if (event.keyCode === 13) {
        $("#btn1").click();
    }
});

$("#btn1").click(function() {
    alert("Button clicked");
}); */


</script>
<script type="text/javascript">
function modalWin() {
	if (window.showModalDialog) {
		window.showModalDialog("MailAlert.jsp?LANG=<c:out value='<%= refBean.getLANGUAGE()%>'/>&REFNO=<c:out value='<%= refBean.getREFNO()%>'/>&REFNAME=<c:out value='<%= refBean.getREFERENCENAME()%>'/>&LETTERDATE=<c:out value='<%= refBean.getLETTERDATE()%>'/>&SUBJECT=<c:out value='<%= refBean.getSUBJECT()%>'/>&VIPSTATUS=<c:out value='<%= refBean.getVIPSTATUS()%>'/>&STATE=<c:out value='<%= refBean.getSTATECODE()%>'/>","name","dialogWidth: 800px; dialogHeight: 800px");
	} else {
		window.open('MailAlert.jsp','name','height=255,width=250,toolbar=no,directories=no,status=no, menubar=no,scrollbars=no,resizable=no ,modal=yes');
	}
}

function setTenureDate(obj){
	if(obj.value.length > 0){
		window.document.getElementById("INCOMINGDATEFROM").className = 'active';
		window.document.getElementById("INCOMINGDATETO").className = 'active';
		
		window.document.getElementById("INCOMINGDATEFROM").value = '<c:out value="<%= sessionBean.getTENURESTARTDATE() %>"/>';
		window.document.getElementById("INCOMINGDATETO").value = '<c:out value="<%=  serverDate%>"/>';
	}
	else {
		window.document.getElementById("INCOMINGDATEFROM").value = 'In. Date from';
		window.document.getElementById("INCOMINGDATETO").value = 'In. Date to';
			
		window.document.getElementById("INCOMINGDATEFROM").className = 'inactive';
		window.document.getElementById("INCOMINGDATETO").className = 'inactive';
		
	}
}
</script>
</head>
<body style="" onload="bodyOnload();">
<form name="frmOriginated" id="frmOriginated" action="${pageContext.request.contextPath}/TrnReferenceController" method="post" enctype="multipart/form-data">
<table width="100%" align="left" border="0" cellspacing="0" cellpadding="0" id="MainTabDiv" >
	<tr>  
      	<td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Add</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
        </td>
 	</tr>
<tr valign="top"> 
	<td valign="top"> 
			        <table width="100%" cellpadding="0" cellspacing="0">
			        <tbody id="Originated">
 	<tr>
 		<td width="*" valign="top" align="left" valign="top" height="100%">
 			<fieldset id="Search" style="border: 0px;">
 			<table border="0" cellspacing="0" cellpadding="0">
 				<tr>
 					<td> <img border="0" src="images/Search1.png"
								width="91" height="35"></td>
						<td valign="top">
							<A href="#" style="color: red;text-decoration: underline;font-size: 11px;font-weight: bold;"
							onmouseover="javascript:changeStatus(1)"
							onmouseout="javascript:changeStatus(2)"
							>Ref. Class Details</A></td>
					
					</tr>
					
						
 				<tr>
 					<td></td>
						<td align="right">
<DIV id="displaystatus" style="display:none;float:left;position:absolute;background-color:transparent;Z-index:10;
							margin-left:auto;margin-top:auto;border-width: inherit ;border-style: none; width: 300px; ">
							
							
					</div></td>
					</tr>
 				<tr>
 					<td>
 						<label for="REFNOFROM">Ref. No from</label>
 						<input type="text" name="REFNOFROM" id="REFNOFROM" size="15" 
 						 style="text-transform: uppercase;"
 						 onkeypress="allowAlphaNumWithoutSpace('/');" 
 						 onkeyup="setToField(this, 'REFNOTO');" onblur="document.getElementById('btn1').focus();" >
 					</td>
					<td align="left">
						<label for="REFNOTO">Ref. No to</label>
						<input type="text" name="REFNOTO" id="REFNOTO" size="15" 
						style="text-transform: uppercase;" onkeypress="allowAlphaNumWithoutSpace('/');">
					</td>
					</tr>
					<tr>
 						<td>Example: 'A/1000'</td>
						<td align="right">
						<!-- 	<input type="button" name="btn1" value="&gt;&gt;" onclick="getSearch(this,1);">  -->
							<input type="button" name="btn1" id="btn1" value="&gt;&gt;" onclick="getSearchOnRefNo(this);">
						</td>
					</tr>
					
					<tr>
	 					<td>
	 						<label for="REFERENCENAMESEARCH">Received from</label>
	 						<input type="text" name="REFERENCENAMESEARCH" id="REFERENCENAMESEARCH" style="text-transform: uppercase;" size="15" onkeyup ="setTenureDate(this);">
	 					</td>
						<td align="left">
							<label for="SUBJECTSEARCH">Subject</label>
							<input type="text" name="SUBJECTSEARCH" id="SUBJECTSEARCH" style="text-transform: uppercase;" size="15" onkeyup ="setTenureDate(this);">
						</td>
					</tr>
					<tr>
					<td align="left">
						<label for="COMMONSEARCHVALUE">FORWARD TO</label>
							<input type="text" name="COMMONSEARCHVALUE" id="COMMONSEARCHVALUE" style="text-transform: uppercase;" size="15" onkeyup ="setTenureDate(this);">
						</td>
						<td align="left">
						<label for="REMARKSEARCH">REMARKS</label>
							<input type="text" name="REMARKSEARCH" id="REMARKSEARCH" style="text-transform: uppercase;" size="15" onkeyup ="setTenureDate(this);">
			<!-- 		<select name="COMMONSEARCH" id="COMMONSEARCH" style="width: 100px" >
							<option value="" selected></option>
							<option value="Y">Budget</option>
							<option value="N">Non-Budget</option>
						</select>		 -->
						</td>
						
					</tr>
					<tr>
						<td align="left"><nobr>
	<label for="INCOMINGDATEFROM">In. Date from</label>
		<input type="text" name="INCOMINGDATEFROM"  id="INCOMINGDATEFROM" size="15" style="text-transform: uppercase;"
		onblur="chkWorkDate(this);" maxlength="10">
		<!-- <input type="button" class="calbutt" name="regdtfrm" onclick="Calender1(document.getElementById('INCOMINGDATEFROM'))"> -->
	 					</nobr></td>
						<td align="left"><nobr>
	<label for="INCOMINGDATETO">In.	 Date to</label>
		<input type="text" name="INCOMINGDATETO" id="INCOMINGDATETO" size="15" style="text-transform: uppercase;" onblur="chkWorkDate(this);">
		<!-- <input type="button" class="calbutt" name="regdtto"	onclick="Calender2(document.getElementById('INCOMINGDATETO'))"> --></nobr>
						</td>
					</tr>
					<tr >
						<td align="right"></td>
						<td align="right">
					<!-- 	<input type="button" name="btn2" value="&gt;&gt;" onclick="getSearch(this,2);">  -->
						<input type="button" name="btn2" value="&gt;&gt;" onclick="getSearch(this);">
						
						</td>
					</tr>
						
						
					</table>
					
					
<div id="searchResult" style="height:250px; overflow:auto; overflow-x: hidden;"></div>
					
					</fieldset >
		</td>
		
		<td width="5pxs;" bgcolor="#F2F3F1" onclick="showHideTD(this)" title="Show search criteria" style="cursor: pointer;" valign="top" name="showhidecol" id="showhidecol">
			<div id="scrollingDiv"> 
       			<img src="images/next.gif" ></div></td>
       		
 		<td id="td2" width="100%" valign="top">	 
			<div id="tabs" style=" border: 0px">
			    <ul>
			        <li><a id="MainReferencesLink" href="#MainReferences"><span> Main </span></a></li>
			        <li><a href="#MainReminder"><span> Reminder </span></a></li>
			        <li id="listMainBudget"><a href="#MainBudget"><span>Budget</span></a></li>
			        <li id="listMainReturned"><a href="#MainReturned"><span>Sent Back</span></a></li>
			        <li><a href="#MainFinalStatus"><span> Final Status </span></a></li>
			        <li id="listfileReply"><a href="#fileReply"><span> Reply </span></a></li>
			        <li><a id="MainAttachmentLink" href="#MainAttachment"><span> Attachment </span></a></li>
			        <li><a href="#MainStickyNote"><span> Yellow Slip </span></a></li>
			        <li><a href="#MainForwardChain"><span> Forwarding Chainage </span></a></li>
			        <li><a href="#MainReminderChain"><span> Reminder Chainage </span></a></li>
			    </ul>
			    <div align="right"><c:out value="<%= refBean.getLOGINID().toUpperCase() %>"/></div>
			    <div id="MainReferences" >
			    	<fieldset >
					<table id="data" width="99%" >
					<tr>
						<td nowrap>E-Office Reference No. <span style="color: red"><b>*</b></span></td>
						<td nowrap colspan="2"> 
						<input type="text" name="EOFFICEREFNO" id="EOFFICEREFNO" value="<c:out value='<%= refBean.getEOFFICEREFNO()%>'/>" />
							<input type="hidden" name="EOFFICEREFNOForSave" id="EOFFICEREFNOForSave" value="<c:out value='<%= refBean.getEOFFICEREFNO()%>'/>" />
							<%-- <input type="hidden" name="URL" id="URL" value="<%= refBean.getURL()%>" /> --%>
							 <input type="hidden" name="MaximizeOnAttach" id="MaximizeOnAttach" />
							
						</td>
						<td>
						<input type="button" name="fetchEofficedet" id="fetchEofficedet" value="Go" class ="butts" onclick="maximizeResultForEoffice();">
						</td>
						<td nowrap colspan="2">
						<%
									if( refBean.getEOFFICEMSG().equalsIgnoreCase("Data Received successfully from E-Office")){
								%>
						<font color='green'><c:out value='<%= refBean.getEOFFICEMSG()%>'/></font>
						<%
									}else{
								%>
						<font color='red'><c:out value='<%= refBean.getEOFFICEMSG()%>'/></font>
							<%
									}
								%>	
						</td>
						
						
					</tr>
						<tr >
							<td nowrap>Ref. Class <span style="color: red"><b>*</b></span></td>
							<td nowrap colspan="2">
							<select style="width: 60px" name="REFCLASS" id="REFCLASS" onchange="setRefNoPrefix(this); setInOutLabels(this); setRefCategoryE(this);" tabindex="1">
								<option value="" selected> </option>
								<%
									for(int i=0;i<refClassList.size();i++){
									CommonBean beanCommon=(CommonBean) refClassList.get(i);
								%>
<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getREFCLASS())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")==null)&&(request.getParameter("REFCLASS")!=null ?request.getParameter("REFCLASS"):"").equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/>><c:out value='<%=beanCommon.getField1()%>'/></option>
								<%}%>
							</select> <input type="text" name="INOUT" id="INOUT" readonly
								class="readonly" size="7"></td><td colspan="2"><span
								style="display: none; text-align: left;" id="REFCOUNTER"></span></td><td >Ref.No.</td>
							<td >
							<input type="text" name="REFNO" id="REFNO" value="<c:out value='<%= refBean.getREFNO()%>'/>" readonly="readonly" class="readonly"  >
							<input type="hidden" name="REFID" id="REFID" value="<c:out value='<%= refBean.getREFID()%>'/>" readonly="readonly" class="readonly"> <input
								type="hidden" name="REFCOUNT" id="REFCOUNT" value="<c:out value='<%= refBean.getREFCOUNT()%>'/>" readonly="readonly" class="readonly">
							</td>
							<td width="">Link Ref.No.</td>
							<td width=""><input type="HIDDEN" name="LINKREFID" id="LINKREFID" value="<c:out value='<%= refBean.getLINKREFID()%>'/>" readonly="readonly"
								class="readonly">
								<input type="text" name="LINKREFNO" id="LINKREFNO" value="<c:out value='<%= refBean.getLINKREFNO()%>'/>" readonly="readonly"
								class="readonly"></td>	
						</tr>
						<tr >
							<td >Ref. Category <span style="color: red"><b>*</b></span></td>
							<td colspan="2"><select name="REFCATEGORY" id="REFCATEGORY" style="width: 120px" tabindex="2" onchange="hideShowTourRemarks(this);" >
								<option value="" selected> </option>
								<%
										for(int i=0;i<refCategoryList.size();i++){
										CommonBean beanCommon=(CommonBean) refCategoryList.get(i);
										%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getREFCATEGORY())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")==null)&&"L".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
									}%>
							</select>
							
							</td><td nowrap >Language <span style="color: red"><b>*</b></span></td>
							<td><select name="LANGUAGE"  id="LANGUAGE" style="width: 120px" tabindex="3"  >
								<option value="" selected> </option>
								<%
										for(int i=0;i<languageList.size();i++){
										CommonBean beanCommon=(CommonBean) languageList.get(i);
										%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getLANGUAGE())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")==null)&&"2".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
									}%>
							</select></td>
							<%--   		    		
							<td>Security Grading <span style="color: red"><b>*</b></span></td>	
			    			
			    			<td><select name="SECURITYGRADING" style="width: 120px">
			    					<option value="" selected> </option>
			    					<option value="" selected> </option>
			    					<%
										for(int i=0;i<SecurityGradingList.size();i++){
										CommonBean beanCommon=(CommonBean) SecurityGradingList.get(i);
										%>
											<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(refBean.getSECURITYGRADING())?"selected":""%>><%=beanCommon.getField2()%></option>
										<%
									}%>
			    				</select></td>
			    				--%>
							<td nowrap="nowrap" >Urgency &amp; Security <span style="color: red"><b>*</b></span></td>
							<td nowrap="nowrap"><select name="URGENCY" id="URGENCY" style="width: 120px" tabindex="3"  >
								<option value="" selected> </option>
								<%
										for(int i=0;i<urgencyList.size();i++){
										CommonBean beanCommon=(CommonBean) urgencyList.get(i);
										%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getURGENCY())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")==null)&&"G".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/> <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getURGENCY())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")!=null)&& refBean.getURGENCY().equalsIgnoreCase("") && "G".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
									}%>
							</select></td>
							<td nowrap="nowrap">Received By<span style="color: red"><b>*</b></span></td>
							<td nowrap="nowrap">
								<select name="RECIEVEDBY" id="RECIEVEDBY"  style="width: 120px" tabindex="3"  >
								<option value="" selected> </option>
								<%
										for(int i=0;i<receivedByList.size();i++){
										CommonBean beanCommon=(CommonBean) receivedByList.get(i);
										%>
							
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getRECIEVEDBY())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")==null)&&"PO".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
									}%>
							</select>
							</td>
						</tr>
						<tr >
								<td nowrap>File No.</td>
								<td nowrap colspan="2">
								<input type="text" name="FILENO" id="FILENO" style="width: 120px" maxlength="40" style="text-transform: uppercase;"
															 onkeypress="allowAlphaNumWithoutSpace('/');" 
															 value="<c:out value='<%=refBean.getFILENO()%>'/>"></td>
															 
								<td nowrap >E-Office File No.</td>							 
								<td nowrap colspan="2"><input type="text" name="EofficeFILENO" id="EofficeFILENO" style="width: 120px" maxlength="40" style="text-transform: uppercase;"
								 onkeypress="allowAlphaNumWithoutSpace('/');" value="<c:out value='<%=refBean.getEOFFICEFILENO()%>'/>"></td>
					<td> <a href="#" onclick="myFunction(); return false;">See Reference Movement</a></td>
							</tr> 
					</table>
					</fieldset>
			    	<fieldset>	
			    	<table id="data" width="100%">	
			    		<tr >
			    			<td nowrap="nowrap" width="96"><span id="label_INCOMINGDATE">Incoming Date</span><span style="color: red"><b>*</b></span><span style="color: red"><b></b></span></td>
			    			<td width="40">
			    				<input type="text" name="INCOMINGDATE" id="INCOMINGDATE"
								value="<c:out value='<%= refBean.getINCOMINGDATE()%>'/>"
								onblur="chkWorkDate(this);" onkeypress="chknumeric(47);"
								maxlength="10"
								onchange="window.document.frmOriginated.LETTERDATE.value=this.value"
								tabindex="5" readonly="readonly" size="13">
								<input type="hidden" name="ISCONF" id="ISCONF" value="<c:out value='<%= sessionBean.getISCONF() %>'/>" size="2">
								
									<input type="hidden" name="TENUREID" id="TENUREID" value="<c:out value='<%= refBean.getTENUREID() %>'/>">
			    			</td>
			    			
			    			<td nowrap="nowrap" width="37">Letter No</td><td width="100"><input type="text" name="LETTERNO" value="<c:out value='<%= refBean.getLETTERNO()%>'/>" tabindex="6" size="30" onkeypress="allowAlphaNum('()&/*-');"></td>
			    			
			    			<td nowrap="nowrap" width="37">Ref. Dated<span style="color: red"><b>*</b></span></td><td width="168"><input type="text" name="LETTERDATE" value="<c:out value='<%= refBean.getLETTERDATE()%>'/>"  onblur="checkDateVer2(this);" onkeypress="chknumeric(47);" maxlength="10" tabindex="6" size="14"></td>
						<td width="39">Budget</td>
						<td width="100">
						<select name="ISBUDGET" id="ISBUDGET" style="width: 160px" onchange=setMaxRowsOfMarking(this); tabindex="7"  >
							<option value=""> </option>
							<option value="N" <c:out value='<%="N".equalsIgnoreCase(refBean.getISBUDGET())?"selected":""%>'/> <c:out value='<%=((request.getAttribute("refBean")==null) ?"selected":"" ) %>'/> <c:out value='<%=((eofficeFlag=="eofficedata") ?"selected":"" ) %>'/>>Non-Budget</option>
							<option value="Y" <c:out value='<%="Y".equalsIgnoreCase(refBean.getISBUDGET())?"selected":""%>'/> >Budget</option>
						</select></td>
					</tr>
					<tr >
					<td nowrap="nowrap" width="96"><span id="label_REFERENCENAME">Received From</span><span style="color: red"><b>*</b></span></td>
					<td nowrap="nowrap" width="303">
						<input type="text" id="REFERENCENAME" name="REFERENCENAME"
								maxlength="50" value="<c:out value='<%= refBean.getREFERENCENAME()%>'/>"
								style=" background-image: url('${pageContext.request.contextPath}/images/search.png'); background-position: right;background-repeat: no-repeat; "
								size="42" tabindex="8" onblur="getRefNameDetail();"
								onkeypress="allowAlphaNum('()/');">
								<!--  <img
								border="0" id="refDetailImg" style="display: none;"
								onclick="document.getElementById('divRefNameResult').style.display='block'"
								src="images/details.png" width="8" height="21"></img>-->
					</td>
					<td width="75">Status</td><td width="337"><input type="text"
								name="VIPSTATUS" id="VIPSTATUS" maxlength="50"
								value="<c:out value='<%= refBean.getVIPSTATUS()%>'/>" tabindex="9"
								onblur="hideDivRefStatus(this);" size="45" onkeypress="allowAlphaNum('()/');"></td>
					
						<td width="39">State</td>
						<td width="148"><select name="STATECODE" id="STATECODE" style="width: 160px" tabindex="9">
							<option value=""> </option>
							<%
								for(int i=0;i<stateList.size();i++){
								CommonBean beanCommon=(CommonBean) stateList.get(i);
								%>
									<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
										<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSTATECODE())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/>
									</option>
								<%
							}%>
						</select></td>
					</tr>
					<tr>
					<td>Party</td>
						<td><select name="VIPPARTY" id="VIPPARTY" style="width: 160px" tabindex="9" >
							<option value=""> </option>
							<%
								for(int i=0;i<partyList.size();i++){
								CommonBean beanCommon=(CommonBean) partyList.get(i);
								%>
									<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
										<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getVIPPARTY())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/>
									</option>
								<%
							}%>
						</select></td>
						<td width="40">Salutation <span style="color: red" id='compCondSalute'><b>*</b></span> </td>
						<td><select name="SALUTATION" id="SALUTATION" style="width: 120px;" tabindex="9" >  
						<option value="" selected> </option>
								<%
										for(int i=0;i<salutationList.size();i++){
										CommonBean beanCommon=(CommonBean) salutationList.get(i);
										%>
							
									<%-- <option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSALUTATION())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")==null)&&"PO".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/>><c:out value='<%=beanCommon.getField2()%>'/></option> --%>
									<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSALUTATION())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")==null)&&"D".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/> <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSALUTATION())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")!=null)&& refBean.getSALUTATION().equalsIgnoreCase("") && "D".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
									}%>
					</select></td>
						<td width="40">Address(English)</td><td nowrap="nowrap" width="200"><input type="text"
								name="ADDRESSENGLISH" id="ADDRESSENGLISH" maxlength="50"
								value="<c:out value='<%= refBean.getADDRESSENGLISH()%>'/>" tabindex="9"
								onblur="window.document.getElementsByName('SUBJECT')[1].focus();" size="30" onkeypress="allowAlphaNum('()/');" ></td>
						<td><input type="hidden" name="VIPID" id="VIPID" value="<c:out value='<%=refBean.getADDVIPID() %>'/>"/></td>
						<td></td>
						<td><input type="hidden" name="VIPADDRESS" id="VIPADDRESS" value="<c:out value='<%=refBean.getADDVIPTYPE() %>'/>"/>
						<input type="hidden" name="VIPSTATUSDESC" id="VIPSTATUSDESC" value="<c:out value='<%=refBean.getVIPSTATUSDESC() %>'/>"/>
						</td>
					</tr>
					
					<tr >
						<td colspan="6">
						<table id="data" width="100%">
				    		<tr style="display:block">
								<td nowrap="nowrap" width="510">Subject<input type="hidden" name="btnClick" id="btnClick"></td>
					    		<td nowrap="nowrap" width="146">Sub.<span style="color: red"><b>*</b></span></td>
					    		<td nowrap="nowrap" width="134"><span id="label_FORWARDTO">Forward To</span><span style="color: red"><b>*</b></span></td>
								<td nowrap="nowrap" width="155"><span id="label_FORWARDON">Forward On</span></td>
					    		<td nowrap="nowrap" width="110" colspan="2"><span id="label_TARGET">Tar. days &amp; dt.</span></td></tr>
					<tbody id="baserow" >
					<tr  style="display: none">
						<td nowrap="nowrap" width="442">
						<input type="hidden" name="MARKINGREFID" readonly="readonly" class="readonly" size="5" />
						<input type="hidden" name="MARKINGSEQUENCE" readonly="readonly" class="readonly" size="5" />
						<input type="hidden" name="MARKINGRECEIPTNUMBER" readonly="readonly" class="readonly"  />
						<textarea rows="2" cols="70" name="SUBJECT" id="SUBJECT" 
											onkeypress="allowAlphaNum('()&/*');"></textarea> 
												<br>
						<span  style="display: none;">Stn1: <input type="text" name="keyword1" id="keyword1" size="17"></span>
						<span  style="display: none;">Stn2: <input type="text" name="keyword2" id="keyword2" size="17"></span>
						<span  style="display: none;">Trn: <input type="text" name="keyword3"  id="keyword3" size="17"></span>		
						<span style="display: none;"> <input type="text" name="k1" id="k1"> <input type="text" name="k2" id="k2"> <input type="text" name="k3" id="k3"> </span>									
											</td>
						<td nowrap="nowrap" width="133">
							<select name="SUBJECTCODE" id="SUBJECTCODE" style="width: 120px;"   onclick="displayKeyword(this,true);" > 
								<option value="" selected> </option>
								<%
									for(int i=0;i<subjectList.size();i++){
									CommonBean beanCommon = (CommonBean) subjectList.get(i);
								%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" title="<c:out value='<%=beanCommon.getField2()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%}%>
							</select>
					 	 	
						</td>
						<td nowrap="nowrap" width="130">
						<select name="MARKINGTO" id="MARKINGTO" style="width: 120px;" onchange="setMarkingDateToCurrent();" >  
						<option value="" selected> </option>
						<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/> ( <c:out value='<%=beanCommon.getField3()%>'/> ) </option>
							<%
						}%>
					</select>
						</td>
						
						<td nowrap="nowrap" width="110"><input type="text" name="MARKINGDATE" id="MARKINGDATE" size="10" maxlength="10" value="<c:out value='<%=serverDate %>'/>"  /><input type="hidden" id="servDate" value="<c:out value='<%=serverDate %>'/>" /></td>
						<td nowrap="nowrap" colspan="2" width="165"><nobr><input type="text" name="TARGETDAYS" id="TARGETDAYS" size="2" maxlength="2" value="7" onblur="setTargetDate('<c:out value='<%=serverDate %>'/>', this);" onkeypress="chknumeric();" />
						<input type="text" size="10" maxlength="10" readonly name="TARGETDATE" id="TARGETDATE" class="readonly" /></nobr></td></tr>
					
					<% for(int i=0; i < refBean.getMarkingBeanList().size();i++){
					TrnMarking markingBean = (TrnMarking)refBean.getMarkingBeanList().get(i);
					
						%>
						<tr style="display:block" >
							<td nowrap="nowrap" width="442">
							<input type="hidden" name="MARKINGREFID" id="MARKINGREFID" readonly="readonly" class="readonly" value="<c:out value='<%=markingBean.getREFID()%>'/>" size="5">
							<input type="hidden" name="MARKINGSEQUENCE" id="MARKINGSEQUENCE" readonly="readonly" class="readonly" value="<c:out value='<%=markingBean.getMARKINGSEQUENCE()%>'/>" size="5">
							<input type="hidden" name="MARKINGRECEIPTNUMBER" id="MARKINGRECEIPTNUMBER" readonly="readonly" class="readonly" value="<c:out value='<%=markingBean.getEOFFICERECEIPTNO()%>'/>" >
							
							
						<textarea rows="2" cols="70" onkeypress="allowAlphaNum('()&/*');"
											name="SUBJECT" id="SUBJECT" ><c:out value='<%= refBean.getSUBJECT()%>'/></textarea>
						<br>
						<span style="display: none;">Stn1: <input type="text" name="keyword1" size="17" value="<c:out value='<%= refBean.getKeywords1()%>'/>"></span>
						<span style="display: none;">Stn2: <input type="text" name="keyword2" size="17" value="<c:out value='<%= refBean.getKeywords2()%>'/>"></span>
						<span style="display: none;">Trn: <input type="text" name="keyword3" size="17" value="<c:out value='<%= refBean.getKeywords3()%>'/>"></span>	
						<span style="display: none;"> <input type="text" name="k1"  value="<c:out value='<%= refBean.getKeywords1()%>'/>"> <input type="text" name="k2"  value="<c:out value='<%= refBean.getKeywords2()%>'/>" > <input type="text" name="k3"  value="<c:out value='<%= refBean.getKeywords3()%>'/>" > </span>						
											</td>
							<td nowrap="nowrap" width="133">
							<select name="SUBJECTCODE" id="SUBJECTCODE" style="width: 120px;" onchange="setAckWithSubcode(this);" onclick="displayKeyword(this,true);"> 
							<option value="" selected> </option>
							<%
											for(int j=0;j<subjectList.size();j++){
											CommonBean beanCommon = (CommonBean) subjectList.get(j);
											%>
												<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSUBJECTCODE())?"selected":""%>'/> title="<c:out value='<%=beanCommon.getField2()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
											<%
										}%>
						</select>
						</td>
							<td nowrap="nowrap" width="130">
						<select name="MARKINGTO" id="MARKINGTO" style="width: 120px;" onchange="setMarkingDateToCurrent();"  > 	 
						<option value="" selected> </option>
						<%
							for(int k=0;k<markingToList.size();k++){
							CommonBean beanCommon = (CommonBean) markingToList.get(k);
							%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(markingBean.getMARKINGTO())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/> ( <c:out value='<%=beanCommon.getField3()%>'/> )</option>
							<%
						}%>
					</select>
							</td>
							<td nowrap="nowrap" width="110"><input type="text" name="MARKINGDATE" id="MARKINGDATE" size="10" maxlength="10" value="<c:out value='<%= markingBean.getMARKINGDATE()%>'/>"  ><input type="hidden" id="servDate" value="<c:out value='<%=serverDate %>'/>" /></td>
							<td nowrap="nowrap" colspan="2" width="165"><input type="text" name="TARGETDAYS" id="TARGETDAYS" size="2" maxlength="2" value="<c:out value='<%= markingBean.getTARGETDAYS()%>'/>" onblur="setTargetDate('<c:out value='<%=serverDate %>'/>', this);" onkeypress="chknumeric(47);" >
							<input type="text" class="readonly" size="10" maxlength="10" readonly name="TARGETDATE" id="TARGETDATE" value="<c:out value='<%= markingBean.getTARGETDATE()%>'/>"></td></tr>
						
						<%
						}
					%>
					
					
					</tbody>
					<tr >
						<td width="462"> <input type="button" name="btnADD" id="btnADD" value=" + " onclick="addrow(window.document.getElementById('baserow'));setTimeout(function(){ enable_autocomplete(); }, 1000);"
							class="butts1" title="Add Multiple Markings here." onblur="window.document.getElementById('REMARKS').focus();"></td>
						<td width="146"></td>
						<td width="134"></td>
						<td width="155"></td>
						<td width="110"></td>
						<td></td>
					</tr>
				</table></td>
					</tr>
					
			    		
			    		</table>	
				
				</fieldset>
				
				<fieldset>
				<table width="100%" id="data">
					<tr >
						<td nowrap="nowrap" colspan="2" rowspan="2" width="517">Remarks 
						<br>
							<textarea onkeypress="allowAlphaNum('()/*');" rows="2" cols="70"
								name="REMARKS" id="REMARKS" tabindex="20"><c:out value='<%= refBean.getREMARKS()%>'/></textarea></td><td nowrap="nowrap" width="95">Signed By</td>
			    		<td width="216">
							<select name="SIGNEDBY" id="SIGNEDBY" style="width: 120px" onchange="setOnDate(this, 'SIGNEDON');" tabindex="21"> 	 
							<option value=""> </option>
							<%
								for(int k=0;k<querySignedByList.size();k++){
								CommonBean beanCommon = (CommonBean) querySignedByList.get(k);
								 if (!refBean.getREFID().isEmpty()) {%>
									 <option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSIGNEDBY())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option> 
									<%} else { %>
									<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSIGNEDBY())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")==null)&&"440".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/> <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSIGNEDBY())?"selected":""%>'/><c:out value='<%=((request.getAttribute("refBean")!=null)&& refBean.getSIGNEDBY().equalsIgnoreCase("") && "440".equalsIgnoreCase(beanCommon.getField1()) ?"selected":"" ) %>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<% }
							}%>
							</select>
						</td>
			    		<td  nowrap="nowrap" width="59">Ack. By</td>
			    		<td width="150">
							<select name="ACKBY"   id="ACKBY"style="width: 120px" onchange="setOnDate(this, 'ACKDATE'); fillRemarks();" tabindex="23"> 	 
								<option value=""> </option>
								<%
									for(int k=0;k<queryAckByList.size();k++){
									CommonBean beanCommon = (CommonBean) queryAckByList.get(k);
									%>
										<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getACKBY())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
									<%
								}%>
							</select>
						</td>
					</tr>
			    	<tr ><td nowrap="nowrap" width="95">Signed On</td>
						<td nowrap="nowrap" width="216"><input type="text" name="SIGNEDON" id="SIGNEDON"  tabindex="22"
							value="<c:out value='<%= refBean.getSIGNEDON()%>'/>" onblur="chkWorkDate(this);"
							onkeypress="chknumeric(47);" maxlength="10"></td>
						<td nowrap="nowrap" width="59">Ack. on</td>
						<td nowrap="nowrap" width="150"><input type="text" name="ACKDATE" id="ACKDATE" tabindex="24"
							value="<c:out value='<%= refBean.getACKDATE()%>'/>" onblur="chkWorkDate(this);"  
							onkeypress="chknumeric(47);" maxlength="10"></td>
						
					</tr>
					<tr style="display:none" id="tourRem">
					<td nowrap="nowrap"  width="517">Tour/Parliament Remarks 
						<br>
							<textarea onkeypress="allowAlphaNum('()/*');" rows="2" cols="70"
								name="TOURREMARKS" id="TOURREMARKS" tabindex="20"><c:out value='<%= refBean.getTOURREMARKS()%>'/></textarea></td>
					
					</tr>
				</table>
				</fieldset>
				<fieldset>
				<table width="100%" id="data">
					<tr >
					<td nowrap="nowrap" width="120">Compliance</td>
			    		<td width="216">
							<select name="COMPLIANCE" id="COMPLIANCE" style="width: 120px" tabindex="20" onchange="complianceDateChange();" > 	 
							<option value="" selected>Select one- </option>
								<%
									for(int k=0;k<complianceCode.size();k++){
									CommonBean beanCommon = (CommonBean) complianceCode.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getCOMPLIANCE())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select>
						</td>
						<td nowrap="nowrap" colspan="2" rowspan="2" width="517">Compliance Remarks 
						<br>
							<textarea onkeypress="allowAlphaNum('()/*');" rows="2" cols="70"
								name="COMPREMARKS" id="COMPREMARKS" tabindex="20"><c:out value='<%= refBean.getCOMPLIANCEREMARKS()%>'/></textarea></td>
			    		<td width="216">
			    			<select  id="no_file_flag" name="no_file_flag"  onchange="setOnDate(this, 'COMPDATE');" tabindex="20">
								<option value="">  </option>
								<option value="18" <%= refBean.getFILESTATUS1().equalsIgnoreCase("18")?"selected":""%>> Fair Letter Signed (Disposed) </option>
								<option value="99" <%= refBean.getFILESTATUS1().equalsIgnoreCase("99")?"selected":""%> > Draft Reply Ready </option>
								<option value="98" <%= refBean.getFILESTATUS1().equalsIgnoreCase("98")?"selected":""%> > Reply Not Required </option>
							</select>
							
							
							
						</td>
			    		
					</tr>
			    	<tr >
						<td nowrap="nowrap" width="120">Compliance Date</td>
						<td nowrap="nowrap" width="150"><input type="text" name="COMPDATE" id="COMPDATE" tabindex="24"
							value="<c:out value='<%= refBean.getCOMPLIANCEDATE()%>'/>" onblur="chkWorkDate(this);"  
							onkeypress="chknumeric(47);" maxlength="10"></td>
						
					</tr>
					
				</table>
				</fieldset>
				<fieldset style=" border: 0px"><legend><b>Additional Information <input type="button" onclick="hideUnHideDiv()"  style="cursor: pointer;  width:20px; height:18px; background-image: url('${pageContext.request.contextPath}/images/next.gif'); " ></b></legend>
				<table id="data" width="100%">
				
				<tr  id="TAGS" style="display: none" style="display: none">
							<td>Zone</td>
							<td><input type="text" name="TAG1" id="TAG1" value="<c:out value='<%= refBean.getTAG1()%>'/>" tabindex="28" size="20" maxlength="20" onkeypress="allowAlphaNum('()&/*');"></td>
							<td>Division</td>
							<td><input type="text" name="TAG2"  id="TAG2" value="<c:out value='<%= refBean.getTAG2()%>'/>" tabindex="29" size="20" maxlength="20" onkeypress="allowAlphaNum('()&/*');"></td>
							<td>Station</td>
							<td><input type="text" name="TAG3" id="TAG3" value="<c:out value='<%= refBean.getTAG3()%>'/>" tabindex="30" size="20" maxlength="20" onkeypress="allowAlphaNum('()&/*');"></td>
							<td>Train</td>
							<td><input type="text" name="TAG4" id="TAG4" value="<c:out value='<%= refBean.getTAG4()%>'/>" tabindex="31" size="20" maxlength="20" onkeypress="allowAlphaNum('()&/*');"></td>
						</tr>
				
				<tr  id="ODS" style="display: none">
					<td>Place-1</td>
					<td><input type="text" name="ODSPLACE" id="ODSPLACE" value="<c:out value='<%= refBean.getODSPLACE()%>'/>" tabindex="25" onkeypress="allowAlphaNum('()&/*');"></td>
					
					<td>Place-1</td>
						<td><input type="text" name="ODSRAILWAY"  id="ODSRAILWAY"value="<c:out value='<%= refBean.getODSRAILWAY()%>'/>" tabindex="26" onkeypress="allowAlphaNum('()&/*');"></td>
						<td>Date</td>
						<td><input type="text" name="ODSDATE"  id="ODSDATE" value="<c:out value='<%= refBean.getODSDATE()%>'/>" onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10" tabindex="27"></td>
							<td></td>
							<td></td>
						</tr>
						
					</table>
				</fieldset>
						
			    	
			    </div>
			    <div id="MainBudget">
			    <table cellpadding="0" cellspacing="0" border="0" width="100%" style="margin: 0px">
			    	<tr>
			    		<th width="50%" align="left" valign="middle" height="30" style="border: 0px">
						    			<b><c:out value='<%= refBean.getREFNO() %>'/> </b>
						</th>	
						<th width="50%" align="right" style="border: 0px">
							<b><c:out value='<%= refBean.getREFERENCENAME() %>'/> </b>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<b><c:out value='<%= refBean.getVIPSTATUS() %>'/> </b>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<b><c:out value='<%= refBean.getSTATECODE() %>'/> </b>
						</th>		    
			    	</tr>
			    </table>
			    <fieldset>
				<table width="100%">
				<tr >
				
					<th>Budget No. <input type="hidden" name="btnClickBDG" id="btnClickBDG"></th>
					<th>Sub.</th>
					<th>Subject</th>
					<th>MR's Remarks</th>
					<th>Remarks</th>
					<th></th>
				</tr>
				<TBODY id="baserowBDG" >
					<tr  style="display: none">
					<td>
						<input type="hidden" name="BDGREFID" id="BDGREFID" value="<c:out value='<%= refBean.getREFID()%>'/>" size="5" readonly="readonly">
						<input type="text" name="BUDGETSEQUENCE" id="BUDGETSEQUENCE" value="" size="5" readonly class="readonly" >	
					</td>
					<td nowrap="nowrap">
						<select name="BDGSUBJECTCODE" id="BDGSUBJECTCODE" style="width: 120px"> 
							<option value=""> </option>
							<%
								for(int j=0;j<bdgSubjectList.size();j++){
								CommonBean beanCommon = (CommonBean) bdgSubjectList.get(j);
							%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select>
					</td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGSUBJECT" id="BDGSUBJECT" onkeypress="return MaxLength(this, 399); allowAlphaNum('()/');"></textarea></td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGMRREMARK"  id="BDGMRREMARK" onkeypress="return MaxLength(this, 399);allowAlphaNum('()/');"></textarea></td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGREMARK"  id="BDGMRREMARK" onkeypress="return MaxLength(this, 399);allowAlphaNum('()/');"></textarea></td>
					<td nowrap="nowrap">
					<input type="button" name="btnDelBudget" id="btnDelBudget" onclick="delBudgetRow(this,'<c:out value="<%= refBean.getREFID()%>"/>','', window.document.getElementById('baserowBDG'));"
											class="delbutt" title="Delete Budget Item" >
					<!-- <img src="images/error.gif" width="15" height="15" name=btnDelBudget" style="cursor: pointer;" 	onclick="delBudgetRow(this,'<c:out value="<%= refBean.getREFID()%>"/>','', window.document.getElementById('baserowBDG'));"></td>  -->
				</tr>
				
				<% for(int i=0;i< refBean.getBudgetBeanList().size();i++){
				TrnBudget budgetBn = (TrnBudget) refBean.getBudgetBeanList().get(i);
				%>
					<tr  >
					<td><input type="hidden" name="BDGREFID" id="BDGREFID" value="<c:out value='<%= refBean.getREFID()%>'/>" size="5" readonly="readonly">
						<input type="text" name="BUDGETSEQUENCE" id="BUDGETSEQUENCE" value="<c:out value='<%= budgetBn.getBUDGETSEQUENCE()%>'/>" size="5" readonly class="readonly">	
					</td>
					
					<td nowrap="nowrap">
						<select name="BDGSUBJECTCODE" id="BDGSUBJECTCODE"  style="width: 120px"> 
							<option value=""> </option>
							<%
								for(int j=0;j<bdgSubjectList.size();j++){
								CommonBean beanCommon = (CommonBean) bdgSubjectList.get(j);
							%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(budgetBn.getSUBJECTCODE())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select>
					</td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGSUBJECT"  id="BDGSUBJECT" onkeypress="return MaxLength(this, 399);allowAlphaNum('()/');"><c:out value='<%= budgetBn.getSUBJECT()%>'/></textarea></td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGMRREMARK"  id="BDGMRREMARK" onkeypress="return MaxLength(this, 399);allowAlphaNum('()/');"><c:out value='<%= budgetBn.getMRREMARK()%>'/></textarea></td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGREMARK"  id="BDGREMARK" onkeypress="return MaxLength(this, 399);allowAlphaNum('()/');"><c:out value='<%= budgetBn.getREMARK()%>'/></textarea></td>
					<td nowrap="nowrap">
					<input type="button" name="btnDelBudget" id="btnDelBudget" onclick="delBudgetRow(this,'<c:out value="<%= refBean.getREFID()%>"/>','<c:out value="<%= budgetBn.getBUDGETSEQUENCE()%>"/>', window.document.getElementById('baserowBDG'));"
											class="delbutt" title="Delete Budget Item" >
					<!-- 						
					<img src="images/error.gif" width="15" height="15" name=btnDelBudget" style="cursor: pointer;" 
									onclick="delBudgetRow(this,'<c:out value="<%= refBean.getREFID()%>"/>','<c:out value="<%= budgetBn.getBUDGETSEQUENCE()%>"/>', window.document.getElementById('baserowBDG'))"></td>
					 -->				
				</tr>
				<%}%>
			</TBODY>
				<tr >
					<td>
						<input type="button" name="btnADD" id="btnADD" value=" + " onclick="addrow(window.document.getElementById('baserowBDG'));"	class="butts1" title="Add Multiple Markings here."></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				</fieldset>
				<fieldset>
				<table id="data" width="100%">
			    				<tr >
									<td align="center">
									<%if(request.getAttribute("refBean")!=null){ %>
										<input type="button" name="btnBDG" id="btnBDG" style="display: none;" value="Save Budget" class="butts1" onclick="remoteSaveBudget();">   
									<%} %>
									</td>
								</tr>
			    			</table>
				
				</fieldset>
			    
			    
			    </div>

			    
			    <div id="MainReminder">
			    <table width="100%">
			    	<tr>
			    		<th width="100%" align="left" valign="middle" height="30"">
			    			<b><c:out value="<%= refBean.getREFNO() %>"/> </b>
			    		</th>
			    	</tr>
			    </table>
			    <fieldset><legend><font color="brown">Previous Reminder Sent :</font></legend>
					<table id="data">
					<% if(reminderArr.size()>0) {%>
						<tr  > 
						<th>#</th>
			            <th> Reminder Type</th>
			            <th>Reminder On</th>
			            <th>Adjective</th>
			            <th>Signed By</th>
			            <th>Signed On</th>
			            <th>Sent To</th>
			         <tr>
					<%} %>	
						<%
						//System.out.println(" reminderArr size " +reminderArr.size());
						
						
					for (int i = 0; i < reminderArr.size(); i++) {
						CommonBean cmnbn = (CommonBean) reminderArr.get(i);
						
						//System.out.println(" CommonBean 1size ["+i +"]" +cmnbn.getField1());
						//System.out.println(" CommonBean 3size " +cmnbn.getField2());
						//System.out.println(" CommonBean 3size " +cmnbn.getField3());
						//System.out.println(" CommonBean 4 " +cmnbn.getField4());
						
				    %>
					<tr  >  
					    <td align="right"><%=i+1%>.</td>
			            <td nowrap="nowrap" align="center">
			            	<c:out value="<%=cmnbn.getField7() %>"/>
			            </td>
			            <td nowrap="nowrap"><c:out value="<%=cmnbn.getField2() %>"/></td>   
			            <td nowrap="nowrap"><c:out value="<%=cmnbn.getField3() %>"/></td>
			            <td nowrap="nowrap"><c:out value="<%=cmnbn.getField8() %>"/>			            
			            <input type="hidden" name="REMINDERFROM" id="REMINDERFROM" size="11" value="<c:out value='<%=sessionBean.getLOGINASROLEID()%>'/>"/></td>
						<td nowrap="nowrap"><c:out value="<%=cmnbn.getField9() %>"/></td>
						<td><c:out value='<%=cmnbn.getField10()%>'/></td>
			         </tr>
						<%} %>
					</table>
					</fieldset>
					<br>
					<fieldset><legend><font color="brown">Send New Reminder :</font></legend>
			      	<table id="data">
			         <tr > 
			            <th>Reminder Type</th>
			            <th>Reminder On</th>
			            <th colspan="2">Remarks</th>
			            <th>Signed By</th>
			            <th>Signed On</th>
			            <th></th>
			         <tr>
			         <tr >  
			            <td nowrap="nowrap" align="center">
			               <select name="REMINDERTYPE" id="REMINDERTYPE" style="width: 120px; height: 20px" size="20">  
			                  <option value="5" selected>Sender</option>
			               </select>
			            </td>
			            <td nowrap="nowrap"><input type="text" value="<c:out value='<%=serverDate%>'/>" id="REMINDERDATE" name="REMINDERDATE" size="11"/></td>   
			            <td nowrap="nowrap" colspan="2" rowspan="2"><textarea rows="2" cols="40" id="REMINDERREMARKS" name="REMINDERREMARKS" onkeypress="allowAlphaNum('()/*'); return MaxLength(this, 399);"><c:out value='<%= remBean.getREMARKS()%>'/></textarea></td>
			            <td nowrap="nowrap"><select name="REMINDERSIGNEDBY" id="REMINDERSIGNEDBY"  style=" text-align:center; width: 120px; height: 50px" size="20"> 
						<option value="" SELECTED> </option>
						<%
							for(int i=0;i<querySignedByList.size();i++){
							CommonBean beanCommon = (CommonBean) querySignedByList.get(i);
							%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%
						}%>
					</select><input type="hidden" name="REMINDERFROM" id="REMINDERFROM" size="11" value="<c:out value='<%=sessionBean.getLOGINASROLEID()%>'/>"/></td>
						<td nowrap="nowrap"><input type="text" value="<c:out value='<%=serverDate%>'/>" id="REMINDERSIGNEDON" name="REMINDERSIGNEDON" size="11"></td>
						<td><%if(request.getAttribute("refBean")!=null){ %>
						<input type="button" name="btnREM" id="btnREM" value="Send" class="butts1" onclick="saveRemind();"/>
						<%} %></td>
			         </tr>
			      </table>
			      </fieldset>
			    </div>
			    
			    

			    
			    
			    <div id="MainReturned">
			    
			    <table width="100%">
			    	<tr>
			    		<th width="100%" align="left" valign="middle" height="30"">
			    			<b><c:out value='<%= refBean.getREFNO() %>'/> </b>
			    		</th>
			    	</tr>
			    </table>
			    
			    <fieldset>
			    <table width="100%">
					<tbody>
						<tr >
							<th>Ref. No. </th>
							<th>Returned By </th>
							<th>Return  On</th>
							<th>Sub.</th>
							<th>Subject</th>
							<th>Remarks(Return)</th>
							<th>Mark To</th>
							<th>Remarks</th>
							<th>Action</th>
						</tr>
						
						<%
						for(int i=0; i<returnBoxList.size();i++){
							TrnMarking returnBoxBean = returnBoxList.get(i);
							%>
						<tr >
							<td><c:out value='<%=returnBoxBean.getREFNO() %>'/></td>
							<td><c:out value='<%=returnBoxBean.getMARKINGFROMNAME() %>'/></td>
							<td><c:out value='<%=returnBoxBean.getACTIONDATE() %>'/></td>
							<td><c:out value='<%=returnBoxBean.getSUBJECTCODE() %>'/></td>
							<td><c:out value='<%=returnBoxBean.getSUBJECT() %>'/></td>
							<td><c:out value='<%=returnBoxBean.getMARKINGREMARK() %>'/></td>
							<td> <select name="RETURNMARKTO"  id="RETURNMARKTO" style="width: 100px">
									<option value="" selected="selected"> </option>
									<%
										for(int ii=0;ii<markingToList.size();ii++){
										CommonBean beanCommon = (CommonBean) markingToList.get(ii);
										%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
										<%
									}%>
							</select>
							</td>
							<td><input type="text" name="RETURNMARKINGREMARK"  id="RETURNMARKINGREMARK" size="40" onkeypress="allowAlphaNum('()/');"></td>
							
							<td> <select name="RETURNACTION"  id="RETURNACTION" style="width: 100px">
								<option value="FOW" <c:out value='<%="FOW".equalsIgnoreCase(returnBoxBean.getACTION())?"selected":""%>'/>>Accept &amp; Forward</option>
								
							</select><input type="button" value="confirm" class="butts" onclick="setReturnBoxAction('<c:out value="<%=returnBoxBean.getREFID()%>"/>', '<%=i%>', '<c:out value="<%=returnBoxBean.getMARKINGFROM() %>"/>', '<c:out value="<%=returnBoxBean.getTARGETDATE()%>"/>', '<c:out value="<%=returnBoxBean.getSUBJECTCODE()%>"/>', '<c:out value="<%=returnBoxBean.getSUBJECT()%>"/>');" ></td>
						</tr>
							<%
						}
						%>
						
					</tbody>
				</table>
			    </fieldset>
			    </div>
			    <div id="MainFinalStatus">
			    
			    <table width="100%">
			    	<tr>
			    		<th width="100%" align="left" valign="middle" height="30"">
			    			<b><c:out value='<%= refBean.getREFNO() %>'/> </b>
			    		</th>
			    	</tr>
			    </table>
			    
			    	<fieldset>
					<table width="100%">
							<tr >
								<td width="20%" >File Regn. No.</td>
								<td width="10%"><input type="text" name="REGISTRATIONNO" id="REGISTRATIONNO" size="20" value="<c:out value='<%=refBean.getREGISTRATIONNO()%>'/>" 
												 readonly class="readonly"></td>
								<td width="15%" align="right">File Regn. Date</td>
								<td width="65%"><input type="text" name="REGISTRATIONDATE"
								id="REGISTRATIONDATE" value="<c:out value='<%=refBean.getREGISTRATIONDATE()%>'/>"
								onblur="chkWorkDate(this);" onkeypress="chknumeric(47);"
								maxlength="10" size="14"></td>
								
							</tr>
							<%-- <tr >
								<td width="10%">File No.</td>
								<td width="10%">
								<input type="text" name="FILENO" id="FILENO" size="40" maxlength="40" style="text-transform: uppercase;"
															 onkeypress="allowAlphaNumWithoutSpace('/');" 
															 value="<%=refBean.getFILENO()%>"></td>
															 
								<td width="15%" align="right">E-Office File No.</td>							 
								<td width="65%"><input type="text" name="EofficeFILENO" id="EofficeFILENO" size="40" maxlength="40" style="text-transform: uppercase;"
								 onkeypress="allowAlphaNumWithoutSpace('/');" value="<%=refBean.getEOFFICEFILENO()%>"></td>
							</tr> --%>
							
							
					</table>
					</fieldset>
					<fieldset>
							<legend > <b>File Position</b></legend>
							<table width="100%">
							<tr >
								<td width="11%">Internal marking</td>
								<td width="16%">
								<select name="IMARKINGTO" id="IMARKINGTO" style="width: 120px">  
									<option value=""> </option>
									<%
										for(int i=0;i<markingToList.size();i++){
										CommonBean beanCommon = (CommonBean) markingToList.get(i);
										%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getIMARKINGTO())?"selected":""%>'/> ><c:out value='<%=beanCommon.getField2()%>'/></option>
										<%
									}%>
								</select>
								
								</td>
							<td width="9%">Marking On</td>
						<td width="106"><input type="text" name="IMARKINGDATE" id="IMARKINGDATE" value="<c:out value='<%=refBean.getIMARKINGON()%>'/>"
								onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10" size="14"></td>
						<td width="571">Return On : <input type="text" name="ICHANGEDATE" id="ICHANGEDATE" value="<c:out value='<%=refBean.getCHANGEDATE()%>'/>"
								onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10" size="14"></td>
								
					</tr>
							<tr >
								<td width="11%">Status</td>
								<td colspan="2">
																		
									<select name="FILESTATUS1" onchange="setStatusRemark();"
								id="FILESTATUS1"   >
								<option value="" selected> </option>
								<%
									for(int k=0;k<fileStatus1.size();k++){
									CommonBean beanCommon = (CommonBean) fileStatus1.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getFILESTATUS1())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select>	
									<select name="FILESTATUS2" onchange="setStatusRemark();"
								id="FILESTATUS2" >
								<option value="" selected > </option>
								<%
									for(int k=0;k<fileStatus2.size();k++){
									CommonBean beanCommon = (CommonBean) fileStatus2.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getFILESTATUS2())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select>	
									
								</td>
								<td width="106">Action Taken</td>
								
								
								
						<td width="571"><input type="text" name="STATUSREMARK" id="STATUSREMARK" size="60" value="<c:out value='<%=refBean.getSTATUSREMARK()%>'/>" onkeypress="allowAlphaNum('()/');"></td>
					</tr>
							</table>
							
						</fieldset>
					
					<fieldset>
							<table width="100%">
							<tr >
								<td width="10%">Reply Type</td>
								<td width="10%">
								<select name="REPLYTYPE" id="REPLYTYPE" style="width: 120px">
								<option value=""> </option>
								<%
									for(int k=0;k<replyCode.size();k++){
									CommonBean beanCommon = (CommonBean) replyCode.get(k);
									%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
									<c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getREPLYTYPE())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
								}%>
							</select>
								</td>
								<td width="15%">Reason </td>
								<td width="65%">
									<textarea rows="2" cols="60" name="REASON" id="REASON" onkeypress="allowAlphaNum('()/');"><c:out value='<%= refBean.getREASON()%>'/></textarea>
									<!-- <input type="text" name="REASON" size="20" value="<c:out value='<%=refBean.getREASON()%>'/>">  -->
								</td>
							</tr>
							<tr >
								<td width="10%">Mark To</td>
								<td width="10%">
								<select name="DMARKINGTO" id="DMARKINGTO" style="width: 120px">  
									<option value=""> </option>
									<%
										for(int i=0;i<markingToList.size();i++){
										CommonBean beanCommon = (CommonBean) markingToList.get(i);
										%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getDMARKINGTO())?"selected":""%>'/> ><c:out value='<%=beanCommon.getField2()%>'/></option>
										<%
									}%>
								</select>
								
								</td>
								<td width="15%">Mark  On</td>
								<td width="65%"><input type="text" name="DMARKINGDATE" id="DMARKINGDATE" size="20"
												onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10" 
												 value="<c:out value='<%=refBean.getDMARKINGDATE()%>'/>"></td>
							</tr>
							</table>
							
						</fieldset>	
					<fieldset>
						<table id="data" width="100%">
			    				<tr >
									<td align="center">
										<%if(request.getAttribute("refBean")!=null && refBean.getFMID().length()==0){ %>
										<input type="button" name="btnSaveStatus" id="btnSaveStatus" value="Save Status" class="butts1" onclick="remoteSaveFileStatus();" style="display:none">
										<%} %>  
									</td>
								</tr>
			    		</table>
				
				</fieldset>
					
				
				</div>	
				 <div id="fileReply" >
			    <table width="1000">
 					<tr>
  						<td valign="top">
							
			<div style="width: 100%;height:350px;overflow: scroll;overflow-x:hidden;background-color: white; "><c:out value='<%=refBean.getREPLY() %>'/></div>
		
				</td>
				</tr>
				</table>		
			    
			    
			    </div>
				<div id="MainAttachment">
			      <table width="100%">
			    	<tr>
			    		<th width="100%" align="left" valign="middle" height="30"">
			    			<b>&nbsp;<c:out value='<%= refBean.getREFNO() %>'/></b>
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
									<th  align="center" width="20%">Delete</th>
								</tr>
							<%} %>	
								<tbody id="refimage">
								<%
								System.out.println(filepathArr.size());
								for (int i = 0; i < filepathArr.size(); i++) {
									CommonBean cmnbn = (CommonBean) filepathArr.get(i);
						    	%>
								<tr>
									<td width="507" align="left"><a href="#" onclick="openAttachment('<c:out value="<%=cmnbn.getField1()%>"/>','<c:out value="<%=cmnbn.getField2()%>"/>','<c:out value="<%=cmnbn.getField3()%>"/>','<c:out value="<%=cmnbn.getField4()%>"/>','<c:out value="<%=cmnbn.getField5()%>"/>','<c:out value="<%=cmnbn.getField6()%>"/>');"><font color="blue"><c:out value="<%=cmnbn.getField3()%>"/></font></a></td>
									<td align="center">
									<% if("R".equalsIgnoreCase(cmnbn.getField6())) { %>
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
							<input type="hidden" name="ATTACHMENTREFID" id="ATTACHMENTREFID" value="<c:out value='<%=refBean.getREFID()%>'/>">
							<input size="30" type="file" class="drop" name="uploadImage"  id="uploadImage" accept="jpg">
						</td>
					</tr>
				</tbody>
				<tr class="trodd">
					<td colspan="6">
						<%if(request.getAttribute("refBean")!=null){ %>
						<input class="butts" type="button" name="attachbutt" id="attachbutt"  value="Add pdf/jpg" style= "width: 100px" onclick="addrowAttach();">(Upload .JPG or .PDF files only.)
						<%} %>
						<input name = "cntimg" id = "cntimg" type = "hidden" value="0">
					</td></tr>
			</table>
			</fieldset>
			 </div>
			 
			<div id="MainStickyNote" >
			    <table width="100%">
			    	<tr>
			    		<th width="100%" align="left" valign="middle" height="30"">
			    			<b><c:out value='<%= refBean.getREFNO() %>'/> </b>
			    		</th>
			    	</tr>
			    </table>
			    <table style="margin-top: 0px" width="510px">
			    	<tr>
			    		<td align="center" style="padding-left: 20px;" valign="middle" background="images/sticky.gif" rowspan="2" width="319">
						<fieldset style="width: 240px;">
    					<textarea style="border-width: 0; background-color: transparent; overflow: auto;"  rows="15" cols="37" name="TXT_NOTE" id="TXT_NOTE" onkeypress="allowAlphaNum('()/');"><c:out value='<%= refBean.getTXT_NOTE() %>'/></textarea>
			    		</fieldset>
			    		</td>
			    		<td valign="bottom" width="185">
			    			Signed By
			    		</td>
			    	</tr>
						<tr>
							<td valign="top" height="327" width="185">
							<select name="SIGNEDBY_YS" id="SIGNEDBY_YS" style="width: 180px; height: 20px;"> 	 
							<option value=""> </option>
							<%
								for(int k=0;k<querySignedByList.size();k++){
								CommonBean beanCommon = (CommonBean) querySignedByList.get(k);
								%>
									<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSIGNEDBY_YS())?"selected":""%>'/>><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
							}%>
							</select>
							</td>
						</tr>
					</table>
			    </div>
			    <div id="MainForwardChain">
			    <table width="100%">
			    	<tr>
			    		<th width="100%" align="left" valign="middle" height="30"">
			    			<b><c:out value='<%= refBean.getREFNO() %>'/> </b>
			    		</th>
			    	</tr>
			    </table>
			    
			    <fieldset>
			    <table>
 					<tr>
  						<td valign="top">
							<div id="treediv" class="dtree">
								<script type="text/javascript">
									var col = new Array("#d69203","#927344","#ac5e09","#805e38","#451800","#d69203","#927344","#ac5e09","#805e38","#451800");
									
									d = new dTree('d');
									
									<%for (int kk = 0; kk < arrTree.size(); kk++) {
										CommonBean bn = (CommonBean) arrTree.get(kk);
										%>
											<c:out value='<%=bn.getField1()%>' escapeXml="False" />
										<%}
									%>
									
									
									document.write(d);
									d.openAll();
								</script>
							</div>
				</td>
				</tr>
				</table>		
			    </fieldset>
			    
			    </div>
			    <div id="MainReminderChain">
			    <table width="100%">
			    	<tr>
			    		<th width="100%" align="left" valign="middle" height="30"">
			    			<b><c:out value='<%= refBean.getREFNO() %>'/> </b>
			    		</th>
			    	</tr>
			    </table>
			    
			    <fieldset>
			    	<table>
 					<tr>
  						<td valign="top">
							<div id="treediv" class="dtree">
								<script type="text/javascript">
									var col = new Array("#d69203","#927344","#ac5e09","#805e38","#451800","#d69203","#927344","#ac5e09","#805e38","#451800");
									
									d1 = new dTree('d1');
									
									<%for (int kk = 0; kk < arrTreeReminder.size(); kk++) {
										CommonBean bn = (CommonBean) arrTreeReminder.get(kk);
										%>
											<c:out value='<%=bn.getField1()%>' escapeXml="False"/>
										<%}
									%>
									
									
									document.write(d1);
									d.openAll();
								</script>
							</div>
				</td>
				</tr>
				</table>	
			    </fieldset>
			    </div>
			    <table  align="center">
			    		<tr >
							<td align="center" valign="top">
								<input type="button" name="btnSAVE" id="btnSAVE" class="butts" value=" Save " onclick="submitForm('SAVE');" tabindex="25">
								</td>
							<td align="center" valign="top">
								<input type="button" name="btnCLEAR" id="btnCLEAR" class="butts" value=" Clear " onclick="submitForm('CLEAR');" tabindex="26"></td>
							<td align="center" valign="top">
								<INPUT type="button" name="mail" id="mail" value=" E-Mail " style="display: none" onclick="submitForm('EMAIL');"></td>
							<td align="center" valign="top">
								<INPUT type="text" name="MAILID" id="MAILID" style="display: none"  size=30 value="rohitjaincse@gmail.com"></td>
						</tr>
			    	</table>
			    </div>
		</td>
	</tr>
	</tbody>
</table>
</td>
</tr>
</table>
<!-- DO NOT DELETE BELOW THIS!!! Following will create model. It will get uncommented once jsp is processed. -->

<!--main Content Area Ends--> <!--  <%=" Model Starts "%>-->

<DIV class="transparent_class" align="center" style="z-index:2000;  background-color:#000001; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 150%;" id="updateDiv">
	
</DIV>
<DIV style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:20px; top: 160px; display: none; width: 100%" id="updateDivInner">





<DIV class="pageheader" style="color: blue; font-family:tahoma ; z-index:5000; background-color: #FBD6B5; width: 400px; border: groove; text-align: center; background-image:url(); background-repeat: repeat; " id="divLoading">

<%	if(msg.length()>3) { 
	String s[] = msg.split("/-");
	int slen = s.length;
	%>

			<BR><img src="images/<c:out value='<%= msg.substring(0,3)%>'/>.gif"/><c:out value='<%= s[0] %>' escapeXml="false"/>
			 <BR><input name="btnOK" id="btnOK" type="button"  class="butts" value=" Close " onclick="callMe()"><input name="btnOKAttach" id="btnOKAttach" type="button" class="butts" value=" Continue to attach file " onclick="callMeAttach()">
			
			<%for(int i=2; i<slen;i=i+2){ %>
			<BR><c:out value='<%= s[i] %>' escapeXml="false"/>
			<%} %>
			<BR><BR>
<% } %>






</DIV> 
</DIV >
<DIV style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:20px; top: 160px; display: none; width: 100%" id="CommonDivInner">
<DIV class="pageheader" style="color: blue; font-family:tahoma ; z-index:5000; background-color: #FBD6B5; width: 500px; border: groove; text-align: center; background-image:url(); background-repeat: repeat; " id="CommondivLoading">
<div id='CommonMsg'></div>
			<input name="CommonbtnOK" id="CommonbtnOK" type="button" value=" Ok " onclick="CommoncallMe()">
			<BR><BR>
</DIV> 
</DIV >
<div id="divRefNameResult"  style="display:none;color: blue; font-family:tahoma ; position: absolute; left:250px; top: 210px; z-index:5000; background-color: white; width: 80%; border: groove; text-align: center; background-image:url(); background-repeat: repeat; ">
</div>

<DIV align="center"  style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:180px; top: 20px; display: none; width: 50%;height: 50%;" id="MailDivInner">
<DIV align="center" style="color:black; font-family:tahoma ; background-color: white; width: 730px; text-align: center;  " id="divLoading">
<BR>
<% if(refBean.getLANGUAGE().equalsIgnoreCase("1")) { %>
<TABLE WIDTH="100%" BORDER=1 CELLPADDING=0 CELLSPACING=0 align="center">
	<tr><td align="center"><br />
		<input type="button" name="withMail" id="withMail" value="Save with Mail" style="height: 23px;" onclick="submitPage('EMAIL');"/>
		<input type="button" name="withOutMail" id="withOutMail" value="Save without Mail" style="height: 23px;" onclick="submitPage('SAVE');"/>
		<INPUT	name="btnRESET1" id="btnRESET1" type="button"  value="  Cancel  " onclick="hideMailDiv()" style="height: 23px;"/><br /></td></tr>
	<TR><TD VALIGN="TOP" ALIGN="CENTER">
							 <TABLE WIDTH="100%" BORDER=0 CELLPADDING=0 CELLSPACING=0 style="font-size: 14; font-family: TAHOMA">
							 <TR><TD ALIGN="CENTER" COLSPAN=4><IMG SRC="images/letterHeader.jpg" WIDTH=726 HEIGHT=165></TD></TR>
							 <TR><TD ALIGN="CENTER" COLSPAN=4><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD ALIGN="CENTER" COLSPAN=4><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD align="LEFT" width="623"><c:out value='<%= refBean.getREFNO() %>'/></TD><TD ALIGN="RIGHT" > <c:out value='<%= serverDate %>'/></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD align="LEFT" width="623"></TD><TD ALIGN="RIGHT" ></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD align="LEFT" width="623"></TD><TD ALIGN="RIGHT" ></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD align="LEFT" width="623"></TD><TD ALIGN="RIGHT" ></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT">Dear <c:out value='<%= refBean.getREFERENCENAME() %>'/></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I am in receipt of your letter dated <c:out value='<%= refBean.getLETTERDATE() %>'/> regarding <c:out value='<%= refBean.getSUBJECT() %>'/></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I am having the matter looked into.</TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;With regards,</TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right">Yours sincerely,</TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right">( Mukul Roy )</TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="right"></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"><c:out value='<%= refBean.getREFERENCENAME() %>'/></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"><c:out value='<%= refBean.getVIPSTATUS() %>'/></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 <TR><TD width="10"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align="LEFT"><c:out value='<%= refBean.getSTATECODE() %>'/></TD><TD width="29"><IMG SRC="images/left_spacer.jpg" WIDTH=35 HEIGHT=20></TD></TR>
							 </TABLE>
	</TD></TR>
	</TABLE>
<%}else { %>
							<TABLE WIDTH="100%" BORDER=1 CELLPADDING=0 CELLSPACING=0 align="center">
	<tr><td align="center"><br />
		LETTER FORMAT NOT AVAILABLE IN SELECTED LANGUAGE<br />
		<input type="button" name="withOutMail" id="withOutMail" value="Save without Mail" style="height: 23px;" onclick="submitPage('SAVE');"/>
		<INPUT	name="btnRESET1" id="btnRESET1" type="button"  value="  Cancel  " onclick="hideMailDiv()" style="height: 23px;"/><br /></td></tr>
	</TABLE>
<%} %>	
</DIV>
</DIV>

<%="<!-- Model Ends "%>    --> <!-- DO NOT ABOVE BELOW THIS!!! -->
<%session.removeAttribute("msg"); %>
<%session.removeAttribute("sbean"); %>
<%session.removeAttribute("refClass"); %>
</form>

</body>
<script>
<!--
$(document).ready(function(){
   dwr.util.useLoadingMessage();
	enable_autocomplete();
	displayKeyword(document.getElementsByName("SUBJECTCODE")[1],false);
  });





function enable_autocomplete() {
$("input[name='keyword1']:last").css("border", "red 1px dotted");
    $("input[name='keyword1']:last").autocomplete("getStations.jsp", {scroll:false});
   $("input[name='keyword1']:last").result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			var idx = getIndex(this);
			document.getElementsByName('keyword1')[idx].value = dataarr[0];
			document.getElementsByName('k1')[idx].value = dataarr[0];
		}
	});
	
	$("input[name='keyword2']:last").css("border", "red 1px dotted");
	$("input[name='keyword2']:last").autocomplete("getStations.jsp", {scroll:false});
   $("input[name='keyword2']:last").result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			var idx = getIndex(this);
			document.getElementsByName('keyword2')[idx].value = dataarr[0];
			document.getElementsByName('k2')[idx].value = dataarr[0];
		}
	});
	
	$("input[name='keyword3']:last").css("border", "red 1px dotted");
	$("input[name='keyword3']:last").autocomplete("getTrain.jsp", {scroll:false});
   $("input[name='keyword3']:last").result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			var idx = getIndex(this);
			document.getElementsByName('keyword3')[idx].value = dataarr[0];
			document.getElementsByName('k3')[idx].value = dataarr[0];
		}
	});
	
	
}


function displayKeyword(obj,resetval){
if(resetval)
$(obj).closest("tr").find("td:eq(0) span input").each(function(){$(this).val("")});

$(obj).closest("tr").find("td:eq(0) span:eq(0)").css("display","none");
$(obj).closest("tr").find("td:eq(0) span:eq(1)").css("display","none");
$(obj).closest("tr").find("td:eq(0) span:eq(2)").css("display","none");
 CommonDAO.getSubjectKeywords(obj.value,
 					function(data){
          for(var i=0;i<data.length;i++){
          	//alert(data[i].field2.replace( /^\D+/g, ''));
          	$(obj).closest("tr").find("td:eq(0) span:eq("+(parseInt((data[i].field2.replace( /^\D+/g, '')),10)-1)+")").css("display","inline");
          }
 
 });

}
-->
</script>
<script>
function myFunction() {
	 
	var refno = document.getElementById("REFNO").value;
	var tenureid = document.getElementById("TENUREID").value;
	//var tenureid='<c:out value="<%= sessionBean.getTENUREID()%>"/>';
	// alert(refno);
	var link = "${pageContext.request.contextPath}/filemovement.jsp?q="+refno+"&tenure="+tenureid;
	var myWindow = window.open(link);
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

function hideDivRef(obj){

	document.getElementById("divRefNameResult").style.display='none';
//document.getElementById("refDetailImg").style.display='block'
}
function hideDivRefStatus(obj){
var tabindex=document.activeElement.tabIndex; 
if(tabindex=='10'){
document.getElementById("divRefNameResult").style.display='none';
}
//document.getElementById("refDetailImg").style.display='block'
}
function setMarkingDateToCurrent(){
	//alert();
	var x = document.getElementById("servDate").value;
	//alert(x)
			document.getElementsByName("MARKINGDATE")[1].value=x;
	//alert(document.getElementById("MARKINGDATE").value);
	//document.getElementById("MARKINGDATE").value= x;
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

</script>
<% session.setAttribute("attachFlag", "0"); %>
</html>