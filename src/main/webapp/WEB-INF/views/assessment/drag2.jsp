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
    	left: 500px;
    	position: absolute;
    	top: 50px;
    	z-index: 1000;
    		
	 }
      
 	#next {
    	left: 700px;
    	position: absolute;
    	top: 100px;
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
				    		  layer.add(buildSelfCircle("Self", 200, 100));
				    		  layer.add(build5Circle(imageObj3, "happy.png", 267, 400, 100, 250)); 
				    		  layer.add(build5Circle(imageObj1, "success.png", 200, 90, 550, 250)); 
				    		  layer.add(build5Circle(imageObj2, "open_consc.png", 200, 200, 675, 250));				    		  
				    		  stage.add(layer);				    		  				    		  
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
							   dragArray.push([group.getX(), group.getY()]);	
						   });
							
						   group.on('dragend', function() {							   
							   logArrayEvent(group.getName());
							   
							   var count = 0;
							   for (var i in movedMap) {
							      count++;
							   }							   
							   if (count == 3) {
								   $("#next").show();
							   } 
							   
						   });
			          
			            		            
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
		        		return group;
		        	
		        }
		        		       
		        drawImages();
		       
			});
		})(jQuery);		
	</script>

  </body>

</html>