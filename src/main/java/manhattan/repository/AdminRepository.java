package manhattan.repository;

import manhattan.domain.impl.Admin;

public interface AdminRepository extends GenericRepository<Admin> {

	Admin getByMailAndPassword(String email, String password);
	Admin login(String email, String password);
}
