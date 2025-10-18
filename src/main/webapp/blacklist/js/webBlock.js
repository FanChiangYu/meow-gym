const email = document.querySelector("#email");
const nameInput = document.querySelector("#nameInput");
const block = document.querySelector("#block");

document.querySelector('button').addEventListener('click', function(){
   if (!email.value) {
		alert("信箱不允許空白!!");
		return;
	}
     if (!nameEnter.value) {
		alert("名稱不允許空白!!");
		return;
	}
	
	
	
    fetch('webBlock', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			email: email.value,
			nameInput: nameInput.value,
		})
	})
		.then(resp => resp.json())
		.then(body => {
			if (body.success) {
				location.href = "showList.html"
			} else {
				alert("您輸入的信箱/名稱不是該會員，請在確認一次");
			}
		});


});