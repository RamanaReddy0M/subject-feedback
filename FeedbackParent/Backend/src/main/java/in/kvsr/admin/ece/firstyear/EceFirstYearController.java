package in.kvsr.admin.ece.firstyear;

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
import in.kvsr.common.entity.ece.EceOneOne;
import in.kvsr.common.entity.ece.EceOneTwo;

@Controller
public class EceFirstYearController {

	@Autowired
	private EceFirstYearService eceFirstYearService;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private HandSFeedbackService handSFeedbackService;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/ece/1-1")
	public String ece1_1(Model model) {
		model.addAttribute("title", "ece 1-1");
		model.addAttribute("pageTitle", "First Year ECE Subjects");
		model.addAttribute("eceActive", "active");
		model.addAttribute("semister1", "/ece/1-1");
		model.addAttribute("semister2", "/ece/1-2");
		model.addAttribute("refactorId", "/ece/1-1/refactor");
		model.addAttribute("firstActive", "sub_active");
		model.addAttribute("add", "/ece/1-1/add");
		model.addAttribute("delete", "/ece/1-1/delete/");
		model.addAttribute("update", "/ece/1-1/update/");
		model.addAttribute("info", "/ece/1/s-code/");
		model.addAttribute("subjects", eceFirstYearService.listAllOf1_1());
		return "ece/subjects";
	}

	@GetMapping("/ece/1-2")
	public String ece1_2(Model model) {
		model.addAttribute("title", "ece 1-2");
		model.addAttribute("pageTitle", "First Year ECE Subjects");
		model.addAttribute("eceActive", "active");
		model.addAttribute("semister1", "/ece/1-1");
		model.addAttribute("semister2", "/ece/2-2");
		model.addAttribute("refactorId", "/ece/1-2/refactor");
		model.addAttribute("secondActive", "sub_active");
		model.addAttribute("add", "/ece/1-2/add");
		model.addAttribute("delete", "/ece/1-2/delete/");
		model.addAttribute("update", "/ece/1-2/update/");
		model.addAttribute("info", "/ece/1/s-code/");
		model.addAttribute("subjects", eceFirstYearService.listAllOf1_2());
		return "ece/subjects";
	}

	@GetMapping("/ece/1-1/add")
	public String ece1_1_add(Model model) {
		model.addAttribute("title","ECE 1-1");
		model.addAttribute("pageTitle", "ECE 1st year 1st semister");
		model.addAttribute("eceActive", "active");
		model.addAttribute("subject", new EceOneOne());
		model.addAttribute("action", "/ece/1-1/save");
		return "ece/subjectform";
	}

	@GetMapping("/ece/1-2/add")
	public String ece1_2_add(Model model) {
		model.addAttribute("title","ECE 1-2");
		model.addAttribute("pageTitle", "ECE 1st year 2nd semister");
		model.addAttribute("eceActive", "active");
		model.addAttribute("subject", new EceOneTwo());
		model.addAttribute("action", "/ece/1-2/save");
		return "ece/subjectform";
	}

	@RequestMapping("/ece/1-1/save")
	public String save1_1(@ModelAttribute("subject") EceOneOne eceOneOne, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title","ECE 1-1");
		model.addAttribute("pageTitle", "ECE 1st year 1st semister");
		model.addAttribute("eceActive", "active");
		if (key != null && !key.isBlank()) {

			model.addAttribute("subject", eceOneOne);
			model.addAttribute("action", "/ece/1-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "ece/subjectform";
		}
		String response = preProcess(eceOneOne);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eceOneOne);
			model.addAttribute("action", "/ece/1-1/save");
			model.addAttribute("message", response);
			return "ece/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eceOneOne.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eceOneOne);
			model.addAttribute("action", "/ece/1-2/save");
			model.addAttribute("message", "No faculty found with id, " + eceOneOne.getFacultyRegId());
			return "ece/subjectform";
		}

