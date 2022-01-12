package in.kvsr.admin.mechanical.fourthyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.mechanical.MechanicalFourTwo;

@Repository
public interface MechanicalFourTwoRepository extends CrudRepository<MechanicalFourTwo, Long> {

	
	@Transactional
	@Modifying
	@Query("delete from MechanicalFourTwo where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);
	
	@Transactional
	@Modifying
	@Query(value="alter table mechanical_4_2 drop column id", nativeQuery=true)
	Integer dropIdColumn();
	
	@Transactional
	@Modifying
	@Query(value="alter table mechanical_4_2 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery=true)
	Integer addIdColumn();
	
	@Query("select s from MechanicalFourTwo s where subjectCode = :subjectCode")
	MechanicalFourTwo findBySubjectCode(String subjectCode);
}
