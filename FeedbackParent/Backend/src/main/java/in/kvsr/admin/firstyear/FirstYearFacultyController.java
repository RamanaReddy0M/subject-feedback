package in.kvsr.admin.firstyear;

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
import in.kvsr.common.entity.FirstYearFaculty;
import in.kvsr.util.FacultyPdfExporter;

@Controller
public class FirstYearFacultyController {
	
	@Autowired
	private FirstYearFacultyService firstYearFacultyService;
	
	@Autowired
	private FacultyService facultyService;
	
	
	private List<FirstYearFaculty> firstYearFacultyList=null;
	
	@GetMapping("/first-year-faculty")
	public String viewHodPage(Model model) {
		model.addAttribute("title", "first-year-faculty");
		model.addAttribute("firstYearActive", "active");
		model.addAttribute("newFaculty", new FirstYearFaculty());
		firstYearFacultyList = firstYearFacultyService.listAll();
		if(firstYearFacultyList != null) {
			for(FirstYearFaculty firstYearFaculty: firstYearFacultyList) {
				Faculty faculty = facultyService.getByRegId(firstYearFaculty.getRegId());
				if(faculty != null) {
					preProcess(firstYearFaculty, faculty);					
				}
			}
		}
		
		model.addAttribute("facultyList", firstYearFacultyList);
		return "firstyearfaculty";
	}
	
	
	@PostMapping("/first-year-faculty/save")
	public String save(@ModelAttribute("newFaculty") FirstYearFaculty firstYearFaculty, RedirectAttributes redirectAttributes) {
		Faculty faculty = facultyService.getByRegId(firstYearFaculty.getRegId().trim());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","Soryy! no faculty found with id, "+firstYearFaculty.getRegId());
			return "redirect:/first-year-faculty";
		}
		preProcess(firstYearFaculty, faculty);
		if(firstYearFacultyService.save(firstYearFaculty)) {
			redirectAttributes.addFlashAttribute("message","Added First Year Faculty with id, "+firstYearFaculty.getRegId()+"!");
			return "redirect:/first-year-faculty";
		}
		redirectAttributes.addFlashAttribute("message","field error, person might exists!");
		return "redirect:/first-year-faculty";
	}
	
	@GetMapping("/first-year-faculty/update/{regId}")
	public String updateByRegId(@PathVariable String regId, Model model) {
		FirstYearFaculty firstYearFaculty = firstYearFacultyService.getFacultyByRegId(regId);
		model.addAttribute("year", "(1st Year)");
		model.addAttribute("firstYearFaculty", firstYearFaculty);
		return "branchform1";
	}
	
	@PostMapping("/first-year-faculty/update")
	public String update(@ModelAttribute FirstYearFaculty firstYearFaculty, RedirectAttributes redirectAttributes) {
		Faculty faculty = facultyService.getByRegId(firstYearFaculty.getRegId());
		
		preProcess(firstYearFaculty, faculty);
		if(firstYearFacultyService.save(firstYearFaculty)) {
			redirectAttributes.addFlashAttribute("message","Person with id, "+firstYearFaculty.getRegId()+" details updated!");
			return "redirect:/first-year-faculty";
		}else {
			redirectAttributes.addFlashAttribute("message","try again!");
			return "redirect:/first-year-faculty/update/"+firstYearFaculty.getRegId();
		}
	}
	
	@GetMapping("/first-year-faculty/delete/{regId}")
	public String delete(@PathVariable String regId, RedirectAttributes redirectAttributes) {
		if(firstYearFacultyService.deleteByRegId(regId.trim())) {
			redirectAttributes.addFlashAttribute("message","First Year Faculty with id, "+regId+" deleted!");
			return "redirect:/first-year-faculty";
		}
		redirectAttributes.addFlashAttribute("message","try again!");
		return "redirect:/first-year-faculty";
	}
	
	@GetMapping("/first-year-faculty/refactor-id")
	public String truncate(RedirectAttributes redirectAttributes) {
		if(firstYearFacultyService.refactorIdColumn()) {
			return "redirect:/first-year-faculty";
		}
		redirectAttributes.addFlashAttribute("message","try again!");
		return "redirect:/first-year-faculty";
	}
	
	@GetMapping("/first-year-faculty/export-to-pdf")
	public void exportToPdf(HttpServletResponse response) throws IOException {
		FacultyPdfExporter pdfExporter = new FacultyPdfExporter();
		List<Faculty> facultyList = new ArrayList<>();
		for(FirstYearFaculty firstYearFaculty: firstYearFacultyService.listAll()) {
			facultyList.add(facultyService.getByRegId(firstYearFaculty.getRegId()));
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
	
	public void preProcess(FirstYearFaculty firstYearFaculty, Faculty faculty) {
		
		firstYearFaculty.setRegId(faculty.getRegId().trim());
		firstYearFaculty.setFirstName(faculty.getFirstName());
		firstYearFaculty.setAverage(faculty.getAverage());
		
		switch (faculty.getDepartment().toUpperCase()) {
		case "CSE": 
			firstYearFaculty.setCse(true);
			break;
		case "H&S": 
			firstYearFaculty.sethAndS(true);
			break;
		case "CIVIL": 
			firstYearFaculty.setCivil(true);
			break;
		case "ECE": 
			firstYearFaculty.setEce(true);
			break;
		case "EEE": 
			firstYearFaculty.setEee(true);
			break;
		case "MECHANICAL": 
			firstYearFaculty.setMechanical(true);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + faculty);
		}
	}
}
