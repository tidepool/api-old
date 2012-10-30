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
    </style>
   

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
          <a class="brand" href="<c:url value="/" />">Tidepool Assess</a>          
        </div>
      </div>
    </div>

    <div class="container">
		
		<div class="hero-unit">
			<div class="offset1">
				<h3>Welcome to the photo assesment. Before starting, please provide us with some basic information.</h3>
			</div>	
			
			
			<form class="form-horizontal" action="<c:url value="/assessPost" />" method="get">
							
				
				<div class="control-group">
					<label class="control-label" for="zipCode">Zip Code</label>
					<div class="controls">
						<input type="text" name="zipCode" id="zipCode" placeholder="Zip Code">
					</div>
				</div>
				
				
				<div class="control-group">
					<label class="control-label">Scoring for the following questions:</label>
					<div class="controls">
						<label class="radio inline scoring">
	  						<input type="radio" checked value="option1">1 Disagree strongly
						</label>
						<label class="radio inline scoring">
	  						<input type="radio"  checked value="option2"> 2 Disagree moderately
						</label>
						<label class="radio inline scoring">
	  						<input type="radio"  checked  value="option3"> 3 Disagree a little
						</label>
						<label class="radio inline scoring">
	  						<input type="radio"  checked value="option4"> 4 Neither agree nor disagree
						</label>
						<label class="radio inline scoring">
	  						<input type="radio"  checked  value="option5"> 5 Agree a little
						</label>
						<label class="radio inline scoring">
	  						<input type="radio"  checked value="option6"> 6 Agree moderately
						</label>
						<label class="radio inline scoring">
	  						<input type="radio"  checked value="option7"> 7 Agree strongly
						</label>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="extraverted">${ account.extravertedLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="extraverted" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="extraverted" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="extraverted" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="extraverted" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="extraverted" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="extraverted" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="extraverted"  value="7"> 7
						</label>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="critical">${ account.criticalLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="critical" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="critical" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="critical" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="critical" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="critical" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="critical" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="critical"  value="7"> 7
						</label>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="dependable">${ account.dependableLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="dependable" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="dependable" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="dependable" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="dependable" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="dependable" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="dependable" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="dependable"  value="7"> 7
						</label>
					</div>
				</div>
	
				<div class="control-group">
					<label class="control-label" for="anxious">${ account.anxiousLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="anxious" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="anxious" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="anxious" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="anxious" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="anxious" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="anxious" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="anxious"  value="7"> 7
						</label>
					</div>
				</div>			
				
				<div class="control-group">
					<label class="control-label" for="open">${ account.openLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="open" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="open" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="open" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="open" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="open" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="open" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="open"  value="7"> 7
						</label>
					</div>
				</div>		
	
				<div class="control-group">
					<label class="control-label" for="reserved">${ account.reservedLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="reserved" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="reserved" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="reserved" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="reserved" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="reserved" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="reserved" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="reserved"  value="7"> 7
						</label>
					</div>
				</div>	
				
				
				<div class="control-group">
					<label class="control-label" for="sympathetic">${ account.sympatheticLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="sympathetic" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="sympathetic" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="sympathetic" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="sympathetic" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="sympathetic" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="sympathetic" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="sympathetic"  value="7"> 7
						</label>
					</div>
				</div>				
				
				<div class="control-group">
					<label class="control-label" for="disorganized">${ account.disorganizedLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="disorganized" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="disorganized" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="disorganized" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="disorganized" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="disorganized" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="disorganized" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="disorganized"  value="7"> 7
						</label>
					</div>
				</div>				
				
	
				<div class="control-group">
					<label class="control-label" for="calm">${ account.calmLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="calm" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="calm" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="calm" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="calm" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="calm" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="calm" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="calm"  value="7"> 7
						</label>
					</div>
				</div>						
				
				<div class="control-group">
					<label class="control-label" for="conventional">${ account.conventionalLabel }</label>
					<div class="controls">
						<label class="radio inline">
	  						<input type="radio" name="conventional" value="1"> 1
						</label>
						<label class="radio inline">
	  						<input type="radio" name="conventional" value="2"> 2
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="conventional" value="3"> 3
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="conventional" value="4"> 4
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="conventional" value="5"> 5
						</label>
						<label class="radio inline">
	  						<input type="radio"  name="conventional" value="6"> 6
						</label>
						<label class="radio inline">
	  						<input type="radio" name="conventional"  value="7"> 7
						</label>
					</div>
				</div>				
	
				
				<div class="control-group">
					<div class="controls">					
						<button type="submit" id="feedbackSubmit" class="btn">Next</button>
					</div>
				</div>
			</form>
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
	
	<script>	
		(function ($) {
			$(document).ready(function () {				
				
				$('#feedbackSubmit').click(function() {
					
					if ($("#zipCode").val() == '' ||
						!$("input[name='extraverted']:checked").val() ||
						!$("input[name='critical']:checked").val() ||		
						!$("input[name='dependable']:checked").val() ||
						!$("input[name='anxious']:checked").val() ||
						!$("input[name='open']:checked").val() ||
						!$("input[name='reserved']:checked").val() ||
						!$("input[name='sympathetic']:checked").val() ||
						!$("input[name='disorganized']:checked").val() ||
						!$("input[name='calm']:checked").val() ||
						!$("input[name='conventional']:checked").val()
					) {
						   alert('Please provide answers to all of the questions!');
						}
						else {
						 	return true;
						}
					
					return false;
				});
				
			});
		})(jQuery);		
	</script>


  </body>

</html>