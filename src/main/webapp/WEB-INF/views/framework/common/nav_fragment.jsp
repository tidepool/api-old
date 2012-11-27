<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="<c:url value="/" />">${ param.title }</a>
          <div class="nav-collapse">
            <ul class="nav">
              <!-- <li class="active"><a href="/">How it works</a></li>
              <li><a href="/try-it">Try It</a></li>
              <li><a href="/contact">Contact</a></li> -->
            </ul>
          </div><!--/.nav-collapse -->
        
        <c:if test="${ empty account }">	
	         <form class="navbar-form pull-right" action="<c:url value="/signin/authenticate" />" method="post">
				<c:if test="${not empty param.login_error}">
			    	<div>
	  					Error logging in. Try again.
					</div>    		
				</c:if>            
	            <input type="text" class="span2" name="j_username" id="inputLogin" placeholder="Email">
	  			<input type="password" name="j_password" id="inputPassword" placeholder="Password">
	  			<button type="submit" class="btn">Login</button>
	  			<div class="forgot right">
	  				<a>forgot password</a> / <a>forgot login</a>
				</div> 
			</form>
		</c:if>
		
		<c:if test="${ not empty account }">
			<p class="navbar-text pull-right">Logged in as <a href="/">${account.email}</a> | <a href="<c:url value="/signout" />">logout</a></p>
		</c:if>			
        </div>        
      </div>
</div>