const userMenu = document.querySelector('#user-menu');
const coachMenu = document.querySelector('#coach-menu');
const adminMenu = document.querySelector('#admin-menu');
const userName = document.querySelector('#user-name');
const avatarImg = document.querySelector('#user-avatar');
const shoppingCart = document.querySelector('#shopping-cart');
const addCartBtn = document.querySelector('#add-cart-btn');
const courseId = document.querySelector('#course-id');


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

  fetch('addCart', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      courseId: courseId.value
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
      // location.href = '/meow-gym/order/newOrder.html';
    }else{
      Swal.fire({
        title: '錯誤',
        text: body.message,
        icon: 'error',
        target: document.body 
      });
    }
  });

});
