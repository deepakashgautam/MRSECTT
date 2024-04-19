<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MenuBean"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%><html>
<head>
<title>MenuAdministration</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/simpletree.css" />
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
if(checkMenu(menuid)){
document.all("formAction").value="ADD";
document.all("menuid").value=menuid
document.all("flag").value=flag;

if(pageflag==false){

document.menuadminstration.submit();
pageflag=true; 
}
}
}
function removeUrl(menuid,flag){

document.all("formAction").value="REMOVE";
document.all("menuid").value=menuid;
document.all("flag").value=flag;
if(pageflag==false){

document.menuadminstration.submit();
pageflag=true; 
}
}
function fetchData(btnClick) {
document.all("formAction").value=btnClick;
document.menuadminstration.submit();
}


function checkMenu(menuid){
//alert(document.getElementsByName("menuid1").length);
for(var i=0;i<document.getElementsByName("menuid1").length;i++){

if(document.getElementsByName("menuid1")[i].value==menuid){
alert('Selected Menu is already availiable in User Menu');
return false;
}
}
return true;
}
</script>



<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/simpletree.css" />
<%  ArrayList arr=new ArrayList();
     ArrayList arr1=new ArrayList();
     
  
 String formAction=request.getParameter("formAction")!=null ?request.getParameter("formAction"):"";
 String groupid=request.getParameter("txtGroupId")!=null ?request.getParameter("txtGroupId"):"";
 if(formAction.equalsIgnoreCase("CLEAR")){
 groupid="";
 }
 if(groupid.trim().length()>0){
  arr=(new CommonDAO()).getMasterMenuData("rfms");
  arr1=(new CommonDAO()).getUserMenuData(groupid);
  }
 
MenuBean bean;
   MenuBean bean1;
  String lastflag="";
  String parentid="";
  String rootid="0"; 
  String arraymenuid="";
  boolean arrayflag=true;
  
    String lastflag1="";
  String parentid1="";
  String rootid1="0"; 
  String arraymenuid1="";
  boolean arrayflag1=true;
  
  	String querylogin = "SELECT LOGINID, LOGINNAME, (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = LOGINASROLEID) LOGINASROLEID FROM MSTLOGIN WHERE NVL(ISACTIVELOGIN,'N')='Y'	"; 
	ArrayList<CommonBean> loginList = (new CommonDAO()).getSQLResult(querylogin, 3);
  
 %> 
<body>
<form action="/MRSECTT/MenuAdministrationController" method="post" name="menuadminstration">
<input type="hidden" name="formAction"><input type="hidden" name="menuid">
<input type="hidden" name="flag">
<table  align="center" border="0" cellpadding="0" cellspacing="0">
	<tbody align="center">
	
		<tr>
			<td height="22" align="right" valign="top" width="58"></td>
			<td height="22" align="left" valign="top" width="686"></td>
		</tr>
		<tr>
			<td align="center" valign="middle" width="58"><nobr><b>Login Id :</b></nobr></td>
			<td  align="left" valign="top" width="686"><select name="txtGroupId" id="txtGroupId" style="width: 110px">
					<option value="">-Select-</option>
					<%
							for(int i=0;i<loginList.size();i++){
							CommonBean beanCommon = (CommonBean) loginList.get(i);
							%>
					<option value="<%=beanCommon.getField1()%>" <%=beanCommon.getField1().equalsIgnoreCase(groupid)?"selected":""%>><%= beanCommon.getField2()+" - "+beanCommon.getField3() %></option>

					<%
						}%>
				</select>
				<input type="button" name="btnGo" style="display: none; background: #4d90fe;height: 21px;color: white;font-weight: bold"  value="   Go   " onclick="fetchData('GO');" <%=groupid.length()==0?"":"disabled" %>>
				<input type="button" name="btnClear" style="display: none; background: #4d90fe;height: 21px;color: white;font-weight: bold"  value="   Clear   " onclick="fetchData('CLEAR');">

				<input type="button" name="btnGo" style="height: 21px;color: white;font-weight: bold" class="butts"  value="   Go   " onclick="fetchData('GO');" <%=groupid.length()==0?"":"disabled" %>>
				<input type="button" name="btnClear" style="height: 21px;color: white;font-weight: bold" class="butts"  value="   Clear   " onclick="fetchData('CLEAR');"></td>
			
		</tr>
		<tr>
			<td colspan="2" align="center" valign="top" height="426">
			<table id="data" align="center">
				<tbody>
					<tr>
						<td width="338" style="text-align: center"><b>Master Menu</b></td>
						<td width="386" style="text-align: center"><b>User Menu</b></td>
					</tr>
					<tr>
						<td width="338" height="193" align="left" valign="top"><a
							href="javascript:ddtreemenu.flatten('treemenu1', 'expand')">Expand
						All</a> | <a
							href="javascript:ddtreemenu.flatten('treemenu1', 'contact')">Contract
						All</a>

						<ul id="treemenu1" class="treeview">
							<li>RFMS(Project)
							<ul>
								<% 

