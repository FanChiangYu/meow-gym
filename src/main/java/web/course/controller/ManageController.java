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

import web.course.pojo.ClassResponse;
import web.course.pojo.ClassSessions;
import web.course.service.CourseService;

@RestController
@RequestMapping("course/manage")
public class ManageController {
	@Autowired
	private CourseService service;
	
	@GetMapping
	public List<ClassResponse> getCourses(){
		Integer coachId = 1; // 假設已從session取得coachId = 1;
		return service.getCoursesByCoach(coachId);
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
