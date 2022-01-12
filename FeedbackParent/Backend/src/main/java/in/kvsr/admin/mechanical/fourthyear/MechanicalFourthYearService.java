package in.kvsr.admin.mechanical.fourthyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.mechanical.MechanicalFourOne;
import in.kvsr.common.entity.mechanical.MechanicalFourTwo;

@Service
public class MechanicalFourthYearService {
	
	@Autowired
	private MechanicalFourOneRepository oneOneRepository;
	@Autowired
	private MechanicalFourTwoRepository oneTwoRepository;
	
	public MechanicalFourOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public MechanicalFourTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn4_1(MechanicalFourOne mechanicalFourOne) {
		try {
			oneOneRepository.save(mechanicalFourOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn4_2(MechanicalFourTwo mechanicalFourTwo) {
		try {
			oneTwoRepository.save(mechanicalFourTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<MechanicalFourOne> listAllOf4_1() {
		return (List<MechanicalFourOne>) oneOneRepository.findAll();
	}
	public List<MechanicalFourTwo> listAllOf4_2() {
		return (List<MechanicalFourTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(MechanicalFourOne mechanicalFourOne) {
		return oneOneRepository.deleteBySubCode(mechanicalFourOne.getSubjectCode())>0;
	}
	
	public boolean delete(MechanicalFourTwo mechanicalFourTwo) {
		return oneTwoRepository.deleteBySubCode(mechanicalFourTwo.getSubjectCode())>0;
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
		if(subject instanceof MechanicalFourOne) {
			return saveIn4_1((MechanicalFourOne)subject);
		}else {
			return saveIn4_2((MechanicalFourTwo)subject);
		}
	}
}
