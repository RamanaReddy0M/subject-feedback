package in.kvsr.admin.secondyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.SecondYearFaculty;

@Service
public class SecondYearFacultyService {
	
	@Autowired
	private SecondYearFacultyRepository secondYearFacultyRepository;
	
	public boolean save(SecondYearFaculty secondYearFaculty) {
		try {
			secondYearFacultyRepository.save(secondYearFaculty);
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	
	public List<SecondYearFaculty> listAll() {
		List<SecondYearFaculty> facultyList = (List<SecondYearFaculty>) secondYearFacultyRepository.findAll();
		
		if(facultyList == null) {
			return null;
		}
		return facultyList;
	}
	
	public SecondYearFaculty getFacultyByRegId(String regId) {
		return secondYearFacultyRepository.getByRegId(regId);
	}
	
	public boolean deleteById(Long id) {
		try {
			secondYearFacultyRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public boolean deleteByRegId(String regId) {
		if(secondYearFacultyRepository.deleteByRegId(regId)==1) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean refactorIdColumn() {
		Integer dv =secondYearFacultyRepository.dropIdColumn();
		Integer av = secondYearFacultyRepository.addIdColumn();
		if(av==0 && dv==0)
			return true;
		return !(av==dv);
	}
	
	public boolean truncateAll() {
		return (secondYearFacultyRepository.truncateAll()==0);
	}

}
