package in.kvsr.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.kvsr.common.entity.Faculty;

public class FacultyDetails implements UserDetails {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Faculty faculty;
	
	public FacultyDetails(Faculty faculty) {
		this.faculty = faculty;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("FACULTY"));
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return faculty.getPassword();
	}

	@Override
	public String getUsername() {
		return faculty.getRegId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
