package web.course.controller;

import static core.util.CommonUtil.writePojo2Json;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.course.service.CourseService;
import web.course.service.impl.CourseServiceImpl;

@MultipartConfig
@WebServlet("/course/uploadFile")
public class UploadFileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static String fileRootPath;
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
//		fileRootPath = getServletContext().getRealPath("/img/");
		fileRootPath = System.getProperty("catalina.home") + "/webapps/meow-gym/img/";
//		fileRootPath = "/Users/fanjiangyu/sts3-workspace/meow-gym/src/main/webapp/img/course/";
		System.out.println(getServletContext().getRealPath("/img/"));
		System.out.println(fileRootPath);
		JsonObject respbody = new JsonObject();
		Part part = req.getPart("file");
		
		try (
				InputStream src = part.getInputStream();
			) {
			Path dest = Paths.get(fileRootPath, service.getFileName(part));
			System.out.println(dest);
			
//			if(Files.exists(dest)) {
//				respbody.addProperty("success", false);
//				respbody.addProperty("message", "圖片已存在");
//			} else {
//				Files.copy(src, dest);
//				respbody.addProperty("success", true);
//				respbody.addProperty("url", fileRootPath);
//			}
			
			Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
//			fileRootPath = fileRootPath + service.getFileName(part);
//			fileRootPath = System.getProperty("catalina.home") +"/img/" + service.getFileName(part);
			fileRootPath = req.getContextPath() +"/img/" + service.getFileName(part);
			respbody.addProperty("success", true);
			respbody.addProperty("url", fileRootPath);
			
//			String json = gson.toJson(respbody);
//			resp.setContentType("application/json");
//			resp.getWriter().write(json);
			writePojo2Json(resp, respbody);
		} catch (Exception e) {
			e.printStackTrace();
			respbody.addProperty("success", false);
			respbody.addProperty("message", "圖片上傳失敗");
//			String json = gson.toJson(respbody);
//			resp.setContentType("application/json");
//			resp.getWriter().write(json);
			writePojo2Json(resp, respbody);
		}
	}
	
//	private String getFilename(Part part) {
//		String fileDesc = part.getHeader("Content-Disposition");
//		int index = fileDesc.indexOf("filename=\"");
//		String fileName = fileDesc.substring(index + 10, fileDesc.length() - 1);
//		return FilenameUtils.getName(fileName);
//	}

}
