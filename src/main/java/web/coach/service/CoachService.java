package web.coach.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import web.coach.pojo.CoachAndUser;
import web.coach.pojo.CoachApplyUpdateRequest;
import web.coach.pojo.CoachCertificates;
import web.coach.pojo.CoachEducations;
import web.coach.pojo.CoachExperiences;
import web.coach.pojo.CoachProfiles;
import web.user.pojo.User;

public interface CoachService {
	
	List<CoachAndUser> findCoachAndUser();
	
	boolean inviteCoach(Integer userId) throws ParseException;
	
	CoachProfiles findProfile(Integer userId);
	
	CoachCertificates findCertificate(Integer coachId);
	
	CoachEducations findEducation(Integer coachId);
	
	CoachExperiences findExperience(Integer coachId);
	
	User findUser(Integer userId);
	
	boolean updateCoachData(CoachApplyUpdateRequest request) throws IOException;

	Boolean updateApprovalStatus(CoachProfiles profile);
}
