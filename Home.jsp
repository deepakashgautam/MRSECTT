<!DOCTYPE html>

<%@page import="in.org.cris.mrsectt.dao.MstMessageDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%><html>
<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
  
%>


 
 
<!-- <% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";
	//	String themeColor = session.getAttribute("themeColor")!=null? session.getAttribute("themeColor").toString(): "";

%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.quovolver.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />

<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/CommonDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<% 
CommonBean bn;
MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
String loginid=sessionBean.getLOGINID()!=null?sessionBean.getLOGINID():"";

String rlycode = request.getParameter("rlycode")!=null?request.getParameter("rlycode"):"";
String finyear = request.getParameter("finyear")!=null?request.getParameter("finyear"):"";
ArrayList<CommonBean> arrMr = new MstMessageDAO().getMRData();
ArrayList<CommonBean> arrNote = new MstMessageDAO().getNoteData(sessionBean.getLOGINASROLEID());
ArrayList<CommonBean> arrClass = new MstMessageDAO().getRefClassData(sessionBean.getLOGINASROLEID());
ArrayList<CommonBean> arrMarquee = new MstMessageDAO().getMARQUEEData();
ArrayList<CommonBean> arrIq = new MstMessageDAO().getIQData(loginid);
ArrayList<CommonBean> arrMenuLevel = new MstMessageDAO().getMenuLevel(loginid);


 %>
 
 <script type="text/javascript">
	$(document).ready(function() {
		
		$('blockquote').quovolver();
		
	});
	
	
	function showQuote(){
	//alert('hi'+window.document.getElementById("plus").style.display);
	if(window.document.getElementById("plus").style.display=='block'){
	window.document.getElementById("plus").style.display='none';
	window.document.getElementById("minus").style.display='inline';
	window.document.getElementById("messagetext").style.display='inline';
	//window.document.getElementById("loginid").style.display='inline';
	window.document.getElementById("saveimg").style.display='inline';
	}else{
	window.document.getElementById("plus").style.display='block';
	window.document.getElementById("minus").style.display='none';
	window.document.getElementById("messagetext").style.display='none';
	//window.document.getElementById("loginid").style.display='none';
	window.document.getElementById("saveimg").style.display='none';
	}
	}

function SQuotes()
{
  
  var messagetext = window.document.getElementById("messagetext").value;
 // var loginid = window.document.getElementById("loginid").value;
  var loginid = '<c:out value="<%=sessionBean.getLOGINID()%>"/>';
  //alert(messagetext);
  if(messagetext.length>0 && loginid.length>0){	
	 CommonDAO.SaveQuotes(messagetext,loginid, function (data){
  		if(data.split("~")[0]==1)
  		{
  			//alert(window.parent.location);
  			window.location.reload();
  			//alert(data.split("~")[1]);
  		} else {
  			alert(data.split("~")[1]);
  		}
  } 
  );
  }
}	

	
	
	
	</script>
<style type="text/css">


blockquote p { margin-bottom: 5px; font-style: italic; width: 180px; font-size: 13px;}
blockquote cite { font-style: normal; display: block; text-align: right; text-transform: uppercase; font-size: 9px; font-weight: bold; letter-spacing: 1px; font-family: Arial, Helvetica, sans-serif; }

/*
 |	Setting the width for the blockquotes is required
 |	to accurately adjust it's contianer
*/

blockquote {
	font-family: Georgia, Times, serif;
	width: 180px; /* required */
	margin: 0 auto;
	color: <c:out value='<%=theme.equals("3")?"#517d42":theme.equals("2")?"#EE934F":"#0273c1"%>' />;
	}
	
	


	
/*
 |	The #quote_wrap div is created
 |	by Quovolver to wrap the quotes
*/

#quote_wrap { 
	margin: 5px 0; padding: 5px; border: 0px solid #94c5ef;
	}
	



