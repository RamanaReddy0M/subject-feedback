package in.kvsr.admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
	@Query(value="select a from Admin a where a.regId = :regId")
	public Admin findByRegId(@Param("regId") String regId);
	
}
