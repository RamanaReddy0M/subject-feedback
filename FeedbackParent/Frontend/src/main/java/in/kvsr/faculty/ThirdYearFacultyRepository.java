package in.kvsr.faculty;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import in.kvsr.common.entity.ThirdYearFaculty;

@Repository
public interface ThirdYearFacultyRepository extends CrudRepository<ThirdYearFaculty, Long> {

}
