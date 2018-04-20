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
