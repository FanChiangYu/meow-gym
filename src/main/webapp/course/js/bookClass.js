// document.addEventListener('DOMContentLoaded', function() {

  const userMenu = document.querySelector('#user-menu');
  const coachMenu = document.querySelector('#coach-menu');
  const adminMenu = document.querySelector('#admin-menu');
  const userName = document.querySelector('#user-name');
  const avatarImg = document.querySelector('#user-avatar');
  const shoppingCart = document.querySelector('#shopping-cart');
  const classContent = document.querySelector('#class-content');
  const userCenter = document.querySelector('#user-center');
  const logoutBtn = document.querySelector('#logout-btn');
  
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

  // 分類標籤顏色切換
  function categoryLabel (category) {
    switch(category){
      case "重訓":
        return "bg-label-primary";

      case "飛輪":
        return "bg-label-twitter";

      case "瑜伽":
        return "bg-label-dribbble";

      default:
        return "bg-label-primary"
    }
  }

  // 滿堂預約處理
  function maxQuotaHandle(bookStatus, sessionQuota, QuotaUsed){
    if (bookStatus === "未預約") {
      return QuotaUsed >= sessionQuota ? "無法預約" : bookStatus;
    }
    return bookStatus;
  }

  // 按鍵顏色切換
  function btnColor (bookStatus) { 
    switch(bookStatus){
      case "未預約":
        return "btn-primary";

      case "已預約":
        return "btn-danger";

      case "無法預約":
        return "btn-gray";

      default:
        return "btn-gray"
    }
  }

  // 按鍵文字切換
  function btnText (bookStatus) {
    switch(bookStatus){
      case "未預約":
        return "預約";

      case "已預約":
        return "取消預約";

      case "無法預約":
        return "無法預約";

      default:
        return "無法預約"
    }
  }

  // 按鍵失效切換
  function btnDisable (bookStatus) {
    return bookStatus === "無法預約" ? "disabled" : "";
  }

  // 按鍵觸發function切換
  function btnFuncSwitch (bookStatus, sessionId) {
    switch(bookStatus){
      case "未預約":
        return `reserveById(${sessionId})`;

      case "已預約":
        return `cancelById(${sessionId})`;

      case "無法預約":
        return ``;

      default:
        return ``;
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

// ------------ 預約上課班次 -----------------
function reserveById(sessionId) {
  fetch('reserve', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      sessionId,
      bookStatus: "未預約"
    }),
  })
  .then(resp => resp.json())
  .then(respBody => {
    if(respBody.successful){
      location.reload();
    }else{
      Swal.fire({
        title: '錯誤',
        text: respBody.message,
        icon: 'error',
        target: document.body 
      });
    }
  });
}

// ------------ 取消上課班次 -----------------
function cancelById(sessionId) {
  fetch('reserve', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      sessionId,
      bookStatus: "已預約"
    }),
  })
  .then(resp => resp.json())
  .then(respBody => {
    if(respBody.successful){
      location.reload();
    }else{
      Swal.fire({
        title: '錯誤',
        text: respBody.message,
        icon: 'error',
        target: document.body 
      });
    }
  });
}

  // 顯示已購買課程列表
  fetch('reserve')
    .then(resp => resp.json())
    .then(classResponses => {

      // 顯示課程
      for (let classResponse of classResponses) {
        
        // 顯示班次
        let sessionHtml = '';
        for (let classSession of classResponse.classSessions) {
          let newBookStatus = maxQuotaHandle(classSession.bookStatus, classResponse.course.sessionQuota, classResponse.course.quotaUsed);
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
                <span class="text-heading">${newBookStatus}</span>
              </td>
              <td>
                <button onclick="${btnFuncSwitch(newBookStatus, classSession.sessionId)}" class="btn rounded-pill waves-effect waves-light ${btnColor(newBookStatus)} ${btnDisable(newBookStatus)}">${btnText(newBookStatus)}</button>
              </td>
            </tr>
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
                      <div class="d-flex justify-content-between align-items-center mb-4">
                        <span class="badge ${categoryLabel(classResponse.course.category)}">${classResponse.course.category}</span>
                      </div>
                      <p class="mt-1">課程ID : ${classResponse.course.courseId}</p>
                      <p class="mt-1">教練 : ${classResponse.coachName}</p>
                      <p class="mt-1">使用期限 : ${new Date(classResponse.course.dateStart).toLocaleDateString('zh-TW')} ~ ${new Date(classResponse.course.dateEnd).toLocaleDateString('zh-TW')}</p>
                      <p class="mt-1">課堂額度 : ${classResponse.course.sessionQuota}堂</p>
                      <p class="mt-1">總共使用 : ${classResponse.course.quotaUsed}堂</p>
                    </div>
                    <div class="card-datatable">
                      <table class="datatables-users table">
                        <thead class="border-top">
                          <tr>
                            <th>課程班次ID</th>
                            <th>日期</th>
                            <th>時段</th>
                            <th>預約人數</th>
                            <th>預約狀態</th>
                            <th>動作</th>
                          </tr>
                        </thead>
                        <tbody>
                          ${sessionHtml}
                        </tbody>   
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        `;

      }
    });
    

// });
