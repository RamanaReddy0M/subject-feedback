package in.kvsr.admin.civil.thirdyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.civil.CivilThreeOne;
import in.kvsr.common.entity.civil.CivilThreeTwo;

@Service
public class CivilThirdYearService {
	
	@Autowired
	private CivilThreeOneRepository oneOneRepository;
	@Autowired
	private CivilThreeTwoRepository oneTwoRepository;
	
	public CivilThreeOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public CivilThreeTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn3_1(CivilThreeOne civilThreeOne) {
		try {
			oneOneRepository.save(civilThreeOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn3_2(CivilThreeTwo civilThreeTwo) {
		try {
			oneTwoRepository.save(civilThreeTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<CivilThreeOne> listAllOf3_1() {
		return (List<CivilThreeOne>) oneOneRepository.findAll();
	}
	public List<CivilThreeTwo> listAllOf3_2() {
		return (List<CivilThreeTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(CivilThreeOne civilThreeOne) {
		return oneOneRepository.deleteBySubCode(civilThreeOne.getSubjectCode())>0;
	}
	
	public boolean delete(CivilThreeTwo civilThreeTwo) {
		return oneTwoRepository.deleteBySubCode(civilThreeTwo.getSubjectCode())>0;
	}
	
	public boolean deleteBySubCodeOf3_1(String subCode) {
		return oneOneRepository.deleteBySubCode(subCode)>0;
	}
	public boolean deleteBySubCodeOf3_2(String subCode) {
		return oneTwoRepository.deleteBySubCode(subCode)>0;
	}
	
	public boolean refactor3_1() {
		Integer dv =oneOneRepository.dropIdColumn();
		Integer av = oneOneRepository.addIdColumn();
		return !(av==dv);
	}
	public boolean refactor3_2() {
		Integer dv =oneTwoRepository.dropIdColumn();
		Integer av = oneTwoRepository.addIdColumn();
		return !(av==dv);
	}
	
	public Subject findBySubjectCode(String subjectCode) {
		Subject subject = oneOneRepository.findBySubjectCode(subjectCode);
		if(subject == null) {
			subject = oneTwoRepository.findBySubjectCode(subjectCode);
		}
		return subject;
	}
	public boolean save(Subject subject) {
		if(subject instanceof CivilThreeOne) {
			return saveIn3_1((CivilThreeOne)subject);
		}else {
			return saveIn3_2((CivilThreeTwo)subject);
		}
	}
}
