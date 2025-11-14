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
	const orderItemId = orderItemList.find(e => e.courseId === courseId).orderItemId; //找時間弄懂

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
}

//課程結帳清單//
const title = document.querySelector('#title');
const promoPrice = document.querySelector('#coursePromoPrice');
const coursePrice = document.querySelector('#coursePrice');
const payAmount = document.querySelector('#pay_amount');
const orderPayCourseList = document.querySelector('#orderPayCourseList');
const paymentPayCourseList = document.querySelector('#paymentPayCourseList');
const orderTotalAmount = document.querySelector('#orderTotalAmount');
const paymentTotalAmount = document.querySelector('#paymentTotalAmount');

payAmountList();
function payAmountList(){
	fetch('payAmount')
	.then(resp => resp.json())
	.then(body => {
		payAmount_orders = body.Orders;
		payAmount_orderItemList = body.Orderitems;
		for (let orderItem of payAmount_orderItemList) {
			orderPayCourseList.innerHTML += 
			`
			<dl class="row mb-0 text-heading" id="orderPayCourseList">
				<dt class="col-6 fw-normal" id="title">${orderItem.title}</dt>
				<dd class="col-6 text-end" id="coursePrice">${orderItem.purchasedPrice}</dd>
			</dl>
			`;

			paymentPayCourseList.innerHTML += 
			`
			<dl class="row mb-0 text-heading" id="paymentPayCourseList">
				<dt class="col-6 fw-normal" id="title">${orderItem.title}</dt>
				<dd class="col-6 text-end" id="coursePrice">${orderItem.purchasedPrice}</dd>
			</dl>
			`;
		}

		orderTotalAmount.innerHTML += 
		`
		  <dt class="col-6 text-heading">總價</dt>
			<dd class="col-6 fw-medium text-end text-heading mb-0" id="orderTotalAmount">${payAmount_orders.payAmount}</dd>
		`;

		paymentTotalAmount.innerHTML += 
		`
		  <dt class="col-6 text-heading mb-3">總價</dt>
			<dd class="col-6 fw-medium text-end text-heading mb-0" id="paymentTotalAmount">${payAmount_orders.payAmount}</dd>
		`;
	});
}

//付款//
const payOnCard = document.querySelector('#payOnCard');
const payOnCash = document.querySelector('#payOnCash');
const cardNumber = document.querySelector('#cardNumber');
const cardHolder = document.querySelector('#cardHolder');
const paymentCardExpiryDate = document.querySelector('#paymentCardExpiryDate');
let expYear = paymentCardExpiryDate.toString().slice(0, 2);
let expMonth = paymentCardExpiryDate.toString().slice(-2);
// const expYear = document.querySelector('#expYear');
// const expMonth = document.querySelector('#expMonth');
const cvc = document.querySelector('#cvc');

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
			paymentMethod: 'Cash'
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
const confirmation_payment = document.querySelector('#confirmation_payment');
const confirmation_title = document.querySelector('#confirmation_title');
const confirmation_coachName = document.querySelector('#confirmation_coachName');
const confirmation_dateStart = document.querySelector('#confirmation_dateStart');
const confirmation_dateEnd = document.querySelector('#confirmation_dateEnd');
const confirmation_capacityMax = document.querySelector('#confirmation_capacityMax');
const confirmation_promoPrice = document.querySelector('#confirmation_promoPrice');
const confirmation_coursePrice = document.querySelector('#confirmation_coursePrice');
const confirmation_course = document.querySelector('#confirmation_course');

const confirmPayCourseList = document.querySelector('#confirmPayCourseList');
const confirmTotalAmount = document.querySelector('#confirmTotalAmount');

confirmation();
function confirmation(){
	fetch('confirmation')
	.then(resp => resp.json())
	.then(body => {
		userEmail = body.User;
		orderDetail = body.Orders;
		confirmation_courseList = body.Course;
		confirmation_orderItemList = body.Orderitems;

		confirmation_payment.innerHTML += 
		`
		<li class="list-group-item flex-fill p-6 text-body">
			<h6 class="d-flex align-items-center gap-2">會員付款資訊</h6>
			<p class="mb-0 mt-4">
				<i class="icon-base ti tabler-file-dollar"></i>訂單編號 : <span>${orderDetail.orderId}</span>
			</p>
			<p class="mb-0 mt-4">
				<i class="icon-base ti tabler-clock me-1 text-heading"></i>訂單成立時間 : &nbsp;<span>${orderDetail.createdAt}</span>
			</p>
			<p class="mb-0 mt-4">
				<i class="icon-base ti tabler-credit-card"></i>付款方式 : <span>${orderDetail.paymentMethod}</span>
			</p>
			<p class="mb-0 mt-4">
				<i class="icon-base ti tabler-mail"></i>信箱 : <span>${userEmail}</span>
			</p>  
		</li>
		`;

		for (let course of confirmation_courseList) {
			confirmation_course.innerHTML += 
			`
			<li class="list-group-item p-6">
				<div class="d-flex gap-4">
					<div class="flex-shrink-0">
						<img src="${course.imgUrl}" alt="google home" class="w-px-80" />
					</div>
					<div class="flex-grow-1">
						<div class="row">
							<div class="col-md-8">
								<a href="javascript:void(0)">
									<h6 class="mb-2" id="confirmation_title">${course.title}</h6>
								</a>
								<div class="text-body mb-2 d-flex flex-wrap">
									<span class="me-1" id="confirmation_coachName">教練：</span> <a href="javascript:void(0)">${course.coachName}</a>
								</div>
								<div class="text-body mb-2 d-flex flex-wrap">
									<span class="me-1" id="confirmation_dateStart">開課日：</span> <a href="javascript:void(0)">${course.dateStart}</a>
								</div>
								<div class="text-body mb-2 d-flex flex-wrap">
									<span class="me-1" id="confirmation_dateEnd">完課日：</span> <a href="javascript:void(0)">${course.dateEnd}</a>
								</div>
								<div class="text-body mb-2 d-flex flex-wrap">
									<span class="me-1">課堂額度：</span> <a href="javascript:void(0)" id="confirmation_capacityMax">${course.capacityMax}</a>
								</div>                           
							</div>
							<div class="col-md-4">
								<div class="text-md-end">
									<div class="my-2 my-lg-6">
										<span class="text-primary" id="confirmation_promoPrice">${course.promoPrice}</span>
										<span class="text-primary" id="confirmation_coursePrice">${course.coursePrice}</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</li>		
			`;
		}

		for (let orderItem of confirmation_orderItemList) {
			confirmPayCourseList.innerHTML += 
			`
			<dl class="row mb-0 text-heading" id="confirmPayCourseList">
				<dt class="col-6 fw-normal" id="title">${orderItem.title}</dt>
				<dd class="col-6 text-end" id="coursePrice">${orderItem.purchasedPrice}</dd>
			</dl>
			`;
		}

		confirmTotalAmount.innerHTML += 
		`
		  <dt class="col-6 text-heading">總價</dt>
			<dd class="col-6 fw-medium text-end text-heading mb-0" id="confirmTotalAmount">${orderDetail.payAmount}</dd>
		`;
	});
}


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