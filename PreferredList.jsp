<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.Beans.MenuBean"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%><html>
<head>
<title>MenuAdministration</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/PreferredListDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
 <style type="text/css">
  
  .ui-autocomplete , .ui-menu-item{
  	width: 300px;
  	list-style: none;
  	text-align: left;
  	background-color:red; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; font-weight:bold; color:#101010; height:220px;}
  .ui-menu-item a{text-align: left;width: 100%;}
  
  /* div container containing the form  */
#searchContainer {
	margin:20px;
}
 
/* Style the search input field. */
.field {
	float:left;
	width:300px;
	height:27px;
	line-height:27px;
	text-indent:10px;
	font-family:arial, sans-serif;
	font-size:1em;
	color:#333;
	background: #fff;
	border:solid 1px #d9d9d9;
	border-top:solid 1px #c0c0c0;
	border-right:none;
}
 
/* Style the "X" text button next to the search input field */
.delete {
	float:left;
	width:16px;
	height:29px;
	line-height:27px;
	margin-right:15px;
	padding:0 10px 0 10px;
	font-family: "Lucida Sans", "Lucida Sans Unicode",sans-serif;
	font-size:22px;
	background: #fff;
	border:solid 1px #d9d9d9;
	border-top:solid 1px #c0c0c0;
	border-left:none;
}
/* Set default state of "X" and hide it */
.delete #x {
	color:#A1B9ED;
	cursor:pointer;
	display:none;
}
/* Set the hover state of "X" */
.delete #x:hover {
	color:#36c;
}
/* Syle the search button. Settings of line-height, font-size, text-indent used to hide submit value in IE */
.submit {
	cursor:pointer;
	width:50px;
	height: 21px;
	line-height:0;
	font-size:0;
	text-indent:-999px;
	color: transparent;
	background: url(images/icon_search12.png) no-repeat #4d90fe center;
	border: 1px solid #3079ED;
	-moz-border-radius: 2px;
	-webkit-border-radius: 2px;
}
/* Style the search button hover state */
.submit:hover {
	background: url(images/icon_search12.png) no-repeat center #357AE8;
	border: 1px solid #2F5BB7;
}
/* Clear floats */
.fclear {clear:both}
  
  </style>
</head>
<%
 String formAction=request.getParameter("formAction")!=null ?request.getParameter("formAction"):"";
 String groupid=request.getParameter("txtGroupId")!=null ?request.getParameter("txtGroupId"):"";
 if(formAction.equalsIgnoreCase("CLEAR")){
 groupid="";
 }
 
 String queryMarkedTo = " SELECT A.ROLEID, A.ROLENAME "+ 
 						" FROM MSTROLE A"+
 						" MINUS"+ 
 						" SELECT TO_NUMBER(B.PREFERREDID),(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+groupid+"' ORDER BY 2"; 
 						
 ArrayList<CommonBean> queryMarkedToList = (new CommonDAO()).getSQLResult(queryMarkedTo, 2);
 
 String selqueryMarkedTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						   " FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+groupid+"' ORDER BY 2"; 
 ArrayList<CommonBean> selqueryMarkedToList = (new CommonDAO()).getSQLResult(selqueryMarkedTo, 2);
 
 String querySignedBy = " SELECT A.ROLEID, A.ROLENAME "+ 
 						" FROM MSTROLE A"+
 						" MINUS"+ 
 						" SELECT TO_NUMBER(B.PREFERREDID),(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+groupid+"' ORDER BY 2"; 
 ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);
 
 String queryAckBy = " SELECT A.ROLEID, A.ROLENAME "+ 
			" FROM MSTROLE A"+
			" MINUS"+ 
			" SELECT TO_NUMBER(B.PREFERREDID),(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
			" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='7' AND ROLEID='"+groupid+"' ORDER BY 2"; 
ArrayList<CommonBean> queryAckByList = (new CommonDAO()).getSQLResult(queryAckBy, 2);
 
 String selquerySignedBy = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+groupid+"' ORDER BY 2";  
 ArrayList<CommonBean> selquerySignedByList = (new CommonDAO()).getSQLResult(selquerySignedBy, 2);
 
 String selqueryAckBy = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
			" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='7' AND ROLEID='"+groupid+"' ORDER BY 2";  
