package in.kvsr.admin.ece.secondyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.ece.EceTwoOne;
import in.kvsr.common.entity.ece.EceTwoTwo;

@Service
public class EceSecondYearService {
	
	@Autowired
	private EceTwoOneRepository oneOneRepository;
	@Autowired
	private EceTwoTwoRepository oneTwoRepository;
	
	public EceTwoOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}
	
	public EceTwoTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}
	
	public boolean saveIn2_1(EceTwoOne eceTwoOne) {
		try {
			oneOneRepository.save(eceTwoOne);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	public boolean saveIn2_2(EceTwoTwo eceTwoTwo) {
		try {
			oneTwoRepository.save(eceTwoTwo);
			return true;
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	public List<EceTwoOne> listAllOf2_1() {
		return (List<EceTwoOne>) oneOneRepository.findAll();
	}
	public List<EceTwoTwo> listAllOf2_2() {
		return (List<EceTwoTwo>) oneTwoRepository.findAll();
	}
	
	public boolean delete(EceTwoOne eceTwoOne) {
		return oneOneRepository.deleteBySubCode(eceTwoOne.getSubjectCode())>0;
	}
	
	public boolean delete(EceTwoTwo eceTwoTwo) {
		return oneTwoRepository.deleteBySubCode(eceTwoTwo.getSubjectCode())>0;
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
		if(subject instanceof EceTwoOne) {
			return saveIn2_1((EceTwoOne)subject);
		}else {
			return saveIn2_2((EceTwoTwo)subject);
		}
	}
}
