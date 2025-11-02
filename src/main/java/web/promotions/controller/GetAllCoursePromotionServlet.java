package web.promotions.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.util.CommonUtil;
import web.course.pojo.Course;
import web.promotions.service.PromotionsService;

@WebServlet("/promotions/getAll")
public class GetAllCoursePromotionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PromotionsService service;
	
	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), PromotionsService.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Course> list = service.findCourseAndPromotionAll();
		CommonUtil.writePojo2Json(resp, list);
	}
}
