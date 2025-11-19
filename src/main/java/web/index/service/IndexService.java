package web.index.service;

import java.util.List;

import web.coach.pojo.CoachProfiles;
import web.promotions.pojo.CoursePromo;

public interface IndexService {
	List<CoursePromo> findAllPromo();
	Boolean isOnSale (CoursePromo coursePromo);
	List<CoachProfiles> findAllCoach();
}
