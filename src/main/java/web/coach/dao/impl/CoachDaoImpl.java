package web.coach.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
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

	

}
