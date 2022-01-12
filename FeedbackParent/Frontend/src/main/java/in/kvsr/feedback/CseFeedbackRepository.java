package in.kvsr.feedback;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.cse.CseFeedback;

@Repository
public interface CseFeedbackRepository extends CrudRepository<CseFeedback, Long>{
	
	@Query("select f.remarks from CseFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<String> getRemarks(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);
	
}
