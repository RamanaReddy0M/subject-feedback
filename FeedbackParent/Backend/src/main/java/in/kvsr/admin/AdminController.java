package in.kvsr.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.admin.faculty.FacultyService;
import in.kvsr.common.entity.Admin;
import in.kvsr.common.entity.Faculty;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private FacultyService facultyService;
	
	@GetMapping("/settings")
	public String settingsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/login";
		}
		model.addAttribute("title", "settings");
		model.addAttribute("settingsActive", "active");
		//card details
		Faculty faculty = (Faculty) facultyService.getByRegId(authentication.getName());
		Admin admin = adminService.getByRegId(authentication.getName());
		if(admin==null) {
			return "redirect:/faculty";
		}
		model.addAttribute("admin", admin);
		String displayProperty="none";
		if(faculty != null) {
			displayProperty = "block";
			model.addAttribute("faculty",faculty);		
		}
		model.addAttribute("displayProperty",displayProperty);
		return "adminsettings";
	}
	@PostMapping("/save")
	public String saveAdminChanges(@ModelAttribute Admin admin, RedirectAttributes redirectAttributes) {
		String message=admin.getRegId();
		admin.setRegId(message.trim().toUpperCase());
		message = admin.getPassword();
		admin.setPassword(message.trim());
		message=null;
		if(admin.getId() > 0L) {
			if(!admin.isEnabled()) {
				message = "check box needs to be enabled!";
				redirectAttributes.addFlashAttribute("message", message);
				return "redirect:/settings";
			}
			if(adminService.save(admin)) {
				message = "Admin credentails updated!";
				redirectAttributes.addFlashAttribute("message", message);
				return "redirect:/logout";
			}
			message = "Field error!";
			redirectAttributes.addFlashAttribute("message", message);
			return "redirect:/settings";
			
		}else {
			message = "try again!";
			redirectAttributes.addFlashAttribute("message", message);
			return "redirect:/settings";
		}
	}
	
}
