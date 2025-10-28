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
public class NewOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private OrderService orderservice;
	
	@Override
	public void init() throws ServletException {
		//Spring無法控管Servlet物件，透過 CommonUtil.getBean()￾取得 Service物件
		orderservice = CommonUtil.getBean(getServletContext(), OrderService.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Integer userId = 1;
//		//取會員資料
//		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("user");
//		Integer id = user.getUserId();
//		//先寫死
//		Integer courseid = 1;
//		String coachName = "Alice";
//		//取課程資訊
//		Course course = (Course) session.getAttribute("course");
//		String coachName = (String)session.getAttribute("coachName");
//		course = orderservice.addcart(course, userId);
//		
//		//回傳課程資訊
//		writePojo2Json(response, course);
	}
		
//		JsonObject respbody = new JsonObject();
//		NewCourseRequest newCourseRequest = json2Pojo(request, NewCourseRequest.class);
//		Course course = newCourseRequest.getCourse();
//		List<CourseRecurringRules> Rules = newCourseRequest.getRules(); 
//		course = service.apply(course);
//		
//		if(course.isSuccessful()) {
//			System.out.println(course.getCourseId());
//			respbody = service.apply(Rules, course.getCourseId());
//			writePojo2Json(response, respbody);
//		} else {
//			writePojo2Json(response, course);
//		}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
