const reg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

const email = document.querySelector("#email");
const username = document.querySelector("#name");
const password = document.querySelector("#password");
const confirmPassword = document.querySelector("#confirmPassword");
const phoneNumber = document.querySelector("#phoneNumber");
const gender = document.querySelector("#gender");
const birthday = document.querySelector("#date-birthday");
const cnt_code = document.querySelector("#cnt_code");
const dist_code = document.querySelector("#dist_code");
const detail_address = document.querySelector("#detail_address");
const applybutton = document.querySelector("#applybutton");

function valueOrNull(value) {
	if (value === undefined || value === null || value === '' || Number.isNaN(value)) {
		return null;
	} else {
		return value;
	}
}

email.addEventListener('blur', function () {
	if (email.value.match(reg) === null) {
		alert('帳號格式不正確'); //
	}
})



applybutton.addEventListener('click', function () {


	if (valueOrNull(username.value) == null) {
		alert('姓名未輸入');
		return;
	}

	if (valueOrNull(password.value) == null) {
		alert('密碼未輸入');
		return;
	}

	if (valueOrNull(confirmPassword.value) == null) {
		alert('欄位未輸入');
		return;
	}

	if (valueOrNull(phoneNumber.value) == null) {
		alert('欄位必填');
		return;
	}

	if (valueOrNull(gender.value) == null) {
		alert('請選擇性別');
		return;
	}

	if (valueOrNull(birthday.value) == null) {
		alert('生日為必填欄位');
		return;
	}

	if (valueOrNull(cnt_code.value) == null) {
		alert('請選擇縣市');
		return;
	}

	if (valueOrNull(dist_code.value) == null) {
		alert('請選擇鄉鎮');
		return;
	}

	if (valueOrNull(detail_address.value) == null) {
		alert('地址為必填欄位');
		return;
	}

	fetch('register', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({
			cntCode: cnt_code.value,
			distCode: password.value,
			password: password.value,
			password: password.value,
			password: password.value,
			password: password.value,
			password: password.value,
			password: password.value,
			password: password.value,
			password: password.value,
			password: password.value,
			password: password.value,
		}),
	})


});





