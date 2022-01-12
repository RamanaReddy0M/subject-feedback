package in.kvsr.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="FACULTY")
public class Faculty {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID") 
	private Long id=-1L;
	
	@Column(name = "FIRST_NAME", nullable = false, length = 50)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false, length = 50)
	private String lastName;
	
	@Column(name = "REG_ID", nullable = false, unique = true)
	private String regId;
	
	@Column(name="DESIGNATION", nullable = false)
	private String designation;
	
	@Column(name = "DEPARTMENT", nullable = false, length = 10)
	private String department;
	
	@Column(name="PASSWORD", nullable = false)
	private String password;
	
	@Column(name="PHOTOS")
	private String photo;
	
	@Column(name="average")
	private Float average=0.0F;
	
	@Transient
	MultipartFile file;
	
	public Faculty() {
		
	}
	
	public Faculty(String redId, String firstName, String department) {
		this.regId = redId;
		this.firstName = firstName;
		this.department = department;
	}
	
	
	public Faculty(String firstName, String lastName, String regId, String designation, String department,
			String password) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.regId = regId;
		this.designation = designation;
		this.department = department;
		this.password = password;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}
	

	@Override
	public String toString() {
		return "Faculty [firstName=" + firstName + ", regId=" + regId + ", designation=" + designation + ", department="
				+ department + ", password=" + password + ", photo=" + photo + "]";
	}
	
	@Transient
	public MultipartFile getFile() {
		return file;
	}
	@Transient
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Transient
	public String getImagePath() {
		if(this.id < 0L || this.photo ==null) return "/images/defaultImg.jpg";
		
		return "/faculty-images/"+this.regId+"/"+this.photo;
	}
	
}
