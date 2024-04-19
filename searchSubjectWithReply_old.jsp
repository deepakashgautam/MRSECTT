<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.org.cris.mrsectt.dao.TrnReferenceDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.org.cris.mrsectt.Beans.TrnReference"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Text Search - Reference</title>
  <link href="${pageContext.request.contextPath}/theme/sub/style.css" rel="stylesheet" type="text/css" />
  <LINK  href="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 
  <% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> <!-- <%= "-->" %> <link href="${pageContext.request.contextPath}/theme/Master<%=theme%>.css" type="text/css" rel="stylesheet"/> <%= "<!--" %>-->   <%= "<!--" %> <link href="${pageContext.request.contextPath}/theme/Master.css" type="text/css" rel="stylesheet"/>  <%= "-->" %>  <!-- <link type="text/css" href="${pageContext.request.contextPath}/theme/Master.css" rel="stylesheet" />  -->
  <link type="text/css" href="${pageContext.request.contextPath}/theme/autoSuggest.css" rel="stylesheet" />
  <link type="text/css" href="${pageContext.request.contextPath}/theme/jquery/jquery-ui-1.7.1.custom.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/validateinput.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery-ui.min.1.7.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/scripts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.tablefilter.js"></script>
	<SCRIPT type="text/javascript" src="${pageContext.request.contextPath}/theme/jquery/jquery.autocomplete.min.js"></SCRIPT>
    <SCRIPT src='${pageContext.request.contextPath}/dwr/interface/TrnReferenceDAO.js'></SCRIPT>
    <SCRIPT src='${pageContext.request.contextPath}/dwr/interface/TrnFileManagementDAO.js'></SCRIPT>
	<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
	<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
  
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
	font-family:tahoma, arial, sans-serif;
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
	font-family: "tahoma", "Lucida Sans", "Lucida Sans Unicode",sans-serif;
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

<script language=JavaScript> 
/*
 * This is the function that actually highlights a text string by
 * adding HTML tags before and after all occurrences of the search
 * term. You can pass your own tags if you'd like, or if the
 * highlightStartTag or highlightEndTag parameters are omitted or
 * are empty strings then the default <font> tags will be used.
 */
function doHighlight(bodyText, searchTerm, highlightStartTag, highlightEndTag) 
{
  // the highlightStartTag and highlightEndTag parameters are optional
  if ((!highlightStartTag) || (!highlightEndTag)) {
    highlightStartTag = "<font style='color:blue; background-color:yellow;'>";
    highlightEndTag = "</font>";
  }
  
  // find all occurences of the search term in the given text,
  // and add some "highlight" tags to them (we're not using a
  // regular expression search, because we want to filter out
  // matches that occur within HTML tags and script blocks, so
  // we have to do a little extra validation)
  var newText = "";
  var i = -1;
  var lcSearchTerm = searchTerm.toLowerCase();
  var lcBodyText = bodyText.toLowerCase();
    
  while (bodyText.length > 0) {
    i = lcBodyText.indexOf(lcSearchTerm, i+1);
    if (i < 0) {
      newText += bodyText;
      bodyText = "";
    } else {
      // skip anything inside an HTML tag
      if (bodyText.lastIndexOf(">", i) >= bodyText.lastIndexOf("<", i)) {
        // skip anything inside a <script> block
        if (lcBodyText.lastIndexOf("/script>", i) >= lcBodyText.lastIndexOf("<script", i)) {
          newText += bodyText.substring(0, i) + highlightStartTag + bodyText.substr(i, searchTerm.length) + highlightEndTag;
          bodyText = bodyText.substr(i + searchTerm.length);
          lcBodyText = bodyText.toLowerCase();
          i = -1;
        }
      }
    }
  }
  
  return newText;
}
 
 
/*
 * This is sort of a wrapper function to the doHighlight function.
 * It takes the searchText that you pass, optionally splits it into
 * separate words, and transforms the text on the current web page.
 * Only the "searchText" parameter is required; all other parameters
 * are optional and can be omitted.
 */
function highlightSearchTerms(searchText, treatAsPhrase, warnOnFailure, highlightStartTag, highlightEndTag)
{
  // if the treatAsPhrase parameter is true, then we should search for 
  // the entire phrase that was entered; otherwise, we will split the
  // search string so that each word is searched for and highlighted
  // individually
  if (treatAsPhrase) {
    searchArray = [searchText];
  } else {
    searchArray = searchText.split(" ");
  }
  
  if (!document.body || typeof(window.document.getElementById("reportData").innerHTML) == "undefined") {
    if (warnOnFailure) {
      alert("Sorry, for some reason the text of this page is unavailable. Searching will not work.");
    }
    return false;
  }
  
  var bodyText = window.document.getElementById("reportData").innerHTML;
  for (var i = 0; i < searchArray.length; i++) {
    bodyText = doHighlight(bodyText, searchArray[i], highlightStartTag, highlightEndTag);
  }
  
  window.document.getElementById("reportData").innerHTML = bodyText;
  return true;
}
 
 
/*
 * This displays a dialog box that allows a user to enter their own
 * search terms to highlight on the page, and then passes the search
 * text or phrase to the highlightSearchTerms function. All parameters
 * are optional.
 */
