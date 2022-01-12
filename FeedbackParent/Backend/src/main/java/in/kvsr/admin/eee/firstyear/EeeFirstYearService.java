package in.kvsr.admin.eee.firstyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.eee.EeeOneOne;
import in.kvsr.common.entity.eee.EeeOneTwo;

@Service
public class EeeFirstYearService {
	
	@Autowired
	private EeeOneOneRepository oneOneRepository;
	@Autowired
	private EeeOneTwoRepository oneTwoRepository;
	
	public EeeOneOne getOneOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public EeeOneTwo getOneTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn1_1(EeeOneOne eeeOneOne) {
		try {
			oneOneRepository.save(eeeOneOne);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn1_2(EeeOneTwo eeeOneTwo) {
		try {
			oneTwoRepository.save(eeeOneTwo);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<EeeOneOne> listAllOf1_1() {
		return (List<EeeOneOne>) oneOneRepository.findAll();
	}
	public List<EeeOneTwo> listAllOf1_2() {
		return (List<EeeOneTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(EeeOneOne eeeOneOne) {
		return oneOneRepository.deleteBySubCode(eeeOneOne.getSubjectCode())>0;
	}
	
	public boolean delete(EeeOneTwo eeeOneTwo) {
		return oneTwoRepository.deleteBySubCode(eeeOneTwo.getSubjectCode())>0;
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
		if(subject instanceof EeeOneOne) {
			return saveIn1_1((EeeOneOne)subject);
		}else {
			return saveIn1_2((EeeOneTwo)subject);
		}
	}
}
