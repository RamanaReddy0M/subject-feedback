package in.kvsr.admin.ece.secondyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.ece.EceTwoTwo;

@Repository
public interface EceTwoTwoRepository extends CrudRepository<EceTwoTwo, Long> {

	
	@Transactional
	@Modifying
	@Query("delete from EceTwoTwo where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table ece_2_2 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table ece_2_2 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from EceTwoTwo s where subjectCode = :subjectCode")
	EceTwoTwo findBySubjectCode(String subjectCode);
}
