package web.course.dao;

import core.dao.CoreDao;
import web.course.pojo.Course;

public interface CourseDao extends CoreDao<Course, Integer>{
	
	int insert(Course course);
	
}
