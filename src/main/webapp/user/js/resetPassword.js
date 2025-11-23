'use strict';

const setBtn = document.querySelector('#set-btn');
const password = document.querySelector('#password');
const cPassword = document.querySelector('#confirm-password');

function valueOrNull(value) {
  if (value === undefined || value === null || value === '' || Number.isNaN(value)) {
    return null;
  } else {
    return value;
  }
}

fetch('checkUser', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    userId: sessionStorage.getItem('userId')
  }),
})
  .then(resp => resp.json())
  .then(respbody => {
    if (!respbody.checkStatus) {
      location.href = '/meow-gym/user/forgotPassword.html';
    }
  });

setBtn.addEventListener('click', function () {

  if (valueOrNull(password.value) === null) {
    Swal.fire({
      title: '錯誤',
      text: '新密碼未輸入',
      icon: 'error',
      target: document.body
    });
    return;
  }

  if (valueOrNull(cPassword.value) === null) {
    Swal.fire({
      title: '錯誤',
      text: '確認密碼未輸入',
      icon: 'error',
      target: document.body
    });
    return;
  }

  if (valueOrNull(cPassword.value) !== valueOrNull(password.value)) {
    Swal.fire({
      title: '錯誤',
      text: '新密碼與確認密碼不相同',
      icon: 'error',
      target: document.body
    });
    return;
  }

  fetch('change', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      userId: sessionStorage.getItem('userId'),
      password: valueOrNull(password.value)
    }),
  })
    .then(resp => resp.json())
    .then(respbody => {
      if (respbody.successful) {
        Swal.fire({
          title: '完成',
          text: '密碼已變更',
          icon: 'success',
          target: document.body
        })
          .then(() => location.href = '/meow-gym/index/index.html');
      } else {
        Swal.fire({
          title: '錯誤',
          text: '新密碼與舊密碼相同',
          icon: 'error',
          target: document.body
        });
      }
    });
});
