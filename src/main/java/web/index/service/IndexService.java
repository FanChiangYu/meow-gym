package web.index.service;

import java.util.List;

import web.coach.pojo.CoachProfiles;
import web.promotions.pojo.CoursePromo;
import web.user.pojo.User;

public interface IndexService {
	List<CoursePromo> findAllPromo();
	Boolean isOnSale (CoursePromo coursePromo);
	List<CoachProfiles> findAllCoach();
	Boolean coachApprovalStatus(User user);
}
