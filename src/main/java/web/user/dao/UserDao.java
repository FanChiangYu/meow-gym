package web.user.dao;

import java.util.List;

import core.dao.CoreDao;
import web.user.pojo.Country;
import web.user.pojo.District;
import web.user.pojo.User;

public interface UserDao extends CoreDao<User, Integer> {

	User selectForLogin(String email, String password);

	int insertUser(User user);

	User edit(String email);

	List<District> selectDist();

	List<Country> selectCountry();

	User selectByEmail(User user);

	int updateCodebByUser(User user);

	String selectCodeById(Integer userId);

	String selectPasswordById(Integer userId);

	void updatePasswordByUser(User user);

}
