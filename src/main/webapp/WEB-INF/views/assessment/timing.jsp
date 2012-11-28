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
    	left: 210px;
    	position: absolute;
    	top: 100px;
    	z-index: 1000;
    	border:3px solid #000;
    	padding:40px;
    	max-width:475px;
    		
	 }
      
      #next {
    	left: 700px;
    	position: absolute;
    	top: 300px;
    	z-index: 1000;
    	display:none;
    	font-size:18px;	
	 }	
      
      .buttonPanel {
      	padding:20px;
      	text-align:center;
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
				
				<div class="buttonPanel" >
				  <button class="btn btn-success" id="start">Click to continue</button>
				  <button class="btn btn-success" id="test0" style="display:none">Click the red circle on the left as soon as it appears. Click here when you ready to START.</button>
				  <button class="btn btn-success" id="test0Next" style="display:none">Again, click the red circle on the left when it appears. Click here when ready to START.</button>
				  <button class="btn btn-success" id="test1" style="display:none">Click the red circle only when it appears AFTER a yellow circle. Do not click unless there is a red circle after a yellow circle.

Click here when ready to START

 </button>
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
		var test0Limit = 3;
		var test1Limit = 3;
		var test0Counter = 0;
		var test1Counter = 0;
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
				
		          
		     
		      $("#start").click(function() {		    	    
		    		
 			   	$.post(servicesAPI + "/json/assessmentevent.ajax", 
 		    	   {accountId:$('#userId').val(), explicitId:'self' , type:"start-timing", x0:200, y0:100}, function(items) {	    		    			    			    		    			           	
 		    			$("#start").hide();
 		    			$("#test0").show();	    		    			    				    		    			    			
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
		    	    var yellowLimit = Math.floor((Math.random() * 12));
		    	    yellowLimit = (yellowLimit == 0) ? 2 : yellowLimit;
		    	    var increment = 0;
		    	    var intervalId = 0;
		    	    function f() {
		    	    	if (increment == yellowLimit) {
		    	    		clearInterval(intervalId);
		    	    		circleArray[0].setFill('white');
			    	    	circleArray[1].setFill('white');
			    	    	circleArray[2].setFill('white');			    	    	
		    	    		startTime = new Date().getTime();
	  						selectedCircle = Math.floor((Math.random()*3));
	  						circleArray[selectedCircle].setFill('red');
	  						layer.draw();
	  						
	  						return;
	  						
		    	    	}
		    	    	circleArray[0].setFill('white');
		    	    	circleArray[1].setFill('white');
		    	    	circleArray[2].setFill('white');
						startTime = new Date().getTime();
						var newCircle = Math.floor((Math.random() * 3));
						
						if (increment %2 == 0) {
							circleArray[newCircle].setFill('yellow');
						} else {
							circleArray[newCircle].setFill('green');							
						}
						
						layer.draw();
						increment++;
					}		    	    
		    	    intervalId = setInterval(f, 1000 * Math.random());	
		      }
		      
		      
		      $("#test0").click(function() {		    	    
		    	  $("#test0").removeClass("btn-success");
			  			    			    			
	    			$.post(servicesAPI + "/json/assessmentevent.ajax", 
	    		   		{accountId:$('#userId').val(), explicitId:'self' , type:"test0-button-click"}, function(items) {	    		    			           
	    		    			showTest0();	    		    			    				    		    			    			
	    		    });
	    			    		
			   }); 
		      
		      $("#test0Next").click(function() {		    	    
		    	  $("#test0Next").removeClass("btn-success");
			  		 	    			    			
		    		$.post(servicesAPI + "/json/assessmentevent.ajax", 
		    		    {accountId:$('#userId').val(), explicitId:'self' , type:"test0-next-button-click" }, function(items) {	    		    			           
		    		    showTest0();	    		    			    				    		    			    			
		    		});
	    			    		
			   }); 
		      
		      $("#test1").click(function() {		    	    
		    	  $("#test1").removeClass("btn-success");
			  		 	    			    			
	    		  $.post(servicesAPI + "/json/assessmentevent.ajax", 
	    		  {accountId:$('#userId').val(), explicitId:'self' , type:"click", type:"test1-button-click"}, function(items) {	    		    			           
	    		   	showTest1();	    		    			    				    		    			    			
	    		   });
	    			    		
			   }); 
		      
		      
		      function nextCommand() {
		      	if (circleArrayCounter.length < test0Limit) {
		      		
		      		if (circleArrayCounter.length > 0) {
		      			$('#test0Next').show();
		      			$("#test0Next").addClass("btn-success");
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
		      
		      		        
		        function logCircleEvent(circle) {
		        	
		        	if (test0Counter == test0Limit) {
		        		if (circle.getName() == circleArray[selectedCircle].getName()) {
		        			test1Counter++;
		        			$.post(servicesAPI + "/json/assessmentevent.ajax", 
		    			   			{accountId:$('#userId').val(), explicitId:name , type:"timing-click-red-after-yellow", startTime:startTime, endTime:new Date().getTime()}, 
		    			    			function(items) {
		    			   				if (test1Counter == test1Limit) {	
		    			   				 	window.location="<c:url value="/image0"/>";
		    			   				} else {
		    			   					$("#test1").addClass("btn-success");
		    			   				}
		    			   		});
		        			circle.setFill('white');
				        	layer.draw();
		        		}		        		
		        		return;
		        	}
		        	
		        	
		        	circle.setFill('white');
		        	layer.draw();
		        	if (circle.getName() == circleArray[selectedCircle].getName()) {
		        		test0Counter++;
		        		
		        		$.post(servicesAPI + "/json/assessmentevent.ajax", 
		    			   		{accountId:$('#userId').val(), explicitId:name , type:"timing-click-red", startTime:startTime, endTime:new Date().getTime()}, 
		    			    		function(items) {
		    			   				nextCommand();
		    			   		});
		        	} else {
		        		$.post(servicesAPI + "/json/assessmentevent.ajax", 
		    			   		{accountId:$('#userId').val(), explicitId:name , type:"timing-wrong-click", startTime:startTime, endTime:new Date().getTime()}, 
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
			              draggable:false
			            });
			          
		        	var box = new Kinetic.Rect({
		                x: 0,
		                y: 0,
		                width: 100,
		                height:274,
		                fill: 'grey',
		                stroke: 'black',
		                strokeWidth: 4
		              });

		        	  
			          var circle0 = new Kinetic.Circle({
			              x: 50,
			              y: 50,
			              width: 75,
			              height: 75,
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
			              x: 50,
			              y: 135,
			              width: 75,
			              height: 75,
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
			              x: 50,
			              y: 225,
			              width: 75,
			              height: 75,
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