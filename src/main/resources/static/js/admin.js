
// generate a report with the books sold each month
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

// provide real time analytics (Listeners) with most popular products (life time), like “top 10 sold books
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

// create table function to render most selling books