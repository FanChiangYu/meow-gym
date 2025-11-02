package web.order.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.course.pojo.Course;
import web.order.dao.OrderDao;
import web.order.dao.impl.OrderDaoImpl;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;
import web.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{	
	@Autowired
	private OrderDao orderdao;
	
	//標註需要交易控制的⽅法
	@Transactional
	@Override
	public Integer addcart(Course course, Integer userId) {
		//比對訂單course資訊		
		if(course.getCourseId() == null) {
			System.out.println("取得訂單course資訊失敗");
			return null;
		}
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
				return null;
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
			return orderId;
		} else {
			System.out.println("儲存orderitems資料失敗");
			return null;
		}
	}
	
	@Transactional	
	@Override
	public List<Course> getAllCourseByOrderId(Integer orderId, String coachname) {
		List<Course> courseList = orderdao.selectCourseIdByOrderId(orderId);
		return courseList;
	}
	
	@Transactional	
	@Override
	public String deletecoursefromcart(Course course, Integer userId) {
		//搜尋order in DB
		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "pending");
		int count2 = orderdao.modifyStatusByUesrIdAndOrderId(userId, orderId);
		return count2 > 0 ? "cancel成功" : "cancel失敗";
	}

	@Transactional
	@Override
	//比對付款資訊
	public Orders payment(Orders orders) {
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
		
		//確認付款資料
		System.out.println(orders.getPayAmount());
		System.out.println(orders.getStatus());
		System.out.println(orders.getPaymentMethod());
		System.out.println(orders.getCardHolder());
		System.out.println(orders.getCardNumber());
		System.out.println(orders.getExpYear());
		System.out.println(orders.getExpMonth());
		System.out.println(orders.getCvc());
		
		//寫入DB資料
//		orders.setOrderId(1); //暫定
		orders.setUserId(2); //暫定
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
		orders.setCreatedAt(timestamp);
		
		//執行資料交易控制
//		beginTx();
		int count = orderdao.insert(orders);
		if(count == 1) {
			orders.setMessage("送出成功");
			orders.setSuccessful(true);
		} else {
			orders.setMessage("送出失敗");
			orders.setSuccessful(false);
//			rollback();
		}
//		commit();
		return orders;
	}
}
