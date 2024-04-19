<!DOCTYPE html>
<%@page import="in.org.cris.mrsectt.Beans.MstSubject"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.UpdateVIPName"%><html>
<head><title>Minister for Railways Secretariat</title>
<!-- <LINK href="${pageContext.request.contextPath}/theme/MasterGreen.css" rel="stylesheet" type="text/css">  -->
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="/MRSECTT/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="/MRSECTT/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="/MRSECTT/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="/MRSECTT/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="/MRSECTT/script/validateinput.js"></script>
<script type="text/javascript" src="/MRSECTT/script/scripts.js"></script>
<script type="text/javascript" src="/MRSECTT/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="/MRSECTT/theme/jquery/jquery.tablefilter.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/MstSubjectDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<script type="text/javascript" src="/MRSECTT/script/validateinput.js"></script>
<link type="text/css" href="/MRSECTT/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<%
	String REFERENCENAME = StringFormat.nullString(request.getParameter("REFERENCENAME"));
	String VIPSTATUS = StringFormat.nullString(request.getParameter("VIPSTATUS"));
	String STATECODE = StringFormat.nullString(request.getParameter("STATECODE"));
	String VIPPARTY = StringFormat.nullString(request.getParameter("VIPPARTY"));
	String msg = StringFormat.nullString((String)session.getAttribute("msg"));
	CommonBean bean = new CommonBean();
	ArrayList vipNameList = null;
	String condition="";
	if(REFERENCENAME.length()>0) {
		condition+=" and UPPER(REFERENCENAME) LIKE UPPER('%"+REFERENCENAME+"%')";
	}
	if(VIPSTATUS.length()>0) {
		condition+=" and UPPER(VIPSTATUS) LIKE UPPER('%"+VIPSTATUS+"%')";
	}
	if(STATECODE.length()>0) {
		condition+=" and UPPER(STATECODE) LIKE UPPER('%"+STATECODE+"%')";
	}
	if(VIPPARTY.length()>0) {
		condition+=" and UPPER(VIPPARTY) LIKE UPPER('%"+VIPPARTY+"%')";
	}

	if(REFERENCENAME.length()>0 || VIPSTATUS.length()>0 || STATECODE.length()>0 || VIPPARTY.length()>0) {
		String Query = "SELECT DISTINCT REFERENCENAME, VIPSTATUS, STATECODE,VIPPARTY FROM TRNREFERENCE WHERE 1=1 "+condition;
		vipNameList = (new CommonDAO()).getSQLResult(Query, 4);
	}
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
 %>
