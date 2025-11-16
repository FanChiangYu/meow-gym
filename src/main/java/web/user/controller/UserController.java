package web.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.user.pojo.User;
import web.user.service.UserService;

@Controller
@RequestMapping("user")
public class UserController extends HttpServlet {

	@Autowired
	private UserService userService;

	@PostMapping("login")
	@ResponseBody
	public User login(@RequestBody User user, HttpSession session) {

		User respbody = userService.login(user);

		if (!respbody.isSuccessful()) {
			System.out.println("error");
		} else {
			session.setAttribute("user", respbody);
		}
		return respbody;

	}

	@PostMapping("register")
	@ResponseBody
	public User register(@RequestBody User user, HttpSession session) throws IOException {
		if (user == null) {
			user = new User();
			user.setMessage("無會員資訊");
			user.setSuccessful(false);
			return user;
		}
		return userService.register(user);
	}

}
