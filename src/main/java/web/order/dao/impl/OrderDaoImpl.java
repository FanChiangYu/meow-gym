package web.order.dao.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;
import java.util.List;

import web.course.pojo.Course;
import web.order.dao.OrderDao;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;
import web.promotions.pojo.CoursePromo;
import web.user.pojo.User;

@Repository
public class OrderDaoImpl implements OrderDao{
	
	@PersistenceContext
	private Session session;

	//Hibernate寫法	
	@Override
	public Integer selectOrderIdByUesrIdAndStatus(Integer userId, String status) {
		String hql= "select max(orderId) from Orders where userId = :userId and status = :status";
		Query<Integer> query = session.createQuery(hql, Integer.class);		
		return query.setParameter("userId", userId)
				.setParameter("status", status)
				.uniqueResult();
	}
	
	@Override
	public Integer selectCoursePriceByCourseId(Integer courseId) {
		String hql = "select coursePrice from Course where courseId = :courseId";
		Query<Integer> query = session.createQuery(hql, Integer.class);		
		return query.setParameter("courseId", courseId)
				.uniqueResult();
	}
	
	@Override
	public int insert(Orderitems orderitems) {
		session.persist(orderitems);
		return 1;
	}
	
	@Override
	public List<Orderitems> selectOrderitemsListByOrderId(Integer orderId) {
		//找同筆訂單下所有CourseID
		String hql = "from Orderitems where orderId = :orderId";
		Query<Orderitems> query = session.createQuery(hql, Orderitems.class);
		List<Orderitems> orderItemsList = query.setParameter("orderId", orderId).getResultList();
		return orderItemsList;	
	}
	
	@Override
	public List<Course> selectCourseListByOrderitemsCourseIdList(List<Integer> courseIdList) {
		//找Course.class
		String hql = "FROM Course where courseId IN(:courseIdList)";
		Query<Course> query = session.createQuery(hql, Course.class);
		List<Course> courseList = query.setParameterList("courseIdList", courseIdList).getResultList();
		return courseList;
	}
	
	@Override
	public Integer selectPromoPriceByCourseId(Integer courseId) {
		String hql = "select promoPrice from CoursePromo where courseId = :courseId";
		Query<Integer> query = session.createQuery(hql, Integer.class);		
		return query.setParameter("courseId", courseId)
				.uniqueResult();
	}
	
	@Override
	public Integer deleteOrderitemsByOrderItemId(Integer orderItemId) {
		int result = session.createQuery("DELETE Orderitems "
				 + "WHERE orderItemId = :orderItemId")
				 .setParameter("orderItemId", orderItemId)
				 .executeUpdate();
		return result;	
	}

	@Override
	public Integer modifyStatusByUesrIdAndOrderIdAndStatus(Integer orderId, String status) {		
		int result = session.createQuery("UPDATE Orders "
				+ "SET status = :status "
				+ "WHERE orderId = :orderId")
				.setParameter("status", status)
				.setParameter("orderId", orderId)
				.executeUpdate();
		return result;
	}
	
	@Override
	public Course selectCourseByCourseId(Integer courseId) {
		return session.get(Course.class, courseId);
	}

	@Override
	public CoursePromo selectCoursePromoByCourseId(Integer courseId) {
	    String hql = "FROM CoursePromo WHERE courseId = :courseId";
	    return session.createQuery(hql, CoursePromo.class)
	                  .setParameter("courseId", courseId)
	                  .uniqueResult();
	}
	
	@Override
	public Orders selectOrdersByOrderId(Integer orderId) {
		return session.get(Orders.class, orderId);
	}
	
	@Override
	public int insert(Orders orders) {
		session.persist(orders);
		return 1;
	}
	

	@Override
	public Integer selectOrderIdAfterPaymentByUesrId(Integer userId) {
		String hql= "select max(orderId) from Orders where userId = :userId and status IN ('PAID', 'WAIT_PAID')";
		Query<Integer> query = session.createQuery(hql, Integer.class);		
		return query.setParameter("userId", userId)
				.uniqueResult();
	}

	@Override
	public String selectUserEmailByUserId(Integer userId) {
		String hql = "select email from User where userId = :userId";
		Query<String> query = session.createQuery(hql, String.class);		
		return query.setParameter("userId", userId)
				.uniqueResult();
	}
	
	@Override
	public User selectUserByUserId(Integer userId) {
		return session.get(User.class, userId);
	}
	
	@Override
	public List<Orders> selectShoppingRecordOrdersByUserId(Integer userId) {
		String hql = "FROM Orders where userId =:userId and status IN ('PAID', 'WAIT_PAID')";
		Query<Orders> query = session.createQuery(hql, Orders.class);
		return query.setParameter("userId", userId)
				.getResultList();
	}
	
	@Override
	public List<Orderitems> selectOrderitemListByOrderIdList(List<Integer> orderIdList) {
		//找Orderitems.class
		String hql = "FROM Orderitems where orderId IN(:orderIdList)";
		Query<Orderitems> query = session.createQuery(hql, Orderitems.class);
		return query.setParameterList("orderIdList", orderIdList)
				.getResultList();
	}
	
	@Override
	public List<Orders> selectCashOrdersByStatus(String status) {
		String hql = "FROM Orders where status = :status";
		Query<Orders> query = session.createQuery(hql, Orders.class);
		return query.setParameter("status", status)
				.getResultList();
	}
	

	@Override
	public Integer selectUserIdByOrderId(Integer orderId) {
		String hql= "select userId from Orders where orderId = :orderId";
		Query<Integer> query = session.createQuery(hql, Integer.class);		
		return query.setParameter("orderId", orderId)
				.uniqueResult();
	}
	

	@Override
	public Integer selectChangeStatusOrderIdByUesrIdAndOrderId(Integer orderId, Integer userId) {
		String hql= "select orderId from Orders where userId = :userId and orderId = :orderId";
		Query<Integer> query = session.createQuery(hql, Integer.class);		
		return query.setParameter("userId", userId)
				.setParameter("orderId", orderId)
				.uniqueResult();
	}

    //未使用的方法
	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Orders selectById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int update(Orders pojo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Orders> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
}




