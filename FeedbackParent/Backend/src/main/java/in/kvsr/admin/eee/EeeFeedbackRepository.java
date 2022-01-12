package in.kvsr.admin.eee;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.eee.EeeFeedback;

@Repository
public interface EeeFeedbackRepository extends CrudRepository<EeeFeedback, Long>{
	
	@Transactional
	@Modifying
	@Query(value="truncate table eee_feedback", nativeQuery=true)
	Integer truncate();
	
	@Query("select f from EeeFeedback f")
	List<EeeFeedback> sortByField(Sort by);
	
	@Query("select f from EeeFeedback f where facultyRegId = :facultyRegId")
	List<EeeFeedback> getFeedbacksByFacultyId(@Param("facultyRegId") String facultyRegId);
	
	@Query("select f from EeeFeedback f where subjectCode = :subjectCode")
	List<EeeFeedback> getFeedbacksBySubjectCode(@Param("subjectCode") String subjectCode);
	
	@Query("select f from EeeFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<EeeFeedback> getFeedbacksBySubjectCodeAndFacultyRegId(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);
	
	@Query(value="select distinct subject_code from eee_feedback", nativeQuery=true)
	List<String> distinctSubjects();
	
	@Query(value="select distinct subject_code,faculty_reg_id from eee_feedback", nativeQuery=true)
	List<String> distinctSubjectsAndFaculty();
	
	@Query("select f.remarks from EeeFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<String> getRemarks(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);	
	
}
