package manhattan.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import manhattan.domain.impl.About;
import manhattan.repository.AboutRepository;
import manhattan.repository.HibernateUtil;

public class AboutRepositoryImpl implements AboutRepository {

	@Override
	public boolean save(About entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(entity);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean update(About entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(entity);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean delete(About entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(entity);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<About> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		//setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) onetomany veya manytomany listelerde criteria.list() fazladan ayný entityden donduruyor. Bunu önlemek için set ediyoruz.
		@SuppressWarnings("unchecked")
		List<About> list = session.createCriteria(About.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		return list;
	}

	@Override
	public About getById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		About about = (About) session.get(About.class, id);
		return about;
	}

	@Override
	public List<About> findByCriteria(Criterion... criterions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public About getAbout() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		About about = (About) session.createCriteria(About.class).setMaxResults(1).list().get(0);
		return about;
	}

}
