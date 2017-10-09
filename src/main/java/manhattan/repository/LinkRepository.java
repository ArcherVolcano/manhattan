package manhattan.repository;

import java.util.List;

import manhattan.domain.impl.Link;

public interface LinkRepository extends GenericRepository<Link> {
	List<Link> getAllPictures();
	List<Link> getAllVideos();
	List<Link> getPicturesByLimit(int limit);
	List<Link> getVideosByLimit(int limit);
	List<Link> getPicturesByLimitAndOffset(int limit, int offset);
	List<Link> getVideosByLimitAndOffset(int limit, int offset);
 	Integer getPictureCount();
 	Integer getVideoCount();
	
}