function searchPrompt(defaultText, treatAsPhrase, textColor, bgColor)
{
  // This function prompts the user for any words that should
  // be highlighted on this web page
  if (!defaultText) {
    defaultText = "";
  }
  
  // we can optionally use our own highlight tag values
  if ((!textColor) || (!bgColor)) {
    highlightStartTag = "";
    highlightEndTag = "";
  } else {
    highlightStartTag = "<font style='color:" + textColor + "; background-color:" + bgColor + ";'>";
    highlightEndTag = "</font>";
  }
  
  if (treatAsPhrase) {
    promptText = "Please enter the phrase you'd like to search for:";
  } else {
    promptText = "Please enter the words you'd like to search for, separated by spaces:";
  }
  
  searchText = prompt(promptText, defaultText);
 
  if (!searchText)  {
    alert("No search terms were entered. Exiting function.");
    return false;
  }
  
  return highlightSearchTerms(searchText, treatAsPhrase, true, highlightStartTag, highlightEndTag);
}
 
 
/*
 * This function takes a referer/referrer string and parses it
 * to determine if it contains any search terms. If it does, the
 * search terms are passed to the highlightSearchTerms function
 * so they can be highlighted on the current page.
 */
function highlightGoogleSearchTerms(referrer)
{
  // This function has only been very lightly tested against
  // typical Google search URLs. If you wanted the Google search
  // terms to be automatically highlighted on a page, you could
  // call the function in the onload event of your <body> tag, 
  // like this:
  //   <body onload='highlightGoogleSearchTerms(document.referrer);'>
  
  //var referrer = document.referrer;
  if (!referrer) {
    return false;
  }
  
  var queryPrefix = "q=";
  var startPos = referrer.toLowerCase().indexOf(queryPrefix);
  if ((startPos < 0) || (startPos + queryPrefix.length == referrer.length)) {
    return false;
  }
  
  var endPos = referrer.indexOf("&", startPos);
  if (endPos < 0) {
    endPos = referrer.length;
  }
  
  var queryString = referrer.substring(startPos + queryPrefix.length, endPos);
  // fix the space characters
  queryString = queryString.replace(/%20/gi, " ");
  queryString = queryString.replace(/\+/gi, " ");
  // remove the quotes (if you're really creative, you could search for the
  // terms within the quotes as phrases, and everything else as single terms)
  queryString = queryString.replace(/%22/gi, "");
  queryString = queryString.replace(/\"/gi, "");
  
  return highlightSearchTerms(queryString, false);
}
 
 
/*
 * This function is just an easy way to test the highlightGoogleSearchTerms
 * function.
 */
function testHighlightGoogleSearchTerms()
{
  var referrerString = "http://www.google.com/search?q=javascript%20highlight&start=0";
  referrerString = prompt("Test the following referrer string:", referrerString);
  return highlightGoogleSearchTerms(referrerString);
}
 
</script>



<script type="text/javascript">
<% 
String searchString = (request.getParameter("searchString")!=null) ?request.getParameter("searchString"): "";
MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
String type = (request.getParameter("type")!=null) ?request.getParameter("type"): "";
String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");  	
//ArrayList<TrnReference> sSub = (new TrnReferenceDAO()).getSearchSub(searchString);
 	String queryRefClass = "SELECT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"'"; 
	ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
%>

 $(document).ready(function() {
 
    //Setup the sorting for the table with the first column initially sorted ascending
    //and the rows striped using the zebra widget
       // $("#tableOne").tablesorter({ sortList: [[0, 0]], widgets: ['zebra'] });
	label2value();
	
	
});  
var searchString ="";
var sphrase = "0";
var advstxt = "";

function getsearchDetail(){
if(chkblank(document.frm.FDATE) && chkblank(document.frm.TDATE)) {
  var searchStringid = window.document.getElementById("searchString");
 // alert(searchStringid.className.replace("ac_input","").Trim());  
  var fdateid = window.document.getElementById("FDATE");  
  var tdateid = window.document.getElementById("TDATE");  
  var refClass = window.document.getElementById("REFCLASS").value;  
   var suggestData = window.document.getElementById("suggest");
    sphrase = document.getElementById('SPHRASE').value;  
    advstxt = document.getElementById('advsearchtxt').value;  
	 searchString = searchStringid.className.replace("ac_input","").Trim()=='active'?searchStringid.value:"";
	//var fdate = fdateid.className=='active'?fdateid.value:"";
	//var tdate = tdateid.className=='active'?tdateid.value:"";
	var fdate = fdateid.value;
	var tdate = tdateid.value;
	//alert(fdate);
  // alert(searchString);
	if(searchString.length<2){
		alert('Please Enter at least Two Character');
		return;
	}
	//alert(suggestData.childNodes.length);
	var lengthDiv=suggestData.childNodes.length;
	if(lengthDiv==0){
	suggestData.innerHTML += '<span><a href="#" onclick=window.document.getElementById("searchString").value=this.innerHTML>'+searchString+'</a><br><span>';
	}else{
	for(var i=0;i<lengthDiv;i++){
	//alert(suggestData.childNodes[i].firstChild.innerHTML);
	if(suggestData.childNodes[i].firstChild.innerHTML==searchString){
	suggestData.removeChild(suggestData.childNodes[i]);
	break;
	}
	}
	suggestData.innerHTML += '<span><a href="#" onclick=window.document.getElementById("searchString").value=this.innerHTML>'+searchString+'</a><br><span>';
	}
	//suggestData.innerHTML += '<span><a href="#" onclick=window.document.getElementById("searchString").value=this.innerHTML>'+searchString+'</a><br><span>';
	//alert('<%=type%>');
	var roleID = '<%= sessionBean.getLOGINASROLEID() %>';
	var ISCONF = '<%= sessionBean.getISCONF() %>';
	var ISREPLY = '<%= sessionBean.getISREPLY() %>';
	if('<%=type%>'=='RO'){
	TrnReferenceDAO.getSearchSubRefReply(searchString,fdate,tdate,sphrase,roleID,ISCONF,ISREPLY,advstxt,refClass,dataSearchDetail);
	}
	if('<%=type%>'=='RF'){
	TrnReferenceDAO.getSearchSub1(searchString,fdate,tdate,sphrase,roleID,dataSearchDetail1);
	}
	if('<%=type%>'=='FO'){
	TrnFileManagementDAO.getSearchSubReply(searchString,fdate,tdate,sphrase,roleID,ISCONF,ISREPLY,advstxt,dataSearchDetail2);
	}
	if('<%=type%>'=='FF'){
	TrnFileManagementDAO.getSearchSub3(searchString,fdate,tdate,sphrase,roleID,dataSearchDetail3);
	}
	//alert(searchString);
	
	showDiv();
	}}
	
   function dataSearchDetail(data){
   //alert(data.length);
  if(data.length>0){
  	window.document.getElementById("reportData").style.display="block";
  }
  else {
 	window.document.getElementById("reportData").style.display="none";
  	hideDiv();
  	alert('No Data Found!!!');
  }
  var reportData = window.document.getElementById("reportData");
 
  var htmlText = '';
  	//htmlText  = '<table id="data" width="100%"><tr><td>';	
	htmlText = '<table  width="100%" style="page-break-after: always;display: table-header-group;">';	
	htmlText += '<thead style="display: table-header-group;"><tr><th filter="false"  style="border: solid 1px black;border-style: dotted">S.No.</th><th filter="false" style="border: solid 1px black;border-style: dotted"></th><th  style="border: solid 1px black;border-style: dotted"><b>Ref.No.</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Incoming Date</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Ref.Dated</b></th><th  style="border: solid 1px black;border-style: dotted;"><b>Received From</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Status</b></th><th  style="border: solid 1px black;border-style: dotted"><b>State</b></th><th nowrap="nowrap" style="border: solid 1px black;border-style: dotted;"><b>Sub.</b></th><th nowrap="nowrap" style="border: solid 1px black;border-style: dotted;width:200px;"><b>Subject</b></th><th align="center" nowrap="nowrap"  style="border: solid 1px black;border-style: dotted"><b>Forward To</b></th><th align="center" nowrap="nowrap"  style="border: solid 1px black;border-style: dotted"><b>Forward On</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Remarks</b></th><th  style="border: solid 1px black;border-style: dotted"><b>File No.</b></th><th  style="border: solid 1px black;border-style: dotted"><b>FM Mark To</b></th><th  style="border: solid 1px black;border-style: dotted"><b>FM Mark On</b></th><th  style="border: solid 1px black;border-style: dotted"><b>File Status1</b></th><th  style="border: solid 1px black;border-style: dotted"><b>File Status2</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Return To</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Return On</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Reply</b></th><th style="border: solid 1px black;border-style: dotted">&nbsp;</th></tr></thead>';
	htmlText += '<tbody  id="content">';
	
	//alert(searchResult.innerHTML);
	//alert(data);
	
	var refBean = new Array(data.length);
	var counter=0;
	for(var loop=0; loop < data.length; loop++){
	counter++;
		var trnRefBean = data[loop];
		refBean[loop] = data[loop];
		var attachment = (trnRefBean.ISATTACHMENT == "1"? '<img border="0" alt="Image Gallery" src="${pageContext.request.contextPath}/images/stn.gif" onclick="popupGallery('+trnRefBean.REFID+','+trnRefBean.REFID+',1);">': "");
		var attachmentfile = (trnRefBean.ISATTACHMENTFILE == "1"? '<img border="0" alt="Image Gallery" src="${pageContext.request.contextPath}/images/stn.gif" onclick="popupGallery('+trnRefBean.FMID+','+trnRefBean.FMID+',2);">': "");
		var REFNO = (data[loop].URGENCY == 'C' || data[loop].URGENCY == 'S' || data[loop].URGENCY == 'T')? '<font color="#FF0000">'+trnRefBean.REFNO+'*</font>': trnRefBean.REFNO;
		var trclass = (loop%2==0)? "trwhite":"treven";
		htmlText += '<tr><td  >'+ counter +'</td><td  >'+ attachment +'&nbsp;</td><td  >'+REFNO+'</td><td  >'+trnRefBean.INCOMINGDATE+'</td><td  >'+trnRefBean.LETTERDATE+'</td><td  >'+trnRefBean.REFERENCENAME+'</td><td  >'+trnRefBean.VIPSTATUS+'&nbsp;</td><td  >'+trnRefBean.STATECODE+'&nbsp;</td><td >'+trnRefBean.SUBJECTCODE + (trnRefBean.ISBUDGET == "Y"?" - Budget":"") +'&nbsp;</td><td >'+trnRefBean.SUBJECT+'</td><td  >'+trnRefBean.MARKINGTO+'</td><td  >'+trnRefBean.MARKINGDATE+'</td><td  >'+trnRefBean.REMARKS + (trnRefBean.SIGNEDBY != null? (trnRefBean.SIGNEDBY+"  "+trnRefBean.SIGNEDON): "") +'&nbsp;</td><td  ><a href="#" onclick="PopUpFile(\''+trnRefBean.REFID+'\');">'+trnRefBean.FILENO+'</a>&nbsp;</td><td  >'+trnRefBean.IMARKINGTO+'&nbsp;</td><td  >'+trnRefBean.IMARKINGON+'&nbsp;</td><td  >'+trnRefBean.FILESTATUS1+'&nbsp;</td><td >'+trnRefBean.FILESTATUS2+'&nbsp;</td><td >'+trnRefBean.DMARKINGTO+'&nbsp;</td><td >'+trnRefBean.DMARKINGDATE+'&nbsp;</td><td >'+trnRefBean.REPLYTYPE+'&nbsp;</td><td  >'+ attachmentfile +'&nbsp;</td></tr>';
		htmlText += '<tr><td colspan="1" style="border-bottom: dotted; border-width: 1px; border-color: gray;"></td><td colspan="1" style="border-bottom: dotted; border-width: 1px; border-color: gray;"></td><td colspan="19" style="border-bottom: dotted; border-width: 1px; border-color: gray;">'+trnRefBean.REPLY+'&nbsp;</td></tr>';
		//htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick="maximizeRefData(refBean[loop]);"> <td  style="border: solid 1px black;border-style: dotted"> '+trnRefBean.REFNO+' </td><td  style="border: solid 1px black;border-style: dotted"> kkkkk </td> </tr>';
			
			//alert(' htmlText ' + htmlText);	
					
	}
	
	htmlText += '</tbody></table>';
	//alert(htmlText);
	reportData.innerHTML = htmlText;
	
	
    
	if(sphrase=="0"){
  	highlightSearchTerms(searchString,false);
  	}else{
  	highlightSearchTerms(searchString,true);
  	}
  	
  	$("#sorttable").tablesorter(); 
	$("#sorttable").tableFilter();
  	hideDiv();
  }
  
  
  function dataSearchDetail1(data){
   //alert(data.length);
  if(data.length>0){
  	window.document.getElementById("reportData").style.display="block";
  }
  else {
 	window.document.getElementById("reportData").style.display="none";
 	hideDiv();
 	alert('No Data Found!!!');
  }
  var reportData = window.document.getElementById("reportData");
  

  
  var htmlText = '';
  	//htmlText  = '<table id="data" width="100%"><tr><td  style="border: solid 1px black;border-style: dotted">';	
	htmlText = '<table class="tablesorter" id="sorttable" width="">';	
	htmlText += '<thead><tr><th  style="border: solid 1px black;border-style: dotted"><b>Reference No.</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Incoming Date</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Letter Date</b></th><th width="20px"><b>Received From</b></th><th width="20px"><b>Status</b></th><th  style="border: solid 1px black;border-style: dotted"><b>State</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Subject</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Remarks</b></th></tr></thead>';
	htmlText += '<tbody  id="content">';
	
	//alert(searchResult.innerHTML);
	//alert(data);
	
	var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop]; 
		//alert('refBean.REFNO '+ refBean[loop].REFNO);
		htmlText += '<tr  class= "'+trclass+'" ><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.REFNO+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.INCOMINGDATE+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.LETTERDATE+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.REFERENCENAME+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.VIPSTATUS+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.STATECODE+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.SUBJECT+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.REMARKS+'</td></tr>';
		//htmlText += '<tr><td colspan="18"><hr></td></tr>';
		//htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick="maximizeRefData(refBean[loop]);"> <td  style="border: solid 1px black;border-style: dotted"> '+trnRefBean.REFNO+' </td><td  style="border: solid 1px black;border-style: dotted"> kkkkk </td> </tr>';
			
			//alert(' htmlText ' + htmlText);	
					
	}
	
	htmlText += '</tbody></table>';
	//alert(htmlText);
	reportData.innerHTML = htmlText;
	
	$("#sorttable").tablesorter(); 
	$("#sorttable").tableFilter();
    
