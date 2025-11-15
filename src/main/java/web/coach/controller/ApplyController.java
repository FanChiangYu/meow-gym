package web.coach.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.coach.pojo.CoachProfiles;
import web.coach.service.CoachService;
import web.user.pojo.User;

@RestController
@RequestMapping("coach/apply")
public class ApplyController {
	@Autowired
	private CoachService service;
	
	@GetMapping
	public Map<String, Object> getApplyData(@RequestBody User user){
		Map<String, Object> respbody = new HashMap<>();
		return respbody;
		
	}

}
