<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="in.org.cris.mrsectt.Beans.satisfiedCustomerBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RFMS Rating</title>
<style>

 body {
  margin: 0;
  
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  font-family: sans-serif;
}

.hide {
	display: none;
}

h1,
p {
  margin: 0;
  padding: 0;
  line-height: 1.5;
}

.app {
  width: 90%;
  max-width: 800px;
  margin: 0 auto;
}
.container1 {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}

.item {
  width: 90px;
  height: 90px;
  display: flex;
  justify-content: center;
  align-items: center;
  user-select: none;
}

.radio1 {
  display: none;
}

.radio1 ~ span {
  font-size: 5rem;
  filter: grayscale(.4);
  cursor: pointer;
  transition: 0.3s;
}

.radio1:checked ~ span {
  filter: grayscale(0);
  font-size: 8rem;
}
 
 label.error {
    color: #772b35;
      text-align: center;   
    color: red;
}

.label1 {
    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
     color: white;
     font-size: 28px;
}

.label-primary1 {
    background-color: #337ab7;
}


.txt-input{
border: 0px none;
outline: none;
width: 200px;
height:30px;
text-align: center;
text-transform: capitalize;
padding:15px;
padding-left:21px;
}

/* .progress-bar {
  text-align: left;
  transition-duration: 3s;
} */


</style>
 <link href="crisfeedback/css/style.rating.min.css" rel="stylesheet">  
 <link rel="stylesheet" href="crisfeedback/css/jquery.toast.min.css">
  
 <link href="crisfeedback/css/bootstrap.min.css" rel="stylesheet"> 
<link href="crisfeedback/font-awesome/css/font-awesome.min.css" rel="stylesheet">
 <link href="crisfeedback/css/fontawesomenew.css" rel="stylesheet"> 
<!-- <link href="css/plugins/toastr/toastr.min.css" rel="stylesheet"> --><!--  -->
<link href="crisfeedback/css/toastr.min.css" rel="stylesheet">
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/FeedbackDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<%	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin"); %>
</head>

  <body onload="checkUserRating()">  
<!--  <body> -->

 <!--  <div class="bigbox c-fixed-components text-center">   -->   
   <!--   <div th:replace="pat/topbarrating"> </div>  -->  
<%--  <form name="loginForm" method="post" action="/MRSECTT/LoginController"> --%>
    <input type="hidden" id="loginid" name="loginid" value="<c:out value='<%= sessionBean.getLOGINID()%>'/>" >
   <input type="hidden" id="password" name="password" value="<c:out value='<%= sessionBean.getPASSWORD()%>'/>">
   <div class="c-wrapper c-fixed-components">  
        <div class="container-fluid">       
        <div class="card">
		<div class="card-header bg-info h1">
					<span style="color:white;"><strong>User  Rating</strong></span>  
					
					<a href="${pageContext.request.contextPath}/LogOffController"><button id="logout"  class="btn bg-transparent pull-right"  type="button" style="color:white;" >Logout</button></a>
		</div>
        <div class="card-body">			
		<div class="app ">
		<div class=" text-center" id="aftersubmitmsgdiv">
  			<h1>How would you rate this application?</h1>
  			<h4>Let us know how we did ! <br>On the basis of User Interface, Functionality, Ease of access and Navigation.</h4>
  
  			
  		</div><div class="progress hide" style="height:30px;" id="progress">
  		 <div id="progressbar" class="progress-bar progress-bar-striped active"  role="progressbar"  style="width:1%;height:30px;font-size: 25px;"  ></div>
  		 </div>
  		<span  id="aftersubmitmsg" class="label1 label-primary1  text-center hide">User satisfaction for RFMS-MR portal last month was: </span>
  		<div class="text-center"><h4><span  id="aftersubmitmsg1" class="hide text-center">This page will be redirected to main application after 10 seconds.<br> OR <br> Click 
      <button type="button" class="btn btn-primary" onclick='loadmain()'>Here</button> to load main content immediately.</span ></h4></div>
   
