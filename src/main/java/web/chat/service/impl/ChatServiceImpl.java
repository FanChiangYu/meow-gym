package web.chat.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.chat.dao.ChatDao;
import web.chat.pojo.Chats;
import web.chat.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	 @Autowired 
	 private ChatDao chatDao;
	 
	 @Override
	 @Transactional
	 public Chats saveAndLoad(Chats chats) {
		 return chatDao.saveAndLoad(chats);
	 }
	 

}
