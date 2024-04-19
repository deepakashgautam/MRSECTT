<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Reference &amp; File Management System</title>
<%
	HttpSession session = request.getSession();
	String theme = request.getParameter("theme") != null ? request.getParameter("theme"): "";
	String msg = StringFormat.nullString( (String) session.getAttribute("sessionmsg"));
%>
<link href="${pageContext.request.contextPath}/theme/Master<c:out value="<%=theme%>"/>.css" type="text/css" rel="stylesheet" />
<script>
var usertheme="";
function checkCookie() {
	usertheme=getCookie("usertheme");

//alert("::::::::"+usertheme);
	if (usertheme!=null && usertheme!="")
  {
  	if(usertheme!="<c:out value='<%=theme%>'/>")
  		window.location.href="Login.jsp?theme="+usertheme;
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

	

function checkFormValidity() {
		var msg="";
		if(document.loginForm.loginid.value=="type your username")
			msg+="UserName could not be blank\n";
			
		if(document.loginForm.loginid.value.indexOf(">")!=-1 )
			msg+="UserName cannot contain special characters\n";  
			
		if(document.loginForm.password.value=="type your password")
			msg+="Password could not be blank\n";  
			
		if(msg!="")	{
			alert("Please Check the following\n\n" + msg);
			return false;
		}
		document.loginForm.submit(); 
	}

function getInternetExplorerVersion()
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

  var msg = "Please use Internet Explorer.";

  var ver = getInternetExplorerVersion();

  if ( ver > -1 )

  {

    if (! (ver >= 7.0) ) 

     {

     	 msg = "Internet Explorer 7 and above is recommended.";

     	// return false;

     	//alert( msg );

     	//document.getElementById("button").disabled=true;

     	return false;

     }

  }	else

  {

  	//alert( msg );

  	//document.getElementById("button").disabled=true;

  	return false;

  }

  return true;

  

}



function setToField(thisObj, TOFIELD){	

	var fieldTo = window.document.getElementById(TOFIELD); 	

	fieldTo.value = thisObj.value;

	fieldTo.className = 'active';

} 





function setlabel(obj) {



  var $this = obj;

  if(obj.value === '') {

    obj.value = obj.title;

    obj.style.color="#A0A0A0";

  }  

  obj.onfocus = function() {

    if(obj.value === obj.title) {

      obj.value ='';

      obj.style.color="black";

    }

  };  

 

  obj.onkeydown = function() {

    if(obj.value === obj.title) {

      obj.value ='';

      obj.style.color="black";

    }

  };

  obj.onblur = function() {

    if(obj.value === '') {

      obj.value = obj.title;

      obj.style.color="#A0A0A0";

    }

  };

 



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



<body onLoad="checkVersion();document.getElementById('loginid').focus();setlabel(document.getElementById('loginid'));setlabel(document.getElementById('password'));" 
style="font-family: Verdana, Arial, Helvetica, sans-serif; margin:0px; padding:0px; background:url(/MRSECTT/images/main-bg3.gif) repeat-x; vertical-align:top; text-align:center;">



<form name="loginForm" method="post" action="/MRSECTT/LoginController"

	onsubmit="checkFormValidity">

<table width="1002px" border="0" align="center" cellpadding="0"

	cellspacing="0" style="margin-top: 0px;">

	<tr>

		<td height="10"></td>

	</tr>

	<tr>

		<td class="appbg">

		<table width="994" border="0" align="center" cellpadding="0"

			cellspacing="0" style="margin-top: 0px;">

			<tr>

				<td >

				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 0px;">

					<tr>

						<td height="80" valign="bottom" background="images/header<c:out value='<%=theme%>'/>.jpg"

							style="background-repeat: no-repeat; ">

						<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 0px;">

							<tr>

								

								<td width="10%">&nbsp;</td>

								<td width="7%">

								<div align="right"></div>

								</td>

							</tr>

						</table>

						</td>

					</tr>

					<tr>

						<td>

						<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 0px;">

							<tr>

								<td width="28"><img src="images/menu-arrow<c:out value='<%=theme%>'/>.jpg" alt=""

									width="28" height="26" /></td>

								<td align="left" background="images/menu-bg.jpg" class="welcome">Welcome to RFMS</td>

							</tr>

						</table>

						</td>

					</tr>

					<tr>

						<td>

						 <SPAN class="toptext" style="display: none;"> <marquee behavior=alternate>Application will not be available on 31/08/2012 from 18:00 to 21:00 for server maintenance.</marquee> </SPAN>

						<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 0px;">



							<tr>

								<td width="50%">

								

								<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 0px;">

									<tr>

										<td style="height: 2px;" height="16"></td>

									</tr>

									<tr>

										<td align="center" valign="middle" height="316"><img

											src="images/rfmslogin.jpg"  height="250"/></td>

									</tr>

									<tr>

										<td class="toptext"> </td>

									</tr>

								</table>

								</td>

								<td width="1"><img src="images/login-div.jpg" alt=""

									width="1" height="330" /></td>

								<td width="50%" valign="top">

								<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 0px;">

									<tr>

										<td height="46">&nbsp;</td>

										<td>&nbsp;</td>

										<td>&nbsp;</td>

									</tr>

									<tr>

										<td width="54">&nbsp;</td>

										<td valign="middle">

										<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 0px;">

											<tr>

												<td width="10"><img src="images/top-left-cor<c:out value='<%=theme%>'/>.jpg"

													alt="" width="10" height="9" /></td>

												<td height="9" class="themebgcollogin"></td>

												<td width="11"><img src="images/top-rgt-cor<c:out value='<%=theme%>'/>.jpg" alt=""

													width="11" height="9" /></td>

											</tr>

											<tr>

												<td class="themebgcollogin">&nbsp;</td> 

												<td height="23" class="loginhead themebgcollogin">Login

												to RFMS</td>

												<td class="themebgcollogin">&nbsp;</td>

											</tr>

											<tr>

												<td bgcolor="#c4c4c4"></td>

												<td height="1" bgcolor="#c4c4c4"></td>

												<td bgcolor="#c4c4c4"></td>

											</tr>

											<tr>

												<td><img src="images/left-side.jpg" alt="" width="10"

													height="152" /></td>

												<td background="images/logmid-bg.jpg">

												<table width="100%" border="0" cellspacing="0"

													cellpadding="0" style="margin-top: 0px;">

													<tr>

														<td height="14"></td>

													</tr>

													<tr>

														<td>

														<table width="100%" border="0" cellspacing="0"

															cellpadding="0" style="margin-top: 0px;">

															<tr>

																<td class="logintext">User Name :</td>

																<td width="195" height="32" valign="middle"

																	background="images/text-box.jpg">    <input

																	name="loginid" type="text" class="InputField"

																	id="loginid" size="25" onkeypress="checkEnter(event)" title="type your username"  /></td>

															</tr>

														</table>

														</td>

													</tr>

													<tr>

														<td>

														<table width="100%" border="0" cellspacing="0"

															cellpadding="0" style="margin-top: 0px;">

															<tr>

																<td class="logintext">Password :</td>

																<td width="195" height="32"

																	background="images/text-box2.jpg"><input

																	name="password" type="password" class="InputField"

																	id="password" title="type your password"

																	size="25" onkeypress="checkEnter(event)" /></td>

															</tr>

														</table>

														</td>

													</tr>

													<tr>

														<td height="3"></td>

													</tr>

													<tr>

														<td>

														<table width="100%" border="0" cellspacing="0"

															cellpadding="0" style="margin-top: 0px;">

															<tr>

																<td width="10%">&nbsp;</td>

																<td width="65%">&nbsp;</td>

																<td width="23%"><input type="button" class="Butt"

																	name="button" id="button" value="Submit"

																	onclick="checkFormValidity();" /></td>

																<td width="2%">&nbsp;</td>

															</tr>

														</table>

														</td>

													</tr>

													<tr>

														<td height="4"></td>

													</tr>

													<tr>

														<td height="1" background="images/dotted-bg.jpg"></td>

													</tr>

													<tr>

														<td class="forgottext">
						<font color="red" size="2" ><b><c:out value='<%=msg%>'/></b> </font></td>

													</tr>

													<tr>

														<td height="1" background="images/dotted-bg.jpg"></td>

													</tr>

													
													<tr>

														<td>&nbsp;</td>

													</tr>


												</table>

												</td>

												<td><img src="images/right-side.jpg" alt="" width="11"

													height="152" /></td>

											</tr>

											<tr>

												<td width="10" height="10"><img

													src="images/bot-left-cor.jpg" alt="" width="10" height="10" /></td>

												<td height="10" bgcolor="#c5c5c5"></td>

												<td><img src="images/bot-rgt-cor.jpg" alt="" width="11"

													height="10" /></td>

											</tr>



										</table>

										</td>

										<td width="96">&nbsp;</td>

									</tr>

									<tr>

										<td></td>

										<td class="footer">Internet Explorer 7 or above is recommended. </td>

										<td height="88">&nbsp;</td>

									</tr>

								</table>

								</td>

							</tr>



						</table>

						</td>

					</tr>





					<tr>

						<td>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">

							<tr>

								<td width="19" class="footleft"></td>

								<td class="footerbg">

								<table width="100%" border="0" cellspacing="0" cellpadding="0">

									<tr>

										<td class="footer">
										<a href="#">Copyright&copy; 2012, Reference and File Management System. All rights reserved</a></td>

									</tr>

									<tr>
										<td class="footer">Designed &amp; Developed by <a href="#"
											title="Center for Railway Information Systems"	onclick="window.open('http://cris.org.in')">CRIS</a></td>
									</tr>
								</table>
								</td>
								<td width="20" class="footright"></td>
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
	<tr ><td background="images/app-bg_bottom<c:out value='<%=theme%>'/>.gif" style="background-repeat: repeat-x">&nbsp;</td></tr>
</table>
<%session.removeAttribute("msg"); %>
</form>
</body>
</html>