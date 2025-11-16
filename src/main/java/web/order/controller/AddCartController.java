package web.order.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.course.pojo.Course;
import web.order.service.OrderService;
import web.user.pojo.User;

@Controller
@RequestMapping("order")
public class AddCartController {
	@Autowired
	private OrderService orderservice;

	@GetMapping("addCart")
	@ResponseBody
	protected Map<String, Object> addCart(HttpSession session,@SessionAttribute(value = "user", required = false) User setUser, 
			@SessionAttribute(value = "course", required = false) Course setCourse) {
		//取會員資料
		Integer userId = setUser.getUserId();
		// 先寫死
//		Integer userId = 1;

		// 判斷是否有加入購物車的課程，如無則顯示課程清單
		if (setCourse != null) {// 比對訂單course資訊
			Boolean booLean = orderservice.addcart(setCourse, userId);
			System.out.println(booLean);
		}
		// 回傳購物車清單
		Map<String, Object> orderitemsAndCourseList = orderservice.getAllOrderitemsAndCourseByUserId(userId);
		session.removeAttribute("course");
		return orderitemsAndCourseList;
	}
}
