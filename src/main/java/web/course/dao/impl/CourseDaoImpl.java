package web.course.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import web.coach.pojo.CoachProfiles;
import web.course.dao.CourseDao;
import web.course.pojo.ClassSessions;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.SessionUsers;
import web.course.pojo.SessionUsersId;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;
import web.user.pojo.User;

@Repository
public class CourseDaoImpl implements CourseDao {
	@PersistenceContext
	private Session session;

	@Override
	public int insert(Course course) {
		session.persist(course);
		session.flush(); // 先insert，插入副表時Hibernate看得到course_id(fk)
		return 1;
	}
	
	@Override
	public int insert(CourseRecurringRules courseRecurringRules) {
//		courseRecurringRules.setRuleId(null); // 回到暫態
		session.persist(courseRecurringRules);
		return 1;
	}
	
	@Override
	public int insert(ClassSessions classSessions) {
		session.persist(classSessions);
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Course course) {
		final StringBuilder hql = new StringBuilder()
				.append("update Course set ")
				.append("approvalStatus = :approvalStatus ")
				.append("where courseId = :courseId");
		
		Query<?> query = session.createQuery(hql.toString());
		
		return query
				.setParameter("approvalStatus", course.getApprovalStatus())
				.setParameter("courseId", course.getCourseId())
				.executeUpdate();
	}

	@Override
	public Course selectById(Integer id) {
		return session.get(Course.class, id);
	}
	
	@Override
	public CoachProfiles selectByCoachId (Integer id) {
		return session.get(CoachProfiles.class, id);
	}
	
	@Override
	public User selectByUserId (Integer id) {
		return session.get(User.class, id);
	}

	@Override
	public List<Course> selectAll() {
		final String hql = "FROM Course ORDER BY courseId";
		return session
				.createQuery(hql, Course.class)
				.getResultList();
	}

	@Override
	public List<CourseRecurringRules> selectByCourseId (Integer id) {
		final String hql = "FROM CourseRecurringRules WHERE courseId = :courseId ORDER BY ruleId";
		return session
				.createQuery(hql, CourseRecurringRules.class)
				.setParameter("courseId", id)
				.getResultList();
	}

	@Override
	public List<Orders> selectOrderByUserId(Integer id) {
		final StringBuilder hql = new StringBuilder()
				.append("FROM Orders WHERE ")
				.append("userId = :userId ")
				.append("AND status = :status ")
				.append("ORDER BY orderId");
		
		return session
				.createQuery(hql.toString(), Orders.class)
				.setParameter("userId", id)
				.setParameter("status", "PAID")
				.getResultList();
	}

	@Override
	public List<Integer> selectCourseIdByOrderId(Integer id) {
		final StringBuilder hql = new StringBuilder()
				.append("SELECT courseId ")
				.append("FROM Orderitems ")
				.append("WHERE orderId = :orderId ")
				.append("ORDER BY courseId");
		
		return session
				.createQuery(hql.toString(), Integer.class)
				.setParameter("orderId", id)
				.getResultList();
	}

	@Override
	public List<ClassSessions> selectClassSessionBycourseID(Integer id) {
		final StringBuilder hql = new StringBuilder()
				.append("FROM ClassSessions ")
				.append("WHERE courseId = :courseId ")
				.append("ORDER BY sessionId");
		
		return session
				.createQuery(hql.toString(), ClassSessions.class)
				.setParameter("courseId", id)
				.getResultList();
	}

	@Override
	public SessionUsers selectBySessionIdUserID(Integer sessionId, Integer userId) {
		SessionUsersId id = new SessionUsersId();
		id.sessionId = sessionId;
		id.userId = userId;
		return session.get(SessionUsers.class, id);
	}

	@Override
	public Long selectCntBySessionId(Integer sessionId) {
		String hql = "SELECT COUNT(*) FROM SessionUsers WHERE sessionId = :sessionId";
		
		return session
				.createQuery(hql, Long.class)
				.setParameter("sessionId", sessionId)
				.uniqueResult();
	}

	@Override
	public int insert(SessionUsers sessionUsers) {
		session.persist(sessionUsers);
		return 1;
	}

	@Override
	public int deleteById(SessionUsers sessionUsers) {
		final StringBuilder hql = new StringBuilder()
				.append("DELETE FROM SessionUsers ")
				.append("WHERE sessionId = :sessionId ")
				.append("AND userId = :userId");
		
		return session
				.createQuery(hql.toString())
				.setParameter("sessionId", sessionUsers.getSessionId())
				.setParameter("userId", sessionUsers.getUserId())
				.executeUpdate();
	}

	@Override
	public Long selectCntFromSessionUserById(Integer sessionId, Integer userId) {
		StringBuilder hql = new StringBuilder()
				.append("SELECT COUNT(*) FROM SessionUsers ")
				.append("WHERE sessionId = :sessionId ")
				.append("AND userId = :userId");
		
		return session
				.createQuery(hql.toString(), Long.class)
				.setParameter("sessionId", sessionId)
				.setParameter("userId", userId)
				.uniqueResult();
	}

}
