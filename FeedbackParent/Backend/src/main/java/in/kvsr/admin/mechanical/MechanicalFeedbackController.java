package in.kvsr.admin.mechanical;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.mechanical.MechanicalFeedback;

@Controller
public class MechanicalFeedbackController {
	
	@Autowired
	private MechanicalFeedbackService mechanicalFeedbackService;
	
	@GetMapping("/mechanical/feedback") 
	public String feedbackPage(Model model) {
		model.addAttribute("title","feedback");
		model.addAttribute("feedbackActive","active"); 
		List<MechanicalFeedback> feedbackList = mechanicalFeedbackService.listAll();
		model.addAttribute("feedbackList", feedbackList);
		return "mechanical/mechanicalfeedback";
	}
	
	@GetMapping("/mechanical/feedback/clear")
	public String truncateFeedback(RedirectAttributes redirectAttributes) {
		mechanicalFeedbackService.truncateAll();
		return "redirect:/mechanical/feedback";
	}
	
	@GetMapping("/mechanical/feedback/{field}") 
	public String feedbackPage(Model model, @PathVariable String field) {
		model.addAttribute("title","feedback");
		model.addAttribute("feedbackActive","active"); 
		List<MechanicalFeedback> feedbackList = mechanicalFeedbackService.sortByField(field);
		model.addAttribute("feedbackList", feedbackList);
		return "mechanical/mechanicalfeedback";
	}
	@GetMapping("/mechanical/faculty/averages")
	public String calavg() {
		mechanicalFeedbackService.setAverage();
		return "redirect:/mechanical/2-1";
	}

}
