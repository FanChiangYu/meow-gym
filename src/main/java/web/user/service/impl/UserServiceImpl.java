package web.user.service.impl;

import web.user.pojo.User;
import web.user.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public User login(User user) {
		final String useremail = user.getEmail();
		final String password = user.getPassword();

		if (useremail == null) {
			user.setMessage("使用者名稱未輸入");
			user.setSuccessful(false);
			return user;
		}

		if (password == null) {
			user.setMessage("密碼未輸入");
			user.setSuccessful(false);
			return user;
		}

		user.setMessage("登入成功");
		user.setSuccessful(true);

		return user;

	}

}