ArrayList  myarray=new ArrayList();
if(arr.size()>0){ 
for(int i=0;i<arr.size();i++){
bean=new MenuBean();
bean=(MenuBean)arr.get(i);
//System.out.println(bean.getRoot());
if(!bean.getRoot().equalsIgnoreCase(rootid)){

while( myarray.size()>0&& rootid.length()>0) {
myarray.remove(myarray.size()-1);%>
							</ul>
							</li>
							<%
} %>
							<li><a href="#"  
								ondblclick="addUrl('<%=bean.getMenuid() %>','<%=bean.getFlag() %>');"><%=bean.getMenutext() %></a>
								<input type='hidden' name='menuid0' id='menuid0' value='<%=bean.getMenuid() %>'/>
							<ul>
								<% myarray.add(bean.getMenuid());
}else if(bean.getFlag().equalsIgnoreCase("H")&& lastflag.equalsIgnoreCase("U")){
arrayflag=true;
while(arrayflag&& myarray.size()>0) {
//System.out.println("myarray.size()>>"+myarray.size());
arraymenuid=myarray.get(myarray.size()-1).toString();
if(!arraymenuid.equalsIgnoreCase(bean.getParentid())){
myarray.remove(myarray.size()-1);%>
							</ul>
							</li>
							<%
}else {
arrayflag=false;

}

}

myarray.add(bean.getMenuid());%>

							<li><a href="#" 
								ondblclick="addUrl('<%=bean.getMenuid() %>','<%=bean.getFlag() %>');"><%=bean.getMenutext() %></a>
								<input type='hidden' name='menuid0' id='menuid0' value='<%=bean.getMenuid() %>'/>
							<ul>
								<%
}
else if(bean.getFlag().equalsIgnoreCase("H")) {
myarray.add(bean.getMenuid()); %>
								<li><a href="#"
									ondblclick="addUrl('<%=bean.getMenuid() %>','<%=bean.getFlag() %>');"><%=bean.getMenutext() %></a>
									<input type='hidden' name='menuid0' id='menuid0' value='<%=bean.getMenuid() %>'/>
								<ul>
									<%
} else if(bean.getFlag().equalsIgnoreCase("U")&& lastflag.equalsIgnoreCase("U") && !parentid.equalsIgnoreCase(bean.getParentid()) ){
arrayflag=true;
while(arrayflag&& myarray.size()>0) {
arraymenuid=myarray.get(myarray.size()-1).toString();
if(!arraymenuid.equalsIgnoreCase(bean.getParentid())){
myarray.remove(myarray.size()-1);//System.out.println("myarray.size()>>"+myarray.size());%>
								</ul>
								</li>
								<%
}else {
arrayflag=false;

}

}
%>
								<li><a href="#"
									ondblclick="addUrl('<%=bean.getMenuid() %>','<%=bean.getFlag() %>');"><%=bean.getMenutext() %></a>
									<input type='hidden' name='menuid0' id='menuid0' value='<%=bean.getMenuid() %>'/></li>
								<%} else if(bean.getFlag().equalsIgnoreCase("U")){%>
								<li><a href="#"
									ondblclick="addUrl('<%=bean.getMenuid() %>','<%=bean.getFlag() %>');"><%=bean.getMenutext() %></a>
									<input type='hidden' name='menuid0' id='menuid0' value='<%=bean.getMenuid() %>'/></li>
								<%} %>

								<% parentid=bean.getParentid();lastflag=bean.getFlag();rootid=bean.getRoot();} } %>


							</ul>
							</li>
						</ul>
						<br style="clear: left" />


						<script type="text/javascript">

//ddtreemenu.createTree(treeid, enablepersist, opt_persist_in_days (default is 1))

ddtreemenu.createTree("treemenu1", true);
ddtreemenu.flatten('treemenu1', 'expand')

</script></td>
						<td width="338" height="193" align="left" valign="top"><a
							href="javascript:ddtreemenu.flatten('treemenu2', 'expand')">Expand
						All</a> | <a
							href="javascript:ddtreemenu.flatten('treemenu2', 'contact')">Contract
						All</a>

						<ul id="treemenu2" class="treeview">
							<li><%=groupid %><ul>
								<% 

ArrayList  myarray1=new ArrayList();
if(arr1.size()>0){ 
for(int i=0;i<arr1.size();i++){
bean1=new MenuBean();
bean1=(MenuBean)arr1.get(i);

if(!bean1.getRoot().equalsIgnoreCase(rootid1)){

while( myarray1.size()>0&& rootid1.length()>0) {
myarray1.remove(myarray1.size()-1);%>
							</ul>
							</li>
							<%
} %>
							<li><a href="#"
								ondblclick="removeUrl('<%=bean1.getMenuid() %>','<%=bean1.getFlag() %>');"><%=bean1.getMenutext() %></a>
								<input type='hidden' name='menuid1' id='menuid1' value='<%=bean1.getMenuid() %>'/>
							<ul>
								<% myarray1.add(bean1.getMenuid());
}else if(bean1.getFlag().equalsIgnoreCase("H")&& lastflag1.equalsIgnoreCase("U")){
arrayflag1=true;
while(arrayflag1&& myarray1.size()>0) {
arraymenuid1=myarray1.get(myarray1.size()-1).toString();
if(!arraymenuid1.equalsIgnoreCase(bean1.getParentid())){
myarray1.remove(myarray1.size()-1);%>
							</ul>
							</li>
							<%
}else {
arrayflag1=false;

}

}

myarray1.add(bean1.getMenuid());%>

							<li><a href="#"
								ondblclick="removeUrl('<%=bean1.getMenuid() %>','<%=bean1.getFlag() %>');"><%=bean1.getMenutext() %></a>
								<input type='hidden' name='menuid1' id='menuid1' value='<%=bean1.getMenuid() %>'/>
							<ul>
								<%
}
else if(bean1.getFlag().equalsIgnoreCase("H")) {
myarray1.add(bean1.getMenuid()); %>
								<li><a href="#"
									ondblclick="removeUrl('<%=bean1.getMenuid() %>','<%=bean1.getFlag() %>');"><%=bean1.getMenutext() %></a>
									<input type='hidden' name='menuid1' id='menuid1' value='<%=bean1.getMenuid() %>'/>
								<ul>
									<%
} else if(bean1.getFlag().equalsIgnoreCase("U")&& lastflag1.equalsIgnoreCase("U") && !parentid1.equalsIgnoreCase(bean1.getParentid()) ){
arrayflag1=true;
while(arrayflag1&& myarray1.size()>0) {
arraymenuid1=myarray1.get(myarray1.size()-1).toString();
if(!arraymenuid1.equalsIgnoreCase(bean1.getParentid())){
myarray1.remove(myarray1.size()-1);%>
								</ul>
								</li>
								<%
}else {
arrayflag1=false;

}

}
%>
								<li><a href="#"
									ondblclick="removeUrl('<%=bean1.getMenuid() %>','<%=bean1.getFlag() %>');"><%=bean1.getMenutext() %></a>
									<input type='hidden' name='menuid1' id='menuid1' value='<%=bean1.getMenuid() %>'/></li>
								<%} else if(bean1.getFlag().equalsIgnoreCase("U")){%>
								<li><a href="#"
									ondblclick="removeUrl('<%=bean1.getMenuid() %>','<%=bean1.getFlag() %>');"><%=bean1.getMenutext() %></a>
									<input type='hidden' name='menuid1' id='menuid1' value='<%=bean1.getMenuid() %>'/></li>
								<%} %>

								<% parentid1=bean1.getParentid();lastflag1=bean1.getFlag();rootid1=bean1.getRoot();} } %>


							</ul>
							</li>
						</ul>
						<br style="clear: left" />


						<script type="text/javascript">

//ddtreemenu.createTree(treeid, enablepersist, opt_persist_in_days (default is 1))

ddtreemenu.createTree("treemenu2", true);
ddtreemenu.flatten('treemenu2', 'expand')

</script></td>
					</tr>
				</tbody>
			</table>
			<td align="center"></td>
		<tr align="center"></tr>
	<tbody align="center"></tbody>
</table></form>
</body>
</html>
