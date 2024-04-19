
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="in.org.cris.mrsectt.Beans.satisfiedCustomerBean"%>
<%@page import="in.org.cris.mrsectt.Beans.MstLogin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setBundle basename="devmgs.i18n.messages" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="script/scripts.js"></script>
<SCRIPT src='${pageContext.request.contextPath}/dwr/interface/FeedbackDAO.js'></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/engine.js"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/dwr/util.js"></SCRIPT>
<%	MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin"); %>
        <title>RFMS : User Feedback</title>
       <style>input[type="radio"] {
    margin-left:10px;
}</style>
       
      <script>
     
      </script>
    </head>
    
    <body>
    
 <%-- <jsp:include page="menu.jsp"></jsp:include> --%>

<!--  Content Start -->
<div class=" container-fluidy panel panel-primary clear-top">
  <div class="panel-heading clearfix">
    <h2 class="panel-title pull-left">User Rating</h2>
    <div class="btn-group pull-right hidden">
        
    </div>
  </div>
  <div id="aftersubmitmsgdiv1">
  <div class="panel-body">
 <div class=" text-center" id="aftersubmitmsgdiv">
  			<h1>How would you rate this application?</h1>
  			<h4>Let us know how we did ! <br>On the basis of User Interface, Functionality, Ease of access and Navigation.</h4>
  
  			
  		</div>
   </div>
  <div class="row" id="ratingdiv">
 			
 		
 		
    <div class="col-sm-3"  style="display:inline">
      <label for="0" class="radio-inline">
      <img class="" src="crisfeedback/images/bad.jpg" width="5%" height="5%" />
      <input class="radio" type="radio" name="rating" id="0" value="1">
      <i class="glyphicon glyphicon-ok hidden"></i>
      <span>Poor</span>
    </label>
    </div>

    <div class="col-sm-3"  style="display:inline">
      <label for="1" class="radio-inline">
      <img class="" src="crisfeedback/images/average.jpg" width="5%" height="5%" />
      <input class="radio" type="radio" name="rating" id="1" value="2">
      <i class="glyphicon glyphicon-ok hidden"></i>
      <span>Average</span>
    </label>
    </div>

    <div class="col-sm-3"  style="display:inline">
      <label for="2" class="radio-inline">
      <img class="" src="crisfeedback/images/green.jpg" width="5%" height="5%"/>
      <input class="radio" type="radio" name="rating" id="2" value="3">
      <i class="glyphicon glyphicon-ok hidden"></i>
      <span>Good</span>
    </label>
    </div>
		<div class="col-sm-3"></div>	
			
    	
    </div>
    <br><br>
  <div class="row" id="reviewdiv">
  <div class="col-sm-3">
   <label>Please leave your feedback</label>
  </div>
    	<div class="col-sm-5">

			<div class="form-group has-feedback">
	       
	        <textarea rows="3" id="review" style="width: 50%" name="review" maxlength="1000" class="htmlEditor_removed"></textarea>
	        <span class="hint" id="textarea_message"></span>
	        </div>

		</div>
    	<div class="col-sm-5">
    	<div class="form-group has-feedback">
	        <br>
	        <button class="btn btn-primary" onclick="submitFeedback();" id="buttonfeedback">Submit</button>
	        </div>
	        
    	
    	</div>
    </div>
    </div>
    <div class="row">
    </div>
    <div  id="aftersubmitmsg2"  style="display:none">
    
    </div>
    <div  id="aftersubmitmsg1" style="display:none">
    <h4><span >This page will be redirected to main application after 10 sec, or click 
      <button type="button" onclick='loadmain()'>Here</button> to load main content immediately.</span ></h4>
  </div>
  </div>
  

<!--  Content Ends -->


