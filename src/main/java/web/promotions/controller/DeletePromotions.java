package web.promotions.controller;

import java.util.HashMap;
import java.util.Map;

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
public class DeletePromotions {
	@Autowired
	private PromotionsService service;

	@PostMapping("delete")
	@ResponseBody
	public Map<String, Object> deletePromo(@RequestBody CoursePromo coursePromo) {
		Map<String, Object> result = new HashMap<>();
		if (service.delete(coursePromo) == 1) {
			result.put("successful", true);
			result.put("message", "刪除成功");
		} else {
			result.put("successful", false);
			result.put("message", "此課程沒有促銷資訊，請在確認");
		}
		return result;
	}

}
