package web.coach.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.coach.pojo.CoachCertificates;
import web.coach.pojo.CoachEducations;
import web.coach.pojo.CoachExperiences;
import web.coach.pojo.CoachProfiles;
import web.coach.service.CoachService;
import web.user.pojo.User;

@RestController
@RequestMapping("coach/audit")
public class AuditCoachController {
	@Autowired
	private CoachService service;
	
	@GetMapping("{userId}")
	public Map<String, Object> getCoachInfo (@PathVariable Integer userId) {
		Map<String, Object> respbody = new HashMap<>();
		
		User user = service.findUser(userId);
		CoachProfiles profile = service.findProfile(userId);
		CoachCertificates certificate = service.findCertificate(profile.getCoachId());
		CoachEducations education = service.findEducation(profile.getCoachId());
		CoachExperiences experience = service.findExperience(profile.getCoachId());
		
		respbody.put("user", user);
		respbody.put("profile", profile);
		respbody.put("certificate", certificate);
		respbody.put("education", education);
		respbody.put("experience", experience);
		
		return respbody;
	}
	
	@PutMapping
	public Map<String, Object> updateProfile(@RequestBody CoachProfiles profile){
		Map<String, Object> respbody = new HashMap<>();
		Boolean result = service.updateApprovalStatus(profile);
		respbody.put("successful", result);
		return respbody;
	}
}
