// generate a report with the books sold each month
var domain = window.location.hostname + (location.port ? ':' + location.port : '');

function doAjax(address){
	 var request = new XMLHttpRequest();
	 var month = document.getElementById("monthlyReport").value;
	 var url = "./api/checkout/" + month;
	 request.onreadystatechange = function() {
			handler(request);
	 };
	 request.open("GET", url, true);
	 request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 request.send(); 
} 

function handler(request){
	 if ((request.readyState == 4) && (request.status == 200))
	 {
		 var target = document.getElementById("result");
		 target.innerHTML = request.responseText;
	 }
}

//provide real time analytics (Listeners) with most popular products (life time), like “top 10 sold books
function getBooksStats(){
	var request = new XMLHttpRequest();
	url = "./api/"
	request.onreadystatechange = function(){
		handlerBooksStats(request);
	 };
	 request.open("GEt", ulr, true);
	 request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 request.send(data); 	
}

function handlerBooksStats(request){
	 if ((request.readyState == 4) && (request.status == 200))
	 {
		 var target = document.getElementById("result");
		 var result =  JSON.parse(request.responseText);
		 var table = buildTable(result);
		 table.border = '1';
		 target.innerHTML = "";
		 target.appendChild(table);
	 }
}

function buildTable(result){
	var table = document.createElement("TABLE");
	table.setAttribute("id", "myTable");
 var row = document.createElement("TR");
 var data = document.createElement("TD");
 data.innerHTML = "Book Title";
 row.appendChild(data);
 
 data = document.createElement("TD");
 data.innerHTML = "Book Id";
 row.appendChild(data);
 
 table.appendChild(row);
 
 var i=0;
 for (i=0; i<result.length; i++){
 	
	    row = document.createElement("TR");
	    
	    data = document.createElement("TD");
	    data.innerHTML =result[i].username;
	    row.appendChild(data);
	    
	    data = document.createElement("TD");
	    data.innerHTML = result[i];
	    row.appendChild(data);
	    
	    table.appendChild(row);
 }
 
 return table;
}

function populate_products(){
	 var request = new XMLHttpRequest();
	 request.onreadystatechange = function() {
		 populate_products_handler(request);
	 };
	 request.open("GET","http://localhost:8090" + "/api/iteminfo/", true);
	 request.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
	 request.send(); 
} 

function populate_products_handler(request){
	 if ((request.readyState == 4) && (request.status == 200)) {
		 var target = document.getElementById("products_table");
		 var products = JSON.parse(request.responseText)
		 var table = "";
		 table += '<table>';
		 table += "<tr>";
		 table += "<td>Id</td>";
		 table += "<td>Product</td>";
		 table += "<td>Category</td>";
		 table += "<td>Price</td>";
		 table +="</tr>";
		 
		 for (var product of products) {
			 console.log(product.id)
			 table += "<tr>";
			 table +="<td>" + product.productId + "</td>";
			 table +="<td>" + product.itemName + "</td>";
			 table +="<td>" + product.category + "</td>";
			 table += "<td>" + product.price + "</td>";
		     table += '<td><a type="button" onclick="showProduct(\'' + product.productId + '\' )">' + "Show" + "</a></td>"; 
		     table += '<td><a href = "">' + "Edit" + "</a></td>";
		     table += '<td><a href ="" >' + "Delete" + "</a></td>";
			 table += "</tr>"
		 }
		 target.innerHTML = table;
	 }
}