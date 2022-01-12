package in.kvsr.student;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.Student;
import in.kvsr.common.entity.Subject;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

	@Query("select s from Student s where rollNumber = :rollNumber")
	Student findByRollNumber(@Param("rollNumber") String rollNumber);

	/*
	 * CSE BRANCH
	 */

	/* I year */
	
	@Query("select s from CseOneOne s")
	List<Subject> getCseOneOneSubjects();

	@Query(value = "select distinct faculty_reg_id from cse_1_1", nativeQuery = true)
	List<String> getCseOneOneDistinctFRI();

    @Query("select s from CseOneOne s where facultyRegId = :facultyRegId")
	List<Subject> getCseOneOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from CseOneTwo s") 
	List<Subject> getCseOneTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from cse_1_2", nativeQuery = true) 
	List<String> getCseOneTwoDistinctFRI();
	  
	@Query("select s from CseOneTwo s where facultyRegId = :facultyRegId")
	List<Subject> getCseOneTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* II year*/
	  
	
	@Query("select s from CseTwoOne s")
	List<Subject> getCseTwoOneSubjects();

	@Query(value = "select distinct faculty_reg_id from cse_2_1", nativeQuery = true)
	List<String> getCseTwoOneDistinctFRI();

    @Query("select s from CseTwoOne s where facultyRegId = :facultyRegId")
	List<Subject> getCseTwoOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from CseTwoTwo s") 
	List<Subject> getCseTwoTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from cse_2_2", nativeQuery = true) 
	List<String> getCseTwoTwoDistinctFRI();
	  
	@Query("select s from CseTwoTwo s where facultyRegId = :facultyRegId")
	List<Subject> getCseTwoTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	  /*III year*/
	  
	@Query("select s from CseThreeOne s")
	List<Subject> getCseThreeOneSubjects();

	@Query(value = "select distinct faculty_reg_id from cse_3_1", nativeQuery = true)
	List<String> getCseThreeOneDistinctFRI();

    @Query("select s from CseThreeOne s where facultyRegId = :facultyRegId")
	List<Subject> getCseThreeOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from CseThreeTwo s") 
	List<Subject> getCseThreeTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from cse_3_2", nativeQuery = true) 
	List<String> getCseThreeTwoDistinctFRI();
	  
	@Query("select s from CseThreeTwo s where facultyRegId = :facultyRegId")
	List<Subject> getCseThreeTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* IV year */
	  
	@Query("select s from CseFourOne  s")
	List<Subject > getCseFourOneSubjects();

	@Query(value = "select distinct faculty_reg_id from cse_4_1", nativeQuery = true)
	List<String> getCseFourOneDistinctFRI();

    @Query("select s from CseFourOne  s where facultyRegId = :facultyRegId")
	List<Subject > getCseFourOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from CseFourTwo s") 
	List<Subject> getCseFourTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from cse_4_2", nativeQuery = true) 
	List<String> getCseFourTwoDistinctFRI();
	  
	@Query("select s from CseFourTwo s where facultyRegId = :facultyRegId")
	List<Subject> getCseFourTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	/* 
	 * ECE BRANCH
	 * 
	 */
	
    /* I year */
	
	@Query("select s from EceOneOne s")
	List<Subject> getEceOneOneSubjects();

	@Query(value = "select distinct faculty_reg_id from ece_1_1", nativeQuery = true)
	List<String> getEceOneOneDistinctFRI();

    @Query("select s from EceOneOne s where facultyRegId = :facultyRegId")
	List<Subject> getEceOneOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from EceOneTwo s") 
	List<Subject> getEceOneTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from ece_1_2", nativeQuery = true) 
	List<String> getEceOneTwoDistinctFRI();
	  
	@Query("select s from EceOneTwo s where facultyRegId = :facultyRegId")
	List<Subject> getEceOneTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* II year*/
	  
	
	@Query("select s from EceTwoOne s")
	List<Subject> getEceTwoOneSubjects();

	@Query(value = "select distinct faculty_reg_id from ece_2_1", nativeQuery = true)
	List<String> getEceTwoOneDistinctFRI();

    @Query("select s from EceTwoOne s where facultyRegId = :facultyRegId")
	List<Subject> getEceTwoOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from EceTwoTwo s") 
	List<Subject> getEceTwoTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from ece_2_2", nativeQuery = true) 
	List<String> getEceTwoTwoDistinctFRI();
	  
	@Query("select s from EceTwoTwo s where facultyRegId = :facultyRegId")
	List<Subject> getEceTwoTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	  /*III year*/
	  
	@Query("select s from EceThreeOne s")
	List<Subject> getEceThreeOneSubjects();

	@Query(value = "select distinct faculty_reg_id from ece_3_1", nativeQuery = true)
	List<String> getEceThreeOneDistinctFRI();

    @Query("select s from EceThreeOne s where facultyRegId = :facultyRegId")
	List<Subject> getEceThreeOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from EceThreeTwo s") 
	List<Subject> getEceThreeTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from ece_3_2", nativeQuery = true) 
	List<String> getEceThreeTwoDistinctFRI();
	  
	@Query("select s from EceThreeTwo s where facultyRegId = :facultyRegId")
	List<Subject> getEceThreeTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* IV year */
	  
	@Query("select s from EceFourOne  s")
	List<Subject > getEceFourOneSubjects();

	@Query(value = "select distinct faculty_reg_id from ece_4_1", nativeQuery = true)
	List<String> getEceFourOneDistinctFRI();

    @Query("select s from EceFourOne  s where facultyRegId = :facultyRegId")
	List<Subject > getEceFourOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from EceFourTwo s") 
	List<Subject> getEceFourTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from ece_4_2", nativeQuery = true) 
	List<String> getEceFourTwoDistinctFRI();
	  
	@Query("select s from EceFourTwo s where facultyRegId = :facultyRegId")
	List<Subject> getEceFourTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	
	/* 
	 * CIVIL BRANCH
	 * 
	 */

	/* I year */
	
	@Query("select s from CivilOneOne s")
	List<Subject> getCivilOneOneSubjects();

	@Query(value = "select distinct faculty_reg_id from civil_1_1", nativeQuery = true)
	List<String> getCivilOneOneDistinctFRI();

    @Query("select s from CivilOneOne s where facultyRegId = :facultyRegId")
	List<Subject> getCivilOneOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from CivilOneTwo s") 
	List<Subject> getCivilOneTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from civil_1_2", nativeQuery = true) 
	List<String> getCivilOneTwoDistinctFRI();
	  
	@Query("select s from CivilOneTwo s where facultyRegId = :facultyRegId")
	List<Subject> getCivilOneTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* II year*/
	  
	
	@Query("select s from CivilTwoOne s")
	List<Subject> getCivilTwoOneSubjects();

	@Query(value = "select distinct faculty_reg_id from civil_2_1", nativeQuery = true)
	List<String> getCivilTwoOneDistinctFRI();

    @Query("select s from CivilTwoOne s where facultyRegId = :facultyRegId")
	List<Subject> getCivilTwoOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from CivilTwoTwo s") 
	List<Subject> getCivilTwoTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from civil_2_2", nativeQuery = true) 
	List<String> getCivilTwoTwoDistinctFRI();
	  
	@Query("select s from CivilTwoTwo s where facultyRegId = :facultyRegId")
	List<Subject> getCivilTwoTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	  /*III year*/
	  
	@Query("select s from CivilThreeOne s")
	List<Subject> getCivilThreeOneSubjects();

	@Query(value = "select distinct faculty_reg_id from civil_3_1", nativeQuery = true)
	List<String> getCivilThreeOneDistinctFRI();

    @Query("select s from CivilThreeOne s where facultyRegId = :facultyRegId")
	List<Subject> getCivilThreeOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from CivilThreeTwo s") 
	List<Subject> getCivilThreeTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from civil_3_2", nativeQuery = true) 
	List<String> getCivilThreeTwoDistinctFRI();
	  
	@Query("select s from CivilThreeTwo s where facultyRegId = :facultyRegId")
	List<Subject> getCivilThreeTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* IV year */
	  
	@Query("select s from CivilFourOne  s")
	List<Subject > getCivilFourOneSubjects();

	@Query(value = "select distinct faculty_reg_id from civil_4_1", nativeQuery = true)
	List<String> getCivilFourOneDistinctFRI();

    @Query("select s from CivilFourOne  s where facultyRegId = :facultyRegId")
	List<Subject > getCivilFourOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from CivilFourTwo s") 
	List<Subject> getCivilFourTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from civil_4_2", nativeQuery = true) 
	List<String> getCivilFourTwoDistinctFRI();
	  
	@Query("select s from CivilFourTwo s where facultyRegId = :facultyRegId")
	List<Subject> getCivilFourTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	
	/*
	 * EEE BRANCH
	 */

	/* I year */
	
	@Query("select s from EeeOneOne s")
	List<Subject> getEeeOneOneSubjects();

	@Query(value = "select distinct faculty_reg_id from eee_1_1", nativeQuery = true)
	List<String> getEeeOneOneDistinctFRI();

    @Query("select s from EeeOneOne s where facultyRegId = :facultyRegId")
	List<Subject> getEeeOneOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from EeeOneTwo s") 
	List<Subject> getEeeOneTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from eee_1_2", nativeQuery = true) 
	List<String> getEeeOneTwoDistinctFRI();
	  
	@Query("select s from EeeOneTwo s where facultyRegId = :facultyRegId")
	List<Subject> getEeeOneTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* II year*/
	  
	
	@Query("select s from EeeTwoOne s")
	List<Subject> getEeeTwoOneSubjects();

	@Query(value = "select distinct faculty_reg_id from eee_2_1", nativeQuery = true)
	List<String> getEeeTwoOneDistinctFRI();

    @Query("select s from EeeTwoOne s where facultyRegId = :facultyRegId")
	List<Subject> getEeeTwoOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from EeeTwoTwo s") 
	List<Subject> getEeeTwoTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from eee_2_2", nativeQuery = true) 
	List<String> getEeeTwoTwoDistinctFRI();
	  
	@Query("select s from EeeTwoTwo s where facultyRegId = :facultyRegId")
	List<Subject> getEeeTwoTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	  /*III year*/
	  
	@Query("select s from EeeThreeOne s")
	List<Subject> getEeeThreeOneSubjects();

	@Query(value = "select distinct faculty_reg_id from eee_3_1", nativeQuery = true)
	List<String> getEeeThreeOneDistinctFRI();

    @Query("select s from EeeThreeOne s where facultyRegId = :facultyRegId")
	List<Subject> getEeeThreeOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from EeeThreeTwo s") 
	List<Subject> getEeeThreeTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from eee_3_2", nativeQuery = true) 
	List<String> getEeeThreeTwoDistinctFRI();
	  
	@Query("select s from EeeThreeTwo s where facultyRegId = :facultyRegId")
	List<Subject> getEeeThreeTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* IV year */
	  
	@Query("select s from EeeFourOne  s")
	List<Subject > getEeeFourOneSubjects();

	@Query(value = "select distinct faculty_reg_id from eee_4_1", nativeQuery = true)
	List<String> getEeeFourOneDistinctFRI();

    @Query("select s from EeeFourOne  s where facultyRegId = :facultyRegId")
	List<Subject > getEeeFourOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from EeeFourTwo s") 
	List<Subject> getEeeFourTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from eee_4_2", nativeQuery = true) 
	List<String> getEeeFourTwoDistinctFRI();
	  
	@Query("select s from EeeFourTwo s where facultyRegId = :facultyRegId")
	List<Subject> getEeeFourTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	
	/*
	 * MECHANICAL BRANCH
	 */

	/* I year */
	
	@Query("select s from MechanicalOneOne s")
	List<Subject> getMechanicalOneOneSubjects();

	@Query(value = "select distinct faculty_reg_id from mechanical_1_1", nativeQuery = true)
	List<String> getMechanicalOneOneDistinctFRI();

    @Query("select s from MechanicalOneOne s where facultyRegId = :facultyRegId")
	List<Subject> getMechanicalOneOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from MechanicalOneTwo s") 
	List<Subject> getMechanicalOneTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from mechanical_1_2", nativeQuery = true) 
	List<String> getMechanicalOneTwoDistinctFRI();
	  
	@Query("select s from MechanicalOneTwo s where facultyRegId = :facultyRegId")
	List<Subject> getMechanicalOneTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* II year*/
	  
	
	@Query("select s from MechanicalTwoOne s")
	List<Subject> getMechanicalTwoOneSubjects();

	@Query(value = "select distinct faculty_reg_id from mechanical_2_1", nativeQuery = true)
	List<String> getMechanicalTwoOneDistinctFRI();

    @Query("select s from MechanicalTwoOne s where facultyRegId = :facultyRegId")
	List<Subject> getMechanicalTwoOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from MechanicalTwoTwo s") 
	List<Subject> getMechanicalTwoTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from mechanical_2_2", nativeQuery = true) 
	List<String> getMechanicalTwoTwoDistinctFRI();
	  
	@Query("select s from MechanicalTwoTwo s where facultyRegId = :facultyRegId")
	List<Subject> getMechanicalTwoTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	  /*III year*/
	  
	@Query("select s from MechanicalThreeOne s")
	List<Subject> getMechanicalThreeOneSubjects();

	@Query(value = "select distinct faculty_reg_id from mechanical_3_1", nativeQuery = true)
	List<String> getMechanicalThreeOneDistinctFRI();

    @Query("select s from MechanicalThreeOne s where facultyRegId = :facultyRegId")
	List<Subject> getMechanicalThreeOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from MechanicalThreeTwo s") 
	List<Subject> getMechanicalThreeTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from mechanical_3_2", nativeQuery = true) 
	List<String> getMechanicalThreeTwoDistinctFRI();
	  
	@Query("select s from MechanicalThreeTwo s where facultyRegId = :facultyRegId")
	List<Subject> getMechanicalThreeTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	  
	 /* IV year */
	  
	@Query("select s from MechanicalFourOne  s")
	List<Subject > getMechanicalFourOneSubjects();

	@Query(value = "select distinct faculty_reg_id from mechanical_4_1", nativeQuery = true)
	List<String> getMechanicalFourOneDistinctFRI();

    @Query("select s from MechanicalFourOne  s where facultyRegId = :facultyRegId")
	List<Subject > getMechanicalFourOneSubjectsByFRI(@Param("facultyRegId") String facultyRegId);
	 
	@Query("select s from MechanicalFourTwo s") 
	List<Subject> getMechanicalFourTwoSubjects();
	  
	@Query(value = "select distinct faculty_reg_id from mechanical_4_2", nativeQuery = true) 
	List<String> getMechanicalFourTwoDistinctFRI();
	  
	@Query("select s from MechanicalFourTwo s where facultyRegId = :facultyRegId")
	List<Subject> getMechanicalFourTwoSubjectsByFRI(@Param("facultyRegId") String facultyRegId);

}
