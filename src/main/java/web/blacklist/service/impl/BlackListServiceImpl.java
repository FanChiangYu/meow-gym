package web.blacklist.service.impl;

import java.util.List;

import javax.naming.NamingException;

import web.blacklist.dao.BlackLiskDao;
import web.blacklist.dao.impl.BlackListDaoImpl;
import web.blacklist.service.BlackListService;
import web.user.pojo.User;

public class BlackListServiceImpl implements BlackListService {
	private BlackLiskDao blackListDao;
	
	public BlackListServiceImpl() throws NamingException {
		blackListDao = new BlackListDaoImpl();
	}

	@Override
	public List<User> findAll() {		
		return blackListDao.selectAll();
	}
}
