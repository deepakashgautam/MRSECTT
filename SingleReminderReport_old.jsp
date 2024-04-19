<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnReminder"%>
<%@page import="in.org.cris.mrsectt.dao.ReminderAutoDAO"%>
<%@page import="in.org.cris.mrsectt.dbConnection.DBConnection"%>
<%@page import="java.sql.ResultSet"%>
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
    String buttonClick = request.getParameter("buttonClick")!=null ? request.getParameter("buttonClick") : "";
    String INDATE_DRPDWN=request.getParameter("INDATE_DRPDWN")!=null ? request.getParameter("INDATE_DRPDWN") : "";
	String REMARKS=request.getParameter("REMARKS")!=null ? request.getParameter("REMARKS") : "";
	String SIGNEDBY=request.getParameter("SIGNEDBY")!=null ? request.getParameter("SIGNEDBY") : "";
	String serverDate="";
	String refID="";
	serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
	 
 	String SignedBy = " SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = '"+SIGNEDBY+"'"; 
	String SignedByName = (new CommonDAO()).getStringParam(SignedBy);
	
	String refNo = "";
	String inDate = "";
	if (INDATE_DRPDWN.trim().length() > 0) {
		String[] arrRefNo = INDATE_DRPDWN.split("   ");
		refNo = arrRefNo[0];
		inDate = arrRefNo[1];
	}		
 	String refIdSQL = " SELECT REFID FROM TRNREFERENCE WHERE REFNO = '"+refNo+"' AND TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = '"+inDate+"' ";
 	DBConnection dbCon = new DBConnection();
 	dbCon.openConnection();
 	ResultSet rs = dbCon.select(refIdSQL);
 	if(rs.next()){ refID = StringFormat.nullString(rs.getString("REFID")); }
%>
<script>

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
	
