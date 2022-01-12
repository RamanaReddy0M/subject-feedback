package in.kvsr.admin.hands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.HandSFeedback;

@Controller
public class HandSFeedbackController {
	
	@Autowired
	private HandSFeedbackService handSFeedbackService;
	
	@GetMapping("/hands/feedback") 
	public String feedbackPage(Model model) {
		model.addAttribute("title","feedback-h");
		model.addAttribute("feedbackActive","active"); 
		List<HandSFeedback> feedbackList = handSFeedbackService.listAll();
		model.addAttribute("feedbackList", feedbackList);
		return "handsfeedback";
	}
	
	@GetMapping("/hands/feedback/clear")
	public String truncateFeedback(RedirectAttributes redirectAttributes) {
		handSFeedbackService.truncateAll();
		return "redirect:/hands/feedback";
	}
	
	@GetMapping("/hands/feedback/{field}") 
	public String feedbackPage(Model model, @PathVariable String field) {
		model.addAttribute("title","feedback");
		model.addAttribute("feedbackActive","active"); 
		List<HandSFeedback> feedbackList = handSFeedbackService.sortByField(field);
		model.addAttribute("feedbackList", feedbackList);
		return "handsfeedback";
	}
	@GetMapping("/hands/faculty/averages")
	public String calavg() {
		handSFeedbackService.setAverage();
		return "redirect:/cse/1-1";
	}
}