hideDiv();
  
  }
  
  
function dataSearchDetail2(data){
	var roleID = '<%= sessionBean.getLOGINASROLEID() %>';
	var fdateid = window.document.getElementById("FDATE");  
  	var tdateid = window.document.getElementById("TDATE");
  	var fdate = fdateid.value;
	var tdate = tdateid.value;
  	
  if(data.length>0){
  	window.document.getElementById("reportData").style.display="block";
  }
  else {
 	window.document.getElementById("reportData").style.display="none";
 	hideDiv();
 	alert('No Data Found!!!');
  }
  var reportData = window.document.getElementById("reportData");
  var htmlTextIntM = '';
  var htmlText = '';
 // var srno;
	htmlText = '<table  width="100%" style="page-break-after: always; display: table-header-group;">';	
	htmlText += '<thead style="display: table-header-group;"><tr><th filter="false"  style="border: solid 1px black;border-style: dotted">S.No.</th><th filter="false" style="border: solid 1px black;border-style: dotted"></th><th  style="border: solid 1px black;border-style: dotted"><b>Reg.No<br>Reg.Dt.</b></th><th  style="border: solid 1px black;border-style: dotted"><b>File Type</b></th><th  style="border: solid 1px black;border-style: dotted"><b>File No.</b></th><th  style="border: solid 1px black;border-style: dotted;"><b>Branch<br>Final Marking</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Received From</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Subject</b></th><th nowrap="nowrap" colspan="3" style="border: solid 1px black;border-style: dotted;" align="left"><b>Internal Marking</b></th><th align="center" nowrap="nowrap"  style="border: solid 1px black;border-style: dotted"><b>File Status1<br>File Status2</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Full Status</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Mark To<br>Mark On</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Remarks</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Reply type</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Reason</b></th></tr></thead>';
	htmlText += '<tbody  id="content">';
	var refBean = new Array(data.length);
	var refBeanIntM = new Array(data.length);
	
	for(var loop=0,srno=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop];
		if(data[loop].COUNT==2){
		srno=srno;
		
		}else{
		//vfmid=trnRefBean.FMID;
		srno=srno+1;
		
		}
		var cnt=data[loop].COUNT;
		
		//var type='F';
		var REGISTRATIONNO = data[loop].REFERENCETYPE == 'Confidential'? '<font color="#FF0000">'+data[loop].REGISTRATIONNO+'*</font>': data[loop].REGISTRATIONNO;
		var attachment = (trnRefBean.ISATTACHMENT == "1"? '<img border="0" alt="Image Gallery" src="${pageContext.request.contextPath}/images/stn.gif" onclick="popupGallery('+trnRefBean.FMID+','+trnRefBean.FMID+',2);">': "");
 		
 		if(data[loop].COUNT==1){ 		
		htmlText += '<tr><td align="center" id="std00" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+srno+' </td><td  style="border-bottom: dotted; border-width: 1px; border-color: gray;">'+ attachment +'&nbsp;</td> <td align="center" id="std0" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+REGISTRATIONNO+'<br>'+data[loop].REGISTRATIONDATE+' </td><td  id="std1" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].REFERENCETYPE+' </td><td  id="std2" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].FILENO+'<br><a href="#" onclick="PopUpRef(\''+data[loop].PUCLIST+'\');">'+data[loop].PUCLIST+'</a> </td><td  id="std3" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].DEPARTMENTCODE+'<br>'+data[loop].DESTINATIONMARKING+' </td><td id="std31"   style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].RECEIVEDFROM+' </td><td  id="std4" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].SUBJECT+'&nbsp; </td><td  id="std5" valign="top" style=" font-family: Tahoma; font-size: 11px;" align="left" colspan="3" valign="top"><nobr>'+data[loop].IMARKINGTO+'&nbsp;</nobr></td><td  id="std8" style=" font-family: Tahoma; font-size: 11px;" valign="top"><nobr>'+data[loop].FILESTATUS1+'</nobr><br><nobr>'+data[loop].FILESTATUS2+'</nobr></td><td  id="std8" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].REMARKS+'</td><td  id="std8" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].DMARKINGTO+'<br>'+data[loop].DMARKINGDATE+'</td><td  id="std8" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].MARKINGREMARK+'</td><td  id="std8" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].REPLYTYPE+'</td><td  id="std8" style=" font-family: Tahoma; font-size: 11px;" valign="top"> '+data[loop].REASON+'</td> </tr>';
		htmlText += '<tr><td colspan="1" style="border-bottom: dotted; border-width: 1px; border-color: gray;">&nbsp;</td><td colspan="1" style="border-bottom: dotted; border-width: 1px; border-color: gray;">&nbsp;</td><td colspan="15" style="border-bottom: dotted; border-width: 1px; border-color: gray;font-family: Tahoma; font-size: 11px;">'+trnRefBean.REPLY+'&nbsp;</td></tr>';
		}
		
		
		
	}	
	//htmlText += '<tr><td align="center" id="std00" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td> <td align="center" id="std0" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td  id="std1" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma;;">&nbsp;</td><td  id="std2" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td  id="std3" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td id="std31"   style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td  id="std4" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td  id="std5" valign="top" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 10px;" align="left" colspan="3">&nbsp;</td><td  id="std8" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td  id="std8" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td  id="std8" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td  id="std8" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td  id="std8" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td><td  id="std8" style="border-top: dotted; border-width: 1px; border-color: gray; font-family: Tahoma; font-size: 11px;">&nbsp;</td> </tr>';
	//htmlText += '<tr><td colspan="17" style="border-bottom: dotted; border-width: 1px; border-color: gray;">'+trnRefBean.REPLY+'&nbsp;</td></tr>';
	htmlText += '</tbody></table>';
	reportData.innerHTML = htmlText;
	if(sphrase=="0"){
  	highlightSearchTerms(searchString,false);
  	}else{
  	highlightSearchTerms(searchString,true);
  	}
  	$("#sorttable").tablesorter(); 
	$("#sorttable").tableFilter();
	hideDiv();
  }
  
  function dataSearchDetail3(data){
   //alert(data.length);
  if(data.length>0){
  	window.document.getElementById("reportData").style.display="block";
  }
  else {
 	window.document.getElementById("reportData").style.display="none";
 	hideDiv();
 	alert('No Data Found!!!');
  }
  var reportData = window.document.getElementById("reportData");
 
  var htmlText = '';
  	//htmlText  = '<table id="data" width="100%"><tr><td  style="border: solid 1px black;border-style: dotted">';	
	htmlText = '<table class="tablesorter" id="sorttable" width="100%">';	
	htmlText += '<thead><tr><th  style="border: solid 1px black;border-style: dotted"><b>Reference No.</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Incoming Date</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Letter Date</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Received From</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Status</b></th><th  style="border: solid 1px black;border-style: dotted"><b>State</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Subject</b></th><th  style="border: solid 1px black;border-style: dotted"><b>Remarks</b></th></tr></thead>';
	htmlText += '<tbody  id="content">';
	
	//alert(searchResult.innerHTML);
	//alert(data);
	
	var refBean = new Array(data.length);
	for(var loop=0; loop < data.length; loop++){
		var trnRefBean = data[loop];
		refBean[loop] = data[loop]; 
		//alert('refBean.REFNO '+ refBean[loop].REFNO);
		
		var trclass = (loop%2==0)? "trwhite":"treven";
		htmlText += '<tr  class= "'+trclass+'" ><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.REFNO+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.INCOMINGDATE+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.LETTERDATE+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.REFERENCENAME+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.VIPSTATUS+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.STATECODE+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.SUBJECT+'</td><td  style="border: solid 1px black;border-style: dotted">'+trnRefBean.REMARKS+'</td></tr>';
		//htmlText += '<tr><td colspan="11"><hr></td></tr>';
		
		//htmlText += '<tr style="cursor: pointer;" class= "'+trclass+'" onclick="maximizeRefData(refBean[loop]);"> <td  style="border: solid 1px black;border-style: dotted"> '+trnRefBean.REFNO+' </td><td  style="border: solid 1px black;border-style: dotted"> kkkkk </td> </tr>';
			
			//alert(' htmlText ' + htmlText);	
					
	}
	
	htmlText += '</tbody></table>';
	//alert(htmlText);
	reportData.innerHTML = htmlText;
	
	$("#sorttable").tablesorter(); 
	$("#sorttable").tableFilter();
    
	hideDiv();
  }

