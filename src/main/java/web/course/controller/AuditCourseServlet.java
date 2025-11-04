package web.course.controller;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import core.util.CommonUtil;
import web.course.pojo.Course;
import web.course.service.CourseService;
import web.course.service.impl.CourseServiceImpl;

//@WebServlet("/course/auditCourse")
public class AuditCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseService service;

	@Override
	public void init() throws ServletException {		
		service = CommonUtil.getBean(getServletContext(), CourseService.class);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Course course = json2Pojo(req, Course.class);
		JsonObject obj = new JsonObject();
		String message = service.modify(course);
		obj.addProperty("message", message);
		writePojo2Json(resp, obj);
	}

}