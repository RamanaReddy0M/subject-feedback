package in.kvsr.admin.eee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.eee.EeeFeedback;

@Controller
public class EeeFeedbackController {
	
	@Autowired
	private EeeFeedbackService eeeFeedbackService;
	
	@GetMapping("/eee/feedback") 
	public String feedbackPage(Model model) {
		model.addAttribute("title","feedback");
		model.addAttribute("feedbackActive","active"); 
		List<EeeFeedback> feedbackList = eeeFeedbackService.listAll();
		model.addAttribute("feedbackList", feedbackList);
		return "eee/eeefeedback";
	}
	
	@GetMapping("/eee/feedback/clear")
	public String truncateFeedback(RedirectAttributes redirectAttributes) {
		eeeFeedbackService.truncateAll();
		return "redirect:/eee/feedback";
	}
	
	@GetMapping("/eee/feedback/{field}") 
	public String feedbackPage(Model model, @PathVariable String field) {
		model.addAttribute("title","feedback");
		model.addAttribute("feedbackActive","active"); 
		List<EeeFeedback> feedbackList = eeeFeedbackService.sortByField(field);
		model.addAttribute("feedbackList", feedbackList);
		return "eee/eeefeedback";
	}
	@GetMapping("/eee/faculty/averages")
	public String calavg() {
		eeeFeedbackService.setAverage();
		return "redirect:/eee/2-1";
	}

}
