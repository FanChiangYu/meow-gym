package web.order.dao;

import java.util.List;

import core.dao.CoreDao;
import web.course.pojo.Course;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;

public interface OrderDao extends CoreDao<Orders, Integer>{
	
	int insert(Orderitems orderitems);
	
	Integer selectOrderIdByUesrIdAndStatus(Integer userId, String status);
	
	Integer selectCoursePriceByCourseId(Integer courseId);
	
	List<Integer> selectCourseListByOrderId(Integer orderId);
	
	List<Course> selectCourseListByCourseIdList(List<Integer> courseIdList);
	
	Integer deleteOrderitemsByCourseIdAndOrderId(Integer courseId, Integer orderId);
	
	List<Orderitems> selectOrderitemsListByOrderId(Integer orderId);
	
	Integer modifyStatusByUesrIdAndOrderIdAndStatus(Integer orderId, String status);
	
	Orders selectOrdersByOrderId(Integer orderId);
	
	int insert(Orders orders);
}
