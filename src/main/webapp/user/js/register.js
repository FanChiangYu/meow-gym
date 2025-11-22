const reg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const avatarUrl = document.querySelector('#upload');
const email = document.querySelector("#email");
const username = document.querySelector("#name");
const password = document.querySelector("#password");
const phoneNumber = document.querySelector("#phoneNumber");
const gender = document.querySelector("#gender");
const birthday = document.querySelector("#date-birthday");
const cnt_code = document.querySelector("#cnt_code");
const dist_code = document.querySelector("#dist_code");
const detail_address = document.querySelector("#detail_address");
const applybutton = document.getElementById("applybutton");

function valueOrNull(value) {
	if (value === undefined || value === null || value === '' || Number.isNaN(value)) {
		return null;
	} else {
		return value;
	}
}

applybutton.addEventListener('click', function () {
	if (email.value.match(reg) === null) {
		Swal.fire({
			title: '錯誤',
			text: '帳號格式有誤',
			icon: 'error',
			target: document.body
		});
	}

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
			text: '電話欄位必填',
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
	const file = avatarUrl.files[0];
	if (!file) {
		Swal.fire({
			title: '錯誤',
			text: '請上傳圖片！',
			icon: 'error',
			target: document.body
		});
		return;
	}

	const formData = new FormData();
	formData.append('cntCode', cnt_code.value);
	formData.append('distCode', dist_code.value);
	formData.append('detailAddress', detail_address.value);
	formData.append('email', email.value);
	formData.append('name', username.value);
	formData.append('password', password.value);
	formData.append('phone', phoneNumber.value);
	formData.append('birthday', String(birthday.value));
	formData.append('gender', gender.value);
	formData.append('createdAt', password.value);
	formData.append('avatarFile', file, file.name); // 直接上傳檔案

	fetch('register', {
		method: 'POST',
		body: formData
	})
		.then(resp => resp.json())
		.then(body => {
			if (body.successful) {
				location.href = '/meow-gym/index/index.html';
			} else {
				alert(body.message);
			}
		});
});