<form method="post" id="userrating">
  <div class="container1 text-center">
      <div class="item">
      <label for="1">
      <input class="radio1 rating" type="radio" name="feedback" id="1" value="1" onclick="display()">
       <span   >
			<i class='fas fa-frown' style='color: #E2584B'></i>				
	</span>    <br>
      <strong> Poor</strong>
    </label>
    </div>


    <div class="item">
      <label for="2">
      <input class="radio1 rating" type="radio" name="feedback" id="2" value="2" onclick="display()">
      <span> <i  class="fas fa-meh-o " style='color: #F6D554' ></i></span><br>
      <strong>Average</strong>
     
    </label>
    </div>


    <div class="item">
      <label for="3">
      <input class="radio1 rating" type="radio" name="feedback" id="3" value="3" onclick="display()">
      <span> <i  class="fas fa-smile-o " style='color: #1EB262' ></i></span><br>
      <strong>Good</strong>
    </label>
    </div>

  </div>
  <div class="text-center">
  <input class=" hide " type="text" name="rating" id="rating" >
  </div>
  <div class="form-group">
  <label for="comment"><strong>Feedback:</strong></label>
  <textarea class="form-control" rows="6" id="comments" name="comments"  maxlength="500" placeholder="Start Typing..."></textarea>
   <div id="the-count" class="text-left">
    <span id="current">0</span>
    <span id="maximum">/ 500</span>
  </div>
  
 
  <button id="ratingsubmit"  onclick="submitFeedback()" class="btn btn-primary" type="button" >Submit</button>
 <!--  <button type="button" class="btn btn-warning">Skip</button> -->
</div>
</form>
<div class="text-center">
  <input class="txt-input"   name="outvalue" id="outvalue" size="100" >
  </div>
<div id="lastmsg">
<ol>
<strong>Your valuable feedback is requested to help us serve you better.</b>.</strong>
</ol>
</div>
</div>


							</div>


						</div>
 

					</div>
						  
				</div>		  
		<!-- </form>	 -->			  

   </body>
   


<script src="crisfeedback/js/jquery-2.1.1.js"></script>
    <script src="crisfeedback/js/bootstrap.min.js"></script>
    
    <script src="crisfeedback/js/jquery.toast.min.js"></script>
   <%--  <script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/feedback.js'></script> --%>
<script type="text/javascript">
<c:choose>
<c:when test="${usertype eq 'D' }">
var homeUrl="HomeOther.jsp";
</c:when>
<c:when test="${usertype eq 'H' }">
var homeUrl="HomeOther_SubjectWise";
</c:when>
<c:when test="${usertype eq 'S' }">
var homeUrl="HomeOther_SubHeads.jsp";
</c:when>
<c:when test="${usertype eq 'R' }">
var homeUrl="HomeOther_Railway.jsp";
</c:when>
<c:otherwise>
var homeUrl="HomeIframe.jsp";
</c:otherwise>
</c:choose>
$('textarea').keyup(function() {
    
	  var characterCount = $(this).val().length,
	      current = $('#current'),
	      maximum = $('#maximum'),
	      theCount = $('#the-count');
	    
	  current.text(characterCount);
	 
	  
	  /*This isn't entirely necessary, just playin around*/
	  if (characterCount < 150) {
	    current.css('color', '#666');
	  }
	  if (characterCount > 150 && characterCount < 200) {
	    current.css('color', '#6d5555');
	  }
	  if (characterCount > 200 && characterCount < 250) {
	    current.css('color', '#793535');
	  }
	  if (characterCount > 250 && characterCount < 300) {
	    current.css('color', '#841c1c');
	  }
	  if (characterCount > 300 && characterCount < 400) {
	    current.css('color', '#8f0001');
	  }
	  
	  if (characterCount >= 400) {
	    maximum.css('color', '#8f0001');
	    current.css('color', '#8f0001');
	    theCount.css('font-weight','bold');
	  } else {
	    maximum.css('color','#666');
	    theCount.css('font-weight','normal');
	  }
	  
	      
	});
	
function update(requiredwidth) {
	  var element = document.getElementById("progressbar");   
	  var width = 1;
	  var identity = setInterval(scene, 10);
	  function scene() {
	    if (width >= requiredwidth) {
	      clearInterval(identity);
	    } else {
	      width++; 
	      element.style.width = width + '%'; 
	      element.innerHTML = width * 1  + '%';
	    }
	  }
	}

$('#ratingsubmit').click(function(e) {
    e.preventDefault();
     var form = $(this).parents('form');
     $.ajax({
      //Rest of the code
     success:function(){
       $('body').find(form).hide();
       $('#aftersubmitmsgdiv').hide();
       $('#lastmsg').hide();
       
      }

    })
 });


function loadmain(){
	
	$.toast({
		heading : 'Success',
		text : 'Thank you for submitting your feedback &#128522; !<br> ',
		showHideTransition : 'slide',
		icon : 'success',
		hideAfter : 4000,
		position : 'top-right'
	})
	
	var _ctx = "${pageContext.request.contextPath}/"+homeUrl;
	window.location.href = _ctx;
}


