<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Minister for Railways Secretariat</title>
<%
String theme = request.getParameter("theme") != null ? request
			.getParameter("theme")
			: "";

 %>
<link href="${pageContext.request.contextPath}/style/irpsm<%=theme%>.css" type="text/css" rel="stylesheet" />

<script>
var usertheme="";
function checkCookie()
{

usertheme=getCookie("usertheme");
//alert("::::::::"+usertheme);
  if (usertheme!=null && usertheme!="")
  {
  	if(usertheme!="<%=theme%>")
  		window.location.href="Login_.jsp?theme="+usertheme;
  }
else 
  {
    setCookie("usertheme","",7);
    
  }
}

function setCookie(c_name,value,exdays)
{
var exdate=new Date();
exdate.setDate(exdate.getDate() + exdays);
var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
document.cookie=c_name + "=" + c_value;
}

function getCookie(c_name)
{
var i,x,y,ARRcookies=document.cookie.split(";");
for (i=0;i<ARRcookies.length;i++)
{
  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
  x=x.replace(/^\s+|\s+$/g,"");
  if (x==c_name)
    {
    return unescape(y);
    }
  }
}

checkCookie();
	history.forward();
	
	function checkEnter(e){ //e is event object passed from function invocation
		var characterCode //literal character code will be stored in this variable
		
		if(e && e.which){ //if which property of event object is supported (NN4)
			e = e
			characterCode = e.which //character code is contained in NN4's which property
		}
		else{
			e = event
			characterCode = e.keyCode //character code is contained in IE's keyCode property
		}
		
		if(characterCode == 13){ //if generated character code is equal to ascii 13 (if enter key)
			checkFormValidity();
			return false 
		}
		else{
			return true 
		}
		
	}
	
	function checkFormValidity()
	{
//		alert("hi");
//		alert(document.loginForm.loginid.value);
		var msg="";
		if(window.document.loginForm.loginid.value=="")
			msg+="UserName could not be blank\n";
		if(window.document.loginForm.loginid.value.indexOf(">")!=-1 )
			msg+="UserName cannot contain special characters\n";  
			
		if(window.document.loginForm.password.value=="")
			msg+="Password could not be blank\n";  
			
		if(msg!="")
		{
			alert("Please Check the following\n\n" + msg);
			return false;
		}
		//alert(document.loginForm.action);
		window.document.loginForm.submit(); 
		       
	}

function getInternetExplorerVersion()
// Returns the version of Internet Explorer or a -1
// (indicating the use of another browser).
{
  var rv = -1; // Return value assumes failure.
  if (navigator.appName == 'Microsoft Internet Explorer')
  {
    var ua = navigator.userAgent;
    var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
    if (re.exec(ua) != null)
      rv = parseFloat( RegExp.$1 );
  }
  return rv;
}
function checkVersion()
{
  window.document.getElementById("loginid").focus();
  var msg = "Please use Internet Explorer.";
  var ver = getInternetExplorerVersion();
  if ( ver > -1 )
  {
    if (! (ver >= 7.0) ) 
     {
     	 msg = "Internet Explorer 7 and above is recommended.";
     	// return false;
     	alert( msg );
     	//document.getElementById("button").disabled=true;
     	return false;
     }
  }	else
  {
  	alert( msg );
  	//document.getElementById("button").disabled=true;
  	return false;
  }
  return true;
  
}
</script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-26442246-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
</head>

<body onLoad="checkVersion();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="loginForm" method="post" action="${pageContext.request.contextPath}/LoginController" onsubmit="checkFormValidity">
<!-- ImageReady Slices (Login.jpg) -->
<table id="Table_01" width="800" height="601" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6">
			<img src="images/Login_01.jpg" width="800" height="461" alt=""></td>
	</tr>
	<tr>
		<td colspan="2" rowspan="6">
			<img src="images/Login_02.jpg" width="603" height="82" alt=""></td>
		<td width="128" height="21" colspan="2"><input type="text" name="loginid"  style="width: 125px; height: 18px;"></td>
		<td colspan="2" rowspan="6">
			<img src="images/Login_04.jpg" width="69" height="82" alt=""></td>
	</tr>
	<tr>
		<td colspan="2">
			<img src="images/Login_05.jpg" width="128" height="4" alt=""></td>
	</tr>
	<tr>
		<td width="128" height="20" colspan="2"><input type="password" name="password"  style="width: 125px; height: 18px;"></td>
	</tr>
	<tr>
		<td colspan="2">
			<img src="images/Login_07.jpg" width="128" height="7" alt=""></td>
	</tr>
	<tr>
		<td rowspan="2">
			<img src="images/Login_08.jpg" width="59" height="30" alt=""></td>
		<td width="69" height="20"><input type="submit" class="Butt" style="width: 68px; height: 18px;" name="button" id="button"	value="Submit" onclick="checkFormValidity();" /></td>
	</tr>
	<tr>
		<td>
			<img src="images/Login_10.jpg" width="69" height="10" alt=""></td>
	</tr>
	<tr>
		<td rowspan="2">
			<img src="images/Login_11.jpg" width="548" height="57" alt=""></td>
		<td width="192" height="11" colspan="4"></td>
		<td rowspan="2">
			<img src="images/Login_13.jpg" width="60" height="57" alt=""></td>
	</tr>
	<tr>
		<td colspan="4">
			<img src="images/Login_14.jpg" width="192" height="46" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="images/spacer.gif" width="548" height="1" alt=""></td>
		<td>
			<img src="images/spacer.gif" width="55" height="1" alt=""></td>
		<td>
			<img src="images/spacer.gif" width="59" height="1" alt=""></td>
		<td>
			<img src="images/spacer.gif" width="69" height="1" alt=""></td>
		<td>
			<img src="images/spacer.gif" width="9" height="1" alt=""></td>
		<td>
			<img src="images/spacer.gif" width="60" height="1" alt=""></td>
	</tr>
</table>
<!-- End ImageReady Slices -->
</form>
</body>
</html>
