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
  			<h1></h1>
  			<p>Now you try...Select attribute buttons that match the all the highlighted regions.</p>
  			
  			<p>
	  		<div>		  			
	  			<div class="btn-toolbar">
	  			    <form class="form-horizontal" action="<c:url value="/training1Post" />" method="post">
	  				<c:forEach var="attribute" items="${codedAttributes}" varStatus="rowCounter">
						<div class="btn-group">
							<button class="attribute btn" data-placement="bottom" data-trigger="hover" data-content="${ attribute.element_description }" id="${ attribute.element }">${ attribute.element_name}</button>							
							<input type="hidden" id="${ attribute.element }_field" name="button${ rowCounter.index }">
						</div>
	  				</c:forEach>	  				
	  				<div class="btn-group">
	  					<button type="submit" class="btn btn-success" id="nextButton">OK</button>
	  				</div>
	  				<input type="hidden" id="ci" name="ci" value="${ currentTrainingItem }">
	  				</form>
	  			</div>	  			
	  		</div>
	  		</p> 	
     	
  			<p>
  				<div>
					<canvas id="imageCanvas" height="700px" width="700px"></canvas>
				</div>	
     		</p>
     	
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
	<script>
	
		var boxen = [
					<c:forEach var="highlight" items="${trainingItem.codedItem.highlightMap}">
						[
							[${highlight.value.x0}, ${highlight.value.y0}],
							[${highlight.value.x0}, ${highlight.value.y1}],
							[${highlight.value.x1}, ${highlight.value.y1}],
							[${highlight.value.x1}, ${highlight.value.y0}],
							[${highlight.value.x0}, ${highlight.value.y0}]						
						]<c:if test="${fn:length(allAttributes) - 1 != rowCounter.index}">,</c:if>
						
					</c:forEach>
		             ];
	
	     function drawBox() {
	    	 
	    	 var obj = {
					  strokeStyle: "#EE0000",
					  strokeWidth: 2,
					  rounded: true
					};

		
			for (var box=0; box<boxen.length; box+=1) {
				var pts = boxen[box];
				
				for (var p=0; p<pts.length; p+=1) {
			  		obj['x'+(p+1)] = pts[p][0];
			  		obj['y'+(p+1)] = pts[p][1];
				}
			
				$("#imageCanvas").drawLine(obj);
			}
			
	    	 	    	 
	     }
	
		(function ($) {
			$(document).ready(function () {				
				
				$("#imageCanvas").drawImage({
					  source: "${cdn_url}/${trainingItem.bucketName}/${trainingItem.folderName}/${trainingItem.elementFolderName}/${trainingItem.pictureId}",					  
					  x: 0, y: 0,					  
					  fromCenter: false,
					  load:drawBox							
				});
				
				$('.attribute').each(function() {
					var button = $(this);
				    $(this).click(function() {					 		
				    	if (button.is('.active')) {
				    		$('#' + button.attr('id') + '_field').val("");
				    		button.removeClass('active');
				    	} else {
				    		$('#' + button.attr('id') + '_field').val(button.attr('id'));
				    		button.addClass('active');
				    	}
				    	
				    	return false;
				    });
				});
									
			});
		})(jQuery);		
	</script>


  </body>

</html>