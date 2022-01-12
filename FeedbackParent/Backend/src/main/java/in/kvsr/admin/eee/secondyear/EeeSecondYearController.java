package in.kvsr.admin.eee.secondyear;

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
import in.kvsr.common.entity.eee.EeeTwoOne;
import in.kvsr.common.entity.eee.EeeTwoTwo;

@Controller
public class EeeSecondYearController {

	@Autowired
	private EeeSecondYearService eeeSecondYearService;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private EeeFeedbackService eeeFeedbackService;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/eee/2-1")
	public String eee2_1(Model model) {
		model.addAttribute("title", "Eee 2-1");
		model.addAttribute("pageTitle", "Second Year EEE Subjects");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("semister1", "/eee/2-1");
		model.addAttribute("semister2", "/eee/2-2");
		model.addAttribute("refactorId", "/eee/2-1/refactor");
		model.addAttribute("firstActive", "sub_active");
		model.addAttribute("add", "/eee/2-1/add");
		model.addAttribute("delete", "/eee/2-1/delete/");
		model.addAttribute("update", "/eee/2-1/update/");
		model.addAttribute("info", "/eee/2/s-code/");
		model.addAttribute("subjects", eeeSecondYearService.listAllOf2_1());
		return "eee/subjects";
	}

	@GetMapping("/eee/2-2")
	public String eee22(Model model) {
		model.addAttribute("title", "eee 2-2");
		model.addAttribute("pageTitle", "Second Year EEE Subjects");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("semister1", "/eee/2-1");
		model.addAttribute("semister2", "/eee/2-2");
		model.addAttribute("refactorId", "/eee/2-2/refactor");
		model.addAttribute("secondActive", "sub_active");
		model.addAttribute("add", "/eee/2-2/add");
		model.addAttribute("delete", "/eee/2-2/delete/");
		model.addAttribute("update", "/eee/2-2/update/");
		model.addAttribute("info", "/eee/2/s-code/");
		model.addAttribute("subjects", eeeSecondYearService.listAllOf2_2());
		return "eee/subjects";
	}

	@GetMapping("/eee/2-1/add")
	public String eee2_1_add(Model model) {
		model.addAttribute("title", "Eee 2-1");
		model.addAttribute("pageTitle", "EEE 2nd year 1st semister");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", new EeeTwoOne());
		model.addAttribute("action", "/eee/2-1/save");
		return "eee/subjectform";
	}

	@GetMapping("/eee/2-2/add")
	public String eee2_2_add(Model model) {
		model.addAttribute("title", "Eee 2-2");
		model.addAttribute("pageTitle", "EEE 2nd year 2nd semister");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", new EeeTwoTwo());
		model.addAttribute("action", "/eee/2-2/save");
		return "eee/subjectform";
	}

	@RequestMapping("/eee/2-1/save")
	public String save2_1(@ModelAttribute("subject") EeeTwoOne eeeTwoOne, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title", "Eee 2-1");
		model.addAttribute("pageTitle", "EEE 2nd year 1st semister");
		model.addAttribute("secondYearActive", "active");
		if (key != null && !key.isBlank()) {

			model.addAttribute("subject", eeeTwoOne);
			model.addAttribute("action", "/eee/2-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "eee/subjectform";
		}
		String response = preProcess(eeeTwoOne);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eeeTwoOne);
			model.addAttribute("action", "/eee/2-1/save");
			model.addAttribute("message", response);
			return "eee/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eeeTwoOne.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eeeTwoOne);
			model.addAttribute("action", "/eee/2-1/save");
			model.addAttribute("message", "No faculty found with id, " + eeeTwoOne.getFacultyRegId());
			return "eee/subjectform";
		}

		if (eeeSecondYearService.saveIn2_1(eeeTwoOne)) {
			redirectAttributes.addFlashAttribute("message", eeeTwoOne.getSubjectName() + " added!");
			return "redirect:/eee/2-1";
		} else {
			model.addAttribute("subject", eeeTwoOne);
			model.addAttribute("action", "/eee/2-1/save");
			model.addAttribute("message", "subject might exits!");
			return "eee/subjectform";
		}
	}

	@RequestMapping("/eee/2-2/save")
	public String save2_2(@ModelAttribute("subject") EeeTwoTwo eeeTwoTwo, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title", "Eee 2-2");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("pageTitle", "EEE 2nd year 2nd semister");
		if (key != null && !key.isBlank()) {
			model.addAttribute("subject", eeeTwoTwo);
			model.addAttribute("action", "/eee/2-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "eee/subjectform";
		}

		String response = preProcess(eeeTwoTwo);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eeeTwoTwo);
			model.addAttribute("action", "/eee/2-2/save");
			model.addAttribute("message", response);
			return "eee/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eeeTwoTwo.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eeeTwoTwo);
			model.addAttribute("action", "/eee/2-2/save");
			model.addAttribute("message", "No faculty found with id, " + eeeTwoTwo.getFacultyRegId());
			return "eee/subjectform";
		}

		if (eeeSecondYearService.saveIn2_2(eeeTwoTwo)) {
			redirectAttributes.addFlashAttribute("message", eeeTwoTwo.getSubjectName() + " added!");
			return "redirect:/eee/2-2";
		} else {
			model.addAttribute("subject", eeeTwoTwo);
			model.addAttribute("action", "/eee/2-2/save");
			model.addAttribute("message", "subject might exits!");
			return "eee/subjectform";
		}
	}

	/* Subject info */
	@GetMapping("/eee/2/s-code/{subjectCode}")
	public String subjectInfo(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = eeeSecondYearService.findBySubjectCode(subjectCode);
		if (subject.getTotal() == null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " has no feedback.");
			return "redirect:/eee/2-1";
		}
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if (faculty == null) {
			redirectAttributes.addFlashAttribute("message", "No faculty found with id, " + subject.getFacultyRegId());
			return "redirect:/eee/2-1";
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
				eeeFeedbackService.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId()));
		model.addAttribute("questions", questionService.listAll());
		return "eee/subjectinfo";
	}

	@GetMapping("/eee/2-1/update/{id}")
	public String update2_1(@PathVariable Long id, Model model) {
		EeeTwoOne subject = eeeSecondYearService.getOneById(id);
		model.addAttribute("pageTitle", "EEE 2nd year 1st semister");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/eee/faculty/search/1");
		model.addAttribute("action", "/eee/2-1/save");
		return "eee/subjectForm";
	}

	@GetMapping("/eee/2-2/update/{id}")
	public String update2_2(@PathVariable Long id, Model model) {
		EeeTwoTwo subject = eeeSecondYearService.getTwoById(id);
		model.addAttribute("pageTitle", "EEE 2nd year 2nd semister");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/eee/faculty/search/2");
		model.addAttribute("action", "/eee/2-2/save");
		return "eee/subjectForm";
	}

	@GetMapping("/eee/2-1/delete/{id}")
	public String delete2_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EeeTwoOne subject = eeeSecondYearService.getOneById(id);
		if (eeeSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/eee/2-1";
	}

	@GetMapping("/eee/2-2/delete/{id}")
	public String delete2_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EeeTwoTwo subject = eeeSecondYearService.getTwoById(id);
		if (eeeSecondYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/eee/2-2";
	}

	@GetMapping("/eee/2-1/refactor")
	public String refactor2_1() {
		eeeSecondYearService.refactor2_1();
		return "redirect:/eee/2-1";
	}

	@GetMapping("/eee/2-2/refactor")
	public String refactor2_2() {
		eeeSecondYearService.refactor2_2();
		return "redirect:/eee/2-2";
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
