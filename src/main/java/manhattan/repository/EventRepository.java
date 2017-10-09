package manhattan.repository;

import java.util.List;

import manhattan.domain.impl.Event;

public interface EventRepository extends GenericRepository<Event> {

	List<Event> getByPublishEntitiesLimit(int limit);
	
	List<Event> getByUnPublishEntitiesLimit(int limit);
	
	List<Event> getAllUnPublishEntities();
	
	List<Event> getAllPublishEntities();
	
}
