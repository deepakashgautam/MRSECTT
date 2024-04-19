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
<%@page import="in.org.cris.mrsectt.Beans.TrnBudget"%><html>
<head>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dateFormatter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.min.js"></SCRIPT>

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/TrnReferenceDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<LINK  href="${pageContext.request.contextPath}/theme/gmailStyleMenu/gmailStyleMenu.css" rel="stylesheet" type="text/css"> 
<LINK  href="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />

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
</style>

  <script type="text/javascript">
  
  <% 
  
  	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
  	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();
  	
  	TrnReference refBean =  (request.getAttribute("refBean")!=null) ?(TrnReference)request.getAttribute("refBean"): new TrnReference();
  	TrnReference remBean =  (request.getAttribute("remBean")!=null) ?(TrnReference)request.getAttribute("remBean"): new TrnReference();
  	
  	
  	
  	if(refBean.getINCOMINGDATE().length()==0){
  		refBean.setINCOMINGDATE(CommonDAO.getSysDate("dd/MM/yyyy"));
  	}
  	
  	if(refBean.getLETTERDATE().length()==0){
  		refBean.setLETTERDATE(CommonDAO.getSysDate("dd/MM/yyyy"));
  	}
  
  	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
  	
  	//code for showing alteast one row in the marking detail table
      if(refBean.getMarkingBeanList().size()==0){
          TrnMarking tMark =  new TrnMarking();
          tMark.setMARKINGDATE(serverDate);
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
  	
  	
  	
  	String queryRefClass = "SELECT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
	
	String queryRefCCategory = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00'"; 
	ArrayList<CommonBean> refCategoryList = (new CommonDAO()).getSQLResult(queryRefCCategory, 3);

	String queryUrgency = "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00'"; 
	ArrayList<CommonBean> urgencyList = (new CommonDAO()).getSQLResult(queryUrgency, 3);
	
	String queryLanguage = "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '3' AND CODE<>'00'"; 
	ArrayList<CommonBean> languageList = (new CommonDAO()).getSQLResult(queryLanguage, 3);

 	String querySecurityGrading = "SELECT CODE, SNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '2' AND CODE<>'00'"; 
	ArrayList<CommonBean> SecurityGradingList = (new CommonDAO()).getSQLResult(querySecurityGrading, 3);

 	String queryState = "SELECT STATECODE, STATENAME FROM MSTSTATE"; 
	ArrayList<CommonBean> stateList = (new CommonDAO()).getSQLResult(queryState, 2);

 	String querySubject = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 3);

 	String queryMarkingTo = " SELECT B.TENUREID, (SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.ROLEID ) ROLENAME "+ 
 							" FROM MSTTENURE B WHERE B.ISACTIVE = 'Y' AND TENUREID <> '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);

 	String querySignedBy = " SELECT B.ROLEID, (SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.ROLEID ) ROLENAME "+ 
 							" FROM MSTTENURE B WHERE B.ISACTIVE = 'Y'"; 
	ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);
	
//	ArrayList<TrnMarking> inboxList = (new TrnReferenceDAO()).getInboxData(sessionBean.getTENUREID());
//	ArrayList<TrnMarking> outBoxList = (new TrnReferenceDAO()).getOutBoxData(sessionBean.getTENUREID());
	ArrayList<TrnMarking> returnBoxList = (new TrnReferenceDAO()).getReturnBoxData(sessionBean.getTENUREID());
  	
  	ArrayList<CommonBean> arrTree = new ArrayList<CommonBean>();
  	arrTree = (new TrnReferenceDAO()).getTreeview(refBean.getREFID());
  	
  	
  	
  	ArrayList<CommonBean> filepathArr=new ArrayList();
	if(request.getAttribute("filepathArr")!=null) {
		filepathArr = (ArrayList) request.getAttribute("filepathArr");
	}

    ArrayList<CommonBean> reminderArr=new ArrayList();
		if(request.getAttribute("reminderArr")!=null) {
		reminderArr = (ArrayList) request.getAttribute("reminderArr");
	}
  	
  	
  	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
  
  %>
  
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
		
		alert(' objname.length ' + objname.length);
		alert(' value.length ' + value.length);
	
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
		var LOGINID = '<%=sessionBean.getLOGINID() %>';
		
		TrnReferenceDAO.saveBudgetData(BDGREFID,BUDGETSEQUENCE,BDGSUBJECTCODE,BDGSUBJECT,BDGMRREMARK,BDGREMARK, LOGINID, isSaved);
	} 
	
    function isSaved(data){
		if(data.split("~")[0]=="0"){
			alert(data.split("~")[1]);
		} else{
			alert("Data Saved!!!");;
		}
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
    
    var REFID = '<%=refBean.getREFID()%>';
    var TENUREID = '<%=sessionBean.getTENUREID()%>';
    var LOGINID = '<%=sessionBean.getLOGINID()%>';
    //alert("inside save8");
    TrnReferenceDAO.saveReminder(REFID,REMINDERFROM,REMINDERTYPE,REMINDERSIGNEDBY,REMINDERSIGNEDON,REMINDERREMARKS,TENUREID,LOGINID,saveRem);
}

