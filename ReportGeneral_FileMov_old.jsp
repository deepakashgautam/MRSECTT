<html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.dao.ReportFileNoDAO"%>
<%@page import="in.org.cris.mrsectt.dao.ModifyHindi_GDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<head>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.min.js"></SCRIPT>

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ModifyHindi_GDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<LINK  href="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 
<% 
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
    
    String queryRefClass = "SELECT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
/*    
	String querySubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME " +    
  						  "	FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND DEPTCODE='"+sessionBean.getDEPTCODE()+"' ORDER BY 2"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 2);
*/
	String querySubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME " +    
  						  "	FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' ORDER BY 2"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 2);

	String queryfileStatus1 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'0' AND CODE <> '00' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus1 = (new CommonDAO()).getSQLResult(queryfileStatus1, 3);
	
	String queryfileStatus2 = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'0' AND CODE <> '00' ORDER BY CODE"; 
	ArrayList<CommonBean> fileStatus2 = (new CommonDAO()).getSQLResult(queryfileStatus2, 3);

	String queryMarkingTo = " SELECT DISTINCT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	String queryreplyCode = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00'"; 
	ArrayList<CommonBean> replyCode = (new CommonDAO()).getSQLResult(queryreplyCode, 3);
	
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

var isconf = '<%=sessionBean.getISCONF()%>';
var isreply = '<%=sessionBean.getISREPLY()%>';
for (var i=0;i<window.document.frm.REP.length;i++)
{
if(window.document.frm.REP[i].checked==true)
  {
	if(window.document.frm.REP[i].value==1)
	{
	hideTR(document.getElementById("tr1"));
	hideTR(document.getElementById("tr2"));
	hideTR(document.getElementById("tr4"));
	hideTR(document.getElementById("tr5"));
	hideTR(document.getElementById("tr7"));
    unhideTR(document.getElementById("tr8"));
	hideTR(document.getElementById("tr9"));
	hideTR(document.getElementById("trconf"));
	hideTR(document.getElementById("trreply"));
    document.frm.reportNumber.value=document.frm.REP[i].value;
	}
	else if(window.document.frm.REP[i].value==2)
	{
	unhideTR(document.getElementById("tr1"));
	unhideTR(document.getElementById("tr2"));
    unhideTR(document.getElementById("tr4"));
    hideTR(document.getElementById("tr5"));
    hideTR(document.getElementById("tr7"));
	hideTR(document.getElementById("tr8"));
	hideTR(document.getElementById("tr9"));
	if(isconf == "1"){
	unhideTR(document.getElementById("trconf"));
	}
	if(isreply == "1"){
	unhideTR(document.getElementById("trreply"));
	}
    document.frm.reportNumber.value=document.frm.REP[i].value;
	}
	else if(window.document.frm.REP[i].value==3)
	{
	hideTR(document.getElementById("tr1"));
	unhideTR(document.getElementById("tr2"));
    unhideTR(document.getElementById("tr4"));
    unhideTR(document.getElementById("tr5"));
    unhideTR(document.getElementById("tr7"));
	hideTR(document.getElementById("tr8"));
	unhideTR(document.getElementById("tr9"));
	if(isconf == "1"){
	unhideTR(document.getElementById("trconf"));
	}
	if(isreply == "1"){
	unhideTR(document.getElementById("trreply"));
	}
	
    document.frm.reportNumber.value=document.frm.REP[i].value;
    }
	else if(window.document.frm.REP[i].value==6)
	{
	hideTR(document.getElementById("tr1"));
	hideTR(document.getElementById("tr2"));
    unhideTR(document.getElementById("tr4"));
    hideTR(document.getElementById("tr5"));
    hideTR(document.getElementById("tr7"));
	hideTR(document.getElementById("tr8"));
	hideTR(document.getElementById("tr9"));
	hideTR(document.getElementById("trconf"));
	hideTR(document.getElementById("trreply"));
	
    document.frm.reportNumber.value=document.frm.REP[i].value;
    }
   /*  if(window.document.frm.REP[i].value==6)
	{
	//alert()
	hideTR(document.getElementById("tr1"));
	hideTR(document.getElementById("tr2"));
	hideTR(document.getElementById("tr4"));
	hideTR(document.getElementById("tr5"));
	hideTR(document.getElementById("tr7"));
    hideTR(document.getElementById("tr8"));
	hideTR(document.getElementById("tr9"));
	hideTR(document.getElementById("trconf"));
	hideTR(document.getElementById("trreply"));
    document.frm.reportNumber.value=document.frm.REP[i].value;
	} */
   }
  }
}

