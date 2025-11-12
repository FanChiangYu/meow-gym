package web.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.order.pojo.Orders;
import web.order.service.OrderService;
import web.user.pojo.User;

@Controller
@RequestMapping("order")
//@WebServlet("/order/payment")
public class PaymentController {
	@Autowired
	private OrderService orderservice;
	
	@PostMapping("payment")
	@ResponseBody
	protected Orders payment (@RequestBody Orders orders, 
			@SessionAttribute(value = "user", required = false) User setUser) {		
//		//取會員資料
//		Integer userId = setUser.getUserId();
		// 先寫死
		Integer userId = 1;
		
		//import static 套件寫法		
		return orderservice.payment(orders, userId);
	}
}