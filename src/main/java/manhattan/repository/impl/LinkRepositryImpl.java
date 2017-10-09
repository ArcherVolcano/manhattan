package manhattan.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import manhattan.domain.impl.Link;
import manhattan.repository.HibernateUtil;
import manhattan.repository.LinkRepository;

public class LinkRepositryImpl implements LinkRepository {

	@Override
	public boolean save(Link entity) {
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
	public boolean update(Link entity) {
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
	public boolean delete(Link entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		boolean result = false;
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
	public List<Link> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		//setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) onetomany veya manytomany listelerde criteria.list() fazladan ayný entityden donduruyor. Bunu önlemek için set ediyoruz.
		List<Link> list = session.createCriteria(Link.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		return list;
	}

	@Override
	public Link getById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Link Picture = (Link) session.get(Link.class, id);
		return Picture;
	}

	@Override
	public List<Link> findByCriteria(Criterion... criterions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Link> getAllPictures() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Link l WHERE l.isGalleryItem = true Order By l.id");
		@SuppressWarnings("unchecked")
		List<Link> pictures = query.list();
		return pictures;
	}

	@Override
	public List<Link> getAllVideos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Link l WHERE l.isVideoItem = true Order By l.id");
		@SuppressWarnings("unchecked")
		List<Link> videos = query.list();
		return videos;
	}

	@Override
	public List<Link> getPicturesByLimit(int limit) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Link l WHERE l.isGalleryItem = true Order By l.id");
		@SuppressWarnings("unchecked")
		List<Link> pictures = query.setMaxResults(limit).list();
		return pictures;
	}

	@Override
	public List<Link> getVideosByLimit(int limit) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Link l WHERE l.isVideoItem = true Order By l.id");
		@SuppressWarnings("unchecked")
		List<Link> videos = query.setMaxResults(limit).list();
		return videos;
	}

	@Override
	public List<Link> getPicturesByLimitAndOffset(int limit, int offset) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Link l WHERE l.isGalleryItem = true Order By l.id");
		@SuppressWarnings("unchecked")
		List<Link> pictures = query.setMaxResults(limit).setFirstResult(offset).list();
		return pictures;
	}

	@Override
	public Integer getPictureCount() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Integer count = ((Number) session.createCriteria(Link.class).add(Restrictions.eq("isGalleryItem", true)).setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return count;
	}

	@Override
	public List<Link> getVideosByLimitAndOffset(int limit, int offset) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Link l WHERE l.isVideoItem = true Order By l.id");
		@SuppressWarnings("unchecked")
		List<Link> videos = query.setMaxResults(limit).setFirstResult(offset).list();
		return videos;
	}

	@Override
	public Integer getVideoCount() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Integer count = ((Number) session.createCriteria(Link.class).add(Restrictions.eq("isVideoItem", true)).setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return count;
	}

}
