package manhattan.service.impl;

import java.util.List;

import manhattan.domain.impl.About;
import manhattan.repository.AboutRepository;
import manhattan.repository.GenericRepository;
import manhattan.repository.impl.AboutRepositoryImpl;
import manhattan.service.AboutService;

public class AboutServiceImpl implements AboutService {
	
	private final GenericRepository<About> aboutRepository;
	
	public AboutServiceImpl() {
		aboutRepository = new AboutRepositoryImpl();
	}

	@Override
	public boolean save(About entity) {
		return aboutRepository.save(entity);
	}

	@Override
	public boolean update(About entity) {
		return aboutRepository.update(entity);
	}

	@Override
	public boolean delete(About entity) {
		return aboutRepository.delete(entity);
	}

	@Override
	public List<About> getlAll() {
		return aboutRepository.getAll();
	}

	@Override
	public About getById(Long id) {
		return aboutRepository.getById(id);
	}

	@Override
	public About getAbout() {
		return ((AboutRepository)aboutRepository).getAbout();
	}

}
