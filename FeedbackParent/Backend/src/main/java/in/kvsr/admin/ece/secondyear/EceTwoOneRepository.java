package in.kvsr.admin.ece.secondyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.ece.EceTwoOne;

@Repository
public interface EceTwoOneRepository extends CrudRepository<EceTwoOne, Long> {
	
	@Transactional
	@Modifying
	@Query("delete from EceTwoOne where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table ece_2_1 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table ece_2_1 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from EceTwoOne s where subjectCode = :subjectCode")
	EceTwoOne findBySubjectCode(String subjectCode);

}
