const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');
const courseId = document.querySelector('#course-id');
const courseContainer = document.querySelector('#course-container');
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

fetch('/meow-gym/index/loginData')
.then(resp => resp.json())
.then(respbody => {
  if(respbody.successful && respbody.user.role === 1){
    switchMenu(respbody.user.role); // 切換側邊欄: 1 -> 一般會員、2 -> 教練、3 -> 管理者
    userName.textContent = respbody.user.name; // 修改標籤內使用者名稱
    avatarImg.src = respbody.user.avatarUrl; // 更換img標籤圖片
  } else if(respbody.successful) {
    Swal.fire({
      title: '錯誤',
      text: '非訪客及一般會員即將登出',
      icon: 'error',
      target: document.body 
    })
    .then(() => logoutBtn.click());
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

function addCart(courseId){
  fetch('browse', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      courseId
    }),
  })
  .then(resp => resp.json())
  .then(body => {
    if(body.successful){
      Swal.fire({
        title: body.message,
        text: '已加入購物車',
        icon: 'success',
        confirmButtonText: '前往購物車'
      })
      .then(()=>location.href = '/meow-gym/order/newOrder.html');
    }else{
      Swal.fire({
        title: '錯誤',
        text: body.message,
        icon: 'error',
        target: document.body 
      })
      .then(()=>location.href = '/meow-gym/user/login.html');
    }
  });
}

function browseById(courseId) {
  fetch(`browse/${courseId}`)
  .then(resp => resp.json())
  .then(courseResponse => {
        
    if(courseResponse.course.successful){

      let rulesHtml = '';
      let promoPriceHtml = '';

      if(courseResponse.course.promoPrice === null){
        promoPriceHtml += `
          <p>課程定價: <strong>${courseResponse.course.coursePrice}</strong></p>
        `;
      } else {
        promoPriceHtml += `
          <p class="text-decoration-line-through">課程定價: ${courseResponse.course.coursePrice}</p>
          <p class="text-danger"><strong>課程特價: ${courseResponse.course.promoPrice}</strong></p>
        `;
      }

      courseResponse.rules.forEach((rule, index) => {
        rulesHtml += `
          ${showWeekDay(rule.weekday)} ${showTimeSlot(rule.timeSlot)}</p>
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
            <p>地點: ${roomName(courseResponse.course.roomId)}</p>
            <p>上課日期: ${new Date(courseResponse.course.dateStart).toLocaleDateString('zh-TW')} ~ ${new Date(courseResponse.course.dateEnd).toLocaleDateString('zh-TW')}</p>
            <p>課程介紹:</p>
            <p>${courseResponse.course.description}</p>
            <p>每週上課時間:</p>
            ${rulesHtml}
            ${promoPriceHtml}
          </div>
        `,
        imageUrl: courseResponse.course.imgUrl,
        imageWidth: 500,
        // imageHeight: 500,
        imageAlt: '課程圖片',
        icon: 'info',
        showCancelButton: true,
        confirmButtonText: '加入購物車',
        cancelButtonText: '返回',
        reverseButtons: true, 
        customClass: {
          confirmButton: 'btn btn-primary',
          cancelButton: 'btn btn-info me-12',
        },
        didOpen: () => {
          const confirmBtn = Swal.getConfirmButton();
          if(courseResponse.course.payStatus == "PAID"){
            confirmBtn.disabled = true;
            confirmBtn.textContent = "已購買"; 
          } else if(courseResponse.course.payStatus == "PENDING" || courseResponse.course.payStatus == "WAIT_PAID") {
            confirmBtn.disabled = true;
            confirmBtn.textContent = "已加入購物車"; 
          }
        }
      }).then(result => {

        if (result.isConfirmed) {
          addCart(courseResponse.course.courseId);
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

fetch('browse')
.then(resp => resp.json())
.then(courses => {
  let courseHtml = '';
  courses.forEach(course => {
    courseHtml += `
      <div class="col-md-6 col-lg-4">
        <div class="card h-100">
          <div class="card-body">
            <h5 class="card-title">${course.title}</h5>
            <h6 class="card-subtitle">教練: ${course.coachName}</h6>
            <img class="img-fluid d-flex mx-auto my-6 rounded" src="${course.imgUrl}" alt="課程圖片">
            <p class="card-text">課程簡介:</p>
            <p class="card-text">${course.description}</p>
            <button onclick="browseById(${course.courseId})" class="btn btn-outline-primary waves-effect">詳細資訊</button>
          </div>
        </div>
      </div>
    `;
  });
  courseContainer.innerHTML += courseHtml;
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