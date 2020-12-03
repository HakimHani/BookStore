console.log("Products JS!");
var domain = "";
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':'+location.port: ''));
    console.log(location.protocol);
    domain = window.location.hostname + (location.port ? ':'+location.port: '');


    $(document).on('click', '#product', function () {

        var pid= $(this).attr('productId')
        console.log(pid);
        window.location.href = "/product/" + pid;

    })

    
    $(document).on('click', '#navigate', function () {

        var pageId= $(this).attr('pageIndex')
        console.log(pageId);
        window.location.href = "/products/" + pageId;

    })




});
