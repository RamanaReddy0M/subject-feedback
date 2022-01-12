package in.kvsr.admin.hands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.kvsr.admin.cse.firstyear.CseFirstYearService;
import in.kvsr.common.entity.HandSFeedback;
import in.kvsr.common.entity.Subject;

@Service
public class HandSFeedbackService {
	
	@Autowired
	private HandSFeedbackRepository handSFeedbackRepository;
	
	@Autowired
	private CseFirstYearService cseFirstYearService;
	
	public List<HandSFeedback> listAll() {
		return (List<HandSFeedback>) handSFeedbackRepository.findAll();
	}
	public List<HandSFeedback> sortByField(String field){
		return handSFeedbackRepository.sortByField(Sort.by(field));
	}
	
	public boolean save(HandSFeedback feedback) {
		
			try {
				feedback.setAverage(feedback.calcAverage());
				handSFeedbackRepository.save(feedback);
				return true;
			}catch (Exception e) {
				return false;
			}
	}
	
	public List<HandSFeedback> getFeedbacksBySubjectCode(String subjectCode) {
		return handSFeedbackRepository.getFeedbacksBySubjectCode(subjectCode);
	}
	
	public List<String> getRemarks(String subjectCode, String facultyRegId) {
		return handSFeedbackRepository.getRemarks(subjectCode, facultyRegId);
	}
	
	public boolean truncateAll() {
		return (handSFeedbackRepository.truncate()==0);
	}
	
	public void setAverage() {
		for(String scAndFR: distinctSubjectsAndFaculty()) {
			String[] array = scAndFR.split(",");
			List<HandSFeedback> feedbacks = handSFeedbackRepository.getFeedbacksBySubjectCodeAndFacultyRegId(array[0], array[1]);
			setTotalToSubject(array[0], array[1], feedbacks);
		}
	}
	
	public void setTotalToSubject(String subjectCode, String facultyRegId,List<HandSFeedback> feedbacks) {
		Map<Integer, Integer> hMap = new LinkedHashMap<>();
		Float average = 0.0f;
		for(int i=0;i<5;i++) {
			hMap.put(i, 0);
		}
		for(HandSFeedback feedback: feedbacks) {
			setQuestionCounter(feedback, hMap);
			average+=feedback.getAverage();
		}
		average = average / feedbacks.size();
		String total="";
		for(int i=0;i<5;i++) {
			total=total+String.format("%.2f", ((float)hMap.get(i)/(float)feedbacks.size()))+" ";
		}
		Subject subject = cseFirstYearService.findBySubjectCode(subjectCode);
		if(subject != null && subject.getFacultyRegId().equalsIgnoreCase(facultyRegId)) {
			subject.setAverage(average);
			subject.setTotal(total);
			cseFirstYearService.save(subject);
		}
	}
	
	private void setQuestionCounter(HandSFeedback feedback,Map<Integer, Integer> hashMap) {
		List<Integer> feedbackList = new ArrayList<>();
		feedbackList.add(feedback.getQuestion1());
		feedbackList.add(feedback.getQuestion2());
		feedbackList.add(feedback.getQuestion3());
		feedbackList.add(feedback.getQuestion4());
		feedbackList.add(feedback.getQuestion5());
		for(int i=0;i<feedbackList.size();i++) {
			hashMap.put(i, hashMap.get(i)+feedbackList.get(i));
		}
	}
	
	public List<String> distinctSubjects() {
		return handSFeedbackRepository.distinctSubjects();
	}
	
	public List<String> distinctSubjectsAndFaculty(){
		return handSFeedbackRepository.distinctSubjectsAndFaculty();
	}
	

}
