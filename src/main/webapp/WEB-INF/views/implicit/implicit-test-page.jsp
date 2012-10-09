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
          <a class="brand" href="<c:url value="/" />">Tidepool Survey</a>
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
		<input type="hidden" name="explicitId" id="explicitId" value="${ codedItem.id }">
		<input type="hidden" name="currentPage" id="currentPage" value="${ currentPage }">
		<input type="hidden" name="buttonPanelSize" id="buttonPanelSize" value="${fn:length(codedAttributes)}">
		<input type="hidden" name="buttonPanelIndex" id="buttonPanelIndex" value="1">
		<div class="hero-unit">			
  			<h1></h1>
  			<p>Please select as many attributes from the following photo:</p>
  			
  			<p>  			
  				<div >
	  				<img class="imageViewer" id="jcrop_target" src="${cdn_url}/${codedItem.bucket_name}/${codedItem.folder_name}/${codedItem.picture_id}">
	  			</div>
	  		</p>
	  				  		
	  		<p>
		  		<div style="display:none" id="highlightAlert" class="alert alert-success">
	  					Please highlight the associated attribute by dragging the mouse on the picture.		
				</div>
	  		</p>
	  		
	  		<p>
	  		<div>		  			
	  			<c:forEach var="rollups" items="${codedAttributes}" varStatus="rollupCounter">
	  				<span id="buttonToolbar${ rollupCounter.count }" class="btn-toolbar" <c:if test="${ rollupCounter.count gt 1 }">style="display:none"</c:if> >	  				
	  					<c:forEach var="attribute" items="${rollups}" varStatus="rowCounter">							
								<button class="attribute btn" tidepool-highlightable="${ attribute.highlightable }" data-placement="bottom" data-trigger="hover" data-content="${ attribute.element_description }" id="${ attribute.element }">${ attribute.element_name}</button>														
		  				</c:forEach>
	  				</span>
	  			</c:forEach>	
	  		</div>
	  		</p>
	  		
	  		<p>
	  		<div>		
	  			<textarea id="textInput" class="attributeInput" name="textinput" rows="3" placeholder="Describe the image you see.."></textarea>
	  			<button class="btn btn-success" id="nextButton">Next</button>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
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
    <script src="<c:url value="/resources/js/jquery.Jcrop.min.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
    
	
	<script>
		var SCALE_VALUE = 0.50;
	    var contextPath = "${pageContext.request.contextPath}";
	    var jcrop_api;
	    var currentAttribute;
	    function showCoords(c) {
	    	
	    	var inputx0 = c.x / SCALE_VALUE;
	    	var inputy0 = c.y / SCALE_VALUE;
	    	var inputx1 = c.x2 / SCALE_VALUE;
	    	var inputy1 = c.y2 / SCALE_VALUE;
	    	$.post(contextPath + "/json/saveattribute.ajax", { explicitId:$("#explicitId").val(), 
	    														attributeId:currentAttribute,
	    														x0:inputx0, 
		    													y0:inputy0, 
		    													x1:inputx1,
		    													y1:inputy1,		
 	    														height:c.height, 
	    														width:c.weight }, function(attribute) {	
	    	
	    															$('#highlightAlert').hide();		
	    															jcrop_api.release();	    															
	    															jcrop_api.disable();
	    															//jcrop_api.destroy();
	    															//jcrop_api = null;
	    	});			
		};
		
		function initializeJCrop() {
			$('#jcrop_target').Jcrop({					
				onSelect: showCoords
			}, function(){
				  jcrop_api = this;
			});
		}
		
		(function ($) {
			$(document).ready(function () {
				
				
				$("#nextButton").click(function() {	
					
					if ($('#textInput').val() != '') {
						$.post(contextPath + "/json/saveattribute.ajax", { explicitId:$("#explicitId").val(), attributeComment:$('#textInput').val()}, function(attribute) {	
							$('#textInput').hide();
							$('#textInput').val("");
					 	});
					}
					
				
					var currentButtonGroupCount = $('#buttonPanelSize').val();
					var currentButtonGroupIndex = $('#buttonPanelIndex').val();
					
					if (currentButtonGroupIndex == (currentButtonGroupCount - 1)) {
						$.post(contextPath + "/json/saveattribute.ajax", { explicitId:$("#explicitId").val(), attributeComment:$('#textInput').val()}, function(attribute) {	
							window.location.href = contextPath + "/survey";		    			 														 
						 });						
					} else {
						
						$("#buttonToolbar" + currentButtonGroupIndex).hide("slow", function() {
							currentButtonGroupIndex++;
							$('#buttonPanelIndex').val(currentButtonGroupIndex);							
							$("#buttonToolbar" + currentButtonGroupIndex).show("slow");	
						});
						
					}
				});
				
				$('.attribute').popover();
				
				$('.attribute').each(function() {
					var button = $(this);
				    $(this).click(function() {					    	
				    	
				    	if (jcrop_api == null) {
				    		initializeJCrop();
				    	}
				    	jcrop_api.enable();
				    	if (button.is('.active')) {
				    		var buttonStatus = "inactive"; 					    	 
				    	} else {
				    		var buttonStatus = "active";
				    		
				    		if (button.attr('tidepool-highlightable') == "true") {
				    			currentAttribute = button.attr("id");
				    			$('#highlightAlert').show();
				    		}
				    	}
				    	
				    	$.post(contextPath + "/json/saveattribute.ajax", { explicitId:$("#explicitId").val(), attributeId:button.attr("id"), attributeValue:buttonStatus}, function(attribute) {	
				    		if (button.is('.active')) {
					    		button.removeClass('active');
					    		$('#highlightAlert').hide();
					    	} else {
					    		button.addClass('active') 					    	
					    	}	 														 
						 });
				    });
				}); 
			});
		})(jQuery);		
	</script>


  </body>

</html>