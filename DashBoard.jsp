
<!DOCTYPE html>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@page import="in.org.cris.mrsectt.Beans.CommonBean"%>
<%@page import="in.org.cris.mrsectt.dao.CommonDAO"%><html>
<head>
<%
MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
  	CommonBean sBean = (session.getAttribute("sbean")!=null) ?(CommonBean)session.getAttribute("sbean"): new CommonBean();
  	String serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
  	
  	String queryMarkingTo = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='1' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"'"+
 							" AND B.PREFERREDID <> '"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
	ArrayList<CommonBean> markingToList = (new CommonDAO()).getSQLResult(queryMarkingTo, 2);
	
	
String divcode = session.getAttribute("divcode") != null ? session
			.getAttribute("divcode").toString() : "";
	String rlycode = session.getAttribute("rlycode") != null ? session
			.getAttribute("rlycode").toString() : "";
	String authtype = session.getAttribute("authtype") != null ? session
			.getAttribute("authtype").toString()
			: "";
	String authlevel = session.getAttribute("authlevel") != null ? session
			.getAttribute("authlevel").toString()
			: "";
	String loginid = session.getAttribute("loginid") != null ? session
			.getAttribute("loginid").toString() : "";
	String roleid = session.getAttribute("roleid") != null ? session
			.getAttribute("roleid").toString() : "";
		String category = session.getAttribute("category") != null ? session
			.getAttribute("category").toString() : "";
	String designation = session.getAttribute("designation") != null ? session
			.getAttribute("designation").toString() : "";
				String month=request.getParameter("month")!=null?request.getParameter("month"):"";
				String year=request.getParameter("year")!=null?request.getParameter("year"):"";
	DateFormat df1 = new SimpleDateFormat("yyyy");
	DateFormat dfMonth = new SimpleDateFormat("MM");
	String currDate = df1.format(new Date());
	int endyr = ((Integer.parseInt(currDate)));
	pageContext.setAttribute("endyr",endyr);
	int iAuthlevel=0;
	if(authlevel.length()>0){
		iAuthlevel= Integer.parseInt(authlevel);
	}
/*	CommonDAO cd = 		new CommonDAO();
	ArrayList<String> finyrArr = cd.getFinYear(4);
	String crrFinYear=CommonDAO.getFinYearExtended();
	String crrMonth= dfMonth.format(new Date());
	ArrayList<CommonBean> sseArr =new ArrayList<CommonBean>();
		ArrayList<CommonBean> divArr=new ArrayList<CommonBean>();
	
	ArrayList<CommonBean> rlyArr=new ArrayList<CommonBean>();
	if(iAuthlevel==5){
		
		CommonBean cb = new CommonBean();
		cb.setField1(rlycode);
		cb.setField2(rlycode);
		rlyArr.add(cb);
		
		cb = new CommonBean();
		cb.setField1(divcode);
		cb.setField2(divcode);
		divArr.add(cb);
		
		sseArr = cd.getSSEofDivCategory(divcode,category);
	} 
	else if(iAuthlevel==6){
		divArr = cd.getDivOfRly(rlycode);
		CommonBean cb = new CommonBean();
		cb.setField1(rlycode);
		cb.setField2(rlycode);
		rlyArr.add(cb);
	} 
	else if (iAuthlevel==7){
		rlyArr = cd.getRly();
	}*/
	
	
 %>	
	<title>Dash Board</title>
<style type="text/css">

.jqplot-yaxis {
  margin-left:-5px !important;
}

</style>

<script language="JavaScript" src="${pageContext.request.contextPath}/jquery1.9.2/js/jquery-1.8.3.js"></script>
<script src="${pageContext.request.contextPath}/jquery1.9.2/js/jquery-ui-1.9.2.custom.js"></script>
<script src="${pageContext.request.contextPath}/jquery1.9.2/js/jquery.dimensions.js" type="text/javascript"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery1.9.2/css/smoothness/jquery-ui-1.9.2.custom.css" />
 
<% String theme = session.getAttribute("theme")!=null? session.getAttribute("theme").toString(): "";%> 

    <link class="include" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqplot/jquery.jqplot.css" />
  <style>
.ui-datepicker-calendar {
    display: inline;
    }
</style>
<!-- DWR -->
<SCRIPT src='dwr/util.js'></SCRIPT>
<SCRIPT src='dwr/engine.js'></SCRIPT>
<SCRIPT src='dwr/interface/DashboardDAO.js'></SCRIPT>
<SCRIPT src='dwr/interface/CommonDAO.js'></SCRIPT>

  <!--[if lte IE 9]><script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/jqplot/excanvas.js"></script><![endif]-->
   
   
   
<!-- Don't touch this! -->


    <script class="include" type="text/javascript" src="${pageContext.request.contextPath}/jqplot/jquery.jqplot.js"></script>
<!-- End Don't touch this! -->

<!-- Additional plugins go here -->

<script class="include" language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.pieRenderer.js"></script>
<script class="include" language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.donutRenderer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.barRenderer.js"></script>
<script class="include" type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.meterGaugeRenderer.js"></script>
  
  
  
  
<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.pointLabels.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.highlighter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.cursor.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.canvasTextRenderer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.categoryAxisRenderer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.canvasAxisLabelRenderer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.canvasAxisTickRenderer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.logAxisRenderer.js"></script>


<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.canvasAxisTickRenderer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.dateAxisRenderer.min.js"></script>


<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.pointLabels.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/jqplot/plugins/jqplot.cursor.min.js"></script>


<!-- End additional plugins --> 
 <script class="code" type="text/javascript">
 var reportYear='2013';
 var yearHTML="<select name='consumptionyear' id='consumptionyear' style='z-index: 5000'> <option value=''> -[Select One]- </option>";
 var endyr= <c:out value="<%= endyr %>"/>;
 var tempMeterData;
 var charts=new Array();
 var ajaxcount=0;
 var DivId;
