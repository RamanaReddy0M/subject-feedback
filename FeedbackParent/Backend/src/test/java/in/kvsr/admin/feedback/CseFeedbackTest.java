package in.kvsr.admin.feedback;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import in.kvsr.admin.cse.CseFeedbackRepository;

import in.kvsr.common.entity.cse.CseFeedback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CseFeedbackTest {
	
	@Autowired
	private CseFeedbackRepository cseFeedbackRepository;
	
	@Test
	public void testToInsertIntoCseFeedback() {
		List<CseFeedback> feedbacks = new ArrayList<>();
		CseFeedback feedback = new CseFeedback(1L,"22P&S","FN015",3,2,2,4,5, " ");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		feedback = new CseFeedback(2L,"22P&S","FN015",5,4,2,4,5, "we want some more explanation.");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		feedback = new CseFeedback(3L,"22P&S","FN015",5,4,1,2,5, "be a bit louder.");			
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		
		feedback = new CseFeedback(1L,"21DB","FN010",1,4,3,4,5, "no worries.");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		feedback = new CseFeedback(2L,"21DB","FN010",1,4,3,3,5, "       ");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		feedback = new CseFeedback(3L,"21DB","FN010",1,2,3,4,5, "use some exaples.");
		feedback.setAverage(feedback.calcAverage());
		feedbacks.add(feedback);
		
		feedback = new CseFeedback(1L,"21M3","FN022",1,2,3,4,5, "use some exaples.");
				feedback.setAverage(feedback.calcAverage());
				feedbacks.add(feedback);
		feedback = new CseFeedback(2L,"21M3","FN022",1,2,3,4,5, "we want some more explanation.");
				feedback.setAverage(feedback.calcAverage());
				feedbacks.add(feedback);
		feedback = new CseFeedback(3L,"21M3","FN022",2,4,3,4,5, "be a bit louder.");
				feedback.setAverage(feedback.calcAverage());
				feedbacks.add(feedback);
		
		
		cseFeedbackRepository.saveAll(feedbacks);
	}
	

}
