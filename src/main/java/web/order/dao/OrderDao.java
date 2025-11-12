package web.order.dao;

import java.util.List;

import core.dao.CoreDao;
import web.course.pojo.Course;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;
import web.promotions.pojo.CoursePromo;

public interface OrderDao extends CoreDao<Orders, Integer>{
	
	int insert(Orderitems orderitems);
	
	Integer selectOrderIdByUesrIdAndStatus(Integer userId, String status);
	
	Integer selectCoursePriceByCourseId(Integer courseId);
	
	List<Orderitems> selectOrderitemsListByOrderId(Integer orderId);
	
	List<Course> selectCourseAndOrderitemListByOrderitems(List<Integer> courseIdList);
	
	Integer selectPromoPriceByCourseId (Integer courseId);
	
	Integer deleteOrderitemsByOrderItemId(Integer orderItemId);
	
	Integer modifyStatusByUesrIdAndOrderIdAndStatus(Integer orderId, String status);
	
	Course selectCourseByCourseId (Integer courseId);
	
	CoursePromo selectCoursePromoPriceByCourseId(Integer courseId);
	
	Orders selectOrdersByOrderId(Integer orderId);
	
	int insert(Orders orders);
}
