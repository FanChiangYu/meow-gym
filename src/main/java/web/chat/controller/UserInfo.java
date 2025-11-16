package web.chat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.user.pojo.User;


//Spring MVC
@Controller
@RequestMapping("chat")
public class UserInfo{
	private static final long serialVersionUID = 1L;

	Map<String, Object> body = new HashMap<>();
	
	@GetMapping("userinfo")
	@ResponseBody
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		User loginUser = (User) session.getAttribute("user");
		
		body.put("loginUser", loginUser);
		return body; //Jackson 序列化成 JSON
	}
}


