package in.kvsr.admin.hod;

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

import java.util.ArrayList;
import java.util.List;
import in.kvsr.admin.faculty.FacultyService;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.HOD;

@Controller
public class HODController {
	
	@Autowired
	private HODService hodService;
	@Autowired
	private FacultyService facultyService;
	
	@GetMapping("/hod")
	public String viewHodPage(Model model) {
		if(!isAuthenticated()) {
			return "redirect:/login";
		}
		model.addAttribute("title", "hod-mgmt");
		model.addAttribute("hodActive", "active");
		List<HOD> hodList = hodService.listAll();
		model.addAttribute("newHOD", new HOD());
		List<Faculty> facultyList = new ArrayList<>();
		for(HOD hod : hodList) {
			facultyList.add(facultyService.getByRegId(hod.getRegId()));
		}
		model.addAttribute("facultyList", facultyList);
		return "adminhod";
	}
	
	@PostMapping("/hod/save")
	public String save(@ModelAttribute("newHOD") HOD hod, RedirectAttributes redirectAttributes) {
		
		Faculty faculty = facultyService.getByRegId(hod.getRegId().trim());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","Soryy! no faculty found with id, "+hod.getRegId());
			return "redirect:/hod";
		}
		hod.setRegId(hod.getRegId().trim());
		hod.setPassword(faculty.getPassword());
		if(hodService.save(hod)) {
			redirectAttributes.addFlashAttribute("message","Added hod with id, "+hod.getRegId()+"!");
			return "redirect:/hod";
		}
		redirectAttributes.addFlashAttribute("message","field error, person might exists!");
		return "redirect:/hod";
	}
	
	@GetMapping("/hod/refactor-id")
	public String truncate(RedirectAttributes redirectAttributes) {
		if(hodService.refactorIdColumn()) {
			return "redirect:/hod";
		}
		redirectAttributes.addFlashAttribute("message","try again!");
		return "redirect:/hod";
	}
	
	@GetMapping("/hod/delete/{regId}")
	public String delete(@PathVariable String regId, RedirectAttributes redirectAttributes) {
		if(hodService.deleteByRegId(regId.trim())) {
			redirectAttributes.addFlashAttribute("message","Hod with id, "+regId+" deleted!");
			return "redirect:/hod";
		}
		redirectAttributes.addFlashAttribute("message","try again!");
		return "redirect:/hod";
	}
	
	private  boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return false;
		}
		return true;
	}
	
}
