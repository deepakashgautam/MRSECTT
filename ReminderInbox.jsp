<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.ReminderInbxoDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnReminder"%><HTML>
 <HEAD>
 <SCRIPT src='${pageContext.request.contextPath}/dwr/interface/ReminderInbxoDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dateFormatter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.min.js"></SCRIPT>



<LINK  href="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
 
 <% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
 <link type="text/css" href="${pageContext.request.contextPath}/theme/gmailStyleMenu/gmailStyleMenu.css" rel="stylesheet" />
 <link type="text/css" href="${pageContext.request.contextPath}/theme/dtree.css" rel="stylesheet" />
 <style type="text/css">
#frm .inactive{
		color:#999;
		}	 
	#frm .active{
		color:#000;
		}	 		
	#frm .focused{
		color:#000;
		background:#fffee7;
		}	 		
</style>
  <TITLE> Reminder Inbox/Outbox </TITLE>
  <META NAME="Generator" CONTENT="EditPlus">
  <META NAME="Author" CONTENT="">
  <META NAME="Keywords" CONTENT="">
  <META NAME="Description" CONTENT="">
  <% 
  
  MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
  
  ArrayList<TrnReminder> inboxReminderList = (new ReminderInbxoDAO()).getInboxReminderData(sessionBean.getTENUREID());
  ArrayList<TrnReminder> outBoxReminderList = (new ReminderInbxoDAO()).getOutBoxReminderData(sessionBean.getTENUREID());
  
  String queryReminderTo = " SELECT B.TENUREID, (SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.ROLEID ) ROLENAME "+ 
 							" FROM MSTTENURE B WHERE B.ISACTIVE = 'Y' AND TENUREID <> '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> reminderToList = (new CommonDAO()).getSQLResult(queryReminderTo, 2);
	
	String queryMarkingTo = " SELECT B.TENUREID, (SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.ROLEID ) ROLENAME "+ 
 							" FROM MSTTENURE B WHERE B.ISACTIVE = 'Y' AND TENUREID <> '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	String querySubject = "SELECT SUBJECTCODE, SUBJECTNAME, SUBJECTDEPT FROM MSTSUBJECT"; 
	ArrayList<CommonBean> subjectList = (new CommonDAO()).getSQLResult(querySubject, 3);
  
  %>
  
  
  <script>
  
  function confirmReminderAction(refId, index, thisObj){

	alert(' refId : '+ refId);
	alert(' index: '+ index);
	var INACTION   = window.document.getElementsByName("INACTION")[index].value;
	//var ACTION = thisObj.value
	alert(' INACTION: '+ INACTION);
	ReminderInbxoDAO.setInboxReminderAction(refId, INACTION, getReturnData);

	//alert('Table updated');	

} 
 



function getReturnData(data){
	//alert(data);
	window.location.reload();

}

function setOutBoxReminderAction(index, refId, inReminderFrom, outBoxReminderFrom, targetDate, subjectCode, subject ){

	//alert(' index: '+ index);
	//alert(' refId : '+ refId);
	var OUTBOXMARKTO  = window.document.getElementsByName("OUTBOXMARKTO")[index].value;
	var OUTBOXREMINDERREMARK  = window.document.getElementsByName("OUTBOXREMINDERREMARK")[index].value;
	var OUTACTION   = window.document.getElementsByName("OUTACTION")[index].value;
	//alert(' OUTBOXMARKTO: '+ OUTBOXMARKTO);
	//alert(' outBoxReminderFrom: '+ outBoxReminderFrom);
	//alert(' OUTBOXREMINDERREMARK: '+ OUTBOXREMINDERREMARK);
	//alert(' OUTACTION: '+ OUTACTION);
	//alert(' targetDate: '+ targetDate);
	//alert(' subjectCode: '+ subjectCode);
	//alert(' subject: '+ subject);
	
	if(OUTACTION =='RET'){
		OUTBOXMARKTO = inReminderFrom;
	}

	//alert(' OUTBOXMARKTO: '+ OUTBOXMARKTO);
	
	//return;	
	ReminderInbxoDAO.setoutBoxAction(refId, outBoxReminderFrom, OUTBOXMARKTO, OUTBOXREMINDERREMARK, targetDate, subjectCode, subject, OUTACTION, getOutReturnData);

}  

function getOutReturnData(data){
	//alert(data);
	window.location.reload();


}

