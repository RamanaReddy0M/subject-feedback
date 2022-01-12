package in.kvsr.admin.ece.fourthyear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.ece.EceFourOne;
import in.kvsr.common.entity.ece.EceFourTwo;

@Service
public class EceFourthYearService {

	@Autowired
	private EceFourOneRepository oneOneRepository;
	@Autowired
	private EceFourTwoRepository oneTwoRepository;

	public EceFourOne getOneById(Long id) {
		return oneOneRepository.findById(id).get();
	}

	public EceFourTwo getTwoById(Long id) {
		return oneTwoRepository.findById(id).get();
	}

	public boolean saveIn4_1(EceFourOne eceFourOne) {
		try {
			oneOneRepository.save(eceFourOne);
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	public boolean saveIn4_2(EceFourTwo eceFourTwo) {
		try {
			oneTwoRepository.save(eceFourTwo);
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	public List<EceFourOne> listAllOf4_1() {
		return (List<EceFourOne>) oneOneRepository.findAll();
	}

	public List<EceFourTwo> listAllOf4_2() {
		return (List<EceFourTwo>) oneTwoRepository.findAll();
	}

	public boolean delete(EceFourOne eceFourOne) {
		return oneOneRepository.deleteBySubCode(eceFourOne.getSubjectCode()) > 0;
	}

	public boolean delete(EceFourTwo eceFourTwo) {
		return oneTwoRepository.deleteBySubCode(eceFourTwo.getSubjectCode()) > 0;
	}

	public boolean deleteBySubCodeOf4_1(String subCode) {
		return oneOneRepository.deleteBySubCode(subCode) > 0;
	}

	public boolean deleteBySubCodeOf4_2(String subCode) {
		return oneTwoRepository.deleteBySubCode(subCode) > 0;
	}

	public boolean refactor4_1() {
		Integer dv = oneOneRepository.dropIdColumn();
		Integer av = oneOneRepository.addIdColumn();
		return !(av == dv);
	}

	public boolean refactor4_2() {
		Integer dv = oneTwoRepository.dropIdColumn();
		Integer av = oneTwoRepository.addIdColumn();
		return !(av == dv);
	}

	public Subject findBySubjectCode(String subjectCode) {
		Subject subject = oneOneRepository.findBySubjectCode(subjectCode);
		if (subject == null) {
			subject = oneTwoRepository.findBySubjectCode(subjectCode);
		}
		return subject;
	}

	public boolean save(Subject subject) {
		if (subject instanceof EceFourOne) {
			return saveIn4_1((EceFourOne) subject);
		} else {
			return saveIn4_2((EceFourTwo) subject);
		}
	}
}
