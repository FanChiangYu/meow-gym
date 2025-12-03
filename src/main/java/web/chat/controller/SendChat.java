package web.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.chat.dao.ChatDao;
import web.chat.pojo.Chats;


@Controller
@RequestMapping("chat")
public class SendChat {

	@Autowired
	private ChatDao chatDao;

	@PostMapping("sendchat")
	@ResponseBody
	public Chats sendChatToDB(@RequestBody Chats chats) {

		if (chats == null) {
			chats = new Chats();
			chats.setMessage("請輸入訊息");
			chats.setSuccessful(false);
			return chats;
		} else {
			int result = chatDao.insert(chats); // 將聊天訊息送進資料庫
			if (result == 1) {
				chats.setMessage("輸入訊息成功");
				chats.setSuccessful(true);
			};
			return chats;
		}

	}

}
