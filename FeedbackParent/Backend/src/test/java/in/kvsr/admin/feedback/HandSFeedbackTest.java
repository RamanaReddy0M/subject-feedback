package in.kvsr.admin.feedback;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import in.kvsr.admin.hands.HandSFeedbackRepository;
import in.kvsr.common.entity.HandSFeedback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class HandSFeedbackTest {
	
	@Autowired
	public HandSFeedbackRepository feedbackRepository;
	
	@Test
	public void testToInsertIntoHandSFeedback() {
		List<HandSFeedback> feedbacks = new ArrayList<>();
		HandSFeedback feedback = new HandSFeedback(1L,"11M1","FN015",3,2,2,4,5, " ");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		feedback = new HandSFeedback(2L,"11M1","FN015",5,4,2,4,5, "we want some more explanation.");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		feedback = new HandSFeedback(3L,"11M1","FN015",5,4,1,2,5, "be a bit louder.");			
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		
		feedback = new HandSFeedback(1L,"11PH","FN017",1,4,3,4,5, "no worries.");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		feedback = new HandSFeedback(2L,"11PH","FN017",1,4,3,3,5, "       ");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		feedback = new HandSFeedback(3L,"11PH","FN017",1,2,3,4,5, "use some exaples.");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		
		feedback = new HandSFeedback(1L,"12M2","FN015",1,2,3,4,5, "use some exaples.");
				feedback.setAverage(feedback.calcAverage());
				feedbacks.add(feedback);
		feedback = new HandSFeedback(2L,"12M2","FN015",1,2,3,4,5, "we want some more explanation.");
				feedback.setAverage(feedback.calcAverage());
				feedbacks.add(feedback);
		feedback = new HandSFeedback(3L,"12M2","FN015",2,4,3,4,5, "be a bit louder.");
				feedback.setAverage(feedback.calcAverage());
				feedbacks.add(feedback);
		
		
		feedbackRepository.saveAll(feedbacks);
	}

}
