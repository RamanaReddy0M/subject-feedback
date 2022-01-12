package in.kvsr.student;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Student;
import in.kvsr.common.entity.Subject;
import in.kvsr.faculty.FacultyService;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;


	public Student getByRollNumber(String rollNumber) { 
		
		return studentRepository.findByRollNumber(rollNumber); }

	
	public Map<Faculty, List<Subject>> getFaculty(Student student) {
		String branch = student.getBranch(),
			   semister = student.getSemister();
		List<String> faculties = new ArrayList<>();
		Map<Faculty, List<Subject>> facultyMap = new LinkedHashMap<>();
		switch (branch) {
		case "CSE": 
			switch (semister) {
			case "1-1": 
				faculties = studentRepository.getCseOneOneDistinctFRI();
				for(int i=0;i<faculties.size();i++) {
					facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCseOneOneSubjectsByFRI(faculties.get(i)));
				}
				break;
			case "1-2": 
				faculties = studentRepository.getCseOneTwoDistinctFRI();
				for(int i=0;i<faculties.size();i++) {
					facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCseOneTwoSubjectsByFRI(faculties.get(i)));
				}
				break;
			case "2-1": 
				faculties = studentRepository.getCseTwoOneDistinctFRI();
				for(int i=0;i<faculties.size();i++) {
					facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCseTwoOneSubjectsByFRI(faculties.get(i)));
				}
				break;
			case "2-2": 
				faculties = studentRepository.getCseTwoTwoDistinctFRI();
				for(int i=0;i<faculties.size();i++) {
					facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCseTwoTwoSubjectsByFRI(faculties.get(i)));
				}
				break;
			case "3-1": 
				faculties = studentRepository.getCseThreeOneDistinctFRI();
				for(int i=0;i<faculties.size();i++) {
					facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCseThreeOneSubjectsByFRI(faculties.get(i)));
				}
				break;
			case "3-2": 
				faculties = studentRepository.getCseThreeTwoDistinctFRI();
				for(int i=0;i<faculties.size();i++) {
					facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCseThreeTwoSubjectsByFRI(faculties.get(i)));
				}
				break;
			case "4-1": 
				faculties = studentRepository.getCseFourOneDistinctFRI();
				for(int i=0;i<faculties.size();i++) {
					facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCseFourOneSubjectsByFRI(faculties.get(i)));
				}
				break;
			case "4-2": 
				faculties = studentRepository.getCseFourTwoDistinctFRI();
				for(int i=0;i<faculties.size();i++) {
					facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCseFourTwoSubjectsByFRI(faculties.get(i)));
				}
				break;
			}
			break;
	case "ECE": 
		switch (semister) {
		case "1-1": 
			faculties = studentRepository.getEceOneOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEceOneOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "1-2": 
			faculties = studentRepository.getEceOneTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEceOneTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "2-1": 
			faculties = studentRepository.getEceTwoOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEceTwoOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "2-2": 
			faculties = studentRepository.getEceTwoTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEceTwoTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "3-1": 
			faculties = studentRepository.getEceThreeOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEceThreeOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "3-2": 
			faculties = studentRepository.getEceThreeTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEceThreeTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "4-1": 
			faculties = studentRepository.getEceFourOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEceFourOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "4-2": 
			faculties = studentRepository.getEceFourTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEceFourTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		}
		break;
	case "CIVIL": 
		switch (semister) {
		case "1-1": 
			faculties = studentRepository.getCivilOneOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCivilOneOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "1-2": 
			faculties = studentRepository.getCivilOneTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCivilOneTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "2-1": 
			faculties = studentRepository.getCivilTwoOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCivilTwoOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "2-2": 
			faculties = studentRepository.getCivilTwoTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCivilTwoTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "3-1": 
			faculties = studentRepository.getCivilThreeOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCivilThreeOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "3-2": 
			faculties = studentRepository.getCivilThreeTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCivilThreeTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "4-1": 
			faculties = studentRepository.getCivilFourOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCivilFourOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "4-2": 
			faculties = studentRepository.getCivilFourTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getCivilFourTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		}
		break;
	case "MECHANICAL": 
		switch (semister) {
		case "1-1": 
			faculties = studentRepository.getMechanicalOneOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getMechanicalOneOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "1-2": 
			faculties = studentRepository.getMechanicalOneTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getMechanicalOneTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "2-1": 
			faculties = studentRepository.getMechanicalTwoOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getMechanicalTwoOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "2-2": 
			faculties = studentRepository.getMechanicalTwoTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getMechanicalTwoTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "3-1": 
			faculties = studentRepository.getMechanicalThreeOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getMechanicalThreeOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "3-2": 
			faculties = studentRepository.getMechanicalThreeTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getMechanicalThreeTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "4-1": 
			faculties = studentRepository.getMechanicalFourOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getMechanicalFourOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "4-2": 
			faculties = studentRepository.getMechanicalFourTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getMechanicalFourTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		}
		break;	

	case "EEE": 
		switch (semister) {
		case "1-1": 
			faculties = studentRepository.getEeeOneOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEeeOneOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "1-2": 
			faculties = studentRepository.getEeeOneTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEeeOneTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "2-1": 
			faculties = studentRepository.getEeeTwoOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEeeTwoOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "2-2": 
			faculties = studentRepository.getEeeTwoTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEeeTwoTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "3-1": 
			faculties = studentRepository.getEeeThreeOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEeeThreeOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "3-2": 
			faculties = studentRepository.getEeeThreeTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEeeThreeTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "4-1": 
			faculties = studentRepository.getEeeFourOneDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEeeFourOneSubjectsByFRI(faculties.get(i)));
			}
			break;
		case "4-2": 
			faculties = studentRepository.getEeeFourTwoDistinctFRI();
			for(int i=0;i<faculties.size();i++) {
				facultyMap.put(facultyService.getByRegId(faculties.get(i)), studentRepository.getEeeFourTwoSubjectsByFRI(faculties.get(i)));
			}
			break;
		}
		break;	
		
	default:
		throw new IllegalArgumentException("Unexpected value: " + semister);
	}
		return facultyMap;
	}
	 
	
	public Student getById(Long id) {
		try {
			return studentRepository.findById(id).get();
		}catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public boolean save(Student student) {
		try {
			if(student.getId() > 0L && (student.getPassword().trim()==null || student.getPassword().trim().isEmpty())) {
				student.setPassword(studentRepository.findByRollNumber(student.getRollNumber()).getPassword());
			}else {
				encodePassword(student);
			}
			studentRepository.save(student);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
            return false;		
        }
	}
	
	private void encodePassword(Student student) {
		String encoded = passwordEncoder.encode(student.getPassword());
		student.setPassword(encoded);
	}





}
