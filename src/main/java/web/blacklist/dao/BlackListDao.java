package web.blacklist.dao;

import java.util.List;

import web.user.pojo.User;

public interface BlackListDao {
//	int insert(User user);

	List<User> selectAll();

	int update(User user);
}
