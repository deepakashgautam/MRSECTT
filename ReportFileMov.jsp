<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.util.Encrypt"%>
<%@ taglib prefix="enc" uri="/WEB-INF/encrypt.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%><html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT</title>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<SCRIPT src='${pageContext.request.contextPath}/dwr/engine.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/util.js'></SCRIPT>
<script type="text/javascript" language="Javascript" src="slideshow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dateFormatter.js"></script>

<%

String reportid = request.getParameter("reportid") != null ? request.getParameter("reportid") : "";
String reportidencr = request.getParameter("reportidencr") != null ? Encrypt.decrypt(request.getParameter("reportidencr")) : "";
reportid = request.getParameter("reportid") != null ? reportid : reportidencr;
String repheader = request.getParameter("repheader") != null ? request.getParameter("repheader") : "";
String selcolparam = request.getParameter("selcolparam") != null ? request.getParameter("selcolparam") : "";

String REGDATECOND = request.getParameter("REGDATECOND") != null ? request.getParameter("REGDATECOND") : "";
String REGDATEFROM = request.getParameter("REGDATEFROM") != null ? request.getParameter("REGDATEFROM") : "";
String REGDATETO = request.getParameter("REGDATETO") != null ? request.getParameter("REGDATETO") : "";

String FILETYPE = request.getParameter("FILETYPE") != null ? request.getParameter("FILETYPE") : "";
String FILENO = request.getParameter("FILENO") != null ? request.getParameter("FILENO") : "";
String LINKFILENO = request.getParameter("LINKFILENO") != null ? request.getParameter("LINKFILENO") : "";
String PUC = request.getParameter("PUC") != null ? request.getParameter("PUC") : "";
String SUBJECT = request.getParameter("SUBJECT") != null ? request.getParameter("SUBJECT") : "";
String PROPOSEDPATH = request.getParameter("PROPOSEDPATH") != null ? request.getParameter("PROPOSEDPATH") : "";
String MARKTOUP = request.getParameter("MARKTOUP") != null ? request.getParameter("MARKTOUP") : "";

String MARKTOUPCOND = request.getParameter("MARKTOUPCOND") != null ? request.getParameter("MARKTOUPCOND") : "";
String MARKTOUPFROM = request.getParameter("MARKTOUPFROM") != null ? request.getParameter("MARKTOUPFROM") : "";
String MARKTOUPTO = request.getParameter("MARKTOUPTO") != null ? request.getParameter("MARKTOUPTO") : "";

String MARKINGREMARKS = request.getParameter("MARKINGREMARKS") != null ? request.getParameter("MARKINGREMARKS") : "";
String INTMARKING = request.getParameter("INTMARKING") != null ? request.getParameter("INTMARKING") : "";

String INTMARKINGCOND = request.getParameter("INTMARKINGCOND") != null ? request.getParameter("INTMARKINGCOND") : "";
String INTMARKINGFROM = request.getParameter("INTMARKINGFROM") != null ? request.getParameter("INTMARKINGFROM") : "";
String INTMARKINGTO = request.getParameter("INTMARKINGTO") != null ? request.getParameter("INTMARKINGTO") : "";

String RETURNONCOND = request.getParameter("RETURNONCOND") != null ? request.getParameter("RETURNONCOND") : "";
String RETURNONFROM = request.getParameter("RETURNONFROM") != null ? request.getParameter("RETURNONFROM") : "";
String RETURNONTO = request.getParameter("RETURNONTO") != null ? request.getParameter("RETURNONTO") : "";

String MARKINGREMARKS1 = request.getParameter("MARKINGREMARKS1") != null ? request.getParameter("MARKINGREMARKS1") : "";
String MARKTODOWN = request.getParameter("MARKTODOWN") != null ? request.getParameter("MARKTODOWN") : "";

String MARKTODOWNCOND = request.getParameter("MARKTODOWNCOND") != null ? request.getParameter("MARKTODOWNCOND") : "";
String MARKTODOWNFROM = request.getParameter("MARKTODOWNFROM") != null ? request.getParameter("MARKTODOWNFROM") : "";
String MARKTODOWNTO = request.getParameter("MARKTODOWNTO") != null ? request.getParameter("MARKTODOWNTO") : "";

String MARKINGREMARKS2 = request.getParameter("MARKINGREMARKS2") != null ? request.getParameter("MARKINGREMARKS2") : "";
String REPLYTYPE = request.getParameter("REPLYTYPE") != null ? request.getParameter("REPLYTYPE") : "";
String REASON = request.getParameter("REASON") != null ? request.getParameter("REASON") : "";

String [] DEPTCODE = request.getParameterValues("SEL_DEPARTMENTCODE") != null ? request.getParameterValues("SEL_DEPARTMENTCODE") :  new String [0];
String [] DESTMARKING = request.getParameterValues("SEL_DESTINATIONMARKING") != null ? request.getParameterValues("SEL_DESTINATIONMARKING") :  new String [0];
String [] SUBJECTCODE = request.getParameterValues("SEL_SUBJECTCODE") != null ? request.getParameterValues("SEL_SUBJECTCODE") :  new String [0];	
String [] RECEIVEDFROM = request.getParameterValues("SEL_RECEIVEDFROM") != null ? request.getParameterValues("SEL_RECEIVEDFROM") :  new String [0];			
String [] FILESTATUS1 = request.getParameterValues("SEL_FILESTATUS1") != null ? request.getParameterValues("SEL_FILESTATUS1") :  new String [0];
String [] FILESTATUS2 = request.getParameterValues("SEL_FILESTATUS2") != null ? request.getParameterValues("SEL_FILESTATUS2") :  new String [0];

//String [] seltabOrderBy = request.getParameterValues("sel_tabOrderBy") != null ? request.getParameterValues("sel_tabOrderBy") :  new String [0];
String [] selcolval = request.getParameterValues("sel_tabcols") != null ? request.getParameterValues("sel_tabcols") :  new String [0];

/*
int startyr = (Integer.parseInt(budgetyear.split("-")[0]));
	DateFormat df1 = new SimpleDateFormat("yyyy");
	String currDate = df1.format(new Date());
	int curryear = ((Integer.parseInt(currDate)));
*/
String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
/*
	if ("cepd".equalsIgnoreCase(session.getAttribute("loginid")
			.toString().length() > 4 ? session.getAttribute("loginid")
			.toString().substring(0, 4) : "")) {
		planheadarr = cd.getPlanhd(cd.getPlanhdList("pce"
				+ session.getAttribute("loginid").toString().substring(
						4)));
	} else {
		planheadarr = cd
				.getPlanhd(session.getAttribute("VIPSTATUS") != null ? session
						.getAttribute("VIPSTATUS").toString()
						: "");
	}
	
	
		if(VIPSTATUS.length==0){
	VIPSTATUS = new String [planheadarr.size()];
	for(int i=0;i<planheadarr.size();i++)
		VIPSTATUS[i]=  ((CommonBean)  planheadarr.get(i)).getField1();
	}
	
	
	planheadarr = cd.getPlanhd(cd.getPlanhdList("edceg"));
*/	