ArrayList<CommonBean> selqueryAckByList = (new CommonDAO()).getSQLResult(selqueryAckBy, 2);
 String queryinternalManking = " SELECT A.ROLEID, A.ROLENAME "+ 
 						" FROM MSTROLE A"+
 						" MINUS"+ 
 						" SELECT TO_NUMBER(B.PREFERREDID),(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='5' AND ROLEID='"+groupid+"' ORDER BY 2"; 
 ArrayList<CommonBean> queryinternalMankingList = (new CommonDAO()).getSQLResult(queryinternalManking, 2);
 
 String selqueryinternalManking = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='5' AND ROLEID='"+groupid+"' ORDER BY 2";  
 ArrayList<CommonBean> selqueryinternalMankingList = (new CommonDAO()).getSQLResult(selqueryinternalManking, 2);
 
 String queryBranch = " SELECT A.ROLEID, A.ROLENAME "+ 
 						" FROM MSTROLE A WHERE A.AUTHLEVEL=2"+
 						" MINUS"+ 
 						" SELECT TO_NUMBER(B.PREFERREDID),(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='6' AND ROLEID='"+groupid+"' ORDER BY 2"; 
 						
 						
 ArrayList<CommonBean> queryBranchList = (new CommonDAO()).getSQLResult(queryBranch, 2);
 
 String selqueryBranch = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='6' AND ROLEID='"+groupid+"' ORDER BY 2";  
 ArrayList<CommonBean> selqueryBranchList = (new CommonDAO()).getSQLResult(selqueryBranch, 2);
 
 String queryrefSubject = "SELECT SUBJECTCODE, SUBJECTNAME FROM MSTSUBJECT "+
 							" MINUS"+
 							" SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND ROLEID='"+groupid+"' ORDER BY 2"; 
 ArrayList<CommonBean> refsubjectList = (new CommonDAO()).getSQLResult(queryrefSubject, 2);
 
 String selqueryrefSubject =" SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND ROLEID='"+groupid+"' ORDER BY 2";  
 ArrayList<CommonBean> selrefsubjectList = (new CommonDAO()).getSQLResult(selqueryrefSubject, 2);
 
 String queryfmSubject = "SELECT SUBJECTCODE, SUBJECTNAME FROM MSTSUBJECT "+
 							" MINUS"+
 							" SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='4' AND ROLEID='"+groupid+"' ORDER BY 2";  
 ArrayList<CommonBean> fmsubjectList = (new CommonDAO()).getSQLResult(queryfmSubject, 2);
  
 String selqueryfmSubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='4' AND ROLEID='"+groupid+"' ORDER BY 2";
 ArrayList<CommonBean> selfmsubjectList = (new CommonDAO()).getSQLResult(selqueryfmSubject, 2);
 
 String querydept = "SELECT DISTINCT A.DEPTCODE,A.DEPTNAME FROM MSTDEPT A ORDER BY A.DEPTNAME	"; 
 ArrayList<CommonBean> deptList = (new CommonDAO()).getSQLResult(querydept, 2);
 
 String queryauth = "SELECT DISTINCT A.AUTHLEVEL,A.AUTHNAME FROM MSTAUTHLEVEL A ORDER BY A.AUTHLEVEL DESC"; 
 ArrayList<CommonBean> authList = (new CommonDAO()).getSQLResult(queryauth, 2);
  
  String queryrole = "SELECT DISTINCT A.ROLEID,A.ROLENAME FROM MSTROLE A,MSTTENURE B WHERE A.ROLEID=B.ROLEID AND B.ISACTIVE='Y' ORDER BY A.ROLENAME	"; 
 ArrayList<CommonBean> roleList = (new CommonDAO()).getSQLResult(queryrole, 2);
%> 
<script>

