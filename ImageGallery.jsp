<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.ImageGalleryDAO"%>
<%@page import="in.org.cris.mrsectt.util.Encrypt"%>
<%@ taglib prefix="enc" uri="/WEB-INF/encrypt.tld"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%><html>
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="IBM Software Development Platform">
<meta http-equiv="Content-Style-Type" content="text/css">
<title>ImageGallery</title>
<script type="text/javascript">
  var GB_ROOT_DIR = "./iwebalbumfiles/";
</script>
<script type="text/javascript" src="iwebalbumfiles/ajs.js"></script>
<script type="text/javascript" src="iwebalbumfiles/ajs_fx.js"></script>
<script type="text/javascript" src="iwebalbumfiles/gb_scripts.js"></script>
<script type="text/javascript" src="iwebalbumfiles/iwebalbumpics.js"></script>
<link href="iwebalbumfiles/gb_styles.css" rel="stylesheet" type="text/css" />

<!-- <link href="iwebalbumfiles/style.css" rel="stylesheet" type="text/css" />  -->
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<c:out value="<%=theme%>"/>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->

<%
	String eform = request.getParameter("eform")!=null ? request.getParameter("eform") : (request.getAttribute("eform")!=null ? request.getAttribute("eform").toString() : "");
	//String REFID = StringFormat.nullString( (String)request.getAttribute("refId1"));
	String REFID = request.getParameter("refId1")!=null ? request.getParameter("refId1") : (request.getAttribute("refId1")!=null ? request.getAttribute("refId1").toString() : "");
	//System.out.print("Encode value "+Encrypt.decrypt(request.getParameter("refId")));
	String refId = request.getParameter("refId")!=null ? Encrypt.decrypt(request.getParameter("refId")) : (request.getAttribute("refId")!=null ? request.getAttribute("refId").toString() : "");
	String refIdenc = request.getParameter("refId")!=null ? request.getParameter("refId") : (request.getAttribute("refId")!=null ? request.getAttribute("refId").toString() : "");
	
	
	
	String type = request.getParameter("type")!=null ? request.getParameter("type") : (request.getAttribute("type")!=null ? request.getAttribute("type").toString() : "");
	String flg = request.getParameter("flg")!=null ? Encrypt.decrypt(request.getParameter("flg")) : (request.getAttribute("flg")!=null ? request.getAttribute("flg").toString() : "");
	String flgnoenc = request.getParameter("flgnoenc")!=null ? request.getParameter("flgnoenc") : (request.getAttribute("flgnoenc")!=null ? request.getAttribute("flgnoenc").toString() : "");
	flg = flg.length()>0 ? flg : flgnoenc;
	//System.out.println("refId"+refId);
	//System.out.println("REFID"+REFID);
	refId = REFID.length()>0 ? REFID: refId;
	//System.out.println("refId"+refId);
	ArrayList arr = (new ImageGalleryDAO()).getImageList(refId,type);
%>

<script language="javascript">

function checkform()
{
	var filename = document.upform.uploadfile.value;
	if(document.upform.uploadfile.value)
	{
		var ext = filename.substring(filename.lastIndexOf('.') + 1);
		if( ext == "jpg" || ext == "JPG" || ext == "pdf" || ext == "PDF")
		{
			//return true;
		}else
		{
			alert("Upload JPG or PDF Files Only");
			window.document.upform.reset();
			document.upform.uploadfile.focus();
			return false;
		}
		
       // window.document.upform.action = "UploadServ?refId=<%=refId%>";
       	show_div();
		window.document.upform.Submit.disabled = "true";
		window.document.upform.submit();
         
	}else
	{
		alert("Please specify file to import. ");
	}
}

document.onkeyup = KeyCheckup;       
function KeyCheckup()
{
  var KeyID = event.keyCode;
  //alert(KeyID);
  if(KeyID=="120")
  {
	  checkform();
  }
}

function clearform()
{
	window.document.upform.reset();
}

function show_div()
{
	window.document.getElementById("progress").style.display = 'block';
}

function hide_div()
{
	window.document.getElementById("progress").style.display = 'none';
}
var	ip = "<c:out value='<%=request.getContextPath() %>'/>";
//var ip="http://203.176.113.28:9080/";
var tmparray=new Array();
<% 
	int cnt=0;
	for(int i=0;i<arr.size();i++)
	{
	//if(arr.get(i).toString().split("~")[1].equalsIgnoreCase("jpg")){
%>
		tmparray[<%=cnt++%>]="<c:out value='<%= arr.get(i)%>'/>";
<%//}
	}
%>

