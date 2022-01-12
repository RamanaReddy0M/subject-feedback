package in.kvsr.admin.ece.fourthyear;

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
import in.kvsr.common.entity.ece.EceFourOne;
import in.kvsr.common.entity.ece.EceFourTwo;

@Controller
public class EceFourthYearController {

	@Autowired
	private EceFourthYearService eceFourthYearService;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private EceFeedbackService eceFeedbackService;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/ece/4-1")
	public String ece4_1(Model model) {
		model.addAttribute("title", "Ece 4-1");
		model.addAttribute("pageTitle", "Fourth Year ECE Subjects");
		model.addAttribute("fourthYearActive", "active");
		model.addAttribute("semister1", "/ece/4-1");
		model.addAttribute("semister2", "/ece/4-2");
		model.addAttribute("refactorId", "/ece/4-1/refactor");
		model.addAttribute("firstActive", "sub_active");
		model.addAttribute("add", "/ece/4-1/add");
		model.addAttribute("delete", "/ece/4-1/delete/");
		model.addAttribute("update", "/ece/4-1/update/");
		model.addAttribute("info", "/ece/4/s-code/");
		model.addAttribute("subjects", eceFourthYearService.listAllOf4_1());
		return "ece/subjects";
	}

	@GetMapping("/ece/4-2")
	public String ece4_2(Model model) {
		model.addAttribute("title", "Ece 4-2");
		model.addAttribute("pageTitle", "Fourth Year ECE Subjects");
		model.addAttribute("fourthYearActive", "active");
		model.addAttribute("semister1", "/ece/4-1");
		model.addAttribute("semister2", "/ece/4-2");
		model.addAttribute("refactorId", "/ece/4-2/refactor");
		model.addAttribute("secondActive", "sub_active");
		model.addAttribute("add", "/ece/4-2/add");
		model.addAttribute("delete", "/ece/4-2/delete/");
		model.addAttribute("update", "/ece/4-2/update/");
		model.addAttribute("info", "/ece/4/s-code/");
		model.addAttribute("subjects", eceFourthYearService.listAllOf4_2());
		return "ece/subjects";
	}

	@GetMapping("/ece/4-1/add")
	public String ece4_1_add(Model model) {
		model.addAttribute("title","ECE 4-1");
		model.addAttribute("pageTitle", "ECE 4th year 1st semister");
		model.addAttribute("fourthYearActive", "active");
		model.addAttribute("subject", new EceFourOne());
		model.addAttribute("action", "/ece/4-1/save");
		return "ece/subjectform";
	}

	@GetMapping("/ece/4-2/add")
	public String ece4_2_add(Model model) {
		model.addAttribute("title","ECE 4-2");
		model.addAttribute("pageTitle", "ECE 4th year 2nd semister");
		model.addAttribute("fourthYearActive", "active");
		model.addAttribute("subject", new EceFourTwo());
		model.addAttribute("action", "/ece/4-2/save");
		return "ece/subjectform";
	}

	@RequestMapping("/ece/4-1/save")
	public String save4_1(@ModelAttribute("subject") EceFourOne eceFourOne, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title","ECE 4-1");
		model.addAttribute("pageTitle", "ECE 2nd year 1st semister");
		model.addAttribute("fourthYearActive", "active");
		if (key != null && !key.isBlank()) {

			model.addAttribute("subject", eceFourOne);
			model.addAttribute("action", "/ece/4-1/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "ece/subjectform";
		}
		String response = preProcess(eceFourOne);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eceFourOne);
			model.addAttribute("action", "/ece/4-1/save");
			model.addAttribute("message", response);
			return "ece/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eceFourOne.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eceFourOne);
			model.addAttribute("action", "/ece/4-1/save");
			model.addAttribute("message", "No faculty found with id, " + eceFourOne.getFacultyRegId());
			return "ece/subjectform";
		}

