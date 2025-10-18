package web.order.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.naming.NamingException;

import web.order.dao.OrderDao;
import web.order.dao.impl.OrderDaoImpl;
import web.order.pojo.Orders;
import web.order.service.OrderService;

public class OrderServiceImpl implements OrderService{
	private OrderDao orderdao;
	
	public OrderServiceImpl() throws NamingException {
		orderdao = new OrderDaoImpl();
	}
	
	@Override
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
		
		//確認前端資料
		System.out.println(orders.getPayAmount());
		System.out.println(orders.getStatus());
		System.out.println(orders.getCardHolder());
		System.out.println(orders.getCardNumber());
		System.out.println(orders.getExpYear());
		System.out.println(orders.getExpMonth());
		System.out.println(orders.getCvc());
		System.out.println(orders.getPaymentMethod());
		
		//需寫入DB資料
//		orders.setOrderId(3); //暫定
		orders.setUserId(1); //暫定
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
		orders.setCreatedAt(timestamp);

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
