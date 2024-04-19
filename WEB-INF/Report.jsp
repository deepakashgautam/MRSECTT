<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.MstLogin" %>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT</title>
<script type="text/javascript" src="script/scripts.js"></script>
<script type="text/javascript" src="script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<%
String reportid = request.getParameter("reportid") != null ? request.getParameter("reportid") : "";
String repheader = request.getParameter("repheader") != null ? request.getParameter("repheader") : "";
String selcolparam = request.getParameter("selcolparam") != null ? request.getParameter("selcolparam") : "";

String BUDGETYEAR = request.getParameter("BUDGETYEAR") != null ? request.getParameter("BUDGETYEAR") : "";

String INDATECONDITION = request.getParameter("INDATECONDITION") != null ? request.getParameter("INDATECONDITION") : "";
String INDATEFROM = request.getParameter("INDATEFROM") != null ? request.getParameter("INDATEFROM") : "";
String INDATETO = request.getParameter("INDATETO") != null ? request.getParameter("INDATETO") : "";

String LETDATECONDITION = request.getParameter("LETDATECONDITION") != null ? request.getParameter("LETDATECONDITION") : "";
String LETDATEFROM = request.getParameter("LETDATEFROM") != null ? request.getParameter("LETDATEFROM") : "";
String LETDATETO = request.getParameter("LETDATETO") != null ? request.getParameter("LETDATETO") : "";

String RECEIVEDFROM = request.getParameter("RECEIVEDFROM") != null ? request.getParameter("RECEIVEDFROM") : "";
String STATUS = request.getParameter("STATUS") != null ? request.getParameter("STATUS") : "";
String STATE = request.getParameter("STATE") != null ? request.getParameter("STATE") : "";
String SUBJECT = request.getParameter("SUBJECT") != null ? request.getParameter("SUBJECT") : "";
String ACKNBY = request.getParameter("ACKNBY") != null ? request.getParameter("ACKNBY") : "";

String ACKNDATECONDITION = request.getParameter("ACKNDATECONDITION") != null ? request.getParameter("ACKNDATECONDITION") : "";
String ACKNDATEFROM = request.getParameter("ACKNDATEFROM") != null ? request.getParameter("ACKNDATEFROM") : "";
String ACKNDATETO = request.getParameter("ACKNDATETO") != null ? request.getParameter("ACKNDATETO") : "";

String MARKINGDATECONDITION = request.getParameter("MARKINGDATECONDITION") != null ? request.getParameter("MARKINGDATECONDITION") : "";
String MARKINGDATEFROM = request.getParameter("MARKINGDATEFROM") != null ? request.getParameter("MARKINGDATEFROM") : "";
String MARKINGDATETO = request.getParameter("MARKINGDATETO") != null ? request.getParameter("MARKINGDATETO") : "";

String TARGETDATECONDITION = request.getParameter("TARGETDATECONDITION") != null ? request.getParameter("TARGETDATECONDITION") : "";
String TARGETDATEFROM = request.getParameter("TARGETDATEFROM") != null ? request.getParameter("TARGETDATEFROM") : "";
String TARGETDATETO = request.getParameter("TARGETDATETO") != null ? request.getParameter("TARGETDATETO") : "";

String REMARKS = request.getParameter("REMARKS") != null ? request.getParameter("REMARKS") : "";
String SIGNEDBY = request.getParameter("SIGNEDBY") != null ? request.getParameter("SIGNEDBY") : "";

String SIGNEDONCONDITION = request.getParameter("SIGNEDONCONDITION") != null ? request.getParameter("SIGNEDONCONDITION") : "";
String SIGNEDONFROM = request.getParameter("SIGNEDONFROM") != null ? request.getParameter("SIGNEDONFROM") : "";
String SIGNEDONTO = request.getParameter("SIGNEDONTO") != null ? request.getParameter("SIGNEDONTO") : "";

String TAG = request.getParameter("TAG") != null ? request.getParameter("TAG") : "";
String RLY = request.getParameter("RLY") != null ? request.getParameter("RLY") : "";
String PLACE = request.getParameter("PLACE") != null ? request.getParameter("PLACE") : "";

String PLCRLYCONDITION = request.getParameter("SIGNEDONCONDITION") != null ? request.getParameter("SIGNEDONCONDITION") : "";
String PLCRLYFROM = request.getParameter("SIGNEDONFROM") != null ? request.getParameter("SIGNEDONFROM") : "";
String PLCRLYTO = request.getParameter("SIGNEDONTO") != null ? request.getParameter("SIGNEDONTO") : "";
// new code (suneel)

//String [] ROLEID = request.getParameterValues("SEL_ROLEID") != null ? request.getParameterValues("SEL_ROLEID") :  new String [0];
String [] REFCLASS = request.getParameterValues("SEL_REFCLASS") != null ? request.getParameterValues("SEL_REFCLASS") :  new String [0];
String [] REFCATEGORY = request.getParameterValues("SEL_REFCATEGORY") != null ? request.getParameterValues("SEL_REFCATEGORY") :  new String [0];
String [] SUBJECTTYPE = request.getParameterValues("SEL_SUBJECTTYPE") != null ? request.getParameterValues("SEL_SUBJECTTYPE") :  new String [0];	
String [] MARKTO = request.getParameterValues("SEL_MARKTO") != null ? request.getParameterValues("SEL_MARKTO") :  new String [0];			
//String [] STATE = request.getParameterValues("SEL_STATE") != null ? request.getParameterValues("SEL_STATE") :  new String [0];
//String [] seltabOrderBy = request.getParameterValues("sel_tabOrderBy") != null ? request.getParameterValues("sel_tabOrderBy") :  new String [0];
String [] selcolval = request.getParameterValues("sel_tabcols") != null ? request.getParameterValues("sel_tabcols") :  new String [0];




