package web.chat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.cj.xdevapi.PreparableStatement;

import web.chat.dao.ChatDao;
import web.chat.pojo.ChatDTO;
import web.chat.pojo.Chats;
import web.chat.pojo.SessionUsers;

public class ChatDaoImpl implements ChatDao {
	private DataSource ds;

	public ChatDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/group1project");
	}

	@Override
	public int insert(Chats chats) {
		String sql = "insert into CHATS(COURSE_ID, USER_ID, COACH_ID, CONTENT) values(?, ?, ?, ?)";
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, chats.getCourseId());
			pstmt.setInt(2, chats.getUserId());
			pstmt.setInt(3, chats.getCoachId());
			pstmt.setString(4, chats.getContent());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	
	@Override
	public List<Chats> selectChatsByCourseId(Integer courseId) {
		String sql = "select * from CHATS where COURSE_ID = ?";
		List<Chats> chatList = new ArrayList<>();

		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, courseId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					Chats chats = new Chats();
					// 取出所有較方便
					chats.setChatId(rs.getInt("CHAT_ID"));
					chats.setCourseId(rs.getInt("COURSE_ID"));
					chats.setUserId(rs.getInt("USER_ID"));
					chats.setCoachId(rs.getInt("COACH_ID"));
					chats.setContent(rs.getString("CONTENT"));
					chats.setCreatedAt(rs.getTimestamp("CREATED_AT"));
					 //在第一筆就 return，等於永遠只拿到一筆
					chatList.add(chats);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chatList;
	}
	

	// 為了user_id要對應到name而製作的，屬非必要
	@Override
	public List<ChatDTO> selectCourseChatsWithUser(Integer courseId) {
	    String sql = "select c.CHAT_ID, c.COURSE_ID, c.USER_ID, c.CONTENT, c.CREATED_AT, u.NAME, u.NAME FROM CHATS c JOIN USER u ON u.USER_ID = c.USER_ID WHERE c.COURSE_ID = ? ORDER BY c.CREATED_AT";
	    List<ChatDTO> list = new ArrayList<>();
	    try (Connection conn = ds.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, courseId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                ChatDTO dto = new ChatDTO();
	                dto.setChatId(rs.getInt("chat_id"));
	                dto.setCourseId(rs.getInt("course_id"));
	                dto.setUserId(rs.getInt("user_id"));
	                // 顯示對應的 name
	                dto.setName(rs.getString("name")); 
	                dto.setContent(rs.getString("content"));
	                dto.setCreatedAt(rs.getTimestamp("created_at"));
	                list.add(dto);
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}

	
	@Override
	public Set<SessionUsers> selectUserCourseId(Integer userId) {
		String sql = "select * from SESSION_USERS where USER_ID = ?";

		// 因為一個使用者有很多courseId,要用不重複的Set接住
		Set<SessionUsers> courselist = new HashSet<>(); // 有錯嗎? 有hash用hash

		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, userId); // 有寫錯嗎?

			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					SessionUsers sessionusers = new SessionUsers();
					// 待續
					sessionusers.setCourseId(rs.getInt("COURSE_ID"));
					sessionusers.setSessionId(rs.getInt("SESSION_ID"));
					sessionusers.setUserId(rs.getInt("USER_ID"));
					courselist.add(sessionusers);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return courselist;
	}



}
