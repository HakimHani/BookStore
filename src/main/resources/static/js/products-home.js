console.log("Hello from products-home js");
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

    $(document).on('click', '#bestProduct', function () {

        var pid= $(this).attr('productId')
        console.log(pid);
        window.location.href = "/product/" + pid;

    })



});




























/*var items = [{
        "productId": "abcde01",
        "itemName": "BLACK LOGO HOODIE - SS2020",
        "itemLabel": "BLACK LOGO HOODIE - SS2020",
        "brand": "bookshop",
        "price": 159.99,
        "sizeSku": "01",
        "category": "hoodie",
        "maxCartNumber": 2,
        "releaseDate": "2020",
        "inventory": 0.0,
        "sku": "abcde",
        "salesCount": 0,
        "sizes": "",
        "avaliable": false,
        "instock": false
    },
    {
        "productId": "bbcda01",
        "itemName": "RED LOGO HOODIE - SS2020",
        "itemLabel": "RED LOGO HOODIE - SS2020",
        "brand": "bookshop",
        "price": 159.99,
        "sizeSku": "01",
        "category": "hoodie",
        "maxCartNumber": 2,
        "releaseDate": "2020",
        "inventory": 3.0,
        "sku": "bbcda",
        "salesCount": 0,
        "sizes": "",
        "avaliable": false,
        "instock": false
    },
    {
        "productId": "ddcao01",
        "itemName": "GREY LOGO HOODIE - SS2020",
        "itemLabel": "GREY LOGO HOODIE - SS2020",
        "brand": "bookshop",
        "price": 159.99,
        "sizeSku": "01",
        "category": "hoodie",
        "maxCartNumber": 2,
        "releaseDate": "2020",
        "inventory": 3.0,
        "sku": "ddcao",
        "salesCount": 0,
        "sizes": "",
        "avaliable": false,
        "instock": false
    },
    {
        "productId": "blued00",
        "itemName": "BLUE DRAGON TEE",
        "itemLabel": "BLUE DRAGON TEE S",
        "brand": "NIKE",
        "price": 69.99,
        "sizeSku": "0",
        "category": "TEE",
        "maxCartNumber": 5,
        "releaseDate": "2019",
        "inventory": 15.0,
        "sku": "blued",
        "salesCount": 0,
        "sizes": "S",
        "avaliable": true,
        "instock": true
    },
    {
        "productId": "blued01",
        "itemName": "BLUE DRAGON TEE",
        "itemLabel": "BLUE DRAGON TEE M",
        "brand": "NIKE",
        "price": 69.99,
        "sizeSku": "1",
        "category": "TEE",
        "maxCartNumber": 5,
        "releaseDate": "2019",
        "inventory": 15.0,
        "sku": "blued",
        "salesCount": 0,
        "sizes": "M",
        "avaliable": false,
        "instock": true
    },
    {
        "productId": "blued02",
        "itemName": "BLUE DRAGON TEE",
        "itemLabel": "BLUE DRAGON TEE L",
        "brand": "NIKE",
        "price": 129.99,
        "sizeSku": "2",
        "category": "TEE",
        "maxCartNumber": 2,
        "releaseDate": "2019",
        "inventory": 12.0,
        "sku": "blued",
        "salesCount": 0,
        "sizes": "L",
        "avaliable": true,
        "instock": true
    },
    {
        "productId": "blued03",
        "itemName": "BLUE DRAGON TEE",
        "itemLabel": "BLUE DRAGON TEE XL",
        "brand": "NIKE",
        "price": 129.99,
        "sizeSku": "3",
        "category": "TEE",
        "maxCartNumber": 2,
        "releaseDate": "2019",
        "inventory": 12.0,
        "sku": "blued",
        "salesCount": 0,
        "sizes": "XL",
        "avaliable": false,
        "instock": true
    }
];








document.addEventListener("DOMContentLoaded", function () {
    setupItems(items);
});















async function setupItems(response) {
    var itemRoot = document.getElementById("itemsRoot");
    for (var i = 0; i < response.length; i++) {

        var item = response[i];
        var name = item.itemLabel;
        var brand = item.brand;
        var price = item.price;
        var category = item.category;
        var instock = item.instock;


        var colElement = document.createElement('div');
        colElement.className = 'col-4';

        var imgElement = document.createElement('img');
        imgElement.src = "img/category-" + Math.floor(Math.random() * 10) + ".jpg"
        colElement.append(imgElement);

        var h4Element = document.createElement('h4');
        h4Element.innerHTML = name + "</br>" + category;
        colElement.append(h4Element);

        var h5Element = document.createElement('h5');
        h5Element.innerHTML = brand
        colElement.append(h5Element);

        var ratingElement = document.createElement('div');
        ratingElement.className = 'rating';
        ratingElement.innerHTML = '<i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i>'
        colElement.append(ratingElement);

        var priceElement = document.createElement('p');
        priceElement.innerHTML = "$" + price
        colElement.append(priceElement);

        itemRoot.appendChild(colElement);
        console.log(response[i]);
    }
}


*/