<script>
$(document).ready(function(){
    // add/remove checked class
    $(".rating").each(function(){
        if($(this).find('input[type="radio"]').first().attr("checked")){
            $(this).addClass('rating-checked');
        }else{
            $(this).removeClass('rating-checked');
        }
    });

    // sync the input state
    $(".rating").on("click", function(e){
        $(".rating").removeClass('rating-checked');
        $(this).addClass('rating-checked');
        var $radio = $(this).find('input[type="radio"]');
        $radio.prop("checked",!$radio.prop("checked"));

        e.preventDefault();
    });
});

$('textarea#review').on('keyup',function() 
		{
		  var maxlen = $(this).attr('maxlength');
		  
		  var length = $(this).val().length;
		  if(length > (maxlen-10) ){
		    $('#textarea_message').text('max length '+maxlen+' characters only!')
		  }
		  else
		    {
		      $('#textarea_message').text('');
		    }
		});

function loadmain(){
	location.href='${pageContext.request.contextPath}/HomeIframe.jsp'
}
function satisfiedCustomers(){
	
	FeedbackDAO.satisfiedCustomer(function(data){
		
		 var average = (data.positive/data.total*100).toFixed(2);
		 var averagemessage="User satisfaction level for the previous month is: ";
		 document.getElementById('aftersubmitmsg2').innerHTML=averagemessage+average+"%";
		 document.getElementById("aftersubmitmsg2").style.display='block';
 	});
}
function checkFeedbackExist(){
	feedback.checkExistingFeedbackforMonth(function(data){
		if(data){
			alert("Feedback already present");
		}else 
			{
			alert("Feedback not present");
			
			}
	});
}
 
function submitFeedback(){
	//checkFeedbackExist();
	//var review=$("#review").val();
	var review=document.getElementById('review').value;
	//alert(review);
	var loginid = '<c:out value="<%=sessionBean.getLOGINID() %>"/>';
	var ratingOption=document.getElementsByName('rating');
 	if (!(ratingOption[0].checked||ratingOption[1].checked||ratingOption[2].checked)) {
	 	alert("Please rate by clicking on of the smiley.");
	 	setTimeout(function () {location.href='${pageContext.request.contextPath}/Feedback.jsp'},100);
	 	return false;
 	}
 	var rating=-1;
 	if(ratingOption[0].checked){
 		rating=1;
 	}
 	else if(ratingOption[1].checked){
 		rating=2;
 	}
 	else if(ratingOption[2].checked){
 		rating=3;
 	}
 	
	if ((rating==1)&&(review=="")){
		
		alert("Please enter feedback.");
		setTimeout(function () {location.href='${pageContext.request.contextPath}/Feedback.jsp'},100);
		return false;
	}
	//var rating = $("input[type='radio'][name='rating']:checked").val();
	//var rating = document.querySelector('input[name="rating"]:checked').value;
	//alert(rating);
	 FeedbackDAO.checkExistingFeedbackforMonth(loginid,function(data){
	    	if(data){
				alert("Feedback already present");
				document.getElementById("aftersubmitmsg1").style.display='block';
				//$('#aftersubmitmsg').removeClass('hide');
				//$('#aftersubmitmsg1').removeClass('hide');//alert("success");
				
				setTimeout(function () {location.href='${pageContext.request.contextPath}/HomeIframe.jsp'},10001);
				
				return false;
			}else 
				{  
				//alert("Feedback not present");

				FeedbackDAO.saveFeedback(rating,review,loginid,function(data){
			    		alert("Success");
			    		satisfiedCustomers();
			    			//notifyUser("success",data[1]);
			    			//$('#aftersubmitmsg').removeClass('hide');
			    			document.getElementById("aftersubmitmsgdiv1").style.display='none';
			    			document.getElementById("aftersubmitmsg1").style.display='block';
			    			//$('#aftersubmitmsg1').removeClass('hide');//alert("success");
			    			//notifyUser("success",data[1]);
			    			//satisfiedCustomers();
			    			setTimeout(function () {location.href='${pageContext.request.contextPath}/HomeIframe.jsp'},10001);
			    			
			    		
			    	});
				
				  }
	    });  


    	
    	
}

 
</script>
    </body>
</html>