package in.kvsr.admin.eee;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.kvsr.admin.eee.fourthyear.EeeFourthYearService;
import in.kvsr.admin.eee.secondyear.EeeSecondYearService;
import in.kvsr.admin.eee.thirdyear.EeeThirdYearService;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.eee.EeeFeedback;

@Service
public class EeeFeedbackService {

	@Autowired
	private EeeFeedbackRepository eeeFeedbackRepository;

	@Autowired
	private EeeSecondYearService eeeSecondYearService;

	@Autowired
	private EeeThirdYearService eeeThirdYearService;

	@Autowired
	private EeeFourthYearService eeeFourthYearService;

	public List<EeeFeedback> listAll() {
		return (List<EeeFeedback>) eeeFeedbackRepository.findAll();
	}

	public List<EeeFeedback> sortByField(String field) {
		return eeeFeedbackRepository.sortByField(Sort.by(field));
	}

	public boolean save(EeeFeedback feedback) {

		try {
			feedback.setAverage(feedback.calcAverage());
			eeeFeedbackRepository.save(feedback);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<EeeFeedback> getFeedbacksBySubjectCode(String subjectCode) {
		return eeeFeedbackRepository.getFeedbacksBySubjectCode(subjectCode);
	}

	public List<String> getRemarks(String subjectCode, String facultyRegId) {
		return eeeFeedbackRepository.getRemarks(subjectCode, facultyRegId);
	}

	public boolean truncateAll() {
		return (eeeFeedbackRepository.truncate() == 0);
	}

	public void setAverage() {
		for (String scAndFR : distinctSubjectsAndFaculty()) {
			String[] array = scAndFR.split(",");
			List<EeeFeedback> feedbacks = eeeFeedbackRepository.getFeedbacksBySubjectCodeAndFacultyRegId(array[0],
					array[1]);
			setTotalToSubject(array[0], array[1], feedbacks);
		}
	}

	public void setTotalToSubject(String subjectCode, String facultyRegId, List<EeeFeedback> feedbacks) {
		Map<Integer, Integer> hMap = new LinkedHashMap<>();
		Float average = 0.0f;
		for (int i = 0; i < 5; i++) {
			hMap.put(i, 0);
		}
		for (EeeFeedback feedback : feedbacks) {
			setQuestionCounter(feedback, hMap);
			average += feedback.getAverage();
		}
		average = average / feedbacks.size();
		String total = "";
		for (int i = 0; i < 5; i++) {
			total = total + String.format("%.2f", ((float) hMap.get(i) / (float) feedbacks.size())) + " ";
		}
		Subject subject = eeeSecondYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			eeeSecondYearService.save(subject);
			return;
		}

		subject = eeeThirdYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			eeeThirdYearService.save(subject);
			return;
		}

		subject = eeeFourthYearService.findBySubjectCode(subjectCode);
		if (subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			eeeFourthYearService.save(subject);
			return;
		}

	}

	private void setQuestionCounter(EeeFeedback feedback, Map<Integer, Integer> hashMap) {
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
		return eeeFeedbackRepository.distinctSubjects();
	}

	public List<String> distinctSubjectsAndFaculty() {
		return eeeFeedbackRepository.distinctSubjectsAndFaculty();
	}
}
