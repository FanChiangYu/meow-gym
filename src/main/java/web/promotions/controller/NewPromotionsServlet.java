package web.promotions.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import core.util.CommonUtil;
import web.promotions.pojo.CoursePromo;
import web.promotions.service.PromotionsService;

@WebServlet("/promotions/verify")
public class NewPromotionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PromotionsService promotionsService;
	
	@Override
	public void init() throws ServletException {
		promotionsService = CommonUtil.getBean(getServletContext(), PromotionsService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd")
				.create();
		CoursePromo coursePromo = gson.fromJson(req.getReader(), CoursePromo.class);
		if (coursePromo == null) {
			coursePromo = new CoursePromo();
			coursePromo.setMessage("缺少資料");
			coursePromo.setSuccessful(false);
		} else {
			coursePromo = promotionsService.apply(coursePromo);
		}
		resp.getWriter().write(gson.toJson(coursePromo));
	}
}
