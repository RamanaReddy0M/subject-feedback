package in.kvsr.admin.hod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import in.kvsr.common.entity.HOD;

@Service
public class HODService {
	
	@Autowired
	private HODRepository hodRepository;
	
	public boolean save(HOD hod) {
		try {
			hodRepository.save(hod);
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	
	public List<HOD> listAll() {
		return (List<HOD>) hodRepository.findAll();
	}
	
	public boolean deleteById(Long id) {
		try {
			hodRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public boolean deleteByRegId(String regId) {
		if(hodRepository.deleteByRegId(regId)==1) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean truncateAll() {
		return (hodRepository.truncateAll()==0);
	}
	
	public HOD findByRegId(String regId) {
		return hodRepository.findByRegId(regId);
	}
	
	public boolean refactorIdColumn() {
		Integer dv = hodRepository.dropIdColumn();
		Integer av = hodRepository.addIdColumn();
		if(av==0 && dv==0)
			return true;
		return !(av==dv);
	}
}
