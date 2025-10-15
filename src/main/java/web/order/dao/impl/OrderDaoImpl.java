package web.order.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.order.dao.OrderDao;
import web.order.pojo.Orders;


public class OrderDaoImpl implements OrderDao{
	
	private DataSource ds;
	
	public OrderDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/group1Project");
	}

	@Override
	public int insert(Orders orders) {
		String sql = "insert into ORDERS(ORDER_ID, USER_ID, PAY_AMOUNT, STATUS, PAYMENT_METHOD, CARD_HOLDER, CARD_NUMBER, EXP_YEAR, EXP_MONTH, CVC, CREATED_AT) " +
					 "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)
		) {
			pstmt.setInt(1, orders.getOrderId());
			pstmt.setInt(2, orders.getUserId());
			pstmt.setInt(3, orders.getPayAmount());
			pstmt.setString(4, orders.getStatus());
			pstmt.setString(5, orders.getPaymentMethod());
			pstmt.setString(6, orders.getCardHolder());
			pstmt.setInt(7, orders.getCardNumber());
			pstmt.setInt(8, orders.getExpYear());
			pstmt.setInt(9, orders.getExpMonth());
			pstmt.setInt(10, orders.getCvc());
			pstmt.setTimestamp(11, orders.getCreatedAt());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//為什麼強制implement 4個方法
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
	public Orders selectById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
