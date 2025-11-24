package web.index.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.index.service.IndexService;
import web.user.pojo.User;

@RestController
@RequestMapping("index/userCenter")
public class UserCenterController {
	@Autowired
	private IndexService service;
	
	@GetMapping
	public Map<String, Object> toUserCenter (@SessionAttribute(value = "user", required = false) User user){
		Map<String, Object> respbody = new HashMap<>();
		
		if(user == null) {
			respbody.put("url", "/meow-gym/user/login.html");
			return respbody;
		}
		
		switch (user.getRole()) {
		case 1:
			respbody.put("url", "/meow-gym/user/edit.html");
			break;
			
		case 2:
			boolean result = service.coachApprovalStatus(user);
			if(result) {
				respbody.put("url", "/meow-gym/course/manageCourse.html");
			} else {
				respbody.put("url", "/meow-gym/coach/applyCoach.html");
			}
			break;
			
		case 3:
			respbody.put("url", "/meow-gym/blacklist/reviewBlocklist.html");
			break;

		default:
			respbody.put("url", "/meow-gym/index/index.html");
			break;
		}
		return respbody;
	}
}
