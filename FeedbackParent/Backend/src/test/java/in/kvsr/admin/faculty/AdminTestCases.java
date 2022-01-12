package in.kvsr.admin.faculty;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import in.kvsr.admin.AdminService;
import in.kvsr.common.entity.Admin;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AdminTestCases {
	@Autowired
	private AdminService adminService;
	
	@Test
	public void testToAddAdmin() {
		Admin admin = new Admin("85008500", "85008500");
		admin.setId(1L);
		boolean isSaved = adminService.save(admin);
		adminService.save(new Admin("525526539555","525526539555"));
		assertThat(isSaved).isTrue();
	}
	@Test
	public void testToGetAdminByRegId() {
		Admin admin = adminService.getByRegId("85008500");
		System.out.println(admin);
		assertThat(admin).isNotNull();
	}
}
