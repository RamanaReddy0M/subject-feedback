package in.kvsr.admin.ece.secondyear;

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

import in.kvsr.admin.ece.EceFeedbackService;
import in.kvsr.admin.faculty.FacultyService;
import in.kvsr.admin.questions.QuestionService;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.ece.EceTwoOne;
import in.kvsr.common.entity.ece.EceTwoTwo;

@Controller
public class EceSecondYearController {

	@Autowired
	private EceSecondYearService eceSecondYearService;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private EceFeedbackService eceFeedbackService;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/ece/2-1")
	public String ece2_1(Model model) {
		model.addAttribute("title", "Ece 2-1");
		model.addAttribute("pageTitle", "Second Year ECE Subjects");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("semister1", "/ece/2-1");
		model.addAttribute("semister2", "/ece/2-2");
		model.addAttribute("refactorId", "/ece/2-1/refactor");
		model.addAttribute("firstActive", "sub_active");
		model.addAttribute("add", "/ece/2-1/add");
		model.addAttribute("delete", "/ece/2-1/delete/");
		model.addAttribute("update", "/ece/2-1/update/");
		model.addAttribute("info", "/ece/2/s-code/");
		model.addAttribute("subjects", eceSecondYearService.listAllOf2_1());
		return "ece/subjects";
	}

	@GetMapping("/ece/2-2")
	public String ece22(Model model) {
		model.addAttribute("title", "Ece 2-2");
		model.addAttribute("pageTitle", "Second Year ECE Subjects");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("semister1", "/ece/2-1");
		model.addAttribute("semister2", "/ece/2-2");
		model.addAttribute("refactorId", "/ece/2-2/refactor");
		model.addAttribute("secondActive", "sub_active");
		model.addAttribute("add", "/ece/2-2/add");
		model.addAttribute("delete", "/ece/2-2/delete/");
		model.addAttribute("update", "/ece/2-2/update/");
		model.addAttribute("info", "/ece/2/s-code/");
		model.addAttribute("subjects", eceSecondYearService.listAllOf2_2());
		return "ece/subjects";
	}

	@GetMapping("/ece/2-1/add")
	public String ece2_1_add(Model model) {
		model.addAttribute("title","ECE 2-1");
		model.addAttribute("pageTitle", "ECE 2nd year 1st semister");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", new EceTwoOne());
		model.addAttribute("action", "/ece/2-1/save");
		return "ece/subjectform";
	}

	@GetMapping("/ece/2-2/add")
	public String ece2_2_add(Model model) {
		model.addAttribute("title","ECE 2-2");
		model.addAttribute("pageTitle", "ECE 2nd year 2nd semister");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", new EceTwoTwo());
		model.addAttribute("action", "/ece/2-2/save");
		return "ece/subjectform";
	}

	@RequestMapping("/ece/2-1/save")
	public String save2_1(@ModelAttribute("subject") EceTwoOne eceTwoOne, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title","ECE 2-1");
		model.addAttribute("pageTitle", "ECE 2nd year 1st semister");
		model.addAttribute("secondYearActive", "active");
		if (key != null && !key.isBlank()) {

			model.addAttribute("subject", eceTwoOne);
			model.addAttribute("action", "/ece/2-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "ece/subjectform";
		}
		String response = preProcess(eceTwoOne);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eceTwoOne);
			model.addAttribute("action", "/ece/2-1/save");
			model.addAttribute("message", response);
			return "ece/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eceTwoOne.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eceTwoOne);
			model.addAttribute("action", "/ece/2-1/save");
			model.addAttribute("message", "No faculty found with id, " + eceTwoOne.getFacultyRegId());
			return "ece/subjectform";
		}