//	ArrayList rlycodearr = cd.getRly(sessionrly);

	ArrayList divarr = new ArrayList();		


 %>
 
 <script><!--
 function showAverage(obj){
 
 
 }
function reset()
{

		//fnSelectAll("VIPSTATUS");
		//fnMoveAll("VIPSTATUS","sel_VIPSTATUS"); 

	// Set Railway and Division
	//alert("break");
	//Set planhead
	var deptCode= new Array("0" <% for(int i=0;i<DEPTCODE.length;i++){%>,"<c:out value='<%= DEPTCODE[i] %>' />"<%} %>);
	mulitselect(document.getElementById("DEPARTMENTCODE"),document.getElementById("SEL_DEPARTMENTCODE"),deptCode);

	var destMarking= new Array("0" <% for(int i=0;i<DESTMARKING.length;i++){%>,"<c:out value='<%= DESTMARKING[i] %>' />"<%} %>);
	mulitselect(document.getElementById("DESTINATIONMARKING"),document.getElementById("SEL_DESTINATIONMARKING"),destMarking);

	var subCode= new Array("0" <% for(int i=0;i<SUBJECTCODE.length;i++){%>,"<c:out value='<%= SUBJECTCODE[i] %>' />"<%} %>);
	mulitselect(document.getElementById("SUBJECTCODE"),document.getElementById("SEL_SUBJECTCODE"),subCode);

	var recvdFrom= new Array("0" <% for(int i=0;i<RECEIVEDFROM.length;i++){%>,"<c:out value='<%= RECEIVEDFROM[i] %>' />"<%} %>);
	mulitselect(document.getElementById("RECEIVEDFROM"),document.getElementById("SEL_RECEIVEDFROM"),recvdFrom);

	var fileStatus1= new Array("0" <% for(int i=0;i<FILESTATUS1.length;i++){%>,"<c:out value='<%= FILESTATUS1[i] %>' />"<%} %>);
	mulitselect(document.getElementById("FILESTATUS1"),document.getElementById("SEL_FILESTATUS1"),fileStatus1);

	var fileStatus2= new Array("0" <% for(int i=0;i<FILESTATUS2.length;i++){%>,"<c:out value='<%= FILESTATUS2[i] %>' />"<%} %>);
	mulitselect(document.getElementById("FILESTATUS2"),document.getElementById("SEL_FILESTATUS2"),fileStatus2);

	//Set Selected Columns
	var selcolval= new Array("0" <% for(int i=0;i<selcolval.length;i++){%>,"<c:out value='<%= selcolval[i] %>' />"<%} %>);
	mulitselect(document.getElementById("tabcols"),document.getElementById("sel_tabcols"),selcolval);

	//Set Order By
//	var seltabOrderBy = new Array("0"  for(int i=0;i<seltabOrderBy.length;i++){%>,"= seltabOrderBy[i] %>"} %>);
	//Set Out of Turn
	
	//Set Sanc authority	
	
	//Set sancyear
	//Set cost
	//Set Column Alias
	var selcolparam = "<c:out value='<%=selcolparam%>' />";
	selcolparam = selcolparam.split("~");
	/*
	for(var i=0;i<selcolparam.length;i++)
	{
		if(selcolparam[i].length>0)
		{
			var tempcol = selcolparam[i].split("^");
			for(var j=0;j<document.getElementById("sel_tabcols").length;j++)
			{
				if(document.getElementById("sel_tabcols").options[j].value==tempcol[0])
					document.getElementById("sel_tabcols").options[j].text=tempcol[1];
			}
		}
	}*/
	//alert("before Manupulating --> "+ selcolparam);
	document.getElementById("sel_tabcols").length=0;
	for(var i=0;i<selcolparam.length;i++)
	{
		if(selcolparam[i].length>0)
		{
			var tempcol = selcolparam[i].split("^");
			//alert(tempcol);
			//document.getElementById("sel_tabcols").options[i-1].value==tempcol[0];
			//document.getElementById("sel_tabcols").options[i-1].text=tempcol[1];
				var newOption = new Option();
				newOption.text = tempcol[1];
				newOption.value = tempcol[0];
				document.getElementById("sel_tabcols").options[document.getElementById("sel_tabcols").length] = newOption;
				
				
			
		}
	}
	//Rearreange Order By
/*	for(var i=0;i<seltabOrderBy.length;i++)
	{
		for(var j=0;j<document.getElementById("sel_tabcols").length;j++)
		{
			
			if( (seltabOrderBy[i].match("~ASC")||seltabOrderBy[i].match("~DESC")) )
				{
					if(document.getElementById("sel_tabcols").options[j].value==seltabOrderBy[i].split("~")[0])
					{
						var newOption = new Option();
						newOption.text =   document.getElementById("sel_tabcols").options[j].text +(seltabOrderBy[i].split("~")[1]=="ASC"?"▲":"▼");
						newOption.value = seltabOrderBy[i];
//						document.getElementById("sel_tabOrderBy").options[document.getElementById("sel_tabOrderBy").length] = newOption;
					
					}
									
				}
				
		}
	}
*/
}

function resetdiv() {
//alert("in reset div");
	divval= new Array("0" <% for(int i=0;i<DESTMARKING.length;i++){%>,"<c:out value='<%= DESTMARKING[i] %>' />"<%} %>);
	mulitselect(document.getElementById("DESTINATIONMARKING"),document.getElementById("SEL_DESTINATIONMARKING"),divval);
}
function renameColumn (obj)
{
	var newname = prompt("Enter New Name for :"+ obj.options[obj.selectedIndex].value,obj.options[obj.selectedIndex].text);
	if(newname)
	{
		if(newname.length<=30)
			{ obj.options[obj.selectedIndex].text=newname; //addRemoveToOrderBy(this);
			}
		else
			alert("Max characters allowed is 30 but found "+newname.length);
		
	}
}

