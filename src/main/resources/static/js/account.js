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
                                     '<center>'+'<h3>'+json['title']+'</h3>'+'</center>'+
								'<br>' +
									'<p id = "offer_style">' +
										'<b>&nbsp;&nbsp;&nbsp;&nbsp; Description: </b> ' + json['description'] + '<br>' +
										'<b>&nbsp;&nbsp;&nbsp;&nbsp; Phone: </b> ' + json['phone'] + '<br>' +
										'<b>&nbsp;&nbsp;&nbsp;&nbsp; Address: </b> ' + json['address'] + '<br>' +
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
/*
$(document).ready(function(){
    $("#offerView").click(function(){
        $("#myModal").modal("show");
    });
        $("#myModal").on('hidden.bs.modal', function () {
    });
});
*/