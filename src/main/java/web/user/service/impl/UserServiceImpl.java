package web.user.service.impl;

import javax.naming.NamingException;

import web.user.dao.UserDao;
import web.user.dao.impl.UserDaoImpl;
import web.user.pojo.User;
import web.user.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao dao;

	public UserServiceImpl() throws NamingException {
		dao = new UserDaoImpl();
	}

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

}
