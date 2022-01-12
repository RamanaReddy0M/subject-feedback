package in.kvsr.admin.mechanical.secondyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.mechanical.MechanicalTwoOne;
import in.kvsr.common.entity.mechanical.MechanicalTwoTwo;

@Service
public class MechanicalSecondYearService {
	
	@Autowired
	private MechanicalTwoOneRepository oneOneRepository;
	@Autowired
	private MechanicalTwoTwoRepository oneTwoRepository;
	
	public MechanicalTwoOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public MechanicalTwoTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn2_1(MechanicalTwoOne mechanicalTwoOne) {
		try {
			oneOneRepository.save(mechanicalTwoOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn2_2(MechanicalTwoTwo mechanicalTwoTwo) {
		try {
			oneTwoRepository.save(mechanicalTwoTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<MechanicalTwoOne> listAllOf2_1() {
		return (List<MechanicalTwoOne>) oneOneRepository.findAll();
	}
	public List<MechanicalTwoTwo> listAllOf2_2() {
		return (List<MechanicalTwoTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(MechanicalTwoOne mechanicalTwoOne) {
		return oneOneRepository.deleteBySubCode(mechanicalTwoOne.getSubjectCode())>0;
	}
	
	public boolean delete(MechanicalTwoTwo mechanicalTwoTwo) {
		return oneTwoRepository.deleteBySubCode(mechanicalTwoTwo.getSubjectCode())>0;
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
		if(subject instanceof MechanicalTwoOne) {
			return saveIn2_1((MechanicalTwoOne)subject);
		}else {
			return saveIn2_2((MechanicalTwoTwo)subject);
		}
	}
}
