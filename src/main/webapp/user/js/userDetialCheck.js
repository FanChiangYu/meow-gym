
const reg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

const email = document.querySelector("#email");

email.addEventListener('blur', function(){
	
	if(email.value.match(reg) === null){
		alert('error'); //
	}else{
		alert('finish');
	}
	; //時機
})