package in.kvsr.common.entity.cse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import in.kvsr.common.entity.Feedback;

@Entity
@Table(name = "cse_feedback",uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"student_id", "subject_code"})
	})
public class CseFeedback implements Feedback{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id=0L;
	@Column(name="student_id", nullable = false)
	private Long studentId=0L;
	@Column(name="subject_code", nullable = false)
	private String subjectCode=null;
	@Column(name="faculty_reg_Id", nullable = false)
	private String facultyRegId=null;
	@Column(name="question_1", nullable = false)
	private Integer question1=0;
	@Column(name="question_2", nullable = false)
	private Integer question2=0;
	@Column(name="question_3", nullable = false)
	private Integer question3=0;
	@Column(name="question_4", nullable = false)
	private Integer question4=0;
	@Column(name="questin_5", nullable = false)
	private Integer question5=0;
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="average", nullable = false)
	private Float average=0.0f;
	
	public CseFeedback() {
		
	}
	
	public CseFeedback(Long studentId, String subjectCode, String facultyRegId,Integer question1, Integer question2, Integer question3,
			Integer question4, Integer question5, String remarks) {
		
		this.studentId = studentId;
		this.subjectCode = subjectCode;
		this.facultyRegId = facultyRegId;
		this.question1 = question1;
		this.question2 = question2;
		this.question3 = question3;
		this.question4 = question4;
		this.question5 = question5;
		this.remarks = remarks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	
	
	public String getFacultyRegId() {
		return facultyRegId;
	}

	public void setFacultyRegId(String facultyRegId) {
		this.facultyRegId = facultyRegId;
	}

	public Integer getQuestion1() {
		return question1;
	}
	
	public void setQuestion1(Integer question1) {
		this.question1 = question1;
	}

	public Integer getQuestion2() {
		return question2;
	}

	public void setQuestion2(Integer question2) {
		this.question2 = question2;
	}

	public Integer getQuestion3() {
		return question3;
	}

	public void setQuestion3(Integer question3) {
		this.question3 = question3;
	}

	public Integer getQuestion4() {
		return question4;
	}

	public void setQuestion4(Integer question4) {
		this.question4 = question4;
	}

	public Integer getQuestion5() {
		return question5;
	}

	public void setQuestion5(Integer question5) {
		this.question5 = question5;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}
	
	@Transient
	public float calcAverage() {
		return (float)(question1+question2+question3+question4+question5)/5.0F;
	}
	
	@Override
	public String toString() {
		return "CseFeedback [studentId=" + studentId + ", subjecCode=" + subjectCode +", F.regId:"+facultyRegId+ ", question1=" + question1
				+ ", question2=" + question2 + ", question3=" + question3 + ", question4=" + question4 + ", question5="
				+ question5  + ", average=" + average +"remarks="+remarks+ "]";
	}
	
}
