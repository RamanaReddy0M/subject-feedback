package in.kvsr.admin.faculty;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import in.kvsr.admin.hod.HODRepository;
import in.kvsr.common.entity.HOD;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class HodTestCases {
	@Autowired
	private HODRepository hodRepository;
	
	@Test
	public void testTodeleteByRegId() {
		Integer hod = hodRepository.deleteByRegId("85008502");
		System.out.println(hod);
	}
	
	@Test
	public void testToGetByRegId() {
		HOD hod = hodRepository.findByRegId("85008500");
		System.out.println(hod);
	}
	
	@Test
	public void testToTruncateHOD() {
		Integer value = hodRepository.truncateAll();
		System.out.println(value);
		assertThat(value==0).isTrue();
	}

}
