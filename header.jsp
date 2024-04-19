<!DOCTYPE html>

<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<!-- <% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> 
<%
	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");

//	String theme=session.getAttribute("theme").toString();
//	String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";
	String loginid = session.getAttribute("loginid")!=null? session.getAttribute("loginid").toString(): "";

	CommonDAO cd=new CommonDAO();
	String newtheme = request.getParameter("newtheme") != null ? request.getParameter("newtheme"): "0";
//	String helptype=divcode.length()>0?"RLY":"22".equals(rlycode)?"RB":"RLY";
//	ArrayList arr = cd.getHeaderDetails(loginid);
	if(!"0".equalsIgnoreCase(newtheme))
		{
			new CommonDAO().setTheme(loginid,newtheme);
			theme=newtheme;
			session.setAttribute("theme",theme);
		}
 %>

<!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/ddsmoothmenu.css" /> -->
<!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/ddsmoothmenu<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/ddsmoothmenu.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  

<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/jquery-ui-1.8.4.custom/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/Menu/ddsmoothmenu.js"></script>


<script type="text/javascript">
//ddsmoothmenu.init({
	//mainmenuid: "smoothmenu1", //menu DIV id
	//orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	//classname: 'ddsmoothmenu', //class added to menu's outer DIV
	//customtheme: ["#1c5a80", "#18374a"],
	//contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
//})


ddsmoothmenu.init({
mainmenuid: "smoothmenu-ajax",

//customtheme: ["#1c5a80", "#18374a"], //override default menu CSS background values? Uncomment: ["normal_background", "hover_background"]
contentsource: ["smoothcontainer", "MENUold.jsp"] //"markup" or ["container_id", "path_to_menu_file"]
});

//ddsmoothmenu.init({
//mainmenuid: "smoothmenu-ajax1",
//customtheme: ["#1c5a80", "#18374a"], //override default menu CSS background values? Uncomment: ["normal_background", "hover_background"]
//contentsource: ["smoothcontainer1", "menu2.jsp"] //"markup" or ["container_id", "path_to_menu_file"]
//})

function setTheme(themeno)
{
	//alert(themeno);
	document.getElementById("themelist").style.display="none";
	document.getElementById("themelistin").style.display="none";
	setCookie("usertheme",themeno,365);
	window.location.href="${pageContext.request.contextPath}/HomeIframe.jsp?newtheme="+themeno;
}

function setCookie(c_name,value,exdays)
{
var exdate=new Date();
exdate.setDate(exdate.getDate() + exdays);
var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
document.cookie=c_name + "=" + c_value;
}

function LogoffMe(x)
{
	if(x=='home')
			document.getElementById("tmscontent").src="<c:out value='<%= request.getContextPath().toString() %>' />/Home.jsp";
	else if(x=='logoff')
		window.open("LogOffController","_self");
	else if(x=='pass')
		document.getElementById("tmscontent").src="<c:out value='<%= request.getContextPath().toString() %>'/>/UpdateProfile.jsp?open=self";
	else if(x=='help')
		{
			var ctx = "<c:out value='<%= request.getRequestURL() %>' />";
			ctx  = (ctx.substring(0, ctx.indexOf("TMS"))+"TMSHelp/WebHelp/Help.htm");
			url= ctx; //"http://203.176.113.28:9080/TMSHelp/WebHelp/Help.htm";
			window.open(url,"","toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,height=600,width=800,left=0,top=0,scrollbars=1,resizable=1");	
		}
	else if(x=='faqs')
		{
			url="FAQs.jsp";
			window.open(url,"","toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,height=460,width=700,left=0,top=0,scrollbars=1,resizable=1");
		}
	else if(x=='theme')
		{
		var pos = findPos(document.getElementById("themeimg"));
		document.getElementById("themelistin").style.top=(pos[1]+20)+"px";
		document.getElementById("themelistin").style.left=(pos[0]-100)+"px";
		//document.getElementById("themelist").style.display="block";
		document.getElementById("themelistin").style.display="block";
		//window.open(url,"","toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,height=600,width=800,left=0,top=0,scrollbars=1,resizable=1");
		//return;
		}	
}


</script>

</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0">
	<tr height="100%"  align="right">
		<td background="${pageContext.request.contextPath}/images/header<c:out value='<%= theme%>' />.jpg" width="100%" valign="top" height="100%" style="background-repeat: no-repeat; size: 100% auto;">
			<table width="100%" height="70" cellpadding="0" cellspacing="0" >
				<tr>
					<td align="left" rowspan="2" valign="top" width="26%">
        			</td>
				<td align="left" valign="top" width="63%"></td>
				<td align="right" valign="top" nowrap="nowrap"><font color="white"><b><c:out value='<%= sessionBean.getLOGINNAME() %>'/> (<c:out value='<%=sessionBean.getLOGINASROLENAME() %>'/>)</b></font>
					</td>
					<td align="right" valign="top" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
				<td align="left" valign="middle" width="63%">
<!--	<div class="TmsTitle"><marquee behavior="scroll" scrolldelay="1" scrollamount="3"><font color="red">Application will not be available from 07/08/2012:11:30 to 07/08/2012:14:00 due to maintenance purpose.</font></marquee></div> -->
		<div class="TmsTitle" align="center"><b><c:out value='<%= sessionBean.getTENUREOFFICENAME() %>'/></b></div>
	        			</td>
				<td width="100%" align="right" nowrap="nowrap"><nobr>
						<img onclick="LogoffMe('home');" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/home-icon.png">
						<img src="images/themes.png" alt=" change theme " id="themeimg" border="0" onclick="LogoffMe('theme')" style="cursor: pointer;"/>
						<img onclick="LogoffMe('pass');" style="cursor: pointer; width: 20px;; height: 20px;" src="${pageContext.request.contextPath}/images/changePass.png">
						<img onclick="LogoffMe('logoff');" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/logout.png" alt="Change Password" title="ChangePassword">
					</nobr></td>
					<td align="right" valign="top" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</table>	
		</td>
	</tr>
	
</table>
<!--  <%="  -->"%>
<div id="themelist" class="transparent_class" style="display: none; position: absolute; top:0px; left: 0px; width: 150%;height: 1500; background: white;">
</div>
<div  id="themelistin" align="center" style="display: none; vertical-align: middle; text-align: center; position: absolute; left: 550px; background-color: white; width: 200px; "> 
<table  width="100%" border="1" bordercolor="#cfcfcf" cellspacing="0" cellpadding="0"	style="border-collapse: collapse; border: solid 1px #cfcfcf; vertical-align: middle;">
	<tr class="tab1" ><td>Click on Theme to Selects</td><td><span style="cursor: pointer;" onclick='document.getElementById("themelistin").style.display="none";'>X</span></td></tr>
	<tr class="tab1" style="background-color: #2d7fd1; cursor: pointer;"><td colspan="2" onclick="setTheme('');">Default Theme</td></tr>
	<tr class="tab1" style="background-color: #ff7300; cursor: pointer;"><td colspan="2" onclick="setTheme(2);">Orange Theme</td></tr>
	<tr class="tab1" style="background-color: #79b764; cursor: pointer;"><td colspan="2" onclick="setTheme(3);">Green Theme</td></tr>
</table>

</div>
<%="  <!--"%>  -->
<!--Top Banner Ends-->
<div id="smoothcontainer">
<noscript>
<a href="#"></a>
</noscript>
</div>
</body>
</html>