function showHideRegNo()
{
	if(window.document.getElementById("FILECOUNTERDES_CONDITION").value == "BETWEEN")
	{
		window.document.getElementById("FILECOUNTERDESFROM").style.display = "inline";
		window.document.getElementById("FILECOUNTERDESTO").style.display = "inline";
	}
	else if(window.document.getElementById("FILECOUNTERDES_CONDITION").value == "=")
	{
		window.document.getElementById("FILECOUNTERDESFROM").style.display = "inline";
		window.document.getElementById("FILECOUNTERDESTO").style.display = "none";
	}
}
function showHideRegDate()
{
	if(window.document.getElementById("REGDATECOND").value == "BETWEEN")
	{
		window.document.getElementById("REGDATEFROM").style.display = "inline";
		window.document.getElementById("REGDATETO").style.display = "inline";
	}
	else if(window.document.getElementById("REGDATECOND").value == "=")
	{
		window.document.getElementById("REGDATEFROM").style.display = "inline";
		window.document.getElementById("REGDATETO").style.display = "none";
	}
}
function showHideMarkingOn()
{
	if(window.document.getElementById("MARKTODOWNCOND").value == "BETWEEN")
	{
		window.document.getElementById("MARKTODOWNFROM").style.display = "inline";
		window.document.getElementById("MARKTODOWNTO").style.display = "inline";
	}
	else if(window.document.getElementById("MARKTODOWNCOND").value == "=")
	{
		window.document.getElementById("MARKTODOWNFROM").style.display = "inline";
		window.document.getElementById("MARKTODOWNTO").style.display = "none";
	}
}
function showHideReturnOn()
{
	if(window.document.getElementById("RETURNONCOND").value == "BETWEEN")
	{
		window.document.getElementById("RETURNONFROM").style.display = "inline";
		window.document.getElementById("RETURNONTO").style.display = "inline";
	}
	else if(window.document.getElementById("RETURNONCOND").value == "=")
	{
		window.document.getElementById("RETURNONFROM").style.display = "inline";
		window.document.getElementById("RETURNONTO").style.display = "none";
	}
}
function showHideIntMarking()
{
	if(window.document.getElementById("INTMARKINGCOND").value == "BETWEEN")
	{
		window.document.getElementById("INTMARKINGFROM").style.display = "inline";
		window.document.getElementById("INTMARKINGTO").style.display = "inline";
	}
	else if(window.document.getElementById("INTMARKINGCOND").value == "=")
	{
		window.document.getElementById("INTMARKINGFROM").style.display = "inline";
		window.document.getElementById("INTMARKINGTO").style.display = "none";
	}
}
function showHideLDownDate()
{
	if(window.document.getElementById("MARKINGTOCONDITION").value == "BETWEEN")
	{
		window.document.getElementById("MARKINGTOFROM").style.display = "inline";
		window.document.getElementById("MARKINGTOTO").style.display = "inline";
	}
	else if(window.document.getElementById("MARKINGTOCONDITION").value == "=")
	{
		window.document.getElementById("MARKINGTOFROM").style.display = "inline";
		window.document.getElementById("MARKINGTOTO").style.display = "none";
	}
}
function showHideMarkingDate()
{
	if(window.document.getElementById("MARKTOUPCOND").value == "BETWEEN")
	{
		window.document.getElementById("MARKTOUPFROM").style.display = "inline";
		window.document.getElementById("MARKTOUPTO").style.display = "inline";
	}
	else if(window.document.getElementById("MARKTOUPCOND").value == "=")
	{
		window.document.getElementById("MARKTOUPFROM").style.display = "inline";
		window.document.getElementById("MARKTOUPTO").style.display = "none";
	}
}
function showHideAckn()
{
	if(window.document.getElementById("ACKNDATECONDITION").value == "BETWEEN")
	{
		window.document.getElementById("ACKNDATEFROM").style.display = "inline";
		window.document.getElementById("ACKNDATETO").style.display = "inline";
	}
	else if(window.document.getElementById("ACKNDATECONDITION").value == "=")
	{
		window.document.getElementById("ACKNDATEFROM").style.display = "inline";
		window.document.getElementById("ACKNDATETO").style.display = "none";
	}
}

function changeOrder(obj)
{
	
	var opt = obj.options[obj.selectedIndex];
	var order = opt.value.split("~")[1]== "ASC"? "DESC":"ASC";	
	//alert(order);
	opt.text = opt.text.substring(0,opt.text.length-1) + (order == "ASC"?"▲":"▼");
	opt.value = (opt.value.split("~")[0]+"~"+order);
	//alert(obj.value);
}

function mulitselect(obj1,obj2,listval,opt)
{

	var flag=false;
	for(i=0;i<obj1.length;i++)
	{
		
		for(j=0;j<listval.length;j++)
		{
			if(listval[j]==obj1.options[i].value)
				{
					flag=true;
					obj1.options[i].selected=true;
					//alert(listval[j] + " :::::: " + obj1.options[i].value);	
				}
		}
		
	}
	
	if(flag)
		{ fnMoveItems(obj1.name,obj2.name); fnSelectAll(obj2.name);}
	flag=false;
}

function showDiv()
{
	document.getElementById('updateDiv').style.display="block";
	document.getElementById('updateDivInner').style.display="block";
	self.focus();
}

function showDiv()
{
	document.getElementById('updateDiv').style.display="none";
	document.getElementById('updateDivInner').style.display="none";
	self.focus();
}
/*
function addRemoveToOrderBy(obj)
{
	//alert("hi");
	var selcolsobj = document.getElementById("sel_tabcols");
	var orderbyObj = document.getElementById("sel_tabOrderBy");
	if(orderbyObj.length)
		orderbyObj.length=0;
	
	for(var i=0;i<selcolsobj.length;i++)
		{
			//alert(selcolsobj.options[i].text);
			var newOption = new Option();
			newOption.text = selcolsobj.options[i].text+"▲";
			newOption.value = selcolsobj.options[i].value+"~ASC";
			orderbyObj.options[orderbyObj.length] = newOption;
		}
	 
}
*/
function submitPage(){

//	if(validateform(false)){
		var selcolparam="";
		
		for(var i=0;i<document.getElementById("sel_tabcols").length;i++)
		{
			selcolparam += "~"+document.getElementById("sel_tabcols").options[i].value+"^"+document.getElementById("sel_tabcols").options[i].text;
		}
		//alert("selcolparam generating : " + selcolparam);
		document.getElementById("selcolparam").value=selcolparam;
		fnSelectAll("SEL_DEPARTMENTCODE");
		fnSelectAll("SEL_DESTINATIONMARKING");
		fnSelectAll("SEL_SUBJECTCODE");
		fnSelectAll("SEL_RECEIVEDFROM");
		fnSelectAll("SEL_FILESTATUS1");
		fnSelectAll("SEL_FILESTATUS2");
		fnSelectAll("sel_tabcols");
//		fnSelectAll("sel_tabOrderBy");
		disabled(document.getElementById("DEPARTMENTCODE"),true);
		disabled(document.getElementById("DESTINATIONMARKING"),true);
		disabled(document.getElementById("SUBJECTCODE"),true);
		disabled(document.getElementById("RECEIVEDFROM"),true);
		disabled(document.getElementById("FILESTATUS1"),true);
		disabled(document.getElementById("FILESTATUS2"),true);
		disabled(document.getElementById("tabcols"),true);
		disabled(document.getElementById("btnsave"),true);
		disabled(document.getElementById("btncancel"),true);
		disabled(document.getElementById("btnpreview"),true);
		document.forms[0].submit();
	}
//}

