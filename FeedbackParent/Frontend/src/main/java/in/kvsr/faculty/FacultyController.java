package in.kvsr.faculty;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.FileUploadUtil;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Subject;
import in.kvsr.questions.QuestionService;

@Controller
public class FacultyController {
	
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/faculty")
	public String homePage(Model model, Authentication authentication) {
		if(!isAuthenticated()) {
			return "redirect:/login";
		}
		model.addAttribute("title","faculty");
		
		Faculty faculty = facultyService.getByRegId(authentication.getName());
		model.addAttribute("faculty",faculty);
		Map<Subject,RemarkAndQuestionList> subjectMap = new LinkedHashMap<>();
		facultyService.getSubjects(faculty, subjectMap);
		model.addAttribute("subjectMap",subjectMap);
		model.addAttribute("questions", questionService.listAll());
		return "facultyhomepage";
	}
	
	@GetMapping("/faculty/profile")
	public String profile(Model model, Authentication authentication) {
		
		if(!isAuthenticated()) {
			return "redirect:/login";
		}
		model.addAttribute("title","profile");
		model.addAttribute("profileActive","active");
		Faculty faculty = facultyService.getByRegId(authentication.getName());
		model.addAttribute("faculty",faculty);
		return "facultyprofile";
	}
	
	@PostMapping("/save-faculty")
	public String saveUser(@ModelAttribute("faculty") Faculty faculty,
					RedirectAttributes redirectAttributes,
					Model model, 
					@RequestParam("facultyImage") MultipartFile multipartFile
					) throws IOException {
		   String str = null, uploadDir=null, fileName=null;
		    if(!validateFaculty(faculty)) {
		    	model.addAttribute("message","Form validation failed!");
		    	model.addAttribute("faculty","faculty");
		    	return "facultyregistration";
		    }
		    if (!multipartFile.isEmpty()) {
				 uploadDir = "faculty-images/"+faculty.getRegId();
			     fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename().trim().replaceAll("\\s","%20"));
			     faculty.setPhoto(fileName);
			}
			
			if ((faculty.getId() > 0L) && (faculty.getPassword() == null || faculty.getPassword().isBlank())) {
				
				if (facultyService.save(faculty)) {
					if(!multipartFile.isEmpty()) {
						FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
					}
					redirectAttributes.addFlashAttribute("message", "Updated!");
					return "redirect:/faculty";
				} else {
					model.addAttribute("message", "field error, person might exists!");
					model.addAttribute("faculty", faculty);
					return "facultyprofile";
				}
			}
			boolean isNew = (faculty.getId() < 1L) ? true : false;
			if(facultyService.save(faculty)) {
				if(!multipartFile.isEmpty()) {
					FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				}
				System.out.println("here:");
				if(isNew) 
					redirectAttributes.addFlashAttribute("message","Account created, Please login!");
				else 
					redirectAttributes.addFlashAttribute("message","Account details updated, please login!");
				return "redirect:/login";
			}else {
				str="field error, person might exists!";
				model.addAttribute("faculty", faculty);
				model.addAttribute("message", str);
				return "facultyregistration";
			}
	}

	private boolean validateFaculty(Faculty faculty) {
		faculty.setFirstName(faculty.getFirstName().trim());
		if (faculty.getFirstName() == null || faculty.getFirstName().isBlank()) {
			return false;
		}
		faculty.setLastName(faculty.getLastName().trim());
		if (faculty.getLastName() == null || faculty.getLastName().isBlank()) {
			return false;
		}
		faculty.setRegId(faculty.getRegId().trim().toUpperCase());
		if (faculty.getRegId() == null || faculty.getRegId().isBlank()) {
			return false;
		}
		faculty.setPassword(faculty.getPassword().trim());
		if ((faculty.getId() <= 0L) && (faculty.getPassword() == null || faculty.getPassword().isBlank())) {
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
