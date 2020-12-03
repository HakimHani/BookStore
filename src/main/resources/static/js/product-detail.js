console.log("Hello from product detail js");
var domain = "";
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':'+location.port: ''));
    console.log(location.protocol);
    domain = window.location.hostname + (location.port ? ':'+location.port: '');


    $(document).on('click', '#atc', function () {

        var productId = $(this).attr('pid')
        console.log(productId);
        atc(productId);

    })

    $(document).on('click', '#product', function () {

        var pid= $(this).attr('productId')
        console.log(pid);
        window.location.href = "/product/" + pid;

    })


});



async function atc(detail) {
    console.log(detail);
    
    var xhr = new XMLHttpRequest();
    xhr.open("GET", location.protocol  + "//" + domain + "/api/cart/atc/" + detail, true);
    xhr.send();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            if(this.responseText.includes("Success")){
                location.reload();
            }

        }
    };
    
}