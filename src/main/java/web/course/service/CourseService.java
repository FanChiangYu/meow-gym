package web.course.service;

import java.util.List;

import javax.servlet.http.Part;

import com.google.gson.JsonObject;

import core.service.CoreService;
import netscape.javascript.JSObject;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;

public interface CourseService extends CoreService {
	
	Course apply(Course course);
	
	JsonObject apply(List<CourseRecurringRules> rules, Integer id);
	
	JsonObject removeById(Integer id);
	
	List<Course> findAll();
	
	Course find(Course cousre);
	
	String modify(Course cousre);
}
