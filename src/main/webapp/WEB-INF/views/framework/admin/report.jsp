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
  							<li><a href="adminhome">Home</a> <span class="divider">/</span></li>
  							<li><a href="reports">Reports</a> <span class="divider">/</span></li>
  							<li class="active"><c:if test="${ not empty report }">${ report.name }</c:if><c:if test="${ empty report }">New Report</c:if></li>
						</ul>
	  				</div>
	  			</div>
	  			<div class="row-fluid mini-layout">  				
	    			<div class="span2 mini-layout-sidebar">
	      				Filters
	    			</div>
	    			<div class="span7 mini-layout-body">
	      				Add report
	    			</div>
	  			</div>
			</div>
		</div>
		
		<c:import url="../common/foot_fragment.jsp"></c:import>		
		<c:import url="../common/javascript_fragment.jsp"></c:import>		
		<script>	
			(function ($) {
				$(document).ready(function () {								
										
				});
			})(jQuery);		
		</script>			
	</body>
</html>