function generateChartsMain(chartid,divid,proleid,pfdate,ptdate){
DivId = divid;
var vroleid;
var vfdate;
var vtdate;

var vcond="";



if(proleid==''){
vroleid='%%';
}else{
vroleid=proleid;
}
if(pfdate==''){
vfdate='<c:out value="<%=sessionBean.getTENURESTARTDATE()%>"/>';
}else{
vfdate=pfdate;
}
if(ptdate==''){
vtdate='<c:out value="<%=serverDate%>"/>';
}else{
vtdate=ptdate;
}

if(proleid==''){
vcond = "AND A.REFERENCEDATE BETWEEN TO_DATE('"+vfdate+"','DD/MM/YYYY') AND TO_DATE('"+vtdate+"','DD/MM/YYYY')";
}else{
vcond = "AND (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) = '"+vroleid+"' AND A.REFERENCEDATE BETWEEN TO_DATE('"+vfdate+"','DD/MM/YYYY') AND TO_DATE('"+vtdate+"','DD/MM/YYYY')";
}



switch(chartid) {

case 1:
case "1":
	
 DashboardDAO.getTotalReference('<c:out value="<%=sessionBean.getTENUREID()%>"/>',vcond,
  	function (data) {if(data.length==0){ data = [[null]];} createBarChartTR(data,'Total References between '+reportYear,divid,'Nos.');  }
  );

break;
case 2:
case "2":
	
 DashboardDAO.getReferenceStatus(vcond,
  	function (data) {if(data.length==0){ data = [[null]];} createBarChartRS(data,'Total References between '+reportYear,divid,'Nos.');  }
  );

break;
case 3:
case "3":

DashboardDAO.getReferenceSubjectWise(vcond,
  	function (data) {if(data.length==0){ data = [[null]];} createBarChartRSW(data,'Total References between '+reportYear,divid,'Nos.');  }
  );
  
break;
case 4:
case "4":
//alert(chartid);
 DashboardDAO.getSSEWiseConsumption("<c:out value='<%=authlevel%>'/>","<%if(authlevel.equalsIgnoreCase("6")){%>"+vrlycode+"<%}else if(authlevel.equalsIgnoreCase("5")){%>"+vdivcode+"<%}else{%>"+vroleid+"<%}%>",vcond,vcategory,vrlycode,vdivcode,
  	function (data) { if(data.length==0){ data = [[null]];} createBarChartSSE(data,'Consumption for January '+reportYear,divid,"<%if(authlevel.equalsIgnoreCase("7")){%>"+'Division'+"<%}else if(authlevel.equalsIgnoreCase("6")){%>"+'ADEE'+"<%}else{%>"+'SSE'+"<%}%>");  }
  );

break;
case 5:
case "5":
//alert('hiiiii');
 DashboardDAO.getStateWiseConsumption("<c:out value='<%=authlevel%>'/>","<%if(authlevel.equalsIgnoreCase("6")){%>"+vrlycode+"<%}else if(authlevel.equalsIgnoreCase("5")){%>"+vdivcode+"<%}else{%>"+vroleid+"<%}%>",vcond,vcategory,vrlycode,vdivcode,
  	function (data) {
  	//alert(data.length);
  		if(data.length==0){ data = [[null]];} createBarChartState(data,'Consumption for January '+reportYear,divid,'Rs.');  }
  );

break;
case 6:
case "6":
 DashboardDAO.getSSWiseConsumption("<c:out value='<%=authlevel%>'/>","<%if(authlevel.equalsIgnoreCase("6")){%>"+vrlycode+"<%}else if(authlevel.equalsIgnoreCase("5")){%>"+vdivcode+"<%}else{%>"+vroleid+"<%}%>",vcond,vcategory,vrlycode,vdivcode,
  	function (data) {if(data.length==0){ data = [[null]];} createBarChartSubStation(data,'Consumption for January '+reportYear,divid,'Rs.');  }
  );

break;
case 11:
case "11":
//alert('11');
	DashboardDAO.getSSbillingD("<c:out value='<%=authlevel%>'/>","<%if(authlevel.equalsIgnoreCase("6")){%>"+vrlycode+"<%}else if(authlevel.equalsIgnoreCase("5")){%>"+vdivcode+"<%}else{%>"+vroleid+"<%}%>",vcond,vcategory,vrlycode,vdivcode,
  	function (data) { tempMeterData=data; if(data.length==0){ data = [[null]];} createConsumptionYearlyBilling(data,'Billing for Year '+reportYear,divid,'Rs.');  }
  );
break;
case 12:
case "12":
DashboardDAO.getSEBWiseConsumptionBilling("<c:out value='<%=authlevel%>'/>","<%if(authlevel.equalsIgnoreCase("6")){%>"+vrlycode+"<%}else if(authlevel.equalsIgnoreCase("5")){%>"+vdivcode+"<%}else{%>"+vroleid+"<%}%>",vcond,vcategory,vrlycode,vdivcode,
  	function (data) {if(data.length==0){ data = [[null]];} createBarChartSSB(data,'Billing for January '+reportYear,divid,'Rs.');  }
  );

break;
case 14:
case "14":
//alert(chartid);
 DashboardDAO.getSSEWiseConsumptionBilling("<c:out value='<%=authlevel%>'/>","<%if(authlevel.equalsIgnoreCase("6")){%>"+vrlycode+"<%}else if(authlevel.equalsIgnoreCase("5")){%>"+vdivcode+"<%}else{%>"+vroleid+"<%}%>",vcond,vcategory,vrlycode,vdivcode,
  	function (data) { if(data.length==0){ data = [[null]];} createBarChartSSEB(data,'Billing for January '+reportYear,divid,"<%if(authlevel.equalsIgnoreCase("7")){%>"+'Division'+"<%}else if(authlevel.equalsIgnoreCase("6")){%>"+'ADEE'+"<%}else{%>"+'SSE'+"<%}%>");  }
  );

break;
case 15:
case "15":
 DashboardDAO.getStateWiseConsumptionBilling("<c:out value='<%=authlevel%>'/>","<%if(authlevel.equalsIgnoreCase("6")){%>"+vrlycode+"<%}else if(authlevel.equalsIgnoreCase("5")){%>"+vdivcode+"<%}else{%>"+vroleid+"<%}%>",vcond,vcategory,vrlycode,vdivcode,
  	function (data) {if(data.length==0){ data = [[null]];} createBarChartStateB(data,'Billing for January '+reportYear,divid,'Rs.');  }
  );

break;

case 16:
case "16":
 DashboardDAO.getMonthWiseConsumption("<c:out value='<%=authlevel%>'/>","<%if(authlevel.equalsIgnoreCase("6")){%>"+vrlycode+"<%}else if(authlevel.equalsIgnoreCase("5")){%>"+vdivcode+"<%}else{%>"+vroleid+"<%}%>",vcond,vcategory,vrlycode,vdivcode,
  	function (data) {if(data.length==0){ data = [[null]];} createBarChartSS(data,'Consumption for January '+reportYear,divid,'Rs.');  }
  );

break;

}
}

