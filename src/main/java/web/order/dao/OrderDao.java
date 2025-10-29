package web.order.dao;

import java.util.List;

import core.dao.CoreDao;
import web.course.pojo.Course;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;

public interface OrderDao extends CoreDao<Orders, Integer>{
	
	Orders selectById(Integer id);
	
	int insert(Orders orders);
	
	int insert(Orderitems orderitems);
	
	Integer selectOrderIdByUesrIdAndStatus(Integer userId, String status);
	
	Integer selectCoursePriceByCourseId(Integer courseId);
	
	List<Course> selectCourseIdByOrderId(Integer orderId);
	
	Integer modifyStatusByUesrIdAndOrderId(Integer userId, Integer orderId);
}
