$(document).ready(function(){
	$.ajax({
    url: 'http://localhost:8080/getTotal',
    method: 'GET',
    contentType: 'application/json',
    success: function(response) {
       document.getElementById('totalAmount').innerHTML= response;
    },
    error: function(xhr, status, error) {
        console.error(status, error);
    }
});

})

document.getElementById('governate').addEventListener('change', function(){
	var value = document.getElementById('governate').value;
	var total = document.getElementById('totalAmount').innerHTML;
	var dc = document.getElementById('deliveryCharge');
	if(value == 1 || value == 4){
		dc.innerHTML = '1';
		var t = parseInt(total) + parseInt(dc.innerHTML);
		document.getElementById('totalAmount').innerHTML= t;
	}
	if(value == 2 || value == 5){
		dc.innerHTML = '2';
		var t = parseInt(total) + parseInt(dc.innerHTML);
		document.getElementById('totalAmount').innerHTML= t;
	}
	if(value ==3 || value ==6){
		dc.innerHTML = '3';
		var t = parseInt(total) + parseInt(dc.innerHTML);
		document.getElementById('totalAmount').innerHTML= t;
		
	}
	
})

document.getElementById('placeOrder').addEventListener('click', function(){
	document.getElementById('fname').classList.remove('d-block');
		document.getElementById('fname').classList.add('d-none');
		document.getElementById('lname').classList.remove('d-block');
		document.getElementById('lname').classList.add('d-none');
		document.getElementById('shipping1').classList.remove('d-block');
		document.getElementById('shipping1').classList.add('d-none');
	var fname = document.getElementById('firstName').value;
	var lname = document.getElementById('lastName').value;
	var email = document.getElementById('email').value;
	var address = document.getElementById('address').value;
	var address2 = document.getElementById('address2').value;
	var governate = document.getElementById('governate').value;
	var cod = "Cash On Delivery";
	var dc = document.getElementById('deliveryCharge').innerHTML;
	var totalAmt = document.getElementById('totalAmount').innerHTML;
	
	if(fname === ''){
		document.getElementById('fname').classList.remove('d-none');
		document.getElementById('fname').classList.add('d-block');
	}else if(lname == ''){
		document.getElementById('lname').classList.remove('d-none');
		document.getElementById('lname').classList.add('d-block');
	}else if(address == ''){
		document.getElementById('shipping1').classList.remove('d-none');
		document.getElementById('shipping1').classList.add('d-block');
	}else if(governate == ''){
		document.getElementById('governateError').classList.remove('d-none');
		document.getElementById('governateError').classList.add('d-block');
	}else{
		
		var OrderDto = {
		fname:fname,
		lname:lname,
		email: email,
		address1: address,
		address2: address2,
		governate: governate,
		mop: cod,
		deliveryCharge: dc,
		totalAmount:totalAmt
	}
	
	
	
	$.ajax({
    url: 'http://localhost:8080/placeOrder',
    method: 'POST',
    data: JSON.stringify(OrderDto),
    dataType: 'json',
    contentType: 'application/json',
    success: function(response) {
        if(response.code == 200){
			window.location.href = "http://localhost:8080/order?id=" + response.id;
		}else{
			console.log("error");
		}
    },
    error: function(xhr, status, error) {
        console.error(status, error);
    }
});
	}
	
	
});

function getProducts(){
	
}