<script type="text/javascript">
 function submitForm(btnclick) {
	window.document.frmUpdateRole.btnclick.value=btnclick;
	if("GO"==btnclick){
			 window.document.frmUpdateRole.submit();
	}
	if("S"==btnclick){
	    window.document.frmUpdateRole.method = "post";
			 window.document.frmUpdateRole.action = "UpdateVIPNameController";
			 window.document.frmUpdateRole.submit();
	}
	if("C"==btnclick){
	    window.document.frmUpdateRole.method = "get";
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
	document.frmUpdateRole.REFERENCENAME.focus();
}

function copyAllRow() {
//	window.document.getElementById("FcopyAll").value = 'Y';
	var flg = window.document.getElementById("FcopyAll").value;
	if(flg == "Y") {
		for(var i=1;i<window.document.getElementsByName("NEWNAME").length;i++) {
			window.document.getElementsByName("NEWNAME")[i].value = window.document.getElementsByName("NEWNAME")[0].value;
			window.document.getElementsByName("NEWSTATUS")[i].value = window.document.getElementsByName("NEWSTATUS")[0].value;
			window.document.getElementsByName("NEWSTATE")[i].value = window.document.getElementsByName("NEWSTATE")[0].value;
			window.document.getElementsByName("NEWPARTY")[i].value = window.document.getElementsByName("NEWPARTY")[0].value;
		}
		window.document.getElementById("FcopyAll").value = 'N';
	}else if(flg == "N") {
		for(i=1;i<window.document.getElementsByName("NEWNAME").length;i++) {
			window.document.getElementsByName("NEWNAME")[i].value = '';
			window.document.getElementsByName("NEWSTATUS")[i].value = '';
			window.document.getElementsByName("NEWSTATE")[i].value = '';
			window.document.getElementsByName("NEWPARTY")[i].value = '';
		}	
		window.document.getElementById("FcopyAll").value = 'Y';
	}
}

$(document).ready(function(){
   $("input[name='NEWNAME']").autocomplete("getOldVIPName.jsp", {scroll:false});
   $("input[name='NEWNAME']").result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			var idx = getIndex(this);
			document.getElementsByName('NEWNAME')[idx].value = dataarr[0];
			document.getElementsByName('NEWSTATUS')[idx].value = dataarr[1];
			document.getElementsByName('NEWSTATE')[idx].value = dataarr[2];
			document.getElementsByName('NEWPARTY')[idx].value = dataarr[3];
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
  $(document).ready(function(){
    $("#MainTabDiv").tabs({ fx: { slide: true, fade: true, speed: 'normal' }  });
    $( "#tabs" ).tabs();
    
    $("#REFERENCENAME").autocomplete("getReferenceNamesData.jsp?page=updatevip", {scroll:false});
    $('#REFERENCENAME').result(function(event, data, formatted) {
		    if (data) {
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('REFERENCENAME').value = dataarr[0];
			window.document.getElementById('VIPSTATUS').value = dataarr[1];
			window.document.getElementById('STATECODE').value = dataarr[2];
			window.document.getElementById('VIPPARTY').value = dataarr[3];
		}
	});
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
<FORM action="UpdateVIPName111.jsp" method="post"  name="frmUpdateRole">
<table width="100%">
<tr>
	<td><font size="3" > 
	<b><i>Masters </i> - Update VIP Name</b>
           </font><img src="/MRSECTT/images/arrow_big.gif" width="6" height="11">  
			        <table width="95%" align="left"  style="vertical-align: top;" id="data">
 	<tr>
 		<td width="*" valign="top" align="left" >
		</td>
 		<td id="td2" width="100%">
<TABLE align="center" >
	<TBODY>
		<tr class="treven">
			<td valign="top" align="center" width="46"><input type="text" id="REFERENCENAME" name="REFERENCENAME"
								maxlength="50" value="<%= REFERENCENAME %>"
								style=" background-image: url('${pageContext.request.contextPath}/images/search.png'); background-position: right;background-repeat: no-repeat; "
								size="42" onkeypress="allowAlphaNum('()/');"></td>
			<td valign="top" align="center" width="72"><input type="text"
								name="VIPSTATUS" id="VIPSTATUS" maxlength="50"
								value="<%=  VIPSTATUS%>" size="45" onkeypress="allowAlphaNum('()/');"></td>
			<td valign="top" align="center" width="119"><input type="text" name="STATECODE" id="STATECODE" value="<%=  STATECODE%>"></td>
			<td valign="top" align="center" width="119"><input type="text" name="VIPPARTY" id="VIPPARTY" value="<%=  VIPPARTY%>"></td>
			<td valign="top" align="center" width="119"><input type="button" name="GO" id="GO" class="butts" value=" Go " onclick="submitForm('GO')">
			<input type="hidden" name="btnclick" ></td>
		</tr>
	</TBODY>
</TABLE> 		
<%if(vipNameList != null) { %>		
<TABLE align="center" width="80%">
	<TBODY>
		<tr class="treven">
			<td valign="top" align="center" width="46"><b>S No.</b></td>
			<td valign="top" align="center" width="72">Old Name</td>
			<td valign="top" align="center" width="119">Old Status</td>
			<td valign="top" align="center" width="118">Old State</td>
			<td valign="top" align="center" width="119">Old Party</td>
			<td valign="top" align="center" width="72">New Name</td>
			<td valign="top" align="center" width="119">New Status</td>
			<td valign="top" align="center" width="118">New State</td>
			<td valign="top" align="center" width="119">New Party
			
			<input type="checkbox" name="copyAll" id="copyAll" alt="Copy"  onclick="copyAllRow();"> Copy
			<input type="hidden" name="FcopyAll" id="FcopyAll" value="Y" size="2">
			</td>
		</tr>
	</TBODY>
	<TBODY id="baserow">
<%
	for(int i=0;i<vipNameList.size();i++){
	CommonBean beanCmn = (CommonBean) vipNameList.get(i);
%>
		<TR class="<%=i%2==0?"treven":"trodd" %>" align="left" >
			<td align="center"><%=i+1%></td>
			<td><input type="text" name="OLDNAME" id="OLDNAME"  value="<%= beanCmn.getField1()%>" maxlength="4" readonly size="30" ></td>
			<td><input type="text" name="OLDSTATUS" id="OLDSTATUS" maxlength="50" value="<%= beanCmn.getField2()%>" size="30" readonly><INPUT type="hidden" name="dtlid" value="<%=bean.getField1()%>"></td>
			<td><input type="text" name="OLDSTATE" id="OLDSTATE" maxlength="50" value="<%= beanCmn.getField3()%>" size="5" ></td>
			<td><input type="text" name="OLDPARTY" id="OLDPARTY" maxlength="50" value="<%= beanCmn.getField4()%>" size="5" ></td>
			<td><input type="text" name="NEWNAME" id="NEWNAME" maxlength="50"  value="" size="30" ></td>
			<td><input type="text" name="NEWSTATUS" id="NEWSTATUS" value="" size="30">
			<td><input type="text" name="NEWSTATE" id="NEWSTATE" maxlength="4"  value="" size="5">
			<td><input type="text" name="NEWPARTY" id="NEWPARTY" value="" size="30">
			<INPUT type="hidden" name="dtlid" value="<%=bean.getField1()%>">
		</TR>
<%}%>
	</TBODY>
	
	<tr >
            <td> </td>
			<td> </td>
			<td> </td>
		    <td> </td>
			<td><input type="button" name="btnSAVE" class="butts" value=" Save " onclick="submitForm('S')"></td>
			<td><input type="button" name="btnCLEAR" class="butts" value=" Clear " onclick="submitForm('C')"></td>
	</tr>
	</TABLE>
<%} %>
</TD>
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
			<BR><img src="images/<%= msg.substring(0,3).equals("GRN")?"GRN":"RED"%>.gif"/><%= msg.substring(3) %> <BR><input type="button" value=" Ok " onclick="callMe()">
			<BR><BR>
<% } %>
</DIV>
</DIV>
<%="<!-- Model Ends "%>    --> <!-- DO NOT ABOVE BELOW THIS!!! -->
<% session.removeAttribute("msg"); %>
</FORM>
</body>
</html>
