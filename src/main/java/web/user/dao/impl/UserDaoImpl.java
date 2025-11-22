package web.user.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import web.user.dao.UserDao;
import web.user.pojo.Country;
import web.user.pojo.District;
import web.user.pojo.User;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private Session session;

	@Override
	public User selectForLogin(String email, String password) {
		String hql1 = "from User where email = :email and password = :password";

		return session.createQuery(hql1, User.class).setParameter("email", email).setParameter("password", password)
				.uniqueResult();

	}

	@Override
	public int insertUser(User user) {
		session.persist(user);
		return 1;
	}

	@Override
	public User edit(String email) {
		CriteriaBuilder cBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> cQuery = cBuilder.createQuery(User.class);

		Root<User> root = cQuery.from(User.class);
		cQuery.where(cBuilder.equal(root.get("email"), email));
		return session.createQuery(cQuery).uniqueResult();

	}

	@Override
	public int insert(User pojo) {
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		return 0;
	}

	@Override
	public int update(User pojo) {
		final StringBuilder hql = new StringBuilder().append("update User set ").append("cntCode = :cntCode, ")
				.append("distCode = :distCode, ").append("detailAddress = :detailAddress, ")
				.append("password = :password, ").append("name = :name, ").append("phone = :phone, ")
				.append("avatarUrl = :avatarUrl, ").append("birthday = :birthday, ").append("gender = :gender ")
				.append("where userId = :userId");

		return session.createQuery(hql.toString()).setParameter("cntCode", pojo.getCntCode())
				.setParameter("distCode", pojo.getDistCode()).setParameter("detailAddress", pojo.getDetailAddress())
				.setParameter("password", pojo.getPassword()).setParameter("name", pojo.getName())
				.setParameter("phone", pojo.getPhone()).setParameter("avatarUrl", pojo.getAvatarUrl())
				.setParameter("birthday", pojo.getBirthday()).setParameter("gender", pojo.getGender())
				.setParameter("userId", pojo.getUserId()).executeUpdate();
	}

	@Override
	public User selectById(Integer id) {
		return null;
	}

	@Override
	public List<User> selectAll() {
		return null;
	}

	@Override
	public List<District> selectDist() {
		final String hql = "FROM District ORDER BY distCode";
		return session.createQuery(hql, District.class).getResultList();
	}

	@Override
	public List<Country> selectCountry() {
		final String hql = "FROM Country ORDER BY cntCode";
		return session.createQuery(hql, Country.class).getResultList();
	}

	@Override
	public User selectByEmail(User user) {
		String hql1 = "from User where email = :email";

		return session.createQuery(hql1, User.class).setParameter("email", user.getEmail()).uniqueResult();
	}

	@Override
	public int updateCodebByUser(User user) {
		final StringBuilder hql = new StringBuilder().append("update User set ").append("resetCode = :resetCode ")
				.append("where userId = :userId");

		return session.createQuery(hql.toString()).setParameter("userId", user.getUserId())
				.setParameter("resetCode", user.getResetCode()).executeUpdate();
	}

	@Override
	public String selectCodeById(Integer userId) {
		String hql = "SELECT resetCode FROM User WHERE userId = :userId";

		return session.createQuery(hql, String.class).setParameter("userId", userId).uniqueResult();
	}

	@Override
	public String selectPasswordById(Integer userId) {
		String hql = "SELECT password FROM User WHERE userId = :userId";

		return session.createQuery(hql, String.class).setParameter("userId", userId).uniqueResult();
	}

	@Override
	public void updatePasswordByUser(User user) {
		final StringBuilder hql = new StringBuilder().append("update User set ").append("password = :password ")
				.append("where userId = :userId");

		session.createQuery(hql.toString()).setParameter("userId", user.getUserId())
				.setParameter("password", user.getPassword()).executeUpdate();
	}

}
