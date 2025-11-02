package web.index.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.index.dao.IndexDao;
import web.promotions.pojo.CoursePromo;

@Repository
public class IndexDaoImpl implements IndexDao {
	@PersistenceContext
	private Session session;

	@Override
	public int insert(CoursePromo pojo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(CoursePromo pojo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CoursePromo selectById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoursePromo> selectAll() {
		final String hql = "FROM CoursePromo ORDER BY promoId";
		return session
				.createQuery(hql, CoursePromo.class)
				.getResultList();
	}

}
