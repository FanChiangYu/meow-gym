package web.order.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.order.service.OrderService;
import web.user.pojo.User;

@Controller
@RequestMapping("order")
public class ShoppingRecordController {
	@Autowired
	private OrderService orderservice;

	@GetMapping("shoppingRecord")
	@ResponseBody
	protected Map<String, Object> shoppingRecord(@SessionAttribute(value = "user", required = false) User setUser) {
		//取會員資料
		Integer userId = setUser.getUserId();

		// 回傳購物車清單
		Map<String, Object> shoppingRecordList = orderservice.getAllShoppingRecordListByUserId(userId);
		return shoppingRecordList;
	}
}
