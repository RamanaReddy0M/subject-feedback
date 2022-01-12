package in.kvsr.admin.civil.fourthyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.civil.CivilFourOne;
import in.kvsr.common.entity.civil.CivilFourTwo;

@Service
public class CivilFourthYearService {
	
	@Autowired
	private CivilFourOneRepository oneOneRepository;
	@Autowired
	private CivilFourTwoRepository oneTwoRepository;
	
	public CivilFourOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public CivilFourTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn4_1(CivilFourOne civilFourOne) {
		try {
			oneOneRepository.save(civilFourOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn4_2(CivilFourTwo civilFourTwo) {
		try {
			oneTwoRepository.save(civilFourTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<CivilFourOne> listAllOf4_1() {
		return (List<CivilFourOne>) oneOneRepository.findAll();
	}
	public List<CivilFourTwo> listAllOf4_2() {
		return (List<CivilFourTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(CivilFourOne civilFourOne) {
		return oneOneRepository.deleteBySubCode(civilFourOne.getSubjectCode())>0;
	}
	
	public boolean delete(CivilFourTwo civilFourTwo) {
		return oneTwoRepository.deleteBySubCode(civilFourTwo.getSubjectCode())>0;
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
		if(subject instanceof CivilFourOne) {
			return saveIn4_1((CivilFourOne)subject);
		}else {
			return saveIn4_2((CivilFourTwo)subject);
		}
	}
}
