package in.kvsr.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "second_year_faculty")
public class SecondYearFaculty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="reg_id", unique = true, nullable = false, length = 20)
	private String regId;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="average")
	private Float average=0.0f;
	
	@Column(name="cse")
	private boolean cse=false;
	
	@Column(name="ece")
	private boolean ece=false;
	
	@Column(name="h_and_s")
	private boolean hAndS = false;
	
	@Column(name="civil")
	private boolean civil=false;
	
	@Column(name="eee")
	private boolean eee=false;
	
	@Column(name="mechanical")
	private boolean mechanical=false;
	
	public SecondYearFaculty() {
		
	}

	public SecondYearFaculty(String regId) {
		this.regId = regId;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public boolean isCse() {
		return cse;
	}

	public void setCse(boolean cse) {
		this.cse = cse;
	}

	public boolean isEce() {
		return ece;
	}

	public void setEce(boolean ece) {
		this.ece = ece;
	}

	public boolean ishAndS() {
		return hAndS;
	}

	public void sethAndS(boolean hAndS) {
		this.hAndS = hAndS;
	}

	public boolean isCivil() {
		return civil;
	}

	public void setCivil(boolean civil) {
		this.civil = civil;
	}

	public boolean isEee() {
		return eee;
	}

	public void setEee(boolean eee) {
		this.eee = eee;
	}

	public boolean isMechanical() {
		return mechanical;
	}

	public void setMechanical(boolean mechanical) {
		this.mechanical = mechanical;
	}
	
	@Transient
	public boolean contains(String branch) {
		if(branch.equalsIgnoreCase("CSE") && cse) {
			return true;
		}else if(branch.equalsIgnoreCase("CIVIL") && civil) {
			return true;
		}else if(branch.equalsIgnoreCase("ECE") && ece) {
			return true;
		}else if(branch.equalsIgnoreCase("EEE") && eee) {
			return true;
		}else if(branch.equalsIgnoreCase("H&S") && hAndS) {
			return true;
		}else if(branch.equalsIgnoreCase("MECHANICAL") && mechanical) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "SecondYearFaculty [regId=" + regId + ", firstName=" + firstName + ", average=" + average + ", cse="
				+ cse + ", ece=" + ece + ", hAndS=" + hAndS + ", civil=" + civil + ", eee=" + eee + ", mechanical="
				+ mechanical + "]";
	}
	
}
