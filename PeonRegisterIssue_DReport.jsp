<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="in.org.cris.mrsectt.dao.BudgetReportDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="enc" uri="/WEB-INF/encrypt.tld"%>
<html> 

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
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<c:out value="<%=theme%>"/>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
  
  
<%
  
    //ArrayList<CommonBean> dataArr=new ArrayList();	
    MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
    
  	
    String btnClick = request.getParameter("btnClick")!=null ? request.getParameter("btnClick") : "";
    String BOOKDATEFROM=request.getParameter("BOOKDATEFROM")!=null ? request.getParameter("BOOKDATEFROM") : "";
    String BOOKDATETO=request.getParameter("BOOKDATETO")!=null ? request.getParameter("BOOKDATETO") : "";
    String CLASS=request.getParameter("CLASS")!=null ? request.getParameter("CLASS") : "";
    String ACKBY=request.getParameter("ACKBY")!=null ? request.getParameter("ACKBY") : "";
    ArrayList<CommonBean> dataArr = new ArrayList<CommonBean>();
    if(btnClick.equals("GO"))
    {
	dataArr = (new BudgetReportDAO()).getPeonReportData_IssueD(sessionBean.getLOGINASROLEID(),sessionBean.getTENUREID(),BOOKDATEFROM,BOOKDATETO,CLASS,sessionBean.getISCONF(),ACKBY); 
	
	}
	 String serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
	 String serverTime = CommonDAO.getSysDate("HH:mm:ss"); 
	
	 String queryRefClass = "SELECT DISTINCT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"' AND INOUT='I'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
	
	String queryAckBy = " SELECT TO_NUMBER(B.PREFERREDID) ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
			   " FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY ROLENAME";  
    ArrayList<CommonBean> queryAckByList = (new CommonDAO()).getSQLResult(queryAckBy, 2);
%>
<script>

function popUpPrintReport(param){
	var	url="PopUpPrintReport.jsp?flagecr="+param;
	//alert(url);
	window.open(url);
}


