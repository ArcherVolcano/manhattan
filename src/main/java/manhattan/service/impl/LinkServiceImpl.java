package manhattan.service.impl;

import java.util.List;

import manhattan.domain.impl.Link;
import manhattan.repository.GenericRepository;
import manhattan.repository.LinkRepository;
import manhattan.repository.impl.LinkRepositryImpl;
import manhattan.service.LinkService;

public class LinkServiceImpl implements LinkService {

	private final GenericRepository<Link> linkRepository;
	
	public LinkServiceImpl() {
		this.linkRepository = new LinkRepositryImpl();
	}
	@Override
	public boolean save(Link entity) {
		return linkRepository.save(entity);
	}

	@Override
	public boolean update(Link entity) {
		return linkRepository.update(entity);
	}

	@Override
	public boolean delete(Link entity) {
		return linkRepository.delete(entity);
	}

	@Override
	public List<Link> getlAll() {
		return linkRepository.getAll();
	}

	@Override
	public Link getById(Long id) {
		return linkRepository.getById(id);
	}
	@Override
	public List<Link> getAllPictures() {
		return ((LinkRepository)linkRepository).getAllPictures();
	}
	@Override
	public List<Link> getAllVideos() {
		return ((LinkRepository)linkRepository).getAllVideos();
	}
	@Override
	public List<Link> getPicturesByLimit(int limit) {
		return ((LinkRepository)linkRepository).getPicturesByLimit(limit);
	}
	@Override
	public List<Link> getVideosByLimit(int limit) {
		return ((LinkRepository)linkRepository).getVideosByLimit(limit);
	}
	@Override
	public List<Link> getPicturesByLimitAndOffset(int limit, int offset) {
		return ((LinkRepository)linkRepository).getPicturesByLimitAndOffset(limit, offset);
	}
	@Override
	public Integer getPictureCount() {
		return ((LinkRepository)linkRepository).getPictureCount();
	}
	@Override
	public List<Link> getVideosByLimitAndOffset(int limit, int offset) {
		return ((LinkRepository)linkRepository).getVideosByLimitAndOffset(limit, offset);
	}
	@Override
	public Integer getVideoCount() {
		return ((LinkRepository)linkRepository).getVideoCount();
	}

}
