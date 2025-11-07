package web.order.service;

import java.util.List;
import java.util.Map;

import core.service.CoreService;
import web.course.pojo.Course;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;

public interface OrderService extends CoreService{
	
	Boolean addcart(Course course, Integer userId);
	
	Map<String, Object> getAllOrderitemsAndCourseByUserId(Integer userId);
	
	String deletecoursefromcart(Integer courseId, Integer userId);
	
	Map<String, Object> getPayAmountListByUserId(Integer userId);
	
	Orders payment(Orders orders, Integer userId);

}
