package web.order.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.order.dao.OrderDao;
import web.order.dao.impl.OrderDaoImpl;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;
import web.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	//設定Spring 注入(DI)
	@Autowired
	private OrderDao orderdao;

	//Spring 注入，註解(或刪除)初始化該屬性的程式
//	public OrderServiceImpl() throws NamingException {
//		orderdao = new OrderDaoImpl();
//	}
	
	//標註需要交易控制的⽅法
//	@Transactional
//	@Override
//	public Orderitems addcart(Orderitems orderitems, Integer userId) {
//		//比對訂單資訊		
//		if(orderitems.getCourseId() == null) {
//			orderitems.setMessage("未取得購買課程ID");
//			orderitems.setSuccessful(false);
//			return orderitems;
//		}
//		//確認Order_items內容
//		System.out.println(orderitems.getCourseId());
//		
//		//
//		Integer orderId = orderdao.selectOrderIdByUesrIdAndStatus(userId, "pending");
//		
//		
//		int count2 = orderdao.insert(orderitems);
//		
//		//執行資料交易控制
//		if(count2 == 1) {
//			orderitems.setMessage("送出成功");
//			orderitems.setSuccessful(true);
//		} else {
//			orderitems.setMessage("送出失敗");
//			orderitems.setSuccessful(false);
//		}
//		//回傳course info
//		return course;
//	}


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
