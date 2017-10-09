package manhattan.repository;

import manhattan.domain.impl.About;

public interface AboutRepository extends GenericRepository<About> {
	
	About getAbout();

}
