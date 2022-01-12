package in.kvsr.questions;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.Question;


@Repository
public interface QuestionsRepository extends CrudRepository< Question, Long> {

}
