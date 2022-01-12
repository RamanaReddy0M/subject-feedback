package in.kvsr.admin.eee.thirdyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.eee.EeeThreeOne;
import in.kvsr.common.entity.eee.EeeThreeTwo;

@Service
public class EeeThirdYearService {
	
	@Autowired
	private EeeThreeOneRepository oneOneRepository;
	@Autowired
	private EeeThreeTwoRepository oneTwoRepository;
	
	public EeeThreeOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public EeeThreeTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn3_1(EeeThreeOne eeeThreeOne) {
		try {
			oneOneRepository.save(eeeThreeOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn3_2(EeeThreeTwo eeeThreeTwo) {
		try {
			oneTwoRepository.save(eeeThreeTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<EeeThreeOne> listAllOf3_1() {
		return (List<EeeThreeOne>) oneOneRepository.findAll();
	}
	public List<EeeThreeTwo> listAllOf3_2() {
		return (List<EeeThreeTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(EeeThreeOne eeeThreeOne) {
		return oneOneRepository.deleteBySubCode(eeeThreeOne.getSubjectCode())>0;
	}
	
	public boolean delete(EeeThreeTwo eeeThreeTwo) {
		return oneTwoRepository.deleteBySubCode(eeeThreeTwo.getSubjectCode())>0;
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
		if(subject instanceof EeeThreeOne) {
			return saveIn3_1((EeeThreeOne)subject);
		}else {
			return saveIn3_2((EeeThreeTwo)subject);
		}
	}
}
