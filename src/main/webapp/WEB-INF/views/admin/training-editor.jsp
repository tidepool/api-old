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
    <title>Tidepool Survey</title>    
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Le styles -->
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/bootstrap/css/tidepool.css" />" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
    </style>
   

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
          <a class="brand" href="<c:url value="/" />">Tidepool Training</a>
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
		
		<div class="hero-unit">			  			
  			<div class="row">	  				  			
	  			<%-- <div class="span-4">
		  			
		  			<p>Current Coded Attributes</p>
		  			
		  			<p>
		  				<div>
							<canvas id="imageCanvas" height="300px" width="300px"></canvas>
						</div>	
		     		</p>
		     	
		     		<p>
		     		 Attributes:
		     		 <c:forEach var="attribute" items="${codedAttributes}" varStatus="rowCounter">
		     		 	<div>${ attribute.element_name }</div>
		     		 </c:forEach>
		     		</p>
		    		 	
	     		</div> --%>
	     		
	     		<div class="span-4">
	  				<p>Edit Attributes</p>
	  				<p>
	  				<img id="jcrop_target" src="${cdn_url}/${trainingItem.bucketName}/${trainingItem.folderName}/${trainingItem.pictureId}">
	  				</p>
	  							
	  				<p>
			  			<div>	  			
			  				<button class="btn btn-success" id="nextButton">Done</button>
		  				</div>
		  			</p>
	  				
	  			</div>
	     		
	     		<div class="span-3">
		     		<p>
				  		<div>		  			
				  			<div>
				  				<c:forEach var="attribute" items="${allAttributes}" varStatus="rowCounter">
									
									<c:if test="${rowCounter.index == 0 or rowCounter.index % 5 == 0}">     	
     									<c:if test="${rowCounter.index != 0}">
     										</div>
     									</c:if>
      									
      									<div class="btn-group">
      								
      								</c:if>
																		
										<button class="attribute btn" data-placement="bottom" data-trigger="hover" data-content="${ attribute.element_description }" id="${ attribute.element }">${ attribute.element_name}</button>							
									
									<c:if test="${fn:length(allAttributes) - 1 == rowCounter.index}">  
         								</div>
      								</c:if>
									
				  				</c:forEach>
				  			</div>
				  		</div>
				  	</p>
	     		</div>
	     		
     		</div>
     	</div>
      
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
	 <script src="<c:url value="/resources/bootstrap/js/jcanvas.min.js"/>"></script>
	 <script src="<c:url value="/resources/js/jquery.Jcrop.min.js"/>"></script>
	<script>
	
	 	var jcrop_api;
	 
		(function ($) {
			$(document).ready(function () {				
				
				$('#jcrop_target').Jcrop({
					//onChange: showCoords,
					onSelect: function(c) { alert("Hola:" + c.x)}
				}, function(){
					  jcrop_api = this;
				});
				
				$("#imageCanvas").drawImage({
					  source: "${cdn_url}/${trainingItem.bucketName}/${trainingItem.folderName}/${trainingItem.pictureId}",					  
					  x: 0, y: 0,					  
					  fromCenter: false	
								
				});
				
				var obj = {
						  strokeStyle: "#000",
						  strokeWidth: 2,
						  rounded: true
						};

				// Your array of points
				var x0 = 0;
				var y0 = 0;
				var x1 = 100;
				var y1 = 100;
				var pts = [
				  [x0, y0],
				  [x0, y1],
				  [x1, y1],
				  [x1, y0],
				  [x0, y0]
				];

				// Add the points from the array to the object
				for (var p=0; p<pts.length; p+=1) {
				  obj['x'+(p+1)] = pts[p][0];
				  obj['y'+(p+1)] = pts[p][1];
				}

				// Draw the line
				$("canvas").drawLine(obj);
				
			});
		})(jQuery);		
	</script>


  </body>

</html>