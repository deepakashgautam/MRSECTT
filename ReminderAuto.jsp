<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnReminder"%>
<%@page import="in.org.cris.mrsectt.dao.ReminderAutoDAO"%><html> 
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
    String FREMDATE=request.getParameter("FREMDATE")!=null ? request.getParameter("FREMDATE") : "";
    String TREMDATE=request.getParameter("TREMDATE")!=null ? request.getParameter("TREMDATE") : "";
	String CLASS=request.getParameter("CLASS")!=null ? request.getParameter("CLASS") : "";
	String DEPT=request.getParameter("DEPT")!=null ? request.getParameter("DEPT") : "";
	String REQOF=request.getParameter("REQOF")!=null ? request.getParameter("REQOF") : "";
	String STATE=request.getParameter("STATE")!=null ? request.getParameter("STATE") : "";
	String REMARKS=request.getParameter("REMARKS")!=null ? request.getParameter("REMARKS") : "";
	String SIGNEDBY=request.getParameter("SIGNEDBY")!=null ? request.getParameter("SIGNEDBY") : "";
	String SUBJECT=request.getParameter("SUBJECT")!=null ? request.getParameter("SUBJECT") : "";
	//String REMARKS=request.getParameter("REMARKS")!=null ? request.getParameter("REMARKS") : "";
	
   	String ISCONF = sessionBean.getISCONF();
	String tmpCond = ISCONF.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
   String serverDate="";
   
    ArrayList<TrnReminder> dataArr = new ArrayList<TrnReminder>();
    
    dataArr =  (new ReminderAutoDAO()).getReminderReportData(sessionBean.getLOGINASROLEID(),sessionBean.getTENUREID(),FREMDATE,TREMDATE,CLASS,DEPT,STATE,REQOF,ISCONF,SUBJECT,REMARKS);
  	
   
	 serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
	 
	String queryRefClass = "SELECT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"' AND INOUT='I'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
	
	String querydept = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2";  
 	ArrayList<CommonBean> deptList = (new CommonDAO()).getSQLResult(querydept, 2);
 	
 	String queryState = "SELECT STATECODE, STATENAME FROM MSTSTATE"; 
	ArrayList<CommonBean> stateList = (new CommonDAO()).getSQLResult(queryState, 2);
 	
 	String querySignedBy = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);
 
%>
<script>




function submitPage(){
if(chkblank(window.document.getElementById("REMDATE"))){
  document.forms[0].btnClick.value="GO";
  document.forms[0].submit();
  }
}

  

	$(document).ready(function() { 
	    $("#sorttable").tablesorter({ 
	        // pass the headers argument and assing a object 
	        headers: { 
	            // assign the tenth column (we start counting zero) 
	          
	            0: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }
	             
	        }
	    }); 
	    
	});
	
	
		
	$(function(){
		$("#sorttable").live("sortEnd", function(){
	  		resetSrNo();
		});
	
	
            $("#BOOKDATE").datepicker({ dateFormat: 'dd/mm/yy',showButtonPanel:'true'});
	
	
	});
	
	
	//$(document).ready(function() {
    //	$("#sorttable").tableFilter();
	//});
	

	$(document).ready(function() {     // call the tablesorter plugin     
		$("table").tablesorter({         // change the multi sort key from the default shift to alt button         
			sortMultiSortKey: 'altKey'     
		});
	 }); 	
	
	function resetSrNo(){
	
		var content = window.document.getElementById("content");
		//alert(' content length ' + content.rows.length );
		
		var counter = 0;
		for(var i=0;i<content.rows.length ;i++){
			
			
			//alert(content.childNodes[i].style.display);
			
			if(content.childNodes[i].style.display!='none'){
				counter++;
			}
			
			content.childNodes[i].firstChild.innerText = (counter)+'.';
		}
		
		//alert(content.childNodes[1].childNodes[0]);
		//alert(content.childNodes[1].firstChild.innerText);
		//alert(content.childNodes[6].firstChild.innerText);
		
	
	}
	
	
	
