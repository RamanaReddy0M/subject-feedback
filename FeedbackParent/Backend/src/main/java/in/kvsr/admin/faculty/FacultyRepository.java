package in.kvsr.admin.faculty;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.Faculty;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long>{
	
	@Query("select f from Faculty f where regId = :regId")
	Faculty getByRegId(@Param("regId") String regId);
	
	@Query("select f from Faculty f")
	List<Faculty> findAllBySortOrder(Sort sort);
	
	@Query(value="select * from faculty f where f.designation like %:key% or "
			+ "f.department like %:key% or f.reg_id like %:key% or"
			+ " f.first_name like %:key% or f.last_name like %:key%",
			nativeQuery=true)	
	List<Faculty> searchByRegIdAndName(@Param("key") String key);
	
	@Transactional
	@Modifying
	@Query(value="alter table faculty drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table faculty add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();

}

