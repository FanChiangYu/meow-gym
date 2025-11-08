package web.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.user.dao.UserDao;
import web.user.pojo.User;
import web.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Transactional
	@Override
	public User login(User user) {
		final String email = user.getEmail();
		final String password = user.getPassword();

		if (email == null) {
			user.setMessage("使用者名稱未輸入");
			user.setSuccessful(false);
			return user;
		}

		if (password == null) {
			user.setMessage("密碼未輸入");
			user.setSuccessful(false);
			return user;
		}

		user = dao.selectForLogin(email, password);

		if (user != null) {
			user.setMessage("登入成功");
			user.setSuccessful(true);
		} else {

		}

		return user;

	}

	@Override
	public User register(User user) {
		// business logic
		return null;
	}

}
