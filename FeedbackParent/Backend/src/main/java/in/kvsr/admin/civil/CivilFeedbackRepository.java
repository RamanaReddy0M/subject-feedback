package in.kvsr.admin.civil;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.civil.CivilFeedback;

@Repository
public interface CivilFeedbackRepository extends CrudRepository<CivilFeedback, Long>{
	
	@Transactional
	@Modifying
	@Query(value="truncate table civil_feedback", nativeQuery=true)
	Integer truncate();
	
	@Query("select f from CivilFeedback f")
	List<CivilFeedback> sortByField(Sort by);
	
	@Query("select f from CivilFeedback f where facultyRegId = :facultyRegId")
	List<CivilFeedback> getFeedbacksByFacultyId(@Param("facultyRegId") String facultyRegId);
	
	@Query("select f from CivilFeedback f where subjectCode = :subjectCode")
	List<CivilFeedback> getFeedbacksBySubjectCode(@Param("subjectCode") String subjectCode);
	
	@Query("select f from CivilFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<CivilFeedback> getFeedbacksBySubjectCodeAndFacultyRegId(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);
	
	@Query(value="select distinct subject_code from civil_feedback", nativeQuery=true)
	List<String> distinctSubjects();
	
	@Query(value="select distinct subject_code,faculty_reg_id from civil_feedback", nativeQuery=true)
	List<String> distinctSubjectsAndFaculty();
	
	@Query("select f.remarks from CivilFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<String> getRemarks(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);	
	
}
