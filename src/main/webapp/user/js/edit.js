console.log("1111");
(() => {
	const avatarImg = document.querySelector('#avatarImg');
	const email = document.querySelector("#email");
	const username = document.querySelector("#name");
	const password = document.querySelector("#password");
	const phoneNumber = document.querySelector("#phoneNumber");
	const gender = document.querySelector("#gender");
	const birthday = document.querySelector("#date-birthday");
	const cnt_code = document.querySelector("#cnt_code");
	const dist_code = document.querySelector("#dist_code");
	const detail_address = document.querySelector("#detail_address");
	const applybutton = document.querySelector("#applybutton");
	const logoutBtn = document.querySelector('#logout-btn');


	fetch('/meow-gym/index/loginData')
		.then(resp => resp.json())
		.then(respLoginData => {
			avatarImg.src = respLoginData.user.avatarUrl;
			email.value = respLoginData.user.email;
			username.value = respLoginData.user.name;
			password.value = respLoginData.user.password;
			phoneNumber.value = respLoginData.user.phone;
			gender.value = respLoginData.user.gender;
			birthday.value = respLoginData.user.birthday;
			cnt_code.value = respLoginData.user.cntCode;
			dist_code.value = respLoginData.user.distCode;
			detail_address.value = respLoginData.user.detailAddress;
		});

	userCenterBtn.addEventListener('click', e => {
		e.preventDefault();
		fetch('/meow-gym/index/userCenter')
			.then(resp => resp.json())
			.then(respbody => {
				location.href = respbody.url;
			});
	});

	logoutBtn.addEventListener('click', e => {
		e.preventDefault();
		fetch('/meow-gym/user/logout')
			.then(() => location.href = '/meow-gym/index/index.html');
	});

})();
