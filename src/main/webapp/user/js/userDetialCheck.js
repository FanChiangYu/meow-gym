const username = document.querySelector("#username");
const password = document.querySelector("#password");
const cPassword = document.querySelector("#cPassword");
const nickname = document.querySelector("#nickname");

fetch('memberDetialCheck')
	.then(resp => resp.json())
	.then(member => {
		username.value = member.username;
		nickname.value = member.nickname;
	});

document.querySelector("button").addEventListener("click", () => {
	let len = username.value.length;


	len = password.value.length;
	if (password.value && (len < 6 || len > 12)) {
		alert("密碼長度必須介於 6 ~ 12");
		return;
	}

	len = nickname.value.length;
	if (len < 1 || len > 20) {
		alert("暱稱必須介於 1 ~ 20");
		return;
	}

	if (password.value !== cPassword.value) {
		alert("密碼與確認密碼不符合");
		return;
	}

	fetch('memberDetialCheck', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			password: password.value,
			nickname: nickname.value,
		})
	})

		.then(resp => resp.json())
		.then(body => {
			if (body.success) {
				alert(body.nickname);
				alert(body.username);
			} else {
				alert(body.errorMessage);
			}
		});

});