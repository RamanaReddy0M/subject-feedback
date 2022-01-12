package in.kvsr.admin.secondyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.SecondYearFaculty;

@Repository
public interface SecondYearFacultyRepository extends CrudRepository<SecondYearFaculty, Long> {
	
	@Query("select f from SecondYearFaculty f where regId = :regId")
	SecondYearFaculty getByRegId(@Param("regId") String regId);
	
	@Transactional
	@Modifying
	@Query("delete from SecondYearFaculty where regId = :regId")
	Integer deleteByRegId(@Param("regId") String regId);
	
	@Transactional
	@Modifying
	@Query(value="truncate table second_year_faculty", nativeQuery=true)
	Integer truncateAll();
	
	@Transactional
	@Modifying
	@Query(value="alter table second_year_faculty drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	
	@Transactional
	@Modifying
	@Query(value="alter table second_year_faculty add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
}
