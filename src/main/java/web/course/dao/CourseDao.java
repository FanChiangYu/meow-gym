package web.course.dao;

import java.util.List;

import core.dao.CoreDao;
import web.coach.pojo.CoachProfiles;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.user.pojo.User;

public interface CourseDao extends CoreDao<Course, Integer>{
	
	int insert (Course course);
	
	int insert (CourseRecurringRules courseRecurringRules);
	
	CoachProfiles selectByCoachId (Integer id);
	
	User selectByUserId (Integer id);
	
	List<CourseRecurringRules> selectByCourseId (Integer id);
	
}
