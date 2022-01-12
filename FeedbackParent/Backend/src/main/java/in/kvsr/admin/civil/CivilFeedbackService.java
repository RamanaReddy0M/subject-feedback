package in.kvsr.admin.civil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.kvsr.admin.civil.fourthyear.CivilFourthYearService;
import in.kvsr.admin.civil.secondyear.CivilSecondYearService;
import in.kvsr.admin.civil.thirdyear.CivilThirdYearService;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.civil.CivilFeedback;

@Service
public class CivilFeedbackService {

	@Autowired
	private CivilFeedbackRepository civilFeedbackRepository;

	@Autowired
	private CivilSecondYearService civilSecondYearService;

	@Autowired
	private CivilThirdYearService civilThirdYearService;

	@Autowired
	private CivilFourthYearService civilFourthYearService;

	public List<CivilFeedback> listAll() {
		return (List<CivilFeedback>) civilFeedbackRepository.findAll();
	}

	public List<CivilFeedback> sortByField(String field) {
		return civilFeedbackRepository.sortByField(Sort.by(field));
	}

	public boolean save(CivilFeedback feedback) {

		try {
			feedback.setAverage(feedback.calcAverage());
			civilFeedbackRepository.save(feedback);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<CivilFeedback> getFeedbacksBySubjectCode(String subjectCode) {
		return civilFeedbackRepository.getFeedbacksBySubjectCode(subjectCode);
	}

	public List<String> getRemarks(String subjectCode, String facultyRegId) {
		return civilFeedbackRepository.getRemarks(subjectCode, facultyRegId);
	}

	public boolean truncateAll() {
		return (civilFeedbackRepository.truncate() == 0);
	}

	public void setAverage() {
		for (String scAndFR : distinctSubjectsAndFaculty()) {
			String[] array = scAndFR.split(",");
			List<CivilFeedback> feedbacks = civilFeedbackRepository.getFeedbacksBySubjectCodeAndFacultyRegId(array[0],
					array[1]);
			setTotalToSubject(array[0], array[1], feedbacks);
		}
	}

	public void setTotalToSubject(String subjectCode, String facultyRegId, List<CivilFeedback> feedbacks) {
		Map<Integer, Integer> hMap = new LinkedHashMap<>();
		Float average = 0.0f;
		for (int i = 0; i < 5; i++) {
			hMap.put(i, 0);
		}
		for (CivilFeedback feedback : feedbacks) {
			setQuestionCounter(feedback, hMap);
			average += feedback.getAverage();
		}
		average = average / feedbacks.size();
		String total = "";
		for (int i = 0; i < 5; i++) {
			total = total + String.format("%.2f", ((float) hMap.get(i) / (float) feedbacks.size())) + " ";
		}
		Subject subject = civilSecondYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			civilSecondYearService.save(subject);
			return;
		}

		subject = civilThirdYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			civilThirdYearService.save(subject);
			return;
		}

		subject = civilFourthYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			civilFourthYearService.save(subject);
			return;
		}

	}

	private void setQuestionCounter(CivilFeedback feedback, Map<Integer, Integer> hashMap) {
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
		return civilFeedbackRepository.distinctSubjects();
	}

	public List<String> distinctSubjectsAndFaculty() {
		return civilFeedbackRepository.distinctSubjectsAndFaculty();
	}
}
