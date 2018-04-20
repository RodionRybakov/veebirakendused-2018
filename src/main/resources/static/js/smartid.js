var polling = true;

$(function () {
	$("#login-smartid").click(function () {
		$("#login-fields").hide();
		$("#loader").show();
		init($("#selectpicker").val(), $("#identityNumber").val());
		// poll();
	});
});

function init(countryCode, identityNumber) {
	$.ajax({
		url: "/smartid/init",
		type: "POST",
		data: {
			countryCode: countryCode,
			identityNumber: identityNumber
		},
		success: function (response) {
			if (response.length > 4) {
				window.location.replace("/smartid?error=Error")
			} else {
				$("#control-code").empty().append("Control code: " + response);
			}
		}
	});
}

function poll() {
	if (polling) {
		window.setTimeout(function () {
			$.ajax({
				url: "/smartid/status",
				type: "POST",
				complete: poll,
				success: function () {
					window.location.replace("/");
				},
				error: function () {
					window.location.replace("/smartid?error=Error");
				}
			});
		}, 2500);
	}
}