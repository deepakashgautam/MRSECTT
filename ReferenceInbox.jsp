<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnMarking"%>
<%@page import="in.org.cris.mrsectt.dao.ReferenceInbxoDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="com.ibm.ws.install.configmanager.actionengine.ant.utils.StringFormatUtils"%>
<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.dao.ReferenceInbxoDAO"%><html>
<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dtree.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/theme/dtree.css" rel="stylesheet" />

<SCRIPT	src='${pageContext.request.contextPath}/dwr/interface/ReferenceInbxoDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/theme/dtree.css" rel="stylesheet" />

<script type="text/javascript">
  
  <% 
  MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
  
  ArrayList<TrnMarking> inboxList = (new ReferenceInbxoDAO()).getInboxData(sessionBean.getLOGINASROLEID());
  ArrayList<TrnMarking> outBoxList = (new ReferenceInbxoDAO()).getOutBoxData(sessionBean.getLOGINASROLEID());
  
  String querySubject = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 3);
  
  /*
  String queryMarkingTo = " SELECT B.TENUREID, (SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.ROLEID ) ROLENAME "+ 
 							" FROM MSTTENURE B WHERE B.ISACTIVE = 'Y' AND TENUREID <> '"+sessionBean.getTENUREID()+"'";
 */							 
  
  String queryMarkingTo = " SELECT DISTINCT A.ROLEID, A.ROLENAME FROM MSTROLE A WHERE A.ROLEID <> '"+sessionBean.getLOGINASROLEID()+"'"; 
  ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
  
  String msg = StringFormat.nullString((String)session.getAttribute("msg"));
  
  %>
  
  function showHideTD(tobj){

		var img = tobj.childNodes[0].childNodes[0];
		if((img.src).match("next")=="next"){
			img.src="images/prev.gif";
			document.getElementById("showhidecol").title="Hide search criteria";
			window.location.href="#";
		}else{
			img.src="images/next.gif";
			document.getElementById("showhidecol").title="Show search criteria";
		}
		
		obj=document.getElementById("td2");

		if(obj.style.display=='none'){
			document.getElementById("td2").style.display='block';
			//document.getElementById("td3").style.display='none';
		}else{
			//document.getElementById("td3").style.display='block';
			document.getElementById("td2").style.display='none';
		}
	
	
	ShowSearch(obj);
}


function ShowSearch(obj){

if(document.getElementById("searchTd")!=null){
		var idno = document.getElementById("searchTd").getElementsByTagName('tr').length;
		//alert(idno);
		
		if(obj.style.display=='none'){
		
			document.getElementById("sth1").style.display='inline';
			document.getElementById("sth2").style.display='inline';
			document.getElementById("sth3").style.display='inline';
			document.getElementById("sth4").style.display='inline';
			document.getElementById("sth5").style.display='inline';
			document.getElementById("sth6").style.display='inline';
			document.getElementById("sth7").style.display='inline';
			//document.getElementById("sth8").style.display='inline';
		for(var i=0;i<idno-2;i++){
		
			document.getElementsByName("std1")[i].style.display='inline';
			document.getElementsByName("std2")[i].style.display='inline';
			document.getElementsByName("std3")[i].style.display='inline';
			document.getElementsByName("std4")[i].style.display='inline';
			document.getElementsByName("std5")[i].style.display='inline';
			document.getElementsByName("std6")[i].style.display='inline';
			document.getElementsByName("std7")[i].style.display='inline';
			//document.getElementsByName("std8")[i].style.display='inline';
			}
			
			document.getElementById("TDTREE").style.display='inline';
		
			
		
	}else{
			document.getElementById("sth1").style.display='none';
			document.getElementById("sth2").style.display='none';
			document.getElementById("sth3").style.display='none';
			document.getElementById("sth4").style.display='none';
			document.getElementById("sth5").style.display='none';
			document.getElementById("sth6").style.display='none';
			document.getElementById("sth7").style.display='none';
			//document.getElementById("sth8").style.display='none';
			for(i=0;i<idno-2;i++){
			document.getElementsByName("std1")[i].style.display='none';
			document.getElementsByName("std2")[i].style.display='none';
			document.getElementsByName("std3")[i].style.display='none';
			document.getElementsByName("std4")[i].style.display='none';
			document.getElementsByName("std5")[i].style.display='none';
			document.getElementsByName("std6")[i].style.display='none';
			document.getElementsByName("std7")[i].style.display='none';
			//document.getElementsByName("std8")[i].style.display='none';
			}
		
		document.getElementById("TDTREE").style.display='none';
	}
}

}  

