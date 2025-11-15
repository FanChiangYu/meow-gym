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
public class PayAmountController {
	@Autowired
	private OrderService orderservice;

	@GetMapping("payAmount")
	@ResponseBody
	protected Map<String, Object> payAmount(@SessionAttribute(value = "user", required = false) User setUser) {
		//取會員資料
//		Integer userId = setUser.getUserId();
		// 先寫死
		Integer userId = 1;
		
		// 回傳個課程價格及購課總價
		Map<String, Object> payAmountList = orderservice.getPayAmountListByUserId(userId);
		return payAmountList;
	}
}
