package manhattan.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import manhattan.domain.impl.Admin;
import manhattan.repository.AdminRepository;
import manhattan.repository.HibernateUtil;

public class AdminRepositoryImpl implements AdminRepository {
	
	@Override
	public boolean save(Admin entity) {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(entity);
			transaction.commit();
			result = true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public boolean update(Admin entity) {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(entity);
			transaction.commit();
			result = true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public boolean delete(Admin entity) {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(entity);
			transaction.commit();
			result = true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Admin> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createCriteria(Admin.class).list();
	}

	@Override
	public Admin getById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Admin admin = null;
		admin = (Admin) session.get(Admin.class, id);
		return admin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Admin> findByCriteria(Criterion... criterions) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria  = session.createCriteria(Admin.class);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return criteria.list();
	}

	@Override
	public Admin getByMailAndPassword(String email, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = (Query) session.createQuery("From Admin a WHERE a.email = :email and a.password = :password");
		query.setParameter("email", email).setParameter("password", password);
		Admin admin = (Admin) query.getSingleResult();
		return admin;
	}
	
	@Override
	public Admin login(String email, String password) {
		Admin admin = null;
		List<Admin> admins = findByCriteria(Restrictions.eq("email", email), Restrictions.eq("password", password));
		if (admins.size() > 0) admin = admins.get(0);
		return admin;
	}
}
