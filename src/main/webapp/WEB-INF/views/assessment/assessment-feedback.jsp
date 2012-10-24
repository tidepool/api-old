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
		
		<div class="offset1">
			<p>Thanks for taking the assessment. Please help us improve the process by answering these questions:</p>
		</div>	
		
		
		<form class="form-horizontal" action="<c:url value="/postAssessfeedback" />" method="get">
												
			<div class="control-group">
				<label class="control-label">Scoring</label>
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
				<label class="control-label" for="artistic_appeal">Artistic Appeal</label>
				<div class="controls">
					<label class="radio inline">
  						<input type="radio" name="artistic_appeal" value="1"> 1
					</label>
					<label class="radio inline">
  						<input type="radio" name="artistic_appeal" value="2"> 2
					</label>
					<label class="radio inline">
  						<input type="radio"  name="artistic_appeal" value="3"> 3
					</label>
					<label class="radio inline">
  						<input type="radio"  name="artistic_appeal" value="4"> 4
					</label>
					<label class="radio inline">
  						<input type="radio"  name="artistic_appeal" value="5"> 5
					</label>
					<label class="radio inline">
  						<input type="radio"  name="artistic_appeal" value="6"> 6
					</label>
					<label class="radio inline">
  						<input type="radio" name="artistic_appeal"  value="7"> 7
					</label>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="effective_user_interface">Effective User Interface</label>
				<div class="controls">
					<label class="radio inline">
  						<input type="radio" name="effective_user_interface" value="1"> 1
					</label>
					<label class="radio inline">
  						<input type="radio" name="effective_user_interface" value="2"> 2
					</label>
					<label class="radio inline">
  						<input type="radio"  name="effective_user_interface" value="3"> 3
					</label>
					<label class="radio inline">
  						<input type="radio"  name="effective_user_interface" value="4"> 4
					</label>
					<label class="radio inline">
  						<input type="radio"  name="effective_user_interface" value="5"> 5
					</label>
					<label class="radio inline">
  						<input type="radio"  name="effective_user_interface" value="6"> 6
					</label>
					<label class="radio inline">
  						<input type="radio" name="effective_user_interface"  value="7"> 7
					</label>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="interest_in_measurement">Interest in what is being measured by my selections</label>
				<div class="controls">
					<label class="radio inline">
  						<input type="radio" name="interest_in_measurement" value="1"> 1
					</label>
					<label class="radio inline">
  						<input type="radio" name="interest_in_measurement" value="2"> 2
					</label>
					<label class="radio inline">
  						<input type="radio"  name="interest_in_measurement" value="3"> 3
					</label>
					<label class="radio inline">
  						<input type="radio"  name="interest_in_measurement" value="4"> 4
					</label>
					<label class="radio inline">
  						<input type="radio"  name="interest_in_measurement" value="5"> 5
					</label>
					<label class="radio inline">
  						<input type="radio"  name="interest_in_measurement" value="6"> 6
					</label>
					<label class="radio inline">
  						<input type="radio" name="interest_in_measurement"  value="7"> 7
					</label>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="understanding_personality">Do you think picture selections, like you just made, could be helpful in understanding your personality?</label>
				<div class="controls">
					<label class="radio inline">
  						<input type="radio" name="understanding_personality" value="1"> 1
					</label>
					<label class="radio inline">
  						<input type="radio" name="understanding_personality" value="2"> 2
					</label>
					<label class="radio inline">
  						<input type="radio"  name="understanding_personality" value="3"> 3
					</label>
					<label class="radio inline">
  						<input type="radio"  name="understanding_personality" value="4"> 4
					</label>
					<label class="radio inline">
  						<input type="radio"  name="understanding_personality" value="5"> 5
					</label>
					<label class="radio inline">
  						<input type="radio"  name="understanding_personality" value="6"> 6
					</label>
					<label class="radio inline">
  						<input type="radio" name="understanding_personality"  value="7"> 7
					</label>
				</div>
			</div>			
			
			<div class="control-group">
				<label class="control-label" for="interesting_dating_partners">Provided you were given accurate feedback on your personality, would you consider using a dating site
where picture selection helped match you with interesting dating partners?</label>
				<div class="controls">
					<label class="radio inline">
  						<input type="radio" name="interesting_dating_partners" value="1"> 1
					</label>
					<label class="radio inline">
  						<input type="radio" name="interesting_dating_partners" value="2"> 2
					</label>
					<label class="radio inline">
  						<input type="radio"  name="interesting_dating_partners" value="3"> 3
					</label>
					<label class="radio inline">
  						<input type="radio"  name="interesting_dating_partners" value="4"> 4
					</label>
					<label class="radio inline">
  						<input type="radio"  name="interesting_dating_partners" value="5"> 5
					</label>
					<label class="radio inline">
  						<input type="radio"  name="interesting_dating_partners" value="6"> 6
					</label>
					<label class="radio inline">
  						<input type="radio" name="interesting_dating_partners"  value="7"> 7
					</label>
				</div>
			</div>	

			
			<div class="control-group">
				<div class="controls">					
					<button type="submit" id="doneButton" class="btn">Done</button>
				</div>
			</div>
		</form>
		
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
				
				$('#doneButton').click(function() {
				if (!$("input[name='artistic_appeal']:checked").val() ||
						!$("input[name='effective_user_interface']:checked").val() ||		
						!$("input[name='interest_in_measurement']:checked").val() ||
						!$("input[name='understanding_personality']:checked").val() ||
						!$("input[name='interesting_dating_partners']:checked").val() 
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