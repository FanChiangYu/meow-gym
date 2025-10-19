package web.user.controller;

import static core.util.CommonUtil.json2Pojo;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import web.user.pojo.User;
import web.user.service.UserService;
import web.user.service.impl.UserServiceImpl;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	@Override
	public void init() throws ServletException {
		try {
			userService = new UserServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = json2Pojo(req, User.class);
		JsonObject obj = new JsonObject();
		User message = userService.login(user);
		obj.add("", obj);
	}
}
