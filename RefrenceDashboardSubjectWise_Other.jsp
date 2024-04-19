
 
 <!DOCTYPE html>

<%@page import="in.org.cris.mrsectt.dao.MstMessageDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%><html>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
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
String themecolor=sessionBean.getTHEMECOLOR()!=null?sessionBean.getTHEMECOLOR():"";

String rlycode = request.getParameter("rlycode")!=null?request.getParameter("rlycode"):"";
String finyear = request.getParameter("finyear")!=null?request.getParameter("finyear"):"";
ArrayList<CommonBean> arrMr = new MstMessageDAO().getMRData();
ArrayList<CommonBean> arrNote = new MstMessageDAO().getNoteData(sessionBean.getLOGINASROLEID());
ArrayList<CommonBean> arrClass = new MstMessageDAO().getRefClassData(sessionBean.getLOGINASROLEID());
ArrayList<CommonBean> arrMarquee = new MstMessageDAO().getMARQUEEData();
ArrayList<CommonBean> arrIq = new MstMessageDAO().getIQData(loginid);
ArrayList<CommonBean> arrMenuLevel = new MstMessageDAO().getMenuLevel(loginid);
ArrayList<CommonBean> RefDashBoardDetails = null;
if(themecolor.equalsIgnoreCase("H")){
RefDashBoardDetails = new CommonDAO().getRefDashBoardDetails_Other(sessionBean.getLOGINASROLEID());
}
else if (themecolor.equalsIgnoreCase("S")){
RefDashBoardDetails = new CommonDAO().getRefDashBoardDetails_OtherSubHeadWise(sessionBean.getLOGINNAME());
}
else if (themecolor.equalsIgnoreCase("R")){
	RefDashBoardDetails = new CommonDAO().getRefDashBoardDetails_OtherRailway(sessionBean.getLOGINNAME());
}

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
  var loginid = '<%=sessionBean.getLOGINID()%>';
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
	color: <%=theme.equals("3")?"#517d42":theme.equals("2")?"#EE934F":"#0273c1"%>;
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
				<font size="4px">Subject wise breakup :</font></td>
				<td >
				<div id="SubHdTabRight"></div>
				</td>
			</tr>
			<tr>
				
			</tr>
		
		</table>
		</td>
	</tr>
			</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"  style="margin-top: 0px;margin: 0px; height: 331px" >
	      <form action="RefrenceDashboardSubjectWise" method="POST">
    <input type ="hidden" value =<%= sessionBean.getLOGINASROLEID() %> name ="loginid">	
     <input type ="hidden" value =<%= sessionBean.getLOGINNAME() %> name ="loginname">	
     <input type ="hidden" value =<%= sessionBean.getTHEMECOLOR() %> name ="typeOfUser">					
        <button class="btn btn-large btn-primary" type="submit">Print in Excel</button>
        
      </form>
		 <% if(themecolor.equalsIgnoreCase("H")){%>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="RefrenceDashboardRoleWise.jsp">Officer Wise Breakup</a>
		<%} %>
			<tr>
				<td valign="top" id="SubHdCenLft">&nbsp;</td>
				<td width="100%" valign="top">
<!-- CHANGED ON 24_07_2012 -->
			<table width="100%" style="margin-top: 1px;margin: 1px;" border="1" hieght = '40%'  >
				<tr><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Subject Code</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Pending</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Under Process</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Complied</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Not Feasable</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Partially Complied</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Not Applicable</b></td><td style="border-bottom: dotted; border-width: 1px; border-color: blue"><b>Total</b></td></tr>
				<%
					for(int i=0;i<RefDashBoardDetails.size();i++){
						bn = (CommonBean) RefDashBoardDetails.get(i);
				if(themecolor.equalsIgnoreCase("H"))
				{
				if(i<RefDashBoardDetails.size()-1) {%>
				<tr>
				<td> <%=bn.getField9() %></td>
				<td><a href="DetailedListOfDashboard_Other.jsp?subjectCode=<%= bn.getField1() %>&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='A'" target='_blank'><%=bn.getField2() %></a></td>
				<td><a href="DetailedListOfDashboard_Other.jsp?subjectCode=<%= bn.getField1() %>&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='B'" target='_blank'><%=bn.getField3() %></a></td>
				<td><a href="DetailedListOfDashboard_Other.jsp?subjectCode=<%= bn.getField1() %>&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='C'" target='_blank'><%=bn.getField4() %></a></td>
				<td><a href="DetailedListOfDashboard_Other.jsp?subjectCode=<%= bn.getField1() %>&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='D'" target='_blank'><%=bn.getField5() %></a></td>
				<td><a href="DetailedListOfDashboard_Other.jsp?subjectCode=<%= bn.getField1() %>&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='E'" target='_blank'><%=bn.getField6() %></a></td>
				<td><a href="DetailedListOfDashboard_Other.jsp?subjectCode=<%= bn.getField1() %>&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='N'" target='_blank'><%=bn.getField7() %></a></td>
				<td><b><a href="DetailedListOfDashboard_Other.jsp?subjectCode=<%= bn.getField1() %>&loginID=<%= sessionBean.getLOGINASROLEID() %>&status=Total" target='_blank'><%=bn.getField8() %></a></b></td>
				</tr>
				<%} else {%>
			<tr>
			<td><b>Total</b></td>
			<td><b><a href="DetailedListOfDashboard_Other.jsp?subjectCode=All&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='A'" target='_blank'><%=bn.getField2() %></a></b></td>
			<td><b><a href="DetailedListOfDashboard_Other.jsp?subjectCode=All&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='B'" target='_blank'><%=bn.getField3() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_Other.jsp?subjectCode=All&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='C'" target='_blank'><%=bn.getField4() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_Other.jsp?subjectCode=All&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='D'" target='_blank'><%=bn.getField5() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_Other.jsp?subjectCode=All&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='E'" target='_blank'><%=bn.getField6() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_Other.jsp?subjectCode=All&loginID=<%= sessionBean.getLOGINASROLEID() %>&status='N'" target='_blank'><%=bn.getField7() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_Other.jsp?subjectCode=All&loginID=<%= sessionBean.getLOGINASROLEID() %>&status=Total" target='_blank'><%=bn.getField8() %></a></b></td>
				</tr>
				<%}%>
				<%} else if(themecolor.equalsIgnoreCase("S")) {
				if(i<RefDashBoardDetails.size()-1) {%>
				<tr>
				<td> <%=bn.getField9() %></td>
				<td><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='A'" target='_blank'><%=bn.getField2() %></a></td>
				<td><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='B'" target='_blank'><%=bn.getField3() %></a></td>
				<td><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='C'" target='_blank'><%=bn.getField4() %></a></td>
				<td><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='D'" target='_blank'><%=bn.getField5() %></a></td>
				<td><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='E'" target='_blank'><%=bn.getField6() %></a></td>
				<td><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='N'" target='_blank'><%=bn.getField7() %></a></td>
				<td><b><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status=Total" target='_blank'><%=bn.getField8() %></a></b></td>
				</tr>
				<%} else {%>
			<tr>
			<td><b>Total</b></td>
			<td><b><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='A'" target='_blank'><%=bn.getField2() %></a></b></td>
			<td><b><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='B'" target='_blank'><%=bn.getField3() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='C'" target='_blank'><%=bn.getField4() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='D'" target='_blank'><%=bn.getField5() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='E'" target='_blank'><%=bn.getField6() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='N'" target='_blank'><%=bn.getField7() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_OtherSubHead.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status=Total" target='_blank'><%=bn.getField8() %></a></b></td>
				</tr>
				<%}%>
				
				<%} else if(themecolor.equalsIgnoreCase("R")) {
					if(i<RefDashBoardDetails.size()-1) {%>
					<tr>
					<td> <%=bn.getField9() %></td>
					<td><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='A'" target='_blank'><%=bn.getField2() %></a></td>
					<td><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='B'" target='_blank'><%=bn.getField3() %></a></td>
					<td><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='C'" target='_blank'><%=bn.getField4() %></a></td>
					<td><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='D'" target='_blank'><%=bn.getField5() %></a></td>
					<td><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='E'" target='_blank'><%=bn.getField6() %></a></td>
					<td><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status='N'" target='_blank'><%=bn.getField7() %></a></td>
					<td><b><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=<%= bn.getField1() %>&loginName=<%= sessionBean.getLOGINNAME() %>&status=Total" target='_blank'><%=bn.getField8() %></a></b></td>
					</tr>
					<%} else {%>
				<tr>
				<td><b>Total</b></td>
				<td><b><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='A'" target='_blank'><%=bn.getField2() %></a></b></td>
				<td><b><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='B'" target='_blank'><%=bn.getField3() %></a></b></td>
					<td><b><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='C'" target='_blank'><%=bn.getField4() %></a></b></td>
					<td><b><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='D'" target='_blank'><%=bn.getField5() %></a></b></td>
					<td><b><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='E'" target='_blank'><%=bn.getField6() %></a></b></td>
					<td><b><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status='N'" target='_blank'><%=bn.getField7() %></a></b></td>
					<td><b><a href="DetailedListOfDashboard_OtherSubRailway.jsp?subjectCode=All&loginName=<%= sessionBean.getLOGINNAME() %>&status=Total" target='_blank'><%=bn.getField8() %></a></b></td>
					</tr>
					<%}%>
					
					<%}%>
				<%}%>
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
		</td>
		
		<td width="15%" height="90%" valign="top">
			
			
								<table  width="100%"  height="100%" style="margin-top: 0px;margin: 0px;" >
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
			<p>&#8220;<%=bn.getField1() %>&#8221;</p>
			<cite>&ndash; <%=bn.getField2() %></cite>
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
						<%=bn.getField1() %></font></marquee>
		</td>
		</tr>
		<%} %>	
	
</table>

</body>
</html>
 
 
 
 