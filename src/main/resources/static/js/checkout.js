console.log("Shipping page js..");
var domain = "";
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':'+location.port: ''));
    domain = window.location.hostname + (location.port ? ':'+location.port: '');
    checkSavedShipping();

    $(document).on('click', '#pickAddrBtn', function () {

        console.log(JSON.parse($(this).attr('detail')));
        var addr = JSON.parse($(this).attr('detail'));
        document.getElementById("fname").value = addr.firstName + " " + addr.lastName;
        document.getElementById("email").value = addr.email;
        document.getElementById("adr").value = addr.addressOne;
        document.getElementById("city").value = addr.city;
        document.getElementById("state").value = addr.state;
        document.getElementById("zip").value = addr.postal;

        displayNewAddress(true, true);
        document.getElementById("submitShipping").setAttribute("addressId", addr.addressId);

    })


    $(document).on('click', '#savedAddress', function () {
        displaySavedAddress();
    })


    $(document).on('click', '#newAddress', function () {
        displayNewAddress(true, false);
    })

    $(document).on('click', '#submitShipping', async function () {
        var addressId = $(this).attr('addressid');
        var email = document.getElementById("checkoutInfo").getAttribute("data-customerEmail");
        var isGuest = document.getElementById("checkoutInfo").getAttribute("data-guest");
        var checkoutId = document.getElementById("checkoutInfo").getAttribute("data-checkoutId");
        if (addressId == "new") {
            await uploadAddress(isGuest,email,checkoutId);
        } else {
            uploadShipping(email,checkoutId,addressId);
            console.log("use saved address");
        }
    })



});


async function uploadAddress(isGuest, clientEmail,checkoutId) {
    var email = document.getElementById("email").value;
    if (!isGuest) {
        email = clientEmail;
    }
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://" + domain + "/api/address/create/" + email, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    var data = {
        "email": email,
        "firstName": document.getElementById("fname").value.split(' ')[0],
        "lastName": document.getElementById("fname").value.split(' ')[1],
        "addressOne": document.getElementById("adr").value,
        "addressTwo": "none",
        "city": document.getElementById("city").value,
        "country": "Canada",
        "state": document.getElementById("state").value,
        "postal": document.getElementById("zip").value,
        "phone": "none"
    }
    console.log(data);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            cAddr = JSON.parse(this.responseText)
            uploadShipping(email,checkoutId,cAddr.addressId);
        }
    };
}

async function uploadShipping(clientEmail,checkoutId,addressId) {
    console.log("sending shipping");
    email = clientEmail;
 
    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "http://" + domain + "/api/checkout/shipping/" + checkoutId, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    var data = {
        "email": email,
        "addressId": addressId,
 
    }
    console.log(data);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            var checkoutResult = JSON.parse(this.responseText);
            if(checkoutResult.checkoutState == "SHIPPING_INFO"){
                console.log("Proceeding to billing");
            }
        }
    };
}


async function checkSavedShipping() {
    //var addrs = await doSimpleAjax("GET", "https://eshop-bookshop.herokuapp.com/api/address/EmilyClock@gmail.com", false, "");
    var isGuest = document.getElementById("checkoutInfo").getAttribute("data-guest");
    if (isGuest == "true") {
        displayNewAddress(false, false);
    } else {
        var email = document.getElementById("checkoutInfo").getAttribute("data-customerEmail");
        var addrs = await getReq("http://" + domain + "/api/address/" + email);
    }
}

async function getReq(url) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            //return this.responseText;
            formatSavedAddress(this.responseText);
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}


async function formatSavedAddress(savedAddress) {
    document.getElementById("savedAddressLoadingStatus").style.display = "none";
    var addrs = JSON.parse(savedAddress);
    if (addrs.length == 0) {
        displayNewAddress(false, false);
    }
    for (var i = 0; i < addrs.length; i++) {
        console.log(addrs[i].addressOne);
        var addrLi = document.createElement("div");
        var addrDiv = document.createElement("div");
        addrDiv.setAttribute('id', "savedAddr");
        addrDiv.setAttribute('detail', JSON.stringify(addrs[i]));
        addrDiv.innerHTML = addrs[i].addressOne + "<button id='pickAddrBtn' detail='" + JSON.stringify(addrs[i]) + "'>Use</button>";
        addrLi.appendChild(addrDiv);
        document.getElementById("savedAddressList").appendChild(addrLi);
    }
}





function displayNewAddress(isShowSavedBtn, isLockInput) {
    if (isShowSavedBtn) {
        document.getElementById("savedAddress").style.display = "block";
    } else {
        document.getElementById("savedAddress").style.display = "none";
    }

    document.getElementById("submitShipping").setAttribute("addressId", "new");
    document.getElementById("savedAddressDiv").style.display = "none";
    document.getElementById("shippingDiv").style.display = "block";
    document.getElementById("fname").readOnly = false;
    document.getElementById("email").readOnly = false;
    document.getElementById("adr").readOnly = false;
    document.getElementById("city").readOnly = false;
    document.getElementById("state").readOnly = false;
    document.getElementById("zip").readOnly = false;

    document.getElementById("fname").readOnly = isLockInput;
    document.getElementById("email").readOnly = isLockInput;
    document.getElementById("adr").readOnly = isLockInput;
    document.getElementById("city").readOnly = isLockInput;
    document.getElementById("state").readOnly = isLockInput;
    document.getElementById("zip").readOnly = isLockInput;
}

function displaySavedAddress() {
    document.getElementById("savedAddressDiv").style.display = "block";
    document.getElementById("shippingDiv").style.display = "none";
    document.getElementById("savedAddress").style.display = "none";
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