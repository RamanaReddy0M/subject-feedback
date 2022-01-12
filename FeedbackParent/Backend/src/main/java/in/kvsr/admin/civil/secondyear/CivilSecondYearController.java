package in.kvsr.admin.civil.secondyear;



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
import in.kvsr.common.entity.civil.CivilTwoOne;
import in.kvsr.common.entity.civil.CivilTwoTwo;

@Controller
public class CivilSecondYearController {
	
	@Autowired
	private CivilSecondYearService civilSecondYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private CivilFeedbackService civilFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/civil/2-1")
	public String civil2_1(Model model) {
		model.addAttribute("title","Civil 2-1");
		model.addAttribute("pageTitle","Second Year CIVIL Subjects");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("semister1","/civil/2-1");
		model.addAttribute("semister2","/civil/2-2");
		model.addAttribute("refactorId","/civil/2-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/civil/2-1/add");
		model.addAttribute("delete","/civil/2-1/delete/");
		model.addAttribute("update","/civil/2-1/update/");
		model.addAttribute("info","/civil/2/s-code/");
		model.addAttribute("subjects", civilSecondYearService.listAllOf2_1());
		return "civil/subjects";
	}
	
	@GetMapping("/civil/2-2")
	public String civil22(Model model) {
		model.addAttribute("title","civil 2-2");
		model.addAttribute("pageTitle","Second Year CIVIL Subjects");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("semister1","/civil/2-1");
		model.addAttribute("semister2","/civil/2-2");
		model.addAttribute("refactorId","/civil/2-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/civil/2-2/add");
		model.addAttribute("delete","/civil/2-2/delete/");
		model.addAttribute("update","/civil/2-2/update/");
		model.addAttribute("info","/civil/2/s-code/");
		model.addAttribute("subjects", civilSecondYearService.listAllOf2_2());
		return "civil/subjects";
	}
	
	@GetMapping("/civil/2-1/add")
	public String civil2_1_add(Model model) {
		model.addAttribute("title","Civil 2-1");
		model.addAttribute("pageTitle","CIVIL 2nd year 1st semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject", new CivilTwoOne());
		model.addAttribute("action","/civil/2-1/save");
		return "civil/subjectform";
	}
	
	@GetMapping("/civil/2-2/add")
	public String civil2_2_add(Model model) {
		model.addAttribute("title","Civil 2-2");
		model.addAttribute("pageTitle","CIVIL 2nd year 2nd semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject", new CivilTwoTwo());
		model.addAttribute("action","/civil/2-2/save");
		return "civil/subjectform";
	}
	
	@RequestMapping("/civil/2-1/save")
	public String save2_1(@ModelAttribute("subject") CivilTwoOne civilTwoOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Civil 2-1");
		model.addAttribute("pageTitle","CIVIL 2nd year 1st semister");
		model.addAttribute("secondYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", civilTwoOne);
			model.addAttribute("action", "/civil/2-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "civil/subjectform";
		}
		String response = preProcess(civilTwoOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", civilTwoOne);
			model.addAttribute("action", "/civil/2-1/save");
			model.addAttribute("message",response);
			return "civil/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(civilTwoOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", civilTwoOne);
			model.addAttribute("action", "/civil/2-1/save");
			model.addAttribute("message","No faculty found with id, "+civilTwoOne.getFacultyRegId());
			return "civil/subjectform";
		}
		
		
		if(civilSecondYearService.saveIn2_1(civilTwoOne)) {
			redirectAttributes.addFlashAttribute("message",civilTwoOne.getSubjectName()+" added!");
			return "redirect:/civil/2-1";
		}else {
			model.addAttribute("subject", civilTwoOne);
			model.addAttribute("action", "/civil/2-1/save");
			model.addAttribute("message","subject might exits!");
			return "civil/subjectform";
		}
	}
	
	@RequestMapping("/civil/2-2/save")
	public String save2_2(@ModelAttribute("subject") CivilTwoTwo civilTwoTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Civil 2-2");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("pageTitle","CIVIL 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", civilTwoTwo);
			model.addAttribute("action", "/civil/2-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "civil/subjectform";
		}
		
		String response = preProcess(civilTwoTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", civilTwoTwo);
			model.addAttribute("action", "/civil/2-2/save");
			model.addAttribute("message",response);
			return "civil/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(civilTwoTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", civilTwoTwo);
			model.addAttribute("action", "/civil/2-2/save");
			model.addAttribute("message","No faculty found with id, " + civilTwoTwo.getFacultyRegId());
			return "civil/subjectform";
		}
		
		if(civilSecondYearService.saveIn2_2(civilTwoTwo)) {
			redirectAttributes.addFlashAttribute("message",civilTwoTwo.getSubjectName()+" added!");
			return "redirect:/civil/2-2";
		}else {
			model.addAttribute("subject", civilTwoTwo);
			model.addAttribute("action", "/civil/2-2/save");
			model.addAttribute("message","subject might exits!");
			return "civil/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/civil/2/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = civilSecondYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/civil/2-1";
		}
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/civil/2-1";
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
	
	@GetMapping("/civil/2-1/update/{id}")
	public String update2_1(@PathVariable Long id, Model model) {
		CivilTwoOne subject = civilSecondYearService.getOneById(id);
		model.addAttribute("pageTitle","CIVIL 2nd year 1st semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/civil/faculty/search/1");
		model.addAttribute("action","/civil/2-1/save");
		return "civil/subjectForm";
	}
	
	@GetMapping("/civil/2-2/update/{id}")
	public String update2_2(@PathVariable Long id, Model model) {
		CivilTwoTwo subject = civilSecondYearService.getTwoById(id);
		model.addAttribute("pageTitle","CIVIL 2nd year 2nd semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/civil/faculty/search/2");
		model.addAttribute("action","/civil/2-2/save");
		return "civil/subjectForm";
	}
	
	@GetMapping("/civil/2-1/delete/{id}")
	public String delete2_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CivilTwoOne subject = civilSecondYearService.getOneById(id);
		if(civilSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/civil/2-1";
	}
	
	@GetMapping("/civil/2-2/delete/{id}")
	public String delete2_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CivilTwoTwo subject = civilSecondYearService.getTwoById(id);
		if(civilSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/civil/2-2";
	}
	
	@GetMapping("/civil/2-1/refactor")
	public String refactor2_1() {
		civilSecondYearService.refactor2_1();
		return "redirect:/civil/2-1";
	}
	
	@GetMapping("/civil/2-2/refactor")
	public String refactor2_2() {
		civilSecondYearService.refactor2_2();
		return "redirect:/civil/2-2";
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
