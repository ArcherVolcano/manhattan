package manhattan.repository;

import java.util.List;

import org.hibernate.criterion.Criterion;

import manhattan.domain.GenericEntity;

public interface GenericRepository<E extends GenericEntity> {
	
	public abstract boolean save(E entity);

	public abstract boolean update(E entity);

	public abstract boolean delete(E entity);

	public abstract List<E> getAll();

	public abstract E getById(Long id);
	
	public abstract List<E> findByCriteria(Criterion... criterions);

}
