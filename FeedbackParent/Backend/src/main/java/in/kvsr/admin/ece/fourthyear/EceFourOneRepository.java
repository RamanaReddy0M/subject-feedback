package in.kvsr.admin.ece.fourthyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.ece.EceFourOne;

@Repository
public interface EceFourOneRepository extends CrudRepository<EceFourOne, Long> {

	@Transactional
	@Modifying
	@Query("delete from EceFourOne where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);

	@Transactional
	@Modifying
	@Query(value = "alter table ece_4_1 drop column id", nativeQuery = true)
	Integer dropIdColumn();

	@Transactional
	@Modifying
	@Query(value = "alter table ece_4_1 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery = true)
	Integer addIdColumn();

	@Query("select s from EceFourOne s where subjectCode = :subjectCode")
	EceFourOne findBySubjectCode(String subjectCode);

}
