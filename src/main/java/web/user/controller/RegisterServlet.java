package web.user.controller;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.util.CommonUtil;
import web.user.pojo.User;
import web.user.service.UserService;

@WebServlet("/user/register")
public class RegisterServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private UserService userService;

	@Override
	public void init() throws ServletException {
		userService = CommonUtil.getBean(getServletContext(), UserService.class);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = json2Pojo(req, User.class);
		HttpSession session = req.getSession();

		User respbody = userService.register(user);

		if (respbody.isSuccessful()) {
			session.setAttribute("user", respbody);
			writePojo2Json(resp, respbody);
		} else {
			System.out.println("error");
		}

	}

}