function popupGallery(refId, flg,num)
{
	var type= num==1?'R':'F';
	var url="ImageGallery.jsp?refId="+refId+"&flg="+flg+"&type="+type;
	window.open(url,"","toolbar=0,status=0,location=0,menubar=0,directories=0,titlebar=0,left=0,top=0,scrollbars=1,resizable=1");
}
 
function functionKey(obj,objvalue){
	//alert('Hi'); 
	if(!obj.readOnly&&((obj.type=='text')||(obj.type=='textarea'))){
	
	if(window.event.keyCode==113){
	//alert(parseInt(obj.maxLength));
	var maxlength=parseInt(obj.maxLength);
	
	if(maxlength >0 ){
	//alert("1   "+obj.value);
	if(maxlength >= (parseInt(obj.value.length)+parseInt('<%=serverDate.length()%>'))){
	 obj.value=obj.value+"<%=serverDate%>";
	 //alert("2  "+obj.value);
	 }}
	 else {
	 	obj.value=obj.value+"<%=serverDate%>";
	 	//alert("3  "+obj.value);
	 }
	 
	 }
	 
	 if(window.event.keyCode==119){
				//alert((cpval.length));
				var maxlength=parseInt(objvalue.length);
				
				obj.value=obj.value+'(Personally Handed Over)';
				
			}
	}
	// if((window.event.shiftKey==true) && (window.event.keyCode==80))   
	//{   
	//	alert("alt+p")
	//}  
	//obj.value=objvalue+" hello ";
	}
	 $(function(){
 $("INPUT").keyup(function () {functionKey(this,this.value); }); 
 })
 $(function(){
 $("TEXTAREA").keyup(function () {functionKey(this,this.value); }); 
 })


