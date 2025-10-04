package web.course.service;

import core.service.CoreService;
import web.course.pojo.Course;

public interface CourseService extends CoreService {
	
	Course apply(Course course);
}
