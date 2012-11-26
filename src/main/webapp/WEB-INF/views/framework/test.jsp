<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
	<c:import url="head_fragment.jsp">
		<c:param name = "title" value = "HireSmart"/>
	</c:import>

	<body>	
		<c:import url="nav_fragment.jsp">
			<c:param name = "title" value = "HireSmart"/>
		</c:import>
		
		<div id="wrap">
	    	<div class="container-fluid">
	    	    <div class="row-fluid mini-layout">
	  				<div class="span10 mini-layout-breadcrumbs">
	  					<ul class="breadcrumb">
  							<li><a href="#">Home</a> <span class="divider">/</span></li>
  							<li><a href="#">Library</a> <span class="divider">/</span></li>
  							<li class="active">Data</li>
						</ul>
	  				</div>
	  			</div>
	  			<div class="row-fluid mini-layout">  				
	    			<div class="span2 mini-layout-sidebar">
	      				POW
	    			</div>
	    			<div class="span8 mini-layout-body">
	      				POW
	    			</div>
	  			</div>
			</div>
		</div>
		
		<c:import url="foot_fragment.jsp"></c:import>		
		<c:import url="javascript_fragment.jsp"></c:import>		
		<script>	
			(function ($) {
				$(document).ready(function () {								
										
				});
			})(jQuery);		
		</script>			
	</body>
</html>