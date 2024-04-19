<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="in.org.cris.mrsectt.dao.MstVIPDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%><html> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="enc" uri="/WEB-INF/encrypt.tld"%>
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
    String LANGUAGE=request.getParameter("LANGUAGE")!=null ? request.getParameter("LANGUAGE") : "";
    int ACKCOUNT=0;
    if(btnClick.equals("GO"))
    {
    	ACKCOUNT = (new MstVIPDAO()).getAckPrepared(BOOKDATEFROM,BOOKDATETO,LANGUAGE); 
	}
	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
 	String serverTime = CommonDAO.getSysDate("HH:mm:ss"); 
%>
<script>

function submitPage(){
if(chkblank(window.document.getElementById("BOOKDATEFROM")) && chkblank(window.document.getElementById("BOOKDATETO"))){
  document.forms[0].btnClick.value="GO";
  document.forms[0].submit();
  }
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
<body>
<form name="frm" action="AckCount.jsp" method="post">
<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create</i> - Acknowledgement Preparation - Date</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
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
								<tbody><tr  align="center"><td  align="right"><b>Acknowledgement prepared on:</b> </td>
						<td nowrap="nowrap"><input type="text" 	name="BOOKDATEFROM" id="BOOKDATEFROM" size="10" value="<c:out value='<%=request.getParameter("BOOKDATEFROM")!=null ? request.getParameter("BOOKDATEFROM"):serverDate%>'/>"/></td>
				   	<td> <b>&nbsp - &nbsp</b> </td>
						<td nowrap="nowrap"><input type="text" 	name="BOOKDATETO" id="BOOKDATETO" size="10" value="<c:out value='<%=request.getParameter("BOOKDATETO")!=null ? request.getParameter("BOOKDATETO"):serverDate%>'/>"/></td>
						<td   align="right">&nbsp;&nbsp;&nbsp;<b>Language:</b> </td><td nowrap="nowrap"><select style="width: 120px"  name="LANGUAGE" id="LANGUAGE"  tabindex="1" >
								<option value="" selected>ALL</option>
								<option value = "2" <c:out value='<%=LANGUAGE.equalsIgnoreCase("2")?"selected":""%>'/>>Hindi</option>
								<option value = "1" <c:out value='<%=LANGUAGE.equalsIgnoreCase("1")?"selected":""%>'/>>English</option>	
								
							</select><input type="hidden" name="btnClick"
												id="btnClick"
												value="<c:out value='<%=request.getParameter("btnClick")!=null ? request.getParameter("btnClick"):""%>'/>"
												/></td></tr>
								<tr align="center">
									<td align="center" colspan="6"><input type="button" class="butts" value="Generate" style="height: 20px;" onclick="submitPage();" /></td>
								</tr>
							</tbody></table></fieldset></center>
						
						<div id="reportData">
						<table width="100%" align="center">
							<thead>
								<th> Number of Acknowledgement Prepared
								<c:out value='<%=LANGUAGE.equalsIgnoreCase("1")?"in (English)":""%>'/>
								<c:out value='<%=LANGUAGE.equalsIgnoreCase("2")?"in (Hindi)":""%>'/>
								:</th>
							</thead>
							<tbody id="content">
							 <tr>
							 <td> <c:out value='<%=ACKCOUNT %>' />
							 </td>
							 </tr>
							</tbody>
						</table>
				</div>
						
						</td>
					</tr>
				</table>
				</td>
			</tr>
						</table>
  </form> 
</body>
</html>