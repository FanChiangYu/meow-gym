package web.promotions.dao;


import java.util.List;

import web.promotions.pojo.CoursePromo;

public interface PromotionsDao{

	List<CoursePromo> selectPromo();


	int insert(CoursePromo coursePromo);




}
