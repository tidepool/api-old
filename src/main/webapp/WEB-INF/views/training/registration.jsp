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
    <title>Tidepool Survey Register</title>   
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
    </style>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.css" />" rel="stylesheet">

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
          <a class="brand" href="<c:url value="/" />">Tidepool Survey Register</a>
          <div class="nav-collapse">
            <ul class="nav">                            
             <c:if test="${ account.admin}">
	            <li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Admin<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/admin/createAccount" />">Create Account</a>
								</li>
								<li><a href="<c:url value="/admin/accounts" />">Accounts</a>
								</li>
								<li class="divider"></li>
								<li class="nav-header">Training</li>
								<li><a href="<c:url value="/admin/trainingSets" />">Training sets</a>
								</li>							
								</li>
								<li class="divider"></li>
								<li class="nav-header">Groups</li>
								<li><a href="<c:url value="/admin/groups" />">Group List</a>
								</li>							
								</li>
							</ul>
						</li>
			</c:if>
            </ul>
            <p class="navbar-text pull-right">Logged in as <a href="/">${account.email}</a> | <a href="<c:url value="/signout" />">logout</a></p>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

		<p>
			Thank you for participating in this survey. You will be asked to identify attributes of photos by first clicking an attribute (I.e. “Birds”) and then highlighting the area of the photo where that attribute exists.
		</p>
		
		<p>
			The form below is optional to complete. Your personal information remains anonymous and we will not disclose your information to third parties. The purpose of collecting this information is to provide us with demographic data for internal analysis. Additionally, if we find your results to be reliable and consistent, we’d like the opportunity to work with you again in the future.
		</p>

		<p>
		<form class="form-horizontal" action="<c:url value="/registerPost" />" method="post">
			
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
				<label class="control-label" for="gender">Gender</label>
				<div class="controls">
					<select name="gender" id="gender">
						<option name="male" value="male">Male</option>
						<option name="female" value="female">Female</option>
					</select>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="birthYear">Birth Year</label>
				<div class="controls">
					<input type="text" id="birthYear" name="birthYear" placeholder="Year">
				</div>
			</div>	
			

			<div class="control-group">
				<label class="control-label" for="education">Education</label>
				<div class="controls">
					<select name="education" id="education">
						<option name="highschool" value="highschool">High School</option>
						<option name="college" value="college">College</option>
						<option name="advancedDegree" value="advancedDegree">Advanced Degree</option>
					</select>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="zipcode">Zip Code</label>
				<div class="controls">
					<input type="text" id="zipcode" name="zipcode" placeholder="Zip Code">
				</div>
			</div>
			
			<div class="control-group">
				<div class="controls">				
					<button type="submit" class="btn">Next</button>
				</div>
			</div>
		</form>
		</p>
		<footer>
        <p>&copy; Tidepool 2012</p>
      </footer>

    </div> <!-- /container -->

    <!-- Le javascript
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
				
			});
		})(jQuery);		
	</script>


  </body>

</html>