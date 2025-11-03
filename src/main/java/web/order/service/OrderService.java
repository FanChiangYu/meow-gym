package web.order.service;

import java.util.List;

import core.service.CoreService;
import web.course.pojo.Course;
import web.order.pojo.Orders;

public interface OrderService extends CoreService{
	
	Boolean addcart(Course course, Integer userId);
	
	List<Course> getAllCourseByUserId(Integer userId);
	
	String deletecoursefromcart(Integer courseId, Integer userId);
	
	Orders payment(Orders orders);

}
