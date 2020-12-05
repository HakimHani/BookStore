console.log("Billing page js..");
var domain = "";

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':' + location.port : ''));
    console.log(location.protocol);
    domain = window.location.hostname + (location.port ? ':' + location.port : '');



    $(document).on('click', '#backToShipping', function () {
        window.location.href = location.protocol + "//" + domain + "/checkout/shipping";
    })


    $(document).on('click', '#submitBilling', async function () {
        console.log("Clicked submit billing");
        var checkoutId = document.getElementById("checkoutInfo").getAttribute("data-checkoutid");
        createBilling(checkoutId);
    })


});





async function createBilling(checkoutId) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", location.protocol + "//" + domain + "/api/billing/create", true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    var data = {
        "cardType": "2",
        "firstName": document.getElementById("cname").value.split(' ')[0],
        "lastName": document.getElementById("cname").value.split(' ')[1],
        "cardNumber": document.getElementById("ccnum").value,
        "expMonth": document.getElementById("expmonth").value,
        "expDate": document.getElementById("expyear").value,
        "ccv": document.getElementById("cvv").value,
        "postal": document.getElementById("postal").value,
    }
    console.log(data);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (this.readyState == 4) {
            var response = this.responseText;
            console.log(response);
            if (this.status == 200) {
                response = JSON.parse(this.responseText);
                if (response.status == "Success") {
                    responseBilling = response.item;
                    console.log(responseBilling);
                    uploadBilling(checkoutId, responseBilling.billingId);
                } else {
                    console.log(response.status + " " + response.message);
                }

            } else {
                console.log("FAILED");
            }
        }
    };
}







async function uploadBilling(checkoutId, billingId) {
    console.log("sending shipping");

    var xhr = new XMLHttpRequest();
    xhr.open("PUT", location.protocol + "//" + domain + "/api/checkout/billing/" + checkoutId, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    var data = {
        "billingId": billingId
    }
    console.log(data);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (this.readyState == 4) {
            var response = this.responseText;
            console.log(response);
            if (this.status == 200) {
                response = JSON.parse(this.responseText);
                console.log(response);
                if (response.status == "Success") {
                    console.log("Proceeding to review order");
                    window.location.href = location.protocol + "//" + domain + "/checkout/review";
                } else {
                    console.log(response.status + " " + response.message);
                }
            }else{
                console.log("FAILED");
            }
        }
    };
}