package web.course.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import core.util.FileUtil;

//@WebServlet("/course/getImg")
public class GetImgServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("file");	
		byte[] bytes = FileUtil.readFromImgPath(fileName);
		System.out.println(bytes);
		resp.setContentType("image/png");
		resp.getOutputStream().write(bytes);
	}

}
