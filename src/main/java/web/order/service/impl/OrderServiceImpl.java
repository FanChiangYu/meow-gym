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
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "pending");
		if (orderId == null) {
			Orders orders = new Orders(); 
			orders.setUserId(userId);
			orders.setPayAmount(0);
			orders.setStatus("pending");
			orders.setPaymentMethod("pending");
			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			orders.setCreatedAt(timestamp);
			int count = orderdao.insert(orders);
			if(count != 1) {
				System.out.println("產生Order失敗");
				return false;
			}
			orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "pending");	
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
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "pending");
		//Step2:找同筆訂單下所有CourseID
		List<Orderitems> orderitemsList = orderdao.selectOrderitemsListByOrderId(orderId);
		//Step3:藉由courseID 去撈course.class
		List<Integer> courseIdList = orderitemsList.stream().map(item -> item.getCourseId()).collect(Collectors.toList());
		List<Course> courseList = orderdao.selectCourseAndOrderitemListByOrderitems(courseIdList);
		//Step4:跑foreach 放入 coachname
		for(Course course : courseList) {
			String coachName = courseService.findName(course);
			course.setCoachName(coachName);
		}
		//Step5:跑foreach 放入 coursePromo
		for(Course course : courseList) {
			Integer courseId = course.getCourseId();
			CoursePromo coursePromo = orderdao.selectCoursePromoByCourseId(courseId);
			//Step6:決定回傳課程價錢(確認是否為促銷區間) debug促銷價跑掉
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
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "pending");
		int count1 = orderdao.deleteOrderitemsByOrderItemId(orderItemId); //需修改 因從前端拿Orderitems
		if(count1 == 1) {
			System.out.println("Delete course in DB success.");
			//Step2:比對orderitems與orders
			List<Orderitems> orderitemList = orderdao.selectOrderitemsListByOrderId(orderId);
			System.out.println(orderitemList);
			if (orderitemList == null) { //修改orders狀態為cancel, 需要debug進不來, 問老師
				orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "pending");
				int count2 = orderdao.modifyStatusByUesrIdAndOrderIdAndStatus(orderId, "cancel"); 
				if(count2 == 1) {
					System.out.println("orderitems不存在 orders updatestatus_cancel成功");
				}else {
					System.out.println("orderitems存在 orders updatestatus_cancel失敗");
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
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "pending");
		//Step2:撈List<Orderitems> by orderId
		List<Orderitems> orderitemList = orderdao.selectOrderitemsListByOrderId(orderId);
		//Step3:回傳Orders and List<Orderitems> payAmountList
		for (Orderitems orderitems : orderitemList) {
			Integer courseId = orderitems.getCourseId();
			Course course = orderdao.selectCourseByCourseId(courseId);
			CoursePromo coursePromo = orderdao.selectCoursePromoByCourseId(courseId);
			orderitems.setTitle(course.getTitle());
			orderitems.setCoursePrice(course.getCoursePrice());
			//Step4:決定回傳課程價錢(確認是否為促銷區間) debug促銷價跑掉
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

		}
		//Step5:計算payAmount and 回傳Orders payAmount (決定回傳課程總價)
		Orders orders = orderdao.selectOrdersByOrderId(orderId);
		Integer totalAmount = 0;
		for (Orderitems orderitems : orderitemList) {
		Integer purchasedPrice = orderitems.getPurchasedPrice();
		totalAmount += purchasedPrice;
		}
		orders.setPayAmount(totalAmount); 
		//Step6:回傳個課程價格及購課總價
		Map<String, Object> payAmountList = new HashMap<>();
		payAmountList.put("Orders", orders);
		payAmountList.put("Orderitems", orderitemList);
		return payAmountList;
	}

	@Transactional
	@Override
	public Orders payment(Orders orders, Integer userId) {
		//Step1:判斷信用卡 or 現金付款, 使用信用卡比對前端付款資訊  debug
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
			
			if(orders.getExpYear() == null) {
				orders.setMessage("未輸入有效年份");
				orders.setSuccessful(false);
				return orders;
			}
			
			if(orders.getExpMonth() == null) {
				orders.setMessage("未輸入有效月份");
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
		System.out.println(orders.getExpYear());
		System.out.println(orders.getExpMonth());
		System.out.println(orders.getCvc());
		
		//Step2:確認orderId by userId and status
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "pending");
		//Step3:select Orders by orderId, 並寫入DB資料
		Orders payOrder = orderdao.selectOrdersByOrderId(orderId);
		payOrder.setPaymentMethod(orders.getPaymentMethod());
		payOrder.setCardNumber(orders.getCardNumber());
		payOrder.setCardHolder(orders.getCardHolder());
		payOrder.setExpYear(orders.getExpYear());
		payOrder.setExpMonth(orders.getExpMonth());
		payOrder.setCvc(orders.getCvc());
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
		payOrder.setCreatedAt(timestamp);
		//Step4:執行資料交易控制
		int count1 = orderdao.insert(payOrder);
		if(count1 == 1) {
			payOrder.setMessage("送出成功");
			payOrder.setSuccessful(true);
			int count2 = orderdao.modifyStatusByUesrIdAndOrderIdAndStatus(orderId, "paid"); 
			if(count2 == 1) {
				System.out.println("orders updatestatus_paid成功");
			}else {
				System.out.println("orders updatestatus_paid失敗");
			}	
		} else {
			payOrder.setMessage("送出失敗");
			payOrder.setSuccessful(false);
		}
		return payOrder;
	}

	@Transactional	
	@Override
	public Map<String, Object> getOrderConfirmation(Integer userId) {
		//Step1:用userId找orderId by Orders
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "paid");
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
		List<Course> completeCourseList = orderdao.selectCourseAndOrderitemListByOrderitems(courseIdList);
		//Step7:跑foreach 放入 coachname
		for(Course course : completeCourseList) {
			String coachName = courseService.findName(course);
			course.setCoachName(coachName);
		}
		//Step8:回傳userEmail, Orders, Orderitems and Course
		Map<String, Object> orderConfirmation = new HashMap<>();
		orderConfirmation.put("User", userEmail);
		orderConfirmation.put("Orders", completeOrders);
		orderConfirmation.put("Orderitems", completeOrderitemsList);
		orderConfirmation.put("Course", completeCourseList);
		return orderConfirmation;		
	}
}
