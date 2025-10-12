package web.chat.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.chat.dao.ChatDao;
import web.chat.dao.impl.ChatDaoImpl;
import web.chat.pojo.Chats;

@WebServlet("/chat/getchat")
public class getChat extends HttpServlet {

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
		Gson gson = new Gson();
		//Chats courseIdchats = gson.fromJson(req.getReader(), Chats.class); // 轉成Chats JavaBean，對照到內部courseId，才能取出該courseId正確的值
		
		//前端要直接給一個courseId數字
		Chats courseIdchats = gson.fromJson(req.getParameter("courseId"), Chats.class); //because of get method, getReader() 是讀 body
		
		System.out.println("courseIdchats"+ courseIdchats); //檢查 >> null >> 有誤
		
		List<Chats> result = chatDao.selctChatsByCourseId(courseIdchats.getCourseId()); //????
		JsonObject respbody = new JsonObject();
		respbody.addProperty("courseId", result.toString()); //????
		String json = gson.toJson(respbody);
		resp.getWriter().write(json);
		
	}

}
