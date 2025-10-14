package core.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import web.user.pojo.User;

@Data
public class Core implements Serializable {
	private static final long serialVersionUID = 1457755989409740329L;
	private boolean successful;
	private String message;

	public User selectUserById(Integer userId) {
		return null;
	}

	public int insert(User pojo) {
		return 0;
	}

	public int deleteById(Integer id) {
		return 0;
	}

	public int update(User pojo) {
		return 0;
	}

	public User selectById(Integer id) {
		return null;
	}

	public List<User> selectAll() {
		return null;
	}

	public User selectForLogin(String useremail, String password) {
		return null;
	}

	public int insert(Object pojo) {
		return 0;
	}

	public int deleteById(Object id) {
		return 0;
	}

	public int update(Object pojo) {
		return 0;
	}

	public Object selectById(Object id) {
		return null;
	}

}
