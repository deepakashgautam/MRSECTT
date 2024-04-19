<!DOCTYPE html>
<%@page import="in.org.cris.mrsectt.Beans.MstSubject"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%><html>
<head><title>Minister for Railways Secretariat</title>
<!-- <LINK href="${pageContext.request.contextPath}/theme/MasterGreen.css" rel="stylesheet" type="text/css">  -->
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/MstSubjectDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">

<%
	MstSubject	mstSubjectBean =  (request.getAttribute("mstSubjectBean")!=null) ?(MstSubject)request.getAttribute("mstSubjectBean"): new MstSubject();
	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();
	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
	CommonBean bean = new CommonBean();
	
	//String Query="SELECT ROLEID, ROLENAME FROM MSTROLE ORDER BY ROLENAME"; 
	//String Query="SELECT ROLEID, ROLENAME FROM MSTROLE where roleid in (select distinct(trnmarking.MARKINGTO) from trnmarking where refid in (select refid from trnreference where tenureid = 12)) ORDER BY ROLENAME ";
	
	String Query="SELECT ROLEID, ROLENAME FROM MSTROLE where REGEXP_LIKE(UPPER(TRIM(rolename)), '^[A-Z]') ORDER BY ROLENAME";
	ArrayList RsubjectList = (new CommonDAO()).getSQLResult(Query, 2);

	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
 %>
<style>
	#tabs { height: 420px } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 420px; overflow: auto; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
</style>

<script type="text/javascript">
 function submitForm(btnclick) {
	window.document.frmUpdateRole.btnclick.value=btnclick;
	if("S"==btnclick){
			 window.document.frmUpdateRole.submit();
	}
	if("C"==btnclick){
	    window.document.frmUpdateRole.method = "post";
		window.document.frmUpdateRole.submit();
	}
} 

function showDiv()
{
<%
	if (msg.length() > 3 )
	{%>
	document.getElementById('updateDiv').style.display="block";
	document.getElementById('updateDivInner').style.display="block";
<%	}%>
}

function hideDiv()
{
	document.getElementById('updateDiv').style.display="none";
	document.getElementById('updateDivInner').style.display="none";
}

function callMe()
{
	hideDiv();
}

function bodyOnLoad()
{
	document.frmUpdateRole.NROLENAME[0].focus();
}

$(document).ready(function(){
   $("input[name='NROLENAME']").autocomplete("getOldRefID.jsp", {scroll:false});
   $("input[name='NROLENAME']").result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			var idx = getIndex(this);
			document.getElementsByName('NROLENAME')[idx].value = dataarr[0];
			document.getElementsByName('NROLEID')[idx].value = dataarr[1];
		}
	});
  });
</script>
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
	}
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
            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
             $("input[type=text]").blur(function () {$(this).css('background', '#FFFFFF') });
             $("select").live('focus', function() {$(this).css('background', '#FFE4E1')});
             $("select").live('blur', function() {$(this).css('background', '#FFFFFF')});
            $("TEXTAREA").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
            $("TEXTAREA").blur(function () {  $(this).css('background', '#FFFFFF') });
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
</script>
</head>

<body onload="showDiv(); bodyOnLoad()">
<FORM action="${pageContext.request.contextPath}/UpdateRoleIDController" method="post"  name="frmUpdateRole">
<table width="100%">
<tr>
	<td><font size="3" > 
	<b><i>Masters </i> - Update Role ID</b>
           </font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">  
      		</font>
			        <table width="95%" align="left"  style="vertical-align: top;" id="data">
 	<tr>
 		<td width="*" valign="top" align="left" >
		</td>
 		<td id="td2" width="100%">
<TABLE align="center" >
	<TBODY>
		<tr class="treven">
			<td valign="top" align="center" width="46"><b>S No.<input type="hidden" name="btnclick" ></td>
			<td valign="top" align="center" width="72">Old Role ID</td>
			<td valign="top" align="center" width="119">Old Role Name</td>
			<td valign="top" align="center" width="118">New Role Name</td>
			<td valign="top" align="center">New Role ID</td>
		</tr>
	</TBODY>
	<TBODY id="baserow">
<%
	for(int i=0;i<RsubjectList.size();i++){
	CommonBean beanCommon=(CommonBean) RsubjectList.get(i);
%>
		<TR class="<%=i%2==0?"treven":"trodd" %>" align="left" >
			<td><input type="text" name="SNO" id="SNO" value="<%=i+1%>" size="4" readonly  ></td>
			<td><input type="text" name="OROLEID" id="OROLEID"  value="<c:out value='<%=beanCommon.getField1() %>'/>" maxlength="4" readonly size="10" ></td>
			<td><input type="text" name="OROLENAME" id="OROLENAME" maxlength="50" value="<c:out value='<%=beanCommon.getField2() %>'/>" size="30" readonly>
				<INPUT type="hidden" name="dtlid" value="<c:out value='<%=bean.getField1()%>'/>"></td>
			<td><input type="text" name="NROLENAME" id="NROLENAME" maxlength="50"  value="" size="30" ></td>
			<td><input type="text" name="NROLEID" id="NROLEID" maxlength="4"  value="" size="10" readonly="readonly">
			<INPUT type="hidden" name="dtlid" value="<c:out value='<%=bean.getField1()%>'/>">
		</TR>
<%}%>
	</TBODY>
	</TABLE>
</TD>
<TR align="left" id="td4"><TD></TD>
<TD align="left" >
						<table border="0" width="150" align="center">
							<tbody>
								<tr>
									<td>
										<input type="button" name="btnSAVE" class="butts" value=" Save " onclick="submitForm('S')"></td>
									<td ><input type="button" name="btnCLEAR"
										class="butts" value=" Clear " onclick="submitForm('C')">
									</td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
				</table>	
</td>
</tr>
</table>
<!-- DO NOT DELETE BELOW THIS!!! Following will create model. It will get uncommented once jsp is processed. -->
<!--main Content Area Ends--> <!--  <%=" Model Starts -->"%>
<DIV class="transparent_class" style="z-index:2000; background-color:#000; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 150%;" id="updateDiv">
</DIV>
<DIV style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:20px; top: 160px; display: none; width: 100%" id="updateDivInner">
<DIV class="pageheader" style="color: blue; font-family:tahoma ; z-index:5000; background-color: #FBD6B5; width: 400px; border: groove; text-align: center; background-image:url(images/top-hd-bar-bg.gif); background-repeat: repeat; " id="divLoading">
<%	if(msg.length()>3) { %>
			<BR><img src="images/<%= msg.substring(0,3).equals("GRN")?"GRN":"RED"%>.gif"/><c:out value='<%= msg.substring(3) %>'/> <BR><input type="button" value=" Ok " onclick="callMe()">
			<BR><BR>
<% } %>
</DIV>
</DIV>
<%="<!-- Model Ends "%>    --> <!-- DO NOT ABOVE BELOW THIS!!! -->
<% session.removeAttribute("msg"); %>
</FORM>
</body>
</html>
