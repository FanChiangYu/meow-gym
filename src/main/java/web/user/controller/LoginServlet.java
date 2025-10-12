package web.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import web.user.pojo.User;
import web.user.service.UserService;
import web.user.service.impl.UserServiceImpl;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
	private UserService userservice;
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		try {
			userservice = new UserServiceImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		User user = gson.fromJson(req.getReader(), User.class);
		if (user == null) {
			user = new User();
			user.setMessage("無此會員資料");
			user.setSuccessful(false);
			String json = gson.toJson(user);
			resp.getWriter().write(json);
			return;
		}

		user = userservice.login(user);
		if (user.isSuccessful()) {
			if (req.getSession(false) != null) {
				req.changeSessionId();
			}
			final HttpSession session = req.getSession();
			System.out.println("Login servlet's session" + session);
			session.setAttribute("loggedin", true);
			session.setAttribute("user", user);

		}

		String json = gson.toJson(user);
		System.out.println(json);
		resp.getWriter().write(json);

		// 登入成功後直接由伺服器分派到這一頁 為何不行?
		// req.getRequestDispatcher("/chat/chatroom.html").forward(req, resp);

	}
}
