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
	String REQOF=request.getParameter("REQOF")!=null ? request.getParameter("REQOF") : "";
	String DEPT=request.getParameter("DEPT")!=null ? request.getParameter("DEPT") : "";
	String STATE=request.getParameter("STATE")!=null ? request.getParameter("STATE") : "";
	String REMARKS=request.getParameter("REMARKS")!=null ? request.getParameter("REMARKS") : "";
	String DREMARKS=request.getParameter("DREMARKS")!=null ? request.getParameter("DREMARKS") : "";
	String SUBJECT=request.getParameter("SUBJECT")!=null ? request.getParameter("SUBJECT") : "";
	
	String SIGNEDBY=request.getParameter("SIGNEDBY")!=null ? request.getParameter("SIGNEDBY") : "";
	String ISCONF = sessionBean.getISCONF();
	
	String tmpCond = "";
   	String serverDate="";
	int countJ = 0;   
	 serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
	 
	String queryRefClass = "SELECT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"' AND INOUT='I'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
	
	String querydept = "SELECT DISTINCT A.DEPTCODE,A.DEPTNAME FROM MSTDEPT A ORDER BY A.DEPTNAME	"; 
 	ArrayList<CommonBean> deptList = (new CommonDAO()).getSQLResult(querydept, 2);
 	
 	String queryState = "SELECT STATECODE, STATENAME FROM MSTSTATE"; 
	ArrayList<CommonBean> stateList = (new CommonDAO()).getSQLResult(queryState, 2);
 	
 	String querySignedBy = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);
 
 	String SignedBy = " SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = '"+SIGNEDBY+"'"; 
	String SignedByName = (new CommonDAO()).getStringParam(SignedBy);
	
		tmpCond += ISCONF.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
		tmpCond += CLASS.length() > 0? " AND B.REFCLASS = '"+CLASS+"'": "";
		tmpCond += DEPT.length() > 0? " AND A.MARKINGTO = '"+DEPT+"'": "";
		tmpCond += STATE.length() > 0? " AND B.STATECODE = '"+STATE+"'": "";
		tmpCond += REQOF.length() > 0? " AND REFERENCENAME LIKE '%"+REQOF+"%'": "";
		tmpCond += SUBJECT.length() > 0? " AND B.SUBJECT LIKE '%"+SUBJECT+"%'": "";
		tmpCond += DREMARKS.length() > 0? " AND B.REMARKS LIKE '%"+DREMARKS+"%'": "";
		
 		String dept =	" SELECT DISTINCT A.MARKINGTO,GETROLENAME(A.MARKINGTO) ROLENAME"+
 						" FROM TRNMARKING A,TRNREFERENCE B"+
 						" WHERE TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FREMDATE+"','DD/MM/YYYY') AND TO_DATE('"+TREMDATE+"','DD/MM/YYYY')"+
						" AND A.REFID=B.REFID AND B.TENUREID='"+sessionBean.getTENUREID()+"'"+
						" AND A.MARKINGSEQUENCE=1 AND B.FILENO IS NULL AND A.MARKINGTO IS NOT NULL "+tmpCond+" ORDER BY ROLENAME";
	ArrayList<CommonBean> deptNameList = (new CommonDAO()).getSQLResult(dept, 2);
	//System.out.println("dept:::" + dept);
	String olddept="";
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
	
	function printme(obj)
{	
			obj.style.display="none";
			window.print();
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
<form name="frm" action="" method="post">

		<table style="width: 690px" border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
				<td><table width="100%" border="0"  cellspacing="0"
					cellpadding="0" style="border-collapse: collapse; margin-top: 1px;">
					<tr>
						<td colspan="8" valign="top">
						
						
						<%
											if ((deptNameList != null) && (deptNameList.size() != 0)) {
										%>
						<table align="center">
							<tr>
								<td colspan="11" width="24"><img width="25" height="25"
									src="images/printer_new.png"
									onclick="printme(this);" ></img></td>
							</tr>
						</table>
						<%for (int i = 0; i < deptNameList.size(); i++) {
						
								CommonBean cb = (CommonBean) deptNameList.get(i);
										  if(!cb.getField1().equalsIgnoreCase(olddept)){ %>
										 
						<div id="reportData" align="left">
						<table width="100%"  align="center">
							
								<tr align="center">
									<td colspan="5"><FONT size="4"><b><%= sessionBean.getTENUREOFFICENAME() %></b></FONT></td>
								</tr>
								
								<tr align="center">
									<td colspan="5"><FONT size="2"><%= sessionBean.getTENUREOFFICEADDRESS() %></FONT></td>
								</tr>
															
								
								<tr>
									<td colspan="5"><br /><br />
									<table align="center" width="100%">
										<tr>
											<td colspan="3"> <b><u><%=cb.getField2() %></u></b></td>
											<td colspan="2" align="right"><%=serverDate %>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
								
									<td colspan="5">
									<table align="center">
										<tr>
											<td nowrap="nowrap" style="text-align: left;">LIST OF
											PENDING CASE( s )</td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							<table width="100%"  align="center" style="page-break-after: always;display: table-header-group;">
							<thead  style="display: table-header-group;">	
								<tr>
									<td class="bototmDashedth"><b><nobr>SNo.</nobr>&nbsp;&nbsp;</b></td>
									<td class="bototmDashedth"><b><nobr>Reference No.</nobr>&nbsp;&nbsp;</b></td>
									<td class="bototmDashedth"><b><nobr>Reference from
									&amp; content</nobr>&nbsp;&nbsp;</b></td>
									<td class="bototmDashedth"><b><nobr>Target Date</nobr>&nbsp;&nbsp;</b></td>
									<td class="bototmDashedth"><b><nobr>Mark [&#10004;] &amp; fill up the information</nobr></b></td>
								</tr>
							</thead>
							<tbody id="content" >
								<%   ArrayList<TrnReminder> dataArr = new ArrayList<TrnReminder>();
    								 dataArr =  (new ReminderAutoDAO()).getReminderReportData(sessionBean.getLOGINASROLEID(),sessionBean.getTENUREID(),FREMDATE,TREMDATE,CLASS,cb.getField1(),STATE,REQOF,ISCONF,SUBJECT,DREMARKS);
									for (int j = 0; j < dataArr.size(); j++) {
										  TrnReminder rb = (TrnReminder) dataArr.get(j);%>
									<tr class="trodd" style="page-break-inside:avoid;page-break-after: auto;">
									<td style="vertical-align: top;page-break-inside:avoid;page-break-after: auto;" class="bototmDashed"><%= j+1 %></td>
									<td style="vertical-align: top;page-break-inside:avoid;page-break-after: auto;" class="bototmDashed"><%=rb.getREFNO() %><b>&nbsp;&nbsp;</b><br><%=rb.getINCOMINGDATE() %><b>&nbsp;&nbsp;</b></td>
									<td style="vertical-align: top;page-break-inside:avoid;page-break-after: auto;" class="bototmDashed"><%=rb.getREFERENCENAME() %>,<%=rb.getVIPSTATUS() %>,<%=rb.getSTATECODE() %><b>&nbsp;&nbsp;</b><br/>[<%=rb.getSUBJECT() %>]<b>&nbsp;&nbsp;</b></td>
									<td style="vertical-align: top;page-break-inside:avoid;page-break-after: auto;" class="bototmDashed"><%=rb.getTARGETDATE() %>&nbsp;&nbsp;
									<br /><br />Reply burst by <%= rb.getTARDAYS() %> day(s)
									</td>
									<td style="vertical-align: top;page-break-inside:avoid;page-break-after: auto;" class="bototmDashed"><NOBR>BRANCH:................................ FILE NO.:.............................................</NOBR><br /><NOBR>[&nbsp;&nbsp;]
									PUT UP TO ............................ ON .................. [&nbsp;&nbsp;] UNDER PROCESS</NOBR><br /><NOBR>[&nbsp;&nbsp;] TRANSFERRED TO ........................ ON ............. [&nbsp;&nbsp;] NOT RECEIVED</NOBR><br /><NOBR>[&nbsp;&nbsp;] REPLY AWAITED FROM RLY. [&nbsp;&nbsp;] REPLY ISSUED BY .......... ON .........</NOBR></td>
									</tr>
								<%}%>
							</tbody>
						</table>
						</div>
								<%}
								olddept= cb.getField1();
								}}else{%><center><font color="red" face="Tahoma" style="font-size: 20px; font: Tahoma; font-style: italic;">No data found...</font></center><%} %>
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