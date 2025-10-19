package web.blacklist.dao;

import java.util.List;

import web.user.pojo.User;


public interface BlackLiskDao {
	List<User> selectAll();

	int update(User user);
}
