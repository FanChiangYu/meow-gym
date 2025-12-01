package web.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.CourseResponse;
import web.course.service.CourseService;

@RestController
@RequestMapping("course/audit")
public class AuditController {
	@Autowired
	private CourseService service;
	
	@GetMapping
	public List<Course> showCourseList() {
		return service.findAll();
	}
	
	@GetMapping("{courseId}")
	public CourseResponse reviewCourseList(@PathVariable Integer courseId) {
		CourseResponse courseResponse = new CourseResponse();
		Course course = new Course();
		course.setCourseId(courseId);
		course = service.find(course);
		String userName = service.findName(course);
		List<CourseRecurringRules> rules = service.findRules(course);
		courseResponse.setCourse(course);
		courseResponse.setUserName(userName);
		courseResponse.setRules(rules);
		return courseResponse;
	}
	
	@PutMapping
	public Map<String, Object> auditCourse (@RequestBody Course course) {
		Map<String, Object> respBody = new HashMap<>();
		String message = service.modify(course);
		respBody.put("message", message);
		return respBody;
	}

}
