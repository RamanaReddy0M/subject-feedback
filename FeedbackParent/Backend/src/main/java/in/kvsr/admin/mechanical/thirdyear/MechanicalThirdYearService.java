package in.kvsr.admin.mechanical.thirdyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.mechanical.MechanicalThreeOne;
import in.kvsr.common.entity.mechanical.MechanicalThreeTwo;

@Service
public class MechanicalThirdYearService {
	
	@Autowired
	private MechanicalThreeOneRepository oneOneRepository;
	@Autowired
	private MechanicalThreeTwoRepository oneTwoRepository;
	
	public MechanicalThreeOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public MechanicalThreeTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn3_1(MechanicalThreeOne mechanicalThreeOne) {
		try {
			oneOneRepository.save(mechanicalThreeOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn3_2(MechanicalThreeTwo mechanicalThreeTwo) {
		try {
			oneTwoRepository.save(mechanicalThreeTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<MechanicalThreeOne> listAllOf3_1() {
		return (List<MechanicalThreeOne>) oneOneRepository.findAll();
	}
	public List<MechanicalThreeTwo> listAllOf3_2() {
		return (List<MechanicalThreeTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(MechanicalThreeOne mechanicalThreeOne) {
		return oneOneRepository.deleteBySubCode(mechanicalThreeOne.getSubjectCode())>0;
	}
	
	public boolean delete(MechanicalThreeTwo mechanicalThreeTwo) {
		return oneTwoRepository.deleteBySubCode(mechanicalThreeTwo.getSubjectCode())>0;
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
		if(subject instanceof MechanicalThreeOne) {
			return saveIn3_1((MechanicalThreeOne)subject);
		}else {
			return saveIn3_2((MechanicalThreeTwo)subject);
		}
	}
}