</style>	
</head>
<body>
<table width="100%" >
 	<tr>
		<td width="85%" valign="top">
			
			<table  width="100%" style="margin-top: 0px;margin: 0px;" >
				<tr>
		<td width="100%" valign="top" colspan="3">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"  style="margin-top: 0px;margin: 0px;" >
			<tr>
				<td >
				<div id="SubHdTabLft" ></div>
				</td>
				<td id="SubHdCenTop" >
				<font size="4px">MR's Message</font></td>
				<td >
				<div id="SubHdTabRight"></div>
				</td>
			</tr>
			<tr>
				<td valign="top" id="SubHdCenLft">&nbsp;</td>
				
				<td width="100%" valign="top">
				<div align="center" class="MessageCenterFormInput" style="margin-top: 3px; overflow-x: hidden; overflow-y:auto ; height: 70px;">
				<table width="100%" border="0" cellpadding="1" cellspacing="1" style="border-collapse: collapse;margin-top: 0px;margin: 0px;" >
					<%
					
					for(int i=0;i<arrMr.size();i++){
						bn = (CommonBean) arrMr.get(i);
				 %>
					<tr>
						<td align="left" width="10%">

					<img src="/MRSECTT/images/imageMRAshwini.jpg" width="80">
						</td>
						<td align="left" >
						<c:out value='<%=bn.getField1() %>'/>
						</td>
						<td align="left" width="4%">
						&nbsp;
						</td>
					</tr>
				<%} %>	
					
				</table>
				</div>
				</td>
				<td valign="top" id="SubHdCenRight">&nbsp;</td>
			</tr>
			<tr>
				<td>
				<div id="SubHdBtmLft">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmCen">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmRt">&nbsp;</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
			</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"  style="margin-top: 0px;margin: 0px;" >
			 <tr>
				<td>
				<div id="SubHdTopLft">&nbsp;</div>
				</td>
				<td valign="top" >
				<div id="SubHdTopCen"></div>
				</td>
				<td>
				<div id="SubHdTopRt">&nbsp;</div>
				</td>
			</tr>
			<tr>
				<td valign="top" id="SubHdCenLft">&nbsp;</td>
				<td width="100%" valign="top">
<!-- CHANGED ON 24_07_2012 -->
				<table width="100%" style="margin-top: 0px;margin: 0px;" >
				<tr><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Tenure</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>From</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>To</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Ref.Class</b></td></tr>
				<%
					for(int i=0;i<arrNote.size();i++){
						bn = (CommonBean) arrNote.get(i);
				 %>
				<tr><td><c:out value='<%=bn.getField1() %>'/></td><td><c:out value='<%=bn.getField2() %>'/></td><td><c:out value='<%=bn.getField3() %>'/></td>
				<td><c:out value='<%= arrClass.get(i).getField2() %>'/></td>
				</tr>
				<%} %>
				</table>
				</td>
				<td valign="top" id="SubHdCenRight">&nbsp;</td>
			</tr>

			<tr>
				<td>
				<div id="SubHdBtmLft">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmCen">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmRt">&nbsp;</div>
				</td>
			</tr>
		</table>
		<table  width="100%"   style="margin-top: 0px;margin: 0px;" >
				<tr>
		<td width="100%" valign="top" colspan="3">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"  style="margin-top: 0px;margin: 0px;" >
			<tr>
				<td>
				<div id="SubHdTopLft">&nbsp;</div>
				</td>
				<td valign="top" class="iconname1" height="10">
				<div id="SubHdTopCen" align="center"><b><font size="2">Search-Dynamic</font></b></div>
				</td>
				<td>
				<div id="SubHdTopRt">&nbsp;</div>
				</td>
			</tr>
			<tr>
				<td valign="top" id="SubHdCenLft">&nbsp;</td>
				
				<td width="100%" valign="top">
				<div align="center" class="MessageCenterFormInput" style="margin-top: 3px; ">
			<table  width="100%"  style="margin-top: 0px;margin: 0px;" >
				<tr>
				<td colspan="4"></td>
				<td align="center" width="141" height="125" background="${pageContext.request.contextPath}/images/icon-bgnew<c:out value='<%= theme %>'/>.gif">
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center" valign="top" height="50">
              <%
					for(int j=0;j<arrMenuLevel.size();j++){
						bn = (CommonBean) arrMenuLevel.get(j);
						if(bn.getField1().equalsIgnoreCase("1")) {
			  %>
                <a href="searchSubject.jsp?type=RO" onclick="" style="text-decoration: none"><img src="${pageContext.request.contextPath}/images/letter-icon50.png" alt="" width="50" height="50" border="0" /></a>
              <%} }%>
              </td>
              </tr>
              
              <tr>
                <td class="iconname"  ></td></tr>
									<tr>
										<td align="center" class="iconname1" valign="bottom">Reference-Create</td>
									</tr>
								</table></td>
				<td align="center" width="141" height="125" background="${pageContext.request.contextPath}/images/icon-bgnew<c:out value='<%= theme %>'/>.gif">
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center" height="50">
                <%
					for(int j=0;j<arrMenuLevel.size();j++){
						bn = (CommonBean) arrMenuLevel.get(j);
						if(bn.getField1().equalsIgnoreCase("2")) {
			  	%>
                <a href="searchSubject.jsp?type=FO" onclick="" style="text-decoration: none"><img src="${pageContext.request.contextPath}/images/files-icon50.png" alt="" width="50" height="50" border="0" /></a>
                <% }} %>
                </td>
              </tr>
              
              <tr>
                 <td class="iconname" ></td></tr>
									<tr>
										<td align="center" class="iconname1">File-Create</td>
									</tr>
								</table></td>
				<%-- <td align="center" width="141" height="125" background="${pageContext.request.contextPath}/images/icon-bgnew<%= theme %>.gif"> --%>
