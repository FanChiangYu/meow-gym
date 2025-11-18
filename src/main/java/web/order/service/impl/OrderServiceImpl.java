package web.order.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.course.pojo.Course;
import web.course.service.CourseService;
import web.order.dao.OrderDao;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;
import web.order.service.OrderService;
import web.promotions.pojo.CoursePromo;
import web.user.pojo.User;

@Service
public class OrderServiceImpl implements OrderService{	
	@Autowired
	private OrderDao orderdao;
	@Autowired
	private CourseService courseService;
	
	//標註需要交易控制的⽅法
	@Transactional
	@Override
	public Boolean addcart(Course course, Integer userId) {
		//比對訂單course資訊		
//		if(course.getCourseId() == null) {
//			System.out.println("取得訂單course資訊失敗");
//			return false;
//		}
		System.out.println(course.getCourseId());
		
		//邏輯：產生Orders By userId/
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "PENDING");
		if (orderId == null) {
			Orders orders = new Orders(); 
			orders.setUserId(userId);
			orders.setPayAmount(0);
			orders.setStatus("PENDING");
			orders.setPaymentMethod("PENDING");
			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			orders.setCreatedAt(timestamp);
			int count = orderdao.insert(orders);
			if(count != 1) {
				System.out.println("產生Order失敗");
				return false;
			}
			orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "PENDING");	
		}
		//邏輯：執行儲存orderitems資料交易控制
		Orderitems orderitems = new Orderitems();
		orderitems.setOrderId(orderId);
		orderitems.setCourseId(course.getCourseId());
		Integer purchasedPrice = orderdao.selectCoursePriceByCourseId(course.getCourseId()); 
		orderitems.setPurchasedPrice(purchasedPrice);
		int count1 = orderdao.insert(orderitems);
		if(count1 == 1) {
			System.out.println("儲存orderitems資料成功");
			return true;
		} else {
			System.out.println("儲存orderitems資料失敗");
			return false;
		}
	}
	
	@Transactional	
	@Override
	public Map<String, Object> getAllOrderitemsAndCourseByUserId(Integer userId) {
		//Step1:用userId找orderId by Orders
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "PENDING");
		//Step2:找同筆訂單下所有CourseID
		List<Orderitems> orderitemsList = orderdao.selectOrderitemsListByOrderId(orderId);
		//Step3:藉由courseID 去撈course.class
		List<Integer> courseIdList = orderitemsList.stream().map(item -> item.getCourseId()).collect(Collectors.toList());
		List<Course> courseList = orderdao.selectCourseListByOrderitemsCourseIdList(courseIdList);
		//Step4:跑foreach 放入 coachname
		for(Course course : courseList) {
			String coachName = courseService.findName(course);
			course.setCoachName(coachName);
		}
		//Step5:跑foreach 放入 coursePromo
		for(Course course : courseList) {
			Integer courseId = course.getCourseId();
			CoursePromo coursePromo = orderdao.selectCoursePromoByCourseId(courseId);
			//Step6:決定回傳課程價錢(確認是否為促銷區間)
			if (coursePromo != null) {
				Date today = new Date();
				if((today.after(coursePromo.getDateStart()) || today.equals(coursePromo.getDateStart())) &&
					    (today.before(coursePromo.getDateEnd()) || today.equals(coursePromo.getDateEnd()))) {
					course.setPromoPrice(coursePromo.getPromoPrice());
				}
			}
		}
		//Step7:回傳Orderitems and Course
		Map<String, Object> orderitemsAndCourseList = new HashMap<>();
		orderitemsAndCourseList.put("Orderitems", orderitemsList);
		orderitemsAndCourseList.put("Course", courseList);
		return orderitemsAndCourseList;
	}
	
	@Transactional	
	@Override
	public boolean deletecoursefromcart(Integer orderItemId, Integer userId) {
		//Step1:確認and刪除orderitems的課程資訊
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "PENDING");
		int count1 = orderdao.deleteOrderitemsByOrderItemId(orderItemId); //需修改 因從前端拿Orderitems
		if(count1 == 1) {
			System.out.println("Delete course in DB success.");
			//Step2:比對orderitems與orders
			List<Orderitems> orderitemList = orderdao.selectOrderitemsListByOrderId(orderId);
			if (orderitemList == null || orderitemList.isEmpty()) {
				orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "PENDING");
				int count2 = orderdao.modifyStatusByUesrIdAndOrderIdAndStatus(orderId, "CANCEL"); 
				if(count2 == 1) {
					System.out.println("orderitems不存在 orders updatestatus_CANCEL成功");
				}else {
					System.out.println("orderitems存在 orders updatestatus_CANCEL失敗");
				}			
			}
			return true;
		}else {
			System.out.println("Delete course in DB fail, pls check!");
			return false;
		}
	}
	
	@Transactional
	@Override
	public Map<String, Object> getPayAmountListByUserId(Integer userId) {
		//Step1:確認Orders and Orderitems 的 orderId
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "PENDING");
		//Step2:撈List<Orderitems> by orderId
		List<Orderitems> orderitemList = orderdao.selectOrderitemsListByOrderId(orderId);
		//Step3:回傳Orders and List<Orderitems> payAmountList
		Integer totalAmount = 0;
		Orders orders = orderdao.selectOrdersByOrderId(orderId);
		for (Orderitems orderitems : orderitemList) {
			Integer courseId = orderitems.getCourseId();
			Course course = orderdao.selectCourseByCourseId(courseId);
			CoursePromo coursePromo = orderdao.selectCoursePromoByCourseId(courseId);
			orderitems.setTitle(course.getTitle());
			orderitems.setCoursePrice(course.getCoursePrice());
			//Step4:決定回傳課程價錢(確認是否為促銷區間)
			Date today = new Date();			
			if(coursePromo != null) {
				if((today.after(coursePromo.getDateStart()) || today.equals(coursePromo.getDateStart())) &&
					    (today.before(coursePromo.getDateEnd()) || today.equals(coursePromo.getDateEnd()))) {
					orderitems.setDateStart(coursePromo.getDateStart());
					orderitems.setDateEnd(coursePromo.getDateEnd());
					orderitems.setPromoPrice(coursePromo.getPromoPrice());
				}
			}
			if(orderitems.getPromoPrice() != null) {
				orderitems.setPurchasedPrice(orderitems.getPromoPrice());
			}else {
				orderitems.setPurchasedPrice(orderitems.getCoursePrice());
			}
			//Step5:計算payAmount and 回傳Orders payAmount (決定回傳課程總價)
			Integer purchasedPrice = orderitems.getPurchasedPrice();
			totalAmount += purchasedPrice;
			orders.setPayAmount(totalAmount); 
		}
		//Step6:回傳個課程價格及購課總價
		Map<String, Object> payAmountList = new HashMap<>();
		payAmountList.put("Orders", orders);
		payAmountList.put("Orderitems", orderitemList);
		return payAmountList;
	}

	@Transactional
	@Override
	public Orders payment(Orders orders, Integer userId) {
		//Step1:判斷信用卡 or 現金付款, 使用信用卡比對前端付款資訊
		if(orders.getPaymentMethod().equals("Card")) {
			if(orders.getCardNumber() == null) {
				orders.setMessage("信用卡卡號未輸入");
				orders.setSuccessful(false);
				return orders;
			}
			
			if(orders.getCardHolder() == null) {
				orders.setMessage("未輸入持卡人姓名");
				orders.setSuccessful(false);
				return orders;
			}
			
			if(orders.getExpDate() == null) {
				orders.setMessage("未輸入有效年月");
				orders.setSuccessful(false);
				return orders;
			}
			
			if(orders.getCvc() == null) {
				orders.setMessage("未輸入信用卡驗證碼");
				orders.setSuccessful(false);
				return orders;
			}
		}		
		System.out.println(orders.getPaymentMethod());
		System.out.println(orders.getCardNumber());
		System.out.println(orders.getCardHolder());
		System.out.println(orders.getExpDate());
		System.out.println(orders.getCvc());
		
		//Step2:確認orderId by userId and status
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "PENDING");
		//Step3:select Orders by orderId, 依據PaymentMethod寫入DB資料
		Orders payOrder = orderdao.selectOrdersByOrderId(orderId);
		if(orders.getPaymentMethod().equals("Cash")) {
			payOrder.setPaymentMethod(orders.getPaymentMethod());
			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			payOrder.setCreatedAt(timestamp);
			//Step4-1:現金付款執行資料交易控制
			int count1 = orderdao.insert(payOrder);
			if(count1 == 1) {
				payOrder.setMessage("送出成功");
				payOrder.setSuccessful(true);
				int count2 = orderdao.modifyStatusByUesrIdAndOrderIdAndStatus(orderId, "WAIT_PAID"); 
				if(count2 == 1) {
					System.out.println("orders updatestatus_WAIT_PAID成功");
				}else {
					System.out.println("orders updatestatus_WAIT_PAID失敗");
				}	
			} else {
				payOrder.setMessage("送出失敗");
				payOrder.setSuccessful(false);
			}
			return payOrder;			
		}else {
			payOrder.setPaymentMethod(orders.getPaymentMethod());
			payOrder.setCardNumber(orders.getCardNumber());
			payOrder.setCardHolder(orders.getCardHolder());
			payOrder.setExpDate(orders.getExpDate());
			payOrder.setCvc(orders.getCvc());
			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			payOrder.setCreatedAt(timestamp);
			//Step4-2:信用卡付款執行資料交易控制
			int count1 = orderdao.insert(payOrder);
			if(count1 == 1) {
				payOrder.setMessage("送出成功");
				payOrder.setSuccessful(true);
				int count2 = orderdao.modifyStatusByUesrIdAndOrderIdAndStatus(orderId, "PAID"); 
				if(count2 == 1) {
					System.out.println("orders updatestatus_PAID成功");
				}else {
					System.out.println("orders updatestatus_PAID失敗");
				}	
			} else {
				payOrder.setMessage("送出失敗");
				payOrder.setSuccessful(false);
			}
			return payOrder;
		}
	}

	@Transactional	
	@Override
	public Map<String, Object> getOrderConfirmation(Integer userId) {
		//Step1:用userId找orderId by Orders
		Integer orderId = orderdao.selectOrderIdAfterPaymentByUesrId(userId);
		//Step2:撈Orders by orderId
		Orders completeOrders = orderdao.selectOrdersByOrderId(orderId);		
		//Step3:撈Email by userId
		String userEmail = orderdao.selectUserEmailByUserId(userId);
		//Step4:找同筆訂單下所有CourseID
		List<Orderitems> completeOrderitemsList = orderdao.selectOrderitemsListByOrderId(orderId);
		//Step5:回傳List<Orderitems> payAmountList
		for (Orderitems orderitems : completeOrderitemsList) {
			Integer courseId = orderitems.getCourseId();
			Course course = orderdao.selectCourseByCourseId(courseId);
			orderitems.setTitle(course.getTitle());
		}
		//Step6:藉由courseID 去撈course.class
		List<Integer> courseIdList = completeOrderitemsList.stream().map(item -> item.getCourseId()).collect(Collectors.toList());
		List<Course> completeCourseList = orderdao.selectCourseListByOrderitemsCourseIdList(courseIdList);
		//Step7:跑foreach 放入 coachname
		for(Course course : completeCourseList) {
			String coachName = courseService.findName(course);
			course.setCoachName(coachName);
		}
		//Step8:跑foreach 放入 coursePromo
		for(Course course : completeCourseList) {
			Integer courseId = course.getCourseId();
			CoursePromo coursePromo = orderdao.selectCoursePromoByCourseId(courseId);
			//Step6:決定回傳課程價錢(確認是否為促銷區間)
			if (coursePromo != null) {
				Date today = new Date();
				if((today.after(coursePromo.getDateStart()) || today.equals(coursePromo.getDateStart())) &&
					    (today.before(coursePromo.getDateEnd()) || today.equals(coursePromo.getDateEnd()))) {
					course.setPromoPrice(coursePromo.getPromoPrice());
				}
			}
		}
		//Step9:回傳userEmail, Orders, Orderitems and Course
		Map<String, Object> orderConfirmation = new HashMap<>();
		orderConfirmation.put("User", userEmail);
		orderConfirmation.put("Orders", completeOrders);
		orderConfirmation.put("Orderitems", completeOrderitemsList);
		orderConfirmation.put("Course", completeCourseList);
		return orderConfirmation;		
	}

	@Override
	public Map<String, Object> getAllShoppingRecordListByUserId(Integer userId) {
		//Step1:撈OrderList by userId
		List<Orders> shoppingRecordOrders = orderdao.selectShoppingRecordOrdersByUserId(userId);
		//Step2:撈OrderItemsList by OrdersList
		List<Integer> orderIdList = shoppingRecordOrders.stream().map(item -> item.getOrderId()).collect(Collectors.toList());
		List<Orderitems> shoppingRecordOrderItemsList = orderdao.selectOrderitemListByOrderIdList(orderIdList);
		for (Orderitems orderitems : shoppingRecordOrderItemsList) {
			Integer courseId = orderitems.getCourseId();
			Course course = orderdao.selectCourseByCourseId(courseId);
			orderitems.setTitle(course.getTitle());
		}
		//Step3:OrderItemsList打包入OrdersList
		for (Orders orders : shoppingRecordOrders) {
			orders.setOrderitems(shoppingRecordOrderItemsList);
		}
		//Step4:回傳Orders, Orderitems
		Map<String, Object> shoppingRecordList = new HashMap<>();
		shoppingRecordList.put("Orders", shoppingRecordOrders);
		shoppingRecordList.put("Orderitems", shoppingRecordOrderItemsList);	
		return shoppingRecordList;
	}

	@Override
	public Map<String, Object> getAllCashOrderList() {
		//Step1:撈OrderList by userId
		List<Orders> cashOrders = orderdao.selectCashOrdersByStatus("WAIT_PAID");
		//Step2:撈OrderItemsList by OrdersList
		List<Integer> orderIdList = cashOrders.stream().map(item -> item.getOrderId()).collect(Collectors.toList());
		List<Orderitems> cashOrderItemsList = orderdao.selectOrderitemListByOrderIdList(orderIdList);
		for (Orderitems orderitems : cashOrderItemsList) {
			Integer courseId = orderitems.getCourseId();
			Course course = orderdao.selectCourseByCourseId(courseId);
			orderitems.setTitle(course.getTitle());
		}
		//Step4:跑foreach 放入 userName/userEmail, OrderItemsList打包入OrdersList
		for (Orders orders : cashOrders) {
			orders.setOrderitems(cashOrderItemsList);
			Integer userId = orderdao.selectUserIdByOrderId(orders.getOrderId());
			User user = orderdao.selectUserByUserId(userId);
			orders.setName(user.getName());
			orders.setEmail(user.getEmail());
		}
		//Step4:回傳Orders
		Map<String, Object> cashOrderList = new HashMap<>();
		cashOrderList.put("Orders", cashOrders);
		cashOrderList.put("Orderitems", cashOrderItemsList);
		return cashOrderList;
	}

	@Override
	public Boolean changeOrderStatusForPaymentByCash(Integer orderId, Integer userId) {
		//Step1:確認需更改狀態的orderID
		Integer changeStatusOrderId = orderdao.selectChangeStatusOrderIdByUesrIdAndOrderId(userId, orderId);
		System.out.println(changeStatusOrderId);
		//Step2:更改狀態為PAID
		int count1 = orderdao.modifyStatusByUesrIdAndOrderIdAndStatus(changeStatusOrderId, "PAID"); 
		if(count1 == 1) {
			System.out.println("orders updatestatus_PAID成功");
			Orders changeStatusOrder = orderdao.selectOrdersByOrderId(changeStatusOrderId);
			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			changeStatusOrder.setCreatedAt(timestamp);
			int count2 = orderdao.insert(changeStatusOrder);
			if(count2 == 1) {
				System.out.println("orders updateCreatedAt成功");
				return true;
			} else {
				System.out.println("orders updateCreatedAt失敗");
				return false;
			}
		}else {
			System.out.println("orders updatestatus_PAID失敗");
			return false;
		}
	}
}