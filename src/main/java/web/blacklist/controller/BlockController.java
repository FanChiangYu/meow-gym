package web.blacklist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.blacklist.service.BlockListService;
import web.user.pojo.User;

@Controller
@RequestMapping("blacklist")
public class BlockController {
	@Autowired
	private BlockListService blackListService;

	@GetMapping("reviewBlocklist")
	@ResponseBody
	public List<User> findAll() {
		return blackListService.selectAllBlockService();
	}

	@PostMapping("block")
	@ResponseBody
	public Map<String, Object> updateState(@RequestBody User user) {
		Map<String, Object> respbody = new HashMap<>();
		int count = blackListService.updateBlockStateService(user);
		if(count == 1) {
			respbody.put("successful", true);
			respbody.put("message", "已加入黑名單完成");
		} else {
			respbody.put("successful", false);
			respbody.put("message", "此會員已在黑名單或不存在");
		}
		return respbody;
	}
	@PostMapping("unlock")
	@ResponseBody
	public Map<String, Object> updateState2(@RequestBody User user) {
		Map<String, Object> respbody = new HashMap<>();
		int count = blackListService.updateUnlockStateService(user);
		if(count == 1) {
			respbody.put("successful", true);
			respbody.put("message", "移除黑名單完成");
		} else {
			respbody.put("successful", false);
			respbody.put("message", "此會員沒資料不存在");
		}
		return respbody;
	}
}
