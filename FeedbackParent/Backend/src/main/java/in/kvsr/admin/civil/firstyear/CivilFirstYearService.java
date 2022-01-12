package in.kvsr.admin.civil.firstyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.civil.CivilOneOne;
import in.kvsr.common.entity.civil.CivilOneTwo;

@Service
public class CivilFirstYearService {
	
	@Autowired
	private CivilOneOneRepository oneOneRepository;
	@Autowired
	private CivilOneTwoRepository oneTwoRepository;
	
	public CivilOneOne getOneOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public CivilOneTwo getOneTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn1_1(CivilOneOne civilOneOne) {
		try {
			oneOneRepository.save(civilOneOne);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn1_2(CivilOneTwo civilOneTwo) {
		try {
			oneTwoRepository.save(civilOneTwo);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<CivilOneOne> listAllOf1_1() {
		return (List<CivilOneOne>) oneOneRepository.findAll();
	}
	public List<CivilOneTwo> listAllOf1_2() {
		return (List<CivilOneTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(CivilOneOne civilOneOne) {
		return oneOneRepository.deleteBySubCode(civilOneOne.getSubjectCode())>0;
	}
	
	public boolean delete(CivilOneTwo civilOneTwo) {
		return oneTwoRepository.deleteBySubCode(civilOneTwo.getSubjectCode())>0;
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
		if(subject instanceof CivilOneOne) {
			return saveIn1_1((CivilOneOne)subject);
		}else {
			return saveIn1_2((CivilOneTwo)subject);
		}
	}
}
