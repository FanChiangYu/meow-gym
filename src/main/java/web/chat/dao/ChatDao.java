package web.chat.dao;

import java.util.List;
import java.util.Set;

import web.chat.pojo.Chats;
import web.chat.pojo.SessionUsers;
import web.user.pojo.User;

public interface ChatDao {

	int insert(Chats chats);

	List<Chats> selctChatsByCourseId(Integer courseId); // 原本是Member member 參數要不要改成integer

	Set<SessionUsers> selectUserCourseId(Integer userId);

}