function chkWorkDate(obj) {
	if(obj.value.length>0 && chkDate(obj)) {
		if(compareDateLTET(obj.value,'<%=serverDate%>'))
			return true;
		else {
			alert("Date should be less than or equal to Current Date!!!");
			obj.style.backgroundColor = "yellow";
			//obj.value="";
			obj.focus();
			return false;
		}
	}
	return false;
}
function chkDate(obj) {
	if(obj.value=="") {
		obj.style.backgroundColor = "white";
		return true;
	}else {
		if(isValidDate(obj,'DMY')) {
			if(obj.value.split('/')[2].length==2) {
				alert("Enter valid date in format DD/MM/YYYY");
				obj.style.backgroundColor = "yellow";
				obj.focus();
				return false;
			}
			return true;
		}else {
			alert("Enter valid date in format DD/MM/YYYY");
			obj.focus();
			return false;
		}
	}
}

 




$().ready(function() {
	// if text input field value is not empty show the "X" button
	$("#field").keyup(function() {
		$("#x").fadeIn();
		if ($.trim($("#field").val()) == "") {
			$("#x").fadeOut();
		}
	});
	// on click of "X", delete input field value and hide "X"
	$("#x").click(function() {
		$("#field").val("");
		$(this).hide();
	});
});


$(document).ready(function ()
        {
            $.ui.dialog.defaults.bgiframe = true;
            $("#FDATE").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true'});
            $("#TDATE").datepicker({ dateFormat: 'dd/mm/yy', showButtonPanel:'true' });
            $("input[type=text]").focus(function () { this.select(); $(this).css('background', '#FFE4E1') });
        });
        
 function onReferenceselect(){
 	var fdate = window.document.getElementById("FDATE").value;  
  	var tdate = window.document.getElementById("TDATE").value;
 // 	alert(fdate+tdate)
	if('<%=type%>'=='RO'){	
		var inputf = $("#searchString").clone(false);
		$("#searchString").autocomplete("getReferenceNamesDataSearch.jsp", {scroll:false});
		var parentf = $("#searchString").parent();
		$("#searchString").remove();
		parentf.append( inputf );
		$("#searchString").autocomplete("getReferenceNamesDataSearch.jsp", {scroll:false});
		$('#searchString').result(function(event, data, formatted) {
		    if (data) {
		    var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			document.getElementById('searchString').value=dataarr[0];
 			
 			
		}
	});
	}
	if('<%=type%>'=='FO'){	
		var inputf = $("#searchString").clone(false);
		$("#searchString").autocomplete("getFileNo.jsp?fdate="+fdate+"&tdate="+tdate, {scroll:false});
		var parentf = $("#searchString").parent();
		$("#searchString").remove();
		parentf.append( inputf );
		$("#searchString").autocomplete("getFileNo.jsp?fdate="+fdate+"&tdate="+tdate, {scroll:false});
		$('#searchString').result(function(event, data, formatted) {
		    if (data) {
		    var dataarr = new Array();
 			dataarr = String(data).split(",,");
 			document.getElementById('searchString').value=dataarr[0];
 			
 			
		}
	});
	}
	label2value();
	} 
	
	function onReferenceUnselect(){
		
		document.getElementById('searchString').value='';
		var inputf = $("#searchString").clone(false);
		var parentf = $("#searchString").parent();
		$("#searchString").remove();
		parentf.append( inputf );
		label2value();
		
	} 
  
 function setPhrase(obj){
 //alert(obj.checked);
	if(obj.checked){
		document.getElementById('SPHRASE').value="1";
	}else{
		document.getElementById('SPHRASE').value="0";
	}
	}

