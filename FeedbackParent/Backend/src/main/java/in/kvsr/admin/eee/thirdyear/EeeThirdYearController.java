package in.kvsr.admin.eee.thirdyear;

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
import in.kvsr.common.entity.eee.EeeThreeOne;
import in.kvsr.common.entity.eee.EeeThreeTwo;

@Controller
public class EeeThirdYearController {

	@Autowired
	private EeeThirdYearService eeeThirdYearService;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private EeeFeedbackService eeeFeedbackService;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/eee/3-1")
	public String eee3_1(Model model) {
		model.addAttribute("title", "Eee 3-1");
		model.addAttribute("pageTitle", "Third Year EEE Subjects");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("semister1", "/eee/3-1");
		model.addAttribute("semister2", "/eee/3-2");
		model.addAttribute("refactorId", "/eee/3-1/refactor");
		model.addAttribute("firstActive", "sub_active");
		model.addAttribute("add", "/eee/3-1/add");
		model.addAttribute("delete", "/eee/3-1/delete/");
		model.addAttribute("update", "/eee/3-1/update/");
		model.addAttribute("info", "/eee/3/s-code/");
		model.addAttribute("subjects", eeeThirdYearService.listAllOf3_1());
		return "eee/subjects";
	}

	@GetMapping("/eee/3-2")
	public String eee3_2(Model model) {
		model.addAttribute("title", "eee 3-2");
		model.addAttribute("pageTitle", "Third Year EEE Subjects");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("semister1", "/eee/3-1");
		model.addAttribute("semister2", "/eee/3-2");
		model.addAttribute("refactorId", "/eee/3-2/refactor");
		model.addAttribute("secondActive", "sub_active");
		model.addAttribute("add", "/eee/3-2/add");
		model.addAttribute("delete", "/eee/3-2/delete/");
		model.addAttribute("update", "/eee/3-2/update/");
		model.addAttribute("info", "/eee/3/s-code/");
		model.addAttribute("subjects", eeeThirdYearService.listAllOf3_2());
		return "eee/subjects";
	}

	@GetMapping("/eee/3-1/add")
	public String eee3_1_add(Model model) {
		model.addAttribute("title", "Eee 3-1");
		model.addAttribute("pageTitle", "EEE 3rd year 1st semister");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", new EeeThreeOne());
		model.addAttribute("action", "/eee/3-1/save");
		return "eee/subjectform";
	}

	@GetMapping("/eee/3-2/add")
	public String eee3_2_add(Model model) {
		model.addAttribute("title", "Eee 3-2");
		model.addAttribute("pageTitle", "EEE 3rd year 2nd semister");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", new EeeThreeTwo());
		model.addAttribute("action", "/eee/3-2/save");
		return "eee/subjectform";
	}

	@RequestMapping("/eee/3-1/save")
	public String save3_1(@ModelAttribute("subject") EeeThreeOne eeeThreeOne, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title", "Eee 3-1");
		model.addAttribute("pageTitle", "EEE 2nd year 1st semister");
		model.addAttribute("thirdYearActive", "active");
		if (key != null && !key.isBlank()) {

			model.addAttribute("subject", eeeThreeOne);
			model.addAttribute("action", "/eee/3-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "eee/subjectform";
		}
		String response = preProcess(eeeThreeOne);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eeeThreeOne);
			model.addAttribute("action", "/eee/3-1/save");
			model.addAttribute("message", response);
			return "eee/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eeeThreeOne.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eeeThreeOne);
			model.addAttribute("action", "/eee/3-1/save");
			model.addAttribute("message", "No faculty found with id, " + eeeThreeOne.getFacultyRegId());
			return "eee/subjectform";
		}

		if (eeeThirdYearService.saveIn3_1(eeeThreeOne)) {
			redirectAttributes.addFlashAttribute("message", eeeThreeOne.getSubjectName() + " added!");
			return "redirect:/eee/3-1";
		} else {
			model.addAttribute("subject", eeeThreeOne);
			model.addAttribute("action", "/eee/3-1/save");
			model.addAttribute("message", "subject might exits!");
			return "eee/subjectform";
		}
	}

	@RequestMapping("/eee/3-2/save")
	public String save3_2(@ModelAttribute("subject") EeeThreeTwo eeeThreeTwo, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title", "Eee 3-2");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("pageTitle", "EEE 2nd year 2nd semister");
		if (key != null && !key.isBlank()) {
			model.addAttribute("subject", eeeThreeTwo);
			model.addAttribute("action", "/eee/3-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "eee/subjectform";
		}

		String response = preProcess(eeeThreeTwo);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eeeThreeTwo);
			model.addAttribute("action", "/eee/3-2/save");
			model.addAttribute("message", response);
			return "eee/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eeeThreeTwo.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eeeThreeTwo);
			model.addAttribute("action", "/eee/3-2/save");
			model.addAttribute("message", "No faculty found with id, " + eeeThreeTwo.getFacultyRegId());
			return "eee/subjectform";
		}

		if (eeeThirdYearService.saveIn3_2(eeeThreeTwo)) {
			redirectAttributes.addFlashAttribute("message", eeeThreeTwo.getSubjectName() + " added!");
			return "redirect:/eee/3-2";
		} else {
			model.addAttribute("subject", eeeThreeTwo);
			model.addAttribute("action", "/eee/3-2/save");
			model.addAttribute("message", "subject might exits!");
			return "eee/subjectform";
		}
	}

	/* Subject info */
	@GetMapping("/eee/3/s-code/{subjectCode}")
	public String subjectInfo3(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = eeeThirdYearService.findBySubjectCode(subjectCode);
		if (subject.getTotal() == null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " has no feedback.");
			return "redirect:/eee/3-1";
		}
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if (faculty == null) {
			redirectAttributes.addFlashAttribute("message", "No faculty found with id, " + subject.getFacultyRegId());
			return "redirect:/eee/3-1";
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

	@GetMapping("/eee/3-1/update/{id}")
	public String update3_1(@PathVariable Long id, Model model) {
		EeeThreeOne subject = eeeThirdYearService.getOneById(id);
		model.addAttribute("pageTitle", "EEE 2nd year 1st semister");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/eee/faculty/search/1");
		model.addAttribute("action", "/eee/3-1/save");
		return "eee/subjectForm";
	}

	@GetMapping("/eee/3-2/update/{id}")
	public String update3_2(@PathVariable Long id, Model model) {
		EeeThreeTwo subject = eeeThirdYearService.getTwoById(id);
		model.addAttribute("pageTitle", "EEE 2nd year 2nd semister");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/eee/faculty/search/2");
		model.addAttribute("action", "/eee/3-2/save");
		return "eee/subjectForm";
	}

	@GetMapping("/eee/3-1/delete/{id}")
	public String delete3_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EeeThreeOne subject = eeeThirdYearService.getOneById(id);
		if (eeeThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/eee/3-1";
	}

	@GetMapping("/eee/3-2/delete/{id}")
	public String delete3_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EeeThreeTwo subject = eeeThirdYearService.getTwoById(id);
		if (eeeThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/eee/3-2";
	}

	@GetMapping("/eee/3-1/refactor")
	public String refactor3_1() {
		eeeThirdYearService.refactor3_1();
		return "redirect:/eee/3-1";
	}

	@GetMapping("/eee/3-2/refactor")
	public String refactor3_2() {
		eeeThirdYearService.refactor3_2();
		return "redirect:/eee/3-2";
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
