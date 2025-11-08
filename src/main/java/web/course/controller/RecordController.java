package web.course.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.course.pojo.ClassResponse;
import web.course.service.CourseService;

@RestController
@RequestMapping("course/record")
public class RecordController {
	@Autowired
	private CourseService service;
	
	@GetMapping
	public List<ClassResponse> getRecord(){
		Integer userId = 1; // 假設已從session取得userId = 1;
		return service.findClass(userId);
	}
	
	@GetMapping("{courseId}")
	public Map<String, Object> goChat(@PathVariable Integer courseId, HttpSession session){
		Map<String, Object> respbody = new HashMap<>();
		session.setAttribute("courseId", courseId);
		respbody.put("ok", true);
		return respbody;
	}
}
