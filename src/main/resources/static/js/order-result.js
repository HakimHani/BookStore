console.log("order result page js..");
var domain = "";

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}


document.addEventListener("DOMContentLoaded", function () {


    notifyCheckoutInputError("Your checkout is confirnmed!");


})




async function notifyCheckoutInputError(msg) {
    $("#notify").fadeIn();
    document.querySelector("#notificationLogo").className = "fa fa-check";
    document.querySelector("#notificationLogo").style.color = "green";
    document.querySelector("#notificationText").innerHTML = msg;
    await sleep(8000);
    document.querySelector("#notify").style.display = "none";
}

