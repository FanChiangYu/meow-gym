package web.coach.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.coach.dao.CoachDao;
import web.coach.pojo.CoachAndUser;
import web.coach.pojo.CoachCertificates;
import web.coach.pojo.CoachEducations;
import web.coach.pojo.CoachExperiences;
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
	public boolean inviteCoach(Integer userId) throws ParseException {
		int count = dao.updateRole(userId);
		if (count <= 0) {
			return false;
		}
		
		CoachProfiles profiles = new CoachProfiles();
		profiles.setApprovalStatus("待審核");
		profiles.setUserId(userId);
		profiles.setBio("");
		count = dao.insertCoachProfiles(profiles);
		if (count != 1) {
			return false;
		}
		Integer coachId = profiles.getCouachId();
		
		CoachCertificates certificates = new CoachCertificates();
		certificates.setCoachId(coachId);
		certificates.setName("");
		certificates.setFileUrl("");
		count = dao.insertCoachCertificates(certificates);
		if (count != 1) {
			return false;
		}
		
		CoachEducations educations = new CoachEducations();
		educations.setCoachId(coachId);
		educations.setSchool("");
		educations.setDegree("");
		count = dao.insertCoachEducations(educations);
		if (count != 1) {
			return false;
		}
		
		CoachExperiences experiences = new CoachExperiences();
		String dateStr = "2025-01-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date initDate = sdf.parse(dateStr);
		experiences.setCoachId(coachId);
		experiences.setCompany("");
		experiences.setTitle("");
		experiences.setStartDate(initDate);
		experiences.setEndDate(initDate);
		count = dao.insertCoachExperiences(experiences);
		if (count != 1) {
			return false;
		}
		
		return true;
	}
}