var s0=[];
var s1=[];
var s2=[];
function getCurrentYearConsumption(data)
{
if(data.length==0){ data = [[null]];} 
  	for(var i=0;i<data.length;i++) {
  			s0.push(parseFloat(data[i].field1,10));
	}
	ajaxcount-- ;
	checkajax();

}


function getFirstYearConsumption(data)
{
if(data.length==0){ data = [[null]];} 
  	for(var i=0;i<data.length;i++) {
  			s1.push(parseFloat(data[i].field1,10));
	}
	ajaxcount-- ;
	checkajax();
}

function getSecondYearConsumption(data)
{
if(data.length==0){ data = [[null]];} 
  	for(var i=0;i<data.length;i++) {
  			s2.push(parseFloat(data[i].field1,10));
	}
	ajaxcount-- ;
	checkajax();
}

function checkajax()
{
	if(ajaxcount==0) {	
	//alert(s0);
	//alert(s1);
	//alert(s2);
		//createBarChart(s0,s1,s2,DivId);
		
		createLineChartSSE(s0,s1,s2,DivId);
		
	}
}

function generateBilling(vroleid,vfdate,vtdate){
//if(!chartExists){
	 alert(chartExists);
	 generateChartsMain(11,'chartSummaryYearBilling',vroleid,vfdate,vtdate);
  	 generateChartsMain(12,'chartSummarySSBarBilling',vroleid,vfdate,vtdate);
  	 generateChartsMain(15,'chartStatewiseBilling',vroleid,vfdate,vtdate);
  	 generateChartsMain(13,'chartSummarySSLineBilling',vroleid,vfdate,vtdate);
     chartExists=true;
//	} 
}

function generateConsumption(vroleid,vfdate,vtdate){
   generateChartsMain(1,'chartTotalReferences',vroleid,vfdate,vtdate);
  generateChartsMain(2,'chartReferenceStatus',vroleid,vfdate,vtdate);
  generateChartsMain(3,'chartRefSubjectwise',vroleid,vfdate,vtdate);
  generateChartsMain(5,'chartStatewise',vroleid,vfdate,vtdate);
  generateChartsMain(11,'chartSummaryYearBilling',vroleid,vfdate,vtdate);
  	 generateChartsMain(12,'chartSummarySSBarBilling',vroleid,vfdate,vtdate);
  	 generateChartsMain(14,'chartSummarySSLineBilling',vroleid,vfdate,vtdate);
  	 generateChartsMain(15,'chartStatewiseBilling',vroleid,vfdate,vtdate);
  generateChartsMain(16,'chartSummaryMonthWise',vroleid,vfdate,vtdate);
}

var chartExists=false;

function generatePieChart(vroleid,vfdate,vtdate)
{
	 $( "#TabContainerDiv" ).tabs();

	 
	  $('#TabContainerDiv').bind('tabsshow', function(event, ui) {
      if (ui.index === 1 && ptintedPlotB._drawCount === 0) {
        ptintedPlotTR.replot();
        ptintedPlotRSW.replot();
        ptintedPlotRS.replot();
        //ptintedPlotB3.replot();
      }
      
      
    });
 
 
generateConsumption(vroleid,vfdate,vtdate);

}

