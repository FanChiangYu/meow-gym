package web.user.service;

import core.service.CoreService;
import web.user.pojo.User;

public interface UserService extends CoreService{
	
	User login(User user);
	
	
	
}
