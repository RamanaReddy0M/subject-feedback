package in.kvsr.admin.faculty;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.admin.AdminService;
import in.kvsr.common.entity.Admin;
import in.kvsr.common.entity.Faculty;
import in.kvsr.util.FacultyPdfExporter;

@Controller
public class FacultyController {
	
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private AdminService adminService;
	
	
	@GetMapping({"/faculty"})
	public String facultyPage(Model model, Authentication authentication) {
		if(!isAuthenticated()) {
			return "redirect:/login";
		}
		model.addAttribute("title","faculty");
		model.addAttribute("facultyActive","active");
		List<Faculty> facultyList = facultyService.listAll();
		if(facultyList != null) {
			model.addAttribute("facultyList", facultyList);
		}
		return "adminfaculty";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		return "login";
	}
	
	
	
	@GetMapping({"/faculty/{sortOrder}"})
	public String facultyPage(Model model, @PathVariable String sortOrder) {
		model.addAttribute("title","faculty");
		model.addAttribute("facultyActive","active");
		List<Faculty> facultyList = facultyService.listAllInSortOrder(sortOrder);
		if(facultyList != null) {
			model.addAttribute("facultyList", facultyList);
		}
		return "adminfaculty";
	}
	@GetMapping({"/faculty/search"})
	public String facultySearch(Model model, @RequestParam String key) {
		//System.out.println("key: "+key);
		if(key.trim() == null || key.trim() == "") {
			return "redirect:/faculty";
		}
		model.addAttribute("title","faculty");
		model.addAttribute("facultyActive","active");
		List<Faculty> facultyList = facultyService.searchByRegIdAndName(key);
		if(facultyList != null) {
			model.addAttribute("facultyList", facultyList);
		}
		return "adminfaculty";
	}
	//pdf exporter
	
	@GetMapping("/faculty/export-to-pdf")
	public void exportToPdf(HttpServletResponse httpServletResponse) throws IOException {
			List<Faculty> facultyList = facultyService.listAllInSortOrder("department");
			FacultyPdfExporter pdfExporter = new FacultyPdfExporter();
			pdfExporter.export(httpServletResponse, facultyList);
	}
	
	@GetMapping({"/faculty/refactor-id"})
	public String facultyRefactorID(RedirectAttributes redirectAttributes) {
		facultyService.refactorIdColumn();
		return "redirect:/faculty";
	}
	
	@GetMapping("/faculty/delete/{id}")
	public String deleteFaculty(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Faculty faculty = facultyService.getById(id);
		if(facultyService.deleteByID(id)) {
			redirectAttributes.addFlashAttribute("message", faculty.getFirstName()+" deleted!");
			return "redirect:/faculty";
		}
		redirectAttributes.addFlashAttribute("message", "try again!");
		return "redirect:/faculty";
	}
	
	@GetMapping("/faculty/update/{id}")
	public String updateFaculty(@PathVariable Long id, Model model,RedirectAttributes redirectAttributes) {
		Faculty faculty = facultyService.getById(id);
		if(faculty!=null) {
			model.addAttribute("faculty", faculty);
			return "facultyregistration";
		}
		return "redirect:/faculty";
	}

	@PostMapping("/faculty/save")
	public String saveUser(@ModelAttribute("faculty") Faculty faculty,
			RedirectAttributes redirectAttributes, Model model) {
		String str = null;
		
		if(!validateFaculty(faculty)) {
	    	model.addAttribute("message","Form validation failed!");
	    	model.addAttribute("faculty","faculty");
	    	return "facultyregistration";
	    }
		
		
		if(faculty.getId() != null) {
			if(facultyService.save(faculty)) {
				str = "Faculty, "+faculty.getFirstName()+" "+faculty.getLastName()+
					      " details, updated!";
			}else {
				str="field error!";
			}
		}else {
			str = "try again, "+faculty.getFirstName()+" "+ faculty.getLastName()+".";
		}
		
		redirectAttributes.addFlashAttribute("message", str);
		return "redirect:/faculty";
	}
	
	public void generateKey() {
		Admin admin = new Admin("0123456789","0123456789");
		admin.setId(1L);
		adminService.save(admin);
	}
	
	private boolean validateFaculty(Faculty faculty) {
		faculty.setFirstName(faculty.getFirstName().trim());
		if(faculty.getFirstName()==null || faculty.getFirstName().isBlank()) {
			return false;
		}
		faculty.setLastName(faculty.getLastName().trim());
       if(faculty.getLastName()==null || faculty.getLastName().isBlank()) {
			return false;
		}
       faculty.setRegId(faculty.getRegId().trim().toUpperCase());
       if(faculty.getRegId()==null || faculty.getRegId().isBlank()) {
			return false;
		}
       faculty.setPassword(faculty.getPassword().trim());
       if((faculty.getId() <= 0L) && (faculty.getPassword()==null || faculty.getPassword().isBlank())) {
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