/*
int startyr = (Integer.parseInt(budgetyear.split("-")[0]));
	DateFormat df1 = new SimpleDateFormat("yyyy");
	String currDate = df1.format(new Date());
	int curryear = ((Integer.parseInt(currDate)));
*/
ArrayList STATEdarr = new CommonDAO().getState();
ArrayList planheadarr = new ArrayList();
/*
	if ("cepd".equalsIgnoreCase(session.getAttribute("loginid")
			.toString().length() > 4 ? session.getAttribute("loginid")
			.toString().substring(0, 4) : "")) {
		planheadarr = cd.getPlanhd(cd.getPlanhdList("pce"
				+ session.getAttribute("loginid").toString().substring(
						4)));
	} else {
		planheadarr = cd
				.getPlanhd(session.getAttribute("CATEGORY") != null ? session
						.getAttribute("CATEGORY").toString()
						: "");
	}
	
	
		if(CATEGORY.length==0){
	CATEGORY = new String [planheadarr.size()];
	for(int i=0;i<planheadarr.size();i++)
		CATEGORY[i]=  ((CommonBean)  planheadarr.get(i)).getField1();
	}
	
	
	planheadarr = cd.getPlanhd(cd.getPlanhdList("edceg"));
*/	

//	ArrayList rlycodearr = cd.getRly(sessionrly);

	ArrayList divarr = new ArrayList();		
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");

 %>
 
<script>

   $(document).ready(function(){
    $("#RECEIVEDFROM").autocomplete("getReferenceNamesData.jsp", {scroll:false});
    $('#RECEIVEDFROM').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('RECEIVEDFROM').value = dataarr[0];
		}
	});

    $("#STATUS").autocomplete("getStatus.jsp", {scroll:false});
    $('#STATUS').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('STATUS').value = dataarr[0];
		}
	});

    $("#STATE").autocomplete("getState.jsp", {scroll:false});
    $('#STATE').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('STATE').value = dataarr[0];
		}
	});
  });
 
function reset()
{

		//fnSelectAll("CATEGORY");
		//fnMoveAll("CATEGORY","sel_CATEGORY"); 

	// Set Railway and Division
	//alert("break");
	
	//Set REF Class
	var refClass= new Array("0" <% for(int i=0;i<REFCLASS.length;i++){%>,"<%= REFCLASS[i] %>"<%} %>);
	mulitselect(document.getElementById("REFCLASS"),document.getElementById("SEL_REFCLASS"),refClass);
	
	//Set REF Category
	var refCategory= new Array("0" <% for(int i=0;i<REFCATEGORY.length;i++){%>,"<%= REFCATEGORY[i] %>"<%} %>);
	mulitselect(document.getElementById("REFCATEGORY"),document.getElementById("SEL_REFCATEGORY"),refCategory);

	//Set Subject Type
	var subType= new Array("0" <% for(int i=0;i<SUBJECTTYPE.length;i++){%>,"<%= SUBJECTTYPE[i] %>"<%} %>);
	mulitselect(document.getElementById("SUBJECTTYPE"),document.getElementById("SEL_SUBJECTTYPE"),subType);

	//Set Subject Type
	var markTo= new Array("0" <% for(int i=0;i<MARKTO.length;i++){%>,"<%= MARKTO[i] %>"<%} %>);
	mulitselect(document.getElementById("MARKTO"),document.getElementById("SEL_MARKTO"),markTo);

	//Set Selected Columns
	var selcolval= new Array("0" <% for(int i=0;i<selcolval.length;i++){%>,"<%= selcolval[i] %>"<%} %>);
	mulitselect(document.getElementById("tabcols"),document.getElementById("sel_tabcols"),selcolval);

	//Set Order By
//	var seltabOrderBy = new Array("0"  for(int i=0;i<seltabOrderBy.length;i++){%>," = seltabOrderBy[i] "} %>);
	
	var INDATECONDITION = "<%=INDATECONDITION%>";
	if(INDATECONDITION == "BETWEEN")
	{
		window.document.getElementById("INDATECONDITION").selectedIndex=1;
		window.document.getElementById("INDATEFROM").style.display = "inline";
		window.document.getElementById("INDATETO").style.display = "inline";
	}
	else if(INDATECONDITION == "=")
	{
		window.document.getElementById("INDATECONDITION").selectedIndex=0;
		window.document.getElementById("INDATEFROM").style.display = "inline";
		window.document.getElementById("INDATETO").style.display = "none";
	}
	
	var LETDATECONDITION = "<%=LETDATECONDITION%>";
	if(LETDATECONDITION == "BETWEEN")
	{
		window.document.getElementById("LETDATECONDITION").selectedIndex=1;
		window.document.getElementById("LETDATEFROM").style.display = "inline";
		window.document.getElementById("LETDATETO").style.display = "inline";
	}
	else if(LETDATECONDITION == "=")
	{
		window.document.getElementById("LETDATECONDITION").selectedIndex=0;
		window.document.getElementById("LETDATEFROM").style.display = "inline";
		window.document.getElementById("LETDATETO").style.display = "none";
	}

	var ACKNDATECONDITION = "<%=ACKNDATECONDITION%>";
	if(ACKNDATECONDITION == "BETWEEN")
	{
		window.document.getElementById("ACKNDATECONDITION").selectedIndex=1;
		window.document.getElementById("ACKNDATEFROM").style.display = "inline";
		window.document.getElementById("ACKNDATETO").style.display = "inline";
	}
	else if(ACKNDATECONDITION == "=")
	{
		window.document.getElementById("ACKNDATECONDITION").selectedIndex=0;
		window.document.getElementById("ACKNDATEFROM").style.display = "inline";
		window.document.getElementById("ACKNDATETO").style.display = "none";
	}

	var MARKINGDATECONDITION = "<%=MARKINGDATECONDITION%>";
	if(MARKINGDATECONDITION == "BETWEEN")
	{
		window.document.getElementById("MARKINGDATECONDITION").selectedIndex=1;
		window.document.getElementById("MARKINGDATEFROM").style.display = "inline";
		window.document.getElementById("MARKINGDATETO").style.display = "inline";
	}
	else if(MARKINGDATECONDITION == "=")
	{
		window.document.getElementById("MARKINGDATECONDITION").selectedIndex=0;
		window.document.getElementById("MARKINGDATEFROM").style.display = "inline";
		window.document.getElementById("MARKINGDATETO").style.display = "none";
	}

	var TARGETDATECONDITION = "<%=TARGETDATECONDITION%>";
	if(TARGETDATECONDITION == "BETWEEN")
	{
		window.document.getElementById("TARGETDATECONDITION").selectedIndex=1;
		window.document.getElementById("TARGETDATEFROM").style.display = "inline";
		window.document.getElementById("TARGETDATETO").style.display = "inline";
	}
	else if(TARGETDATECONDITION == "=")
	{
		window.document.getElementById("TARGETDATECONDITION").selectedIndex=0;
		window.document.getElementById("TARGETDATEFROM").style.display = "inline";
		window.document.getElementById("TARGETDATETO").style.display = "none";
	}

	var SIGNEDONCONDITION = "<%=SIGNEDONCONDITION%>";
	if(SIGNEDONCONDITION == "BETWEEN")
	{
		window.document.getElementById("SIGNEDONCONDITION").selectedIndex=1;
		window.document.getElementById("SIGNEDONFROM").style.display = "inline";
		window.document.getElementById("SIGNEDONTO").style.display = "inline";
	}
	else if(SIGNEDONCONDITION == "=")
	{
		window.document.getElementById("SIGNEDONCONDITION").selectedIndex=0;
		window.document.getElementById("SIGNEDONFROM").style.display = "inline";
		window.document.getElementById("SIGNEDONTO").style.display = "none";
	}
		
	//Set Out of Turn
	//Set Sanc authority	
	//Set sancyear
	//Set cost
	//Set Column Alias
	var selcolparam = "<%=selcolparam%>";
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
	}*/

}
function renameColumn (obj)
{
	var newname = prompt("Enter New Name for :"+ obj.options[obj.selectedIndex].value,obj.options[obj.selectedIndex].text);
	if(newname)
	{
		if(newname.length<=30)
			{ obj.options[obj.selectedIndex].text=newname;
			//addRemoveToOrderBy(this);
			}
		else
			alert("Max characters allowed is 30 but found "+newname.length);
		
	}
}

