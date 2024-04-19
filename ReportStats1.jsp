<html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<head>
<script type="text/javascript" src="script/scripts.js"></script>
<script type="text/javascript" src="script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<link href="${pageContext.request.contextPath}/theme/sub/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="${pageContext.request.contextPath}/theme/autoSuggest.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
<% 
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
    
    String queryRefClass = "SELECT DISTINCT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"' ORDER BY REFCLASS"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
/*    
	String querySubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME " +    
  						  "	FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND DEPTCODE='"+sessionBean.getDEPTCODE()+"' ORDER BY 2"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 2);
*/
	String querySubject = " SELECT DISTINCT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID AND A.SUBJECTTYPE='R') SUBJECTNAME " +    
  						  "	FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' ORDER BY 2"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 2);
	
	String queryState = "SELECT STATECODE, STATENAME FROM MSTSTATE"; 
	ArrayList<CommonBean> stateList = (new CommonDAO()).getSQLResult(queryState, 2);
	/*
 	String queryMarkingTo = " SELECT B.TENUREID, (SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.ROLEID ) ROLENAME "+ 
 							" FROM MSTTENURE B WHERE B.ISACTIVE = 'Y' AND TENUREID <> '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	*/
/*	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND DEPTCODE='"+sessionBean.getDEPTCODE()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
*/
	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
%>

<script type="text/javascript">

  $(document).ready(function(){
    $("#receivedfrom").autocomplete("getReferenceNamesData.jsp", {scroll:false});
    $('#receivedfrom').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('receivedfrom').value = dataarr[0];
		}
	});
  });
  

function unhideTR(row)
{
   row.style.display = '';
}

function hideTR(row)
{
   row.style.display = 'none';
}

function hideField()
{
for (var i=0;i<window.document.frm.REP.length;i++)
{
if(window.document.frm.REP[i].checked==true)
  {
	if(window.document.frm.REP[i].value==1)
	{
		window.document.getElementById("div_InDt").style.display = "block";
		window.document.getElementById("div_TrDt").style.display = "none";
		hideTR(document.getElementById("tr1"));
		hideTR(document.getElementById("tr2"));
		hideTR(document.getElementById("tr3"));
		unhideTR(document.getElementById("tr4"));
		unhideTR(document.getElementById("tr5"));
		hideTR(document.getElementById("tr6"));
		hideTR(document.getElementById("tr7"));
	    hideTR(document.getElementById("tr8"));
		hideTR(document.getElementById("tr9"));
		hideTR(document.getElementById("tr10"));
		hideTR(document.getElementById("tr11"));
	    document.frm.reportNumber.value=document.frm.REP[i].value;
	}
    else if(window.document.frm.REP[i].value==2)
	{
		window.document.getElementById("div_InDt").style.display = "block";
		window.document.getElementById("div_TrDt").style.display = "none";
		hideTR(document.getElementById("tr1"));
		hideTR(document.getElementById("tr2"));
		hideTR(document.getElementById("tr3"));
		unhideTR(document.getElementById("tr4"));
		unhideTR(document.getElementById("tr5"));
		hideTR(document.getElementById("tr6"));
		hideTR(document.getElementById("tr7"));
	    hideTR(document.getElementById("tr8"));
		hideTR(document.getElementById("tr9"));
		hideTR(document.getElementById("tr10"));
		hideTR(document.getElementById("tr11"));
	    document.frm.reportNumber.value=document.frm.REP[i].value;
    }
    else if(window.document.frm.REP[i].value==3)
	{
		window.document.getElementById("div_InDt").style.display = "block";
		window.document.getElementById("div_TrDt").style.display = "none";
		hideTR(document.getElementById("tr1"));
		hideTR(document.getElementById("tr2"));
		hideTR(document.getElementById("tr3"));
		unhideTR(document.getElementById("tr4"));
		unhideTR(document.getElementById("tr5"));
		hideTR(document.getElementById("tr6"));
		hideTR(document.getElementById("tr7"));
	    hideTR(document.getElementById("tr8"));
		hideTR(document.getElementById("tr9"));
		unhideTR(document.getElementById("tr10"));
		unhideTR(document.getElementById("tr11"));
	    document.frm.reportNumber.value=document.frm.REP[i].value;
    }
    else if(window.document.frm.REP[i].value==4)
	{
		window.document.getElementById("div_InDt").style.display = "none";
		window.document.getElementById("div_TrDt").style.display = "block";
		hideTR(document.getElementById("tr1"));
		hideTR(document.getElementById("tr2"));
		hideTR(document.getElementById("tr3"));
		unhideTR(document.getElementById("tr4"));
		unhideTR(document.getElementById("tr5"));
		hideTR(document.getElementById("tr6"));
		hideTR(document.getElementById("tr7"));
	    hideTR(document.getElementById("tr8"));
		hideTR(document.getElementById("tr9"));
		hideTR(document.getElementById("tr10"));
		hideTR(document.getElementById("tr11"));
	    document.frm.reportNumber.value=document.frm.REP[i].value;
    }
    else if(window.document.frm.REP[i].value==5)
	{
		window.document.getElementById("div_InDt").style.display = "none";
		window.document.getElementById("div_TrDt").style.display = "block";
		hideTR(document.getElementById("tr1"));
		hideTR(document.getElementById("tr2"));
		hideTR(document.getElementById("tr3"));
		unhideTR(document.getElementById("tr4"));
		unhideTR(document.getElementById("tr5"));
		hideTR(document.getElementById("tr6"));
		hideTR(document.getElementById("tr7"));
	    hideTR(document.getElementById("tr8"));
		hideTR(document.getElementById("tr9"));
		hideTR(document.getElementById("tr10"));
		hideTR(document.getElementById("tr11"));
	    document.frm.reportNumber.value=document.frm.REP[i].value;
    }
   }
  }
}

