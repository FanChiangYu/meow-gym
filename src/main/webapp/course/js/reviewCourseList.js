// document.addEventListener("DOMContentLoaded", function(){
const tbody = document.querySelector('#course-table-body');
const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');
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

function showWeekDay (weekday) {
  switch (weekday) {
    case 1:
      return "星期一";

    case 2:
      return "星期二";
    
    case 3:
      return "星期三";
    
    case 4:
      return "星期四";
    
    case 5:
      return "星期五";

    case 6:
      return "星期六";

    case 7:
      return "星期日";

    default:
      return "";
  }
}

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

// ------------ 載入課程審核表單 -----------------
fetch('audit')
	.then(resp => resp.json())
	.then(courses => {

		for (let course of courses) {
    
      tbody.innerHTML += `
        <tr>
          <td>
            <span class="text-heading">${course.courseId}</span>
          </td>
          <td>
            <span class="text-heading">${course.title}</span>
          </td>
          <td>
            <span class="text-truncate d-flex align-items-center text-heading">
              <i class="icon-base ti tabler-user icon-md text-success me-2"></i>
              ${course.coachName}
            </span>
          </td>
          <td>
            <span class="text-heading">${course.category}</span>
          </td>
          <td>
            <span class="text-heading">${course.sessionQuota}堂</span>
          </td>
          <td>
            <span class="text-heading">${course.coursePrice}</span>
          </td>
          <td>
            <span class="badge ${approvalLabel(course.approvalStatus)}">${course.approvalStatus}</span>
          </td>
          <td>
            <button onclick="auditById(${course.courseId})" class="btn rounded-pill btn-primary waves-effect waves-light">審核</button>
          </td>
        </tr>
        `;

		}
	});

  // ------------ 審核課程 -----------------
  function auditById(id) {

    fetch(`audit/${id}`)
      .then(resp => resp.json())
      .then(courseResponse => {
        
        if(courseResponse.course.successful){

          let rulesHtml = '';

          courseResponse.rules.forEach((rule, index) => {
            rulesHtml += `
              <p>課程規則${index + 1}: ${showWeekDay(rule.weekday)} ${showTimeSlot(rule.timeSlot)}</p>
            `;
          });

          Swal.fire({
            title: courseResponse.course.title,
            html: `
              <div style="text-align:left">
                <p>課程ID: ${courseResponse.course.courseId}</p>
                <p>教練: ${courseResponse.userName}</p>
                <p>類別: ${courseResponse.course.category}</p>
                <p>堂數: ${courseResponse.course.sessionQuota}堂</p>
                <p>上課人數上限: ${courseResponse.course.capacityMax}人</p>
                <p>教室: ${roomName(courseResponse.course.roomId)}</p>
                <p>開始日期: ${new Date(courseResponse.course.dateStart).toLocaleDateString('zh-TW')}</p>
                <p>結束日期: ${new Date(courseResponse.course.dateEnd).toLocaleDateString('zh-TW')}</p>
                <p>課程介紹:</p>
                <p>${courseResponse.course.description}</p>
                ${rulesHtml}
                <p>審核狀態: <span class="badge ${approvalLabel(courseResponse.course.approvalStatus)}">${courseResponse.course.approvalStatus}</span></p>
                <p>課程定價: <strong>${courseResponse.course.coursePrice}</strong></p>
              </div>
            `,
            imageUrl: courseResponse.course.imgUrl,
            imageWidth: 500,
            // imageHeight: 500,
            imageAlt: '課程圖片',
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
                  courseId: id,
                  approvalStatus: '通過'
                }),
              })
              .then(() => location.reload());
            } else if(result.isDenied) {
              fetch('audit', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                  courseId: id,
                  approvalStatus: '不通過'
                }),
              })
              .then(() => location.reload());
            } else {
              console.log('取消');
            }
      
          });

        }else{

          Swal.fire({
            title: '錯誤',
            text: '載入失敗',
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
// });