package web.course.controller;

import java.io.IOException;
import java.util.List;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import core.util.CommonUtil;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.NewCourseRequest;
import web.course.service.CourseService;
import web.course.service.impl.CourseServiceImpl;

//@WebServlet("/course/newCourse")
public class NewCourseServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private CourseService service;
	
	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), CourseService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject respbody = new JsonObject();
		NewCourseRequest newCourseRequest = json2Pojo(request, NewCourseRequest.class);
		Course course = newCourseRequest.getCourse();
		List<CourseRecurringRules> Rules = newCourseRequest.getRules(); 
		course = service.apply(course);
		
		if(course.isSuccessful()) {
			respbody = service.apply(Rules, course);
			writePojo2Json(response, respbody);
		} else {
			writePojo2Json(response, course);
		}
	}
	

}
