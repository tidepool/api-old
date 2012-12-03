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
    <div class="container">    	
    	<div class="row-fluid">
    		<!-- <div class="span5" style="padding-top:40px">...</div> -->
			<div class="hero-unit">
			
				<form class="form-horizontal" action="<c:url value="/resetpasswordPost" />" method="POST">
									
					<div class="control-group alert alert-error" style="display:none" id="error">Hold up....</div>
					
					<input type="hidden" name="challenge" value="${ challenge }">
												
					<div class="control-group">
						<label class="control-label" for="password">Password</label>
						<div class="controls">
							<input type="password" id="password" name="password" placeholder="Password">
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="password">Re-type Password</label>
						<div class="controls">
							<input type="password" id="repassword" name="repassword" placeholder="Retype Password">
						</div>
					</div>
					
					
					<div class="control-group">
						<div class="controls">				
							<button type="submit" id="signUpButton" class="btn">Reset</button>
						</div>
					</div>
				</form>
			</div>	
		</div>	
		
		
	</div> <!-- /container -->
	
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