function submitForm(btnClick) {
document.all("formAction").value=btnClick;
disabled(window.document.getElementById("txtGroupId"),false);
if(chkblank(window.document.getElementById("txtGroupId"))){
if(btnClick=='GO'||btnClick=='CLEAR'){
document.menuadminstration.submit();
}
if(btnClick=='SAVE'){
		fnSelectAll("SEL_MARKINGTO");
		fnSelectAll("SEL_SIGNEDBY");
		fnSelectAll("SEL_ACKBY");
		fnSelectAll("SEL_REFSUBJECTCODE");
		//fnSelectAll("SEL_FILESUBJECTCODE");
		fnSelectAll("SEL_INTMARKING");
		fnSelectAll("SEL_BRANCH");
		document.menuadminstration.submit();
		
}
}
}

function funconload() {
if('<c:out value="<%=formAction%>"/>'=='GO'||'<c:out value="<%=formAction%>"/>'=='SAVE'){
disabled(window.document.getElementById("txtGroupId"),true);
window.document.getElementById("details").style.display='block';
window.document.getElementById("save").style.display='block';
}
}

function funcpopulateDeptList() {

var deptcode = window.document.getElementById("deptcode1").value;
var MARKINGTO = window.document.getElementById("MARKINGTO");

PreferredListDAO.populateDeptList(deptcode, function (data){
  			MARKINGTO.length = 0;
  		if(data.length>0)
  		{
  			for(var i=0;i < data.length; i++){
        		        	
		        	var newOption = new Option();
					newOption.text = data[i].ROLENAME;
					newOption.value = data[i].ROLEID;
					MARKINGTO.options[MARKINGTO.length] = newOption;
		   	}
  			
  		} else {
  			alert('No Data Found');
  			
  		}
  } 
  );

}


function funcpopulateAuthList() {

var deptcode = window.document.getElementById("deptcode1").value;
var authlevel = window.document.getElementById("authlevel1").value;
var MARKINGTO = window.document.getElementById("MARKINGTO");

PreferredListDAO.populateAuthList(deptcode,authlevel, function (data){
  			MARKINGTO.length = 0;
  		if(data.length>0)
  		{
  			for(var i=0;i < data.length; i++){
        		        	
		        	var newOption = new Option();
					newOption.text = data[i].ROLENAME;
					newOption.value = data[i].ROLEID;
					MARKINGTO.options[MARKINGTO.length] = newOption;
		   	}
  			
  		} else {
  			alert('No Data Found');
  			
  		}
  } 
  );

}

function funcpopulateDeptLists() {

var deptcode = window.document.getElementById("deptcode2").value;
var SIGNEDBY = window.document.getElementById("SIGNEDBY");

PreferredListDAO.populateDeptList(deptcode, function (data){
  			SIGNEDBY.length = 0;
  		if(data.length>0)
  		{
  			for(var i=0;i < data.length; i++){
        		        	
		        	var newOption = new Option();
					newOption.text = data[i].ROLENAME;
					newOption.value = data[i].ROLEID;
					SIGNEDBY.options[SIGNEDBY.length] = newOption;
		   	}
  			
  		} else {
  			alert('No Data Found');
  			
  		}
  } 
  );

}


function funcpopulateAuthLists() {

var deptcode = window.document.getElementById("deptcode2").value;
var authlevel = window.document.getElementById("authlevel2").value;
var SIGNEDBY = window.document.getElementById("SIGNEDBY");

PreferredListDAO.populateAuthList(deptcode,authlevel, function (data){
  			SIGNEDBY.length = 0;
  		if(data.length>0)
  		{
  			for(var i=0;i < data.length; i++){
        		        	
		        	var newOption = new Option();
					newOption.text = data[i].ROLENAME;
					newOption.value = data[i].ROLEID;
					SIGNEDBY.options[SIGNEDBY.length] = newOption;
		   	}
  			
  		} else {
  			alert('No Data Found');
  			
  		}
  } 
  );

}


function funcpopulateDeptListi() {

var deptcode = window.document.getElementById("deptcode3").value;
var INTMARKING = window.document.getElementById("INTMARKING");

PreferredListDAO.populateDeptList(deptcode, function (data){
  			INTMARKING.length = 0;
  		if(data.length>0)
  		{
  			for(var i=0;i < data.length; i++){
        		        	
		        	var newOption = new Option();
					newOption.text = data[i].ROLENAME;
					newOption.value = data[i].ROLEID;
					INTMARKING.options[INTMARKING.length] = newOption;
		   	}
  			
  		} else {
  			alert('No Data Found');
  			
  		}
  } 
  );

}


