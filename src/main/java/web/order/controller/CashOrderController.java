package web.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.pojo.Core;
import web.order.pojo.Orders;
import web.order.service.OrderService;
import web.user.pojo.User;

@Controller
@RequestMapping("order")
public class CashOrderController {
	@Autowired
	private OrderService orderservice;

	@GetMapping("cashOrder")
	@ResponseBody
	protected Map<String, Object> cashOrder(@SessionAttribute(value = "user", required = false) User setUser) {
		//取登入資料
		String name = setUser.getName();
		// 先寫死
//		Integer userId = 1;
		//判斷登入者是否為管理員
		if(name.equals("系統管理員")) {
			// 回傳購物車清單
			Map<String, Object> cashOrderList = orderservice.getAllCashOrderList();
			return cashOrderList;
		}else {
			Map<String, Object> core = new HashMap<>();
			String message = "無系統管理員權限，請洽櫃台";
			core.put("message", message);
			return core;
		}
	}
	
	@PostMapping("statusChange")
	@ResponseBody
	protected Core statusChange(@RequestBody Orders orders,
			@SessionAttribute(value = "user", required = false) User setUser) {
		Integer orderId = orders.getOrderId();
		//取會員資料
		Integer userId = setUser.getUserId();
		// 先寫死
//		Integer userId = 1;

		// 回傳更改狀態是否成功
		Core core = new Core();
		core.setSuccessful(orderservice.changeOrderStatusForPaymentByCash(orderId, userId));
		return core;
	}
}
