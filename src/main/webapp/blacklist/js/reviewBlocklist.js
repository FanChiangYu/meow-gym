const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');

function switchMenu (role) {
  switch (role) {
    // 顯示會員列表
    case 1: 
      userMenu.classList.remove('d-none'); 
      shoppingCart.classList.remove('d-none');  // 顯示購物車按鍵
      break;
  
    // 顯示教練列表  
    case 2:
      coachMenu.classList.remove('d-none'); 
      break;
  
    // 顯示管理者列表  
    case 3:
      adminMenu.classList.remove('d-none'); 
      break;
  
    // 預設顯示會員列表
    default:
      userMenu.classList.remove('d-none'); 
      shoppingCart.classList.remove('d-none');  // 顯示購物車按鍵
      break;
  }
}
fetch('/meow-gym/index/loginData')
.then(resp => resp.json())
.then(respbody => {
  if(respbody.successful){
    switchMenu(respbody.user.role); // 切換側邊欄: 1 -> 一般會員、2 -> 教練、3 -> 管理者
    userName.textContent = respbody.user.name; // 修改標籤內使用者名稱
    avatarImg.src = respbody.user.avatarUrl; // 更換img標籤圖片
  }
});

//以下自己編寫的js
const tbody = document.querySelector('tbody');

fetch('reviewBlocklist')
  .then(resp => resp.json())
  .then(users => {
    for (const user of users) {
      const bannedText = user.isBanned ? '已加入黑名單' : '未加入黑名單';
      if(user.role === 1){
        tbody.innerHTML += `
        <tr>
          <td>${user.userId}</td>
          <td>${user.email}</td>
          <td>${user.createdAt}</td>
          <td>${bannedText}</td>
          <td>
            <button id="apply-btn" type="button" class="btn btn-primary"
                    onclick="addBlockMember(${user.userId})">
              加入黑名單
            </button>
          </td>
          <td>
           <button id="apply-btn" type="button" class="btn btn-primary"
                    onclick="removeBlockMember(${user.userId})">
              移除黑名單
            </button>
          </td>
        </tr>
      `;
      }
    }
  });

  function addBlockMember(userId) {
  Swal.fire({
    title: '(是)(否)加入黑名單？',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: '是',
    cancelButtonText: '否',
  }).then((result) => {
    if (result.isConfirmed) {
      fetch('block',{
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          userId,
         })
      })
      .then(resp => resp.json())
      .then(respbody => {
        if(respbody.successful){
          Swal.fire({
            title: '成功',
            text: '加入成功',
            icon: 'success',
            target: document.body 
          })
          .then(() => location.reload());
        }else{
          Swal.fire({
            title: '錯誤',
            text: '加入失敗',
            icon: 'error',
            target: document.body 
          })
        }
      })
    }
  });
}
function removeBlockMember(userId) {
  Swal.fire({
    title: '(是)(否)移除黑名單？',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: '是',
    cancelButtonText: '否',
  }).then((result) => {
    if (result.isConfirmed) {
      fetch('unlock',{
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          userId,
         })
      })
      .then(resp => resp.json())
      .then(respbody => {
        if(respbody.successful){
          Swal.fire({
            title: '成功',
            text: '移除成功',
            icon: 'success',
            target: document.body 
          })
          .then(() => location.reload());
        }else{
          Swal.fire({
            title: '錯誤',
            text: '移除失敗',
            icon: 'error',
            target: document.body 
          })
        }
      })
    }
  });
}