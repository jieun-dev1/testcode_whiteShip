package testcode_whiteship.inflearnjavatest.study;

import org.springframework.data.jpa.repository.JpaRepository;
import testcode_whiteship.inflearnjavatest.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
