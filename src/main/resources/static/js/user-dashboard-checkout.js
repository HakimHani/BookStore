console.log("Hello from user-dashboard js");
var domain = "";
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':'+location.port: ''));
    console.log(location.protocol);
    domain = window.location.hostname + (location.port ? ':'+location.port: '');


    $(document).on('click', '#orderDiv', function () {


        //console.log(itemsArray);
        //console.log(detail);
        if($(this).find("#orderDetail").css("display") == "block"){
            $(this).find("#orderDetail").css("display", "none");
            return
        }else{
            $(this).find("#orderDetail").css("display", "block");
        }
        var checkoutId= $(this).attr('data-checkoutId');
        var itemsArray = $(this).attr('data-itemsArray').replace("[","").replace("]","").split(",");
        console.log(checkoutId);
        var element = $(this).find("#orderDetail")[0];
        console.log("fetch");
        fetchOrderItems(itemsArray,element);



    })






    $(document).on('click', '#deleteAddress', function () {

        var addressId= $(this).parent().attr('productId')
        console.log(addressId);
        
    })

    
    $(document).on('click', '#closeEdit', function () {
        document.getElementById("myModal").style.display = "none";
    })



});



async function fetchOrderItems(detail,element) {
    console.log(detail);
    console.log(element);
    /*
    var xhr = new XMLHttpRequest();
    xhr.open("POST", location.protocol  + "//" + domain + "/api/address/create/" + email, true);
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
    */
}