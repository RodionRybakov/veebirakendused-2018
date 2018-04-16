$(function () {
	$(document).on('click', 'a', function () {
		var workingObject = $(this).parent().parent();
		var offerId = workingObject.attr('id').replace('offer', '');
		
		switch ($(this).attr('id')) {
			case 'offerView':
				$.ajax({
					type: 'POST',
					url: '/offers/get',
					data: 'offerId=' + offerId,
					success: [

						function (data) {
							var json = $.parseJSON(data);
							$('#offerPreview').empty().append(
								'<div class="col-md-12">\n' +
								'\t<div class="col-md-3 crane-image-block ">\n' +
								'\t\t<div class="crane-list-img">\n' +
								'\t\t\t<img class="img-responsive" src="/img/offer_unknown.png"/>\n' +
								'\t\t</div>\n' +
								'\t</div>\n' +
								'\t<div class="col-md-7">\n' +
								'\t\t<div class="crane-list-content">\n' +
								'\t\t\t<div class="crane-title">\n' +
								'\t\t\t\t<h3>' + json['title'] + '</h3>\n' +
								'\t\t\t</div>\n' +
								'\t\t\t<div class="crane-phone">\n' +
								'\t\t\t\t<ul class="list-inline">\n' +
								'\t\t\t\t\t<li><i class="fa fa-phone"></i>  <span>' + json['phone'] + '</span></li>\n' +
								'\t\t\t\t</ul>\n' +
								'\t\t\t</div>\n' +
								'\t\t\t<div class="crane-address">\n' +
								'\t\t\t\t<ul class="list-inline">\n' +
								'\t\t\t\t\t<li><i class="fa fa-map-marker"></i></li>\n' +
								'\t\t\t\t\t<li>' + json['address'] + '</li>\n' +
								'\t\t\t\t</ul>\n' +
								'\t\t\t</div>\n' +
								'\t\t\t<div class="crane-category">\n' +
								'\t\t\t\t<!--<ul class="list-inline">-->\n' +
								'\t\t\t\t\t<!--<li><a href="#">Cybersecurity</a></li>-->\n' +
								'\t\t\t\t\t<!--<li><a href="#">Crane Towing Service</a></li>-->\n' +
								'\t\t\t\t\t<!--<li><a href="#">Salary starting from 10 000$</a></li>-->\n' +
								'\t\t\t\t\t<!--<li><a href="#">More...</a></li>-->\n' +
								'\t\t\t\t<!--</ul>-->\n' +
								'\t\t\t\t<p title="description">' + json['description'] + '</p>\n' +
								'\t\t\t</div>\n' +
								'\t\t</div>\n' +
								'\t</div>\n' +
								'</div>'
							);
						}

					]
				});
				break;
			case 'offerEdit':
				break;
			case 'offerDelete':
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