function previewReport()
{
	
//	if(validateform(true)){
		var selcolparam="";
		
		for(var i=0;i<document.getElementById("sel_tabcols").length;i++)
		{
			selcolparam += "~"+document.getElementById("sel_tabcols").options[i].value+"^"+document.getElementById("sel_tabcols").options[i].text;
		}
		document.getElementById("selcolparam").value=selcolparam;
		fnSelectAll("SEL_DEPARTMENTCODE");
		fnSelectAll("SEL_DESTINATIONMARKING");
		fnSelectAll("SEL_SUBJECTCODE");
		fnSelectAll("SEL_RECEIVEDFROM");
		fnSelectAll("SEL_FILESTATUS1");
		fnSelectAll("SEL_FILESTATUS2");
		fnSelectAll("sel_tabcols");
//		fnSelectAll("sel_tabOrderBy");
			var url ="GenerateReportFileMovController";
			//document.getElementById("divLoading").style.display="block";
				
			//showDiv();
			
			document.forms[0].action = url;
			document.forms[0].target="popreport";
			document.forms[0].submit();
			window.setTimeout("showDiv();",1000);	
			document.forms[0].target="_self";
			document.forms[0].action = "CustomReportFileMovController";
	}
//}


function  validateform(x)
{
	if( (x || chkblank(document.getElementById("repheader"))) && (document.getElementById("SEL_DEPARTMENTCODE").options.length>0) && (document.getElementById("sel_tabcols").options.length>0) && (document.getElementById("SEL_SUBJECTCODE").options.length>0))
		return true;
	else
		alert("Please enter all compulsary fields !!!");
	return false;
}
 --></script>
</head>

<body>
<form name="customrep" action="CustomReportFileMovController" method="post">
<%
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
//	String branchSQL="SELECT DEPARTMENTCODE FROM TRNFILEHDR ORDER BY DEPARTMENTCODE"; 
//	ArrayList<CommonBean> branchList = (new CommonDAO()).getSQLResult(branchSQL, 1);

//	String finalMarkingSQL="SELECT DESTINATIONMARKING FROM TRNFILEHDR ORDER BY DESTINATIONMARKING"; 
//	ArrayList<CommonBean> finalMarkingList = (new CommonDAO()).getSQLResult(finalMarkingSQL, 1);

 	String querySubject = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT";
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 3);

//	String subjectTypeSQL="SELECT SUBJECTCODE FROM TRNFILEMARKING ORDER BY SUBJECTCODE";
//	ArrayList<CommonBean> subjectTypeList = (new CommonDAO()).getSQLResult(subjectTypeSQL, 1);

 	String querySignedBy = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
				" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='6' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2";
 			
 		/* SELECT B.ROLEID, (SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.ROLEID ) ROLENAME "+ 
 						   " FROM MSTTENURE B WHERE B.ISACTIVE = 'Y' ORDER BY ROLENAME"; */
	ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);
 	String receivedFromSQL = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 		 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'"+
 		 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
 			ArrayList<CommonBean> receivedFromList = (new CommonDAO()).getSQLResult(receivedFromSQL, 2);
	
//	String receivedFromSQL="SELECT RECEIVEDFROM FROM TRNFILEHDR ORDER BY RECEIVEDFROM"; 
//	ArrayList<CommonBean> receivedFromList = (new CommonDAO()).getSQLResult(receivedFromSQL, 1);
	
	String queryfileStatus1 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'0' AND CODE <> '00' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus1 = (new CommonDAO()).getSQLResult(queryfileStatus1, 3);

	String queryfileStatus2 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'0' AND CODE <> '00' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus2 = (new CommonDAO()).getSQLResult(queryfileStatus2, 3);

//	String markingToSQL = "SELECT MARKINGTO FROM TRNFILEINTMARKING ORDER BY MARKINGTO"; 
//	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(markingToSQL, 1);

	String downMarkingToSQL = "SELECT DISTINCT MARKINGTO FROM TRNFILEMARKING WHERE MARKINGTYPE = 'D' AND MARKINGFROM = '"+sessionBean.getLOGINASROLEID()+"' ORDER BY MARKINGTO"; 
	ArrayList<CommonBean> downMarkingToList = (new CommonDAO()).getSQLResult(downMarkingToSQL, 1);

//	String upMarkingToSQL = "SELECT MARKINGTO FROM TRNFILEMARKING WHERE MARKINGTYPE = 'U' ORDER BY MARKINGTO"; 
//	ArrayList<CommonBean> upMarkingToList = (new CommonDAO()).getSQLResult(upMarkingToSQL, 1);

	String markToUpwardSQL = "SELECT DISTINCT A.MARKINGTO, (SELECT B.ROLENAME FROM MSTROLE B WHERE B.ROLEID = A.MARKINGTO) ROLENAME FROM TRNFILEMARKING A order by rolename";
	ArrayList<CommonBean> markToUpwardList = (new CommonDAO()).getSQLResult(markToUpwardSQL, 2);

	String rplyTypeSQL = "SELECT DISTINCT REPLYTYPE FROM TRNFILEHDR WHERE REPLYTYPE IS NOT NULL ORDER BY REPLYTYPE"; 
	ArrayList<CommonBean> rplyTypeList = (new CommonDAO()).getSQLResult(rplyTypeSQL, 1);

	String refTypeSQL = "SELECT DISTINCT REFERENCETYPE FROM TRNFILEHDR ORDER BY REFERENCETYPE"; 
	ArrayList<CommonBean> refTypeList = (new CommonDAO()).getSQLResult(refTypeSQL, 1);

	String queryreplyCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00'"; 
	ArrayList<CommonBean> replyCode = (new CommonDAO()).getSQLResult(queryreplyCode, 3);

