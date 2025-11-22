package web.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.course.pojo.ClassResponse;
import web.course.pojo.ClassSessions;
import web.course.service.CourseService;
import web.user.pojo.User;

@RestController
@RequestMapping("course/reserve")
public class ReserveController {
	@Autowired
	private CourseService service;
	
	@GetMapping
	public List<ClassResponse> bookClass(@SessionAttribute(value = "user", required = false) User user){
		return service.findClass(user.getUserId());
	}
	
	@PutMapping
	public Map<String, Object> reserveSession(@RequestBody ClassSessions cs, @SessionAttribute(value = "user", required = false) User user) {
		Boolean result = service.reserveUpdate(cs, user.getUserId());
		Map<String, Object> respBody = new HashMap<>();
		respBody.put("successful", result);
		if (!result) {
			respBody.put("message", "操作失敗");
		}
		return respBody;
	}

}
