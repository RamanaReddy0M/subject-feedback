package in.kvsr.admin.ece.firstyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.ece.EceOneOne;
import in.kvsr.common.entity.ece.EceOneTwo;

@Service
public class EceFirstYearService {
	
	@Autowired
	private EceOneOneRepository oneOneRepository;
	@Autowired
	private EceOneTwoRepository oneTwoRepository;
	
	public EceOneOne getOneOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public EceOneTwo getOneTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn1_1(EceOneOne eceOneOne) {
		try {
			oneOneRepository.save(eceOneOne);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn1_2(EceOneTwo eceOneTwo) {
		try {
			oneTwoRepository.save(eceOneTwo);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<EceOneOne> listAllOf1_1() {
		return (List<EceOneOne>) oneOneRepository.findAll();
	}
	public List<EceOneTwo> listAllOf1_2() {
		return (List<EceOneTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(EceOneOne eceOneOne) {
		return oneOneRepository.deleteBySubCode(eceOneOne.getSubjectCode())>0;
	}
	
	public boolean delete(EceOneTwo eceOneTwo) {
		return oneTwoRepository.deleteBySubCode(eceOneTwo.getSubjectCode())>0;
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
		if(subject instanceof EceOneOne) {
			return saveIn1_1((EceOneOne)subject);
		}else {
			return saveIn1_2((EceOneTwo)subject);
		}
	}
}
