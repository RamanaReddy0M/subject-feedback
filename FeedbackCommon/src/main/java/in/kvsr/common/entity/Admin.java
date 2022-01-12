package in.kvsr.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id=-1L;
	
	@Column(name="reg_id", unique = true, nullable = false, length = 20)
	private String regId;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="enabled")
	private boolean enabled=true;
	
	public Admin() {
		
	}

	public Admin(String regId, String password) {
		
		this.regId = regId;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Admin [regId=" + regId + ", password=" + password + "]";
	}
	
}
