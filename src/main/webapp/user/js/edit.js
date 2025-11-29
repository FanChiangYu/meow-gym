'use strict'

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
const upload = document.querySelector('#upload');

function valueOrNull(value) {
	if (value === undefined || value === null || value === '' || Number.isNaN(value)) {
		return null;
	} else {
		return value;
	}
}

function editCheck() {
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

	if (valueOrNull(phoneNumber.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '欄位必填',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	if (valueOrNull(gender.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '請選擇性別',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	if (valueOrNull(birthday.value) == null) {
		Swal.fire({
			title: '錯誤',
			text: '生日為必填欄位',
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
			text: '地址為必填欄位',
			icon: 'error',
			target: document.body
		});
		return false;
	}

	return true;
}

applybutton.addEventListener('click', () => {

	if (!editCheck()) {
		return;
	}

	Swal.fire({
			title: "確認",
			text: "是否修改資料？",
			icon: "warning",
			showCancelButton: true,
			confirmButtonText: "確認",
			cancelButtonText: "取消",
			reverseButtons: true,
			customClass: {
				confirmButton: 'btn btn-success',
				cancelButton: 'btn btn-gray me-12'
			}
	})
	.then(result => {
		if (!result.isConfirmed) return;

		if(!upload.files || upload.files.length === 0){
	
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
					birthday: birthday.value.replaceAll('-', '/'),
					cntCode: cnt_code.value,
					distCode: dist_code.value,
					detailAddress: detail_address.value
				}),
			})
			.then(resp => resp.json())
			.then(user => {
				if(user.successful){
					Swal.fire({
						title: '完成',
						text: user.message,
						icon: 'success',
						target: document.body 
					})
					.then(() => location.reload());
				}else{
					Swal.fire({
						title: '錯誤',
						text: user.message,
						icon: 'error',
						target: document.body 
					})
				}
			});
	
		}else{
	
			const fr = new FileReader();
			fr.addEventListener('load', e => {
				const imgBase64 = e.target.result.split(',')[1];
	
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
						birthday: birthday.value.replaceAll('-', '/'),
						cntCode: cnt_code.value,
						distCode: dist_code.value,
						detailAddress: detail_address.value,
						imgBase64,
						filename: upload.files[0].name
					}),
				})
				.then(resp => resp.json())
				.then(user => {
				if(user.successful){
					Swal.fire({
						title: '完成',
						text: user.message,
						icon: 'success',
						target: document.body 
					})
					.then(() => location.reload());
				}else{
					Swal.fire({
						title: '錯誤',
						text: user.message,
						icon: 'error',
						target: document.body 
					})
				}
			});
			 
			});
			fr.readAsDataURL(upload.files[0]);
	
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

		return fetch('/meow-gym/index/loginData');
	})
	.then(resp => resp.json())
	.then(respLoginData => {
		avatarImg2.src = respLoginData.user.avatarUrl;
		avatarImg2.classList.remove('d-none');
		email.value = respLoginData.user.email;
		username.value = respLoginData.user.name;
		password.value = respLoginData.user.password;
		phoneNumber.value = respLoginData.user.phone;
		gender.value = respLoginData.user.gender;
		birthday.value = respLoginData.user.birthday;
		cnt_code.value = respLoginData.user.cntCode;
		dist_code.value = respLoginData.user.distCode;
		detail_address.value = respLoginData.user.detailAddress;
		$('#cnt_code').val(String(respLoginData.user.cntCode)).trigger('change');
		$('#dist_code').val(String(respLoginData.user.distCode)).trigger('change.select2');
		$('#gender').val(String(respLoginData.user.gender)).trigger('change.select2');
	});


upload.addEventListener('change', () => {
	const file = upload.files[0];
	if (file) {
		avatarImg2.src = URL.createObjectURL(file);
		avatarImg2.classList.remove('d-none');
	}
});
