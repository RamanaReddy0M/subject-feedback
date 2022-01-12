package in.kvsr.admin.mechanical.thirdyear;



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
import in.kvsr.common.entity.mechanical.MechanicalThreeOne;
import in.kvsr.common.entity.mechanical.MechanicalThreeTwo;

@Controller
public class MechanicalThirdYearController {
	
	@Autowired
	private MechanicalThirdYearService mechanicalThirdYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private MechanicalFeedbackService mechanicalFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/mechanical/3-1")
	public String mechanical3_1(Model model) {
		model.addAttribute("title","Mechanical 3-1");
		model.addAttribute("pageTitle","Third Year MECHANICAL Subjects");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("semister1","/mechanical/3-1");
		model.addAttribute("semister2","/mechanical/3-2");
		model.addAttribute("refactorId","/mechanical/3-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/mechanical/3-1/add");
		model.addAttribute("delete","/mechanical/3-1/delete/");
		model.addAttribute("update","/mechanical/3-1/update/");
		model.addAttribute("info","/mechanical/3/s-code/");
		model.addAttribute("subjects", mechanicalThirdYearService.listAllOf3_1());
		return "mechanical/subjects";
	}
	
	@GetMapping("/mechanical/3-2")
	public String mechanical3_2(Model model) {
		model.addAttribute("title","mechanical 3-2");
		model.addAttribute("pageTitle","Third Year MECHANICAL Subjects");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("semister1","/mechanical/3-1");
		model.addAttribute("semister2","/mechanical/3-2");
		model.addAttribute("refactorId","/mechanical/3-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/mechanical/3-2/add");
		model.addAttribute("delete","/mechanical/3-2/delete/");
		model.addAttribute("update","/mechanical/3-2/update/");
		model.addAttribute("info","/mechanical/3/s-code/");
		model.addAttribute("subjects", mechanicalThirdYearService.listAllOf3_2());
		return "mechanical/subjects";
	}
	
	@GetMapping("/mechanical/3-1/add")
	public String mechanical3_1_add(Model model) {
		model.addAttribute("title","Mechanical 3-1");
		model.addAttribute("pageTitle","MECHANICAL 3rd year 1st semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject", new MechanicalThreeOne());
		model.addAttribute("action","/mechanical/3-1/save");
		return "mechanical/subjectform";
	}
	
	@GetMapping("/mechanical/3-2/add")
	public String mechanical3_2_add(Model model) {
		model.addAttribute("title","Mechanical 3-2");
		model.addAttribute("pageTitle","MECHANICAL 3rd year 2nd semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject", new MechanicalThreeTwo());
		model.addAttribute("action","/mechanical/3-2/save");
		return "mechanical/subjectform";
	}
	
	@RequestMapping("/mechanical/3-1/save")
	public String save3_1(@ModelAttribute("subject") MechanicalThreeOne mechanicalThreeOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Mechanical 3-1");
		model.addAttribute("pageTitle","MECHANICAL 2nd year 1st semister");
		model.addAttribute("thirdYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", mechanicalThreeOne);
			model.addAttribute("action", "/mechanical/3-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "mechanical/subjectform";
		}
		String response = preProcess(mechanicalThreeOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", mechanicalThreeOne);
			model.addAttribute("action", "/mechanical/3-1/save");
			model.addAttribute("message",response);
			return "mechanical/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(mechanicalThreeOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", mechanicalThreeOne);
			model.addAttribute("action", "/mechanical/3-1/save");
			model.addAttribute("message","No faculty found with id, "+mechanicalThreeOne.getFacultyRegId());
			return "mechanical/subjectform";
		}
		
		
		if(mechanicalThirdYearService.saveIn3_1(mechanicalThreeOne)) {
			redirectAttributes.addFlashAttribute("message",mechanicalThreeOne.getSubjectName()+" added!");
			return "redirect:/mechanical/3-1";
		}else {
			model.addAttribute("subject", mechanicalThreeOne);
			model.addAttribute("action", "/mechanical/3-1/save");
			model.addAttribute("message","subject might exits!");
			return "mechanical/subjectform";
		}
	}
	
	@RequestMapping("/mechanical/3-2/save")
	public String save3_2(@ModelAttribute("subject") MechanicalThreeTwo mechanicalThreeTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Mechanical 3-2");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("pageTitle","MECHANICAL 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", mechanicalThreeTwo);
			model.addAttribute("action", "/mechanical/3-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "mechanical/subjectform";
		}
		
		String response = preProcess(mechanicalThreeTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", mechanicalThreeTwo);
			model.addAttribute("action", "/mechanical/3-2/save");
			model.addAttribute("message",response);
			return "mechanical/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(mechanicalThreeTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", mechanicalThreeTwo);
			model.addAttribute("action", "/mechanical/3-2/save");
			model.addAttribute("message","No faculty found with id, " + mechanicalThreeTwo.getFacultyRegId());
			return "mechanical/subjectform";
		}
		
		if(mechanicalThirdYearService.saveIn3_2(mechanicalThreeTwo)) {
			redirectAttributes.addFlashAttribute("message",mechanicalThreeTwo.getSubjectName()+" added!");
			return "redirect:/mechanical/3-2";
		}else {
			model.addAttribute("subject", mechanicalThreeTwo);
			model.addAttribute("action", "/mechanical/3-2/save");
			model.addAttribute("message","subject might exits!");
			return "mechanical/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/mechanical/3/s-code/{subjectCode}")
	public String subjectInfo3(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = mechanicalThirdYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/mechanical/3-1";
		}
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/mechanical/3-1";
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
	
	@GetMapping("/mechanical/3-1/update/{id}")
	public String update3_1(@PathVariable Long id, Model model) {
		MechanicalThreeOne subject = mechanicalThirdYearService.getOneById(id);
		model.addAttribute("pageTitle","MECHANICAL 2nd year 1st semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/mechanical/faculty/search/1");
		model.addAttribute("action","/mechanical/3-1/save");
		return "mechanical/subjectForm";
	}
	
	@GetMapping("/mechanical/3-2/update/{id}")
	public String update3_2(@PathVariable Long id, Model model) {
		MechanicalThreeTwo subject = mechanicalThirdYearService.getTwoById(id);
		model.addAttribute("pageTitle","MECHANICAL 2nd year 2nd semister");
		model.addAttribute("thirdYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/mechanical/faculty/search/2");
		model.addAttribute("action","/mechanical/3-2/save");
		return "mechanical/subjectForm";
	}
	
	@GetMapping("/mechanical/3-1/delete/{id}")
	public String delete3_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		MechanicalThreeOne subject = mechanicalThirdYearService.getOneById(id);
		if(mechanicalThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/mechanical/3-1";
	}
	
	@GetMapping("/mechanical/3-2/delete/{id}")
	public String delete3_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		MechanicalThreeTwo subject = mechanicalThirdYearService.getTwoById(id);
		if(mechanicalThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/mechanical/3-2";
	}
	
	@GetMapping("/mechanical/3-1/refactor")
	public String refactor3_1() {
		mechanicalThirdYearService.refactor3_1();
		return "redirect:/mechanical/3-1";
	}
	
	@GetMapping("/mechanical/3-2/refactor")
	public String refactor3_2() {
		mechanicalThirdYearService.refactor3_2();
		return "redirect:/mechanical/3-2";
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
