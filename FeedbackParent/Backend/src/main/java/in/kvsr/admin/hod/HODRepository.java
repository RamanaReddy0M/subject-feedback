package in.kvsr.admin.hod;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.HOD;

@Repository
public interface HODRepository extends CrudRepository<HOD, Long> {
	@Transactional
	@Modifying
	@Query("delete from HOD where regId = :regId")
	Integer deleteByRegId(@Param("regId") String regId);
	
	@Transactional
	@Modifying
	@Query(value="truncate table HOD", nativeQuery=true)
	Integer truncateAll();
	
	@Query("select H from HOD H where regId = :regId")
	HOD findByRegId(@Param("regId") String regId);
	
	@Transactional
	@Modifying
	@Query(value="alter table hod drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	
	@Transactional
	@Modifying
	@Query(value="alter table hod add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
}
