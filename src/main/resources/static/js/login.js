

function login(){
	
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	
	console.log(username,password);
	
	var LoginRequestDTO = {
		username:username,
		password:password
	}
	
	$.ajax({
    url: 'http://localhost:8080/login',
    method: 'POST',
    data: JSON.stringify(LoginRequestDTO),
    dataType: 'json',
    contentType: 'application/json',
    success: function(response) {
        if(response.code == 200){
			window.location.href = "http://localhost:8080/admin";
		}else{
			console.log("error");
		}
    },
    error: function(xhr, status, error) {
        console.error(status, error);
    }
});
	
}

function logout(){
	
	$.ajax({
    url: 'http://localhost:8080/logout',
    method: 'POST',
    success: function(response) {
        if(response.code == 200){
			window.location.href = "http://localhost:8080";
		}else{
			console.log("error");
		}
    },
    error: function(xhr, status, error) {
        console.error(status, error);
    }
});
	
}