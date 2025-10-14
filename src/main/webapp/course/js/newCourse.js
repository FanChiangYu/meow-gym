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
      <h6>課程規則${ruleCount}</h6>
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