%>
<table width="1002px" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td height="4px"></td>
	</tr>
	<tr>
		<td class="appbg">
		<table width="984" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td></td>
					</tr>
					<tr>
						<td align="center">

						<table width="984px" border="1" class="bor"
							cellspacing="0" cellpadding="0"
							style="border-collapse: collapse; margin-top: 7px;">
							<tr>
								<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="tablehead">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<th class="tableheadtext" height="20">Custom Report Parameter <c:out value='<%= repheader.length()>0?" { "+repheader+" }":"" %>' /></th>
										</table></td>
									</tr>
									<tr>
										<td>
										
										<table width="97%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td style="padding-left: 7px;"><table bordercolor="#cdcdcd" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;">
                  <tr class="firstbg">
                  		<th colspan="8" height="20"> Filter Criteria<input type="hidden" name="dummyfield" value="" /><input type="hidden" name="repType" value="2" /></th>
                  </tr>
													<tr class="firstbg">
														<th colspan="8">
														<table width="100%" border="0" style="width: 939px;">
															<tbody align="left">
																<tr class="trodd">
																	<td width="124"><font color="#000000">Registration Date</font></td>
																	<td width="300" nowrap="nowrap"><select
																		name="REGDATECOND"
																		id="REGDATECOND"
																		onchange="showHideRegDate();">
																		<option value="=" >EQUAL</option>
																		<option value="BETWEEN" selected="selected">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="REGDATEFROM" id="REGDATEFROM" size="10"
																	value="<c:out value='<%= REGDATEFROM!=""?REGDATEFROM:sessionBean.getTENURESTARTDATE()%>' />"/>
																	&nbsp;<input type="text" name="REGDATETO" id="REGDATETO"
																		size="10" value="<c:out value='<%= REGDATETO!=""?REGDATETO:serverDate%>' />"/></td>
																	<td width="164"><font color="#000000">File Type</font></td>
																	<td colspan="3" width="345"><% if(sessionBean.getISCONF().equalsIgnoreCase("0")){ %>
			    			<select name="FILETYPE" id="FILETYPE">
								<option value="" selected="selected">- SELECT -</option>
																		<option value="A" <%= FILETYPE.equalsIgnoreCase("A")?"selected":""%>>Approval</option>
																		<option value="D" <%= FILETYPE.equalsIgnoreCase("D")?"selected":""%>>Draft</option>
																		<option value="P" <%= FILETYPE.equalsIgnoreCase("P")?"selected":""%>>Position</option>
																		</select>
				<%}else{ %>
							<select	name="FILETYPE"	id="FILETYPE">
								<option value="" selected="selected">- SELECT -</option>
																		<option value="A" <%= FILETYPE.equalsIgnoreCase("A")?"selected":""%>>Approval</option>
																		<option value="D" <%= FILETYPE.equalsIgnoreCase("D")?"selected":""%>>Draft</option>
																		<option value="P" <%= FILETYPE.equalsIgnoreCase("P")?"selected":""%>>Position</option>
																		<option value="C" <%= FILETYPE.equalsIgnoreCase("C")?"selected":""%>>Confidential</option>
																	
							</select>
				<%} %></td>
																</tr>
																<tr class="treven">
																	<td width="124"><font color="#000000">File No.</font></td>
																	<td width="300"><input type="text" name="FILENO"
																		size="34" value="<c:out value='<%= FILENO %>' />"/></td>
																	<td width="164"><font color="#000000">Link File No</font></td>
																	<td colspan="3" width="345"><input type="text"
																		name="LINKFILENO" size="34" value="<c:out value='<%= LINKFILENO %>' />"/></td>
																</tr>
																<tr class="trodd">
																	<td width="124"><font color="#000000">PUC</font></td>
																	<td width="300"><input type="text" name="PUC"
																		id="PUC" size="34" value="<c:out value='<%= PUC %>' />"/></td>
																	<td width="164"><font color="#000000">Subject</font></td>
																	<td colspan="3" width="345"><input type="text"
																		name="SUBJECT" id="SUBJECT" size="34" value="<c:out value='<%= SUBJECT %>' />"/></td>
																</tr>
																<tr class="treven">
																	<td width="124"><font color="#000000">Proposed Path</font></td>
																	<td width="300"><input type="text"
																		name="PROPOSEDPATH" id="PROPOSEDPATH" size="34" value="<c:out value='<%= PROPOSEDPATH %>' />"/></td>
																	<td width="164"><font color="#000000">Mark To (Upward)</font></td>
																	<td colspan="3" width="345">
													<select	name="MARKTOUP" id="MARKTOUP">
														<option value="">- SELECT -</option>
													<%
														for(int i=0;i<markToUpwardList.size();i++){
														CommonBean beanCommon=(CommonBean) markToUpwardList.get(i);
													%>
														<option value="<c:out value='<%= beanCommon.getField1()%>' />" <%=beanCommon.getField1().equalsIgnoreCase(MARKTOUP)?"selected":""%>><c:out value='<%= beanCommon.getField2()%>' /></option>
													<%}%>
																	</select></td>
																</tr>
																<tr class="trodd">
																	<td width="124"><font color="#000000">Mark On (Upward)</font></td>
																	<td width="300"><select name="MARKTOUPCOND"	id="MARKTOUPCOND" onchange="showHideMarkingDate();">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="MARKTOUPFROM" size="10" id="MARKTOUPFROM"
																		 value="<c:out value='<%= MARKTOUPFROM %>' />"/>
																	&nbsp;<input type="text" name="MARKTOUPTO" size="10" style="display: none;" id="MARKTOUPTO"
																		 value="<c:out value='<%= MARKTOUPTO %>' />"/></td>
																	<td width="164"><font color="#000000">Marking Remarks<br />(Upward)</font>
																	</td>
																	<td colspan="3" width="345"><input type="text"
																		name="MARKINGREMARKS" id="MARKINGREMARKS" size="34" value="<c:out value='<%= MARKINGREMARKS %>' />"/></td>
																</tr>
																<tr class="treven">
																	<td width="124"><font color="#000000">Mark To (Internal)</font></td>
																	<td width="300"><select name="INTMARKING"
																		id="INTMARKING">
																		<option value="">- SELECT -</option>
				<%
					for(int i=0;i<markToUpwardList.size();i++){
					CommonBean beanCommon=(CommonBean) markToUpwardList.get(i);
				%>
<option value="<c:out value='<%= beanCommon.getField1()%>' />" <%=beanCommon.getField1().equalsIgnoreCase(INTMARKING)?"selected":""%>><c:out value='<%= beanCommon.getField2()%>' /></option>
																		<%}%>
																	</select></td>
																	<td width="164"><font color="#000000">Mark On</font> (Internal)</td>
																	<td colspan="3" width="345"><select
																		name="INTMARKINGCOND" id="INTMARKINGCOND"
																		onchange="showHideIntMarking();">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="INTMARKINGFROM"
																		id="INTMARKINGFROM" size="10" value="<c:out value='<%= INTMARKINGFROM %>' />"/>
																	&nbsp;<input type="text" name="INTMARKINGTO" size="10"
																		style="display: none;" id="INTMARKINGTO" value="<c:out value='<%= INTMARKINGTO %>' />"/></td>
																</tr>
																<tr class="trodd">
																	<td width="124"><font color="#000000">Return On</font> (Internal)</td>
																	<td width="300"><select name="RETURNONCOND"
																		id="RETURNONCOND" onchange="showHideReturnOn();">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="RETURNONFROM"
																		id="RETURNONFROM" size="10"
																		
																		 value="<c:out value='<%= RETURNONFROM %>' />"/>
																	&nbsp;<input type="text" name="RETURNONTO" size="10"
																		style="display: none;" id="RETURNONTO"
																		
																		 value="<c:out value='<%= RETURNONTO %>' />"/></td>
																	<td width="164"><font color="#000000">Full Status</font></td>
																	<td width="345" colspan="3"><input type="text"
																		name="MARKINGREMARKS1" id="MARKINGREMARKS1" size="34" value="<c:out value='<%= MARKINGREMARKS1 %>' />"/></td>
																</tr>
																<tr class="treven">
																	<td width="124"><font color="#000000">Mark To (Downward)</font></td>
																	<td width="300"><select name="MARKTODOWN" id="MARKTODOWN">
																		<option value="">- SELECT -</option>
																		<%
					for(int i=0;i<markToUpwardList.size();i++){
					CommonBean beanCommon=(CommonBean) markToUpwardList.get(i);
				%>
<option value="<c:out value='<%= beanCommon.getField1()%>' />" <%=beanCommon.getField1().equalsIgnoreCase(MARKTODOWN)?"selected":""%>><c:out value='<%= beanCommon.getField2()%>' /></option>
																		<%}%>
																	</select></td>
																	<td width="164"><font color="#000000">Mark On (Downward)</font></td>
																	<td width="345" colspan="3"><select
																		name="MARKTODOWNCOND" id="MARKTODOWNCOND"
																		onchange="showHideMarkingOn();">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="MARKTODOWNFROM"
																		id="MARKTODOWNFROM" size="10" value="<c:out value='<%= MARKTODOWNFROM %>' />"/>
																	&nbsp;<input type="text" name="MARKTODOWNTO"
																		size="10" style="display: none;" id="MARKTODOWNTO"
																		 value="<c:out value='<%= MARKTODOWNTO %>' />"/></td>
																</tr>
																<tr class="trodd">
																	<td width="124"><font color="#000000">Remarks<br />(Downward)
																	</font></td>
																	<td width="300"><input type="text" name="MARKINGREMARKS2"
																		id="REMARKS2" size="34" value="<c:out value='<%= MARKINGREMARKS2 %>' />"/></td>
																	<td width="164"><font color="#000000">Reply Type</font></td>
																	<td width="345" colspan="3">
																	<select	name="REPLYTYPE" id="REPLYTYPE">
																		<option value="">- SELECT -</option>
																		<%
																			for(int k=0;k<replyCode.size();k++){
																			CommonBean beanCommon = (CommonBean) replyCode.get(k);
																		%>
																			<option value="<c:out value='<%=beanCommon.getField1()%>' />"	<%=beanCommon.getField1().equalsIgnoreCase(REPLYTYPE)?"selected":""%>><c:out value='<%=beanCommon.getField2()%>' /></option>
																		<%}%>
																	</select></td>
																</tr>
																<tr class="treven">
																	<td width="124"><font color="#000000">Reason</font></td>
																	<td width="300"><input type="text" name="REASON"
																		id="REASON" size="34" value="<c:out value='<%= REASON %>' />"/></td>
																	
																	<td width="124"><font color="#000000">Recd From (Except)</font></td>
																	<td width="300"><select name="RECEIVEDFROMEXCEPT" id="RECEIVEDFROMEXCEPT">
																		<option value="">- SELECT -</option>
																		<%
					for(int i=0;i<markToUpwardList.size();i++){
					CommonBean beanCommon=(CommonBean) markToUpwardList.get(i);
				%>
<option value="<c:out value='<%= beanCommon.getField1()%>' />" <%=beanCommon.getField1().equalsIgnoreCase(MARKTODOWN)?"selected":""%>><c:out value='<%= beanCommon.getField2()%>' /></option>
																		<%}%>
																	</select></td>
																</tr>
															</tbody>
														</table>
														</th>
													</tr>
													<tr class="firstbg">
                   		<th></th><th>Available</th><th></th><th>Selected <sup style="color: red;">*</sup></th><th></th><th>Available</th><th></th><th>Selected </th>
                   		
                   	</tr>
                  	<tr class="treven">
                  		<td>Branch</td>
                  		<td> 
                  		<select ondblclick="fnMoveItems('DEPARTMENTCODE', 'SEL_DEPARTMENTCODE');" name="DEPARTMENTCODE" id="DEPARTMENTCODE" multiple="multiple" style="width: 180px; height: 100px">
						<%
							for(int k=0;k<querySignedByList.size();k++){
							CommonBean beanCommon = (CommonBean) querySignedByList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>' />"><c:out value='<%=beanCommon.getField2()%>' /></option>
						<%}%>
						</select> 
                  		</td>
                  		<td valign="middle" align="center"> 
                  			<img align="middle" src="images/next.gif" onclick="fnMoveItems( 'DEPARTMENTCODE','SEL_DEPARTMENTCODE');" /> <br />
                  			<img align="middle" src="images/prev.gif" onclick="fnMoveItems( 'SEL_DEPARTMENTCODE','DEPARTMENTCODE');" /> 
                  		</td>
                  		<td>
                  		<select  ondblclick="fnMoveItems('SEL_DEPARTMENTCODE', 'DEPARTMENTCODE');" name="SEL_DEPARTMENTCODE" id="SEL_DEPARTMENTCODE" multiple="multiple" style="width: 180px; height: 100px">
										
										</select>
                  		</td>
                  		<td>
                  			Final Marking</td>
                  		<td>
                  		<select ondblclick="fnMoveItems('DESTINATIONMARKING', 'SEL_DESTINATIONMARKING');" name="DESTINATIONMARKING" id="DESTINATIONMARKING" multiple="multiple" style="width: 180px; height: 100px" >
						<%
							for(int k=0;k<querySignedByList.size();k++){
							CommonBean beanCommon = (CommonBean) querySignedByList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>' />"><c:out value='<%=beanCommon.getField2()%>' /></option>
						<%}%>
						</select>
                  		</td>
                  		<td valign="middle" align="center"> 
                  			<img align="middle" src="images/next.gif" onclick="fnMoveItems( 'DESTINATIONMARKING','SEL_DESTINATIONMARKING')" /> <br />
                  			<img align="middle" src="images/prev.gif" onclick="fnMoveItems( 'SEL_DESTINATIONMARKING','DESTINATIONMARKING')" /> 
                  		</td>
                  		<td>
                  		<select ondblclick="fnMoveItems('SEL_DESTINATIONMARKING', 'DESTINATIONMARKING');" name="SEL_DESTINATIONMARKING" id="SEL_DESTINATIONMARKING" multiple="multiple" style="width: 180px; height: 100px" >
                  		
                  		</select>
                  		</td>
                  	</tr>
                  	<tr class="firstbg">
                   		<th></th><th>Available</th><th></th><th>Selected <sup style="color: red;">*</sup></th><th></th><th>Available</th><th></th><th>Selected </th>
                   	</tr>                
                  	<tr class="trodd">
                  		<td>Subject Type<br />
														</td> <td>
				<select multiple="multiple"	style="width: 180px; height: 100px" id="SUBJECTCODE"	name="SUBJECTCODE" ondblclick="fnMoveItems('SUBJECTCODE','SEL_SUBJECTCODE')">
				<%
					for(int j=0;j<subjectList.size();j++){
					CommonBean beanCommon = (CommonBean) subjectList.get(j);
				%>
					<option value="<c:out value='<%=beanCommon.getField1()%>' />"><c:out value='<%=beanCommon.getField2()%>' /></option>
				<%}%>
				</select>
										
										
										</td>
										<td valign="middle" align="center"> 
						           			<img align="middle" src="images/next.gif" onclick="fnMoveItems( 'SUBJECTCODE','SEL_SUBJECTCODE');" /> <br />
						           			<img align="middle" src="images/prev.gif" onclick="fnMoveItems( 'SEL_SUBJECTCODE','SUBJECTCODE');" /> 
						           		</td>
						           		<td>
					                  		<select ondblclick="fnMoveItems('SEL_SUBJECTCODE','SUBJECTCODE')" name="SEL_SUBJECTCODE" id="SEL_SUBJECTCODE" multiple="multiple" style="width: 180px; height: 100px" >
					                  		
					                  		</select>
				                  		</td>
												<td>Receid From<br />
														</td> <td>
				<select multiple="multiple"	style="width: 180px; height: 100px" id="RECEIVEDFROM"	name="RECEIVEDFROM" ondblclick="fnMoveItems('RECEIVEDFROM','SEL_RECEIVEDFROM')">
				<%
					for(int k=0;k<receivedFromList.size();k++){
					CommonBean beanCommon = (CommonBean) receivedFromList.get(k);
				%>
					<option value="<c:out value='<%=beanCommon.getField1()%>' />"><c:out value='<%=beanCommon.getField2()%>' /></option>
				<%}%>
				</select>
							</td>
							<td valign="middle" align="center"> 
		           			<img align="middle" src="images/next.gif" onclick="fnMoveItems( 'RECEIVEDFROM','SEL_RECEIVEDFROM');" /> <br />
		           			<img align="middle" src="images/prev.gif" onclick="fnMoveItems( 'SEL_RECEIVEDFROM','RECEIVEDFROM');" /> 
			           		</td>
			           		<td>
					                  		<select ondblclick="fnMoveItems('SEL_RECEIVEDFROM','RECEIVEDFROM')" name="SEL_RECEIVEDFROM" id="SEL_RECEIVEDFROM" multiple="multiple" style="width: 180px; height: 100px" >
					                  		
					                  		</select>
				                  		</td>
											</tr>

													<tr class="firstbg">
														<th></th>
														<th>Available</th>
														<th valign="middle" align="center"></th>
														<th>Selected</th>
														<th></th>
														<th>Available</th>
														<th valign="middle" align="center"></th>
														<th>Selected</th>
													</tr>
													<tr class="treven">
														<td>File Status 1</td>
														<td>
				<select multiple="multiple"	style="width: 180px; height: 100px" id="FILESTATUS1" name="FILESTATUS1"	ondblclick="fnMoveItems('FILESTATUS1','SEL_FILESTATUS1')">
				<%
					for(int k=0;k<fileStatus1.size();k++){
					CommonBean beanCommon = (CommonBean) fileStatus1.get(k);
				%>
					<option value="<c:out value='<%=beanCommon.getField1()%>' />"><c:out value='<%=beanCommon.getField2()%>' /></option>
				<%}%>
				</select>
				</td>
														<td valign="middle" align="center">
							<img align="middle" src="images/next.gif" onclick="fnMoveItems( 'FILESTATUS1','SEL_FILESTATUS1');" /> <br />
		           			<img align="middle" src="images/prev.gif" onclick="fnMoveItems( 'SEL_FILESTATUS1','FILESTATUS1');" /> 
														</td>
														<td><select
															ondblclick="fnMoveItems('SEL_FILESTATUS1','FILESTATUS1')"
															name="SEL_FILESTATUS1" id="SEL_FILESTATUS1"
															multiple="multiple" style="width: 180px; height: 100px">

														</select></td>
														<td>File Status 2</td>
														<td>
				<select multiple="multiple"	style="width: 180px; height: 100px" id="FILESTATUS2" name="FILESTATUS2" ondblclick="fnMoveItems('FILESTATUS2','SEL_FILESTATUS2')">
				<%
					for(int k=0;k<fileStatus2.size();k++){
					CommonBean beanCommon = (CommonBean) fileStatus2.get(k);
				%>
					<option value="<c:out value='<%=beanCommon.getField1()%>' />"><c:out value='<%=beanCommon.getField2()%>' /></option>
				<%}%>
				</select>
				</td>
														<td valign="middle" align="center">
							<img align="middle" src="images/next.gif" onclick="fnMoveItems( 'FILESTATUS2','SEL_FILESTATUS2');" /> <br />
		           			<img align="middle" src="images/prev.gif" onclick="fnMoveItems( 'SEL_FILESTATUS2','FILESTATUS2');" /> 
														</td>
														<td><select ondblclick="fnMoveItems('SEL_FILESTATUS2','FILESTATUS2')"
															name="SEL_FILESTATUS2" id="SEL_FILESTATUS2" multiple="multiple"
															style="width: 180px; height: 100px">

														</select></td>
													</tr>
													<tr class="treven">
														<th colspan="8">
														<br />
														
														</th>
													</tr>
												</table>
												<br /><table>
                   <tr>
                   <td>
                 <table border="1" bordercolor="#cdcdcd" cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin-top:7px;">
                 	<tr>
                   		<th class="firstbg" colspan=5"> View Fields </th>
                   	</tr>
                   	<tr class="firstbg">
                   		<th></th><th>Available</th><th></th><th>Selected <sup style="color: red;">*</sup></th><th></th>
                   	</tr>
                 	<tr class="treven">
                 						<td>
                 							Columns
                 						</td>
                 						<td>
					                  		<select ondblclick="fnMoveItems('tabcols','sel_tabcols'); showAverage(this);" name="tabcols" id="tabcols" multiple="multiple" style="width: 180px; height: 100px" >
	<%-- <option value="TRNFILEHDR.REGISTRATIONNODES">Registration No.</option>  
	<option value="TO_CHAR(TRNFILEHDR.REGISTRATIONDATEDES,'DD-MM-YYYY')">Registration Date</option>                        
	<option value="DECODE(TRNFILEHDR.REferenceTYPE,'A','APPROVAL','D','DRAFT','P','POSITION','C','CONFIDENTIAL','')">File Type</option>                    
	<option value="TRNFILEHDR.FILENO">File No.</option> 
	<% String deptCode = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEHDR.DEPARTMENTCODE) "; %>  
	<option value="<%=deptCode %>">Branch</option>                      
