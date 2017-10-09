package manhattan.service.impl;

import java.util.List;

import manhattan.domain.impl.Event;
import manhattan.repository.EventRepository;
import manhattan.repository.GenericRepository;
import manhattan.repository.impl.EventRepositoryImpl;
import manhattan.service.EventService;

public class EventServiceImpl implements EventService {

	private final GenericRepository<Event> eventRepository;
	
	public EventServiceImpl() {
		this.eventRepository = new EventRepositoryImpl();
	}
	
	@Override
	public boolean save(Event entity) {
		return eventRepository.save(entity);
	}

	@Override
	public boolean update(Event entity) {
		return eventRepository.update(entity);
	}

	@Override
	public boolean delete(Event entity) {
		return eventRepository.delete(entity);
	}

	@Override
	public List<Event> getlAll() {
		return eventRepository.getAll();
	}

	@Override
	public Event getById(Long id) {
		return eventRepository.getById(id);
	}

	@Override
	public List<Event> getByPublishEntitiesLimit(int limit) {
		return ((EventRepository)eventRepository).getByPublishEntitiesLimit(limit);
	}

	@Override
	public List<Event> getByUnPublishEntitiesLimit(int limit) {
		return ((EventRepository)eventRepository).getByUnPublishEntitiesLimit(limit);
	}

	@Override
	public List<Event> getAllUnPublishEntities() {
		return ((EventRepository)eventRepository).getAllUnPublishEntities();
	}

	@Override
	public List<Event> getAllPublishEntities() {
		return ((EventRepository)eventRepository).getAllPublishEntities();
	}


}
