package web.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.NewCourseRequest;
import web.course.service.CourseService;

@RestController
@RequestMapping("course/newCourse")
public class ApplyController {
	@Autowired
	private CourseService service;
	
	@PostMapping
	public Map<String, Object> newCourse(@RequestBody NewCourseRequest newCourseRequest) {
		Map<String, Object> respbody = new HashMap<>();
		JsonObject obj = new JsonObject();
		Course course = newCourseRequest.getCourse();
		List<CourseRecurringRules> Rules = newCourseRequest.getRules(); 
		course = service.apply(course);
		
		if(course.isSuccessful()) {
			obj = service.apply(Rules, course);
			respbody.put("successful", obj.get("successful").getAsBoolean());
			respbody.put("message", obj.get("message").getAsString());
		} else {
			respbody.put("successful", course.isSuccessful());
			respbody.put("message", course.getMessage());
		}
		return respbody;
	}
}
