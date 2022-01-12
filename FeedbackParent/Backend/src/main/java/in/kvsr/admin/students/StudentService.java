package in.kvsr.admin.students;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Student;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Student> listAll() {
		return (List<Student>) studentRepository.findAll();
	}
	
	public List<Student> listAllInSortOrder(String sortOrder){
		return studentRepository.findAllBySortOrder(Sort.by(sortOrder));
	}
	
	public Student getById(Long id) {
		try {
			return studentRepository.findById(id).get();
		}catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public boolean deleteByID(Long id) {
		try {
			Student student = studentRepository.findById(id).get();
			studentRepository.delete(student);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean save(Student student) {
		try {
			if(student.getId() != null && (student.getPassword()==null || student.getPassword().isEmpty())) {
				student.setPassword(studentRepository.getByRegId(student.getRollNumber()).getPassword());
			}else {
				encodePassword(student);
			}
			studentRepository.save(student);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
            return false;		
        }
	}
	
	public List<Student> searchByRollNoAndName(String key){
		return studentRepository.searchByRollNoAndName(key);
	}
	
	public boolean refactorIdColumn() {
		Integer dv =studentRepository.dropIdColumn();
		Integer av = studentRepository.addIdColumn();
		return !(av==dv);
	}
	
	private void encodePassword(Student student) {
		String encoded = passwordEncoder.encode(student.getPassword());
		student.setPassword(encoded);
	}
}
