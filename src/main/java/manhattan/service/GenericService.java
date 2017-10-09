package manhattan.service;

import java.util.List;

import manhattan.domain.GenericEntity;

public interface GenericService<E extends GenericEntity> {

	public abstract boolean save(E entity);

	public abstract boolean update(E entity);

	public abstract boolean delete(E entity);

	public abstract List<E> getlAll();

	public abstract E getById(Long id);
	
}
