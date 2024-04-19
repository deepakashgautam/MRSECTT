<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="in.org.cris.mrsectt.dao.BudgetReportDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.Beans.GenBean"%>
<%@page import="in.org.cris.mrsectt.dao.KeywordReportDAO"%><html> 
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
	 
	 String queryRefClass = "SELECT DISTINCT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);


String INDATECONDITION = request.getParameter("INDATECONDITION") != null ? request.getParameter("INDATECONDITION") : "";
String INDATEFROM = request.getParameter("INDATEFROM") != null ? request.getParameter("INDATEFROM") : "";
String INDATETO = request.getParameter("INDATETO") != null ? request.getParameter("INDATETO") : "";

String LETDATECONDITION = request.getParameter("LETDATECONDITION") != null ? request.getParameter("LETDATECONDITION") : "";
String LETDATEFROM = request.getParameter("LETDATEFROM") != null ? request.getParameter("LETDATEFROM") : "";
String LETDATETO = request.getParameter("LETDATETO") != null ? request.getParameter("LETDATETO") : "";

String RECEIVEDFROM = request.getParameter("RECEIVEDFROM") != null ? request.getParameter("RECEIVEDFROM") : "";
String STATUS = request.getParameter("STATUS") != null ? request.getParameter("STATUS") : "";
String STATE = request.getParameter("STATE") != null ? request.getParameter("STATE") : "";
String SUBJECT = request.getParameter("SUBJECT") != null ? request.getParameter("SUBJECT") : "";
String ACKNBY = request.getParameter("ACKNBY") != null ? request.getParameter("ACKNBY") : "";

String ACKNDATECONDITION = request.getParameter("ACKNDATECONDITION") != null ? request.getParameter("ACKNDATECONDITION") : "";
String ACKNDATEFROM = request.getParameter("ACKNDATEFROM") != null ? request.getParameter("ACKNDATEFROM") : "";
String ACKNDATETO = request.getParameter("ACKNDATETO") != null ? request.getParameter("ACKNDATETO") : "";

String MARKINGDATECONDITION = request.getParameter("MARKINGDATECONDITION") != null ? request.getParameter("MARKINGDATECONDITION") : "";
String MARKINGDATEFROM = request.getParameter("MARKINGDATEFROM") != null ? request.getParameter("MARKINGDATEFROM") : "";
String MARKINGDATETO = request.getParameter("MARKINGDATETO") != null ? request.getParameter("MARKINGDATETO") : "";

String TARGETDATECONDITION = request.getParameter("TARGETDATECONDITION") != null ? request.getParameter("TARGETDATECONDITION") : "";
String TARGETDATEFROM = request.getParameter("TARGETDATEFROM") != null ? request.getParameter("TARGETDATEFROM") : "";
String TARGETDATETO = request.getParameter("TARGETDATETO") != null ? request.getParameter("TARGETDATETO") : "";

String REMARKS = request.getParameter("REMARKS") != null ? request.getParameter("REMARKS") : "";
String SIGNEDBY = request.getParameter("SIGNEDBY") != null ? request.getParameter("SIGNEDBY") : "";

String SIGNEDONCONDITION = request.getParameter("SIGNEDONCONDITION") != null ? request.getParameter("SIGNEDONCONDITION") : "";
String SIGNEDONFROM = request.getParameter("SIGNEDONFROM") != null ? request.getParameter("SIGNEDONFROM") : "";
String SIGNEDONTO = request.getParameter("SIGNEDONTO") != null ? request.getParameter("SIGNEDONTO") : "";

String TAG = request.getParameter("TAG") != null ? request.getParameter("TAG") : "";
String RLY = request.getParameter("RLY") != null ? request.getParameter("RLY") : "";
String PLACE = request.getParameter("PLACE") != null ? request.getParameter("PLACE") : "";

String PLCRLYCONDITION = request.getParameter("SIGNEDONCONDITION") != null ? request.getParameter("SIGNEDONCONDITION") : "";
String PLCRLYFROM = request.getParameter("SIGNEDONFROM") != null ? request.getParameter("SIGNEDONFROM") : "";
String PLCRLYTO = request.getParameter("SIGNEDONTO") != null ? request.getParameter("SIGNEDONTO") : "";
// new code (suneel)

