<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.MstLogin" %>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT-Summary Report</title>
<script type="text/javascript" src="script/scripts.js"></script>
<script type="text/javascript" src="script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->

 
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

    $("#STATE").autocomplete("getState.jsp", {scroll:false});
    $('#STATE').result(function(event, data, formatted) {
		    if (data) {
		   // alert(data);
 			var dataarr = new Array();
 			dataarr = String(data).split(",,");
			window.document.getElementById('STATE').value = dataarr[0];
		}
	});
  });
 
function reset()
{

		//fnSelectAll("CATEGORY");
		//fnMoveAll("CATEGORY","sel_CATEGORY"); 

	// Set Railway and Division
	//alert("break");
	
	<%-- //Set REF Class
	var refClass= new Array("0" <% for(int i=0;i<REFCLASS.length;i++){%>,"<%= REFCLASS[i] %>"<%} %>);
	mulitselect(document.getElementById("REFCLASS"),document.getElementById("SEL_REFCLASS"),refClass);
	
	//Set REF Category
	var refCategory= new Array("0" <% for(int i=0;i<REFCATEGORY.length;i++){%>,"<%= REFCATEGORY[i] %>"<%} %>);
	mulitselect(document.getElementById("REFCATEGORY"),document.getElementById("SEL_REFCATEGORY"),refCategory);

	//Set Subject Type
	var subType= new Array("0" <% for(int i=0;i<SUBJECTTYPE.length;i++){%>,"<%= SUBJECTTYPE[i] %>"<%} %>);
	mulitselect(document.getElementById("SUBJECTTYPE"),document.getElementById("SEL_SUBJECTTYPE"),subType);

	//Set Subject Type
	var markTo= new Array("0" <% for(int i=0;i<MARKTO.length;i++){%>,"<%= MARKTO[i] %>"<%} %>);
	mulitselect(document.getElementById("MARKTO"),document.getElementById("SEL_MARKTO"),markTo);

	//Set Selected Columns
	var selcolval= new Array("0" <% for(int i=0;i<selcolval.length;i++){%>,"<%= selcolval[i] %>"<%} %>);
	mulitselect(document.getElementById("tabcols"),document.getElementById("sel_tabcols"),selcolval);
 --%>
	//Set Order By
//	var seltabOrderBy = new Array("0"  for(int i=0;i<seltabOrderBy.length;i++){%>," = seltabOrderBy[i] "} %>);
	
	
	
	
		
	//Set Out of Turn
	//Set Sanc authority	
	//Set sancyear
	//Set cost
	//Set Column Alias
	
	/*
	for(var i=0;i<selcolparam.length;i++)
	{
		if(selcolparam[i].length>0)
		{
			var tempcol = selcolparam[i].split("^");
			for(var j=0;j<document.getElementById("sel_tabcols").length;j++)
			{
				if(document.getElementById("sel_tabcols").options[j].value==tempcol[0])
					document.getElementById("sel_tabcols").options[j].text=tempcol[1];
			}
		}
	}*/
	//alert("before Manupulating --> "+ selcolparam);
	//document.getElementById("sel_tabcols").length=0;
	
	//Rearreange Order By
/*	for(var i=0;i<seltabOrderBy.length;i++)
	{
		for(var j=0;j<document.getElementById("sel_tabcols").length;j++)
		{
			
			if( (seltabOrderBy[i].match("~ASC")||seltabOrderBy[i].match("~DESC")) )
				{
					if(document.getElementById("sel_tabcols").options[j].value==seltabOrderBy[i].split("~")[0])
					{
						var newOption = new Option();
						newOption.text =   document.getElementById("sel_tabcols").options[j].text +(seltabOrderBy[i].split("~")[1]=="ASC"?"▲":"▼");
						newOption.value = seltabOrderBy[i];
//						document.getElementById("sel_tabOrderBy").options[document.getElementById("sel_tabOrderBy").length] = newOption;
					
					}
									
				}
				
		}
	}*/

}
function renameColumn (obj)
{
	var newname = prompt("Enter New Name for :"+ obj.options[obj.selectedIndex].value,obj.options[obj.selectedIndex].text);
	if(newname)
	{
		if(newname.length<=30)
			{ obj.options[obj.selectedIndex].text=newname;
			//addRemoveToOrderBy(this);
			}
		else
			alert("Max characters allowed is 30 but found "+newname.length);
		
	}
}


function submitPage(btnval){
	    if(btnval=='Go1')
	    	{
	    	window.open("/MRSECTT/NewReport")
	    	}
	    else
	    	{
		window.document.getElementById("btnClick").value = btnval;
		//fnSelectAll("SEL_MARKTO");
		document.forms[0].submit();
}
	
}

</script>
<script>  
 
 

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
<form name="SummaryRep" action="SummaryReportController" method="post">
<%
  	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
	String roleIDSQL="SELECT ROLEID, ROLENAME FROM MSTROLE ORDER BY ROLENAME"; 
	ArrayList<CommonBean> roleIdList = (new CommonDAO()).getSQLResult(roleIDSQL, 2);

	/* String queryRefClass = "SELECT DISTINCT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
	
	String queryviptype = "SELECT DISTINCT ADDVIPTYPE, ADDVIPTYPE FROM TRNREFERENCE order by 1"; 
	ArrayList<CommonBean> viptypelist = (new CommonDAO()).getSQLResult(queryviptype, 2);
	
	String queryvipparty = "SELECT DISTINCT VIPPARTY, VIPPARTY FROM TRNREFERENCE order by 1"; 
	ArrayList<CommonBean> vippartylist = (new CommonDAO()).getSQLResult(queryvipparty, 2);

	String queryRefCCategory = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' ORDER BY PRIORITYNO"; 
	ArrayList<CommonBean> refCategoryList = (new CommonDAO()).getSQLResult(queryRefCCategory, 3); */

