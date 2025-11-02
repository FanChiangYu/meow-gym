package web.course.controller;

import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.util.CommonUtil;
import web.course.pojo.ClassResponse;
import web.course.service.CourseService;

@WebServlet("/course/bookClass")
public class BookClass extends HttpServlet {
	private static final long serialVersionUID = 2025350997976321063L;
	private CourseService service;
	
	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), CourseService.class);	
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 假設已從session取得userId = 1;
		Integer userId = 1;
		List<ClassResponse> respBody = service.findClass(userId);
		writePojo2Json(resp, respBody);
	}
}
