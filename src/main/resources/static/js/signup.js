$(function () {
	var pass1 = $("#passwordSignUp");
	var pass2 = $("#passwordConfirmation");
	var goodColor = "#5ab75c";
	var badColor = "#da4f4a";
	
	pass2.on("keyup change", function () {
		if (pass1.val() === pass2.val()) {
			$(this).css({'background-color' : goodColor});
		} else {
			$(this).css({'background-color' : badColor});
		}
	});
	
	$("form").submit(function () {
		if (pass1.val() !== pass2.val()) {
			return false;
		}
	});
});