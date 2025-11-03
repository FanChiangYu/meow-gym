package web.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.order.pojo.Orders;
import web.order.service.OrderService;

@Controller
@RequestMapping("order")
//@WebServlet("/order/payment")
public class NewOrderPaymentController {
	@Autowired
	private OrderService orderservice;
	
	@PostMapping("payment")
	@ResponseBody
	protected Orders payment (@RequestBody Orders orders) {		
		//import static 套件寫法		
		return orderservice.payment(orders);
	}
}