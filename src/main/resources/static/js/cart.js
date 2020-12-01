console.log("Cart JS");

var domain = "";

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':' + location.port : ''));
    console.log(location.protocol);
    domain = window.location.hostname + (location.port ? ':' + location.port : '');
    console.log(domain);

    $(document).on('click', '#remove', function () {

        var sessionId = $(this).attr('productId');
        console.log(sessionId);
        removeItem(sessionId);


    });

    $(document).on('click', '#checkoutBtn', function () {

        document.location.href = '/checkout';


    });


});



async function removeItem(itemSessionId) {
    console.log("removing " + itemSessionId);
    var xhr = new XMLHttpRequest();
    xhr.open("GET", location.protocol + "//" + domain + "/api/cart/remove/" + itemSessionId, true);
    xhr.send();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var resText = this.responseText;
            console.log(resText);
            if(resText.includes('Success')){
                location.reload();
            }else{
                document.getElementById("statusText").innerHTML = "Error removing product";
                console.log("Failed removing item");
            }
        };
    }
}