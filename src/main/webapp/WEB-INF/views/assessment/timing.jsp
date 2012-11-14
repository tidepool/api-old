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
    <title>Tidepool Assess</title>   
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
            
      #instructions {
    	left: 400px;
    	position: absolute;
    	top: 270px;
    	z-index: 1000;
    	border:3px solid #000;
    	padding:40px;
    		
	 }
      
      #next {
    	left: 700px;
    	position: absolute;
    	top: 300px;
    	z-index: 1000;
    	display:none;
    	font-size:18px;	
	 }	
      
    </style>
   

  </head>

  <body>
    <input type="hidden" name="userId" id="userId" value="${account.userId}">
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="<c:url value="/" />">Tidepool Assess</a>          
        </div>
      </div>
    </div>

    <div class="container">
		<button id="next">Next</button>
		<div id="container"></div>		
		
		<div id="instructions">
			<div>
				<p>Follow the instructions that will appear on the button below:</p> 
				
				<div>
				  <button id="start">Ok</button>
				  <button id="test0" style="display:none">Click the red circle when it appears. Click here when ready.</button>
				  <button id="test0Next" style="display:none">Again, click the red circle when it appears. Click here when ready.</button>
				  <button id="test1" style="display:none">Click the red circle when it appears AFTER a yellow circle. Click here when ready.</button>
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
	<script src="<c:url value="/resources/js/kinetic-v4.0.5.js"/>"></script>
	
	<script>
		var servicesAPI = "${pageContext.request.contextPath}";
		var contentURL = "${cdn_url}";
		var circleArray = [];
		var circleArrayCounter = [];
		var test0Limit = 4;
		var startTime;
		var selectedCircle;
		
		(function ($) {
			$(document).ready(function () {	
				
				var movedMap = {};
				var dragArray = [];
				var stage = new Kinetic.Stage({
		            container: "container",
		            width: 970,
		            height: 700
		          });
		          var layer = new Kinetic.Layer();	
				
		          
		      $("#next").click(function() {
		    	  $.post(servicesAPI + "/json/assessmentevent.ajax", 
  			    		{accountId:$('#userId').val(), explicitId:'next' , type:"next"}, 
  			    		function(items) {});
		    	 window.location="<c:url value="/drag1"/>";  
		      });  
		     
		      $("#start").click(function() {		    	    
		    		
			  		 $.post(servicesAPI + "/json/assessmentevent.ajax", 
	    			    		{accountId:$('#userId').val(), explicitId:'instruction' , type:"click"}, 
	    			    		function(items) {	    			    			
	    			    			$.post(servicesAPI + "/json/assessmentevent.ajax", 
	    		    			    		{accountId:$('#userId').val(), explicitId:'self' , type:"click", x0:200, y0:100}, function(items) {	    		    			    			    		    			           	
	    		    			    			$("#start").hide();
	    		    			    			$("#test0").show();	    		    			    				    		    			    			
	    		    			    		});
	    			    		});	
			   }); 
		      
		      function showTest0() {
		      		function f() {
  						startTime = new Date().getTime();
  						selectedCircle = Math.floor((Math.random()*3));
  						circleArray[selectedCircle].setFill('red');
  						layer.draw();
  						circleArrayCounter.push(1);
  					}
  					setTimeout(f, 4000 * Math.random());	
		      }
		      
		      function showTest1() {
		    	    var redArray = [Math.floor((Math.random() * 10))];		      				    	   
		    	    function f() {
						startTime = new Date().getTime();
						var newCircle = Math.floor((Math.random() * 3));
						circleArray[newCircle].setFill('yellow');
						layer.draw();						
					}		    	    
					setTimeout(f, 2000 * Math.random());	
		      }
		      
		      
		      $("#test0").click(function() {		    	    
		    		
			  		 $.post(servicesAPI + "/json/assessmentevent.ajax", 
	    			    		{accountId:$('#userId').val(), explicitId:'instruction' , type:"click"}, 
	    			    		function(items) {	    			    			
	    			    			$.post(servicesAPI + "/json/assessmentevent.ajax", 
	    		    			    		{accountId:$('#userId').val(), explicitId:'self' , type:"click", x0:200, y0:100}, function(items) {	    		    			           
	    		    			    			showTest0();	    		    			    				    		    			    			
	    		    			    		});
	    			    		});	
			   }); 
		      
		      $("#test0Next").click(function() {		    	    
		    		
			  		 $.post(servicesAPI + "/json/assessmentevent.ajax", 
	    			    		{accountId:$('#userId').val(), explicitId:'instruction' , type:"click"}, 
	    			    		function(items) {	    			    			
	    			    			$.post(servicesAPI + "/json/assessmentevent.ajax", 
	    		    			    		{accountId:$('#userId').val(), explicitId:'self' , type:"click", x0:200, y0:100}, function(items) {	    		    			           
	    		    			    			showTest0();	    		    			    				    		    			    			
	    		    			    		});
	    			    		});	
			   }); 
		      
		      $("#test1").click(function() {		    	    
		    		
			  		 $.post(servicesAPI + "/json/assessmentevent.ajax", 
	    			    		{accountId:$('#userId').val(), explicitId:'instruction' , type:"click"}, 
	    			    		function(items) {	    			    			
	    			    			$.post(servicesAPI + "/json/assessmentevent.ajax", 
	    		    			    		{accountId:$('#userId').val(), explicitId:'self' , type:"click", x0:200, y0:100}, function(items) {	    		    			           
	    		    			    			showTest1();	    		    			    				    		    			    			
	    		    			    		});
	    			    		});	
			   }); 
		      
		      
		      function nextCommand() {
		      	if (circleArrayCounter.length < test0Limit) {
		      		
		      		if (circleArrayCounter.length > 0) {
		      			$('#test0Next').show();
		      			$('#test0').hide();
		      		}
		      		
		      		
		      	} else {
		      		$('#test0Next').hide();
		      		$('#test1').show();
		      	}	  
		      };
			  			  
		      function drawImages() {		            
		    	  layer.add(buildSelfCircle("Self", 100, 10)); 		    	  		         
		          stage.add(layer);
		        }
		      
		      
		        function logArrayEvent(name) {
		        	
		        	var coordinateString = "";
		        	for (var i in dragArray) {
		        		coordinateString += dragArray[i][0] + "-" + dragArray[i][1] + ","; 
		        	}
		        	
		        	dragArray.length = 0;
		        	
		        	$.post(servicesAPI + "/json/assessmentevent.ajax", 
    			   		{accountId:$('#userId').val(), explicitId:name , type:"drag", coordinates:coordinateString}, 
    			    		function(items) {});
		        }
		       
		        function logCircleEvent(circle) {
		        	circle.setFill('white');
		        	layer.draw();
		        	if (circle.getName() == circleArray[selectedCircle].getName()) {
		        		console.log("Logging: " + circle.getName());
		        		$.post(servicesAPI + "/json/assessmentevent.ajax", 
		    			   		{accountId:$('#userId').val(), explicitId:name , type:"timing-click"}, 
		    			    		function(items) {
		    			   				nextCommand();
		    			   		});
		        	} else {
		        		$.post(servicesAPI + "/json/assessmentevent.ajax", 
		    			   		{accountId:$('#userId').val(), explicitId:name , type:"timing-wrong-click"}, 
		    			    		function(items) {
		    			   			    nextCommand();	
		    			   		});
		        	}
		        }
		        
				function buildSelfCircle(name, x, y) {
		        	
		        	var group = new Kinetic.Group({
			              x: x,
			              y: y,
			              rotationDeg: 0,
			              draggable:true
			            });
			          
		        	var box = new Kinetic.Rect({
		                x: 0,
		                y: 0,
		                width: 200,
		                height:565,
		                fill: 'grey',
		                stroke: 'black',
		                strokeWidth: 4
		              });

		        	  
			          var circle0 = new Kinetic.Circle({
			              x: 100,
			              y: 100,
			              width: 150,
			              height: 150,
			              name: 'circle0',
			              fill: 'white',
			              stroke: "black",
			              strokeWidth: 2
			            });
			      	  circleArray.push(circle0);	
			      	  circle0.on("click", function(){
			      		logCircleEvent(this);
			      	  });
			      	  
			          var circle1 = new Kinetic.Circle({
			              x: 100,
			              y: 275,
			              width: 150,
			              height: 150,
			              name: 'circle1',
			              fill: 'white',
			              stroke: "black",
			              strokeWidth: 2
			            });
			          circleArray.push(circle1);	
			          circle1.on("click", function(){
				      	logCircleEvent(this);
				      });
			          
			          var circle2 = new Kinetic.Circle({
			              x: 100,
			              y: 450,
			              width: 150,
			              height: 150,
			              name: 'circle2',
			              fill: 'white',
			              stroke: "black",
			              strokeWidth: 2
			            });
			          circleArray.push(circle2);
			          circle2.on("click", function(){
					      	logCircleEvent(this);
					  }); 
			          
			          group.add(box);
			          group.add(circle0);
			          group.add(circle1);
			          group.add(circle2);
		        	  return group;		        	
		        }
		        		       
		        drawImages();
		       
			});
		})(jQuery);		
	</script>

  </body>

</html>