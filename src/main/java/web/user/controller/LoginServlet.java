package web.user.controller;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;
import static core.util.UserConstants.SERVICE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.user.pojo.User;
	
@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = json2Pojo(req, User.class);
		if (user == null) {
			user = new User();
			user.setMessage("無會員資訊");
			user.setSuccessful(false);
			writePojo2Json(resp, user);
			return;
		}

		user = SERVICE.login(user);
		if (user.isSuccessful()) {
			if (req.getSession(false) != null) {
				req.changeSessionId();
			}
			final HttpSession session = req.getSession();
			session.setAttribute("loggedin", true);
			session.setAttribute("user", user);
			System.out.println(user);
		}
		writePojo2Json(resp, user);

	}

}
