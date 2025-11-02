package web.course.controller;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import core.util.CommonUtil;
import web.course.pojo.ClassSessions;
import web.course.service.CourseService;

@WebServlet("/course/reserveSession")
public class reserveSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseService service;

	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), CourseService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 假設已從session取得userId = 1;
		Integer userId = 1;
		ClassSessions cs = json2Pojo(req, ClassSessions.class);
		Boolean result = service.reserveUpdate(cs, userId);
		JsonObject respBody = new JsonObject();
		if (result) {
			respBody.addProperty("successful", result);
		} else {
			respBody.addProperty("successful", result);
			respBody.addProperty("message", "操作失敗");
		}
		writePojo2Json(resp, respBody);
		
	}
}
