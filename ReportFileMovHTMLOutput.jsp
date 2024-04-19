<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.MastersReportBean"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MRSECTT</title>
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
<LINK  href="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 
<link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/themes/base/ui.all.css" rel="stylesheet" />
<link type="text/css" href="${pageContext.request.contextPath}/theme/dtree.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dateFormatter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/ui.tabs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>

<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/engine.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/util.js'></SCRIPT>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/CustomReportFileMovDAO.js'></SCRIPT>	
<%
ArrayList<MastersReportBean> mstarr = (ArrayList <MastersReportBean>) request.getAttribute("mstarr");
ArrayList arrh = new ArrayList();
ArrayList arrDT = new ArrayList();
ArrayList arr = new ArrayList();
String colwidth="650~";
String save = request.getParameter("save")!=null? request.getParameter("save"):"";
String reportid = request.getParameter("reportid")!=null? request.getParameter("reportid"):"";
%>
 <style>

.resizing {
    cursor: col-resize;
}
 </style>
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
		
   
    div.quicksearch 
    {              
      padding-bottom: 10px;      
    }
    		

table.tablesorter thead tr .header {
	background-image: url(images/bg.gif);
	background-repeat: no-repeat;
	background-position: center right;
	cursor: pointer;
	/*background-color : #F6CED8;*/
}

table.tablesorter thead tr .headerSortUp {
	background-image: url(images/asc.gif);
}

table.tablesorter thead tr .headerSortDown {
	background-image: url(images/desc.gif);
}

/* div container containing the form  */
#searchContainer {
	margin:20px;
}
 
/* Style the search input field. */
#field {
	float:left;
	width:300px;
	height:27px;
	line-height:27px;
	text-indent:10px;
	font-family:arial, sans-serif;
	font-size:1em;
	color:#333;
	background: #fff;
	border:solid 1px #d9d9d9;
	border-top:solid 1px #c0c0c0;
	border-right:none;
}
 
/* Style the "X" text button next to the search input field */
#delete {
	float:left;
	width:16px;
	height:29px;
	line-height:27px;
	margin-right:15px;
	padding:0 10px 0 10px;
	font-family: "Lucida Sans", "Lucida Sans Unicode",sans-serif;
	font-size:22px;
	background: #fff;
	border:solid 1px #d9d9d9;
	border-top:solid 1px #c0c0c0;
	border-left:none;
}
/* Set default state of "X" and hide it */
#delete #x {
	color:#A1B9ED;
	cursor:pointer;
	display:none;
}
/* Set the hover state of "X" */
#delete #x:hover {
	color:#36c;
}
/* Syle the search button. Settings of line-height, font-size, text-indent used to hide submit value in IE */
#submit {
	cursor:pointer;
	width:50px;
	height: 21px;
	line-height:0;
	font-size:0;
	text-indent:-999px;
	color: transparent;
	background: url(images/icon_search12.png) no-repeat #4d90fe center;
	border: 1px solid #3079ED;
	-moz-border-radius: 2px;
	-webkit-border-radius: 2px;
}
/* Style the search button hover state */
#submit:hover {
	background: url(images/icon_search12.png) no-repeat center #357AE8;
	border: 1px solid #2F5BB7;
}
/* Clear floats */
.fclear {clear:both}

</style>
 <script>
 
 ///////////////////////////
 $(document).ready(function() {
    var pressed = false;
    var start = undefined;
    var startX, startWidth;
    
    $("thead th").mousedown(function(e) {
    	document.getElementById("saveFormat").disabled=false;
        start = $(this);
        pressed = true;
        startX = e.pageX;
        startWidth = $(this).width();
        //document.getElementById("testdiv").innerHTML="1.startX:"+startX+" <br />2.startWidth:"+startWidth+"";
        $(start).addClass("resizing");
    });
    
    $(document).mousemove(function(e) {
    	
        if(pressed) {
            $(start).width(startWidth+(e.pageX-startX));
            //document.getElementById("testdiv").innerHTML="1.startX:"+startX+" <br />2.startWidth:"+startWidth+"3.startWidth+(e.pageX-startX):"+(startWidth+(e.pageX-startX));
        }
    });
    
    $(document).mouseup(function() {
        if(pressed) {
            $(start).removeClass("resizing");
            pressed = false;
        }
    });
    
    $("#sorttable").tablesorter(); 
});
 
 
 ////////////////////////
