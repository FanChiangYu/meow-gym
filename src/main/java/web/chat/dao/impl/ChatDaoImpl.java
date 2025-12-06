package web.chat.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.chat.dao.ChatDao;
import web.chat.pojo.ChatDTO;
import web.chat.pojo.Chats;
import web.chat.pojo.UserCourseDTO;
import web.course.pojo.Course;
import web.user.pojo.User;

@Repository
public class ChatDaoImpl implements ChatDao {

	// 外部已經傳進來的 Entity 物件 Chats chats
	// 前端點"送出訊息" 用的

	@PersistenceContext
	private Session session;

	// 送出訊息
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

		// 寫入 + 立即同步 + 重新讀回 (created_at由DB填)
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


	@Override
	public List<ChatDTO> selectCourseChatsWithUser(Integer courseId) {
		String hql = "select new web.chat.pojo.ChatDTO(c.chatId, c.courseId, c.userId, u.name, c.content, c.createdAt, u.avatarUrl, u.role) from web.chat.pojo.Chats c join web.user.pojo.User u on u.userId = c.userId where c.courseId = :courseId order by c.createdAt";
		return session.createQuery(hql, ChatDTO.class).setParameter("courseId", courseId).getResultList();
	}

	// 藉由Courses 表格 courseId >> 找到coachId
	@Override
	public Integer selectCoachIdByCourse(Integer courseId) {
		// ChatCourses course = session.get(ChatCourses.class, courseId);
		Course course = session.get(Course.class, courseId);
		return course.getCoachId();
	}

	@Override
	public List<UserCourseDTO> selectUserCourseId(Integer role) {
		String hql = "SELECT distinct new web.chat.pojo.UserCourseDTO(c.role, i.courseId) FROM Course c WHERE c.approvalStatus = :status";
		return session.createQuery(hql, UserCourseDTO.class).setParameter("status", "通過").getResultList();
	}

	// 使用courseId查詢課程名稱
	@Override
	public String selectCourseTitle(Integer courseId) {
		Course course = session.get(Course.class, courseId);
		System.out.println(course.getTitle());
		return course.getTitle();
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
