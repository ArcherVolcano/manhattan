package manhattan.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import manhattan.domain.impl.Contact;
import manhattan.repository.ContactRepository;
import manhattan.repository.HibernateUtil;

public class ContactRepositoryImpl implements ContactRepository {

	@Override
	public boolean save(Contact entity) {
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
	public boolean update(Contact entity) {
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
	public boolean delete(Contact entity) {
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
	public List<Contact> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		//setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) onetomany veya manytomany listelerde criteria.list() fazladan ayný entityden donduruyor. Bunu önlemek için set ediyoruz.
		@SuppressWarnings("unchecked")
		List<Contact> list = session.createCriteria(Contact.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		return list;
	}

	@Override
	public Contact getById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Contact contact = (Contact) session.get(Contact.class, id);
		return contact;
	}

	@Override
	public List<Contact> findByCriteria(Criterion... criterions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contact getContact() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Contact contact = (Contact) session.createCriteria(Contact.class).setMaxResults(1).list().get(0);
		return contact;
	}

}
