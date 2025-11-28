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
const avatarImg = document.querySelector('#avatarImg');


function valueOrNull(value) {
	if (value === undefined || value === null || value === '' || Number.isNaN(value)) {
		return null;
	} else {
		return value;
	}
}

// check();
function check() {


	if (email.value.match(reg) === null) {
		Swal.fire({
			title: '錯誤',
			text: '帳號格式不正確',
			icon: 'error',
			target: document.body
		});
		return false;
	}


	if (valueOrNull(username.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '姓名未輸入',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	if (valueOrNull(password.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '密碼未輸入',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	if (password.value.length < 8) {
		Swal.fire({
			title: '錯誤',
			text: '密碼長度不足(至少8位數)',
			icon: 'error',
			target: document.body
		});
		return false;
	}


	if (valueOrNull(phoneNumber.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '電話號碼未輸入',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	if (valueOrNull(gender.value) == null) {
		alert('請選擇性別');
		Swal.fire({
			title: '錯誤',
			text: body.message,
			icon: 'error',
			target: document.body
		});
		return false;
	}

	if (valueOrNull(birthday.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '請選擇出生日期',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	if (valueOrNull(cnt_code.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '請選擇縣市',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	if (valueOrNull(dist_code.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '請選擇鄉鎮',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	if (valueOrNull(detail_address.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '請填寫地址',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	return true;
}

applybutton.addEventListener('click', function () {
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

	if (!check()) {
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
				Swal.fire({
					title: '完成',
					text: body.message,
					icon: 'success',
					target: document.body
				})
					.then(() => location.href = '/meow-gym/index/index.html');
			} else {
				Swal.fire({
					title: '錯誤',
					text: body.message,
					icon: 'error',
					target: document.body
				})
			}
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

avatarUrl.addEventListener('change', () => {
	const file = avatarUrl.files[0];
	if (file) {
		avatarImg.src = URL.createObjectURL(file);
		avatarImg.classList.remove('d-none');
	}
});
