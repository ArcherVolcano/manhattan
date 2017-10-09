package manhattan.service;

import java.util.List;

import manhattan.domain.impl.Event;

public interface EventService  extends GenericService<Event> {

	List<Event> getByPublishEntitiesLimit(int limit);
	
	List<Event> getByUnPublishEntitiesLimit(int limit);
	
	List<Event> getAllUnPublishEntities();
	
	List<Event> getAllPublishEntities();
}
