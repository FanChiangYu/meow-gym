package web.index.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import web.user.pojo.User;

@RestController
@RequestMapping("index/loginData")
public class LoginController {
	@GetMapping
	public Map<String, Object> getLoginData (@SessionAttribute(value = "user", required = false) User user) {
		Map<String, Object> respbody = new HashMap<>();
		if (user == null) {
			respbody.put("successful", false);
		} else {
			respbody.put("successful", true);
			respbody.put("user", user);
		}
		return respbody;
	}
}
