package core.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import core.util.HibernateUtil;

public interface CoreService {
	
	private Session getCurSession() {
		return HibernateUtil
				.getSessionFactory()
				.getCurrentSession();
	}
	
	private Transaction getTx() {
		return getCurSession()
			  .getTransaction();
	}
	
	default Transaction beginTx() {
		return getCurSession().beginTransaction();
	}
	
	default void commit() {
		getTx().commit();
	}
	
	default void rollback() {
		getTx().rollback();
	}
}
