package in.kvsr.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id=-1L;
	@Column(name="first_name", nullable = false, length = 70)
	private String firstName;
	@Column(name="last_name", nullable = false, length = 70)
	private String lastName;
	@Column(name = "roll_number",unique = true, nullable = false, length = 15)
	private String rollNumber;
	@Column(name = "branch", nullable = false, length = 100)
	private String branch;
	@Column(name = "semister", nullable = false, length = 10)
	private String semister;
	@Column(name = "password", nullable = false)
	private String password;
	
	public Student() {
		
	}

	public Student(String firstName, String lastName, String rollNumber, String branch, String semister, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.rollNumber = rollNumber;
		this.branch = branch;
		this.semister = semister;
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

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getSemister() {
		return semister;
	}

	public void setSemister(String semister) {
		this.semister = semister;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return this.rollNumber+" "+this.firstName+" branch: "+branch+" semister: "+semister;
	}
	
	
	
}