function saveRem(data)
{
  if(data=="0")
  {
    alert("Some Problem Occured in Saving Record.");
  }
  else
  {
    alert("Data Saved Successfully.");
  }
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

function getBlankValue(thisObj){
	return (thisObj.className=='active')? thisObj.value:"";
	
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
    
    var REFID = '<%=refBean.getREFID()%>';
    var TENUREID = '<%=sessionBean.getTENUREID()%>';
    var LOGINID = '<%=sessionBean.getLOGINID()%>';
    //alert("inside save8");
    TrnReferenceDAO.saveReminder(REFID,REMINDERFROM,REMINDERTYPE,REMINDERSIGNEDBY,REMINDERSIGNEDON,REMINDERREMARKS,TENUREID,LOGINID,saveRem);
}


function saveRem(data)
{
  if(data=="0")
  {
    alert("Some Problem Occured in Saving Record.");
  }
  else
  {
    alert("Data Saved Successfully.");
  }
}

function getSearch(thisObj){
  	
  	var REFNOFROM =  window.document.getElementById("REFNOFROM");
  	var REFNOTO =  window.document.getElementById("REFNOTO");
  	var INCOMINGDATEFROM =  window.document.getElementById("INCOMINGDATEFROM");
  	var INCOMINGDATETO =  window.document.getElementById("INCOMINGDATETO");
  	var REFERENCENAMESEARCH =  window.document.getElementById("REFERENCENAMESEARCH");
  	var SUBJECTSEARCH =  window.document.getElementById("SUBJECTSEARCH");
  	var COMMONSEARCH =  window.document.getElementById("COMMONSEARCH");
  	var COMMONSEARCHVALUE =  window.document.getElementById("COMMONSEARCHVALUE");

  	//Check for atleast One field for search
  	if(    getBlankValue(REFNOFROM)=='' && getBlankValue(REFNOTO)=='' && getBlankValue(INCOMINGDATEFROM)=='' && getBlankValue(INCOMINGDATETO)==''
  		   && getBlankValue(REFERENCENAMESEARCH)=='' && getBlankValue(SUBJECTSEARCH)=='' && getBlankValue(COMMONSEARCH)=='' && getBlankValue(COMMONSEARCHVALUE)==''
  	  ){
  	  	//nothing to search ...simply return 
  		return false;
  	}
  	
	TrnReferenceDAO.getSearchData(getBlankValue(REFNOFROM), getBlankValue(REFNOTO), getBlankValue(INCOMINGDATEFROM), getBlankValue(INCOMINGDATETO),getBlankValue(REFERENCENAMESEARCH), getBlankValue(SUBJECTSEARCH), getBlankValue(COMMONSEARCH), getBlankValue(COMMONSEARCHVALUE), getData);  	
	//TrnReferenceDAO.getSearchDataNew(getBlankValue(REFNOFROM), getBlankValue(REFNOTO), getBlankValue(INCOMINGDATEFROM), getBlankValue(INCOMINGDATETO),getBlankValue(REFERENCENAMESEARCH), getBlankValue(SUBJECTSEARCH), getBlankValue(COMMONSEARCH), getBlankValue(COMMONSEARCHVALUE), getData);  	
	//TrnReferenceDAO.getSearchDataNew(REFNOFROM.value, REFNOTO.value, INCOMINGDATEFROM.value, INCOMINGDATETO.value,REFERENCENAMESEARCH.value, SUBJECTSEARCH.value, COMMONSEARCH.value,  getData);  	
	
  }
  
  function getData(data){
	
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	//alert('data.length : '+data.length);
	htmlText  = ' <table id="data" width="100%">';	
	htmlText += ' <tr><td align="left" width="50%"><b>Total Records</b></td> <td align="center" width="50%"><b>'+data.length+'</b></td></tr>';
	htmlText += ' <tr bgcolor="#F6CED8"><td align="center" width="50%"><b>Ref No.</b></td> <td align="center" width="50%" nowrap="nowrap"><b>Subject</b></td></tr>';
	
	//alert(searchResult.innerHTML);
	var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop]; 
		//alert('trnRefBean.REFNO '+ trnRefBean.REFNO);
		
		var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick = "maximizeResult('+trnRefBean.REFID+');"> <td> '+trnRefBean.REFNO+' </td> <td> '+trnRefBean.SUBJECT+' </td> </tr>';
		//htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick="maximizeRefData(refBean[loop] );"> <td> '+trnRefBean.REFNO+' </td><td> kkkkk </td> </tr>';
			
			//alert(' htmlText ' + htmlText);	
					
	}
	
	htmlText += '</table>';
	//alert(htmlText);
	
	searchResult.innerHTML = htmlText;
	
}


function maximizeResult(REFID){
	//alert('REFID 11'+REFID);
	
	window.document.frmOriginated.REFID.value = REFID;
	
	
	var REFNOFROM =  window.document.getElementById("REFNOFROM");
  	var REFNOTO =  window.document.getElementById("REFNOTO");
  	var INCOMINGDATEFROM =  window.document.getElementById("INCOMINGDATEFROM");
  	var INCOMINGDATETO =  window.document.getElementById("INCOMINGDATETO");
  	var REFERENCENAMESEARCH =  window.document.getElementById("REFERENCENAMESEARCH");
  	var SUBJECTSEARCH =  window.document.getElementById("SUBJECTSEARCH");
  	var COMMONSEARCH =  window.document.getElementById("COMMONSEARCH");
  	var COMMONSEARCHVALUE =  window.document.getElementById("COMMONSEARCHVALUE");
  	
  	REFNOFROM.value = getBlankValue(REFNOFROM);
  	REFNOTO.value = getBlankValue(REFNOTO);
  	INCOMINGDATEFROM.value = getBlankValue(INCOMINGDATEFROM);
  	INCOMINGDATETO.value = getBlankValue(INCOMINGDATETO);
  	REFERENCENAMESEARCH.value = getBlankValue(REFERENCENAMESEARCH);
  	SUBJECTSEARCH.value = getBlankValue(SUBJECTSEARCH);
  	COMMONSEARCH.value = getBlankValue(COMMONSEARCH);
  	COMMONSEARCHVALUE.value = getBlankValue(REFNOFROM);
	
	submitForm('GO');
	
}


