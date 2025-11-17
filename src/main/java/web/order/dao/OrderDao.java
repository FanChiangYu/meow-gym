package web.order.dao;

import java.util.List;

import core.dao.CoreDao;
import web.course.pojo.Course;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;
import web.promotions.pojo.CoursePromo;
import web.user.pojo.User;

public interface OrderDao extends CoreDao<Orders, Integer>{
	
	int insert(Orderitems orderitems);
	
	Integer selectOrderIdByUesrIdAndStatus(Integer userId, String status);
	
	Integer selectCoursePriceByCourseId(Integer courseId);
	
	List<Orderitems> selectOrderitemsListByOrderId(Integer orderId); //1找多
	
	List<Course> selectCourseListByOrderitemsCourseIdList(List<Integer> courseIdList);
	
	Integer selectPromoPriceByCourseId (Integer courseId); //沒用到
	
	CoursePromo selectCoursePromoByCourseId(Integer courseId);
	
	Integer deleteOrderitemsByOrderItemId(Integer orderItemId);
	
	Integer modifyStatusByUesrIdAndOrderIdAndStatus(Integer orderId, String status);
	
	Course selectCourseByCourseId (Integer courseId);
	
	Orders selectOrdersByOrderId(Integer orderId);
	
	int insert(Orders orders);
	
	Integer selectOrderIdAfterPaymentByUesrId (Integer userId);
	
	String selectUserEmailByUserId(Integer userId);
	
	User selectUserByUserId(Integer userId);
	
	List<Orders> selectShoppingRecordOrdersByUserId(Integer userId);
	
	List<Orderitems>selectOrderitemListByOrderIdList(List<Integer> orderIdList); //多找多
	
	List<Orders> selectCashOrdersByUserIdAndStatus(Integer userId, String status);
}