$(document).ready(function(){

switch(<c:out value='<%=iAuthlevel%>'/>) {
case 2:
case 3:
case 4:
case 5:
	$('#divcode').find("option").eq(0).remove();
case 6:
	$('#rlycode').find("option").eq(0).remove();
	break;

}

// Jqyery Portler
 $( "#sortable" ).sortable();
  $( "#sortable" ).disableSelection();
  
  $( ".portlet" )
    .addClass( "ui-widget ui-widget-content ui-corner-all" )
    .find( ".portlet-header" )
    .addClass( "ui-widget-header ui-corner-all" )
    .prepend( "<span class='ui-icon ui-icon-minusthick'></span>")
    .end()
    .find( ".portlet-content" );

  $( ".portlet-header .ui-icon" ).click(function() {
    $( this ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
    $( this ).parents( ".portlet:first" ).toggleClass( "portlet-minimized" );
  });


    
    $( ".portletHDR" )
    .addClass( "ui-widget ui-widget-content ui-corner-all" )
    .find( ".portlet-header" )
    .addClass( "ui-widget-header ui-corner-all" );
    
    
  $( ".portlet" )
    .find( ".portlet-header[print='yes']" )
    .prepend( "<span class='ui-icon ui-icon-print'></span>")
    .end()
    .find( ".portlet-content" );
    
    
  $( ".portlet-header .ui-icon-minusthick" ).click(function() {
    $( this ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
    $( this ).parents( ".portlet:first" ).toggleClass( "portlet-minimized" );
  });

  $( ".portlet-header .ui-icon-print" ).click(function() {
	   printChart($(this).parents(".portlet-header:first").attr('chartid'));
  });
  $( ".column" ).disableSelection();
   //Jquery Portlet
$('.date-picker').datepicker( {
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        dateFormat: 'MM yy',
        onClose: function(dateText, inst) { 
            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
            $(this).datepicker('setDate', new Date(year, month, 1));
        }
    });
    
    
generatePieChart('','<c:out value="<%=sessionBean.getTENURESTARTDATE()%>"/>','<c:out value="<%=serverDate%>"/>');


 var ptintedPlot; 
   function printChart(chartid)
   {
   
   	var vroleid = window.document.getElementById("roleid").value;
	var vfdate = window.document.getElementById("fdate").value;
	var vtdate = window.document.getElementById("tdate").value;
	//alert(vroleid);
   		
   		//$('#'+chartid).height(500);
        //$('#'+chartid).width(500);
      if(ptintedPlot)
       	{alert("found plot"); ptintedPlot.destroy(); }
        $('#ChartPrint').html("");
        $('#ChartPrintOuter').css('display','block');
       // resetChart(vroleid,vfdate,vtdate);
        ptintedPlot = generateChartsMain(chartid,'ChartPrint',vroleid,vfdate,vtdate);
      // ptintedPlot.replot({resetAxes:true});
        //printID('sortableHDR');
     
     /*
      alert("hi");
     
     var picture = jqplotToImg($('#ChartPrint'));
     alert(picture);
  	$('#ChartPrintPNG').attr('src',picture);
  	*/
   			
   }
// Line chart
/*
  var cosPoints = []; 
  for (var i=0; i<2*Math.PI; i+=0.1){ 
     cosPoints.push([i, Math.cos(i)]); 
  } 
  var plot1 = $.jqplot('chart1', [cosPoints], {  
      series:[{showMarker:false}],
      axes:{
        xaxis:{
          label:'Month'
        },
        yaxis:{
          label:'Consumption'
        }
      }
  });
  
  /*
  var line1 = [[10,425.32], [11,424.84], [12,417.23], [13,390], [14,393.69], [15,392.24], [16,369.78], [17,330.16], [18,308.57], [19,346.45], [20,371.28], [21,324.7]];

var line2 = [[10,325.32], [11,324.84], [12,217.23], [13,190], [14,593.69], [15,292.24], [16,369.78], [17,330.16], [18,308.57], [19,446.45], [20,241.28], [21,624.7]];

var plot3 = $.jqplot('chart1', [line1, line2 ], { 
  title: 'XYZ, Inc.', 
    series: [{ 
        label: 'XYZ, Inc.', 
        neighborThreshold: -1 
    }], 
    axes: {
        xaxis: {
            min: 1,
            max: 24,
            tickInterval: 2,
            tickOptions:{ suffix: ':00' } 
        },
         yaxis: {  
            renderer: $.jqplot.LogAxisRenderer,
            tickOptions:{ prefix: '$' } 
        } 
    },
    cursor:{
        show: true, 
        zoom: true
    }    
  */

    // Can specify a custom tick Array.
    // Ticks should match up one for each y value (category) in the series.

    

  
  
  // Meter Guage
     $('.jqplot-highlighter-tooltip').addClass('ui-corner-all')
  
});



function createConsumptionYearly(arrData,title,objid,unit)
{
																  
				
	
	var data=[];
	for(var i=0;i<arrData.length;i++) {
	//alert(arrData.length);
		data.push([arrData[i].field2,parseFloat(arrData[i].field1,10)])
	}

   ptintedPlot = jQuery.jqplot (objid, [data], 
    { 
   // title: title,
    seriesColors :seriesColors,
      seriesDefaults: {
        // Make this a pie chart.
        renderer: jQuery.jqplot.PieRenderer, 
        rendererOptions: {
          // Put data labels on the pie slices.
          // By default, labels show the percentage of the slice.
          showDataLabels: false,
          dataLabels: 'value'
        }
      }, 
      legend: { show:true, location: 'e', rendererOptions: {numberColumns: 2} },
      highlighter: {        show: false,
			  formatString:'%s,%d '+unit, 
			  tooltipLocation:'se', 
			  useAxesFormatters:false     },      
      cursor: {        show: false      }
    }
    
  );
  //alert(ptintedPlot);
 
}


function createConsumptionYearlyBilling(arrData, title, objid, unit)
{	
	//alert("inside createConsumptionYearlyBilling");
	//alert(arrData);
	//alert(title);
	//alert(objid);
	//alert(unit);
	
	var data=[];
	for(var i=0;i<arrData.length;i++) {
	//alert(arrData.length);
		data.push([arrData[i].field2,parseFloat(arrData[i].field1,10)])
	}

   ptintedPlotB = jQuery.jqplot (objid, [data], 
    { 
   // title: title,
    seriesColors :seriesColors,
      seriesDefaults: {
        // Make this a pie chart.
        renderer: jQuery.jqplot.PieRenderer, 
        rendererOptions: {
          // Put data labels on the pie slices.
          // By default, labels show the percentage of the slice.
          showDataLabels: false,
          dataLabels: 'value'
        }
      }, 
      legend: { show:true, location: 'e', rendererOptions: {numberColumns: 2} },
      highlighter: {        show: true,
			  formatString:'%s,%d '+unit, 
			  tooltipLocation:'se', 
			  useAxesFormatters:false     },      
      cursor: {        show: false      }
    }
    
  );
  //alert(ptintedPlot);
 
}

function createMeters(data)
{
	for(var i=0;i<data.length;i++) {
		 createMeter(parseInt(data[i].field1,10),data[i].field2);
	}
}
function createMeter(ssvalue,title)
{
	var divid='meter'+Math.floor((Math.random()*10000)+1);
	$('#chartMeter').append('<div id="chartMeter'+divid+'" style="height:200px; width:200px; float: left;"> </div>');
	 s1 = [ssvalue];
	//alert(ssvalue);
   plot3 = $.jqplot(('chartMeter'+divid),[s1],{
       seriesDefaults: {
           renderer: $.jqplot.MeterGaugeRenderer,
           rendererOptions: {
               min: 0,
               max: ssvalue*3,
               //ticks: [100, 200, 300, 400,500],
               intervals:[ssvalue*2,ssvalue*2.5,ssvalue*3],
               intervalColors:['#66cc66', '#E7E658', '#cc6666'],
              // label: 'Current Year Consumption till Date <br />'+ title,               
               labelPosition: 'bottom',               
               labelHeightAdjust: -5,
           }
       }
   });
   
}

function setYear(obj) {
	reportYear=obj.value;
	resetCharts();
	
}

function createBarChart(vs1,vs2,vs3,divid)
{
//alert(vs1[0]);
	var s1=new Array();
	s1=vs1;
	
	var s2=new Array();
	s2=vs2;
	var s3=new Array();
	s3=vs3;
	//s1=[30238.36,26734.32,31379.08,27999.08,0,0,0,0,0,0,0,0];
	//s2=[30238.36,26734.32,31379.08,27999.08,0,0,0,0,0,0,0,0];
	//s3=[30238.36,26734.32,31379.08,27999.08,0,0,0,0,0,0,0,0];
	
	
	
	
    // Can specify a custom tick Array.
    // Ticks should match up one for each y value (category) in the series.
     var ticks = ['JAN','FEB','MAR','APR','MAY', 'JUN', 'JUL', 'AUG','SEP','OCT','NOV','DEC' ];
    
    ptintedPlot = $.jqplot(divid, [s1, s2, s3], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true}
        },
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        series:[
            {label:'<c:out value="<%=Integer.parseInt(currDate)%>"/>'},
            {label:'<c:out value="<%=Integer.parseInt(currDate)-1%>"/>'},
            {label:'<c:out value="<%=Integer.parseInt(currDate)-2%>"/>'}
        ],
        seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: true,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ticks,
                label:'Months',           labelOptions:{            fontFamily:'Helvetica',            fontSize: '10pt'          },
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Consumption' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d KWH'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createBarChartTR(arrData,title,objid,unit)
{
//alert(objid);
	var data=[];
	var ticks = [];
	//var seriescolor=[];
	for(var i=0;i<arrData.length;i++) {
	//alert(arrData[i].field1);
	//alert(arrData[i].field2);
		data.push(parseFloat(arrData[i].field1,10));
		ticks.push(arrData[i].field2);
		//seriescolor.push(arrData[i].field3);
	}
	
    
    ptintedPlotTR = $.jqplot(objid, [data], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        //seriesColors:seriesColor,
        seriesColors: ["#4bb2c5", "#EAA228", "#c5b47f", "#579575", "#839557", "#958c12", "#953579", "#4b5de4", "#d8b83f", "#ff5800", "#0085cc", "#c747a3", "#cddf54", "#FBD178", "#26B4E3", "#bd70c7"],
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true,varyBarColor: true},
             pointLabels: { show: true }
        },
        
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        //alert(seriesColors);
        //seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: false,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                 renderer: $.jqplot.CategoryAxisRenderer,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                label:'Classes',           labelOptions:{            fontFamily:'Helvetica',            fontSize: '10pt'          },
                ticks: ticks,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                
          		tickOptions: {
	              // labelPosition: 'left',
	              angle: 0,
	              fontFamily: 'Helvetica',
	              fontSize: '10pt'
	              }
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Numbers' ,
            	min: 0,
                pad: 1.30,
                tickOptions: {formatString: '%d'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createBarChartRSW(arrData,title,objid,unit)
{
//alert(objid);
	var data=[];
	var ticks = [];
	//var seriescolor=[];
	for(var i=0;i<arrData.length;i++) {
	//alert(arrData[i].field1);
	//alert(arrData[i].field2);
		data.push(parseFloat(arrData[i].field1,10));
		ticks.push(arrData[i].field2);
		//seriescolor.push(arrData[i].field3);
	}
	
    
    ptintedPlotRSW = $.jqplot(objid, [data], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        //seriesColors:seriesColor,
        seriesColors: ["#4bb2c5", "#EAA228", "#c5b47f", "#579575", "#839557", "#958c12", "#953579", "#4b5de4", "#d8b83f", "#ff5800", "#0085cc", "#c747a3", "#cddf54", "#FBD178", "#26B4E3", "#bd70c7"],
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true,varyBarColor: true},
            pointLabels: { show: true }
        },
        
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        //alert(seriesColors);
        //seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: false,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                 renderer: $.jqplot.CategoryAxisRenderer,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                label:'Subject Code',           labelOptions:{            fontFamily:'Helvetica',            fontSize: '10pt'          },
                ticks: ticks,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                
          		tickOptions: {
	              // labelPosition: 'left',
	              angle: -30,
	              fontFamily: 'Helvetica',
	              fontSize: '10pt'
	              }
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Numbers' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createBarChartRS(arrData,title,objid,unit)
{
//alert(objid);
	var data=[];
	var ticks = [];
	//var seriescolor=[];
	for(var i=0;i<arrData.length;i++) {
	//alert(arrData[i].field1);
	//alert(arrData[i].field2);
		data.push(parseFloat(arrData[i].field1,10));
		ticks.push(arrData[i].field2);
		//seriescolor.push(arrData[i].field3);
	}
	
   // alert(data);
    ptintedPlotRS = $.jqplot(objid, [data], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        //seriesColors:seriesColor,
        seriesColors: ["#4bb2c5", "#EAA228", "#c5b47f", "#579575", "#839557", "#958c12", "#953579", "#4b5de4", "#d8b83f", "#ff5800", "#0085cc", "#c747a3", "#cddf54", "#FBD178", "#26B4E3", "#bd70c7"],
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true,varyBarColor: true}
            ,pointLabels: { show: true }
        },
        
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        //alert(seriesColors);
        //seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: false,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                 renderer: $.jqplot.CategoryAxisRenderer,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                label:'Reply Type',           labelOptions:{            fontFamily:'Helvetica',            fontSize: '10pt'          },
                ticks: ticks,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                
          		tickOptions: {
	              // labelPosition: 'left',
	              angle: 0,
	              fontFamily: 'Helvetica',
	              fontSize: '8pt'
	              }
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Numbers' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createBarChartSSB(arrData,title,objid,unit)
{
	//alert("inside createBarChartSSB");
	var data=[];
	var ticks = [];
	var seriescolor=[];
	for(var i=0;i<arrData.length;i++) {
	
		data.push(parseFloat(arrData[i].field1,10));
		ticks.push(arrData[i].field2);
		seriescolor.push(arrData[i].field3);
		//alert(arrData[i].field3);
	}
	
    
    ptintedPlotB1 = $.jqplot(objid, [data], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        seriesColors:seriescolor,
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true,varyBarColor: true}
        },
        
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        
        //seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: false,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                 renderer: $.jqplot.CategoryAxisRenderer,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                label:'SEBs',           labelOptions:{            fontFamily:'Helvetica',            fontSize: '8pt'          },
                ticks: ticks,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                
          		tickOptions: {
	              // labelPosition: 'left',
	              angle: -30,
	              fontFamily: 'Helvetica',
	              fontSize: '6pt'
	              }
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Billing' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d Rs.'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createBarChartSubStation(arrData,title,objid,unit)
{

	var data=[];
	var ticks = [];
	for(var i=0;i<arrData.length;i++) {
	
		data.push(parseFloat(arrData[i].field1,10));
		ticks.push(arrData[i].field2);
	}
	
    
    ptintedPlot = $.jqplot(objid, [data], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true,varyBarColor: true}
        },
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        
        seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: false,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                label:'Sub Station',           labelOptions:{            fontFamily:'Helvetica',            fontSize: '10pt'          },
                ticks: ticks,
          		tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                
          		tickOptions: {
	              angle: -10,
	              fontFamily: 'Courier New',
	              fontSize: '9pt'
	          }
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Consumption' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d KWH'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createBarChartState(arrData,title,objid,unit)
{
//alert("state");
//alert(title);
//alert(objid);
//alert(unit);
	var data=[];
	var ticks = [];
	var seriescolor=[];
	for(var i=0;i<arrData.length;i++) {
	//alert(arrData[i].field1);
	
		data.push(parseFloat(arrData[i].field1,10));
		ticks.push(arrData[i].field2);
		seriescolor.push(arrData[i].field3);
	}
	
    
    ptintedPlot = $.jqplot(objid, [data], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        seriesColors:seriescolor,
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true,varyBarColor: true}
        },
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        
        //seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: false,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
              
                 renderer: $.jqplot.CategoryAxisRenderer,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                label:'States',           labelOptions:{            fontFamily:'Helvetica',            fontSize: '8pt'          },
                ticks: ticks,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                
          		tickOptions: {
	              // labelPosition: 'left',
	              angle: -30,
	              fontFamily: 'Helvetica',
	              fontSize: '6pt'
	              }
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Consumption' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d KWH'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createBarChartStateB(arrData,title,objid,unit)
{

	var data=[];
	var ticks = [];
	var seriescolor=[];
	for(var i=0;i<arrData.length;i++) {
	
		data.push(parseFloat(arrData[i].field1,10));
		ticks.push(arrData[i].field2);
		seriescolor.push(arrData[i].field3);
	}
	
    
    ptintedPlotB2 = $.jqplot(objid, [data], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        seriesColors:seriescolor,
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true,varyBarColor: true}
        },
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        
        //seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: false,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
               renderer: $.jqplot.CategoryAxisRenderer,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                label:'States',           labelOptions:{            fontFamily:'Helvetica',            fontSize: '8pt'          },
                ticks: ticks,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                
          		tickOptions: {
	              // labelPosition: 'left',
	              angle: -30,
	              fontFamily: 'Helvetica',
	              fontSize: '6pt'
	              }
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Billing' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d Rs.'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createBarChartSSE(arrData,title,objid,unit)
{

	var data=[];
	var ticks = [];
	//alert(arrData.length);
	for(var i=0;i<arrData.length;i++) {
	
		data.push(parseFloat(arrData[i].field1,10));
		ticks.push(arrData[i].field2+"   ");
	}
	
    
    ptintedPlot = $.jqplot(objid, [data], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true,varyBarColor: true}
        },
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        
        seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: false,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                label:unit,           labelOptions:{            fontFamily:'Helvetica',            fontSize: '8pt'          },
                ticks: ticks,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                
          		tickOptions: {
	              // labelPosition: 'left',
	              angle: -30,
	              fontFamily: 'Helvetica',
	              fontSize: '6pt'
	          }
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Consumption' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d KWH'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createBarChartSSEB(arrData,title,objid,unit)
{


	var data=[];
	var ticks = [];
	//alert(arrData.length);
	for(var i=0;i<arrData.length;i++) {
	
		data.push(parseFloat(arrData[i].field1,10));
		ticks.push(arrData[i].field2+"   ");
	}
	
    
    ptintedPlotB3 = $.jqplot(objid, [data], {
      //  title: 'Monthly Consumption of '+'<c:out value="<%=designation%>"/>'+' for Last Three Years',
        animate: true,
            // Will animate plot on calls to plot1.replot({resetAxes:true})
           
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {fillToZero: true,varyBarColor: true}
        },
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
        
        /*
      cursor:{ 
		show: true,
        tooltipLocation:'sw', 
        zoom:true
      } ,*/
        
        
        seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: false,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                label:unit,           labelOptions:{            fontFamily:'Helvetica',            fontSize: '8pt'          },
                ticks: ticks,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                
          		tickOptions: {
	              //labelPosition: 'middle',
	              angle: -30,
	              fontFamily: 'Helvetica',
	              fontSize: '6pt'
	          }
            },
            
           
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {
            	//rendererOptions:{tickRenderer: $.jqplot.CanvasAxisTickRenderer},
            	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Billing' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d Rs.'}
                
            }
        }
    });
    //ptintedPlot=plot1;
}