function showHideRef()
{
	if(window.document.getElementById("REFCONDITION").value == "BETWEEN")
	{
		window.document.getElementById("REFNOFROM").style.display = "inline";
		window.document.getElementById("REFNOTO").style.display = "inline";
	}
	else if(window.document.getElementById("REFCONDITION").value == "=")
	{
		window.document.getElementById("REFNOFROM").style.display = "inline";
		window.document.getElementById("REFNOTO").style.display = "none";
	}
}

function showHideIndate()
{
	if(window.document.getElementById("INDATECONDITION").value == "BETWEEN")
	{
		window.document.getElementById("INDATEFROM").style.display = "inline";
		window.document.getElementById("INDATETO").style.display = "inline";
	}
	else if(window.document.getElementById("INDATECONDITION").value == "=")
	{
		window.document.getElementById("INDATEFROM").style.display = "inline";
		window.document.getElementById("INDATETO").style.display = "none";
	}
}
function showHideSOn()
{
	if(window.document.getElementById("SIGNEDONCONDITION").value == "BETWEEN")
	{
		window.document.getElementById("SIGNEDONFROM").style.display = "inline";
		window.document.getElementById("SIGNEDONTO").style.display = "inline";
	}
	else if(window.document.getElementById("SIGNEDONCONDITION").value == "=")
	{
		window.document.getElementById("SIGNEDONFROM").style.display = "inline";
		window.document.getElementById("SIGNEDONTO").style.display = "none";
	}
}
function showHideLDate()
{
	if(window.document.getElementById("LETDATECONDITION").value == "BETWEEN")
	{
		window.document.getElementById("LETDATEFROM").style.display = "inline";
		window.document.getElementById("LETDATETO").style.display = "inline";
	}
	else if(window.document.getElementById("LETDATECONDITION").value == "=")
	{
		window.document.getElementById("LETDATEFROM").style.display = "inline";
		window.document.getElementById("LETDATETO").style.display = "none";
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
function showHideMDate()
{
	if(window.document.getElementById("MARKINGDATECONDITION").value == "BETWEEN")
	{
		window.document.getElementById("MARKINGDATEFROM").style.display = "inline";
		window.document.getElementById("MARKINGDATETO").style.display = "inline";
	}
	else if(window.document.getElementById("MARKINGDATECONDITION").value == "=")
	{
		window.document.getElementById("MARKINGDATEFROM").style.display = "inline";
		window.document.getElementById("MARKINGDATETO").style.display = "none";
	}
}
function showHideTargetDate()
{
	if(window.document.getElementById("TARGETDATECONDITION").value == "BETWEEN")
	{
		window.document.getElementById("TARGETDATEFROM").style.display = "inline";
		window.document.getElementById("TARGETDATETO").style.display = "inline";
	}
	else if(window.document.getElementById("TARGETDATECONDITION").value == "=")
	{
		window.document.getElementById("TARGETDATEFROM").style.display = "inline";
		window.document.getElementById("TARGETDATETO").style.display = "none";
	}
}
function showHidePRDate()
{
	if(window.document.getElementById("PLCRLYCONDITION").value == "BETWEEN")
	{
		window.document.getElementById("PLCRLYFROM").style.display = "inline";
		window.document.getElementById("PLCRLYTO").style.display = "inline";
	}
	else if(window.document.getElementById("PLCRLYCONDITION").value == "=")
	{
		window.document.getElementById("PLCRLYFROM").style.display = "inline";
		window.document.getElementById("PLCRLYTO").style.display = "none";
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

function addRemoveToOrderBy(obj)
{
	alert("hi");
	var selcolsobj = document.getElementById("sel_tabcols");
//	var orderbyObj = document.getElementById("sel_tabOrderBy");
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

function submitPage(){

	if(validateform(false)){
		var selcolparam="";
		
		for(var i=0;i<document.getElementById("sel_tabcols").length;i++)
		{
			selcolparam += "~"+document.getElementById("sel_tabcols").options[i].value+"^"+document.getElementById("sel_tabcols").options[i].text;
		}
		//alert("selcolparam generating : " + selcolparam);
		document.getElementById("selcolparam").value=selcolparam;
//		fnSelectAll("SEL_ROLEID");
		fnSelectAll("SEL_REFCLASS");
		fnSelectAll("SEL_REFCATEGORY");
		fnSelectAll("SEL_SUBJECTTYPE");
		fnSelectAll("SEL_MARKTO");
		fnSelectAll("sel_tabcols");
//		fnSelectAll("sel_tabOrderBy");
//		fnSelectAll("SEL_REFCATEGORY");
//		disabled(document.getElementById("ROLEID"),true);
		disabled(document.getElementById("REFCLASS"),true);
		disabled(document.getElementById("REFCATEGORY"),true);
		disabled(document.getElementById("SUBJECTTYPE"),true);
		disabled(document.getElementById("MARKTO"),true);
		disabled(document.getElementById("tabcols"),true);
		disabled(document.getElementById("btnsave"),true);
		disabled(document.getElementById("btncancel"),true);
		disabled(document.getElementById("btnpreview"),true);
		document.forms[0].submit();
	}
}

function previewReport()
{
	if(validateform(true)){
		var selcolparam="";
		
		for(var i=0;i<document.getElementById("sel_tabcols").length;i++)
		{
			selcolparam += "~"+document.getElementById("sel_tabcols").options[i].value+"^"+document.getElementById("sel_tabcols").options[i].text;
		}
		document.getElementById("selcolparam").value=selcolparam;
//		fnSelectAll("SEL_ROLEID");
		fnSelectAll("SEL_REFCLASS");
		fnSelectAll("SEL_REFCATEGORY");
		fnSelectAll("SEL_SUBJECTTYPE");
		fnSelectAll("SEL_MARKTO");
		fnSelectAll("sel_tabcols");
//		fnSelectAll("sel_tabOrderBy");
			var url ="GenerateReportController";
			//document.getElementById("divLoading").style.display="block";
				
			//showDiv();
			
			document.forms[0].action = url;
			document.forms[0].target="popreport";
			document.forms[0].submit();
			window.setTimeout("showDiv();",1000);	
			document.forms[0].target="_self";
			document.forms[0].action = "CustomReportController";
	}
}


function  validateform(x)
{
	if( (x || chkblank(document.getElementById("repheader"))) && (document.getElementById("sel_tabcols").options.length>0))
		return true;
	else
		alert("Please select atleast one column...!!!");
	return false;
}
</script>
<script>  
 function funcKeyPress(obj,objvalue){
 //if(objvalue.length==0){
    if(window.event.keyCode==113){
        obj.value=obj.value+'<%=serverDate%>'; 
 	}
 // } 
 }
 
  function functionKey(obj,objvalue){ 
	
	if(!obj.readOnly&&((obj.type=='text')||(obj.type=='textarea'))){
	
	if(window.event.keyCode==113){
	var maxlength=parseInt(obj.maxLength);
	
	if(maxlength >0 ){
	if(maxlength >= (parseInt(objvalue.length)+parseInt('<%=serverDate.length()%>'))){
	 obj.value=obj.value+"<%=serverDate%>";
	 }}
	 else {
	 	obj.value=obj.value+"<%=serverDate%>";
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

</script>
</head>

<body>
<form name="customrep" action="CustomReportController" method="post">
<%
  	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
	String roleIDSQL="SELECT ROLEID, ROLENAME FROM MSTROLE ORDER BY ROLENAME"; 
	ArrayList<CommonBean> roleIdList = (new CommonDAO()).getSQLResult(roleIDSQL, 2);

	String queryRefClass = "SELECT DISTINCT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);

	String queryRefCCategory = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' ORDER BY PRIORITYNO"; 
	ArrayList<CommonBean> refCategoryList = (new CommonDAO()).getSQLResult(queryRefCCategory, 3);

//	String refCategorySQL="SELECT DISTINCT REFCATEGORY FROM TRNREFERENCE ORDER BY REFCATEGORY"; 
//	ArrayList<CommonBean> refCategoryList = (new CommonDAO()).getSQLResult(refCategorySQL, 1);

	String querySubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2";  
	ArrayList<CommonBean> subjectTypeList = (new CommonDAO()).getSQLResult(querySubject, 2);
	
	String refCatSQL = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00'"; 
	ArrayList<CommonBean> refCatList = (new CommonDAO()).getSQLResult(refCatSQL, 3);

	String STATEListSQL = "SELECT STATECODE, STATENAME FROM MSTSTATE ORDER BY STATENAME"; 
	ArrayList<CommonBean> STATEList = (new CommonDAO()).getSQLResult(STATEListSQL, 2);

	String acknByListSQL="SELECT NAME, TENUREID FROM MSTTENURE WHERE ISACTIVE = 'Y' ORDER BY NAME"; 
	ArrayList<CommonBean> acknByList = (new CommonDAO()).getSQLResult(acknByListSQL, 2);

	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						    " FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	

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
											<tr>
												<th>Custom Report Parameter <%= repheader.length()>0?" { "+repheader+" }":"" %></th>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td></td>
									</tr>
									<tr>
										<td>
										<table width="97%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td height="8px"></td>
											</tr>
											<tr>
												<td style="padding-left: 7px;"><table bordercolor="#cdcdcd" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;">
                  <tr class="firstbg">
                  		<th colspan="8"> Filter Criteria</th>
                  </tr>
													<tr class="trodd">
														<td colspan="8"></td>
													</tr>
													<tr class="treven">
														<td colspan="8">
														<table width="100%" border="0">
															<tbody>
																<tr class="trodd">
																	<td width="124" align="left"><input type="hidden" name="dummyfield" value="" /><input type="hidden" name="repType" value="1" /> Budget</td>
																	<td nowrap="nowrap" align="left" width="351">
																	<select name="BUDGETYEAR" id="BUDGETYEAR">
																		<option value="" selected="selected">-SELECT-</option>
																		<option value="Y" <%= BUDGETYEAR.equalsIgnoreCase("Y")?"selected":""%>>Budget</option>
																		<option value="N" <%= BUDGETYEAR.equalsIgnoreCase("N")?"selected":""%>>Non-Budget</option>
																	</select></td>
																	<td align="left" width="130"></td>
																	<td colspan="3" align="left" width="327"></td>
																</tr>
																<tr class="treven">
																	<td width="124" align="left">Incoming Date</td>
																	<td nowrap="nowrap" align="left" width="351"><select name="INDATECONDITION" id="INDATECONDITION" onchange="showHideIndate(this);">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="INDATEFROM" id="INDATEFROM" size="10" value="<%=sessionBean.getTENURESTARTDATE() %>"/>
																	&nbsp;<input type="text" name="INDATETO" id="INDATETO" size="10" value="<%= serverDate %>"
																		style="display: none;"/></td>
																	<td align="left" width="130">Ref. Date</td>
																	<td colspan="3" align="left" width="327"><select
																		name="LETDATECONDITION" id="LETDATECONDITION"
																		onchange="showHideLDate(this);">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="LETDATEFROM" id="LETDATEFROM" size="10"
																		value="<%= LETDATEFROM%>" />
																	&nbsp;<input type="text" name="LETDATETO" id="LETDATETO" size="10"
																		style="display: none;" value="<%= LETDATETO%>" /></td>
																</tr>
																<tr class="trodd">
																	<td width="124" align="left">Received From</td>
																	<td nowrap="nowrap" align="left" width="351"><input style=" background-image: url('images/search.png'); background-position: right;background-repeat: no-repeat;"
																		type="text" id="RECEIVEDFROM" name="RECEIVEDFROM" size="34" value="<%= RECEIVEDFROM %>"   /></td>
																	<td align="left" width="130">Status</td>
																	<td colspan="3" align="left" width="327"><input value="<%= STATUS%>" style=" background-image: url('images/search.png'); background-position: right;background-repeat: no-repeat;"
																		type="text" name="STATUS" id="STATUS" size="34" /></td>
																</tr>
																<tr class="treven">
																	<td width="124" align="left">State</td>
																	<td nowrap="nowrap" align="left" width="351"><input style=" background-image: url('images/search.png'); background-position: right;background-repeat: no-repeat;"
																		type="text" name="STATE" id="STATE" size="34" value="<%= STATE%>"/></td>
																	<td align="left" width="130">Subject</td>
																	<td colspan="3" align="left" width="327"><input
																		type="text" name="SUBJECT" id="SUBJECT" size="34" value="<%= SUBJECT%>" /></td>
																</tr>
																<tr class="trodd">
																	<td width="124" align="left">Ack. By</td>
																	<td align="left" width="351"><input type="text"
																		name="ACKNBY" id="ACKNBY" size="34" value="<%= ACKNBY%>" /></td>
																	<td align="left" width="130">Ack. Date</td>
																	<td colspan="3" align="left" width="327"><select
																		name="ACKNDATECONDITION" id="ACKNDATECONDITION"
																		onchange="showHideAckn(this);">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="ACKNDATEFROM" id="ACKNDATEFROM" size="10"
																		value="<%= ACKNDATEFROM%>" />
																	&nbsp;<input type="text" name="ACKNDATETO" id="ACKNDATETO" size="10"
																		style="display: none;" value="<%= ACKNDATETO%>" /></td>
																</tr>
																<tr class="treven">
																	<td width="124" align="left">Forwared on</td>
																	<td align="left" width="351"><select
																		name="MARKINGDATECONDITION" id="MARKINGDATECONDITION"
																		onchange="showHideMDate(this);">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="MARKINGDATEFROM" id="MARKINGDATEFROM"
																		size="10" value="<%= MARKINGDATEFROM%>" />
																	&nbsp;<input type="text" name="MARKINGDATETO" id="MARKINGDATETO" size="10"
																		style="display: none;" value="<%= MARKINGDATETO%>" /></td>
																	<td align="left" width="130">Target Date</td>
																	<td colspan="3" align="left" width="327"><select
																		name="TARGETDATECONDITION" id="TARGETDATECONDITION"
																		onchange="showHideTargetDate(this);">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="TARGETDATEFROM"
																		size="10" id="TARGETDATEFROM" value="<%= TARGETDATEFROM%>" />
																	&nbsp;<input type="text" name="TARGETDATETO" id="TARGETDATETO" size="10"
																		style="display: none;"  value="<%= TARGETDATETO%>" /></td>
																</tr>
																<tr class="trodd">
																	<td width="124" align="left">Remarks</td>
																	<td align="left" width="351"><input type="text"
																		name="REMARKS" id="REMARKS" size="34" value="<%= REMARKS%>" /></td>
																	<td align="left" width="130">Remarks Signed By</td>
																	<td colspan="3" align="left" width="327">
																<select	name="SIGNEDBY" id="SIGNEDBY">
																		<option value="">- SELECT -</option>
																	<%
																		for(int i=0;i<acknByList.size();i++){
																		CommonBean beanCommon=(CommonBean) acknByList.get(i);
																	%>
																		<option value="<%= beanCommon.getField2()%>" <%=beanCommon.getField2().equalsIgnoreCase(SIGNEDBY)?"selected":""%>><%= beanCommon.getField1()%></option>
																	<%}%>
																</select></td>
																</tr>
																<tr class="treven">
																	<td width="124" align="left">Remarks Signed On</td>
																	<td align="left" width="351"><select
																		name="SIGNEDONCONDITION" id="SIGNEDONCONDITION"
																		onchange="showHideSOn(this);">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="SIGNEDONFROM" id="SIGNEDONFROM" size="10" value="<%= SIGNEDONFROM%>" />
																	&nbsp;<input type="text" name="SIGNEDONTO" id="SIGNEDONTO" size="10"
																		style="display: none;" value="<%= SIGNEDONTO%>" /></td>
																	<td align="left" width="130">Tag (1,2,3,4)</td>
																	<td colspan="3" align="left" width="327"><input
																		type="text" name="TAG" id="TAG" size="34"
																		value="<%= TAG%>" /></td>
																</tr>
																<tr class="treven">
																	<td width="124" align="left">Place</td>
																	<td align="left" width="351"><input type="text"
																		name="PLACE" id="PLACE" size="34" value="<%= PLACE%>" /></td>
																	<td align="left" width="130">Railway</td>
																	<td align="left" colspan="3" width="327"><input
																		type="text" name="RLY" id="RLY" size="34"
																		value="<%= RLY%>" /></td>
																</tr>
																<tr class="treven">
																	<td width="124" align="left">Place-Rly-Date</td>
																	<td align="left" width="351"><select
																		name="PLCRLYCONDITION"
																		id="PLCRLYCONDITION"
																		onchange="showHidePRDate(this);">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text"
																		name="PLCRLYFROM" id="PLCRLYFROM"
																		size="10" value="<%= PLCRLYFROM%>" />
																	&nbsp;<input type="text" name="PLCRLYTO"
																		id="PLCRLYTO" size="10" style="display: none;"
																		value="<%= PLCRLYTO%>" /></td>
																	<td align="left" width="130"></td>
																	<td align="left" colspan="3" width="327"></td>
																</tr>
															</tbody>
														</table>
														</td>
													</tr>
													<tr class="firstbg">
                   		<th></th><th>Available</th><th></th><th>Selected <sup style="color: red;">*</sup></th><th></th><th>Available</th><th></th><th>Selected </th>
                   		
                   	</tr>
                  	<tr class="trodd">
 <!--                   <td>Role ID</td>
                  		<td> <input type="hidden" name="dummyfield" value="" /><input type="hidden" name="repType" value="1" />
                  		<select ondblclick="fnMoveItems('ROLEID', 'SEL_ROLEID');" name="ROLEID" id="ROLEID" multiple="multiple" style="width: 180px; height: 100px">
						<%
							for(int i=0;i<roleIdList.size();i++){
							CommonBean beanCommon=(CommonBean) roleIdList.get(i);
						%>			
							<option value="<%= beanCommon.getField1()%>"><%= beanCommon.getField2()%></option>
						<%}%>
						</select> 
                  		</td>
                  		<td valign="middle" align="center"> 
                  			<img align="middle" src="images/next.gif" onclick="fnMoveItems( 'ROLEID','SEL_ROLEID');" /> <br />
                  			<img align="middle" src="images/prev.gif" onclick="fnMoveItems( 'SEL_ROLEID','ROLEID');" /> 
                  		</td>
                  		<td>
                  		<select  ondblclick="fnMoveItems('SEL_ROLEID', 'ROLEID');" name="SEL_ROLEID" id="SEL_ROLEID" multiple="multiple" style="width: 180px; height: 100px">
										
										</select>
                  		</td>
 -->
                    <td>Ref Class</td>
                  		<td> 
                  		<select
															ondblclick="fnMoveItems('REFCLASS', 'SEL_REFCLASS');"
															name="REFCLASS" id="REFCLASS" multiple="multiple"
															style="width: 180px; height: 100px">
															<%
								for(int i=0;i<refClassList.size();i++){
								CommonBean beanCommon=(CommonBean) refClassList.get(i);
							%>
															<option value="<%= beanCommon.getField1()%>"><%= beanCommon.getField1()%></option>
															<%}%>
														</select></td>
                  		<td valign="middle" align="center"> <img
															align="middle" src="images/next.gif"
															onclick="fnMoveItems( 'REFCLASS','SEL_REFCLASS')" /> <br />
														<img align="middle" src="images/prev.gif"
															onclick="fnMoveItems( 'SEL_REFCLASS','REFCLASS')" /><br /> 
                  		</td>
                  		<td>
                  		<select
															ondblclick="fnMoveItems('SEL_REFCLASS', 'REFCLASS');"
															name="SEL_REFCLASS" id="SEL_REFCLASS" multiple="multiple"
															style="width: 180px; height: 100px">

														</select></td>
                  		<td>
                  			Category</td>
                  		<td>
                  		<select multiple="multiple"	style="width: 180px; height: 100px" id="REFCATEGORY" name="REFCATEGORY"	ondblclick="fnMoveItems('REFCATEGORY','SEL_REFCATEGORY')">
						<%
							for(int i=0;i<refCategoryList.size();i++){
							CommonBean beanCommon=(CommonBean) refCategoryList.get(i);
						%>
							<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
						<%}%>
						</select>
						</td>
                  		<td valign="middle" align="center"> 
                  			 
                  		<img align="middle" src="images/next.gif"
															onclick="fnMoveItems( 'REFCATEGORY','SEL_REFCATEGORY');" /> <br />
														<img align="middle" src="images/prev.gif"
															onclick="fnMoveItems( 'SEL_REFCATEGORY','REFCATEGORY');" /></td>
                  		<td>
                  		<select
															ondblclick="fnMoveItems('SEL_REFCATEGORY','REFCATEGORY')"
															name="SEL_REFCATEGORY" id="SEL_REFCATEGORY"
															multiple="multiple" style="width: 180px; height: 100px">

														</select></td>
                  	</tr>
                  	<tr class="firstbg">
                   		<th></th><th>Available</th><th></th><th>Selected <sup style="color: red;">*</sup></th><th></th><th>Available</th><th></th><th>Selected </th>
                   	</tr>                
                  	<tr class="trodd">
                  		<td>Sub.</td> <td>
										
										
										<select multiple="multiple"
															style="width: 180px; height: 100px" id="SUBJECTTYPE"
															name="SUBJECTTYPE"
															ondblclick="fnMoveItems('SUBJECTTYPE','SEL_SUBJECTTYPE')">
															<%
					for(int i=0;i<subjectTypeList.size();i++){
					CommonBean beanCommon=(CommonBean) subjectTypeList.get(i);
				%>
															<option value="<%= beanCommon.getField1()%>"><%= beanCommon.getField2()%></option>
															<%}%>
														</select></td>
										<td valign="middle" align="center"> 
						           			 
						           		<img align="middle" src="images/next.gif"
															onclick="fnMoveItems( 'SUBJECTTYPE','SEL_SUBJECTTYPE');" /> <br />
														<img align="middle" src="images/prev.gif"
															onclick="fnMoveItems( 'SEL_SUBJECTTYPE','SUBJECTTYPE');" /></td>
						           		<td>
				                  		<select
															ondblclick="fnMoveItems('SEL_SUBJECTTYPE','SUBJECTTYPE')"
															name="SEL_SUBJECTTYPE" id="SEL_SUBJECTTYPE"
															multiple="multiple" style="width: 180px; height: 100px">

														</select></td>
												<td> Forward To<br />
														</td> <td>
							<select multiple="multiple" style="width: 180px; height: 100px"
															id="MARKTO" name="MARKTO"
															ondblclick="fnMoveItems('MARKTO','SEL_MARKTO')">
															<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
															<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
															<%
						}%>
														</select></td>
							<td valign="middle" align="center"> 
		           			 
			           		<img align="middle" src="images/next.gif"
															onclick="fnMoveItems( 'MARKTO','SEL_MARKTO');" /> <br />
														<img align="middle" src="images/prev.gif"
															onclick="fnMoveItems( 'SEL_MARKTO','MARKTO');" /></td>
			           		<td>
				                  		<select
															ondblclick="fnMoveItems('SEL_MARKTO','MARKTO')"
															name="SEL_MARKTO" id="SEL_MARKTO" multiple="multiple"
															style="width: 180px; height: 100px">

														</select></td>
											</tr>
													<tr class="trodd">
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
                 	<tr class="trodd">
                 						<td>
                 							Columns
                 						</td>
                 						<td>
					                  		<select ondblclick="fnMoveItems('tabcols','sel_tabcols');" name="tabcols" id="tabcols" multiple="multiple" style="width: 180px; height: 100px" >
												<option value="TRNREFERENCE.REFNO">Reference No</option>                          
												<option value="TO_CHAR(TRNREFERENCE.INCOMINGDATE,'DD-MM-YYYY')">Incoming Date</option>                   
												<option value="TO_CHAR(TRNREFERENCE.LETTERDATE,'DD-MM-YYYY')">Letter Date</option>                     
												<option value="TRNREFERENCE.REFERENCENAME">Received From</option>                  
												<option value="TRNREFERENCE.VIPSTATUS">Status</option>                      
												<option value="TRNREFERENCE.STATECODE">State</option>                      
												<option value="TRNREFERENCE.SUBJECTCODE">Sub.</option>                    
												<option value="TRNREFERENCE.SUBJECT">Subject</option>                        
										<% String frwrdTo = "GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = TRNREFERENCE.REFID AND MARKINGSEQUENCE = 1)) "; %>
												<option value="<%= frwrdTo %>">Forward To</option>                      
												<option value="TO_CHAR(TRNMARKING.MARKINGDATE,'DD-MM-YYYY')">Forward Date</option>                    
												<option value="TO_CHAR(TRNMARKING.TARGETDATE,'DD-MM-YYYY')">Target Date</option>                     
												<option value="TRNREFERENCE.REMARKS">Remarks</option>                        
										<% String sgndBy = "(SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.SIGNEDBY) "; %>
												<option value="<%= sgndBy %>">Signed By</option>                       
												<option value="TO_CHAR(TRNREFERENCE.SIGNEDON,'DD-MM-YYYY')">Signed On</option>                       
										<% String acknBy = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.ACKBY) "; %>
												<option value="<%= acknBy %>">Acknowledge By</option>                          
												<option value="TO_CHAR(TRNREFERENCE.ACKDATE,'DD-MM-YYYY')">Acknowledge Date</option>                        
										<% String markTo = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.IMARKINGTO) "; %>
												<option value="<%= markTo %>">Mark To</option>
												<option value="TRNREFERENCE.REGISTRATIONNO">FM Regn.</option>
												<option value="TO_CHAR(TRNREFERENCE.REGISTRATIONDATE,'DD-MM-YYYY')">FM Date</option>
												<option value="TRNREFERENCE.FILENO">File No.</option>
										<% String fStatus1 = "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS1) "; %>
												<option value="<%= fStatus1 %>">File Status 1</option>
										<% String fStatus2 = "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS2) "; %>
												<option value="<%= fStatus2 %>">File Status 2</option>
										<% String rtrnTo = "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.DMARKINGTO) "; %>
												<option value="<%= rtrnTo %>">Return To</option>
												<option value="TO_CHAR(TRNREFERENCE.DMARKINGDATE,'DD-MM-YYYY')">Return On</option>
												<option value="TRNREFERENCE.REPLYTYPE">Reply</option>
												<option value="TRNREFERENCE.ISBUDGET">Is Budget</option>
					                  		</select>
				                  		</td>
                 						<td valign="middle" align="center"> 
						           			<img align="middle" src="images/next.gif" onclick="fnMoveItems( 'tabcols','sel_tabcols');" /> <br />
						           			<img align="middle" src="images/prev.gif" onclick="fnMoveItems( 'sel_tabcols','tabcols');" /> 
						           		</td>
						           		<td>
					                  		<select ondblclick="renameColumn(this);" name="sel_tabcols" id="sel_tabcols" multiple="multiple" style="width: 180px; height: 100px">
					                  		
					                  		</select><input type="hidden" value="<%=selcolparam%>" name="selcolparam" id="selcolparam" />
				                  		</td>
				                  		<td>
				                  		<input class="butts" value="   &uarr;   " type="button" name="btnMoveRightAll0"	onclick="fnMoveUpDown(this,'sel_tabcols','U')" /><br />
										<input class="butts" value="   &darr;   " type="button"	name="btnMoveRightAll1"	onclick="fnMoveUpDown(this,'sel_tabcols','D')" />
				                  		</td>
                 	</tr>
                 </table> 
                 </td>
                 <td>
<!--
                  <table border="1" bordercolor="#cdcdcd" cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin-top:7px;">
                 	<tr>
																<th class="firstbg" colspan="3"">Order By</th>
															</tr>
                   	<tr class="firstbg">
                   		<th></th><th>Selected</th>
																<th></th>
                   	</tr>
                 	<tr class="trodd">
                 		<td width="50">&nbsp;</td>
						           		<td>
					                  		<select ondblclick="changeOrder(this);"  name="sel_tabOrderBy" id="sel_tabOrderBy" multiple="multiple" style="width: 180px; height: 100px" >
					                  		
					                  		</select>
				                  		</td>
										<td>
				                  		<input class="butts" value="   &uarr;   " type="button" name="btnMoveRightAll0"	onclick="fnMoveUpDown(this,'sel_tabOrderBy','U')" /><br />
										<input class="butts" value="   &darr;   " type="button"	name="btnMoveRightAll1"	onclick="fnMoveUpDown(this,'sel_tabOrderBy','D')" />
				                  		</td>
                 	</tr>
                 </table> 
-->
                 </td></tr></table>
                 <br />
                  <table border="1" bordercolor="#7e7d7d" cellspacing="0" cellpadding="0" style="border-collapse:collapse; border: solid 1px #7e7d7d;">
                  	<tr class="tab1">
              			<th>   Report  Title</th><td> <input type="text" name="repheader" id="repheader" value="<%= repheader %>" size="100" /></td>
              		</tr>
              		<tr class="tab1" style="display: none;">
              			<td> Report Id</td><td><input type="text"  class="readonly" name="reportid" value="<%= reportid %>" />
              			
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
            		<td><input type="button" name="btncancel" id="btncancel" class="butts" value=" Cancel " onclick="window.location.href='CustomReport.jsp'" /></td>
            		<td><input type="button" name="btnpreview" id="btnpreview" class="butts" value=" Preview " onclick="previewReport();" /></td>
            	</tr>
            </table>
            
            
           

<script type="text/javascript">
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
 </form>
</body>
</html>