package manhattan.service.impl;

import java.util.List;

import manhattan.domain.impl.Contact;
import manhattan.repository.ContactRepository;
import manhattan.repository.GenericRepository;
import manhattan.repository.impl.ContactRepositoryImpl;
import manhattan.service.ContactService;

public class ContactServiceImpl implements ContactService {
	
	private final GenericRepository<Contact> contactRepository;
	
	public ContactServiceImpl() {
		contactRepository = new ContactRepositoryImpl();
	}

	@Override
	public boolean save(Contact entity) {
		return contactRepository.save(entity);
	}

	@Override
	public boolean update(Contact entity) {
		return contactRepository.update(entity);
	}

	@Override
	public boolean delete(Contact entity) {
		return contactRepository.delete(entity);
	}

	@Override
	public List<Contact> getlAll() {
		return contactRepository.getAll();
	}

	@Override
	public Contact getById(Long id) {
		return contactRepository.getById(id);
	}

	@Override
	public Contact getContact() {
		return ((ContactRepository)contactRepository).getContact();
	}

}
