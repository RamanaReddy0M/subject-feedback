package in.kvsr.admin.questions;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.Question;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping({"/questions"})
	public String facultyPage(Model model, Authentication authentication) {
		if(!isAuthenticated()) {
			return "redirect:/login";
		}
		model.addAttribute("title","questions");
		model.addAttribute("questionsActive","active");
		List<Question> questionList = questionService.listAll();
		if(questionList.isEmpty()) {
			defaultQuestions();
		}
		model.addAttribute("questionList", questionList);
		return "questions";
	}
	
	@GetMapping("/question/update/{id}")
	public String updateQuestion(@PathVariable Long id,Model model) {
		Question tempQuestion = questionService.getById(id);
		model.addAttribute("tempQuestion", tempQuestion);
		return "questioneditor";
	}
	
	@PostMapping("/question/update")
	public String update(@ModelAttribute("tempQuestion") Question question, Model model,
			RedirectAttributes redirectAttributes) {
		
		if(questionService.save(question)) {
			redirectAttributes.addFlashAttribute("message","updated!");
			return "redirect:/questions";
		}else {
			model.addAttribute("question", question);
			redirectAttributes.addFlashAttribute("message","try again!");
			return "questioneditor";
		}
	}
	
	private  boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return false;
		}
		return true;
	}
	
	public void defaultQuestions() {
		Question question = new Question("Instructor's ability to communicate the subject matter");
		question.setId(1L);
		question.setOption1("Poor");
		question.setOption2("Fair");
		question.setOption3("Good");
		question.setOption4("Very Good");
		question.setOption5("Excellent");
		questionService.save(question);
		
		question = new Question("Instructor's accessibility and willingness to discuss course content and any problems");
		question.setId(2L);
		question.setOption1("Poor");
		question.setOption2("Fair");
		question.setOption3("Good");
		question.setOption4("Very Good");
		question.setOption5("Excellent");
		questionService.save(question);
		
		question = new Question("Helping the students in\r\n"
				+ "conducting experiments through\r\n"
				+ "set of instructions or\r\n"
				+ "demonstrations ");
		question.setId(3L);
		question.setOption1("Poor");
		question.setOption2("Fair");
		question.setOption3("Good");
		question.setOption4("Very Good");
		question.setOption5("Excellent");
		questionService.save(question);
		
		question = new Question("Makes sure that he/she is being\r\n"
				+ "understood");
		question.setId(4L);
		question.setOption1("Poor");
		question.setOption2("Fair");
		question.setOption3("Good");
		question.setOption4("Very Good");
		question.setOption5("Excellent");
		questionService.save(question);
		
		question = new Question("Helps student in providing study\r\n"
				+ "material which is not readily\r\n"
				+ "available in the text books say\r\n"
				+ "through e-resources, e-journals,\r\n"
				+ "reference books, open course\r\n"
				+ "wares etc.");
		question.setId(5L);
		question.setOption1("Poor");
		question.setOption2("Fair");
		question.setOption3("Good");
		question.setOption4("Very Good");
		question.setOption5("Excellent");
		questionService.save(question);
		
		/*
		 * question = new Question("Skill of linking subject to life\r\n" +
		 * "experience & creating interest in\r\n" + "the subject"); question.setId(6L);
		 * question.setOption1("Poor"); question.setOption2("Fair");
		 * question.setOption3("Good"); question.setOption4("Very Good");
		 * question.setOption5("Excellent"); questionService.save(question);
		 * 
		 * question = new
		 * Question("Instructor's ability to communicate the subject matter");
		 * question.setId(7L); question.setOption1("Poor"); question.setOption2("Fair");
		 * question.setOption3("Good"); question.setOption4("Very Good");
		 * question.setOption5("Excellent"); questionService.save(question);
		 * 
		 * question = new
		 * Question("Instructor's accessibility and willingness to discuss course content and any problems"
		 * ); question.setId(8L); question.setOption1("Poor");
		 * question.setOption2("Fair"); question.setOption3("Good");
		 * question.setOption4("Very Good"); question.setOption5("Excellent");
		 * questionService.save(question);
		 * 
		 * question = new Question("Helping the students in\r\n" +
		 * "conducting experiments through\r\n" + "set of instructions or\r\n" +
		 * "demonstrations "); question.setId(9L); question.setOption1("Poor");
		 * question.setOption2("Fair"); question.setOption3("Good");
		 * question.setOption4("Very Good"); question.setOption5("Excellent");
		 * questionService.save(question);
		 * 
		 * question = new Question("Makes sure that he/she is being\r\n" +
		 * "understood"); question.setId(10L); question.setOption1("Poor");
		 * question.setOption2("Fair"); question.setOption3("Good");
		 * question.setOption4("Very Good"); question.setOption5("Excellent");
		 * questionService.save(question);
		 */	}
}
