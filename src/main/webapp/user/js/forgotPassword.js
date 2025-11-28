'use strict';

const reg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const sendBtn = document.querySelector('#send-btn');
const email = document.querySelector('#email');

sendBtn.addEventListener('click', function () {

  if (email.value.match(reg) === null) {
    Swal.fire({
      title: '錯誤',
      text: 'email格式錯誤',
      icon: 'error',
      target: document.body
    });
    return;
  }

  fetch('getCode', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      email: email.value
    }),
  })
    .then(resp => resp.json())
    .then(user => {
      if (user.successful) {
        sessionStorage.setItem('email', user.email);
        sessionStorage.setItem('userId', user.userId);
        location.href = '/meow-gym/user/authCode.html';
      } else {
        Swal.fire({
          title: '錯誤',
          text: user.message,
          icon: 'error',
          target: document.body
        });
      }
    });
});
