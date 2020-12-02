console.log("Hello from user-dashboard js");
var domain = "";

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':' + location.port : ''));
    console.log(location.protocol);
    domain = window.location.hostname + (location.port ? ':' + location.port : '');

    var addresses = document.getElementById("addrInfo").innerHTML;
    //console.log(addresses);



    $(document).on('click', '#editAddress', function () {

        var addressId = $(this).parent().attr('productId')
        console.log(addressId);
        document.getElementById("myModal").style.display = "block";
        document.getElementById("saveAddress").setAttribute("addressId", addressId);

        document.getElementById("fName").value = $(this).attr('data-firstName');
        document.getElementById("lName").value = $(this).attr('data-lastName');
        document.getElementById("addressOne").value = $(this).attr('data-addressOne');
        document.getElementById("addressTwo").value = $(this).attr('data-addressTwo');
        document.getElementById("city").value = $(this).attr('data-city');
        document.getElementById("country").value = $(this).attr('data-country');
        document.getElementById("state").value = $(this).attr('data-state');
        document.getElementById("postal").value = $(this).attr('data-postal');
        document.getElementById("phone").valu = $(this).attr('data-phone');


        console.log("displayed");
    })



    $(document).on('click', '#saveAddress', function () {

        var addressId = $(this).attr('addressId');
        var nAddress = {
            "email": document.getElementById("userEmail").innerHTML,
            "firstName": document.getElementById("fName").value,
            "lastName": document.getElementById("lName").value,
            "addressOne": document.getElementById("addressOne").value,
            "addressTwo": document.getElementById("addressTwo").value,
            "city": document.getElementById("city").value,
            "country": document.getElementById("country").value,
            "state": document.getElementById("state").value,
            "postal": document.getElementById("postal").value,
            "phone": document.getElementById("phone").value
        };
        console.log(nAddress);
        console.log("Updating address " + addressId);


        uploadAddress(addressId, nAddress);

        /*
        {
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
        */

    })




    $(document).on('click', '#deleteAddress', function () {

        var addressId = $(this).parent().attr('productId')
        console.log(addressId);

    })


    $(document).on('click', '#closeEdit', function () {
        document.getElementById("fName").value = "";
        document.getElementById("lName").value = "";
        document.getElementById("addressOne").value = "";
        document.getElementById("addressTwo").value = "";
        document.getElementById("city").value = "";
        document.getElementById("country").value = "";
        document.getElementById("state").value = "";
        document.getElementById("postal").value = "";
        document.getElementById("phone").valu = "";
        document.getElementById("myModal").style.display = "none";
    })



});



async function uploadAddress(addressId, newAddress) {

    var xhr = new XMLHttpRequest();
    xhr.open("PUT", location.protocol + "//" + domain + "/api/address/modify/" + addressId, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    var data = newAddress;

    console.log(data);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            try {
                cAddr = JSON.parse(this.responseText)
                document.getElementById("fName").value = "";
                document.getElementById("lName").value = "";
                document.getElementById("addressOne").value = "";
                document.getElementById("addressTwo").value = "";
                document.getElementById("city").value = "";
                document.getElementById("country").value = "";
                document.getElementById("state").value = "";
                document.getElementById("postal").value = "";
                document.getElementById("phone").valu = "";
                location.reload();
            } catch (err) {
                console.log("error updating address");
            }


        }
    };
}