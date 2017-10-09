package manhattan.service;

import manhattan.domain.impl.Contact;

public interface ContactService extends GenericService<Contact> {
	
	Contact getContact();

}
