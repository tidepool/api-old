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
    <title>Tidepool Template</title>    
    <meta name="description" content="">
    <meta name="author" content="">

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
          <a class="brand" href="<c:url value="/" />">Tidepool Template</a>
          <div class="nav-collapse">
            <ul class="nav">
              <!-- <li class="active"><a href="/">How it works</a></li>
              <li><a href="/try-it">Try It</a></li>
              <li><a href="/contact">Contact</a></li> -->
            </ul>
          </div><!--/.nav-collapse -->
        
        <c:if test="${ empty account }">	
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
		</c:if>
		
		<c:if test="${ not empty account }">
			<p class="navbar-text pull-right">Logged in as <a href="/">${account.email}</a> | <a href="<c:url value="/signout" />">logout</a></p>
		</c:if>			
        </div>        
      </div>
    </div>

	<div id="wrap">
    	<div class="container-fluid">
    	    <div class="row-fluid mini-layout">
  				<div class="span10 mini-layout-breadcrumbs">
  					BREADCRUMBS
  				</div>
  			</div>
  			<div class="row-fluid mini-layout">  				
    			<div class="span2 mini-layout-sidebar">
      				POW
    			</div>
    			<div class="span8 mini-layout-body">
      				POW
    			</div>
  			</div>
		</div>
	</div>
		
	<div id="footer">
      <div class="container">
        	<div class="muted span-2 menu">Home</div> <div class="muted span-2 menu">Blog</div> <div class="muted span-2 menu">Contact</div> <div class="muted span-2 menu">Terms</div> <div class="muted span-2 menu">Privacy</div><div class="span-1 pull-right social"><img>fb</div><div class="span-1 pull-right social"><img>li</div><div class="span-1 pull-right social"><img>t</div>
        	<p class="muted credit">&copy; Tidepool 2013</p>      
        </div>
    </div>  
    
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
