package web.promotions.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.promotions.pojo.CoursePromo;
import web.promotions.service.PromotionsService;

@Controller
@RequestMapping("promotions")
public class NewPromotionsController {
	@Autowired
	private PromotionsService service;

	@PostMapping("verify")
	@ResponseBody
	public CoursePromo verify(@RequestBody CoursePromo coursePromo) throws IOException {
		if (coursePromo == null) {
		coursePromo = new CoursePromo();
		coursePromo.setMessage("缺少資料");
		coursePromo.setSuccessful(false);
		} else {
		coursePromo = service.apply(coursePromo);
		}
		return coursePromo;
	}
}
