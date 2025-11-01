package web.index.controller;

import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.util.CommonUtil;
import web.index.service.IndexService;
import web.promotions.pojo.CoursePromo;

@WebServlet("/index/getPromotions")
public class GetPromotionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IndexService service;

	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), IndexService.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<CoursePromo> cpList = service.findAllPromo();
		writePojo2Json(resp, cpList);
	}
}
