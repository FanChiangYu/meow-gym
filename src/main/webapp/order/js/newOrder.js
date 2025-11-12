let courseList;
let orderItemList;

function valueOrNull (value) {
	if(value === undefined || value === null || value === ''){
		return null;
	}else{
		return value;
	}
}

//我的購物車//
const course_title = document.querySelector('#course_title');
const course_coachName = document.querySelector('#course_coachName');
const course_dateStart = document.querySelector('#course_dateStart');
const course_dateEnd = document.querySelector('#course_dateEnd');
const course_capacityMax = document.querySelector('#course_capacityMax');
const course_promoPrice = document.querySelector('#course_promoPrice');
const course_coursePrice = document.querySelector('#course_coursePrice');
const myCart = document.querySelector('#myCart');

addCart();
function addCart(){
	fetch('addCart')
	.then(resp => resp.json())
	.then(body => {
		courseList = body.Course;
		orderItemList = body.Orderitems;
		
		for (let course of courseList) {
			myCart.innerHTML += 
			`
				<li class="list-group-item p-6">
						<div class="d-flex gap-4">
							<div class="flex-shrink-0 d-flex align-items-center">
									<img src="${course.imgUrl}" class="w-px-100" />
							</div>
							<div class="flex-grow-1">
									<div class="row">
										<div class="col-md-8">
												<p class="me-3 mb-2">
													<a href="javascript:void(0)" class="fw-medium">
													<span class="text-heading" id="course_title">${course.title}</span></a>
												</p>
												<div
													class="read-only-ratings raty mb-2"
													data-read-only="true"
													data-score="4"
													data-number="5"></div>
												<div class="text-body-secondary mb-2 d-flex flex-wrap">
													<span class="me-1" >教練：</span>
													<a href="javascript:void(0)" class="me-4" id="course_coachName">${course.coachName}</a>
												</div>
												<div class="text-body-secondary mb-2 d-flex flex-wrap">
													<span class="me-1" >開課日：</span>
													<a href="javascript:void(0)" class="me-4" id="course_dateStart">${course.dateStart}</a>
												</div>
												<div class="text-body-secondary mb-2 d-flex flex-wrap">
													<span class="me-1" >完課日：</span>
													<a href="javascript:void(0)" class="me-4" id="course_dateEnd">${course.dateEnd}</a>
												</div>
												<div class="text-body-secondary mb-2 d-flex flex-wrap">
													<span class="me-1" >課堂額度：</span>
													<a href="javascript:void(0)" class="me-4" id="course_capacityMax">${course.capacityMax}</a>
												</div>
										</div>
										<div class="col-md-4">
												<div class="text-md-end">
													<button type="button" class="btn-close btn-pinned" aria-label="Close" onclick="deleteCourse(${course.courseId})"></button>
													<div class="my-2 mt-md-6 mb-md-4">
															<span class="text-primary" id="course_promoPrice">${course.promoPrice}</span>
															<span class="text-primary" id="course_coursePrice">${course.coursePrice}</span>
													</div>
												</div>
										</div>
									</div>
							</div>
						</div>
				</li>
			`;
		}
	});
}

//刪除課程//
function deleteCourse(courseId){
	const orderItemId = orderItemList.find(e => e.courseId === courseId).orderItemId;

	fetch('deleteCart', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({
			orderItemId: valueOrNull(orderItemId)
		}),
	})
	.then(resp => resp.json())
	.then(body => {
		if(body.successful){
			// alert(body.message);
			Swal.fire({ //Swal.fire() SweetAlert2 的主函式，用來顯示彈出視窗。
				title: '完成',
				text: body.message, //彈窗的主要文字內容
				icon: 'success', //彈窗的圖示類型
				target: document.body //決定彈窗要插入到哪個 DOM 元素裡。預設是整個 <body>，這裡明確指定為 document.body。
			});
			location.reload();
		}else{
			// alert(body.message,);
			Swal.fire({
				title: '錯誤',
				text: body.message,
				icon: 'error',
				target: document.body 
			});
		}
	});
	
	// if(e.target.classList.contains("delete_btn")){
	//   //console.log("delete");
	//   let r = confirm("確認移除？");
	//   if (r){
	// 	  e.target.closest("li").classList.add("fade_out");
	// 	  setTimeout(function(){
	// 	  e.target.closest("li").remove();
	// 	  }, 1000);      
	//   }
	// }
}

//課程結帳清單//
const title = document.querySelector('#title');
const promoPrice = document.querySelector('#coursePromoPrice');
const coursePrice = document.querySelector('#coursePrice');
const payAmount = document.querySelector('#pay_amount');
const payCourseList = document.querySelector('#payCourseList');
const totalAmount = document.querySelector('#totalAmount');

payAmountList();
function payAmountList(){
	fetch('payAmount')
	.then(resp => resp.json())
	.then(body => {
		orders = body.Orders;
		orderitemList = body.Orderitems;
		for (let orderItem of orderitemList) {
			payCourseList.innerHTML += 
			`
			<dl class="row mb-0 text-heading" id="payCourseList">
				<dt class="col-6 fw-normal" id="title">${orderItem.title}</dt>
				<dd class="col-6 text-end" id="coursePromoPrice">${orderItem.promoPrice}</dd>
				<dd class="col-6 text-end" id="coursePrice">${orderItem.purchasedPrice}</dd>
			</dl>
			`;
		}

		totalAmount.innerHTML += 
		`
		  <dt class="col-6 text-heading">總價</dt>
			<dd class="col-6 fw-medium text-end text-heading mb-0" id="pay_amount">${orders.payAmount}</dd>
		`;
	});
}

//付款//
const payOnCard = document.querySelector('#payOnCard');
const payOnCash = document.querySelector('#payOnCash');
const cardNumber = document.querySelector('#cardNumber');
const cardHolder = document.querySelector('#cardHolder');
const expYear = document.querySelector('#expYear');
const expMonth = document.querySelector('#expMonth');
const cvc = document.querySelector('#ccc');

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
			Swal.fire({
				title: '完成',
				text: body.message,
				icon: 'success', 
				target: document.body
			});
		}else{
			// alert(body.message,);
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

//結帳確認//
