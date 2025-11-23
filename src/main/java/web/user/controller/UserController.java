package web.user.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import web.user.pojo.Country;
import web.user.pojo.District;
import web.user.pojo.User;
import web.user.service.UserService;

@Controller
@RequestMapping("user")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	@PostMapping("login")
	@ResponseBody
	public User login(@RequestBody User user, HttpSession session) {

		User respbody = userService.login(user);

		if (!respbody.isSuccessful()) {
			System.out.println("error");
		} else {
			session.setAttribute("user", respbody);
		}
		return respbody;

	}

	@PostMapping("register")
	@ResponseBody
	public User register(@RequestParam String cntCode, @RequestParam String distCode,
			@RequestParam String detailAddress, @RequestParam String email, @RequestParam String name,
			@RequestParam String password, @RequestParam String phone, @RequestParam String birthday,
			@RequestParam String gender, @RequestParam("avatarFile") MultipartFile avatarFile) throws IOException {

		User user = new User();
		user.setCntCode(Integer.parseInt(cntCode));
		user.setDistCode(Integer.parseInt(distCode));
		user.setDetailAddress(detailAddress);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setPhone(phone);
		user.setBirthday(Date.valueOf(birthday));
		user.setGender(gender);
		user.setAvatarFile(avatarFile);

		return userService.register(user);
	}

	@PostMapping("edit")
	@ResponseBody
	public User edit(@RequestBody User user, HttpSession session) {

		User respbody = userService.edit(user);

		if (!respbody.isSuccessful()) {
			System.out.println("error");
		} else {
			session.setAttribute("user", respbody);
		}
		return respbody;

	}

	@GetMapping("logout")
	@ResponseBody
	public void logout(HttpSession session) {
		session.removeAttribute("user");
	}

	@GetMapping("dist")
	@ResponseBody
	public Map<String, Object> getDist() {
		Map<String, Object> respbody = new HashMap<>();
		List<District> distList = userService.findDist();
		List<Country> countryList = userService.findCountry();
		respbody.put("distList", distList);
		respbody.put("countryList", countryList);
		return respbody;
	}

}
