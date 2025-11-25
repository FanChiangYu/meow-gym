const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');
const tbody = document.querySelector('#user-table-body');
const logoutBtn = document.querySelector('#logout-btn');
const userCenter = document.querySelector('#user-center');

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

function approvalLabel (status) {
  switch(status){
    case "待審核":
      return "bg-label-info";

    case "通過":
      return "bg-label-success";

    case "不通過":
      return "bg-label-danger";

    default:
      return "bg-label-secondary"
  }
}

function roleName (role) {
   switch(role){
    case 1:
      return "一般會員";

    case 2:
      return "教練";

    case 3:
      return "管理者";

    default:
      return "一般會員"
  }
}

function gender (gender) {
  return gender === 'M' ? '男' : '女'; 
}

function inviteById (userId) {
  fetch('manage', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      userId
   })
  })
  .then(resp => resp.json())
  .then(body => {
    if(body.successful){
      Swal.fire({
        title: '完成',
        text: body.message,
        icon: 'success',
        target: document.body 
      })
      .then(()=>location.reload());
    }else{
      Swal.fire({
        title: '錯誤',
        text: body.message,
        icon: 'error',
        target: document.body 
      });
    }
  });
}

function auditById (userId) {
  fetch(`audit/${userId}`)
  .then(resp => resp.json())
  .then(respbody => {
    let certHtml = '';
    if(respbody.certificate.fileUrl != '' && respbody.certificate.name != ''){
      certHtml = `<p>證照: <a href="${respbody.certificate.fileUrl}" class="card-link" download>${respbody.certificate.name}</a></p>`;
    }
    Swal.fire({
      title: respbody.user.name,
      html: `
        <div style="text-align:left">
          <p>教練ID: ${respbody.profile.coachId}</p>
          <p>性別: ${gender(respbody.user.gender)}</p>
          <p>自我介紹: </p>
          <p>${respbody.profile.bio}</p>
          <p>學歷: ${respbody.education.school} ${respbody.education.degree}</p>
          <p>工作經歷: ${respbody.experience.company} ${respbody.experience.title}</p>
          <p>任職日期: ${respbody.experience.startDate} ~ ${respbody.experience.endDate}</p>
          ${certHtml}
        </div>
      `,
      imageUrl: respbody.user.avatarUrl,
      imageWidth: 500,
      // imageHeight: 500,
      imageAlt: '教練頭像',
      icon: 'info',
      showCancelButton: true,
      showDenyButton: true,
      confirmButtonText: '通過',
      denyButtonText: '不通過',
      cancelButtonText: '取消',
      reverseButtons: true, 
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-gray me-12',
        denyButton: 'btn btn-danger me-12'
      }
    }).then(result => {

      if (result.isConfirmed) {
        fetch('audit', {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            coachId: respbody.profile.coachId,
            approvalStatus: '通過'
          }),
        })
        .then(() => location.reload());
      } else if(result.isDenied) {
        fetch('audit', {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            coachId: respbody.profile.coachId,
            approvalStatus: '不通過'
          }),
        })
        .then(() => location.reload());
      } else {
        console.log('取消');
      }

    });
  });

}

fetch('/meow-gym/index/loginData')
.then(resp => resp.json())
.then(respbody => {
  if(respbody.successful){
    switchMenu(respbody.user.role); // 切換側邊欄: 1 -> 一般會員、2 -> 教練、3 -> 管理者
    userName.textContent = respbody.user.name; // 修改標籤內使用者名稱
    avatarImg.src = respbody.user.avatarUrl; // 更換img標籤圖片
  }else{
    Swal.fire({
      title: '錯誤',
      text: '請先登入',
      icon: 'error',
      target: document.body 
    })
    .then(() => location.href = '/meow-gym/user/login.html');
  }
});

fetch('manage')
.then(resp => resp.json())
.then(cuList => {
  let tbodyHtml = '';
  cuList.forEach(cu => {
    let statusHtml = '';
    let auditBtnHtml = '';
    let inviteBtnHtml = '';

    // 判斷有沒被邀請教練資格
    if(cu.coachProfiles){
      statusHtml = `<span class="badge ${approvalLabel(cu.coachProfiles.approvalStatus)}">${cu.coachProfiles.approvalStatus}</span>`;
      auditBtnHtml = `<button onclick="auditById(${cu.user.userId})" class="btn rounded-pill btn-primary waves-effect waves-light">審核</button>`;
      inviteBtnHtml = `<button class="btn rounded-pill btn-gray waves-effect waves-light" disabled>已邀請</button>`;
    }else{
      statusHtml = `<span> - </span>`;
      auditBtnHtml = `<button class="btn rounded-pill btn-gray waves-effect waves-light" disabled>審核</button>`;
      inviteBtnHtml = `<button onclick="inviteById(${cu.user.userId})" class="btn rounded-pill btn-primary waves-effect waves-light">邀請</button>`;
    }

    if(cu.user.role == 3){ // 管理者不顯示
      return;
    }

    tbodyHtml += `
      <tr>
        <td>
          <span class="text-heading">${cu.user.userId}</span>
        </td>
        <td>
          <span class="text-truncate d-flex align-items-center text-heading">
            <i class="icon-base ti tabler-user icon-md text-success me-2"></i>
            ${cu.user.name}
          </span>
        </td>
        <td>
          <span class="text-heading">${cu.user.email}</span>
        </td>
        <td>
          <span class="text-heading">${roleName(cu.user.role)}</span>
        </td>
        <td>
          ${statusHtml}
        </td>
        <td>
          ${inviteBtnHtml}
        </td>
        <td>
          ${auditBtnHtml}
        </td>
      </tr>
    `;
  });
  tbody.innerHTML = tbodyHtml;
});

logoutBtn.addEventListener('click', e => {
  e.preventDefault();
  fetch('/meow-gym/user/logout')
  .then(()=>location.href = '/meow-gym/index/index.html');
});

userCenter.addEventListener('click', e => {
  e.preventDefault();
  fetch('/meow-gym/index/userCenter')
  .then(resp => resp.json())
  .then(respbody => {
    location.href = respbody.url;
  });
});