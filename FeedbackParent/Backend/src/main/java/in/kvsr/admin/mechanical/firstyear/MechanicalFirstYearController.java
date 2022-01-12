package in.kvsr.admin.mechanical.firstyear;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.admin.faculty.FacultyService;
import in.kvsr.admin.hands.HandSFeedbackService;
import in.kvsr.admin.questions.QuestionService;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.mechanical.MechanicalOneOne;
import in.kvsr.common.entity.mechanical.MechanicalOneTwo;

@Controller
public class MechanicalFirstYearController {
	
	@Autowired
	private MechanicalFirstYearService mechanicalFirstYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private HandSFeedbackService handSFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/mechanical/1-1")
	public String mechanical1_1(Model model) {
		model.addAttribute("title","mechanical 1-1");
		model.addAttribute("pageTitle","First Year MECHANICAL Subjects");
		model.addAttribute("mechanicalActive","active");
		model.addAttribute("semister1","/mechanical/1-1");
		model.addAttribute("semister2","/mechanical/1-2");
		model.addAttribute("refactorId","/mechanical/1-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/mechanical/1-1/add");
		model.addAttribute("delete","/mechanical/1-1/delete/");
		model.addAttribute("update","/mechanical/1-1/update/");
		model.addAttribute("info","/mechanical/1/s-code/");
		model.addAttribute("subjects", mechanicalFirstYearService.listAllOf1_1());
		return "mechanical/subjects";
	}
	
	@GetMapping("/mechanical/1-2")
	public String mechanical1_2(Model model) {
		model.addAttribute("title","mechanical 1-2");
		model.addAttribute("pageTitle","First Year MECHANICAL Subjects");
		model.addAttribute("mechanicalActive","active");
		model.addAttribute("semister1","/mechanical/1-1");
		model.addAttribute("semister2","/mechanical/2-2");
		model.addAttribute("refactorId","/mechanical/1-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/mechanical/1-2/add");
		model.addAttribute("delete","/mechanical/1-2/delete/");
		model.addAttribute("update","/mechanical/1-2/update/");
		model.addAttribute("info","/mechanical/1/s-code/");
		model.addAttribute("subjects", mechanicalFirstYearService.listAllOf1_2());
		return "mechanical/subjects";
	}
	
	@GetMapping("/mechanical/1-1/add")
	public String mechanical1_1_add(Model model) {
		model.addAttribute("title","Mechanical 1-1");
		model.addAttribute("pageTitle","MECHANICAL 1st year 1st semister");
		model.addAttribute("mechanicalActive","active");
		model.addAttribute("subject", new MechanicalOneOne());
		model.addAttribute("action","/mechanical/1-1/save");
		return "mechanical/subjectform";
	}
	
	@GetMapping("/mechanical/1-2/add")
	public String mechanical1_2_add(Model model) {
		model.addAttribute("title","Mechanical 1-2");
		model.addAttribute("pageTitle","MECHANICAL 1st year 2nd semister");
		model.addAttribute("mechanicalActive","active");
		model.addAttribute("subject", new MechanicalOneTwo());
		model.addAttribute("action","/mechanical/1-2/save");
		return "mechanical/subjectform";
	}
	
