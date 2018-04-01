
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

/* Upload image to avatar */
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

/*
//Add job
function addAdv() {
    var node = document.createElement("LI");
    //var textnode = document.createTextNode("Water");
    "${location}".textContent("awd");
    var textnode = document.createTextNode("${location}");

    node.appendChild(textnode);
    document.getElementById("JobList").appendChild(node);
}
*/
/*
// Add job
function addAdv() {
 var element = document.getElementById("location");
    var node = document.createElement("LI");
    var y = document.getElementById("loc");
    var location = document.createTextNode(y.value);
   	var hr = document.createElement("HR");
    var br = document.createElement("BR")
	var z =  document.getElementById("description");
    var desc = document.createTextNode(z.value)

    var x =document.getElementById("type_of_job");
    var head =document.createTextNode(x.value);


if(location.length != 0 && head.length != 0 && desc.length != 0) {
    node.classList.add("mystyle")
    //node.appendChild(location);
    //node.appendChild(br);
    node.appendChild(head);
    node.appendChild(br);
    node.appendChild(desc);


    document.getElementById("JobList").appendChild(node);
    node.appendChild(hr);

   		// do something
	}
}
*/