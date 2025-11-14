package web.coach.dao;

import java.util.List;

import web.coach.pojo.CoachProfiles;
import web.user.pojo.User;

public interface CoachDao {
	
	List<User> selectAllUser();
	
	CoachProfiles selectByUserId(Integer userId);
	
	int updateRole(Integer userId);
	
	int insertCoachProfiles(CoachProfiles coachProfiles);
}
