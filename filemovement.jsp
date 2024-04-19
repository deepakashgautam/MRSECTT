<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.dao.FileMovementDAO"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT</title>
<!-- <link href="${pageContext.request.contextPath}/style/irpsm.css" type="text/css" rel="stylesheet"/>  --> 
<link type="text/css" href="${pageContext.request.contextPath}/jquery-ui-1.8.4.custom/css/blitzer/jquery-ui-1.8.5.custom.css" rel="stylesheet" />

<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->

</head>

<body>

<table>
<tr>  
      <td > 
      		<font size="3" > 
      			<b><i>Reference-Create - Add</i></b>
      		</font>
      		<img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">
      		<font size="3" > 
      			<b>Reference Movement</b>
      		</font>
      		<img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">    
       </td>
 </tr>
</table>
<table width="97%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="8px"></td>
                  </tr>
                  <tr>
                    <td align="center" style="padding-left:7px;">
 <%
	
	String q = request.getParameter("q");
 String tenure = request.getParameter("tenure");
	//System.out.println("tenure"+tenure);
ArrayList<CommonBean> obj = new FileMovementDAO().getFileMovement(q,tenure);
	%>
 
                     <table  width="50%" border="1" bordercolor="black" cellspacing="0" cellpadding="0">
                  	<tr align ="center" class="treven">
                  	<td><b>EOFFICE REF NO</td>
	<td><b>Sequence Number</td><td><b>From ID</td><td><b>From Name</td>
	<td><b>Move date</td><td><b>To ID</td><td><b>To Name</th><td><b>File No</td>
	
	</tr>
<% 
int j =1;
for(int i = 0; i<obj.size(); i++)
	{
CommonBean cob = (CommonBean)obj.get(i);
 
 %>
	<tr align ="center" class="treven">
	<td><c:out value='<%=cob.getField1() %>' /></td>
	<td><c:out value='<%=j %>' /></td>
	<td><c:out value='<%=cob.getField3() %>' /></td>
	<td><c:out value='<%=cob.getField4() %>' /></td>
	<td><c:out value='<%=cob.getField5() %>' /></td>
	<td><c:out value='<%=cob.getField6() %>' /></td>
	<td><c:out value='<%=cob.getField7() %>' /></td>
	<td><c:out value='<%=cob.getField8() %>' /></td>		
	</tr>
<%
j++;
	} %>

                  </table>                     
                   </td>
              </tr>                      
            </table>
</body>
</html>
	