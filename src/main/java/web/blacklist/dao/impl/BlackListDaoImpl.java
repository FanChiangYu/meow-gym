package web.blacklist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.blacklist.dao.BlackLiskDao;
import web.user.pojo.User;

public class BlackListDaoImpl implements BlackLiskDao {
	private DataSource ds;

	public BlackListDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/group1Project");
	}

//	@Override
//	public int insert(User user) {
//		String sql = "insert into User(USER_ID,EMAIL) values( ?, ? )";
//		try (
//			 Connection conn = ds.getConnection();
//			 PreparedStatement pstmt = conn.prepareStatement(sql);
//			){
//			pstmt.setString(1, user.getEmail());
//			pstmt.setInt(2, user.getUserId());
//			return pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return -1;
//	}

	@Override
	public List<User> selectAll() {
		String sql = "select user_id, email, created_at from User";
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {
			List<User> list = new ArrayList<>();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setEmail(rs.getString("email"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				list.add(user);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//封鎖使用者(修)update
	@Override
	public int update(User user) {
		String sql = "update user set is_banned = ?  where email = ?";
		try (
				Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setBoolean(1, user.isBanned());
			pstmt.setString(2, user.getEmail());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
