package in.kvsr.admin.cse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.cse.CseFeedback;

@Controller
public class CseFeedbackController {
	
	@Autowired
	private CseFeedbackService cseFeedbackService;
	
	@GetMapping("/cse/feedback") 
	public String feedbackPage(Model model) {
		model.addAttribute("title","feedback");
		model.addAttribute("feedbackActive","active"); 
		List<CseFeedback> feedbackList = cseFeedbackService.listAll();
		model.addAttribute("feedbackList", feedbackList);
		return "cse/csefeedback";
	}
	
	@GetMapping("/cse/feedback/clear")
	public String truncateFeedback(RedirectAttributes redirectAttributes) {
		cseFeedbackService.truncateAll();
		return "redirect:/cse/feedback";
	}
	
	@GetMapping("/cse/feedback/{field}") 
	public String feedbackPage(Model model, @PathVariable String field) {
		model.addAttribute("title","feedback");
		model.addAttribute("feedbackActive","active"); 
		List<CseFeedback> feedbackList = cseFeedbackService.sortByField(field);
		model.addAttribute("feedbackList", feedbackList);
		return "cse/csefeedback";
	}
	@GetMapping("/cse/faculty/averages")
	public String calavg() {
		cseFeedbackService.setAverage();
		return "redirect:/cse/2-1";
	}

}
