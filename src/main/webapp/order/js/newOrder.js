//我的購物車//
const course_title = document.querySelector('#course_title');
const course_coachName = document.querySelector('#course_coachName');
const course_dateStart = document.querySelector('#course_dateStart');
const course_dateEnd = document.querySelector('#course_dateEnd');
const course_capacityMax = document.querySelector('#course_capacityMax');
const course_coursePrice = document.querySelector('#course_coursePrice');
// document.addEventListener('load', addCart);
addCart();
function addCart(){
fetch('addCart')
	.then(resp => resp.json())
	.then(courseList => {
		for (let course of courseList) {
			course_title.innerHTML += `
				<p>
					<a>
						<span>${course.title}</span>
					</a>	
				</p>
			`;
			course_coachName.innerHTML += `
				<div>
					<a>${course.coachName}</a>
				</div>
			`;
			course_dateStart.innerHTML += `
				<div>
					<a>${course.dateStart}</a>
				</div>
			`;
			course_dateEnd.innerHTML += `
				<div>
					<a>${course.dateEnd}</a>
				</div>
			`;
			course_capacityMax.innerHTML += `
				<div>
					<a>${course.capacityMax}</a>
				</div>
			`;
			course_coursePrice.innerHTML += `
				<div>
					<s>${course.coursePrice}</s>
				</div>
			`;
		}
	});
}

//確認付款內容//
const payOnCard = document.querySelector('#payOnCard');
const payOnCash = document.querySelector('#payOnCash');
const cardNumber = document.querySelector('#cardNumber');
const cardHolder = document.querySelector('#cardHolder');
const expYear = document.querySelector('#expYear');
const expMonth = document.querySelector('#expMonth');
const cvc = document.querySelector('#ccc');

function valueOrNull (value) {
  if(value === undefined || value === null || value === ''){
    return null;
  }else{
    return value;
  }
}

document.getElementById('payOnCard').addEventListener('click', paymentByCard);
function paymentByCard(){  
  fetch('payment', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      paymentMethod: 'Card',
      cardHolder: valueOrNull(cardHolder.value),
      cardNumber: valueOrNull(cardNumber.value),
      expYear: valueOrNull(expYear.value),
      expMonth: valueOrNull(expMonth.value),
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
};

document.getElementById('payOnCash').addEventListener('click', paymentByCash);
function paymentByCash(){
  fetch('payment', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      paymentMethod: 'cash'
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
};

//結帳確認
//const orderId = document.querySelector('#order_id');
//const userId = document.querySelector('#user_id');
//const payAmount = document.querySelector('#pay_amount');
//const cardNumber = document.querySelector('#paymentCard');
//const cardHolder = document.querySelector('#paymentCardName');
//const expYear = document.querySelector('#paymentCardExpiryDate');
//const expMonth = document.querySelector('#paymentCardExpiryDate');
//const cvc = document.querySelector('#paymentCardCvv');
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
//        text: body.message,
//        icon: 'error',
//        target: document.body 
//      });
//    }
//  });
//});