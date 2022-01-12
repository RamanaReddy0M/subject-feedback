package in.kvsr.admin.cse;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.cse.CseFeedback;

@Repository
public interface CseFeedbackRepository extends CrudRepository<CseFeedback, Long>{
	
	@Transactional
	@Modifying
	@Query(value="truncate table cse_feedback", nativeQuery=true)
	Integer truncate();
	
	@Query("select f from CseFeedback f")
	List<CseFeedback> sortByField(Sort by);
	
	@Query("select f from CseFeedback f where facultyRegId = :facultyRegId")
	List<CseFeedback> getFeedbacksByFacultyId(@Param("facultyRegId") String facultyRegId);
	
	@Query("select f from CseFeedback f where subjectCode = :subjectCode")
	List<CseFeedback> getFeedbacksBySubjectCode(@Param("subjectCode") String subjectCode);
	
	@Query("select f from CseFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<CseFeedback> getFeedbacksBySubjectCodeAndFacultyRegId(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);
	
	@Query(value="select distinct subject_code from cse_feedback", nativeQuery=true)
	List<String> distinctSubjects();
	
	@Query(value="select distinct subject_code,faculty_reg_id from cse_feedback", nativeQuery=true)
	List<String> distinctSubjectsAndFaculty();
	
	@Query("select f.remarks from CseFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<String> getRemarks(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);	
	
}
