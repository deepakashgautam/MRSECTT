<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MenuBean"%><html>
<head>
<title>Menu Generation</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href="style/wrbcs.css" type="text/css" rel="stylesheet"/> 
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/Menu/simpletreemenu.js">

/***********************************************
* Simple Tree Menu- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

</script>
<script>
var pageflag=false;
function addUrl(menuid,flag){
document.all("menuid").value=menuid
document.all("flag").value=flag;

if(pageflag==false && flag=='H'){

document.getElementById("txtParentMenuid").value=menuid;
document.getElementById("dataid").style.display="block";
document.getElementById("menuid").style.display="none";
pageflag=true; 
}
}
function saveData(){
document.menugeneration.submit();

}
function Cancel(){
document.getElementById("txtMenuText").value="";
document.getElementById("txtParentMenuid").value="";
document.getElementById("txtURL").value="";
document.getElementById("dataid").style.display="none";
document.getElementById("menuid").style.display="block";
pageflag=false; 
}

function setHDR(){
var nodalflag=document.getElementById("cmbNodalFlag").value;
//alert(nodalflag);
if(nodalflag=='H'){
document.getElementById("hdrid").style.display="block";
}else {
document.getElementById("hdrid").style.display="none";
}

}
function setAddRemove(){
var action=document.getElementById("cmbAction").value;
if(action=='A'){
alert('1');
document.getElementById("addmenutab").style.display=false;

}else {
alert('2');
document.getElementById("addmenutab").style.display=true;

}


}


</script>



<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/simpletree.css" />
<%  ArrayList arr=new ArrayList();

  arr=(new CommonDAO()).getMasterMenuData("rfms");

 
  MenuBean bean;


  String lastflag="";
  String parentid="";
  String rootid="0"; 
  String arraymenuid="";
  boolean arrayflag=true;
  int level=1;

 %> 
<body>
<form action="${pageContext.request.contextPath}/MenuGenerationController" method="post" name="menugeneration"><input type="hidden"
	name="formAction"><input type="hidden"
	name="menuid"><input type="hidden"
	name="flag">
	<div id="menuid">
<table border="1" align="center" >
	<tbody>
		<tr>
			<td  align="left" valign="top"  width="434">
			<table border="1" align="center">
				<tbody>
					<tr>
						<td style="text-align: center" width="435">Master Menu</td>
					</tr>
					<tr>
						<td height="193" align="left" valign="top" width="435"><a
							href="javascript:ddtreemenu.flatten('treemenu1', 'expand')">Expand
						All</a> | <a
							href="javascript:ddtreemenu.flatten('treemenu1', 'contact')">Contract
						All</a>

						<ul id="treemenu1" class="treeview">
							<li><a href="#"  
								ondblclick="addUrl('0','H');">RFMS(Project)</a> 
							<ul>
								<% 

ArrayList  myarray=new ArrayList();
if(arr.size()>0){ 
for(int i=0;i<arr.size();i++){
bean=new MenuBean();
bean=(MenuBean)arr.get(i);
//System.out.println(bean.getLevel());
//System.out.println(level);
if(Integer.parseInt(bean.getLevel())==level) {%>
<li><a href="#"  ondblclick="addUrl('<%=bean.getMenuid() %>','<%=bean.getFlag() %>');"><%=bean.getMenutext() %></a></li>
<%}
else if(Integer.parseInt(bean.getLevel())>level){
%>
<ul><li><a href="#"  ondblclick="addUrl('<%=bean.getMenuid() %>','<%=bean.getFlag() %>');"><%=bean.getMenutext() %></a></li>
<%
} else { %>
<ul><li><a href="#"  ondblclick="addUrl('<%=bean.getMenuid() %>','<%=bean.getFlag() %>');"><%=bean.getMenutext() %></a></li>
<%} %>

		<%level=Integer.parseInt(bean.getLevel());}} %>				
		
		               	</ul>
							</li>
						</ul>
						
						<br style="clear: left" />


						<script type="text/javascript">

//ddtreemenu.createTree(treeid, enablepersist, opt_persist_in_days (default is 1))

ddtreemenu.createTree("treemenu1", true);


</script></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table></div>
<div id="dataid"  style="display: none">
<table>
<tr><td >Parent ID</td><td width="318" align="left"><input type="text"
			name="txtParentMenuid"  id="txtParentMenuid" 
			size="20" class="Inputbox"
			readonly value=""></td></tr>
	<tr>
		<td>Add/Remove</td>
		<td width="318" align="left"><select name="cmbAction"
			id="cmbAction" onchange="setAddRemove();">
			<option value="A" selected>ADD</option>
			<option value="R" >Remove</option>
		</select></td>
	</tr>
	<tr >
		<td colspan="2">
		<table border="1" id="addmenutab">
			<tbody>
				<tr>
		<td width="212">Nodal Flag</td>
		<td width="318" align="left"><select name="cmbNodalFlag" id="cmbNodalFlag" onchange="setHDR();">
			<option value="H">YES</option>
			<option value="U" selected>NO</option>
		</select></td>
	</tr>
	<tr id="hdrid" >
		<td >Header Text</td>
		<td  align="left"><input type="text" name="txtHDRText" id="txtHDRText"
			 class="Inputbox" value=""></td>
	</tr>
	<tr><td width="212">URL</td><td width="318" align="left"><input type="text"
			name="txtURL" id="txtURL" size="44"
			class="Inputbox" value=""></td></tr>
	<tr>
		<td width="212">Menu Text</td>
		<td width="318" align="left"><input type="text" name="txtMenuText"
			id="txtMenuText" size="44" class="Inputbox" value=""></td>
	</tr>
			</tbody>
		</table>
		</td></tr>
	
	<tr>
		<td  colspan="2" align="center" valign="middle"><input type="button" name="btnGo"
			class="butts" value="   Save   " onclick="saveData();" >
			<input type="button" name="btnGo0" class="butts" value="  Cancel   "
			onclick="Cancel()" ></td></tr>
</table>


</div>
</form>
</body>
</html>
