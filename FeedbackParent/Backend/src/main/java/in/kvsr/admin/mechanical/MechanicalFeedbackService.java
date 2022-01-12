package in.kvsr.admin.mechanical;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.kvsr.admin.mechanical.fourthyear.MechanicalFourthYearService;
import in.kvsr.admin.mechanical.secondyear.MechanicalSecondYearService;
import in.kvsr.admin.mechanical.thirdyear.MechanicalThirdYearService;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.mechanical.MechanicalFeedback;

@Service
public class MechanicalFeedbackService {

	@Autowired
	private MechanicalFeedbackRepository mechanicalFeedbackRepository;

	@Autowired
	private MechanicalSecondYearService mechanicalSecondYearService;

	@Autowired
	private MechanicalThirdYearService mechanicalThirdYearService;

	@Autowired
	private MechanicalFourthYearService mechanicalFourthYearService;

	public List<MechanicalFeedback> listAll() {
		return (List<MechanicalFeedback>) mechanicalFeedbackRepository.findAll();
	}

	public List<MechanicalFeedback> sortByField(String field) {
		return mechanicalFeedbackRepository.sortByField(Sort.by(field));
	}

	public boolean save(MechanicalFeedback feedback) {

		try {
			feedback.setAverage(feedback.calcAverage());
			mechanicalFeedbackRepository.save(feedback);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<MechanicalFeedback> getFeedbacksBySubjectCode(String subjectCode) {
		return mechanicalFeedbackRepository.getFeedbacksBySubjectCode(subjectCode);
	}

	public List<String> getRemarks(String subjectCode, String facultyRegId) {
		return mechanicalFeedbackRepository.getRemarks(subjectCode, facultyRegId);
	}

	public boolean truncateAll() {
		return (mechanicalFeedbackRepository.truncate() == 0);
	}

	public void setAverage() {
		for (String scAndFR : distinctSubjectsAndFaculty()) {
			String[] array = scAndFR.split(",");
			List<MechanicalFeedback> feedbacks = mechanicalFeedbackRepository.getFeedbacksBySubjectCodeAndFacultyRegId(array[0],
					array[1]);
			setTotalToSubject(array[0], array[1], feedbacks);
		}
	}

	public void setTotalToSubject(String subjectCode, String facultyRegId, List<MechanicalFeedback> feedbacks) {
		Map<Integer, Integer> hMap = new LinkedHashMap<>();
		Float average = 0.0f;
		for (int i = 0; i < 5; i++) {
			hMap.put(i, 0);
		}
		for (MechanicalFeedback feedback : feedbacks) {
			setQuestionCounter(feedback, hMap);
			average += feedback.getAverage();
		}
		average = average / feedbacks.size();
		String total = "";
		for (int i = 0; i < 5; i++) {
			total = total + String.format("%.2f", ((float) hMap.get(i) / (float) feedbacks.size())) + " ";
		}
		Subject subject = mechanicalSecondYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			mechanicalSecondYearService.save(subject);
			return;
		}

		subject = mechanicalThirdYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			mechanicalThirdYearService.save(subject);
			return;
		}

		subject = mechanicalFourthYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			mechanicalFourthYearService.save(subject);
			return;
		}

	}

	private void setQuestionCounter(MechanicalFeedback feedback, Map<Integer, Integer> hashMap) {
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
		return mechanicalFeedbackRepository.distinctSubjects();
	}

	public List<String> distinctSubjectsAndFaculty() {
		return mechanicalFeedbackRepository.distinctSubjectsAndFaculty();
	}
}
