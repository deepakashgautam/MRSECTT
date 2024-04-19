<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">


<TITLE>RFMS: CalendarPopUp</TITLE>
<script language="JavaScript">

<!--
var months = new Array("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var days = new Array("S", "M", "T", "W", "T", "F", "S");
var txtid="";
var param="";
var cnt="";
today = new getToday();	
var element_id;

<%
	java.util.Calendar c=java.util.Calendar.getInstance();
	int datetoday=c.getTime().getDate();
	int monthtoday=c.getTime().getMonth();
	int yeartoday=c.getTime().getYear()+1900;
%>

function getDays(month, year) 
{
	// Test for leap year when February is selected.
	if (1 == month)
		return ((0 == year % 4) && (0 != (year % 100))) ||
			(0 == year % 400) ? 29 : 28;
	else
		return daysInMonth[month];
}

function getToday()
{
	this.now = new Date(<%=yeartoday%>,<%=monthtoday%>,<%=datetoday%>);
	this.year = this.now.getFullYear() ;
	this.month = this.now.getMonth();
	this.day = this.now.getDate();	
}

 
function newCalendar(){
	var parseYear = parseInt(document.all.year  [document.all.year.selectedIndex].text);
 	var newCal = new Date(parseYear , document.all.month.selectedIndex, 1);
	var day = -1;
	var startDay = newCal.getDay();
	var daily = 0; 
	
	//new code by Rohit
 	var mn = document.all.month.selectedIndex+1;
 	var Year = document.all.year [document.all.year.selectedIndex].text;
 	if(mn<10)	mn="0"+mn;
	// End of new code
	today = new getToday(); // 1st call
	var intDaysInMonth = getDays(newCal.getMonth(), newCal.getFullYear() );
	
	if ((today.year == newCal.getFullYear() ) &&   (today.month == newCal.getMonth()))
	   day = today.day;
	else
		day=daysInMonth[document.all.month.selectedIndex];

	var tableCal = document.all.calendar.tBodies.dayList;

	for (var intWeek = 0; intWeek < tableCal.rows.length;  intWeek++)
		for (var intDay = 0;intDay < tableCal.rows[intWeek].cells.length;intDay++){
			var cell = tableCal.rows[intWeek].cells[intDay];
			cell.onclick=getDate;
			// Start counting days.
			if ((intDay == startDay) && (0 == daily))
				daily = 1;
			// Highlight the current day.
			cell.style.color = (day == daily) ? "red" : "";
			if(day == daily){
				document.all.todayday.innerText= "Today: " +  today.day + "/" +	(today.month+1) + "/" + today.year ;
			}
			// Output the day number into the cell.
			var passyearmn=Year+""+mn;
			var currmn=today.month+1;
			if(currmn<10) currmn="0"+currmn;
			var curryearmn=today.year+""+(currmn);
			//
			if(param=="L"){
				if ((daily > 0) && (daily <= intDaysInMonth) && (parseInt(passyearmn)<=parseInt(curryearmn))){
			 		cell.innerText = daily++;
					if(daily>day+1){
				 		cell.onclick=null;
					}
				}
			else
				cell.innerText = "";
		}else if(param=="G"){
			if ((daily > 0) && (daily <= intDaysInMonth) && (parseInt(passyearmn)>=parseInt(curryearmn))){
				cell.innerText = daily++;
				if(daily<day+1 && (parseInt(passyearmn)==parseInt(curryearmn))) 
					cell.onclick=null;
			} else
				cell.innerText = "";
			}
		else if(param=="A"){
			if ((daily > 0) && (daily <= intDaysInMonth)){
				cell.innerText = daily++;
			}else
				cell.innerText = "";
		}
	}
}

function getTodayDay(){
	document.all[element_id].value = today.day + "/" + (today.month+1) +"/" + today.year; 
	var padday="";
	var padmonth="";
	if(parseInt(today.day)<10) 
		padday="0"+today.day;
	else 
		padday=today.day;
	if(parseInt(today.month+1)<10)
		padmonth="0"+(today.month+1);
	else
		padmonth=(today.month+1);
	if(cnt==""){
		window.opener.document.getElementById(txtid).value=padday + "/" + padmonth +"/" + today.year; 
	}else{
		window.opener.document.getElementsByName(txtid)[cnt].value=padday+ "/" + padmonth +	"/" + today.year; 
	}
	document.all.calendar.style.display="none";
	window.close();
	document.all.calendar.style.display="none";
	document.all.year.selectedIndex =100;   
	document.all.month.selectedIndex = today.month; 
}

function cleardate(){
	if(cnt==""){
		window.opener.document.getElementById(txtid).value=""; 
	}else{
		window.opener.document.getElementsByName(txtid)[cnt].value=""; 
	}
 	//window.opener.document.getElementById(txtid).value="";
 	window.close();
 }
function getDate(){
	// This code executes when the user clicks on a day
	// in the calendar.
    if ("TD" == event.srcElement.tagName)
    	// Test whether day is valid.
        if ("" != event.srcElement.innerText){ 
			var mn = document.all.month.selectedIndex+1;
    		var Year = document.all.year [document.all.year.selectedIndex].text;
			document.all[element_id].value=event.srcElement.innerText+"/"+mn +"/"  +Year;
		    //document.all.calendar.style.visibility="hidden";
			var padday="";
			var padmonth="";
			if(parseInt(event.srcElement.innerText)<10) padday="0"+event.srcElement.innerText;
			else padday=event.srcElement.innerText;
			if(parseInt(mn)<10) padmonth="0"+mn;
			else padmonth=mn;
			if(cnt==""){
				window.opener.document.getElementById(txtid).value=padday+"/"+padmonth +"/"  +Year;
			}else{
				window.opener.document.getElementsByName(txtid)[cnt].value=padday+"/"+padmonth +"/"  +Year;
			}
			document.all.calendar.style.display="none";
			window.close();
		}
}
 
function GetBodyOffsetX(el_name, shift){
	var x;
	var y;
	x = 0;
	y = 0;
	var elem = document.all[el_name];
	do{
		x += elem.offsetLeft;
		y += elem.offsetTop;
		if (elem.tagName == "BODY")
			break;
		elem = elem.offsetParent; 
	} while  (1 > 0);

	shift[0] = x;
	shift[1] = y;
	return  x;
}	

function SetCalendarOnElement(el_name){
	if (el_name=="") 
		el_name = element_id;
	var shift = new Array(2);
	GetBodyOffsetX(el_name, shift);
}
	           
function ShowCalendar(elem_name){
	if (elem_name=="")
		elem_name = element_id;
	element_id	= elem_name; // element_id is global variable
	newCalendar();
	SetCalendarOnElement(element_id);
	document.all.calendar.style.display="inline";
}

function HideCalendar(){
	document.all.calendar.style.display="none";
}

function toggleCalendar(elem_name){
	if(document.all.calendar.style.display=="none")
		ShowCalendar(elem_name);
	else 
		HideCalendar();
}

function populate(){
	var url=window.location;
	var query = window.location.search.substring(1);
	var arrquery=query.split("~");
	if(arrquery.length==1){
		txtid = query.substring(0,query.length-2);
		if(query.substring(query.length-2,query.length-1)=="-")
			param=query.substring(query.length-1);
		else{
			param="A";
			txtid=query;
		}
	}else{
		query=arrquery[0];
		cnt=arrquery[1];
		txtid = query.substring(0,query.length-2);
		if(query.substring(query.length-2,query.length-1)=="-")
			param=query.substring(query.length-1);
		else{
			param="A";
			txtid=query;
		}
	}
	toggleCalendar("MyDate");
}

-->
</script>

<style>
.today {COLOR: black; FONT-FAMILY: sans-serif; FONT-SIZE: 10pt; FONT-WEIGHT: bold; TEXT-ALIGN: right}
.days {COLOR: navy; FONT-FAMILY: sans-serif; FONT-SIZE: 10pt; FONT-WEIGHT: bold; TEXT-ALIGN: center}
.dates {COLOR: black; FONT-FAMILY: sans-serif; FONT-SIZE: 10pt; TEXT-ALIGN: right}
</style>

</HEAD>
<BODY style="background: none;" onload="populate();">

<script>
	var maxdate=today.year+""+(today.month+1)+""+today.day;
</script>

<INPUT type="hidden" id=MyDate name=MyDate size=15>
<TABLE border=1 cellPadding=1 cellSpacing=0 id=calendar style="DISPLAY: none; POSITION: absolute; Z-INDEX: 4; background: none;">
	<TBODY>
  		<TR>
    		<TD colSpan=7 vAlign=center>
			<!-- Month combo box -->
				<SELECT class="drop" id=month onchange=newCalendar()> 
					<option value="0">January</option>
					<option value="1">February</option>
					<option value="2">March</option>
					<option value="3">April</option>
					<option value="4">May</option>
					<option value="5">June</option>
					<option value="6">July</option>
					<option value="7">August</option>
					<option value="8">September</option>
					<option value="9">October</option>
					<option value="10">November</option>
					<option value="11">December</option>
			</SELECT> 
    	<SCRIPT language=JavaScript>
		// Output months into the document.
		// Select current month.
		document.getElementById("month").value=today.month;
		//for (var intLoop = 0; intLoop < months.length; intLoop++)
			//document.write("<OPTION " +	(today.month == intLoop ? "Selected" : "") + ">" + months[intLoop]);
		</SCRIPT>
	
	<!-- Year combo box -->
	<SELECT class="drop"  id=year onchange=newCalendar()> 
    
    <SCRIPT language=JavaScript>
	// Output years into the document.
	// Select current year.
	query = window.location.search.substring(1);
	var arrquery=query.split("~");
	if(arrquery.length==1){
		txtid = query.substring(0,query.length-2);
		if(query.substring(query.length-2,query.length-1)=="-")
			param=query.substring(query.length-1);
		else{
			param="A";
			txtid=query;
		}
	}else{
		query=arrquery[0];
		cnt=arrquery[1];
		txtid = query.substring(0,query.length-2);
		if(query.substring(query.length-2,query.length-1)=="-")
			param=query.substring(query.length-1);
		else{
			param="A";
			txtid=query;
		}
	}
	if(param=="L"){
		//for (var intLoop = (today.year - 100); intLoop < (today.year+1); intLoop++)
		for (var intLoop = 1980; intLoop < (today.year+1); intLoop++)
			document.write("<OPTION " + (today.year == intLoop ? "Selected" : "") + ">" + intLoop);
	}
	if(param=="G"){
		for (var intLoop = (today.year); intLoop < (today.year+10); intLoop++)
			document.write("<OPTION " + (today.year == intLoop ? "Selected" : "") + ">" + intLoop);
	}
	if(param=="A"){
		//for (var intLoop = (today.year-100); intLoop < (today.year+10); intLoop++)
		for (var intLoop = 1980; intLoop < (today.year+10); intLoop++)
			document.write("<OPTION " + (today.year == intLoop ? "Selected" : "") + ">" + intLoop);
	}
	</SCRIPT>
	
	</SELECT> 

	</TD>
  </TR>


	
  <TR  class=days bgcolor="#fbd9c8" >
	<!-- Generate column for each day. -->
    <SCRIPT language=JavaScript>
	// Output days.
	for (var intLoop = 0; intLoop < days.length; intLoop++){		
		document.write("<TD>" + days[intLoop] + "</TD>");
	}
	</SCRIPT>
  </TR>


  <TBODY class=dates id=dayList vAlign=center>
  <!-- Generate grid for individual days. -->
  <SCRIPT language=JavaScript>
	for (var intWeeks = 0; intWeeks < 6; intWeeks++){
		document.write("<TR>");
		for (var intDays = 0; intDays < days.length; intDays++){
			document.write("<TD style='cursor:pointer' onclick='getDate();'></TD>");
		}
		document.write("</TR>");
	}
  </SCRIPT>

  <!-- Generate today day. --></TBODY>
  <TBODY>
  <TR class="drop">
    <TD class=today colSpan=5 id=todayday style="cursor: pointer;" onclick="getTodayDay();"></TD>
    <TD align="center" colSpan=2  onclick="cleardate();" style="cursor: pointer;font-weight: bold;">Clear</TD>
  </TR>
  </TBODY>

</TABLE>
</BODY>
</HTML>
