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
							$('#offerPreview').empty();
							$('#offerPreview').append(
								'<br><br>' +
									'<p>' +
										'<b>Description: </b> ' + json['description'] + '<br>' +
										'<b>Phone: </b> ' + json['phone'] + '<br>' +
										'<b>Address: </b> ' + json['address'] + '<br>' +
									'</p>'
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