package manhattan.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import manhattan.domain.impl.Band;
import manhattan.repository.BandRepository;
import manhattan.repository.HibernateUtil;

public class BandRepositoryImpl implements BandRepository {

	@Override
	public boolean save(Band entity) {
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
	public boolean update(Band entity) {
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
	public boolean delete(Band entity) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Band> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		//setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) onetomany veya manytomany listelerde criteria.list() fazladan ayný entityden donduruyor. Bunu önlemek için set ediyoruz.
		List<Band> list = session.createCriteria(Band.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		return list;
	}

	@Override
	public Band getById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Band band = (Band) session.get(Band.class, id);
		return band;
	}

	@Override
	public List<Band> findByCriteria(Criterion... criterions) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
