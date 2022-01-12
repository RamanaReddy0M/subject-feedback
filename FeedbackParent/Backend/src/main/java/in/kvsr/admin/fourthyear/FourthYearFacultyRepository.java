package in.kvsr.admin.fourthyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.FourthYearFaculty;

@Repository
public interface FourthYearFacultyRepository extends CrudRepository<FourthYearFaculty, Long> {
	
	@Query("select f from FourthYearFaculty f where regId = :regId")
	FourthYearFaculty getByRegId(@Param("regId") String regId);
	
	@Transactional
	@Modifying
	@Query("delete from FourthYearFaculty where regId = :regId")
	Integer deleteByRegId(@Param("regId") String regId);
	
	@Transactional
	@Modifying
	@Query(value="truncate table fourth_year_faculty", nativeQuery=true)
	Integer truncateAll();
	
	@Transactional
	@Modifying
	@Query(value="alter table fourth_year_faculty drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	
	@Transactional
	@Modifying
	@Query(value="alter table fourth_year_faculty add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
}
