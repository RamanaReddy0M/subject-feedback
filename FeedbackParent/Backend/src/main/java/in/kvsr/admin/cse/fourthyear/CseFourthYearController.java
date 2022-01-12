package in.kvsr.admin.cse.fourthyear;



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

import in.kvsr.admin.cse.CseFeedbackService;
import in.kvsr.admin.faculty.FacultyService;
import in.kvsr.admin.questions.QuestionService;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.cse.CseFourOne;
import in.kvsr.common.entity.cse.CseFourTwo;

@Controller
public class CseFourthYearController {
	
	@Autowired
	private CseFourthYearService cseFourthYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private CseFeedbackService cseFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/cse/4-1")
	public String cse4_1(Model model) {
		model.addAttribute("title","Cse 4-1");
		model.addAttribute("pageTitle","Fourth Year CSE Subjects");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("semister1","/cse/4-1");
		model.addAttribute("semister2","/cse/4-2");
		model.addAttribute("refactorId","/cse/4-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/cse/4-1/add");
		model.addAttribute("delete","/cse/4-1/delete/");
		model.addAttribute("update","/cse/4-1/update/");
		model.addAttribute("info","/cse/4/s-code/");
		model.addAttribute("subjects", cseFourthYearService.listAllOf4_1());
		return "cse/subjects";
	}
	
	@GetMapping("/cse/4-2")
	public String cse4_2(Model model) {
		model.addAttribute("title","cse 4-2");
		model.addAttribute("pageTitle","Fourth Year CSE Subjects");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("semister1","/cse/4-1");
		model.addAttribute("semister2","/cse/4-2");
		model.addAttribute("refactorId","/cse/4-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/cse/4-2/add");
		model.addAttribute("delete","/cse/4-2/delete/");
		model.addAttribute("update","/cse/4-2/update/");
		model.addAttribute("info","/cse/4/s-code/");
		model.addAttribute("subjects", cseFourthYearService.listAllOf4_2());
		return "cse/subjects";
	}
	
	@GetMapping("/cse/4-1/add")
	public String cse4_1_add(Model model) {
		model.addAttribute("title","Cse 4-1");
		model.addAttribute("pageTitle","CSE 4th year 1st semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject", new CseFourOne());
		model.addAttribute("action","/cse/4-1/save");
		return "cse/subjectform";
	}
	
	@GetMapping("/cse/4-2/add")
	public String cse4_2_add(Model model) {
		model.addAttribute("title","Cse 4-2");
		model.addAttribute("pageTitle","CSE 4th year 2nd semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject", new CseFourTwo());
		model.addAttribute("action","/cse/4-2/save");
		return "cse/subjectform";
	}
	
	@RequestMapping("/cse/4-1/save")
	public String save4_1(@ModelAttribute("subject") CseFourOne cseFourOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Cse 4-1");
		model.addAttribute("pageTitle","CSE 2nd year 1st semister");
		model.addAttribute("fourthYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", cseFourOne);
			model.addAttribute("action", "/cse/4-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "cse/subjectform";
		}
		String response = preProcess(cseFourOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", cseFourOne);
			model.addAttribute("action", "/cse/4-1/save");
			model.addAttribute("message",response);
			return "cse/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(cseFourOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", cseFourOne);
			model.addAttribute("action", "/cse/4-1/save");
			model.addAttribute("message","No faculty found with id, "+cseFourOne.getFacultyRegId());
			return "cse/subjectform";
		}
		
		
		if(cseFourthYearService.saveIn4_1(cseFourOne)) {
			redirectAttributes.addFlashAttribute("message",cseFourOne.getSubjectName()+" added!");
			return "redirect:/cse/4-1";
		}else {
			model.addAttribute("subject", cseFourOne);
			model.addAttribute("action", "/cse/4-1/save");
			model.addAttribute("message","subject might exits!");
			return "cse/subjectform";
		}
	}
	
	@RequestMapping("/cse/4-2/save")
	public String save4_2(@ModelAttribute("subject") CseFourTwo cseFourTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Cse 4-2");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("pageTitle","CSE 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", cseFourTwo);
			model.addAttribute("action", "/cse/4-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "cse/subjectform";
		}
		
		String response = preProcess(cseFourTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", cseFourTwo);
			model.addAttribute("action", "/cse/4-2/save");
			model.addAttribute("message",response);
			return "cse/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(cseFourTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", cseFourTwo);
			model.addAttribute("action", "/cse/4-2/save");
			model.addAttribute("message","No faculty found with id, " + cseFourTwo.getFacultyRegId());
			return "cse/subjectform";
		}
		
		if(cseFourthYearService.saveIn4_2(cseFourTwo)) {
			redirectAttributes.addFlashAttribute("message",cseFourTwo.getSubjectName()+" added!");
			return "redirect:/cse/4-2";
		}else {
			model.addAttribute("subject", cseFourTwo);
			model.addAttribute("action", "/cse/4-2/save");
			model.addAttribute("message","subject might exits!");
			return "cse/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/cse/4/s-code/{subjectCode}")
	public String subjectInfo4(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = cseFourthYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/cse/4-1";
		}
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/cse/4-1";
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
		model.addAttribute("remarks",cseFeedbackService.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId()));
		model.addAttribute("questions",questionService.listAll());
		return "cse/subjectinfo";
	}
	
	@GetMapping("/cse/4-1/update/{id}")
	public String update4_1(@PathVariable Long id, Model model) {
		CseFourOne subject = cseFourthYearService.getOneById(id);
		model.addAttribute("pageTitle","CSE 4th year 1st semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/cse/faculty/search/1");
		model.addAttribute("action","/cse/4-1/save");
		return "cse/subjectForm";
	}
	
	@GetMapping("/cse/4-2/update/{id}")
	public String update4_2(@PathVariable Long id, Model model) {
		CseFourTwo subject = cseFourthYearService.getTwoById(id);
		model.addAttribute("pageTitle","CSE  year 2nd semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/cse/faculty/search/2");
		model.addAttribute("action","/cse/4-2/save");
		return "cse/subjectForm";
	}
	
	@GetMapping("/cse/4-1/delete/{id}")
	public String delete4_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CseFourOne subject = cseFourthYearService.getOneById(id);
		if(cseFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/cse/4-1";
	}
	
	@GetMapping("/cse/4-2/delete/{id}")
	public String delete4_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CseFourTwo subject = cseFourthYearService.getTwoById(id);
		if(cseFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/cse/4-2";
	}
	
	@GetMapping("/cse/4-1/refactor")
	public String refactor4_1() {
		cseFourthYearService.refactor4_1();
		return "redirect:/cse/4-1";
	}
	
	@GetMapping("/cse/4-2/refactor")
	public String refactor4_2() {
		cseFourthYearService.refactor4_2();
		return "redirect:/cse/4-2";
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
