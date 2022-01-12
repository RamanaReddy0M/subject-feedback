package in.kvsr.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.Feedback;
import in.kvsr.questions.QuestionService;

@Controller
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private QuestionService questionService;

	@PostMapping("/save/feedback/{department}")
	public String saveFeedback(@PathVariable String department,@ModelAttribute("feedback") Feedback feedback, Model model,
			RedirectAttributes redirectAttributes) {
		String validationResponse = validate(feedback);
		if (validationResponse.equalsIgnoreCase("OK")) {
			setAverageToFeedback(feedback);
			if(feedbackService.save(feedback, department)) {
				redirectAttributes.addFlashAttribute("message", "your respone saved!");
				return "redirect:/student";
			}else {
				redirectAttributes.addFlashAttribute("message", "your response already saved!");
				return "redirect:/student";
			}
		} else {
			model.addAttribute("message", validationResponse);
			model.addAttribute("questionList", questionService.listAll());
			model.addAttribute("feedback", feedback);
			return "questions";
		}
	}

	public String validate(Feedback feedback) {
		feedback.setRemarks(feedback.getRemarks().trim());
		
		if (feedback.getQuestion1() == null) {
			return "Answer question 1 and try again!";
		} else if (feedback.getQuestion2() == null) {
			return "Answer question 2 and try again!";
		} else if (feedback.getQuestion3() == null) {
			return "Answer question 3 and try again!";
		} else if (feedback.getQuestion4() == null) {
			return "Answer question 4 and try again!";
		} else if (feedback.getQuestion5() == null) {
			return "Answer question 5 and try again!";
		} /*
			 * else if(feedback.getQuestion6()<=0) { return
			 * "Answer question 6 and try again!"; }else if(feedback.getQuestion7()<=0) {
			 * return "Answer question 7 and try again!"; }else
			 * if(feedback.getQuestion8()<=0) { return "Answer question 8 and try again!";
			 * }else if(feedback.getQuestion9()<=0) { return
			 * "Answer question 9 and try again!"; }else if(feedback.getQuestion10()<=0) {
			 * return "Answer question 10 and try again!"; }
			 */
		return "OK";
	}
	
	private void setAverageToFeedback(Feedback feedback) {
		Float average = ((float) (feedback.getQuestion1() + feedback.getQuestion2() + feedback.getQuestion3()
				+ feedback.getQuestion4() + feedback.getQuestion5()) / 5);
		feedback.setAverage(average);
	}

}
