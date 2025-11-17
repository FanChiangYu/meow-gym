package web.promotions.dao.impl;

import java.util.List;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import web.promotions.dao.PromotionsDao;
import web.promotions.pojo.CoursePromo;

@Repository
public class PromotionsDaoImpl implements PromotionsDao {

	@PersistenceContext
	private Session session;

	@Override
	public List<CoursePromo> selectPromo() {
		final String hql = "FROM CoursePromo";
		return session.createQuery(hql, CoursePromo.class).getResultList();
	}

	@Override
	public int insert(CoursePromo coursePromo) {
		session.persist(coursePromo);
		return 1;
	}

	@Override
	public int deleteById(CoursePromo coursePromo) {
		final StringBuilder hql = new StringBuilder()
				.append("DELETE FROM CoursePromo ")
				.append("WHERE courseId = :courseId ");

		return session.createQuery(hql.toString())
				.setParameter("courseId", coursePromo.getCourseId())
				.executeUpdate();
	}
}
