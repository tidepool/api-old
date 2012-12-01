(function ($) {
	$(document).ready(function () {								
		
		$("#passwordEmailButton").click(function() {					 		
				
			 $.post("passwordEmailPost",									 
					 {
						 emailReset:$("#emailReset").val(),				 	  
					 }, 
					 function (data) {																	 		
				 		 $('#passwordEmailModal').modal('hide');
			 		}, "json"	
			  );
			
			return true;
		});
		
	});
})(jQuery);