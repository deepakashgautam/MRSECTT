<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
   String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";
   
%>
<title>menu</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<!-- --> <link href="/MRSECTT/theme/ddsmoothmenu<%=theme%>.css"" type="text/css" rel="stylesheet"/> <!---->   <!-- <link href="/MRSECTT/theme/ddsmoothmenu.css" type="text/css" rel="stylesheet"/>  -->  

<link rel="stylesheet" type="text/css" href="/MRSECTT/theme/ddsmoothmenu-v.css" />

<script type="text/javascript"  src="/MRSECTT/jquery-ui-1.8.4.custom/js/jquery-1.3.2.min.js"></script>

 
<body bgcolor="#ffd6bf">

<div id="smoothmenu-ajax" class="ddsmoothmenu" >
<ul>

<li><a href="#"  >Reference-Create</a><ul>


<li><a href="Reference.jsp"  target="tmscontent">Add</a></li>



<li><a href="searchSubject.jsp?type=RO"  target="tmscontent">Search-Dynamic</a></li>



<li><a href="BudgetReport.jsp"  target="tmscontent">Search-Budget</a></li>



<li><a href="ReportGeneral1.jsp"  target="tmscontent">Report-General</a></li>



<li><a href="CustomReport.jsp"  target="tmscontent">Report-Custom</a></li>



<li><a href="ReportStats1.jsp"  target="tmscontent">Report-Statistical</a></li>



<li><a href="Reminder.jsp"  target="tmscontent">Reminder</a></li>



<li><a href="PeonRegisterReport.jsp"  target="tmscontent">Peon Book - Date</a></li>



<li><a href="PeonRegisterReport_RefNo.jsp"  target="tmscontent">Peon Book - Ref No</a></li>



<li><a href="PeonRegisterIssue_DReport.jsp"  target="tmscontent">Issue/Dispatch</a></li>



<li><a href="OutDak.jsp"  target="tmscontent">Out Dak</a></li>



<li><a href="UploadAttachment.jsp"  target="tmscontent">Upload Attachment</a></li>



<li><a href="searchSubjectWithReply.jsp?type=RO"  target="tmscontent">Search-Reply</a></li>



<li><a href="ReportStatsSubject.jsp"  target="tmscontent">Report Statistical-Detail</a></li>



<li><a href="MonthlyReport.jsp"  target="tmscontent">Consolidated Report</a></li>



<li><a href="SummaryReport.jsp"  target="tmscontent">State Coordinators Report</a></li>




</ul>

<li><a href="#"  >File-Create</a><ul>



<li><a href="FileManagement.jsp"  target="tmscontent">Add</a></li>


<li><a href="searchSubject.jsp?type=FO"  target="tmscontent">Search-Dynamic</a></li>



<li><a href="ReportGeneral_FileMov.jsp"  target="tmscontent">Report-General</a></li>



<li><a href="CustomReportFileMov.jsp"  target="tmscontent">Report-Custom</a></li>



<li><a href="PeonBookFM_Param.jsp"  target="tmscontent">Peon Book</a></li>



<li><a href="UploadAttachmentFile.jsp"  target="tmscontent">Upload Attachment</a></li>



<li><a href="searchSubjectWithReply.jsp?type=FO"  target="tmscontent">Search-Reply</a></li>



<!-- <li><a href="FileSummaryReport.jsp"  target="tmscontent">Yearwise Disposal report</a></li>



<li><a href="PMOfficeReport.jsp"  target="tmscontent">PM Office Report</a></li> -->




</ul>
<br style="clear: left" />
</div>
</body>
</html>
