package in.kvsr.admin.cse.firstyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.cse.CseOneOne;
import in.kvsr.common.entity.cse.CseOneTwo;

@Service
public class CseFirstYearService {
	
	@Autowired
	private CseOneOneRepository oneOneRepository;
	@Autowired
	private CseOneTwoRepository oneTwoRepository;
	
	public CseOneOne getOneOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public CseOneTwo getOneTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn1_1(CseOneOne cseOneOne) {
		try {
			oneOneRepository.save(cseOneOne);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn1_2(CseOneTwo cseOneTwo) {
		try {
			oneTwoRepository.save(cseOneTwo);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<CseOneOne> listAllOf1_1() {
		return (List<CseOneOne>) oneOneRepository.findAll();
	}
	public List<CseOneTwo> listAllOf1_2() {
		return (List<CseOneTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(CseOneOne cseOneOne) {
		return oneOneRepository.deleteBySubCode(cseOneOne.getSubjectCode())>0;
	}
	
	public boolean delete(CseOneTwo cseOneTwo) {
		return oneTwoRepository.deleteBySubCode(cseOneTwo.getSubjectCode())>0;
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
		if(subject instanceof CseOneOne) {
			return saveIn1_1((CseOneOne)subject);
		}else {
			return saveIn1_2((CseOneTwo)subject);
		}
	}
}