function PrintReport(){
	var param1= document.getElementById('searchString').value;
	var param2 = document.getElementById('FDATE').value +' - '+document.getElementById('TDATE').value;
	var url = 'PopUpPrintReport_SearchGeneral.jsp?flag=y&hideftr=Y&param1='+param1+'&param2='+param2;
	window.open(url);
}

function bodyOnload() {
	var titleLabel = window.document.getElementById("titleLabel");
	var htmlText = '';
	if('<%=type%>'=='FO'){
		window.document.getElementById("tdRefCls1").style.display='none';
		window.document.getElementById("tdRefCls2").style.display='none';
		window.document.getElementById("madvs").style.display='block';
		window.document.getElementById("d1").innerHTML='File No. ';
		window.document.getElementById("d2").innerHTML='Internal Marking ';
		window.document.getElementById("d3").innerHTML='Downward Marking';
		htmlText = '<table><tr><td><font size="3"><b><i>File-Create</i> - Search-Dynamic </b></font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11"></td></tr></table>';
		titleLabel.innerHTML = htmlText;
	}
	if('<%=type%>'=='RO'){
		window.document.getElementById("tdRefCls1").style.display='block';
		window.document.getElementById("tdRefCls2").style.display='block';
		window.document.getElementById("madvs").style.display='block';
		window.document.getElementById("d1").innerHTML='Reference Name ';
		window.document.getElementById("d2").innerHTML='Forward To ';
		window.document.getElementById("d3").innerHTML='Returned To';
		htmlText = '';
		htmlText = '<table><tr><td><font size="3"><b><i>Reference-Create</i> - Search-Dynamic </b></font><img src="${pageContext.request.contextPath}/images/arrow_big.gif" width="6" height="11"></td></tr></table>';
		titleLabel.innerHTML = htmlText;
	}
}