function display() { 
	//let elements = document.getElementsByName("feedback").value;
	if (document.getElementById('1').checked) {
		  //alert(document.getElementById('1').value);
		document.getElementById('rating').value =document.getElementById('1').value;
		document.getElementById('comments').placeholder="Start Typing...";
		}
	if (document.getElementById('2').checked) {
		  //alert(document.getElementById('2').value);
		document.getElementById('rating').value =document.getElementById('2').value;
		document.getElementById('comments').placeholder="Start Typing...";
		}
	if (document.getElementById('3').checked) {
		  //alert(document.getElementById('3').value);
		document.getElementById('rating').value =document.getElementById('3').value;
		document.getElementById('comments').placeholder="Start Typing...";
		}
    }


 function checkUserRating(){
	var ratingtestalready;
	//RatingCall.checkUserData(function(data){console.log(data);
	FeedbackDAO.satisfiedCustomer(function(data){console.log(data.positive);
		console.log(data.total);
		var messageaverage="User Satisfaction for RFMS-MR portal last month was : "
		var average = (data.positive/data.total*100).toFixed(2);
		var averagewithoutdecimal = data.positive/data.total*100;
		//alert(average);
		if(isNaN(average)){
			messageaverage+="No Data."
		}
		else{
			messageaverage=messageaverage+average+"%";
		}
		document.getElementById('aftersubmitmsg').innerText=messageaverage;
		//document.getElementById('progressbar').innerText=average+"%";
		//update(averagewithoutdecimal);
		//document.querySelector(".progress-bar").style.width = average + "%";
	});
	
} 
	
function checkFeedbackExist(){
	var loginid = '<c:out value="<%=sessionBean.getLOGINID() %>"/>';
	FeedbackDAO.checkExistingFeedbackforMonth(loginid,function(data){
		if(data){
			alert("Feedback already present");
		}else 
			{
			alert("Feedback not present");
			
			}
	});
}
 function satisfiedCustomers(){
	
	 FeedbackDAO.satisfiedCustomer(function(data){
		 
		 var average = (data.positive/data.total*100).toFixed(2);
		 document.getElementById('happycustomers').innerHTML=average;
		 document.getElementById('totalrating').innerHTML=data.total;
		 $("#happycustomersdiv").removeClass("hidden");
		 $("#ratingdiv").addClass("hidden");
		 $("#reviewdiv").addClass("hidden");
		 $("#headingdiv").addClass("hidden");
		 return data;
  	});
 }
function submitFeedback(){
	//checkFeedbackExist();
	var review=$("#comments").val();
	var loginid = '<c:out value="<%=sessionBean.getLOGINID() %>"/>';
	//alert(loginid);
	//alert(review);
	var ratingOption=document.getElementsByName('feedback');
 	if (!(ratingOption[0].checked||ratingOption[1].checked||ratingOption[2].checked)) {
	 	alert("Please rate by clicking on of the smiley.")
	 	setTimeout(function () {location.href='${pageContext.request.contextPath}/Feedback4.jsp'},100);
	 	return false;
 	}
	var rating = $("input[type='radio'][name='feedback']:checked").val();
	if ((rating==1)&&(review.trim()=="")){
		
		alert("Please enter feedback.");
		setTimeout(function () {location.href='${pageContext.request.contextPath}/Feedback4.jsp'},100);
		return false;
	}
	//alert(rating);
      FeedbackDAO.checkExistingFeedbackforMonth(loginid,function(data){
    	if(data){
			alert("Feedback already present");
			$('#aftersubmitmsg').removeClass('hide');
			$('#aftersubmitmsg1').removeClass('hide');//alert("success");
			$('#progress').removeClass('hide');
			
			FeedbackDAO.satisfiedCustomer(function(data1){
			var averagewithoutdecimal = data1.positive/data1.total*100;
			update(averagewithoutdecimal);
			});
			
			setTimeout(function () {location.href='${pageContext.request.contextPath}/'+homeUrl},10001);
			
			return false;
		}else 
			{  
			//alert("Feedback not present");

			FeedbackDAO.saveFeedback(rating,review,loginid,function(data){
		    		//alert(data);
		    			//notifyUser("success",data[1]);
		    			$('#aftersubmitmsg').removeClass('hide');
		    			$('#aftersubmitmsg1').removeClass('hide');//alert("success");
		    			$('#progress').removeClass('hide');
		    			//notifyUser("success",data[1]);
		    			//satisfiedCustomers();
		    			FeedbackDAO.satisfiedCustomer(function(data1){
						var averagewithoutdecimal = data1.positive/data1.total*100;
						update(averagewithoutdecimal);
						});
			
		    			setTimeout(function () {location.href='${pageContext.request.contextPath}/'+homeUrl},10001);
		    			
		    		
		    	});
			
			  }
    });  

    	
    	
}
	
</script>

  <SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<%-- <script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/feedback.js'></script>
 --%></html>