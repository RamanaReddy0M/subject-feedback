package in.kvsr.feedback;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.eee.EeeFeedback;

@Repository
public interface EeeFeedbackRepository extends CrudRepository<EeeFeedback, Long>{
	
	@Query("select f.remarks from EeeFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<String> getRemarks(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);
	
}
