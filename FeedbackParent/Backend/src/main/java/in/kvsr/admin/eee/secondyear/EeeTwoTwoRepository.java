package in.kvsr.admin.eee.secondyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.eee.EeeTwoTwo;

@Repository
public interface EeeTwoTwoRepository extends CrudRepository<EeeTwoTwo, Long> {

	
	@Transactional
	@Modifying
	@Query("delete from EeeTwoTwo where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table eee_2_2 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table eee_2_2 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from EeeTwoTwo s where subjectCode = :subjectCode")
	EeeTwoTwo findBySubjectCode(String subjectCode);
}
