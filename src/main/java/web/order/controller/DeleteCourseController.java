package web.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import core.pojo.Core;
import web.order.pojo.Orderitems;
import web.order.service.OrderService;
import web.user.pojo.User;

@Controller
@RequestMapping("order")
public class DeleteCourseController {
	@Autowired
	private OrderService orderservice;
	
	@PostMapping("deleteCart")
	@ResponseBody
	protected Core deleteCart(@RequestBody Orderitems orderitems, 
			@SessionAttribute(value = "user", required = false) User setUser){
		Integer orderItemId = orderitems.getOrderItemId();
		//取會員資料
//		Integer userId = setUser.getUserId();
		//先寫死
		Integer userId = 1;
		Core core = new Core();
		core.setSuccessful(orderservice.deletecoursefromcart(orderItemId, userId));
		return core;
	}
}

