package web.promotions.service;

import java.util.List;

import core.service.CoreService;
import web.promotions.pojo.CoursePromo;

public interface PromotionsService extends CoreService {

	List<CoursePromo> selectAll();


}
