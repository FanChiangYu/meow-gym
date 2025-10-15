package web.order.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.order.pojo.Orders;
import web.order.service.OrderService;
import web.order.service.impl.OrderServiceImpl;

@WebServlet("/order/newOrder")
public class NewOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private OrderService orderservice;
	
	@Override
	public void init() throws ServletException {
		try {
			orderservice = new OrderServiceImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//GSON 反序列化寫法
		Gson gson = new Gson();
		Orders orders = gson.fromJson(request.getReader(), Orders.class);
		JsonObject jsonObject = new JsonObject();
		
		orders = orderservice.payment(orders);
		if(orders != null) {
			jsonObject.addProperty("success", true);
			jsonObject.addProperty("order ID", orders.getOrderId());
		} else {
			jsonObject.addProperty("success", false);
		}
		
		
		String json = gson.toJson(orders);
		response.setContentType("application/json");
		response.getWriter().write(json);
		

		//import static 套件寫法
//		Orders orders = json2Pojo(request, Orders.class);
//		orders = orderservice.payment(orders);
//		writePojo2Json(response, orders);
	}
}
