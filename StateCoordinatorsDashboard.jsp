<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.MstLogin" %>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
  	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");




	String loginid=sessionBean.getLOGINASROLEID()!=null?sessionBean.getLOGINASROLEID():"";
	
	
	//System.out.println(loginid);
	String roleIDSQL="SELECT ROLEID, ROLENAME FROM MSTROLE ORDER BY ROLENAME"; 
	ArrayList<CommonBean> roleIdList = (new CommonDAO()).getSQLResult(roleIDSQL, 2);

	String year="select to_char(add_months(sysdate,-(rownum-1)*12),'yyyy') from trnreference where rownum<=to_char(sysdate,'yyyy')-2013"; 
	ArrayList<CommonBean> yearList = (new CommonDAO()).getSQLResult(year, 1); 

	String queryState = "select a.statecode, (select STATENAME from mststate b where b.statecode= a.statecode) statename from mstrolestate a where roleid='"+sessionBean.getLOGINASROLEID()+"' order by 2";
	
	
	
	String queryviptype = "SELECT DISTINCT ADDVIPTYPE, ADDVIPTYPE FROM TRNREFERENCE order by 1"; 
	ArrayList<CommonBean> viptypelist = (new CommonDAO()).getSQLResult(queryviptype, 2);
	
	/* String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 						    " FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2";  */
	ArrayList<CommonBean> stateList = (new CommonDAO()).getSQLResult(queryState, 2);
	

%>
<title>MRSECTT</title>
<script type="text/javascript" src="script/scripts.js"></script>
<script type="text/javascript" src="script/validateinput.js"></script>
<script type="text/javascript" src="theme/jquery/jquery-1.3.2.js"></script>
<SCRIPT type="text/javascript" src="theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<LINK href="theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css">
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->

 
<script>

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


function submitPage(){
	   
	    
		
		fnSelectAll("SEL_STATE");
		fnSelectAll("SEL_VIP");
		
		//alert(loginID)
		document.forms[0].submit();

	
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
<form name="stateCoordinorsRep" action="StateCoordinatorReportController" method="post">

 <input type ="hidden"  name ="loginid" value="<%=loginid%>">	</input>

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
												<th>State Wise Detailed Report </th>
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
												<td >
												
                  <tr class="firstbg">
                  		<th colspan="16"> Filter Criteria</th>
                  </tr>
												
              
                  	<tr class="firstbg">
                   		<th></th><th>Available</th><th></th><th>Selected <sup style="color: red;">*</sup></th><th></th><th></th><th></th><th></th>
                   	</tr>                
                  	<tr class="trodd">
                  	
										
												<td> <b>State:</b><br />
														</td> <td>
														
														
							<select multiple="multiple" style="width: 180px; height: 100px"
															id="STATE" name="STATE"
															ondblclick="fnMoveItems('STATE','SEL_STATE')">
															
														
														</select></td>
							<td valign="middle" align="center"> 
		           			 
			           		<img align="middle" src="images/next.gif"
															onclick="fnMoveItems( 'STATE','SEL_STATE');" /> <br />
														<img align="middle" src="images/prev.gif"
															onclick="fnMoveItems( 'SEL_STATE','STATE');" /></td>
			           		<td>
				                  		<select
															ondblclick="fnMoveItems('SEL_STATE','STATE')"
															name="SEL_STATE" id="SEL_STATE" multiple="multiple"
															style="width: 180px; height: 100px">
															
															<%
							for(int i=0;i<stateList.size();i++){
							CommonBean beanCommon = (CommonBean) stateList.get(i);
							%>
															<option value="<%=beanCommon.getField1()%>" selected="selected"><%=beanCommon.getField2()%></option>
															<%
						}%>
</select></td>
											</tr>
											
						
													<tr class="trodd">
														
													</tr>
												
												
               
              </td>
              </tr>
            
              <tr>
              <td>&nbsp;</td>
              </tr>
              
              	<tr class="firstbg">
                   		<th></th><th>Available</th><th></th><th>Selected <sup style="color: red;">*</sup></th><th></th><th></th><th></th><th></th>
                   	</tr>                
                  	<tr class="trodd">
                  	
										
												<td> <b>Vip Status</b><br />
														</td> <td>
							<select multiple="multiple" style="width: 180px; height: 100px"
															id="VIP" name="VIP"
															ondblclick="fnMoveItems('VIP','SEL_VIP')">
															
													
														</select></td>
							<td valign="middle" align="center"> 
		           			 
			           		<img align="middle" src="images/next.gif"
															onclick="fnMoveItems( 'VIP','SEL_VIP');" /> <br />
														<img align="middle" src="images/prev.gif"
															onclick="fnMoveItems( 'SEL_VIP','VIP');" /></td>
			           		<td>
				                  		<select
															ondblclick="fnMoveItems('SEL_VIP','VIP')"
															name="SEL_VIP" id="SEL_VIP" multiple="multiple"
															style="width: 180px; height: 100px">
																<%
							for(int i=0;i<viptypelist.size();i++){
							CommonBean beanCommon = (CommonBean) viptypelist.get(i);
							%>
															<option value="<%=beanCommon.getField1()%>" selected="selected"><%=beanCommon.getField2()%></option>
															<%
						}%>
</select></td>
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
            	
            		<td>
            		
            		<input type="button" name="btnsave" id="btnsave" class="butts" value="Generate Excel Report" onclick="submitPage();" /></td>
            		
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