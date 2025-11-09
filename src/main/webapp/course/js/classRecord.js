const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');
const classContent = document.querySelector('#class-content');

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

// ------------ 顯示會員得上課紀錄 -----------------
fetch('record')
.then(resp => resp.json())
.then(classResponses => {

  // 課程迭代
  for (let classResponse of classResponses) {

    // 班次迭代
    let sessionHtml = '';
    for (let classSession of classResponse.classSessions) {
      if(classSession.bookStatus == '已預約'){  
        sessionHtml += `
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
              <span class="text-heading">${nullDisplay(classSession.checkinAt)}</span>
            </td>
              <td>
              <span class="text-heading">${nullDisplay(classSession.checkinOut)}</span>
            </td>
          </tr>
        `;
      }

    }

    if(sessionHtml == ''){
      return;                     // 如果沒有任合預約班次紀錄，則不顯示
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
                <div class="card-body p-4 pt-2">
                  <p class="mt-1">課程ID : ${classResponse.course.courseId}</p>
                  <p class="mt-1">教練 : ${classResponse.coachName}</p>
                  <div class="d-flex justify-content-between align-items-center mb-4">
                    <p class="mt-1">地點 : ${roomName(classResponse.course.roomId)}</p>
                    <button onclick="chatById(${classResponse.course.courseId})" class="btn rounded-pill waves-effect waves-light btn-primary ">聊天室</button>
                  </div>
                </div>
                <div class="card-datatable">
                  <table class="datatables-users table">
                    <thead class="border-top">
                      <tr>
                        <th>課程班次ID</th>
                        <th>日期</th>
                        <th>時段</th>
                        <th>實際上課時間</th>
                        <th>實際下課時間</th>
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

// ------------ 轉跳聊天室 -----------------
function chatById(courseId) {
  fetch(`record/${courseId}`)
  .then(resp => resp.json())
  .then(respbody => {
    if(respbody.ok){
      location.href = "/meow-gym/chat/addChat.html"
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