function funcpopulateAuthListi() {

var deptcode = window.document.getElementById("deptcode3").value;
var authlevel = window.document.getElementById("authlevel3").value;
var INTMARKING = window.document.getElementById("INTMARKING");

PreferredListDAO.populateAuthList(deptcode,authlevel, function (data){
  			INTMARKING.length = 0;
  		if(data.length>0)
  		{
  			for(var i=0;i < data.length; i++){
        		        	
		        	var newOption = new Option();
					newOption.text = data[i].ROLENAME;
					newOption.value = data[i].ROLEID;
					INTMARKING.options[INTMARKING.length] = newOption;
		   	}
  			
  		} else {
  			alert('No Data Found');
  			
  		}
  } 
  );

}

function funcpopulateDeptListb() {

var deptcode = window.document.getElementById("deptcode4").value;
var BRANCH = window.document.getElementById("BRANCH");

PreferredListDAO.populateDeptList(deptcode, function (data){
  			BRANCH.length = 0;
  		if(data.length>0)
  		{
  			for(var i=0;i < data.length; i++){
        		        	
		        	var newOption = new Option();
					newOption.text = data[i].ROLENAME;
					newOption.value = data[i].ROLEID;
					BRANCH.options[BRANCH.length] = newOption;
		   	}
  			
  		} else {
  			alert('No Data Found');
  			
  		}
  } 
  );

}

</script>




<body onload="funconload();">
<form action="/MRSECTT/PreferredListController" method="post" name="menuadminstration"><input type="hidden"
	name="formAction"><input type="hidden"
	name="menuid"><input type="hidden"
	name="flag">
