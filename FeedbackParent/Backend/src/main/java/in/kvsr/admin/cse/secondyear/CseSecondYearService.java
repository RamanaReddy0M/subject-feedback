package in.kvsr.admin.cse.secondyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.cse.CseTwoOne;
import in.kvsr.common.entity.cse.CseTwoTwo;

@Service
public class CseSecondYearService {
	
	@Autowired
	private CseTwoOneRepository oneOneRepository;
	@Autowired
	private CseTwoTwoRepository oneTwoRepository;
	
	public CseTwoOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public CseTwoTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn2_1(CseTwoOne cseTwoOne) {
		try {
			oneOneRepository.save(cseTwoOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn2_2(CseTwoTwo cseTwoTwo) {
		try {
			oneTwoRepository.save(cseTwoTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<CseTwoOne> listAllOf2_1() {
		return (List<CseTwoOne>) oneOneRepository.findAll();
	}
	public List<CseTwoTwo> listAllOf2_2() {
		return (List<CseTwoTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(CseTwoOne cseTwoOne) {
		return oneOneRepository.deleteBySubCode(cseTwoOne.getSubjectCode())>0;
	}
	
	public boolean delete(CseTwoTwo cseTwoTwo) {
		return oneTwoRepository.deleteBySubCode(cseTwoTwo.getSubjectCode())>0;
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
		if(subject instanceof CseTwoOne) {
			return saveIn2_1((CseTwoOne)subject);
		}else {
			return saveIn2_2((CseTwoTwo)subject);
		}
	}
}
