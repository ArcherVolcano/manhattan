package manhattan.service;

import manhattan.domain.impl.Admin;

public interface AdminService extends GenericService<Admin>{
	
	public abstract Admin login(String email, String password);
	Admin getByMailAndPassword(String email, String password);

}
