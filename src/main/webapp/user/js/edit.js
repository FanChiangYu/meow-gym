const avatarImg2 = document.querySelector("#avatarImg");
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
const logoutBtn2 = document.querySelector('#logout-btn');


fetch('/meow-gym/index/loginData')
	.then(resp => resp.json())
	.then(respLoginData => {
		userId  = respLoginData.user.userId;
		avatarImg2.src = respLoginData.user.avatarUrl;
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

logoutBtn2.addEventListener('click', e => {
	e.preventDefault();
	fetch('/meow-gym/user/logout')
		.then(() => location.href = '/meow-gym/index/index.html');
});

// function checkOldPassword() {
// 	fetch(`edit/${oPassword.value}`)
// 		.then(resp => resp.json())
// 		.then(body => {
// 			btn1.disabled = !body['successful']
// 		});
// }

function valueOrNull(value) {
	if (value === undefined || value === null || value === '' || Number.isNaN(value)) {
		return null;
	} else {
		return value;
	}
}

function editCheck(value) {
	if (valueOrNull(username.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '姓名未輸入',
			icon: 'error',
			target: document.body
		});
		return;
	}

	if (valueOrNull(password.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '密碼未輸入',
			icon: 'error',
			target: document.body
		});
		return;
	}

	if (valueOrNull(phoneNumber.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '欄位必填',
			icon: 'error',
			target: document.body
		});
		return;
	}

	if (valueOrNull(gender.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '請選擇性別',
			icon: 'error',
			target: document.body
		});
		return;
	}

	if (valueOrNull(birthday.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '生日為必填欄位',
			icon: 'error',
			target: document.body
		});
		return;
	}

	if (valueOrNull(cnt_code.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '請選擇縣市',
			icon: 'error',
			target: document.body
		});
		return;
	}

	if (valueOrNull(dist_code.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '請選擇鄉鎮',
			icon: 'error',
			target: document.body
		});
		return;
	}

	if (valueOrNull(detail_address.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '地址為必填欄位',
			icon: 'error',
			target: document.body
		});
		return;
	}

	return value;
}

applybutton.addEventListener('click', e => {

	editCheck();

	fetch('edit', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({
			avatarImg: avatarImg2.src,
			email: email.value,
			name: username.value,
			password: password.value,
			phone: phoneNumber.value,
			gender: gender.value,
			birthday: birthday.value,
			cntCode: cnt_code.value,
			distCode: dist_code.value,
			detailAddress: detail_address.value
		}),
	})

		.then(resp => resp.json())
		.then(body => {
			location.reload() = body;
		});
});

let distDate = null;

fetch('dist')
	.then(resp => resp.json())
	.then(body => {
		distDate = body;
		cnt_code.innerHTML = '<option value="">選擇縣市</option>';

		let cntOption = '';
		body.countryList.forEach(country => {
			cntOption += `<option value="${country.cntCode}">${country.cntName}</option>`;
		});
		cnt_code.innerHTML += cntOption;
		$('#cnt_code').trigger('change.select2');

	});

$('#cnt_code').on('change', function () {
	dist_code.innerHTML = '<option value="">選擇鄉鎮</option>';

	if (!this.value) {
		$('#dist_code').trigger('change.select2');
		return;
	}

	var distOption = '';
	distDate.distList.forEach(dist => {
		if (dist.cntCode === Number(this.value)) {
			distOption += `<option value="${dist.distCode}">${dist.distName}</option>`;
		}
	});
	dist_code.innerHTML += distOption;
	$('#dist_code').trigger('change.select2');

});
