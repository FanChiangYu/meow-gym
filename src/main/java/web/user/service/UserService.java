package web.user.service;

import java.io.IOException;
import java.util.List;

import core.service.CoreService;
import web.user.pojo.Country;
import web.user.pojo.District;
import web.user.pojo.User;

public interface UserService extends CoreService {

	User login(User user);

	User register(User user) throws IOException;

	User edit(User user);

	List<District> findDist();

	List<Country> findCountry();

}
