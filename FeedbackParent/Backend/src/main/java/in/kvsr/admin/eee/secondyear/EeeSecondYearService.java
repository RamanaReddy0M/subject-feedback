package in.kvsr.admin.eee.secondyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.eee.EeeTwoOne;
import in.kvsr.common.entity.eee.EeeTwoTwo;

@Service
public class EeeSecondYearService {
	
	@Autowired
	private EeeTwoOneRepository oneOneRepository;
	@Autowired
	private EeeTwoTwoRepository oneTwoRepository;
	
	public EeeTwoOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public EeeTwoTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn2_1(EeeTwoOne eeeTwoOne) {
		try {
			oneOneRepository.save(eeeTwoOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn2_2(EeeTwoTwo eeeTwoTwo) {
		try {
			oneTwoRepository.save(eeeTwoTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<EeeTwoOne> listAllOf2_1() {
		return (List<EeeTwoOne>) oneOneRepository.findAll();
	}
	public List<EeeTwoTwo> listAllOf2_2() {
		return (List<EeeTwoTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(EeeTwoOne eeeTwoOne) {
		return oneOneRepository.deleteBySubCode(eeeTwoOne.getSubjectCode())>0;
	}
	
	public boolean delete(EeeTwoTwo eeeTwoTwo) {
		return oneTwoRepository.deleteBySubCode(eeeTwoTwo.getSubjectCode())>0;
	}
	
	public boolean deleteBySubCodeOf2_1(String subCode) {
		return oneOneRepository.deleteBySubCode(subCode)>0;
	}
	public boolean deleteBySubCodeOf2_2(String subCode) {
		return oneTwoRepository.deleteBySubCode(subCode)>0;
	}
	
	public boolean refactor2_1() {
		Integer dv =oneOneRepository.dropIdColumn();
		Integer av = oneOneRepository.addIdColumn();
		return !(av==dv);
	}
	public boolean refactor2_2() {
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
		if(subject instanceof EeeTwoOne) {
			return saveIn2_1((EeeTwoOne)subject);
		}else {
			return saveIn2_2((EeeTwoTwo)subject);
		}
	}
}
