package web.coach.dao;

import java.util.List;

import web.coach.pojo.CoachCertificates;
import web.coach.pojo.CoachEducations;
import web.coach.pojo.CoachExperiences;
import web.coach.pojo.CoachProfiles;
import web.user.pojo.User;

public interface CoachDao {
	
	List<User> selectAllUser();
	
	CoachProfiles selectByUserId(Integer userId);
	
	int updateRole(Integer userId);
	
	int insertCoachProfiles(CoachProfiles coachProfiles);
	
	int insertCoachCertificates(CoachCertificates certificates);
	
	int insertCoachEducations(CoachEducations educations);
	
	int insertCoachExperiences(CoachExperiences experiences);
}
