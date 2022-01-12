package in.kvsr.admin.civil.firstyear;



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
import in.kvsr.common.entity.civil.CivilOneOne;
import in.kvsr.common.entity.civil.CivilOneTwo;

@Controller
public class CivilFirstYearController {
	
	@Autowired
	private CivilFirstYearService civilFirstYearService;
	@Autowired 
	private FacultyService facultyService;
	@Autowired
	private HandSFeedbackService handSFeedbackService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/civil/1-1")
	public String civil1_1(Model model) {
		model.addAttribute("title","civil 1-1");
		model.addAttribute("pageTitle","First Year CIVIL Subjects");
		model.addAttribute("civilActive","active");
		model.addAttribute("semister1","/civil/1-1");
		model.addAttribute("semister2","/civil/1-2");
		model.addAttribute("refactorId","/civil/1-1/refactor");
		model.addAttribute("firstActive","sub_active");
		model.addAttribute("add","/civil/1-1/add");
		model.addAttribute("delete","/civil/1-1/delete/");
		model.addAttribute("update","/civil/1-1/update/");
		model.addAttribute("info","/civil/1/s-code/");
		model.addAttribute("subjects", civilFirstYearService.listAllOf1_1());
		return "civil/subjects";
	}
	
	@GetMapping("/civil/1-2")
	public String civil1_2(Model model) {
		model.addAttribute("title","civil 1-2");
		model.addAttribute("pageTitle","First Year CIVIL Subjects");
		model.addAttribute("civilActive","active");
		model.addAttribute("semister1","/civil/1-1");
		model.addAttribute("semister2","/civil/2-2");
		model.addAttribute("refactorId","/civil/1-2/refactor");
		model.addAttribute("secondActive","sub_active");
		model.addAttribute("add","/civil/1-2/add");
		model.addAttribute("delete","/civil/1-2/delete/");
		model.addAttribute("update","/civil/1-2/update/");
		model.addAttribute("info","/civil/1/s-code/");
		model.addAttribute("subjects", civilFirstYearService.listAllOf1_2());
		return "civil/subjects";
	}
	
	@GetMapping("/civil/1-1/add")
	public String civil1_1_add(Model model) {
		model.addAttribute("title","Civil 1-1");
		model.addAttribute("pageTitle","CIVIL 1st year 1st semister");
		model.addAttribute("civilActive","active");
		model.addAttribute("subject", new CivilOneOne());
		model.addAttribute("action","/civil/1-1/save");
		return "civil/subjectform";
	}
	
	@GetMapping("/civil/1-2/add")
	public String civil1_2_add(Model model) {
		model.addAttribute("title","Civil 1-2");
		model.addAttribute("pageTitle","CIVIL 1st year 2nd semister");
		model.addAttribute("civilActive","active");
		model.addAttribute("subject", new CivilOneTwo());
		model.addAttribute("action","/civil/1-2/save");
		return "civil/subjectform";
	}
	
