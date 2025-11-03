package web.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.course.pojo.Course;
import web.order.service.OrderService;
import web.user.pojo.User;

@Controller
@RequestMapping("order")
public class NewOrderDeleteCourseController {
	@Autowired
	private OrderService orderservice;
	
	@PostMapping("deleteCart")
	@ResponseBody
	protected String deleteCart(@RequestBody Course course, @SessionAttribute(value = "user", required = false) User setUser){
		Integer courseId = course.getCourseId();
//		//取會員資料
		Integer userId = setUser.getUserId();
		//先寫死
//		Integer userId = 1;
		String answer = orderservice.deletecoursefromcart(courseId, userId);
		return answer;
	}
}

