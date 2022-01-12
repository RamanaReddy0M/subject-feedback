package in.kvsr.admin.civil.thirdyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.civil.CivilThreeOne;

@Repository
public interface CivilThreeOneRepository extends CrudRepository<CivilThreeOne, Long> {

	
	@Transactional
	@Modifying
	@Query("delete from CivilThreeOne where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table civil_3_1 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table civil_3_1 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from CivilThreeOne s where subjectCode = :subjectCode")
	CivilThreeOne findBySubjectCode(String subjectCode);
}
