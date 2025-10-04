package core.dao;

import java.util.List;

import org.hibernate.Session;

import core.util.HibernateUtil;

public interface CoreDao<P, I> {
	int insert(P pojo);

	int deleteById(I id);

	int update(P pojo);

	P selectById(I id);

	List<P> selectAll();
	
	default Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
}
