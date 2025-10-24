package web.course.controller;

import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.JsonObject;

import core.util.CommonUtil;
import core.util.FileUtil;
import web.course.service.CourseService;
import web.course.service.impl.CourseServiceImpl;

@MultipartConfig
@WebServlet("/course/uploadFile")
public class UploadFileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static String imgReqPath;
	private CourseService service;
	
	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), CourseService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonObject respbody = new JsonObject();
		Part part = req.getPart("file");
		String fileName = FileUtil.getFileName(part);
		fileName = service.addTimestampToFileName(fileName);
		imgReqPath = "/meow-gym/getImg?file=" + fileName;
		
		boolean writeResult = service.writeToImgPath(part);
		
		if (writeResult) {
			respbody.addProperty("success", writeResult);
			respbody.addProperty("url", imgReqPath);
			writePojo2Json(resp, respbody);
		} else {
			respbody.addProperty("success", writeResult);
			respbody.addProperty("message", "圖片上傳失敗");
			writePojo2Json(resp, respbody);
		}	
	}
}
