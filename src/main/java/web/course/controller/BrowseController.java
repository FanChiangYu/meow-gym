package web.course.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.course.pojo.Course;
import web.course.service.CourseService;

@Controller
@RequestMapping("course")
public class BrowseController {
	@Autowired
	private CourseService service;
	
	@PostMapping("addCart")
	@ResponseBody
	public Map<String, Object> addCart(HttpSession session, @RequestBody Course course) {
		Map<String, Object> result = new HashMap<>();
		course = service.find(course);
		
		if (course.isSuccessful()) {
			String coachName = service.findName(course);
			result.put("successful", course.isSuccessful());
			result.put("message", course.getTitle());
			session.setAttribute("course", course);
			session.setAttribute("coachName", coachName);
		} else {
			result.put("successful", course.isSuccessful());
			result.put("message", "沒有此課程");
		}
		return result;
	}
}
