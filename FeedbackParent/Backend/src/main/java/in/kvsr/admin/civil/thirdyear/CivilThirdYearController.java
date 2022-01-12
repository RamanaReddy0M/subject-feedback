package in.kvsr.admin.civil.thirdyear;



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
import in.kvsr.common.entity.civil.CivilThreeOne;
import in.kvsr.common.entity.civil.CivilThreeTwo;

@Controller
public class CivilThirdYearController {
	
	@Autowired
	private CivilThirdYearService civilThirdYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private CivilFeedbackService civilFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/civil/3-1")
	public String civil3_1(Model model) {
		model.addAttribute("title","Civil 3-1");
		model.addAttribute("pageTitle","Third Year CIVIL Subjects");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("semister1","/civil/3-1");
		model.addAttribute("semister2","/civil/3-2");
		model.addAttribute("refactorId","/civil/3-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/civil/3-1/add");
		model.addAttribute("delete","/civil/3-1/delete/");
		model.addAttribute("update","/civil/3-1/update/");
		model.addAttribute("info","/civil/3/s-code/");
		model.addAttribute("subjects", civilThirdYearService.listAllOf3_1());
		return "civil/subjects";
	}
	
	@GetMapping("/civil/3-2")
	public String civil3_2(Model model) {
		model.addAttribute("title","civil 3-2");
		model.addAttribute("pageTitle","Third Year CIVIL Subjects");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("semister1","/civil/3-1");
		model.addAttribute("semister2","/civil/3-2");
		model.addAttribute("refactorId","/civil/3-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/civil/3-2/add");
		model.addAttribute("delete","/civil/3-2/delete/");
		model.addAttribute("update","/civil/3-2/update/");
		model.addAttribute("info","/civil/3/s-code/");
		model.addAttribute("subjects", civilThirdYearService.listAllOf3_2());
		return "civil/subjects";
	}
	
	@GetMapping("/civil/3-1/add")
	public String civil3_1_add(Model model) {
		model.addAttribute("title","Civil 3-1");
		model.addAttribute("pageTitle","CIVIL 3rd year 1st semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject", new CivilThreeOne());
		model.addAttribute("action","/civil/3-1/save");
		return "civil/subjectform";
	}
	
	@GetMapping("/civil/3-2/add")
	public String civil3_2_add(Model model) {
		model.addAttribute("title","Civil 3-2");
		model.addAttribute("pageTitle","CIVIL 3rd year 2nd semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject", new CivilThreeTwo());
		model.addAttribute("action","/civil/3-2/save");
		return "civil/subjectform";
	}
	
	@RequestMapping("/civil/3-1/save")
	public String save3_1(@ModelAttribute("subject") CivilThreeOne civilThreeOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Civil 3-1");
		model.addAttribute("pageTitle","CIVIL 2nd year 1st semister");
		model.addAttribute("thirdYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", civilThreeOne);
			model.addAttribute("action", "/civil/3-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "civil/subjectform";
		}
		String response = preProcess(civilThreeOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", civilThreeOne);
			model.addAttribute("action", "/civil/3-1/save");
			model.addAttribute("message",response);
			return "civil/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(civilThreeOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", civilThreeOne);
			model.addAttribute("action", "/civil/3-1/save");
			model.addAttribute("message","No faculty found with id, "+civilThreeOne.getFacultyRegId());
			return "civil/subjectform";
		}
		
		
		if(civilThirdYearService.saveIn3_1(civilThreeOne)) {
			redirectAttributes.addFlashAttribute("message",civilThreeOne.getSubjectName()+" added!");
			return "redirect:/civil/3-1";
		}else {
			model.addAttribute("subject", civilThreeOne);
			model.addAttribute("action", "/civil/3-1/save");
			model.addAttribute("message","subject might exits!");
			return "civil/subjectform";
		}
	}
	
	@RequestMapping("/civil/3-2/save")
	public String save3_2(@ModelAttribute("subject") CivilThreeTwo civilThreeTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Civil 3-2");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("pageTitle","CIVIL 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", civilThreeTwo);
			model.addAttribute("action", "/civil/3-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "civil/subjectform";
		}
		
		String response = preProcess(civilThreeTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", civilThreeTwo);
			model.addAttribute("action", "/civil/3-2/save");
			model.addAttribute("message",response);
			return "civil/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(civilThreeTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", civilThreeTwo);
			model.addAttribute("action", "/civil/3-2/save");
			model.addAttribute("message","No faculty found with id, " + civilThreeTwo.getFacultyRegId());
			return "civil/subjectform";
		}
		
		if(civilThirdYearService.saveIn3_2(civilThreeTwo)) {
			redirectAttributes.addFlashAttribute("message",civilThreeTwo.getSubjectName()+" added!");
			return "redirect:/civil/3-2";
		}else {
			model.addAttribute("subject", civilThreeTwo);
			model.addAttribute("action", "/civil/3-2/save");
			model.addAttribute("message","subject might exits!");
			return "civil/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/civil/3/s-code/{subjectCode}")
	public String subjectInfo3(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = civilThirdYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/civil/3-1";
		}
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/civil/3-1";
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
	
	@GetMapping("/civil/3-1/update/{id}")
	public String update3_1(@PathVariable Long id, Model model) {
		CivilThreeOne subject = civilThirdYearService.getOneById(id);
		model.addAttribute("pageTitle","CIVIL 2nd year 1st semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/civil/faculty/search/1");
		model.addAttribute("action","/civil/3-1/save");
		return "civil/subjectForm";
	}
	
	@GetMapping("/civil/3-2/update/{id}")
	public String update3_2(@PathVariable Long id, Model model) {
		CivilThreeTwo subject = civilThirdYearService.getTwoById(id);
		model.addAttribute("pageTitle","CIVIL 2nd year 2nd semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/civil/faculty/search/2");
		model.addAttribute("action","/civil/3-2/save");
		return "civil/subjectForm";
	}
	
	@GetMapping("/civil/3-1/delete/{id}")
	public String delete3_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CivilThreeOne subject = civilThirdYearService.getOneById(id);
		if(civilThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/civil/3-1";
	}
	
	@GetMapping("/civil/3-2/delete/{id}")
	public String delete3_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CivilThreeTwo subject = civilThirdYearService.getTwoById(id);
		if(civilThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/civil/3-2";
	}
	
	@GetMapping("/civil/3-1/refactor")
	public String refactor3_1() {
		civilThirdYearService.refactor3_1();
		return "redirect:/civil/3-1";
	}
	
	@GetMapping("/civil/3-2/refactor")
	public String refactor3_2() {
		civilThirdYearService.refactor3_2();
		return "redirect:/civil/3-2";
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
