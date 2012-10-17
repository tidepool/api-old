<!DOCTYPE html>
<!--
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
     KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.
-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="format-detection" content="telephone=no" />
        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height, target-densitydpi=device-dpi" />
        <title>Hello World</title>
    </head>
    <body>
        <div class="app">
            <h1>PhoneGap</h1>
            <div id="deviceready">
                <p class="status pending blink">Connecting to Device</p>
                <p class="status complete blink hide">Device is Ready</p>
            </div>
        </div>
       <!--  <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>  -->
        <script type="text/javascript" src="http://localhost:8080/tidepoolAPI/resources/bootstrap/js/jquery.js"></script> 
     <!--    <script src="js/jquery.mobile-1.2.0.min.js"></script>        -->
        <script type="text/javascript">
        (function ($) {
        	
        	$(document).ready(function () {	
        							
        			$.support.cors = true;
        		  /*  	$.mobile.allowCrossDomainPages = true;	 */
        		    
 /*        		   $.ajax({
  						url: "http://localhost:8080/tidepoolAPI/json/test1.ajax",
  						crossDomain:true,
  						data:{id:"oooh"}
					}).done(function(data) { 
  						alert("data: " + data);
					}); */
        		   
         		    $.post("http://localhost:8080/tidepoolAPI/json/test.ajax", 
        		    		{id:'ouch'}, 
        		    		function(item) {	
        		    			alert("Item:" + item);	
        		    		}).error(function(jqXHR, textStatus, errorThrown) { 
        		    			alert("error: " + jqXHR + " " + textStatus + " " + errorThrown); 
        		    			}); 
        		    
//        		    $.get("http://localhost:8080/tidepoolAPI/json/test1.ajax", { id:"id" }, function(value) {																												
//        		    	alert("V!!!" + value);
//        		    }).error(function(jqXHR, textStatus, errorThrown) { alert("error: " + jqXHR + " " + textStatus + " " + errorThrown); });			    	
//        		
        		    
        		   $.getJSON('http://localhost:8080/tidepoolAPI/json/test1.ajax',
        		    		{id: "poooooo"},
        		    		function(data) {
        		    	alert("Data:" + data);
        		    }).error(function(jqXHR, textStatus, errorThrown) { 
        		    	alert("error: " + jqXHR + " " + textStatus + " " + errorThrown); 
        		    	});		    	
        		    
        		/*    $.get("http://localhost:8080/tidepoolAPI/signin", {id:"id" }, 			
        		    function(value) {																												
        		    	alert("V!!!" + value);
        		    }).error(function(jqXHR, textStatus, errorThrown) { alert("error: " + jqXHR + " " + textStatus + " " + errorThrown); });			    	
        		  */
        		    
        		    
        	});
        			
        })(jQuery);
        </script>
    </body>
</html>