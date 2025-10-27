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
	public int insert(Orderitems orderitems) {
		session.persist(orderitems);
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

	//JDBC 寫法
//	private DataSource ds;
//	
//	public OrderDaoImpl() throws NamingException {
//		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/group1Project");
//	}
//
//	@Override
//	public int insert(Orders orders) {
//		String sql = "insert into ORDERS(ORDER_ID, USER_ID, PAY_AMOUNT, STATUS, PAYMENT_METHOD, CARD_HOLDER, CARD_NUMBER, EXP_YEAR, EXP_MONTH, CVC, CREATED_AT) " +
//					 "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//		try (
//			Connection conn = ds.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql)
//		) {
//			pstmt.setInt(1, orders.getOrderId());
//			pstmt.setInt(2, orders.getUserId());
//			pstmt.setInt(3, orders.getPayAmount());
//			pstmt.setString(4, orders.getStatus());
//			pstmt.setString(5, orders.getPaymentMethod());
//			pstmt.setString(6, orders.getCardHolder());
//			pstmt.setInt(7, orders.getCardNumber());
//			pstmt.setInt(8, orders.getExpYear());
//			pstmt.setInt(9, orders.getExpMonth());
//			pstmt.setInt(10, orders.getCvc());
//			pstmt.setTimestamp(11, orders.getCreatedAt());
//			return pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return -1;
//	}
	
}




