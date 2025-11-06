const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');
const addCartBtn = document.querySelector('#add-cart-btn');
const courseId = document.querySelector('#course-id');
const courseContainer = document.querySelector('#course-container');


// 1a. 用fetch向後端取得roleId(角色ID)
// 1b. 或從瀏覽器的sessionStorage取得roleId (如果登入時有存的話)
// 2. 呼叫switchMenu(); 切換側邊欄顯示

// roldId = 1 -> 一般會員
// roldId = 2 -> 教練
// roldId = 3 -> 管理者

// 如果還沒寫取得roleId，先依照功能關聯對象寫死一個數值，代入並呼叫switchMenu();以切換側邊欄
let Id = 1;

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

switchMenu(Id); // 呼叫function切換側邊欄


// 使用者名稱顯示同理，如果還無法向後端取得user table的name，一樣先寫死，改標籤內的顯示文字
let uName = '金城武'; 
userName.textContent = uName; // 修改標籤內使用者名稱文字


// 使用者頭像顯示，如果還無法向後端取得user table的avatal_url，img標籤src可不改，直接顯示預設頭像
// 如果有取得avatal_url，按照以下寫法更改img標籤src的圖片路徑，以顯示使用者上傳的頭像
let avatarUrl = '../img/avatar/result1.png'; // 假設從後端取得到使用者頭像Url
avatarImg.src = avatarUrl; // 更換img標籤內的src屬性值


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
      });
    }
  });
}

function browseById(courseId) {
  fetch(`browse/${courseId}`)
  .then(resp => resp.json())
  .then(courseResponse => {
        
    if(courseResponse.course.successful){

      let rulesHtml = '';

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
            <p>課程定價: <strong>${courseResponse.course.coursePrice}</strong></p>
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
          } else if(courseResponse.course.payStatus == "PENDING") {
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

// 測試用
addCartBtn.addEventListener('click', function(){

  if(courseId.value === "" || isNaN(courseId.value)){
    Swal.fire({
      title: '提醒',
      text: '請輸入課程ID！',
      icon: 'warning',
      target: document.body
    });
    return;
  }

  addCart(courseId.value);

});
