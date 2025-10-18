package web.blacklist.service.impl;

import java.util.List;

import javax.naming.NamingException;

import web.blacklist.dao.BlackListDao;
import web.blacklist.dao.impl.BlackListDaoImpl;
import web.blacklist.service.BlackListService;
import web.user.pojo.User;

public class BlackListServiceImpl implements BlackListService {
	private BlackListDao blackListDao;
	
	public BlackListServiceImpl() throws NamingException {
		blackListDao = new BlackListDaoImpl();
	}

	@Override
	public List<User> findAll() {		
		return blackListDao.selectAll();
	}
}
