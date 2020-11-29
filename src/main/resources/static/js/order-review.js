console.log("review page js..");
var domain = "";

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':' + location.port : ''));
    console.log(location.protocol);
    domain = window.location.hostname + (location.port ? ':' + location.port : '');



    $(document).on('click', '#backToBilling', function () {
        window.location.href = location.protocol + "//" + domain + "/checkout/billing";
    })


    $(document).on('click', '#submitOrder', async function () {
        console.log("Clicked submit order");
        var checkoutId = document.getElementById("checkoutInfo").getAttribute("data-checkoutid");
        submitOrder(checkoutId);
    })


});


async function submitOrder(checkoutId){
    console.log("submitting...");

    var xhr = new XMLHttpRequest();
    xhr.open("PUT", location.protocol + "//" + domain + "/api/checkout/processing/" + checkoutId, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    var data = {
        "checkoutId": checkoutId,
    }
    console.log(data);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            var checkoutResult = JSON.parse(this.responseText);
            if(checkoutResult.checkoutState == "PROCESSING_BILLING"){
                console.log("Proceeding to order result page");
                window.location.href = location.protocol + "//" + domain + "/checkout/success";
            }else{
                console.log("Order failed or payment declined");
            }
        }
    };
}
