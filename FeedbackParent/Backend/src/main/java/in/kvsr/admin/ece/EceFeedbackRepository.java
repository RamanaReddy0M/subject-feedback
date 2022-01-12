package in.kvsr.admin.ece;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.ece.EceFeedback;

@Repository
public interface EceFeedbackRepository extends CrudRepository<EceFeedback, Long> {

	@Transactional
	@Modifying
	@Query(value = "truncate table ece_feedback", nativeQuery = true)
	Integer truncate();

	@Query("select f from EceFeedback f")
	List<EceFeedback> sortByField(Sort by);

	@Query("select f from EceFeedback f where facultyRegId = :facultyRegId")
	List<EceFeedback> getFeedbacksByFacultyId(@Param("facultyRegId") String facultyRegId);

	@Query("select f from EceFeedback f where subjectCode = :subjectCode")
	List<EceFeedback> getFeedbacksBySubjectCode(@Param("subjectCode") String subjectCode);

	@Query("select f from EceFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<EceFeedback> getFeedbacksBySubjectCodeAndFacultyRegId(@Param("subjectCode") String subjectCode,
			@Param("facultyRegId") String facultyRegId);

	@Query(value = "select distinct subject_code from ece_feedback", nativeQuery = true)
	List<String> distinctSubjects();

	@Query(value = "select distinct subject_code,faculty_reg_id from ece_feedback", nativeQuery = true)
	List<String> distinctSubjectsAndFaculty();

	@Query("select f.remarks from EceFeedback f where subjectCode = :subjectCode and facultyRegId = :facultyRegId")
	List<String> getRemarks(@Param("subjectCode") String subjectCode, @Param("facultyRegId") String facultyRegId);

}
