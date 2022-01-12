package in.kvsr.admin.faculty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import in.kvsr.admin.hod.HODService;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.HOD;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FacultyService {
	
	@Autowired
	private FacultyRepository facultyRepository;
	@Autowired
	private HODService hodService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Faculty> listAll() {
		return (List<Faculty>) facultyRepository.findAll();
	}
	
	public List<Faculty> listAllInSortOrder(String sortOrder){
		return facultyRepository.findAllBySortOrder(Sort.by(sortOrder));
	}
	
	public Faculty getById(Long id) {
		try {
			return facultyRepository.findById(id).get();
		}catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public boolean deleteByID(Long id) {
		try {
			Faculty faculty = facultyRepository.findById(id).get();
			Path directory = Paths.get("../frontend/faculty-images/"+faculty.getRegId());
			FileSystemUtils.deleteRecursively(directory);
			facultyRepository.delete(faculty);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Faculty getByRegId(String name) {
		
		return facultyRepository.getByRegId(name);
	}
	
	
	

	public boolean save(Faculty faculty) {
		try {
			Faculty tempFaculty = facultyRepository.getByRegId(faculty.getRegId());
			faculty.setAverage(tempFaculty.getAverage());
			if(faculty.getId() != null && (faculty.getPassword()==null || faculty.getPassword().isEmpty())) {
				faculty.setPassword(facultyRepository.getByRegId(faculty.getRegId()).getPassword());
			}else {
				encodePassword(faculty);
			}
			if(faculty.getRegId()!=null) {
				HOD hod = hodService.findByRegId(faculty.getRegId());
				if(hod!=null) {
					hod.setPassword(faculty.getPassword());
					hodService.save(hod);
				}
			}
			facultyRepository.save(faculty);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * private boolean saveWithoutEncryption(Faculty faculty) { try {
	 * facultyRepository.save(faculty); return true; }catch (Exception e) { return
	 * false; } }
	 */
	
	public List<Faculty> searchByRegIdAndName(String key) {
		return facultyRepository.searchByRegIdAndName(key);
	}
	
	public boolean refactorIdColumn() {
		Integer dv =facultyRepository.dropIdColumn();
		Integer av = facultyRepository.addIdColumn();
		return !(av==dv);
	}
	 
	private void encodePassword(Faculty faculty) {
		String encoded = passwordEncoder.encode(faculty.getPassword());
		faculty.setPassword(encoded);
	}

}
