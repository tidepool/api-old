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
    	top: 70px;
    	z-index: 1000;
    	border:3px solid #000;
    	padding:10px;
    		
	 }
	 
	 #instructions0 {
    	left: 400px;
    	position: absolute;
    	top: 70px;
    	z-index: 1000;
    	border:3px solid #000;
    	padding:10px;
    		
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
		
		<div id="instructions0">
			<div>
			    <p>Here are a number of psychological traits that may or may not describe you. </p>
			    <p>Simply collapse or expand each circle to indicate how well the trait describes you.</p>
				<p>You must change the size of all circles.</p> 				
				<div>
				  <button id="start0">Start</button>
				</div>
			</div>
		</div>
		
		<div id="instructions" style="display:none">
			<div>
				<p>Now place each of the five circles in position to the new circle representing your self. </p> 
				<p>Place each trait anywhere on the screen so that it best represents how important that trait is to your self.</p>
				<div>
				  <button id="start">Start</button>
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
		
		(function ($) {
			$(document).ready(function () {	
				
				var movedMap = {};
				var sizedMap = {};
				var dragArray = [];
				var circleArray = [];
				var selfGroup;
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
		    	 window.location="<c:url value="/image1"/>";  
		      });  
		     
		      $("#start0").click(function() {
			  		$("#instructions0").hide();
			  		 $.post(servicesAPI + "/json/assessmentevent.ajax", 
	    			    		{accountId:$('#userId').val(), explicitId:'instruction0' , type:"click"}, 
	    			    		function(items) {	    			    			
	    			    			$.post(servicesAPI + "/json/assessmentevent.ajax", 
	    		    			    		{accountId:$('#userId').val(), explicitId:'self' , type:"click", x0:200, y0:100}, function(items) {	    		    			    		
	    		    			    });
	    			    		});	
			   }); 
		     
		      
		      $("#start").click(function() {
			  		$("#instructions").hide();
			  		 $.post(servicesAPI + "/json/assessmentevent.ajax", 
	    			    		{accountId:$('#userId').val(), explicitId:'instruction' , type:"click"}, 
	    			    		function(items) {	    			    			
	    			    			$.post(servicesAPI + "/json/assessmentevent.ajax", 
	    		    			    		{accountId:$('#userId').val(), explicitId:'self' , type:"click", x0:200, y0:100}, function(items) {	    		    			    		
	    		    			    });
	    			    		});	
			   }); 
		      
			  			  
		      function drawImages() {		            
		    	  selfGroup = buildSelfCircle("Self", 100, 100);
		    	  layer.add(selfGroup); 
		    	  layer.add(build5Circle("Self-disciplined/ \nPersistent", "self-disciplined_persistent", 100, 350));
		          layer.add(build5Circle("Anxious/ \nDramatic", "anxious_dramatic", 275, 350));
		          layer.add(build5Circle("Cooperative/ \nFriendly", "cooperative_friendly", 455, 350));
		          layer.add(build5Circle("Sociable/ \nAdventurous", "sociable_adventurous", 625, 350));
		          layer.add(build5Circle("Curious/ \nCultured", "curious_cultured", 795, 350));		          
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
		       
		        function build5Circle(name, id,  x, y) {
		        	
		        	var group = new Kinetic.Group({
			              x: x,
			              y: y,
			              name:id,
			              rotationDeg: 0,
			              draggable:true
			            });
			          
		        	
		        	var plusText = new Kinetic.Text({
			        	  x: -24,
			        	  y: 8,
			        	  text: "+",
			        	  fontSize: 12,
			        	  fontFamily: "Calibri",
			        	  textFill: "white",
			        	  align: "center",
			        	  verticalAlign: "middle"
			        	  });
		        	
		        	var plusBox = new Kinetic.Rect({
		                x: -30,
		                y: 5,
		                width: 20,
		                height:20,
		                fill: 'grey',
		                stroke: 'black',
		                strokeWidth: 1
		              });		        	
		        			        			        	
		        	var minusText = new Kinetic.Text({
			        	  x: 5,
			        	  y: 8,
			        	  text: "-",
			        	  fontSize: 12,
			        	  fontFamily: "Calibri",
			        	  textFill: "white",
			        	  align: "center",
			        	  verticalAlign: "middle"
			        	  });
		        	
		        	var minusBox = new Kinetic.Rect({
		                x: -3,
		                y: 5,
		                width: 20,
		                height:20,
		                fill: 'grey',
		                stroke: 'black',
		                strokeWidth: 1
		              });
		        	
		        	function checkAndShowSelf() {
		        		sizedMap[name] = 1;		        		
		        		var count = 0;
						for (var i in sizedMap) {
							count++;
						}							   
						
						if (count == 5) {
							  $('#instructions0').hide();
							  $('#instructions').show();
							  selfGroup.setVisible(true);
							  
							  for (var i in circleArray) {
							  	 circleArray[i].hideResize();
							  }
						} 
		        		
		        	}
		        	
		        	function increaseCircleSize() {
		        		  checkAndShowSelf();
						  if (box.getRadius() < 115) { 							   
						   		
							   if (box.getRadius() > 60 && simpleText.getFontSize() == 6) {
									simpleText.setFontSize(12);
									simpleText.setX(simpleText.getX() - 20);
									simpleText.setY(simpleText.getY() - 15);
							 	}
							  
							  	box.setRadius(box.getRadius() + 10);					      							    	
						   		layer.draw();						    	
						    	$.post(servicesAPI + "/json/assessmentevent.ajax", 
				    			   		{accountId:$('#userId').val(), explicitId:id , type:"increase-size", 
						    			width:box.getRadius() * 2, height:box.getRadius() * 2,
						    			screenHeight:screen.height, screenWidth:screen.width}, 
				    			    		function(items) {});
						    	
						   }
					  }
					  
		        	
					 function decreaseCircleSize() {
						 checkAndShowSelf();
						 											 
						 if (box.getRadius() > 35) {
							 	
							 	if (box.getRadius() < 60 && simpleText.getFontSize() == 12) {
									simpleText.setFontSize(6);
									simpleText.setX(simpleText.getX() + 20);
									simpleText.setY(simpleText.getY() + 15);
							 	}
							 
						    	box.setRadius(box.getRadius() - 10);					      	
						    	layer.draw(); 
						    	$.post(servicesAPI + "/json/assessmentevent.ajax", 
				    			   		{accountId:$('#userId').val(), explicitId:id , type:"decrease-size", 
						    		width:box.getRadius() * 2, height:box.getRadius() * 2,
						    		screenHeight:screen.height, screenWidth:screen.width}, 
				    			    		function(items) {});
						    	
						   }
					  }
					   
					  plusText.on("click", function(){
					  	increaseCircleSize();
					  });
						   
						  
					  minusText.on("click", function(){
						  decreaseCircleSize();
					   }); 
					   
					  plusBox.on("click", function(){
						  increaseCircleSize();
				      });
					   						  
					  minusBox.on("click", function(){
						  decreaseCircleSize();
					  }); 
		        	
		        	
			          var box = new Kinetic.Circle({
			              x: 0,
			              y: 0,
			              width: 150,
			              height: 150,
			              name: id,
			              fill: 'white',
			              stroke: "black",
			              strokeWidth: 2
			            });

			          var simpleText = new Kinetic.Text({
			        	  x: -40,
			        	  y: -25,
			        	  text: name,
			        	  fontSize: 12,
			        	  fontFamily: "Calibri",
			        	  textFill: "black",
			        	  align: "center",
			        	  verticalAlign: "middle"
			        	  });
			          
			          
				           group.on('dragstart', function() {
				        	 $.post(servicesAPI + "/json/assessmentevent.ajax", 
			    			    		{accountId:$('#userId').val(), explicitId:id , type:"click", x0:group.getX(), y0:group.getY()}, 
			    			    		function(items) {
			    			    				
			    			    		});	
				        	 movedMap[name] = 1;
				           });
				          
						   group.on('dragmove', function() {							   
							   dragArray.push([group.getX(), group.getY()]);	
						   });
							
						   group.on('dragend', function() {							   
							   logArrayEvent(group.getName());
							   
							   var count = 0;
							   for (var i in movedMap) {
							      count++;
							   }							   
							   if (count == 5) {
								   $("#next").show();
							   } 
							   
						   });
			          
			            group.add(box);
			            group.add(simpleText);
			            group.add(plusBox);
			            group.add(plusText);
			            group.add(minusBox);
			            group.add(minusText);
			            
			            group.hideResize = function() {
			            	plusBox.setVisible(false);
			            	plusText.setVisible(false);
			            	minusBox.setVisible(false);
			            	minusText.setVisible(false);
			            }
			            circleArray.push(group);
		        		return group;
		        	
		        }
		        
				function buildSelfCircle(name, x, y) {
		        	
		        	var group = new Kinetic.Group({
			              x: x,
			              y: y,
			              rotationDeg: 0,
			              draggable:false
			            });
			          
			          var box = new Kinetic.Circle({
			              x: 0,
			              y: 0,
			              width: 200,
			              height: 200,
			              name: 'self',
			              fill: 'white',
			              stroke: "black",
			              strokeWidth: 2
			            });

			          var simpleText = new Kinetic.Text({
			        	  x: -10,
			        	  y: -10,
			        	  text: name,
			        	  fontSize: 18,
			        	  fontFamily: "Calibri",
			        	  textFill: "black",
			        	  align: "center",
			        	  verticalAlign: "middle"
			        	  });
			          
			            group.add(box);
			            group.add(simpleText);
			            group.setVisible(false);
		        		return group;
		        	
		        }
		        		       
		        drawImages();
		       
			});
		})(jQuery);		
	</script>

  </body>

</html>