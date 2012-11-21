<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Tidepool Sign In / Register</title>    
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/bootstrap/css/anchor-footer.css" />" rel="stylesheet">
   <%--  <link href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.css" />" rel="stylesheet"> --%>
	<link href="<c:url value="/resources/bootstrap/css/framework.css" />" rel="stylesheet">
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="<c:url value="/" />">Tidepool</a>
          <div class="nav-collapse">
            <ul class="nav">
              <!-- <li class="active"><a href="/">How it works</a></li>
              <li><a href="/try-it">Try It</a></li>
              <li><a href="/contact">Contact</a></li> -->
            </ul>
          </div><!--/.nav-collapse -->
        	
         <form class="navbar-form pull-right" action="<c:url value="/signin/authenticate" />" method="post">
			<c:if test="${not empty param.login_error}">
		    	<div>
  					Error logging in. Try again.
				</div>    		
			</c:if>            
            <input type="text" class="span2" name="j_username" id="inputLogin" placeholder="Email">
  			<input type="password" name="j_password" id="inputPassword" placeholder="Password">
  			<button type="submit" class="btn">Login</button>
		</form>
			
        </div>
        
      </div>
    </div>

	<div id="wrap">
    <div class="container">    	
    	<div class="row-fluid">
    		<!-- <div class="span5" style="padding-top:40px">...</div> -->
			<div class="hero-unit">
			
				<form class="form-horizontal" action="<c:url value="/userRegister" />" method="POST">
									
					<div class="control-group alert alert-error" style="display:none" id="error">Hold up....</div>
												
					<div class="control-group">
						<label class="control-label" for="firstName">First Name</label>
						<div class="controls">
							<input type="text" id="firstName" name="firstName" placeholder="First Name">
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="lastName">Last Name</label>
						<div class="controls">
							<input type="text" id="lastName" name="lastName" placeholder="Last Name">
						</div>
					</div>
		
					<div class="control-group">
						<label class="control-label" for="company">Company</label>
						<div class="controls">
							<input type="text" id="company" name="company" placeholder="Company">
						</div>
					</div>	
					
					<div class="control-group">
						<label class="control-label" for="phoneNumber">Phone Number</label>
						<div class="controls">
							<input type="text" id="phoneNumber" name="phoneNumber" placeholder="Phone number">
						</div>
					</div>							
					
					<div class="control-group">
						<label class="control-label" for="email">Email</label>
						<div class="controls">
							<input type="text" id="email" name="email" placeholder="email">
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="password">Password</label>
						<div class="controls">
							<input type="password" id="password" name="password" placeholder="Password">
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">				
							<button type="submit" id="signUpButton" class="btn">Sign Up</button>
						</div>
					</div>
				</form>
			</div>	
		</div>	
		
		
	</div> <!-- /container -->
	
	</div>
		
	<div id="footer">
      <div class="container">
        	<p class="muted credit">&copy; Tidepool 2013</p>      
        </div>
    </div>  
    
       
    <!-- El javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:url value="/resources/bootstrap/js/jquery.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-transition.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-alert.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-modal.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-dropdown.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-scrollspy.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-tab.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-tooltip.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-popover.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-button.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-collapse.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap-carousel.js"/>"></script>
	
	<script>	
		(function ($) {
			$(document).ready(function () {								
				$('#signUpButton').click(function() {					
						if ($("#firstName").val() == '' ||
							$("#lastName").val() == '' ||
							$("#company").val() == '' ||
							$("#phoneNumber").val() == '' ||
							$("#email").val() == '' ||
							$("#password").val() == ''	
							) {
							   $('#error').html('Please fill in all fields!');
							   $('#error').show();
							}
							else {
							 	return true;
							}
						
						return false;
					});								
			});
		})(jQuery);		
	</script>
  </body>
</html>
