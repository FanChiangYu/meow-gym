package web.course.dao;

import java.util.Date;
import java.util.List;

import core.dao.CoreDao;
import web.coach.pojo.CoachProfiles;
import web.course.pojo.ClassSessions;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.order.pojo.Orders;
import web.user.pojo.User;

public interface CourseDao extends CoreDao<Course, Integer>{
	
	int insert (Course course);
	
	int insert (CourseRecurringRules courseRecurringRules);
	
	int insert (ClassSessions classSessions);
	
	CoachProfiles selectByCoachId (Integer id);
	
	User selectByUserId (Integer id);
	
	List<CourseRecurringRules> selectByCourseId (Integer id);
	
	List<Orders> selectOrderByUserId (Integer id);
	
	List<Integer> selectCourseIdByOrderId (Integer id);
	
	List<ClassSessions> selectClassSessionBycourseID (Integer id);
	
}
