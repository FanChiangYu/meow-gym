package web.user.service;

import java.io.IOException;

import core.service.CoreService;
import web.user.pojo.User;

public interface UserService extends CoreService {

	User login(User user);

	User register(User user) throws IOException;

	User edit(User user);

}