function createLineChartSSE(vs1,vs2,vs3,divid)
{
//alert(vs1[0]);
	var s1=new Array();
	s1=vs1;
	
	var s2=new Array();
	s2=vs2;
	var s3=new Array();
	s3=vs3;
 /*var a = [];
 var ss = [];
 var count = arrData.length;
 var qm;
 for(var k=0;k<arrData.length;k++){
 a[k] = new Array();
 qm=(count>1?',':'');
 //alert(qm);
 ss.push('{label:\''+arrData[k].field1+'\'}'+qm);
 // alert(arrData[k].field1);
 // for(var i=1;i<13;i++){
   // a[k].push([0, arrData[k].field1]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field2,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field3,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field4,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field5,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field6,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field7,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field8,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field9,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field10,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field11,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field12,10)]); 
    a[k].push([arrData[k].field1, parseFloat(arrData[k].field13,10)]); 
//  } 
     
 count--;
 } */  
 //alert(ss);

 var ticks = ['JAN','FEB','MAR','APR','MAY', 'JUN', 'JUL', 'AUG','SEP','OCT','NOV','DEC' ];
 //alert(ticks);
    
  ptintedPlot = $.jqplot(divid, [s1,s2,s3], 
    { 
      //title:'Line Style Options', 
      // Set default options on all series, turn on smoothing.
      seriesDefaults: {
          rendererOptions: {
              smooth: true
          }
      },
      
      
        seriesColors: seriesColors,
        // Show the legend and put it outside the grid, but inside the
        // plot container, shrinking the grid to accomodate the legend.
        // A value of "outside" would not shrink the grid and allow
        // the legend to overflow the container.
        legend: {
            show: true,
            placement: 'outside',
            //placement: 'outsideGrid',
            xoffset: -115 ,
            rendererOptions: {numberColumns: 2}
        },
      	highlighter: {        show: true,
			  formatString:'%2$s', 
			  tooltipLocation:'sw', 
			  useAxesFormatters:true     }, 
            
        axes: {
        // options for each axis are specified in seperate option objects.
        xaxis: {
          renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ticks,
                label:'Months',           labelOptions:{            fontFamily:'Helvetica',            fontSize: '10pt'          },
        },
        yaxis: {
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            	label:'Consumption' ,
            	min: 0,
                pad: 1.05,
                tickOptions: {formatString: '%d KWH'}
        }
      },
      // Series options are specified as an array of objects, one object
      // for each series.
       series:[
            {label:'<c:out value="<%=Integer.parseInt(currDate)%>"/>'},
            {label:'<c:out value="<%=Integer.parseInt(currDate)-1%>"/>'},
            {label:'<c:out value="<%=Integer.parseInt(currDate)-2%>"/>'}
        ]
         
    }
  );
}


