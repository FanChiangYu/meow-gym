package web.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.coach.pojo.CoachProfiles;
import web.coach.service.CoachService;
import web.course.pojo.ClassResponse;
import web.course.pojo.ClassSessions;
import web.course.service.CourseService;
import web.user.pojo.User;

@RestController
@RequestMapping("course/manage")
public class ManageController {
	@Autowired
	private CourseService service;
	@Autowired
	private CoachService coachService;
	
	@GetMapping
	public List<ClassResponse> getCourses(@SessionAttribute(value = "user", required = false) User user){
		CoachProfiles profile = coachService.findProfile(user.getUserId());
		return service.getCoursesByCoach(profile.getCoachId());
	}
	
	@PutMapping
	public Map<String, Object> checkin(@RequestBody ClassSessions classSessions){
		Map<String, Object> respbody = new HashMap<>();
		Boolean chkResult = service.updateChkTime(classSessions); 
		respbody.put("successful", chkResult);
		if(chkResult) {
			respbody.put("message", "打卡成功");
		} else {
			respbody.put("message", "打卡失敗");
		}
		return respbody;
	}

}
