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
const courseTitle = document.querySelector('#courseTitle');
const coursePrice = document.querySelector('#coursePrice');
const dateStart = document.querySelector('#date-start');
const dateEnd = document.querySelector('#date-end');
const courseImg = document.querySelector('#course-img');
const promoPrice = document.querySelector('#promo-price');

init();
function init() {
	const title = sessionStorage.getItem('title');
	const price = sessionStorage.getItem('price');
	
	courseTitle.textContent = title;
	coursePrice.value = price;
}

document.querySelector('#apply-btn').addEventListener('click', () => {
    const fr = new FileReader();
    fr.addEventListener('load', e => {
        const imgBase64 = e.target.result.split(',')[1];
        fetch('verify', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            courseId: sessionStorage.getItem('id'),
            promoPrice: promoPrice.value,
            dateStart: dateStart.value.replaceAll('-', '/'),
            dateEnd: dateEnd.value.replaceAll('-', '/'),
            imgBase64,
            filename: courseImg.files[0].name
        })
    })
        .then(resp => resp.json())
        .then(body => {
            if (body.successful) {
                location.href = 'reviewPomotionsList.html';
            } else {
            	alert(body.message);
            }
        });
    });
    fr.readAsDataURL(courseImg.files[0]);
});