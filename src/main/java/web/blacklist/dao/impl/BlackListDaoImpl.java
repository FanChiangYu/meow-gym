package web.blacklist.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.oracle.wls.shaded.org.apache.regexp.recompile;

import web.blacklist.dao.BlackLisyDao;
import web.blacklist.pojo.User;

public class BlackListDaoImpl implements BlackLisyDao {
	private DataSource ds;
	
	public BlackListDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/group1Project");
	}
	
	@Override
	public int insert(User user) {
		String sql = "insert into User(USER_ID,EMAIL) values( ?, ? )";
		try (
			 Connection conn = ds.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			){
			pstmt.setString(1, user.getEmail());
			pstmt.setInt(2, user.getUserId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int update(User user) {
		String sql = "update user set is_banned = ?  where email = ?";
		try (
			 Connection conn = ds.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			){
			pstmt.setString(1, user.getEmail());
			pstmt.setBoolean(2, user.getIsBanned());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
