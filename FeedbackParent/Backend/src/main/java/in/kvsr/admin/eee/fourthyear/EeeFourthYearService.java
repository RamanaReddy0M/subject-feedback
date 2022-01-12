package in.kvsr.admin.eee.fourthyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.eee.EeeFourOne;
import in.kvsr.common.entity.eee.EeeFourTwo;

@Service
public class EeeFourthYearService {
	
	@Autowired
	private EeeFourOneRepository oneOneRepository;
	@Autowired
	private EeeFourTwoRepository oneTwoRepository;
	
	public EeeFourOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public EeeFourTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn4_1(EeeFourOne eeeFourOne) {
		try {
			oneOneRepository.save(eeeFourOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn4_2(EeeFourTwo eeeFourTwo) {
		try {
			oneTwoRepository.save(eeeFourTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<EeeFourOne> listAllOf4_1() {
		return (List<EeeFourOne>) oneOneRepository.findAll();
	}
	public List<EeeFourTwo> listAllOf4_2() {
		return (List<EeeFourTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(EeeFourOne eeeFourOne) {
		return oneOneRepository.deleteBySubCode(eeeFourOne.getSubjectCode())>0;
	}
	
	public boolean delete(EeeFourTwo eeeFourTwo) {
		return oneTwoRepository.deleteBySubCode(eeeFourTwo.getSubjectCode())>0;
	}
	
	public boolean deleteBySubCodeOf4_1(String subCode) {
		return oneOneRepository.deleteBySubCode(subCode)>0;
	}
	public boolean deleteBySubCodeOf4_2(String subCode) {
		return oneTwoRepository.deleteBySubCode(subCode)>0;
	}
	
	public boolean refactor4_1() {
		Integer dv =oneOneRepository.dropIdColumn();
		Integer av = oneOneRepository.addIdColumn();
		return !(av==dv);
	}
	public boolean refactor4_2() {
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
		if(subject instanceof EeeFourOne) {
			return saveIn4_1((EeeFourOne)subject);
		}else {
			return saveIn4_2((EeeFourTwo)subject);
		}
	}
}
