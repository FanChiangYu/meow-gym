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
		
		//註解(或刪除)初始化該屬性的程式
//		try {
//			orderservice = new OrderServiceImpl();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Orderitems orderitems = json2Pojo(request, Orderitems.class);
		//先寫死
//		Integer userId = 1;
		//取會員資料
//		HttpSession session = req.getSession();
//		User user = (User) session.getAttribute("user");
//		Integer id = user.getUserId();
//		orderitems = orderservice.addcart(orderitems, userId);
		
		//回傳課程資訊
//		writePojo2Json(response, Course);
		
		
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
//	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//GSON 反序列化寫法
//		Gson gson = new Gson();
//		Orders orders = gson.fromJson(request.getReader(), Orders.class);
//		JsonObject jsonObject = new JsonObject();
//		
//		orders = orderservice.payment(orders);
//		if(orders != null) {
//			jsonObject.addProperty("success", true);
//			jsonObject.addProperty("order ID", orders.getOrderId());
//		} else {
//			jsonObject.addProperty("success", false);
//		}
//		
//		
//		String json = gson.toJson(orders);
//		response.setContentType("application/json");
//		response.getWriter().write(json);
		

		//import static 套件寫法		
		Orders orders = json2Pojo(request, Orders.class);
		orders = orderservice.payment(orders);
		writePojo2Json(response, orders);
	}
}
