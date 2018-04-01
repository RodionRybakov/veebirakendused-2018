
// Skrqt i otkrqt menu zagruzki izobrazenija

function showDLG(){
	var dlg = document.getElementById("dlgbox");
	var btn = document.getElementById("accountButton");
	//dlg.style.display = "block";
	if ( $(dlg).css('display') == 'none')
        {
            dlg.style.display = "block";
            btn.textContent="Save and close";
        }
     else{
        dlg.style.display = "none";
        btn.textContent="Click here change image";
     }

}

/////////// Upload image to avatar
function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah')
                    .attr('src', e.target.result)
                    .width(150)
                    .height(150);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }