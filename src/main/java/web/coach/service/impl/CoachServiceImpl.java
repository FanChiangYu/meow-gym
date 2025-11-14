package web.coach.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.coach.dao.CoachDao;
import web.coach.pojo.CoachAndUser;
import web.coach.pojo.CoachProfiles;
import web.coach.service.CoachService;
import web.user.pojo.User;

@Service
@Transactional
public class CoachServiceImpl implements CoachService{
	@Autowired
	private CoachDao dao;

	@Override
	public List<CoachAndUser> findCoachAndUser() {
		List<CoachAndUser> cuList = new ArrayList<>();
		List<User> userList = dao.selectAllUser();
		for (User user : userList) {
			CoachAndUser coachAndUser = new CoachAndUser();
			CoachProfiles cp = dao.selectByUserId(user.getUserId());
			if(cp != null) {
				coachAndUser.setCoachProfiles(cp);
			}
			coachAndUser.setUser(user);
			cuList.add(coachAndUser);
		}
		return cuList;
	}

	@Override
	public boolean inviteCoach(Integer userId) {
		int count = dao.updateRole(userId);
		if(count > 0) {
			CoachProfiles cp = new CoachProfiles();
			cp.setApprovalStatus("待審核");
			cp.setUserId(userId);
			cp.setBio("");
			int cpCount = dao.insertCoachProfiles(cp);
			return cpCount > 0 ? true : false;
		}
		return false;
	}
}
