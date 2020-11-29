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
}