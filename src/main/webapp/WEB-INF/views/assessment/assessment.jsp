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
    
    <c:if test="${ not empty admin }">
     	 <link href="<c:url value="/resources/bootstrap/css/bullet.css" />" rel="stylesheet">
    </c:if>
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      
	.axis path,
	.axis line {
	  fill: none;
	  stroke: #000;
	  shape-rendering: crispEdges;
	}
	
	.bar {
	  fill: steelblue;
	  color: white;
	}
	
	.x.axis path {
	  display: none;
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
        </div>
      </div>
    </div>

    <div class="container">
		
			<input type="hidden" name="userId" id="userId" value="${account.userId}">
			
			<div id="assessThanks"  class="offset1" style="display:none"><h3>Thanks for taking the assessment! </h3></div>
			
			<div class="offset1 span-9" id="assessTable">
				<h3>Choose the photo you prefer:</h3>	
				<table class="assess-table" style="text-align:center" >
					<tr><td><a href="#match" data-role="button" id="testImage0Link"><img id="testImage0" class="testImage"></a></td><td> <a href="#match" data-role="button" id="testImage1Link"><img class="testImage" id="testImage1"></a></td></tr>
				</table>	   
	        </div>
	        
	        
	        <c:if test="${ not empty admin }">
	        	<div class="offset1 span-9">
					<table class="table">					
						<tr><th>Openness</th><th>Conscientiousness</th><th>Extraversion</th><th>Agreeableness</th><th>Neuroticism</th><th>Calculation Time(ms)</th></tr>
						<tr><td id="oVal">0</td><td id="cVal">0</td><td id="eVal">0</td><td id="aVal">0</td><td id="nVal">0</td><td id="timing">0</td></tr>
					</table>
	        	</div>
	        	
	        	<div class="offset1 span-9" id="chart"></div>
			
			</c:if>
			
			
			
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
	
	<c:if test="${ not empty admin }">
	    <script src="<c:url value="/resources/bootstrap/js/d3.v2.js"/>"></script>
		  
	</c:if>
	
	<script>
		var testImages = [];
		var currentImage0 = 0;
		var currentImage1 = 1;
		var servicesAPI = "${pageContext.request.contextPath}";
		var contentURL = "${cdn_url}";
		var resizeImages = false;
				
		(function ($) {
			$(document).ready(function () {				
								

		<c:if test="${ not empty admin }">		
							
			var chartLoaded = false;
			function updateOcean() {				  
				  d3.json(servicesAPI + "/json/bigfivebullet.ajax", function(newData) {
											  
						$('#oVal').html(newData[0].ranges[0].toFixed(2));
						$('#cVal').html(newData[1].ranges[0].toFixed(2));
						$('#eVal').html(newData[2].ranges[0].toFixed(2));
						$('#aVal').html(newData[3].ranges[0].toFixed(2));
						$('#nVal').html(newData[4].ranges[0].toFixed(2));
						$('#timing').html(newData[0].timing);
					d3.selectAll("#chart").selectAll("svg").remove();	
						var margin = {top: 20, right: 20, bottom: 30, left: 40},
					    width = 960 - margin.left - margin.right,
					    height = 500 - margin.top - margin.bottom;

					var x = d3.scale.ordinal()
					    .rangeRoundBands([0, width], .1);

					var y = d3.scale.linear()
					    .range([height, 0]);

					var xAxis = d3.svg.axis()
					    .scale(x)
					    .orient("bottom");

					var yAxis = d3.svg.axis()
					    .scale(y)
					    .orient("left")
					    

					var svg = d3.select("#chart").append("svg")
					    .attr("width", width + margin.left + margin.right)
					    .attr("height", height + margin.top + margin.bottom)
					    .append("g")
					    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");	
						
																		
						var data = [{letter:newData[0].title, frequency:newData[0].ranges[0].toFixed(2)}, 
						            {letter:newData[1].title, frequency:newData[1].ranges[0].toFixed(2)},
						            {letter:newData[2].title, frequency:newData[2].ranges[0].toFixed(2)}, 
						            {letter:newData[3].title, frequency:newData[3].ranges[0].toFixed(2)},
						            {letter:newData[4].title, frequency:newData[4].ranges[0].toFixed(2)}];
																		
						  x.domain(data.map(function(d) { return d.letter; }));
						  y.domain([0, d3.max(data, function(d) { return d.frequency; })]);
				
					    svg.append("g")
							.attr("class", "x axis")
							.attr("transform", "translate(0," + height + ")")
							.call(xAxis);

						svg.append("g")
							.attr("class", "y axis")
							.call(yAxis)
							.append("text")
							.attr("transform", "rotate(-90)")
							.attr("y", 6)
							.attr("dy", ".71em")
							.style("text-anchor", "end")
							.text("Score");
					
												
						svg.selectAll(".bar")
							.data(data)
							.enter().append("rect")
							.attr("class", "bar")
							.attr("x", function(d) { return x(d.letter); })
							.attr("width", x.rangeBand())
							.attr("y", function(d) { return y(d.frequency); })
							.attr("height", function(d) { return height - y(d.frequency); })							
							.text(String);
												
				  });
			  };				
			  </c:if>
				
				function showDialog(imageNumber) {
					
					var imageId = (imageNumber == 0) ? currentImage0 : currentImage1;
					$('#dialogPhoto').attr('src', contentURL + testImages[imageId].folder_name + "/" + testImages[imageId].picture_id);
					
					$( "#dialog-message" ).dialog({
			            modal: true,
			            width:700,
			            zIndex:10000,
			            buttons: {
			                Yes: function() {
			                    
			                	if (imageNumber == 0) 
			                		updateImage0();
			                	else
			                		updateImage1();
			                	
			                	$( this ).dialog( "close" );
			                	
			                	<c:if test="${ not empty admin }">
			                		updateOcean();
			    				</c:if>
			                	
			                    			              			                    
			                },
			                No: function() {
			                	
			                    $( this ).dialog( "close" );
			                    
			                    if (imageNumber == 0) 
			                		var image = currentImage0;
			                	else
			                		var image = currentImage1;
			                    
			                    $.post(servicesAPI + "/json/assessmentevent.ajax", 
			    			    		{accountId:$('#userId').val(), explicitId:testImages[image].id, type:"cancel"}, 
			    			    		function(items) {});			                 			               
			                }
			            }
			        });										
				}
				
				function updateImage0() {
					$.post(servicesAPI + "/json/assessmentevent.ajax", 
    			    		{accountId:$('#userId').val(), explicitId:testImages[currentImage0].id, type:"selected"}, 
    			    		function(items) {
    			    			
    			    		});
    				
    							    				
    				currentImage0 = currentImage1 + 1;
    				currentImage1 = currentImage0 + 1;
    				
    				
    				if (currentImage0 >= testImages.length  || currentImage1 >= testImages.length ) {    									
    					window.location="<c:url value="/assessmentFeedback"/>";
    				}
    				updateImages();
				}
				
				function updateImage1() {

    				$.post(servicesAPI + "/json/assessmentevent.ajax", 
    			    		{accountId:$('#userId').val(), explicitId:testImages[currentImage1].id, type:"selected"}, 
    			    		function(items) {
    			    			
    			    		});			    				
    							    				
    				
    				currentImage0 = currentImage1 + 1;
    				currentImage1 = currentImage0 + 1;
    							    				
    				if (currentImage0 >= testImages.length  || currentImage1 >= testImages.length ) {
    					/* $('#assessTable').hide();
    					$('#assessThanks').show();
    					$( "#dialog-message" ).dialog('close'); */
    					window.location="<c:url value="/assessmentFeedback"/>";
    				}
    				updateImages();
				}
				
				function updateImages() {			    				
    				
					$('#testImage0').attr('src', contentURL + testImages[currentImage0].folder_name + "/" + testImages[currentImage0].picture_id);    				    									
					$('#testImage1').attr('src', contentURL + testImages[currentImage1].folder_name + "/" + testImages[currentImage1].picture_id);    								
    				
    			}
				
				$.post(servicesAPI + "/json/assessment.ajax", 
			    		{sessionId:'1'}, 
			    		function(items) {		    						    			
			    			    			
			    			var i = 0;
			    			$.each(items, function(index, value) {
			    				testImages[i] = value;
			    				
			    				//Preload images.
			    				var preloadImage = new Image();
			    				preloadImage.src = contentURL + value.folder_name + "/" + value.picture_id;
			    				
			    				i++;
			    			});
			    			
			    			updateImages();
			    			
			    			$('#testImage0Link').click(function() {
			    				
			    				$.post(servicesAPI + "/json/assessmentevent.ajax", 
			    			    		{accountId:$('#userId').val(), explicitId:testImages[currentImage0].id, type:"viewed"}, 
			    			    		function(items) {
			    			    			
			    			    		});
			    				
			    				showDialog(0);			    				
			    				return false;
			    				
			    			});
			    			
			    			$('#testImage1Link').click(function() {
			    				
			    				$.post(servicesAPI + "/json/assessmentevent.ajax", 
			    			    		{accountId:$('#userId').val(), explicitId:testImages[currentImage1].id, type:"viewed"}, 
			    			    		function(items) {
			    			    			
			    			    		});
			    				
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