package web.user.dao;

import web.user.pojo.User;

public interface UserDao {
	User selectByUsernameAndPassword(User user);
}
