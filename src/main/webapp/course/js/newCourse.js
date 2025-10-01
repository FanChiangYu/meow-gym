const title = document.querySelector('#title');
const category = document.querySelector('#category');
const roomId = document.querySelector('#room-id');
const sessionQuota = document.querySelector('#session-quota');
const capacityMax = document.querySelector('#capacity-max');
const dateStart = document.querySelector('#date-start');
const dateEnd = document.querySelector('#date-end');
const coursePrice = document.querySelector('#course-price');
const description = document.querySelector('#description');

function valueOrNull (value) {
  if(value === undefined || value === null || value === ''){
    return null;
  }else{
    return value;
  }
}

document.getElementById('apply-btn').addEventListener('click', function(){
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
      description: valueOrNull(description.value)
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
});