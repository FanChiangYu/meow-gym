package web.order.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;
import java.util.List;

import web.order.dao.OrderDao;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;

@Repository
public class OrderDaoImpl implements OrderDao{
	
	@PersistenceContext
	private Session session;

	//Hibernate寫法
	@Override
	public Orders selectById(Integer id) {
		return session.get(Orders.class, id);
	}
	
	@Override
	public int insert(Orders orders) {
		session.persist(orders);
		return 1;
	}
	
	@Override
	public Integer selectOrderIdByUesrIdAndStatus(Integer userId, String status) {
		String hql= "selet max(orderId) from Orders where userId =:userId and status =:status";
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
	public Integer modifyStatusByUesrIdAndOrderId(Integer userId, Integer orderId) {
		return 1;
	}

    //未使用的方法
	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
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




