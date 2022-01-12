package in.kvsr.admin.cse.thirdyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.cse.CseThreeOne;
import in.kvsr.common.entity.cse.CseThreeTwo;

@Service
public class CseThirdYearService {
	
	@Autowired
	private CseThreeOneRepository oneOneRepository;
	@Autowired
	private CseThreeTwoRepository oneTwoRepository;
	
	public CseThreeOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public CseThreeTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn3_1(CseThreeOne cseThreeOne) {
		try {
			oneOneRepository.save(cseThreeOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn3_2(CseThreeTwo cseThreeTwo) {
		try {
			oneTwoRepository.save(cseThreeTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<CseThreeOne> listAllOf3_1() {
		return (List<CseThreeOne>) oneOneRepository.findAll();
	}
	public List<CseThreeTwo> listAllOf3_2() {
		return (List<CseThreeTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(CseThreeOne cseThreeOne) {
		return oneOneRepository.deleteBySubCode(cseThreeOne.getSubjectCode())>0;
	}
	
	public boolean delete(CseThreeTwo cseThreeTwo) {
		return oneTwoRepository.deleteBySubCode(cseThreeTwo.getSubjectCode())>0;
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
		if(subject instanceof CseThreeOne) {
			return saveIn3_1((CseThreeOne)subject);
		}else {
			return saveIn3_2((CseThreeTwo)subject);
		}
	}
}
