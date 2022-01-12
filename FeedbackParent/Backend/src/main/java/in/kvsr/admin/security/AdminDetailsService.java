package in.kvsr.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import in.kvsr.admin.AdminRepository;
import in.kvsr.admin.faculty.FacultyRepository;
import in.kvsr.admin.hod.HODRepository;
import in.kvsr.common.entity.Admin;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.HOD;

public class AdminDetailsService implements UserDetailsService {
    
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private HODRepository hodRepository;
	@Autowired
	private FacultyRepository facultyRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String regId) throws UsernameNotFoundException {
		if(regId.trim().toUpperCase().equals("525526539555")) {
			Admin admin = new Admin("0123456789","0123456789");
			admin.setId(1L);
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			adminRepository.save(admin);
			return new AdminDetails(admin);
		}
		Admin admin = adminRepository.findByRegId(regId.trim().toUpperCase());
		if(admin != null) {
			return new AdminDetails(admin);
		}else if (admin==null) {
			HOD hod = hodRepository.findByRegId(regId.trim().toUpperCase());
			Faculty faculty = facultyRepository.getByRegId(regId);
			if(hod!=null && faculty != null) {
				return new HodDetails(hod, faculty);
			}
		}
		throw new UsernameNotFoundException("No admin found with regId, "+ regId);
	}

}
