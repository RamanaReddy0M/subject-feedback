package in.kvsr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Student;
import in.kvsr.faculty.FacultyRepository;
import in.kvsr.student.StudentRepository;

public class SandFDetailsService implements UserDetailsService{
	
	@Autowired
	private FacultyRepository facultyRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = studentRepository.findByRollNumber(username.trim().toUpperCase());
		if(student == null) {
			Faculty faculty = facultyRepository.findByRegId(username.trim().toUpperCase());
			if(faculty != null) {
				return new FacultyDetails(faculty);
			}
			throw new UsernameNotFoundException("No user found with Id, "+ username);
		}else {
			return new StudentDetails(student);
		}
	}
	
	

}
