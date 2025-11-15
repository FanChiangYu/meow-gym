package web.user.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.user.dao.UserDao;
import web.user.pojo.User;
import web.user.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

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
		}
		return user;

	}

	@Override
	public User register(User user) {

		if (user.getEmail() == null) {
			user.setMessage("會員帳號未輸入");
			user.setSuccessful(false);
			return user;
		}

		if (user.getName() == null) {
			user.setMessage("姓名未輸入");
			user.setSuccessful(false);
			return user;
		}

		if (user.getPassword() == null) {
			user.setMessage("請輸入密碼");
			user.setSuccessful(false);
			return user;
		}

		if (user.getPhone() == null) {
			user.setMessage("請輸入電話");
			user.setSuccessful(false);
			return user;
		}

		if (user.getBirthday() == null) {
			user.setMessage("出生日期未輸入");
			user.setSuccessful(false);
			return user;
		}

		if (user.getCntCode() == null) {
			user.setMessage("請選擇縣市");
			user.setSuccessful(false);
			return user;
		}

		if (user.getDistCode() == null) {
			user.setMessage("請選擇鄉鎮區域");
			user.setSuccessful(false);
			return user;
		}

		if (user.getDetailAddress() == null) {
			user.setMessage("請填寫地址");
			user.setSuccessful(false);
			return user;
		}

		user.setRole(1);
		user.setBanned(false);
		user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		int count = dao.insertUser(user);
		if (count == 1) {
			user.setMessage("註冊成功");
			user.setSuccessful(true);
		} else {
			user.setMessage("註冊失敗");
			user.setSuccessful(false);
		}
		return user;
	}

}
