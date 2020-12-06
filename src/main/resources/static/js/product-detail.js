console.log("Hello from product detail js");
var scores = 0;
var domain = "";

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(window.location.hostname + (location.port ? ':' + location.port : ''));
    console.log(location.protocol);
    domain = window.location.hostname + (location.port ? ':' + location.port : '');


    $(document).on('click', '#atc', function () {
        $(this).text("Adding...");
        $(this).prop('disabled', true);
        var productId = $(this).attr('pid')
        console.log(productId);
        atc(productId);

    })

    $(document).on('click', '#product', function () {

        var pid = $(this).attr('productId')
        console.log(pid);
        window.location.href = "/product/" + pid;

    })



    $(document).on('click', '#submitReview', function () {

        var productId = $(this).attr('pid')
        console.log(productId);
        var btn = document.querySelector(".post-btn");
        var post = document.querySelector(".post");
        var widget = document.querySelector(".star-widget");
        var editBtn = document.querySelector(".edit");
        //widget.style.display = "none";
        //post.style.display = "block";
        //var stars = document.querySelectorAll(".stars");
        //console.log(stars);
        var comments = document.getElementById("comments").value;
        console.log(scores);
        console.log(comments);
        var data = {
            productId: productId,
            scores: scores,
            comments: comments
        }
        submitComment(data);

    })

    $(document).on('click', '.edit', function () {
        var widget = document.querySelector(".star-widget");
        var post = document.querySelector(".post");
        widget.style.display = "block";
        post.style.display = "none";

    })

    $(document).on('click', '.stars', function () {
        var startCount = $(this).attr('id');
        console.log(startCount);
        if (startCount == 'rate-1') {
            scores = 1;
        } else if (startCount == 'rate-2') {
            scores = 2;
        } else if (startCount == 'rate-3') {
            scores = 3;
        } else if (startCount == 'rate-4') {
            scores = 4;
        } else if (startCount == 'rate-5') {
            scores = 5;
        }
        //console.log(scores);

    })





});



async function atc(detail) {
    console.log(detail);

    var xhr = new XMLHttpRequest();
    xhr.open("GET", location.protocol + "//" + domain + "/api/cart/atc/" + detail, true);
    xhr.send();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4) {
            var response = this.responseText;
            console.log(response);
            if (this.status == 200) {
                response = JSON.parse(this.responseText);
                console.log(response);
                if (response.status == "Success") {
                    console.log("ITEM ADDED");
                    notifyCartSuccess();
                } else {
                    console.log(response.status + " " + response.message);
                    notifyCartFailed();
                }

            } else {
                console.log("FAILED");
                notifyCartFailed();
            }
        }
    };

}



async function submitComment(detail) {
    console.log(detail);


    var xhr = new XMLHttpRequest();
    xhr.open("POST", location.protocol + "//" + domain + "/api/review/create", true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    var data = {
        "rate": detail.scores,
        "comment": detail.comments,
        "productId": detail.productId
    }
    console.log(data);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (this.readyState == 4) {
            var response = this.responseText;
            console.log(response);
            if (this.status == 200) {
                response = JSON.parse(this.responseText);
                console.log(response);
                if (response.status == "Success") {
                    //location.reload();
                    console.log("successfully submitted review");
                    document.getElementById("reviewStatus").innerHTML = "Review submitted";
                    document.querySelector(".star-widget").style.display = "none";

                } else {
                    displayReviewErrorMessage(response.status + " " + response.message);
                }
            } else {
                displayReviewErrorMessage("Failed saving review");
            }
        }
    };

}

async function displayReviewErrorMessage(msg) {
    console.log("review submission failed");
    document.getElementById("reviewStatus").innerHTML = msg;
}


async function notifyCartSuccess() {

    $("#notify").fadeIn();
    //document.querySelector("#notify").style.display = "block";
    document.querySelector("#notificationLogo").className = "fa fa-check";
    document.querySelector("#notificationLogo").style.color = "green";
    document.querySelector("#notificationText").innerHTML = "Product added to cart";
    await sleep(1000);
    document.querySelector("#notify").style.display = "none";
    $("#atc").text("Add To Cart");
    $("#atc").prop('disabled', false);
}

async function notifyCartFailed() {

    $("#notify").fadeIn();
    //document.querySelector("#notify").style.display = "block";
    document.querySelector("#notificationLogo").className = "fa fa-exclamation";
    document.querySelector("#notificationLogo").style.color = "red";
    document.querySelector("#notificationText").innerHTML = "Error adding to cart";
    await sleep(1000);
    document.querySelector("#notify").style.display = "none";
    $("#atc").text("Add To Cart");
    $("#atc").prop('disabled', false);
}