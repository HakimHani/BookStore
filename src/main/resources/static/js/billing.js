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





});