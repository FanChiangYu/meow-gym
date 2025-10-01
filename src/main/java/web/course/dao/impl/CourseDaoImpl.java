package web.course.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.course.dao.CourseDao;
import web.course.pojo.Course;

public class CourseDaoImpl implements CourseDao {
	private DataSource ds;
	
	public CourseDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/group1Project");
	}

	@Override
	public int insert(Course course) {
		String sql = "insert into COURSES(COACH_ID, ROOM_ID, TITLE, CATEGORY, SESSION_QUOTA, DESCRIPTION, CAPACITY_MAX, DATE_START, DATE_END, COURSE_PRICE, APPROVAL_STATUS, IMG_URL) " +
					 "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)
		) {
			pstmt.setInt(1, course.getCoachId());
			pstmt.setInt(2, course.getRoomId());
			pstmt.setString(3, course.getTitle());
			pstmt.setString(4, course.getCategory());
			pstmt.setInt(5, course.getSessionQuota());
			pstmt.setString(6, course.getDescription());
			pstmt.setInt(7, course.getCapacityMax());
			pstmt.setDate(8, new java.sql.Date(course.getDateStart().getTime()));
			pstmt.setDate(9, new java.sql.Date(course.getDateEnd().getTime()));
			pstmt.setInt(10, course.getCoursePrice());
			pstmt.setString(11, course.getApprovalStatus());
			pstmt.setString(12, course.getImgUrl());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
