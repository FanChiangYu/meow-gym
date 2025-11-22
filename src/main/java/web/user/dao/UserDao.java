package web.user.dao;

import core.dao.CoreDao;
import web.user.pojo.User;

public interface UserDao extends CoreDao<User, Integer> {

	User selectForLogin(String email, String password);

	int insertUser(User user);

	User edit(String email);

}
