package web.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.course.pojo.Course;
import web.order.service.OrderService;
import web.user.pojo.User;

@Controller
@RequestMapping("order")
public class NewOrderAddCartController {
	@Autowired
	private OrderService orderservice;
	
	@PostMapping("addCart")
	@ResponseBody
	protected List<Course> addCart(@SessionAttribute(value = "user", required = false) User setUser, @SessionAttribute(value = "course", required = false) Course setCourse) {

//		//取會員資料
//		Integer userId = setUser.getUserId();
		//先寫死
		Integer userId = 1;
		
		//取課程資訊
//		String coachname = (String)session.getAttribute("coachName");
		
		//判斷是否有加入購物車的課程，如無則顯示課程清單
		if (setCourse != null) {//比對訂單course資訊
			Boolean booLean = orderservice.addcart(setCourse, userId);
			System.out.println(booLean);
			}else {
				System.out.println("未取得訂單course資訊");
				return null;					
			}
		//回傳購物車清單
		List<Course> courseList = orderservice.getAllCourseByUserId(userId);
		//回傳課程資訊
		return courseList;
	}
}

