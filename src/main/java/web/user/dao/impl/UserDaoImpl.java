package web.user.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.user.dao.UserDao;
import web.user.pojo.User;

public class UserDaoImpl implements UserDao {
	private DataSource ds;

	public UserDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/group1project");
	}

	@Override
	public User selectByUsernameAndPassword(User user) {
		String sql = "select * from USER where EMAIL = ? and PASSWORD = ?";
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getPassword());
			try (ResultSet rs = pstmt.executeQuery();) {
				
				//為什麼要放這麼多東西到JavaBean?Session 是靠這個 JavaBean 保存使用者狀態的
				//因為只有一項，所以可以直接用if(rs.next())
				if(rs.next()) {
					user = new User(); //為何不能用User user = new User();
					user.setUserId(rs.getInt("USER_ID"));
					user.setCntCode(rs.getInt("CNT_CODE"));
					user.setDistCode(rs.getInt("DIST_CODE"));
					user.setDetailAddress(rs.getString("DETAIL_ADDRESS"));
					user.setEmail(rs.getString("EMAIL"));
					user.setPassword(rs.getString("PASSWORD"));
					user.setName(rs.getString("NAME"));
					user.setResetCode(rs.getString("RESET_CODE"));
					user.setPhone(rs.getString("PHONE"));
					user.setAvatarUrl(rs.getString("AVATAR_URL"));
					user.setIsBanned(rs.getBoolean("IS_BANNED"));
					user.setBirthday(rs.getDate("BIRTHDAY"));
					user.setGender(rs.getString("GENDER"));
					user.setCreatedAt(rs.getTimestamp("CREATED_AT"));
					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
