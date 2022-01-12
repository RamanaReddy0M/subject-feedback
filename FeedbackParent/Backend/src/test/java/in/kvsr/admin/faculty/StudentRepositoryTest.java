package in.kvsr.admin.faculty;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import in.kvsr.admin.students.StudentService;
import in.kvsr.common.entity.Student;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)

public class StudentRepositoryTest {
	
	
	
	@Autowired
	private StudentService studentService;
	
	
	@Test
	public void testToInsertStudents() {
		List<Student> studentList = new ArrayList<>();
		
		Student s1 = new Student("Anuraag","Dwaram","18FH1A0525","CSE","4-1","Anu@525");
		
		studentList.add(s1);
		
		 s1 = new Student("Charan","D V","18FH1A0526","CSE","4-1","Program@526");
		
		studentList.add(s1);
		
		 s1 = new Student("Ramana","Manda","18FH1A0539","CSE","4-1","Paripo@539");
		
		studentList.add(s1);
		
		 s1 = new Student("Ganesh","V","18FH1A0555","CSE","4-1","Vgr@555");
		
		studentList.add(s1);
		
		 s1 = new Student("Eesha","Angadi","18FH1A0501","CSE","4-1","Eesha@501");
		
		studentList.add(s1);
		
		 s1 = new Student("Ramu","K","19FH1A0535","ECE","3-1","Ramu@535");
		
		studentList.add(s1);
		
		 s1 = new Student("Rajesh","W","19FH1A0300","EEE","3-1","Raj@500");
		
		studentList.add(s1);
		
		 s1 = new Student("Robert","N","20FH1A0522","CSE","2-1","Rob@522");
		
		studentList.add(s1);
		
		 s1 = new Student("Poolandevi","O","20FH1A0402","ECE","2-1","Pool@402");
		
		studentList.add(s1);
		
		 s1 = new Student("Meera","Joseph","20FH1A0201","CIVIL","2-1","meera@525");
		
		studentList.add(s1);
		
		 s1 = new Student("Lakshmi","Nenavath","20FH1A0426","CSE","2-1","Luck@525");
		
		studentList.add(s1);
		
		 s1 = new Student("Ramadevi","G","19FH1A0522","CSE","3-1","Ram@528");
		
		studentList.add(s1);
		
		 s1 = new Student("Karthik","P","19FH1A0302","EEE","3-2","Kar@525");
		
		studentList.add(s1);
		
		 s1 = new Student("Jagadeesh","J","17FH1A0525","CSE","4-2","Jaggu@525");
		
		studentList.add(s1);
		
		
		
		 s1 = new Student("Sudhakar","I V","19FH5A0501","CSE","4-2","IV@525");
		
		studentList.add(s1);
		
		 s1 = new Student("David","S","19FH5A0526","CSE","4-2","Dav@525");
		
		studentList.add(s1);
		
		 s1 = new Student("Roger","That","20FH1A0433","ECE","2-1","OK@525");
		
		studentList.add(s1);
		
		 s1 = new Student("Let's","Go","19FH1A0225","Mech","4-2","Anu@525");
		
		studentList.add(s1);
		
		for(Student student: studentList) {
			studentService.save(student);
		}
		
	}
	

}
