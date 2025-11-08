package web.user.controller;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = json2Pojo(req, User.class);
		HttpSession session = req.getSession();

		User respbody = userService.register(user);

	}

}
