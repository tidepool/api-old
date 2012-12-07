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
  							<li class="active"><a href="#">Tag</a> <span class="divider"></span></li>  							
						</ul>
	  				</div>
	  			</div>
	  			<div class="row-fluid mini-layout">  				
	    			<div class="span2 mini-layout-sidebar">
	    				
						</div>
	    			
	    			<div class="span7 mini-layout-body">
	      				<div>
	      					Copy and past this tag on your html page before the end <code>body</code> tag
	      				</div>
	      				<div>
	      					<textarea id="codelink_stage1" class="span7" readonly="" rows="14" name="codelink_stage1" wrap="off"><c:import url="tag-code.jsp"></c:import></textarea>
	      				</div>
	      				<div>	      				
	      				 Here is a sample button <button class="btn btn-success" id="tidepoolButton">Test</button>
	      				</div>	      			
	      			</div>	
	    		</div>
	  		</div>
		</div>
		
		<c:import url="../common/foot_fragment.jsp"></c:import>		
		<c:import url="../common/javascript_fragment.jsp"></c:import>		
		<c:import url="tag-code.jsp"></c:import>		
	</body>
</html>