<% String finalMarking = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEHDR.DESTINATIONMARKING) "; %>
	<option value="<%=finalMarking %>">Final Marking</option>  
	<% String recFrom = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEHDR.RECEIVEDFROM) "; %>
	<option value="<%=recFrom %>">Received From</option>               
	<option value="TRNFILEHDR.SUBJECT">Subject</option> 
	<% String intMarking = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEINTMARKING.MARKINGTO) "; %>
	<option value="substr(getFileCustomReportData(TRNFILEHDR.FMID),2)">Internal Marking</option>                   
<!--	<option value="getFileCustomReportData(TRNFILEHDR.FMID)">Internal Marking On</option>     -->                    
<!-- <option value="TRNFILEHDR.PROPOSEDPATH">Proposed Path</option>  -->            
<% String markToUp = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEMARKING.MARKINGTO) "; %>
<!-- <option value="<%=markToUp %>">Mark To (upward)</option>  -->                 
<!-- <option value="TO_CHAR(TRNFILEMARKING.MARKINGDATE,'DD-MM-YYYY')">Marking On (upward)</option>  -->                    
<!-- <option value="TRNFILEHDR.REMARKS">Marking Remarks</option>  -->                        
         
	<!--	<option value="getFileCustomReportData(TRNFILEHDR.FMID)">Return On</option>  -->
	<% String fStatus1 = "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = TRNFILEHDR.FILESTATUS1) "; %>
	<option value="<%=fStatus1 %>">File Status 1</option>     
