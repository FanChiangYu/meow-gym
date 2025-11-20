package web.user.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import core.util.FileUtil;
import web.course.service.CourseService;
import web.user.dao.UserDao;
import web.user.pojo.User;
import web.user.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private CourseService courseService;

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
	public User register(User user) throws IOException {

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

		MultipartFile file = user.getAvatarFile();
		String filename = file.getOriginalFilename();

		if (filename == null || filename.isEmpty()) {
			user.setMessage("缺少圖片檔名");
			user.setSuccessful(false);
			return user;
		}
		filename = courseService.addTimestampToFileName(filename);
		String fullPath = FileUtil.IMG_ROOT_PATH + filename;
		byte[] img = file.getBytes();

		Path path = Paths.get(fullPath);
		Files.write(path, img);
		user.setAvatarUrl("/meow-gym/course/img/" + filename);

		user.setRole(1);
		user.setIsBanned(false);
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

	@Override
	public User edit(User user) {
		final User oUser = dao.edit(user.getEmail());
		user.setAvatarUrl(oUser.getAvatarUrl());
		user.setName(oUser.getName());
		user.setPassword(oUser.getPassword());
		user.setGender(oUser.getGender());
		user.setPhone(oUser.getPhone());
		user.setBirthday(oUser.getBirthday());
		user.setCntCode(oUser.getCntCode());
		user.setDistCode(oUser.getDistCode());
		user.setDetailAddress(oUser.getDetailAddress());
		final int resultCount = dao.update(user);
		user.setSuccessful(resultCount >0);
		user.setMessage(resultCount > 0 ? "編輯成功" : "編輯失敗");
		return user;
	}

}
