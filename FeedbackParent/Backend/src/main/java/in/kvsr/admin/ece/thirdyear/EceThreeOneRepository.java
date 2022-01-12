package in.kvsr.admin.ece.thirdyear;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.ece.EceThreeOne;

@Repository
public interface EceThreeOneRepository extends CrudRepository<EceThreeOne, Long> {

	@Transactional
	@Modifying
	@Query("delete from EceThreeOne where subjectCode = :subjectCode")
	public Integer deleteBySubCode(@Param("subjectCode") String subjectCode);

	@Transactional
	@Modifying
	@Query(value = "alter table ece_3_1 drop column id", nativeQuery = true)
	Integer dropIdColumn();

	@Transactional
	@Modifying
	@Query(value = "alter table ece_3_1 add column id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY", nativeQuery = true)
	Integer addIdColumn();

	@Query("select s from EceThreeOne s where subjectCode = :subjectCode")
	EceThreeOne findBySubjectCode(String subjectCode);
}
