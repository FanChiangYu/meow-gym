package web.order.service;

import java.util.List;
import java.util.Map;

import core.service.CoreService;
import web.course.pojo.Course;
import web.order.pojo.Orders;
import web.promotions.pojo.CoursePromo;

public interface OrderService extends CoreService{
	
	List<CoursePromo> getAllCoursePromo();
	
	Boolean addcart(Course course, Integer userId);
	
	Map<String, Object> getAllOrderitemsAndCourseByUserId(Integer userId);
	
	boolean deletecoursefromcart(Integer courseId, Integer userId);
	
//	Map<String, Object> getPayAmountListByUserId(Integer userId);
	
	Orders payment(Orders orders, Integer userId);
	
	Map<String, Object> getOrderConfirmation(Integer userId);
	
	Map<String, Object> getAllShoppingRecordListByUserId(Integer userId);
	
	Map<String, Object> getAllCashOrderList();
	
	Boolean changeOrderStatusForPaymentByCash(Integer orderId);
}
