package web.member.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.member.dao.MemberDao;
import web.member.pojo.Member;

public class MemberDaoImpl implements MemberDao {
	private DataSource ds;
	
	public MemberDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/javaFramework");
	}
	
	@Override
	public Member selectByUsernameAndPassword(Member member) {
		final String sql = "select * from MEMBER where USERNAME = ? and PASSWORD = ?";
		try (
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)
		) {
			pstmt.setString(1, member.getUsername());
			pstmt.setString(2, member.getPassword());
			try (
				ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					member = new Member();
					member.setId(rs.getInt("ID"));
					member.setUsername(rs.getString("USERNAME"));
					member.setPassword(rs.getString("PASSWORD"));
					member.setNickname(rs.getString("NICKNAME"));
					member.setPass(rs.getBoolean("PASS"));
					member.setRoleId(rs.getInt("ROLE_ID"));
					member.setCreator(rs.getString("CREATOR"));
					member.setCreatedAt(rs.getTimestamp("CREATED_AT"));
					member.setUpdater(rs.getString("UPDATER"));
					member.setLastUpdatedAt(rs.getTimestamp("LAST_UPDATED_AT"));
					return member;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
