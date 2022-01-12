package in.kvsr.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.kvsr.common.entity.Feedback;
import in.kvsr.common.entity.HandSFeedback;
import in.kvsr.common.entity.civil.CivilFeedback;
import in.kvsr.common.entity.cse.CseFeedback;
import in.kvsr.common.entity.ece.EceFeedback;
import in.kvsr.common.entity.eee.EeeFeedback;
import in.kvsr.common.entity.mechanical.MechanicalFeedback;

@Service
public class FeedbackService {
	
	@Autowired
	private HandSFeedbackRepository handSFeedbackRepository;
	
	@Autowired
	private CseFeedbackRepository cseFeedbackRepository;
	
	@Autowired
	private EceFeedbackRepository eceFeedbackRepository;
	
	@Autowired
	private EeeFeedbackRepository eeeFeedbackRepository;
	
	@Autowired
	private CivilFeedbackRepository civilFeedbackRepository;
	
	@Autowired
	private MechanicalFeedbackRepository mechanicalFeedbackRepository;
	
	
	
	public boolean save(Feedback feedback, String department) {
		
		try {
			if(department.equalsIgnoreCase("H&S")) {
				Feedback temp = new HandSFeedback();
				copy(feedback, temp);
				handSFeedbackRepository.save((HandSFeedback)temp);
				return true;
			}else if (department.equalsIgnoreCase("CSE")) {
				Feedback temp = new CseFeedback();
				copy(feedback, temp);
				cseFeedbackRepository.save((CseFeedback)temp);
				return true;
			}else if (department.equalsIgnoreCase("ECE")) {
				Feedback temp = new EceFeedback();
				copy(feedback, temp);
				eceFeedbackRepository.save((EceFeedback)temp);
				return true;
			}else if (department.equalsIgnoreCase("CIVIL")) {
				Feedback temp = new CivilFeedback();
				copy(feedback, temp);
				civilFeedbackRepository.save((CivilFeedback)temp);
				return true;
			}else if (department.equalsIgnoreCase("EEE")) {
				Feedback temp = new EeeFeedback();
				copy(feedback, temp);
				eeeFeedbackRepository.save((EeeFeedback)temp);
				return true;
			}else if (department.equalsIgnoreCase("MECHANICAL")) {
				Feedback temp = new MechanicalFeedback();
				copy(feedback, temp);
				mechanicalFeedbackRepository.save((MechanicalFeedback)temp);
				return true;
			}
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
		return false;
	}
	
	private void copy(Feedback source, Feedback destination) {
		destination.setId(source.getId());
		destination.setStudentId(source.getStudentId());
		destination.setFacultyRegId(source.getFacultyRegId());
		destination.setSubjectCode(source.getSubjectCode());
		destination.setQuestion1(source.getQuestion1());
		destination.setQuestion2(source.getQuestion2());
		destination.setQuestion3(source.getQuestion3());
		destination.setQuestion4(source.getQuestion4());
		destination.setQuestion5(source.getQuestion5());
		destination.setAverage(source.getAverage());
		destination.setRemarks(source.getRemarks());
	}
	
	
}
