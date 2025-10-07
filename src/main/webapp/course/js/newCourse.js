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
  if(value === undefined || value === null || value === ''){
    return null;
  }else{
    return value;
  }
}

document.getElementById('apply-btn').addEventListener('click', function(){
  const file = courseImg.files[0];
  const formData = new FormData();
  formData.append('file', file);

  function callNewCourse(imgUrl) {
    fetch('newCourse', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
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