function setChkBlank()
{
for (var i=0;i<window.document.frm.REP.length;i++)
{
if(window.document.frm.REP[i].checked==true)
  {
	if(window.document.frm.REP[i].value==1)
	{
		if(chkblank(window.document.getElementById("refNo")))
		{return 'true'; } else {return 'false';}
	}
	else if(window.document.frm.REP[i].value==2)
	{
		if(chkblank(window.document.getElementById("datefrom")) && chkblank(window.document.getElementById("dateto")) && chkblank(window.document.getElementById("regnofrom")) && chkblank(window.document.getElementById("regnoto")))
		{return 'true'; } else {return 'false';}
	}
	else if(window.document.frm.REP[i].value==3)
	{
		if(chkblank(window.document.getElementById("datefrom")) && chkblank(window.document.getElementById("dateto")))
		{return 'true'; } else {return 'false';}
    }
    else if(window.document.frm.REP[i].value==4)
	{
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
		var regnofrom = window.document.frm.regnofrom;
		var regnoto = window.document.frm.regnoto;
		var searchfor = window.document.frm.searchfor;
		var receivedfrom = window.document.frm.receivedfrom;
		var datefrom = window.document.frm.datefrom;
		var dateto = window.document.frm.dateto;
		var refclass = window.document.frm.refclass;
		var subjectcode = window.document.frm.subjectcode;
		var markto = window.document.frm.markto;        
		var rplyType = window.document.frm.REPLYTYPE;        
		if(flag)
		{
			window.document.frm.method="post";
			window.document.frm.target="_new";
			window.document.frm.action="GeneralReports_FileMovController";
			window.document.frm.submit();
		}
	}
 }

function getIndate(obj)
{
	var ROLEID ='<%= sessionBean.getLOGINASROLEID() %>';
	var REFNO = obj.value;
	var ISCONF = '<%= sessionBean.getISCONF() %>';
	if(REFNO.length>0){
		ModifyHindi_GDAO.getIncomingDateFM(ROLEID, REFNO,ISCONF, getInDateData);
	}
}

function getInDateData(data)
{
	if(document.getElementById("INDATE_DRPDWN").options.length > 0)
	{
		document.getElementById("INDATE_DRPDWN").options.length=0;
	}
  if(data.length > 0)
  {
  	document.getElementById("INDATE_DRPDWN").style.display = "block" ;
  	var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop];
		 
  		var indate = data;
  		var opt = document.createElement("option");
 		var refType = trnRefBean.REFERENCETYPE == 'C'? trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE+' *': trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE;
  		document.getElementById("INDATE_DRPDWN").options.add(opt);
  		opt.text = refType;
    	opt.value = trnRefBean.REFNO+'   '+trnRefBean.INCOMINGDATE;
    	trnRefBean.REFERENCETYPE == 'C'? opt.style.color = "red":"" ;
  	}
  	document.getElementById("INDATE_DRPDWN").focus();
  }
  else
  {
  	document.getElementById("INDATE_DRPDWN").style.display = "none";
  	document.getElementById("INDATE_DRPDWN").options.length=0;
  	document.getElementById("search").focus();
  }
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
	//alert(parseInt(obj.maxLength));
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

function setConf(obj){
	if(obj.checked){
		document.getElementById('conf').value="1";
	}else{
		document.getElementById('conf').value="0";
	}
}

function setReply(obj){
	if(obj.checked){
		document.getElementById('reply').value="1";
	}else{
		document.getElementById('reply').value="0";
	}
}

function setFinal(obj){
	if(obj.checked){
		document.getElementById('mfinal').value="1";
	}else{
		document.getElementById('mfinal').value="0";
	}
}
</script>
</head>
<body onload="hideField();">
<form name="frm" id="frm" action="" method="post">
<font size="3" face="Tahoma"> <b><i>File-Create</i> - Report-General</b></font>
<img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">
				<br><br><center><fieldset style="width: 600px;"><TABLE width="100%" align="center">
						<TR>
							<TD align="center" valign="middle">
		<table id="data" width="100%">
			<tbody>
				<tr id="a1" class="treven	">
					<td align="right" width="48"><input type="radio" name="REP"
						value="1" onclick="hideField();" checked="checked"></td>
					<td align="left" colspan="2" width="238">Reg. No. (Single)</td>
					<td align="right"><input type="radio" name="REP" value="2"
						onclick="hideField();"></td>
					<td align="left" colspan="2" width="233">Serial No From-To</td>
					<td align="right" width="21"><input type="radio" name="REP" value="3"
						onclick="hideField();"></td>
					<td align="left" colspan="2" width="186">Multiple </td>
					<td align="right" width="21"><input type="radio" name="REP" value="6"
						onclick="hideField();"></td>
					<td align="left" colspan="2" width="186">Pending Approval Files</td>
					
					<!-- <td align="right" width="21"><input type="radio" name="REP" value="6"
						onclick="hideField();"></td>
					<td align="left" colspan="2" width="186">Files Consolidated Report</td> -->
				</tr>
			</tbody>
		</table>
		<hr>
				<div id="d2">
							<table width="100%" id="data">
								<tbody>
									<tr>
									<td align="left" height="18" colspan="2">
									<input type="hidden" name="reportNumber" size="15"></td>
									</tr>
									<tr id="tr1" >
									<td align="right" width="222">Reg. No. From<font color="red"></font> :</td>
									<td align="left" width="410"><input type="text" name="regnofrom" id="regnofrom" size="15" tabindex="1"> to&nbsp;
									<input type="text" name="regnoto" id="regnoto" size="15" tabindex="2"> </td>
									</tr>
									<tr id="tr4"  style="display: none;">
									<td align="right" width="222">Regn. Date<font color="red"></font> :</td>
									<td align="left" width="410"><input type="text" name="datefrom" id="datefrom" value="<%=sessionBean.getTENURESTARTDATE() %>" size="11" tabindex="3"> to
									<input type="text" name="dateto" id="dateto" value="<%= serverDate %>" size="11" tabindex="4"></td>
									</tr>
									<tr id="tr5" style="display: none;">
									<td align="right" width="222">File Type:</td>
									<td align="left" width="410">
									<% if(sessionBean.getISCONF().equalsIgnoreCase("0")){ %>
						    			<select style="width: 120px;height: 20px" name="fileType" id="fileType" tabindex="5">
											<option value="" >All</option>
											<option value="A" >Approval</option>
											<option value="D" >Draft</option>
											<option value="P" >Position</option>
										</select>
									<%}else{ %>
										<select style="width: 120px;height: 20px" name="fileType" id="fileType" tabindex="5">
											<option value="" >All</option>
											<option value="A" >Approval</option>
											<option value="D" >Draft</option>
											<option value="P" >Position</option>											
											<option value="C" >Confidential</option>
										</select>
									<%} %>
									</td>
									</tr>
															<tr id="tr2" >
									<td align="right" width="222">Reply Type :</td>
									<td align="left" width="410">
										<select name="REPLYTYPE" id="REPLYTYPE" class="drop">
											<option value="">--All--</option>
											<%
												for(int k=0;k<replyCode.size();k++){
												CommonBean beanCommon = (CommonBean) replyCode.get(k);
											%>
											<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
											<%}%>
										</select>
									</td>
									</tr>
						
						<tr id="tr7" style="display: none;">
									<td align="right" width="222">Internal Marking  :</td>
									<td align="left" width="410"><nobr><select name="markto" id="markto" class="drop" tabindex="6">
									<option value="">--All--</option>
									<%
										for(int i=0;i<markingToList.size();i++){
										CommonBean beanCommon = (CommonBean) markingToList.get(i);
										%>
											<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
										<%
									}%>
									</select>&nbsp;Last Movement?&nbsp;<input type="checkbox" name="chkfinal" id="chkfinal" onclick="setFinal(this);"/><input type="hidden" name="mfinal" id="mfinal" value="0"/></nobr></td>
									
									</tr>
						<tr id="tr8"  style="display: none;">
									<td align="right" valign="top" width="222">Reg. No :</td>
									<td align="left" nowrap="nowrap" width="410">
									<input type="text" id="refNo" name="refNo" maxlength="5" size="14" onblur="getIndate(this)" tabindex="7"><BR/>
									<select	name="INDATE_DRPDWN" id="INDATE_DRPDWN" style="display: none; height: 50; width: 200" size="3" >
									</select>
									</td>
									
									</tr>
						<tr id="tr9">
							<td align="right" width="222">File Status :</td>
							<td align="left" width="410"><nobr><select name="FILESTATUS1" id="FILESTATUS1" style="height:20px" tabindex="8">
								<option value="">--</option>
							<%
								for(int k=0;k<fileStatus1.size();k++){
								CommonBean beanCommon = (CommonBean) fileStatus1.get(k);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
							<%}%>
							</select>
							<select name="FILESTATUS2" id="FILESTATUS2" style="height:20px"  tabindex="9">
								<option value="">--</option>
							<%
								for(int k=0;k<fileStatus2.size();k++){
								CommonBean beanCommon = (CommonBean) fileStatus2.get(k);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
							<%}%>
							</select></nobr></td>
						</tr>
				
				<tr id="trconf">
					<td align="right" width="222">Confidential? : </td>
					<td align="left" width="410"><input type="checkbox" name="chkconf" id="chkconf" onclick="setConf(this);"/><input type="hidden" name="conf" id="conf" value="0"/></td>
				</tr>
				<tr id="trreply" style="display:none;">
					<td align="right" width="222">Reply? : </td>
					<td align="left" width="410"><input type="checkbox" name="chkreply" id="chkreply" onclick="setReply(this);"/><input type="hidden" name="reply" id="reply" value="0"/></td>
				</tr>
				
			</tbody>
							</table>
							</div>
							</TD>
						</TR>
						<tr>
							<td>
							<table class="formtext" width="100%" border=".5"
								cellspacing="0" cellpadding="2" bordercolor="white"
								>
								<TR>
									<TD colspan="4" align="center">
									<INPUT type="button" name="Submit" id="Submit" value="Submit" onclick="runReport();" style="height: 18px;" tabindex="10" class="butts">
								</TD>
								</TR>
							</TABLE>
							</td>
						</tr>
					</TABLE></fieldset></center> 
</form>
</body>
</html>