function setChkBlank()
{
for (var i=0;i<window.document.frm.REP.length;i++)
{
if(window.document.frm.REP[i].checked==true)
  {
	if(window.document.frm.REP[i].value==3)
	{
		if(chkblank(window.document.getElementById("diarynofrom")) && chkblank(window.document.getElementById("diarynoto")) && chkblank(window.document.getElementById("datefrom")) && chkblank(window.document.getElementById("dateto")))
		{return 'true'; } else {return 'false';}
	}
   }
  }
}
function runReport()
 {
	var aa = setChkBlank();
	if(aa == 'false'){
		return false;
	}
	else {
		var flag = true;
		var diarynofrom = window.document.frm.diarynofrom;
		var diarynoto = window.document.frm.diarynoto;
		var searchfor = window.document.frm.searchfor;
		var receivedfrom = window.document.frm.receivedfrom;
		var datefrom = window.document.frm.datefrom;
		var dateto = window.document.frm.dateto;
		var refclass = window.document.frm.refclass;
		var subjectcode = window.document.frm.subjectcode;
		var markto = window.document.frm.markto;        
		if(flag)
		{
			if(chkblank(window.document.getElementById("refclass"))){
				window.document.frm.method="post";
				window.document.frm.target="_new";
				window.document.frm.action="StaticReportsController";
				window.document.frm.submit();
			}
		}
	}
 }

 </script>
<script>  
 function funcKeyPress(obj,objvalue){
 //if(objvalue.length==0){
    if(window.event.keyCode==113){
        obj.value=obj.value+'<c:out value="<%=serverDate%>"/>'; 
 	}
 // } 
 }
 
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

$(document).ready(function ()
        {
            $.ui.dialog.defaults.bgiframe = true;
            $("#datefrom").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true'});
            $("#dateto").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true' });
            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
        });
