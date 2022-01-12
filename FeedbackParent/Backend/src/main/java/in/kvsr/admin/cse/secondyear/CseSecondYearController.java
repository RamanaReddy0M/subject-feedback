package in.kvsr.admin.cse.secondyear;



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
import in.kvsr.common.entity.cse.CseTwoOne;
import in.kvsr.common.entity.cse.CseTwoTwo;

@Controller
public class CseSecondYearController {
	
	@Autowired
	private CseSecondYearService cseSecondYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private CseFeedbackService cseFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/cse/2-1")
	public String cse2_1(Model model) {
		model.addAttribute("title","Cse 2-1");
		model.addAttribute("pageTitle","Second Year CSE Subjects");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("semister1","/cse/2-1");
		model.addAttribute("semister2","/cse/2-2");
		model.addAttribute("refactorId","/cse/2-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/cse/2-1/add");
		model.addAttribute("delete","/cse/2-1/delete/");
		model.addAttribute("update","/cse/2-1/update/");
		model.addAttribute("info","/cse/2/s-code/");
		model.addAttribute("subjects", cseSecondYearService.listAllOf2_1());
		return "cse/subjects";
	}
	
	@GetMapping("/cse/2-2")
	public String cse22(Model model) {
		model.addAttribute("title","cse 2-2");
		model.addAttribute("pageTitle","Second Year CSE Subjects");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("semister1","/cse/2-1");
		model.addAttribute("semister2","/cse/2-2");
		model.addAttribute("refactorId","/cse/2-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/cse/2-2/add");
		model.addAttribute("delete","/cse/2-2/delete/");
		model.addAttribute("update","/cse/2-2/update/");
		model.addAttribute("info","/cse/2/s-code/");
		model.addAttribute("subjects", cseSecondYearService.listAllOf2_2());
		return "cse/subjects";
	}
	
	@GetMapping("/cse/2-1/add")
	public String cse2_1_add(Model model) {
		model.addAttribute("title","Cse 2-1");
		model.addAttribute("pageTitle","CSE 2nd year 1st semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject", new CseTwoOne());
		model.addAttribute("action","/cse/2-1/save");
		return "cse/subjectform";
	}
	
	@GetMapping("/cse/2-2/add")
	public String cse2_2_add(Model model) {
		model.addAttribute("title","Cse 2-2");
		model.addAttribute("pageTitle","CSE 2nd year 2nd semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject", new CseTwoTwo());
		model.addAttribute("action","/cse/2-2/save");
		return "cse/subjectform";
	}
	
	@RequestMapping("/cse/2-1/save")
	public String save2_1(@ModelAttribute("subject") CseTwoOne cseTwoOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Cse 2-1");
		model.addAttribute("pageTitle","CSE 2nd year 1st semister");
		model.addAttribute("secondYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", cseTwoOne);
			model.addAttribute("action", "/cse/2-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "cse/subjectform";
		}
		String response = preProcess(cseTwoOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", cseTwoOne);
			model.addAttribute("action", "/cse/2-1/save");
			model.addAttribute("message",response);
			return "cse/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(cseTwoOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", cseTwoOne);
			model.addAttribute("action", "/cse/2-1/save");
			model.addAttribute("message","No faculty found with id, "+cseTwoOne.getFacultyRegId());
			return "cse/subjectform";
		}
		
		
		if(cseSecondYearService.saveIn2_1(cseTwoOne)) {
			redirectAttributes.addFlashAttribute("message",cseTwoOne.getSubjectName()+" added!");
			return "redirect:/cse/2-1";
		}else {
			model.addAttribute("subject", cseTwoOne);
			model.addAttribute("action", "/cse/2-1/save");
			model.addAttribute("message","subject might exits!");
			return "cse/subjectform";
		}
	}
	
	@RequestMapping("/cse/2-2/save")
	public String save2_2(@ModelAttribute("subject") CseTwoTwo cseTwoTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Cse 2-2");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("pageTitle","CSE 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", cseTwoTwo);
			model.addAttribute("action", "/cse/2-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "cse/subjectform";
		}
		
		String response = preProcess(cseTwoTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", cseTwoTwo);
			model.addAttribute("action", "/cse/2-2/save");
			model.addAttribute("message",response);
			return "cse/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(cseTwoTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", cseTwoTwo);
			model.addAttribute("action", "/cse/2-2/save");
			model.addAttribute("message","No faculty found with id, " + cseTwoTwo.getFacultyRegId());
			return "cse/subjectform";
		}
		
		if(cseSecondYearService.saveIn2_2(cseTwoTwo)) {
			redirectAttributes.addFlashAttribute("message",cseTwoTwo.getSubjectName()+" added!");
			return "redirect:/cse/2-2";
		}else {
			model.addAttribute("subject", cseTwoTwo);
			model.addAttribute("action", "/cse/2-2/save");
			model.addAttribute("message","subject might exits!");
			return "cse/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/cse/2/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = cseSecondYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/cse/2-1";
		}
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/cse/2-1";
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
	
	@GetMapping("/cse/2-1/update/{id}")
	public String update2_1(@PathVariable Long id, Model model) {
		CseTwoOne subject = cseSecondYearService.getOneById(id);
		model.addAttribute("pageTitle","CSE 2nd year 1st semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/cse/faculty/search/1");
		model.addAttribute("action","/cse/2-1/save");
		return "cse/subjectForm";
	}
	
	@GetMapping("/cse/2-2/update/{id}")
	public String update2_2(@PathVariable Long id, Model model) {
		CseTwoTwo subject = cseSecondYearService.getTwoById(id);
		model.addAttribute("pageTitle","CSE 2nd year 2nd semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/cse/faculty/search/2");
		model.addAttribute("action","/cse/2-2/save");
		return "cse/subjectForm";
	}
	
	@GetMapping("/cse/2-1/delete/{id}")
	public String delete2_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CseTwoOne subject = cseSecondYearService.getOneById(id);
		if(cseSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/cse/2-1";
	}
	
	@GetMapping("/cse/2-2/delete/{id}")
	public String delete2_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CseTwoTwo subject = cseSecondYearService.getTwoById(id);
		if(cseSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/cse/2-2";
	}
	
	@GetMapping("/cse/2-1/refactor")
	public String refactor2_1() {
		cseSecondYearService.refactor2_1();
		return "redirect:/cse/2-1";
	}
	
	@GetMapping("/cse/2-2/refactor")
	public String refactor2_2() {
		cseSecondYearService.refactor2_2();
		return "redirect:/cse/2-2";
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
