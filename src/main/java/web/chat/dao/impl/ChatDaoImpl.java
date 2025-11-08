package web.chat.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

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

	//送出訊息
	@Override
	public int insert(Chats chats) {
		// hibernate自動對應哪個資料表、屬性對應欄位。
		session.persist(chats);
		// 回傳 1 代表成功（對應 JDBC 的 executeUpdate() 回傳值）
		return 1;
	}

	// 移到Service:即時拉出DB-對話訊息的所有詳細資料
	@Override
	public Chats saveAndLoad(Chats chats) {
		//Chats chat = new Chats();
		
//		chat.setCourseId(chatscourseId);
//		chat.setUserId(userId);
//		chat.setCoachId(coachId);
//		chat.setContent(content);
		
//		chats.setCourseId(chats.getCourseId());
//		chats.setUserId(chats.getUserId());
//		chats.setCoachId(chats.getCoachId());
//		chats.setContent(chats.getContent());

		 //寫入 + 立即同步 + 重新讀回 (created_at由DB填)
		session.persist(chats); // insert
		session.flush(); // 寫進DB拿到chat_id
		session.refresh(chats); // 重新從DB撈（created_at會有值）
		return chats;
	}

	// 依照courseId 查詢歷史訊息
	@Override
	public List<Chats> selectChatsByCourseId(Integer courseId) {
		// 13-3 查詢"所有屬性,多筆"
		String hql = "FROM Chats c WHERE c.courseId = :courseId ORDER BY c.createdAt ASC";
		return session.createQuery(hql, Chats.class).setParameter("courseId", courseId).getResultList();
	}

	// 1. 用courseId 去抓有哪些User,為了user_id要對應到name而製作的，屬非必要
	// ChatDTO為了user_id要對應到name而製作的，新增一個，有加上name的購物袋，因為本來的chats沒有name
	@Override
	public List<ChatDTO> selectCourseChatsWithUser(Integer courseId) {
		String hql = "select new web.chat.pojo.ChatDTO(c.chatId, c.courseId, c.userId, u.name, c.content, c.createdAt) from web.chat.pojo.Chats c join web.user.pojo.User u on u.userId = c.userId where c.courseId = :courseId order by c.createdAt";
		return session.createQuery(hql, ChatDTO.class).setParameter("courseId", courseId).getResultList();
	}

	// 對應orders & order_items to find courseId
	@Override
	public List<UserCourseDTO> selectUserCourseId(Integer userId) {
		String hql = "SELECT distinct new web.chat.pojo.UserCourseDTO(o.userId, i.courseId) FROM ChatOrders o JOIN o.items i WHERE o.userId = :userId";
		return session.createQuery(hql, UserCourseDTO.class).setParameter("userId", userId).getResultList();
	}

	// 藉由Courses 表格 courseId >> 找到coachId的方法
	@Override
	public Integer selectCoachIdByCourse(Integer courseId) {
		ChatCourses course = session.get(ChatCourses.class, courseId);
		return course.getCoachId();
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