		if (eceFourthYearService.saveIn4_1(eceFourOne)) {
			redirectAttributes.addFlashAttribute("message", eceFourOne.getSubjectName() + " added!");
			return "redirect:/ece/4-1";
		} else {
			model.addAttribute("subject", eceFourOne);
			model.addAttribute("action", "/ece/4-1/save");
			model.addAttribute("message", "subject might exits!");
			return "ece/subjectform";
		}
	}

	@RequestMapping("/ece/4-2/save")
	public String save4_2(@ModelAttribute("subject") EceFourTwo eceFourTwo, RedirectAttributes redirectAttributes,
			Model model, @RequestParam String key) {

		key = key.trim();
		model.addAttribute("title","ECE 4-2");
		model.addAttribute("fourthYearActive", "active");
		model.addAttribute("pageTitle", "ECE 2nd year 2nd semister");
		if (key != null && !key.isBlank()) {
			model.addAttribute("subject", eceFourTwo);
			model.addAttribute("action", "/ece/4-2/save");
			List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
			model.addAttribute("facultyList", facultyList);
			return "ece/subjectform";
		}

		String response = preProcess(eceFourTwo);
		if (!response.equalsIgnoreCase("ok")) {
			model.addAttribute("subject", eceFourTwo);
			model.addAttribute("action", "/ece/4-2/save");
			model.addAttribute("message", response);
			return "ece/subjectform";
		}

		Faculty faculty = facultyService.getByRegId(eceFourTwo.getFacultyRegId());
		if (faculty == null) {
			model.addAttribute("subject", eceFourTwo);
			model.addAttribute("action", "/ece/4-2/save");
			model.addAttribute("message", "No faculty found with id, " + eceFourTwo.getFacultyRegId());
			return "ece/subjectform";
		}

		if (eceFourthYearService.saveIn4_2(eceFourTwo)) {
			redirectAttributes.addFlashAttribute("message", eceFourTwo.getSubjectName() + " added!");
			return "redirect:/ece/4-2";
		} else {
			model.addAttribute("subject", eceFourTwo);
			model.addAttribute("action", "/ece/4-2/save");
			model.addAttribute("message", "subject might exits!");
			return "ece/subjectform";
		}
	}

	/* Subject info */
	@GetMapping("/ece/4/s-code/{subjectCode}")
	public String subjectInfo4(@PathVariable String subjectCode, Model model, RedirectAttributes redirectAttributes) {
		Subject subject = eceFourthYearService.findBySubjectCode(subjectCode);
		if (subject.getTotal() == null || subject.getTotal().isBlank()) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " has no feedback.");
			return "redirect:/ece/4-1";
		}
		model.addAttribute("fourthYearActive", "active");
		model.addAttribute("subject", subject);
		Faculty faculty = facultyService.getByRegId(subject.getFacultyRegId());
		if (faculty == null) {
			redirectAttributes.addFlashAttribute("message", "No faculty found with id, " + subject.getFacultyRegId());
			return "redirect:/ece/4-1";
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

	@GetMapping("/ece/4-1/update/{id}")
	public String update4_1(@PathVariable Long id, Model model) {
		EceFourOne subject = eceFourthYearService.getOneById(id);
		model.addAttribute("pageTitle", "ECE 4th year 1st semister");
		model.addAttribute("fourthYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/ece/faculty/search/1");
		model.addAttribute("action", "/ece/4-1/save");
		return "ece/subjectForm";
	}

	@GetMapping("/ece/4-2/update/{id}")
	public String update4_2(@PathVariable Long id, Model model) {
		EceFourTwo subject = eceFourthYearService.getTwoById(id);
		model.addAttribute("pageTitle", "ECE  year 2nd semister");
		model.addAttribute("fourthYearActive", "active");
		model.addAttribute("subject", subject);
		model.addAttribute("actionForSearch", "/ece/faculty/search/2");
		model.addAttribute("action", "/ece/4-2/save");
		return "ece/subjectForm";
	}

	@GetMapping("/ece/4-1/delete/{id}")
	public String delete4_1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EceFourOne subject = eceFourthYearService.getOneById(id);
		if (eceFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/ece/4-1";
	}

	@GetMapping("/ece/4-2/delete/{id}")
	public String delete4_2(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		EceFourTwo subject = eceFourthYearService.getTwoById(id);
		if (eceFourthYearService.delete(subject)) {
			redirectAttributes.addFlashAttribute("message", subject.getSubjectName() + " deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "try again!");
		}
		return "redirect:/ece/4-2";
	}

	@GetMapping("/ece/4-1/refactor")
	public String refactor4_1() {
		eceFourthYearService.refactor4_1();
		return "redirect:/ece/4-1";
	}

	@GetMapping("/ece/4-2/refactor")
	public String refactor4_2() {
		eceFourthYearService.refactor4_2();
		return "redirect:/ece/4-2";
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
