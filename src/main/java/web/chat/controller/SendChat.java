package web.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.chat.dao.ChatDao;
import web.chat.pojo.Chats;


@Controller
@RequestMapping("chat")
public class SendChat {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ChatDao chatDao;

	@PostMapping("sendchat")
	@ResponseBody
	public Chats sendChatToDB(@RequestBody Chats chats) {

		if (chats == null) {
			chats = new Chats();
			chats.setMessage("請輸入訊息");
			chats.setSuccessful(false);
			return chats;
		} else {
			int result = chatDao.insert(chats); // 將聊天訊息送進資料庫
			if (result == 1) {
				chats.setMessage("輸入訊息成功");
				chats.setSuccessful(true);
			};
			return chats;
		}

	}

}



//@WebServlet("/chat/sendchat")
//public class SendChatServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private ChatDao chatDao;
//
//	@Override
//	public void init() throws ServletException {
//		try {
//			chatDao = new ChatDaoImpl();
//		} catch (Exception e) {
//			throw new ServletException("DAO init failed", e);
//		}
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//
//		Gson gson = new Gson();
//		// req.getReader()讀取 HTTP 請求的 Body (前端送來的 JSON)
//		// fromJson:把 JSON 轉成 Chats >> Java Bean
//
//		// req.getReader()讀取請求內的body 轉成Javabean
//		Chats chats = gson.fromJson(req.getReader(), Chats.class);
//
//		int result = chatDao.insert(chats); // 將聊天訊息送進資料庫
//
//		JsonObject respBody = new JsonObject();
//		respBody.addProperty("ok", result == 1);
//		// respBody.add("loginUser", gson.toJsonTree(loginUser));
//		// //因為loginUser是購物袋，所以要用JsonElement toJsonTree(Object src)和
//		// add(而非addProperty)去新增
//
//		String json = gson.toJson(respBody);
//		resp.setContentType("application/json");
//		resp.getWriter().write(json);
//
//	}
//
//	// 方便測試GET
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		resp.setContentType("application/json");
//		resp.getWriter().write("addChat alive");
//	}
//}

