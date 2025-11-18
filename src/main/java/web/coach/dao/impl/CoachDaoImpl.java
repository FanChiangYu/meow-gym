package web.coach.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.coach.dao.CoachDao;
import web.coach.pojo.CoachCertificates;
import web.coach.pojo.CoachEducations;
import web.coach.pojo.CoachExperiences;
import web.coach.pojo.CoachProfiles;
import web.user.pojo.User;

@Repository
public class CoachDaoImpl implements CoachDao{
	@PersistenceContext
	private Session session;

	@Override
	public List<User> selectAllUser() {
		final String hql = "FROM User ORDER BY userId";
		return session
				.createQuery(hql, User.class)
				.getResultList();
	}

	@Override
	public CoachProfiles selectByUserId(Integer userId) {
		String hql = "FROM CoachProfiles WHERE userId = :userId";
		
		return session
				.createQuery(hql, CoachProfiles.class)
				.setParameter("userId", userId)
				.uniqueResult();
	}

	@Override
	public int updateRole(Integer userId) {
		final StringBuilder hql = new StringBuilder()
				.append("update User set ")
				.append("role = 2 ")
				.append("where userId = :userId");
		
		return session.createQuery(hql.toString())
				.setParameter("userId", userId)
				.executeUpdate();
	}

	@Override
	public int insertCoachProfiles(CoachProfiles coachProfiles) {
		session.persist(coachProfiles);
		return 1;
	}

	@Override
	public int insertCoachCertificates(CoachCertificates certificates) {
		session.persist(certificates);
		return 1;
	}

	@Override
	public int insertCoachEducations(CoachEducations educations) {
		session.persist(educations);
		return 1;
	}

	@Override
	public int insertCoachExperiences(CoachExperiences experiences) {
		session.persist(experiences);
		return 1;
	}

	@Override
	public CoachCertificates selectCertificateByCoachId(Integer coachId) {
		String hql = "FROM CoachCertificates WHERE coachId = :coachId";
		
		return session
				.createQuery(hql, CoachCertificates.class)
				.setParameter("coachId", coachId)
				.uniqueResult();
	}

	@Override
	public CoachEducations selectEducationByCoachId(Integer coachId) {
		String hql = "FROM CoachEducations WHERE coachId = :coachId";
		
		return session
				.createQuery(hql, CoachEducations.class)
				.setParameter("coachId", coachId)
				.uniqueResult();
	}

	@Override
	public CoachExperiences selectExperienceByCoachId(Integer coachId) {
		String hql = "FROM CoachExperiences WHERE coachId = :coachId";
		
		return session
				.createQuery(hql, CoachExperiences.class)
				.setParameter("coachId", coachId)
				.uniqueResult();
	}

	@Override
	public int updateProfileBio(CoachProfiles profile) {
		String hql = "UPDATE CoachProfiles SET bio = :bio WHERE coachId = :coachId";

	    return session.createQuery(hql)
	            .setParameter("bio", profile.getBio())
	            .setParameter("coachId", profile.getCoachId())
	            .executeUpdate();
	}

	@Override
	public int updateEducation(CoachEducations education) {
		String hql = "UPDATE CoachEducations " +
                "SET school = :school, degree = :degree " +
                "WHERE coachId = :coachId";

		return session.createQuery(hql)
                 .setParameter("school", education.getSchool())
                 .setParameter("degree", education.getDegree())
                 .setParameter("coachId", education.getCoachId())
                 .executeUpdate();
	}

	@Override
	public int updateExperience(CoachExperiences experience) {
		String hql = "UPDATE CoachExperiences " +
                "SET company = :company, " +
                "    title = :title, " +
                "    startDate = :startDate, " +
                "    endDate = :endDate " +
                "WHERE coachId = :coachId";

		return session.createQuery(hql)
	           .setParameter("company", experience.getCompany())
	           .setParameter("title", experience.getTitle())
	           .setParameter("startDate", experience.getStartDate())
	           .setParameter("endDate", experience.getEndDate())
	           .setParameter("coachId", experience.getCoachId())
	           .executeUpdate();
	}

	@Override
	public int updateCertificate(CoachCertificates certificate) {
		String hql = "UPDATE CoachCertificates " +
                "SET name = :name, " +
                "    fileUrl = :fileUrl " +
                "WHERE coachId = :coachId";

		return session.createQuery(hql)
		       .setParameter("name", certificate.getName())
		       .setParameter("fileUrl", certificate.getFileUrl())
		       .setParameter("coachId", certificate.getCoachId())
		       .executeUpdate();
	}

	@Override
	public User selectUserById(Integer userId) {
		return session.get(User.class, userId);
	}

	@Override
	public int updateApprovalStatus(CoachProfiles profile) {
		String hql = "UPDATE CoachProfiles " + 
				"SET approvalStatus = :approvalStatus, " + 
				"    approvedAt = NOW() " + 
				"WHERE coachId = :coachId";

	    return session.createQuery(hql)
	            .setParameter("approvalStatus", profile.getApprovalStatus())
	            .setParameter("coachId", profile.getCoachId())
	            .executeUpdate();
	}

	

}