function getSearch(thisObj){
  	
  		
  	var ROLEID ='<%= sessionBean.getLOGINASROLEID() %>';
  	var SEARCHREFNO = window.document.getElementById("SEARCHREFNO");
  	
  	var SUBJECTCODE =  window.document.getElementById("SUBJECTCODE");
  	var SUBJECT =  window.document.getElementById("SUBJECT");
  	var MARKINGFROM =  window.document.getElementById("MARKINGFROM");
  	var MARKINGTO =  window.document.getElementById("MARKINGTO");
  	//var REMARKS =  window.document.getElementById("REMARKS");
  	
	ReferenceInbxoDAO.getReferenceInboxSearchData(ROLEID, getBlankValue(SEARCHREFNO), getBlankValue(SUBJECTCODE), getBlankValue(SUBJECT), getBlankValue(MARKINGFROM), getBlankValue(MARKINGTO),  getData);  	
  }
  
  function getData(data){
	//alert(data.length);
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText  = ' <table id="data" width="100%"><tbody id="searchTd">';	
	htmlText += ' <tr><td align="left"><b>Total Records</b></td> <td align="center"><b>'+data.length+'</b></td></tr>';
	//htmlText += ' <tr><th align="center"><b>Ref No.</b></th> <th align="center"><b>Subject</b></th></tr>';
	htmlText += ' <tr><th align="center" ><b>Ref. No.</b></th><th align="center" style="display:none" id="sth1"><b>Marked on</b></th><th align="center" style="display:none" id="sth2"><b>Received from</b></th><th align="center" style="display:none" id="sth3"><b>Sub.</b></th> <th align="center"  nowrap="nowrap"><b>Subject</b></th><th align="center" style="display:none" id="sth4"><b>Acceptance Date</b></th><th align="center" style="display:none" id="sth5"><b>Mark To</b></th><th align="center" style="display:none" id="sth6"><b>Marking Remarks</b></th><th align="center" style="display:none" id="sth7"><b>Action</b></th></tr>';
	
	//var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		//refBean[loop] = data[loop]; 
		//alert('refBean.REFNO '+ refBean[loop].REFNO);
		
		var trclass = (loop%2==0)? "trodd":"treven";
		//htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick ="showMarkingTree('+trnRefBean.REFID+');"> <td> '+trnRefBean.REFNO+' </td> <td> '+trnRefBean.SUBJECT+' </td> </tr>';
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick="showMarkingTree('+trnRefBean.REFID+');"> <td>'+trnRefBean.REFNO+'</td><td style="display:none" id="std1"> '+trnRefBean.MARKINGBEAN.MARKINGDATE+' </td><td style="display:none" id="std2"> '+trnRefBean.MARKINGBEAN.MARKINGFROM+' </td><td style="display:none" id="std3"> '+trnRefBean.SUBJECTCODE+' </td><td>'+trnRefBean.SUBJECT+'</td><td style="display:none" id="std4"> '+trnRefBean.MARKINGBEAN.ACTIONDATE+' </td><td style="display:none" id="std5"> '+trnRefBean.MARKINGBEAN.MARKINGTO+' </td><td style="display:none" id="std6"> '+trnRefBean.MARKINGBEAN.MARKINGREMARK+' </td><td style="display:none" id="std7"> '+trnRefBean.MARKINGBEAN.ACTION+' </td></tr>';
			
		
	}
	
	htmlText += '</tbody></table>';
	//alert(htmlText);	
	searchResult.innerHTML = htmlText;
}

