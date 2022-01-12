package in.kvsr.faculty;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.FourthYearFaculty;

@Repository
public interface FourthYearFacultyRepository extends CrudRepository<FourthYearFaculty, Long> {

}
