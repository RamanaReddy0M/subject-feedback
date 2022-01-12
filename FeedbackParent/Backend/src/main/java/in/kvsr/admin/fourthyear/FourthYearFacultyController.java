package in.kvsr.admin.fourthyear;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.kvsr.admin.faculty.FacultyService;
import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.FourthYearFaculty;
import in.kvsr.util.FacultyPdfExporter;

@Controller
public class FourthYearFacultyController {
	
	@Autowired
	private FourthYearFacultyService fourthYearFacultyService;
	
	@Autowired
	private FacultyService facultyService;
	
	
	private List<FourthYearFaculty> fourthYearFacultyList=null;
	
	@GetMapping("/fourth-year-faculty")
	public String viewHodPage(Model model) {
		model.addAttribute("title", "fourth-year-faculty");
		model.addAttribute("fourthYearActive", "active");
		model.addAttribute("newFaculty", new FourthYearFaculty());
		fourthYearFacultyList = fourthYearFacultyService.listAll();
		if(fourthYearFacultyList != null) {
			for(FourthYearFaculty fourthYearFaculty: fourthYearFacultyList) {
				Faculty faculty = facultyService.getByRegId(fourthYearFaculty.getRegId());
				if(faculty != null) {
					preProcess(fourthYearFaculty, faculty);					
				}
			}
		}
		
		model.addAttribute("facultyList", fourthYearFacultyList);
		return "fourthyearfaculty";
	}
	
	
	@PostMapping("/fourth-year-faculty/save")
	public String save(@ModelAttribute("newFaculty") FourthYearFaculty fourthYearFaculty, RedirectAttributes redirectAttributes) {
		Faculty faculty = facultyService.getByRegId(fourthYearFaculty.getRegId().trim());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","Soryy! no faculty found with id, "+fourthYearFaculty.getRegId());
			return "redirect:/fourth-year-faculty";
		}
		preProcess(fourthYearFaculty, faculty);
		if(fourthYearFacultyService.save(fourthYearFaculty)) {
			redirectAttributes.addFlashAttribute("message","Added Fourth Year Faculty with id, "+fourthYearFaculty.getRegId()+"!");
			return "redirect:/fourth-year-faculty";
		}
		redirectAttributes.addFlashAttribute("message","field error, person might exists!");
		return "redirect:/fourth-year-faculty";
	}
	
	@GetMapping("/fourth-year-faculty/update/{regId}")
	public String updateByRegId(@PathVariable String regId, Model model) {
		FourthYearFaculty fourthYearFaculty = fourthYearFacultyService.getFacultyByRegId(regId);
		model.addAttribute("year", "(4th Year)");
		model.addAttribute("fourthYearFaculty", fourthYearFaculty);
		return "branchform4";
	}
	
	@PostMapping("/fourth-year-faculty/update")
	public String update(@ModelAttribute FourthYearFaculty fourthYearFaculty, RedirectAttributes redirectAttributes) {
		Faculty faculty = facultyService.getByRegId(fourthYearFaculty.getRegId());
		
		preProcess(fourthYearFaculty, faculty);
		if(fourthYearFacultyService.save(fourthYearFaculty)) {
			redirectAttributes.addFlashAttribute("message","Person with id, "+fourthYearFaculty.getRegId()+" details updated!");
			return "redirect:/fourth-year-faculty";
		}else {
			redirectAttributes.addFlashAttribute("message","try again!");
			return "redirect:/fourth-year-faculty/update/"+fourthYearFaculty.getRegId();
		}
	}
	
	@GetMapping("/fourth-year-faculty/delete/{regId}")
	public String delete(@PathVariable String regId, RedirectAttributes redirectAttributes) {
		if(fourthYearFacultyService.deleteByRegId(regId.trim())) {
			redirectAttributes.addFlashAttribute("message","Fourth Year Faculty with id, "+regId+" deleted!");
			return "redirect:/fourth-year-faculty";
		}
		redirectAttributes.addFlashAttribute("message","try again!");
		return "redirect:/fourth-year-faculty";
	}
	
	@GetMapping("/fourth-year-faculty/refactor-id")
	public String truncate(RedirectAttributes redirectAttributes) {
		if(fourthYearFacultyService.refactorIdColumn()) {
			return "redirect:/fourth-year-faculty";
		}
		redirectAttributes.addFlashAttribute("message","try again!");
		return "redirect:/fourth-year-faculty";
	}
	
	@GetMapping("/fourth-year-faculty/export-to-pdf")
	public void exportToPdf(HttpServletResponse response) throws IOException {
		FacultyPdfExporter pdfExporter = new FacultyPdfExporter();
		List<Faculty> facultyList = new ArrayList<>();
		for(FourthYearFaculty fourthYearFaculty: fourthYearFacultyService.listAll()) {
			facultyList.add(facultyService.getByRegId(fourthYearFaculty.getRegId()));
		}
		sortByBranch(facultyList);
		pdfExporter.export(response, facultyList);
	}
	
	private void sortByBranch(List<Faculty> faculties) {
		Collections.sort(faculties, new Comparator<Faculty>() {
			public int compare(Faculty f1, Faculty f2) {
				return f1.getDepartment().compareTo(f2.getDepartment());
			}
		});
	}
	
	public void preProcess(FourthYearFaculty fourthYearFaculty, Faculty faculty) {
		
		fourthYearFaculty.setRegId(faculty.getRegId().trim());
		fourthYearFaculty.setFirstName(faculty.getFirstName());
		fourthYearFaculty.setAverage(faculty.getAverage());
		
		switch (faculty.getDepartment().toUpperCase()) {
		case "CSE": 
			fourthYearFaculty.setCse(true);
			break;
		case "H&S": 
			fourthYearFaculty.sethAndS(true);
			break;
		case "CIVIL": 
			fourthYearFaculty.setCivil(true);
			break;
		case "ECE": 
			fourthYearFaculty.setEce(true);
			break;
		case "EEE": 
			fourthYearFaculty.setEee(true);
			break;
		case "MECHANICAL": 
			fourthYearFaculty.setMechanical(true);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + faculty);
		}
	}

}
