package in.kvsr.admin.civil.fourthyear;



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

import in.kvsr.admin.civil.CivilFeedbackService;
import in.kvsr.admin.faculty.FacultyService;
import in.kvsr.admin.questions.QuestionService;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.civil.CivilFourOne;
import in.kvsr.common.entity.civil.CivilFourTwo;

@Controller
public class CivilFourthYearController {
	 
	@Autowired
	private CivilFourthYearService civilFourthYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private CivilFeedbackService civilFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/civil/4-1")
	public String civil4_1(Model model) {
		model.addAttribute("title","Civil 4-1");
		model.addAttribute("pageTitle","Fourth Year CIVIL Subjects");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("semister1","/civil/4-1");
		model.addAttribute("semister2","/civil/4-2");
		model.addAttribute("refactorId","/civil/4-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/civil/4-1/add");
		model.addAttribute("delete","/civil/4-1/delete/");
		model.addAttribute("update","/civil/4-1/update/");
		model.addAttribute("info","/civil/4/s-code/");
		model.addAttribute("subjects", civilFourthYearService.listAllOf4_1());
		return "civil/subjects";
	}
	
	@GetMapping("/civil/4-2")
	public String civil4_2(Model model) {
		model.addAttribute("title","civil 4-2");
		model.addAttribute("pageTitle","Fourth Year CIVIL Subjects");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("semister1","/civil/4-1");
		model.addAttribute("semister2","/civil/4-2");
		model.addAttribute("refactorId","/civil/4-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/civil/4-2/add");
		model.addAttribute("delete","/civil/4-2/delete/");
		model.addAttribute("update","/civil/4-2/update/");
		model.addAttribute("info","/civil/4/s-code/");
		model.addAttribute("subjects", civilFourthYearService.listAllOf4_2());
		return "civil/subjects";
	}
	
	@GetMapping("/civil/4-1/add")
	public String civil4_1_add(Model model) {
		model.addAttribute("title","Civil 4-1");
		model.addAttribute("pageTitle","CIVIL 4th year 1st semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject", new CivilFourOne());
		model.addAttribute("action","/civil/4-1/save");
		return "civil/subjectform";
	}
	
	@GetMapping("/civil/4-2/add")
	public String civil4_2_add(Model model) {
		model.addAttribute("title","Civil 4-2");
		model.addAttribute("pageTitle","CIVIL 4th year 2nd semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject", new CivilFourTwo());
		model.addAttribute("action","/civil/4-2/save");
		return "civil/subjectform";
	}
	
	@RequestMapping("/civil/4-1/save")
	public String save4_1(@ModelAttribute("subject") CivilFourOne civilFourOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Civil 4-1");
		model.addAttribute("pageTitle","CIVIL 2nd year 1st semister");
		model.addAttribute("fourthYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", civilFourOne);
			model.addAttribute("action", "/civil/4-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "civil/subjectform";
		}
		String response = preProcess(civilFourOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", civilFourOne);
			model.addAttribute("action", "/civil/4-1/save");
			model.addAttribute("message",response);
			return "civil/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(civilFourOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", civilFourOne);
			model.addAttribute("action", "/civil/4-1/save");
			model.addAttribute("message","No faculty found with id, "+civilFourOne.getFacultyRegId());
			return "civil/subjectform";
		}
		
		
		if(civilFourthYearService.saveIn4_1(civilFourOne)) {
			redirectAttributes.addFlashAttribute("message",civilFourOne.getSubjectName()+" added!");
			return "redirect:/civil/4-1";
		}else {
			model.addAttribute("subject", civilFourOne);
			model.addAttribute("action", "/civil/4-1/save");
			model.addAttribute("message","subject might exits!");
			return "civil/subjectform";
		}
	}
	
	@RequestMapping("/civil/4-2/save")
	public String save4_2(@ModelAttribute("subject") CivilFourTwo civilFourTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Civil 4-2");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("pageTitle","CIVIL 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", civilFourTwo);
			model.addAttribute("action", "/civil/4-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "civil/subjectform";
		}
		
		String response = preProcess(civilFourTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", civilFourTwo);
			model.addAttribute("action", "/civil/4-2/save");
			model.addAttribute("message",response);
			return "civil/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(civilFourTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", civilFourTwo);
			model.addAttribute("action", "/civil/4-2/save");
			model.addAttribute("message","No faculty found with id, " + civilFourTwo.getFacultyRegId());
			return "civil/subjectform";
		}
		
		if(civilFourthYearService.saveIn4_2(civilFourTwo)) {
			redirectAttributes.addFlashAttribute("message",civilFourTwo.getSubjectName()+" added!");
			return "redirect:/civil/4-2";
		}else {
			model.addAttribute("subject", civilFourTwo);
			model.addAttribute("action", "/civil/4-2/save");
			model.addAttribute("message","subject might exits!");
			return "civil/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/civil/4/s-code/{subjectCode}")
	public String subjectInfo4(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = civilFourthYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/civil/4-1";
		}
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/civil/4-1";
		}
		model.addAttribute("faculty", faculty);
		model.addAttribute("title",subject.getSubjectName());
		model.addAttribute("pageTitle",faculty.getFirstName()+" "+faculty.getLastName());
		model.addAttribute("subjectName",subject.getSubjectName());
		List<Float> questionCounters = new ArrayList<>(); 
		for(String q: subject.getTotal().split(" ")) {
			questionCounters.add(Float.parseFloat(q));
		}
		model.addAttribute("questionCounters",questionCounters);
		model.addAttribute("q1",questionCounters.get(0)*20);
		model.addAttribute("q2",questionCounters.get(1)*20);
		model.addAttribute("q3",questionCounters.get(2)*20);
		model.addAttribute("q4",questionCounters.get(3)*20);
		model.addAttribute("q5",questionCounters.get(4)*20);
		model.addAttribute("remarks",civilFeedbackService.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId()));
		model.addAttribute("questions",questionService.listAll());
		return "civil/subjectinfo";
	}
	
	@GetMapping("/civil/4-1/update/{id}")
	public String update4_1(@PathVariable Long id, Model model) {
		CivilFourOne subject = civilFourthYearService.getOneById(id);
		model.addAttribute("pageTitle","CIVIL 4th year 1st semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/civil/faculty/search/1");
		model.addAttribute("action","/civil/4-1/save");
		return "civil/subjectForm";
	}
	
	@GetMapping("/civil/4-2/update/{id}")
	public String update4_2(@PathVariable Long id, Model model) {
		CivilFourTwo subject = civilFourthYearService.getTwoById(id);
		model.addAttribute("pageTitle","CIVIL  year 2nd semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/civil/faculty/search/2");
		model.addAttribute("action","/civil/4-2/save");
		return "civil/subjectForm";
	}
	
	@GetMapping("/civil/4-1/delete/{id}")
	public String delete4_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CivilFourOne subject = civilFourthYearService.getOneById(id);
		if(civilFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/civil/4-1";
	}
	
	@GetMapping("/civil/4-2/delete/{id}")
	public String delete4_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CivilFourTwo subject = civilFourthYearService.getTwoById(id);
		if(civilFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/civil/4-2";
	}
	
	@GetMapping("/civil/4-1/refactor")
	public String refactor4_1() {
		civilFourthYearService.refactor4_1();
		return "redirect:/civil/4-1";
	}
	
	@GetMapping("/civil/4-2/refactor")
	public String refactor4_2() {
		civilFourthYearService.refactor4_2();
		return "redirect:/civil/4-2";
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
