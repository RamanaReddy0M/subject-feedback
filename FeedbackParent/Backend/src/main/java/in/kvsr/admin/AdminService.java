package in.kvsr.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Admin;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean save(Admin admin) {
		try {
			passwordEncoder(admin);
			adminRepository.save(admin);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public Admin getById(Long id) {
		return adminRepository.findById(id).get();
	}
	
	public Admin getByRegId(String regId) {
		return adminRepository.findByRegId(regId);
	}
	
	private void passwordEncoder(Admin admin) {
		String encodedString = passwordEncoder.encode(admin.getPassword());
		admin.setPassword(encodedString);
	}
}
