package in.kvsr.admin.faculty;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import in.kvsr.common.entity.Faculty;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class FacultyRepositoryTest {
	
	@Autowired
	private FacultyRepository facultyRepository;
	
	@Autowired
	private FacultyService facultyService;
	
	    @Test
	    public void testToInsertOneRow() {
			Faculty f1 = new Faculty("Obulesh","G","10008601","Ass. Prof.","CSE","10008601");
			facultyService.save(f1);
	    }
	    
	    @Test
	    public void testDropAndAddColumn() {
	    	System.out.println("result: "+facultyRepository.dropIdColumn());
	    	System.out.println("result: "+facultyRepository.addIdColumn());
	    }
		
		@Test
		public void testToInsertFaculty() {
			List<Faculty> facultyList = new ArrayList<>();
			Faculty f1 = new Faculty("Gulzar","Mohammed","85008500","HOD","CSE","gulzar@8500");
			facultyList.add(f1);
			
			f1 = new Faculty("Harikrishna","Magishetty","85008508","Associate Professor","H&S","Magi@8508");
			facultyList.add(f1);
			
			f1 = new Faculty("Ateeq","Ahmed","85008501","Associate Professor","CSE","Ateeq@8501");
			facultyList.add(f1);
			
			f1 = new Faculty("Harish","Reddy","85008502","Assisstant Professor","CSE","Harish@8502");
			facultyList.add(f1);
			
			f1 = new Faculty("Bushra","Tashreen","85008503","Associate Professor","CSE","Bushra@8503");
			facultyList.add(f1);
			
			f1 = new Faculty("Jayaram","Reddy","85008504","Associate Professor","ECE","Jaya@8504");
			facultyList.add(f1);
			
			f1 = new Faculty("Raju","Emanuel","85008505","Assisstant Professor","EEE","Raju@8505");
			facultyList.add(f1);
			
			f1 = new Faculty("Maheh","Bhatuskar","85008506","Associate Professor","ECE","Mahesh@8506");
			facultyList.add(f1);
			
			f1 = new Faculty("Yeshovardhan","Raju","85008507","Assisstant Professor","CIVIL","Yesho@8507");
			facultyList.add(f1);
			
			f1 = new Faculty("Jacob","Xyz","85008509","Associate Professor","H&S","Jacob@8509");
			facultyList.add(f1);
			
			f1 = new Faculty("Shyanti","Vanta","85008510","Associate Professor","ECE","Shanti@8510");
			facultyList.add(f1);
			
			f1 = new Faculty("Mahesh","P","85008511","Associate Professor","ECE","Mahesh@8511");
			facultyList.add(f1);
			
			f1 = new Faculty("Latha","A","85008512","Associate Professor","H&S","Latha@8512");
			facultyList.add(f1);
			
			f1 = new Faculty("Nagendra","Reddy","85008513","VP","H&S","Nagu@8513");
			facultyList.add(f1);
			
			f1 = new Faculty("Thimmayya","L","85008514","Principal","Mechanical","THimmu@8514");
			facultyList.add(f1);
			
			f1 = new Faculty("Geeta","Vani","85008515","Associate Professor","H&S","Geeta@8515");
			facultyList.add(f1);			
			
			for(Faculty faculty: facultyList) {
				facultyService.save(faculty);
			}
		}

	
	@Test
	public void testToGetFacultyById() {
		Faculty faculty = facultyRepository.findById((long) 6).get();
		System.out.println(faculty);
		assertThat(faculty).isNotNull();
	}
	
	@Test
	public void testToDeleteFacultyById() {
		Faculty faculty = facultyRepository.findById(3L).get();
		System.out.println(faculty);
		facultyRepository.delete(faculty);
		try {
			faculty = facultyRepository.findById(3L).get();
		}catch (NoSuchElementException e) {
			faculty=null;
		}
		assertThat(faculty).isNull();
	}
	
	@Test
	public void testToGetAllFaculties() {
		Iterable<Faculty> list = facultyRepository.findAll();
		
		list.forEach((faculity) -> {
			          System.out.println(faculity);
			          });
	}
	
	
	@Test
	public void testToUpdateFaculty() {
		Faculty faculty = facultyService.getById(2L);
		System.out.println(faculty+" "+faculty.getDepartment());
		faculty.setDepartment("ECE");
	    facultyService.save(faculty);
	    faculty = facultyRepository.findById(2L).get();
		System.out.println(faculty+" "+faculty.getDepartment());
	}
	
	@Test
	public void testToGetFacultyByRegId() {
		String regId = "85008500";
		Faculty faculty = facultyService.getByRegId(regId);
		//System.out.println(faculty);
		assertThat(faculty).isNotNull();
	}
	@Test
	public void testToSearch() {
		String key = "ra";
		List<Faculty> faculty = facultyRepository.searchByRegIdAndName(key);
		System.out.println(faculty);
		assertThat(faculty).isNotNull();
	}
}
