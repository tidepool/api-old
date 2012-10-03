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
		<input type="hidden" id="trainingId" value="${ trainingItem.trainingId }">
		<div class="hero-unit">			  			
  			<div class="row">	  				  			
	  		
	     		<div class="span-4">
	  				<p>Edit Attributes</p>
	  				
	  				<p>
		  				<div style="display:none" id="highlightAlert" class="alert alert-success">
	  						Please highlight the associated attribute by dragging the mouse on the picture.		
						</div>
	  				</p>
	  				
	  				<p>
	  					<img class="imageViewer" id="jcrop_target" src="${cdn_url}/${trainingItem.bucketName}/${trainingItem.folderName}/${trainingItem.elementFolderName}/${trainingItem.pictureId}">
	  				</p>
	  				
	  				
	  				<p>Current Highlights</p>			
	  				
  					<p>
						<div id="canvasContainer"></div>	
  					</p>
     				
	  				
	  			</div>
	     		
	     		<div class="span-3">
		     		<p>
				  		<div>		  			
				  			<div>
				  				<c:forEach var="attribute" items="${allAttributes}" varStatus="rowCounter">
									
									<c:if test="${rowCounter.index == 0 or rowCounter.index % 3 == 0}">     	
     									<c:if test="${rowCounter.index != 0}">
     										</div>
     									</c:if>
      									
      									<div class="btn-group">
      								
      								</c:if>
																		
										<button class='attribute btn <c:if test="${ attribute.active }">active</c:if>' tidepool-highlightable="${ attribute.highlightable }" data-placement="bottom" data-trigger="hover" data-content="${ attribute.element_description }" id="${ attribute.element }">${ attribute.element_name}</button>							
									
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
	 <script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
	<script>
	
		var contextPath = "${pageContext.request.contextPath}";
	    var jcrop_api;
	    var currentAttribute;
	    function showCoords(c) {
	    	
	    	$.post(contextPath + "/admin/json/savetraining.ajax", { trainingId:$("#trainingId").val(), 
	    														attributeId:currentAttribute,
	    														x0:c.x, 
		    													y0:c.y, 
		    													x1:c.x2,
		    													y1:c.y2,	
		    													height:c.height, 
	    														width:c.weight }, function(attribute) {	
	    	
	    															$('#highlightAlert').hide();		
	    															jcrop_api.release();													
	    	});			
		};
	 	
		
		var SCALE_VALUE = 0.50;
		
		var boxen = [
						<c:forEach var="highlight" items="${trainingItem.codedItem.highlightMap}">
							[
								[${highlight.value.x0 } * SCALE_VALUE, ${highlight.value.y0 } * SCALE_VALUE],
								[${highlight.value.x0 } * SCALE_VALUE, ${highlight.value.y1 } * SCALE_VALUE],
								[${highlight.value.x1 } * SCALE_VALUE, ${highlight.value.y1 } * SCALE_VALUE],
								[${highlight.value.x1 } * SCALE_VALUE, ${highlight.value.y0 } * SCALE_VALUE],
								[${highlight.value.x0 } * SCALE_VALUE, ${highlight.value.y0 } * SCALE_VALUE]						
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
				
				$('#jcrop_target').Jcrop({
					//onChange: showCoords,
					onSelect: showCoords
				}, function(){
					  jcrop_api = this;
				});
				
				var imgHeight = 0;
				var imgWidth = 0;
				
				//1.Get the image via JS
				var img = new Image();
				img.onload = function() {  					
  					imgHeight = this.height * SCALE_VALUE;
  					imgWidth = this.width * SCALE_VALUE;
									
  					//2.Add the canvas to the DOM
  					$('<canvas>').attr({
  					    id: "imageCanvas",
  					    width: imgWidth + 'px',
  	  					height: imgHeight + 'px'
  					}).appendTo('#canvasContainer');
  					
  					//3.Add the jcanvas bullshit				
  					$("#imageCanvas").drawImage({
  						  source: "${cdn_url}/${trainingItem.bucketName}/${trainingItem.folderName}/${trainingItem.elementFolderName}/${trainingItem.pictureId}",					  
  						  x: 0, y: 0,
  						  width: imgWidth,
  						  height: imgHeight,
  						  fromCenter: false,  						  					
  					});
  					
  					drawBox();
				}
				img.src = '${cdn_url}/${trainingItem.bucketName}/${trainingItem.folderName}/${trainingItem.elementFolderName}/${trainingItem.pictureId}';
				
				
				$("#nextButton").click(function() {	
					
					if ($('#textInput').val() != '') {
						$.post(contextPath + "/admin/json/savetraining.ajax", { trainingId:$("#trainingId").val()}, function(attribute) {	
							$('#textInput').hide();		    			 														 
					 	});
					}
					var currentButtonGroupCount = $('#buttonPanelSize').val();
					var currentButtonGroupIndex = $('#buttonPanelIndex').val();
					
					if (currentButtonGroupIndex == (currentButtonGroupCount - 1)) {
						$.post(contextPath + "/admin/json/savetraining.ajax", { trainingId:$("#trainingId").val()}, function(attribute) {	
							window.location.href = contextPath + "/test";		    			 														 
						 });						
					} else {
						//$("#buttonToolbar" + currentButtonGroupIndex).hide("slide", { direction: "left" }, 1000);
						$("#buttonToolbar" + currentButtonGroupIndex).hide("slow");
						currentButtonGroupIndex++;
						$('#buttonPanelIndex').val(currentButtonGroupIndex);
						//$("#buttonToolbar" + currentButtonGroupIndex).show();
						$("#buttonToolbar" + currentButtonGroupIndex).show("slow");
					}
				});
				
				$('.attribute').popover();
				
				$('.attribute').each(function() {
					var button = $(this);
				    $(this).click(function() {					    	
				    					    	
				    	if (button.is('.active')) {
				    		var buttonStatus = "0"; 					    	 
				    	} else {
				    		var buttonStatus = "1";
				    		
				    		if (button.attr('tidepool-highlightable') == "true") {
				    			currentAttribute = button.attr("id");
				    			$('#highlightAlert').show();
				    		}
				    	}
				    	
				    	$.post(contextPath + "/admin/json/savetraining.ajax", { trainingId:$("#trainingId").val(), attributeId:button.attr("id"), attributeValue:buttonStatus}, function(attribute) {	
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