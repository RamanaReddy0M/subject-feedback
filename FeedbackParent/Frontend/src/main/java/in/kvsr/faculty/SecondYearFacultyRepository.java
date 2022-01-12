package in.kvsr.faculty;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.kvsr.common.entity.SecondYearFaculty;

@Repository
public interface SecondYearFacultyRepository extends CrudRepository<SecondYearFaculty, Long> {

}
