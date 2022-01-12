package in.kvsr.admin.mechanical.firstyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.mechanical.MechanicalOneOne;
import in.kvsr.common.entity.mechanical.MechanicalOneTwo;

@Service
public class MechanicalFirstYearService {
	
	@Autowired
	private MechanicalOneOneRepository oneOneRepository;
	@Autowired
	private MechanicalOneTwoRepository oneTwoRepository;
	
	public MechanicalOneOne getOneOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public MechanicalOneTwo getOneTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn1_1(MechanicalOneOne mechanicalOneOne) {
		try {
			oneOneRepository.save(mechanicalOneOne);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn1_2(MechanicalOneTwo mechanicalOneTwo) {
		try {
			oneTwoRepository.save(mechanicalOneTwo);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<MechanicalOneOne> listAllOf1_1() {
		return (List<MechanicalOneOne>) oneOneRepository.findAll();
	}
	public List<MechanicalOneTwo> listAllOf1_2() {
		return (List<MechanicalOneTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(MechanicalOneOne mechanicalOneOne) {
		return oneOneRepository.deleteBySubCode(mechanicalOneOne.getSubjectCode())>0;
	}
	
	public boolean delete(MechanicalOneTwo mechanicalOneTwo) {
		return oneTwoRepository.deleteBySubCode(mechanicalOneTwo.getSubjectCode())>0;
	}
	
	public boolean deleteBySubCodeOf1_1(String subCode) {
		return oneOneRepository.deleteBySubCode(subCode)>0;
	}
	public boolean deleteBySubCodeOf1_2(String subCode) {
		return oneTwoRepository.deleteBySubCode(subCode)>0;
	}
	
	public boolean refactor1_1() {
		Integer dv =oneOneRepository.dropIdColumn();
		Integer av = oneOneRepository.addIdColumn();
		return !(av==dv);
	}
	public boolean refactor1_2() {
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
		if(subject instanceof MechanicalOneOne) {
			return saveIn1_1((MechanicalOneOne)subject);
		}else {
			return saveIn1_2((MechanicalOneTwo)subject);
		}
	}
}
