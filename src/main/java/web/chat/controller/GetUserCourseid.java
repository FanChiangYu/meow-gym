package web.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.chat.dao.ChatDao;
import web.chat.pojo.UserCourseDTO;
import web.user.pojo.User;

@Controller
@RequestMapping("chat")
public class GetUserCourseid {

//	private static final long serialVersionUID = 1L;
//
//	@Autowired
//	private ChatDao chatDao;
//	
//	@GetMapping("getusercourseid")
//	@ResponseBody
//	public Map<String, Object> getUserCourseId(HttpSession session) {
//	
//		Map<String, Object> body = new HashMap<>();
//	
//		// session原本就有，用舊的就好
//		User loginUser = (User) session.getAttribute("user");
//		int userid = loginUser.getUserId();
//	
//		List<UserCourseDTO> usercourseid = chatDao.selectUserCourseId(userid);
//	
//		body.put("ok", true);
//		body.put("usercourseid", usercourseid);
//	
//		return body;
//	
//	}

	private static final long serialVersionUID = 1L;

	@Autowired
	private ChatDao chatDao;

	@GetMapping("getusercourseid")
	@ResponseBody
	public Map<String, Object> getUserCourseId(HttpSession session) {

		Map<String, Object> body = new HashMap<>();

		User loginUser = (User) session.getAttribute("user");

		// add
		Integer courseIdNew = (Integer) session.getAttribute("courseId");
		System.out.println("fan courseId" + courseIdNew); // send to frontend
		// add end

		//List<UserCourseDTO> usercourseid = chatDao.selectUserCourseId(userid);

		body.put("ok", true);
		body.put("usercourseid", courseIdNew);

		System.out.println("fan body" + body);
		return body;

	}

}