function printme(obj,x)
{
	SaveFormat();
	if(x=='p')
		{
			obj.style.display="none";
			window.print();
		}
	 else if(x=='e')
		{
			document.forms[0].repExportType.value = "E";
			//alert("DONE");
			document.forms[0].submit();
		}
	else if(x=='d')
		{
			document.forms[0].repExportType.value = "D";
			//alert("DONE");
			document.forms[0].submit();
		}
	 else if(x=='c')
		{
			window.close();
		}
	
	
}
 var layout="P";
 function SaveFormat()
 {
 	//alert("<%=mstarr.get(0).getColwidth()%>");
 	var colwidth="";
 	var sumcolwidth=0;
 	
 	for(var i=0;i<="<%=mstarr.get(0).getArrh().size()%>";i++)
 	{
 		colwidth+=$(document.getElementById("col_"+i)).width()+"~";
 		sumcolwidth+=$(document.getElementById("col_"+i)).width();
 		//alert(i+":::::"+document.getElementById("trheader").childNodes[i]);
 	}
 	
 	//alert("total Width: " + sumcolwidth);
 	/*
 	if(layout=="P" && sumcolwidth>650)
 		alert("Size Exceeds Recommended Portrait A4 size");
 	else if(layout=="L" && sumcolwidth>850)
 		alert("Size Exceeds Recommended Landscape A4 size")*/
 	document.getElementById("colwidth").value=colwidth;	
 	
 	<%if("Y".equalsIgnoreCase(save)){%>
 	CustomReportFileMovDAO.updateReportLayout("<%=reportid%>",layout,colwidth,
 	function isSaved(data)
	{
			
		if(data.split("~")[0]=="0")
		{
			alert(data.split("~")[1]);
		} else
		{
			//alert("Layout Saved!!!");;
			//window.location.href="CustomReport.jsp";	
			document.getElementById("saveFormat").disabled=true;
			//alert("done");	
		}
	}
 	
 	);
 	<%}%>
 	
 	
 }
 
 function  setlayout(obj){
 	document.getElementById("saveFormat").disabled=false;
 	if(obj.value=="P")
 		{layout="P"; document.getElementById("sorttable").width="650";}
 	else
 		{layout="L";document.getElementById("sorttable").width="850";}
 }
 </script>
</head>

