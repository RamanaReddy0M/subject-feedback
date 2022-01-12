package in.kvsr.admin.civil.secondyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.civil.CivilTwoOne;
import in.kvsr.common.entity.civil.CivilTwoTwo;

@Service
public class CivilSecondYearService {
	
	@Autowired
	private CivilTwoOneRepository oneOneRepository;
	@Autowired
	private CivilTwoTwoRepository oneTwoRepository;
	
	public CivilTwoOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public CivilTwoTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn2_1(CivilTwoOne civilTwoOne) {
		try {
			oneOneRepository.save(civilTwoOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn2_2(CivilTwoTwo civilTwoTwo) {
		try {
			oneTwoRepository.save(civilTwoTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<CivilTwoOne> listAllOf2_1() {
		return (List<CivilTwoOne>) oneOneRepository.findAll();
	}
	public List<CivilTwoTwo> listAllOf2_2() {
		return (List<CivilTwoTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(CivilTwoOne civilTwoOne) {
		return oneOneRepository.deleteBySubCode(civilTwoOne.getSubjectCode())>0;
	}
	
	public boolean delete(CivilTwoTwo civilTwoTwo) {
		return oneTwoRepository.deleteBySubCode(civilTwoTwo.getSubjectCode())>0;
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
		if(subject instanceof CivilTwoOne) {
			return saveIn2_1((CivilTwoOne)subject);
		}else {
			return saveIn2_2((CivilTwoTwo)subject);
		}
	}
}