function resetCharts(vroleid,vfdate,vtdate) {
/*alert(ptintedPlot.destroy());
	for(var i= 0; i< charts.length; i++) {
		charts[i].destroy();
	}
*/	
	chartExists=false;
 	document.getElementById("chartTotalReferences").innerHTML='';
 	document.getElementById("chartReferenceStatus").innerHTML='';
 	document.getElementById("chartRefSubjectwise").innerHTML='';
 	
	
$( "#TabContainerDiv" ).tabs("option","active",0);
	generatePieChart(vroleid,vfdate,vtdate);
	
	//generatePieChart(prlycode,pdivcode,pcategory,pfinyear,pmonth);
}

function openAsImage(chartid)
{
	var imgData = $('#'+chartid).jqplotToImageStr({});
	open(imgData);
	
}
function jqplotToImg(chartid) {
	var obj = $(chartid);
    var newCanvas = document.createElement("canvas");
    newCanvas.width = obj.find("canvas.jqplot-base-canvas").width();
    newCanvas.height = obj.find("canvas.jqplot-base-canvas").height()+10;
    var baseOffset = obj.find("canvas.jqplot-base-canvas").offset();

    // make white background for pasting
    var context = newCanvas.getContext("2d");
    context.fillStyle = "rgba(255,255,255,1)";
    context.fillRect(0, 0, newCanvas.width, newCanvas.height);

    obj.children().each(function () {
    // for the div's with the X and Y axis
        if ($(this)[0].tagName.toLowerCase() == 'div') {
            // X axis is built with canvas
            $(this).children("canvas").each(function() {
                var offset = $(this).offset();
                newCanvas.getContext("2d").drawImage(this,
                    offset.left - baseOffset.left,
                    offset.top - baseOffset.top
                );
            });
            // Y axis got div inside, so we get the text and draw it on the canvas
            $(this).children("div").each(function() {
                var offset = $(this).offset();
                var context = newCanvas.getContext("2d");
                context.font = $(this).css('font-style') + " " + $(this).css('font-size') + " " + $(this).css('font-family');
                context.fillStyle = $(this).css('color');
                context.fillText($(this).text(),
                    offset.left - baseOffset.left,
                    offset.top - baseOffset.top + $(this).height()
                );
            });
        } else if($(this)[0].tagName.toLowerCase() == 'canvas') {
            // all other canvas from the chart
            var offset = $(this).offset();
            newCanvas.getContext("2d").drawImage(this,
                offset.left - baseOffset.left,
                offset.top - baseOffset.top
            );
        }
    });

    // add the point labels
    obj.children(".jqplot-point-label").each(function() {
        var offset = $(this).offset();
        var context = newCanvas.getContext("2d");
        context.font = $(this).css('font-style') + " " + $(this).css('font-size') + " " + $(this).css('font-family');
        context.fillStyle = $(this).css('color');
        context.fillText($(this).text(),
            offset.left - baseOffset.left,
            offset.top - baseOffset.top + $(this).height()*3/4
        );
    });

    // add the title
    obj.children("div.jqplot-title").each(function() {
        var offset = $(this).offset();
        var context = newCanvas.getContext("2d");
        context.font = $(this).css('font-style') + " " + $(this).css('font-size') + " " + $(this).css('font-family');
        context.textAlign = $(this).css('text-align');
        context.fillStyle = $(this).css('color');
        context.fillText($(this).text(),
            newCanvas.width / 2,
            offset.top - baseOffset.top + $(this).height()
        );
    });

    // add the legend
    obj.children("table.jqplot-table-legend").each(function() {
        var offset = $(this).offset();
        var context = newCanvas.getContext("2d");
        context.strokeStyle = $(this).css('border-top-color');
        context.strokeRect(
            offset.left - baseOffset.left,
            offset.top - baseOffset.top,
            $(this).width(),$(this).height()
        );
        context.fillStyle = $(this).css('background-color');
        context.fillRect(
            offset.left - baseOffset.left,
            offset.top - baseOffset.top,
            $(this).width(),$(this).height()
        );
    });

    // add the rectangles
    obj.find("div.jqplot-table-legend-swatch").each(function() {
        var offset = $(this).offset();
        var context = newCanvas.getContext("2d");
        context.fillStyle = $(this).css('background-color');
        context.fillRect(
            offset.left - baseOffset.left,
            offset.top - baseOffset.top,
            $(this).parent().width(),$(this).parent().height()
        );
    });

    obj.find("td.jqplot-table-legend").each(function() {
        var offset = $(this).offset();
        var context = newCanvas.getContext("2d");
        context.font = $(this).css('font-style') + " " + $(this).css('font-size') + " " + $(this).css('font-family');
        context.fillStyle = $(this).css('color');
        context.textAlign = $(this).css('text-align');
        context.textBaseline = $(this).css('vertical-align');
        context.fillText($(this).text(),
            offset.left - baseOffset.left,
            offset.top - baseOffset.top + $(this).height()/2 + parseInt($(this).css('padding-top').replace('px',''))
        );
    });

    // convert the image to base64 format
    return newCanvas.toDataURL("image/png");
}

