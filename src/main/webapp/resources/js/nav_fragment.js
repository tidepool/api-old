(function ($) {
	$(document).ready(function () {								
		
		$("#passwordEmailButton").click(function() {					 		
				
			 $.post("passwordEmailPost",									 
					 {
						 email:$("#emailReset").val()			 	  
					 }, 
					 function (data) {																	 						 		 						 
						 $("#passwordEmailButton").hide();
						 $("#passwordResetForm").hide();
						 $("#passwordResetInstructionsButton").show();
						 $("#passwordResetInstructions").show();
			 		}, "json"	
			  );
			
			return true;
		});
		
		$("#passwordResetInstructionsButton").click(function() {
			$('#passwordEmailModal').modal('hide');
		});
		
	});
})(jQuery);