function showMarkingTree(REFID){
	//alert("refid is "+ REFID);
	
	var treeDiv  = window.document.getElementById("treeDiv");
	//alert(' treeDiv '+ treeDiv);
	
	var col = new Array("#d69203","#927344","#ac5e09","#805e38","#451800","#d69203","#927344","#ac5e09","#805e38","#451800");
	d = new dTree('d');
	
	d.add(0,-1,'<span class="treespan" name="treespan" style="color:'+col[2]+';">MR   - Forwarded on 28/12/2011 14:57:21</span>','11');
	d.add(1,0, '<span class="treespan" name="treespan" style="color:'+col[2]+';">MT   - Received on  28/12/2011 14:58:54</span>','10');
	
	//alert(d);
	d.openAll();
	treeDiv.innerHTML = d;
	
	
	
}

function getBlankValue(thisObj){
	return (thisObj.className=='active')? thisObj.value:"";
	
}  
  
  function confirmAction(refId, index, thisObj){

	alert(' refId : '+ refId);
	alert(' index: '+ index);
	var ACTION   = window.document.getElementsByName("INACTION")[index].value;
	//var ACTION = thisObj.value
	alert(' ACTION: '+ ACTION);
	ReferenceInbxoDAO.setInboxAction(refId, ACTION, getReturnData);

	//alert('Table updated');	

} 

function getReturnData(data){
	window.location.reload();
}

 
function setOutBoxMarkAction(index, refId, inMarkingFrom, outBoxMarkingFrom, targetDate, subjectCode, subject ){

	//alert(' index: '+ index);
	//alert(' refId : '+ refId);
	var OUTBOXMARKTO  = window.document.getElementsByName("OUTBOXMARKTO")[index].value;
	var OUTBOXMARKINGREMARK  = window.document.getElementsByName("OUTBOXMARKINGREMARK")[index].value;
	var OUTACTION   = window.document.getElementsByName("OUTACTION")[index].value;
	//alert(' OUTBOXMARKTO: '+ OUTBOXMARKTO);
	//alert(' outBoxMarkingFrom: '+ outBoxMarkingFrom);
	//alert(' OUTBOXMARKINGREMARK: '+ OUTBOXMARKINGREMARK);
	//alert(' OUTACTION: '+ OUTACTION);
	//alert(' targetDate: '+ targetDate);
	//alert(' subjectCode: '+ subjectCode);
	//alert(' subject: '+ subject);
	
	if(OUTACTION =='RET'){
		OUTBOXMARKTO = inMarkingFrom;
	}

	//alert(' OUTBOXMARKTO: '+ OUTBOXMARKTO);
	
	//return;	
	ReferenceInbxoDAO.setoutBoxAction(refId, outBoxMarkingFrom, OUTBOXMARKTO, OUTBOXMARKINGREMARK, targetDate, subjectCode, subject, OUTACTION, getOutReturnData);

}  

function getOutReturnData(data){
	//alert(data);
	window.location.reload();
}
  
function hideDiv(){
	window.document.getElementById('updateDiv').style.display="none";
	window.document.getElementById('updateDivInner').style.display="none";
}

function callMe(){
	hideDiv();
}
 
$(document).ready(function(){
    $("#MainTabDiv").tabs({ fx: { slide: true, fade: true, speed: 'normal' }  });
  //  $( "#tabs" ).tabs();
  
   
  }); 
 
function onBodyOnLoad(){

	label2value();	

} 
 
 </script>

