//我的購物車//
const title = document.querySelector('#title');
const coach = document.querySelector('#name');
const date = document.querySelector('#date');
const capacityMax = document.querySelector('#capacity-max');

fetch('newOrder')
	.then(resp => resp.json())
	.then(neworders => {
		for (let neworder of neworders) {
			span.innerHTML = `
				<a>
					<span>${orders.courseId}</span>
				</a>
			`;
			a.innerHTML += `
				<a>
					<span>${orders.coachId}</span>
				</a>
			`;
			a.innerHTML += `
				<a>
					<span>${orders.date}</span>
				</a>
			`;
			a.innerHTML += `
				<a>
					<span>${orders.capacityMax}</span>
				</a>
			`;
		}
	});

//確認付款內容//
//const pills-cc-tab = document.querySelector('#pills-cc-tab');//
//const pills-cod-tab = document.querySelector('#pills-cod-tab');//
const payOnCard = document.querySelector('#payOnCard');
const payOnCash = document.querySelector('#payOnCash');

const order_id = document.querySelector('#order_id');
const user_id = document.querySelector('#user_id');
const pay_amount = document.querySelector('#pay_amount');

const card_number = document.querySelector('#paymentCard');
const card_holder = document.querySelector('#paymentCardName');
const exp_year = document.querySelector('#paymentCardExpiryDate');
const exp_month = document.querySelector('#paymentCardExpiryDate');
const cvc = document.querySelector('#paymentCardCvv');

function valueOrNull (value) {
  if(value === undefined || value === null || value === ''){
    return null;
  }else{
    return value;
  }
}

document.getElementById('payOnCard').addEventListener('click', function(){
  fetch('newOrder', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      //order_id: '99999',//
      //user_id: '1',//
      pay_amount: pay_amount.value,
      status: 'pending',
      pay_method: 'credit_card',
      card_holder: valueOrNull(card_holder.value),
      card_number: valueOrNull(card_number.value),
      exp_year: valueOrNull(exp_year.value),
      exp_month: valueOrNull(exp_month.value),
      cvc: valueOrNull(cvc.value)
    }),
  })
  .then(resp => resp.json())
  .then(body => {
    if(body.successful){
      // alert(body.message);
      Swal.fire({ //Swal.fire() SweetAlert2 的主函式，用來顯示彈出視窗。//
        title: '完成',
        text: body.message, //彈窗的主要文字內容//
        icon: 'success', //彈窗的圖示類型//
        target: document.body //決定彈窗要插入到哪個 DOM 元素裡。預設是整個 <body>，這裡明確指定為 document.body。//
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

document.getElementById('payOnCash').addEventListener('click', function(){
  fetch('newOrder', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      //order_id: 99998,//
      //user_id: 2,//
      pay_amount: pay_amount.value,
      status: 'pending',
      pay_method: 'cash',
      card_holder: 'user_id.value',
      card_number: '999999999',
      exp_year: 9999,
      exp_month: 99,
      cvc: 9999
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

//結帳確認
//const title = document.querySelector('#title');
//const category = document.querySelector('#category');
//const roomId = document.querySelector('#room-id');
//const sessionQuota = document.querySelector('#session-quota');
//const capacityMax = document.querySelector('#capacity-max');
//const dateStart = document.querySelector('#date-start');
//const dateEnd = document.querySelector('#date-end');
//const coursePrice = document.querySelector('#course-price');
//const description = document.querySelector('#description');

//function valueOrNull (value) {
//  if(value === undefined || value === null || value === ''){
//    return null;
//  }else{
//    return value;
//  }
//}

//document.getElementById('apply-btn').addEventListener('click', function(){
//  fetch('newCourse', {
//    method: 'POST',
//    headers: { 'Content-Type': 'application/json' },
//    body: JSON.stringify({
//      title: valueOrNull(title.value),
//      category: valueOrNull(category.value),
//      roomId: valueOrNull(roomId.value),
//      sessionQuota: valueOrNull(sessionQuota.value),
//      capacityMax: valueOrNull(capacityMax.value),
//      dateStart: valueOrNull(dateStart.value),
//      dateEnd: valueOrNull(dateEnd.value),
//      coursePrice: valueOrNull(coursePrice.value),
//      description: valueOrNull(description.value)
//    }),
//  })
//  .then(resp => resp.json())
//  .then(body => {
//    if(body.successful){
      // alert(body.message);
//      Swal.fire({
//        title: '完成',
//        text: body.message,
//        icon: 'success',
//        target: document.body 
//      });
//    }else{
      // alert(body.message);
//      Swal.fire({
//        title: '錯誤',
        text: body.message,
//        icon: 'error',
//        target: document.body 
//      });
//    }
//  });
});