package web.order.controller;

import java.io.IOException;
import java.util.List;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import core.util.CommonUtil;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.NewCourseRequest;
import web.index.pojo.Courses;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;
import web.order.service.OrderService;
import web.order.service.impl.OrderServiceImpl;
import web.user.pojo.User;

@WebServlet("/order/newOrder")
public class NewOrderAddCartServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private OrderService orderservice;
	
	@Override
	public void init() throws ServletException {
		//Spring無法控管Servlet物件，透過 CommonUtil.getBean()￾取得 Service物件
		orderservice = CommonUtil.getBean(getServletContext(), OrderService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
//		//取會員資料
//		User user = (User) session.getAttribute("user");
//		Integer userId = user.getUserId();
		//先寫死
		Integer userId = 1;
		
		//取課程資訊
		Course course = (Course) session.getAttribute("course");
//		String coachname = (String)session.getAttribute("coachName");
		Integer orderId = orderservice.addcart(course, userId);
		List<Course> courseList = orderservice.getAllCourseByOrderId(orderId);
		//回傳課程資訊
		writePojo2Json(response, courseList);
	}
}


//JsonObject respbody = new JsonObject();
//NewCourseRequest newCourseRequest = json2Pojo(request, NewCourseRequest.class);
//Course course = newCourseRequest.getCourse();
//List<CourseRecurringRules> Rules = newCourseRequest.getRules(); 
//course = service.apply(course);
//
//if(course.isSuccessful()) {
//	System.out.println(course.getCourseId());
//	respbody = service.apply(Rules, course.getCourseId());
//	writePojo2Json(response, respbody);
//} else {
//	writePojo2Json(response, course);
//}

