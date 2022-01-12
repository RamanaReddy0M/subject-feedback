package in.kvsr.admin.cse.thirdyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.cse.CseThreeTwo;

@Repository
public interface CseThreeTwoRepository extends CrudRepository<CseThreeTwo, Long> {

	
	@Transactional
	@Modifying
	@Query("delete from CseThreeTwo where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table cse_3_2 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table cse_3_2 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from CseThreeTwo s where subjectCode = :subjectCode")
	CseThreeTwo findBySubjectCode(String subjectCode);
}