function msgClose() {
	alert('To patch this reminder click "Patch(Paper Single)" button.');
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

function printme(obj)
{	
			obj.style.display="none";
			window.print();
}

</style>
</head>

<body onload="alert('To patch this reminder click Patch(Paper Single) button.');">
<form name="frm" action="" method="post">
	<%  ArrayList<TrnReminder> dataArr = new ArrayList<TrnReminder>();
 		dataArr =  (new ReminderAutoDAO()).getSingleReminderReportData(refID);
		TrnReminder rb = (TrnReminder) dataArr.get(0);
		if(rb.getFILESTATUS1().length()==0 && rb.getFILESTATUS2().length()==0){ %>
		<table width="100% border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
				<td><table width="100%" border="0"  cellspacing="0" cellpadding="0" style="border-collapse: collapse; margin-top: 1px;">
					<tr>
						<td colspan="8">
						<table align="center">
							<tr>
								<td colspan="11"><img width="20" height="20" src="images/printer_new.png" onclick="printme(this);"></img>
								</td>
							</tr>
						</table>
						<div id="reportData" align="left">
						<table width="80%" class="tablesorter" id="sorttable" align="center" style="margin-left: 60px">
							<thead>
							
							<tr align="center">
									<td colspan="5"><FONT size="3"><b><%= sessionBean.getTENUREOFFICENAME() %></b></FONT></td>
								</tr>
								<tr align="center">
									<td colspan="5"><FONT size="2"><%= sessionBean.getTENUREOFFICEADDRESS() %></FONT></td>
								</tr>
				
								<tr align="center">
									<td colspan="5" style="height: 5px;" align="right"><br />
									<br />
									&nbsp;<font><u><b><%= REMARKS %></b></u></font></td>
								</tr>
								<tr>
									<td colspan="5">
									<p style="text-align: justify;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Given below are the details of the case sent by this office bearing target date <%= rb.getTARGETDATE() %>, which is pending.</p>

									<p style="text-align: justify;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You
									are requested to expedite the action and submit the
									file immediately for the kind perusal of Hon’ble MR.</p>
									</td>
								</tr>
								<tr>
									<td colspan="5">
									
									</td>
								</tr>
								<tr>
								
									<td colspan="5">
									<table align="center">
										<tr>
											<td nowrap="nowrap" style="text-align: left;"><b>DETAILS OF
											PENDING CASE</b></td>
										</tr>
									</table>
									</td>
								</tr>
								
								<tr onclick="resetSrNo();">
									<td class="bototmDashed" colspan="4"></td>
								</tr>
							</thead>
							<tbody id="content">
								
								<tr  >
									<td style="vertical-align: top" class="bototmDashed" width="198">Ref.
									No. :</td>
									<td style="vertical-align: top" class="bototmDashed" width="794"><%= rb.getREFNO() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= rb.getINCOMINGDATE() %> <br /> &nbsp;(&nbsp;Ref. Dated: <%= rb.getLETTERDATE() %>&nbsp;)
									</td>
								</tr>
								<tr  >
									<td style="vertical-align: top" class="bototmDashed" width="198">Received From :</td>
									<td style="vertical-align: top" class="bototmDashed" width="794"><%= rb.getREFERENCENAME() %>,&nbsp;&nbsp;<%= rb.getVIPSTATUS() %>,&nbsp;&nbsp;<%= rb.getSTATECODE() %> </td>
								</tr>
								<tr  >
									<td style="vertical-align: top" class="bototmDashed" width="198">Subject :</td>
									<td style="vertical-align: top" class="bototmDashed" width="794"><%= rb.getSUBJECT() %></td>
								</tr>
								<tr  >
									<td style="vertical-align: top" class="bototmDashed" colspan="2">
<%
if(rb.getREMARKS().length() > 1) { %>
	<%= "Remarks :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+rb.getREMARKS()+"&nbsp;&nbsp;&nbsp;"+rb.getSIGNEDBY()+"&nbsp;&nbsp;&nbsp;"+rb.getSIGNEDON() %>
<%}else if(rb.getSIGNEDBY().length() > 1) { %>
	<%= "Signed By :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+rb.getSIGNEDBY()+"&nbsp;&nbsp;&nbsp;"+rb.getSIGNEDON()%>
<%}else {%>
	<%= " " %>
<%} %>&nbsp;
			</td></tr>
							</tbody>
						</table>
					<%  ArrayList<TrnReminder> reminderArr = new ArrayList<TrnReminder>();
    					reminderArr =  (new ReminderAutoDAO()).getReminderData(refID);
    					if(reminderArr.size()>0) { %>
					<table width="80%" align="center" style="margin-left: 60px">
						<tbody>
					<tr>
						<td style="vertical-align: top" class="bototmDashed" width="200" colspan="6" align="center"><b>PREVIOUS REMINDER(s)</b></td>
					</tr>
					<tr>
						<td style="vertical-align: top" class="bototmDashed" width="200"><b>S.No.</b></td>
						<td style="vertical-align: top" class="bototmDashed" width="910"><b>Reminder Type</b></td>
						<td style="vertical-align: top" class="bototmDashed" width="800"><b>Reminder Dt.</b></td>
						<td style="vertical-align: top" class="bototmDashed" width="910"><b>Adjective</b></td>
						<td style="vertical-align: top" class="bototmDashed" width="910"><b>Signed By</b></td>
						<td style="vertical-align: top" class="bototmDashed" width="800"><b>Signed On</b></td>
					</tr>
					<%	for (int j = 0; j < reminderArr.size(); j++) {
							TrnReminder reminderBean = (TrnReminder) reminderArr.get(j); %>
					<tr>
						<td style="vertical-align: top" class="bototmDashed" width="200"><b><%= (j+1)+"." %></b></td>
						<td style="vertical-align: top" class="bototmDashed" width="910"><%=reminderBean.getREMINDERTYPE() %></td>
						<td style="vertical-align: top" class="bototmDashed" width="800"><%=reminderBean.getREMINDERDATE() %></td>
						<td style="vertical-align: top" class="bototmDashed" width="910"><%=reminderBean.getREMINDERREMARK() %></td>
						<td style="vertical-align: top" class="bototmDashed" width="910"><%=reminderBean.getSIGNEDBY() %></td>
						<td style="vertical-align: top" class="bototmDashed" width="800"><%=reminderBean.getSIGNEDON() %></td>
					</tr>
					<%} %>
							</tbody>
						</table> <%} %><br><br><br><br>
						<table align="center" width="80%" style="margin-left: 60px">
										<tr>
											<td colspan="1" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td colspan="2" width="720"></td>
											<td colspan="1" align="right" width="26"><b><%=SignedByName%></b><br /><%=serverDate %>
											</td>
											<td colspan="1" align="right" width="46"></td>
										</tr>
							<tr>
								<td align="left"><b><u><%= rb.getMARKINGTO() %></u></b></td>
								<td width="720" colspan="2"></td>
								<td align="left" width="26"></td>
								<td align="right" width="46"></td>
							</tr>
							<tr>
								<td align="left"><br><br></td>
								<td width="720" colspan="2"></td>
								<td align="left" width="26"></td>
								<td align="right" width="46"></td>
							</tr>
						</table>
						</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
						</table>
						</td>
					</tr>
				</table> <%} else { %>
	<table width="100% border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
				<td><table width="100%" border="0"  cellspacing="0" cellpadding="0" style="border-collapse: collapse; margin-top: 1px;">
					<tr>
						<td colspan="8">
						<table align="center">
							<tr>
								<td colspan="11"><img width="20" height="20" src="images/printer_new.png" onclick="window.print();"></img>
								</td>
							</tr>
						</table>
						<div id="reportData" align="left">
						<table width="100%" class="tablesorter" id="sorttable" align="center" >
							<thead>
							
							
								<tr align="center">
									<td colspan="5"><FONT size="3"><b><%= sessionBean.getTENUREOFFICENAME() %></b></FONT></td>
								</tr>
								<tr align="center">
									<td colspan="5"><FONT size="2"><%= sessionBean.getTENUREOFFICEADDRESS() %></FONT></td>
								</tr>
								<tr align="center">
									<td colspan="5" style="height: 5px;" align="right"><br />
									<br />
									<font><%= REMARKS %></font></td>
								</tr>
								<tr>
									<td colspan="5">
									<p style="text-align: justify;">&nbsp;&nbsp;&nbsp;&nbsp;
									Given below are the details of case sent by this office dealt with File No : <%= rb.getFILENO() %>.</p>

									<p style="text-align: justify;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You
									are requested to furnish the current status of the file for MR's perusal.</p>
									</td>
								</tr>
								<tr>
									<td colspan="5">
									
									</td>
								</tr>
								<tr>
								
									<td colspan="5">
									<table align="center">
										<tr>
											<td nowrap="nowrap" style="text-align: left;"><b>DETAILS OF
											FILE</b></td>
										</tr>
									</table>
									</td>
								</tr>
								
								<tr onclick="resetSrNo();">
									<td class="bototmDashed" colspan="4"></td>
								</tr>
							</thead>
							<tbody id="content">
								
								<tr>
									<td style="vertical-align: top" class="bototmDashed" width="116">File No</td>
									<td style="vertical-align: top" class="bototmDashed" width="1085">: <%= rb.getFILENO() %></td>
								</tr>
								<tr  >
									<td style="vertical-align: top" class="bototmDashed" width="116">Diary
									No</td>
									<td style="vertical-align: top" class="bototmDashed" width="1085">:&nbsp;<%= rb.getREFNO() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= rb.getINCOMINGDATE() %><br /> &nbsp;&nbsp;(&nbsp;Ref. Dated: <%= rb.getLETTERDATE() %>&nbsp;)</td>
								</tr>
								<tr  >
									<td style="vertical-align: top" class="bototmDashed" width="116">Ref.
									From</td>
									<td style="vertical-align: top" class="bototmDashed" width="1085">:&nbsp;<%= rb.getREFERENCENAME() %>,&nbsp;&nbsp;<%= rb.getVIPSTATUS() %>,&nbsp;&nbsp;<%= rb.getSTATECODE() %> </td>
								</tr>
								<tr  >
									<td style="vertical-align: top" class="bototmDashed" width="116">Contents</td>
									<td style="vertical-align: top" class="bototmDashed" width="1085">:&nbsp;<%= rb.getSUBJECT() %></td>
								</tr>
								<tr  >
									<td style="vertical-align: top" class="bototmDashed"
										width="116" colspan="3">
								<%
									if(rb.getREMARKS().length() > 1) { %> <%= "Remarks &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;"+rb.getREMARKS()+"&nbsp;&nbsp;&nbsp;"+rb.getSIGNEDBY()+"&nbsp;&nbsp;&nbsp;"+rb.getSIGNEDON() %>
									<%}else if(rb.getSIGNEDBY().length() > 1) { %> <%= "Signed By&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;"+rb.getSIGNEDBY()+"&nbsp;&nbsp;&nbsp;"+rb.getSIGNEDON()%>
									<%}else {%> <%= " " %> <%} %>&nbsp;</td>
								</tr>
								<tr>
									<td style="vertical-align: top" class="bototmDashed" width="116"><b></b>File Status 1</td>
									<td style="vertical-align: top" class="bototmDashed" width="1085">: <%= rb.getFILESTATUS1()%></td>
								</tr>
								<tr>
									<td style="vertical-align: top" class="bototmDashed" width="116"><b></b>File Status 2</td>
									<td style="vertical-align: top" class="bototmDashed" width="1085">: <%= rb.getFILESTATUS2()%></td>
								</tr>
								
								<tr >
									<td style="vertical-align: top" class="bototmDashed"  colspan="2">
									</td>
								</tr>
							</tbody>
						</table>
					<br><br><br><br>
						<table align="center" width="80%">
										<tr>
											<td colspan="1" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td colspan="2" width="720"></td>
											<td colspan="1" align="left" width="26"><b><%=SignedByName%></b><br /><%=serverDate %>
											</td>
											<td colspan="1" align="right" width="46"></td>
										</tr>
							<tr>
								<td align="left"><b><u><%= rb.getMARKINGTO() %></u></b></td>
								<td width="720" colspan="2"></td>
								<td align="left" width="26"></td>
								<td align="right" width="46"></td>
							</tr>
							<tr>
								<td align="left"><br><br></td>
								<td width="720" colspan="2"></td>
								<td align="left" width="26"></td>
								<td align="right" width="46"></td>
							</tr>
						</table>
						</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
						</table>
						</td>
					</tr>
				</table><%} %>
				
				
  </form> 
</body>
</html>