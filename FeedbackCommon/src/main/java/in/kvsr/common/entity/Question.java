package in.kvsr.common.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="questions")
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id=0L;
	
	@Column(name="question")
	private String question;
	
	@Column(name = "option1")
	private String option1;
	
	@Column(name="option2")
	private String option2;
	
	@Column(name="option3")
	private String option3;
	
	@Column(name = "option4")
	private String option4;
	
	@Column(name = "option5")
	private String option5;
	
	
	public Question() {
		
	}
	
	public Question(String question, String option1, String option2, String option3, String option4, String option5) {
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.option5 = option5;
	}
	
	

	public Question(String question) {
		super();
		this.question = question;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getOption5() {
		return option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}
	
	@Transient
	public Map<String, Integer> getOptions(){
		Map<String, Integer> hMap = new LinkedHashMap<>();
		hMap.put(option1, 1);
		hMap.put(option2, 2);
		hMap.put(option3, 3);
		hMap.put(option4, 4);
		hMap.put(option5, 5);
		return hMap;
	}
	
	@Override
	public String toString() {
		return "Questions [question=" + question + ", option1=" + option1 + ", option2=" + option2 + ", option3="
				+ option3 + ", option4=" + option4 + ", option5=" + option5 + "]";
	}
	
}
