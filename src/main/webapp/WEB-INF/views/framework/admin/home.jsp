<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
	<c:import url="../common/head_fragment.jsp">
		<c:param name = "title" value = "HireSmart"/>
	</c:import>

	<body>	
		<c:import url="../common/nav_fragment.jsp">
			<c:param name = "title" value = "HireSmart"/>
		</c:import>
		
		<div id="wrap">
	    	<div class="container-fluid">
	    	    <div class="row-fluid mini-layout">
	  				<div class="span10 mini-layout-breadcrumbs">
	  					<ul class="breadcrumb">
  							<li class="active"><a href="#">Home</a> <span class="divider">/</span></li>  							
						</ul>
	  				</div>
	  			</div>
	  			<div class="row-fluid mini-layout">  				
	    			<div class="span2 mini-layout-sidebar">
	      				<div class="accordion" id="accordion2">
						  <div class="accordion-group">
						    <div class="accordion-heading">
						      <a href="teams">
						        Teams
						      </a>
						    </div>
						   </div>
						  <div class="accordion-group">
						    <div class="accordion-heading">
						      <a href="reports">
						       Reports
						      </a>
						    </div>						    
						  </div>
						
							<div class="accordion-group">
						    <div class="accordion-heading">
						      <a href="tag">
						       Tag
						      </a>
						    </div>						    
						  </div>
						</div>
						
						
	    			</div>
	    			<div class="span7 mini-layout-body">
	      				Main body
	    			</div>
	  			</div>
			</div>
		</div>
		
		<c:import url="../common/foot_fragment.jsp"></c:import>		
		<c:import url="../common/javascript_fragment.jsp"></c:import>		
		<script>	
			(function ($) {
				$(document).ready(function () {								
					
					$(".collapse").collapse();
					
				});
			})(jQuery);		
		</script>			
	</body>
</html>