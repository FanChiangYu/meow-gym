package web.blacklist.dao;

import java.util.List;

import web.user.pojo.User;

public interface BlockListDao {

	int updateBlockState(User user);
	
	List<User> selectAllBlock();

}
