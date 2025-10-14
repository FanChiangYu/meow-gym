package web.course.dao;

import core.dao.CoreDao;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;

public interface CourseDao extends CoreDao<Course, Integer>{
	
	int insert(Course course);
	
	int insert(CourseRecurringRules courseRecurringRules);
	
}
