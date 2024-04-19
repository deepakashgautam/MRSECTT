<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="in.org.cris.mrsectt.dao.BudgetReportDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%><html> 
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
<script type="text/javascript" src="theme/jquery/jquery.tablesorter.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
<%
    //ArrayList<CommonBean> dataArr=new ArrayList();	
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
    String btnClick = request.getParameter("btnClick")!=null ? request.getParameter("btnClick") : "";
    String BOOKDATEFROM=request.getParameter("BOOKDATEFROM")!=null ? request.getParameter("BOOKDATEFROM") : "";
    String BOOKDATETO=request.getParameter("BOOKDATETO")!=null ? request.getParameter("BOOKDATETO") : "";
    String FREFNO = request.getParameter("FREFNO")!=null ? request.getParameter("FREFNO") : "";
    String TREFNO = request.getParameter("TREFNO")!=null ? request.getParameter("TREFNO") : "";
    String CLASS=request.getParameter("CLASS")!=null ? request.getParameter("CLASS") : "";
    ArrayList<CommonBean> dataArr = new ArrayList<CommonBean>();
    if(btnClick.equals("GO"))
    {
	dataArr = (new BudgetReportDAO()).getPeonReportData(sessionBean.getLOGINASROLEID(),sessionBean.getTENUREID(),BOOKDATEFROM,BOOKDATETO,FREFNO,TREFNO,sessionBean.getISCONF()); 
	}
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
 	String serverTime = CommonDAO.getSysDate("HH:mm:ss");
%>
<script>
function submitPage(){
if(chkblank(window.document.getElementById("BOOKDATEFROM")) && chkblank(window.document.getElementById("BOOKDATETO"))
	&& chkblank(window.document.getElementById("FREFNO")) && chkblank(window.document.getElementById("TREFNO"))){
  document.forms[0].btnClick.value="GO";
  document.forms[0].submit();
  }
}

function setToField(thisObj, TOFIELD){
	var fieldTo = window.document.getElementById(TOFIELD); 
	fieldTo.value = thisObj.value;
	fieldTo.className = 'active';
} 

$(document).ready(function ()
        {
            $.ui.dialog.defaults.bgiframe = true;
          /*  $("#BOOKDATEFROM").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true'});
            $("#BOOKDATETO").datepicker({ dateFormat: 'dd/mm/yy',showOn: 'both', buttonImage:'/MRSECTT/images/cal.gif',buttonImageOnly:'true',showButtonPanel:'true'}); */
            $("#BOOKDATEFROM").datepicker({ dateFormat: 'dd/mm/yy',showButtonPanel:'true'});
            $("#BOOKDATETO").datepicker({ dateFormat: 'dd/mm/yy',showButtonPanel:'true'});
            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
        });
</script>
<style>
/* Syle the search button. Settings of line-height, font-size, text-indent used to hide submit value in IE */