<table  align="center" border="0" cellpadding="0" cellspacing="0">
	<tbody align="center">
		<tr>
			<td height="22" align="left" valign="top" colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<td align="left" valign="middle" colspan="4"><nobr><b>Role Name :</b></nobr>&nbsp;&nbsp;&nbsp;<select name="txtGroupId" id="txtGroupId"
				style="height:20px">
				<option value="">-Select-</option>
				<%
							for(int i=0;i<roleList.size();i++){
							CommonBean beanCommon = (CommonBean) roleList.get(i);
							%>
				<option value="<c:out value='<%=beanCommon.getField1()%>'/>"
					<%=beanCommon.getField1().equalsIgnoreCase(groupid)?"selected":""%>><c:out value='<%=beanCommon.getField2()%>'/></option>

				<%
						}%>
			</select>&nbsp;&nbsp;<input type="button" name="btnGo"  value="   Go   "
				onclick="submitForm('GO');" <%=groupid.length()==0?"":"disabled" %> class="submit">
			</td>
		</tr>
		</tbody>
		</table>
	<table  align="center" border="0" cellpadding="0" cellspacing="0">
		<tbody align="center">
		<tr id="details" style="display: none">
			<td colspan="2" align="left" valign="top" >
			
			<table id="data" align="center">
				<tbody>
					<tr class="treven">
						<td style="text-align: center" align="right"><b>Master
						List</b></td>
						<td></td>
						<td style="text-align: center" align="left"><b>User </b>List</td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3"><u>Forward
						To :</u></td>
						
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3"></td>
						
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3">Dept.
													: <select name="deptCode1" id="deptCode1"  onchange="funcpopulateDeptList();">
														<option value="" selected>All</option>
														<%
															for(int k=0;k<deptList.size();k++){
															CommonBean beanCommon = (CommonBean) deptList.get(k);
																%>
																	<option  value="<c:out value='<%=beanCommon.getField1()%>'/>" ><c:out value='<%=beanCommon.getField2()%>'/></option>
																<%
															}
														%>
														
													</select>&nbsp;&nbsp;Authlevel
													: <select name="authlevel1" id="authlevel1"  onchange="funcpopulateAuthList();">
														<option value="" selected>All</option>
														<%
															for(int k=0;k<authList.size();k++){
															CommonBean beanCommon = (CommonBean) authList.get(k);
																%>
																	<option  value="<c:out value='<%=beanCommon.getField1()%>'/>" ><c:out value='<%=beanCommon.getField2()%>'/></option>
																<%
															}
														%>
														
													</select></td>
						
					</tr>
					<tr>
						<td align="right"><select
							ondblclick="fnMoveItems('MARKINGTO', 'SEL_MARKINGTO');"
							name="MARKINGTO" id="MARKINGTO"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<queryMarkedToList.size();k++){
							CommonBean beanCommon = (CommonBean) queryMarkedToList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
						<td valign="middle" align="center"><img align="middle"	src="images/next.gif"
							onclick="fnMoveAll( 'MARKINGTO','SEL_MARKINGTO')" /><br />
						<img align="middle" src="images/next_single.gif"
							onclick="fnMoveItems( 'MARKINGTO','SEL_MARKINGTO')" /><br />
						<img align="middle" src="images/prev_single.gif"
							onclick="fnMoveItems( 'SEL_MARKINGTO','MARKINGTO')" /><br />
						<img align="middle" src="images/prev.gif"
							onclick="fnMoveAll( 'SEL_MARKINGTO','MARKINGTO')" />
						</td>
						<td><select
							ondblclick="fnMoveItems('SEL_MARKINGTO', 'MARKINGTO');"
							name="SEL_MARKINGTO" id="SEL_MARKINGTO"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<selqueryMarkedToList.size();k++){
							CommonBean beanCommon = (CommonBean) selqueryMarkedToList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>

						</select></td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3"><u>Signed
						By </u>:</td>
						
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3"></td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3">Dept.
													: <select name="deptCode2" id="deptCode2"   onchange="funcpopulateDeptLists();">
														<option value="" selected>All</option>
														<%
															for(int k=0;k<deptList.size();k++){
															CommonBean beanCommon = (CommonBean) deptList.get(k);
																%>
																	<option  value="<c:out value='<%=beanCommon.getField1()%>'/>" ><c:out value='<%=beanCommon.getField2()%>'/></option>
																<%
															}
														%>
														
													</select>&nbsp;&nbsp;Authlevel
													: <select name="authlevel2" id="authlevel2" onchange="funcpopulateAuthLists();">
														<option value="" selected>All</option>
														<%
															for(int k=0;k<authList.size();k++){
															CommonBean beanCommon = (CommonBean) authList.get(k);
																%>
																	<option  value="<c:out value='<%=beanCommon.getField1()%>'/>" ><c:out value='<%=beanCommon.getField2()%>'/></option>
																<%
															}
														%>
														
													</select>
						</td>
					</tr>
					<tr>
						<td align="right"><select
							ondblclick="fnMoveItems('SIGNEDBY', 'SEL_SIGNEDBY');"
							name="SIGNEDBY" id="SIGNEDBY"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<querySignedByList.size();k++){
							CommonBean beanCommon = (CommonBean) querySignedByList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
						<td valign="middle" align="center"><img align="middle"	src="images/next.gif"
							onclick="fnMoveAll( 'SIGNEDBY','SEL_SIGNEDBY')" /><br />
						<img align="middle"	src="images/next_single.gif"
							onclick="fnMoveItems( 'SIGNEDBY','SEL_SIGNEDBY')" /><br />
						<img align="middle" src="images/prev_single.gif"
							onclick="fnMoveItems( 'SEL_SIGNEDBY','SIGNEDBY')" /><br />
						<img align="middle" src="images/prev.gif"
							onclick="fnMoveAll( 'SEL_SIGNEDBY','SIGNEDBY')" />
						</td>
						<td><select
							ondblclick="fnMoveItems('SEL_SIGNEDBY', 'SIGNEDBY');"
							name="SEL_SIGNEDBY" id="SEL_SIGNEDBY"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<selquerySignedByList.size();k++){
							CommonBean beanCommon = (CommonBean) selquerySignedByList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3"
							height="21"><u>Internal Marking :</u></td>

					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3"
							height="4"></td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3">Dept.
													: <select name="deptCode3" id="deptCode3"   onchange="funcpopulateDeptListi();">
														<option value="" selected>All</option>
														<%
															for(int k=0;k<deptList.size();k++){
															CommonBean beanCommon = (CommonBean) deptList.get(k);
																%>
																	<option  value="<c:out value='<%=beanCommon.getField1()%>'/>" ><c:out value='<%=beanCommon.getField2()%>'/></option>
																<%
															}
														%>
														
													</select>&nbsp;&nbsp;Authlevel
													: <select name="authlevel3" id="authlevel3" onchange="funcpopulateAuthListi();">
														<option value="" selected>All</option>
														<%
															for(int k=0;k<authList.size();k++){
															CommonBean beanCommon = (CommonBean) authList.get(k);
																%>
																	<option  value="<c:out value='<%=beanCommon.getField1()%>'/>" ><c:out value='<%=beanCommon.getField2()%>'/></option>
																<%
															}
														%>
														
													</select>
						</td>
					</tr>
					<tr>
						<td align="right"><select
							ondblclick="fnMoveItems('INTMARKING', 'SEL_INTMARKING');"
							name="INTMARKING" id="INTMARKING"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<queryinternalMankingList.size();k++){
							CommonBean beanCommon = (CommonBean) queryinternalMankingList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
						<td valign="middle" align="center"><img align="middle"	src="images/next.gif"
							onclick="fnMoveAll( 'INTMARKING','SEL_INTMARKING')" /><br />
						<img align="middle"	src="images/next_single.gif"
							onclick="fnMoveItems( 'INTMARKING','SEL_INTMARKING')" /><br />
						<img align="middle" src="images/prev_single.gif"
							onclick="fnMoveItems( 'SEL_INTMARKING','INTMARKING')" /><br />
						<img align="middle" src="images/prev.gif"
							onclick="fnMoveAll( 'SEL_INTMARKING','INTMARKING')" />
						</td>
						<td><select
							ondblclick="fnMoveItems('SEL_INTMARKING', 'INTMARKING');"
							name="SEL_INTMARKING" id="SEL_INTMARKING"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<selqueryinternalMankingList.size();k++){
							CommonBean beanCommon = (CommonBean) selqueryinternalMankingList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
					</tr>
				</tbody>
			</table>
	</td>
	<td colspan="2" align="left" valign="top" >
			<table id="data" align="center">
				<tbody>
					<tr class="treven">
						<td style="text-align: center" align="right"><b>Master
						List</b></td>
						<td></td>
						<td style="text-align: center" align="left"><b>User </b>List</td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3"><u>Reference
						Sub. :</u></td>
						
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><select
							ondblclick="fnMoveItems('REFSUBJECTCODE', 'SEL_REFSUBJECTCODE');"
							name="REFSUBJECTCODE" id="REFSUBJECTCODE"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<refsubjectList.size();k++){
							CommonBean beanCommon = (CommonBean) refsubjectList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
						<td valign="middle" align="center"><img align="middle"	src="images/next.gif"
							onclick="fnMoveAll( 'REFSUBJECTCODE','SEL_REFSUBJECTCODE')" /><br />
						<img align="middle"	src="images/next_single.gif"
							onclick="fnMoveItems( 'REFSUBJECTCODE','SEL_REFSUBJECTCODE')" /><br />
						<img align="middle" src="images/prev_single.gif"
							onclick="fnMoveItems( 'SEL_REFSUBJECTCODE','REFSUBJECTCODE')" /><br />
						<img align="middle" src="images/prev.gif"
							onclick="fnMoveAll( 'SEL_REFSUBJECTCODE','REFSUBJECTCODE')" />
						</td>
						<td><select
							ondblclick="fnMoveItems('SEL_REFSUBJECTCODE', 'REFSUBJECTCODE');"
							name="SEL_REFSUBJECTCODE" id="SEL_REFSUBJECTCODE"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<selrefsubjectList.size();k++){
							CommonBean beanCommon = (CommonBean) selrefsubjectList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3"><u>Ack By. :</u></td>
						
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3">&nbsp;</td>
					</tr>
<!------------------------------------------------------------------------------------------------------------------>					
					<tr>
						<td align="right"><select
							ondblclick="fnMoveItems('ACKBY', 'SEL_ACKBY');"
							name="ACKBY" id="ACKBY"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<queryAckByList.size();k++){
							CommonBean beanCommon = (CommonBean) queryAckByList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
						<td valign="middle" align="center"><img align="middle"	src="images/next.gif"
							onclick="fnMoveAll( 'ACKBY','SEL_ACKBY')" /><br />
						<img align="middle"	src="images/next_single.gif"
							onclick="fnMoveItems( 'ACKBY','SEL_ACKBY')" /><br />
						<img align="middle" src="images/prev_single.gif"
							onclick="fnMoveItems( 'SEL_ACKBY','ACKBY')" /><br />
						<img align="middle" src="images/prev.gif"
							onclick="fnMoveAll( 'SEL_ACKBY','ACKBY')" />
						</td>
						<td><select
							ondblclick="fnMoveItems('SEL_ACKBY', 'ACKBY');"
							name="SEL_ACKBY" id="SEL_ACKBY"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<selqueryAckByList.size();k++){
							CommonBean beanCommon = (CommonBean) selqueryAckByList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
					</tr>
<!------------------------------------------------------------------------------------------------------------------>	
					<tr>
						<td style="text-align: center" align="right" colspan="3"
							height="21"><u>Originated Branch:</u></td>

					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3"
							height="4"></td>
					</tr>
					<tr>
						<td style="text-align: center" align="right" colspan="3">Dept.
													: <select name="deptCode4" id="deptCode4"   onchange="funcpopulateDeptListb();">
														<option value="" selected>All</option>
														<%
															for(int k=0;k<deptList.size();k++){
															CommonBean beanCommon = (CommonBean) deptList.get(k);
																%>
																	<option  value="<c:out value='<%=beanCommon.getField1()%>'/>" ><c:out value='<%=beanCommon.getField2()%>'/></option>
																<%
															}
														%>
														
													</select>
						</td>
					</tr>
					<tr>
						<td align="right"><select
							ondblclick="fnMoveItems('BRANCH', 'SEL_BRANCH');"
							name="BRANCH" id="BRANCH"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<queryBranchList.size();k++){
							CommonBean beanCommon = (CommonBean) queryBranchList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
						<td valign="middle" align="center"><img align="middle"	src="images/next.gif"
							onclick="fnMoveAll( 'BRANCH','SEL_BRANCH')" /><br />
						<img align="middle"	src="images/next_single.gif"
							onclick="fnMoveItems( 'BRANCH','SEL_BRANCH')" /><br />
						<img align="middle" src="images/prev_single.gif"
							onclick="fnMoveItems( 'SEL_BRANCH','BRANCH')" /><br />
						<img align="middle" src="images/prev.gif"
							onclick="fnMoveAll( 'SEL_BRANCH','BRANCH')" />
						</td>
						<td><select
							ondblclick="fnMoveItems('SEL_BRANCH', 'BRANCH');"
							name="SEL_BRANCH" id="SEL_BRANCH"
							multiple="multiple" style="width: 180px; height: 100px">
							<%
							for(int k=0;k<selqueryBranchList.size();k++){
							CommonBean beanCommon = (CommonBean) selqueryBranchList.get(k);
						%>
							<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%}%>
						</select></td>
					</tr>
				</tbody>
			</table>
			 	
	</td>
	</tr></tbody>
</table><table width="100%" id="save" style="display: none">
			    		<tr >
						<td colspan="6" align="center"><input type="button" name="btnNEW" id="btnNEW" class="buttNew" style="display: none;" >  
										<input type="button" name="btnSAVE" id="btnSAVE" tabindex="12" onclick="submitForm('SAVE');" class="butts" value=" Save ">
										
										<input type="button" name="btnCLEAR" onclick="submitForm('CLEAR');" class="butts" value=" Clear " >
										
										
						</td>
					</tr>
			    		</table></form>
</body>
</html>
