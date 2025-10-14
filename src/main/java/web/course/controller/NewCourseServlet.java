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

import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.NewCourseRequest;
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
//		Gson gson = new Gson();
//		Course course = gson.fromJson(request.getReader(), Course.class);
		
//		Course course = json2Pojo(request, Course.class);
		JsonObject respbody = new JsonObject();
		NewCourseRequest newCourseRequest = json2Pojo(request, NewCourseRequest.class);
		Course course = newCourseRequest.getCourse();
		List<CourseRecurringRules> Rules = newCourseRequest.getRules(); 
		course = service.apply(course);
		
		if(course.isSuccessful()) {
			System.out.println(course.getCourseId());
			respbody = service.apply(Rules, course.getCourseId());
			writePojo2Json(response, respbody);
		} else {
			writePojo2Json(response, course);
		}
////		JsonObject jsonObject = new JsonObject();
		
//		if(errorMessage != null) {
//			jsonObject.addProperty("success", false);
//			jsonObject.addProperty("errorMessage", errorMessage);
//		} else {
//			jsonObject.addProperty("success", true);
//			jsonObject.addProperty("title", course.getTitle());
//		}
		
		
//		String json = gson.toJson(course);
//		response.setContentType("application/json");
//		response.getWriter().write(json);
//		writePojo2Json(response, course);
	}
	

}