function popupReminder(obj)
{
	var FREMDATE = document.frm.FREMDATE.value;
	var TREMDATE = document.frm.TREMDATE.value;
	var CLASS = document.frm.CLASS.value;
	var DEPT = document.frm.DEPT.value;
	var STATE = document.frm.STATE.value;
	var REMARKS = document.frm.REMARKS.value;
	var SIGNEDBY = document.frm.SIGNEDBY.value;
			
	
	
			
if(chkblank(document.frm.FREMDATE) && chkblank(document.frm.TREMDATE))
		{
	var url="ReminderReport.jsp?FREMDATE="+FREMDATE+"&TREMDATE="+TREMDATE+"&CLASS="+CLASS+"&DEPT="+DEPT+"&STATE="+STATE+"&REMARKS="+REMARKS+"&SIGNEDBY="+SIGNEDBY;
	
	 	 var width  = 920;
		 var height = 600;
		 var left   = (screen.width  - width)/2;
		 var top    = (screen.height - height)/2;
		 var params = 'width='+width+', height='+height;
		 params += ', top=20, left='+left;
		 params += ', directories=no';
		 params += ', location=no';
		 params += ', menubar=no';
		 params += ', resizable=no';
		 params += ', scrollbars=yes';
		 params += ', status=no';
		 params += ', toolbar=no';		

		var win = window.open(url ,"popupReminder",params);
		win.focus();
		}

}
	
</script>

<style>

table.tablesorter thead tr .header {
	background-image: url(images/bg.gif);
	background-repeat: no-repeat;
	background-position: center right;
	cursor: pointer;
}

table.tablesorter thead tr .headerSortUp {
	background-image: url(images/asc.gif);
}

table.tablesorter thead tr .headerSortDown {
	background-image: url(images/desc.gif);
}

/* Syle the search button. Settings of line-height, font-size, text-indent used to hide submit value in IE */
#submit {
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
#submit:hover {
	background: url(images/icon_search12.png) no-repeat center #357AE8;
	border: 1px solid #2F5BB7;
}

</style>
</head>

