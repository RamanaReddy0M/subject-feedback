package in.kvsr.admin.cse.thirdyear;



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
import in.kvsr.common.entity.cse.CseThreeOne;
import in.kvsr.common.entity.cse.CseThreeTwo;

@Controller
public class CseThirdYearController {
	
	@Autowired
	private CseThirdYearService cseThirdYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private CseFeedbackService cseFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/cse/3-1")
	public String cse3_1(Model model) {
		model.addAttribute("title","Cse 3-1");
		model.addAttribute("pageTitle","Third Year CSE Subjects");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("semister1","/cse/3-1");
		model.addAttribute("semister2","/cse/3-2");
		model.addAttribute("refactorId","/cse/3-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/cse/3-1/add");
		model.addAttribute("delete","/cse/3-1/delete/");
		model.addAttribute("update","/cse/3-1/update/");
		model.addAttribute("info","/cse/3/s-code/");
		model.addAttribute("subjects", cseThirdYearService.listAllOf3_1());
		return "cse/subjects";
	}
	
	@GetMapping("/cse/3-2")
	public String cse3_2(Model model) {
		model.addAttribute("title","cse 3-2");
		model.addAttribute("pageTitle","Third Year CSE Subjects");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("semister1","/cse/3-1");
		model.addAttribute("semister2","/cse/3-2");
		model.addAttribute("refactorId","/cse/3-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/cse/3-2/add");
		model.addAttribute("delete","/cse/3-2/delete/");
		model.addAttribute("update","/cse/3-2/update/");
		model.addAttribute("info","/cse/3/s-code/");
		model.addAttribute("subjects", cseThirdYearService.listAllOf3_2());
		return "cse/subjects";
	}
	
	@GetMapping("/cse/3-1/add")
	public String cse3_1_add(Model model) {
		model.addAttribute("title","Cse 3-1");
		model.addAttribute("pageTitle","CSE 3rd year 1st semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject", new CseThreeOne());
		model.addAttribute("action","/cse/3-1/save");
		return "cse/subjectform";
	}
	
	@GetMapping("/cse/3-2/add")
	public String cse3_2_add(Model model) {
		model.addAttribute("title","Cse 3-2");
		model.addAttribute("pageTitle","CSE 3rd year 2nd semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject", new CseThreeTwo());
		model.addAttribute("action","/cse/3-2/save");
		return "cse/subjectform";
	}
	
	@RequestMapping("/cse/3-1/save")
	public String save3_1(@ModelAttribute("subject") CseThreeOne cseThreeOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Cse 3-1");
		model.addAttribute("pageTitle","CSE 2nd year 1st semister");
		model.addAttribute("thirdYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", cseThreeOne);
			model.addAttribute("action", "/cse/3-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "cse/subjectform";
		}
		String response = preProcess(cseThreeOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", cseThreeOne);
			model.addAttribute("action", "/cse/3-1/save");
			model.addAttribute("message",response);
			return "cse/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(cseThreeOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", cseThreeOne);
			model.addAttribute("action", "/cse/3-1/save");
			model.addAttribute("message","No faculty found with id, "+cseThreeOne.getFacultyRegId());
			return "cse/subjectform";
		}
		
		
		if(cseThirdYearService.saveIn3_1(cseThreeOne)) {
			redirectAttributes.addFlashAttribute("message",cseThreeOne.getSubjectName()+" added!");
			return "redirect:/cse/3-1";
		}else {
			model.addAttribute("subject", cseThreeOne);
			model.addAttribute("action", "/cse/3-1/save");
			model.addAttribute("message","subject might exits!");
			return "cse/subjectform";
		}
	}
	
	@RequestMapping("/cse/3-2/save")
	public String save3_2(@ModelAttribute("subject") CseThreeTwo cseThreeTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Cse 3-2");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("pageTitle","CSE 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", cseThreeTwo);
			model.addAttribute("action", "/cse/3-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "cse/subjectform";
		}
		
		String response = preProcess(cseThreeTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", cseThreeTwo);
			model.addAttribute("action", "/cse/3-2/save");
			model.addAttribute("message",response);
			return "cse/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(cseThreeTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", cseThreeTwo);
			model.addAttribute("action", "/cse/3-2/save");
			model.addAttribute("message","No faculty found with id, " + cseThreeTwo.getFacultyRegId());
			return "cse/subjectform";
		}
		
		if(cseThirdYearService.saveIn3_2(cseThreeTwo)) {
			redirectAttributes.addFlashAttribute("message",cseThreeTwo.getSubjectName()+" added!");
			return "redirect:/cse/3-2";
		}else {
			model.addAttribute("subject", cseThreeTwo);
			model.addAttribute("action", "/cse/3-2/save");
			model.addAttribute("message","subject might exits!");
			return "cse/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/cse/3/s-code/{subjectCode}")
	public String subjectInfo3(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = cseThirdYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/cse/3-1";
		}
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/cse/3-1";
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
	
	@GetMapping("/cse/3-1/update/{id}")
	public String update3_1(@PathVariable Long id, Model model) {
		CseThreeOne subject = cseThirdYearService.getOneById(id);
		model.addAttribute("pageTitle","CSE 2nd year 1st semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/cse/faculty/search/1");
		model.addAttribute("action","/cse/3-1/save");
		return "cse/subjectForm";
	}
	
	@GetMapping("/cse/3-2/update/{id}")
	public String update3_2(@PathVariable Long id, Model model) {
		CseThreeTwo subject = cseThirdYearService.getTwoById(id);
		model.addAttribute("pageTitle","CSE 2nd year 2nd semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/cse/faculty/search/2");
		model.addAttribute("action","/cse/3-2/save");
		return "cse/subjectForm";
	}
	
	@GetMapping("/cse/3-1/delete/{id}")
	public String delete3_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CseThreeOne subject = cseThirdYearService.getOneById(id);
		if(cseThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/cse/3-1";
	}
	
	@GetMapping("/cse/3-2/delete/{id}")
	public String delete3_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CseThreeTwo subject = cseThirdYearService.getTwoById(id);
		if(cseThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/cse/3-2";
	}
	
	@GetMapping("/cse/3-1/refactor")
	public String refactor3_1() {
		cseThirdYearService.refactor3_1();
		return "redirect:/cse/3-1";
	}
	
	@GetMapping("/cse/3-2/refactor")
	public String refactor3_2() {
		cseThirdYearService.refactor3_2();
		return "redirect:/cse/3-2";
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
