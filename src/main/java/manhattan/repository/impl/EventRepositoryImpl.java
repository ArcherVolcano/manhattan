package manhattan.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import manhattan.domain.impl.Event;
import manhattan.repository.EventRepository;
import manhattan.repository.HibernateUtil;

public class EventRepositoryImpl implements EventRepository {

	@Override
	public boolean save(Event entity) {
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
	public boolean update(Event entity) {
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
	public boolean delete(Event entity) {
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
	public List<Event> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		//setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) onetomany veya manytomany listelerde criteria.list() fazladan ayný entityden donduruyor. Bunu önlemek için set ediyoruz.
		List<Event> events = session.createCriteria(Event.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return events;
	}

	@Override
	public Event getById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Event event = (Event) session.get(Event.class, id);
		return event;
	}

	@Override
	public List<Event> findByCriteria(Criterion... criterions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getByPublishEntitiesLimit(int limit) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Event.class);
		//Fazla sonuç dönmemesi için id ye göre distnict ettik. setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) sorgudan sonra iþlem yaptýðýndan burda iþimize yaramadý.
		criteria.setProjection((Projections.distinct(Projections.property("id"))) );
		//Suan ki tarihten daha büyük startDate i olanlarý getir.
		criteria.add(Restrictions.ge("startDate", new Date()));
		//startDate'e göre sýrala.
		criteria.addOrder(Order.asc("startDate"));
		@SuppressWarnings("unchecked")
		List<Long> ids = criteria.setMaxResults(limit).list();
		List<Event> events = new ArrayList<>();
		for (Long id : ids) {
			Event event = (Event) session.get(Event.class, id);
			events.add(event);
		}
		return events;
	}

	@Override
	public List<Event> getByUnPublishEntitiesLimit(int limit) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Event.class);
		//Fazla sonuç dönmemesi için id ye göre distnict ettik. setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) sorgudan sonra iþlem yaptýðýndan burda iþimize yaramadý.
		criteria.setProjection((Projections.distinct(Projections.property("id"))) );
		//Suan ki tarihten daha küçük startDate'i olanlarý getir.
		criteria.add(Restrictions.le("startDate", new Date()));
		//startDate'e göre sýrala.
		criteria.addOrder(Order.asc("endDate"));
		@SuppressWarnings("unchecked")
		List<Long> ids = criteria.setMaxResults(limit).list();
		List<Event> events = new ArrayList<>();
		for (Long id : ids) {
			Event event = (Event) session.get(Event.class, id);
			events.add(event);
		}
		return events;
	}

	@Override
	public List<Event> getAllPublishEntities() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Event.class);
		//Fazla sonuç dönmemesi için id ye göre distnict ettik. setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) sorgudan sonra iþlem yaptýðýndan burda iþimize yaramadý.
		criteria.setProjection((Projections.distinct(Projections.property("id"))) );
		//Suan ki tarihten daha büyük startDate i olanlarý getir.
		criteria.add(Restrictions.ge("startDate", new Date()));
		//startDate'e göre sýrala.
		criteria.addOrder(Order.asc("startDate"));
		@SuppressWarnings("unchecked")
		List<Long> ids = criteria.list();
		List<Event> events = new ArrayList<>();
		for (Long id : ids) {
			Event event = (Event) session.get(Event.class, id);
			events.add(event);
		}
		return events;
	}

	@Override
	public List<Event> getAllUnPublishEntities() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Event.class);
		//Fazla sonuç dönmemesi için id ye göre distnict ettik. setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) sorgudan sonra iþlem yaptýðýndan burda iþimize yaramadý.
		criteria.setProjection((Projections.distinct(Projections.property("id"))) );
		//Suan ki tarihten daha küçük startDate'i olanlarý getir.
		criteria.add(Restrictions.le("startDate", new Date()));
		//startDate'e göre sýrala.
		criteria.addOrder(Order.asc("endDate"));
		@SuppressWarnings("unchecked")
		List<Long> ids = criteria.list();
		List<Event> events = new ArrayList<>();
		for (Long id : ids) {
			Event event = (Event) session.get(Event.class, id);
			events.add(event);
		}
		return events;
	}

}
