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
    	left: 50px;
    	position: absolute;
    	top: 75px;
    	z-index: 1000;
    	border:3px solid #000;
    	padding:10px;
    		
	 }
      
 	#next {
    	left: 700px;
    	position: absolute;
    	top: 400px;
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
					<p>Position the photos over the squares in order of importance. </p> 					
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
		
		<c:forEach var="codedItem" items="${images}" varStatus="rowCounter">
			//${cdn_url}/${codedItem.bucket_name}/${codedItem.folder_name}/${codedItem.picture_id}
		</c:forEach>
		
		(function ($) {
			$(document).ready(function () {	
				
				var movedMap = {};
				var dragArray = [];
				var stage = new Kinetic.Stage({
		            container: "container",
		            width: 1200,
		            height: 700
		          });
		          var layer = new Kinetic.Layer();	
				
		          
		      $("#next").click(function() {
		    	  $.post(servicesAPI + "/json/assessmentevent.ajax", 
	  			    		{accountId:$('#userId').val(), explicitId:'next' , type:"next"}, 
	  			    		function(items) {});
		    	 window.location="<c:url value="/drag3"/>";  
		      });  
		     
		      $("#start").click(function() {
			  		$("#instructions").hide();
			   }); 
		      
			  			  
		      function drawImages() {		            
		    	 		    	  
		    	  var imageObj1 = new Image();
		    	  imageObj1.onload = function() {
		    		  
		    		  var imageObj2 = new Image();
			    	  imageObj2.onload = function() {
			    		  
			    		  var imageObj3 = new Image();
				    	  imageObj3.onload = function() {
				    		  
				    		  var imageObj4 = new Image();
					    	  imageObj4.onload = function() {
				    		  
					    		  var imageObj5 = new Image();
						    	  imageObj5.onload = function() {
					    		  
						    		  layer.add(buildSelfCircle("1", 50, 100));
						    		  layer.add(buildSelfCircle("2", 260, 100));
						    		  layer.add(buildSelfCircle("3", 470, 100));
						    		  layer.add(buildSelfCircle("4", 680, 100));
						    		  layer.add(buildSelfCircle("5", 890, 100));
						    		  layer.add(build5Circle(imageObj5, "image0", 100, 45, 50, 400)); 
						    		  layer.add(build5Circle(imageObj4, "image1", 100, 45, 200, 400)); 
						    		  layer.add(build5Circle(imageObj3, "image2", 133, 200, 300, 400)); 
						    		  layer.add(build5Circle(imageObj1, "image3", 100, 45, 550, 400)); 
						    		  layer.add(build5Circle(imageObj2, "image4", 100, 100, 675, 400));				    		  
						    		  stage.add(layer);
						    	  }
						    	  imageObj5.src = "<c:url value="/resources/bootstrap/img/success.png" />";
					    	  }
					    	  imageObj4.src = "<c:url value="/resources/bootstrap/img/success.png" />";
				    	  }
				    	  imageObj3.src = "<c:url value="/resources/bootstrap/img/happy.png" />";			    		  			    		 
			    	  }
			    	  imageObj2.src = "<c:url value="/resources/bootstrap/img/open_consc.png" />";		    		  		    		  		    	  
		    	  }
		    	  imageObj1.src = "<c:url value="/resources/bootstrap/img/success.png" />";		    	  		          
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
		        
				function logEvent(name, image) {
		        			        	
		        	$.post(servicesAPI + "/json/assessmentevent.ajax", 
    			   		{accountId:$('#userId').val(), explicitId:name , type:"imageOver", attributeValue:image}, 
    			    		function(items) {});
		        }
		        
		       
		        function build5Circle(imageObj, name, height, width, x, y) {
		        	
			          var group = new Kinetic.Image({
			              x: x,
			              y: y,
			              name:name,
			              image: imageObj,
			              width: width,
			              height: height,
			              draggable:true
			            });
			         
			          
				           group.on('dragstart', function() {
				        	 $.post(servicesAPI + "/json/assessmentevent.ajax", 
			    			    		{accountId:$('#userId').val(), explicitId:name , type:"click", x0:group.getX(), y0:group.getY()}, 
			    			    		function(items) {
			    			    				
			    			    		});	
				        	 movedMap[name] = 1;
				           });
				          
						   group.on('dragmove', function() {							   
							   //dragArray.push([group.getX(), group.getY()]);	
						   });
							
						   group.on('dragend', function() {							   
							   //logArrayEvent(group.getName());
							   
							   group.isOver(group.getX(), group.getY());
							   
							   var count = 0;
							   for (var i in movedMap) {
							      count++;
							   }							   
							   if (count == 5) {
								   $("#next").show();
							   } 
							   
						   });
			          
						group.isOver = function(mouseX, mouseY) {							
							for (var i = 0; i < 5; i++) {
								if (mouseX > (50 + (i * 200)) && mouseX < (250 + (i * 200)) 
									&& mouseY > 100 && mouseY < 300) {								
									logEvent(group.getName(), i + 1);
									return true;
								}
							}							
						}   
			            		            
		        		return group;
		        	
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
			              width: 200,
			              height: 200,
			              name: name,
			              fill: 'white',
			              stroke: "black",
			              strokeWidth: 2
			            });

			          var simpleText = new Kinetic.Text({
			        	  x: 100,
			        	  y: 100,
			        	  text: name,
			        	  fontSize: 18,
			        	  fontFamily: "Calibri",
			        	  textFill: "black",
			        	  align: "center",
			        	  verticalAlign: "middle"
			        	  });
			          	
			          
			            group.add(box);
			            group.add(simpleText);
		        		return group;
		        	
		        }
		        		       
		        drawImages();
		       
			});
		})(jQuery);		
	</script>

  </body>

</html>