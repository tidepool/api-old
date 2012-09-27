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
    <title>Tidepool(Admin) Group Editor</title>   
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
    </style>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.css" />" rel="stylesheet">

  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="<c:url value="/" />">Tidepool(Admin) Group Editor</a>
          <div class="nav-collapse">
            <ul class="nav">                            
             	<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Admin<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/admin/createAccount" />">Create Account</a>
								</li>
								<li><a href="<c:url value="/admin/accounts" />">Accounts</a>
								</li>
								<li class="divider"></li>
								<li class="nav-header">Training</li>
								<li><a href="<c:url value="/admin/trainingSets" />">Training sets</a>
								</li>							
								</li>
								<li class="divider"></li>
								<li class="nav-header">Groups</li>
								<li><a href="<c:url value="/admin/groups" />">Group List</a>
								</li>							
								</li>
							</ul>
						</li>
            </ul>
            <p class="navbar-text pull-right">Logged in as <a href="/">${account.email}</a> | <a href="<c:url value="/signout" />">logout</a></p>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
		<input type="hidden" id="groupId" value="${ group.id }">
		<div class="hero-unit">			  			
  			
  			<p>Group: ${ group.id }</p>
			<div class="row">

				<div class="span-4">

					<div id="cart">
						<h3>Current Attributes</h3>
						<div class="ui-widget-content">
							<ol>
							    <li class="placeholder">Add attributes here</li>
								<c:forEach var="groupAttribute" items="${group.attributes}" varStatus="groupAttributeCounter">
									<li class="placeholder">${ groupAttribute.element_group }</li>								
								</c:forEach>
							</ol>
						</div>
					</div>

				</div>

				<div class="span-8">
					<div id="catalog">
						<h3>
							<a href="#">All Attributes</a>
						</h3>
						<div class="row">							
								<c:forEach var="attribute" items="${allAttributes}" varStatus="rowCounter">
									
									<c:if test="${rowCounter.index == 0 or rowCounter.index % 20 == 0}">     	
     									<c:if test="${rowCounter.index != 0}">
     										</ul>
     										</div>
     									</c:if>      									
      									<div class="span-2">
      									<ul>      								
      								</c:if>
																			
									<li>${ attribute.element_name }</li>								
								
									<c:if test="${fn:length(allAttributes) - 1 == rowCounter.index}">  
         								</ul>
         								</div>
      								</c:if>
								
								</c:forEach>
							
						</div>
						
					</div>

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
	<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
	
	<script>	
		(function ($) {
			$(document).ready(function () {				
				
				
				$( "#catalog" ).accordion();
				$( "#catalog li" ).draggable({
					appendTo: "body",
					helper: "clone"
				});
				$( "#cart ol" ).droppable({
					activeClass: "ui-state-default",
					hoverClass: "ui-state-hover",
					accept: ":not(.ui-sortable-helper)",
					drop: function( event, ui ) {
						$( this ).find( ".placeholder" ).remove();
						$( "<li></li>" ).text( ui.draggable.text() ).appendTo( this );
					}
				}).sortable({
					items: "li:not(.placeholder)",
					sort: function() {
						// gets added unintentionally by droppable interacting with sortable
						// using connectWithSortable fixes this, but doesn't allow you to customize active/hoverClass options
						$( this ).removeClass( "ui-state-default" );
					}
				});
				
				
			});
		})(jQuery);		
	</script>


  </body>

</html>