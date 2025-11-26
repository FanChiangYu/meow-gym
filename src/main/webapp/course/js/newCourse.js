document.addEventListener('DOMContentLoaded', function(){

  const title = document.querySelector('#title');
  const category = document.querySelector('#category');
  const roomId = document.querySelector('#room-id');
  const sessionQuota = document.querySelector('#session-quota');
  const capacityMax = document.querySelector('#capacity-max');
  const dateStart = document.querySelector('#date-start');
  const dateEnd = document.querySelector('#date-end');
  const coursePrice = document.querySelector('#course-price');
  const description = document.querySelector('#description');
  const courseImg = document.querySelector('#course-img');
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
  
  function valueOrNull (value) {
    if(value === undefined || value === null || value === '' || Number.isNaN(value)){
      return null;
    }else{
      return value;
    }
  }
  
  // ------------ 送出表單 -----------------
  document.getElementById('apply-btn').addEventListener('click', function(){
    const file = courseImg.files[0];
    const formData = new FormData();
    formData.append('file', file);

    const ruleLists = document.querySelectorAll('#rule-container .rule-list');
    const rules = [];
    ruleLists.forEach(function(rule){
      const weekday = parseInt(rule.querySelectorAll('select')[0].value);
      const timeSlot = parseInt(rule.querySelectorAll('select')[1].value);
      rules.push(
        {
          weekday: valueOrNull(weekday),
          timeSlot: valueOrNull(timeSlot)
        }
      )
    });

    function callNewCourse(imgUrl) {
      const course = {
        title: valueOrNull(title.value),
        category: valueOrNull(category.value),
        roomId: valueOrNull(roomId.value),
        sessionQuota: valueOrNull(sessionQuota.value),
        capacityMax: valueOrNull(capacityMax.value),
        dateStart: valueOrNull(dateStart.value).replaceAll('-', '/'),
        dateEnd: valueOrNull(dateEnd.value).replaceAll('-', '/'),
        coursePrice: valueOrNull(coursePrice.value),
        description: valueOrNull(description.value),
        imgUrl
      };

      fetch('newCourse', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          course,
          rules
        }),
      })
      .then(resp => resp.json())
      .then(body => {
        if(body.successful){
          Swal.fire({
            title: '完成',
            text: body.message,
            icon: 'success',
            target: document.body 
          });
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
  
    if(!file){
      Swal.fire({
        title: '錯誤',
        text: '未選取上傳圖片',
        icon: 'error',
        target: document.body 
      });
    }else{
      fetch('img', {
        method: 'POST',
        body: formData
      })
      .then(resp => resp.json())
      .then(body => {
        if(body.success){
          callNewCourse(body.url);
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
  });

  // ------------ 新增規則 -----------------
  const addRuleBtn = document.getElementById('rule-btn');
  var ruleCount = 2;

  addRuleBtn.addEventListener('click', function(){
    let rule_html = `
    <hr class="my-6 mx-n6">
    <div class="rule-list">
      <h6>每週課程規則${ruleCount}</h6>
      <div class="mb-6">
        <label class="form-label">星期</label>
        <select class="select2 form-select" data-allow-clear="true">
          <option value="">請選擇</option>
          <option value="1">星期一</option>
          <option value="2">星期二</option>
          <option value="3">星期三</option>
          <option value="4">星期四</option>
          <option value="5">星期五</option>
          <option value="6">星期六</option>
          <option value="7">星期日</option>
        </select>
      </div>
      <div class="mb-6">
        <label class="form-label">時段</label>
        <select class="select2 form-select" data-allow-clear="true" type="time">
          <option value="">請選擇</option>
          <option value="1">8:00 ~ 9:00</option>
          <option value="2">9:00 ~ 10:00</option>
          <option value="3">10:00 ~ 11:00</option>
          <option value="4">11:00 ~ 12:00</option>
          <option value="5">12:00 ~ 13:00</option>
          <option value="6">13:00 ~ 14:00</option>
          <option value="7">14:00 ~ 15:00</option>
          <option value="8">15:00 ~ 16:00</option>
          <option value="9">16:00 ~ 17:00</option>
          <option value="10">17:00 ~ 18:00</option>
          <option value="11">18:00 ~ 19:00</option>
          <option value="12">19:00 ~ 20:00</option>
          <option value="13">20:00 ~ 21:00</option>
        </select>
      </div>
    </div>
    `;
    document.querySelector('#rule-container').insertAdjacentHTML('beforeend', rule_html);
    ruleCount++;
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
});