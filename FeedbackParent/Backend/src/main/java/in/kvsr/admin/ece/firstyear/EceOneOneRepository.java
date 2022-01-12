package in.kvsr.admin.ece.firstyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.ece.EceOneOne;
@Repository
public interface EceOneOneRepository extends CrudRepository<EceOneOne, Long> {
	@Transactional
	@Modifying
	@Query("delete from EceOneOne where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table ece_1_1 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table ece_1_1 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from EceOneOne s where subjectCode = :subjectCode")
	EceOneOne findBySubjectCode(String subjectCode);

}