var iWebAlbumPhotos = new Array();
for(k=0; k<tmparray.length; k++)
{
//	if(tmparray[k].split("~")[1]=="jpg")
		iWebAlbumPhotos[k]={"caption": ""+tmparray[k].split("~")[0], "url": ""+ip+"/ImageServlet?refId=<c:out value='<%=refIdenc%>'/>&file="+tmparray[k].split("~")[0], "comment": ""};
//	else
//		iWebAlbumPhotos[k]={"caption": ""+tmparray[k].split("~")[0]+".pdf", "url": ""+ip+"/ImageServlet?refId=<%=refIdenc%>&file="+tmparray[k]+".pdf", "comment": ""};
}

</script>
</head>
<body>
<form method="post" id="upform" name="upform" action="/MRSECTT/UploadServ" enctype="multipart/form-data">
<div id="iwebalbum" align="center">
		<table border="0" cellpadding="0" cellspacing="0" class="iwebalbum_table">
		<tr>
<%
	for(int i=0; i<arr.size(); i++) 
	{ 
%>
			<td align="center" >
				<table border="0" cellspacing="0" cellpadding="0" class="iwebalbum_photobox_table">
					<tr>
						<td class="iwebalbum_photobox_table" align="center" valign="middle"> 
							<c:set var="fileName" value='<%=arr.get(i).toString().split("~")[0]%>'/>
							<% if(arr.get(i).toString().split("~")[1].equalsIgnoreCase("jpg")){ %>
							
							<a href="#">
							<img src="ImageServlet?refId=<%=refIdenc %>&file=<c:out value="${enc:encrypt(fileName)}" />_s.jpg&type=<%=type%>" alt="<%=arr.get(i).toString().split("~")[0] %>.jpg" onclick="window.open('ImageServlet?refId=<%=refIdenc %>&file=<c:out value="${enc:encrypt(fileName)}" />.jpg&type=<%=type%>','','toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,left=0,top=0,scrollbars=1,resizable=1');"  class="photobox" width="90" height="90" /></a>
							<%} else { %>
							<a href="#">
							<img src="${pageContext.request.contextPath}/images/pdf_icon.png" width="90" height="90" alt="<%=arr.get(i).toString().split("~")[0] %>.pdf" onclick="window.open('ImageServlet?refId=<%=refIdenc %>&file=<c:out value="${enc:encrypt(fileName)}" />.pdf&type=<%=type%>','','toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,left=0,top=0,scrollbars=1,resizable=1');" class="photobox" /></a>
							<%} %>
<%
	if(!("trackdiagram".equals(eform)))
	{
 %>
<% if(flg.length()>0) { }else {%>
					<br /><br />
						<a href="UploadServ?del=<%=arr.get(i).toString().split("~")[0] %>&eform=<%=eform %>&refId=<%=refId %>&type=<%=type %>">Delete</a>
<%} %>
<%
	}
%>
						</td>
					</tr>
				</table>
			</td>
<%
		if((i+1)%4==0 && i!=0) 
		{
%>
			</tr>
			<tr>
<%
		}
	} 
%>
		</tr>
		</table>
		</div>
		
		<div style="clear:both"></div>
		<div id="pages"></div>
		<div id="slideshow" style="display: none">
			<a href="#" onclick="return GB_showImageSet(iWebAlbumPhotos, 1, 'startslideshow')" title="Start Slideshow">Start slideshow</a>
		</div>
		
		<div style="display:none"><img src="${pageContext.request.contextPath}/iwebalbumfiles/indicator.gif" alt="loading" /></div>
		
<%
	if(!("trackdiagram".equals(eform)))
	{
%>		
		<table align="center">
		<tr>
			<td align="center" valign="middle" width="100%">
	<% if(flg.length()>0) { }else {%>
				<table id="data"  width="100%" align="center">
				<tr>
					<th align="center" ></th>
				</tr>
				<tr class="trodd">
					<td align="center" height="23" ></td>
				</tr>
				<tr class="treven">
					<td align="center" ></td>
				</tr>
				<tr>
				<td align="center" height="25" ><font color="black" style="font-weight: bold"> File <input size="60" type="file" class="drop" name="uploadfile" accept="image/gif,image/jpeg"></input></font></td>
				</tr>
				<tr class="treven">
					<td align="center" ><input class="butts" type="button" name="Submit" value="Upload" onclick="checkform(this);">
				<input type="hidden" name="refId"	value="<c:out value='<%=refId%>'/>"></input> 
				<input type="hidden" name="type"	value="<c:out value='<%=type%>'/>"></input> 
				<input class="butts" type="button" name="Reset" value="Cancel" onclick="window.close();"></input></td>
				</tr>
				</table>
	<%} %>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<div id="progress" style="color: red; font-weight: bold; font-size: 12px; display: none;">
					Uploading... Please wait. <br/> <img src="${pageContext.request.contextPath}/images/progress_bar.gif" />
				</div>
				<br/><br/><br/>
			</td>
		</tr>
		<tr align="center">
			<td colspan="2"></td>
		</tr>
		</table>
<%
	}
%>		
	</form>
	</body>
</html>