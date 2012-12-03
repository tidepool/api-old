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
  							<li class="active"><c:if test="${ not empty team }">${ team.name }</c:if><c:if test="${ empty team }">New Team</c:if></li>
						</ul>
	  				</div>
	  			</div>
	  			<div class="row-fluid mini-layout team">  					    			
	    			<div class="span10 mini-layout-body">
	      				<form action="<c:url value="/teamPost" />" method="POST">
	      				<input type="hidden" name="teamId" id="teamId" value="${ team.id }">
	      				<div class="row-fluid">
	      				    <div class="span3 bigthree bigthreewithass">Who?</div>
	      				    <div class="span9">
	      				    <input type="text" name="teamName" id="teamName" placeholder="Team Name" <c:if test="${ not empty team }">value="${ team.name }"</c:if>>
	      					<table class="table table-bordered table-striped">
  								<th>Select</th><th>Name</th><th>Age</th><th>Sex</th><th>Job Title</th>
  								<c:forEach var="member" items="${team.teamMembers}" varStatus="rowCounter">
  									<tr><td><input  value="${member.account.userId }" name="activeUsers" type="checkbox" <c:if test="${ member.active }">checked</c:if> ></td><td>${ member.account.firstName } ${ member.account.lastName }</td><td>${ member.account.age }</td><td>${ member.account.gender }</td><td>${ member.account.jobTitle }</td></tr>
  								</c:forEach>
  								<tr class="template-member-row" style="display:none"><td><input class="template-checkbox" checked type="checkbox"/></td><td class="template-name">Kilgore Trout</td><td class="template-age">44</td><td class="template-sex">M</td><td class="template-job-title">Writer</td></tr>
  								<tr><td colspan="5" style="align:center"><a href="#newMemberModal" role="button" class="btn btn-primary" data-toggle="modal">Add Team Member</a></td></tr>
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
	      						<input type="text" name="timeline" id="datepicker" <c:if test="${ not empty team }">value="${ team.formattedTimeline }"</c:if> placeholder="Pick a date"/>
	      						<a href="#emailModal" role="button" class="btn btn-primary emailButton" data-toggle="modal">Email Notification</a>
	      					</div>
	      					
	      				</div>
	      				
	      				<div class="row-fluid">
	      					<div class="span3 bigthree">&nbsp;</div>
	      				    <div class="span9">
	      						<button class="btn" href='<c:url value="/teams" />'>Cancel</button>
	      						<button class="btn btn-success"><c:if test="${ not empty team }">Send Invite</c:if><c:if test="${ empty team }">Create Team</c:if></button>	      						
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
					
					 function saveTeam() {						
						 $.post("<c:url value="/teamSavePost" />",									 
								 {teamId:$('#teamId').val(),
							      teamName:$('#teamName').val(),
							      timeline:$('#datepicker').val()	  
								 }, 
								 function (team) {									
									 $('#teamId').val(team.id);							 		
						 		}, "json"	
						  );						 
					 } 
						
					 $('#teamName').change(function() {
						  saveTeam();
					 });
					 
					 $('#datepicker').change(function() {
						  saveTeam();
					 });
					 
					 $( "#datepicker" ).datepicker();
					 
					 $("#submitTeamMemberButton").click(function() {					 		
						 if ($("#firstName").val() == '' ||
							 $("#lastName").val() == '' ||
							 $("#age").val() == '' ||
							 $("#jobTitle").val() == '' ||
							 $("#email").val() == '') {
									   $('#error').html('Please fill in all fields!');
									   $('#error').show();
									   return true;		   
						 } else {
							
							 $.post("<c:url value="/teamMemberPost" />",									 
									 {teamId:$('#teamId').val(),
								      firstName:$("#firstName").val(),
								 	  lastName:$("#lastName").val(),
								 	  gender:$('input:radio[name=gender]:checked').val(),
								 	  age:$("#age").val(),
								 	  jobTitle:$("#jobTitle").val(),
								 	  email:$("#email").val()}, 
									 function (account) {									
								 		var $tr = $('.template-member-row');
								 		var $clone = $tr.clone();
								 		$clone.find('.template-checkbox').attr('name',"activeUsers");
								 		$clone.find('.template-checkbox').attr('value', account.userId);								 		
								 		$clone.find('.template-name').html(account.firstName + " " + account.lastName);
								 		$clone.find('.template-age').html(account.age);								 		
								 		$clone.find('.template-gender').html(account.gender);
								 		$clone.find('.template-job-title').html(account.age);
								 		$clone.show();
								 		$tr.after($clone);
								 		$('#newMemberModal').modal('hide');
								 		$("#firstName").val("");
										$("#lastName").val("");
										$("#age").val("");
										$("#jobTitle").val("");
										$("#email").val("");
								 		return true;
							 		}, "json"	
							  );
							
							return true;
						}								
						return true;						 						 
					 });
					 
					 $("#saveEmailButton").click(function() {					 		
						 						
							 $.post("<c:url value="/emailPost" />",									 
									 {teamId:$('#teamId').val(),
								      emailSubject:$("#emailSubject").val(),
								 	  emailBody:$("#emailBody").val(),
								 	  emailReminder:$("#emailReminder").val() 
									 }, 
									 function (data) {																	 		
								 		 $('#emailModal').modal('hide');
							 		}, "json"	
							  );
							
							return true;
						});
					 
					 
				});
			})(jQuery);		
		</script>

	<!-- New Member Modal -->
	<div id="newMemberModal" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="newMemberModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="newMemberModalLabel">Add A Team Member</h3>
		</div>
		<div class="modal-body">
			<form class="form-horizontal">
									
					<div class="control-group alert alert-error" style="display:none" id="error">Hold up....</div>
												
					<div class="control-group">
						<label class="control-label" for="firstName">First Name</label>
						<div class="controls">
							<input type="text" id="firstName" name="firstName" placeholder="First Name">
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="lastName">Last Name</label>
						<div class="controls">
							<input type="text" id="lastName" name="lastName" placeholder="Last Name">
						</div>
					</div>
					
					<div class="control-group">
					<label class="control-label" for="gender">Gender</label>
						<div class="controls">
							<label class="radio inline">							    
		  						<input type="radio" name="gender" value="male"> Male
							</label>
							<label class="radio inline">
		  						<input type="radio" name="gender" value="female"> Female
							</label>						
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="company">Age</label>
						<div class="controls">
							<input type="text" id="age" name="age" placeholder="age">
						</div>
					</div>	
					
					<div class="control-group">
						<label class="control-label" for="company">Job Title</label>
						<div class="controls">
							<input type="text" id="jobTitle" name="jobTitle" placeholder="Job Title">
						</div>
					</div>	
															
					<div class="control-group">
						<label class="control-label" for="email">Email</label>
						<div class="controls">
							<input type="text" id="email" name="email" placeholder="email">
						</div>
					</div>
					
				</form>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
			<button class="btn btn-primary" id="submitTeamMemberButton">Add Team Member</button>
		</div>
	</div>
	
	
	<div id="emailModal" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="emailModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="emailModalLabel">Email Notification</h3>
		</div>
		<div class="modal-body">
			<form class="form-horizontal">
									
					<div class="control-group alert alert-error" style="display:none" id="emailError">Hold up....</div>
					
					<div class="control-group">
						<label class="control-label" for="emailTo">To</label>
						<div class="controls">
							<input type="text" id="emailTo" name="emailTo" placeholder="All selected users">
						</div>
					</div>
												
					<div class="control-group">
						<label class="control-label" for="emailSubject">Subject</label>
						<div class="controls">
							<input type="text" id="emailSubject" name="emailSubject" placeholder="Add a subject" <c:if test="${ not empty team}">${ team.inviteSubject }</c:if>>
						</div>
					</div>
																				
					<div class="control-group">
						<label class="control-label" for="emailBody">Email</label>
						<div class="controls">
							<textarea id="emailBody" name="emailBody" placeholder="Enter a message"><c:if test="${ not empty team}">${ team.inviteBody }</c:if></textarea>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="emailReminder">Remind with Email</label>
						<div class="controls">
							<select id="emailReminder" name="emailReminder" placeholder="Set a reminder">
								<option value="daily" <c:if test="${ not empty team and team.inviteReminder eq 'daily'}">selected</c:if> >Daily</option>
								<option value="weekly" <c:if test="${ not empty team and team.inviteReminder eq 'weekly'}">selected</c:if> >Weekly</option>
								<option value="monthly" <c:if test="${ not empty team and team.inviteReminder eq 'monthly'}">selected</c:if>>Monthly</option>
							</select>
						</div>
					</div>
					
					
				</form>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
			<button class="btn btn-primary" id="saveEmailButton">Save Email</button>
		</div>
	</div>
	
</body>
</html>