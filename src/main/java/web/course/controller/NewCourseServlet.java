package web.course.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.course.pojo.Course;
import web.course.service.CourseService;
import web.course.service.impl.CourseServiceImpl;

@WebServlet("/course/newCourse")
public class NewCourseServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private CourseService service;
	
	@Override
	public void init() throws ServletException {
		try {
			service = new CourseServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		Course course = gson.fromJson(request.getReader(), Course.class);
		System.out.println(course.getTitle());
		course = service.apply(course);
		JsonObject jsonObject = new JsonObject();
		
//		if(errorMessage != null) {
//			jsonObject.addProperty("success", false);
//			jsonObject.addProperty("errorMessage", errorMessage);
//		} else {
//			jsonObject.addProperty("success", true);
//			jsonObject.addProperty("title", course.getTitle());
//		}
		
		
		String json = gson.toJson(course);
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	

}
