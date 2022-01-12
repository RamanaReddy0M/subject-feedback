package in.kvsr.admin.cse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.kvsr.admin.cse.fourthyear.CseFourthYearService;
import in.kvsr.admin.cse.secondyear.CseSecondYearService;
import in.kvsr.admin.cse.thirdyear.CseThirdYearService;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.cse.CseFeedback;

@Service
public class CseFeedbackService {

	@Autowired
	private CseFeedbackRepository cseFeedbackRepository;

	@Autowired
	private CseSecondYearService cseSecondYearService;

	@Autowired
	private CseThirdYearService cseThirdYearService;

	@Autowired
	private CseFourthYearService cseFourthYearService;

	public List<CseFeedback> listAll() {
		return (List<CseFeedback>) cseFeedbackRepository.findAll();
	}

	public List<CseFeedback> sortByField(String field) {
		return cseFeedbackRepository.sortByField(Sort.by(field));
	}

	public boolean save(CseFeedback feedback) {

		try {
			feedback.setAverage(feedback.calcAverage());
			cseFeedbackRepository.save(feedback);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<CseFeedback> getFeedbacksBySubjectCode(String subjectCode) {
		return cseFeedbackRepository.getFeedbacksBySubjectCode(subjectCode);
	}

	public List<String> getRemarks(String subjectCode, String facultyRegId) {
		return cseFeedbackRepository.getRemarks(subjectCode, facultyRegId);
	}

	public boolean truncateAll() {
		return (cseFeedbackRepository.truncate() == 0);
	}

	public void setAverage() {
		for (String scAndFR : distinctSubjectsAndFaculty()) {
			String[] array = scAndFR.split(",");
			List<CseFeedback> feedbacks = cseFeedbackRepository.getFeedbacksBySubjectCodeAndFacultyRegId(array[0],
					array[1]);
			setTotalToSubject(array[0], array[1], feedbacks);
		}
	}

	public void setTotalToSubject(String subjectCode, String facultyRegId, List<CseFeedback> feedbacks) {
		Map<Integer, Integer> hMap = new LinkedHashMap<>();
		Float average = 0.0f;
		for (int i = 0; i < 5; i++) {
			hMap.put(i, 0);
		}
		for (CseFeedback feedback : feedbacks) {
			setQuestionCounter(feedback, hMap);
			average += feedback.getAverage();
		}
		average = average / feedbacks.size();
		String total = "";
		for (int i = 0; i < 5; i++) {
			total = total + String.format("%.2f", ((float) hMap.get(i) / (float) feedbacks.size())) + " ";
		}
		Subject subject = cseSecondYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			cseSecondYearService.save(subject);
			return;
		}

		subject = cseThirdYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			cseThirdYearService.save(subject);
			return;
		}

		subject = cseFourthYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			cseFourthYearService.save(subject);
			return;
		}

	}

	private void setQuestionCounter(CseFeedback feedback, Map<Integer, Integer> hashMap) {
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
		return cseFeedbackRepository.distinctSubjects();
	}

	public List<String> distinctSubjectsAndFaculty() {
		return cseFeedbackRepository.distinctSubjectsAndFaculty();
	}
}
