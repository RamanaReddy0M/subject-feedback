package in.kvsr.admin.eee.firstyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.eee.EeeOneOne;
@Repository
public interface EeeOneOneRepository extends CrudRepository<EeeOneOne, Long> {
	@Transactional
	@Modifying
	@Query("delete from EeeOneOne where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table eee_1_1 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table eee_1_1 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from EeeOneOne s where subjectCode = :subjectCode")
	EeeOneOne findBySubjectCode(String subjectCode);

}