<% String fStatus2 = "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = TRNFILEHDR.FILESTATUS2) "; %>                 
	<option value="<%=fStatus2 %>">File Status 2</option>                     
	<option value="TRNFILEHDR.REMARKS">Full Status</option>
<% String markToDown = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEMARKING.MARKINGTO) "; %>
	<option value="<%=markToDown %>">Mark To (downward)</option>                         
	<option value="TO_CHAR(TRNFILEMARKING.MARKINGDATE,'DD-MM-YYYY')">Marking On (downward)</option>                  
	<option value="TRNFILEMARKING.MARKINGREMARK">Remarks 2 (marking)</option>                      
	<option value="TRNFILEHDR.REPLYTYPE">Reply Type</option>                      
	<option value="TRNFILEHDR.REASON">Reason</option>                      

	                      
<!-- <option value="TRNFILEHDR.REPLYTYPE">Subject Type</option>  -->      --%>     

<option value="1">Registration No.</option>  
	<option value="2">Registration Date</option>                        
	<option value="3">File Type</option>                    
	<option value="4">File No.</option> 
	<% String deptCode = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEHDR.DEPARTMENTCODE) "; %>  
	<option value="5">Branch</option>                      
<% String finalMarking = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEHDR.DESTINATIONMARKING) "; %>
	<option value="6">Final Marking</option>  
	<% String recFrom = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEHDR.RECEIVEDFROM) "; %>
	<option value="7">Received From</option>               
	<option value="8">Subject</option> 
	<% String intMarking = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEINTMARKING.MARKINGTO) "; %>
	<option value="9">Internal Marking</option>                   