function setReturnBoxAction(refId, index, returnMarkFrom, targetDate, subjectCode, subject){

	alert(' refId : '+ refId);
	alert(' index: '+ index);
	var MARKFROM   = '<%= sessionBean.getTENUREID()%>';
	var MARKTO   = window.document.getElementsByName("RETURNMARKTO")[index].value;
	var RETURNACTION   = window.document.getElementsByName("RETURNACTION")[index].value;
	var RETURNREMINDERREMARK   = window.document.getElementsByName("RETURNREMINDERREMARK")[index].value;
	alert(' returnMarkFrom: '+ returnMarkFrom);
	alert(' MARKFROM: '+ MARKFROM);
	alert(' MARKTO: '+ MARKTO);
	alert(' RETURNACTION: '+ RETURNACTION);
	alert(' RETURNREMINDERREMARK: '+ RETURNREMINDERREMARK);
	alert(' targetDate: '+ targetDate);
	alert(' subjectCode: '+ subjectCode);
	alert(' subject: '+ subject);
	
	ReminderInbxoDAO.setReturnBoxAction(refId, returnMarkFrom, MARKFROM, MARKTO,RETURNREMINDERREMARK,  targetDate, subjectCode, subject, RETURNACTION, getReturnBoxData);

	alert('Table updated');	
}  

