package in.kvsr.admin.fourthyear;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.FourthYearFaculty;

@Service
public class FourthYearFacultyService {
	
	@Autowired
	private FourthYearFacultyRepository fourthYearFacultyRepository;
	
	public boolean save(FourthYearFaculty fourthYearFaculty) {
		try {
			fourthYearFacultyRepository.save(fourthYearFaculty);
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	
	public List<FourthYearFaculty> listAll() {
		List<FourthYearFaculty> facultyList = (List<FourthYearFaculty>) fourthYearFacultyRepository.findAll();
		
		if(facultyList == null) {
			return null;
		}
		return facultyList;
	}
	
	public FourthYearFaculty getFacultyByRegId(String regId) {
		return fourthYearFacultyRepository.getByRegId(regId);
	}
	
	public boolean deleteById(Long id) {
		try {
			fourthYearFacultyRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public boolean deleteByRegId(String regId) {
		if(fourthYearFacultyRepository.deleteByRegId(regId)==1) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean refactorIdColumn() {
		Integer dv =fourthYearFacultyRepository.dropIdColumn();
		Integer av = fourthYearFacultyRepository.addIdColumn();
		if(av==0 && dv==0)
			return true;
		return !(av==dv);
	}
	
	public boolean truncateAll() {
		return (fourthYearFacultyRepository.truncateAll()==0);
	}
	
}