function maximizeRefData(refBean){
	//alert('REFID '+REFNO);
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
  
function confirmAction(refId, index, thisObj){

	alert(' refId : '+ refId);
	alert(' index: '+ index);
	var ACTION   = window.document.getElementsByName("INACTION")[index].value;
	//var ACTION = thisObj.value
	alert(' ACTION: '+ ACTION);
	TrnReferenceDAO.setInboxAction(refId, ACTION, getReturnData);

	//alert('Table updated');	

} 
 



function getReturnData(data){
	//alert(data);
	window.location.reload();

}

function setOutBoxMarkAction(index, refId, inMarkingFrom, outBoxMarkingFrom, targetDate, subjectCode, subject ){

	//alert(' index: '+ index);
	//alert(' refId : '+ refId);
	var OUTBOXMARKTO  = window.document.getElementsByName("OUTBOXMARKTO")[index].value;
	var OUTBOXMARKINGREMARK  = window.document.getElementsByName("OUTBOXMARKINGREMARK")[index].value;
	var OUTACTION   = window.document.getElementsByName("OUTACTION")[index].value;
	//alert(' OUTBOXMARKTO: '+ OUTBOXMARKTO);
	//alert(' outBoxMarkingFrom: '+ outBoxMarkingFrom);
	//alert(' OUTBOXMARKINGREMARK: '+ OUTBOXMARKINGREMARK);
	//alert(' OUTACTION: '+ OUTACTION);
	//alert(' targetDate: '+ targetDate);
	//alert(' subjectCode: '+ subjectCode);
	//alert(' subject: '+ subject);
	
	if(OUTACTION =='RET'){
		OUTBOXMARKTO = inMarkingFrom;
	}

	//alert(' OUTBOXMARKTO: '+ OUTBOXMARKTO);
	
	//return;	
	TrnReferenceDAO.setoutBoxAction(refId, outBoxMarkingFrom, OUTBOXMARKTO, OUTBOXMARKINGREMARK, targetDate, subjectCode, subject, OUTACTION, getOutReturnData);

}  

function getOutReturnData(data){
	//alert(data);
	window.location.reload();


}

function setReturnBoxAction(refId, index, returnMarkFrom, targetDate, subjectCode, subject){

	alert(' refId : '+ refId);
	alert(' index: '+ index);
	var MARKFROM   = '<%= sessionBean.getTENUREID()%>';
	var MARKTO   = window.document.getElementsByName("RETURNMARKTO")[index].value;
	var RETURNACTION   = window.document.getElementsByName("RETURNACTION")[index].value;
	var RETURNACTION   = window.document.getElementsByName("RETURNACTION")[index].value;
	var RETURNMARKINGREMARK   = window.document.getElementsByName("RETURNMARKINGREMARK")[index].value;
	alert(' returnMarkFrom: '+ returnMarkFrom);
	alert(' MARKFROM: '+ MARKFROM);
	alert(' MARKTO: '+ MARKTO);
	alert(' RETURNACTION: '+ RETURNACTION);
	alert(' RETURNMARKINGREMARK: '+ RETURNMARKINGREMARK);
	alert(' targetDate: '+ targetDate);
	alert(' subjectCode: '+ subjectCode);
	alert(' subject: '+ subject);
	
	TrnReferenceDAO.setReturnBoxAction(refId, returnMarkFrom, MARKFROM, MARKTO,RETURNMARKINGREMARK,  targetDate, subjectCode, subject, RETURNACTION, getReturnBoxData);

	alert('Table updated');	
}  

function getReturnBoxData(data){
	//alert(data);
	window.location.reload();


}

  
  $(document).ready(function(){
    $("#MainTabDiv").tabs({ fx: { slide: true, fade: true, speed: 'normal' }  });
    $( "#tabs" ).tabs();
    
    $("#REFERENCENAME").autocomplete("getReferenceNamesData.jsp", {scroll:false});
    $('#REFERENCENAME').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			//alert(' dataarr length '+ dataarr.length);
 			//alert(' dataarr 0 '+ dataarr[0]);
 			//alert(' dataarr 1 '+ dataarr[1]);
 			//alert(' dataarr 2 '+ dataarr[2]);
 			//alert(' dataarr 3 '+ dataarr[3]);
			window.document.getElementById('REFERENCENAME').value = dataarr[0];
			window.document.getElementById('VIPSTATUS').value = dataarr[1];
			window.document.getElementById('STATECODE').value = dataarr[2];
		}
	});
    
   
  });
  

  
  function submitForm(btnval){
	//thisObj.disabled = true;
	window.document.getElementById("btnClick").value = btnval;
	//alert( ' window.document.getElementById("btnClick").value ' + window.document.getElementById("btnClick").value);
	
	if(btnval=='GO'){
		window.document.frmOriginated.submit();
	}	
	
	if(btnval=='CLEAR'){
		window.document.frmOriginated.submit();
	}	
	
	if(btnval=='SAVE'){
		
		//window.document.frmOriginated.btnSAVE.disabled = true;
		/*
		alert('btn : '+ "Save");
		alert(' window.document.frmOriginated.REFCLASS '+ window.document.frmOriginated.REFCLASS.value );
		alert(' window.document.frmOriginated.REFCATEGORY '+ window.document.frmOriginated.REFCATEGORY.value );
		alert(' window.document.frmOriginated.LANGUAGE '+ window.document.frmOriginated.LANGUAGE.value );
		alert(' window.document.frmOriginated.URGENCY '+ window.document.frmOriginated.URGENCY.value );
		alert(' window.document.frmOriginated.INCOMINGDATE '+ window.document.frmOriginated.INCOMINGDATE.value );
		alert(' window.document.frmOriginated.LETTERDATE '+ window.document.frmOriginated.LETTERDATE.value );
		alert(' window.document.frmOriginated.ISBUDGET '+ window.document.frmOriginated.ISBUDGET.value );
		alert(' window.document.frmOriginated.REFERENCENAME '+ window.document.frmOriginated.REFERENCENAME.value );
		*/
		
		
		if( chkblank(window.document.frmOriginated.REFCLASS) && chkblank(window.document.frmOriginated.REFCATEGORY)  
			&& chkblank(window.document.frmOriginated.LANGUAGE) && chkblank(window.document.frmOriginated.URGENCY)
			//&& chkblank(window.document.frmOriginated.SECURITYGRADING) 
			&& chkblank(window.document.frmOriginated.INCOMINGDATE) && chkblank(window.document.frmOriginated.LETTERDATE)
			&& chkblank(window.document.frmOriginated.ISBUDGET) && chkblank(window.document.frmOriginated.REFERENCENAME)
		){
		
			var MARKINGTO = window.document.getElementsByName("MARKINGTO");
			var flag = true;
			for (var loop=1; loop<MARKINGTO.length; loop++){
				if(chkblank(MARKINGTO[loop])){
						
				}else{
					flag = false;	
				}
			}
			
			if(flag){
				window.document.getElementById("frmOriginated").submit();
			}	
		
		}
		
		window.document.frmOriginated.btnSAVE.disabled = false;
		
	}	
}
  
  function setMaxRowsOfMarking(thisObj){
  	//alert(thisObj.value);
  	
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
  
  if(thisObj.value == '' || thisObj.value ==' '){
  	 return;
  }
  
 // alert('oldDate '+oldDate);
 var index =  getIndex(thisObj); 
 //alert('index '+index);
 // alert('noOfDaysToAdd '+thisObj.value);
  
  if(thisObj.value == '' || thisObj.value ==' '){
  
  }
  
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
	<%	}%>
	
}

function hideDiv(){
	window.document.getElementById('updateDiv').style.display="none";
	window.document.getElementById('updateDivInner').style.display="none";
}