<body>
<form name="frm" action="PeonRegisterReport.jsp" method="post">

		<table width="984" border="0" align="left" cellpadding="0" cellspacing="0">
			<tr><td class="treven" valign="top"> 
      		<font size="2" > 
      			<b>Pending Cases- Reference</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    


           
        </td>
        </tr>
        <tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
				<td align="center"><table width="984px" border="0"  cellspacing="0"
					cellpadding="0" style="border-collapse: collapse; margin-top: 1px;">
					<tr>
						<td colspan="8">
						<fieldset>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="trodd">
								
								<table border="0">
									
								<tbody><tr align="center"><td align="left">Class &nbsp; &nbsp;</td><td nowrap="nowrap"><select style="width: 120px;height: 50px" multiple="multiple" name="CLASS" id="CLASS" tabindex="1">
								<option value="" selected="selected">ALL</option>
								<%
										for(int i=0;i<refClassList.size();i++){
										CommonBean beanCommon=(CommonBean) refClassList.get(i);
										%>
								<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(CLASS)?"selected":""%>><%=beanCommon.getField1()%></option>
								<%
									}%>
							</select><input type="hidden" name="btnClick" id="btnClick" value='<%=request.getParameter("btnClick")!=null ? request.getParameter("btnClick"):""%>' /></td>
											<td nowrap="nowrap">&nbsp; &nbsp;</td>
											<td align="left">Forward To &nbsp; &nbsp;</td>
							<td nowrap="nowrap" align="left"><select style="width: 120px;height: 50px" multiple="multiple"" name="DEPT" id="DEPT" tabindex="1">
								<option value="" selected="selected">ALL</option>
								<%
										for(int i=0;i<deptList.size();i++){
										CommonBean beanCommon=(CommonBean) deptList.get(i);
										%>
								<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(DEPT)?"selected":""%>><%=beanCommon.getField2()%></option>
								<%
									}%>
							</select></td></tr>
										<tr align="center">
											<td align="left">Reqest Of &nbsp; &nbsp;</td>
											<td nowrap="nowrap" align="left"><input type="text" name="REQOF"
												id="REQOF" 
												value='<%=request.getParameter("REQOF")!=null ? request.getParameter("REQOF"):""%>' size="25"/></td>
											<td nowrap="nowrap" align="left">&nbsp; &nbsp;</td>
											<td align="left">State &nbsp; &nbsp;</td>
											<td nowrap="nowrap" align="left"><select style="width: 120px"
												name="STATE" id="STATE" tabindex="1">
												<option value="" selected="selected">ALL</option>
												<%
										for(int i=0;i<stateList.size();i++){
										CommonBean beanCommon=(CommonBean) stateList.get(i);
										%>
												<option value="<%=beanCommon.getField1()%>"
													<%=beanCommon.getField1().equalsIgnoreCase(STATE)?"selected":""%>><%=beanCommon.getField2()%></option>
												<%
									}%>
											</select></td>
										</tr>
										<tr align="center">
											<td align="left">Target From&nbsp; &nbsp;</td><td nowrap="nowrap" align="left"><input type="text" name="FREMDATE" id="FREMDATE" value='<%=request.getParameter("FREMDATE")!=null ? request.getParameter("FREMDATE"):sessionBean.getTENURESTARTDATE()%>' size="25"/> </td>
											<td nowrap="nowrap" align="left">&nbsp; &nbsp;</td>

											<td align="left">Target To  &nbsp; &nbsp;</td><td nowrap="nowrap" align="left"><input type="text" name="TREMDATE" id="TREMDATE" value='<%=request.getParameter("TREMDATE")!=null ? request.getParameter("TREMDATE"):serverDate%>' size="25"/> </td>
											
										</tr>
										
										<tr align="center">
											<td align="left">Remarks &nbsp; &nbsp;</td>
											<td nowrap="nowrap" colspan="4" align="left"><input
												type="text" name="REMARKS" id="REMARKS"
												value='<%=request.getParameter("REMARKS")!=null ? request.getParameter("REMARKS"):""%>'
												size="75" /></td>
										</tr>
										<tr align="center">
											<td align="left">Signed By&nbsp; &nbsp;</td>
											<td nowrap="nowrap" colspan="3" align="left"><select
												style="width: 120px" name="SIGNEDBY" id="SIGNEDBY"
												tabindex="1">
												<option value="" selected="selected">-Select-</option>
												<%
										for(int i=0;i<querySignedByList.size();i++){
										CommonBean beanCommon=(CommonBean) querySignedByList.get(i);
										%>
												<option value="<%=beanCommon.getField1()%>"
													<%=beanCommon.getField1().equalsIgnoreCase(STATE)?"selected":""%>><%=beanCommon.getField2()%></option>
												<%
									}%>
											</select></td>
											<td nowrap="nowrap"><input type="button"
												value="Generate Report"
												style="background: #4d90fe; height: 21px; color: white; font-weight: bold"
												onclick="popupReminder(this);" /></td>
										</tr>
									</tbody></table>
								
							</tr>
						</table></fieldset>
						<%
											if ((dataArr != null) && (dataArr.size() != 0)) {
										%>
						<table align="center">
							<tr>
								<th colspan="11"><img width="20" height="20"
									src="images/printer_new.png"
									onclick="window.open('PopUpPrintReport.jsp?flag=y');"></img></th>
							</tr>
						</table>
						<div id="reportData">
						<table width="80%" class="tablesorter" id="sorttable"
							align="center">
							<thead>
								<tr>
									<th colspan="5"><FONT><%= sessionBean.getTENUREOFFICENAME() %></FONT></th>
								</tr>
								<tr>
									<th colspan="5"></th>
								</tr>
								<tr>
									<th colspan="5"><FONT
										style="font-style: normal; font-weight: 500; line-height: normal; text-align: center"><%= sessionBean.getTENUREOFFICEADDRESS() %></FONT></th>
								</tr>
								<tr>
									<td colspan="5">
									<table>
										<tr>
											<td nowrap="nowrap" style="text-align: left;" width="442">REMINDER
											DT:<%=request.getParameter("FREMDATE")!=null ? request.getParameter("FREMDATE"):"" %></td>
											<td style="text-align: right" width="330">PRINT DATE : <%=serverDate %></td>
										</tr>
									</table>
									</td>
								</tr>

								<tr onclick="resetSrNo();">
									<th style="width: 150">Sr.No.</th>
									<th style="width: 150">Ref. No.</th>
									<th style="width: 150">Forward To</th>
									<th style="width: 200">Ref. from &amp; Subject</th>
									<th style="width: 125">Target Dt.</th>
								</tr>
							</thead>
							<tbody id="content">
								<%
										  for (int i = 0; i < dataArr.size(); i++) {
										  TrnReminder rb = (TrnReminder) dataArr.get(i);
										     %>
									<tr class="trodd" >
									<td style="vertical-align: top" align="center"><input type="checkbox"
										name="SELREM" value="1" /></td>
									<td style="vertical-align: top" align="center"></td>
									<td style="vertical-align: top"></td>
									<td style="vertical-align: top"></td>
									<td style="vertical-align: top"></td>
								</tr>
								<%} %>
							</tbody>
						</table>
						</div>
								<%}%>
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