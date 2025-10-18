package web.blacklist.service;

import java.util.List;

import web.user.pojo.User;

public interface BlackListService {
	List<User> findAll();
}
