package in.kvsr.admin.mechanical;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.mechanical.MechanicalFeedback;

@Repository
public interface MechanicalFeedbackRepository extends CrudRepository<MechanicalFeedback, Long>{
	
	@Transactional
	@Modifying
	@Query(value="truncate table mechanical_feedback", nativeQuery=true)
	Integer truncate();
	
	@Query("select f from MechanicalFeedback f")
	List<MechanicalFeedback> sortByField(Sort by);
	
	@Query("select f from MechanicalFeedback f where facultyRegId = :facultyRegId")
	List<MechanicalFeedback> getFeedbacksByFacultyId(@Param("facultyRegId") String facultyRegId);
	
	@Query("select f from MechanicalFeedback f where subjectCode = :subjectCode")
	List<MechanicalFeedback> getFeedbacksBySubjectCode(@Param("subjectCode") String subjectCode);
	
	@Query("select f from MechanicalFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<MechanicalFeedback> getFeedbacksBySubjectCodeAndFacultyRegId(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);
	
	@Query(value="select distinct subject_code from mechanical_feedback", nativeQuery=true)
	List<String> distinctSubjects();
	
	@Query(value="select distinct subject_code,faculty_reg_id from mechanical_feedback", nativeQuery=true)
	List<String> distinctSubjectsAndFaculty();
	
	@Query("select f.remarks from MechanicalFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<String> getRemarks(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);	
	
}
