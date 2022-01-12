package in.kvsr.admin.eee.fourthyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.eee.EeeFourTwo;

@Repository
public interface EeeFourTwoRepository extends CrudRepository<EeeFourTwo, Long> {

	
	@Transactional
	@Modifying
	@Query("delete from EeeFourTwo where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table eee_4_2 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table eee_4_2 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from EeeFourTwo s where subjectCode = :subjectCode")
	EeeFourTwo findBySubjectCode(String subjectCode);
}
