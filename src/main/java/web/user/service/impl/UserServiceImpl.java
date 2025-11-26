package web.user.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import core.util.FileUtil;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import web.course.service.CourseService;
import web.user.dao.UserDao;
import web.user.pojo.Country;
import web.user.pojo.District;
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
		user.setUserId(oUser.getUserId());
		final int resultCount = dao.update(user);
		user.setSuccessful(resultCount > 0);
		user.setMessage(resultCount > 0 ? "編輯成功" : "編輯失敗");
		return user;
	}

	@Override
	public List<District> findDist() {
		return dao.selectDist();
	}

	@Override
	public List<Country> findCountry() {
		return dao.selectCountry();
	}

	@Override
	public User updateCode(User user) throws AddressException, MessagingException {
		user = dao.selectByEmail(user);
		if (user == null) {
			User respUser = new User();
			respUser.setSuccessful(false);
			respUser.setMessage("輸入的email未註冊");
			return respUser;
		}

		user.setResetCode(generateCode());
		int count = dao.updateCodebByUser(user);
		if (count > 0) {
			user.setSuccessful(true);
			sendCodeByEmail(user);
		} else {
			user.setSuccessful(false);
			user.setMessage("驗證碼發送失敗");
		}
		return user;
	}

	@Override
	public String generateCode() {
		Random random = new Random();
		int code = random.nextInt(1000000);
		return String.format("%06d", code);
	}

	@Override
	public int updateCodeAgain(User user) throws AddressException, MessagingException {
		user = dao.selectByEmail(user);
		user.setResetCode(generateCode());
		sendCodeByEmail(user);
		return dao.updateCodebByUser(user);
	}

	@Override
	public boolean checkRestCode(User user) {
		String inputCode = user.getResetCode();
		String originalCode = dao.selectCodeById(user.getUserId());
		if (inputCode.equals(originalCode)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean changePassword(User user) {
		String newPassword = user.getPassword();
		String oldPassword = dao.selectPasswordById(user.getUserId());
		if (newPassword.equals(oldPassword)) {
			return false;
		}
		dao.updatePasswordByUser(user);
		return true;
	}

	@Override
	public void sendCodeByEmail(User user) throws AddressException, MessagingException {
		String host = "smtp.gmail.com";
		String username = "legoas299@gmail.com";
		String password = "nyovnwpanytuxipi";

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
		msg.setSubject("MEOW GYM 驗證碼");
		msg.setContent("<h2>你的驗證碼：</h2><h1 style='color:red'>" + user.getResetCode() + "</h1>",
				"text/html; charset=UTF-8");

		Transport.send(msg);

	}

}
