package in.kvsr.admin.mechanical.fourthyear;



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
import in.kvsr.common.entity.mechanical.MechanicalFourOne;
import in.kvsr.common.entity.mechanical.MechanicalFourTwo;

@Controller
public class MechanicalFourthYearController {
	
	@Autowired
	private MechanicalFourthYearService mechanicalFourthYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private MechanicalFeedbackService mechanicalFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/mechanical/4-1")
	public String mechanical4_1(Model model) {
		model.addAttribute("title","Mechanical 4-1");
		model.addAttribute("pageTitle","Fourth Year MECHANICAL Subjects");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("semister1","/mechanical/4-1");
		model.addAttribute("semister2","/mechanical/4-2");
		model.addAttribute("refactorId","/mechanical/4-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/mechanical/4-1/add");
		model.addAttribute("delete","/mechanical/4-1/delete/");
		model.addAttribute("update","/mechanical/4-1/update/");
		model.addAttribute("info","/mechanical/4/s-code/");
		model.addAttribute("subjects", mechanicalFourthYearService.listAllOf4_1());
		return "mechanical/subjects";
	}
	
	@GetMapping("/mechanical/4-2")
	public String mechanical4_2(Model model) {
		model.addAttribute("title","mechanical 4-2");
		model.addAttribute("pageTitle","Fourth Year MECHANICAL Subjects");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("semister1","/mechanical/4-1");
		model.addAttribute("semister2","/mechanical/4-2");
		model.addAttribute("refactorId","/mechanical/4-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/mechanical/4-2/add");
		model.addAttribute("delete","/mechanical/4-2/delete/");
		model.addAttribute("update","/mechanical/4-2/update/");
		model.addAttribute("info","/mechanical/4/s-code/");
		model.addAttribute("subjects", mechanicalFourthYearService.listAllOf4_2());
		return "mechanical/subjects";
	}
	
	@GetMapping("/mechanical/4-1/add")
	public String mechanical4_1_add(Model model) {
		model.addAttribute("title","Mechanical 4-1");
		model.addAttribute("pageTitle","MECHANICAL 4th year 1st semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject", new MechanicalFourOne());
		model.addAttribute("action","/mechanical/4-1/save");
		return "mechanical/subjectform";
	}
	
	@GetMapping("/mechanical/4-2/add")
	public String mechanical4_2_add(Model model) {
		model.addAttribute("title","Mechanical 4-2");
		model.addAttribute("pageTitle","MECHANICAL 4th year 2nd semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject", new MechanicalFourTwo());
		model.addAttribute("action","/mechanical/4-2/save");
		return "mechanical/subjectform";
	}
	
	@RequestMapping("/mechanical/4-1/save")
	public String save4_1(@ModelAttribute("subject") MechanicalFourOne mechanicalFourOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Mechanical 4-1");
		model.addAttribute("pageTitle","MECHANICAL 2nd year 1st semister");
		model.addAttribute("fourthYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", mechanicalFourOne);
			model.addAttribute("action", "/mechanical/4-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "mechanical/subjectform";
		}
		String response = preProcess(mechanicalFourOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", mechanicalFourOne);
			model.addAttribute("action", "/mechanical/4-1/save");
			model.addAttribute("message",response);
			return "mechanical/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(mechanicalFourOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", mechanicalFourOne);
			model.addAttribute("action", "/mechanical/4-1/save");
			model.addAttribute("message","No faculty found with id, "+mechanicalFourOne.getFacultyRegId());
			return "mechanical/subjectform";
		}
		
		
		if(mechanicalFourthYearService.saveIn4_1(mechanicalFourOne)) {
			redirectAttributes.addFlashAttribute("message",mechanicalFourOne.getSubjectName()+" added!");
			return "redirect:/mechanical/4-1";
		}else {
			model.addAttribute("subject", mechanicalFourOne);
			model.addAttribute("action", "/mechanical/4-1/save");
			model.addAttribute("message","subject might exits!");
			return "mechanical/subjectform";
		}
	}
	
	@RequestMapping("/mechanical/4-2/save")
	public String save4_2(@ModelAttribute("subject") MechanicalFourTwo mechanicalFourTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Mechanical 4-2");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("pageTitle","MECHANICAL 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", mechanicalFourTwo);
			model.addAttribute("action", "/mechanical/4-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "mechanical/subjectform";
		}
		
		String response = preProcess(mechanicalFourTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", mechanicalFourTwo);
			model.addAttribute("action", "/mechanical/4-2/save");
			model.addAttribute("message",response);
			return "mechanical/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(mechanicalFourTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", mechanicalFourTwo);
			model.addAttribute("action", "/mechanical/4-2/save");
			model.addAttribute("message","No faculty found with id, " + mechanicalFourTwo.getFacultyRegId());
			return "mechanical/subjectform";
		}
		
		if(mechanicalFourthYearService.saveIn4_2(mechanicalFourTwo)) {
			redirectAttributes.addFlashAttribute("message",mechanicalFourTwo.getSubjectName()+" added!");
			return "redirect:/mechanical/4-2";
		}else {
			model.addAttribute("subject", mechanicalFourTwo);
			model.addAttribute("action", "/mechanical/4-2/save");
			model.addAttribute("message","subject might exits!");
			return "mechanical/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/mechanical/4/s-code/{subjectCode}")
	public String subjectInfo4(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = mechanicalFourthYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/mechanical/4-1";
		}
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/mechanical/4-1";
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
	
	@GetMapping("/mechanical/4-1/update/{id}")
	public String update4_1(@PathVariable Long id, Model model) {
		MechanicalFourOne subject = mechanicalFourthYearService.getOneById(id);
		model.addAttribute("pageTitle","MECHANICAL 4th year 1st semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/mechanical/faculty/search/1");
		model.addAttribute("action","/mechanical/4-1/save");
		return "mechanical/subjectForm";
	}
	
	@GetMapping("/mechanical/4-2/update/{id}")
	public String update4_2(@PathVariable Long id, Model model) {
		MechanicalFourTwo subject = mechanicalFourthYearService.getTwoById(id);
		model.addAttribute("pageTitle","MECHANICAL  year 2nd semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/mechanical/faculty/search/2");
		model.addAttribute("action","/mechanical/4-2/save");
		return "mechanical/subjectForm";
	}
	
	@GetMapping("/mechanical/4-1/delete/{id}")
	public String delete4_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		MechanicalFourOne subject = mechanicalFourthYearService.getOneById(id);
		if(mechanicalFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/mechanical/4-1";
	}
	
	@GetMapping("/mechanical/4-2/delete/{id}")
	public String delete4_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		MechanicalFourTwo subject = mechanicalFourthYearService.getTwoById(id);
		if(mechanicalFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/mechanical/4-2";
	}
	
	@GetMapping("/mechanical/4-1/refactor")
	public String refactor4_1() {
		mechanicalFourthYearService.refactor4_1();
		return "redirect:/mechanical/4-1";
	}
	
	@GetMapping("/mechanical/4-2/refactor")
	public String refactor4_2() {
		mechanicalFourthYearService.refactor4_2();
		return "redirect:/mechanical/4-2";
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