function submitPage(){
if(chkblank(window.document.getElementById("BOOKDATEFROM")) && chkblank(window.document.getElementById("BOOKDATETO"))){
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
	
	
            $("#BOOKDATEFROM").datepicker({ dateFormat: 'dd/mm/yy',showButtonPanel:'true'});
            $("#BOOKDATETO").datepicker({ dateFormat: 'dd/mm/yy',showButtonPanel:'true'});
	
	
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
<form name="frm" action="PeonRegisterIssue_DReport.jsp" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Issue/Dispatch</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
				<td align="center"><table width="984px" border="0"  cellspacing="0"
					cellpadding="0" style="border-collapse: collapse; margin-top: 1px;">
					<tr>
						<td colspan="9">
						<br><br><center><fieldset style="width: 60%;">		
								<table align="center" border="0" width="500">
	
								<tbody><tr  align="center"><td  align="right"><b>Ack.On:</b>&nbsp;</td>
								<td nowrap="nowrap" align="left"><input type="text" name="BOOKDATEFROM" id="BOOKDATEFROM" value="<c:out value='<%=request.getParameter("BOOKDATEFROM")!=null ? request.getParameter("BOOKDATEFROM"):serverDate%>'/>"/></td>
									<td> <b>&nbsp - &nbsp</b> </td>
								<td nowrap="nowrap" align="left"><input type="text" name="BOOKDATETO" id="BOOKDATETO" value="<c:out value='<%=request.getParameter("BOOKDATETO")!=null ? request.getParameter("BOOKDATETO"):serverDate%>'/>"/></td>
								<td   align="right">&nbsp;&nbsp;&nbsp;<b>Ref.Class:</b> </td><td nowrap="nowrap" align="left"><select style="width: 120px"  name="CLASS" id="CLASS"  tabindex="1" >
								<option value="" selected>ALL</option>
								<%
										for(int i=0;i<refClassList.size();i++){
										CommonBean beanCommon=(CommonBean) refClassList.get(i);
										%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(CLASS)?"selected":""%>'/>> <c:out value='<%=beanCommon.getField1()%>'/></option>
								<%
									}%>
							</select><input type="hidden" name="btnClick"
												id="btnClick"
												value="<c:out value='<%=request.getParameter("btnClick")!=null ? request.getParameter("btnClick"):""%>'/>"
												/></td>
											
												</tr><tr>
													<td><b>ACK By:</b></td>
												<td nowrap="nowrap" align="left"><select style="width: 120px"  name="ACKBY" id="ACKBY"  tabindex="1" >
								<option value="">ALL </option>
								<%
									for(int k=0;k<queryAckByList.size();k++){
									CommonBean beanCommon = (CommonBean) queryAckByList.get(k);
									%>
										<option value="<c:out value='<%=beanCommon.getField1()%>'/>" <c:out value='<%=("430".equalsIgnoreCase(beanCommon.getField1())&& ACKBY.isEmpty() ?"selected":"" ) %>'/> <c:out value='<%=beanCommon.getField1().equalsIgnoreCase(ACKBY)?"selected":""%>'/> ><c:out value='<%=beanCommon.getField2()%>'/></option>
									<%
								}%>
							</select></td></tr>
						<tr align="center">
							<td align="center" colspan="6"><input type="button" class="butts"
								value="Generate" style="height: 20px;" onclick="submitPage();" /></td>
						</tr>
					</tbody></table></fieldset></center>
						<%
											if ((dataArr != null) && (dataArr.size() != 0)) {
										%>
						<table align="center">
							<tr>
								<td colspan="11" align="center" width="90" bgcolor="#f5f5f5"><img width="25" height="25"
									src="images/printer_new.png"
									onclick="popUpPrintReport('<c:out value="${enc:encrypt('y')}" />');"></img></td>
							</tr>
						</table>
						<div id="reportData">
						<table width="80%" class="tablesorter" id="sorttable"
							align="center">
							<thead>
								<tr>
									<th colspan="4"><FONT size="3"><c:out value='<%= sessionBean.getTENUREOFFICENAME() %>'/></FONT></th>
								</tr>
								<tr>
									<th colspan="4"></th>
								</tr>
								<tr>
									<th colspan="4"><FONT
										style="font-style: normal; font-weight: 500; line-height: normal; text-align: center"><c:out value='<%= sessionBean.getTENUREOFFICEADDRESS() %>'/></FONT></th>
								</tr>
								<tr>
									<td colspan="3">
									<table>
										<tr>
											<td nowrap="nowrap" style="text-align: left;" width="442"><HR>PEON BOOK FOR DT:<c:out value='<%=request.getParameter("BOOKDATEFROM")!=null ? request.getParameter("BOOKDATEFROM"):"" %>'/><br />
									Class :  <c:out value='<%=request.getParameter("CLASS").length()>0 ? request.getParameter("CLASS"): "ALL" %>'/><HR></td>
											<td style="text-align: right" width="330"><HR> <c:out value='<%=serverDate %>'/><br /> <c:out value='<%=serverTime %>'/>
									<HR></td>
										</tr>
									</table>
									</td>
								</tr>

								<tr onclick="resetSrNo();">
									<th style="width: 150" colspan="4">Reference No.</th>
								</tr>
							</thead>
							<tbody id="content">
							<%
							for(int i=0;i<dataArr.size();){
//							CommonBean bean = (CommonBean) dataArr.get(i);
							%>
								<tr class="trodd">
									<td><c:out value='<%= i<dataArr.size()?((1+i)+".  "+dataArr.get(i++).getField1()):"" %>'/></td>
									<td><c:out value='<%= i<dataArr.size()?((1+i)+".  "+dataArr.get(i++).getField1()):"" %>'/></td>
									<td><c:out value='<%= i<dataArr.size()?((1+i)+".  "+dataArr.get(i++).getField1()):"" %>'/></td>
								</tr>
							<%} %>
							</tbody>
						</table>
						<p><br></p>
<p><br></p>
<p><br></p>
<p><br></p>
<p><br></p>
<table width="60%" border="0" cellspacing="0" cellpadding="0" style="font-family: Tahoma; font-size: 10px;" align="center">
	<tbody>
		<tr>
		   	<td align="right">For S.O./M.R. Sectt.</td>
		</tr>
		<tr>
		   	<td align="left"><BR><BR><BR>Issue(D)</td>
		</tr>
	</tbody>
</table>
						</div>
				<%}  %>
						</td>
					</tr>
				</table>
				</td>
			</tr>
						</table>
  </form> 
</body>
</html>