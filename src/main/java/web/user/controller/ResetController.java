package web.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.user.pojo.User;
import web.user.service.UserService;

@Controller
@RequestMapping("user")
public class ResetController {

	@Autowired
	private UserService service;
	
	@PostMapping("getCode")
	@ResponseBody
	public User getCode(@RequestBody User user) {
		return service.updateCode(user);
	}
	
	@PostMapping("getCodeAgain")
	@ResponseBody
	public Map<String, Object> getCodeAgain(@RequestBody User user) {
		Map<String,Object> respbody = new HashMap<>();
		int count = service.updateCodeAgain(user);
		if(count > 0) {
			respbody.put("successful", true);
		} else {
			respbody.put("successful", false);
		}
		return respbody;
	}
	
	@PostMapping("checkCode")
	@ResponseBody
	public Map<String, Object> checkCode(@RequestBody User user, HttpSession session) {
		Map<String,Object> respbody = new HashMap<>();
		boolean result = service.checkRestCode(user);
		if(result) {
			respbody.put("successful", true);
			session.setAttribute("resetUser", user);
			session.setAttribute("checkStatus", true);
		} else {
			respbody.put("successful", false);
		}
		return respbody;
	}
	
	@PostMapping("checkUser")
	@ResponseBody
	public Map<String, Object> checkUser(@RequestBody User user, 
			@SessionAttribute(value = "resetUser", required = false) User resetUser,
			@SessionAttribute(value = "checkStatus", required = false) Boolean checkStatus){
		Map<String,Object> respbody = new HashMap<>();
		
		if(checkStatus == null || resetUser == null || !checkStatus) {
			respbody.put("checkStatus", false);
			return respbody;
		}
		
		if(user.getUserId() != resetUser.getUserId()) {
			respbody.put("checkStatus", false);
			return respbody;
		}
		
		respbody.put("checkStatus", true);
		return respbody;
	}
	
	@PostMapping("change")
	@ResponseBody
	public Map<String, Object> change(@RequestBody User user, HttpSession session) {
		Map<String,Object> respbody = new HashMap<>();
		boolean result = service.changePassword(user);
		if(result) {
			session.removeAttribute("resetUser");
			session.removeAttribute("checkStatus");
			respbody.put("successful", true);
		} else {
			respbody.put("successful", false);
		}
		return respbody;
	}
}
