package web.order.service;

import core.service.CoreService;
import web.course.pojo.Course;
import web.order.pojo.Orders;

public interface OrderService extends CoreService{
	
	Course addcart(Course course, Integer userId);
	
	String deletecoursefromcart(Course course, Integer userId);
	
	Orders payment(Orders orders);

}