//String [] ROLEID = request.getParameterValues("SEL_ROLEID") != null ? request.getParameterValues("SEL_ROLEID") :  new String [0];
String [] REFCLASS = request.getParameterValues("SEL_REFCLASS") != null ? request.getParameterValues("SEL_REFCLASS") :  new String [0];
String [] REFCATEGORY = request.getParameterValues("SEL_REFCATEGORY") != null ? request.getParameterValues("SEL_REFCATEGORY") :  new String [0];
String [] SUBJECTTYPE = request.getParameterValues("SEL_SUBJECTTYPE") != null ? request.getParameterValues("SEL_SUBJECTTYPE") :  new String [0];	
String [] MARKTO = request.getParameterValues("SEL_MARKTO") != null ? request.getParameterValues("SEL_MARKTO") :  new String [0];			
//String [] STATE = request.getParameterValues("SEL_STATE") != null ? request.getParameterValues("SEL_STATE") :  new String [0];
//String [] seltabOrderBy = request.getParameterValues("sel_tabOrderBy") != null ? request.getParameterValues("sel_tabOrderBy") :  new String [0];
String [] selcolval = request.getParameterValues("sel_tabcols") != null ? request.getParameterValues("sel_tabcols") :  new String [0];
String [] VIPTYPE = request.getParameterValues("SEL_VIPTYPE") != null ? request.getParameterValues("SEL_VIPTYPE") :  new String [0];
String [] VIPPARTY = request.getParameterValues("SEL_VIPPARTY") != null ? request.getParameterValues("SEL_VIPPARTY") :  new String [0];


/*
int startyr = (Integer.parseInt(budgetyear.split("-")[0]));
	DateFormat df1 = new SimpleDateFormat("yyyy");
	String currDate = df1.format(new Date());
	int curryear = ((Integer.parseInt(currDate)));
*/
ArrayList STATEdarr = new CommonDAO().getState();
ArrayList planheadarr = new ArrayList();
/*
	if ("cepd".equalsIgnoreCase(session.getAttribute("loginid")
			.toString().length() > 4 ? session.getAttribute("loginid")
			.toString().substring(0, 4) : "")) {
		planheadarr = cd.getPlanhd(cd.getPlanhdList("pce"
				+ session.getAttribute("loginid").toString().substring(
						4)));
	} else {
		planheadarr = cd
				.getPlanhd(session.getAttribute("CATEGORY") != null ? session
						.getAttribute("CATEGORY").toString()
						: "");
	}
	
	
		if(CATEGORY.length==0){
	CATEGORY = new String [planheadarr.size()];
	for(int i=0;i<planheadarr.size();i++)
		CATEGORY[i]=  ((CommonBean)  planheadarr.get(i)).getField1();
	}
	
	
	planheadarr = cd.getPlanhd(cd.getPlanhdList("edceg"));
*/	

//	ArrayList rlycodearr = cd.getRly(sessionrly);

	ArrayList divarr = new ArrayList();		

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
<%
  	
	String queryviptype = "SELECT DISTINCT ADDVIPTYPE, ADDVIPTYPE FROM TRNREFERENCE order by 1"; 
	ArrayList<CommonBean> viptypelist = (new CommonDAO()).getSQLResult(queryviptype, 2);
	
	String queryvipparty = "SELECT DISTINCT VIPPARTY, VIPPARTY FROM TRNREFERENCE order by 1"; 
	ArrayList<CommonBean> vippartylist = (new CommonDAO()).getSQLResult(queryvipparty, 2);

	String queryRefCCategory = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' ORDER BY PRIORITYNO"; 
	ArrayList<CommonBean> refCategoryList = (new CommonDAO()).getSQLResult(queryRefCCategory, 3);

//	String refCategorySQL="SELECT DISTINCT REFCATEGORY FROM TRNREFERENCE ORDER BY REFCATEGORY"; 
//	ArrayList<CommonBean> refCategoryList = (new CommonDAO()).getSQLResult(refCategorySQL, 1);

	String querySubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2";  
	ArrayList<CommonBean> subjectTypeList = (new CommonDAO()).getSQLResult(querySubject, 2);
	
	String refCatSQL = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00'"; 
	ArrayList<CommonBean> refCatList = (new CommonDAO()).getSQLResult(refCatSQL, 3);

	String STATEListSQL = "SELECT STATECODE, STATENAME FROM MSTSTATE ORDER BY STATENAME"; 
	ArrayList<CommonBean> STATEList = (new CommonDAO()).getSQLResult(STATEListSQL, 2);

	String acknByListSQL="SELECT NAME, TENUREID FROM MSTTENURE WHERE ISACTIVE = 'Y' ORDER BY NAME"; 
	ArrayList<CommonBean> acknByList = (new CommonDAO()).getSQLResult(acknByListSQL, 2);

	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						    " FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	
	
	
	String subjectcode=request.getParameter("SUBJECTTYPE")!=null?request.getParameter("SUBJECTTYPE"):"";
	
	String keyword1=request.getParameter("keyword1")!=null?request.getParameter("keyword1"):"";
	String keyword3=request.getParameter("keyword3")!=null?request.getParameter("keyword3"):"";
	