function callMe(){
	hideDiv();
}
  
 function bodyOnload(){
 
 	////code for handling search paramas
	label2value();	
		
	setSessionSeachParams('REFNOFROM', '<%=sBean.getField1()%>');
	setSessionSeachParams('REFNOTO', '<%=sBean.getField2()%>');
	setSessionSeachParams('INCOMINGDATEFROM', '<%=sBean.getField3()%>');
	setSessionSeachParams('INCOMINGDATETO', '<%=sBean.getField4()%>');
	setSessionSeachParams('REFERENCENAMESEARCH', '<%=sBean.getField5()%>');
	setSessionSeachParams('SUBJECTSEARCH', '<%=sBean.getField6()%>');
	setSessionSeachParams('COMMONSEARCH', '<%=sBean.getField7()%>');
	setSessionSeachParams('COMMONSEARCH', '<%=sBean.getField8()%>');

	//call getSearch to show prvious search
 	getSearch();
 	////search param code ends here
 	
 
 	if('<%= refBean.getISBUDGET()%>' == 'Y'){
 		window.document.getElementById("listMainBudget").style.display ='block';
 	}else{
 		window.document.getElementById("listMainBudget").style.display ='none';
 	
 	}
 
 	showDiv();
 } 
 
  function setSessionSeachParams(OBJNAME, objValue){
		if(objValue!=''){
			window.document.getElementById(OBJNAME).value = objValue;
    		window.document.getElementById(OBJNAME).className = 'active';
    	}	
	}	
 
 function hideUnHideDiv(thisObj){
 	
 	var ODS = window.document.getElementById("ODS");
 	//alert('ODS : '+ ODS.style.display);
 	
 	if(ODS.style.display == 'block')	{
 		ODS.style.display = 'none';
 	}else if(ODS.style.display == 'none')	{
 		ODS.style.display = 'block';
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

function openAttachment(ATTACHMENTID,REFID,ORIGFILENAME,GENFILENAME,EXT){
		var  prev_action = document.forms[0].action;
	//	var newimgid = assetid+"_"+imgid+ext;
		//alert(newimgid);
		window.document.forms[0].action="${pageContext.request.contextPath}/AttachmentController?ATTACHMENTID="+ATTACHMENTID+"&REFID="+REFID+"&ORIGFILENAME="+ORIGFILENAME+"&GENFILENAME="+GENFILENAME+"&EXT="+EXT+"";
		window.document.forms[0].submit();
		window.document.forms[0].action=prev_action;		
		return true;
}

function delRefFile(obj,attachmentID,refID)	{
	  var idx = getIndex(obj);
	  TrnReferenceDAO.deleteRefImage(refID, attachmentID, function (data){
	  		if(data.split("~")[0]==1) {
	  			document.getElementById("refimage").deleteRow(idx); 
	  			alert(data.split("~")[1]);
	  		} else {
	  			alert(data.split("~")[1]);
	  		}
	  } 
   );
}

 </script>
  
</head>
<body style="color: #ffe8d5" onload="bodyOnload();">
<form name="frmOriginated" id="frmOriginated" action="${pageContext.request.contextPath}/TrnReferenceController" method="post" enctype="multipart/form-data">
<table width="100%" align="left" style="vertical-align: top;">
	<tr>
		<td>
			<div class="container">
			    <div id="content">
			        <div class="menu">
			            <ul>
			                <li><a class="left" href="ReferenceTEST.jsp">References</a>
			                </li>
			                <li><a class="middle" href="ReferenceInbox.jsp">Fowarded</a></li>
			                <li><a class="right" href="#">Returned</a>
			                </li>
			               
			                <li><a class="refresh" href="#">&nbsp;</a></li>
			                <li><a class="previous" href="#">&nbsp;</a></li>
			                <li><a class="next" href="#">&nbsp;</a></li>
			            </ul>
			        </div>
			        <!--end  menu-->
			    </div>
			    <!--end content-->
			</div>
			<!--end container-->
		</td>
	</tr>
</table>

	<table width="100%" align="Center" id="MainTabDiv" style="margin-top: 20px " >
			        <tbody id="Originated">
 	<tr>
 		<td width="*" valign="top" align="left" valign="top">
 			<table id="data" >
 				<tr>
 					<td> <font size="+1">Search</font></td>
						<td></td>
					</tr>
 				<tr>
 					<td></td>
						<td align="right"></td>
					</tr>
 				<tr>
 					<td>
 						<label for="REFNOFROM">Ref. No from</label>
 						<input type="text" name="REFNOFROM" id="REFNOFROM" size="15" onkeypress="chknumeric();" >
 					</td>
					<td align="right">
						<label for="REFNOTO">Ref. No to</label>
						<input type="text" name="REFNOTO" id="REFNOTO" size="15" onkeypress="chknumeric();">
					</td>
					</tr>
					<tr>
					<td>
 						<label for="INCOMINGDATEFROM">In. Date from</label>
 						<input type="text" name="INCOMINGDATEFROM" size="15" onKeyUp="DateFormat(this,this.value,event,false,'3');" onblur="chkWorkDate(this);">
 					</td>
					<td align="right">
						<label for="INCOMINGDATETO">In.	 Date to</label>
						<input type="text" name="INCOMINGDATETO" id="INCOMINGDATETO" size="15" onKeyUp="DateFormat(this,this.value,event,false,'3')" onblur="chkWorkDate(this);">
					</td>
					</tr>
					<tr>
 					<td>
 						<label for="REFERENCENAMESEARCH">Received from</label>
 						<input type="text" name="REFERENCENAMESEARCH" id="REFERENCENAMESEARCH" size="15">
 					</td>
					<td align="right">
						<label for="SUBJECTSEARCH">Subject</label>
						<input type="text" name="SUBJECTSEARCH" id="SUBJECTSEARCH" size="15">
					</td>
						
					</tr>
				
					<tr>
						<td align="right"><select name="COMMONSEARCH" id="COMMONSEARCH">
							<option value="" selected>-select-</option>
							<option value="">Ref. No</option>
							<option value="">Received from</option>
							<option value="">Marked to</option>
							<option value="">Date From</option>
							<option value="">Incoming Date</option>
							<option value="">Letter Date</option>
						</select></td>
						<td align="right"><input type="text" name="COMMONSEARCHVALUE" id="COMMONSEARCHVALUE" size="15"></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="right">
						<input type="button" name="" value="&gt;&gt;" onclick="getSearch(this);">
						
						</td>
					</tr>
					
					</table>
					
					<div id="searchResult">
					
					</div>
					
		</td>
		
		<td width="5pxs;" bgcolor="#DDCFB7" onclick="showHideTD(this)" title="Show search criteria" style="cursor: pointer;" valign="top" id="showhidecol">
			<div id="scrollingDiv"> 
       			<img src="images/next.gif" ></div></td>
       		
 		<td id="td2" width="1100" valign="top">	 
			<div id="tabs" >
			    <ul>
			        <li><a href="#MainReferences"><span>Main</span></a></li>
			        <li><a href="#MainReminder"><span>Reminder</span></a></li>
			        <li id="listMainBudget"><a href="#MainBudget"><span>Budget</span></a></li>
			        <li id="listMainReturned"><a href="#MainReturned"><span>Returned</span></a></li>
			        <li><a href="#MainFinalStatus"><span>Final Status</span></a></li>
			        <li><a href="#MainAttachment"><span>Attachment</span></a></li>
			        <li><a href="#MainStickyNote"><span>Sticky Note</span></a></li>
			        <li><a href="#MainMarkingChain"><span>Marking Chain</span></a></li>
			        <li><a href="#MainReminderChain"><span>Reminder Chain</span></a></li>
			    </ul>
			    <div id="MainReferences" >
			    	<fieldset>
			    	<table id="data" width="100%">
			    		<tr class="trodd">
			    			<td>Ref. Class <span style="color: red"><b>*</b></span></td>
			    			<td>
			    				<select style="width: 120px" name="REFCLASS" id="REFCLASS" onchange="setRefNoPrefix(this);">
			    					<option value="" selected>-select-</option>
			    					<%
										for(int i=0;i<refClassList.size();i++){
										CommonBean beanCommon=(CommonBean) refClassList.get(i);
										%>
											<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(refBean.getREFCLASS())?"selected":""%>><%=beanCommon.getField1()%></option>
										<%
									}%>
			    				</select>
			    			</td>
			    			
			    			<td>Ref. No</td>
			    			<td><input type="text" name="REFNO" id="REFNO" value="<%= refBean.getREFNO()%>"
							readonly="readonly" class="readonly">
							<input type="hidden" name="REFID" id="REFID" value="<%= refBean.getREFID()%>" readonly="readonly" class="readonly">
							<input type="hidden" name="REFCOUNT" id="REFCOUNT" value="<%= refBean.getREFCOUNT()%>" readonly="readonly" class="readonly">
						 </td>
			    			<td>Link Ref. No</td><td><input type="text" name="LINKREFID" id="LINKREFID" value="<%= refBean.getLINKREFID()%>" readonly="readonly" class="readonly" >
						 </td>
			    			
			    			
			    			
			    		</tr>
			    		<tr class="treven">
			    			<td>Ref. Category <span style="color: red"><b>*</b></span></td>
			    			<td>
			    				<select name="REFCATEGORY" style="width: 120px"> 
			    					<option value="" selected>-select-</option>
			    					<%
										for(int i=0;i<refCategoryList.size();i++){
										CommonBean beanCommon=(CommonBean) refCategoryList.get(i);
										%>
											<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(refBean.getREFCATEGORY())?"selected":""%>><%=beanCommon.getField2()%></option>
										<%
									}%>
			    				</select>
			    			</td>
			    			<td>Language <span style="color: red"><b>*</b></span></td>
			    			<td>
			    				<select name="LANGUAGE" style="width: 120px">
			    					<option value="" selected>-select-</option>
			    					<%
										for(int i=0;i<languageList.size();i++){
										CommonBean beanCommon=(CommonBean) languageList.get(i);
										%>
											<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(refBean.getLANGUAGE())?"selected":""%>><%=beanCommon.getField2()%></option>
										<%
									}%>
			    				</select>
			    			</td>
							<%--   		    		
							<td>Security Grading <span style="color: red"><b>*</b></span></td>	
			    			
			    			<td><select name="SECURITYGRADING" style="width: 120px">
			    					<option value="" selected>-select-</option>
			    					<option value="" selected>-select-</option>
			    					<%
										for(int i=0;i<SecurityGradingList.size();i++){
										CommonBean beanCommon=(CommonBean) SecurityGradingList.get(i);
										%>
											<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(refBean.getSECURITYGRADING())?"selected":""%>><%=beanCommon.getField2()%></option>
										<%
									}%>
			    				</select></td>
			    				--%>
			    			<td>Urgency <span style="color: red"><b>*</b></span></td>
			    			<td>
			    				<select name="URGENCY" style="width: 120px">
			    					<option value="" selected>-select-</option>
			    					<%
										for(int i=0;i<urgencyList.size();i++){
										CommonBean beanCommon=(CommonBean) urgencyList.get(i);
										%>
											<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(refBean.getURGENCY())?"selected":""%>><%=beanCommon.getField2()%></option>
										<%
									}%>
			    				</select>
			    			</td>
			    			
			    		</tr>
			    		</table>	
			    		</fieldset>
			    	<fieldset>	
			    	<table id="data" width="100%">	
			    		<tr class="trodd">
			    			<td nowrap="nowrap" width="117">Incoming Date<span style="color: red"><b>*</b></span><span style="color: red"><b></b></span></td>
			    			<td width="329">
			    				<input type="text" name="INCOMINGDATE" value="<%= refBean.getINCOMINGDATE()%>" onblur="chkWorkDate(this);" onKeyUp="DateFormat(this,this.value,event,false,'3')"  onkeypress="chknumeric(47);" maxlength="10" onchange="window.document.frmOriginated.LETTERDATE.value=this.value">
			    			</td>
			    			<td nowrap="nowrap" width="176">Letter Date<span style="color: red"><b>*</b></span></td><td><input type="text" name="LETTERDATE" value="<%= refBean.getLETTERDATE()%>" onKeyUp="DateFormat(this,this.value,event,false,'3')"  onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10"></td>
						<td>Budget</td>
						<td><select name="ISBUDGET" style="width: 120px" onchange=setMaxRowsOfMarking(this);>
							<option value="">-select-</option>
							<option value="Y" <%="Y".equalsIgnoreCase(refBean.getISBUDGET())?"selected":""%>>Yes</option>
							<option value="N" <%="N".equalsIgnoreCase(refBean.getISBUDGET())?"selected":""%>>No</option>
						</select></td>
					</tr>
					<tr class="treven">
					<td nowrap="nowrap" width="117">Received From<span style="color: red"><b>*</b></span></td>
					<td width="329">
						<input type="text" id="REFERENCENAME" name="REFERENCENAME"
								maxlength="30" value="<%= refBean.getREFERENCENAME()%>"
								style=" background-image: url('${pageContext.request.contextPath}/images/search.png'); background-position: right;background-repeat: no-repeat; "
								size="50">
					</td>
					<td width="176">Status</td><td><input type="text" name="VIPSTATUS" id="VIPSTATUS" maxlength="20" value="<%= refBean.getVIPSTATUS()%>"></td>
						<td>State</td>
						<td><select name="STATECODE" id="STATECODE" style="width: 20">
							<option value="">-select-</option>
							<%
								for(int i=0;i<stateList.size();i++){
								CommonBean beanCommon=(CommonBean) stateList.get(i);
								%>
									<option value="<%=beanCommon.getField1()%>"
										<%=beanCommon.getField1().equalsIgnoreCase(refBean.getSTATECODE())?"selected":""%>><%=beanCommon.getField2()%>
									</option>
								<%
							}%>
						</select></td>
					</tr>
					
					
					<tr class="trodd">
						<td colspan="6">
						<table id="data" width="100%">
				    		<tr class="treven">
								<td nowrap="nowrap" width="498">Subject</td>
					    		<td nowrap="nowrap" width="130">Sub. Type</td>
					    		<td nowrap="nowrap" width="129">Mark To</td>
								<td nowrap="nowrap" width="76">Marking Dt.</td>
					    		<td nowrap="nowrap" width="110">Tar. days & date</td>
								<td nowrap="nowrap"></td>
							</tr>
					<TBODY id="baserow" >
					<tr class="trodd" style="display: none">
						<td nowrap="nowrap" width="498">
						<input type="text" name="MARKINGREFID" readonly="readonly" class="readonly" size="5">
						<textarea rows="2" cols="60"
											name="SUBJECT"></textarea></td>
						<td nowrap="nowrap" width="130"><select name="SUBJECTCODE" style="width: 120px"> 
						<option value="">-select-</option>
						<%
							for(int i=0;i<subjectList.size();i++){
							CommonBean beanCommon = (CommonBean) subjectList.get(i);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
								
							<%
						}%>
					</select></td>
						<td nowrap="nowrap" width="129">
						<select name="MARKINGTO" style="width: 120px"> 
						<option value="">-select-</option>
						<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
							<%
						}%>
					</select>
						</td>
						<td nowrap="nowrap" width="76"><input type="text" name="MARKINGDATE" size="10" maxlength="10" value="<%=serverDate %>" class="readonly" readonly></td>
						<td nowrap="nowrap" width="110"><input type="text" name="TARGETDAYS" size="3" maxlength="2" onblur="setTargetDate('<%=serverDate %>', this);" onkeypress="chknumeric();">
						<input type="text" size="10" maxlength="10" readonly name="TARGETDATE" class="readonly"></td>
						<td nowrap="nowrap"></td>
					</tr>
					
					<% for(int i=0; i < refBean.getMarkingBeanList().size();i++){
					TrnMarking markingBean = (TrnMarking)refBean.getMarkingBeanList().get(i);
					
						%>
						<tr class="trodd">
							<td nowrap="nowrap" width="498">
							<input type="text" name="MARKINGREFID" readonly="readonly" class="readonly" value="<%=markingBean.getREFID()%>" size="5">
							
							<textarea rows="2" cols="60"
											name="SUBJECT"><%= markingBean.getSUBJECT()%></textarea></td>
							<td nowrap="nowrap" width="130"><select name="SUBJECTCODE" style="width: 120px"> 
							<option value="">-select-</option>
							<%
											for(int j=0;j<subjectList.size();j++){
											CommonBean beanCommon = (CommonBean) subjectList.get(j);
											%>
												<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(markingBean.getSUBJECTCODE())?"selected":""%>><%=beanCommon.getField2()%></option>
											<%
										}%>
						</select></td>
							<td nowrap="nowrap" width="129">
						<select name="MARKINGTO" style="width: 120px"> 	 
						<option value="">-select-</option>
						<%
							for(int k=0;k<markingToList.size();k++){
							CommonBean beanCommon = (CommonBean) markingToList.get(k);
							%>
								<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(markingBean.getMARKINGTO())?"selected":""%>><%=beanCommon.getField2()%></option>
							<%
						}%>
					</select>
							</td>
							<td nowrap="nowrap" width="76"><input type="text" name="MARKINGDATE" size="10" maxlength="10" value="<%= markingBean.getMARKINGDATE()%>" class="readonly" readonly></td>
							<td nowrap="nowrap" width="110"><input type="text" name="TARGETDAYS" size="3" maxlength="2" value="<%= markingBean.getTARGETDAYS()%>" onblur="setTargetDate('<%=serverDate %>', this);" onkeypress="chknumeric(47);">
							<input type="text" class="readonly" size="10" maxlength="10" readonly name="TARGETDATE" value="<%= markingBean.getTARGETDATE()%>"></td>
							<td nowrap="nowrap"></td>
						</tr>
						
						<%
						}
					%>
					
					
					</TBODY>
					<tr class="treven">
						<td width="498"> <input type="button" name="btnADD" id="btnADD" value=" + " onclick="addrow(window.document.getElementById('baserow'));"
							class="butts1" title="Add Multiple Markings here."></td>
						<td width="130"></td>
						<td width="129"></td>
						<td width="76"></td>
						<td width="110"></td>
						<td></td>
					</tr>
				</table></td>
					</tr>
					
			    		
			    		</table>	
				
				</fieldset>
				<fieldset>
				<table width="100%">
					<tr class="trodd">
						<td nowrap="nowrap" colspan="2" rowspan="2" width="432">Remarks 
						<br>
							<textarea
							rows="2" cols="60" name="REMARKS"><%= refBean.getREMARKS()%></textarea></td><td nowrap="nowrap" width="79">Signed By</td>
			    		<td width="165">
							<select name="SIGNEDBY" style="width: 120px"> 	 
							<option value="">-select-</option>
							<%
								for(int k=0;k<querySignedByList.size();k++){
								CommonBean beanCommon = (CommonBean) querySignedByList.get(k);
								%>
									<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(refBean.getSIGNEDBY())?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
							}%>
							</select>
						</td>
			    		<td  nowrap="nowrap" width="112">Ack. By</td>
			    		<td>
							<select name="ACKBY" style="width: 120px"> 	 
								<option value="">-select-</option>
								<%
									for(int k=0;k<querySignedByList.size();k++){
									CommonBean beanCommon = (CommonBean) querySignedByList.get(k);
									%>
										<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(refBean.getACKBY())?"selected":""%>><%=beanCommon.getField2()%></option>
									<%
								}%>
							</select>
						</td>
					</tr>
			    	<tr class="treven"><td nowrap="nowrap" width="79">Signed On</td>
						<td nowrap="nowrap" width="165"><input type="text" name="SIGNEDON" onKeyUp="DateFormat(this,this.value,event,false,'3')"
							value="<%= refBean.getSIGNEDON()%>" onblur="chkWorkDate(this);"
							onkeypress="chknumeric(47);" maxlength="10"></td>
						<td nowrap="nowrap" width="112">Ack Date</td>
						<td nowrap="nowrap"><input type="text" name="ACKDATE"
							value="<%= refBean.getACKDATE()%>" onblur="chkWorkDate(this);" onKeyUp="DateFormat(this,this.value,event,false,'3')" 
							onkeypress="chknumeric(47);" maxlength="10"></td>
						
					</tr>
					
				</table>
				</fieldset>
				<fieldset><b><legend>OUT-DOOR SCHDULE 
					<input type="button" onclick="hideUnHideDiv(this)" style="cursor: pointer;background-image: url('${pageContext.request.contextPath}/images/next.gif'); "></legend></b>
				<table id="data" width="100%"   >
				<tr class="trodd" id="ODS" style="display: none">
					<td>Place</td>
					<td><input type="text" name="ODSPLACE" value="<%= refBean.getODSPLACE()%>"></td>
					
					<td>Railway</td>
						<td><input type="text" name="ODSRAILWAY" value="<%= refBean.getODSRAILWAY()%>"></td>
						<td>Date</td>
						<td><input type="text" name="ODSDATE" value="<%= refBean.getODSDATE()%>" onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10"></td>
					</tr>
					
				</table>
				</fieldset>
				
				
			    	<table id="data" width="100%">
			    		<tr class="treven">
						<td colspan="6" align="center"><input type="button" name="btnNEW" id="btnNEW" value="New" class="butts1">  
										<input type="button" name="btnSAVE" id="btnSAVE" value="Save" class="butts1" onclick="submitForm('SAVE');"> 
										<input type="button" name="btnCLEAR" value="Clear" class="butts1" onclick="submitForm('CLEAR');">
										<input type="hidden" name="btnClick" id="btnClick">
						</td>
					</tr>
			    		</table>	
			    </div>
			    <div id="MainBudget">
				<table>
				<tr class="trodd">
				
					<th>Ref No <input type="hidden" name="btnClickBDG" id="btnClick"></th>
					<th>Subject Code</th>
					<th>Subject</th>
					<th>MR Remark</th>
					<th>Remark</th>
					<th>Delete</th>
				</tr>
				<TBODY id="baserowBDG" >
					<tr class="trodd" style="display: none">
					<td><input type="text" name="BDGREFID" id="BDGREFID" value="<%= refBean.getREFID()%>" size="5" readonly="readonly">
						<input type="hidden" name="BUDGETSEQUENCE" id="BUDGETSEQUENCE" value="" size="5" readonly="readonly">	
					</td>
					<td nowrap="nowrap">
						<select name="BDGSUBJECTCODE" style="width: 120px"> 
							<option value="">-select-</option>
							<%
								for(int j=0;j<subjectList.size();j++){
								CommonBean beanCommon = (CommonBean) subjectList.get(j);
							%>
							<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
							<%}%>
						</select>
					</td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGSUBJECT" onkeypress="return MaxLength(this, 399);"></textarea></td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGMRREMARK" onkeypress="return MaxLength(this, 399);"></textarea></td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGREMARK" onkeypress="return MaxLength(this, 399);"></textarea></td>
					<td nowrap="nowrap"><img src="images/error.gif" width="15" height="15" name=btnDelBudget" style="cursor: pointer;" 
									onclick="delBudgetRow(this,'<%= refBean.getREFID()%>','', window.document.getElementById('baserowBDG'));"></td>
				</tr>
				
				<% for(int i=0;i< refBean.getBudgetBeanList().size();i++){
				TrnBudget budgetBn = (TrnBudget) refBean.getBudgetBeanList().get(i);
				%>
					<tr class="trodd" >
					<td><input type="text" name="BDGREFID" id="BDGREFID" value="<%= refBean.getREFID()%>" size="5" readonly="readonly">
						<input type="hidden" name="BUDGETSEQUENCE" id="BUDGETSEQUENCE" value="<%= budgetBn.getBUDGETSEQUENCE()%>" size="5" readonly="readonly">	
					</td>
					
					<td nowrap="nowrap">
						<select name="BDGSUBJECTCODE" style="width: 120px"> 
							<option value="">-select-</option>
							<%
								for(int j=0;j<subjectList.size();j++){
								CommonBean beanCommon = (CommonBean) subjectList.get(j);
							%>
							<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(budgetBn.getSUBJECTCODE())?"selected":""%>><%=beanCommon.getField2()%></option>
							<%}%>
						</select>
					</td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGSUBJECT" onkeypress="return MaxLength(this, 399);"><%= budgetBn.getSUBJECT()%></textarea></td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGMRREMARK" onkeypress="return MaxLength(this, 399);"><%= budgetBn.getMRREMARK()%></textarea></td>
					<td nowrap="nowrap"><textarea rows="2" cols="30" name="BDGREMARK" onkeypress="return MaxLength(this, 399);"><%= budgetBn.getREMARK()%></textarea></td>
					<td nowrap="nowrap"><img src="images/error.gif" width="15" height="15" name=btnDelBudget" style="cursor: pointer;" 
									onclick="delBudgetRow(this,'<%= refBean.getREFID()%>','<%= budgetBn.getBUDGETSEQUENCE()%>', window.document.getElementById('baserowBDG'))"></td>
				</tr>
				<%}%>
			</TBODY>
				<tr class="treven">
					<td>
						<input type="button" name="btnADD" id="btnADD" value=" + " onclick="addrow(window.document.getElementById('baserowBDG'));"	class="butts1" title="Add Multiple Markings here."></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<input type="button" name="btnBDG" id="btnBDG" value="Save" class="butts1" onclick="remoteSaveBudget();"> 
			    
			    
			    </div>
			    <div id="MainMarkingChain">
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
			    
			    <div id="MainReminder">
			    <fieldset><legend><font color="brown">Previous Reminder Sent :</font></legend>
					<table id="data">
					<% if(reminderArr.size()>0) {%>
						<tr class="treven"> 
						<th>#</th>
			            <th>Reminder Type</th>
			            <th>Reminder Date</th>
			            <th>Remarks</th>
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
					<tr class="trodd">  
					    <td><%=i+1%>.</td>
			            <td nowrap="nowrap">
			            	<%=cmnbn.getField7() %>
			            </td>
			            <td nowrap="nowrap"><%=cmnbn.getField2()%></td>   
			            <td nowrap="nowrap"><%=cmnbn.getField3()%></td>
			            <td nowrap="nowrap"><select name="REMINDERSIGNEDBY" id="REMINDERSIGNEDBY" style="width: 120px" disabled="disabled"> 
						<option value="">-select-</option>
						<%
							for(int ki=0;ki<querySignedByList.size();ki++){
							CommonBean beanNew = (CommonBean) querySignedByList.get(ki);
							%>
								<option value="<%=beanNew.getField1()%>" <%=beanNew.getField1().equals(cmnbn.getField8()) ? "selected" : ""%>><%=beanNew.getField2()%></option>
							<%
						}%>
						</select><input type="hidden" name="REMINDERFROM" id="REMINDERFROM" size="11" value="<%=sessionBean.getLOGINASROLEID()%>"/></td>
						<td nowrap="nowrap"><%=cmnbn.getField9()%></td>
						<td><%=cmnbn.getField10()%></td>
			         </tr>
						<%} %>
					</table>
					</fieldset>
					<br>
					<fieldset><legend><font color="brown">Send New Reminder :</font></legend>
			      	<table id="data">
			         <tr class="treven"> 
			            <th>Reminder Type</th>
			            <th>Reminder Date</th>
			            <th colspan="2">Remarks</th>
			            <th>Signed By</th>
			            <th>Signed On</th>
			            <th></th>
			         <tr>
			         <tr class="trodd">  
			            <td nowrap="nowrap">
			               <select name="REMINDERTYPE" id="REMINDERTYPE">
			                  <option value="1">Self</option>
			                  <option value="2">Senders</option>
			                  <option value="3">Automatic</option>
			               </select>
			            </td>
			            <td nowrap="nowrap"><input type="text" value="<%=serverDate%>" id="REMINDERDATE" name="REMINDERDATE" size="11"/></td>   
			            <td nowrap="nowrap" colspan="2" rowspan="2"><textarea rows="2" cols="40" id="REMINDERREMARKS" name="REMINDERREMARKS"><%= remBean.getREMARKS()%></textarea></td>
			            <td nowrap="nowrap"><select name="REMINDERSIGNEDBY" id="REMINDERSIGNEDBY" style="width: 120px"> 
						<option value="">-select-</option>
						<%
							for(int i=0;i<querySignedByList.size();i++){
							CommonBean beanCommon = (CommonBean) querySignedByList.get(i);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
							<%
						}%>
					</select><input type="hidden" name="REMINDERFROM" id="REMINDERFROM" size="11" value="<%=sessionBean.getLOGINASROLEID()%>"/></td>
						<td nowrap="nowrap"><input type="text" value="<%=serverDate%>" id="REMINDERSIGNEDON" name="REMINDERSIGNEDON" size="11"></td>
						<td><input type="button" name="btnREM" id="btnREM" value="Send" class="butts1" onclick="saveRemind();"/></td>
			         </tr>
			      </table>
			      </fieldset>
			    </div>
			    
			    
			    <div id="MainReturned">
			    
			    <table width="100%">
					<tbody>
						<tr class="treven">
							<th>Reference No. </th>
							<th>Returned By </th>
							<th>Return Date </th>
							<th>Subject Code</th>
							<th>Subject</th>
							<th>Return Remarks</th>
							<th>Mark To</th>
							<th>Remarks</th>
							<th>Action</th>
						</tr>
						
						<%
						for(int i=0; i<returnBoxList.size();i++){
							TrnMarking returnBoxBean = returnBoxList.get(i);
							%>
						<tr class="odd">
							<td><%=returnBoxBean.getREFNO() %></td>
							<td><%=returnBoxBean.getMARKINGFROMNAME() %></td>
							<td><%=returnBoxBean.getACTIONDATE() %></td>
							<td><%=returnBoxBean.getSUBJECTCODE() %></td>
							<td><%=returnBoxBean.getSUBJECT() %></td>
							<td><%=returnBoxBean.getMARKINGREMARK() %></td>
							<td> <select name="RETURNMARKTO"  id="RETURNMARKTO" style="width: 100px">
									<option value="" selected="selected"> -select-</option>
									<%
										for(int ii=0;ii<markingToList.size();ii++){
										CommonBean beanCommon = (CommonBean) markingToList.get(ii);
										%>
											<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
										<%
									}%>
							</select>
							</td>
							<td><input type="text" name="RETURNMARKINGREMARK" size="40"></td>
							
							<td> <select name="RETURNACTION"  id="RETURNACTION" style="width: 100px">
								<option value="FOW" <%="FOW".equalsIgnoreCase(returnBoxBean.getACTION())?"selected":""%>>Accept & Forward</option>
								
							</select><input type="button" value="confirm" class="butts" onclick="setReturnBoxAction('<%=returnBoxBean.getREFID()%>', '<%=i%>', '<%=returnBoxBean.getMARKINGFROM() %>', '<%=returnBoxBean.getTARGETDATE()%>', '<%=returnBoxBean.getSUBJECTCODE()%>', '<%=returnBoxBean.getSUBJECT()%>');" ></td>
						</tr>
							<%
						}
						%>
						
					</tbody>
				</table>
			    
			    </div>
			    <div id="MainFinalStatus">Page Under Construction
			    </div>
			    			    <div id="MainAttachment">
			    <table id="data">
					<tr class="treven">
						<td align="left" width="100%" colspan="4">
							<table id="data" width="100%">
							<% if(filepathArr.size()>0) {%>
								<tr>
									<th width="435">Name</th>
									<th width="507">link</th>
									<th width="507">Delete</th>
								</tr>
							<%} %>	
								<tbody id="refimage">
								<%
								for (int i = 0; i < filepathArr.size(); i++) {
									CommonBean cmnbn = (CommonBean) filepathArr.get(i);
						    	%>
								<tr>
									<td width="435" align="center"><%=cmnbn.getField3()%></td>
									<td width="507" align="center"><a href="#" onclick="openAttachment('<%=cmnbn.getField1()%>','<%=cmnbn.getField2()%>','<%=cmnbn.getField3()%>','<%=cmnbn.getField4()%>','<%=cmnbn.getField5()%>');"><font color="blue"><%=cmnbn.getField4()%></font></a></td>
									<td><img src="images/error.gif" width="15" height="15"	name="delfile" style="cursor: pointer;"	onclick="delRefFile(this,'<%=cmnbn.getField1()%>','<%=cmnbn.getField2()%>')"></td>
								</tr>
								<%} %>
							</table>
						</td>
					</tr>
					
				<tbody id="attachimg">
					<tr class="treven" style="display:none">
						<th><nobr>File Location</nobr></th>
						<td colspan="3">
							<input type="hidden" name="ATTACHMENTREFID" id="ATTACHMENTREFID" value="<%=refBean.getREFID()%>">
							<input size="30" type="file" class="drop" name="uploadImage">
						</td>
					</tr>
				</tbody>
				<tr class="trodd">
					<td colspan="6">
						<input class="butts" type="button" name="attachbutt" value="Add Image" style= "width: 100px" onclick="addrow(window.document.getElementById('attachimg'));">
						<input name = "cntimg" type = "hidden" value="0">
					</td></tr>
			</table>
			    </div>
			    <div id="MainReminderChain">Page Under Construction
			    </div>
			    <div id="MainStickyNote">Page Under Construction
			    </div>
			    </div>
			    
		</td>
	</tr>
	</tbody>
</table>
	
			   
			        

<!-- DO NOT DELETE BELOW THIS!!! Following will create model. It will get uncommented once jsp is processed. -->

<!--main Content Area Ends--> <!--  <%=" Model Starts -->"%>
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
<%="<!-- Model Ends "%>    --> <!-- DO NOT ABOVE BELOW THIS!!! -->

<%session.removeAttribute("msg"); %>
<%session.removeAttribute("sbean"); %>
</body>
</html>
