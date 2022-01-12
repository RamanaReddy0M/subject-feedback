package in.kvsr.admin.ece.thirdyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.ece.EceThreeOne;
import in.kvsr.common.entity.ece.EceThreeTwo;

@Service
public class EceThirdYearService {
	
	@Autowired
	private EceThreeOneRepository oneOneRepository;
	@Autowired
	private EceThreeTwoRepository oneTwoRepository;
	
	public EceThreeOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public EceThreeTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn3_1(EceThreeOne eceThreeOne) {
		try {
			oneOneRepository.save(eceThreeOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn3_2(EceThreeTwo eceThreeTwo) {
		try {
			oneTwoRepository.save(eceThreeTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<EceThreeOne> listAllOf3_1() {
		return (List<EceThreeOne>) oneOneRepository.findAll();
	}
	public List<EceThreeTwo> listAllOf3_2() {
		return (List<EceThreeTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(EceThreeOne eceThreeOne) {
		return oneOneRepository.deleteBySubCode(eceThreeOne.getSubjectCode())>0;
	}
	
	public boolean delete(EceThreeTwo eceThreeTwo) {
		return oneTwoRepository.deleteBySubCode(eceThreeTwo.getSubjectCode())>0;
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
		if(subject instanceof EceThreeOne) {
			return saveIn3_1((EceThreeOne)subject);
		}else {
			return saveIn3_2((EceThreeTwo)subject);
		}
	}
}