<%-- 					<table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center" height="50">
                <%
					for(int j=0;j<arrMenuLevel.size();j++){
						bn = (CommonBean) arrMenuLevel.get(j);
						if(bn.getField1().equalsIgnoreCase("3")) {
			  %>
                <a href="searchSubject.jsp?type=RF" onclick="" style="text-decoration: none"><img src="${pageContext.request.contextPath}/images/imageIO.png" alt="" width="70" height="50" border="0" /></a>
              <%}} %>  
                </td>
              </tr>
              
              <tr>
                <td class="iconname"  ></td></tr>
									<tr>
										<td class="iconname1">Reference-Forward</td>
									</tr>
								</table></td>
            				
            
            <td align="center" width="141" height="125" background="${pageContext.request.contextPath}/images/icon-bgnew<%= theme %>.gif">
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center" height="50">
                <%
					for(int j=0;j<arrMenuLevel.size();j++){
						bn = (CommonBean) arrMenuLevel.get(j);
						if(bn.getField1().equalsIgnoreCase("4")) {
			  %>
                <a href="searchSubject.jsp?type=FF" onclick="" style="text-decoration: none"><img src="${pageContext.request.contextPath}/images/imageIO.png" alt="" width="70" height="50" border="0" /></a>
              <%}} %>  
                </td>
              </tr>
              
              <tr>
                 <td class="iconname" ></td></tr>
									<tr>
										<td class="iconname1">File-Forward</td>
									</tr>
								</table></td> --%>
         
				<td colspan="4"></td>
			</tr>
				
			</table>
			</div>
				</td>
				<td valign="top" id="SubHdCenRight">&nbsp;</td>
			</tr>
			<tr>
				<td>
				<div id="SubHdBtmLft">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmCen">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmRt">&nbsp;</div>
				</td>
			</tr>
		</table>
			
		</td>
		</tr>
		</table></td>
		
		<td width="15%" height="90%" valign="top">
			
			
								<table  width="100%"  height="90%" style="margin-top: 0px;margin: 0px;" >
				<tr>
		<td width="100%" valign="top" colspan="3" height="90%">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="90%"  style="margin-top: 0px;margin: 0px;" >
			<tr>
				<td >
				<div id="SubHdTabLft" ></div>
				</td>
				<td id="SubHdCenTop" ><font size="4" face="Tahoma">
								Inspiration</font></td>
				<td >
				<div id="SubHdTabRight"></div>
				</td>
			</tr>
			<tr>
				<td valign="top" id="SubHdCenLft">&nbsp;</td>
				
				<td width="100%" valign="top" height="100%">
				<div align="left" class="MessageCenterFormInput" style="margin-top: 2px; height: 400px;width: 200px" >
					
					<%
					
					for(int i=0;i<arrIq.size();i++){
						bn = (CommonBean) arrIq.get(i);
				 %>
					<blockquote>
			<p>&#8220;<c:out value='<%=bn.getField1() %>'/>&#8221;</p>
			<cite>&ndash; <c:out value='<%=bn.getField2() %>'/></cite>
		</blockquote>
						
					
				<%} %><img border="0" src="script/img/nolines_plus.gif" width="18" id="plus" 
							height="18" onclick="showQuote();" style="display: block;"><img border="0" src="script/img/nolines_minus.gif" width="18"
							height="18" style="display: none;" id="minus" onclick="showQuote();">
							<br><textarea name="messagetext" id="messagetext" rows="8" style="overflow: auto;width: 195px;display: none;"></textarea>
					<span><img border="0" src="images/save.gif" width="19" height="19"
							style="display: none;" id="saveimg"
							onclick="this.disabled==true;SQuotes();"><br></span></div></td>
				<td valign="top" id="SubHdCenRight">&nbsp;</td>
			</tr>
			<tr>
				<td>
				<div id="SubHdBtmLft">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmCen">&nbsp;</div>
				</td>
				<td>
				<div id="SubHdBtmRt">&nbsp;</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
			</table>
					
					
			
		</td>
		</tr>
		<%
		for(int i=0;i<arrMarquee.size();i++){
		bn = (CommonBean) arrMarquee.get(i);
		%>
		<tr >
		<td colspan="2">
		<marquee direction="left"><font size="4px" class="marquee">
						<c:out value='<%=bn.getField1() %>'/></font></marquee>
		</td>
		</tr>
		<%} %>	
	
</table>

</body>
</html>