	@RequestMapping("/mechanical/1-1/save")
	public String save1_1(@ModelAttribute("subject") MechanicalOneOne mechanicalOneOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Mechanical 1-1");
		model.addAttribute("pageTitle","MECHANICAL 1st year 1st semister");
		model.addAttribute("mechanicalActive","active");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", mechanicalOneOne);
			model.addAttribute("action", "/mechanical/1-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "mechanical/subjectform";
		}
		String response = preProcess(mechanicalOneOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", mechanicalOneOne);
			model.addAttribute("action", "/mechanical/1-1/save");
			model.addAttribute("message",response);
			return "mechanical/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(mechanicalOneOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", mechanicalOneOne);
			model.addAttribute("action", "/mechanical/1-2/save");
			model.addAttribute("message","No faculty found with id, "+mechanicalOneOne.getFacultyRegId());
			return "mechanical/subjectform";
		}
		
		
		if(mechanicalFirstYearService.saveIn1_1(mechanicalOneOne)) {
			redirectAttributes.addFlashAttribute("message",mechanicalOneOne.getSubjectName()+" added!");
			return "redirect:/mechanical/1-1";
		}else {
			model.addAttribute("subject", mechanicalOneOne);
			model.addAttribute("action", "/mechanical/1-1/save");
			model.addAttribute("message","subject might exits!");
			return "mechanical/subjectform";
		}
	}
	
	@RequestMapping("/mechanical/1-2/save")
	public String save1_2(@ModelAttribute("subject") MechanicalOneTwo mechanicalOneTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Mechanical 1-2");
		model.addAttribute("mechanicalActive","active");
		model.addAttribute("pageTitle","MECHANICAL 1st year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", mechanicalOneTwo);
			model.addAttribute("action", "/mechanical/1-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "mechanical/subjectform";
		}
		
		String response = preProcess(mechanicalOneTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", mechanicalOneTwo);
			model.addAttribute("action", "/mechanical/1-2/save");
			model.addAttribute("message",response);
			return "mechanical/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(mechanicalOneTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", mechanicalOneTwo);
			model.addAttribute("action", "/mechanical/1-2/save");
			model.addAttribute("message","No faculty found with id, "+mechanicalOneTwo.getFacultyRegId());
			return "mechanical/subjectform";
		}
		
		if(mechanicalFirstYearService.saveIn1_2(mechanicalOneTwo)) {
			redirectAttributes.addFlashAttribute("message",mechanicalOneTwo.getSubjectName()+" added!");
			return "redirect:/mechanical/1-2";
		}else {
			model.addAttribute("subject", mechanicalOneTwo);
			model.addAttribute("action", "/mechanical/1-2/save");
			model.addAttribute("message","subject might exits!");
			return "mechanical/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/mechanical/1/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = mechanicalFirstYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/mechanical/1-1";
		}
		model.addAttribute("mechanicalActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/mechanical/1-1";
		}
		model.addAttribute("faculty", faculty);
		model.addAttribute("title",subject.getSubjectName());
		model.addAttribute("pageTitle",faculty.getFirstName()+" "+faculty.getLastName());
		model.addAttribute("subjectName",subject.getSubjectName());
		List<Float> questionCounters = new ArrayList<>(); 
		for(String q: subject.getTotal().split(" ")) {
			questionCounters.add(Float.parseFloat(q));
		}
		//model.addAttribute("questionCounters",questionCounters);
		model.addAttribute("q1",questionCounters.get(0)*20);
		model.addAttribute("q2",questionCounters.get(1)*20);
		model.addAttribute("q3",questionCounters.get(2)*20);
		model.addAttribute("q4",questionCounters.get(3)*20);
		model.addAttribute("q5",questionCounters.get(4)*20);
		model.addAttribute("remarks",handSFeedbackService.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId()));
		model.addAttribute("questions",questionService.listAll());
		return "mechanical/subjectinfo";
	}
	
	@GetMapping("/mechanical/1-1/update/{id}")
	public String update1_1(@PathVariable Long id, Model model) {
		MechanicalOneOne subject = mechanicalFirstYearService.getOneOneById(id);
		model.addAttribute("pageTitle","MECHANICAL 1st year 1st semister");
		model.addAttribute("mechanicalActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/mechanical/faculty/search/1");
		model.addAttribute("action","/mechanical/1-1/save");
		return "mechanical/subjectForm";
	}
	
	@GetMapping("/mechanical/1-2/update/{id}")
	public String update1_2(@PathVariable Long id, Model model) {
		MechanicalOneTwo subject = mechanicalFirstYearService.getOneTwoById(id);
		model.addAttribute("pageTitle","MECHANICAL 1st year 2nd semister");
		model.addAttribute("mechanicalActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/mechanical/faculty/search/2");
		model.addAttribute("action","/mechanical/1-2/save");
		return "mechanical/subjectForm";
	}
	
	@GetMapping("/mechanical/1-1/delete/{id}")
	public String delete1_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		MechanicalOneOne subject = mechanicalFirstYearService.getOneOneById(id);
		if(mechanicalFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/mechanical/1-1";
	}
	
	@GetMapping("/mechanical/1-2/delete/{id}")
	public String delete1_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		MechanicalOneTwo subject = mechanicalFirstYearService.getOneTwoById(id);
		if(mechanicalFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/mechanical/1-2";
	}
	
	@GetMapping("/mechanical/1-1/refactor")
	public String refactor1_1() {
		mechanicalFirstYearService.refactor1_1();
		return "redirect:/mechanical/1-1";
	}
	
	@GetMapping("/mechanical/1-2/refactor")
	public String refactor1_2() {
		mechanicalFirstYearService.refactor1_2();
		return "redirect:/mechanical/1-2";
	}
	
	public String preProcess(Subject subject) {
		subject.setSubjectCode(subject.getSubjectCode().trim());
		subject.setSubjectName(subject.getSubjectName().trim());
		subject.setFacultyRegId(subject.getFacultyRegId().trim().toUpperCase());
		
		if(subject.getSubjectCode().isBlank() || subject.getSubjectCode() == null) {
			return "Subject code is required!";
		}else if(subject.getSubjectName().isBlank() || subject.getSubjectCode() == null) {
			return "Subject name is required!";
		}else if (subject.getFacultyRegId().isBlank() || subject.getFacultyRegId() == null) {
			return "Faculty reg.id required!";
		}
		return "ok";
	}
	
}
