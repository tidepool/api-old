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
    <title>Tidepool Assessment</title>   
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/bootstrap/css/tidepool.css" />" rel="stylesheet">    
    <link href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" rel="stylesheet">
    
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
          <a class="brand" href="<c:url value="/" />">Tidepool Assessment</a>
          <div class="nav-collapse">
            <ul class="nav">                            
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
            </ul>
            
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
		
		<input type="hidden" name="userId" id="userId" value="${account.userId}">
		
		<div id="assessThanks"  class="offset1" style="display:none"><h3>Thanks for taking the assessment! </h3></div>
		
		<div class="offset1" id="assessTable">
			<p>Chose the photo you prefer:</p>	
			<table class="assess-table" style="text-align:center" >
				<tr><td><a href="#match" data-role="button" id="testImage0Link"><img id="testImage0" style="width: 70%; height: 70%"></a></td><td> <a href="#match" data-role="button" id="testImage1Link"><img id="testImage1" style="width: 70%; height: 70%"></a></td></tr>
			</table>	   
        </div>
		
			

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:url value="/resources/bootstrap/js/jquery.js" />"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
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
		var testImages = [];
		var currentImage0 = 0;
		var currentImage1 = 1;
		var servicesAPI = "http://localhost:8080";
		var contentURL = "https://s3.amazonaws.com/";
		
		(function ($) {
			$(document).ready(function () {				
				
				function showDialog(imageNumber) {
					
					var imageId = (imageNumber == 0) ? currentImage0 : currentImage1;
					$('#dialogPhoto').attr('src', contentURL +  testImages[imageId].bucket_name + "/" + testImages[imageId].folder_name + "/" + testImages[imageId].picture_id);
					
					$( "#dialog-message" ).dialog({
			            modal: true,
			            width:700,
			            zIndex:10000,
			            buttons: {
			                Ok: function() {
			                    
			                	if (imageNumber == 0) 
			                		updateImage0();
			                	else
			                		updateImage1();
			                	
			                	$( this ).dialog( "close" );
			                    			              			                    
			                },
			                No: function() {
			                	
			                    $( this ).dialog( "close" );
			                    
			                    if (imageNumber == 0) 
			                		var image = currentImage0;
			                	else
			                		var image = currentImage1;
			                    
			                    $.post(servicesAPI + "/tidepoolAPI/json/assessmentevent.ajax", 
			    			    		{accountId:$('#userId').val(), explicitId:testImages[image].id, type:"cancel"}, 
			    			    		function(items) {});			                 			               
			                }
			            }
			        });										
				}
				
				function updateImage0() {
					$.post(servicesAPI + "/tidepoolAPI/json/assessmentevent.ajax", 
    			    		{accountId:$('#userId').val(), explicitId:testImages[currentImage0].id, type:"selected"}, 
    			    		function(items) {
    			    			
    			    		});
    				
    							    				
    				currentImage0 = currentImage1 + 1;
    				currentImage1 = currentImage0 + 1;
    				
    				
    				if (currentImage0 >= testImages.length  || currentImage1 >= testImages.length ) {
    					$('#assessTable').hide();
    					$('#assessThanks').show();
    					$( "#dialog-message" ).dialog('close');
    				}
    				updateImages();
				}
				
				function updateImage1() {

    				$.post(servicesAPI + "/tidepoolAPI/json/assessmentevent.ajax", 
    			    		{accountId:$('#userId').val(), explicitId:testImages[currentImage1].id, type:"selected"}, 
    			    		function(items) {
    			    			
    			    		});			    				
    							    				
    				
    				currentImage0 = currentImage1 + 1;
    				currentImage1 = currentImage0 + 1;
    							    				
    				if (currentImage0 >= testImages.length  || currentImage1 >= testImages.length ) {
    					$('#assessTable').hide();
    					$('#assessThanks').show();
    					$( "#dialog-message" ).dialog('close');
    				}
    				updateImages();
				}
				
				function updateImages() {			    				
    				$('#testImage0').attr('src', contentURL +  testImages[currentImage0].bucket_name + "/" + testImages[currentImage0].folder_name + "/" + testImages[currentImage0].picture_id);
	    			$('#testImage1').attr('src', contentURL +  testImages[currentImage1].bucket_name + "/" + testImages[currentImage1].folder_name + "/" + testImages[currentImage1].picture_id);
    			}
				
				$.post(servicesAPI + "/tidepoolAPI/json/assessment.ajax", 
			    		{sessionId:'1'}, 
			    		function(items) {		    						    			
			    			    			
			    			var i = 0;
			    			$.each(items, function(index, value) {
			    				testImages[i] = value;
			    				i++;
			    			});
			    			
			    			updateImages();
			    			
			    			$('#testImage0Link').click(function() {
			    				
			    				$.post(servicesAPI + "/tidepoolAPI/json/assessmentevent.ajax", 
			    			    		{accountId:$('#userId').val(), explicitId:testImages[currentImage0].id, type:"viewed"}, 
			    			    		function(items) {});
			    				
			    				showDialog(0);			    				
			    				return false;
			    				
			    			});
			    			
			    			$('#testImage1Link').click(function() {
			    				
			    				$.post(servicesAPI + "/tidepoolAPI/json/assessmentevent.ajax", 
			    			    		{accountId:$('#userId').val(), explicitId:testImages[currentImage1].id, type:"viewed"}, 
			    			    		function(items) {});
			    				
			    				showDialog(1);			    				
			    				return false;
			    			});			    						    			
			    		}).error(function(jqXHR, textStatus, errorThrown) { 
			    				alert("error: " + jqXHR + " " + textStatus + " " + errorThrown); 
			    		});
												
				
			});
		})(jQuery);		
	</script>

	<div style="display:none; width:750px" id="dialog-message" title="Choose this photo?" class="assessPopup">
	    <p>
	       <img id="dialogPhoto" height="100%" width="100%">
	    </p>
	</div>

	
  </body>

</html>