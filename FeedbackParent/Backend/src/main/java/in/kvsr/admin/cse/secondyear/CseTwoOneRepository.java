package in.kvsr.admin.cse.secondyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.cse.CseTwoOne;

@Repository
public interface CseTwoOneRepository extends CrudRepository<CseTwoOne, Long> {
	
	@Transactional
	@Modifying
	@Query("delete from CseTwoOne where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table cse_2_1 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table cse_2_1 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from CseTwoOne s where subjectCode = :subjectCode")
	CseTwoOne findBySubjectCode(String subjectCode);

}
