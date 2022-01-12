package in.kvsr.admin.students;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
	@Query("select s from Student s where rollNumber = :rollNumber")
	Student getByRegId(@Param("rollNumber") String rollNumber);
	
	@Query("select s from Student s")
	List<Student> findAllBySortOrder(Sort sort);
	
	@Query(
	     value="select * from student s where s.branch like %:key% or "
			+ "s.roll_number like %:key% or s.first_name like %:key% or"
			+ " s.last_name like %:key% or s.year like %:key%", 
			nativeQuery=true)
	List<Student> searchByRollNoAndName(@Param("key") String key);
	
	@Transactional
	@Modifying
	@Query(value="alter table student drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table student add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();

}
