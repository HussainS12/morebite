function addProduct(id){
	console.log(id);
	$.ajax({
    url: 'http://localhost:8080/addProduct?id='+id,
    method: 'POST',
    dataType: 'json',
    success: function(response) {
        document.getElementById('addToCartIcon').classList.remove('d-none');
        document.getElementById('addToCartIcon').classList.remove('d-block');
    },
    error: function(xhr, status, error) {
        console.error(status, error);
    }
    });
}