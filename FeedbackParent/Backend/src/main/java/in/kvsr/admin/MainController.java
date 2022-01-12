package in.kvsr.admin;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class MainController {
	
	@GetMapping("")
	public String facultyPage() {
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/login";
		}
		return "redirect:/faculty";
	}
	
    @GetMapping("/login")
    public String loginPage() {
    	return "login";
    }
    
    @GetMapping("/signUp")
	public @ResponseBody String signUp() {
		return "signup";
	}
	
	@GetMapping("/signout")
	public @ResponseBody String signoutPage() {
		return "bye bye";
	}
	
	
}
