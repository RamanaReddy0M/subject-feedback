package in.kvsr.admin.students;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.common.entity.Student;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService studentService;


	@GetMapping("/students")
	public String studentPage(Model model) {
		if(!isAuthenticated()) {
			return "redirect:/login";
		}
		model.addAttribute("title","students");
		model.addAttribute("studentActive","active");
		List<Student> studentList = studentService.listAll();
		if(studentList != null) {
			model.addAttribute("studentList", studentList);
		}
		return "adminstudent";
	}
	
	@GetMapping("/students/{sortOrder}")
	public String studentPage(Model model, @PathVariable String sortOrder) {
		model.addAttribute("title","students");
		model.addAttribute("studentActive","active");
		List<Student> studentList = studentService.listAllInSortOrder(sortOrder);
		if(studentList != null) {
			model.addAttribute("studentList", studentList);
		}
		return "adminstudent";
	}
	@GetMapping("/student/search")
	public String studentSearch(Model model, @RequestParam String key) {
		//System.out.println("key: "+key);
		if(key.trim()==null || key.trim()=="") {
			return "redirect:/students";
		}
		model.addAttribute("title","students");
		model.addAttribute("studentActive","active");
		List<Student> studentList = studentService.searchByRollNoAndName(key);
		if(studentList != null) {
			model.addAttribute("studentList", studentList);
		}
		return "adminstudent";
	}
	
	
	@GetMapping({"/student/refactor-id"})
	public String studentRefactorId(RedirectAttributes redirectAttributes) {
		studentService.refactorIdColumn();
		return "redirect:/students";
	}
	
	@GetMapping("/student/delete/{id}")
	public String deleteFaculty(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Student student = studentService.getById(id);
		if(studentService.deleteByID(id)) {
			redirectAttributes.addFlashAttribute("message", student.getFirstName()+" deleted!");
			return "redirect:/students";
		}
		redirectAttributes.addFlashAttribute("message", "try again!");
		return "redirect:/students";
	}
	
	@GetMapping("/student/update/{id}")
	public String updateStudent(@PathVariable Long id, Model model,RedirectAttributes redirectAttributes) {
		Student student = studentService.getById(id);
		if(student!=null) {
			model.addAttribute("student", student);
			return "studentregistration";
		}
		return "redirect:/student";
	}

	@PostMapping("/student/save")
	public String saveUser(@ModelAttribute("student") Student student,
			RedirectAttributes redirectAttributes, Model model) {
		if(!validateStudent(student)) {
			model.addAttribute("student", student);
			model.addAttribute("message", "Form validation failed!");
			return "studentregistration";
		}
		String str = null;
		if(student.getId() != null) {
			if(studentService.save(student)) {
				str = "Student, "+student.getFirstName()+" "+student.getLastName()+
					      " details, updated!";
			}else {
				str="field error!";
			}
		}else {
			str = "try again, "+student.getFirstName()+" "+ student.getLastName()+".";
		}
		
		redirectAttributes.addFlashAttribute("message", str);
		return "redirect:/students";
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
