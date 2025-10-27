package web.promotions.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.promotions.dao.PromotionsDao;
import web.promotions.pojo.CoursePromo;
import web.promotions.service.PromotionsService;

@Service
public class PromotionsServiceImpl implements PromotionsService{
	//設定SPRING的注入DI，叫做@Autowired
	@Autowired
	private PromotionsDao dao;

	@Override
	public List<CoursePromo> selectAll() {
		return dao.selectPromo();
	}
	
	
}
