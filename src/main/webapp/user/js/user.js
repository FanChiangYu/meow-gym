const email = document.querySelector("#email");
const password = document.querySelector("#password");
const loginbt = document.querySelector("#loginbt");

document.querySelector("button").addEventListener("click", () => {
	if (!email.value) {
		alert("使⽤者名稱不得為空白");
		return;
	}

	if (!password.value) {
		alert("密碼不得為空白");
		return;
	}

	fetch('login', {
		method: 'POST',
		headers: {'Content-Type': 'application/json' },
		body: JSON.stringify({
			email: email.value,
			password: password.value,
		}),
	})
		.then(resp => resp.json())
		.then(body => {
			if (body.successful) {
				location.href = "userDetial.html"
			} else {
				alert("使用者名稱或密碼錯誤");
			}
		});

});