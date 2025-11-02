package web.promotions.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import core.util.CommonUtil;
import web.promotions.pojo.CoursePromo;
import web.promotions.service.PromotionsService;

@WebServlet("/promotions/reviewPromotions")
public class ReviewPromotionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PromotionsService service;
	private static final Gson GSON = new Gson();
	private static final Decoder DECODER = Base64.getDecoder();
	
	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), PromotionsService.class);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		CoursePromo coursePromo = json2Pojo(req, CoursePromo.class);
//		JsonObject obj = new JsonObject();
//		resp.setContentType("application/json");
//		CoursePromo coursePromo = GSON.fromJson(req.getReader(), CoursePromo.class);
//		String imgBase64Str = coursePromo.getImgBase64Str();
//		byte[] bytes = DECODER.decode(imgBase64Str);
//		Files.write(Paths.get("D:/uploaded_files/avatar.png"), bytes);
		
	}
}
