package web.index.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.index.service.IndexService;
import web.promotions.pojo.CoursePromo;

@Controller
@RequestMapping("index")
public class IndexController {
	@Autowired
	private IndexService service;
	
	@GetMapping("getPromotions")
	@ResponseBody
	public List<CoursePromo> getPromotions() {
		return service.findAllPromo();
	}
}
