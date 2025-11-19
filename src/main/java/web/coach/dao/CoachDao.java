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
	
	CoachCertificates selectCertificateByCoachId(Integer coachId);
	
	CoachEducations selectEducationByCoachId(Integer coachId);
	
	CoachExperiences selectExperienceByCoachId(Integer coachId);
	
	int updateRole(Integer userId);
	
	int insertCoachProfiles(CoachProfiles coachProfiles);
	
	int insertCoachCertificates(CoachCertificates certificates);
	
	int insertCoachEducations(CoachEducations educations);
	
	int insertCoachExperiences(CoachExperiences experiences);
	
	int updateProfileBio(CoachProfiles profile);
	
	int updateEducation(CoachEducations education);
	
	int updateExperience(CoachExperiences experience);
	
	int updateCertificate(CoachCertificates certificate);

	User selectUserById(Integer userId);

	int updateApprovalStatus(CoachProfiles profile);
}