%>
<form name="frm" action="KeywordsReportExcel" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Report-</i>Keywords</b>
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
						<% if(subjectcode.length()==0){ %>
						<center><fieldset style="width: 80%;">		
								<table align="center" border="0" style="width: 100%">
									
								<tbody>
								
								
												<tr class="trodd"> 
													<tr class="trodd">
                  		<td>Sub.</td> <td>
										
										
										<select 
															 id="SUBJECTTYPE"
															name="SUBJECTTYPE"
															>
															<%
					for(int i=0;i<subjectTypeList.size();i++){
					CommonBean beanCommon=(CommonBean) subjectTypeList.get(i);
				%>
															<option value="<%= beanCommon.getField1()%>"><%= beanCommon.getField2()%></option>
															<%}%>
														</select></td>
														<td>
														Stn1: <input type="text" name="keyword1" id="keyword1" size="17" >
							
						
														</td>
														<td>
														Trn: <input type="text" name="keyword3" id="keyword3" size="17">
														</td>
												</tr>
												<tr class="treven">
																	<td  align="left">Incoming Date</td>
																	<td nowrap="nowrap" align="left"><select name="INDATECONDITION" id="INDATECONDITION" onchange="showHideIndate(this);">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="INDATEFROM" id="INDATEFROM" size="10" value="<%=sessionBean.getTENURESTARTDATE() %>"/>
																	&nbsp;<input type="text" name="INDATETO" id="INDATETO" size="10" value="<%= serverDate %>"
																		style="display: none;"/></td>
																	<td align="left" >Ref. Date</td>
																	<td colspan="3" align="left"><select
																		name="LETDATECONDITION" id="LETDATECONDITION"
																		onchange="showHideLDate(this);">
																		<option value="=" selected="selected">EQUAL</option>
																		<option value="BETWEEN">BETWEEN</option>
																	</select>&nbsp;<input type="text" name="LETDATEFROM" id="LETDATEFROM" size="10"
																		value="<%= LETDATEFROM%>" />
																	&nbsp;<input type="text" name="LETDATETO" id="LETDATETO" size="10"
																		style="display: none;" value="<%= LETDATETO%>" /></td>
																</tr>
																<tr class="trodd">
																	<td  align="left">Received From</td>
																	<td nowrap="nowrap" align="left" ><input style=" background-image: url('images/search.png'); background-position: right;background-repeat: no-repeat;"
																		type="text" id="RECEIVEDFROM" name="RECEIVEDFROM" size="34" value="<%= RECEIVEDFROM %>"   /></td>
																	<td align="left" >Status</td>
																	<td colspan="3" align="left" ><input value="<%= STATUS%>" style=" background-image: url('images/search.png'); background-position: right;background-repeat: no-repeat;"
																		type="text" name="STATUS" id="STATUS" size="34" /></td>
																</tr>
																<tr class="treven">
																
																 <td>VIPTYPE</td>
                  		<td> 
                  		<select
															ondblclick="fnMoveItems('VIPTYPE', 'SEL_VIPTYPE');"
															name="VIPTYPE" id="VIPTYPE" multiple="multiple"
															style="width: 180px; height: 100px">
															<%
								for(int i=0;i<viptypelist.size();i++){
								CommonBean beanCommon=(CommonBean) viptypelist.get(i);
							%>
															<option value="<%= beanCommon.getField1()%>"><%= beanCommon.getField1()%></option>
															<%}%>
														</select></td>
                  		<td valign="middle" align="center"> <img
															align="middle" src="images/next.gif"
															onclick="fnMoveItems( 'VIPTYPE','SEL_VIPTYPE')" /> <br />
														<img align="middle" src="images/prev.gif"
															onclick="fnMoveItems( 'SEL_VIPTYPE','VIPTYPE')" /><br /> 
                  		</td>
                  		<td>
                  		<select
															ondblclick="fnMoveItems('SEL_VIPTYPE', 'VIPTYPE');"
															name="SEL_VIPTYPE" id="SEL_VIPTYPE" multiple="multiple"
															style="width: 180px; height: 100px">

														</select></td>
																</tr>
																<tr class="trodd">
																	
																 <td>VIP Party</td>
                  		<td> 
                  		<select
															ondblclick="fnMoveItems('VIPPARTY', 'SEL_VIPPARTY');"
															name="VIPPARTY" id="VIPPARTY" multiple="multiple"
															style="width: 180px; height: 100px">
															<%
								for(int i=0;i<vippartylist.size();i++){
								CommonBean beanCommon=(CommonBean) vippartylist.get(i);
							%>
															<option value="<%= beanCommon.getField1()%>"><%= beanCommon.getField1()%></option>
															<%}%>
														</select></td>
                  		<td valign="middle" align="center"> <img
															align="middle" src="images/next.gif"
															onclick="fnMoveItems( 'VIPPARTY','SEL_VIPPARTY')" /> <br />
														<img align="middle" src="images/prev.gif"
															onclick="fnMoveItems( 'SEL_VIPPARTY','VIPPARTY')" /><br /> 
                  		</td>
                  		<td>
                  		<select
															ondblclick="fnMoveItems('SEL_VIPPARTY', 'VIPPARTY');"
															name="SEL_VIPPARTY" id="SEL_VIPPARTY" multiple="multiple"
															style="width: 180px; height: 100px">

														</select></td>
																</tr>
																<tr class="treven">
																<td>Ref Class</td>
                  		<td> 
                  		<select
															ondblclick="fnMoveItems('REFCLASS', 'SEL_REFCLASS');"
															name="REFCLASS" id="REFCLASS" multiple="multiple"
															style="width: 180px; height: 100px">
															<%
								for(int i=0;i<refClassList.size();i++){
								CommonBean beanCommon=(CommonBean) refClassList.get(i);
							%>
															<option value="<%= beanCommon.getField1()%>"><%= beanCommon.getField1()%></option>
															<%}%>
														</select></td>
                  		<td valign="middle" align="center"> <img
															align="middle" src="images/next.gif"
															onclick="fnMoveItems( 'REFCLASS','SEL_REFCLASS')" /> <br />
														<img align="middle" src="images/prev.gif"
															onclick="fnMoveItems( 'SEL_REFCLASS','REFCLASS')" /><br /> 
                  		</td>
                  		<td>
                  		<select
															ondblclick="fnMoveItems('SEL_REFCLASS', 'REFCLASS');"
															name="SEL_REFCLASS" id="SEL_REFCLASS" multiple="multiple"
															style="width: 180px; height: 100px">

														</select></td>
																</tr>
						<tr align="center">
							<td align="center" colspan="4"><input type="button"
								value="View" style="height: 20px;"  class="butts" onclick="generateHTML();"/> 
								
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
								value="Generate Excel" style="height: 20px;"  class="butts" onclick="generateXLS();"/></td>
						</tr>
					</tbody></table></fieldset></center>
						<%} %>
						</td>
					</tr>
					
					<tr>
						<td>
						
						<% if(subjectcode.length()>0){ %>
						<table>
							<tr>
								<th>S.No.</th>
								<th>Station</th>
								<th>Station</th>
								<th>Train</th>
								<th>Name</th>
								<th>Type</th>
								<th>Subject</th>
								<th>Incoming Date</th>
								<th>Ref No.</th>
							</tr>
							<% 
								ArrayList<GenBean> list = new KeywordReportDAO().getReport(INDATECONDITION,INDATEFROM,INDATETO,LETDATECONDITION,LETDATEFROM,LETDATETO,RECEIVEDFROM,REFCLASS,VIPTYPE,VIPPARTY,STATUS,subjectcode,keyword1,keyword3 ); 
								int cnt=0;
							 %>
							 
							 <%
							 String oldname="";
							 String oldtype="";
							 String oldK1="--";
							 String oldK2="--";
							 String oldK3="--";
							 boolean changed=true;
							 for(GenBean bean : list){ 
							 if(oldK1.equalsIgnoreCase(bean.getField()[0]) && oldK2.equalsIgnoreCase(bean.getField()[1]) && oldK3.equalsIgnoreCase(bean.getField()[2])){
							   changed=false;
							   }else{
							   changed=true;
							   }
							 %>
							 <tr>
							 	<td><%=changed?(++cnt)+"":"" %></td>
							 	<td><%= oldK1.equalsIgnoreCase(bean.getField()[0]) && oldK2.equalsIgnoreCase(bean.getField()[1])&& oldK3.equalsIgnoreCase(bean.getField()[2]) ?"":bean.getField()[0] %></td>
							 	<td><%= oldK1.equalsIgnoreCase(bean.getField()[0]) && oldK2.equalsIgnoreCase(bean.getField()[1])&& oldK3.equalsIgnoreCase(bean.getField()[2])?"":bean.getField()[1] %></td>
							 	<td><%= oldK1.equalsIgnoreCase(bean.getField()[0]) && oldK2.equalsIgnoreCase(bean.getField()[1])&& oldK3.equalsIgnoreCase(bean.getField()[2])?"":bean.getField()[2] %></td>
							 	<td><%= oldname.equalsIgnoreCase(bean.getField()[3])?"":bean.getField()[3] %></td>
							 	<td><%= oldname.equalsIgnoreCase(bean.getField()[3]) && oldtype.equalsIgnoreCase(bean.getField()[4])?"":bean.getField()[4] %></td>
							 	<td><%= bean.getField()[5] %></td>
							 	<td><%= bean.getField()[6] %></td>
							 	<td><%= bean.getField()[7] %></td>
							 </tr>
							 
							 <%
							  
							 
							    oldname=bean.getField()[3];
							  oldtype=bean.getField()[4];
							  oldK1=bean.getField()[0];
							  oldK2=bean.getField()[1];
							  oldK3=bean.getField()[2];
							 } %>
							
						</table>
						<%} %>
						</td>
					</tr>
				</table>
				</td>
			</tr>
						</table>
  </form> 
  <script>
  
  
  $(document).ready(function(){
    $("#RECEIVEDFROM").autocomplete("getReferenceNamesData.jsp", {scroll:false});
    $('#RECEIVEDFROM').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('RECEIVEDFROM').value = dataarr[0];
		}
	});

    $("#STATUS").autocomplete("getStatus.jsp", {scroll:false});
    $('#STATUS').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('STATUS').value = dataarr[0];
		}
	});
  enable_autocomplete();
  });
  function showHideIndate()
{
	if(window.document.getElementById("INDATECONDITION").value == "BETWEEN")
	{
		window.document.getElementById("INDATEFROM").style.display = "inline";
		window.document.getElementById("INDATETO").style.display = "inline";
	}
	else if(window.document.getElementById("INDATECONDITION").value == "=")
	{
		window.document.getElementById("INDATEFROM").style.display = "inline";
		window.document.getElementById("INDATETO").style.display = "none";
	}
}

