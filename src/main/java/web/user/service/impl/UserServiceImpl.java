package web.user.service.impl;

import javax.naming.NamingException;

import web.member.pojo.Member;
import web.user.dao.UserDao;
import web.user.dao.impl.UserDaoImpl;
import web.user.pojo.User;
import web.user.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao userdao;

	public UserServiceImpl() throws NamingException {
		userdao = new UserDaoImpl();
	}

	@Override
	public User login(User user) {
		final String email = user.getEmail();
		final String password = user.getPassword();
		System.out.println(email == null);
		System.out.println(password == null);

		if (email == null) {
			user.setMessage("使用者信箱未輸入");
			user.setSuccessful(false);
			return user;
		}

		if (password == null) {
			user.setMessage("密碼未輸入");
			user.setSuccessful(false);
			return user;
		}
		
		user = userdao.selectByUsernameAndPassword(user);
		//待續...
		if (user == null) {
			user = new User();
			user.setMessage("使用者名稱或密碼錯誤");
			user.setSuccessful(false);
			return user;
		}

		user.setMessage("登入成功");
		user.setSuccessful(true);
		return user;

	}

}