<!--	<option value="getFileCustomReportData(TRNFILEHDR.FMID)">Internal Marking On</option>     -->                    
<!-- <option value="TRNFILEHDR.PROPOSEDPATH">Proposed Path</option>  -->            
<% String markToUp = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEMARKING.MARKINGTO) "; %>
<!-- <option value="<%=markToUp %>">Mark To (upward)</option>  -->                 
<!-- <option value="TO_CHAR(TRNFILEMARKING.MARKINGDATE,'DD-MM-YYYY')">Marking On (upward)</option>  -->                    
<!-- <option value="TRNFILEHDR.REMARKS">Marking Remarks</option>  -->                        
         
	<!--	<option value="getFileCustomReportData(TRNFILEHDR.FMID)">Return On</option>  -->
	<% String fStatus1 = "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = TRNFILEHDR.FILESTATUS1) "; %>
	<option value="10">File Status 1</option>     
<% String fStatus2 = "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = TRNFILEHDR.FILESTATUS2) "; %>                 
	<option value="11">File Status 2</option>                     
	<option value="12">Full Status</option>
<% String markToDown = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEMARKING.MARKINGTO) "; %>
	<option value="13">Mark To (downward)</option>                         
	<option value="14">Marking On (downward)</option>                  
	<option value="15">Remarks 2 (marking)</option>                      
	<option value="16">Reply Type</option>                      
	<option value="17">Reason</option>         
	<option value="18">Disposal Time</option> 
	<option value="19">Average Disposal Time</option>              
<option value="20">E Office File Number</option>  
<option value="21">Pendency(No. of Days)</option>            
                   
					                  		</select>
				                  		</td>
                 						<td valign="middle" align="center"> 
						           			<img align="middle" src="images/next.gif" onclick="fnMoveItems( 'tabcols','sel_tabcols');" /> <br />
						           			<img align="middle" src="images/prev.gif" onclick="fnMoveItems( 'sel_tabcols','tabcols');" /> 
						           		</td>
						           		<td>
					                  		<select ondblclick="renameColumn(this);" name="sel_tabcols" id="sel_tabcols" multiple="multiple" style="width: 180px; height: 100px">
					                  		
					                  		</select><input type="hidden" value="<c:out value='<%=selcolparam%>' />" name="selcolparam" id="selcolparam" />
				                  		</td>
				                  		<td>
				                  		<input class="butts" value="   &uarr;   " type="button" name="btnMoveRightAll0"	onclick="fnMoveUpDown(this,'sel_tabcols','U')" /><br />
										<input class="butts" value="   &darr;   " type="button"	name="btnMoveRightAll1"	onclick="fnMoveUpDown(this,'sel_tabcols','D')" />
				                  		</td>
                 	</tr>
                 </table> 
                 </td>
                 <td>
                 </td></tr></table>
                 
                 
                 <br />
                 
                 
                 
                 
                  <table border="1" bordercolor="#7e7d7d" cellspacing="0" cellpadding="0" style="border-collapse:collapse; border: solid 1px #7e7d7d;">
                  	<tr class="treven">
              			<td>   Report  Title</td><td> <input type="text" name="repheader" id="repheader" value="<c:out value='<%= repheader %>' />" size="100" /></td>
              		</tr>
              		<tr class="tab1" style="display: none;">
              			<td> Report Id</td><td><input type="text"  class="readonly" name="reportid" value="<c:out value='<%= reportid %>' />" />
              			
              			</td>
              		</tr>
                  </table>
              </td>
              </tr>
              
              
              
              
              <tr>
              <td>&nbsp;</td>
              </tr>
            </table>
            
            <table bordercolor="#7e7d7d" cellspacing="0" cellpadding="2" style="border-collapse:collapse;">
            	<tr class="firstbg">
            		<td><input type="button" name="btnsave" id="btnsave" class="butts" value=" Save " onclick="submitPage();" /></td>
            		<td><input type="button" name="btncancel" id="btncancel" class="butts" value=" Cancel " onclick="window.location.href='CustomReportFileMov.jsp'" /></td>
            		<td><input type="button" name="btnpreview" id="btnpreview" class="butts" value=" Preview " onclick="previewReport();" /></td>
            	</tr>
            </table>
            
            
           
<script type="text/javascript">
<!--
reset();
//-->
</script>

<!--  <%=" Model Starts -->"%>
<DIV class="transparent_class" align="center" style="z-index:2000; background-color:#000; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 150%;" id="updateDiv">
	
</DIV>
<DIV align="center"  style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:80px; top: 260px; display: none; width: 100%" id="updateDivInner">
<DIV class="pageheader" align="center" style="color:white; font-family:tahoma ; background-color: #7e7d7d; width: 300px; border: groove; text-align: center; background-image:url(images/top-hd-bar-bg.gif); background-repeat: repeat; " id="divLoading">
			<BR>Generating Report
			<BR>
			<BR>Please Wait <img src="images_old/progress_bar.gif" alt="Loading" />
			<BR> <input type="button" class="butts" value=" Close " onclick="hideDiv();" /> <BR>
</DIV>
</DIV>
<%="<!-- Model Ends "%>    -->
</td>
											</tr>


										</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>


						</table>
						</td>
					</tr>
				</table>

				</td>
			</tr>

		</table>
 
</body>
</html>