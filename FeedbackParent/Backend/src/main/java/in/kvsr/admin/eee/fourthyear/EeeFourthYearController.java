package in.kvsr.admin.eee.fourthyear;



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

import in.kvsr.admin.eee.EeeFeedbackService;
import in.kvsr.admin.faculty.FacultyService;
import in.kvsr.admin.questions.QuestionService;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.eee.EeeFourOne;
import in.kvsr.common.entity.eee.EeeFourTwo;

@Controller
public class EeeFourthYearController {
	
	@Autowired
	private EeeFourthYearService eeeFourthYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private EeeFeedbackService eeeFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/eee/4-1")
	public String eee4_1(Model model) {
		model.addAttribute("title","Eee 4-1");
		model.addAttribute("pageTitle","Fourth Year EEE Subjects");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("semister1","/eee/4-1");
		model.addAttribute("semister2","/eee/4-2");
		model.addAttribute("refactorId","/eee/4-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/eee/4-1/add");
		model.addAttribute("delete","/eee/4-1/delete/");
		model.addAttribute("update","/eee/4-1/update/");
		model.addAttribute("info","/eee/4/s-code/");
		model.addAttribute("subjects", eeeFourthYearService.listAllOf4_1());
		return "eee/subjects";
	}
	
	@GetMapping("/eee/4-2")
	public String eee4_2(Model model) {
		model.addAttribute("title","eee 4-2");
		model.addAttribute("pageTitle","Fourth Year EEE Subjects");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("semister1","/eee/4-1");
		model.addAttribute("semister2","/eee/4-2");
		model.addAttribute("refactorId","/eee/4-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/eee/4-2/add");
		model.addAttribute("delete","/eee/4-2/delete/");
		model.addAttribute("update","/eee/4-2/update/");
		model.addAttribute("info","/eee/4/s-code/");
		model.addAttribute("subjects", eeeFourthYearService.listAllOf4_2());
		return "eee/subjects";
	}
	
	@GetMapping("/eee/4-1/add")
	public String eee4_1_add(Model model) {
		model.addAttribute("title","Eee 4-1");
		model.addAttribute("pageTitle","EEE 4th year 1st semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject", new EeeFourOne());
		model.addAttribute("action","/eee/4-1/save");
		return "eee/subjectform";
	}
	
	@GetMapping("/eee/4-2/add")
	public String eee4_2_add(Model model) {
		model.addAttribute("title","Eee 4-2");
		model.addAttribute("pageTitle","EEE 4th year 2nd semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject", new EeeFourTwo());
		model.addAttribute("action","/eee/4-2/save");
		return "eee/subjectform";
	}
	
	@RequestMapping("/eee/4-1/save")
	public String save4_1(@ModelAttribute("subject") EeeFourOne eeeFourOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Eee 4-1");
		model.addAttribute("pageTitle","EEE 2nd year 1st semister");
		model.addAttribute("fourthYearActive","active");
		if(  key != null && !key.isBlank()) {
			
			model.addAttribute("subject", eeeFourOne);
			model.addAttribute("action", "/eee/4-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "eee/subjectform";
		}
		String response = preProcess(eeeFourOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eeeFourOne);
			model.addAttribute("action", "/eee/4-1/save");
			model.addAttribute("message",response);
			return "eee/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(eeeFourOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", eeeFourOne);
			model.addAttribute("action", "/eee/4-1/save");
			model.addAttribute("message","No faculty found with id, "+eeeFourOne.getFacultyRegId());
			return "eee/subjectform";
		}
		
		
		if(eeeFourthYearService.saveIn4_1(eeeFourOne)) {
			redirectAttributes.addFlashAttribute("message",eeeFourOne.getSubjectName()+" added!");
			return "redirect:/eee/4-1";
		}else {
			model.addAttribute("subject", eeeFourOne);
			model.addAttribute("action", "/eee/4-1/save");
			model.addAttribute("message","subject might exits!");
			return "eee/subjectform";
		}
	}
	
	@RequestMapping("/eee/4-2/save")
	public String save4_2(@ModelAttribute("subject") EeeFourTwo eeeFourTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Eee 4-2");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("pageTitle","EEE 2nd year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", eeeFourTwo);
			model.addAttribute("action", "/eee/4-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "eee/subjectform";
		}
		
		String response = preProcess(eeeFourTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eeeFourTwo);
			model.addAttribute("action", "/eee/4-2/save");
			model.addAttribute("message",response);
			return "eee/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(eeeFourTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", eeeFourTwo);
			model.addAttribute("action", "/eee/4-2/save");
			model.addAttribute("message","No faculty found with id, " + eeeFourTwo.getFacultyRegId());
			return "eee/subjectform";
		}
		
		if(eeeFourthYearService.saveIn4_2(eeeFourTwo)) {
			redirectAttributes.addFlashAttribute("message",eeeFourTwo.getSubjectName()+" added!");
			return "redirect:/eee/4-2";
		}else {
			model.addAttribute("subject", eeeFourTwo);
			model.addAttribute("action", "/eee/4-2/save");
			model.addAttribute("message","subject might exits!");
			return "eee/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/eee/4/s-code/{subjectCode}")
	public String subjectInfo4(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = eeeFourthYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/eee/4-1";
		}
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/eee/4-1";
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
		model.addAttribute("remarks",eeeFeedbackService.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId()));
		model.addAttribute("questions",questionService.listAll());
		return "eee/subjectinfo";
	}
	
	@GetMapping("/eee/4-1/update/{id}")
	public String update4_1(@PathVariable Long id, Model model) {
		EeeFourOne subject = eeeFourthYearService.getOneById(id);
		model.addAttribute("pageTitle","EEE 4th year 1st semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/eee/faculty/search/1");
		model.addAttribute("action","/eee/4-1/save");
		return "eee/subjectForm";
	}
	
	@GetMapping("/eee/4-2/update/{id}")
	public String update4_2(@PathVariable Long id, Model model) {
		EeeFourTwo subject = eeeFourthYearService.getTwoById(id);
		model.addAttribute("pageTitle","EEE  year 2nd semister");
		model.addAttribute("fourthYearActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/eee/faculty/search/2");
		model.addAttribute("action","/eee/4-2/save");
		return "eee/subjectForm";
	}
	
	@GetMapping("/eee/4-1/delete/{id}")
	public String delete4_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EeeFourOne subject = eeeFourthYearService.getOneById(id);
		if(eeeFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/eee/4-1";
	}
	
	@GetMapping("/eee/4-2/delete/{id}")
	public String delete4_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EeeFourTwo subject = eeeFourthYearService.getTwoById(id);
		if(eeeFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/eee/4-2";
	}
	
	@GetMapping("/eee/4-1/refactor")
	public String refactor4_1() {
		eeeFourthYearService.refactor4_1();
		return "redirect:/eee/4-1";
	}
	
	@GetMapping("/eee/4-2/refactor")
	public String refactor4_2() {
		eeeFourthYearService.refactor4_2();
		return "redirect:/eee/4-2";
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
