package in.kvsr.admin.ece;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.ece.EceFeedback;

@Controller
public class EceFeedbackController {

	@Autowired
	private EceFeedbackService eceFeedbackService;

	@GetMapping("/ece/feedback")
	public String feedbackPage(Model model) {
		model.addAttribute("title", "feedback");
		model.addAttribute("feedbackActive", "active");
		List<EceFeedback> feedbackList = eceFeedbackService.listAll();
		model.addAttribute("feedbackList", feedbackList);
		return "ece/ecefeedback";
	}

	@GetMapping("/ece/feedback/clear")
	public String truncateFeedback(RedirectAttributes redirectAttributes) {
		eceFeedbackService.truncateAll();
		return "redirect:/ece/feedback";
	}

	@GetMapping("/ece/feedback/{field}")
	public String feedbackPage(Model model, @PathVariable String field) {
		model.addAttribute("title", "feedback");
		model.addAttribute("feedbackActive", "active");
		List<EceFeedback> feedbackList = eceFeedbackService.sortByField(field);
		model.addAttribute("feedbackList", feedbackList);
		return "ece/ecefeedback";
	}

	@GetMapping("/ece/faculty/averages")
	public String calavg() {
		eceFeedbackService.setAverage();
		return "redirect:/ece/2-1";
	}

}