function getReturnBoxData(data){
	//alert(data);
	window.location.reload();


}
  
  function getSearch(thisObj){
  		
  	var FILENOID =  window.document.getElementById("FILENO");
  	var SUBJECTCODEID =  window.document.getElementById("SUBJECTCODE");
  	var SUBJECTID =  window.document.getElementById("SUBJECT");
  	var MARKINGFROMID =  window.document.getElementById("MARKINGFROM");
  	var MARKINGTOID =  window.document.getElementById("MARKINGTO");
  	var REMARKSID =  window.document.getElementById("REMARKS");
  	var MARKINGDATEFROMID =  window.document.getElementById("MARKINGDATEFROM");
  	var MARKINGDATETOID =  window.document.getElementById("MARKINGDATETO");
  
  	
  	
  	var FILENO =  FILENOID.className=='active'?FILENOID.value:"";
  	var SUBJECTCODE = SUBJECTCODEID.value;
  	var SUBJECT =  SUBJECTID.className=='active'?SUBJECTID.value:"";
  	var MARKINGFROM =  MARKINGFROMID.value;
  	var MARKINGTO =  MARKINGTOID.value;
  	var REMARKS =  REMARKSID.className=='active'?REMARKSID.value:"";
  	var MARKINGDATEFROM =  MARKINGDATEFROMID.className=='active'?MARKINGDATEFROMID.value:"";
  	var MARKINGDATETO =  MARKINGDATETOID.className=='active'?MARKINGDATETOID.value:"";
  	
  	 	
 //Check for atleast One field for search
  	if(    getBlankValue(FILENOID)=='' && getBlankValue(SUBJECTCODEID)=='' && getBlankValue(SUBJECTID)=='' && getBlankValue(MARKINGFROMID)==''
  		   && getBlankValue(MARKINGTOID)=='' && getBlankValue(REMARKSID)=='' && getBlankValue(MARKINGDATEFROMID)=='' && getBlankValue(MARKINGDATETOID)==''
  	  ){
  	  	//nothing to search ...simply return 
  		//return false;
  	}
  
	ReminderInbxoDAO.getSearchFileInboxOutBox(FILENO,SUBJECTCODE, SUBJECT, MARKINGFROM, MARKINGTO,REMARKS,MARKINGDATEFROM,MARKINGDATETO,'<%=sessionBean.getTENUREID()%>', getData);  	
  }
  
  function getData(data){
	alert(data.length);
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	htmlText = ' <table id="data" width="100%"><tbody id="searchTd">';	
	htmlText += ' <tr><td align="left"><b>Total Records</b></td> <td align="center"><b>'+data.length+'</b></td></tr>';
	htmlText += ' <tr bgcolor="#F6CED8"><td align="center" ><b>File No.</b></td><td align="center" style="display:none" id="sth1"><b>Marked on</b></td><td align="center" style="display:none" id="sth2"><b>Received from</b></td><td align="center" style="display:none" id="sth3"><b>Sub.</b></td> <td align="center"  nowrap="nowrap"><b>Subject</b></td><td align="center" style="display:none" id="sth4"><b>Acceptance Date</b></td><td align="center" style="display:none" id="sth5"><b>Mark To</b></td><td align="center" style="display:none" id="sth6"><b>Marking Remarks</b></td><td align="center" style="display:none" id="sth7"><b>Action</b></td></tr>';
	
	for(var i=0; i < data.length; i++){
	var trclass = (i%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick=""> <td>'+data[i].field2+'</td><td style="display:none" id="std1"> '+data[i].field7+' </td><td style="display:none" id="std2"> '+data[i].field5+' </td><td style="display:none" id="std3"> '+data[i].field4+' </td><td>'+data[i].field3+'</td><td style="display:none" id="std4"> '+data[i].field10+' </td><td style="display:none" id="std5"> '+data[i].field6+' </td><td style="display:none" id="std6"> '+data[i].field8+' </td><td style="display:none" id="std7"> '+data[i].field9+' </td></tr>';
	}
	
	htmlText += '</tbody></table>';
	searchResult.innerHTML = htmlText;
}

function getBlankValue(thisObj){
	return (thisObj.className=='active')? thisObj.value:"";
	
}
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

   $(document).ready(function(){
  $("#MainTabDiv").tabs({ fx: { slide: true, fade: true, speed: 'normal' }  });
  label2value();
  });
  </script>
  
  
 </HEAD>
 <BODY>
<table width="100%" align="left" style="vertical-align: top;" height="100%">
	<tr>
		<td valign="top"><div >
		<table width="100%" align="Center"  style="vertical-align: middle;" height="80%">
			        <tbody>
			        <tr>  
      	<td colspan="3">
      	<font size="3" > 
      			<b><i>Reference-Forward</i> - Reminder Mail Box</b>
      		</font>
      		<img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" >                
        </td>
 	</tr><tr>
 		<tr>
 		
		<td width="*" valign="top" align="left" valign="top" height="100%">
		<fieldset id="Search" style="height: 100%">
		<table border="0" height="100%">
		<tr>
 			<td valign="top">
 			<table  border="0" >
			<tr>
				<td colspan="2"><img border="0" src="images/Search1.png"
								width="91" height="35"></td></tr>
				
			
			<tr>
				<td></td>
				<td align="right"></td>
			</tr>
			<tr>
				<td colspan="2"><label for="FILENO">File No.</label>	
				<input type="text" name="FILENO" id="FILENO" style="width: 230px"></td>
				
			</tr>
			<tr>
				<td align="left" valign="bottom"><nobr><label for="MARKINGDATEFROM">Marking Date From</label>
				<input type="text" name="MARKINGDATEFROM" id="MARKINGDATEFROM"
					style="width: 90px"  onblur="chkWorkDate(this);"><input type="button" class="calbutt" name="mrkdtfrm"
								onclick="Calender1(document.getElementById('MARKINGDATEFROM'))"></nobr></td>
				<td align="left" valign="bottom"><nobr><label for="MARKINGDATETO">Marking Date To</label>
				<input type="text" name="MARKINGDATETO" id="MARKINGDATETO"
					style="width: 90px"  onblur="chkWorkDate(this);"><input type="button" class="calbutt" name="mrkdtfrm"
								onclick="Calender2(document.getElementById('MARKINGDATETO'))"></nobr></td>
			</tr>
			<tr>
				<td align="left" valign="bottom"><select name="SUBJECTCODE"
					id="SUBJECTCODE" style="width: 110px">
					<option value="">Subject Code</option>
					<%
							for(int i=0;i<subjectList.size();i++){
							CommonBean beanCommon = (CommonBean) subjectList.get(i);
							%>
					<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>

					<%
						}%>
				</select></td>
				<td align="left" valign="bottom"><label for="SUBJECT">Subject</label> <input
					type="text" name="SUBJECT" id="SUBJECT" style="width: 110px"></td>
			</tr>
			
			<tr>
				<td align="left"><select name="MARKINGFROM" id="MARKINGFROM"
					style="width: 110px">
					<option value="">Received From</option>
					<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
					<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
					<%
						}%>
				</select></td>
				<td align="left">
					<select name="MARKINGTO" id="MARKINGTO" style="width: 110px"> 
						<option value="">Mark To</option>
						<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
								<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
							<%
						}%>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2"><label for="REMARKS">Remarks</label>
				<input type="text" name="REMARKS" id="REMARKS" style="width: 230px"></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td align="right"><input type="button" name="" value="&gt;&gt;"	onclick="getSearch(this);"></td>
			</tr>

		</table>
		</td>
					<td align="center">
						
					</td>
					</tr>
					</table>
		</fieldset>
		<div id="searchResult"></div>
		</td>

		<td width="5px;" bgcolor="#F2F3F1" onclick="showHideTD(this)" title="Show search criteria" style="cursor: pointer;" valign="top" id="showhidecol">
		<div id="scrollingDiv"><img src="images/next.gif"></div>
		</td>

		<td id="td2" width="80%" valign="top">
		
		<fieldset>
 <table id="data" width="100%" height="100%">
					<tbody id="Forwarded">
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
							<th>Remarks</th>
							<th>Action</th>
						</tr>
						
						<%
						for(int i=0; i<inboxReminderList.size();i++){
							TrnReminder inBoxBean = inboxReminderList.get(i);
							%>
						<tr class="odd">
							<td><%=inBoxBean.getREFNO() %></td>
							<td><%=inBoxBean.getREMINDERDATE() %></td>
							<td><%=inBoxBean.getREMINDERFROM() %></td>
							<td><%=inBoxBean.getSUBJECTCODE() %></td>
							<td><%=inBoxBean.getSUBJECT() %></td>
							<td><%=inBoxBean.getTARGETDATE() %></td>
							<td><%=inBoxBean.getREMINDERREMARK() %></td>
							<td> <select name="INACTION"  id="INACTION" style="width: 100px">
								<option value="" selected="selected"> -select-</option>
								<option value="RCD" <%="RCD".equalsIgnoreCase(inBoxBean.getREMINDERACTION())?"selected":""%>>Received</option>
								<option value="NOR" <%="NOR".equalsIgnoreCase(inBoxBean.getREMINDERACTION())?"selected":""%>>Not Received</option>
								
							</select><input type="button" value="confirm" class="butts" onclick="confirmReminderAction('<%=inBoxBean.getREFID()%>', '<%=i%>')" ></td>
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
							<td width="50%" style="border: 1px dotted;" valign="top">
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
								<th>Remarks</th>
								<th>Acceptance Date</th>
								<th>Mark To</th>
								<th>Reminder Remarks</th>
								<th>Action</th>
							</tr>

							<%
						for(int i=0; i<outBoxReminderList.size();i++){
							TrnReminder outBoxBean = outBoxReminderList.get(i);
							%>
							<tr class="odd">
								<td><%=outBoxBean.getREFNO() %></td>
								<td><%=outBoxBean.getREMINDERDATE() %></td>
								<td><%=outBoxBean.getREMINDERFROMNAME() %></td>
								<td><%=outBoxBean.getSUBJECTCODE() %></td>
								<td><%=outBoxBean.getSUBJECT() %></td>
								<td><%=outBoxBean.getTARGETDATE() %></td>
								<td><%=outBoxBean.getREMINDERREMARK() %></td>
								<td><%= outBoxBean.getREMINDERACTIONDATE()%></td>
								<td><select name="OUTBOXMARKTO" style="width: 120px"> 
										<option value="">-select-</option>
										<%
											for(int ii=0;ii<reminderToList.size();ii++){
											CommonBean beanCommon = (CommonBean) reminderToList.get(ii);
											%>
												<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField2()%></option>
											<%
										}%>
									</select>
									
								</td>
								<td><input type="text" name="OUTBOXREMINDERREMARK" value="<%= outBoxBean.getREMINDERREMARK()%>" ></td>
								
								<td><select name="OUTACTION" id="OUTACTION" style="width: 100px">
									<option value="" selected="selected">-select-</option>
									<option value="FOW" <%="FO".equalsIgnoreCase(outBoxBean.getREMINDERACTION())?"selected":""%>>Forward</option>
									<option value="RET" <%="RT".equalsIgnoreCase(outBoxBean.getREMINDERACTION())?"selected":""%>>Return</option>
								</select><input type="button" name="btnMARK" value="confirm"
									class="butts"
									onclick="setOutBoxReminderAction('<%=i%>','<%=outBoxBean.getREFID()%>', '<%=outBoxBean.getREMINDERFROM() %>' ,'<%=sessionBean.getTENUREID() %>', '<%=outBoxBean.getTARGETDATE()%>', '<%=outBoxBean.getSUBJECTCODE()%>', '<%=outBoxBean.getSUBJECT()%>'  );"></td>
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
		</tbody>
		</table>
		</div>
		</td></tr>
</table>
 
  
 </BODY>
</HTML>