		if (eceSecondYearService.saveIn2_1(eceTwoOne)) {
			redirectAttributes.addFlashAttribute("message", eceTwoOne.getSubjectName() + " added!");
			return "redirect:/ece/2-1";
		} else {
			model.addAttribute("subject", eceTwoOne);
			model.addAttribute("action", "/ece/2-1/save");
			model.addAttribute("message", "subject might exits!");
			return "ece/subjectform";
		}
	}

	@RequestMapping("/ece/2-2/save")
	public String save2_2(@ModelAttribute("subject") EceTwoTwo eceTwoTwo, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title","ECE 2-2");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("pageTitle", "ECE 2nd year 2nd semister");
		if (key != null && !key.isBlank()) {
			model.addAttribute("subject", eceTwoTwo);
			model.addAttribute("action", "/ece/2-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "ece/subjectform";
		}

		String response = preProcess(eceTwoTwo);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eceTwoTwo);
			model.addAttribute("action", "/ece/2-2/save");
			model.addAttribute("message", response);
			return "ece/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eceTwoTwo.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eceTwoTwo);
			model.addAttribute("action", "/ece/2-2/save");
			model.addAttribute("message", "No faculty found with id, " + eceTwoTwo.getFacultyRegId());
			return "ece/subjectform";
		}

		if (eceSecondYearService.saveIn2_2(eceTwoTwo)) {
			redirectAttributes.addFlashAttribute("message", eceTwoTwo.getSubjectName() + " added!");
			return "redirect:/ece/2-2";
		} else {
			model.addAttribute("subject", eceTwoTwo);
			model.addAttribute("action", "/ece/2-2/save");
			model.addAttribute("message", "subject might exits!");
			return "ece/subjectform";
		}
	}

	/* Subject info */
	@GetMapping("/ece/2/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = eceSecondYearService.findBySubjectCode(subjectCode);
		if (subject.getTotal() == null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " has no feedback.");
			return "redirect:/ece/2-1";
		}
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if (faculty == null) {
			redirectAttributes.addFlashAttribute("message", "No faculty found with id, " + subject.getFacultyRegId());
			return "redirect:/ece/2-1";
		}
		model.addAttribute("faculty", faculty);
		model.addAttribute("title", subject.getSubjectName());
		model.addAttribute("pageTitle", faculty.getFirstName() + " " + faculty.getLastName());
		model.addAttribute("subjectName", subject.getSubjectName());
		List<Float> questionCounters = new ArrayList<>();
		for (String q : subject.getTotal().split(" ")) {
			questionCounters.add(Float.parseFloat(q));
		}
		model.addAttribute("questionCounters", questionCounters);
		model.addAttribute("q1", questionCounters.get(0) * 20);
		model.addAttribute("q2", questionCounters.get(1) * 20);
		model.addAttribute("q3", questionCounters.get(2) * 20);
		model.addAttribute("q4", questionCounters.get(3) * 20);
		model.addAttribute("q5", questionCounters.get(4) * 20);
		model.addAttribute("remarks",
				eceFeedbackService.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId()));
		model.addAttribute("questions", questionService.listAll());
		return "ece/subjectinfo";
	}

	@GetMapping("/ece/2-1/update/{id}")
	public String update2_1(@PathVariable Long id, Model model) {
		EceTwoOne subject = eceSecondYearService.getOneById(id);
		model.addAttribute("pageTitle", "ECE 2nd year 1st semister");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/ece/faculty/search/1");
		model.addAttribute("action", "/ece/2-1/save");
		return "ece/subjectForm";
	}

	@GetMapping("/ece/2-2/update/{id}")
	public String update2_2(@PathVariable Long id, Model model) {
		EceTwoTwo subject = eceSecondYearService.getTwoById(id);
		model.addAttribute("pageTitle", "ECE 2nd year 2nd semister");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/ece/faculty/search/2");
		model.addAttribute("action", "/ece/2-2/save");
		return "ece/subjectForm";
	}

	@GetMapping("/ece/2-1/delete/{id}")
	public String delete2_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EceTwoOne subject = eceSecondYearService.getOneById(id);
		if (eceSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/ece/2-1";
	}

	@GetMapping("/ece/2-2/delete/{id}")
	public String delete2_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EceTwoTwo subject = eceSecondYearService.getTwoById(id);
		if (eceSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/ece/2-2";
	}

	@GetMapping("/ece/2-1/refactor")
	public String refactor2_1() {
		eceSecondYearService.refactor2_1();
		return "redirect:/ece/2-1";
	}

	@GetMapping("/ece/2-2/refactor")
	public String refactor2_2() {
		eceSecondYearService.refactor2_2();
		return "redirect:/ece/2-2";
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
