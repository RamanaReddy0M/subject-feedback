package in.kvsr.admin.secondyear;

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
import in.kvsr.common.entity.SecondYearFaculty;
import in.kvsr.util.FacultyPdfExporter;

@Controller
public class SecondYearFacultyController {
	
	@Autowired
	private SecondYearFacultyService secondYearFacultyService;
	
	@Autowired
	private FacultyService facultyService;
	
	
	private List<SecondYearFaculty> secondYearFacultyList=null;
	
	@GetMapping("/second-year-faculty")
	public String viewHodPage(Model model) {
		model.addAttribute("title", "second-year-faculty");
		model.addAttribute("secondYearActive", "active");
		model.addAttribute("newFaculty", new SecondYearFaculty());
		secondYearFacultyList = secondYearFacultyService.listAll();
		if(secondYearFacultyList != null) {
			for(SecondYearFaculty secondYearFaculty: secondYearFacultyList) {
				Faculty faculty = facultyService.getByRegId(secondYearFaculty.getRegId());
				if(faculty != null) {
					preProcess(secondYearFaculty, faculty);					
				}
			}
		}
		
		model.addAttribute("facultyList", secondYearFacultyList);
		return "secondyearfaculty";
	}
	
	
	@PostMapping("/second-year-faculty/save")
	public String save(@ModelAttribute("newFaculty") SecondYearFaculty secondYearFaculty, RedirectAttributes redirectAttributes) {
		Faculty faculty = facultyService.getByRegId(secondYearFaculty.getRegId().trim());
		if(faculty==null) {
			redirectAttributes.addFlashAttribute("message","Soryy! no faculty found with id, "+secondYearFaculty.getRegId());
			return "redirect:/second-year-faculty";
		}
		preProcess(secondYearFaculty, faculty);
		if(secondYearFacultyService.save(secondYearFaculty)) {
			redirectAttributes.addFlashAttribute("message","Added Second Year Faculty with id, "+secondYearFaculty.getRegId()+"!");
			return "redirect:/second-year-faculty";
		}
		redirectAttributes.addFlashAttribute("message","field error, person might exists!");
		return "redirect:/second-year-faculty";
	}
	
	@GetMapping("/second-year-faculty/update/{regId}")
	public String updateByRegId(@PathVariable String regId, Model model) {
		SecondYearFaculty secondYearFaculty = secondYearFacultyService.getFacultyByRegId(regId);
		model.addAttribute("year", "(2nd Year)");
		model.addAttribute("secondYearFaculty", secondYearFaculty);
		return "branchform2";
	}
	
	@PostMapping("/second-year-faculty/update")
	public String update(@ModelAttribute SecondYearFaculty secondYearFaculty, RedirectAttributes redirectAttributes) {
		Faculty faculty = facultyService.getByRegId(secondYearFaculty.getRegId());
		
		preProcess(secondYearFaculty, faculty);
		if(secondYearFacultyService.save(secondYearFaculty)) {
			redirectAttributes.addFlashAttribute("message","Person with id, "+secondYearFaculty.getRegId()+" details updated!");
			return "redirect:/second-year-faculty";
		}else {
			redirectAttributes.addFlashAttribute("message","try again!");
			return "redirect:/second-year-faculty/update/"+secondYearFaculty.getRegId();
		}
	}
	
	@GetMapping("/second-year-faculty/delete/{regId}")
	public String delete(@PathVariable String regId, RedirectAttributes redirectAttributes) {
		if(secondYearFacultyService.deleteByRegId(regId.trim())) {
			redirectAttributes.addFlashAttribute("message","Second Year Faculty with id, "+regId+" deleted!");
			return "redirect:/second-year-faculty";
		}
		redirectAttributes.addFlashAttribute("message","try again!");
		return "redirect:/second-year-faculty";
	}
	
	@GetMapping("/second-year-faculty/refactor-id")
	public String truncate(RedirectAttributes redirectAttributes) {
		if(secondYearFacultyService.refactorIdColumn()) {
			return "redirect:/second-year-faculty";
		}
		redirectAttributes.addFlashAttribute("message","try again!");
		return "redirect:/second-year-faculty";
	}
	
	@GetMapping("/second-year-faculty/export-to-pdf")
	public void exportToPdf(HttpServletResponse response) throws IOException {
		FacultyPdfExporter pdfExporter = new FacultyPdfExporter();
		List<Faculty> facultyList = new ArrayList<>();
		for(SecondYearFaculty secondYearFaculty: secondYearFacultyService.listAll()) {
			facultyList.add(facultyService.getByRegId(secondYearFaculty.getRegId()));
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
	
	public void preProcess(SecondYearFaculty secondYearFaculty, Faculty faculty) {
		
		secondYearFaculty.setRegId(faculty.getRegId().trim());
		secondYearFaculty.setFirstName(faculty.getFirstName());
		secondYearFaculty.setAverage(faculty.getAverage());
		
		switch (faculty.getDepartment().toUpperCase()) {
		case "CSE": 
			secondYearFaculty.setCse(true);
			break;
		case "H&S": 
			secondYearFaculty.sethAndS(true);
			break;
		case "CIVIL": 
			secondYearFaculty.setCivil(true);
			break;
		case "ECE": 
			secondYearFaculty.setEce(true);
			break;
		case "EEE": 
			secondYearFaculty.setEee(true);
			break;
		case "MECHANICAL": 
			secondYearFaculty.setMechanical(true);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + faculty);
		}
	}

}
