package web.course.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import web.course.service.CourseService;

@Controller
@RequestMapping("course")
public class ImgController {
	@Value("#{systemProperties['catalina.home'].concat('/img/')}")
	private String fileRootPath;
	@Autowired
	private CourseService service;
	
	@GetMapping(path = "getImg", produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] getImg(@RequestParam(value = "file") String fileName) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(fileRootPath, fileName));
		return bytes;
	}
	
	@PostMapping("uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		Map<String, Object> respbody = new HashMap<>();
		String fileName = file.getOriginalFilename();
		fileName = service.addTimestampToFileName(fileName);
		String imgReqPath = "/meow-gym/course/getImg?file=" + fileName;
		
		file.transferTo(Paths.get(fileRootPath, fileName));
		respbody.put("success", true);
		respbody.put("url", imgReqPath);
		
		return respbody;
	}

	
}
