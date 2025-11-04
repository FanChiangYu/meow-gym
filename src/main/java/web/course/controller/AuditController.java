package web.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.CourseResponse;
import web.course.pojo.NewCourseRequest;
import web.course.service.CourseService;

@Controller
@RequestMapping("course")
public class AuditController {
	@Autowired
	private CourseService service;
	
	@PostMapping("newCourse")
	@ResponseBody
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
	
	@GetMapping("reviewCourseList")
	@ResponseBody
	public List<Course> reviewCourseList() {
		return service.findAll();
	}
	
	@PostMapping("reviewCourseList")
	@ResponseBody
	public CourseResponse reviewCourseList(@RequestBody Course course) {
		CourseResponse courseResponse = new CourseResponse();
		course = service.find(course);
		String userName = service.findName(course);
		List<CourseRecurringRules> rules = service.findRules(course);
		courseResponse.setCourse(course);
		courseResponse.setUserName(userName);
		courseResponse.setRules(rules);
		return courseResponse;
	}
	
	@PostMapping("auditCourse")
	@ResponseBody
	public Map<String, Object> auditCourse (@RequestBody Course course) {
		Map<String, Object> respBody = new HashMap<>();
		String message = service.modify(course);
		respBody.put("message", message);
		return respBody;
	}

}
