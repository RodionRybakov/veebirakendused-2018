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