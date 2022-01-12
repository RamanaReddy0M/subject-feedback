package in.kvsr.admin.firstyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.FirstYearFaculty;


@Repository
public interface FirstYearFacultyRepository extends CrudRepository<FirstYearFaculty, Long> {
	@Query("select f from FirstYearFaculty f where regId = :regId")
	FirstYearFaculty getByRegId(@Param("regId") String regId);
	
	@Transactional
	@Modifying
	@Query("delete from FirstYearFaculty where regId = :regId")
	Integer deleteByRegId(@Param("regId") String regId);
	
	@Transactional
	@Modifying
	@Query(value="truncate table first_year_faculty", nativeQuery=true)
	Integer truncateAll();
	
	@Transactional
	@Modifying
	@Query(value="alter table first_year_faculty drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	
	@Transactional
	@Modifying
	@Query(value="alter table first_year_faculty add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
}