		if (eceFirstYearService.saveIn1_1(eceOneOne)) {
			redirectAttributes.addFlashAttribute("message", eceOneOne.getSubjectName() + " added!");
			return "redirect:/ece/1-1";
		} else {
			model.addAttribute("subject", eceOneOne);
			model.addAttribute("action", "/ece/1-1/save");
			model.addAttribute("message", "subject might exits!");
			return "ece/subjectform";
		}
	}

	@RequestMapping("/ece/1-2/save")
	public String save1_2(@ModelAttribute("subject") EceOneTwo eceOneTwo, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title","ECE 1-2");
		model.addAttribute("eceActive", "active");
		model.addAttribute("pageTitle", "ECE 1st year 2nd semister");
		if (key != null && !key.isBlank()) {
			model.addAttribute("subject", eceOneTwo);
			model.addAttribute("action", "/ece/1-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "ece/subjectform";
		}

		String response = preProcess(eceOneTwo);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eceOneTwo);
			model.addAttribute("action", "/ece/1-2/save");
			model.addAttribute("message", response);
			return "ece/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eceOneTwo.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eceOneTwo);
			model.addAttribute("action", "/ece/1-2/save");
			model.addAttribute("message", "No faculty found with id, " + eceOneTwo.getFacultyRegId());
			return "ece/subjectform";
		}

		if (eceFirstYearService.saveIn1_2(eceOneTwo)) {
			redirectAttributes.addFlashAttribute("message", eceOneTwo.getSubjectName() + " added!");
			return "redirect:/ece/1-2";
		} else {
			model.addAttribute("subject", eceOneTwo);
			model.addAttribute("action", "/ece/1-2/save");
			model.addAttribute("message", "subject might exits!");
			return "ece/subjectform";
		}
	}

	/* Subject info */
	@GetMapping("/ece/1/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = eceFirstYearService.findBySubjectCode(subjectCode);
		if (subject.getTotal() == null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " has no feedback.");
			return "redirect:/ece/1-1";
		}
		model.addAttribute("eceActive", "active");
		model.addAttribute("subject", subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if (faculty == null) {
			redirectAttributes.addFlashAttribute("message", "No faculty found with id, " + subject.getFacultyRegId());
			return "redirect:/ece/1-1";
		}
		model.addAttribute("faculty", faculty);
		model.addAttribute("title", subject.getSubjectName());
		model.addAttribute("pageTitle", faculty.getFirstName() + " " + faculty.getLastName());
		model.addAttribute("subjectName", subject.getSubjectName());
		List<Float> questionCounters = new ArrayList<>();
		for (String q : subject.getTotal().split(" ")) {
			questionCounters.add(Float.parseFloat(q));
		}
		// model.addAttribute("questionCounters",questionCounters);
		model.addAttribute("q1", questionCounters.get(0) * 20);
		model.addAttribute("q2", questionCounters.get(1) * 20);
		model.addAttribute("q3", questionCounters.get(2) * 20);
		model.addAttribute("q4", questionCounters.get(3) * 20);
		model.addAttribute("q5", questionCounters.get(4) * 20);
		model.addAttribute("remarks",
				handSFeedbackService.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId()));
		model.addAttribute("questions", questionService.listAll());
		return "ece/subjectinfo";
	}

	@GetMapping("/ece/1-1/update/{id}")
	public String update1_1(@PathVariable Long id, Model model) {
		EceOneOne subject = eceFirstYearService.getOneOneById(id);
		model.addAttribute("pageTitle", "ECE 1st year 1st semister");
		model.addAttribute("eceActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/ece/faculty/search/1");
		model.addAttribute("action", "/ece/1-1/save");
		return "ece/subjectForm";
	}

	@GetMapping("/ece/1-2/update/{id}")
	public String update1_2(@PathVariable Long id, Model model) {
		EceOneTwo subject = eceFirstYearService.getOneTwoById(id);
		model.addAttribute("pageTitle", "ECE 1st year 2nd semister");
		model.addAttribute("eceActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/ece/faculty/search/2");
		model.addAttribute("action", "/ece/1-2/save");
		return "ece/subjectForm";
	}

	@GetMapping("/ece/1-1/delete/{id}")
	public String delete1_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EceOneOne subject = eceFirstYearService.getOneOneById(id);
		if (eceFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/ece/1-1";
	}

	@GetMapping("/ece/1-2/delete/{id}")
	public String delete1_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EceOneTwo subject = eceFirstYearService.getOneTwoById(id);
		if (eceFirstYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/ece/1-2";
	}

	@GetMapping("/ece/1-1/refactor")
	public String refactor1_1() {
		eceFirstYearService.refactor1_1();
		return "redirect:/ece/1-1";
	}

	@GetMapping("/ece/1-2/refactor")
	public String refactor1_2() {
		eceFirstYearService.refactor1_2();
		return "redirect:/ece/1-2";
	}

	public String preProcess(Subject subject) {
		subject.setSubjectCode(subject.getSubjectCode().trim());
		subject.setSubjectName(subject.getSubjectName().trim());
		subject.setFacultyRegId(subject.getFacultyRegId().trim().toUpperCase());

		if (subject.getSubjectCode().isBlank() || subject.getSubjectCode() == null) {
			return "Subject code is required!";
		} else if (subject.getSubjectName().isBlank() || subject.getSubjectCode() == null) {
			return "Subject name is required!";
		} else if (subject.getFacultyRegId().isBlank() || subject.getFacultyRegId() == null) {
			return "Faculty reg.id required!";
		}
		return "ok";
	}

}
