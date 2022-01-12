package in.kvsr.admin.questions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.kvsr.common.entity.Question;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionsRepository questionsRepository;
	
	public List<Question> listAll(){
		return (List<Question>) questionsRepository.findAll();
	}
	
	public Question getById(Long id) {
		return questionsRepository.findById(id).get();
	}
	
	public boolean save(Question question) {
		try {
			questionsRepository.save(question);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
