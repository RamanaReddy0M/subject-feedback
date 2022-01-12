package in.kvsr.admin.cse.fourthyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.cse.CseFourOne;
import in.kvsr.common.entity.cse.CseFourTwo;

@Service
public class CseFourthYearService {
	
	@Autowired
	private CseFourOneRepository oneOneRepository;
	@Autowired
	private CseFourTwoRepository oneTwoRepository;
	
	public CseFourOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public CseFourTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn4_1(CseFourOne cseFourOne) {
		try {
			oneOneRepository.save(cseFourOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn4_2(CseFourTwo cseFourTwo) {
		try {
			oneTwoRepository.save(cseFourTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<CseFourOne> listAllOf4_1() {
		return (List<CseFourOne>) oneOneRepository.findAll();
	}
	public List<CseFourTwo> listAllOf4_2() {
		return (List<CseFourTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(CseFourOne cseFourOne) {
		return oneOneRepository.deleteBySubCode(cseFourOne.getSubjectCode())>0;
	}
	
	public boolean delete(CseFourTwo cseFourTwo) {
		return oneTwoRepository.deleteBySubCode(cseFourTwo.getSubjectCode())>0;
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
		if(subject instanceof CseFourOne) {
			return saveIn4_1((CseFourOne)subject);
		}else {
			return saveIn4_2((CseFourTwo)subject);
		}
	}
}
