package web.chat.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.chat.dao.ChatDao;
import web.chat.dao.impl.ChatDaoImpl;
import web.user.pojo.User;

@WebServlet("/chat/getusercourseid")
public class GetUserCourseid extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ChatDao chatDao;

	@Override
	public void init() throws ServletException {
		try {
			chatDao = new ChatDaoImpl();
		} catch (Exception e) {
			throw new ServletException("DAO init failed", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// session原本就有，用舊的就好
		HttpSession session = req.getSession(false);
		System.out.println("GetUserCourseidServlet" + session);

		if (session != null || session.getAttribute("user") == null) {
			// 從 session 取出登入者資訊,一定只有一筆!
			User loginUser = (User) session.getAttribute("user");
			int userid = loginUser.getUserId();
			System.out.println("GetUserCourseid loginUserID" + loginUser.getUserId());

			// selectUserCourseId方法
			// 將登入者的userid，查詢seesion_users表格的courseid
			chatDao.selectUserCourseId(userid);

			Gson gson = new Gson();

			JsonObject respBody = new JsonObject();
			respBody.add("loginUser", gson.toJsonTree(loginUser)); // 改

			// 寫出到前端
			String json = gson.toJson(respBody);
			resp.setContentType("application/json");
			resp.getWriter().write(json);

			// 以下...取出所有的courseid的人的訊息，放在聊天室的課程列表
			// 先取得使用者userId >> 再依據userId去查詢 session_users 內的 courseId列表 (不要找chats
			// table,因為太多筆找太久)

		}

	}

}
