package web.course.dao.impl;

import static core.util.CommonUtil.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.hibernate.Session;
import org.hibernate.query.Query;

import web.course.dao.CourseDao;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.member.pojo.Member;

public class CourseDaoImpl implements CourseDao {

	@Override
	public int insert(Course course) {
		getSession().persist(course);
		getSession().flush(); // 先insert，插入副表時Hibernate看得到course_id(fk)
		return 1;
	}
	
	@Override
	public int insert(CourseRecurringRules courseRecurringRules) {
//		courseRecurringRules.setRuleId(null); // 回到暫態
		getSession().persist(courseRecurringRules);
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
		
		Session session = getSession();
		Query<?> query = session.createQuery(hql.toString());
		
		return query
				.setParameter("approvalStatus", course.getApprovalStatus())
				.setParameter("courseId", course.getCourseId())
				.executeUpdate();
	}

	@Override
	public Course selectById(Integer id) {
		return getSession().get(Course.class, id);
	}

	@Override
	public List<Course> selectAll() {
		final String hql = "FROM Course ORDER BY courseId";
		return getSession()
				.createQuery(hql, Course.class)
				.getResultList();
	}
	
	
	
	
//	private DataSource ds;
//	
//	public CourseDaoImpl() throws NamingException {
//		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/group1Project");
//	}
//
//	@Override
//	public int insert(Course course) {
//		String sql = "insert into COURSES(COACH_ID, ROOM_ID, TITLE, CATEGORY, SESSION_QUOTA, DESCRIPTION, CAPACITY_MAX, DATE_START, DATE_END, COURSE_PRICE, APPROVAL_STATUS, IMG_URL) " +
//					 "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//		try (
//			Connection conn = ds.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql)
//		) {
//			pstmt.setInt(1, course.getCoachId());
//			pstmt.setInt(2, course.getRoomId());
//			pstmt.setString(3, course.getTitle());
//			pstmt.setString(4, course.getCategory());
//			pstmt.setInt(5, course.getSessionQuota());
//			pstmt.setString(6, course.getDescription());
//			pstmt.setInt(7, course.getCapacityMax());
//			pstmt.setDate(8, new java.sql.Date(course.getDateStart().getTime()));
//			pstmt.setDate(9, new java.sql.Date(course.getDateEnd().getTime()));
//			pstmt.setInt(10, course.getCoursePrice());
//			pstmt.setString(11, course.getApprovalStatus());
//			pstmt.setString(12, course.getImgUrl());
//			return pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return -1;
//	}

}