/* Style the search button hover state */
#submit:hover {
	background: url(images/icon_search12.png) no-repeat center #357AE8;
	border: 1px solid #2F5BB7;
}
</style>
</head>
<body onload="document.frm.FREFNO.focus();">
<form name="frm" action="PeonRegisterReport_RefNo.jsp" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Peon Book - Ref. No.</b></font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
					<tr>
				<td align="center"><table width="984px" border="0"  cellspacing="0"
					cellpadding="0">
					<tr>
						<td colspan="9"><br><br><center><fieldset style="width: 60%;">
								<table  border="0" align="center">
								<tbody>
								<tr align="center">
									<td align="right"><b>Ref. No.</b></td>
									<td nowrap="nowrap"><input type="text" name="FREFNO" id="FREFNO" size="10" onkeyup="setToField(this, 'TREFNO');"
										value='<%= request.getParameter("FREFNO")!=null ? request.getParameter("FREFNO"):""%>' /></td>
									<td></td>
									<td nowrap="nowrap"><input type="text" name="TREFNO" id="TREFNO" size="10"
										value='<%=request.getParameter("TREFNO")!=null ? request.getParameter("TREFNO"):""%>' /></td>
									<td align="right"></td>
									<td nowrap="nowrap"></td>
						</tr>
								<tr  align="center"><td  align="right"><b>Forward on:</b> </td>
						<td nowrap="nowrap"><input type="text" 	name="BOOKDATEFROM" id="BOOKDATEFROM" size="10" value='<%=request.getParameter("BOOKDATEFROM")!=null ? request.getParameter("BOOKDATEFROM"):serverDate%>'/></td>
				   	<td> <b>&nbsp - &nbsp</b> </td>
						<td nowrap="nowrap"><input type="text" 	name="BOOKDATETO" id="BOOKDATETO" size="10" value='<%=request.getParameter("BOOKDATETO")!=null ? request.getParameter("BOOKDATETO"):serverDate%>'/></td>
						<td   align="right"></td>
						<td nowrap="nowrap"><input type="hidden" name="btnClick"
												id="btnClick"
												value='<%=request.getParameter("btnClick")!=null ? request.getParameter("btnClick"):""%>'
												/></td></tr>
						<tr align="center">
									<td align="center" colspan="6"><input type="button" class="butts"
										value="Generate" style="height: 20px;"
										onclick="submitPage();" /></td>
								</tr>
							</tbody></table></fieldset></center>
						<%
											if ((dataArr != null) && (dataArr.size() != 0)) {
										%>
						<table align="center">
							<tr>
								<td colspan="11" align="center" width="90" bgcolor="#f5f5f5"><img width="25" height="25"
									src="images/printer_new.png"
									onclick="window.open('PopUpPrintReport.jsp?flag=y');"></img></td>
							</tr>
						</table>
						<div id="reportData">
						<table width="100%" align="center">
							<thead>
								<tr>
									<th colspan="4"><font size="3"><%= sessionBean.getTENUREOFFICENAME() %></font></th>
								</tr>
								<tr>
									<th colspan="4"></th>
								</tr>
								<tr>
									<th colspan="4"><font style="font-style: normal; font-weight: 500; line-height: normal; text-align: center"><%= sessionBean.getTENUREOFFICEADDRESS() %></font></th>
								</tr>
								<tr>
									<td colspan="4">
									<table width="100%">
										<tr>
											<td nowrap="nowrap" style="text-align: left;" width="442"><hr />Peon    Book for Date : <%=request.getParameter("BOOKDATEFROM")!=null ? request.getParameter("BOOKDATEFROM"):"" %> - <%=request.getParameter("BOOKDATETO")!=null ? request.getParameter("BOOKDATETO"):"" %><br/>
																											Reference No. : <%=request.getParameter("FREFNO")!=null ? request.getParameter("FREFNO").toUpperCase():"" %> - <%=request.getParameter("TREFNO")!=null ? request.getParameter("TREFNO").toUpperCase():"" %><hr/></td>
											<td style="text-align: right" width="330"><hr/><%= serverDate %><br /> <%= serverTime %><hr/></td>
										</tr>
									</table>
									</td>
								</tr>

								<tr>
									<th style="width: 50" align="left">S.N.</th>
									<th style="width: 150" align="left">Forward To</th>
									<th style="width: 200" align="left">Content</th>
									<th style="width: 50" align="left">Signature</th>
								</tr>
							</thead>
							<tbody id="content">
							<tr><td style="font-family: Tahoma; font-size: 10px;">
								<%
										  String lastrolename="";
										  int count=1;
										  int srno=1;
										  int filecount=1;
										  for (int i = 0; i < dataArr.size(); i++) {
										  CommonBean cmnbn = (CommonBean) dataArr.get(i);
										     if(!lastrolename.equalsIgnoreCase(cmnbn.getField2())){
										       count=1;
										       filecount=1;
												if(lastrolename.length()>0){
										   %>
								</td>
								<td style="vertical-align: top" style="font-family: Tahoma; font-size: 10px;">
								<%for(int j=0;j<5;j++) 
										   {%>&nbsp; <%} %>
								</td></tr>
								<%} %>
								<tr class="trodd" style="height: <%=cmnbn.getField4() %>px" align="left">
								<td style="vertical-align: top; font-family: Tahoma; font-size: 10px;"><hr/><%=srno++%>.</td>
									<td style="vertical-align: top; font-family: Tahoma; font-size: 10px;"><hr/><%=cmnbn.getField2()%></td>
									<td style="vertical-align: top; font-family: Tahoma; font-size: 10px;"><hr/><%=StringFormat.leftPad1(filecount++ + "",3,"&nbsp;")%>.<%=StringFormat.rightPad1(cmnbn.getField3(),18,"&nbsp")%>
									<%for(int j=((String.valueOf(filecount)).length()+(cmnbn.getField3()).length());j<25;j++) 
										   {%><%} %> <% }else{
										    if(count>=3){ count=1;%> <br><%=StringFormat.leftPad1(filecount++ + "",3,"&nbsp;")%>.<%=StringFormat.rightPad1(cmnbn.getField3(),18,"&nbsp")%>
									<%for(int j=((String.valueOf(filecount)).length()+(cmnbn.getField3()).length());j<25;j++) 
										   {%><%} %> <%}else{%> <%=StringFormat.leftPad1(filecount++ + "",3,"&nbsp;")%>.<%=StringFormat.rightPad1(cmnbn.getField3(),18,"&nbsp")%>
									<%for(int j=((String.valueOf(filecount)).length()+(cmnbn.getField3()).length());j<25;j++) 
										   {%><%} %> <% count++;}}%> <%
										  lastrolename=cmnbn.getField2(); }
										   %>
									</td>
									<td style="vertical-align: top">
									<%for(int j=0;j<5;j++) 
										   {%>&nbsp; <%} %>
									</td>
								</tr>
							</tbody>
						</table>
				</div>
						<%
										   }
										   %>
						</td>
					</tr>
				</table>
				</td>
			</tr>
						</table>
  </form> 
</body>
</html>