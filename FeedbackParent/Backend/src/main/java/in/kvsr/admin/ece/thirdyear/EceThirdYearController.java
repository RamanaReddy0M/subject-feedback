package in.kvsr.admin.ece.thirdyear;

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
import in.kvsr.common.entity.ece.EceThreeOne;
import in.kvsr.common.entity.ece.EceThreeTwo;

@Controller
public class EceThirdYearController {

	@Autowired
	private EceThirdYearService eceThirdYearService;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private EceFeedbackService eceFeedbackService;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/ece/3-1")
	public String ece3_1(Model model) {
		model.addAttribute("title", "Ece 3-1");
		model.addAttribute("pageTitle", "Third Year ECE Subjects");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("semister1", "/ece/3-1");
		model.addAttribute("semister2", "/ece/3-2");
		model.addAttribute("refactorId", "/ece/3-1/refactor");
		model.addAttribute("firstActive", "sub_active");
		model.addAttribute("add", "/ece/3-1/add");
		model.addAttribute("delete", "/ece/3-1/delete/");
		model.addAttribute("update", "/ece/3-1/update/");
		model.addAttribute("info", "/ece/3/s-code/");
		model.addAttribute("subjects", eceThirdYearService.listAllOf3_1());
		return "ece/subjects";
	}

	@GetMapping("/ece/3-2")
	public String ece3_2(Model model) {
		model.addAttribute("title", "ece 3-2");
		model.addAttribute("pageTitle", "Third Year ECE Subjects");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("semister1", "/ece/3-1");
		model.addAttribute("semister2", "/ece/3-2");
		model.addAttribute("refactorId", "/ece/3-2/refactor");
		model.addAttribute("secondActive", "sub_active");
		model.addAttribute("add", "/ece/3-2/add");
		model.addAttribute("delete", "/ece/3-2/delete/");
		model.addAttribute("update", "/ece/3-2/update/");
		model.addAttribute("info", "/ece/3/s-code/");
		model.addAttribute("subjects", eceThirdYearService.listAllOf3_2());
		return "ece/subjects";
	}

	@GetMapping("/ece/3-1/add")
	public String ece3_1_add(Model model) {
		model.addAttribute("title","ECE 3-1");
		model.addAttribute("pageTitle", "ECE 3rd year 1st semister");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", new EceThreeOne());
		model.addAttribute("action", "/ece/3-1/save");
		return "ece/subjectform";
	}

	@GetMapping("/ece/3-2/add")
	public String ece3_2_add(Model model) {
		model.addAttribute("title","ECE 3-2");
		model.addAttribute("pageTitle", "ECE 3rd year 2nd semister");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", new EceThreeTwo());
		model.addAttribute("action", "/ece/3-2/save");
		return "ece/subjectform";
	}

	@RequestMapping("/ece/3-1/save")
	public String save3_1(@ModelAttribute("subject") EceThreeOne eceThreeOne, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title","ECE 3-1");
		model.addAttribute("pageTitle", "ECE 2nd year 1st semister");
		model.addAttribute("thirdYearActive", "active");
		if (key != null && !key.isBlank()) {

			model.addAttribute("subject", eceThreeOne);
			model.addAttribute("action", "/ece/3-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "ece/subjectform";
		}
		String response = preProcess(eceThreeOne);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eceThreeOne);
			model.addAttribute("action", "/ece/3-1/save");
			model.addAttribute("message", response);
			return "ece/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eceThreeOne.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eceThreeOne);
			model.addAttribute("action", "/ece/3-1/save");
			model.addAttribute("message", "No faculty found with id, " + eceThreeOne.getFacultyRegId());
			return "ece/subjectform";
		}

		if (eceThirdYearService.saveIn3_1(eceThreeOne)) {
			redirectAttributes.addFlashAttribute("message", eceThreeOne.getSubjectName() + " added!");
			return "redirect:/ece/3-1";
		} else {
			model.addAttribute("subject", eceThreeOne);
			model.addAttribute("action", "/ece/3-1/save");
			model.addAttribute("message", "subject might exits!");
			return "ece/subjectform";
		}
	}

	@RequestMapping("/ece/3-2/save")
	public String save3_2(@ModelAttribute("subject") EceThreeTwo eceThreeTwo, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title","ECE 3-2");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("pageTitle", "ECE 2nd year 2nd semister");
		if (key != null && !key.isBlank()) {
			model.addAttribute("subject", eceThreeTwo);
			model.addAttribute("action", "/ece/3-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "ece/subjectform";
		}

		String response = preProcess(eceThreeTwo);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eceThreeTwo);
			model.addAttribute("action", "/ece/3-2/save");
			model.addAttribute("message", response);
			return "ece/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eceThreeTwo.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eceThreeTwo);
			model.addAttribute("action", "/ece/3-2/save");
			model.addAttribute("message", "No faculty found with id, " + eceThreeTwo.getFacultyRegId());
			return "ece/subjectform";
		}

		if (eceThirdYearService.saveIn3_2(eceThreeTwo)) {
			redirectAttributes.addFlashAttribute("message", eceThreeTwo.getSubjectName() + " added!");
			return "redirect:/ece/3-2";
		} else {
			model.addAttribute("subject", eceThreeTwo);
			model.addAttribute("action", "/ece/3-2/save");
			model.addAttribute("message", "subject might exits!");
			return "ece/subjectform";
		}
	}

	/* Subject info */
	@GetMapping("/ece/3/s-code/{subjectCode}")
	public String subjectInfo3(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = eceThirdYearService.findBySubjectCode(subjectCode);
		if (subject.getTotal() == null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " has no feedback.");
			return "redirect:/ece/3-1";
		}
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if (faculty == null) {
			redirectAttributes.addFlashAttribute("message", "No faculty found with id, " + subject.getFacultyRegId());
			return "redirect:/ece/3-1";
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

	@GetMapping("/ece/3-1/update/{id}")
	public String update3_1(@PathVariable Long id, Model model) {
		EceThreeOne subject = eceThirdYearService.getOneById(id);
		model.addAttribute("pageTitle", "ECE 2nd year 1st semister");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/ece/faculty/search/1");
		model.addAttribute("action", "/ece/3-1/save");
		return "ece/subjectForm";
	}

	@GetMapping("/ece/3-2/update/{id}")
	public String update3_2(@PathVariable Long id, Model model) {
		EceThreeTwo subject = eceThirdYearService.getTwoById(id);
		model.addAttribute("pageTitle", "ECE 2nd year 2nd semister");
		model.addAttribute("thirdYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/ece/faculty/search/2");
		model.addAttribute("action", "/ece/3-2/save");
		return "ece/subjectForm";
	}

	@GetMapping("/ece/3-1/delete/{id}")
	public String delete3_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EceThreeOne subject = eceThirdYearService.getOneById(id);
		if (eceThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/ece/3-1";
	}

	@GetMapping("/ece/3-2/delete/{id}")
	public String delete3_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EceThreeTwo subject = eceThirdYearService.getTwoById(id);
		if (eceThirdYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/ece/3-2";
	}

	@GetMapping("/ece/3-1/refactor")
	public String refactor3_1() {
		eceThirdYearService.refactor3_1();
		return "redirect:/ece/3-1";
	}

	@GetMapping("/ece/3-2/refactor")
	public String refactor3_2() {
		eceThirdYearService.refactor3_2();
		return "redirect:/ece/3-2";
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
