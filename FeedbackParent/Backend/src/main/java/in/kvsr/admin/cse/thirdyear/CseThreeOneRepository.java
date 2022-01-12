package in.kvsr.admin.cse.thirdyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.cse.CseThreeOne;

@Repository
public interface CseThreeOneRepository extends CrudRepository<CseThreeOne, Long> {

	
	@Transactional
	@Modifying
	@Query("delete from CseThreeOne where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table cse_3_1 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table cse_3_1 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from CseThreeOne s where subjectCode = :subjectCode")
	CseThreeOne findBySubjectCode(String subjectCode);
}
