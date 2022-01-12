package in.kvsr.admin.ece;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.kvsr.admin.ece.fourthyear.EceFourthYearService;
import in.kvsr.admin.ece.secondyear.EceSecondYearService;
import in.kvsr.admin.ece.thirdyear.EceThirdYearService;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.ece.EceFeedback;

@Service
public class EceFeedbackService {

	@Autowired
	private EceFeedbackRepository eceFeedbackRepository;

	@Autowired
	private EceSecondYearService eceSecondYearService;

	@Autowired
	private EceThirdYearService eceThirdYearService;

	@Autowired
	private EceFourthYearService eceFourthYearService;

	public List<EceFeedback> listAll() {
		return (List<EceFeedback>) eceFeedbackRepository.findAll();
	}

	public List<EceFeedback> sortByField(String field) {
		return eceFeedbackRepository.sortByField(Sort.by(field));
	}

	public boolean save(EceFeedback feedback) {

		try {
			feedback.setAverage(feedback.calcAverage());
			eceFeedbackRepository.save(feedback);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<EceFeedback> getFeedbacksBySubjectCode(String subjectCode) {
		return eceFeedbackRepository.getFeedbacksBySubjectCode(subjectCode);
	}

	public List<String> getRemarks(String subjectCode, String facultyRegId) {
		return eceFeedbackRepository.getRemarks(subjectCode, facultyRegId);
	}

	public boolean truncateAll() {
		return (eceFeedbackRepository.truncate() == 0);
	}

	public void setAverage() {
		for (String scAndFR : distinctSubjectsAndFaculty()) {
			String[] array = scAndFR.split(",");
			List<EceFeedback> feedbacks = eceFeedbackRepository.getFeedbacksBySubjectCodeAndFacultyRegId(array[0],
					array[1]);
			setTotalToSubject(array[0], array[1], feedbacks);
		}
	}

	public void setTotalToSubject(String subjectCode, String facultyRegId, List<EceFeedback> feedbacks) {
		Map<Integer, Integer> hMap = new LinkedHashMap<>();
		Float average = 0.0f;
		for (int i = 0; i < 5; i++) {
			hMap.put(i, 0);
		}
		for (EceFeedback feedback : feedbacks) {
			setQuestionCounter(feedback, hMap);
			average += feedback.getAverage();
		}
		average = average / feedbacks.size();
		String total = "";
		for (int i = 0; i < 5; i++) {
			total = total + String.format("%.2f", ((float) hMap.get(i) / (float) feedbacks.size())) + " ";
		}
		Subject subject = eceSecondYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			eceSecondYearService.save(subject);
			return;
		}

		subject = eceThirdYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			eceThirdYearService.save(subject);
			return;
		}

		subject = eceFourthYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			eceFourthYearService.save(subject);
			return;
		}

	}

	private void setQuestionCounter(EceFeedback feedback, Map<Integer, Integer> hashMap) {
		List<Integer> feedbackList = new ArrayList<>();
		feedbackList.add(feedback.getQuestion1());
		feedbackList.add(feedback.getQuestion2());
		feedbackList.add(feedback.getQuestion3());
		feedbackList.add(feedback.getQuestion4());
		feedbackList.add(feedback.getQuestion5());
		for (int i = 0; i < feedbackList.size(); i++) {
			hashMap.put(i, hashMap.get(i) + feedbackList.get(i));
		}
	}

	public List<String> distinctSubjects() {
		return eceFeedbackRepository.distinctSubjects();
	}

	public List<String> distinctSubjectsAndFaculty() {
		return eceFeedbackRepository.distinctSubjectsAndFaculty();
	}
}
