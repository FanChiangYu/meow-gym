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


//@WebServlet("/chat/userinfo")
//public class UserInfoServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		// session原本就有，用舊的就好>> 有新的就不建，有舊的就用舊的
//		HttpSession session = req.getSession(false);
//		System.out.println("UserInfoServlet" + session);
//
//		// if (session != null || session.getAttribute("user") == null) {
//		if (session != null) {
//			// 從 session 取出登入者資訊,一定只有一筆!
//			User loginUser = (User) session.getAttribute("user");
//			System.out.println("loginUser" + loginUser);
//
//			Gson gson = new Gson();
//
//			JsonObject respBody = new JsonObject();
//			respBody.add("loginUser", gson.toJsonTree(loginUser));
//
//			// 寫出到前端
//			String json = gson.toJson(respBody);
//			resp.setContentType("application/json");
//			resp.getWriter().write(json);
//
//			// 以下...取出所有的courseid的人的訊息，放在聊天室的課程列表
//			// 先取得使用者userId >> 再依據userId去查詢 session_users 內的 courseId列表 (不要找chats
//			// table,因為太多筆找太久)
//
//		}
//	}
//
//}