	@RequestMapping("/civil/1-1/save")
	public String save1_1(@ModelAttribute("subject") CivilOneOne civilOneOne, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Civil 1-1");
		model.addAttribute("pageTitle","CIVIL 1st year 1st semister");
		model.addAttribute("civilActive","active");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", civilOneOne);
			model.addAttribute("action", "/civil/1-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "civil/subjectform";
		}
		String response = preProcess(civilOneOne);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", civilOneOne);
			model.addAttribute("action", "/civil/1-1/save");
			model.addAttribute("message",response);
			return "civil/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(civilOneOne.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", civilOneOne);
			model.addAttribute("action", "/civil/1-2/save");
			model.addAttribute("message","No faculty found with id, "+civilOneOne.getFacultyRegId());
			return "civil/subjectform";
		}
		
		
		if(civilFirstYearService.saveIn1_1(civilOneOne)) {
			redirectAttributes.addFlashAttribute("message",civilOneOne.getSubjectName()+" added!");
			return "redirect:/civil/1-1";
		}else {
			model.addAttribute("subject", civilOneOne);
			model.addAttribute("action", "/civil/1-1/save");
			model.addAttribute("message","subject might exits!");
			return "civil/subjectform";
		}
	}
	
	@RequestMapping("/civil/1-2/save")
	public String save1_2(@ModelAttribute("subject") CivilOneTwo civilOneTwo, 
			                   RedirectAttributes redirectAttributes,
			                   Model model, @RequestParam String key) {
		
		key = key.trim();
		model.addAttribute("title","Civil 1-2");
		model.addAttribute("civilActive","active");
		model.addAttribute("pageTitle","CIVIL 1st year 2nd semister");
		if(  key != null && !key.isBlank()) {
			model.addAttribute("subject", civilOneTwo);
			model.addAttribute("action", "/civil/1-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "civil/subjectform";
		}
		
		String response = preProcess(civilOneTwo);
		if(!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", civilOneTwo);
			model.addAttribute("action", "/civil/1-2/save");
			model.addAttribute("message",response);
			return "civil/subjectform";
		}
		
		Faculty faculty = facultyService.getByRegId(civilOneTwo.getFacultyRegId());
		if(faculty==null) {
			model.addAttribute("subject", civilOneTwo);
			model.addAttribute("action", "/civil/1-2/save");
			model.addAttribute("message","No faculty found with id, "+civilOneTwo.getFacultyRegId());
			return "civil/subjectform";
		}
		
		if(civilFirstYearService.saveIn1_2(civilOneTwo)) {
			redirectAttributes.addFlashAttribute("message",civilOneTwo.getSubjectName()+" added!");
			return "redirect:/civil/1-2";
		}else {
			model.addAttribute("subject", civilOneTwo);
			model.addAttribute("action", "/civil/1-2/save");
			model.addAttribute("message","subject might exits!");
			return "civil/subjectform";
		}
	}
	
	/* Subject info */
	@GetMapping("/civil/1/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = civilFirstYearService.findBySubjectCode(subjectCode);
		if(subject.getTotal()==null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" has no feedback.");
			return "redirect:/civil/1-1";
		}
		model.addAttribute("civilActive","active");
		model.addAttribute("subject",subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","No faculty found with id, "+subject.getFacultyRegId());
			return "redirect:/civil/1-1";
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
		return "civil/subjectinfo";
	}
	
	@GetMapping("/civil/1-1/update/{id}")
	public String update1_1(@PathVariable Long id, Model model) {
		CivilOneOne subject = civilFirstYearService.getOneOneById(id);
		model.addAttribute("pageTitle","CIVIL 1st year 1st semister");
		model.addAttribute("civilActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/civil/faculty/search/1");
		model.addAttribute("action","/civil/1-1/save");
		return "civil/subjectForm";
	}
	
	@GetMapping("/civil/1-2/update/{id}")
	public String update1_2(@PathVariable Long id, Model model) {
		CivilOneTwo subject = civilFirstYearService.getOneTwoById(id);
		model.addAttribute("pageTitle","CIVIL 1st year 2nd semister");
		model.addAttribute("civilActive","active");
		model.addAttribute("subject",subject);
		model.addAttribute("actionForSearch","/civil/faculty/search/2");
		model.addAttribute("action","/civil/1-2/save");
		return "civil/subjectForm";
	}
	
	@GetMapping("/civil/1-1/delete/{id}")
	public String delete1_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CivilOneOne subject = civilFirstYearService.getOneOneById(id);
		if(civilFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/civil/1-1";
	}
	
	@GetMapping("/civil/1-2/delete/{id}")
	public String delete1_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		CivilOneTwo subject = civilFirstYearService.getOneTwoById(id);
		if(civilFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName()+" deleted!");
		}else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/civil/1-2";
	}
	
	@GetMapping("/civil/1-1/refactor")
	public String refactor1_1() {
		civilFirstYearService.refactor1_1();
		return "redirect:/civil/1-1";
	}
	
	@GetMapping("/civil/1-2/refactor")
	public String refactor1_2() {
		civilFirstYearService.refactor1_2();
		return "redirect:/civil/1-2";
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
