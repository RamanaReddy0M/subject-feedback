package in.kvsr.common.entity;

public interface Feedback {
	
	public Long getId();

	public void setId(Long id);

	public Long getStudentId();

	public void setStudentId(Long studentId);

	public String getSubjectCode();

	public void setSubjectCode(String subjectCode);
	
	public String getFacultyRegId();

	public void setFacultyRegId(String facultyRegId);

	public Integer getQuestion1();
	
	public void setQuestion1(Integer question1);

	public Integer getQuestion2();

	public void setQuestion2(Integer question2);

	public Integer getQuestion3();

	public void setQuestion3(Integer question3);

	public Integer getQuestion4();

	public void setQuestion4(Integer question4);

	public Integer getQuestion5();

	public void setQuestion5(Integer question5);
	
	public String getRemarks();
	
	public void setRemarks(String remarks);
	
	public Float getAverage();

	public void setAverage(Float average);
	
	
	public float calcAverage();
	
	public String toString();
}
