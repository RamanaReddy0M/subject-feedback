package in.kvsr.common.entity.ece;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import in.kvsr.common.entity.Subject;

@Entity
@Table(name="ece_2_2",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"subject_code", "subject_name"})
})
public class EceTwoTwo implements Subject{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id=0L;
	
	@Column(name="subject_code", unique = true)
	private String subjectCode;
	
	@Column(name="subject_name")
	private String subjectName;
	
	@Column(name="faculty_regId")
	private String facultyRegId;
	
	@Column(name="average")
	private Float average=0.0f;
	
	@Column(name="total")
	private String total;
	
	public EceTwoTwo() {
		
	}
	
	public EceTwoTwo(String subjectCode, String subjectName, String facultyRegId, Float average) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.facultyRegId = facultyRegId;
		this.average = average;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getFacultyRegId() {
		return facultyRegId;
	}

	public void setFacultyRegId(String facultyRegId) {
		this.facultyRegId = facultyRegId;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "EceTwoTwo [subjectCode=" + subjectCode + ", subjectName=" + subjectName + ", facultyRegId="
				+ facultyRegId + ", average=" + average +", total="+ total+"]";
	}

}
