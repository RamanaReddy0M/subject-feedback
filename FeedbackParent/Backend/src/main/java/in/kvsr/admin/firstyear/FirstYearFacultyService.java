package in.kvsr.admin.firstyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.FirstYearFaculty;

@Service
public class FirstYearFacultyService {
	
	@Autowired
	private FirstYearFacultyRepository firstYearFacultyRepository;
	
	public boolean save(FirstYearFaculty firstYearFaculty) {
		try {
			firstYearFacultyRepository.save(firstYearFaculty);
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	
	public List<FirstYearFaculty> listAll() {
		List<FirstYearFaculty> facultyList = (List<FirstYearFaculty>) firstYearFacultyRepository.findAll();
		
		if(facultyList == null) {
			return null;
		}
		return facultyList;
	}
	
	public FirstYearFaculty getFacultyByRegId(String regId) {
		return firstYearFacultyRepository.getByRegId(regId);
	}
	
	public boolean deleteById(Long id) {
		try {
			firstYearFacultyRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public boolean deleteByRegId(String regId) {
		if(firstYearFacultyRepository.deleteByRegId(regId)==1) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean refactorIdColumn() {
		Integer dv =firstYearFacultyRepository.dropIdColumn();
		Integer av = firstYearFacultyRepository.addIdColumn();
		if(av==0 && dv==0)
			return true;
		return !(av==dv);
	}
	
	public boolean truncateAll() {
		return (firstYearFacultyRepository.truncateAll()==0);
	}

}
