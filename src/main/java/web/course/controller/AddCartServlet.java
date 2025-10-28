package web.course.controller;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import core.util.CommonUtil;
import web.course.pojo.Course;
import web.course.service.CourseService;

@WebServlet("/course/addCart")
public class AddCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseService service;

	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), CourseService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Course course = json2Pojo(req, Course.class);
		JsonObject result = new JsonObject();
		HttpSession session = req.getSession();
		course = service.find(course);
		
		if (course.isSuccessful()) {
			String coachName = service.findName(course);
			result.addProperty("successful", course.isSuccessful());
			result.addProperty("message", course.getTitle());
			session.setAttribute("course", course);
			session.setAttribute("coachName", coachName);
		} else {
			result.addProperty("successful", course.isSuccessful());
			result.addProperty("message", "沒有此課程");
		}
		writePojo2Json(resp, result);
		
	}
}