function getDivs(obj) {
	var divobj= document.getElementsByName("divcode");
	var index=getIndex(obj);
	divobj[index].length=1;
	CommonDAO.getDivOfRly(obj.value,
		function (data) {
			for(d in data)
				{
					divobj[index].options[divobj[index].options.length]=new Option(data[d].field1, data[d].field1);
				}
		});
}

function showDetails(){
   
   	var vroleid = window.document.getElementById("roleid").value;
	var vfdate = window.document.getElementById("fdate").value;
	var vtdate = window.document.getElementById("tdate").value;
resetCharts(vroleid,vfdate,vtdate);
//generatePieChart(vroleid,vfdate,vtdate);
//alert("vrlycode" +vrlycode);
}

function getEarlierFinYear(finYear,noYears){

	return ((parseInt(finYear.substring(0, 4)) -noYears) + "-"+ (parseInt(finYear.substring(5)) -noYears)); 
	
}

 function printID(elem)
    {
    	window.document.getElementById(elem).style.display='none';
        window.print();
        window.document.getElementById(elem).style.display='block';
    }

    


</script>  
  <style>
body {
  padding-left: 20px;
  padding-right: 20px;
  padding-bottom: 20px;
}

#sortable {
  width: 100%;
  border: 1px solid #ccc;
  height: 550px;
  padding: 3px;
}
#sortableHDR {
  width: 95%;
  border: 1px solid #ccc;
  height: 80px;
  padding: 3px;
  margin: auto;
}
div.portlet {
  margin: 3px;
  padding: 1px;
  float: left;
  height: 220px;
}
div.portletHDR {
  margin: 3px;
  width:99%;
  padding: 1px;
  float: left;
  height: 70px;
}