function showHideLDate()
{
	if(window.document.getElementById("LETDATECONDITION").value == "BETWEEN")
	{
		window.document.getElementById("LETDATEFROM").style.display = "inline";
		window.document.getElementById("LETDATETO").style.display = "inline";
	}
	else if(window.document.getElementById("LETDATECONDITION").value == "=")
	{
		window.document.getElementById("LETDATEFROM").style.display = "inline";
		window.document.getElementById("LETDATETO").style.display = "none";
	}
}
  
  
  function generateHTML(){
  	
  	document.frm.action="#";
  	fnSelectAll("SEL_VIPPARTY");
  	fnSelectAll("SEL_VIPTYPE");
  	fnSelectAll("SEL_REFCLASS");
  	document.frm.submit();
  }
  
  
  function generateXLS(){
    
    document.frm.action="KeywordsReportExcel";
    fnSelectAll("SEL_VIPPARTY");
  	fnSelectAll("SEL_VIPTYPE");
  	fnSelectAll("SEL_REFCLASS");
  	document.frm.submit();
  }
  
  function enable_autocomplete() {
$("#keyword1").css("border", "red 1px dotted");
    $("#keyword1").autocomplete("getStations.jsp", {scroll:false});
   $("#keyword1").result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");;
			$("#keyword1").val(dataarr[0]);
		}
	});
	

	
	$("#keyword3").css("border", "red 1px dotted");
	$("#keyword3").autocomplete("getTrain.jsp", {scroll:false});
   $("#keyword3").result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			$("#keyword3").val(dataarr[0]);
		}
	});
	
	
}


  </script>
</body>
</html>