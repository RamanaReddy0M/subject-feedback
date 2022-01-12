package in.kvsr.student;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.HandSFeedback;
import in.kvsr.common.entity.Student;
import in.kvsr.common.entity.Subject;
import in.kvsr.common.entity.civil.CivilFeedback;
import in.kvsr.common.entity.cse.CseFeedback;
import in.kvsr.common.entity.ece.EceFeedback;
import in.kvsr.common.entity.eee.EeeFeedback;
import in.kvsr.common.entity.mechanical.MechanicalFeedback;
import in.kvsr.questions.QuestionService;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/student")
	public String homePage(Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
		if(!isAuthenticated()) {
			return "redirect:/login";
		}
		model.addAttribute("title","student");
		Student student = studentService.getByRollNumber(authentication.getName());
		model.addAttribute("student",student);
		Map<Faculty, List<Subject>> facultyMap = studentService.getFaculty(student);
		model.addAttribute("facultyMap", facultyMap);
		model.addAttribute("facultyMap", facultyMap);
		return "studenthomepage";
	}
	
	@GetMapping("/student/profile")
	public String profile(Model model, Authentication authentication) {
		if(!isAuthenticated()) {
			return "redirect:/login";
		}
		model.addAttribute("title","profile");
		model.addAttribute("profileActive","active");
		Student student = studentService.getByRollNumber(authentication.getName());
		model.addAttribute("student",student);
		return "studentprofile";
	}
	
	@PostMapping("/save-student")
	public String saveStudent(@ModelAttribute Student student, Model model, RedirectAttributes redirectAttributes) {
		if(!validateStudent(student)) {
			model.addAttribute("student", student);
			model.addAttribute("message", "Form validation failed!");
			return "studentregistration";
		}
		if(student.getId() > 0L && (student.getPassword().trim()==null || student.getPassword().isBlank())) {
			if(studentService.save(student)){
				redirectAttributes.addFlashAttribute("updated", "true");
				return "redirect:/student";
			}else {
				model.addAttribute("message", "try again!");
				model.addAttribute("student", student);
				return "studentprofile";
			}
		}
		boolean isNew = (student.getId() > 0L) ? false : true;
		if(studentService.save(student)) {
			if(isNew) 
				redirectAttributes.addFlashAttribute("message","Account created, Please login!");
			else 
				redirectAttributes.addFlashAttribute("message","Account details updated, please login!");
			return "redirect:/login";
		}else {
			model.addAttribute("student", student);
			model.addAttribute("message", "Field error, person might exits!");
			return "studentregistration";
		}
	}
	
	@GetMapping("/questions/{facultyRegId}/{subjectCode}")
	public String questions(@PathVariable String facultyRegId,@PathVariable String subjectCode, Model model, Authentication authentication) {
		
		Student student = studentService.getByRollNumber(authentication.getName());
		String semister = student.getSemister(), branch = student.getBranch();
		if((semister.equalsIgnoreCase("1-1") || semister.equalsIgnoreCase("1-2"))) {
			HandSFeedback feedback = new HandSFeedback();
			feedback.setStudentId(student.getId());
			feedback.setFacultyRegId(facultyRegId);
			feedback.setSubjectCode(subjectCode);
			model.addAttribute("feedback", feedback);
			model.addAttribute("department", "H&S");
		}else {
			switch (branch) {
			case "CSE":
				CseFeedback cseFeedback = new CseFeedback();
				cseFeedback.setStudentId(student.getId());
				cseFeedback.setFacultyRegId(facultyRegId);
				cseFeedback.setSubjectCode(subjectCode);
				model.addAttribute("feedback", cseFeedback);
				model.addAttribute("department", "CSE");
				break;
			case "ECE": 
				EceFeedback eceFeedback = new EceFeedback();
				eceFeedback.setStudentId(student.getId());
				eceFeedback.setFacultyRegId(facultyRegId);
				eceFeedback.setSubjectCode(subjectCode);
				model.addAttribute("feedback", eceFeedback);
				model.addAttribute("department", "ECE");
				break;
			case "CIVIL":
				CivilFeedback civilFeedback = new CivilFeedback();
				civilFeedback.setStudentId(student.getId());
				civilFeedback.setFacultyRegId(facultyRegId);
				civilFeedback.setSubjectCode(subjectCode);
				model.addAttribute("feedback", civilFeedback);
				model.addAttribute("department", "CIVIL");
				break;
			case "EEE": 
				EeeFeedback eeeFeedback = new EeeFeedback();
				eeeFeedback.setStudentId(student.getId());
				eeeFeedback.setFacultyRegId(facultyRegId);
				eeeFeedback.setSubjectCode(subjectCode);
				model.addAttribute("feedback", eeeFeedback);
				model.addAttribute("department", "EEE");
				break;
			case "MECHANICAL": 
				MechanicalFeedback mechanicalFeedback = new MechanicalFeedback();
				mechanicalFeedback.setStudentId(student.getId());
				mechanicalFeedback.setFacultyRegId(facultyRegId);
				mechanicalFeedback.setSubjectCode(subjectCode);
				model.addAttribute("feedback", mechanicalFeedback);
				model.addAttribute("department", "MECHANICAL");
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + subjectCode);
			}
		}
		model.addAttribute("title", "questions");
	    model.addAttribute("questionList", questionService.listAll());
		return "questions";
	}
	
	
	private boolean validateStudent(Student student) {
		student.setFirstName(student.getFirstName().trim());
		if(student.getFirstName()==null || student.getFirstName().isBlank()) {
			return false;
		}
		student.setLastName(student.getLastName().trim());
		if(student.getLastName()==null || student.getLastName().isBlank()) {
	    	   return false;
			}
		student.setRollNumber(student.getRollNumber().trim().toUpperCase());
       if(student.getRollNumber()==null || student.getRollNumber().isBlank()) {
    	   return false;
		}
	   student.setPassword(student.getPassword().trim());
       if((student.getId() <= 0L) && (student.getPassword()==null || student.getPassword().isBlank())) {
    	   return false;
       }
       return true;
	}
	private  boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return false;
		}
		return true;
	}
}
