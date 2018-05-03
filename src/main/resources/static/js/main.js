/**
 * CDN fallback
 */
if (typeof($.fn.modal) === 'undefined') {
	document.write('<script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"><\/script>');
	document.write('<script src="/webjars/bootstrap-select/1.12.4/js/bootstrap-select.min.js"><\/script>');
	document.write('<script src="/webjars/font-awesome/5.0.8/svg-with-js/js/fontawesome-all.min.js"><\/script>');
}

$(function () {
	var bodyColor = $('body').css('color');
	if (bodyColor != 'rgb(51, 51, 51)') {
		$("head").prepend('<link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">');
		$("head").prepend('<link rel="stylesheet" href="/webjars/bootstrap-select/1.12.4/css/bootstrap-select.min.css">');
		$("head").prepend('<link rel="stylesheet" href="/webjars/font-awesome/5.0.8/svg-with-js/css/fa-svg-with-js.css">');
	}
});

/**
 * Map
 */
function map() {
	var mapCanvas = document.getElementById("map");
	var myCenter = new google.maps.LatLng(58.378249, 26.714673);
	var mapOptions = {center: myCenter, zoom: 17};
	var map = new google.maps.Map(mapCanvas, mapOptions);
	var marker = new google.maps.Marker({
		position: myCenter,
		animation: google.maps.Animation.BOUNCE
	});
	marker.setMap(map);
}

/**
 * SignUp
 */
$(function () {
	var pass1 = $("#passwordSignUp");
	var pass2 = $("#passwordConfirmation");
	var goodColor = "#5ab75c";
	var badColor = "#da4f4a";
	
	pass2.on("keyup change", function () {
		if (pass1.val() === pass2.val()) {
			$(this).css({'background-color': goodColor});
		} else {
			$(this).css({'background-color': badColor});
		}
	});
	
	$("#signup-form").submit(function () {
		if (pass1.val() !== pass2.val()) {
			return false;
		}
	});
});

/**
 * Account AJAX
 */
$(function () {
	$(document).on('click', '.ajax-button', function () {
		var workingObject = $(this).parent().parent();
		var offerId = workingObject.attr('id').replace('offer', '');
		
		switch ($(this).attr('id')) {
			case 'offer-view':
				$.ajax({
					type: 'POST',
					url: '/offers/get',
					data: 'offerId=' + offerId,
					success: [
						function (data) {
							var json = $.parseJSON(data);
							$('#offer-preview').empty().append(
								'<div class="col-md-12">' +
								'    <div class="col-md-3 crane-image-block ">' +
								'        <div class="crane-list-img">' +
								'            <img class="img-responsive" src="/img/offer_unknown.png" alt="offer-unknown-image"/>' +
								'        </div>' +
								'    </div>' +
								'    <div class="col-md-7">' +
								'        <div class="crane-list-content">' +
								'            <div class="crane-title">' +
								'                <h3>' + json['title'] + '</h3>' +
								'            </div>' +
								'            <div class="crane-phone">' +
								'                <ul class="list-inline">' +
								'                    <li><i class="fa fa-phone"></i>  <span>' + json['phone'] + '</span></li>' +
								'                </ul>' +
								'            </div>' +
								'            <div class="crane-address">' +
								'                <ul class="list-inline">' +
								'                    <li><i class="fa fa-map-marker"></i></li>' +
								'                    <li>' + json['address'] + '</li>' +
								'                </ul>' +
								'            </div>' +
								'            <div class="crane-category">' +
								'                <!--<ul class="list-inline">-->' +
								'                    <!--<li><a href="#">Cybersecurity</a></li>-->' +
								'                    <!--<li><a href="#">Crane Towing Service</a></li>-->' +
								'                    <!--<li><a href="#">Salary starting from 10 000$</a></li>-->' +
								'                    <!--<li><a href="#">More...</a></li>-->' +
								'                <!--</ul>-->' +
								'                <p title="description">' + json['description'] + '</p>' +
								'            </div>' +
								'        </div>' +
								'    </div>' +
								'</div>'
							);
						}
					]
				});
				break;
			case 'offer-edit':
				break;
			case 'offer-delete':
				$.ajax({
					type: 'POST',
					url: '/offers/remove',
					data: 'offerId=' + offerId,
					success: [
						function () {
							workingObject.remove();
						}
					]
				});
				break;
		}
	});
});

/**
 * Smart-ID
 */
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