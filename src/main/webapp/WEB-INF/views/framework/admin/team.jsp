<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
	<c:import url="../common/head_fragment.jsp">
		<c:param name = "title" value = "Team"/>
	</c:import>

	<body>	
		<c:import url="../common/nav_fragment.jsp">
			<c:param name = "title" value = "HireSmart"/>
		</c:import>
		
		<div id="wrap">
	    	<div class="container-fluid">
	    	    <div class="row-fluid mini-layout span8">
	  				<div class="mini-layout-breadcrumbs">
	  					<ul class="breadcrumb">
  							<li><a href="adminhome">Home</a> <span class="divider">/</span></li>
  							<li><a href="teams">Teams</a> <span class="divider">/</span></li>
  							<li class="active">New Team</li>
						</ul>
	  				</div>
	  			</div>
	  			<div class="row-fluid mini-layout team">  					    			
	    			<div class="span10 mini-layout-body">
	      				<form action="<c:url value="/teamPost" />" method="POST">
	      				<div class="row-fluid">
	      				    <div class="span3 bigthree bigthreewithass">Who?</div>
	      				    <div class="span9">
	      				    <input type="text" name="teamName" id="teamName" placeholder="Team Name">
	      					<table class="table table-bordered table-striped">
  								<th>Select</th><th>Name</th><th>Age</th><th>Sex</th><th>Job Title</th>
  								<tr><td><input type="checkbox"/></td><td>Kilgore Trout</td><td>44</td><td>M</td><td>Writer</td></tr>
  								<tr><td><input type="checkbox"/></td><td>Billy Pilgrim</td><td>44</td><td>M</td><td>Writer</td></tr>
  								<tr><td colspan="5" style="align:center"><button class="btn btn-primary">Add Team Member</button></td></tr>
							</table>							
							</div>
														
	      				</div>
	      				<div class="row-fluid">
	      				    <div class="span3 bigthree bigthreewithass">What?</div>
	      				    <div class="span9">
	      					<table class="table table-bordered table-striped">
  								<th>Select</th><th>Dimension</th><th>Strength</th>
  								<tr><td><input type="checkbox"/></td><td>Dimension 1</td><td>44</td></tr>
  								<tr><td><input type="checkbox"/></td><td>Dimension 2</td><td>44</td></tr>
							</table>
							</div>
	      				</div>
	      				
	      				<div class="row-fluid">
	      				    <div class="span3 bigthree">When?</div>
	      				    <div class="span9">
	      						<input type="text" id="datepicker" placeholder="Pick a date"/>
	      					</div>
	      				</div>
	      				
	      				<div class="row-fluid">
	      					<div class="span3 bigthree">&nbsp;</div>
	      				    <div class="span9">
	      						<button class="btn btn-primary">Create Team</button>
	      					</div>
	      				</div>
	      			</form>	
	    			</div>
	  			</div>
			</div>
		</div>
		
		<c:import url="../common/foot_fragment.jsp"></c:import>		
		<c:import url="../common/javascript_fragment.jsp"></c:import>
		<link href="<c:url value="/resources/bootstrap/css/jquery-ui-1.9.2.custom.css" />" rel="stylesheet"/>
		<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>		
		<script>	
			(function ($) {
				$(document).ready(function () {								
						
					 $( "#datepicker" ).datepicker();
					
				});
			})(jQuery);		
		</script>			
	</body>
</html>