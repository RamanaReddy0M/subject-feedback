package in.kvsr.faculty;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.FirstYearFaculty;

@Repository
public interface FirstYearFacultyRepository extends CrudRepository<FirstYearFaculty, Long> {

}
