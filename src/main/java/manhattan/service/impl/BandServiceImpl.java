package manhattan.service.impl;

import java.util.List;

import manhattan.domain.impl.Band;
import manhattan.repository.GenericRepository;
import manhattan.repository.impl.BandRepositoryImpl;
import manhattan.service.BandService;

public class BandServiceImpl implements BandService {
	
	private final GenericRepository<Band> bandRepository;
	
	public BandServiceImpl() {
		this.bandRepository = new BandRepositoryImpl();
	}

	@Override
	public boolean save(Band entity) {
		return bandRepository.save(entity);
	}

	@Override
	public boolean update(Band entity) {
		return bandRepository.update(entity);
		
	}

	@Override
	public boolean delete(Band entity) {
		return bandRepository.delete(entity);
		
	}

	@Override
	public List<Band> getlAll() {
		return bandRepository.getAll();
	}

	@Override
	public Band getById(Long id) {
		return bandRepository.getById(id);
	}

}
