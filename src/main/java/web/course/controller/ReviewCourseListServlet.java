package web.course.controller;



import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.util.CommonUtil;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.CourseResponse;
import web.course.pojo.NewCourseRequest;
import web.course.service.CourseService;
import web.course.service.impl.CourseServiceImpl;
import web.member.service.MemberService;
import web.user.pojo.User;

//@WebServlet("/course/reviewCourseList")
public class ReviewCourseListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private CourseService service;
	
	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), CourseService.class);	
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// test
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
//		System.out.println(user.getUserId());
//		System.out.println(user.getEmail());
//		System.out.println(user.getName());
		// test
		List<Course> courses =  service.findAll();
		writePojo2Json(resp, courses);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CourseResponse courseResponse = new CourseResponse();
		Course course = json2Pojo(req, Course.class);
		course = service.find(course);
		String userName = service.findName(course);
		List<CourseRecurringRules> rules = service.findRules(course);
		courseResponse.setCourse(course);
		courseResponse.setUserName(userName);
		courseResponse.setRules(rules);
		writePojo2Json(resp, courseResponse);
	}
}
