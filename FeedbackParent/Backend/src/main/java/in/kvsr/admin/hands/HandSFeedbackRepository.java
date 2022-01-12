package in.kvsr.admin.hands;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.HandSFeedback;

@Repository
public interface HandSFeedbackRepository extends CrudRepository<HandSFeedback, Long>{

	
	@Transactional
	@Modifying
	@Query(value="truncate table h_and_s_feedback", nativeQuery=true)
	Integer truncate();
	
	@Query("select f from HandSFeedback f")
	List<HandSFeedback> sortByField(Sort by);
	
	@Query("select f from HandSFeedback f where facultyRegId = :facultyRegId")
	List<HandSFeedback> getFeedbacksByFacultyId(@Param("facultyRegId") String facultyRegId);
	
	@Query("select f from HandSFeedback f where subjectCode = :subjectCode")
	List<HandSFeedback> getFeedbacksBySubjectCode(@Param("subjectCode") String subjectCode);
	
	@Query("select f from HandSFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<HandSFeedback> getFeedbacksBySubjectCodeAndFacultyRegId(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);
	
	@Query(value="select distinct subject_code from h_and_s_feedback", nativeQuery=true)
	List<String> distinctSubjects();
	
	@Query(value="select distinct subject_code,faculty_reg_id from h_and_s_feedback", nativeQuery=true)
	List<String> distinctSubjectsAndFaculty();
	
	@Query("select f.remarks from HandSFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<String> getRemarks(@Param("subjectCode") String subjectCode, 
			                 @Param("facultyRegId") String facultyRegId);
}
