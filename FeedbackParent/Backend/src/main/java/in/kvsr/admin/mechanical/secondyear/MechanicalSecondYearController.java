package in.kvsr.admin.mechanical.secondyear;



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

import in.kvsr.admin.mechanical.MechanicalFeedbackService;
import in.kvsr.admin.faculty.FacultyService;
import in.kvsr.admin.questions.QuestionService;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.mechanical.MechanicalTwoOne;
import in.kvsr.common.entity.mechanical.MechanicalTwoTwo;

@Controller
public class MechanicalSecondYearController {
	
	@Autowired
	private MechanicalSecondYearService mechanicalSecondYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private MechanicalFeedbackService mechanicalFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/mechanical/2-1")
	public String mechanical2_1(Model model) {
		model.addAttribute("title","Mechanical 2-1");
		model.addAttribute("pageTitle","Second Year MECHANICAL Subjects");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("semister1","/mechanical/2-1");
		model.addAttribute("semister2","/mechanical/2-2");
		model.addAttribute("refactorId","/mechanical/2-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/mechanical/2-1/add");
		model.addAttribute("delete","/mechanical/2-1/delete/");
		model.addAttribute("update","/mechanical/2-1/update/");
		model.addAttribute("info","/mechanical/2/s-code/");
		model.addAttribute("subjects", mechanicalSecondYearService.listAllOf2_1());
		return "mechanical/subjects";
	}
	
	@GetMapping("/mechanical/2-2")
	public String mechanical22(Model model) {
		model.addAttribute("title","mechanical 2-2");
		model.addAttribute("pageTitle","Second Year MECHANICAL Subjects");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("semister1","/mechanical/2-1");
		model.addAttribute("semister2","/mechanical/2-2");
		model.addAttribute("refactorId","/mechanical/2-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/mechanical/2-2/add");
		model.addAttribute("delete","/mechanical/2-2/delete/");
		model.addAttribute("update","/mechanical/2-2/update/");
		model.addAttribute("info","/mechanical/2/s-code/");
		model.addAttribute("subjects", mechanicalSecondYearService.listAllOf2_2());
		return "mechanical/subjects";
	}
	
	@GetMapping("/mechanical/2-1/add")
	public String mechanical2_1_add(Model model) {
		model.addAttribute("title","Mechanical 2-1");
		model.addAttribute("pageTitle","MECHANICAL 2nd year 1st semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject", new MechanicalTwoOne());
		model.addAttribute("action","/mechanical/2-1/save");
		return "mechanical/subjectform";
	}
	
	@GetMapping("/mechanical/2-2/add")
	public String mechanical2_2_add(Model model) {
		model.addAttribute("title","Mechanical 2-2");
		model.addAttribute("pageTitle","MECHANICAL 2nd year 2nd semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject", new MechanicalTwoTwo());
		model.addAttribute("action","/mechanical/2-2/save");
		return "mechanical/subjectform";
	}
	
	@RequestMapping("/mechanical/2-1/save")
	public String save2_1(@ModelAttribute("subject") MechanicalTwoOne mechanicalTwoOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Mechanical 2-1");
		model.addAttribute("pageTitle","MECHANICAL 2nd year 1st semister");
		model.addAttribute("secondYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", mechanicalTwoOne);
			model.addAttribute("action", "/mechanical/2-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "mechanical/subjectform";
		}
		String response = preProcess(mechanicalTwoOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", mechanicalTwoOne);
			model.addAttribute("action", "/mechanical/2-1/save");
			model.addAttribute("message",response);
			return "mechanical/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(mechanicalTwoOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", mechanicalTwoOne);
			model.addAttribute("action", "/mechanical/2-1/save");
			model.addAttribute("message","No faculty found with id, "+mechanicalTwoOne.getFacultyRegId());
			return "mechanical/subjectform";
		}
		
		
		if(mechanicalSecondYearService.saveIn2_1(mechanicalTwoOne)) {
			redirectAttributes.addFlashAttribute("message",mechanicalTwoOne.getSubjectName()+" added!");
			return "redirect:/mechanical/2-1";
		}else {
			model.addAttribute("subject", mechanicalTwoOne);
			model.addAttribute("action", "/mechanical/2-1/save");
			model.addAttribute("message","subject might exits!");
			return "mechanical/subjectform";
		}
	}
	
	@RequestMapping("/mechanical/2-2/save")
	public String save2_2(@ModelAttribute("subject") MechanicalTwoTwo mechanicalTwoTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Mechanical 2-2");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("pageTitle","MECHANICAL 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", mechanicalTwoTwo);
			model.addAttribute("action", "/mechanical/2-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "mechanical/subjectform";
		}
		
		String response = preProcess(mechanicalTwoTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", mechanicalTwoTwo);
			model.addAttribute("action", "/mechanical/2-2/save");
			model.addAttribute("message",response);
			return "mechanical/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(mechanicalTwoTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", mechanicalTwoTwo);
			model.addAttribute("action", "/mechanical/2-2/save");
			model.addAttribute("message","No faculty found with id, " + mechanicalTwoTwo.getFacultyRegId());
			return "mechanical/subjectform";
		}
		
		if(mechanicalSecondYearService.saveIn2_2(mechanicalTwoTwo)) {
			redirectAttributes.addFlashAttribute("message",mechanicalTwoTwo.getSubjectName()+" added!");
			return "redirect:/mechanical/2-2";
		}else {
			model.addAttribute("subject", mechanicalTwoTwo);
			model.addAttribute("action", "/mechanical/2-2/save");
			model.addAttribute("message","subject might exits!");
			return "mechanical/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/mechanical/2/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = mechanicalSecondYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/mechanical/2-1";
		}
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/mechanical/2-1";
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
		model.addAttribute("remarks",mechanicalFeedbackService.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId()));
		model.addAttribute("questions",questionService.listAll());
		return "mechanical/subjectinfo";
	}
	
	@GetMapping("/mechanical/2-1/update/{id}")
	public String update2_1(@PathVariable Long id, Model model) {
		MechanicalTwoOne subject = mechanicalSecondYearService.getOneById(id);
		model.addAttribute("pageTitle","MECHANICAL 2nd year 1st semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/mechanical/faculty/search/1");
		model.addAttribute("action","/mechanical/2-1/save");
		return "mechanical/subjectForm";
	}
	
	@GetMapping("/mechanical/2-2/update/{id}")
	public String update2_2(@PathVariable Long id, Model model) {
		MechanicalTwoTwo subject = mechanicalSecondYearService.getTwoById(id);
		model.addAttribute("pageTitle","MECHANICAL 2nd year 2nd semister");
		model.addAttribute("secondYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/mechanical/faculty/search/2");
		model.addAttribute("action","/mechanical/2-2/save");
		return "mechanical/subjectForm";
	}
	
	@GetMapping("/mechanical/2-1/delete/{id}")
	public String delete2_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		MechanicalTwoOne subject = mechanicalSecondYearService.getOneById(id);
		if(mechanicalSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/mechanical/2-1";
	}
	
	@GetMapping("/mechanical/2-2/delete/{id}")
	public String delete2_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		MechanicalTwoTwo subject = mechanicalSecondYearService.getTwoById(id);
		if(mechanicalSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/mechanical/2-2";
	}
	
	@GetMapping("/mechanical/2-1/refactor")
	public String refactor2_1() {
		mechanicalSecondYearService.refactor2_1();
		return "redirect:/mechanical/2-1";
	}
	
	@GetMapping("/mechanical/2-2/refactor")
	public String refactor2_2() {
		mechanicalSecondYearService.refactor2_2();
		return "redirect:/mechanical/2-2";
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
