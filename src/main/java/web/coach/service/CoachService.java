package web.coach.service;

import java.text.ParseException;
import java.util.List;

import web.coach.pojo.CoachAndUser;

public interface CoachService {
	
	List<CoachAndUser> findCoachAndUser();
	
	boolean inviteCoach(Integer userId) throws ParseException;
	
}
