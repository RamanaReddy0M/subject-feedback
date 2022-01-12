package in.kvsr.faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.Subject;
import in.kvsr.feedback.CivilFeedbackRepository;
import in.kvsr.feedback.CseFeedbackRepository;
import in.kvsr.feedback.EceFeedbackRepository;
import in.kvsr.feedback.EeeFeedbackRepository;
import in.kvsr.feedback.HandSFeedbackRepository;
import in.kvsr.feedback.MechanicalFeedbackRepository;
import in.kvsr.student.StudentRepository;

@Service
public class FacultyService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private FacultyRepository facultyRepository;

	@Autowired
	private HandSFeedbackRepository handSFeedbackRepository;

	@Autowired
	private CseFeedbackRepository cseFeedbackRepository;

	@Autowired
	private EceFeedbackRepository eceFeedbackRepository;

	@Autowired
	private EeeFeedbackRepository eeeFeedbackRepository;

	@Autowired
	private CivilFeedbackRepository civilFeedbackRepository;

	@Autowired
	private MechanicalFeedbackRepository mechanicalFeedbackRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Faculty getByRegId(String regId) {
		return facultyRepository.findByRegId(regId);
	}

	public Faculty getById(Long id) {
		try {
			return facultyRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public boolean save(Faculty faculty) {
		try {
			Faculty tempFaculty = null;
			if (faculty.getId() > 0L) {
				tempFaculty = facultyRepository.findByRegId(faculty.getRegId());
				faculty.setAverage(tempFaculty.getAverage());
				if (faculty.getPhoto() == null && tempFaculty.getPhoto() != null) {
					faculty.setPhoto(tempFaculty.getPhoto());
				}
			}
			if ((faculty.getId() > 0L) && (faculty.getPassword() == null || faculty.getPassword().isEmpty())) {
				faculty.setPassword(tempFaculty.getPassword());
			} else {
				encodePassword(faculty);
			}
			facultyRepository.save(faculty);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void getSubjects(Faculty faculty, Map<Subject, RemarkAndQuestionList> subjectMap) {
		String facultyRegId = faculty.getRegId();
		List<Subject> subjects = new ArrayList<>();

		/*
		 * CSE Branch
		 */
		subjects = studentRepository.getCseOneOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getCseOneTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getCseTwoOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CSE");
		subjects = studentRepository.getCseTwoTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CSE");
		subjects = studentRepository.getCseThreeOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CSE");
		subjects = studentRepository.getCseThreeTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CSE");
		subjects = studentRepository.getCseFourOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CSE");
		subjects = studentRepository.getCseFourTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CSE");
		/*
		 * ECE Branch
		 */
		subjects = studentRepository.getEceOneOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getEceOneTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getEceTwoOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "ECE");
		subjects = studentRepository.getEceTwoTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "ECE");
		subjects = studentRepository.getEceThreeOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "ECE");
		subjects = studentRepository.getEceThreeTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "ECE");
		subjects = studentRepository.getEceFourOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "ECE");
		subjects = studentRepository.getEceFourTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "ECE");
		/*
		 * EEE Branch
		 */
		subjects = studentRepository.getEeeOneOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getEeeOneTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getEeeTwoOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "EEE");
		subjects = studentRepository.getEeeTwoTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "EEE");
		subjects = studentRepository.getEeeThreeOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "EEE");
		subjects = studentRepository.getEeeThreeTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "EEE");
		subjects = studentRepository.getEeeFourOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "EEE");
		subjects = studentRepository.getEeeFourTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "EEE");
		/*
		 * CIVIL Branch
		 */
		subjects = studentRepository.getCivilOneOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getCivilOneTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getCivilTwoOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CIVIL");
		subjects = studentRepository.getCivilTwoTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CIVIL");
		subjects = studentRepository.getCivilThreeOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CIVIL");
		subjects = studentRepository.getCivilThreeTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CIVIL");
		subjects = studentRepository.getCivilFourOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CIVIL");
		subjects = studentRepository.getCivilFourTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "CIVIL");
		/*
		 * MECHANICAL Branch
		 */
		subjects = studentRepository.getMechanicalOneOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getMechanicalOneTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "H&S");
		subjects = studentRepository.getMechanicalTwoOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "MECHANICAL");
		subjects = studentRepository.getMechanicalTwoTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "MECHANICAL");
		subjects = studentRepository.getMechanicalThreeOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "MECHANICAL");
		subjects = studentRepository.getMechanicalThreeTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "MECHANICAL");
		subjects = studentRepository.getMechanicalFourOneSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "MECHANICAL");
		subjects = studentRepository.getMechanicalFourTwoSubjectsByFRI(facultyRegId);
		fillSubjectMap(subjects, subjectMap, "MECHANICAL");
		
	}

	private void fillSubjectMap(List<Subject> subjects, Map<Subject, RemarkAndQuestionList> subjectMap,
			String department) {
		if (subjects.size() < 1) {
			return;
		}
		for (Subject subject : subjects) {
			if(subject.getTotal() == null) continue;
			if(subject.getTotal().trim().isEmpty()) continue;
			List<Float> questionCounters = new ArrayList<>();
			for (String q : subject.getTotal().split(" ")) {
				questionCounters.add(Float.parseFloat(q) * 20);
			}

			if (department.equalsIgnoreCase("H&S")) {
				List<String> s = handSFeedbackRepository.getRemarks(subject.getSubjectCode(),
						subject.getFacultyRegId());
				subjectMap.put(subject, new RemarkAndQuestionList(s, questionCounters));
			} else if (department.equalsIgnoreCase("CSE")) {
				List<String> s = cseFeedbackRepository.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId());
				subjectMap.put(subject, new RemarkAndQuestionList(s, questionCounters));
			} else if (department.equalsIgnoreCase("ECE")) {

				List<String> s = eceFeedbackRepository.getRemarks(subject.getSubjectCode(), subject.getFacultyRegId());
				subjectMap.put(subject, new RemarkAndQuestionList(s, questionCounters));

			} else if (department.equalsIgnoreCase("EEE")) {

				List<String> s = eeeFeedbackRepository.getRemarks(subject.getSubjectCode(),
						subject.getFacultyRegId());
				subjectMap.put(subject, new RemarkAndQuestionList(s, questionCounters));

			}else if (department.equalsIgnoreCase("CIVIL")) {

				List<String> s = civilFeedbackRepository.getRemarks(subject.getSubjectCode(),
						subject.getFacultyRegId());
				subjectMap.put(subject, new RemarkAndQuestionList(s, questionCounters));

			} else if (department.equalsIgnoreCase("MECHANICAL")) {

				List<String> s = mechanicalFeedbackRepository.getRemarks(subject.getSubjectCode(),
						subject.getFacultyRegId());
				subjectMap.put(subject, new RemarkAndQuestionList(s, questionCounters));

			}
		}
		subjects.clear();
	}

	private void encodePassword(Faculty faculty) {
		String encoded = passwordEncoder.encode(faculty.getPassword());
		faculty.setPassword(encoded);
	}
}
