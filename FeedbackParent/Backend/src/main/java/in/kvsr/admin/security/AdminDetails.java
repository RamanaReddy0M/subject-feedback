package in.kvsr.admin.security;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import in.kvsr.common.entity.Admin;


public class AdminDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 607313536627805861L;
	private Admin admin;
	
	public AdminDetails(Admin admin) {
		this.admin = admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		return authorities;
	}

	@Override
	public String getPassword() {
		return admin.getPassword();
	}

	@Override
	public String getUsername() {
		return admin.getRegId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return admin.isEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return admin.isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return admin.isEnabled();
	}

	@Override
	public boolean isEnabled() {
		
		return admin.isEnabled();
	}
}
