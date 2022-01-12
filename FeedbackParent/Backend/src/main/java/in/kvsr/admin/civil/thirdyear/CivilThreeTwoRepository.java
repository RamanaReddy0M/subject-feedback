package in.kvsr.admin.civil.thirdyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.civil.CivilThreeTwo;

@Repository
public interface CivilThreeTwoRepository extends CrudRepository<CivilThreeTwo, Long> {

	
	@Transactional
	@Modifying
	@Query("delete from CivilThreeTwo where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table civil_3_2 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table civil_3_2 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from CivilThreeTwo s where subjectCode = :subjectCode")
	CivilThreeTwo findBySubjectCode(String subjectCode);
}