</head>
<body onload="onBodyOnLoad();">
<form name="frm" id="frm" action="${pageContext.request.contextPath}/TrnReferenceController" method="post">
<table width="100%" align="left">
	<tr>  
      	<td colspan="4">
      	<font size="3" > 
      			<b><i>Reference-Forward</i> - Main Mail Box</b>
      		</font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11">                
        </td>
 	</tr>

	<tr>
		<td valign="top">
		<td width="*" valign="top" align="left" valign="top">
		<fieldset id="Search">
		<table border="0" >
			<tr>
				<td colspan="2"><img border="0" src="images/Search1.png"
								width="91" height="35"></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"></td></tr>
			<tr>
				<td align="left">
					<label for="SEARCHREFNO">Ref. No</label>
					<input type="text" name="SEARCHREFNO" id="SEARCHREFNO" size="15" style="text-transform: uppercase;"></td>
				<td align="right">
					<label for="REMARKS">Remarks</label> 
					<input type="text" name="REMARKS" id="REMARKS" size="15"></td>
				
			</tr>
			
			<tr>
				<td>
					<select name="SUBJECTCODE" id="SUBJECTCODE" style="width: 100px">
					<option value="">-Subject Code-</option>
						<%
							for(int i=0;i<subjectList.size();i++){
							CommonBean beanCommon = (CommonBean) subjectList.get(i);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
								
							<%
						}%>
					</select>	
				</td>
				<td align="right"><label for="SUBJECT">Subject</label> <input
					type="text" name="SUBJECT" id="SUBJECT" size="15"></td></tr>
			<tr>
				<td>
					<select name="MARKINGFROM" id="MARKINGFROM" style="width: 100px"> 
						<option value="">-Marking From-</option>
						<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
							<%
						}%>
					</select></td>
				<td>
					<select name="MARKINGTO" id="MARKINGTO" style="width: 100px"> 
						<option value="">-Marking To-</option>
						<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
							<%
						}%>
					</select>
				</td></tr>
			<tr>
				<td align="left"><label for="MARKINGDATE">Marking Date</label>
				<input type="text" name="MARKINGDATE" id="MARKINGDATE" size="15"></td>
				<td align="right"> 
				<input type="text" size="15"></td></tr>
			<tr>
				<td align="right"></td>
				<td align="right"><input type="button" name="" value="&gt;&gt;"	onclick="getSearch(this);"></td></tr>

		</table>

		<div id="searchResult"   style="height:200px; overflow:auto; overflow-x: hidden;"></div>
		</fieldset>
		</td>

		<td width="5px;" bgcolor="#F2F3F1" onclick="showHideTD(this)"
			title="Show search criteria" style="cursor: pointer;" valign="top"
			id="showhidecol">
		<div id="scrollingDiv"><img src="images/next.gif"></div>
		</td>

		<td id="td2" width="80%" valign="top">
		
		<fieldset>

		<table id="data" width="100%">
			<tbody>
				<tr height="50%">
					<td width="50%" style="border: 1px dotted;" valign="top">
					<fieldset>	<legend><b><font size="+1">Inbox</font></b></legend>		
				<table width="100%">
					<tbody>
						<tr class="treven">
							<th>Reference No. </th>
							<th>Marked On</th>
							<th>Received from</th>
							<th>Subject Code</th>
							<th>Subject</th>
							<th>Target Date</th>
							<th>Marking Remarks</th>
							<th>Action</th>
						</tr>
						
						<%
						for(int i=0; i<inboxList.size();i++){
							TrnMarking inBoxBean = inboxList.get(i);
							%>
						<tr class="odd">
							<td><%=inBoxBean.getREFNO() %></td>
							<td><%=inBoxBean.getMARKINGDATE() %></td>
							<td><%=inBoxBean.getMARKINGFROM() %></td>
							<td><%=inBoxBean.getSUBJECTCODE() %></td>
							<td><%=inBoxBean.getSUBJECT() %></td>
							<td><%=inBoxBean.getTARGETDATE() %></td>
							<td><%=inBoxBean.getMARKINGREMARK() %></td>
							<td> <select name="INACTION"  id="INACTION" style="width: 100px">
								<option value="" selected="selected"> -select-</option>
								<option value="RCD" <%="RCD".equalsIgnoreCase(inBoxBean.getACTION())?"selected":""%>>Received</option>
								<option value="NOR" <%="NOR".equalsIgnoreCase(inBoxBean.getACTION())?"selected":""%>>Not Received</option>
								
							</select><input type="button" value="confirm" class="butts" onclick="confirmAction('<%=inBoxBean.getREFID()%>', '<%=i%>')" ></td>
						</tr>
							<%
							
						}
						%>
						
						
					</tbody>
				</table>
				</fieldset>
					</td>

				</tr>

				<tr height="50%">
					<td width="50%" style="border: 1px dotted;">
					<fieldset>	<legend><b><font size="+1">Outbox</font></b></legend>
					
					<table width="100%">
						<tbody>
							<tr class="treven">
								<th>Reference No.</th>
								<th>Marked On</th>
								<th>Received from</th>
								<th>Subject Code</th>
								<th>Subject</th>
								<th>Target Date</th>
								<th>Marking Remarks</th>
								<th>Acceptance Date</th>
								<th>Mark To</th>
								<th> Remarks</th>
								<th>Action</th>
							</tr>

							<%
						for(int i=0; i<outBoxList.size();i++){
							TrnMarking outBoxBean = outBoxList.get(i);
							%>
							<tr class="odd">
								<td><%=outBoxBean.getREFNO() %></td>
								<td><%=outBoxBean.getMARKINGDATE() %></td>
								<td><%=outBoxBean.getMARKINGFROMNAME() %></td>
								<td><%=outBoxBean.getSUBJECTCODE() %></td>
								<td><%=outBoxBean.getSUBJECT() %></td>
								<td><%=outBoxBean.getTARGETDATE() %></td>
								<td><%=outBoxBean.getMARKINGREMARK() %></td>
								<td><%= outBoxBean.getACTIONDATE()%></td>
								<td><select name="OUTBOXMARKTO" style="width: 120px"> 
										<option value="">-select-</option>
										<%
											for(int ii=0;ii<markingToList.size();ii++){
											CommonBean beanCommon = (CommonBean) markingToList.get(ii);
											%>
												<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
											<%
										}%>
									</select>
									
								</td>
								<td><input type="text" name="OUTBOXMARKINGREMARK" value="<%= outBoxBean.getMARKINGREMARK()%>" ></td>
								
								<td><select name="OUTACTION" id="OUTACTION" style="width: 100px">
									<option value="" selected="selected">-select-</option>
									<option value="FOW" <%="FO".equalsIgnoreCase(outBoxBean.getACTION())?"selected":""%>>Forward</option>
									<option value="RET" <%="RT".equalsIgnoreCase(outBoxBean.getACTION())?"selected":""%>>Return</option>
								</select><input type="button" name="btnMARK" value="confirm"
									class="butts"
									onclick="setOutBoxMarkAction('<%=i%>','<%=outBoxBean.getREFID()%>', '<%=outBoxBean.getMARKINGFROM() %>' ,'<%=sessionBean.getLOGINASROLEID() %>', '<%=outBoxBean.getTARGETDATE()%>', '<%=outBoxBean.getSUBJECTCODE()%>', '<%=outBoxBean.getSUBJECT()%>'  );"></td>
							</tr>
							<%
							
						}
						%>


						</tbody>
					</table>
					</fieldset>
					</td>

				</tr>
			</tbody>
		</table>
		</fieldset>

		
		</td>
	</tr>
</table>

<!-- DO NOT DELETE BELOW THIS!!! Following will create model. It will get uncommented once jsp is processed. -->

<!--main Content Area Ends-->
<!--  <%=" Model Starts -->"%>
<DIV class="transparent_class" style="z-index:2000; background-color:#000; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 150%;" id="updateDiv">
	
</DIV>
<DIV style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:20px; top: 160px; display: none; width: 100%" id="updateDivInner">
<DIV class="pageheader" style="color: blue; font-family:tahoma ; z-index:5000; background-color: #FBD6B5; width: 400px; border: groove; text-align: center; background-image:url(images/top-hd-bar-bg.gif); background-repeat: repeat; " id="divLoading">
<%	if(msg.length()>3) { %>
			<BR><img src="images/<%= msg.substring(0,3)%>.gif"/><%= msg.substring(3) %> <BR><input type="button" value=" Ok " onclick="callMe()">
			<BR><BR>
<% } %>



</DIV>
</DIV>
<%="<!-- Model Ends "%>    -->
<!-- DO NOT ABOVE BELOW THIS!!! -->

	

<%session.removeAttribute("msg"); %>
</form>
</body>
</html>
