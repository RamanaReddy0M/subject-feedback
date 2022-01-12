package in.kvsr.faculty;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.Faculty;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long> {
	
	@Query("select f from Faculty f where regId = :regId")
	Faculty findByRegId(@Param("regId") String regId);
}
