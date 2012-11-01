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
					<table width="200px">					
						<tr><th>Openness</th><th>Conscientiousness</th><th>Extraversion</th><th>Agreeableness</th><th>Neuroticism</th></tr>
						<tr><td id="oVal">0</td><td id="cVal">0</td><td id="eVal">0</td><td id="aVal">0</td><td id="nVal">0</td></tr>
					</table>
	        	</div>
	        	
	        	<!-- <div class="offset1 span-9" id="chart"></div> -->
			
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
		
		var width = 960,
	    height = 50,
	    margin = {top: 5, right: 40, bottom: 20, left: 120};
		var chart = bulletChart()
    	.width(width - margin.right - margin.left)
    	.height(height - margin.top - margin.bottom);
		
		
		// Chart design based on the recommendations of Stephen Few. Implementation
		// based on the work of Clint Ivy, Jamie Love, and Jason Davies.
		// http://projects.instantcognition.com/protovis/bulletchart/
		function bulletChart() {
		  var orient = "left", // TODO top & bottom
		      reverse = false,
		      duration = 0,
		      ranges = bulletRanges,
		      markers = bulletMarkers,
		      measures = bulletMeasures,
		      width = 380,
		      height = 30,
		      tickFormat = null;

		  // For each small multiple…
		  function bullet(g) {
		    g.each(function(d, i) {
		      var rangez = ranges.call(this, d, i).slice().sort(d3.descending),
		          markerz = markers.call(this, d, i).slice().sort(d3.descending),
		          measurez = measures.call(this, d, i).slice().sort(d3.descending),
		          g = d3.select(this);

		      // Compute the new x-scale.
		      var x1 = d3.scale.linear()
		          .domain([0, Math.max(rangez[0], markerz[0], measurez[0])])
		          .range(reverse ? [width, 0] : [0, width]);

		      // Retrieve the old x-scale, if this is an update.
		      var x0 = this.__chart__ || d3.scale.linear()
		          .domain([0, Infinity])
		          .range(x1.range());

		      // Stash the new scale.
		      this.__chart__ = x1;

		      // Derive width-scales from the x-scales.
		      var w0 = bulletWidth(x0),
		          w1 = bulletWidth(x1);

		      // Update the range rects.
		      var range = g.selectAll("rect.range")
		          .data(rangez);

		      range.enter().append("svg:rect")
		          .attr("class", function(d, i) { return "range s" + i; })
		          .attr("width", w0)
		          .attr("height", height)
		          .attr("x", reverse ? x0 : 0)
		        .transition()
		          .duration(duration)
		          .attr("width", w1)
		          .attr("x", reverse ? x1 : 0);

		      range.transition()
		          .duration(duration)
		          .attr("x", reverse ? x1 : 0)
		          .attr("width", w1)
		          .attr("height", height);

		      // Update the measure rects.
		      var measure = g.selectAll("rect.measure")
		          .data(measurez);

		      measure.enter().append("svg:rect")
		          .attr("class", function(d, i) { return "measure s" + i; })
		          .attr("width", w0)
		          .attr("height", height / 3)
		          .attr("x", reverse ? x0 : 0)
		          .attr("y", height / 3)
		        .transition()
		          .duration(duration)
		          .attr("width", w1)
		          .attr("x", reverse ? x1 : 0);

		      measure.transition()
		          .duration(duration)
		          .attr("width", w1)
		          .attr("height", height / 3)
		          .attr("x", reverse ? x1 : 0)
		          .attr("y", height / 3);

		      // Update the marker lines.
		      var marker = g.selectAll("line.marker")
		          .data(markerz);

		      marker.enter().append("svg:line")
		          .attr("class", "marker")
		          .attr("x1", x0)
		          .attr("x2", x0)
		          .attr("y1", height / 6)
		          .attr("y2", height * 5 / 6)
		        .transition()
		          .duration(duration)
		          .attr("x1", x1)
		          .attr("x2", x1);

		      marker.transition()
		          .duration(duration)
		          .attr("x1", x1)
		          .attr("x2", x1)
		          .attr("y1", height / 6)
		          .attr("y2", height * 5 / 6);

		      // Compute the tick format.
		      var format = tickFormat || x1.tickFormat(8);

		      // Update the tick groups.
		      var tick = g.selectAll("g.tick")
		          .data(x1.ticks(8), function(d) {
		            return this.textContent || format(d);
		          });

		      // Initialize the ticks with the old scale, x0.
		      var tickEnter = tick.enter().append("svg:g")
		          .attr("class", "tick")
		          .attr("transform", bulletTranslate(x0))
		          .style("opacity", 1e-6);

		      tickEnter.append("svg:line")
		          .attr("y1", height)
		          .attr("y2", height * 7 / 6);

		      tickEnter.append("svg:text")
		          .attr("text-anchor", "middle")
		          .attr("dy", "1em")
		          .attr("y", height * 7 / 6)
		          .text(format);

		      // Transition the entering ticks to the new scale, x1.
		      tickEnter.transition()
		          .duration(duration)
		          .attr("transform", bulletTranslate(x1))
		          .style("opacity", 1);

		      // Transition the updating ticks to the new scale, x1.
		      var tickUpdate = tick.transition()
		          .duration(duration)
		          .attr("transform", bulletTranslate(x1))
		          .style("opacity", 1);

		      tickUpdate.select("line")
		          .attr("y1", height)
		          .attr("y2", height * 7 / 6);

		      tickUpdate.select("text")
		          .attr("y", height * 7 / 6);

		      // Transition the exiting ticks to the new scale, x1.
		      tick.exit().transition()
		          .duration(duration)
		          .attr("transform", bulletTranslate(x1))
		          .style("opacity", 1e-6)
		          .remove();
		    });
		    d3.timer.flush();
		  }

		  // left, right, top, bottom
		  bullet.orient = function(x) {
		    if (!arguments.length) return orient;
		    orient = x;
		    reverse = orient == "right" || orient == "bottom";
		    return bullet;
		  };

		  // ranges (bad, satisfactory, good)
		  bullet.ranges = function(x) {
		    if (!arguments.length) return ranges;
		    ranges = x;
		    return bullet;
		  };

		  // markers (previous, goal)
		  bullet.markers = function(x) {
		    if (!arguments.length) return markers;
		    markers = x;
		    return bullet;
		  };

		  // measures (actual, forecast)
		  bullet.measures = function(x) {
		    if (!arguments.length) return measures;
		    measures = x;
		    return bullet;
		  };

		  bullet.width = function(x) {
		    if (!arguments.length) return width;
		    width = x;
		    return bullet;
		  };

		  bullet.height = function(x) {
		    if (!arguments.length) return height;
		    height = x;
		    return bullet;
		  };

		  bullet.tickFormat = function(x) {
		    if (!arguments.length) return tickFormat;
		    tickFormat = x;
		    return bullet;
		  };

		  bullet.duration = function(x) {
		    if (!arguments.length) return duration;
		    duration = x;
		    return bullet;
		  };

		  return bullet;
		};

		function bulletRanges(d) {
		  return d.ranges;
		}

		function bulletMarkers(d) {
		  return d.markers;
		}

		function bulletMeasures(d) {
		  return d.measures;
		}

		function bulletTranslate(x) {
		  return function(d) {
		    return "translate(" + x(d) + ",0)";
		  };
		}

		function bulletWidth(x) {
		  var x0 = x(0);
		  return function(d) {
		    return Math.abs(x(d) - x0);
		  };
		}
		
		(function ($) {
			$(document).ready(function () {				
								
				function buildChart() {
				
					d3.json("http://localhost:8080/tidepoolAPI/json/bigfivebullet.ajax", function(data) {
						vis = d3.select("#chart").selectAll("svg")
						.data(data)
						.enter().append("svg")
						.attr("class", "bullet")
						.attr("width", width)
						.attr("height", height)
						.append("g")
						.attr("transform", "translate(" + margin.left + "," + margin.top + ")")
						.call(chart);
			
						  var title = vis.append("g")
						      .attr("text-anchor", "end")
						      .attr("transform", "translate(-6," + (height - margin.top - margin.bottom) / 2 + ")");
					
						  title.append("text")
						      .attr("class", "title")
						      .text(function(d) { return d.title; });
					
						  title.append("text")
						      .attr("class", "subtitle")
						      .attr("dy", "1em")
						      .text(function(d) { return d.subtitle; });
					
						  chart.duration(1000);
						  var updateChart = function(newData) {
							  vis.datum(data).call(chart);
						  }
						  window.transition = function() {
							  
							  d3.json("http://localhost:8080/tidepoolAPI/json/bigfivebullet.ajax", function(newData) {
									//updateChart(newData);
									$('#oVal').html(newData[0].ranges[0].toFixed(2));
									$('#cVal').html(newData[1].ranges[0].toFixed(2));
									$('#eVal').html(newData[2].ranges[0].toFixed(2));
									$('#aVal').html(newData[3].ranges[0].toFixed(2));
									$('#nVal').html(newData[4].ranges[0].toFixed(2));
							  });
						  };
						});
				}
			
				buildChart();
			
				
			function randomize(d) {
			  if (!d.randomizer) d.randomizer = randomizer(d);
			  d.ranges = d.ranges.map(d.randomizer);
			  d.markers = d.markers.map(d.randomizer);
			  d.measures = d.measures.map(d.randomizer);
			  return d;
			}

			function randomizer(d) {
			  var k = d3.max(d.ranges) * .2;
			  return function(d) {
			    return Math.max(0, d + k * (Math.random() - .5));
			  };
			}

			
				
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
			                		transition();
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