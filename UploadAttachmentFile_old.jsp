<%@page import="in.org.cris.mrsectt.dao.UploadAttachmentFileDAO"%><html>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.dao.ReportRefNoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<head>
  <link href="${pageContext.request.contextPath}/theme/sub/style.css" rel="stylesheet" type="text/css" />
  <% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
  <link type="text/css" href="${pageContext.request.contextPath}/theme/autoSuggest.css" rel="stylesheet" />
  <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
	
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ReportRefNoDAO.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ReminderAutoDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<%
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");

    String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
    String diarynofrom = StringFormat.nullString(request.getParameter("diarynofrom"));
    String diarynoto = StringFormat.nullString(request.getParameter("diarynoto"));
    String datefrom = StringFormat.nullString(request.getParameter("datefrom"));
    String dateto = StringFormat.nullString(request.getParameter("dateto"));

    ArrayList<CommonBean> dataArr = new ArrayList<CommonBean>();
    if(btnClick.equals("GO"))
    {
		dataArr = (new UploadAttachmentFileDAO()).getData(diarynofrom, diarynoto, datefrom, dateto, sessionBean.getLOGINASROLEID(), sessionBean.getISCONF()); 
	}
	 	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
	 	String serverTime = CommonDAO.getSysDate("HH:mm:ss");	 
%>
<script type="text/javascript">
function unhideTR(row)
{
   row.style.display = '';
}
function hideTR(row)
{
   row.style.display = 'none';
}
function runReport()
 {
	//	var diarynofrom = window.document.frmUpload.diarynofrom;
	//	var diarynoto = window.document.frmUpload.diarynoto;
	//	var datefrom = window.document.frmUpload.datefrom;
	//	var dateto = window.document.frmUpload.dateto;
		//	window.document.frmUpload.method="post";
		//	window.document.frmUpload.target="_new";
		//	window.document.frmUpload.action="GeneralReportsController";
		document.forms[0].btnClick.value="GO";
		window.document.frmUpload.submit();
 }

function setToField(thisObj, TOFIELD){
	var fieldTo = window.document.getElementById(TOFIELD); 
	fieldTo.value = thisObj.value;
	fieldTo.className = 'active';
}

function popupGallery(regId)
{
	var url="ImageGallery.jsp?refId="+regId+"&type=F";
	window.open(url,"","toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,left=0,top=0,scrollbars=1,resizable=1");
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
	
	$(document).ready(function ()
        {
            $.ui.dialog.defaults.bgiframe = true;
            $("#datefrom").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true'});
            $("#dateto").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true' });
            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
        });
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
<form name="frmUpload" id="frmUpload" action="UploadAttachmentFile.jsp" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>File-Create</i> - Upload
      		Attachment</b></font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table><br>
				<center><fieldset style="width: 40%; text-align: center">
				<table width="100%" id="data" align="center">
								<tbody>
									<tr>
									<td align="left" height="18" colspan="2">
									<input type="hidden" name="reportNumber" size="15">&nbsp;</td>
									</tr>
									<tr id="tr1" >
									<td align="right" width="406">File Reg. No. From<font color="red"></font> :</td>
									<td align="left" width="784"><input type="text" name="diarynofrom" id="diarynofrom" size="15" style="text-transform: uppercase;" value="<%= diarynofrom %>" onkeyup="setToField(this, 'diarynoto');"> to&nbsp;
									<input type="text" name="diarynoto" id="diarynoto" size="15" value="<%= diarynoto %>" style="text-transform: uppercase;"> (e.g. 1)
									<input type="hidden" name="btnClick" id="btnClick" value='<%=request.getParameter("btnClick")!=null ? request.getParameter("btnClick"):""%>' /></td>
									</tr>
					
									<tr id="tr4">
									<td align="right" width="406">Incoming Dt.<font color="red"></font> :</td>
									<td align="left" width="784"><input type="text" name="datefrom" id="datefrom" value="26/05/2014" size="11"> to
									<input type="text" name="dateto" id="dateto" value="<%= serverDate %>" size="11"></td>
									</tr>
					</tbody>
							</table>
							</fieldset>
							<table class="formtext" width="100%" border=".5" cellspacing="0" cellpadding="2" bordercolor="white">
								<TR id="tr50">
								<TD colspan="4" align="center">
									<INPUT type="button" name="Submit" id="Submit" value="Submit" onclick="runReport();" style="height: 20px;" class="butts">
									<INPUT type="reset" name="Reset" value="Clear" style="height: 20px;" class="butts">
								</TD>
								</TR>
							</TABLE>
	<%
		if ((dataArr != null) && (dataArr.size() != 0)) {
	%>
			<div id="reportData">
						<table class="tablesorter" id="sorttable" align="center">
								<tr>
									<th>SNo.</th>
									<th style="width: 100" >File No.</th>
									<th style="width: 100" >Incoming Date</th>
									<th style="width: 150" >Received From</th>
									<th style="width: 150" >Subject</th>
									<th>Attachment</th>
								</tr>
							<%
								for(int i=0;i<dataArr.size();i++){
								CommonBean bean = (CommonBean) dataArr.get(i);
							%>
								<tr>
									<td align="center" ><%= i+1 %></td>
									<td align="left" ><%= bean.getField2() %>
									<input type="hidden" name="refId" id="refId" value="<%= bean.getField1() %>" readonly="readonly" size="6"></td>
									<td align="left" ><%= bean.getField3() %></td>
									<td align="left" ><%= bean.getField4() %></td>
									<td align="left" ><%= bean.getField5() %></td>
									<td align="center" ><img border="0" alt="Image Gallery" src="${pageContext.request.contextPath}/images/stn.gif" onclick="popupGallery('<%= bean.getField1()%>');"></td>
								</tr>
							<%} %>
						</table>
						</div>
	<%}  %>
</form>
</body>
</html>