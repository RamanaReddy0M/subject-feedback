package in.kvsr.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.kvsr.common.entity.Faculty;
import in.kvsr.common.entity.HOD;



public class HodDetails implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HOD hod;
	@Autowired
	private Faculty faculty=null;
	
	public HodDetails(HOD hod, Faculty faculty) {
		this.hod = hod;
		this.faculty = faculty;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		if(faculty.getDepartment().equalsIgnoreCase("H&S")) {
			authorities.add(new SimpleGrantedAuthority("H&S"));
		}else if (faculty.getDepartment().equalsIgnoreCase("CSE")) {
			authorities.add(new SimpleGrantedAuthority("CSE"));
		}else if (faculty.getDepartment().equalsIgnoreCase("ECE")) {
			authorities.add(new SimpleGrantedAuthority("ECE"));
		}else if (faculty.getDepartment().equalsIgnoreCase("EEE")) {
			authorities.add(new SimpleGrantedAuthority("EEE"));
		}else if (faculty.getDepartment().equalsIgnoreCase("CIVIL")) {
			authorities.add(new SimpleGrantedAuthority("CIVIL"));
		}else if (faculty.getDepartment().equalsIgnoreCase("MECHANICAL")) {
			authorities.add(new SimpleGrantedAuthority("MECHANICAL"));
		}
		authorities.add(new SimpleGrantedAuthority("HOD"));
		return authorities;
	}

	@Override
	public String getPassword() {
		return hod.getPassword();
	}

	@Override
	public String getUsername() {
		return hod.getRegId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return hod.isEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return hod.isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return hod.isEnabled();
	}

	@Override
	public boolean isEnabled() {
		return hod.isEnabled();
	}

}