//	String refCategorySQL="SELECT DISTINCT REFCATEGORY FROM TRNREFERENCE ORDER BY REFCATEGORY"; 
//	ArrayList<CommonBean> refCategoryList = (new CommonDAO()).getSQLResult(refCategorySQL, 1);

	/* String querySubject = " SELECT B.PREFERREDID,(SELECT DISTINCT A.SUBJECTNAME FROM MSTSUBJECT A WHERE A.SUBJECTCODE = B.PREFERREDID ) SUBJECTNAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='3' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2";  
	ArrayList<CommonBean> subjectTypeList = (new CommonDAO()).getSQLResult(querySubject, 2);
	
	String refCatSQL = "SELECT CODE, FNAME, CODETYPE FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00'"; 
	ArrayList<CommonBean> refCatList = (new CommonDAO()).getSQLResult(refCatSQL, 3);

	String STATEListSQL = "SELECT STATECODE, STATENAME FROM MSTSTATE ORDER BY STATENAME"; 
	ArrayList<CommonBean> STATEList = (new CommonDAO()).getSQLResult(STATEListSQL, 2);*/

	String year="select to_char(add_months(sysdate,-(rownum-1)*12),'yyyy') from trnreference where rownum<=to_char(sysdate,'yyyy')-2013"; 
	ArrayList<CommonBean> yearList = (new CommonDAO()).getSQLResult(year, 1); 

	String stateCode = "select STATECODE,STATENAME from MSTSTATE order by 2";
	
	/* String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						    " FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2";  */
	ArrayList<CommonBean> stateCodeList = (new CommonDAO()).getSQLResult(stateCode, 2);
	

%>
<table width="1002px" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td height="4px"></td>
	</tr>
	<tr>
		<td class="appbg">
		<table width="984" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td></td>
					</tr>
					<tr>
						<td align="center">

						<table width="984px" border="1" class="bor"
							cellspacing="0" cellpadding="0"
							style="border-collapse: collapse; margin-top: 7px;">
							<tr>
								<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="tablehead">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<th>Summary Report Parameter </th>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td></td>
									</tr>
									<tr>
										<td>
										<table width="97%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td height="8px"></td>
											</tr>
											<tr>
												<td style="padding-left: 7px;"><table bordercolor="#cdcdcd" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;">
                  <tr class="firstbg">
                  		<th colspan="8"> Filter Criteria</th>
                  </tr>
													
													<tr class="treven">
														<td colspan="8">
														<table width="100%" border="0">
															<tbody>
																
																
																
																
																
																
																
																<tr class="treven">
																	<td align="center" width="130"><b>State:</b><input type="hidden" name="btnClick" id="btnClick"></input></td>
																	<td align="center"  width="327"><select style="width: 250px" name="State" id="State" tabindex="1">
																	
																	<%
									for(int i=0;i<stateCodeList.size();i++){
									CommonBean beanCommon=(CommonBean) stateCodeList.get(i);
								%>
									<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
								<%}%>
							</select></td></tr>
															
																
																
															</tbody>
														</table>
														<br>
														</td>
													</tr>
												                           
													<tr class="trodd">
														<th colspan="8">
														<br />
														
														</th>
													</tr>
												</table>
												
               
              </td>
              </tr>
            
              <tr>
              <td>&nbsp;</td>
              </tr>
            </table>
            
            <table bordercolor="#7e7d7d" cellspacing="0" cellpadding="2" style="border-collapse:collapse;">
            	<tr class="firstbg">
            	<td><input type="button" name="btnsave" id="btnsave" class="butts" value="Subject Wise Summary (Specific Subjects)" onclick="submitPage('SubjectSummary');" /></td>
            		<td><input type="button" name="btnsave" id="btnsave" class="butts" value="State Summary" onclick="submitPage('StateSummary');" /></td>
            		<td><input type="button" name="btnsave" id="btnsave" class="butts" value="All State Summary" onclick="submitPage('CompleteSummary');" /></td>
            		
            	<!-- 	<td><input type="button" name="btncancel" id="btncancel" class="butts" value=" Clear " onclick="window.location.href='MonthlyReport.jsp'" /></td> -->
            		
            	</tr>
            </table>
            
            
           

<script type="text/javascript">

//-->
</script>

<!--  <%=" Model Starts -->"%>
<DIV class="transparent_class" align="center" style="z-index:2000; background-color:#000; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 150%;" id="updateDiv">
	
</DIV>
<DIV align="center"  style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:80px; top: 260px; display: none; width: 100%" id="updateDivInner">
<DIV class="pageheader" align="center" style="color:white; font-family:tahoma ; background-color: #7e7d7d; width: 300px; border: groove; text-align: center; background-image:url(images/top-hd-bar-bg.gif); background-repeat: repeat; " id="divLoading">
			<BR>Generating Report
			<BR>
			<BR>Please Wait <img src="images_old/progress_bar.gif" alt="Loading" />
			<BR> <input type="button" class="butts" value=" Close " onclick="hideDiv();" /> <BR>
</DIV>
</DIV>
<%="<!-- Model Ends "%>    -->
</td>
											</tr>


										</table>
										</td>
									</tr>
								</table>
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