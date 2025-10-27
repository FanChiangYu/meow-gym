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

  // 1a. 用fetch向後端取得roleId(角色ID)
  // 1b. 或從瀏覽器的sessionStorage取得roleId (如果登入時有存的話)
  // 2. 呼叫switchMenu(); 切換側邊欄顯示

  // roldId = 1 -> 一般會員
  // roldId = 2 -> 教練
  // roldId = 3 -> 管理者

  // 如果還沒寫取得roleId，先依照功能關聯對象寫死一個數值，代入並呼叫switchMenu();以切換側邊欄
  let Id = 2;

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
    console.log(rules);

    function callNewCourse(imgUrl) {
      const course = {
        title: valueOrNull(title.value),
        category: valueOrNull(category.value),
        roomId: valueOrNull(roomId.value),
        sessionQuota: valueOrNull(sessionQuota.value),
        capacityMax: valueOrNull(capacityMax.value),
        dateStart: valueOrNull(dateStart.value),
        dateEnd: valueOrNull(dateEnd.value),
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
          // alert(body.message);
          Swal.fire({
            title: '完成',
            text: body.message,
            icon: 'success',
            target: document.body 
          });
        }else{
          // alert(body.message);
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
      fetch('uploadFile', {
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
});