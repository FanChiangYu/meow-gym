package web.chat.controller;

import java.io.IOException;

import javax.naming.NamingException;
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

import web.chat.pojo.Chats;
import web.user.dao.UserDao;
import web.user.pojo.User;

@WebServlet("/chat/sendchat")
public class SendChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ChatDao chatDao;
	// private UserDao userDao; //新增 好像不是這樣做!

	@Override
	public void init() throws ServletException {
		try {
			chatDao = new ChatDaoImpl();
		} catch (Exception e) {
			throw new ServletException("DAO init failed", e);
		}
	}

//	public AddChatServlet(ChatDao chatDao) throws NamingException {
//		chatDao = new ChatDaoImpl();
//	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// session原本就有，用舊的就好
		//HttpSession session = req.getSession(false);
		//System.out.println("AddChatServlet" + session); // 和ws session記憶體空間不一樣

		//if (session != null || session.getAttribute("user") == null) {
			// 從 session 取出登入者
			//User loginUser = (User) session.getAttribute("user");
			//System.out.println("loginUser" + loginUser);
		    //loginUser要回傳到前端，chatroom.html
			
			Gson gson = new Gson();
			// req.getReader()讀取 HTTP 請求的 Body (前端送來的 JSON)
			// fromJson:把 JSON 轉成 Chats >> Java Bean

			// req.getReader()讀取請求內的body 轉成Javabean
			Chats chats = gson.fromJson(req.getReader(), Chats.class);

			int result = chatDao.insert(chats); // 將聊天訊息送進資料庫

			JsonObject respBody = new JsonObject();
			respBody.addProperty("ok", result == 1);
			//respBody.add("loginUser", gson.toJsonTree(loginUser)); //因為loginUser是購物袋，所以要用JsonElement toJsonTree(Object src)和 add(而非addProperty)去新增

			String json = gson.toJson(respBody);
			resp.setContentType("application/json");
			resp.getWriter().write(json);
		//}

		
	}

	// 方便測試GET
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.getWriter().write("addChat alive");
	}

}
