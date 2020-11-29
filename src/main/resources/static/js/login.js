/*----- js for toggle form -----*/

console.log("JSJSJJSSJ");

document.addEventListener("DOMContentLoaded", function(){
    var LoginForm = document.getElementById("LoginForm");
    var RegForm = document.getElementById("RegForm"); 
    var Indicator = document.getElementById("Indicator");


    document.getElementById("switchLoginForm").onclick = function() {login()};
    document.getElementById("switchRegisterForm").onclick = function() {register()};





  
});



function register(){
  console.log("Register");
  RegForm.style.transform ="translateX(0px)";
  LoginForm.style.transform = "translateX(0px)";
  Indicator.style.transform = "translateX(100px)";
 
}

function login(){
  console.log("Login");
  RegForm.style.transform = "translateX(300px)";
  LoginForm.style.transform = "translateX(300px)";   
  Indicator.style.transform = "translateX(0px)";
 
  
}
    