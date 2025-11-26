const email = document.querySelector("#email");
const password = document.querySelector("#password");
const loginbt = document.querySelector("#loginbt");

loginbt.addEventListener("click", () => {
	if (!email.value) {
		Swal.fire({
			title: '錯誤',
			text: '使⽤者名稱不得為空白',
			icon: 'error',
			target: document.body
		});
		return;
	}

	if (!password.value) {
		Swal.fire({
			title: '錯誤',
			text: '密碼不得為空白',
			icon: 'error',
			target: document.body
		});
		return;
	}

	fetch('login', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({
			email: email.value,
			password: password.value,
		}),
	})
		.then(resp => resp.json())
		.then(body => {
			if (body.successful) {
				location.href = "/meow-gym/index/index.html";
			} else {
				Swal.fire({
					title: '錯誤',
					text: '使用者名稱或密碼錯誤',
					icon: 'error',
					target: document.body
				});
			}
		});

});

