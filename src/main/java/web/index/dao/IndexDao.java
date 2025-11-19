package web.index.dao;

import java.util.List;

import core.dao.CoreDao;
import web.coach.pojo.CoachProfiles;
import web.promotions.pojo.CoursePromo;
import web.user.pojo.User;

public interface IndexDao extends CoreDao<CoursePromo, Integer>{

	List<CoachProfiles> selectAllCoach();

	User selectUserById(Integer userId);

}
