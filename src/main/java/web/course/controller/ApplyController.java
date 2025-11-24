package web.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.JsonObject;

import web.coach.pojo.CoachProfiles;
import web.coach.service.CoachService;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.NewCourseRequest;
import web.course.service.CourseService;
import web.user.pojo.User;

@RestController
@RequestMapping("course/newCourse")
public class ApplyController {
	@Autowired
	private CourseService service;
	@Autowired
	private CoachService coachService;
	
	@PostMapping
	public Map<String, Object> newCourse(@RequestBody NewCourseRequest newCourseRequest, 
										 @SessionAttribute(value = "user", required = false) User user) {
		CoachProfiles profile = coachService.findProfile(user.getUserId());
		Map<String, Object> respbody = new HashMap<>();
		JsonObject obj = new JsonObject();
		Course course = newCourseRequest.getCourse();
		course.setCoachId(profile.getCoachId());
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
