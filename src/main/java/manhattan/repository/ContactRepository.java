package manhattan.repository;

import manhattan.domain.impl.Contact;

public interface ContactRepository extends GenericRepository<Contact> {
	
	Contact getContact();

}
