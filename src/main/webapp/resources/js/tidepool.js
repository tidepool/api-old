(function ($) {
	$(document).ready(function () {								
		
		$("#tidepoolButton").click(function() {
			var div = document.createElement("div");
			div.style.width = "550px";
			div.style.height = "500px";
			div.style.zindex = "10000";
			div.style.position = "absolute";
			div.style.top =  "100px";
			div.style.left = window.innerWidth / 8  + "px";
			div.style.color = "white";
			div.innerHTML = "<div  id='tidepoolIFrame' style='background-color: #ccc;padding:20px;margin:-25px'><div style='position:relative;top:-12px;left:549px;font-size:16px;color:#000'><a onclick='javascript:var iframe = document.getElementById(\"tidepoolIFrame\"); iframe.parentNode.removeChild(iframe);'>x</a></div><iframe height='500px' width='550px' src='//code.tidepool.co/assess10'></iframe></div>";	
			document.body.appendChild(div);
		});
		
	});
})(jQuery);		