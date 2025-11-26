'use strict';

const resendBtn = document.querySelector('#resend-btn');
const nextBtn = document.querySelector('#next-btn');
const inputs = document.querySelectorAll('.numeral-mask-wrapper input');

resendBtn.addEventListener('click', e => {
  e.preventDefault();

  fetch('getCodeAgain', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      email: sessionStorage.getItem('email')
    }),
  })
    .then(resp => resp.json())
    .then(respbody => {
      if (respbody.successful) {
        Swal.fire({
          title: '完成',
          text: '驗證碼已再次發送至註冊信箱',
          icon: 'success',
          target: document.body
        });
      } else {
        Swal.fire({
          title: '錯誤',
          text: '驗證碼發送失敗',
          icon: 'error',
          target: document.body
        });
      }
    });
})

nextBtn.addEventListener('click', function () {
  let code = '';

  inputs.forEach(input => {
    console.log(input.value);
    code += input.value;
  });

  if (code.length !== 6) {
    Swal.fire({
      title: '錯誤',
      text: '請輸入完整 6 位數驗證碼',
      icon: 'error',
      target: document.body
    });
    return;
  }

  fetch('checkCode', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      userId: sessionStorage.getItem('userId'),
      resetCode: code
    }),
  })
    .then(resp => resp.json())
    .then(respbody => {
      if (respbody.successful) {
        location.href = '/meow-gym/user/resetPassword.html';
      } else {
        Swal.fire({
          title: '錯誤',
          text: '輸入的驗證碼錯誤',
          icon: 'error',
          target: document.body
        });
      }
    });
});