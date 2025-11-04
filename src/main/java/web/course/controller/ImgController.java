package web.course.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("course")
public class ImgController {
	@Value("#{systemProperties['catalina.home'].concat('/img/')}")
	private String fileRootPath;
	
	@GetMapping(path = "getImg", produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] getImg(@RequestParam(value = "file") String fileName) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(fileRootPath, fileName));
		System.out.println(bytes);
		return bytes;
	}
	
//	@GetMapping("getImg")
//	public ResponseEntity <byte[]> download(@RequestParam(value = "file") String fileName) throws IOException {
//		System.out.println("------------------" + fileName + "----------------------");
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentDisposition(
//			ContentDisposition
//				.attachment()
//				.filename(fileName, StandardCharsets.UTF_8)
//				.build()
//		);
//		byte[] body = Files.readAllBytes(Paths.get(fileRootPath, fileName));
//		return new ResponseEntity<byte[]>( 
//				body,
//				headers,
//				HttpStatus.OK
//		);
//	}
	
}
