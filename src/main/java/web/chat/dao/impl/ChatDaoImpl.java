package web.chat.dao.impl;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import core.util.HibernateUtil;
import web.chat.dao.ChatDao;
import web.chat.pojo.ChatCourses;
import web.chat.pojo.ChatDTO;
import web.chat.pojo.Chats;
import web.chat.pojo.UserCourseDTO;
import web.user.pojo.User;

@Repository
public class ChatDaoImpl implements ChatDao {

	// 外部已經傳進來的 Entity 物件 Chats chats
	// 前端點"送出訊息" 用的
	
	@PersistenceContext
	private Session session;
	
	@Override
	public int insert(Chats chats) {
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.getCurrentSession();
		try {
			Transaction transaction = session.beginTransaction();
			// hibernate自動對應哪個資料表、屬性對應欄位。
			session.persist(chats);
			transaction.commit();
			// 回傳 1 代表成功（對應 JDBC 的 executeUpdate() 回傳值）
			return 1;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		// 若發生例外則回傳 -1
		return -1;

	}
	
	// 即時拉出DB-對話訊息的所有詳細資料
	@Override
	public Chats saveAndLoad(int courseId, int userId, Integer coachId, String content) {
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.getCurrentSession();
		try {
			Transaction tx = session.beginTransaction();
			// 建立 entity
			Chats chat = new Chats();
			chat.setCourseId(courseId);
			chat.setUserId(userId);
			chat.setCoachId(coachId);
			chat.setContent(content);

			// 寫入 + 立即同步 + 重新讀回 (created_at由DB填)
			session.persist(chat); // insert
			session.flush(); // 寫進DB拿到chat_id
			session.refresh(chat); // 重新從DB撈（created_at會有值）

			tx.commit();
			return chat;

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return null;
	}

	// 依照courseId 查詢歷史訊息
	@Override
	public List<Chats> selectChatsByCourseId(Integer courseId) {
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.getCurrentSession();
		List<Chats> chatList = new ArrayList<>();
		try {
			Transaction transaction = session.beginTransaction();
			// String hql = "FROM Chats WHERE courseId = :courseId ORDER BY createdAt ASC";

			String hql = "FROM Chats c WHERE c.courseId = :courseId ORDER BY c.createdAt ASC";
			// 若想查詢多筆資料，可使⽤createQuery()
			// 13-3 查詢"所有屬性,多筆"
			chatList = session.createQuery(hql, Chats.class).setParameter("courseId", courseId).getResultList();
			// 直接把這裡的courseId
			System.out.println("chatList" + chatList);

			transaction.commit();
			return chatList;

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return null; // 這裡改得跟JDBC不一樣，這樣應該才是正確的
	}

	// 1. 用courseId 去抓有哪些User,為了user_id要對應到name而製作的，屬非必要
	// ChatDTO為了user_id要對應到name而製作的，新增一個，有加上name的購物袋，因為本來的chats沒有name
	@Override
	public List<ChatDTO> selectCourseChatsWithUser(Integer courseId) {
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.getCurrentSession();
		List<ChatDTO> list = new ArrayList<>();
		try {
			Transaction transaction = session.beginTransaction();
			// String hql = "select new web.chat.pojo.ChatDTO(c.chatId, c.courseId,
			// c.userId, u.name, c.content, c.createdAt)from Chats c join c,user u where
			// c.courseId =:courseId order by c.createAt";
			String hql = "select new web.chat.pojo.ChatDTO(c.chatId, c.courseId, c.userId, u.name, c.content, c.createdAt) from web.chat.pojo.Chats c join web.user.pojo.User u on u.userId = c.userId where c.courseId = :courseId order by c.createdAt";
			list = session.createQuery(hql, ChatDTO.class).setParameter("courseId", courseId).getResultList();

			transaction.commit();
			return list;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return null;
	}

	// 對應orders & order_items to find courseId
	@Override
	public List<UserCourseDTO> selectUserCourseId(Integer userId) {
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.getCurrentSession();
		List<UserCourseDTO> courselist = new ArrayList<>();

		try {
			// 使用MYSQL 去重複 courseId （同一使用者重複買同一課程只顯示一次）
			// String hql = "SELECT distinct new web.chat.pojo.UserCourseDTO(o.userId,
			// i.courseId) FROM ChatOrders o JOIN ChatOrderitems i WHERE o.orderId =
			// i.orderId";
			// String hql = "SELECT distinct new web.chat.pojo.UserCourseDTO(o.userId,
			// i.courseId) FROM ChatOrders o JOIN ChatOrderitems i WHERE o.orderId =
			// i.orderId AND o.userId = :userId";
			String hql = "SELECT distinct new web.chat.pojo.UserCourseDTO(o.userId, i.courseId) FROM ChatOrders o JOIN o.items i WHERE o.userId = :userId";
			courselist = session.createQuery(hql, UserCourseDTO.class).setParameter("userId", userId).getResultList(); // error
																														// >>
																														// 導致前端拿不到courseId
			System.out.println("courselist" + courselist);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return courselist;
	}

	// 藉由Courses 表格 courseId >> 找到coachId的方法
	@Override
	public Integer selectCoachIdByCourse(Integer courseId) {
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.getCurrentSession();

		try {
			Transaction tx = session.beginTransaction();
			ChatCourses course = session.get(ChatCourses.class, courseId);
			System.out.println("ChatCourses course" + course);

			tx.commit();
			return course.getCoachId();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return null;
	}

	

	@Override
	public int insert(User pojo) {
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		return 0;
	}

	@Override
	public int update(User pojo) {
		return 0;
	}

	@Override
	public User selectById(Integer id) {
		return null;
	}

	@Override
	public List<User> selectAll() {
		return null;
	}


}

// JDBC寫法
//public class ChatDaoImpl implements ChatDao {
//	private DataSource ds;
//
//	public ChatDaoImpl() throws NamingException {
//		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/group1project");
//	}
//
//	@Override
//	public int insert(Chats chats) {
//		String sql = "insert into CHATS(COURSE_ID, USER_ID, COACH_ID, CONTENT) values(?, ?, ?, ?)";
//		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
//			pstmt.setInt(1, chats.getCourseId());
//			pstmt.setInt(2, chats.getUserId());
//			pstmt.setInt(3, chats.getCoachId());
//			pstmt.setString(4, chats.getContent());
//			return pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return -1;
//	}
//
//	
//	@Override
//	public List<Chats> selectChatsByCourseId(Integer courseId) {
//		String sql = "select * from CHATS where COURSE_ID = ?";
//		List<Chats> chatList = new ArrayList<>();
//
//		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
//			pstmt.setInt(1, courseId);
//			try (ResultSet rs = pstmt.executeQuery();) {
//				while (rs.next()) {
//					Chats chats = new Chats();
//					// 取出所有較方便
//					chats.setChatId(rs.getInt("CHAT_ID"));
//					chats.setCourseId(rs.getInt("COURSE_ID"));
//					chats.setUserId(rs.getInt("USER_ID"));
//					chats.setCoachId(rs.getInt("COACH_ID"));
//					chats.setContent(rs.getString("CONTENT"));
//					chats.setCreatedAt(rs.getTimestamp("CREATED_AT"));
//					 //在第一筆就 return，等於永遠只拿到一筆
//					chatList.add(chats);
//				}
//			} 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return chatList;
//	}
//	
//
//	// 為了user_id要對應到name而製作的，屬非必要
//	@Override
//	public List<ChatDTO> selectCourseChatsWithUser(Integer courseId) {
//	    String sql = "select c.CHAT_ID, c.COURSE_ID, c.USER_ID, c.CONTENT, c.CREATED_AT, u.NAME, u.NAME FROM CHATS c JOIN USER u ON u.USER_ID = c.USER_ID WHERE c.COURSE_ID = ? ORDER BY c.CREATED_AT";
//	    List<ChatDTO> list = new ArrayList<>();
//	    try (Connection conn = ds.getConnection();
//	         PreparedStatement ps = conn.prepareStatement(sql)) {
//	        ps.setInt(1, courseId);
//	        try (ResultSet rs = ps.executeQuery()) {
//	            while (rs.next()) {
//	                ChatDTO dto = new ChatDTO();
//	                dto.setChatId(rs.getInt("chat_id"));
//	                dto.setCourseId(rs.getInt("course_id"));
//	                dto.setUserId(rs.getInt("user_id"));
//	                // 顯示對應的 name
//	                dto.setName(rs.getString("name")); 
//	                dto.setContent(rs.getString("content"));
//	                dto.setCreatedAt(rs.getTimestamp("created_at"));
//	                list.add(dto);
//	            }
//	        }
//	    } catch (Exception e) { e.printStackTrace(); }
//	    return list;
//	}
//
//	
//	@Override
//	public Set<SessionUsers> selectUserCourseId(Integer userId) {
//		String sql = "select * from SESSION_USERS where USER_ID = ?";
//
//		// 因為一個使用者有很多courseId,要用不重複的Set接住
//		Set<SessionUsers> courselist = new HashSet<>(); // 有錯嗎? 有hash用hash
//
//		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
//			pstmt.setInt(1, userId); // 有寫錯嗎?
//
//			try (ResultSet rs = pstmt.executeQuery();) {
//				while (rs.next()) {
//					SessionUsers sessionusers = new SessionUsers();
//					// 待續
//					sessionusers.setCourseId(rs.getInt("COURSE_ID"));
//					sessionusers.setSessionId(rs.getInt("SESSION_ID"));
//					sessionusers.setUserId(rs.getInt("USER_ID"));
//					courselist.add(sessionusers);
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return courselist;
//	}
//}
