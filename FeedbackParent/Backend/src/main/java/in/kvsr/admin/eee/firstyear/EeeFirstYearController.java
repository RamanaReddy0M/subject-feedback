package in.kvsr.admin.eee.firstyear;



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
import in.kvsr.common.entity.eee.EeeOneOne;
import in.kvsr.common.entity.eee.EeeOneTwo;

@Controller
public class EeeFirstYearController {
	
	@Autowired
	private EeeFirstYearService eeeFirstYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private HandSFeedbackService handSFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/eee/1-1")
	public String eee1_1(Model model) {
		model.addAttribute("title","eee 1-1");
		model.addAttribute("pageTitle","First Year EEE Subjects");
		model.addAttribute("eeeActive","active");
		model.addAttribute("semister1","/eee/1-1");
		model.addAttribute("semister2","/eee/1-2");
		model.addAttribute("refactorId","/eee/1-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/eee/1-1/add");
		model.addAttribute("delete","/eee/1-1/delete/");
		model.addAttribute("update","/eee/1-1/update/");
		model.addAttribute("info","/eee/1/s-code/");
		model.addAttribute("subjects", eeeFirstYearService.listAllOf1_1());
		return "eee/subjects";
	}
	
	@GetMapping("/eee/1-2")
	public String eee1_2(Model model) {
		model.addAttribute("title","eee 1-2");
		model.addAttribute("pageTitle","First Year EEE Subjects");
		model.addAttribute("eeeActive","active");
		model.addAttribute("semister1","/eee/1-1");
		model.addAttribute("semister2","/eee/2-2");
		model.addAttribute("refactorId","/eee/1-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/eee/1-2/add");
		model.addAttribute("delete","/eee/1-2/delete/");
		model.addAttribute("update","/eee/1-2/update/");
		model.addAttribute("info","/eee/1/s-code/");
		model.addAttribute("subjects", eeeFirstYearService.listAllOf1_2());
		return "eee/subjects";
	}
	
	@GetMapping("/eee/1-1/add")
	public String eee1_1_add(Model model) {
		model.addAttribute("title","Eee 1-1");
		model.addAttribute("pageTitle","EEE 1st year 1st semister");
		model.addAttribute("eeeActive","active");
		model.addAttribute("subject", new EeeOneOne());
		model.addAttribute("action","/eee/1-1/save");
		return "eee/subjectform";
	}
	
	@GetMapping("/eee/1-2/add")
	public String eee1_2_add(Model model) {
		model.addAttribute("title","Eee 1-2");
		model.addAttribute("pageTitle","EEE 1st year 2nd semister");
		model.addAttribute("eeeActive","active");
		model.addAttribute("subject", new EeeOneTwo());
		model.addAttribute("action","/eee/1-2/save");
		return "eee/subjectform";
	}
	
	@RequestMapping("/eee/1-1/save")
	public String save1_1(@ModelAttribute("subject") EeeOneOne eeeOneOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Eee 1-1");
		model.addAttribute("pageTitle","EEE 1st year 1st semister");
		model.addAttribute("eeeActive","active");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", eeeOneOne);
			model.addAttribute("action", "/eee/1-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "eee/subjectform";
		}
		String response = preProcess(eeeOneOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eeeOneOne);
			model.addAttribute("action", "/eee/1-1/save");
			model.addAttribute("message",response);
			return "eee/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(eeeOneOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", eeeOneOne);
			model.addAttribute("action", "/eee/1-2/save");
			model.addAttribute("message","No faculty found with id, "+eeeOneOne.getFacultyRegId());
			return "eee/subjectform";
		}
		
		
		if(eeeFirstYearService.saveIn1_1(eeeOneOne)) {
			redirectAttributes.addFlashAttribute("message",eeeOneOne.getSubjectName()+" added!");
			return "redirect:/eee/1-1";
		}else {
			model.addAttribute("subject", eeeOneOne);
			model.addAttribute("action", "/eee/1-1/save");
			model.addAttribute("message","subject might exits!");
			return "eee/subjectform";
		}
	}
	
	@RequestMapping("/eee/1-2/save")
	public String save1_2(@ModelAttribute("subject") EeeOneTwo eeeOneTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Eee 1-2");
		model.addAttribute("eeeActive","active");
		model.addAttribute("pageTitle","EEE 1st year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", eeeOneTwo);
			model.addAttribute("action", "/eee/1-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "eee/subjectform";
		}
		
		String response = preProcess(eeeOneTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eeeOneTwo);
			model.addAttribute("action", "/eee/1-2/save");
			model.addAttribute("message",response);
			return "eee/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(eeeOneTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", eeeOneTwo);
			model.addAttribute("action", "/eee/1-2/save");
			model.addAttribute("message","No faculty found with id, "+eeeOneTwo.getFacultyRegId());
			return "eee/subjectform";
		}
		
		if(eeeFirstYearService.saveIn1_2(eeeOneTwo)) {
			redirectAttributes.addFlashAttribute("message",eeeOneTwo.getSubjectName()+" added!");
			return "redirect:/eee/1-2";
		}else {
			model.addAttribute("subject", eeeOneTwo);
			model.addAttribute("action", "/eee/1-2/save");
			model.addAttribute("message","subject might exits!");
			return "eee/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/eee/1/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = eeeFirstYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/eee/1-1";
		}
		model.addAttribute("eeeActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/eee/1-1";
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
		return "eee/subjectinfo";
	}
	
	@GetMapping("/eee/1-1/update/{id}")
	public String update1_1(@PathVariable Long id, Model model) {
		EeeOneOne subject = eeeFirstYearService.getOneOneById(id);
		model.addAttribute("pageTitle","EEE 1st year 1st semister");
		model.addAttribute("eeeActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/eee/faculty/search/1");
		model.addAttribute("action","/eee/1-1/save");
		return "eee/subjectForm";
	}
	
	@GetMapping("/eee/1-2/update/{id}")
	public String update1_2(@PathVariable Long id, Model model) {
		EeeOneTwo subject = eeeFirstYearService.getOneTwoById(id);
		model.addAttribute("pageTitle","EEE 1st year 2nd semister");
		model.addAttribute("eeeActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/eee/faculty/search/2");
		model.addAttribute("action","/eee/1-2/save");
		return "eee/subjectForm";
	}
	
	@GetMapping("/eee/1-1/delete/{id}")
	public String delete1_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EeeOneOne subject = eeeFirstYearService.getOneOneById(id);
		if(eeeFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/eee/1-1";
	}
	
	@GetMapping("/eee/1-2/delete/{id}")
	public String delete1_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EeeOneTwo subject = eeeFirstYearService.getOneTwoById(id);
		if(eeeFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/eee/1-2";
	}
	
	@GetMapping("/eee/1-1/refactor")
	public String refactor1_1() {
		eeeFirstYearService.refactor1_1();
		return "redirect:/eee/1-1";
	}
	
	@GetMapping("/eee/1-2/refactor")
	public String refactor1_2() {
		eeeFirstYearService.refactor1_2();
		return "redirect:/eee/1-2";
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
