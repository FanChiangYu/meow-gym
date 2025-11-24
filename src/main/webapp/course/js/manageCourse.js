const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');
const classContent = document.querySelector('#class-content');
const logoutBtn = document.querySelector('#logout-btn');
const userCenter = document.querySelector('#user-center');

function switchMenu (roleId) {
  switch (roleId) {
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

function roomName (number){
  switch (number) {
    case 1:
      return "教室A";

    case 2:
      return "教室B";

    case 3:
      return "教室C";
  
    default:
      return "場地未定";
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

// 時段顯示切換
  function showTimeSlot (timeSlot) {
  switch (timeSlot) {
    case 1:
      return "8:00 ~ 9:00";

    case 2:
      return "9:00 ~ 10:00";
    
    case 3:
      return "10:00 ~ 11:00";
    
    case 4:
      return "11:00 ~ 12:00";
    
    case 5:
      return "12:00 ~ 13:00";

    case 6:
      return "13:00 ~ 14:00";

    case 7:
      return "14:00 ~ 15:00";

    case 8:
      return "15:00 ~ 16:00";

    case 9:
      return "16:00 ~ 17:00";
    
    case 10:
      return "17:00 ~ 18:00";
    
    case 11:
      return "18:00 ~ 19:00";
    
    case 12:
      return "19:00 ~ 20:00";

    case 13:
      return "20:00 ~ 21:00";

    default:
      return "";
  }
}

function nullDisplay (timestamp) {
  return timestamp === null ? "-" : timestamp;
}

// ------------ 顯示所屬教練的所有課程 -----------------
fetch('manage')
.then(resp => resp.json())
.then(classResponses => {

  // 顯示課程
  classResponses.forEach(classResponse => {
    
    // 顯示班次
    let sessionHtml = '';
    classResponse.classSessions.forEach(classSession => {
      sessionHtml +=`
        <tr>
          <td>
            <span class="text-heading">${classSession.sessionId}</span>
          </td>
          <td>
            <span class="text-heading">
              ${new Date(classSession.sessionDate).toLocaleDateString('zh-TW',{
                weekday: 'short',
                year: 'numeric',
                month: 'numeric',
                day: 'numeric'
              })}
            </span>
          </td>
          <td>
            <span class="text-heading">${showTimeSlot(classSession.timeSlot)}</span>
          </td>
          <td>
            <span class="text-heading">${classSession.userCnt}/${classResponse.course.capacityMax}</span>
          </td>
          <td>
            <span class="text-heading">${nullDisplay(classSession.checkinAt)}</span>
          </td>
          <td>
            <span class="text-heading">${nullDisplay(classSession.checkinOut)}</span>
          </td>
          <td>
            <button onclick="checkinAtById(${classSession.sessionId})" class="btn rounded-pill waves-effect waves-light btn-primary">打卡</button>
          </td>
          <td>
            <button onclick="checkinOutById(${classSession.sessionId})" class="btn rounded-pill waves-effect waves-light btn-primary">打卡</button>
          </td>
        </tr>
      `;
    });

    let sessionTableHtml = '';
    let chatBtnHtml = '';
    if(classResponse.course.approvalStatus == "通過"){
      sessionTableHtml = `
        <div class="card-datatable">
          <table class="datatables-users table">
            <thead class="border-top">
              <tr>
                <th>課程班次ID</th>
                <th>日期</th>
                <th>時段</th>
                <th>預約人數</th>
                <th>上課打卡時間</th>
                <th>下課打卡時間</th>
                <th>上課打卡</th>
                <th>下課打卡</th>
              </tr>
            </thead>
            <tbody>     
            ${sessionHtml}
            </tbody>   
          </table>
        </div>
      `;

      chatBtnHtml = `
        <button onclick="chatById(${classResponse.course.courseId})" class="btn rounded-pill waves-effect waves-light btn-primary ">聊天室</button>
      `;
    }

   classContent.innerHTML += `
    <div class="card mb-6">
      <div class="card-header d-flex flex-wrap justify-content-between gap-4">
        <div class="card-title mb-0 me-1">
          <h5 class="mb-0">${classResponse.course.title}</h5>
        </div>
      </div>
      <div class="card-body">
        <div class="row gy-6 mb-6">
          <div class="col-sm-6 col-lg-12">
            <div class="card p-2 h-100 shadow-none border">
              <div class="rounded-2 text-center mb-4">
                <img class="img-fluid d-block mx-auto my-6 rounded w-50" src="${classResponse.course.imgUrl}" alt="課程圖片">
              </div>
              <div class="card-body p-4 pt-2">
                <p class="mt-1">課程ID : ${classResponse.course.courseId}</p>
                <p class="mt-1">審核狀態 : <strong class="badge ${approvalLabel(classResponse.course.approvalStatus)} fs-6">${classResponse.course.approvalStatus}</strong></p>
                <p class="mt-1">課程類別 : ${classResponse.course.category}</p>
                <p class="mt-1">上課日期 : ${new Date(classResponse.course.dateStart).toLocaleDateString('zh-TW')} ~ ${new Date(classResponse.course.dateEnd).toLocaleDateString('zh-TW')}</p>
                <p class="mt-1">課堂額度 : ${classResponse.course.sessionQuota}堂</p>
                <p class="mt-1">地點 : ${roomName(classResponse.course.roomId)}</p>
                <p class="mt-1">上課最大人數 : ${classResponse.course.capacityMax}人</p>
                <div class="d-flex justify-content-between align-items-center mb-4">
                  <p class="mt-1">課程定價 : ${classResponse.course.coursePrice}</p>
                  ${chatBtnHtml}
                </div>
              </div>
              ${sessionTableHtml}
            </div>
          </div>
        </div>
      </div>
    </div>
   `;

  });
});

// ------------ 上課打卡 -----------------
function checkinAtById(sessionId) {
  fetch('manage',{
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      sessionId,
      chkSelect: 'AT'
    })
  })
  .then(resp => resp.json())
  .then(respbody => {
    if(respbody.successful){
      Swal.fire({
        title: '成功',
        text: respbody.message,
        icon: 'success',
        target: document.body 
      })
      .then(() => location.reload());
    }else{
      Swal.fire({
        title: '錯誤',
        text: respbody.message,
        icon: 'error',
        target: document.body 
      });
    }
  })
}

// ------------ 下課打卡 -----------------
function checkinOutById(sessionId) {
  fetch('manage',{
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      sessionId,
      chkSelect: 'OUT'
    })
  })
  .then(resp => resp.json())
  .then(respbody => {
    if(respbody.successful){
      Swal.fire({
        title: '成功',
        text: respbody.message,
        icon: 'success',
        target: document.body 
      })
      .then(() => location.reload());
    }else{
      Swal.fire({
        title: '錯誤',
        text: respbody.message,
        icon: 'error',
        target: document.body 
      });
    }
  })
}

// ------------ 轉跳聊天室 -----------------
function chatById(courseId) {
  fetch(`record/${courseId}`)
  .then(resp => resp.json())
  .then(respbody => {
    if(respbody.ok){
      location.href = "/meow-gym/chat/app-chat.html"
    }else{
      Swal.fire({
        title: '錯誤',
        text: '轉跳失敗',
        icon: 'error',
        target: document.body 
      });
    }
  });
}

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