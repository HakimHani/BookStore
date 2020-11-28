console.log("Shipping page js..");

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    checkSavedShipping();
});

async function checkSavedShipping() {

    var addrs = await doSimpleAjax("GET", "https://eshop-bookshop.herokuapp.com/api/address/EmilyClock@gmail.com", false, "");

    /*
    await sleep(2500);
    document.getElementById("savedAddressDiv").style.display = "none";
    document.getElementById("shippingDiv").style.display = "block";
    */
}

function doSimpleAjax(type, url, isParam, data) {
    console.log(url);
    var request = new XMLHttpRequest();
    if (isParam) {
        request.open(type, (url + "?" + data), true);
    } else {
        request.open(type, url, true);
    }
    request.onreadystatechange = async function () {
        if ((request.readyState == 4) && (request.status == 200)) {
            console.log(request.responseText);
            return request.responseText;
        }
    };
    request.send(null);
}

/*
		var data = "";
		data += "surname=" + document.getElementById("surname").value +
			"&";
		data += "givenname=" + document.getElementById("givenname").value +
			"&";
		data += "sid=" + document.getElementById("sid").value +
			"&";
        data += "addStudentReq=true";
        */