<body>
<form action="" method="post"> 
<table width="1002px" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
    <td height="4px"></td>
  </tr>
  <tr>
    <td style="background: white;">
    <table width="984" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center">

        <table width="984px" border="1" class="bor" cellspacing="0" cellpadding="0" style="border-collapse:collapse; margin-top:7px;">
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="tablehead"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="tableheadtext" style="font-size: 12px;">  <%= mstarr.get(0).getHeader() %></td>
                  </tr>
                </table></td>
              </tr>
            
              <tr>
              <td align="left" style=" padding-left:7px;" >
                  <div id="testdiv"></div>
                  <br />
                  <IMG width="20" height="20" src="${pageContext.request.contextPath}/images/pdf.png" onclick="printme(this,'d');" style="cursor: pointer;" />
                  <IMG width="20" height="20" src="${pageContext.request.contextPath}/images/Excel.png" onclick="printme(this,'e');" style="cursor: pointer;" /> 
             
            <span style="display: <%= "Y".equalsIgnoreCase(save)?"block":"none"%>"></span>
			<input type="button" value="Back to Report List" class="butts" onclick="window.location.href='CustomReportFileMov.jsp'" />
			<input type="button" value="Save Format" class="butts" id="saveFormat" onclick="SaveFormat();" />
			<input type="hidden" name="colwidth" id="colwidth" value="<%=mstarr.get(0).getColwidth() %>" />
			
			<input type="radio" <%=(mstarr.get(0).getLayout().length()==0 ||mstarr.get(0).getLayout().equalsIgnoreCase("P"))?"checked":""%> name="layout" name="layout" value="P"  onclick="setlayout(this);"> Portrait<input <%=mstarr.get(0).getLayout().equalsIgnoreCase("L")?"checked":""%> type="radio" name="layout" value="L" onclick="setlayout(this);"> Landscape
			
                  	<%
							for(int bn = 0 ; bn<mstarr.size();bn++){
							MastersReportBean bean = (MastersReportBean) mstarr.get(bn);
						 %>
						<br/><br/>
			<table id="sorttable" border="1" bordercolor="#7e7d7d" cellspacing="0" cellpadding="0" width="<%=mstarr.get(0).getLayout().equalsIgnoreCase("L")?"850":"650" %>">
							<% if(bean.hasData()){ 
								arrh = bean.getArrh();
								arrDT = bean.getArrDT();
								arr = bean.getArr();
							%>
						<thead style="display: table-header-group;">
							<%if(arr.size()/arrh.size()>999){%>
								<tr class="firstbg">
									<td colspan="<%= (arrh.size()+1) %>" align="left"> <img src="images/RED.gif" width="20" height="20" /> Only first 999 rows are displayed below. Please download excel sheet for complete data i.e. <%=arr.size()/arrh.size()%>  &nbsp;rows</td>
								</tr>
								<%} %>
							<tr class="tab1" id="trheader" >
							<th style="font-size: 10px;" id="col_0" <%=mstarr.get(0).getColwidth().length()>=1?("width='"+mstarr.get(0).getColwidth().split("~")[0]+"'"):"" %> > S.No. </th>							
								<%for(int i=0;i< arrh.size();i++){ %>
									<th <%=mstarr.get(0).getColwidth().length()>=(i+1)?("width='"+mstarr.get(0).getColwidth().split("~")[i+1]+"'"):"" %> style="font-size: 10px;" id="col_<%=(i+1)%>"> <%= arrh.get(i).toString()%> </th>
								<%} %>
							</tr>
						</thead>
						<tbody>
							<%	int cnt=1;
								for(int i=0; (i< arr.size() && i < (999*arrh.size()));i++){ 
								if(i%arrh.size()==0 ){
								%>
								<tr class="<%= i%(2*arrh.size())==0?"trodd":"treven" %>">
									<TD style="padding-left: 2px; padding-right: 2px; font-size: 11px; font: Tahoma;" align="right"> <%= cnt++ %></TD>
								<%} %>
								
									<TD style="padding-left: 2px; padding-right: 2px; font-size: 11px; font: Tahoma;" <%= arrDT.get(i%arrh.size()).toString().equalsIgnoreCase("NUMBER")||arrDT.get(i%arrh.size()).toString().equalsIgnoreCase("DECIMAL")?"align='right'":"" %> > <%= arr.get(i).toString()%>  </TD>
								<%if((i+1)%arrh.size()==0){%>
								</tr>
								<%} %>							

							<%} } %>	
						</tbody>
						</table>
						<BR><BR><BR>
						<%} %>
					
			
			<DIV style="display:none; width: 400px; border: ridge; padding: 5px; margin: 5px; vertical-align: middle; text-align: justify;">
			<P class="readonly">
			This Div Generates all parameters in the HTTPRequest again so that we can generate Excel Sheet with previous data. 
			</P>
			<%
				java.util.Enumeration enumr = request.getParameterNames();
				while(enumr.hasMoreElements())
				{
					Object obj = enumr.nextElement();
					
					%>
						
						
						
						
					<%
					if(request.getParameterValues(obj.toString()).length>1){
					String [] test = request.getParameterValues(obj.toString());
					%>
						<BR><BR> &Dagger; <%= obj %>: <SELECT name="<%= obj %>" multiple="multiple">
					<%
						for(int i=0;i<test.length;i++){
						
					%>
						
						<OPTION value="<%= test[i] %>"> <%= test[i] %> </OPTION>
						
					<%
						}
						%> 
						</SELECT>
						<SCRIPT> fnSelectAll('<%=obj%>'); </SCRIPT>
						<%
					} else
					{
					%> 
						<BR><BR> &Dagger; <%= obj %> : <INPUT TYPE="TEXT"  name="<%= obj %>" value="<%= request.getParameter(obj.toString())!=null ? request.getParameter(obj.toString()).toString() : "" %>" /> 
						
					<%
					}
				}
				
				
			
			 %>
                  
                  
                  
                  <input type="text"  name="repExportType" id="repExportType" value="E" />
                  
                  
                  
                  
                  
                  
              </td>
              </tr>
              <tr>
              <td>&nbsp;</td>
              </tr>
            </table></td>
          </tr>
        </table>
</td>
      </tr>
       
      <tr>
        <td height="4"></td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
    
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>

   </td>
  </tr>
  
</table>
</form>
</body>
</html>
