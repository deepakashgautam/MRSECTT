<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="in.org.cris.mrsectt.dao.BudgetReportDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%><html> 
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
    String FBOOKDATE=request.getParameter("FBOOKDATE")!=null ? request.getParameter("FBOOKDATE") : "";
    String TBOOKDATE=request.getParameter("TBOOKDATE")!=null ? request.getParameter("TBOOKDATE") : "";
    String CLASS=request.getParameter("CLASS")!=null ? request.getParameter("CLASS") : "";
    ArrayList<CommonBean> dataArr = new ArrayList<CommonBean>();
    if(btnClick.equals("GO"))
    {
	dataArr = (new BudgetReportDAO()).getOutDak(sessionBean.getLOGINASROLEID(),sessionBean.getTENUREID(),FBOOKDATE,TBOOKDATE,CLASS,sessionBean.getISCONF()); 
	}
	 	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
	 	String serverTime = CommonDAO.getSysDate("HH:mm:ss"); 
	 
	 String queryRefClass = "SELECT DISTINCT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"' AND INOUT='O' ORDER BY REFCLASS"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
%>
<script>




function submitPage(){
if(chkblank(window.document.getElementById("FBOOKDATE")) && chkblank(window.document.getElementById("TBOOKDATE"))){
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
	
	
            $("#FBOOKDATE").datepicker({ dateFormat: 'dd/mm/yy',showButtonPanel:'true'});
            $("#TBOOKDATE").datepicker({ dateFormat: 'dd/mm/yy',showButtonPanel:'true'});
	
	
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
<form name="frm" action="OutDak.jsp" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Out Dak</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
				<td align="center"><table width="984px" border="0"  cellspacing="0"
					cellpadding="0" style="border-collapse: collapse; margin-top: 1px;">
					<tr>
						<td colspan="8">
						<br><br><center><fieldset style="width: 50%;">		
								<table align="center" border="0">
									
								<tbody><tr  align="center"><td   align="right"><b>Sent Date :</b>&nbsp;</td><td nowrap="nowrap">
								<input type="text" name="FBOOKDATE" id="FBOOKDATE" value='<%=request.getParameter("FBOOKDATE")!=null ? request.getParameter("FBOOKDATE"):serverDate%>' size="10"/>
								 <b>TO </b><input type="text" name="TBOOKDATE" id="TBOOKDATE" value='<%=request.getParameter("TBOOKDATE")!=null ? request.getParameter("TBOOKDATE"):serverDate%>' size="10"/>
							</td><td   align="right"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Class</b> &nbsp; &nbsp;</td><td nowrap="nowrap"><select style="width: 70px"  name="CLASS" id="CLASS"  tabindex="1" >
								<option value="" selected>ALL</option>
								<%
										for(int i=0;i<refClassList.size();i++){
										CommonBean beanCommon=(CommonBean) refClassList.get(i);
										%>
								<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(CLASS)?"selected":""%>><%=beanCommon.getField1()%></option>
								<%
									}%>
							</select><input type="hidden" name="btnClick"
												id="btnClick"
												value='<%=request.getParameter("btnClick")!=null ? request.getParameter("btnClick"):""%>'
												/></td></tr>
						<tr align="center">
							<td align="center" colspan="4"><input type="button"
								value="Generate" style="height: 20px;" onclick="submitPage();" class="butts"/></td>
						</tr>
					</tbody></table></fieldset></center>
						<%
											if ((dataArr != null) && (dataArr.size() != 0)) {
										%>
						<table align="center">
							<tr>
								<td colspan="11" class="treven"><img width="20" height="20"
									src="images/printer_new.png"
									onclick="window.open('PopUpPrintReport_OutDak.jsp?flag=y');"></img></td>
							</tr>
						</table>
						<div id="reportData">
						<table width="80%" class="tablesorter" id="sorttable" align="center">
								<tr class="treven">
									<th colspan="4"><FONT size="3"><%= sessionBean.getTENUREOFFICENAME() %></FONT></th>
								</tr>
								<tr>
									<th colspan="4"></th>
								</tr>
								<tr>
									<th colspan="4" style="font-style: normal; font-weight: 500; line-height: normal; text-align: center"><%= sessionBean.getTENUREOFFICEADDRESS() %></th>
								</tr>
						</table>
						<table width="80%" class="tablesorter" id="sorttable" align="center">
							<thead style="display: table-header-group;">
								<tr>
									<td nowrap="nowrap" style="text-align: left;" width="442"> <HR> OUT DAK FOR DT : <%=request.getParameter("FBOOKDATE")!=null ? request.getParameter("FBOOKDATE"):"" %>  &nbsp;TO <%=request.getParameter("TBOOKDATE")!=null ? request.getParameter("TBOOKDATE"):"" %><br/>Ref.Class : <%=request.getParameter("CLASS").length()>0 ? request.getParameter("CLASS"): "ALL" %><HR></td>
									<td style="text-align: right" width="330"><HR><%= serverDate %><br /><%=serverTime %><HR></td>
								</tr>
								<tr onclick="resetSrNo();">
									<th style="width: 150" colspan="3">Reference No.</th>
								</tr>
							</thead>
							<tbody id="content">
							<%
							for(int i=0;i<dataArr.size();){
//							CommonBean bean = (CommonBean) dataArr.get(i);
							%>
								<tr>
									<td align="left" ><font size="1"><%= i<dataArr.size()?((1+i)+".  <b>"+dataArr.get(i).getField1() +"</b><br>Mr./Ms.&nbsp;"+dataArr.get(i++).getField2()):"" %></font></td>
									<td align="left" ><font size="1"><%= i<dataArr.size()?((1+i)+".  <b>"+dataArr.get(i).getField1() +"</b><br>Mr./Ms.&nbsp;"+dataArr.get(i++).getField2()):"" %></font></td>
								</tr>
								<tr><td><br></td></tr>
							<%} %>
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