package in.kvsr.common.entity;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@MappedSuperclass
public interface Subject {
	
	public Long getId();

	public void setId(Long id);

	public String getSubjectCode();

	public void setSubjectCode(String subjectCode);

	public String getSubjectName();

	public void setSubjectName(String subjectName);

	public String getFacultyRegId();

	public void setFacultyRegId(String facultyRegId);

	public Float getAverage();

	public void setAverage(Float average);
	
	public String getTotal();

	public void setTotal(String total);
	
	public String toString();
}
