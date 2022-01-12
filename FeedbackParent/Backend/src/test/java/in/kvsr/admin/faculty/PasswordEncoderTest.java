package in.kvsr.admin.faculty;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	
	@Test
	public void testToEncodePassword() {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = "abcd1234";
		String encoded = encoder.encode(password);
		System.out.println(encoded);
		boolean match = encoder.matches(password, encoded);
		assertThat(match).isTrue();
		
	}

}
