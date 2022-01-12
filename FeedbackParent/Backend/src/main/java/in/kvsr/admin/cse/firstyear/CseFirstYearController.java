package in.kvsr.admin.cse.firstyear;



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
import in.kvsr.common.entity.cse.CseOneOne;
import in.kvsr.common.entity.cse.CseOneTwo;

@Controller
public class CseFirstYearController {
	
	@Autowired
	private CseFirstYearService cseFirstYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private HandSFeedbackService handSFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/cse/1-1")
	public String cse1_1(Model model) {
		model.addAttribute("title","cse 1-1");
		model.addAttribute("pageTitle","First Year CSE Subjects");
		model.addAttribute("cseActive","active");
		model.addAttribute("semister1","/cse/1-1");
		model.addAttribute("semister2","/cse/1-2");
		model.addAttribute("refactorId","/cse/1-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/cse/1-1/add");
		model.addAttribute("delete","/cse/1-1/delete/");
		model.addAttribute("update","/cse/1-1/update/");
		model.addAttribute("info","/cse/1/s-code/");
		model.addAttribute("subjects", cseFirstYearService.listAllOf1_1());
		return "cse/subjects";
	}
	
	@GetMapping("/cse/1-2")
	public String cse1_2(Model model) {
		model.addAttribute("title","cse 1-2");
		model.addAttribute("pageTitle","First Year CSE Subjects");
		model.addAttribute("cseActive","active");
		model.addAttribute("semister1","/cse/1-1");
		model.addAttribute("semister2","/cse/2-2");
		model.addAttribute("refactorId","/cse/1-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/cse/1-2/add");
		model.addAttribute("delete","/cse/1-2/delete/");
		model.addAttribute("update","/cse/1-2/update/");
		model.addAttribute("info","/cse/1/s-code/");
		model.addAttribute("subjects", cseFirstYearService.listAllOf1_2());
		return "cse/subjects";
	}
	
	@GetMapping("/cse/1-1/add")
	public String cse1_1_add(Model model) {
		model.addAttribute("title","Cse 1-1");
		model.addAttribute("pageTitle","CSE 1st year 1st semister");
		model.addAttribute("cseActive","active");
		model.addAttribute("subject", new CseOneOne());
		model.addAttribute("action","/cse/1-1/save");
		return "cse/subjectform";
	}
	
	@GetMapping("/cse/1-2/add")
	public String cse1_2_add(Model model) {
		model.addAttribute("title","Cse 1-2");
		model.addAttribute("pageTitle","CSE 1st year 2nd semister");
		model.addAttribute("cseActive","active");
		model.addAttribute("subject", new CseOneTwo());
		model.addAttribute("action","/cse/1-2/save");
		return "cse/subjectform";
	}
	
	@RequestMapping("/cse/1-1/save")
	public String save1_1(@ModelAttribute("subject") CseOneOne cseOneOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Cse 1-1");
		model.addAttribute("pageTitle","CSE 1st year 1st semister");
		model.addAttribute("cseActive","active");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", cseOneOne);
			model.addAttribute("action", "/cse/1-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "cse/subjectform";
		}
		String response = preProcess(cseOneOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", cseOneOne);
			model.addAttribute("action", "/cse/1-1/save");
			model.addAttribute("message",response);
			return "cse/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(cseOneOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", cseOneOne);
			model.addAttribute("action", "/cse/1-2/save");
			model.addAttribute("message","No faculty found with id, "+cseOneOne.getFacultyRegId());
			return "cse/subjectform";
		}
		
		
		if(cseFirstYearService.saveIn1_1(cseOneOne)) {
			redirectAttributes.addFlashAttribute("message",cseOneOne.getSubjectName()+" added!");
			return "redirect:/cse/1-1";
		}else {
			model.addAttribute("subject", cseOneOne);
			model.addAttribute("action", "/cse/1-1/save");
			model.addAttribute("message","subject might exits!");
			return "cse/subjectform";
		}
	}
	
	@RequestMapping("/cse/1-2/save")
	public String save1_2(@ModelAttribute("subject") CseOneTwo cseOneTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Cse 1-2");
		model.addAttribute("cseActive","active");
		model.addAttribute("pageTitle","CSE 1st year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", cseOneTwo);
			model.addAttribute("action", "/cse/1-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "cse/subjectform";
		}
		
		String response = preProcess(cseOneTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", cseOneTwo);
			model.addAttribute("action", "/cse/1-2/save");
			model.addAttribute("message",response);
			return "cse/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(cseOneTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", cseOneTwo);
			model.addAttribute("action", "/cse/1-2/save");
			model.addAttribute("message","No faculty found with id, "+cseOneTwo.getFacultyRegId());
			return "cse/subjectform";
		}
		
		if(cseFirstYearService.saveIn1_2(cseOneTwo)) {
			redirectAttributes.addFlashAttribute("message",cseOneTwo.getSubjectName()+" added!");
			return "redirect:/cse/1-2";
		}else {
			model.addAttribute("subject", cseOneTwo);
			model.addAttribute("action", "/cse/1-2/save");
			model.addAttribute("message","subject might exits!");
			return "cse/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/cse/1/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = cseFirstYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/cse/1-1";
		}
		model.addAttribute("cseActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/cse/1-1";
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
		return "cse/subjectinfo";
	}
	
	@GetMapping("/cse/1-1/update/{id}")
	public String update1_1(@PathVariable Long id, Model model) {
		CseOneOne subject = cseFirstYearService.getOneOneById(id);
		model.addAttribute("pageTitle","CSE 1st year 1st semister");
		model.addAttribute("cseActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/cse/faculty/search/1");
		model.addAttribute("action","/cse/1-1/save");
		return "cse/subjectForm";
	}
	
	@GetMapping("/cse/1-2/update/{id}")
	public String update1_2(@PathVariable Long id, Model model) {
		CseOneTwo subject = cseFirstYearService.getOneTwoById(id);
		model.addAttribute("pageTitle","CSE 1st year 2nd semister");
		model.addAttribute("cseActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/cse/faculty/search/2");
		model.addAttribute("action","/cse/1-2/save");
		return "cse/subjectForm";
	}
	
	@GetMapping("/cse/1-1/delete/{id}")
	public String delete1_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CseOneOne subject = cseFirstYearService.getOneOneById(id);
		if(cseFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/cse/1-1";
	}
	
	@GetMapping("/cse/1-2/delete/{id}")
	public String delete1_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CseOneTwo subject = cseFirstYearService.getOneTwoById(id);
		if(cseFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/cse/1-2";
	}
	
	@GetMapping("/cse/1-1/refactor")
	public String refactor1_1() {
		cseFirstYearService.refactor1_1();
		return "redirect:/cse/1-1";
	}
	
	@GetMapping("/cse/1-2/refactor")
	public String refactor1_2() {
		cseFirstYearService.refactor1_2();
		return "redirect:/cse/1-2";
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
