package in.kvsr.student;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import in.kvsr.common.entity.Student;
import in.kvsr.common.entity.Subject;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StudentTestCases {
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	@Test
	public void testToGetStudentByRollNo() {
		String rollNumber = "18fh1a0539";
		Student student = studentRepository.findByRollNumber(rollNumber);
		System.out.println(student);
	}
	
	@Test
	public void subjectTestCases() {
		System.out.println("fid: "+studentRepository.getCseTwoOneDistinctFRI());
		System.out.println("fid: "+studentRepository.getCseTwoOneSubjectsByFRI("FN010"));
		List<Subject>  subjects = studentRepository.getCseTwoOneSubjects();
		System.out.print("subjects: " + subjects);
	
	}

}
