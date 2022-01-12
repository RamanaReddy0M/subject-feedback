package in.kvsr.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lock_feedback")
public class Lock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="is_enabled")
	private boolean isEnabled=false;
	
	@Column(name="regId",nullable = false)
	private String regId="1234";
	
	public Lock() {
		
	}
	
	public Lock(String regId) {
		this.regId = regId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	@Override
	public String toString() {
		return "Lock [isEnabled=" + isEnabled + ", regId=" + regId + "]";
	}
	
	

}
