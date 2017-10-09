package manhattan.service.impl;

import java.util.List;

import manhattan.domain.impl.Admin;
import manhattan.repository.AdminRepository;
import manhattan.repository.GenericRepository;
import manhattan.repository.impl.AdminRepositoryImpl;
import manhattan.service.AdminService;

public class AdminServiceImpl implements AdminService {
	
	private final GenericRepository<Admin> adminRepository;
	
	public AdminServiceImpl() {
		this.adminRepository = new AdminRepositoryImpl();
	}

	@Override
	public boolean save(Admin entity) {
		return adminRepository.save(entity);
		
	}

	@Override
	public boolean update(Admin entity) {
		return adminRepository.update(entity);
		
	}

	@Override
	public boolean delete(Admin entity) {
		return adminRepository.delete(entity);
		
	}

	@Override
	public List<Admin> getlAll() {
		return adminRepository.getAll();
	}

	@Override
	public Admin getById(Long id) {
		return adminRepository.getById(id);
	}

	@Override
	public Admin login(String email, String password) {
		return ((AdminRepository)adminRepository).login(email, password);
	}

	@Override
	public Admin getByMailAndPassword(String email, String password) {
		return ((AdminRepository)adminRepository).getByMailAndPassword(email, password);
	}

}
