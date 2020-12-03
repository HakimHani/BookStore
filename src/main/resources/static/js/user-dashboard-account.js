console.log("Hello from user-account js");
var domain = "";

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

var baseInfo = {
    "firstName": "",
    "lastName": "",
    "phone": ""
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':' + location.port : ''));
    console.log(location.protocol);
    domain = window.location.hostname + (location.port ? ':' + location.port : '');

    baseInfo.firstName = document.getElementById("firstName").value;
    baseInfo.lastName = document.getElementById("lastName").value;
    baseInfo.phone = document.getElementById("phone").value;

    $(document).on('click', '#showSecurity', function () {

        if ($("#securityInfo").css("display") == "block") {
            $("#securityInfo").css("display", "none");
            return
        } else {
            $("#securityInfo").css("display", "block");
        }

    })


    $(document).on('click', '#modifyInfo', function () {

        var userId = $(this).attr('userId');
        console.log("user id " + userId);

        var isInfoSame = (baseInfo.firstName == document.getElementById("firstName").value) &&
            (baseInfo.lastName == document.getElementById("lastName").value) &&
            (baseInfo.phone == document.getElementById("phone").value);

        if (!isInfoSame) {
            console.log("Updating basic info");
            var addrChangeBody = {
                "firstName": document.getElementById("firstName").value,
                "lastName": document.getElementById("lastName").value,
                "phone": document.getElementById("phone").value
            };
            console.log(addrChangeBody);
            updateBasicInfo(addrChangeBody);

        } else {
            console.log("basic info not changed");
            checkSecurity();
        }

    })



});

async function checkSecurity() {
    var isEditingSecuity = $("#securityInfo").css("display") == "block";
    console.log("Editing pass = " + isEditingSecuity);
    if (isEditingSecuity) {
        console.log("Updating security info");
        var securChangeBody = {
            "oldPassword": document.getElementById("oldPassword").value,
            "password": document.getElementById("password").value
        };
        console.log(securChangeBody);
        updateSecurity(securChangeBody);
    }
}



async function updateBasicInfo(detail) {
    //console.log(detail);

    var xhr = new XMLHttpRequest();
    xhr.open("PUT", location.protocol + "//" + domain + "/api/users/update/", true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    var data = detail;
    console.log(data);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            if (this.responseText.includes("Success")) {
                console.log("update success");
                if ($("#securityInfo").css("display") != "block") {
                    location.reload();
                } else {
                    checkSecurity();
                }



            } else {
                console.log("update failed");
            }
        }
    };

}



async function updateSecurity(detail) {
    console.log(detail);

    var xhr = new XMLHttpRequest();
    xhr.open("PUT", location.protocol + "//" + domain + "/api/account/update", true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    var data = detail;
    console.log(data);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            if (this.responseText.includes("Success")) {
                console.log("update success");
                location.reload();
            } else {
                console.log("update failed");
            }
        }
    };

}