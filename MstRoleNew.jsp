<%@page import="in.org.cris.mrsectt.util.StringFormat"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%><html>
<%@page import="in.org.cris.mrsectt.Beans.MstRole"%>
<html>
<head>
<title>Minister for Railways Secretariat</title>
<LINK href="theme/MasterGreen.css" rel="stylesheet" type="text/css">

<SCRIPT src="dwr/interface/MstRoleDAO.js"></SCRIPT>
<SCRIPT src="dwr/engine.js"></SCRIPT>
<SCRIPT src="dwr/util.js"></SCRIPT>

<%
	MstRole mstRoleBean = (request.getAttribute("mstRoleBean") != null) ? (MstRole) request
			.getAttribute("mstRoleBean")
			: new MstRole();
	//	String msg = session.getAttribute("isDataSaved") != null ? request.getParameter("isDataSaved") : "";
	String msg = StringFormat.nullString((String) session
			.getAttribute("msg"));

	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
	//System.out.println(serverDate);
%>

<script>
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
	
}

  function getSearch(thisObj){
  	
  	var ROLEIDFROM =  window.document.getElementById("ROLEIDFROM").value;
  	var ROLEIDTO =  window.document.getElementById("ROLEIDTO").value;
  	var ROLENAME =  window.document.getElementById("ROLENAME").value;
	MstRoleDAO.getSearchData(ROLEIDFROM, ROLEIDTO, ROLENAME, getData);  	
  }
  
  function getData(data){
	
	var searchResult = window.document.getElementById("searchResult");
	var htmlText = '';
	//alert(data.length);
	htmlText = ' <table id="data" width="100%">';	
	htmlText += ' <tr><td align="left" width="50%"><b>Total Records</b></td> <td align="center" width="50%"><b>'+data.length+'</b></td></tr>';
	htmlText += ' <tr bgcolor="#F6CED8"><td align="center" width="50%"><b>RoleID</b></td> <td align="center" width="50%"><b>Rolename</b></td></tr>';
	for(var loop=0; loop < data.length; loop++){
	
	var trclass = (loop%2==0)? "trodd":"treven";
		htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'"> <td onclick="maximizeResult('+data[loop].field1+')"> '+data[loop].field1+' </td> <td> '+data[loop].field2+' </td> </tr>';
	}
	
	
	htmlText += '</table>';
	searchResult.innerHTML = htmlText;
}
 
 function submitForm(btnclick) {
	window.document.frmMstRole.btnclick.value=btnclick;
	
	if("GO"==btnclick){
	    window.document.frmMstRole.method = "post";
		window.document.frmMstRole.submit();
	}
	if("N"==btnclick){
	    window.document.frmMstRole.method = "post";
		window.document.frmMstRole.submit();
	}
	
	if("S"==btnclick){
		if( chkblank(window.document.frmMstRole.ROLEID) && chkblank(window.document.frmMstRole.ROLENAME1)){
		window.document.frmMstRole.submit();
		}	 
	}
	
	if("C"==btnclick){
	    window.document.frmMstRole.method = "post";
		window.document.frmMstRole.submit();
	}
} 

function maximizeResult(ROLEID){
	window.document.frmMstRole.ROLEID.value = ROLEID;
	submitForm('GO');
}

function showDiv()
{
<%if (msg.length() > 3) {%>
	document.getElementById('updateDiv').style.display="block";
	document.getElementById('updateDivInner').style.display="block";
<%}%>
}

function hideDiv()
{
	document.getElementById('updateDiv').style.display="none";
	document.getElementById('updateDivInner').style.display="none";
}

function callMe()
{
	hideDiv();
}
</script>

</head>
<body onload="showDiv();document.frmMstRole.ROLEID.focus();">
<FORM action="MstRoleController" method="post" name="frmMstRole">
<table width="100%" id="data">
	<tr>
		<td>
		<table width="100%" align="Center" style="vertical-align: middle;"
			id="data">
			<tr>
				<td width="*" valign="top" align="left" bgcolor="#fdefe2" >
				<table id="data" width="100%">
					<tr>
						<td><font size="+1">Search</font></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td align="right"></td>
					</tr>
					<tr>
						<td align="left"><input type="text" name="ROLEIDFROM"
							size="15" id="ROLEIDFROM" value="Role Id ..."></td>
						<td align="right"><input type="text" size="15"
							name="ROLEIDTO" id="ROLEIDTO" value="... Role Id"></td>
					</tr>
					<tr>
						<td colspan="2"><input type="text" size="40" name="ROLENAME"
							id="ROLENAME" maxlength="10" value="Role Name ..."
							style=" background-image: url(images/search.png'); background-position: right;background-repeat: no-repeat;"></td>
					</tr>
				</table>

				<div id="searchResult"></div>
				</td>

				<td width="5px;" bgcolor="#fdefe2" onclick="showHideTD(this)"
					title="Show search criteria" style="cursor: pointer;" valign="top"
					id="showhidecol">
				<div id="scrollingDiv"><img src="images/next.gif"></div>
				</td>

				<td id="td2" width="80%">
				<table id="data" width="100%">
					<tr class="trevenRight">
						<td colspan="6" valign="middle"></td>
					</tr>
					<tr class="treven">
						<th align="left" colspan="6"><input type="hidden"
							name="btnclick" size="20"><font size="+1">Master -
						Role</font></th>
					</tr>
					<tr class="trevenRight">
						<td valign="middle" colspan="6"></td>
					</tr>
					<tr class="treven">
						<td></td>
						<td width="10%">Role ID<font color="red">*</font></td>
						<td width="90%"><input type="text" name="ROLEID" value="<%=mstRoleBean.getROLEID()%>" onkeypress="chknumeric();" maxlength="4" size="50"></td>
					</tr>
					<tr class="trodd">
						<td></td>
						<td>Role Name<font color="red">*</font></td>
						<td><input type="text" name="ROLENAME1" maxlength="20" size="50" value="<%=mstRoleBean.getROLENAME()%>"></td>
					</tr>
					<tr class="trevenRight">
						<td nowrap height="31" valign="middle" colspan="6"></td>
					</tr>
					<tr class="trevenRight">
						<td></td>
						<td nowrap height="31" valign="middle" colspan="6">
						<table border="1" width="236">
							<tbody>
								<tr>
									<td width="101"><input type="image" height="15 name="
										btnNEW="" class="butts1" onclick="submitForm('N')" src="images/new.gif">
									New&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td width="88"><input type="button" name="btnSAVE0"	class="butts1"
										style="background-image: url('images/save.gif'); width: 20; height: 20"
										onclick="submitForm('S')" value="      ">&nbsp;Save&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td width="102"><input type="image" name="btnCLEAR0" class="butts1" onclick="submitForm('C')"
										src="images/clear.gif">
									Clear&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		</tr>
</table>
</FORM>
</body>
</html>		