#span-1 { width: 48%; }
#span-2 { width: 97%; height:300px;}

.portlet { margin: 0 1em 1em 0; }
.portlet-minimized { height: auto !important; }
.portlet-header { margin: 0.3em; padding-bottom: 4px; padding-left: 0.2em; }
.portlet-header .ui-icon { float: right; }
.portlet-content { padding: 0.0em; }
.portlet-minimized .portlet-content { display: none !important; }
.ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 50px !important; }
.ui-sortable-placeholder * { visibility: hidden; }
.green{border: 1px solid green; background-color: #f1fee5;}
.higlightborder{border: 1px solid #efa619;}
.calbutt {
	background-color: transparent;
	border : 0px;
	height: 16px;
	float: none;
	width: 16px;
	vertical-align: top;
	background-image: url(/MRSECTT/images/cal.gif);
}
  </style>

</head>
<body style="text-align: center;">
<div id="dashboardid">
	  <div id="sortableHDR">
	    
	    <div  class="portlet portletHDR">
        <div class="portlet-header"> Filter Criteria </div>
        <div class="portlet-content">
        	Forward To
        	<select id="roleid" name="roleid" onchange="" >
        		<option value="">ALL</option>
        			<%
							for(int i=0;i<markingToList.size();i++){
							CommonBean beanCommon = (CommonBean) markingToList.get(i);
							%>
								<option value="<c:out value='<%=beanCommon.getField1()%>'/>"><c:out value='<%=beanCommon.getField2()%>'/></option>
							<%
						}%>	
        	</select>
        	
		<input type="text" size="15" name="fdate" id="fdate"  value="<c:out value='<%=sessionBean.getTENURESTARTDATE() %>'/>" onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10" tabindex="5" class="ui-datepicker-calendar">
		<input type="text" size="15" name="tdate" id="tdate"  value="<c:out value='<%=serverDate%>'/>" onblur="chkWorkDate(this);" onkeypress="chknumeric(47);" maxlength="10" tabindex="5">
		
        		
			<button style="color: black;padding: 2px;" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onclick="showDetails();"> Show Details </button>
			<button style="color: black;padding: 2px;" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onclick="printID('sortableHDR');"> Print </button>								
			<br>
		</div>
    	</div>	
    	</div>


<div id="TabContainerDiv" style="width: 95%;  text-align: center;margin: auto;">
 <ul>
    <li><a href="#tabs-1">References</a></li>
    <!-- <li><a href="#tabs-2">File</a></li>-->
   <!-- <li><a href="#tabs-3">Under Development</a></li>-->
  </ul>
<div id="tabs-1">
<table id="cid">
	<tr>
		<td>
	
		
		
		  <div id="sortable">
	    
	    <div id="span-1" class="portlet">
        <div class="portlet-header" print='yes' chartid='1'> Total References </div>
        <div class="portlet-content">
			<div id="chartTotalReferences" style="height: 180px; width:550px;margin: auto;" ></div>
		</div>
    	</div>	  
	    <div id="span-1" class="portlet">
	      <div class="portlet-header" print='yes' chartid='2'> Reference - Status</div>
	      <div class="portlet-content" style="text-align:  left;">
	      
	      <div id="chartReferenceStatus" style="height:170px; width:550px; margin: auto;"></div>
	 
	      </div>
	    </div>
	    <div id="span-2" class="portlet">
	        <div class="portlet-header"  print='yes' chartid='3'>Total References - Subject Wise</div>
	        <div class="portlet-content"><div id="chartRefSubjectwise" style="height:250px; margin: auto;"></div></div>
	    </div>
	
	 
  	  
    
    
    <!-- added by suneel sambharia on 11/02/2014 -->
     <div id="span-1" class="portlet" style="display: none;">
	      <div class="portlet-header" print='yes' chartid='6'> Consumption-Month Wise</div>
	      <div class="portlet-content" style="text-align:  left;">
	      
	      <div id="chartSummaryMonthWise" style="height:170px; width:450px; margin: auto;"></div>
	 
	      </div>
	    </div>

  </div>
		
		
		
		
		</td>

	</tr>

</table>
</div>

<div id="tabs-2" style="display: none;">

			

<table >
	<tr>
		<td>
	
		
		
		  <div id="sortable">
	    
  		<div id="span-1" class="portlet">
        <div class="portlet-header" print='yes' chartid='11'> Billing</div>
        <div class="portlet-content">
			<div id="chartSummaryYearBilling" style="height: 180px; width:450px;margin: auto;" ></div>
		</div>
    	</div>	  
	    <div id="span-1" class="portlet">
	      <div class="portlet-header" print='yes' chartid='12'> Billing-SEB Wise</div>
	      <div class="portlet-content" style="text-align:  left;">
	      
	      <div id="chartSummarySSBarBilling" style="height:170px; width:450px; margin: auto;"></div>
	 
	      </div>
	    </div>
	    <div id="span-1" class="portlet">
	        <div class="portlet-header"  print='yes' chartid='15'>Billing-State Wise</div>
	        <div class="portlet-content"><div id="chartStatewiseBilling" style="height:170px; width:450px; margin: auto;"></div></div>
	    </div>
	
	 
  
    
    <div id="span-1" class="portlet">
        <div class="portlet-header"  print='yes' chartid='14'>Billing-<%if(authlevel.equalsIgnoreCase("7")){%>Division<%}else if(authlevel.equalsIgnoreCase("6")){%>DEE/ADEE<%}else{%>SSE<%}%> Wise</div>
        <div class="portlet-content"  style="text-align:  left;"><div id="chartSummarySSLineBilling" style="height:170px; width:450px; margin: auto;"></div></div>
        
    </div>

  </div>
		
		
		
		
		</td>

	</tr>

</table>
</div>

<div id="tabs-3">

</div>
</div>
</div>
  <div style="position: absolute; top: 0px;background: white;display:none; width: 100%; height:850px;z-index: 1000;" id="ChartPrintOuter" onclick="javascript: this.style.display='none';">
	    <div  class="portletHDR"  style="height: 650px; width: 90%">
        <div class="portlet-header"> Zoomed Chart <button style="color: black" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onclick="printID('dashboardid');"> Print </button></div>
        <div class="portlet-content" >
			<div id="ChartPrint"  style="height: 450px;   margin: auto; display: block;z-index: 1000; " >
        </div>
    	</div></div>
</div>

</body>
