package in.kvsr;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Student;
/*import in.kvsr.faculty.FacultyService;
import in.kvsr.student.StudentService;*/

@Controller
public class MainController {
	
	/*
	 * @Autowired private StudentService studentService;
	 * 
	 * @Autowired private FacultyService facultyService;
	 */
	
	@GetMapping({"/",""})
	public String viewHomePage() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/login";
		}
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("FACULTY"))) {
			return "redirect:/faculty";
		}else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("STUDENT"))) {
			return "redirect:/student";
		}
		return "error";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup/faculty")
	public String signupFaculty(Model model) {
		Faculty faculty = new Faculty();
		model.addAttribute("faculty",faculty);
		return "facultyregistration";
	}
	
	@GetMapping("/signup/student")
	public String signupStudent(Model model) {
		Student student = new Student();
		model.addAttribute("student",student);
		return "studentregistration";
	}

}
