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

import web.course.pojo.ClassResponse;
import web.course.pojo.ClassSessions;
import web.course.service.CourseService;

@RestController
@RequestMapping("course/reserve")
public class ReserveController {
	@Autowired
	private CourseService service;
	
	@GetMapping
	public List<ClassResponse> bookClass(){
		// 假設已從session取得userId = 1;
		Integer userId = 1;
		return service.findClass(userId);
	}
	
	@PutMapping
	public Map<String, Object> reserveSession(@RequestBody ClassSessions cs) {
		// 假設已從session取得userId = 1;
		Integer userId = 1;
		Boolean result = service.reserveUpdate(cs, userId);
		Map<String, Object> respBody = new HashMap<>();
		respBody.put("successful", result);
		if (!result) {
			respBody.put("message", "操作失敗");
		}
		return respBody;
	}

}