function showDiv()
{
	document.getElementById('updateDiv').style.display="block";
	document.getElementById('updateDivInner').style.display="block";
}
function hideDiv()
{
	
		document.getElementById('updateDiv').style.display="none";
		document.getElementById('updateDivInner').style.display="none";
	
}

function showDivAs()
{
	document.getElementById('advs').style.display="inline";
	document.getElementById('btnADD').style.display="none";
	document.getElementById('btnMINUS').style.display="inline";
	document.getElementById('fssearch').style.height="120px";
}
function hideDivAs()
{
	
		document.getElementById('advs').style.display="none";
		document.getElementById('btnADD').style.display="inline";
		document.getElementById('btnMINUS').style.display="none";
		document.getElementById('fssearch').style.height="90px";
	
}

function setAdvsearch()
{
	var advsearch = document.getElementsByName('advsearch');
	for(var i=0;i<advsearch.length;i++){
	if(advsearch[i].checked){
	document.getElementById('advsearchtxt').value=i;
	}
	}
}

function unsetAdvsearch()
{
	var advsearch = document.getElementsByName('advsearch');
	for(var i=0;i<advsearch.length;i++){
	advsearch[i].checked = false;
	}
	document.getElementById('advsearchtxt').value='';
}

function PopUpRef(val)
{
	var url="PopUpRefNo.jsp?VAL="+val;
	 	 var width  = 920;
		 var height = 600;
		 var left   = (screen.width  - width)/2;
		 var top    = (screen.height - height)/2;
		 var params = 'width='+width+', height='+height;
		 params += ', top=20, left='+left;
		 params += ', directories=no';
		 params += ', location=no';
		 params += ', menubar=no';
		 params += ', resizable=no';
		 params += ', scrollbars=yes';
		 params += ', status=no';
		 params += ', toolbar=no';		
		var win = window.open(url ,"popupReminder",params);
		win.focus();
}

