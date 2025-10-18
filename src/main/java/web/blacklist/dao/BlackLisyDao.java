package web.blacklist.dao;

import web.blacklist.pojo.User;

public interface BlackLisyDao {
	int insert(User user);

	int update(User user);
}