</script>
</head>
<body onload="hideField();">
<form name="frm" id="frm" action="StaticReportsController" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Report-Statistical</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table><br><br><center><fieldset  style="width: 70%">
				<table id="data" width="90%" align="center">
					<tbody>
						<tr class="treven">
							<td align="right" ><input type="radio" name="REP" value="2" onclick="hideField();" checked="checked"></td>
							<td align="left" colspan="2" >Incoming Date</td>
							<td align="right" ><input type="radio" name="REP" value="3" onclick="hideField();"></td>
							<td align="left" colspan="2" >Received From</td>
							<td align="right" ><input type="radio" name="REP" value="4" onclick="hideField();"></td>
							<td align="left" colspan="2" >Forward To (Target Dt.)</td>
							<td align="right" ><input type="radio" name="REP" value="5" onclick="hideField();"></td>
							<td align="left" colspan="2" >Reply Type (Target Dt.)</td>
							<td align="right" ><input type="radio" name="REP" value="1" onclick="hideField();"></td>
							<td align="left" colspan="2" >Forward To (Incoming Dt.)</td>

						</tr>
					</tbody>
				</table></fieldset></center>
				<center><fieldset  style="width: 70%"><table width="70%" id="data">
								<tbody>
									<tr>
									<td align="left" height="18" colspan="2">
									<input type="hidden" name="reportNumber" size="15">&nbsp;&nbsp;</td>
									</tr>
									<tr id="tr1" >
									<td align="right" >Ref. No. From<font color="red"></font> :</td>
									<td align="left" ><input type="text" name="diarynofrom" id="diarynofrom" value="MR/A/1/2012" size="15" onkeypress="changecase(this,'U');" onkeyup="changecase(this,'U');"> to&nbsp;
									<input type="text" name="diarynoto" id="diarynoto" value="MR/A/5/2012" size="15" onkeypress="changecase(this,'U');" onkeyup="changecase(this,'U');"> (e.g. MR/A/1/1900)</td>
									</tr>
						<tr id="tr2"  style="display: none;">
									<td align="right" height="15" >Search For<font color="red"></font> :</td>
									<td align="left" height="15" ><input type="text" name="searchfor" id="searchfor" value="" size="34"></td>
									</tr>
									<tr id="tr4"  style="display: none;">
									<td align="right"><div id="div_InDt" style="display: block;">Incoming Dt. :</div><div id="div_TrDt" style="display: none;">Target Dt. :</div></td>
									<td align="left" ><input type="text" name="datefrom" id="datefrom" value="<c:out value='<%=sessionBean.getTENURESTARTDATE() %>'/>" size="11"/> to
									<input type="text" name="dateto" id="dateto" value="<c:out value='<%= serverDate %>'/>" size="11"/></td>
									</tr>
									<tr id="tr3"  style="display: none;">
									<td align="right" >Received From<font color="red"></font> :</td>
									<td align="left" ><input type="text" id="receivedfrom" name="receivedfrom" maxlength="30" value="" size="38"
										style=" background-image: url('images/search.png'); background-position: right;background-repeat: no-repeat;"></td>
									</tr>
									<tr id="tr5" style="display: none;">
									<td align="right" width="406">Ref. Class :</td>
									<td align="left" width="784"><select name="refclass" id="refclass" class="drop" style="width: 80px;">
									<option value="" selected></option>
			    					<%
										for(int i=0;i<refClassList.size();i++){
										CommonBean beanCommon=(CommonBean) refClassList.get(i);
										%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField1()%>'/></option>
										<%
									}%>
									</select></td>
									</tr>
						<tr id="tr6" style="display: none;">
							<td align="right" width="406">Sub. :</td>
							<td align="left" width="784"><select name="subjectcode" id="subjectcode" class="drop">
								<option value="">-select-</option>
								<%
										for(int i=0;i<subjectList.size();i++){
										CommonBean beanCommon = (CommonBean) subjectList.get(i);
										%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%
									}%>
							</select></td>
						</tr>
						<tr id="tr7" style="display: none;">
									<td align="right" width="406">Forward To :</td>
									<td align="left" width="784"><select name="markto" id="markto" class="drop">
									<option value="">-select-</option>
									<%
										for(int i=0;i<markingToList.size();i++){
										CommonBean beanCommon = (CommonBean) markingToList.get(i);
										%>
											<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
										<%
									}%>
									</select></td>
									
									</tr>
						<tr id="tr8" style="display: none;">
									<td align="right" width="406">Ref. No :</td>
									<td align="left" width="784"><input type="text" id="refNo" name="refNo" maxlength="30" value="" size="38"></td>
									
									</tr>
						<tr id="tr9">
							<td align="right" width="406">Tag (1,2,3,4) :</td>
							<td align="left" width="784"><input type="text" name="tag" id="tag" value="" size="34"></td>
						</tr>
						<tr id="tr10">
							<td align="right" width="406">Status :</td>
							<td align="left" width="784"><input type="text" name="status" id="status" value="" size="34"></td>
						</tr>
						<tr id="tr11">
							<td align="right" width="406">State :</td>
							<td align="left" width="784">
		<select name="statecode" id="statecode" style="width: 160px" tabindex="10">
							<option value=""> </option>
							<%
								for(int i=0;i<stateList.size();i++){
								CommonBean beanCommon=(CommonBean) stateList.get(i);
							%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/>
								</option>
							<%}%>
		</select></td>
						</tr>
						</tbody>
							</table></fieldset></center>
						<table align="center">
						<tr id="tr12">
							<td align="right" width="406"><fieldset style="width: 70%"><legend>Subject :</legend>
							<table width="70%">
							
							<%
								for(int i=0;i<subjectList.size();i++){
								CommonBean beanCommon=(CommonBean) subjectList.get(i);
							%>
							<%if(i%10==0){%><tr><%} %>
								<td><c:out value='<%=beanCommon.getField1() %>'/></td>
								<td><input type="checkbox" name="csubject" id="csubject" value="<%=beanCommon.getField1() %>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<%if(i%10==10){%></tr><%} %>
							 <%}%>
							
							</table>
							</fieldset></td>
							
						</tr>
					
							</table>
							<table class="formtext" width="100%" 
								cellspacing="0" cellpadding="2" bordercolor="white"
								>
								<TR>
									<TD colspan="4" align="center">
									<INPUT type="button" name="Submit" value="Submit" onclick="runReport();" style="height: 20px;" class="butts">
									<INPUT type="reset" name="Reset" value="Clear" style="height: 20px;" class="butts">
								</TD>
								</TR>
							</TABLE>
</form>
</body>
</html>