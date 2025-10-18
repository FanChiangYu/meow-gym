package web.chat.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import web.chat.pojo.ChatDTO;
import web.chat.pojo.Chats;
import web.chat.pojo.SessionUsers;
import web.user.pojo.User;

public interface ChatDao {

	int insert(Chats chats);

	List<Chats> selectChatsByCourseId(Integer courseId); // 原本是Member member 參數要不要改成integer
	
	List<ChatDTO> selectCourseChatsWithUser(Integer courseId); //為了user_id要對應到name而製作的 不是必要

	Set<SessionUsers> selectUserCourseId(Integer userId);
	

}
