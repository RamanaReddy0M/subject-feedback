package in.kvsr.admin.civil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.civil.CivilFeedback;

@Controller
public class CivilFeedbackController {
	
	@Autowired
	private CivilFeedbackService civilFeedbackService;
	
	@GetMapping("/civil/feedback") 
	public String feedbackPage(Model model) {
		model.addAttribute("title","feedback");
		model.addAttribute("feedbackActive","active"); 
		List<CivilFeedback> feedbackList = civilFeedbackService.listAll();
		model.addAttribute("feedbackList", feedbackList);
		return "civil/civilfeedback";
	}
	
	@GetMapping("/civil/feedback/clear")
	public String truncateFeedback(RedirectAttributes redirectAttributes) {
		civilFeedbackService.truncateAll();
		return "redirect:/civil/feedback";
	}
	
	@GetMapping("/civil/feedback/{field}") 
	public String feedbackPage(Model model, @PathVariable String field) {
		model.addAttribute("title","feedback");
		model.addAttribute("feedbackActive","active"); 
		List<CivilFeedback> feedbackList = civilFeedbackService.sortByField(field);
		model.addAttribute("feedbackList", feedbackList);
		return "civil/civilfeedback";
	}
	@GetMapping("/civil/faculty/averages")
	public String calavg() {
		civilFeedbackService.setAverage();
		return "redirect:/civil/2-1";
	}

}