function PopUpFile(val)
{
	var url="GeneralReports_FileMovController?referenceId="+val+"&reportNumber="+5;
	 	 var width  = 1024;
		 var height = 600;
		 var left   = (screen.width  - width)/2;
		 var top    = (screen.height - height)/2;
		 var params = 'width='+width+', height='+height;
		 params += ', top=20, left='+left;
		 params += ', directories=no';
		 params += ', location=no';
		 params += ', menubar=no';
		 params += ', resizable=no';
		 params += ', scrollbars=yes';
		 params += ', status=no';
		 params += ', toolbar=no';		
		var win = window.open(url ,"popupReminder",params);
		win.focus();
}

</script>      
</head>
<body onload="bodyOnload();">
<form name="frm">
<div id="titleLabel"> </div>

<div id="searchContainer"> 
<table align="center" style="border:0;border-color: transparent;" cellpadding="0" cellspacing="0" >
<tr>
<td valign="middle"><fieldset  style="height: 90px;" id="fssearch">
<table align="center" style="border: 0;border-color: transparent;"  cellpadding="0" cellspacing="0">  
	<tr>
		<td colspan='5' align="left" height="28"><label for="searchString">Please Enter at least two Character</label>
			<input type="text" name="searchString" id="searchString" size="60" align="left"></td>
		<td id="tdRefCls1">Ref. Class : </td>
		<td id="tdRefCls2"><select style="width: 60px" name="REFCLASS" id="REFCLASS">
			<option value="" selected>--All--</option>
				<%
					for(int i=0;i<refClassList.size();i++){
					CommonBean beanCommon=(CommonBean) refClassList.get(i);
				%>
			<option value="<%=beanCommon.getField1()%>"><%=beanCommon.getField1()%></option>
				<%}%>
			</select></td>
	</tr>
	<tr>
		<td align="left" colspan="1" style="vertical-align: top">
		<input type="text" size="15" name="FDATE" id="FDATE"  value="<%=sessionBean.getTENURESTARTDATE() %>" onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10" tabindex="5">
		</td>
		<td align="left" colspan="1" style="vertical-align: top"><input type="text" size="15" name="TDATE" id="TDATE"  value="<%=serverDate%>" onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10" tabindex="5">
		</td>
		<td align="right" >&nbsp;<input style="height: 20px; background-color:#f5f5f5" type="button" name="search" value="Search" onclick="getsearchDetail();" title="Search"></td>
	</tr>
	<tr>
	<td align="left" height="28" colspan='2' valign="bottom" id="madvs" style="display: none"><input
					type="button" name="btnADD" tabindex="5" id="btnADD" value="+"
					onclick="showDivAs();" class="butts"
					title="Show More advanced options."><input type="button"
					name="btnMINUS" tabindex="5" id="btnMINUS" value="-"
					onclick="onReferenceUnselect();unsetAdvsearch();hideDivAs();" class="butts"
					title="Hide More advanced options."
					style="display: none; "> More advanced
				options</td>
				<td colspan='2' align="left" height="28"><input type="checkbox"
					name="SPHRASECHECK" onclick="setPhrase(this)" checked="checked">
				Match Whole Word Only<input type="hidden" name="SPHRASE"
					id="SPHRASE" value="1"></td><td></td>
	</tr>
</table>
<div id="advs" style="display: none">
<table>
<tr><td><input type="radio"
					name="advsearch" onclick="onReferenceselect();setAdvsearch();" >
				</td>
<td id="d1">Reference Name </td>
<td><input type="radio"
					name="advsearch" onclick="onReferenceUnselect();setAdvsearch();" >
				</td>
<td id="d2">Forward To </td>
<td><input type="radio"
					name="advsearch" onclick="onReferenceUnselect();setAdvsearch();" >
				<input type="hidden" name="advsearchtxt"
					id="advsearchtxt" value=""></td>
<td id="d3">Returned To</td></tr>
</table>
</div></fieldset></td>
<td valign="top" style="margin-top:0px">
<fieldset style="height: 90px;margin-top:0px"><legend align="top" style="margin-top:0px">Search History</legend>
<table id="data" align="center" style="margin-top:0px">  
	<tr>
				<td colspan='5' align="left">
				<div id="suggest" style="height:70px;width:200px; overflow: auto;"></div>
				</td>
			</tr>
</table>
</fieldset></td>
</tr>
</table>
<table align="center">
<tr><td colspan="11" align="center" bgcolor="#f5f5f5" width="90"><img width="25" height="25" src="images/printer_new.png" onclick="PrintReport();" title="Print"></img></td></tr>
</table>
<div id='reportData'></div>
</div>
</form>

<!--  <%=" Model Starts -->"%>
<DIV class="transparent_class" style="z-index:2000; background-color:#000; position: absolute; left:0px; top: 0px; display: none; width: 100%; height: 150%;" id="updateDiv">
	
</DIV>
<DIV style="vertical-align: middle; text-align:center;  z-index:3000; position: absolute; left:20px; top: 160px; display: none; width: 100%" id="updateDivInner">
<DIV class="pageheader" style="color:white; font-family:tahoma ; z-index:5000; background-color: gray; width: 300px; border: groove; text-align: center; background-image:url(images/top-hd-bar-bg.gif); background-repeat: repeat; " id="divLoading">
			
			<BR>Please Wait <img src="images/progress_bar.gif" alt="Loading" />
			<br><br>
</DIV>
</DIV>
<%="<!-- Model Ends "%>    -->


</body>
</html>